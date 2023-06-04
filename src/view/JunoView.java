package view;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import controller.*;
// Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
// File I/O Packages
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;



/**
 * CLASSE JUNOVIEW
 * 
 * Classe concreta e classe principale del package view.
 * La classe contiene tutte le classi java swing per la GUI del gioco.
 * La View e' aggiornata in base ai cambiamenti del Model tramite l'OBSERVER
 * pattern e comunica indirettamente con il model tramite il Controller.
 * 
 * Tecniche specifiche:
 * - OBSERVER Design Pattern
 * - MODEL-VIEW-CONTROLLER Design Pattern
 * 
 * @author giorg
 *
 */


public class JunoView  {
	
	/** ATTRIBUTI */
	// Riferimenti a Model e Controller 
	protected JunoModel model;
	protected JunoController controller;
	
	// Main Frames of the View
	protected ViewMenu viewMenu;
	protected ViewGame viewGame;
	protected ViewColorChoose viewColorChoose;
	protected Notificatore notificatore;
	
	
	/** COSTRUTTORE */
	public JunoView(JunoModel model, JunoController controller) {
		// Registrazione riferimenti a Model e Controller 
		this.controller=controller;
		this.model=model;}
	
	
	/** METODI */
	
	// CREAZIONE JFRAMES
	public void creaNotificatore() {
		this.notificatore=new Notificatore(this.model, this.controller);}
	
	public void creaViewMenu() {
		this.viewMenu=new ViewMenu(this.model,this.controller);
		// Registrazione View come observer di Model		
		this.model.registerObserver(this.viewMenu);}
	
	public void creaViewGame() {
		this.viewGame=new ViewGame(this.model, this.controller);
		// Registrazione View come observer di Model		
		this.model.registerObserver(this.viewGame);}
	
	public void creaViewColorChoose() {
		this.viewColorChoose=new ViewColorChoose(this.model,this.controller);}
	
	//SETTERS and GETTERS
	public ViewMenu getViewMenu() {return viewMenu;}
	public void setViewMenu(ViewMenu viewMenu) {this.viewMenu = viewMenu;}
	public ViewGame getViewGame() {return viewGame;}
	public void setViewGame(ViewGame viewGame) {this.viewGame = viewGame;}
	public ViewColorChoose getViewColorChoose() {return viewColorChoose;}
	public void setViewColorChoose(ViewColorChoose viewColorChoose) {this.viewColorChoose = viewColorChoose;}
	
	
	
}
