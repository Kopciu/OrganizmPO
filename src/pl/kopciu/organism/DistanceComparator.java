package pl.kopciu.organism;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 
 * @author Kopciu
 *Klasa-Comparator, u¿ywana w Algorytmie Dijkstry do wyszukiwania œcie¿ek
 */
public class DistanceComparator implements Comparator<Node>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;

	/**
	 * Porównuje odleg³oœæ od Ÿród³a 2 obiektów klasy Node
	 */
	@Override
	public int compare(Node arg0, Node arg1) {
		return Double.compare(arg0.getDistance(), arg1.getDistance());
	}
}