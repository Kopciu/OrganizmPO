package pl.kopciu.organism;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Klasa warto�ci od�ywczej (WO)
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
	 * Nie korzysta�, wy�acznie do zapisu/odczytu
	 * @return
	 */
	public static int getCurrentId() {
		return currentId;
	}
	/**
	 * Nie korzysta�, wy�acznie do zapisu/odczytu
	 * @return
	 */
	public static void setCurrentId(int currentId) {
		Nutrition.currentId = currentId;
	}
	
	/**
	 * Konstruktor
	 * @param type typ tworzonej warto�ci od�ywczej 0-ilo�� r�nych warto�ci od�ywcych-1
	 */
	public Nutrition(int type){
		id=currentId++;
		this.type=type;
		this.creationTime=Calendar.getInstance();
		this.nutritionDurability=Constans.NUTRITION_DURABILITY_IN_SEC*1000;
	}
	/**
	 * Zwraca Id Warto�ci od�ywczej (WO i byty poruszaj�ce si� maj� oddzielne indeksowanie, gdzie Id si� powtarzaj�
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
	 * Zwraca czas, jaki pozosta� WO do ko�ca terminu wa�no�ci
	 * @return
	 */
	public long getNutritionDurability() {
		return Calendar.getInstance().getTimeInMillis()-creationTime.getTimeInMillis()-nutritionDurability;
	}
	/**
	 * Sprawdza, czy WO nie przekroczy�� terminu wa�no�ci
	 * @return
	 */
	public boolean checkDurability(){
		if((Calendar.getInstance().getTimeInMillis()-creationTime.getTimeInMillis())>nutritionDurability)
			return true;
		return false;
	}
}
