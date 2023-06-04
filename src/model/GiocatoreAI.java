package model;

/** IMPORT PACKAGES */
import java.util.ArrayList;
import java.util.Optional;


/**
 * CLASSE CONCRETA GIOCATOREAI
 * 
 * Classe che eredita da Giocatore ma definisce in modo specifico i metodi
 * che si differenziano dal comportamento del Giocatore Human.
 * @author giorg
 *
 */

public class GiocatoreAI extends Giocatore {
	
	/** COSTRUTTORI */
	//Default
	public GiocatoreAI() {super();};
	//Overloaded
	public GiocatoreAI(String nome, int posizione) {
		super(nome, posizione);}
	public GiocatoreAI(String nome, int posizione, int punteggio) {
		super (nome, posizione, punteggio);}
	
	/** METODI */
	
	//scegliCarta
	@Override
	public Carta scegliCarta(Simbolo simboloTurno, Colore coloreTurno) {
		// 1. Istanza di classe Optional per utilizzo Streams
		Optional<Carta> cartaMigliore;
		// 2. Estrai la carta COMPATIBILE DI VALORE MASSIMO
		cartaMigliore=
			this.mano.stream()
				.filter(crt->((crt.getSimbolo().equals(simboloTurno))||
					   (crt.getColore().equals(coloreTurno))||
					   (crt.getColore().equals(Colore.NULL))))
					   .max((crt1,crt2)->crt1.compareTo(crt2));
		// 3. Ritorna la carta contenuta nel wrapper Optional
		return cartaMigliore.orElse(null);}
	
	// controllaUno
	@Override
	public void controllaUno() {
		if ((this.getMano().size()==1) && (this.Uno==false)) {
			this.setUno(true);
			this.callUno();}
		else if ((this.getMano().size()!=1) && (this.Uno==true)) {
			this.setUno(false);
			this.calledUno=UnoCall.NOTDONE;}}
	
	// callUno
	@Override
	public void callUno() {this.calledUno=UnoCall.DONE;}

}
