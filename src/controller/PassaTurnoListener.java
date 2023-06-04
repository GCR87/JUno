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
 * PASSATURNOLISTENER
 * 
 * Sottoclasse concreta della classe AbstractActionListener e che implementa
 * l'interfaccia ActionListener. 
 * La classe PassaTurnoListener e' definita in modo specifico per definire le azioni
 * da lanciare una volta che l'utente ha premuto il bottone PassaTurno nel JFrame
 * ViewGame.
 * Il metodo .actionPerformed() aggiorna l'ordine dei giocatori della partita passando
 * il turno al giocatore successivo.
 * 
 * @author giorg
 *
 */

public class PassaTurnoListener extends AbstractActionListener {

	/** ATTRIBUTI */
	// Tutti gli attributi sono ereditati dalla classe astratta AbstractActionListener
	
	/** COSTRUTTORE */
	public PassaTurnoListener(JunoController controller) {
		super(controller);}
	
	/** METODI */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Esegui Azioni solo se il PassaTurnoListener e' attivo (ovvero, se il giocatore del turno e' l'utente)
		if (this.active) {
				
				//1. Lancia Effetto Sonoro
				this.junoController.getSoundsManager().play(Sound.CLICKBUTTON.getPath());
				
				/*2. Disattiva l'actionlistener e il bottone corrispondente per impedire che l'utente 
				 *   lo inneschi di nuovo nello stesso turno */
				this.setActive(false);
				((JButton)e.getSource()).setEnabled(false);
				
				//3. Aggiorna ordine giocatori e giocatore turno
				this.junoModel.getListaGiocatori().aggiorna();
				this.junoModel.setGiocatoreTurno(this.junoModel.getListaGiocatori().getGiocatoreTurno());
				this.junoModel.setGiocatoreSuccessivo(this.junoModel.getListaGiocatori().getGiocatoreSuccessivo());
				//4. Resetta i parametri carta scelta/giocata del giocatore del turno
				this.junoModel.getGiocatoreTurno().setCartaScelta(null);
				this.junoModel.getGiocatoreTurno().setCartaGiocata(null);
				
				//5. Informa che la giocata e' conclusa
				this.junoModel.setGiocataOver(true);
				
				//6. Notifica Observers del Model
				this.junoModel.notifyObservers();
				
				//7. Aggiorna dati giocatori per il passaggio al prossimo turno
				this.junoModel.getListaGiocatori().aggiornaOrdine(junoModel.getListaGiocatori().getGiocatori().get(1));
				this.junoModel.getListaGiocatori().aggiorna();
				this.junoModel.setGiocatoreTurno(this.junoModel.getListaGiocatori().getGiocatoreTurno());
				this.junoModel.setGiocatoreSuccessivo(this.junoModel.getListaGiocatori().getGiocatoreSuccessivo());
				this.junoController.model.setGiocataOver(true);
				this.junoController.model.getGiocatoreTurno().setCalledUno(UnoCall.NOTDONE);
				
				//8. Aggiorna il parametro di controllo
				this.run=true;
				
				//9. Riattiva il Timer per continuare la partita
				this.junoController.getTurnoTimerListener().restart();}}

}
