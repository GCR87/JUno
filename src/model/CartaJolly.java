package model;


/**
 * CLASSE CONCRETA CARTAJOLLY
 * 
 * Sottoclasse concreta della classe astratta CartaAzione.
 * Il comportamento della Carta e' definito tramite lo STRATEGY
 * Design Pattern.
 * 
 * @author giorg
 *
 */

public class CartaJolly extends CartaAzione {

	/** COSTRUTTORE */
	public CartaJolly(Simbolo simbolo, Colore colore, JunoModel model) {
		super(simbolo, colore);
		// Assegna classe di comportamento
		this.compCarta=new CompJolly(model);}
	

}
