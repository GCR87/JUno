package controller;


/** IMPORT PACKAGES */

// MVC Pattern Packages
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
 * CARDSMOUSELISTENER
 * 
 * Sottoclasse concreta della classe AbstractMouseListener e che implementa
 * l'interfaccia MouseListener. 
 * La classe CardsMouseListener e' definita in modo specifico per definire le azioni
 * corrispondenti all'interazione del mouse con i JPanels che modellano le carte da gioco
 * nella View.
 * Un'istanza di CardsMouseListener viene assegnata a ciascuna istanza di classe CartaPanel.
 * 
 * Tecniche Specifiche:
 * - CLASSI ANONIME
 * - AUDIOMANAGER
 * - MOUSELISTENER
 * 
 * @author giorg
 *
 */

public class CardsMouseListener extends AbstractMouseListener {
	
	/** ATTRIBUTI */
	// Tutti gli attributi sono ereditati dalla classe astratta AbstractActionListener
	
	/** COSTRUTTORE */
	public CardsMouseListener(JunoController junoController) {
		super(junoController);}

	
	/** METODI */
	
	/** MouseListener */
	
	// MouseClicked
	@Override
	public void mouseClicked(MouseEvent e) {
		//Esegui Azioni solo se il CardsMouseListener e' attivo (ovvero, se il giocatore del turno e' l'utente)
		if (this.active) {
			//1. Lancia effetto sonoro
			this.junoController.getSoundsManager().play(Sound.SELECTCARD.getPath());
			
			//2. Estrai carta selezionata da corrispondente CartaPanel  
			Carta cartaScelta=((CartaPanel)e.getSource()).getCarta();
			this.junoView.getViewGame().getJpHuman().getGiocatore().setCartaScelta(cartaScelta);
			
			//3. Visualizza JFrame per scelta colore se si tratta di una carta JOLLY
			if (cartaScelta.getSimbolo().toString().contains("JOLLY")) {
				/* Ridefinisci il metodo .scegliColore del comportamento carta Jolly 
				   tramite Classe Anonima */
				cartaScelta.setCompCarta(new CompJolly(this.junoModel) {
					@Override
					public Colore scegliColore() {
						Colore coloreScelto = this.junoModel.getColoreTurno();
						return coloreScelto;}});
				/*Disattiva ViewGame per consentire a utente di usare solo il JFrame
				  ViewColorChoose per la scelta del colore da giocare */
				this.junoView.getViewGame().setEnabled(false);
				/*Crea e visualizza la JFrame ViewColorChoose */
				this.junoView.creaViewColorChoose();
				this.junoController.assegnaColorButtonListeners();
				//Esci dalla funzione per consentire all'utente di selezionare il colore
				return;}
			
			//4. Gioca il turno usando la carta scelta
			try {this.junoController.giocaTurno();} 
			catch (MazzoEsauritoException e1) {
				 this.junoModel.getMazzo().reset();}
			//5. Disattiva CardsMouseListener
			this.setActive(false);
			//6. Riattiva il Timer per continuare la partita
			this.junoController.getTurnoTimerListener().restart();}}

	// MousePressed
	@Override
	public void mousePressed(MouseEvent e) {}
	
	// MouseReleased
	@Override
	public void mouseReleased(MouseEvent e) {}

	// MouseEntered
	@Override
	public void mouseEntered(MouseEvent e) {
		//Esegui Azioni solo se il CardsMouseListener e' attivo (ovvero, se il giocatore del turno e' l'utente)
		if (this.active) {
			//1. Attiva CartaPanel cliccabile per cambiare cursore mouse
			((CartaPanel)e.getSource()).setCliccabile(true);
			//2. Sposta CartaPanel per renderla piu' visibile
			((CartaPanel)e.getSource()).setLocation((int)((CartaPanel)e.getSource()).getLocation().getX()+15,
					(int)((CartaPanel)e.getSource()).getLocation().getY());
			//3. Lancia effetto sonoro
			this.junoController.getSoundsManager().play(Sound.HANDLECARD.getPath());}}

	// MouseExited
	@Override
	public void mouseExited(MouseEvent e) {
		//Esegui Azioni solo se il CardsMouseListener e' attivo (ovvero, se il giocatore del turno e' l'utente)
		if (this.active) {
			//1. Disattiva CartaPanel cliccabile per tornare al cursore mouse di default
			((CartaPanel)e.getSource()).setCliccabile(false);
			//2. Riporta CartaPanel nella posizione originaria
			((CartaPanel)e.getSource()).setLocation((int)((CartaPanel)e.getSource()).getLocation().getX()-15,
				(int)((CartaPanel)e.getSource()).getLocation().getY());}}

	

}

