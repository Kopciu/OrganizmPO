package pl.kopciu.organism;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

 /**Scie�ka pomi�dzy w�z�ami grafu reprezentuj�cego uk�ad krwiono�ny i odporno�ciowy */
public class SystemRoad implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	private static transient Image gridImage;
	private Node startOrgan, endOrgan;
	private double length, transportSpeed;
	private int embolisedCount=0; //zator
	private AffineTransform transform;
	private int type;
	
	/**
	 * ustawia image jako sprite sheet
	 * @param image
	 */
	public static void setGridImage(Image gridImage) {
		SystemRoad.gridImage = gridImage;
	}
	/**
	 * Kostruktor
	 * @param startOrgan w�ze�, z kt�rego wychodzi �cie�ka
	 * @param endOrgan w�ze�, do kt�rego dochodzi �cie�ka
	 * @param transportSpeed ci�nienie w �cie�ce/pr�dko�� przep�ywu przez �cie�k�
	 * @param type typ uk�adu-patrz sta�e w TransportSystem
	 */
	public SystemRoad(Node startOrgan, Node endOrgan, double transportSpeed, int type){
		this.startOrgan=startOrgan;
		this.endOrgan=endOrgan;
		this.transportSpeed=transportSpeed;
		this.length=Math.hypot(endOrgan.getMiddlePosition().x-startOrgan.getMiddlePosition().x, endOrgan.getMiddlePosition().y-startOrgan.getMiddlePosition().y);
		if(this.length==0) return;
		this.type=type;
		transform= new AffineTransform();
		this.length=Math.hypot(endOrgan.getMiddlePosition().x-startOrgan.getMiddlePosition().x, endOrgan.getMiddlePosition().y-startOrgan.getMiddlePosition().y);
		transform.rotate(Math.atan2(endOrgan.getMiddlePosition().y-startOrgan.getMiddlePosition().y, endOrgan.getMiddlePosition().x-startOrgan.getMiddlePosition().x), startOrgan.getMiddlePosition().x, startOrgan.getMiddlePosition().y);
	}
	/**
	 * 
	 * @return d�ugo�� �cie�ki
	 */
	public double getLength() {
		return length;
	}
	/**
	 * 
	 * @return szybko��, z jak� mo�na porusza� si� po �cie�ce
	 */
	public double getTransportSpeed() {
		return transportSpeed;
	}
	/**
	 * 
	 * @return czy �cie�ka jest niezablokowana
	 */
	public boolean isEmbolised() {
		return embolisedCount>0;
	}
	/**
	 * zwi�ksza licznki uszkodzonych krwinek znajduj�cych si� w �cie�ce
	 */
	public synchronized void incEmbolised() {
		embolisedCount++;
	}
	/**
	 * zmniejsza licznik uszkodzonych krwinek znajduj�cych si� w �cie�ce
	 */
	public synchronized void decEmbolised() {
		embolisedCount--;
		if(embolisedCount<0)
			embolisedCount=0;
	}
	public void drawSystemRoad(Graphics2D g2d){
				if(transform==null) return;		
				AffineTransform at= g2d.getTransform();
				g2d.setTransform(transform);
				int x=startOrgan.getMiddlePosition().x;
				int y=startOrgan.getMiddlePosition().y;
				if(type==0)
					g2d.drawImage(gridImage, x, y,(int)( x+length), y+10, 0, 748, 60, 768, null);
				else
					g2d.drawImage(gridImage, x, y,(int)( x+length), y+10, 60, 748, 120, 768, null);
				if(isEmbolised())
					g2d.fillRect(x, y, (int)length, 10);
				g2d.setTransform(at);
	}
	/**
	 * 
	 * @return w�ze� ko�cowy �cie�ki
	 */
	public Node getEndOrgan() {
		return endOrgan;
	}
	/**
	 * 
	 * @return w�ze� pocz�tkowy �cie�ki
	 */
	public Node getStartOrgan() {
		return startOrgan;
	}

	
}
