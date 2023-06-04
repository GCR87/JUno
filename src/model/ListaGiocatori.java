package model;

/** IMPORT PACKAGES */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * CLASSE CONCRETA LISTAGIOCATORI
 * 
 * La classe ListaGiocatori ha il compito di gestire l'ordinamento dei giocatori
 * in base agli sviluppi del gioco e mantenendo il giocatore del turno sempre
 * in prima posizione (i.e. indice 0).
 * 
 * La classe incapsula e decora un arraylist di istanze di Giocatore su cui
 * svolge varie operazioni.
 * 
 * Tecniche specifiche:
 * - DECORATOR Design Pattern
 * - STREAMS
 * 
 * @author giorg
 *
 */

public class ListaGiocatori {
	
	/** ATTRIBUTI */
	private ArrayList<Giocatore> giocatori;
	private Giocatore giocatorePrecedente;
	private Giocatore giocatoreTurno;
	private Giocatore giocatoreSuccessivo;

	/** COSTRUTTORI */
	
	// Default
	public ListaGiocatori() {
		this.giocatori=new ArrayList<Giocatore>();}
	// Overloaded
	public ListaGiocatori(ArrayList<Giocatore> giocatori) {
		this.giocatori=giocatori;
		this.assegnaPosizioni();}
	
	/** METODI */
	
	//assegnaPosizioni
	private void assegnaPosizioni() {
		for (int i=0;i<this.giocatori.size();i++) {
			this.giocatori.get(i).setPosizione(i+1);}}
	//invertiOrdine
	public void invertiOrdine() {
		giocatori.sort((g1,g2)->(g2.getPosizione()-g1.getPosizione()));
		this.assegnaPosizioni();}
	//rimuoviGiocatore
	public void rimuoviGiocatore(Giocatore giocatore) {
		giocatori.remove(giocatore);
		this.assegnaPosizioni();}
	//aggiungiGiocatore
	public void aggiungiGiocatore(Giocatore giocatore) {
		giocatori.add(giocatore);
		this.assegnaPosizioni();}
	//aggiornaOrdine
	public void aggiornaOrdine(Giocatore giocatoreTurno) {
		// Variabili Locali
		int igt;
		//List<Giocatore> temp;
		ArrayList<Giocatore> temp=new ArrayList<Giocatore>();
		
		// 1. Estrai indice giocatore del Turno
		igt= this.giocatori.indexOf(giocatoreTurno);
		
		// 2. Estrai giocatori antecedenti giocatore turno
		for (int i=0;i<igt;i++) {
			temp.add(this.giocatori.get(0));
			this.giocatori.remove(0);}
		
		// 3. Aggiungi giocatori estratti alla fine della Lista
		this.giocatori.addAll(temp);
		
		// 4. Aggiorna le posizioni dei giocatori
		this.assegnaPosizioni();}
	
	//aggiorna
	public void aggiorna() {
		this.giocatoreTurno=this.giocatori.stream()
				.filter((gc)->gc.getPosizione()==1)
				.toList().get(0);
		this.giocatoreSuccessivo=this.giocatori.stream()
				.filter((gc)->gc.getPosizione()==2)
				.toList().get(0);
		this.giocatorePrecedente=this.giocatori.get(this.giocatori.size()-1);}
	
	
	// SETTERS & GETTERS
	public ArrayList<Giocatore> getGiocatori() {return giocatori;}
	public void setGiocatori(ArrayList<Giocatore> giocatori) {
		this.giocatori = giocatori;
		this.assegnaPosizioni();}
	public Giocatore getGiocatoreTurno() {return this.giocatoreTurno;}
	public Giocatore getGiocatorePrecedente() {return this.giocatorePrecedente;}
	public Giocatore getGiocatoreSuccessivo(){return this.giocatoreSuccessivo;}

}
