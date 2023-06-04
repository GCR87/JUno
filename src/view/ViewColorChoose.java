package view;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import controller.*;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
//File I/O Packages
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
//General
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * CLASSE VIEWCOLORCHOOSE
 * 
 * Classe concreta che estende ViewUno implementando l'interfaccia JunoObserver.
 * ViewColorChoose e' la view che consente all'utente di selezionare un colore
 * dopo aver giocato una carta JOLLY.
 * 
 * Tecniche specifiche:
 * - STREAMS
 * - OBSERVER Design Pattern
 * 
 * @author giorg
 *
 */

public class ViewColorChoose extends ViewUno implements ViewUnoInterface {
	
	/**ATTRIBUTI*/
	private JButton btnRosso, btnVerde, btnGiallo, btnBlu;
	
	/**COSTRUTTORE*/
	public ViewColorChoose(JunoModel model, JunoController controller) {
		
		/* 1. RICHIAMO SUPER-COSTRUTTORE CON PASSAGGIO TITOLO FINESTRA*/
		super("JUNO Game - Color Selection", model, controller);
		
		// 2. ASSEGNA PROPRIETA' al JFRAME */
		// 2.1 Dimensioni
		this.setSize(new Dimension(400,200));
		// 2.2 Posizione Relativa (input null per posizionare al centro dello schermo)
		this.setLocationRelativeTo(null);
		// 2.3 Ridimensionabilita' del JFrame
		this.setResizable(true);
		// 2.4 Stop Applicazione a chiusura del JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 2.5 Assegnazione Layout Manager
		this.getContentPane().setLayout(new GridLayout());
		// 2.6 Assegnazione nessuna icona
		ImageIcon img= new ImageIcon(this.IMAGES_FOLDERPATH+"FRAME_ICON.png");
		this.setIconImage(img.getImage());
		// 2.7 Creazione Componenti
		this.creaComponenti();
		
		/* 3. ATTIVA VISIBILITA del JFrame
		 * Senza questa istruzione il this/window non compare sullo schermo */ 
		this.setVisible(true);	
		
		this.junoController.getTurnoTimerListener().stop();
	}

	@Override
	public void creaComponenti() {
		
		// Crea Bordi
		Border lineBorder=BorderFactory.createLineBorder(Color.BLACK, 2);
		Border bevelBorder=BorderFactory.createBevelBorder(0);
		Border compoundBorder=BorderFactory.createCompoundBorder(lineBorder, bevelBorder);
		
		// Crea Bottoni e Assegna Colori e Bordi
		this.btnRosso=new JButton();
		this.btnRosso.setBackground(Color.RED);
		this.btnRosso.setBorder(compoundBorder);
		this.btnVerde=new JButton();
		this.btnVerde.setBackground(Color.GREEN);
		this.btnVerde.setBorder(compoundBorder);
		this.btnGiallo=new JButton();
		this.btnGiallo.setBackground(Color.YELLOW);
		this.btnGiallo.setBorder(compoundBorder);
		this.btnBlu=new JButton();
		this.btnBlu.setBackground(Color.BLUE);
		this.btnBlu.setBorder(compoundBorder);
		
		// Aggiungi Bottoni al Frame
		this.getContentPane().add(btnRosso);
		this.getContentPane().add(btnVerde);
		this.getContentPane().add(btnGiallo);
		this.getContentPane().add(btnBlu);
	}

	// SETTERS e GETTERS
	public JButton getBtnRosso() {return btnRosso;}
	public void setBtnRosso(JButton btnRosso) {this.btnRosso = btnRosso;}
	public JButton getBtnVerde() {return btnVerde;}
	public void setBtnVerde(JButton btnVerde) {this.btnVerde = btnVerde;}
	public JButton getBtnGiallo() {return btnGiallo;}
	public void setBtnGiallo(JButton btnGiallo) {this.btnGiallo = btnGiallo;}
	public JButton getBtnBlu() {return btnBlu;}
	public void setBtnBlu(JButton btnBlu) {this.btnBlu = btnBlu;}

	
	
	
}
