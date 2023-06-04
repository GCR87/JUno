package model;


/** 
 * CLASSE ASTRATTA CARTA
 * 
 * La classe Carta e' la classe astratta da cui ereditano tutte le classi di tipo
 * Carta concrete.
 * Implementa l'interfaccia funzionale Comparable e ne ridefinisce l'unico metodo .compareTo()
 * definendo i criteri che consentono alle carte di essere ordinabili in qualsivoglia
 * struttura dati che consenta/necessiti il sorting dei propri elementi.
 * 
 * La classe Carta ridefinisce anche i metodi .hashCode(), per un corretto ordinamento nelle
 * strutture dati che usano l'hashcode, e il metodo equals().
 * 
 * La classe fa uso dello STRATEGY Design Pattern per definire il proprio metodo .azione()
 * tramite un attributo di classe Comportamento.
 * 
 * Tecniche Specifiche:
 * - STRATEGY Design Pattern
 * 
 * @author giorg
 *
 */


public abstract class Carta implements Comparable<Object> {
	
	/** ATTRIBUTI */
	protected int valore;
	protected Simbolo simbolo;
	protected Colore colore;
	protected String nome;
	protected boolean faceUp;
	protected ComportamentoCarta compCarta;
	
	/** COSTRUTTORE */
	public Carta(int valore, Colore colore) {
		this.valore=valore;
		this.colore=colore;
		this.faceUp=false;} 

	/** METODI */
	
	//Setters and Getters
	/**
	 * Assegna valore carta
	 * @param valore della carta
	 */
	public void setValore(int valore) {this.valore=valore;}
	/**
	 * Ritorna valore carta
	 * @return valore della carta
	 */
	public int getValore() {return this.valore;}
	/**
	 *  Assegna simbolo carta
	 * @param simbolo della carta
	 */
	public void setSimbolo(Simbolo simbolo) {this.simbolo=simbolo;}
	/**
	 * Ritorna simbolo carta
	 * @return simbolo della carta
	 */
	public Simbolo getSimbolo() {return this.simbolo;}
	/**
	 * Assegna colore alla carta
	 * @param colore della carta
	 */
	public void setColore(Colore colore) {this.colore=colore;}
	/**
	 * @return  Ritorna colore carta
	 */
	public Colore getColore() {return this.colore;}
	/**
	 *  Ritorna nome della carta
	 * @return nome della carta
	 */
	public String getNome() {return nome;}
	/**
	 *  Assegna faceUp
	 * @param faceUp
	 */
	public void setFaceUp(boolean faceUp) {this.faceUp=faceUp;}
	/**
	 * Ritorna true/false se la carta e' faceUp
	 * @return paramaetro faceUp
	 */
	public boolean getFaceUp() {return this.faceUp;}
	/**
	 *  Ribalta faccia carta
	 */
	public void flip() {this.faceUp=!this.faceUp;}
	/**
	 * Ritorna comportamento carta
	 * @return comportamento carta
	 */
	public ComportamentoCarta getCompCarta() {return compCarta;}
	/**
	 * Assegna comportamento carta
	 * @param compCarta - comportamento carta
	 */
	public void setCompCarta(ComportamentoCarta compCarta) {this.compCarta = compCarta;}
	/**
	 * Crea il nome della carta a partire da simbolo e colore assegnati
	 */
	public void setNome() {
		this.nome = this.simbolo.toString() + (this.colore.toString()=="NULL"?"":(" " + this.colore.toString()));}

	
	/** METODO AZIONE
	 *  Metodo che richiama il corrispondente metodo dell'istanza di classe 
	 *  ComportamentoCarta assegnata alla presente come attributo.
	 * @throws MazzoEsauritoException */
	public void azione() throws MazzoEsauritoException {this.compCarta.azione();}
	
	
	
	/** METODO HASHCODE
	
	 * Metodo ereditato dalla classe object e da sovrascrivere in abbinamento con
	 * il metodo equals per garantire che le strutture dati basate sulle Hash
	 * Tables funzionino correttamente.  
	 * 
	 * */
	@Override
	public int hashCode() {return this.getValore()+this.getColore().hashCode();}

	

	/** METODO EQUALS	
	
	 * Metodo ereditato dalla classe object e da sovrascrivere per definire il criterio
	 * con cui due istanze della classe possono essere considerate identiche...
	 * invece di effettuare il confronto dei riferimenti, secondo implementazione di 
	 * default. */
	
	@Override 
	public boolean equals(Object o) {
		
		// 1. Controllo oggetto in input sia non nullo e della stessa classe
		if ((o==null)||(o instanceof Carta == false)) return false;
		// 2. Uguaglianza solo se le due carte hanno stesso colore e valore
		Carta cc=(Carta)o;
		if ((this.getValore()==cc.getValore())&&(this.getColore()==cc.getColore())) return true;
		// 3. Falso altrimenti
		return false;
	}	
	
	
	
	/** METODO COMPARE TO
	 * 
	 * Metodo ereditato dalla Interfaccia Comparable e da sovrascrivere 
	 * opportunamente per il corretto ordinamento delle istanze della 
	 * classe Carta e delle sue sottoclassi in strutture dati ordinabili.
	 * 
	 * @param o
	 * @return integer
	 */
	
	@Override
	public int compareTo(Object o) {
		// Downcasting Oggetto a classe Carta
		Carta carta=(Carta)o;
		// Confronto (Prima valore e poi colore)
		if (this.getValore()!=carta.getValore()) {
			return this.getValore()-carta.getValore();}
		else if (this.getColore().getPriorita()!=carta.getColore().getPriorita()) {
			return this.getColore().getPriorita()-carta.getColore().getPriorita();}
		else return 0;
		}
}
	
