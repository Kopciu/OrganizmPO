package pl.kopciu.organism;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.JTextField;

/**
 * Klasa okienka informacyjnego, wygenerowana w wiêkszoœci za pomoc¹ pluginu WindowBuilder
 * @author Kopciu
 *
 */
public class InfoWindow extends JFrame {
	private static final long serialVersionUID = Constans.serialVersionUID;
	private JPanel contentPane;
	private JLabel nameLabel;
	private JLabel idLabel;
	private JLabel speedLabel;
	private JLabel targetLabel;
	private JLabel capLabel;
	private Thing displayedThing;
	private JPanel basicPanel;
	private Box verticalBox, erytrocytPanel, leukoPanel, virusPanel, currentPanel;
	private JPanel nutritionPanel;
	private JLabel nutritionLabels[], nutritionLabels2[];
	private JLabel targetLabel2, targetLabel3;
	private JLabel speedLabel2, speedLabel3;
	private JLabel idLabel2, idLabel3;
	private Box organPanel;
	private JPanel panel_3;
	private JLabel lblNazwa;
	private JLabel nameLabel4;
	private JLabel lblZdrowie;
	private JLabel healthLabel;
	private JLabel label_11;
	private JLabel capLabel2;
	private JLabel label_14;
	private JPanel nutritionPanel2;
	private JLabel lblNewLabel;
	private Box erytroActionsPanel;
	private JPanel panel_4;
	private JButton btnUtwrzZator;
	private JButton btnUsu;
	private boolean isActionsActive=false;
	private JButton actionsButton;
	private Box organActionsPanel;
	private JButton organButtons[];
	private HashMap<JButton, Integer> buttonsIndex;
	private JLabel lblNewLabel_1;
	private JTextField roadField;
	private JLabel lblNewLabel_2;
	private JPanel panel;
	private JButton btnZaadujObecn;
	private JButton btnZatwierd;
	private GUI gui;
	private JPanel buttonsPanel2;
	private JButton btnZapisz;
	private JButton btnWczytaj;

