package controller;


/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
// Java Util Packages
import java.util.Optional;
import java.util.Timer;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer.*;


/** 
 * TURNOTIMERLISTENER
 * 
 * Sottoclasse concreta della classe AbstractTimerListener e che implementa
 * l'interfaccia ActionListener. 
 * La classe TurnoTimerListener e' utilizzata per la gestione dei turni del gioco.
 * Il metodo actionPerformed() viene eseguito ogni volta che il timer contenuto 
 * all'interno della classe TurnoTimerListener viene eseguito (chiamata metodo .start()
 * oppure .restart()) e con un delay pari a quello impostato.
 * 
 * Tecniche Specifiche
 * - STREAMS
 * - SERIALIZZAZIONE
 * 
 * @author giorg
 *
 */

public class TurnoTimerListener extends AbstractTimerListener {

	/** ATTRIBUTI */
	// Tutti gli attributi sono ereditati dalla classe astratta AbstractTimerListener	
	
	/** COSTRUTTORE */
	public TurnoTimerListener(JunoController controller) {
		super(controller, 4000);}

	/** METODI */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Se la partita non e' ancora finita...
		if(!this.junoController.model.isGameOver()) {
			// Se il round non e' ancora concluso...
			if (!this.junoController.model.isRoundOver()) {
				
				this.junoController.model.setGiocataOver(false);
				
				/* *** FASE MAZZIERE *** */
				if (this.junoController.model.getFasePartita().toString().equals("MAZZIERE")) {
					try{
						//1. Gioca fase SCELTA MAZZIERE
						this.junoController.setMazziere();
						//2. Rilancia il Timer
						this.timer.restart();
						return;} 
					catch (MazzoEsauritoException e1) {
						//Gestisci l'eccezione eseguendo il corrispondente metodo Handler
						this.junoController.getMazzoEsauritoHandler().execute();}}
				
				/* *** FASE PREPARATIVI *** */
				if (this.junoController.model.getFasePartita().toString().equals("PREPARATIVI")) {
					try {
						//1. Gioca fase PREPARATIVI
						this.junoController.preparativi();
						//2. Rilancia il Timer
						this.timer.restart();
						return;
					} catch (MazzoEsauritoException e1) {
						//Gestisci l'eccezione eseguendo il corrispondente metodo Handler
						this.junoController.getMazzoEsauritoHandler().execute();}}
				
				/* *** FASE PARTITA *** */
				if (this.junoController.model.getFasePartita().toString().equals("PARTITA")) {
					
					//* Giocatore AI */
					if (!this.junoController.model.getGiocatoreSuccessivo().getClass().getSimpleName().equals("GiocatoreHuman")) {
						try {//1. Reset Listeners e Componenti
							 this.junoController.assegnaCardsListeners();
							 this.junoController.disattivaComponenti();
							 this.junoController.resetButtonListeners();
							 //2. Gioca il turno
							 this.junoController.giocaTurno();
							 //3. Rilancia il Timer
							 this.timer.restart();} 
						catch (MazzoEsauritoException e1) {
						this.junoController.getMazzoEsauritoHandler().execute();}}
					
					//* Giocatore HUMAN */
					else {
						
						//1. Reset Listeners e Componenti
						this.junoController.assegnaCardsListeners();
						this.junoController.attivaComponenti();
						this.junoController.getCardsMouseListener().setActive(true);
						this.junoController.getPescaCartaListener().setActive(true);
						this.junoController.getPassaTurnoListener().setActive(true);
						this.junoController.getUnoListener().setActive(true);
						
						//2. Aggiorna parametri RoundOver e GameOver
						this.junoController.model.setRoundOver(this.junoController.model.getListaGiocatori().getGiocatori().stream()
								.anyMatch((gc)->gc.getMano().isEmpty()));
						this.junoController.model.setGameOver(this.junoController.model.getListaGiocatori().getGiocatori().stream()
								.anyMatch((gc)->gc.getPunteggio()>=500));
						
						//3. Ferma il Timer per consentire all'utente di interagire con la UI
						this.timer.stop();}}}
			
				else {
				
						/* *** FINE ROUND *** */
					
					    //1. Individuazione del vincitore del round - tramite STREAMS
						this.junoController.model.setRoundWinner(Optional.of(this.junoController.model.getListaGiocatori().getGiocatori().stream()
								// Filtra i giocatori che sono rimasti senza carte...
								.filter((gc)->(gc.getMano().isEmpty()))
								// Prendine solo uno...
								.limit(1)
								// Converti lo Stream in una Lista e prendi il primo elemento...
								.toList().get(0))
								// Se non c'e' un vincitore, ritorna NULL...
								.orElse(null));
						
						//2. Stampa vincitore in Console
						System.out.println("\nThe Round Winner is: "+ this.junoController.model.getRoundWinner().getNome());
						
						//3. Aggiorna punteggio giocatori
						this.junoController.model.aggiornaPunteggio(this.junoController.model.getRoundWinner());
						
						//4. Restituisci le carte dei giocatori al mazzo
						this.junoController.model.getListaGiocatori().getGiocatori().stream()
							.forEach((gc)->gc.restituisciMano(this.junoController.model.getMazzoScarto()));
					
						//5. Resetta il Mazzo
						this.junoController.model.getMazzo().reset();
						
						//6. Incrementa i Round giocati di un'unita'
						this.junoController.model.setRoundGiocati(this.junoController.model.getRoundGiocati()+1);
					
						//7. Aggiorna parametri RoundOver e GameOver
						this.junoController.model.setRoundOver(false);						
						this.junoController.model.setGameOver(this.junoController.model.getListaGiocatori().getGiocatori().stream()
								.anyMatch((gc)->gc.getPunteggio()>=500));
	
						//8. Notifica OBSERVERS
						this.junoController.model.notifyObservers();
						
						//9. Reimposta la fase della partita a quella iniziale per un nuovo Round
						this.junoController.model.setFasePartita(FasePartita.MAZZIERE);
						
						//10. Riattiva il Timer per eseguire il turno successivo
						this.timer.restart();}
			}
		
		else {
			
			/* *** FINE PARTITA *** */
			
			//1. Individuazione del vincitore della partita - tramite STREAMS
			this.junoController.model.setGameWinner(Optional.of(this.junoController.model.getListaGiocatori().getGiocatori().stream()
					// Filtra i giocatori che hanno un punteggio uguale o superiore a 500...
					.filter((gc)->(gc.getPunteggio()>=500))
					// Prendine solo uno...
					.limit(1)
					// Converti lo Stream in una Lista e prendi il primo elemento...
					.toList().get(0))
					// Se non c'e' un vincitore, ritorna NULL...
					.orElse(null));
			
			//2. SERIALIZZA IL PROFILO UTENTE
			this.junoController.getGestoreProfilo().aggiornaProfilo();
			
			//3. Ferma il timer per concludere la partita
			this.timer.stop();}
		}
}
