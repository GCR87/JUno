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
 * PESCACARTALISTENER
 * 
 * Sottoclasse concreta della classe AbstractActionListener e che implementa
 * l'interfaccia ActionListener. 
 * La classe PescaCartaListener e' definita in modo specifico per definire le azioni
 * da lanciare una volta che l'utente ha premuto il bottone PescaCarta nel JFrame
 * ViewGame.
 * Il metodo .actionPerformed() aggiunge una carta nella mano del giocatore utente
 * ed aggiorna gli observers del Model.
 * 
 * Tecniche Specifiche
 * - OBSERVER Design Pattern
 * 
 * @author giorg
 *
 */


public class PescaCartaListener extends AbstractActionListener {

	/** ATTRIBUTI */
	// Tutti gli attributi sono ereditati dalla classe astratta AbstractActionListener
	
	/** COSTRUTTORE */
	public PescaCartaListener(JunoController junoController) {
		super(junoController);}
	
	/** METODI */
	
	//ActionPerformed
	@Override
	public void actionPerformed(ActionEvent e) {
		//Esegui Azioni solo se il PassaTurnoListener e' attivo (ovvero, se il giocatore del turno e' l'utente)
		if (this.active) {
			
			try{ 	
				//1. Lancia Effetto Sonoro
				 this.junoController.getSoundsManager().play(Sound.CLICKBUTTON.getPath());
				
				 /*2. Disattiva l'actionlistener e il botton corrispondente per impedire che 
				  *   l'utente lo inneschi di nuovo nello stesso turno */
				 this.setActive(false);
				 ((JButton)e.getSource()).setEnabled(false);
				 
				 //3. Fai pescare una carta al giocatore utente
				 this.junoView.getViewGame().getJpHuman().getGiocatore().pesca(1, this.junoModel.getMazzoPesca());
				 
				 //4. Informa che la giocata non e' ancora conclusa
				 this.junoModel.setGiocataOver(false);
				 
				 //5. Notifica gli observers del Model
				 this.junoController.model.notifyObservers();
				 
				 //6. Riassegna CardsListeners alle nuove CartaPanels inserite nella mano del 
				 //   Giocatore Human
				 this.junoController.assegnaCardsListeners();
				 
				 //7. Aggiorna parametro di controllo
				 this.run=true;}
			
			catch (MazzoEsauritoException e1) {
				//Resetta il Mazzo
				this.junoModel.getMazzo().reset();
				//5. Aggiorna parametro di controllo
				this.run=false;}}}
	
}