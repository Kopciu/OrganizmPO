package pl.kopciu.organism;

import java.awt.Dimension;
import java.util.Random;

/**
 * Klasa patogenu
 * @author Kopciu
 *
 */
public class Virus extends MobileThing{
	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	private static final int TYPE_NORMAL=0, TYPE_STRONG=1;
	private Organ target;
	int totalTimeOfAttack;
	private Organism organism;
	private Random random=new Random();
	private int chasingLeukocytes=0;
	private transient volatile static int organsAlive[]={0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, killedOrgans=0;
	private int type =0, lives;
	
	/**
	 * Nie korzystaæ, wy³acznie do zapisu/odczytu
	 * @return
	 */
	public static int[] getOrgansAlive() {
		return organsAlive;
	}
	/**
	 * Nie korzystaæ, wy³acznie do zapisu/odczytu
	 * @return
	 */
	public static void setOrgansAlive(int[] organsAlive) {
		Virus.organsAlive = organsAlive;
	}
	/**
	 * Nie korzystaæ, wy³acznie do zapisu/odczytu
	 * @return
	 */
	public static int getKilledOrgans() {
		return killedOrgans;
	}
	/**
	 * Nie korzystaæ, wy³acznie do zapisu/odczytu
	 * @return
	 */
	public static void setKilledOrgans(int killedOrgans) {
		Virus.killedOrgans = killedOrgans;
	}
	/**
	 * Konstruktor
	 * @param organism referencja na organizm
	 */
	public Virus(Organism organism) {
		super(organism.getOrgan((new Random()).nextInt(Constans.ORGAN_NAMES.length)), organism);
		random=new Random();
		if(random.nextDouble()<Constans.STRONG_VIRUS_CHANCE[organism.getLevel()]){
			type=TYPE_STRONG;
			lives=Constans.STRONG_VIRUS_LIVES;
			this.setGUICoordinates(this.getLastVisited().getMiddlePosition(),40, 590, 95, 718, new Dimension(14, 32));
		}
		else{
			type=TYPE_NORMAL;
			lives=1;
			this.setGUICoordinates(this.getLastVisited().getMiddlePosition(),106, 542, 146, 607, new Dimension(13, 22));
		}
		this.organism=organism;
		setDead(false);
		chooseNextTarget();
		totalTimeOfAttack=random.nextInt(10);	
	}
	/**
	 * 
	 * @return cel, który ma zaatakowaæ patogen
	 */
	public Organ getTarget() {
		return target;
	}
	/**
	 * ustawiam cel, który ma zaatakowaæ patogen
	 * @param target
	 */
	public void setTarget(Organ target) {
		this.target = target;
		
	}
	private void attackOrgan(Organ o){
		totalTimeOfAttack=random.nextInt(10);
		for(int i=0; i<totalTimeOfAttack && !isDead() && !o.isDead(); i++){
			if(type==TYPE_STRONG)
				o.takeDamageFromVirus(3);
			else
				o.takeDamageFromVirus(1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {return;}
		}
		chooseNextTarget();
	}
	
	private void chooseNextTarget(){
		synchronized (this){
		int t, tmp;
		do{
			if(killedOrgans>=Constans.ORGAN_NAMES.length) return;
			t=killedOrgans+random.nextInt(Constans.ORGAN_NAMES.length-killedOrgans);
			target=organism.getOrgan(organsAlive[t]);
			if(target.isDead()){
				tmp=organsAlive[t];
				organsAlive[t]=organsAlive[killedOrgans];
				organsAlive[killedOrgans]=tmp;
				killedOrgans++;
				target=organism.getOrgan(organsAlive[t]);
			}
		}
		while(target.isDead());
		}
		this.changePath(organism.getImmunologicalSystem().getShortesPathToOrgan((Organ)this.getLastVisited(), target));
	}
	
	@Override
	public void run(){
		
		while(!MobileThing.isFinished() && !isDead()){
			super.run();
			if(this.getLastVisited()==target){
				attackOrgan(target);
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {}
		}
		if(isDead() && this.getLastVisited().getImmuOnBoard()==this)
			this.getLastVisited().setMobileOnBoard(null, false);
		
	}
	@Override
	public String toString(){
		return ("Virus id: "+this.getId());
	}
	/**
	 * 
	 * @return liczba leukocytów, które próbuj¹ przechwyciæ dany patogen
	 */
	public int getChasingLeukocytes() {
		return chasingLeukocytes;
	}
	/**
	 * zwiêksza o 1 liczbê leukocytów, które próbuj¹ przechwyciæ dany patogen
	 */
	public void incChasingLeukocytes(){
		chasingLeukocytes++;
	}
	/**
	 * zmniejsza o 1 liczbê leukocytów, które próbuj¹ przechwyciæ dany patogen
	 */
	public void decChasingLeukocytes(){
		chasingLeukocytes--;
	}
	public synchronized boolean damage(){
		lives--;
		if(lives<=0){
			this.setDead(true);
			return true;
		}
		return false;
	}
	@Override
	protected double getSelfSpeed(){
		if(type==TYPE_NORMAL)
			return 1.5;
		return 0.6;
		
	}
	/**
	 * 
	 * @return pozosta³¹ iloœæ ¿yæ wirusa
	 */
	public int getLives(){
		return lives;
	}
}