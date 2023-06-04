package controller;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import view.*;
// Java Reading Files
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JDialog;
import java.io.File;
import java.util.Hashtable;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
//File I/O Packages
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/** 
 * CARICAPROFILOLISTENER
 * 
 * Sottoclasse concreta della classe AbstractActionListener e che implementa
 * l'interfaccia ActionListener. 
 * La classe CaricaProfiloListener e' definita in modo specifico per definire le azioni
 * da lanciare una volta che l'utente ha premuto il bottone CaricaProfilo nella JFrame
 * ViewMenu.
 * Il metodo .actionPerformed() crea una nuova istanza della classe GestoreProfilo, la usa
 * per caricare un profilo serializzato (*.ser) e ne salva il nome nel Textfield Nickname.
 * 
 * @author giorg
 *
 */

public class CaricaProfiloListener extends AbstractActionListener {
	
	/** ATTRIBUTI */
	// Tutti gli attributi sono ereditati dalla classe astratta AbstractActionListener
	
	/** COSTRUTTORE */
	public CaricaProfiloListener(JunoController controller) {
		super(controller);}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
		//1. Lancia Effetto sonoro
		this.junoController.getSoundsManager().play(Sound.CLICKBUTTON.getPath());
		//2. Crea GestoreProfilo
		this.junoController.setGestoreProfilo(new GestoreProfilo(this.junoController));
		//3. Carica profilo serializzato
		this.junoController.getGestoreProfilo().caricaProfilo();
		//4. Estrai nome profilo e assegnalo al corrispondente Textfield
		String nomeProfilo=this.junoController.getGestoreProfilo().getProfiloUtente().getNome();
		this.junoController.view.getViewMenu().getTfHuman().setText(nomeProfilo);
		//5. Disattiva il Textfield in modo da impedire all'utente di cancellare il nome profilo
		this.junoController.view.getViewMenu().getTfHuman().setEnabled(false);
		//6. Aggiorna il parametro di controllo
		this.run=true;}
		catch (Exception err) {
			this.run=false;
			return;}}
}
