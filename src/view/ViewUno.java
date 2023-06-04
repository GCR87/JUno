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
 * CLASSE ASTRATTA VIEWUNO
 * 
 * Classe astratta che implementa le interfacce ViewUnoInterface e JunoObserver e da 
 * cui devono ereditare tutte le classi JFrame usate nel gioco.
 * Tutte le views sono observers del Model.
 * 
 * @author giorg
 *
 */

public abstract class ViewUno extends JFrame implements ViewUnoInterface, JunoObserver {
	
	/** ATTRIBUTI */
	//Riferimenti a Model e Controller
	protected JunoModel junoModel;
	protected JunoController junoController;
	
	/** COSTRUTTORE */
	public ViewUno (String viewName, JunoModel junoModel, JunoController junoController) {
		// 1. RICHIAMO SUPER-COSTRUTTORE con passaggio Titolo Finestra
		super(viewName);
		// 2. ASSEGNA RIFERIMENTI A MODEL E CONTROLLER
		this.junoModel=junoModel;
		this.junoController=junoController;}
	
	/** METODI */
	// Update() - OBSERVER Pattern
	@Override
	public void update() {}

}
