package model;

/**
 * CLASSE ASTRATTA COMPORTAMENTO
 * 
 * Classe astratta che implementa l'interfaccia ComportamentoCarta
 * e che aggiunge un riferimento al Model dell'applicazione che sara'
 * poi richiamato nel metodo .azione() delle varie sottoclassi tramite
 * l'uso dello STRATEGY Design Pattern.
 * 
 * @author giorg
 *
 */

public abstract class Comportamento implements ComportamentoCarta {
	
	/** ATTRIBUTI */
	protected JunoModel junoModel;
	
	/** COSTRUTTORE */
	public Comportamento(JunoModel junoModel) {
		this.junoModel=junoModel;
	}

}
