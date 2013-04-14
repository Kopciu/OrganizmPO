package pl.kopciu.organism;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.Point;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Klasa okienka wpisywania imienia, wygenerowana w wiêkszoœci za pomoc¹ pluginu WindowBuilder
 * @author Kopciu
 *
 */
public class SavePopup extends JFrame {

	private static final long serialVersionUID = Constans.serialVersionUID;
	private static final String MSG_NO_FILE="Nie ma zapisu o podanej nazwie", MSG_WRONG_NAME="Nazwa musi byæ jednowyrazowa", MSG_ERROR="Operacja nie powiod³a siê";
	private JPanel contentPane;
	private GUI gui;
	private JTextField nameField;
	private boolean save;
	/**
	 * Konstruktor
	 * @param gui referencja do g³ównego okna programu
	 * @param x pozycja x okienka na ekranie
	 * @param y pozycja y okienka na ekranie
	 */
	public SavePopup(GUI gui, int x, int y, boolean save) {
		setAlwaysOnTop(true);
		setUndecorated(true);
		setVisible(true);
		this.gui=gui;
		this.save=save;
		setResizable(false);
		setBounds(x/2-150, y/2-50, 300, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(SystemColor.inactiveCaption, 3, true));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.setVisible(false);
		contentPane.add(verticalBox, BorderLayout.CENTER);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		verticalBox.add(horizontalStrut_1);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		verticalBox.add(horizontalStrut);
		
		JLabel lblWpiszSwojeImie = new JLabel("Wpisz nazw\u0119 zapisu:");
		contentPane.add(lblWpiszSwojeImie, BorderLayout.NORTH);
		lblWpiszSwojeImie.setHorizontalAlignment(SwingConstants.CENTER);
		lblWpiszSwojeImie.setHorizontalTextPosition(SwingConstants.CENTER);
		lblWpiszSwojeImie.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWpiszSwojeImie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(nameField, BorderLayout.CENTER);
		nameField.setLocation(new Point(10, 10));
		nameField.setMargin(new Insets(10, 10, 10, 10));
		nameField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setSize(new Dimension(320, 25));
		panel.setMinimumSize(new Dimension(320, 25));
		panel.setMaximumSize(new Dimension(320, 25));
		panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		panel.setBounds(new Rectangle(0, 0, 320, 25));
		panel.setAlignmentY(0.0f);
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JButton btnPotwierd = new JButton("Potwierd\u017A");
		btnPotwierd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		btnPotwierd.setMinimumSize(new Dimension(100, 25));
		btnPotwierd.setMaximumSize(new Dimension(100, 25));
		btnPotwierd.setDoubleBuffered(true);
		panel.add(btnPotwierd);
		
		JButton btnAnuluj = new JButton("Anuluj");
		btnAnuluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAnuluj.setDoubleBuffered(true);
		btnAnuluj.setAlignmentX(0.5f);
		panel.add(btnAnuluj);
	}
	protected void save() {
		if(nameField.getText().length()==0)
			return;
		int l=nameField.getText().split(" ").length;
		if(l!=1){
			nameField.setText(MSG_WRONG_NAME);
			return;			
		}
		if(save){
			try {
				gui.save(nameField.getText());
			} catch (FileNotFoundException e) {
				nameField.setText(MSG_NO_FILE);
				return;
			} catch (ClassNotFoundException e) {
				nameField.setText(MSG_ERROR);
				return;
			} catch (IOException e) {
				nameField.setText(MSG_ERROR);
				return;
			}
		}
		else{
			try {
				gui.load(nameField.getText());
			} catch (FileNotFoundException e) {
				nameField.setText(MSG_NO_FILE);
				return;
			} catch (ClassNotFoundException e) {
				nameField.setText(MSG_ERROR);
				return;
			} catch (IOException e) {
				nameField.setText(MSG_ERROR);
				return;
			}
		}
		dispose();
		
	}

}
