package pl.kopciu.organism;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Klasa opisuj¹ca rezultat rozgrywki
 * @author Kopciu
 *
 */
public class Result  implements Comparator<Result>{
	private String name;
	private int killedPathogens;
	private long time;
	
	private static LinkedList<Result> results=new LinkedList<Result>();
	/**
	 * Konstruktor
	 * @param name nazwa u¿ytkownika
	 * @param killed iloœæ zabitych wirusów
	 * @param time czas gry w sekundach
	 */
	@SuppressWarnings("unchecked")
	public static void addResult(String name, int killed, long time){
		XMLDecoder d=null;
	    try {
			 d= new XMLDecoder(new BufferedInputStream( new FileInputStream(Constans.FILE_NAME)));
			results=(LinkedList<Result>)d.readObject();
		} catch (Exception e1) {}
		finally{if(d!=null) d.close(); }
		Result r= new Result(name, killed, time);
		results.add(r);
		Collections.sort(results, new Result());
		while(results.size()>10)
			results.removeLast();
		XMLEncoder e = null;
		try {
			e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(Constans.FILE_NAME)));
			e.writeObject(results);
		} catch (Exception ex) {}
		finally{if(e!=null) e.close(); }
	}
	public Result(){
	}
	private Result(String name, int killed, long time){
		this.name=name;
		this.killedPathogens=killed;
		this.time=time;
	}
	/**
	 * Patrz-parametry konstruktora
	 */
	public String getName() {
		return name;
	}
	/**
	 * Patrz-parametry konstruktora
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Patrz-parametry konstruktora
	 */
	public long getTime() {
		return time;
	}
	/**
	 * Patrz-parametry konstruktora
	 */
	public void setTime(long time) {
		this.time = time;
	}
	/**
	 * Patrz-parametry konstruktora
	 */
	public int getKilledPathogens() {
		return killedPathogens;
	}
	/**
	 * Patrz-parametry konstruktora
	 */
	public void setKilledPathogens(int killedPathogens) {
		this.killedPathogens = killedPathogens;
	}
	/**
	 * Porównuje wyniki gry ba podstawie czasów, wynik porównania jest przeciwny do porównania czasów
	 */
	@Override
	public int compare(Result o1, Result o2) {
		return -Long.compare(o1.time, o2.time);
	}

}
