package pl.kopciu.organism;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Random;


import javax.imageio.ImageIO;

/**G��wna klasa do zarz�dzania ca�ym organizmem */
public class Organism implements Serializable{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = Constans.serialVersionUID;
	private static final int[][] ORGAN_SRCS={{0, 0}, {0, 1}, {0, 2}, {1, 2}, {1, 0}, {0, 3}, {1, 1}, {1, 3}, {2, 3}, {2, 2}};
	private TransportSystem immunologicalSystem, circulatorySystem;
	private Organ[] organs;
	private LinkedList<Erythrocyte> erythrocytes;
	private LinkedList<Leukocyte> leukocytes;
	private LinkedList<Virus> viruses;
	private final int nodeSize=25;
	private Dimension windowSize;
	private int distanceX, distanceY;
	private Point startDrawPosition;
	private int marrowNr=-1, killedPatogens=0;
	private RefreshManager rm;
	private Thing popupSubject;
	private Calendar startTime, lastMobileTime=Calendar.getInstance();
	private Random random;
	private int organsAlive;
	private int level;
	private boolean started=false;
	
	private transient GUI gui;
	
	private transient Image bigImg;
	
	/**
	 * Konstruktor
	 * @param gameWindowSize rozmiar okna programu
	 * @param gui referencja na interfejs graficzny
	 */
	public Organism(Dimension gameWindowSize, GUI gui){
		this.windowSize= gameWindowSize;
		this.gui=gui;
		organs=new Organ[Constans.ORGAN_NAMES.length];
		level=2;
		erythrocytes= new LinkedList<Erythrocyte>();
		leukocytes= new LinkedList<Leukocyte>();
		viruses= new LinkedList<Virus>();
		random=new Random();
		organsAlive=Constans.ORGAN_NAMES.length;
			java.net.URL imgURL = getClass().getResource("/images/grid.png");
		    if (imgURL == null) {
		    	try {
					bigImg=ImageIO.read(new File("src/images/grid.png"));
				} catch (IOException e) {
					System.out.println("blad grafik");
				}
		    	
		    }
		    else{
		    	try {
		    		bigImg=ImageIO.read(imgURL);
		    	} catch (IOException e) {
		    		System.out.println("blad grafik");
		    		System.exit(-1);
		    	}
		    }
		Thing.setGridImage(bigImg);
		SystemRoad.setGridImage(bigImg);
		for(int i=0; i<Constans.ORGAN_NAMES.length; i++){
			organs[i]=new Organ(i, this);
		}
		prepareGraphics(windowSize);
		popupSubject=null;
		circulatorySystem= new TransportSystem(TransportSystem.CIRCULATORY_SYSTEM, this);
		immunologicalSystem = new TransportSystem(TransportSystem.IMMUNOLOGICAL_SYSTEM, this);
		startDrawPosition=new Point(organs[0].getMiddlePosition());
		distanceX=(organs[1].getMiddlePosition().x-startDrawPosition.x);
		distanceY=(organs[4].getMiddlePosition().y-startDrawPosition.y);
		startDrawPosition.x-=distanceX/2;
		startDrawPosition.y-=distanceY/2;
		startTime=Calendar.getInstance();
		rm= new RefreshManager();
		new Thread(rm).start();

	}
	/**
	 * 
	 * @return uk�ad odporno�ciowy organizmu
	 */
	public TransportSystem getImmunologicalSystem() {
		return immunologicalSystem;
	}
	/**
	 * 
	 * @return uk�ad krwiono�ny organizmu
	 */
	public TransportSystem getCirculatorySystem() {
		return circulatorySystem;
	}
	private void prepareGraphics(Dimension gameWindowSize){
		Point p;
		for(int i=0; i<3; i++){
				for(int j=0; j<4; j++){
					p=new Point(j*128+(j+1)*(gameWindowSize.height-512)/5+(gameWindowSize.width-gameWindowSize.height)/2, 50+ i*128+(i+1)*(gameWindowSize.height-512)/4);
					organs[4*i+j].setGUICoordinates(p, ORGAN_SRCS[4*i+j][1]*256, ORGAN_SRCS[4*i+j][0]*256, ORGAN_SRCS[4*i+j][1]*256+256, ORGAN_SRCS[4*i+j][0]*256+256, new Dimension(128, 128));
					if(4*i+j==9) return;
				}
		}
	}

