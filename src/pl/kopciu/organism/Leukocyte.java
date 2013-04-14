package pl.kopciu.organism;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Calendar;
/**
 * Klasa bia�ej krwinki broni�cej organizmu przed wirusami
 * @author Kopciu
 * 
 */
public class Leukocyte extends MobileThing{
	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	private Virus target;
	private Node destination;
	private Organism organism;
	/**
	 * Konstruktor
	 * @param organism referencja na organizm
	 * @param target cel, kt�ry b�dzie pr�bowa�a przechwyci� krwinka
	 * @param organ Organ, w kt�ym stworzono leukocyt
	 */
	public Leukocyte(Organism organism, Virus target, Organ organ) {
		super(organ, organism);
		this.setGUICoordinates(this.getLastVisited().getMiddlePosition(),162, 543, 196, 613, new Dimension(11, 23));
		this.organism=organism;
		this.setTarget(target);
		target.incChasingLeukocytes();
		destination=target.getNextNode();
		if(destination==null)
		destination= target.getLastVisited();
		
		this.changePath(organism.getImmunologicalSystem().getShortesPathToOrgan(this.getLastVisited(), destination));
	}
	@Override
	public void draw(Graphics2D g2d){
		super.draw(g2d);
		int alpha=((int) (128*Math.abs(Math.sin(0.01*Calendar.getInstance().get(Calendar.MILLISECOND)))))<<24;
		g2d.setColor(new Color(0x000000FF|alpha, true));
		g2d.fillOval(this.getMiddlePosition().x-this.getSize().width/2, this.getMiddlePosition().y-this.getSize().width/2,this.getSize().width, this.getSize().width);
		g2d.setColor(new Color(0));
	}
	/**
	 * 
	 * @return W�ze� sieci, do kt�ego aktualnie kieruje si� krwinka
	 */
	public Node getDestination() {
		return destination;
	}
	/**
	 * Ustawia w�ze� sieci, do kt�rego aktualnie kieruje si� krwinka
	 * @param destination cel
	 */
	public void setDestination(Node destination) {
		this.destination = destination;
	}

	@Override
	public void run(){
		while(!MobileThing.isFinished() && !isDead()){
		super.run();
		synchronized (target) {		
		if(target!=null && target.isDead()){
			organism.getNextTarget();
		}
		Virus v=organism.getVirusInRange(this.getMiddlePosition());
		if(v!=null && this.sameDirections(v)){
			organism.virusKilled(this, v);
			continue;
		}
		}
		if(destination!=target.getNextNode() || (target.getNextNode()==null && destination!=target.getLastVisited())){
			destination=target.getNextNode();
			if(destination==null)
			destination= target.getLastVisited();
			if(this.getNextNode()!=null)
				this.changePath(organism.getImmunologicalSystem().getShortesPathToOrgan(this.getNextNode(), destination));
			else
				this.changePath(organism.getImmunologicalSystem().getShortesPathToOrgan(this.getLastVisited(), destination));
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {return; }
	}
		if(isDead() && this.getLastVisited().getImmuOnBoard()==this)
			this.getLastVisited().setMobileOnBoard(this, false);
}

	/**
	 * Zwraca cel, za kt�ry pr�buje przechwyci� krwinka
	 * @return Virus b�d�cy celem
	 */
	public Virus getTarget() {
		return target;
	}
	/**
	 * Ustawia cel krwinki
	 * @param target Virus, kt�rego krwinka b�dzie stara�a si� przechwyci�
	 */
	public void setTarget(Virus target) {
		target.incChasingLeukocytes();
		this.target = target;
	}
	@Override
	public String toString(){
		return ("Leukocyt id: "+this.getId());
	}

}
