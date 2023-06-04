package model;

/**
 * CLASSE CONCRETA CARTAPESCADUE
 * 
 * Sottoclasse concreta della classe astratta CartaAzione.
 * Il comportamento della Carta e' definito tramite lo STRATEGY
 * Design Pattern.
 * 
 * @author giorg
 *
 */

public class CartaPescaDue extends CartaAzione {

	/** COSTRUTTORE */
	public CartaPescaDue(Simbolo simbolo, Colore colore, JunoModel model) {
		super(simbolo, colore);
		// Assegna classe di comportamento (STRATEGY PATTERN)
		this.compCarta=new CompPescaDue(model);}
}
