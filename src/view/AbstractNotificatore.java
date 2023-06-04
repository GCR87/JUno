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
 * CLASSE ASTRATTA ABSTRACTNOTIFICATORE
 * 
 * Classe astratta che definisce attributi e metodi di base
 * che devono essere implementati da qualsiasi classe concreta
 * di tipo Notificatore.
 * 
 * @author giorg
 *
 */


public abstract class AbstractNotificatore {

	/** ATTRIBUTI */
	// MVC Model
	protected JunoModel junoModel;
	protected JunoController junoController;
	
	/** COSTRUTTORE */
	public AbstractNotificatore(JunoModel junoModel, JunoController junoController) {
		this.junoModel=junoModel;
		this.junoController=junoController;}
	
	
	/** METODI */
	public abstract String sceltaMazziere();
	public abstract String giocataMazziere();
	public abstract String giocataGiocatore();
	public abstract String chiamataUno();
	public abstract String vittoriaRound();
	public abstract String vittoriaPartita();
	
	public abstract String creaNotifica();

	
}
