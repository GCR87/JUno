package view;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import controller.*;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import javax.swing.*;
import javax.swing.border.Border;
//File I/O Packages
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/** CLASSE CARTAPANEL
 * 
 * La classe CartaPanel estende la superclasse JPanel e contiene un riferimento 
 * alla corrispondente istanza di classe Carta che deve rappresentare graficamente.
 * La classe CartaPanel e' costruita attorno alla classe Carta seguendo il 
 * DECORATOR PATTERN.
 * 
 * Tecniche Specifiche:
 * - DECORATOR Design Pattern
 * 
 * @author giorg
 *
 */


public class CartaPanel extends JPanel {
	
	/** ATTRIBUTI */
	private Carta carta;
	private final String FOLDERPATH="images/";
	private String faceUpFileName;
	private String faceDownFileName;
	private JLabel frontPicLabel;
	private JLabel backPicLabel;
	private BufferedImage frontImage;
	private BufferedImage backImage;
	private boolean cliccabile;
	private int width, height;
	
	
	/** COSTRUTTORE */
	
	//Overloaded 01
	public CartaPanel(Carta carta) {
		
		//1. Richiama super-costruttore di JPanel con GridLayout
		super(new GridLayout(1,1));
		
		//2. Incapsula l'istanza di Carta corrispondente
		this.carta=carta;
		
		//3. Assegna Dimensioni
		this.setWidth(50);
		this.setHeight(70);
		
		//4. Crea PictureLabels per Fronte e Retro della carta
		this.faceUpFileName=this.FOLDERPATH + this.carta.getSimbolo().toString() + " " + 
							this.carta.getColore().toString() + ".png";
		this.faceDownFileName=this.FOLDERPATH + "RETRO.png";
		this.frontPicLabel=PictureLabel.create(this.faceUpFileName, this.width,this.height);
		this.backPicLabel=PictureLabel.create(this.faceDownFileName,this.width, this.height);
		
		//5. Inizializza parametro cliccabile 
		this.cliccabile=false;
		
		//6. Assegna PicureLabel corrispondente a orientamento carta (FaceUp o FaceDown)
		this.setImage();}
	
	//Overloaded 02
	public CartaPanel(Carta carta, boolean cliccabile) {
		//1. Chiama Costruttore Overloaded 01
		this(carta);
		this.cliccabile=cliccabile;
		//2. Se la carta e' cliccabile, cambia il cursore del mouse
        if (this.cliccabile) {
        	this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}}

	//Metodo di utilita per assegnamento immagine a CartaPanel
	private void setImage() {
		this.repaint();
		if (this.carta.getFaceUp()) this.add(this.frontPicLabel);
		else this.add(this.backPicLabel);}
	

	// Setters and Getters
	public boolean isCliccabile() {return cliccabile;}
	public void setCliccabile(boolean cliccabile) {
		this.cliccabile = cliccabile;
		if (this.cliccabile) {
        	this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));}}
	public int getWidth() {return width;}
	public void setWidth(int width) {this.width = width;}
	public int getHeight() {return height;}
	public void setHeight(int height) {this.height = height;}
	public Carta getCarta() {return carta;}
	public void setCarta(Carta carta) {this.carta = carta;}
	

}




