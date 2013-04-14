package pl.kopciu.organism;

import java.io.Serializable;
import java.util.Comparator;
/**
 * 
 * @author Kopciu
 *Klasa-Comparator, por�wnuje czas wa�no�ci 2 warto�ci od�ywczych.
 */
public class DurabilityComparator implements Comparator<Nutrition>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;

	@Override
	public int compare(Nutrition o1, Nutrition o2) {
		return Long.compare(o1.getNutritionDurability(), o2.getNutritionDurability());
	}
	
}
