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


/** CLASSE DECKPANEL
 * 
 * La classe DeckPanel estende la superclasse JPanel e contiene un riferimento 
 * alla corrispondente istanza di classe Mazzo che deve rappresentare graficamente.
 * La classe DeckPanel e' costruita attorno alla classe Mazzo seguendo il 
 * DECORATOR PATTERN.
 * 
 * @author giorg
 *
 */

public class DeckPanel extends JPanel {
	
	/** ATTRIBUTI */
	private Mazzo deck;
	private JPanel pescaPanel,scartoPanel;
	private TitledBorder mazzoBorder, pescaBorder, scartoBorder;
	
	/** COSTRUTTORE */
	public DeckPanel(Mazzo deck) {
		this.deck=deck;
		this.pescaPanel=new JPanel();
		this.pescaPanel.setMinimumSize(new Dimension(70,100));
		this.pescaPanel.setMaximumSize(new Dimension(70,100));
		this.pescaPanel.setPreferredSize(new Dimension(70,100));
		this.pescaPanel.setLayout(new OverlayLayout(this.pescaPanel));
		this.scartoPanel=new JPanel();
		this.scartoPanel.setMinimumSize(new Dimension(70,100));
		this.scartoPanel.setMaximumSize(new Dimension(70,100));
		this.scartoPanel.setPreferredSize(new Dimension(70,100));
		this.scartoPanel.setLayout(new OverlayLayout(this.scartoPanel));
		this.setLayout(new GridLayout(1,2));
		this.setPescaPanel();
		this.setScartoPanel();
		this.add(pescaPanel);
		this.add(scartoPanel);
		this.setBorders();}
	
	/** METODI */
	
	// Setters and Getters
	public void setDeck(Mazzo deck) {this.deck=deck;}
	public Mazzo getDeck() {return this.deck;}
	public JPanel getPescaPanel() {return this.pescaPanel;}
	public JPanel getScartoPanel() {return this.scartoPanel;}

	public void setPescaPanel() {
		this.pescaPanel.removeAll();
		for (Carta carta: this.deck.getPesca().getCarte()) {
			this.pescaPanel.add(new CartaPanel(carta));}}
	
	public void setScartoPanel() {
		this.scartoPanel.removeAll();
		for (Carta carta: this.deck.getScarto().getCarte()) {
			this.scartoPanel.add(new CartaPanel(carta),0);}
		if (this.scartoPanel.getComponents().length>0){
			for (Component comp: this.scartoPanel.getComponents()) {
				comp.setVisible(false);}
			this.scartoPanel.getComponents()[0].setVisible(true);}}

	// Set Borders
	private void setBorders() {
		// Mazzo Border
		this.mazzoBorder=BorderFactory.createTitledBorder("Mazzo");
		this.mazzoBorder.setTitleJustification(TitledBorder.CENTER);
	    this.setBorder(this.mazzoBorder);
		// Pesca Border
	    this.pescaBorder=BorderFactory.createTitledBorder("Pesca");
	    this.pescaBorder.setTitleJustification(TitledBorder.CENTER);
	    this.pescaPanel.setBorder(pescaBorder);
	    // Scarto Border
		this.scartoBorder=BorderFactory.createTitledBorder("Scarto");
	    this.scartoBorder.setTitleJustification(TitledBorder.CENTER);
	    this.scartoPanel.setBorder(scartoBorder);}
	
}
