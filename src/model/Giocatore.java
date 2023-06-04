package model;

/** IMPORT PACKAGES */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


/**
 * CLASSE CONCRETA GIOCATORE
 * 
 * La classe Giocatore e' la classe da cui ereditano tutte le altre classi di tipo
 * Giocatore (AI e Human).
 * Implementa l'interfaccia funzionale Comparable e ne ridefinisce l'unico metodo .compareTo()
 * definendo i criteri che consentono ai giocatori di essere ordinabili in qualsivoglia
 * struttura dati che consenta/necessiti il sorting dei propri elementi.
 * 
 * La classe Giocatore ridefinisce anche i metodi .hashCode(), per un corretto ordinamento nelle
 * strutture dati che usano l'hashcode, e il metodo equals().
 * 
 * @author giorg
 *
 */

public class Giocatore implements Comparable<Object>, ComportamentoGiocatore{
	
	/** ATTRIBUTI */
	protected String nome;
	protected int posizione, punteggio;
	protected List<Carta> mano;
	protected Carta cartaScelta, cartaGiocata;
	protected int numeroCartePescate;
	protected int turniGiocati;
	protected boolean Uno;
	protected UnoCall calledUno;
	
	/** COSTRUTTORE */
	
	//Default
	public Giocatore() {
		this.mano=new ArrayList<Carta>();
		this.Uno=false;
		this.calledUno=UnoCall.NOTDONE;};
		
	//Overloaded
	public Giocatore(String nome, int posizione) {
		this();
		this.nome=nome;
		this.posizione=posizione;}
	public Giocatore(String nome, int posizione, int punteggio) {
		this(nome, posizione);
		this.punteggio=punteggio;}
	
	
	/** METODI */

	// AZIONI

	// pesca
	public void pesca(int numeroCarte, Mazzo.Pesca mazzo) throws MazzoEsauritoException{
		this.numeroCartePescate=numeroCarte;
		for (int i=0;i<numeroCarte;i++) {
			this.mano.add(mazzo.pop());}}
	
	// alza carta
	public Carta alzaCarta(Mazzo.Pesca mazzo) throws MazzoEsauritoException{
		return mazzo.pop();}
	
	// mischiaMazzo
	public void mischiaMazzo(Mazzo.Pesca mazzo) {mazzo.shuffle();}
	   
	// giocaCarta
	public void giocaCarta(Carta carta) throws MazzoEsauritoException {
		carta.azione();
		this.setCartaGiocata(carta);}	
		
	// scartaCarta
	public void scartaCarta(Carta carta, Mazzo.Scarto mazzo) {
		mazzo.push(this.mano.remove(this.mano.indexOf(carta)));
		mazzo.getCarte().lastElement().setFaceUp(true);;}
	
	// restituisci Carta
	public void restituisciCarta(Carta carta, Mazzo.Pesca mazzo) {
		mazzo.push(this.mano.remove(this.mano.indexOf(carta)));}
	
	// restituisci Mano
	public void restituisciMano(Mazzo.Scarto mazzo) {
		
		// 1. Inizializza lista temporanea
		List<Carta> temp=new ArrayList<Carta>();
		
		// 2. Determina numero totale carte nella mano
		int imax= this.getMano().size();
		
		// 3. Trasferisci carte da mano a lista temporanea
		for (int i=0;i<imax;i++) {
			temp.add(this.getMano().get(0));
			this.getMano().remove(0);}
		
		// 3. Aggiungi lista temporanea a carte mazzo
		mazzo.getCarte().addAll(temp);}

	
	// distribuisciCarte
	public void distribuisciCarte(int numeroCarte, Mazzo.Pesca mazzo, Collection<Giocatore> giocatori) throws MazzoEsauritoException {
		for (int i=0; i<numeroCarte; i++) {
			for (Giocatore gc : giocatori) {gc.pesca(1, mazzo);}}}
	
	// aggiornaPunteggio
	public void aggiornaPunteggio(int punteggio) {this.punteggio+=punteggio;}
	
	// scegliCarta
	@Override
	public Carta scegliCarta(Simbolo simboloTurno, Colore coloreTurno) throws CartaNonConsentitaException {
		return null;}
	// controllaUno
	@Override
	public void controllaUno() {}
	// callUno
	@Override
	public void callUno() {}
	
	
	
	// SETTERS and GETTERS
	public void setNome(String nome) {this.nome=nome;}
	public String getNome() {return this.nome;}
	public void setPunteggio(int punteggio) {this.punteggio=punteggio;}
	public int getPunteggio() {return this.punteggio;}
	public void setMano(List<Carta> mano) {this.mano=mano;}
	public List<Carta> getMano()  {return this.mano;}
	public void setTurniGiocati(int turniGiocati) {this.turniGiocati=turniGiocati;}
	public int getTurniGiocati() {return this.turniGiocati;}
	public int getPosizione() {return posizione;}
	public void setPosizione(int posizione) {this.posizione = posizione;}	
	public boolean getUno() {return Uno;}
	public void setUno(boolean uno) {Uno = uno;}	
	public UnoCall getCalledUno() {return calledUno;}
	public void setCalledUno(UnoCall calledUno) {this.calledUno = calledUno;}	
	public Carta getCartaScelta() {return cartaScelta;}
	public void setCartaScelta(Carta cartaScelta) {this.cartaScelta = cartaScelta;}	
	public Carta getCartaGiocata() {return cartaGiocata;}
	public void setCartaGiocata(Carta cartaGiocata) {this.cartaGiocata = cartaGiocata;}
	public int getNumeroCartePescate() {return numeroCartePescate;}
	public void setNumeroCartePescate(int numeroCartePescate) {this.numeroCartePescate = numeroCartePescate;}
	
	
	
	/** METODO HASHCODE
	
	 * Metodo ereditato dalla classe object e da sovrascrivere in abbinamento con
	 * il metodo equals per garantire che le strutture dati basate sulle Hash
	 * Tables funzionino correttamente.  
	 * 
	 * L'Hashcode, viene determinato sulla base di nome, punteggio e posizione del
	 * giocatore.
	 *  
	 * */
	@Override
	public int hashCode() {return this.getNome().hashCode()+this.getPosizione()+this.getPunteggio();}

	

	/** METODO EQUALS	
	
	 * Metodo ereditato dalla classe object e da sovrascrivere per definire il criterio
	 * con cui due istanze della classe possono essere considerate identiche...
	 * invece di effettuare il confronto dei riferimenti, secondo implementazione di 
	 * default. */
	
	@Override 
	public boolean equals(Object o) {
		
		// 1. Controllo oggetto in input sia non nullo e della stessa classe
		if ((o==null)||(o instanceof Giocatore == false)) return false;
		// 2. Uguaglianza solo se i due giocatori hanno stesso nome, punteggio, mano di carte e posizione
		Giocatore gc=(Giocatore)o;
		if ((this.getNome().equals(gc.getNome())&&
		   (this.getPunteggio()==gc.getPunteggio())&&
		   (this.getMano().hashCode()==gc.getMano().hashCode())&&
		   (this.getPosizione()==gc.getPosizione()))) return true;
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
	 * @return
	 */
	
	@Override
	public int compareTo(Object o) {
		// Downcasting Oggetto a classe Carta
		Giocatore giocatore=(Giocatore)o;
		// Confronto (Prima Nome e poi Punteggio)
		if (this.getNome().compareTo(giocatore.getNome())!=0) {
			return this.getNome().compareTo(giocatore.getNome());}
		else if (this.getPunteggio()!=giocatore.getPunteggio()) {
			return this.getPunteggio()-giocatore.getPunteggio();}
		else return 0;}
	
}
