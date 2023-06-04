package model;

/**
 * CLASSE CONCRETA CARTAINVERTI
 * 
 * Sottoclasse concreta della classe astratta CartaAzione.
 * Il comportamento della Carta e' definito tramite lo STRATEGY
 * Design Pattern.
 * 
 * @author giorg
 *
 */

public class CartaInverti extends CartaAzione {

	/** COSTRUTTORE */
	public CartaInverti(Simbolo simbolo, Colore colore, JunoModel model) {
		super(simbolo, colore);
		// Assegna classe di comportamento (STRATEGY PATTERN)
		this.compCarta=new CompInverti(model);
	}
}