	/**
	 * Konstruktor
	 * @param organism referencja na organizm na którym odbywa siê symulacja
	 */
	public InfoWindow(GUI gui) {
		setResizable(false);
		this.gui=gui;
		setAlwaysOnTop(true);
		setBounds(100, 100, 320, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		nutritionLabels=new JLabel[Constans.NUTRITION_NAMES.length];
		nutritionLabels2=new JLabel[Constans.NUTRITION_NAMES.length];
		organButtons= new JButton[Constans.NUTRITION_NAMES.length+2];
		buttonsIndex = new HashMap<JButton, Integer>(Constans.NUTRITION_NAMES.length);
		verticalBox = Box.createVerticalBox();
		verticalBox.setDoubleBuffered(true);
		contentPane.add(verticalBox);
		
		JLabel header = new JLabel("");
		header.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(header);
		header.setIcon(new ImageIcon(InfoWindow.class.getResource("/pl/kopciu/organism/images/logo.png")));
		
		nameLabel = new JLabel(" ");
		nameLabel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		nameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nameLabel.setAlignmentY(Component.TOP_ALIGNMENT);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(nameLabel);
		
		buttonsPanel2 = new JPanel();
		buttonsPanel2.setSize(new Dimension(320, 25));
		buttonsPanel2.setMinimumSize(new Dimension(320, 25));
		buttonsPanel2.setMaximumSize(new Dimension(320, 25));
		buttonsPanel2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		buttonsPanel2.setBounds(new Rectangle(0, 0, 320, 25));
		buttonsPanel2.setAlignmentY(0.0f);
		verticalBox.add(buttonsPanel2);
		buttonsPanel2.setLayout(new GridLayout(1, 3, 0, 0));
		
		btnZapisz = new JButton("Zapisz");
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		btnZapisz.setMinimumSize(new Dimension(100, 25));
		btnZapisz.setMaximumSize(new Dimension(100, 25));
		btnZapisz.setDoubleBuffered(true);
		buttonsPanel2.add(btnZapisz);
		
		btnWczytaj = new JButton("Wczytaj");
		btnWczytaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();
			}
		});
		btnWczytaj.setDoubleBuffered(true);
		btnWczytaj.setAlignmentX(0.5f);
		buttonsPanel2.add(btnWczytaj);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		verticalBox.add(buttonsPanel);
		buttonsPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		buttonsPanel.setSize(new Dimension(320, 25));
		buttonsPanel.setMinimumSize(new Dimension(320, 25));
		buttonsPanel.setMaximumSize(new Dimension(320, 25));
		buttonsPanel.setBounds(new Rectangle(0, 0, 320, 25));
		buttonsPanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JButton infoButton = new JButton("Info");
		infoButton.setDoubleBuffered(true);
		infoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isActionsActive=false;
			}
		});
		infoButton.setMinimumSize(new Dimension(100, 25));
		infoButton.setMaximumSize(new Dimension(100, 25));
		buttonsPanel.add(infoButton);
		
		actionsButton = new JButton("Akcje");
		actionsButton.setDoubleBuffered(true);
		actionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isActionsActive=true;
			}
		});
		actionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonsPanel.add(actionsButton);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 5));
		verticalStrut.setMinimumSize(new Dimension(0, 5));
		verticalBox.add(verticalStrut);
		
		erytrocytPanel = Box.createVerticalBox();
		erytrocytPanel.setDoubleBuffered(true);
		erytrocytPanel.setVisible(false);
		
		erytroActionsPanel = Box.createVerticalBox();
		erytroActionsPanel.setDoubleBuffered(true);
		erytroActionsPanel.setVisible(false);
		
		organActionsPanel = Box.createVerticalBox();
		organActionsPanel.setVisible(false);
		organActionsPanel.setDoubleBuffered(true);
		verticalBox.add(organActionsPanel);
		verticalBox.add(erytroActionsPanel);
		
		panel_4 = new JPanel();
		panel_4.setSize(new Dimension(320, 25));
		panel_4.setMinimumSize(new Dimension(320, 25));
		panel_4.setMaximumSize(new Dimension(320, 25));
		panel_4.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel_4.setBounds(new Rectangle(0, 0, 320, 25));
		panel_4.setAlignmentY(0.0f);
		erytroActionsPanel.add(panel_4);
		panel_4.setLayout(new GridLayout(1, 3, 0, 0));
		
		btnUtwrzZator = new JButton("Utw\u00F3rz zator");
		btnUtwrzZator.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((Erythrocyte) displayedThing).setEmbolised(true);
			}
		});
		btnUtwrzZator.setMinimumSize(new Dimension(100, 25));
		btnUtwrzZator.setMaximumSize(new Dimension(100, 25));
		panel_4.add(btnUtwrzZator);
		
		btnUsu = new JButton("Usu\u0144");
		btnUsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleDeleteButton((JButton) e.getSource());
			}
		});
		btnUsu.setAlignmentX(0.5f);
		panel_4.add(btnUsu);
		
		lblNewLabel_1 = new JLabel("Trasa:");
		erytroActionsPanel.add(lblNewLabel_1);
		lblNewLabel_1.setDoubleBuffered(true);
		lblNewLabel_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblNewLabel_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		roadField = new JTextField();
		roadField.setDoubleBuffered(true);
		roadField.setFocusCycleRoot(true);
		roadField.setMaximumSize(new Dimension(300, 30));
		erytroActionsPanel.add(roadField);
		roadField.setColumns(10);
		
		lblNewLabel_2 = new JLabel("Wpisz indeksy organ\u00F3w i zatwierd\u017A");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		erytroActionsPanel.add(lblNewLabel_2);
		
		panel = new JPanel();
		panel.setSize(new Dimension(320, 25));
		panel.setMinimumSize(new Dimension(320, 25));
		panel.setMaximumSize(new Dimension(320, 25));
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel.setBounds(new Rectangle(0, 0, 320, 25));
		panel.setAlignmentY(0.0f);
		erytroActionsPanel.add(panel);
		panel.setLayout(new GridLayout(1, 3, 0, 0));
		
		btnZaadujObecn = new JButton("Za\u0142aduj obecn\u0105");
		btnZaadujObecn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadRoad();
			}
		});
		btnZaadujObecn.setMinimumSize(new Dimension(100, 25));
		btnZaadujObecn.setMaximumSize(new Dimension(100, 25));
		btnZaadujObecn.setDoubleBuffered(true);
		panel.add(btnZaadujObecn);
		
		btnZatwierd = new JButton("Zatwierd\u017A");
		btnZatwierd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmRoad();
			}
		});
		btnZatwierd.setDoubleBuffered(true);
		btnZatwierd.setAlignmentX(0.5f);
		panel.add(btnZatwierd);
		verticalBox.add(erytrocytPanel);
		
		basicPanel = new JPanel();
		erytrocytPanel.add(basicPanel);
		basicPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		basicPanel.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel Id = new JLabel("id:");
		Id.setHorizontalAlignment(SwingConstants.CENTER);
		Id.setAlignmentX(Component.CENTER_ALIGNMENT);
		Id.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		basicPanel.add(Id);
		
		idLabel = new JLabel("(id)");
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		basicPanel.add(idLabel);
		
		JLabel Speed = new JLabel("szybko\u015B\u0107:");
		Speed.setHorizontalAlignment(SwingConstants.CENTER);
		basicPanel.add(Speed);
		
		speedLabel = new JLabel("(speed)");
		speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		basicPanel.add(speedLabel);
		
		JLabel Target = new JLabel("nast\u0119pny cel:");
		Target.setHorizontalAlignment(SwingConstants.CENTER);
		Target.setHorizontalTextPosition(SwingConstants.CENTER);
		basicPanel.add(Target);
		
		targetLabel = new JLabel("(cel)");
		targetLabel.setHorizontalAlignment(SwingConstants.CENTER);
		basicPanel.add(targetLabel);	
		
		JLabel Cap = new JLabel("pojemnoœæ:");
		Cap.setHorizontalAlignment(SwingConstants.CENTER);
		Cap.setHorizontalTextPosition(SwingConstants.CENTER);
		basicPanel.add(Cap);
		
		capLabel = new JLabel("(cap)");
		capLabel.setHorizontalAlignment(SwingConstants.CENTER);
		basicPanel.add(capLabel);
		
		JLabel nutriPicture = new JLabel("");
		nutriPicture.setAlignmentX(Component.CENTER_ALIGNMENT);
		nutriPicture.setHorizontalAlignment(SwingConstants.CENTER);
		nutriPicture.setIcon(new ImageIcon(InfoWindow.class.getResource("/pl/kopciu/organism/images/nutr.png")));
		nutriPicture.setMinimumSize(new Dimension(268, 30));
		nutriPicture.setMaximumSize(new Dimension(268, 30));
		nutriPicture.setPreferredSize(new Dimension(268, 30));
		nutriPicture.setHorizontalTextPosition(SwingConstants.CENTER);
		erytrocytPanel.add(nutriPicture);
		
		nutritionPanel = new JPanel();
		nutritionPanel.setMaximumSize(new Dimension(268, 30));
		nutritionPanel.setMinimumSize(new Dimension(268, 30));
		nutritionPanel.setPreferredSize(new Dimension(268, 30));
		erytrocytPanel.add(nutritionPanel);
		nutritionPanel.setLayout(new GridLayout(1, Constans.NUTRITION_NAMES.length, 0, 0));
		
		leukoPanel = Box.createVerticalBox();
		leukoPanel.setDoubleBuffered(true);
		leukoPanel.setVisible(false);
		verticalBox.add(leukoPanel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		leukoPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel label = new JLabel("id:");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		label.setAlignmentX(0.5f);
		panel_1.add(label);
		
		idLabel2 = new JLabel("(id)");
		idLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(idLabel2);
		
		JLabel label_2 = new JLabel("szybko\u015B\u0107:");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label_2);
		
		speedLabel2 = new JLabel("(speed)");
		speedLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(speedLabel2);
		
		JLabel label_4 = new JLabel("nast\u0119pny cel:");
		label_4.setHorizontalTextPosition(SwingConstants.CENTER);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(label_4);
		
		targetLabel2 = new JLabel("(cel)");
		targetLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(targetLabel2);
		
		virusPanel = Box.createVerticalBox();
		virusPanel.setDoubleBuffered(true);
		virusPanel.setVisible(false);
		verticalBox.add(virusPanel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		virusPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel label1 = new JLabel("id:");
		label1.setHorizontalAlignment(SwingConstants.CENTER);
		label1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		label1.setAlignmentX(0.5f);
		panel_2.add(label1);
		
		idLabel3 = new JLabel("(id)");
		idLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(idLabel3);
		
		JLabel label_3 = new JLabel("szybko\u015B\u0107:");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_3);
		
		speedLabel3 = new JLabel("(speed)");
		speedLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(speedLabel3);
		
		JLabel label_5 = new JLabel("nast\u0119pny cel:");
		label_5.setHorizontalTextPosition(SwingConstants.CENTER);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_5);
		
		targetLabel3 = new JLabel("(cel)");
		targetLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(targetLabel3);
		
		organPanel = Box.createVerticalBox();
		organPanel.setDoubleBuffered(true);
		organPanel.setVisible(false);
		verticalBox.add(organPanel);
		
		panel_3 = new JPanel();
		panel_3.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		organPanel.add(panel_3);
		panel_3.setLayout(new GridLayout(3, 2, 0, 0));
		
		lblNazwa = new JLabel("nazwa:");
		lblNazwa.setHorizontalAlignment(SwingConstants.CENTER);
		lblNazwa.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		lblNazwa.setAlignmentX(0.5f);
		panel_3.add(lblNazwa);
		
		nameLabel4 = new JLabel("(nazwa)");
		nameLabel4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(nameLabel4);
		
		lblZdrowie = new JLabel("zdrowie:");
		lblZdrowie.setHorizontalTextPosition(SwingConstants.CENTER);
		lblZdrowie.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblZdrowie);
		
		healthLabel = new JLabel("(zdrowie)");
		healthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(healthLabel);
		
		label_11 = new JLabel("pojemno\u015B\u0107:");
		label_11.setHorizontalTextPosition(SwingConstants.CENTER);
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(label_11);
		
		capLabel2 = new JLabel("(cap)");
		capLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(capLabel2);
		
		label_14 = new JLabel("");
		label_14.setIcon(new ImageIcon(InfoWindow.class.getResource("/pl/kopciu/organism/images/nutr.png")));
		label_14.setPreferredSize(new Dimension(268, 30));
		label_14.setMinimumSize(new Dimension(268, 30));
		label_14.setMaximumSize(new Dimension(268, 30));
		label_14.setHorizontalTextPosition(SwingConstants.CENTER);
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setAlignmentX(0.5f);
		organPanel.add(label_14);
		
		nutritionPanel2 = new JPanel();
		nutritionPanel2.setPreferredSize(new Dimension(268, 30));
		nutritionPanel2.setMinimumSize(new Dimension(268, 30));
		nutritionPanel2.setMaximumSize(new Dimension(268, 30));
		organPanel.add(nutritionPanel2);
		nutritionPanel2.setLayout(new GridLayout(1, 9, 0, 0));
		
		lblNewLabel = new JLabel("Wymagane przez organ warto\u015Bci od\u017Cywcze zaznaczone s\u0105 boldem");
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 9));
		organPanel.add(lblNewLabel);
		
		for(int i=0; i<Constans.NUTRITION_NAMES.length; i++){
			nutritionLabels[i] = new JLabel("");
			nutritionLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			nutritionLabels[i].setHorizontalTextPosition(SwingConstants.CENTER);
			nutritionLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			nutritionLabels[i].setToolTipText(Constans.NUTRITION_NAMES[i]);
			nutritionPanel.add(nutritionLabels[i]);
		}
		for(int i=0; i<Constans.NUTRITION_NAMES.length; i++){
			nutritionLabels2[i] = new JLabel("");
			nutritionLabels2[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			nutritionLabels2[i].setHorizontalTextPosition(SwingConstants.CENTER);
			nutritionLabels2[i].setHorizontalAlignment(SwingConstants.CENTER);
			nutritionLabels2[i].setToolTipText(Constans.NUTRITION_NAMES[i]);
			nutritionPanel2.add(nutritionLabels2[i]);
		}
		
		ActionListener al=new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleOrganButton((JButton)e.getSource());
			}
		};
		for(int i=0; i<Constans.NUTRITION_NAMES.length; i++){
			organButtons[i]=new JButton();
			organButtons[i].setText(Constans.NUTRITION_NAMES[i]+"++");
			organButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
			organButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			organButtons[i].setPreferredSize(new Dimension(200, 30));
			organButtons[i].setMaximumSize(new Dimension(200, 30));
			organButtons[i].setMinimumSize(new Dimension(200, 30));
			organButtons[i].addActionListener(al);
			buttonsIndex.put(organButtons[i], i);
			organActionsPanel.add(organButtons[i]);
			
		}
		int i=Constans.NUTRITION_NAMES.length;
		organButtons[i]=new JButton();
		organButtons[i].setText("Erytrocyty++");
		organButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
		organButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
		organButtons[i].setPreferredSize(new Dimension(200, 30));
		organButtons[i].setMaximumSize(new Dimension(200, 30));
		organButtons[i].setMinimumSize(new Dimension(200, 30));
		organButtons[i].addActionListener(al);
		organActionsPanel.add(organButtons[i]);
		
		 i=Constans.NUTRITION_NAMES.length+1;
		organButtons[i]=new JButton();
		organButtons[i].setText("Leukocyty++");
		organButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
		organButtons[i].setAlignmentX(Component.CENTER_ALIGNMENT);
		organButtons[i].setPreferredSize(new Dimension(200, 30));
		organButtons[i].setMaximumSize(new Dimension(200, 30));
		organButtons[i].setMinimumSize(new Dimension(200, 30));
		organButtons[i].addActionListener(al);
		organActionsPanel.add(organButtons[i]);
		
	}
	private void confirmRoad() {
		((Erythrocyte) displayedThing).setRoad(roadField.getText());
		
	}
	private void loadRoad() {
		roadField.setText(((Erythrocyte) displayedThing).getRoad());
		
	}
	/**
	 * uaktualnia obecny stan okienka informacyjnego
	 */
	public void update(){
			refresh(displayedThing);
	}
	/**
	 * ustawia obiekt, o którym informacje s¹ wyœwietlane
	 * @param displayedThing
	 */
	public void setDisplayedThing(Thing displayedThing) {
		this.displayedThing=displayedThing;
	}
	private void refresh(Thing e){
		if(e==null){
			nameLabel.setText(" ");
			if(currentPanel!=null)
				currentPanel.setVisible(false);
			currentPanel=null;
			isActionsActive=false;
		}
		else if(e.isDead()){
			displayedThing=null;
			isActionsActive=false;
		}
		else if(e.getClass()==Erythrocyte.class){
			nameLabel.setText(e.toString());
			if(isActionsActive){
				if(currentPanel!=null && currentPanel!=erytroActionsPanel)
					currentPanel.setVisible(false);
				currentPanel=erytroActionsPanel;
			}
			else{
				
				idLabel.setText(""+((Erythrocyte) e).getId());
				speedLabel.setText(String.format("Speed: %.2f",((Erythrocyte) e).getCurrentSpeed()*((MobileThing)e).getSelfSpeed()));
				targetLabel.setText(((Erythrocyte) e).getNextTarget().toString());
				capLabel.setText(""+((Erythrocyte) e).getCapacity()+"/"+((Erythrocyte) e).getMaxCapacity());
				for (int i = 0; i < Constans.NUTRITION_NAMES.length; i++) {
				nutritionLabels[i].setText(""+((Erythrocyte) e).getNumberOfNutritions(i));
				}
				if(currentPanel!=null && currentPanel!=erytrocytPanel)
					currentPanel.setVisible(false);
				currentPanel=erytrocytPanel;
			}
			if(!currentPanel.isVisible())
				currentPanel.setVisible(true);
		}
		else if(e.getClass()==Leukocyte.class){
			nameLabel.setText(e.toString());
			idLabel2.setText(""+((Leukocyte) e).getId());
			speedLabel2.setText(String.format("Speed: %.2f",((Leukocyte) e).getCurrentSpeed()*((MobileThing)e).getSelfSpeed()));
			targetLabel2.setText(((Leukocyte) e).getTarget().toString());
			if(currentPanel!=null  && currentPanel!=leukoPanel)
				currentPanel.setVisible(false);
			currentPanel=leukoPanel;
			if(!leukoPanel.isVisible())
				currentPanel.setVisible(true);
		}
		else if(e.getClass()==Virus.class){
			nameLabel.setText(e.toString());
			idLabel3.setText(""+((Virus) e).getId());
			speedLabel3.setText(String.format("Speed: %.2f",((Virus) e).getCurrentSpeed()*((MobileThing)e).getSelfSpeed()));
			targetLabel3.setText(((Virus) e).getTarget().toString());
			if(currentPanel!=null && currentPanel!=virusPanel)
				currentPanel.setVisible(false);
			currentPanel=virusPanel;
			if(!currentPanel.isVisible())
				currentPanel.setVisible(true);
		}
		else if(e.getClass()==Organ.class){
			nameLabel.setText(e.toString());
			if(isActionsActive){			
				if(currentPanel!=null && currentPanel!=organActionsPanel)
					currentPanel.setVisible(false);
				currentPanel=organActionsPanel;
				for(int i=0; i<Constans.NUTRITION_NAMES.length; i++){
					if(Constans.fromToNutritionTab[0][i]==((Organ) e).getNumber())
						organButtons[i].setVisible(true);
					else
						organButtons[i].setVisible(false);
				}
				if(e==gui.getOrganism().getMarrow()){
					organButtons[Constans.NUTRITION_NAMES.length].setVisible(true);
					organButtons[Constans.NUTRITION_NAMES.length+1].setVisible(true);
				}
				else{
					organButtons[Constans.NUTRITION_NAMES.length].setVisible(false);
					organButtons[Constans.NUTRITION_NAMES.length+1].setVisible(false);
				}
				if(e==gui.getOrganism().getOrgan(8))
					organButtons[Constans.NUTRITION_NAMES.length+1].setVisible(true);
			}
			else{
				
				nameLabel4.setText(e.toString());
				healthLabel.setText(""+100*((Organ) e).getHealth()/255+"%");
				capLabel2.setText(""+((Organ) e).getCapacity()+"/"+((Organ) e).getMaxCapacity());
				for (int i = 0; i < Constans.NUTRITION_NAMES.length; i++) {
					nutritionLabels2[i].setText(""+((Organ) e).getNumberOfNutritions(i));
					if(Constans.fromToNutritionTab[1][i]==((Organ) e).getNumber())
						nutritionLabels2[i].setFont(nutritionLabels2[i].getFont().deriveFont(Font.BOLD));
					else
						nutritionLabels2[i].setFont(nutritionLabels2[i].getFont().deriveFont(Font.PLAIN));
				}
			if(currentPanel!=null && currentPanel!=organPanel)
				currentPanel.setVisible(false);
			currentPanel=organPanel;
				}
			if(!currentPanel.isVisible())
				currentPanel.setVisible(true);
		}
	}
	private void handleOrganButton(JButton button){
		if(button==organButtons[organButtons.length-2])
			gui.getOrganism().addErythrocyte();
		else if(button==organButtons[organButtons.length-1])
			gui.getOrganism().addLeukocyte();
		else if(buttonsIndex.containsKey(button))
			((Organ) displayedThing).addNutrition(buttonsIndex.get(button));
		
	}
	private void save(){
			gui.launchSavePopup(true);
	}
	private void load(){
			gui.launchSavePopup(false);
	}

	private void handleDeleteButton(JButton button){
		gui.getOrganism().removeErythrocyte(((Erythrocyte) displayedThing));
	}
	public boolean isActionsActive() {
		return isActionsActive;
	}
	public void setActionsActive(boolean isActionsActive) {
		this.isActionsActive = isActionsActive;
	}



}
