package pl.kopciu.organism;
//Wojciech Kopeæ, I4, 101675
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
/** Klasa bazowa dla wszystkich obiektów organizmu
 */
public class Thing implements Serializable{
	private static final long serialVersionUID = Constans.serialVersionUID;
	private transient static Image gridImage;
	private Point position;
	private int sx1, sy1, sx2, sy2;
	private Dimension size;
	private volatile boolean isDead;
	
	/**
	 * ustawia image jako sprite sheet
	 * @param image
	 */
	public static void setGridImage(Image image){
		gridImage=image;
	}
	/**
	 *  Ustawia pozycjê, wielkoœæ i sprite obiektu
	 * @param position Pozycja
	 * @param sx1 pozycja x1 sprite'u w sprite sheecie
	 * @param sy1 pozycja y1 sprite'u w sprite sheecie
	 * @param sx2 pozycja x2 sprite'u w sprite sheecie
	 * @param sy2 pozycja y2 sprite'u w sprite sheecie
	 * @param size wielkoœæ
	 */
	public void setGUICoordinates(Point position, int sx1, int sy1, int sx2, int sy2, Dimension size){
		this.position=position;
		this.sx1=sx1;
		this.sy1=sy1;
		this.sx2=sx2;
		this.sy2=sy2;	
		this.size=size;
	}
	public void draw(Graphics2D g2d){
		g2d.drawImage(gridImage, position.x, position.y, position.x+size.width, position.y+size.height, sx1, sy1, sx2, sy2, null);
	}
	/**
	 * Sprawdza, czy nast¹pi³a kolizja klikniêcia z obiektem
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isClicked(int x, int y){
		return (new Rectangle(position, size)).contains(x, y);
	}
	/**
	 * 
	 * @return pozycja w organiŸmie
	 */
	public Point getPosition() {
		return position;
	}
	/**
	 * Ustawia pozycjê w organiŸmie
	 * @param position
	 */
	public void setPosition(Point position) {
		this.position = position;
	}
	/**
	 * 
	 * @return pozycja œrodka obiektu w organiŸmie
	 */
	public Point getMiddlePosition(){
		Point p =new Point();
		p.x=position.x+size.width/2;
		p.y=position.y+size.height/2;
		return p;
	}
	/**
	 * Ustawia pozycjê obiektu
	 * @param p pozycja, w której ma byæ srodek obiektu
	 */
	public void setMiddlePosition(Point p){
		position.x=p.x-size.width/2;
		position.y=p.y-size.height/2;
	}
	/**
	 * 
	 * @return rozmiar obiektu
	 */
	public Dimension getSize() {
		return size;
	}
	/**
	 * 
	 * @return czy obiekt jest nie¿ywy?
	 */
	public boolean isDead() {
		return isDead;
	}
	/**
	 * Ustawia, czy obiekt jest nie¿ywy
	 * @param isDead
	 */
	public synchronized void setDead(boolean isDead) {
		this.isDead = isDead;
	}

}