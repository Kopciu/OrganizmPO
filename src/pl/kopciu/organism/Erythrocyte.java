package pl.kopciu.organism;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Random;
/**
 * 
 * @author Kopciu
 * Klasa Krwinki s³u¿acej do transportu wartoœci od¿ywczych w organiŸmie
 */
public class Erythrocyte extends MobileThing{
	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	private int capacity, maxCapacity; 
	private LinkedList<Nutrition> nutritionLists[];
	private LinkedList<Organ> destinationOrgans;
	private Organism organism;
	private boolean isEmbolised;
	private Random random=new Random();
	/**
	 * Konstruktor
	 * @param organism referencja do organizmu, w którym krwinka zostaje utworzona
	 */
	@SuppressWarnings("unchecked")
	public Erythrocyte(Organism organism) {
		super(organism.getMarrow(), organism);
		this.setGUICoordinates(this.getLastVisited().getMiddlePosition(), 206, 548, 236, 617, new Dimension(10, 23));
		this.organism=organism;
		capacity=0;
		maxCapacity=Constans.MAX_ERYTHROCYTE_CAPACITY;
		nutritionLists=new LinkedList[Constans.NUTRITION_NAMES.length];
		for (int i = 0; i < nutritionLists.length; i++) {
			nutritionLists[i]=new LinkedList<Nutrition>();
		}
		Random rand= new Random();
		for(int i=rand.nextInt(Constans.MAX_ERYTHROCYTE_CAPACITY); i>=0; i--)
			setNutrition(new Nutrition(rand.nextInt(Constans.NUTRITION_NAMES.length)));
		destinationOrgans=new LinkedList<Organ>();
		generatePath();
		this.changePath(organism.getCirculatorySystem().getShortesPathToOrgan(organism.getMarrow(), destinationOrgans.get(0)));
	}
	/**
	 * zwraca aktualna pojemnosc krwinki
	 * @return
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * 
	 * @return zwraca wêze³ do którego kierujê siê krwinka lub null, je¿eli nie ma takiego
	 */
	public Organ getNextTarget(){
		return destinationOrgans.peekFirst();
	}
	/**
	 * 
	 * @param capacity ustawia aktualny poziom zape³nienia krwinki
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	/**
	 * 
	 * @param capacity iloœæ, o któr¹ ma byæ zmieniona pojemnoœæ krwinki
	 */
	public synchronized void changeCapacity(int capacity){
		this.capacity+=capacity;
	}
	/**
	 * 
	 * @return maksymalna pojemnoœæ krwinki
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}
	/**
	 * ustawia maksymaln¹ pojemnoœæ krwinki
	 * @param maxCapacity maksymalna pojemnoœæ
	 */
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	/**
	 * Dodaje na koñcu listy odwiedzanych organów organ podany w parametrze
	 * @param o organ dok³adany na koñcu listy
	 */
	public void addToRoad(Organ o){
		destinationOrgans.add(o);
	}
	/**
	 * Usuwa organ z listy odwiedzanych organów
	 * @param o organ do usuniêcia 
	 */
	public void removeFromRoad(Organ o){
		destinationOrgans.remove(o);
	}
	/**
	 * Zwraca liczbê wartoœci od¿ywczych danego typu przechowywanych aktualnie w krwince
	 * @param type typ wartoœci od¿ywczej
	 * @return iloœæ w krwince
	 */
	public int getNumberOfNutritions(int type){
		return nutritionLists[type].size();
	}
	/**
	 * losuje listê organów odwiedzanych przez krwinkê
	 */
	public void generatePath(){
		Random rand= new Random();
		int n=rand.nextInt(5)+3;
		for(int i=0; i<n; i++){
			int a=rand.nextInt(Constans.ORGAN_NAMES.length);
			while(!destinationOrgans.isEmpty() && organism.getOrgan(a)==destinationOrgans.getLast())
				a=rand.nextInt(Constans.ORGAN_NAMES.length);
			destinationOrgans.add(organism.getOrgan(a));
		}
	}
	/**
	 * odpowiada za realizacjê ruchu krwinki
	 */
	@Override
	public void run(){
		while(!MobileThing.isFinished() && !isDead()){
			if(!this.isPathValid() && destinationOrgans.getFirst()!=this.getLastVisited()){
				this.changePath(organism.getCirculatorySystem().getShortesPathToOrgan(this.getLastVisited(), destinationOrgans.getFirst()));
				continue;
			}
			super.run();
			if(destinationOrgans!=null && destinationOrgans.size()>1){
				while(destinationOrgans.size()>1 && destinationOrgans.getFirst().isDead())
						destinationOrgans.pollFirst();
				if(this.getLastVisited()==destinationOrgans.getFirst() && destinationOrgans.size()>1){
					this.changePath(organism.getCirculatorySystem().getShortesPathToOrgan(destinationOrgans.getFirst(), destinationOrgans.get(1)));
					destinationOrgans.addLast(destinationOrgans.pollFirst());
				}
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {return;}
			if(this.getLastVisited().getClass()!=Organ.class && this.getNextNode().getClass()!=Organ.class && random.nextDouble()<Constans.EMBOLISTE_CHANCE_RATE)
				setEmbolised(true);
		}
	
	}
	/**
	 * "wyjmuje" z erytrocytu jedn¹ wartoœæ od¿ywcz¹ danego typu
	 * @param i typ wartoœci pobieranej
	 * @return pobrana wartoœæ od¿ywcza
	 */
	public synchronized Nutrition getNutrition(int i) {
		if(nutritionLists[i].isEmpty())
			return null;
		else{
			capacity--;
			return nutritionLists[i].poll();
		}
	}
	/**
	 * "Wk³ada" do krwinki wartoœæ od¿ywcz¹
	 * @param n wartoœæ od¿ywcza do w³o¿enia
	 * @return true, je¿eli uda³o siê dodaæ krwinkê
	 */
	public synchronized boolean setNutrition(Nutrition n) {
		if(n==null || capacity>=maxCapacity)
			return false;
		nutritionLists[n.getType()].add(n);
		capacity++;
		return true;
	}
	/**
	 * 
	 * @return czy krwinka jest przepe³niona?
	 */
	public boolean isFull(){
		return capacity==maxCapacity;
	}
	/**
	 * 
	 * @return czy erytrocyt utwo¿y³ zator?
	 */
	public boolean isEmbolised() {
		return isEmbolised;
	}
	/**
	 * czy erytrocyt mo¿e siê poruszaæ?
	 */
	@Override
	public boolean canMove() {
		return !isDead() && !isEmbolised;
	}
	/**
	 * Zwraca listê organów na drodze krwinki w formie stringa
	 * @return droga w formie stringa
	 */
	public String getRoad(){
		String s="";
		for(Organ o: destinationOrgans)
			s+=o.getNumber();
		return s;
	}
	/**
	 * Ustawia listê organów do odwiedzenia przez krwinke
	 * @param s Lista organów, zakodowana do stringa, która bêdzie odwiedzana przez krwinkê
	 */
	public void setRoad(String s){
		LinkedList<Organ> l= new LinkedList<Organ>();
		for(int i=0; i<s.length(); i++){
			if(Character.isDigit(s.charAt(i)))
				l.add(organism.getOrgan(Character.getNumericValue(s.charAt(i))));
		}
		if(!l.isEmpty())
			setRoad(l);
	}
	private void setRoad(LinkedList<Organ> l) {
		destinationOrgans=l;
		if(this.getNextNode()!=null)
			this.changePath(organism.getCirculatorySystem().getShortesPathToOrgan(this.getNextNode(), l.getFirst()));
		else
			this.changePath(organism.getCirculatorySystem().getShortesPathToOrgan(this.getLastVisited(), l.getFirst()));
		
	}
	/**
	 * Sprawia, ¿e krwinka tworzy, lub nie zator
	 * @param isEmbolised
	 */
	public synchronized void setEmbolised(boolean isEmbolised) {
		this.isEmbolised = isEmbolised;
		if(this.getLastVisited().getErythrocyteOnBoard()!=this && !this.isNearNode()){
			for(int i=0; i<5; i++){
				if(this.getLastVisited().getRoads(i, 0)!=null && this.getLastVisited().getRoads(i, 0).getEndOrgan()==this.getNextNode()){
					synchronized (this.getLastVisited().getRoads(i, 0)) {
					if(isEmbolised)
						this.getLastVisited().getRoads(i, 0).incEmbolised();
					else
						this.getLastVisited().getRoads(i, 0).decEmbolised();
					break;
					}
				}
			}
		}
	}
	@Override
	public String toString(){
		return ("Erytrocyt id: "+this.getId());
	}
}

