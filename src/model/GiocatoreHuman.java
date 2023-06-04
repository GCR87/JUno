package model;

/** IMPORT PACKAGES */
import java.util.ArrayList;


/**
 * CLASSE CONCRETA GIOCATOREHUMAN
 * 
 * Classe che eredita da Giocatore ma definisce in modo specifico i metodi
 * che si differenziano dal comportamento del Giocatore AI.
 * @author giorg
 *
 */

public class GiocatoreHuman extends Giocatore {
	
	/** COSTRUTTORI */
	//Default
	public GiocatoreHuman() {super();};
	//Overloaded
	public GiocatoreHuman(String nome, int posizione) {
		super(nome, posizione);}
	public GiocatoreHuman(String nome, int posizione, int punteggio) {
		super (nome, posizione, punteggio);}

	// scegliCarta
	@Override
	public Carta scegliCarta(Simbolo simboloTurno, Colore coloreTurno) throws CartaNonConsentitaException {
		if ((this.cartaScelta.getSimbolo()==simboloTurno) || 
			(this.cartaScelta.getColore()==coloreTurno)||
			(this.cartaScelta.getSimbolo()==Simbolo.JOLLY)||
			(this.cartaScelta.getSimbolo()==Simbolo.JOLLYPESCAQUATTRO)) {
					return this.cartaScelta;}
		else {throw new CartaNonConsentitaException();}}

	// controllaUno
	@Override
	public void controllaUno() {
		this.setUno(false);
		//Chiamata UNO effettuata
		if ((this.getMano().size()==1) && (this.calledUno==UnoCall.DONE)) {
			this.setUno(true);}
		//Chiamata UNO mancata
		else if ((this.getMano().size()==1) && (this.calledUno==UnoCall.NOTDONE)) {
			this.setUno(false);
			this.calledUno=UnoCall.MISSED;}
		//Chiamata UNO non necessaria
		else if ((this.getMano().size()!=1) && (this.Uno==true)) {
			this.setUno(false);}}

	// callUno
	@Override
	public void callUno() {
		this.calledUno=UnoCall.DONE;}

}
