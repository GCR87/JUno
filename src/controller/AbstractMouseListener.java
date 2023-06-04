package controller;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import view.*;
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


/** 
 * ABSTRACTMOUSELISTENER
 * 
 * Classe astratta che implementa l'interfaccia MouseListener e definisce
 * attributi e metodi di base che devono essere ereditati da tutte le classi
 * concrete di tipo MouseListener e che il Controller ha il compito di assegnare
 * ai diversi comandi della View.
 * 
 * @author giorg
 *
 */


public abstract class AbstractMouseListener implements MouseListener {
	
	/** ATTRIBUTI */
	protected JunoModel junoModel;
	protected JunoController junoController;
	protected JunoView junoView;
	protected boolean active;
	protected boolean run;
	
	/** COSTRUTTORE */
	public AbstractMouseListener(JunoController junoController) {
		this.junoController=junoController;
		this.junoView=junoController.view;
		this.junoModel=junoController.model;
		this.active=false;
		this.run=false;}
	
	/** METODI */
	//Setters and Getters
	public boolean isActive() {return active;}
	public void setActive(boolean active) {this.active = active;}
	public boolean isRun() {return run;}
	public void setRun(boolean run) {this.run=run;}
	

}
