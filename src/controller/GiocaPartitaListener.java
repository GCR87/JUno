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
//File I/O Packages
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/** 
 * GIOCAPARTITALISTENER
 * 
 * Sottoclasse concreta della classe AbstractActionListener e che implementa
 * l'interfaccia ActionListener. 
 * La classe GiocaPartitaListener e' definita in modo specifico per definire le azioni
 * da lanciare una volta che l'utente ha premuto il bottone GiocaPartita nel JFrame
 * ViewMenu.
 * Il metodo .actionPerformed() assegna gli input al Model e lancia il gioco.
 * 
 * @author giorg
 *
 */

public class GiocaPartitaListener extends AbstractActionListener {
	
	/** ATTRIBUTI */
	// Tutti gli attributi sono ereditati dalla classe astratta AbstractActionListener
	
	/** COSTRUTTORE */
	public GiocaPartitaListener(JunoController controller) {
		super(controller);}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//1. Lancia Effetto sonoro			
		this.junoController.getSoundsManager().play(Sound.CLICKBUTTON.getPath());
	
		//2. Controlla Input Nickname TextField
		try {
			// Se il Textfield non e' vuoto...assegna nome a GiocatoreHuman...
			if(!this.junoView.getViewMenu().getTfHuman().getText().isBlank()) {
				this.junoModel.setNomeGiocatoreHuman(this.junoView.getViewMenu().getTfHuman().getText());}
			// ...altrimenti lancia un'eccezione...
			else { throw new UnacceptableInputException();}}
		catch (UnacceptableInputException err) {
			// Gestisci l'eccezione visualizzando un Error Message per l'utente.
			this.errorMsg();
			// Esci dalla funzione per consentire all'utente di interagire con la finestra
			return;}
		
		//3. Crea Nuovo Profilo Utente
		if (this.junoController.getGestoreProfilo()==null) {
			GestoreProfilo gp =new GestoreProfilo(this.junoController);
			gp.creaProfilo(this.junoModel.getNomeGiocatoreHuman());
			this.junoController.setGestoreProfilo(new GestoreProfilo(this.junoController));	}
	
		//3. Controlla Input RadioButton "Mute"
		if(this.junoView.getViewMenu().getRbMute().isSelected()) {
			//...se "mute" e' selezionato, disattiva il MusicManager...
			this.junoController.getMusicManager().setActive(false);}
	
		//4. Controlla Input Nomi Giocatori AI
		ArrayList<String>nomiGiocatoriAI=new ArrayList<>();
		nomiGiocatoriAI.add(this.junoView.getViewMenu().getCbAI01().getSelectedItem().toString());
		nomiGiocatoriAI.add(this.junoView.getViewMenu().getCbAI02().getSelectedItem().toString());
		nomiGiocatoriAI.add(this.junoView.getViewMenu().getCbAI03().getSelectedItem().toString());
		
		//5. Assegna nomi giocatori AI selezionati
		this.junoModel.setNomiGiocatoriAI(nomiGiocatoriAI);
		
		//6. Lancia colonna sonora selezionata
		String musicFileName=this.junoView.getViewMenu().getCbSoundtrack().getSelectedItem().toString();
		this.junoController.getMusicManager().play("music/"+ musicFileName +".wav");
		
		//7. Chiudi JFrame
		this.junoView.getViewMenu().setVisible(false);
		this.junoView.getViewMenu().dispose();
		
		//8. Aggiorna parametro di controllo
		this.setRun(true);
		
		//8. Incomincia la Partita
		this.junoController.iniziaPartita();
	}
	
	
	private void errorMsg() {
		//1. Lancia effetto sonoro
		this.junoController.getSoundsManager().play(Sound.ERROR.getPath());
		//2. Imposta Testo
		String infoMessage="Prima di iniziare la partita, e' necessario inserire il Nickname " +
						   "o caricare un Profilo Utente.";
		String titleBar="Dati inseriti non sufficienti";
		//3. Imposta Icona
		JOptionPane.showMessageDialog(null, infoMessage, "ATTENZIONE - " + titleBar, 
		JOptionPane.ERROR_MESSAGE);
	}
	
}
