package model;

/**
 * ENUMERATION SIMBOLO
 * 
 * Enumerazione costituita da una serie di costanti identificative
 * per i diversi simboli delle carte. Ciascuna costante e' associata a due valori 
 * numerici: uno per il numero di carte con un dato simbolo e uno per il valore
 * corrispondente.
 * Entrambi i valori sono essenziali per la creazione del mazzo e per il conteggio
 * del punteggio dei giocatori.
 * 
 * @author giorg
 *
 */


public enum Simbolo {
	
	/** VALORI */
	ZERO (1,0), UNO (2,1), DUE (2,2), TRE(2,3), QUATTRO (2,4), CINQUE(2,5),
	SEI(2,6), SETTE(2,7), OTTO (2,8), NOVE (2,9),
	PESCADUE (2,20), INVERTI(2,20), SALTO(2, 20), JOLLY(4,50),JOLLYPESCAQUATTRO(4,50);
	
	/** ATTRIBUTI */
	private int istanze, valore;
	
	/** COSTRUTTORE */
	private Simbolo(int istanze, int valore) {
		this.istanze=istanze;
		this.valore=valore;}
	
	/** METODI SETTER E GETTER */
	public void setIstanze (int copie) {this.istanze=copie;}
	public int getIstanze () {return this.istanze;}
	public void setvalore(int valore) {this.valore=valore;}
	public int getvalore() {return this.valore;}
	
}
