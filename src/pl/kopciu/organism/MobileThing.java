package pl.kopciu.organism;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
/**
 * Klasa bazowa dla krwinek i patogenów, opisuj¹ca poruszaj¹cy siê obiekt
 * @author Kopciu
 *
 */
public class MobileThing extends Thing implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	private transient static boolean finished=false;
	private transient static int currentId=0; 
	private int id;
	private LinkedList<Node> path;
	private double speed;
	private Node lastVisited;
	private boolean isNearNode, isInNode;
	private int stepCounter=0;
	private Point direction;

	private AffineTransform transform;
	private double rotation=0;
	private Organism organism;
	
	
	/**
	 * zwraca Id, które dostanie nastêpny mobilny obiekt
	 * @return
	 */
	/**
	 * ustawia Id, które dostanie nastêpny mobilny obiekt
	 * @return
	 */
	public static int getCurrentId() {
		return currentId;
	}
	public static void setCurrentId(int currentId) {
		MobileThing.currentId = currentId;
	}
	/**
	 * Konstruktor
	 * @param host Wêze³, w którym krwinka zostaje umieszczona po stworzeniu
	 * @param organism referencja na organizm w którym obiekt zostaje utworzony
	 */
	public MobileThing(Node host, Organism organism) {
		this.organism=organism;
		this.id=currentId++;
		direction=new Point();
		path=new LinkedList<Node>();
		this.lastVisited=host;
		transform= new AffineTransform();
		if(path.size()>1)
			rotation=Math.atan2(path.get(1).getMiddlePosition().y-path.get(0).getMiddlePosition().y, path.get(1).getMiddlePosition().x-path.get(0).getMiddlePosition().x)+Math.PI/2;
		new Thread(this).start();
	}
	@Override
	public boolean isClicked(int x, int y){
		return new Point(x, y).distanceSq(this.getMiddlePosition())<1600;
	}
	/**
	 * Ustawia flagê odpowiadaj¹cy za zatrzymanie ruchu wszystkich obiektów
	 * @param finished
	 */
	public static void setFinished(boolean finished) {
		MobileThing.finished = finished;
	}
	/**
	 * Sprawdza, wartoœæ flagi odpowiadaj¹ca za zatrzymanie ruchu wszytskich obiektów
	 * @return
	 */
	public static boolean isFinished() {
		return finished;
	}
	/**
	 * 
	 * @return ostatni Wêze³, który odwiedzi³ obiekt
	 */
	public Node getLastVisited() {
		return lastVisited;
	}
	/**
	 * 
	 * @return Id obiektu
	 */
	public int getId() {
		return id;
	}
	/**
	 * ustawia trasê obiektu na przekazan¹ w parametrze
	 * @param path
	 */
	protected void changePath(LinkedList<Node> path){
		this.path=path;
	}
	/**
	 * Zwraca wêze³, który jest nastêpny na trasie obiektu
	 * @return null, o ile obiekt nie ma okreœlonej dalszej trasy
	 */
	protected Node getNextNode(){
		if(path.isEmpty())
			return null;
		else
			return path.getFirst();
	}
	@Override
	public void draw(Graphics2D g2d){
		AffineTransform at= g2d.getTransform();
		transform=(AffineTransform) at.clone();
		transform.rotate(rotation, this.getMiddlePosition().x, this.getMiddlePosition().y);
		if(transform!=null){		
			g2d.setTransform(transform);
		}
		super.draw(g2d);
		g2d.setTransform(at);
}
	@Override
	public void run(){
		if(!path.isEmpty()){
			direction.x=0;
			direction.y=0;
			Node node=path.get(0);
			boolean isE=this.getClass()==Erythrocyte.class;
			if(!isNearNode){
				isNearNode=runTo(node, (isE?0:1));
			}
			if(isNearNode)
				if(isInNode || node.setMobileOnBoard(this, isE)){
					this.setMiddlePosition(node.getMiddlePosition());
					isInNode=!rotateInNode();
					if(isInNode || !canMove())
						return;
					goThroughNode(node);
					isInNode=false;
					if(getNextNode()!=null && isE){
						SystemRoad r=null;
						for(int i=0; i<5; i++){
							if(lastVisited.getRoads(i, 0)!=null)
								if(lastVisited.getRoads(i, 0).getEndOrgan()==getNextNode())
										r=lastVisited.getRoads(i, 0);
						}
						if(r==null || r.isEmbolised() || !r.getEndOrgan().isAvailable())
							changePath(organism.getCirculatorySystem().getShortesPathToOrgan(lastVisited, path.getLast()));
					}
					synchronized (this) {
						isNearNode=false;
						node.setMobileOnBoard(null, isE);
					}

				}
			
		}
		
	}
	/**
	 *
	 * @return Czy krwinka mo¿e siê poruszaæ?
	 */
	public boolean canMove() {
		return !isDead();
	}
	private boolean goThroughNode(Node node){
		lastVisited=node;
		path.pop();
		stepCounter=0;
		return true;
	}
	

	private synchronized boolean runTo(Node node, int type){
		if(node==lastVisited) return true;
		Point posF =lastVisited.getMiddlePosition();
		Point posT =node.getMiddlePosition();
		SystemRoad r=null;
		speed=0;
		for(int i=0; i<5; i++){
			if(lastVisited.getRoads(i, type)!=null)
				if(lastVisited.getRoads(i, type).getEndOrgan()==node)
						r=lastVisited.getRoads(i, type);
		}
		if(r==null ||  posF==null || posT==null || r.isEmbolised()) return false;
		double time=50*stepCounter;
		stepCounter++;
		speed = r.getTransportSpeed()*getSelfSpeed();
		double dysX = time*(posT.x-posF.x)/1000*speed/Constans.SPEED_DIV_FACTOR;			
		double dysY = time*(posT.y-posF.y)/1000*speed/Constans.SPEED_DIV_FACTOR;
		direction.x=(int) Math.signum(dysX);
		direction.y=(int) Math.signum(dysY);
		posF.x+=dysX;
		posF.y+=dysY;
		rotation=Math.atan2(r.getEndOrgan().getMiddlePosition().y-lastVisited.getMiddlePosition().y, r.getEndOrgan().getMiddlePosition().x-lastVisited.getMiddlePosition().x)+Math.PI/2;
		if(type==TransportSystem.IMMUNOLOGICAL_SYSTEM || lastVisited.getClass()!=Organ.class){
			if(dysX<0)
				posF.y-=10;
			if(dysY>0)
				posF.x-=10;
		}
		if(type==TransportSystem.CIRCULATORY_SYSTEM && lastVisited.getClass()==Organ.class){
			posF.y+=dysX>0?10:-10;
		}
		if(type==TransportSystem.CIRCULATORY_SYSTEM && ((Erythrocyte)this).isEmbolised())
			return false;
		posF.y-=6;
		this.setPosition(posF);
		if(/*arrived && */posF.distance(posT)<=25)
			return true;
		return false;	
	}
	private boolean rotateInNode(){
		if(path.size()<2)
			return true;
		if(rotation>=Math.PI)
			rotation-=2*Math.PI;
		if(rotation<-Math.PI)
			rotation+=2*Math.PI;
		double desiredRot=Math.atan2(path.get(1).getMiddlePosition().y-path.get(0).getMiddlePosition().y, path.get(1).getMiddlePosition().x-path.get(0).getMiddlePosition().x)+Math.PI/2;
		if(desiredRot>Math.PI)
			desiredRot-=2*Math.PI;
		double difference=rotation-desiredRot;
		if(difference>Math.PI)
			difference=2*Math.PI-difference;
		else if(difference<-Math.PI)
			difference=2*Math.PI+difference;
		rotation-=Math.PI/45*Math.signum(difference);
		if(Math.PI/45>Math.abs(difference)){
				return true;
		}
		return false;
	}
	/**
	 * 
	 * @return Czy wyznaczona trasa obiektu jest spójna?
	 */
	protected boolean isPathValid(){
		if(path.size()==1 && path.getFirst()==lastVisited){
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @return aktualna prêdkoœæ obiektu
	 */
	public double getCurrentSpeed() {
		return speed;
	}
	/**
	 * 
	 * @return czy obiekt znajduje siê na skrzy¿owaniu?
	 */
	protected boolean isNearNode() {
		return isNearNode;
	}
	/**
	 * Zwraca kierunek poruszania siê Obiektu-wartoœci sk³¹dowych punktu mog¹ przyjmowaæ wartoœci -1, 0, 1
	 * @return
	 */
	public Point getDirection() {
		return direction;
	}
	/**
	 * Sprawdza, czy parametr porusza siê w tym samym kierunku co Obiekt
	 * @param mt
	 * @return
	 */
	protected boolean sameDirections(MobileThing mt){
		if((mt.getDirection().x==0 && mt.getDirection().y==0) || (getDirection().x==0 && getDirection().y==0))
			return true;
		if(mt.getDirection().x==-getDirection().x || mt.getDirection().y==-getDirection().y)
			return false;
		return true;
	}
	/**
	 * 
	 * @return modyfikator prêdkoœci w³aœciwy dla danego typu Obiektu
	 */
	protected double getSelfSpeed(){
		return 1.0;
	}
}
