package pl.kopciu.organism;

import java.awt.Graphics2D;

/**
 * Klasa- wêze³ uk³adów krwionoœnego lub immunologicznego
 * @author Kopciu
 *
 */
public class Node extends Thing{
	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	private volatile SystemRoad roads[][];
	private volatile MobileThing erythrocyteOnBoard, immuOnBoard;
	private volatile double distance;
	private volatile Node from;
	
	/**
	 * 
	 * @param organism referencja na organizm
	 */
	public Node(){
		roads=new SystemRoad[5][2];
	}
	/**
	 * Zwraca obiekt SystemRoad
	 * @param i indeks œcie¿ki, 0-4
	 * @param type typ uk³adu-patrz sta³e w TransportSystem
	 * @return ¿¹dana œcie¿ka 
	 */
	public SystemRoad getRoads(int i, int type) {
		return roads[i][type];
	}
	/**
	 * Dodaje drogê do wêz³a
	 * @param road droga do dodania
	 * @param i indeks œcie¿ki, 0-4
	 * @param type typ uk³adu-patrz sta³e w TransportSystem
	 */
	public void setRoads(SystemRoad road, int i, int type) {
		this.roads[i][type] = road;
	}
	/**
	 * Zwraca Erytrocyt znajduj¹cy siê obecnie w wêŸle
	 * @return
	 */
	public MobileThing getErythrocyteOnBoard() {
		return erythrocyteOnBoard;
	}
	/**
	 * Umieszcza Krwinkê/Wirusa w wêŸle
	 * @param mobileThing krwinka/wirus
	 * @param isE czy obiekt jest erytrocytem?
	 * @return czy operacja siê uda³a?
	 */
	public synchronized boolean setMobileOnBoard(MobileThing mobileThing,boolean isE) {
		if(isE){
			if(this.erythrocyteOnBoard!=null && this.erythrocyteOnBoard.isDead())
				this.erythrocyteOnBoard=null;
			if(this.erythrocyteOnBoard==null){
				this.erythrocyteOnBoard = mobileThing;
				return true;
			}
			else if(mobileThing==null){
				this.erythrocyteOnBoard=null;
				return true;
			}
			return false;
		}
		else{
			if(this.immuOnBoard!=null && this.immuOnBoard.isDead())
				this.immuOnBoard=null;
			if(this.immuOnBoard==null){
				this.immuOnBoard = mobileThing;
				return true;
			}
			else if(mobileThing==null){
				this.immuOnBoard=null;
				return true;
			}
		/*	if(immuOnBoard.getClass()!=mobileThing.getClass()){
				if(mobileThing.getClass()==Leukocyte.class){
					organism.virusKilled((Leukocyte)mobileThing, (Virus)immuOnBoard);
				}
				else{
					organism.virusKilled((Leukocyte)immuOnBoard, (Virus)mobileThing);
				}
				immuOnBoard=null;
			}*/
			return false;
		}
	}
	/**
	 * 
	 * @return czy wêze³ jest przejezdny?(Czy nie znajduje siê tam zepsuty erytrocyt)
	 */
	@Override
	public void draw(Graphics2D g2d){
		super.draw(g2d);
		if(!isAvailable())
			g2d.fillOval(this.getPosition().x, this.getPosition().y, this.getSize().width, this.getSize().height);
	}
	/**
	 * czy wêzel jest przejezdny?
	 * @return
	 */
	public synchronized boolean isAvailable(){
		if(erythrocyteOnBoard!=null && ((Erythrocyte) erythrocyteOnBoard).isEmbolised())
			return false;
		return true;
	}
	/**
	 * Zwraca Wirusa/Leukocyt znajduj¹cy siê w wêŸle
	 * @return
	 */
	public MobileThing getImmuOnBoard() {
		return immuOnBoard;
	}
	/**
	 * Zwraca dystans od wêz³a pocz¹tkowego (parametr potrzebny do wyszukiwania œcie¿ek)
	 * @return
	 */
	public double getDistance() {
		return distance;
	}
	/**
	 * Ustawia dystans od wêz³a pocz¹tkowego (parametr potrzebny do wyszukiwania œcie¿ek)
	 * @param distance
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/**
	 * Zwraca wêze³, z któego algorytm przeszed³ do danego wêz³a(wyszukiwanie œcie¿ek)
	 * @return
	 */
	public Node getFrom() {
		return from;
	}
	/**
	 * Ustawia wêze³, z któego algorytm przeszed³ do danego wêz³a(wyszukiwanie œcie¿ek)
	 * @param from
	 */
	public void setFrom(Node from) {
		this.from = from;
	}
}
