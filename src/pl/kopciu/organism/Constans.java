package pl.kopciu.organism;

/**
 * 
 * @author Kopciu
 * Klasa zawieraj¹ca prawie wszystkie sta³e u¿ywane w programie, zebrane w jednym miejscu dla wygody
 * Celem by³a moja wygoda przy tworzeniu/debugowaniu/dostrajaniu, nie wygoda kombinowania przy sprawdzaniu-zmienianie wartoœci sta³ych mo¿e spowodowaæ b³êdy
 */
public class Constans {
	/**
	 * Nazwy organów
	 */
	public static final String[] ORGAN_NAMES={"Serce", "P³uca", "Mózg", "Nerki", "W¹troba", "¯o³¹dek", "Szpik", "Jelita", "Krzy¿yk", "Tarczyca"};
	/**
	 * nazwy wartoœci od¿ywczych
	 */
	public static final String[] NUTRITION_NAMES={"Tlen", "Dwutlenek wêgla", "Cukier", "Bia³ko", "T³uszcz", "Wêglowodany", "Odpady", "Woda", "Hormony"};
	/**
	 * indeksy organów Ÿród³owych i docelowych dla ka¿dej z wartoœci
	 */
	public static final int[][] fromToNutritionTab={{1, 7, 4, 4, 5, 5, 4, 3, 9},
													{2, 1, 0, 6, 4, 4, 3, 8, 2}};
	/**
	 * czas trwa³oœci wartoœci od¿ywczej w sekundach
	 */
	public static final int NUTRITION_DURABILITY_IN_SEC=120;
	/**
	 * maksymalna pojemnoœæ organu
	 */
	public static final int MAX_ORGAN_CAPACITY=50;
	/**
	 * maksymalna pojemnoœæ krwinki
	 */
	public static final int MAX_ERYTHROCYTE_CAPACITY=5;
	/**
	 * maksymalna iloœæ ¿ycia organu
	 */
	public static final int MAX_ORGAN_HP=255;
	/**
	 * ilosæ wartoœci od¿ywczych ka¿dego typu, które produkuje na pocz¹tku organ
	 */
	public static final int START_AMOUNT_OF_NUTRITIONS=20;
	/**
	 * ogranicznik prêdkoœci, prêdkoœci obiektów mobilnych bêdzie tyle razy mniejsza
	 */
	public static final int SPEED_DIV_FACTOR=4;
	/**
	 * Szansa, ¿e w tej sekundzie pojawi siê wirus
	 */
	public static final double[] NEW_VIRUS_CHANCE_RATE={0, 0.1, 0.2};
	/**
	 * Szansa, ¿e stworzony wirus bêdzie "mocnym" wirusem
	 */
	public static final double[] STRONG_VIRUS_CHANCE={0, 0, 0.3};
	/**
	 * liczba ¿yæ "mocnego" wirusa
	 */
	public static final int STRONG_VIRUS_LIVES=3;
	/**
	 * Szansa ¿e krwinka w danym ruchu stworzy zator
	 */
	public static final double EMBOLISTE_CHANCE_RATE=0.0001;
	/**
	 * Maksymalna iloœæ obiektó mobilnych na planszy, dla ka¿dego rodzaju oddzielna
	 */
	public static final int MAX_MOBILE_THINGS=20;
	/**
	 * nazwa pliku z wynikami
	 */
	public static final String FILE_NAME="wyniki.xml";
	/**
	 * czas, po którym zaczynaj¹ pojawiaæ siê wirusy w ms
	 */
	public static final int START_COOLDOWN=3000;
	/**
	 * cooldown w tworzeniu nowych obiektów mobilnych w ms
	 */
	public static final int MOBILE_CREATE_COOLDOWN=500;
	
	/**
	 * zunifikowana dla ca³ego dokumentu wersja serialaWersji
	 */
	public static final long serialVersionUID =3L;
	
}