	public void drawOrganism(Graphics2D g2d){
		try{
		if(circulatorySystem!=null)
			circulatorySystem.drawTransportSystem(g2d);

		for(Erythrocyte e: erythrocytes)
			e.draw(g2d);
		if(immunologicalSystem!=null)
			immunologicalSystem.drawTransportSystem(g2d);
		for (int i=0; i<Constans.ORGAN_NAMES.length; i++){
			if(organs[i]!=null){
				organs[i].draw(g2d);
			}
		}
		for(Leukocyte l: leukocytes)
			l.draw(g2d);
		for(Virus v: viruses)
			v.draw(g2d);
		drawPopup(g2d);
		}
		catch(Exception e) {}
	}
	private void drawPopup(Graphics2D g2d){
		if(popupSubject==null) return;
		boolean isMobile=popupSubject.getClass()!=Organ.class;
		if(isMobile){
			if(((MobileThing) popupSubject).isDead()){
				popupSubject=null;
				return;
			}
		}
		Dimension popupSize;
		int x, y;
		
		if(isMobile){
			if(popupSubject.getClass()==Erythrocyte.class){
				popupSize=new Dimension(100, 50);
				x=popupSubject.getPosition().x+popupSubject.getSize().width+5;
				y=popupSubject.getMiddlePosition().y-popupSize.height;
				g2d.setColor(Color.WHITE);
				g2d.fillRect(x, y, popupSize.width, popupSize.height);
				g2d.setColor(Color.BLACK);
				g2d.drawRect(x, y, popupSize.width, popupSize.height);
				g2d.setFont(g2d.getFont().deriveFont((float) 9.0));
				g2d.drawString(popupSubject.toString(), x+5, y+10);
				g2d.drawString(String.format("Speed: %.2f", ((MobileThing)popupSubject).getCurrentSpeed()*((MobileThing)popupSubject).getSelfSpeed()), x+5, y+20);
				g2d.drawString("Capacity: "+((Erythrocyte)popupSubject).getCapacity()+"/"+((Erythrocyte)popupSubject).getMaxCapacity(), x+5, y+30);
				g2d.drawString("Zepsuty?: "+(((Erythrocyte)popupSubject).isEmbolised()?"tak":"nie"), x+5, y+40);
			}
			else{
				if(popupSubject.getClass()==Leukocyte.class)
					popupSize=new Dimension(100, 35);
				else
					popupSize=new Dimension(100, 45);
				x=popupSubject.getPosition().x+popupSubject.getSize().width+5;
				y=popupSubject.getMiddlePosition().y-popupSize.height;
				g2d.setColor(Color.WHITE);
				g2d.fillRect(x, y, popupSize.width, popupSize.height);
				g2d.setColor(Color.BLACK);
				g2d.drawRect(x, y, popupSize.width, popupSize.height);
				g2d.setFont(g2d.getFont().deriveFont((float) 9.0));
				g2d.drawString(popupSubject.toString(), x+5, y+10);
				g2d.drawString(String.format("Speed: %.2f", ((MobileThing)popupSubject).getCurrentSpeed()*((MobileThing)popupSubject).getSelfSpeed()), x+5, y+20);
				if(popupSubject.getClass()==Leukocyte.class)
					g2d.drawString("Target: "+((Leukocyte)popupSubject).getTarget(), x+5, y+30);
				else{
					g2d.drawString("Target: "+((Virus)popupSubject).getTarget(), x+5, y+30);
					g2d.drawString("�ycia: "+((Virus)popupSubject).getLives(), x+5, y+40);
				}
			}
		}
		else{
			popupSize=new Dimension(100, 35);
			x=popupSubject.getPosition().x+popupSubject.getSize().width+5;
			y=popupSubject.getMiddlePosition().y-popupSize.height;
			g2d.setColor(Color.WHITE);
			g2d.fillRect(x, y, popupSize.width, popupSize.height);
			g2d.setColor(Color.BLACK);
			g2d.drawRect(x, y, popupSize.width, popupSize.height);
			g2d.setFont(g2d.getFont().deriveFont((float) 9.0));
			g2d.drawString(popupSubject.toString(), x+5, y+10);
			g2d.drawString("Health: "+((Organ)popupSubject).getHealth()*100/255+"%", x+5, y+20);
			g2d.drawString("Capacity: "+((Organ)popupSubject).getCapacity()+"/"+((Organ)popupSubject).getMaxCapacity(), x+5, y+30);
		}
	}
	/**
	 * Zwraca organ o danym indeksie
	 * @param i indeks
	 * @return
	 */
	public Organ getOrgan(int i){
		return organs[i];
	}
	/**
	 * Dodaje do organizmu wirusa
	 */
	public synchronized void addVirus(){
		if(Calendar.getInstance().getTimeInMillis()-lastMobileTime.getTimeInMillis()>Constans.MOBILE_CREATE_COOLDOWN && viruses.size()<=Constans.MAX_MOBILE_THINGS){
			viruses.add(new Virus(this));
			lastMobileTime=Calendar.getInstance();
		}
	}
	/**
	 * Dodaje do organizmu Leukocyt
	 */
	public synchronized void addLeukocyte(){
		if(Calendar.getInstance().getTimeInMillis()-lastMobileTime.getTimeInMillis()>Constans.MOBILE_CREATE_COOLDOWN && leukocytes.size()<=Constans.MAX_MOBILE_THINGS && viruses.size()!=0){
			leukocytes.add(new Leukocyte(this, getNextTarget(),  (Organ)popupSubject));
			lastMobileTime=Calendar.getInstance();
		}
	}
	/**
	 * Dodaje do organizmu Erytrocyt
	 */
	public synchronized void addErythrocyte() {
		if(Calendar.getInstance().getTimeInMillis()-lastMobileTime.getTimeInMillis()>Constans.MOBILE_CREATE_COOLDOWN && erythrocytes.size()<=Constans.MAX_MOBILE_THINGS){
			erythrocytes.add(new Erythrocyte(this));
			lastMobileTime=Calendar.getInstance();
		}
	}
	/**
	 * Usuwa z organizmu dany Erytrocyt
	 * @param e
	 */
	public synchronized void removeErythrocyte(Erythrocyte e) {
		e.setEmbolised(false);
		if(e.getLastVisited().getErythrocyteOnBoard()==e)
			e.getLastVisited().setMobileOnBoard(null, true);
		erythrocytes.remove(e);
		e.setDead(true);
	}
	/**
	 * Zwraca referencj� do szpiku
	 * @return
	 */
	public Organ getMarrow(){
		if(marrowNr==-1){
			for (int i = 0; i < organs.length; i++) {
				if(organs[i].getName().equals("Szpik")){
					marrowNr=i;
					break;
				}
			}
		}
		return organs[marrowNr];
	}
	/**
	 * Sprawdza, czy w zasi�gu jest jaki� wirus
	 * @param position pozycja sprawdzanego obiektu
	 * @return
	 */
	public synchronized Virus getVirusInRange(Point position){
		for(Virus v: viruses)
			if(v.getMiddlePosition().distanceSq(position)<500)
				return v;
		return null;
	}
	/**
	 * Realizuje akcje zabicia wirusa przez leukocyt
	 * @param l
	 * @param v
	 */
	public synchronized void virusKilled(Leukocyte l, Virus v){
		if(v.damage()){
			viruses.remove(v);
			killedPatogens++;
		}
		l.setDead(true);
		l.getTarget().decChasingLeukocytes();
		leukocytes.remove(l);
		if(viruses.isEmpty()){
			for(Leukocyte leu: leukocytes){
				leu.setDead(true);
			}
			leukocytes.clear();
		}
		for(Leukocyte leu: leukocytes){
			if(leu.getTarget()==v)
				leu.setTarget(getNextTarget());
		}
	}
	/**
	 * Znajduje cel dla pozbawionego celu Leukocytu, preferowany jest cel, kt�y jest celem dla najmniejszej liczby leukocyt�w
	 * @return cel
	 */
	public synchronized Virus getNextTarget(){
		if(viruses.isEmpty())
			return null;
		else{
			Virus vir=viruses.getFirst();
			int i=vir.getChasingLeukocytes();
			for(Virus v: viruses)
				if(v.getChasingLeukocytes()==0)
					return v;
				else if(v.getChasingLeukocytes()<i){
					i=v.getChasingLeukocytes();
					vir=v;
				}
			return vir;
		}
	}
	/**
	 * Obs�uguje klikni�cie o danych parametrach
	 * @param x
	 * @param y
	 */
	public void onClick(int x, int y){
		popupSubject=getClosestMobile(new Point(x, y));
		if(popupSubject==null || popupSubject.getClass()==Erythrocyte.class)
		for (int i = 0; i < organs.length; i++) {
			if(organs[i].isClicked(x, y)){
				popupSubject=organs[i];
				return;
			}
		}

	}
	/**
	 * Zapisuje wynik gry
	 * @param name nazwa u�ytkownika
	 */
	public void saveResult(String name){
		Result.addResult(name, killedPatogens, (Calendar.getInstance().getTimeInMillis()-startTime.getTimeInMillis())/1000);
	}
	/**
	 * Zwraca obiekt, kt�rego skr�cone informacje s� obecnie wy�wietlane
	 * @return
	 */
	public Thing getPopupSubject() {
		return popupSubject;
	}
	/**
	 * ustawia obiekt, kt�rego skr�cone dane maj� by� wy�wietlane
	 * @param popupSubject
	 */
	public void setPopupSubject(Thing popupSubject) {
		this.popupSubject = popupSubject;
	}
	private synchronized MobileThing getClosestMobile(Point p){
		double dist=Double.MAX_VALUE;
		MobileThing closest=null;
		for (MobileThing mb: erythrocytes) {
			if(mb.getMiddlePosition().distanceSq(p)<dist){
				dist=mb.getMiddlePosition().distanceSq(p);
				closest=mb;
			}
		}
		for (MobileThing mb: leukocytes) {
			if(mb.getMiddlePosition().distanceSq(p)<dist){
				dist=mb.getMiddlePosition().distanceSq(p);
				closest=mb;
			}
		}
		for (MobileThing mb: viruses) {
			if(mb.getMiddlePosition().distanceSq(p)<dist){
				dist=mb.getMiddlePosition().distanceSq(p);
				closest=mb;
			}
		}
		if(dist>900)
			return null;
		else
			return closest;
	}
	public int getKilledPatogens() {
		return killedPatogens;
	}
	/**
	 * zmniejsza ilo�� �ywych organ�w
	 */
	public synchronized void decOrgansAlive(){
		organsAlive--;
		if(organsAlive==0)
			gui.respondToCloseOperation();
	}
	/**
	 * 
	 * @return wielko�� boku w�z�a (nie organu)
	 */
	public int getNodeSize(){
		return nodeSize;
	}
	/**
	 * 
	 * @return liczb� �ywych organ�w
	 */
	public int getOrgansAlive() {
		return organsAlive;
	}
	/**
	 * Restartuje w�tki po wczytaniu stanu gry
	 */
	public synchronized void restart(){
		for(Erythrocyte e: erythrocytes)
			new Thread(e).start();
		for(Leukocyte l: leukocytes)
			new Thread(l).start();
		for(Virus v: viruses)
			new Thread(v).start();
		new Thread(rm).start();
	}
	/**
	 * startuje organizm na okre�lonym levelu
	 * @param level
	 */
	public void start(int level){
		started=true;
		this.level=level;
		startTime=Calendar.getInstance();
	}
	/**
	 * zwraca poziom trudno�ci
	 * @return
	 */
	public int getLevel(){
		return level;
	}
	/**
	 * Nie korzysta�, wy�acznie do zapisu/odczytu
	 * @param downTime
	 */
	public void adjustTime(Calendar downTime){
		long time=Calendar.getInstance().getTimeInMillis()-downTime.getTimeInMillis();
		startTime.setTimeInMillis(startTime.getTimeInMillis()+time);
		lastMobileTime.setTimeInMillis(lastMobileTime.getTimeInMillis()+time);
	}
	private class RefreshManager implements Runnable, Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = Constans.serialVersionUID;

		@Override
		public void run(){
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				if(!started)
					continue;
				for (int i = 0; i < organs.length; i++) {
					organs[i].updateNutritionsDurabilityState();
				}
				if( Calendar.getInstance().getTimeInMillis()-startTime.getTimeInMillis()>Constans.START_COOLDOWN && random.nextDouble()<Constans.NEW_VIRUS_CHANCE_RATE[level])
					addVirus();
		}
		}
	}

}


