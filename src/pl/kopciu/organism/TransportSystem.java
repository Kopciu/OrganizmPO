package pl.kopciu.organism;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;


/** Uk³ad odpornoœciowy lub krwionoœny reprezentowany za pomoc¹ grafu*/
public class TransportSystem implements Serializable{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = Constans.serialVersionUID;
	public static final int IMMUNOLOGICAL_SYSTEM=1, CIRCULATORY_SYSTEM=0;
	private int type;
	private Organism organism;
	private Node nodes[][];
	private PriorityQueue<Node> queue;
	

	/**
	 * Konstruktor
	 * @param type-patrz-sta³e
	 * @param organism referencja na organizm
	 */
	public TransportSystem(int type, Organism organism){
		this.organism=organism;
		nodes= new Node[5][4];
		if(type==CIRCULATORY_SYSTEM){
			queue= new PriorityQueue<Node>(nodes.length*nodes[0].length+Constans.ORGAN_NAMES.length, new DistanceComparator());
			Point startDrawPosition=new Point(organism.getOrgan(0).getMiddlePosition());
			int distanceX=(organism.getOrgan(1).getMiddlePosition().x-startDrawPosition.x);
			int distanceY=(organism.getOrgan(4).getMiddlePosition().y-startDrawPosition.y);
			startDrawPosition.x-=distanceX/2;
			startDrawPosition.y-=distanceY/2;
			for(int i=0; i<5; i++)
				for(int j=0; j<4; j++){
					nodes[i][j]=new Node();
					nodes[i][j].setGUICoordinates(new Point(startDrawPosition.x-organism.getNodeSize()/2+distanceX*i, startDrawPosition.y-organism.getNodeSize()/2+distanceY*j), 0, 512, 25, 537, new Dimension(organism.getNodeSize(), organism.getNodeSize()));
					if(j==3 && i>2) nodes[i][j]=null;
			}
			generateBloodRoads();
			this.type=type;
		}
		else{
			queue= new PriorityQueue<Node>(Constans.ORGAN_NAMES.length, new DistanceComparator());
			this.type=IMMUNOLOGICAL_SYSTEM;
			generateImmuRoads();			
		}
	}
	public void drawTransportSystem(Graphics2D g2d){
		if(type==CIRCULATORY_SYSTEM){
			for(int i=0; i<5; i++)
				for(int j=0; j<4; j++){
					for(int k=0; k<5; k++)
						if(nodes[i][j]!=null &&nodes[i][j].getRoads(k, type)!=null)
							nodes[i][j].getRoads(k, type).drawSystemRoad(g2d);
				}	
			for(int i=0; i<Constans.ORGAN_NAMES.length; i++)
				organism.getOrgan(i).getRoads(4, type).drawSystemRoad(g2d);
			for(int i=0; i<5; i++)
				for(int j=0; j<4; j++){
					if(nodes[i][j]!=null)
						nodes[i][j].draw(g2d);
			}
		}
		else{
			Organ o;
			for(int i=0; i<Constans.ORGAN_NAMES.length; i++){
				o=organism.getOrgan(i);
				for (int j = 0; j < 4; j++) {
					if(o.getRoads(j, type)!=null)
						o.getRoads(j, type).drawSystemRoad(g2d);
				}
			}
		}
	}
	
	
	/** Znajduje i zwraca najkrótsz¹ mo¿liw¹ drogê z wêz³a do wêz³a
	 * @param from Wêze³, od którego ma zacz¹æ siê droga
	 * @param to   Wêze³, na którym droga mi siê skoñczyæ
	 * @return 	   Lista Node-wêz³ów przez które trzeba przejœæ, by trafiæ z wêz³a from do wêz³¹ to*/
	public synchronized LinkedList<Node> getShortesPathToOrgan(Node from, Node to){
		for (int i = 0; i < Constans.ORGAN_NAMES.length; i++) {
			organism.getOrgan(i).setDistance(Double.MAX_VALUE);
			organism.getOrgan(i).setFrom(null);
			queue.add(organism.getOrgan(i));
		}
		if(type==CIRCULATORY_SYSTEM){
			for(int i=0; i<nodes.length; i++)
				for(int j=0; j<nodes[0].length; j++){
					if(nodes[i][j]!=null){
						nodes[i][j].setDistance(Double.MAX_VALUE);
						nodes[i][j].setFrom(null);
						queue.add(nodes[i][j]);
					}
				}
		}
		from.setDistance(0);
		queue.remove(from);
		Node currentNode=from;
		Node tempNode;
		while(!queue.isEmpty()){
			if(from.getRoads(4, type)!=null && from.getRoads(4, type).getEndOrgan()==to)
				break;
			for(int i=0; i<5; i++){
				if(currentNode.getRoads(i, type)!=null && !currentNode.getRoads(i, type).isEmbolised() && (type==IMMUNOLOGICAL_SYSTEM || currentNode.getRoads(i, type).getEndOrgan().isAvailable())){
					tempNode=currentNode.getRoads(i, type).getEndOrgan();
					if(currentNode.getRoads(i, type).getLength()/currentNode.getRoads(i, type).getTransportSpeed()+currentNode.getDistance()<tempNode.getDistance()){
						tempNode.setDistance(currentNode.getRoads(i, type).getLength()/currentNode.getRoads(i, type).getTransportSpeed()+currentNode.getDistance());
						tempNode.setFrom(currentNode);
						queue.remove(tempNode);
						queue.add(tempNode);
					}
				}
		}	
			currentNode=queue.poll();
		}
		LinkedList<Node> list = new LinkedList<Node>();
		currentNode=to;
		while(currentNode!=null){
			list.addFirst(currentNode);
			currentNode=currentNode.getFrom();	
			if(list.size()>=Constans.ORGAN_NAMES.length+nodes.length*nodes[0].length)
				break;
		}
		if(list.getFirst()!=from){
			list.clear();
			list.add(from);
		}
		queue.clear();
		return list;
	}
	

