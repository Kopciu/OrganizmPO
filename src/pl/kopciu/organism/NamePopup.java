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
/**
 * Klasa okienka wpisywania imienia, wygenerowana w wiêkszoœci za pomoc¹ pluginu WindowBuilder
 * @author Kopciu
 *
 */
public class NamePopup extends JFrame {

	private static final long serialVersionUID = Constans.serialVersionUID;
	private JPanel contentPane;
	private GUI gui;
	/**
	 * Konstruktor
	 * @param gui referencja do g³ównego okna programu
	 * @param x pozycja x okienka na ekranie
	 * @param y pozycja y okienka na ekranie
	 */
	public NamePopup(GUI gui, int x, int y) {
		setUndecorated(true);
		setVisible(true);
		this.gui=gui;
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
		
		JLabel lblWpiszSwojeImie = new JLabel("Wpisz swoje imie:");
		contentPane.add(lblWpiszSwojeImie, BorderLayout.NORTH);
		lblWpiszSwojeImie.setHorizontalAlignment(SwingConstants.CENTER);
		lblWpiszSwojeImie.setHorizontalTextPosition(SwingConstants.CENTER);
		lblWpiszSwojeImie.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWpiszSwojeImie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		final JTextField nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameField.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(nameField, BorderLayout.CENTER);
		nameField.setLocation(new Point(10, 10));
		nameField.setMargin(new Insets(10, 10, 10, 10));
		nameField.setColumns(10);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAndClose(nameField.getText());
			}
		});
		contentPane.add(btnOk, BorderLayout.SOUTH);
		btnOk.setHorizontalTextPosition(SwingConstants.CENTER);
		btnOk.setAlignmentX(Component.CENTER_ALIGNMENT);
	}
	private void saveAndClose(String txt){
		if(txt.length()!=0){
			gui.saveResult(txt);
			this.dispose();
		}
	}

}
