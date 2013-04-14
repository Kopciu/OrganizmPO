package pl.kopciu.organism;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LevelPopup extends JFrame {

	private static final long serialVersionUID = Constans.serialVersionUID;
	private JPanel contentPane;
	private GUI gui;


	public LevelPopup(GUI gui) {
		setAlwaysOnTop(true);
		this.gui=gui;
		setUndecorated(true);
		setVisible(true);
		setResizable(false);
		Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(screenSize.width/2-100, screenSize.height/2-100, 200, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setDoubleBuffered(true);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNewLabel.setSize(new Dimension(150, 35));
		lblNewLabel.setIcon(new ImageIcon(LevelPopup.class.getResource("/pl/kopciu/organism/images/smallLogo.png")));
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_3 = new JButton("Wczytaj");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				load();	
			}
		});
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton = new JButton("Poziom 1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLevel(0);
				dispose();
			}
		});
		btnNewButton.setSize(new Dimension(150, 30));
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Poziom 2");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setLevel(1);
				dispose();
			}
		});
		btnNewButton_1.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_1.setSize(new Dimension(150, 30));
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Poziom 3");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLevel(2);
				dispose();
			}
		});
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_4 = new JButton("Exit");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		contentPane.add(btnNewButton_4);
	}


	private void load() {
		gui.launchSavePopup(false);
		
	}


	private void setLevel(int i) {
		gui.startGameAtLevel(i);
		
	}

}