	private void generateBloodRoads(){
		SystemRoad s;
		Random rand=new Random();
		for(int i=0; i<nodes.length-1; i++){
			for (int j = 0; j < nodes[i].length-1; j++) {
				if(nodes[i][j]!=null && nodes[i+1][j]!=null){
					s = new SystemRoad(nodes[i][j], nodes[i+1][j], rand.nextDouble()+0.5, type);
					nodes[i][j].setRoads(s, 2, type);
					s = new SystemRoad(nodes[i+1][j], nodes[i][j], rand.nextDouble()+0.5, type);
					nodes[i+1][j].setRoads(s, 0, type);
				}
				if(nodes[i][j]!=null && nodes[i][j+1]!=null){
					s = new SystemRoad(nodes[i][j], nodes[i][j+1], rand.nextDouble()+0.5, type);
					nodes[i][j].setRoads(s, 3, type);
					s = new SystemRoad(nodes[i][j+1], nodes[i][j], rand.nextDouble()+0.5, type);
					nodes[i][j+1].setRoads(s, 1, type);
				}
				if(4*j+i>=Constans.ORGAN_NAMES.length) continue;
					s= new SystemRoad(nodes[i][j], organism.getOrgan(4*j+i) , rand.nextDouble()+0.5, type);
					nodes[i][j].setRoads(s, 4, type);
					s= new SystemRoad(organism.getOrgan(4*j+i), nodes[i][j], rand.nextDouble()+0.5, type);
					organism.getOrgan(4*j+i).setRoads(s, 4, type);
					
				
			}
			for (int j = 0; j < nodes[nodes.length-1].length-1; j++) {
				if(nodes[nodes.length-1][j]!=null && nodes[nodes.length-1][j+1]!=null){
					s = new SystemRoad(nodes[nodes.length-1][j], nodes[nodes.length-1][j+1], rand.nextDouble()+0.5, type);
					nodes[nodes.length-1][j].setRoads(s, 3, type);
					s = new SystemRoad(nodes[nodes.length-1][j+1], nodes[nodes.length-1][j], rand.nextDouble()+0.5, type);
					nodes[nodes.length-1][j+1].setRoads(s, 1, type);
				}
			}
		}
		for(int i=0; i<nodes.length-1; i++){
			if(nodes[i][nodes[i].length-1]!=null && nodes[i+1][nodes[i].length-1]!=null){
				s = new SystemRoad(nodes[i][nodes[i].length-1], nodes[i+1][nodes[i].length-1], rand.nextDouble()+0.5, type);
				nodes[i][nodes[i].length-1].setRoads(s, 2, type);
				s = new SystemRoad(nodes[i+1][nodes[i].length-1], nodes[i][nodes[i].length-1], rand.nextDouble()+0.5, type);
				nodes[i+1][nodes[i].length-1].setRoads(s, 0, type);
			}
		}
		
	}
	private void generateImmuRoads(){
		SystemRoad s;
		Organ host;
		for(int i=0; i<4; i++){
			for(int j=0; j<2; j++){
				host=organism.getOrgan(i+j*4);
				if(i+j*4+4<Constans.ORGAN_NAMES.length){
					s=new SystemRoad(host, organism.getOrgan(i+j*4+4), 1, type);
					host.setRoads(s, 2, type);
					s=new SystemRoad(organism.getOrgan(i+j*4+4), host, 1, type);
					organism.getOrgan(i+j*4+4).setRoads(s, 0, type);
				}
			}
		}
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j+=2){
				if(i+j*4>=Constans.ORGAN_NAMES.length-1) break;
				host=organism.getOrgan(i+j*4);
				s=new SystemRoad(host, organism.getOrgan(i+j*4+1), 1, type);
				host.setRoads(s, 1, type);
			}
		for(int i=5; i<8; i++){
			s=new SystemRoad(organism.getOrgan(i), organism.getOrgan(i-1), 1, type);
			organism.getOrgan(i).setRoads(s, 3, type);
		}
	}
	
}

