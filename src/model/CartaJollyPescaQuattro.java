package model;

/**
 * CLASSE CONCRETA CARTAJOLLYPESCAQUATTRO
 * 
 * Sottoclasse concreta della classe astratta CartaAzione.
 * Il comportamento della Carta e' definito tramite lo STRATEGY
 * Design Pattern.
 * 
 * @author giorg
 *
 */

public class CartaJollyPescaQuattro extends CartaAzione {
	
	/** COSTRUTTORE */
	public CartaJollyPescaQuattro(Simbolo simbolo, Colore colore, JunoModel model) {
		super(simbolo, colore);
		// Assegna classe di comportamento
		this.compCarta= new CompJollyPescaQuattro(model);
	}

}
