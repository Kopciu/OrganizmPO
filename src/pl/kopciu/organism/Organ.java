package pl.kopciu.organism;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Klasa organu
 * @author Kopciu
 *
 */

public class Organ extends Node{

	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	private String name;
	private volatile int health, capacity, maxCapacity, number;

	private PriorityQueue<Nutrition> nutritionLists[];
	private LinkedList<Integer> producedNutritions, receivedNutritions;
	private volatile Font font= new Font("Arial", Font.PLAIN, 7);
	private Organism organism;
	
	/**
	 * Konstruktor
	 * @param o indeks organu, 0-9
	 * @param organism referencja na organizm
	 */
	@SuppressWarnings("unchecked")
	public Organ(int o, Organism organism) {
		super();
		this.organism=organism;
		this.name=Constans.ORGAN_NAMES[o];
		this.number=o;
		health=Constans.MAX_ORGAN_HP;
		capacity=0;
		maxCapacity=Constans.MAX_ORGAN_CAPACITY;
		nutritionLists=new PriorityQueue[Constans.NUTRITION_NAMES.length];
		for (int i = 0; i < nutritionLists.length; i++) {
			nutritionLists[i]=new PriorityQueue<Nutrition>(maxCapacity, new DurabilityComparator());
		}
		producedNutritions= new LinkedList<Integer>();
		receivedNutritions= new LinkedList<Integer>();
		for (int i = 0; i < Constans.fromToNutritionTab[0].length; i++) {
			if(Constans.fromToNutritionTab[0][i]==o){
				producedNutritions.add(i);
				for(int j=0; j<Constans.START_AMOUNT_OF_NUTRITIONS; j++){
					if(!addNutrition(i))
						break;
				}
			}
			if(Constans.fromToNutritionTab[1][i]==o){
				receivedNutritions.add(i);
			}			
		}
	}
	@Override
	public synchronized boolean setMobileOnBoard(MobileThing mobileThing, boolean b){
		if(b && !canNutritionsBeExchanged((Erythrocyte)mobileThing))
			return false;
		if(!super.setMobileOnBoard(mobileThing, b))
			return false;
		if(mobileThing!=null && b){
			exchangeNutritionsWithErythrocyte((Erythrocyte)mobileThing);
		}
			return true;
	}
	@Override
	public void draw(Graphics2D g2d){
		super.draw(g2d);
		g2d.setFont(font);
		g2d.drawString("health: "+health*100/Constans.MAX_ORGAN_HP+"%", this.getPosition().x+10, this.getPosition().y-3);
		g2d.setColor(new Color(0x00FF0000+((Constans.MAX_ORGAN_HP-health)<<24), true));
		g2d.fillRect(this.getPosition().x, this.getPosition().y,this.getSize().width, this.getSize().height);
		g2d.setColor(new Color(0x000000));
		
		
	}

	/**
	 * Dodaj Wartoœæ od¿ywcz¹
	 * @param type typ wartoœci
	 * @return czy operacja siê uda³¹
	 */
	public synchronized boolean addNutrition(int type){
		if(capacity<maxCapacity && producedNutritions.contains(type)){
			Nutrition n=new Nutrition(type);
			nutritionLists[n.getType()].add(n);
			capacity++;
			return true;
		}
		return false;
	}
	private synchronized boolean receiveNutrition(Nutrition n){
		if(n==null || !receivedNutritions.contains(n.getType()) || isFull())
			return false;
		nutritionLists[n.getType()].add(n);
		capacity++;
		if(health<Constans.MAX_ORGAN_HP)
			health++;
		return true;
	}
	private synchronized Nutrition getNutrition(int i){
		if(nutritionLists[i].isEmpty())
			return null;
		capacity--;
		return nutritionLists[i].poll();
	}
	private synchronized boolean canNutritionsBeExchanged(Erythrocyte e){
		if(e==null)
			return true;
		int sum=0;
		for(int i=0; i<Constans.NUTRITION_NAMES.length; i++)
			if(receivedNutritions.contains(i))
				sum+=e.getNumberOfNutritions(i);
		if(capacity+sum>maxCapacity)
			return false;
		return true;
	}
	/**
	 * Wymienia WO z Erytrocytem
	 * @param e Erytrocyt, z którym wymienia siê WO
	 */
	public synchronized void exchangeNutritionsWithErythrocyte(Erythrocyte e){
		for (int i = 0; i < nutritionLists.length; i++) {
			if(receivedNutritions.contains(i))
				while(receiveNutrition(e.getNutrition(i)));
			else{
				while(!e.isFull()){
					if(!e.setNutrition(getNutrition(i)))
						break;
				}
			}
		}
		
	}
	/**
	 * przyjmuje obra¿enia zadane przez wirusa
	 * @param damage iloœæ obra¿eñ
	 */
	public synchronized void takeDamageFromVirus(int damage){
		for (int i = 0; i < nutritionLists.length; i++) {
			nutritionLists[i].clear();
		}
		capacity=0;
		health-=((int)damage*3.1);
		if(health<=0){
			health=0;
			die();
		}
	}
	private void die(){
		this.setDead(true);
		organism.decOrgansAlive();
	
	}
	/**
	 * Sprawdza i usuwa przestarza³e WO
	 */
	public synchronized void updateNutritionsDurabilityState(){
		for (int i = 0; i < nutritionLists.length; i++) {
			while(!nutritionLists[i].isEmpty() && nutritionLists[i].peek().getNutritionDurability()>0){
				nutritionLists[i].remove();
				capacity--;
			}
		}
	}
	/**
	 * 
	 * @return czy w organie jest jeszcze miejsce?
	 */
	public boolean isFull(){
		return capacity==maxCapacity;
	}
	/**
	 * 
	 * @return iloœæ zajêtego miejsca w organie
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * 
	 * @return maksymalna pojemnoœæ organu
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}
	/**
	 * 
	 * @return obecn¹ iloœæ ¿ycia organu
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * 
	 * @return nazwa organu
	 */
	public String getName() {
		return name;
	}
	@Override
	public String toString(){
		return name+" - nr "+number;
	}
	/**
	 * Zwraca iloœæ WO danego typu przechowywanych w organie
	 * @param i typ WO
	 * @return
	 */
	public int getNumberOfNutritions(int i) {
		return nutritionLists[i].size();
	}
	/**
	 * 
	 * @return indeks organu
	 */
	public int getNumber() {
		return number;
	}
}
