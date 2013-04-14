package pl.kopciu.organism;

/**
 * 
 * @author Kopciu
 * Klasa zawieraj�ca prawie wszystkie sta�e u�ywane w programie, zebrane w jednym miejscu dla wygody
 * Celem by�a moja wygoda przy tworzeniu/debugowaniu/dostrajaniu, nie wygoda kombinowania przy sprawdzaniu-zmienianie warto�ci sta�ych mo�e spowodowa� b��dy
 */
public class Constans {
	/**
	 * Nazwy organ�w
	 */
	public static final String[] ORGAN_NAMES={"Serce", "P�uca", "M�zg", "Nerki", "W�troba", "�o��dek", "Szpik", "Jelita", "Krzy�yk", "Tarczyca"};
	/**
	 * nazwy warto�ci od�ywczych
	 */
	public static final String[] NUTRITION_NAMES={"Tlen", "Dwutlenek w�gla", "Cukier", "Bia�ko", "T�uszcz", "W�glowodany", "Odpady", "Woda", "Hormony"};
	/**
	 * indeksy organ�w �r�d�owych i docelowych dla ka�dej z warto�ci
	 */
	public static final int[][] fromToNutritionTab={{1, 7, 4, 4, 5, 5, 4, 3, 9},
													{2, 1, 0, 6, 4, 4, 3, 8, 2}};
	/**
	 * czas trwa�o�ci warto�ci od�ywczej w sekundach
	 */
	public static final int NUTRITION_DURABILITY_IN_SEC=120;
	/**
	 * maksymalna pojemno�� organu
	 */
	public static final int MAX_ORGAN_CAPACITY=50;
	/**
	 * maksymalna pojemno�� krwinki
	 */
	public static final int MAX_ERYTHROCYTE_CAPACITY=5;
	/**
	 * maksymalna ilo�� �ycia organu
	 */
	public static final int MAX_ORGAN_HP=255;
	/**
	 * ilos� warto�ci od�ywczych ka�dego typu, kt�re produkuje na pocz�tku organ
	 */
	public static final int START_AMOUNT_OF_NUTRITIONS=20;
	/**
	 * ogranicznik pr�dko�ci, pr�dko�ci obiekt�w mobilnych b�dzie tyle razy mniejsza
	 */
	public static final int SPEED_DIV_FACTOR=4;
	/**
	 * Szansa, �e w tej sekundzie pojawi si� wirus
	 */
	public static final double[] NEW_VIRUS_CHANCE_RATE={0, 0.1, 0.2};
	/**
	 * Szansa, �e stworzony wirus b�dzie "mocnym" wirusem
	 */
	public static final double[] STRONG_VIRUS_CHANCE={0, 0, 0.3};
	/**
	 * liczba �y� "mocnego" wirusa
	 */
	public static final int STRONG_VIRUS_LIVES=3;
	/**
	 * Szansa �e krwinka w danym ruchu stworzy zator
	 */
	public static final double EMBOLISTE_CHANCE_RATE=0.0001;
	/**
	 * Maksymalna ilo�� obiekt� mobilnych na planszy, dla ka�dego rodzaju oddzielna
	 */
	public static final int MAX_MOBILE_THINGS=20;
	/**
	 * nazwa pliku z wynikami
	 */
	public static final String FILE_NAME="wyniki.xml";
	/**
	 * czas, po kt�rym zaczynaj� pojawia� si� wirusy w ms
	 */
	public static final int START_COOLDOWN=3000;
	/**
	 * cooldown w tworzeniu nowych obiekt�w mobilnych w ms
	 */
	public static final int MOBILE_CREATE_COOLDOWN=500;
	
	/**
	 * zunifikowana dla ca�ego dokumentu wersja serialaWersji
	 */
	public static final long serialVersionUID =3L;
	
}