package pl.kopciu.organism;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel implements MouseListener{

	private static final long serialVersionUID = 1L;
	private static Dimension screenSize, windowSize;
	private Organism organism;
	private transient InfoWindow infoWindow;
	private transient LevelPopup levelPopup;

	public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException, ClassNotFoundException {		
		new GUI();

	}
	/**
	 * Klasa g³ównego okna organizmu
	 * @throws InterruptedException
	 */
	public GUI() throws InterruptedException{
		super();
		JFrame window=new JFrame("Organism");
		window.setUndecorated(true);
		screenSize= Toolkit.getDefaultToolkit().getScreenSize();
		windowSize= new Dimension(screenSize.width, screenSize.height);
		addMouseListener(this);
		organism = new Organism(windowSize, this);
		window.add(this);
		setBackground(new Color(0xFFFFFFFF));
		window.setSize(windowSize);
		window.setLocation((screenSize.width-windowSize.width)/2, (screenSize.height-windowSize.height)/2);
		window.setVisible(true);
		window.getContentPane().setBackground(new Color(0xFFFFFFFF));
		infoWindow=new InfoWindow(this);
		levelPopup = new LevelPopup(this);
		levelPopup.setVisible(true);
		WindowAdapter wl=new WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		        respondToCloseOperation();
		    }};
		infoWindow.addWindowListener(wl);
		window.addWindowListener(wl);
		while(true){
			Thread.sleep(40);
			repaint();
			infoWindow.update();
			
		}
		
	}
	public Organism getOrganism() {
		return organism;
	}
	/**
	 * Funkcja wywo³ywana w celu zakoñczenia dzia³ania programu, wyœwietla okno do wpisywania imienia
	 */
	public void respondToCloseOperation() {
		new NamePopup(this, screenSize.width, screenSize.height);
		
	}
	/**
	 * Funkcja wymuszaj¹ca zapisanie wyniku rozgrywki
	 * @param name Imiê gracza, którego wynik bêdzie zapisany
	 */
	public void saveResult(String name){
		organism.saveResult(name);
		System.exit(0); 
	}
	/**
	 * tworzy i pokazuje okienko do zapisu/odczytu stanu gry
	 * @param save czy okno jest odpalone w celu zapisu?
	 */
	public void launchSavePopup(boolean save){
		new SavePopup(this, screenSize.width, screenSize.height, save);
	}
	/**
	 * Zapisuje stan gry do pliku o nazwie podanej w parametrze
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void save(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(
                  new FileOutputStream(fileName)));
		out.writeObject(organism);
		out.writeObject(Nutrition.getCurrentId());
		out.writeObject(MobileThing.getCurrentId());
		out.writeObject(Virus.getKilledOrgans());
		out.writeObject(Virus.getOrgansAlive());
		out.writeBoolean(infoWindow.isActionsActive());
		out.writeObject(Calendar.getInstance());
		out.close();
	
	}
	/**
	 * Wczytuje stan gry z pliku o nazwie wczytanym w parametrze
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void load(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(
                  new FileInputStream(fileName)));
		organism= (Organism) in.readObject();
		Nutrition.setCurrentId((int) in.readObject());
		MobileThing.setCurrentId((int) in.readObject());
		Virus.setKilledOrgans((int) in.readObject());
		Virus.setOrgansAlive((int[]) in.readObject());
		infoWindow.setDisplayedThing(organism.getPopupSubject());
		infoWindow.setActionsActive(in.readBoolean());
		organism.adjustTime((Calendar)in.readObject());
		in.close();
		organism.restart();
		if(levelPopup!=null)
			levelPopup.dispose();
		if(!infoWindow.isVisible())
			infoWindow.setVisible(true);
	}
	/**
	 * startuje grê na poziomie podanym w parametrze
	 * @param level
	 */
	public void startGameAtLevel(int level){
		infoWindow.setVisible(true);
		organism.start(level);
		if(levelPopup!=null)
			levelPopup.dispose();
		
		
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d=(Graphics2D) g;
		super.paintComponents(g2d);
		g2d.clearRect(0, 0, windowSize.width, windowSize.height);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		organism.drawOrganism(g2d);
		
		
		
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		organism.onClick(arg0.getX(), arg0.getY());
		infoWindow.setDisplayedThing(organism.getPopupSubject());
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}


}
