package model;

/**
 * CLASSE CONCRETA NUMERICA
 * 
 * Sottoclasse concreta della classe astratta Carta.
 * Il comportamento della Carta e' definito tramite lo STRATEGY
 * Design Pattern.
 * 
 * @author giorg
 *
 */

public class CartaNumerica extends Carta {

	/** COSTRUTTORE */
	public CartaNumerica(Simbolo simbolo, Colore colore, JunoModel model) {
		super(simbolo.getvalore(),colore);
		this.simbolo=simbolo;
		this.setNome();
		// Assegna classe di comportamento (STRATEGY PATTERN)
		this.compCarta=new CompNull(model);}
	
}
