package model;

/**
 * CLASSE ASTRATTA CARTAAZIONE
 * 
 * Sottoclasse astratta della classe astratta Carta e che raggruppa
 * tutte le sottoclassi concrete di carta NON numeriche.
 * 
 * @author giorg
 *
 */

public abstract class CartaAzione extends Carta {
	
	/** COSTRUTTORE */
	public CartaAzione(Simbolo simbolo, Colore colore) {
		super(simbolo.getvalore(),colore);
		this.simbolo=simbolo;
		this.setNome();}

}
