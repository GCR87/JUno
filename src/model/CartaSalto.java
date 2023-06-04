package model;

/**
 * CLASSE CONCRETA CARTASALTO
 * 
 * Sottoclasse concreta della classe astratta CartaAzione.
 * Il comportamento della Carta e' definito tramite lo STRATEGY
 * Design Pattern.
 * 
 * @author giorg
 *
 */

public class CartaSalto extends CartaAzione {
	
	/** COSTRUTTORE */
	public CartaSalto(Simbolo simbolo, Colore colore, JunoModel model) {
		super(simbolo,colore);
		// Assegna classe di comportamento (STRATEGY PATTERN)
		this.compCarta=new CompSalto(model);
	}

}
