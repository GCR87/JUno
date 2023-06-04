package view;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import controller.*;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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


/** CLASSE PLAYERPANEL
 * 
 * La classe PlayerPanel estende la superclasse JPanel e contiene un riferimento 
 * alla corrispondente istanza di classe Giocatore che deve rappresentare graficamente.
 * La classe PlayerPanel e' costruita attorno alla classe Giocatore seguendo il 
 * DECORATOR PATTERN.
 * 
 * Tecniche Specifiche:
 * - DECORATOR Design Pattern
 * 
 * @author giorg
 *
 */

public class PlayerPanel extends JPanel {
	
	/** ATTRIBUTI */
	private Giocatore giocatore;
	private HandPanel handPanel;
	private TitledBorder nameBorder;
	
	
	/** COSTRUTTORE */
	public PlayerPanel(Giocatore giocatore) {
		this.giocatore=giocatore;
		this.handPanel=new HandPanel();
		this.add(this.handPanel);
		this.setLayout(new GridLayout(1,1));
		this.setBorder();
		this.setMinimumSize(new Dimension(300,400));
		this.setMaximumSize(new Dimension(300,400));
		this.setPreferredSize(new Dimension(300,400));
	}
		
	/** METODI */
	
	// Setters and Getters
	
	public void setGiocatore(Giocatore giocatore) {
		this.giocatore=giocatore;
		this.setBorder();}
	
	public Giocatore getGiocatore() {return this.giocatore;}
	
	private void setBorder() {
		this.nameBorder=BorderFactory.createTitledBorder(this.giocatore.getNome());
	    this.nameBorder.setTitleJustification(TitledBorder.CENTER);
	    this.setBorder(nameBorder);}
	
	public void setHandPanel() {	
		this.handPanel.clearHand();
		if (this.giocatore.getClass().getSimpleName().equals("GiocatoreHuman")) {
			for (Carta carta:this.giocatore.getMano()) {
				carta.setFaceUp(true);
				CartaPanel cartaPanel=new CartaPanel(carta);
				this.handPanel.add(cartaPanel);}}
		else {
			for (Carta carta:this.giocatore.getMano()) {
				this.handPanel.add(new CartaPanel(carta));}}
		}
		
	public HandPanel getHandPanel() {return this.handPanel;}	
	
}
