package pl.kopciu.organism;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Klasa wartoœci od¿ywczej (WO)
 * @author Kopciu
 *
 */
public class Nutrition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	private transient static int currentId=0;
	private int id;
	private int type;
	private long nutritionDurability;
	private Calendar creationTime;
	
	/**
	 * Nie korzystaæ, wy³acznie do zapisu/odczytu
	 * @return
	 */
	public static int getCurrentId() {
		return currentId;
	}
	/**
	 * Nie korzystaæ, wy³acznie do zapisu/odczytu
	 * @return
	 */
	public static void setCurrentId(int currentId) {
		Nutrition.currentId = currentId;
	}
	
	/**
	 * Konstruktor
	 * @param type typ tworzonej wartoœci od¿ywczej 0-iloœæ ró¿nych wartoœci od¿ywcych-1
	 */
	public Nutrition(int type){
		id=currentId++;
		this.type=type;
		this.creationTime=Calendar.getInstance();
		this.nutritionDurability=Constans.NUTRITION_DURABILITY_IN_SEC*1000;
	}
	/**
	 * Zwraca Id Wartoœci od¿ywczej (WO i byty poruszaj¹ce siê maj¹ oddzielne indeksowanie, gdzie Id siê powtarzaj¹
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * Zwraca typ WO
	 * @return
	 */
	public int getType() {
		return type;
	}
	/**
	 * Zwraca czas, jaki pozosta³ WO do koñca terminu wa¿noœci
	 * @return
	 */
	public long getNutritionDurability() {
		return Calendar.getInstance().getTimeInMillis()-creationTime.getTimeInMillis()-nutritionDurability;
	}
	/**
	 * Sprawdza, czy WO nie przekroczy¹³ terminu wa¿noœci
	 * @return
	 */
	public boolean checkDurability(){
		if((Calendar.getInstance().getTimeInMillis()-creationTime.getTimeInMillis())>nutritionDurability)
			return true;
		return false;
	}
}
