package model;

/**
 * ENUMERATION COLORE
 * 
 * Enumerazione costituita da una serie di costanti identificative
 * per i diversi colori. Ciascuna costante e' associata a un valore 
 * numerico che viene utilizzato nell'ordinamento delle carte.
 * 
 * @author giorg
 *
 */

public enum Colore {
	
	/** VALORI */
	ROSSO (1), VERDE (2), GIALLO(3), BLU(4), NULL(5);
	
	/** ATTRIBUTI */
	private int priorita;
	
	/** COSTRUTTORE */
	private Colore(int priorita) {
		this.priorita=priorita;}
	
	/** METODI */
	public void setPriorita(int priorita) {this.priorita=priorita;}
	public int getPriorita() {return this.priorita;}
	
	
}
