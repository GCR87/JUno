package model;

/** IMPORT PACKAGES */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;


/**
 * CLASSE JUNOMODEL
 * 
 * Classe concreta e classe principale del package model.
 * La classe contiene tutti i dati del gioco e i principali metodi per 
 * il suo funzionamento.
 * Il Model e' aggiornato in base alle azioni dell'utente nella View tramite
 * il Controller (MVC Design Pattern) mentre la View e' aggiornata sui cambiamenti
 * del Model tramite l'OBSERVER Design Pattern.
 * 
 * Tecniche specifiche:
 * - OBSERVER Design Pattern
 * - MODEL-VIEW-CONTROLLER Design Pattern
 * 
 * @author giorg
 *
 */

public class JunoModel implements JunoModelInterface, JunoObservable {
	
	/** ATTRIBUTI */
	private static JunoModel junoModel;
	ArrayList<JunoObserver> observers=new ArrayList<>();

	private String nomeGiocatoreHuman;
	private ArrayList<String> nomiGiocatoriAI=new ArrayList<String>();
	private ListaGiocatori listaGiocatori;
	private Mazzo mazzo;
	private Mazzo.Pesca mazzoPesca;
	private Mazzo.Scarto mazzoScarto;
	private FasePartita fasePartita;
	private GiocatoreHuman giocatoreHuman;
	private Giocatore giocatoreTurno;
	private Giocatore giocatoreSuccessivo;
	private Carta cartaGiocataTurno;
	private String azioneCartaTurno;
	private Simbolo simboloTurno;
	private Colore coloreTurno;
	private int numeroGiocate, turniGiocati, roundGiocati;
	private boolean giocataOver, roundOver, gameOver;
	private Giocatore mazziere, roundWinner, gameWinner;

	/** COSTRUTTORE PRIVATO - SINGLETON PATTERN */
	private JunoModel() {};
	
	/** METODO GETINSTANCE() - SINGLETON PATTERN */
	public static JunoModel getInstance() { 
		if (junoModel==null) {junoModel= new JunoModel();}
		return junoModel;}
	
	
	/** METODI OBSERVER PATTERN */
	//registerObserver
	@Override
	public void registerObserver(JunoObserver o) {
		this.observers.add(o);}
	//removeObserver
	@Override
	public void removeObserver(JunoObserver o) {
		this.observers.remove(o);}
	//notifyObservers
	@Override
	public void notifyObservers() {
		for (int i=0; i<this.observers.size();i++) {
			JunoObserver observer=this.observers.get(i);
			observer.update();}}

	
	/** METODI GIOCO */
	
	//initialize()
	public void inizializza() {

		this.listaGiocatori= new ListaGiocatori();
		
		//1. Inizializza Giocatore Human
		this.setGiocatoreHuman(new GiocatoreHuman(this.nomeGiocatoreHuman,1));
		this.listaGiocatori.getGiocatori().add(this.getGiocatoreHuman());
		
		//2. Inizializza Giocatori AI
		int i=2;
		for (String nome: this.nomiGiocatoriAI) {
			listaGiocatori.getGiocatori().add(new GiocatoreAI(nome,2));
			i++;}
		
		//3. Inizializza Mazzo
		this.mazzo=new Mazzo(this);
		this.mazzoPesca=this.mazzo.getPesca();
		this.mazzoScarto=this.mazzo.getScarto();}

	
	//setMazziere()
	public void setMazziere() throws MazzoEsauritoException {
		
		this.fasePartita=FasePartita.MAZZIERE;
		this.setGiocataOver(false);
		
		// 0. Istanzia classe Optional per usare gli Stream
		Optional<Giocatore> mazziere;
		// 1. Ciascun giocatore pesca 1 carta
		for (Giocatore gc: this.listaGiocatori.getGiocatori()) {
			gc.pesca(1, mazzo.getPesca());}
		// 2. Il giocatore avente la carta di valore maggiore diventa il mazziere
		mazziere= this.listaGiocatori.getGiocatori().stream()
						.max((gc1,gc2)->
							(gc1.getMano().get(0).getValore()
							-(gc2.getMano().get(0).getValore())));
		// 3. I giocatori reinseriscono la carta appena pescata nel mazzo di Pesca
		this.listaGiocatori.getGiocatori().stream()
			.forEach((gc)->gc.restituisciCarta(gc.getMano().get(0), mazzo.getPesca()));
		// 4. Aggiorna Lista listaGiocatori
		this.listaGiocatori.aggiornaOrdine(mazziere.orElse(null));	

		// 5. Aggiorna Giocatore Turno
		this.getListaGiocatori().aggiorna();
		this.giocatoreTurno=this.getListaGiocatori().getGiocatoreTurno();		
		
		// 6. Assegna Mazziere
		this.mazziere=this.listaGiocatori.getGiocatoreTurno();
		
		// 7. Informa che la giocata e' finita
		this.setGiocataOver(true);
		
		// 8. Notifica Observers del Model
		this.notifyObservers();
		
		// 9. Aggiorna Fase Partita
		this.setFasePartita(FasePartita.PREPARATIVI);
	}
	
	
	//preparativi()
	public void preparativi() throws MazzoEsauritoException {
		
		Carta cartaAlzata;
		
		this.setGiocataOver(false);
		
		//1. Il mazziere mischia il mazzo
		this.mazziere.mischiaMazzo(this.mazzoPesca);
		
		//2. Il mazziere distribuisce le carte agli altri giocatori
		this.mazziere.distribuisciCarte(7, this.mazzoPesca, this.listaGiocatori.getGiocatori()); //DEVONO ESSERE 7 LE CARTE!!!!
		
		//3. Il mazziere pesca, gioca e scarta una carta
		this.mazziere.pesca(1, this.mazzoPesca);
		cartaAlzata=this.mazziere.getMano().get(this.mazziere.getMano().size()-1);
		this.mazziere.giocaCarta(cartaAlzata);
		this.cartaGiocataTurno=cartaAlzata;
		mazziere.scartaCarta(cartaAlzata, this.mazzoScarto);
		
		//4. Aggiorna Simbolo e Colore del turno
		if ((cartaAlzata.getSimbolo()!=Simbolo.JOLLY)&&(cartaAlzata.getSimbolo()!=Simbolo.JOLLYPESCAQUATTRO)){
				simboloTurno=mazzoScarto.getCarte().peek().getSimbolo();
				coloreTurno=mazzoScarto.getCarte().peek().getColore();} 
		
		// 5. Aggiorna Giocatore Turno
		this.listaGiocatori.aggiorna();
		this.giocatoreSuccessivo=this.getListaGiocatori().getGiocatoreTurno();

		// 6. Inizializza numero turni giocati
		this.setNumeroGiocate(0);
		
		// 7. Aggiorna RoundOver e GameOver - tramite STREAMS
		this.setRoundOver(this.getListaGiocatori().getGiocatori().stream()
				.anyMatch((gc)->gc.getMano().isEmpty()));
		this.setGameOver(this.getListaGiocatori().getGiocatori().stream()
				.anyMatch((gc)->gc.getPunteggio()>=500));
		
		// 8. Informa che la giocata e' finita
		this.setGiocataOver(true);

		// 9. Notifica OBSERVERS
		this.notifyObservers();
		
		// 10. Aggiorna Fase Partita
		this.setFasePartita(FasePartita.PARTITA);
		
		// 11. Resetta Mazziere
		this.mazziere=null;
	}
	
	//giocaTurno()
	@Override
	public void giocaTurno() throws MazzoEsauritoException, CartaNonConsentitaException {
		
		Carta cartaScelta;
		
		this.setGiocataOver(false);
		
		// 1. Aggiorna Giocatore Turno
		this.getListaGiocatori().aggiorna();
		this.setGiocatoreTurno(this.getListaGiocatori().getGiocatoreTurno());
	    this.getGiocatoreTurno().setNumeroCartePescate(0);
		this.getGiocatoreTurno().setTurniGiocati(this.getGiocatoreTurno().getTurniGiocati()+1);
		
		//2. Il giocatore sceglie la carta da giocare
		cartaScelta=this.getGiocatoreTurno().scegliCarta(this.getSimboloTurno(),this.getColoreTurno());
		
		//3. Se il giocatore non ha carte giocabili...
		if (cartaScelta==null) {
			//3.1 Il giocatore pesca una carta...
			try {this.getGiocatoreTurno().pesca(1, this.getMazzoPesca());}
			catch (MazzoEsauritoException e) {
				//Se il mazzo e' esaurito, lo si rimescola e il giocatore pesca la carta...
				this.getMazzo().reset();
				this.getGiocatoreTurno().pesca(1,this.getMazzoPesca());}
			//3.2 Il giocatore gioca una carta...
			cartaScelta=this.getGiocatoreTurno().scegliCarta(this.getSimboloTurno(), this.getColoreTurno());
			//3.3 Se il giocatore ha una carta giocabile...
			if (cartaScelta!=null) {
				//3.3.1 Il giocatore gioca la carta...
				try {this.getGiocatoreTurno().giocaCarta(cartaScelta);}
				catch (MazzoEsauritoException e) {
					//Se il mazzo e' esaurito, lo si rimescola e il giocatore pesca la carta...
					this.getMazzo().reset();
					this.getGiocatoreTurno().giocaCarta(cartaScelta);}
				//3.3.2 Il giocatore scarta la carta giocata...
				this.getGiocatoreTurno().scartaCarta(cartaScelta,this.getMazzoScarto());}
			//3.4 Se il giocatore non ha ancora una carta giocabile, il turno passa al giocatore successivo...
			else {  
					//Si registra che la carta giocata dal giocatore e' NULLA
					this.getGiocatoreTurno().setCartaGiocata(cartaScelta);
					//L'ordine dei giocatori viene aggiornato.
					this.getListaGiocatori().aggiornaOrdine(this.getListaGiocatori().getGiocatori().get(1));}}
		
		//4. Se il giocatore ha carte giocabili...
		else {
			//4.1 Il giocatore gioca la carta...
			try {this.getGiocatoreTurno().giocaCarta(cartaScelta);}
			catch (MazzoEsauritoException e) {
				//Se il mazzo e' esaurito, lo si rimescola e il giocatore pesca la carta...
				this.getMazzo().reset();
				this.getGiocatoreTurno().giocaCarta(cartaScelta);}
			//4.2 Il giocatore scarta la carta giocata...
			this.getGiocatoreTurno().scartaCarta(cartaScelta, this.getMazzoScarto());}
		
		//5. Controlla Uno
		this.getGiocatoreTurno().controllaUno();
	    //Se il giocatore ha dimenticato di chiamare UNO, pesca due carte!
		if (this.getGiocatoreTurno().getCalledUno()==UnoCall.MISSED) {
	    	this.getGiocatoreTurno().pesca(2, this.getMazzoPesca());}
	    
		//6. Aggiorna Simbolo e Colore del Turno
		if (((cartaScelta==null)||(cartaScelta.getSimbolo()!=Simbolo.JOLLY)&&(cartaScelta.getSimbolo()!=Simbolo.JOLLYPESCAQUATTRO))&&
			((this.getMazzoScarto().getCarte().peek().getColore()!=Colore.NULL))) {
				this.setSimboloTurno(this.getMazzoScarto().getCarte().peek().getSimbolo());
				this.setColoreTurno(this.getMazzoScarto().getCarte().peek().getColore());} 
		
		//7. Aggiorna Dati Giocatore Turno
	    this.setCartaGiocataTurno(cartaScelta);
	    this.setNumeroGiocate(this.getNumeroGiocate()+1);
		this.setTurniGiocati((int)Math.floor(this.getNumeroGiocate()/this.getListaGiocatori().getGiocatori().size()));
		
		//8. Visualizza Status Partita su Console
		this.printStatusToConsole();
		
		// 9. Aggiorna Giocatore Turno				
		this.getListaGiocatori().aggiorna();
		this.setGiocatoreSuccessivo(this.getListaGiocatori().getGiocatoreTurno());
		
		// 10. Aggiorna parametri RoundOver e GameOver
		this.setRoundOver(this.getListaGiocatori().getGiocatori().stream()
				.anyMatch((gc)->gc.getMano().isEmpty()));
		this.setGameOver(this.getListaGiocatori().getGiocatori().stream()
				.anyMatch((gc)->gc.getPunteggio()>=500));

		//11. Informa che la giocata e' conclusa
		this.setGiocataOver(true);
		
		// 12. Notifica OBSERVERS
		this.notifyObservers();
		
		//13. Reset parametro UnoCall del giocatore del turno
		this.getGiocatoreTurno().setCalledUno(UnoCall.NOTDONE);}
	
	
	// aggiornaPunteggiolistaGiocatori
	public void aggiornaPunteggio(Giocatore giocatore) {
		
		 int punteggio= 
		 // Creazione Stream su Lista listaGiocatori tramite STREAMS
		 this.listaGiocatori.getGiocatori().stream()
		 	// 1. Trattieni tutti gli altri Giocatori 
		 	.filter((gci)->!gci.equals(giocatore))
		 	// 2. Mappa i Giocatori alle loro Mano corrispondenti
		 	.map((gci)->gci.getMano())
		 	// 3. Converti Stream<List<Carta>> in Stream<Carta>
		 	.flatMap((glist)->glist.stream())
		 	// 4. Somma i valori di tutte le carte
		 	.collect(Collectors.summingInt((crt)->(int)crt.getValore()));
		 
		 // Aggiungi il punteggio calcolato al giocatore corrispondente
		 giocatore.aggiornaPunteggio(punteggio);}
	
	
	//giocaRound
	@Override
	public void giocaRound() throws MazzoEsauritoException {
		
		//1. Inizializza parametro di controllo 
		this.setRoundOver(false);
		
		//2. Gioca fasi preliminari della partita
		this.setMazziere();
		this.preparativi();
		
		//3. Gioca turni fino a che il round e' finito
		while (!this.isRoundOver()) {
			try{this.giocaTurno();}
			catch(CartaNonConsentitaException e) {
				e.printStackTrace();}}
		
		//4. Estrai vincitore round
		this.roundWinner=Optional.of(this.listaGiocatori.getGiocatori().stream()
						.filter((gc)->(gc.getMano().isEmpty()))
						.limit(1).toList().get(0)).orElse(null);
		
		//5. Stampa vincitore in Console
		System.out.println("\nThe Round Winner is: "+ this.roundWinner.getNome());
		
		//6. Aggiorna punteggio giocatori
		this.aggiornaPunteggio(roundWinner);
		
		//7. Restituisci le carte dei giocatori al mazzo
		this.listaGiocatori.getGiocatori().stream()
			.forEach((gc)->gc.restituisciMano(mazzoScarto));
		
		//8. Resetta il Mazzo
		this.mazzo.reset();
		
		//9. Incrementa i Round giocati di un'unita'
		this.roundGiocati++;
		
		//10. Notifica OBSERVERS
		this.notifyObservers();
	}
	
	
	//giocaPartita
	@Override
	public void giocaPartita() throws MazzoEsauritoException {
		
		//1. Inizializza parametro di controllo 
		this.setGameOver(false);
		
		//2. Gioca Round fino alla fine della partita 
		while(!gameOver) {
			this.giocaRound();}
		
		//3. Estrai il vincitore della partita 
		this.gameWinner=Optional.of(this.listaGiocatori.getGiocatori().stream()
									.filter((gc)->(gc.getPunteggio()>=500))
									.limit(1).toList().get(0)).orElse(null);
		
		// 4. Notifica OBSERVERS
		this.notifyObservers();}
	
	
	//printStatusToConsole()
	public void printStatusToConsole() {
		
		//1. Estrai valore variabili significative
		int playersCards= this.getListaGiocatori().getGiocatori().stream().collect(Collectors.summingInt((gc)->(int)gc.getMano().size()));
		int mazzoPescaCards=this.getMazzoPesca().getCarte().size();
		int mazzoScartoCards=this.getMazzoScarto().getCarte().size();
		Optional<Carta> playedCard=Optional.ofNullable(this.getCartaGiocataTurno());
		
		//2. Stampa informazioni Game Status in Console
		System.out.println( "Giocatore: " + this.getGiocatoreTurno().getNome()+"\n" +
							"Played Card: " + ((playedCard.isEmpty()==true)?"NULL CARD":playedCard.get().getSimbolo().toString()) +" " + 
											 ((playedCard.isEmpty()==true)?"NULL CARD":playedCard.get().getColore().toString()) +"\n" +
							"Total Cards Players: " + Integer.toString(playersCards) +"\n" +
						    "Total Cards Mazzo Pesca: " + Integer.toString(mazzoPescaCards) +"\n" +
						    "Total Cards Mazzo Scarto: " + Integer.toString(mazzoScartoCards)+"\n");}
	
	
	// Setter e Getter
	public ListaGiocatori getListaGiocatori() {return listaGiocatori;}
	public void setListaGiocatori(ListaGiocatori listaGiocatori) {this.listaGiocatori = listaGiocatori;}
	public Mazzo getMazzo() {return mazzo;}
	public void setMazzo(Mazzo mazzo) {this.mazzo = mazzo;}
	public Mazzo.Pesca getMazzoPesca() {return this.mazzoPesca;}
	public void setMazzoPesca(Mazzo.Pesca mazzoPesca) {this.mazzoPesca=mazzoPesca;}
	public Mazzo.Scarto getMazzoScarto() {return this.mazzoScarto;}
	public void setMazzoScarto(Mazzo.Scarto mazzoScarto) {this.mazzoScarto=mazzoScarto;}
	public Giocatore getGiocatoreTurno() {return this.giocatoreTurno;}
	public void setGiocatoreTurno(Giocatore giocatoreTurno) {this.giocatoreTurno = this.listaGiocatori.getGiocatoreTurno();}
	public Simbolo getSimboloTurno() {return simboloTurno;}
	public void setSimboloTurno(Simbolo simboloTurno) {this.simboloTurno = simboloTurno;}
	public Colore getColoreTurno() {return coloreTurno;}
	public void setColoreTurno(Colore coloreTurno) {this.coloreTurno = coloreTurno;}
	public Giocatore getRoundWinner() {return this.roundWinner;}
	public Giocatore getGameWinner() {return this.gameWinner;}
	public int getTurniGiocati() {return turniGiocati;}
	public void setTurniGiocati(int turniGiocati) {this.turniGiocati = turniGiocati;}
	public int getRoundGiocati() {return roundGiocati;}
	public void setRoundGiocati(int roundGiocati) {this.roundGiocati = roundGiocati;}
	public boolean isGiocataOver() {return giocataOver;}
	public void setGiocataOver(boolean giocataOver) {this.giocataOver = giocataOver;}
	public boolean isRoundOver() {return roundOver;}
	public void setRoundOver(boolean roundOver) {this.roundOver = roundOver;}
	public boolean isGameOver() {return gameOver;}
	public void setGameOver(boolean gameOver) {this.gameOver = gameOver;}
	public void setRoundWinner(Giocatore roundWinner) {this.roundWinner = roundWinner;}
	public void setGameWinner(Giocatore gameWinner) {this.gameWinner = gameWinner;}	
	public Giocatore getGiocatoreSuccessivo() {return giocatoreSuccessivo;}
	public void setGiocatoreSuccessivo(Giocatore giocatoreSuccessivo) {this.giocatoreSuccessivo = giocatoreSuccessivo;}
	public FasePartita getFasePartita() {return fasePartita;}
	public void setFasePartita(FasePartita fasePartita) {this.fasePartita = fasePartita;}
	public Giocatore getMazziere() {return this.mazziere;}
	public String getAzioneCartaTurno() {return azioneCartaTurno;}
	public void setAzioneCartaTurno(String azioneCartaTurno) {this.azioneCartaTurno = azioneCartaTurno;}
	public int getNumeroGiocate() {return this.numeroGiocate;}
	public void setNumeroGiocate(int numeroGiocate) {this.numeroGiocate=numeroGiocate;}
	public String getNomeGiocatoreHuman() {return nomeGiocatoreHuman;}
	public void setNomeGiocatoreHuman(String nomeGiocatoreHuman) {this.nomeGiocatoreHuman = nomeGiocatoreHuman;}
	public ArrayList<String> getNomiGiocatoriAI() {return nomiGiocatoriAI;}
	public void setNomiGiocatoriAI(ArrayList<String> nomiGiocatoriAI) {this.nomiGiocatoriAI = nomiGiocatoriAI;}
	public Carta getCartaGiocataTurno() {return cartaGiocataTurno;}
	public void setCartaGiocataTurno(Carta cartaGiocataTurno) {this.cartaGiocataTurno = cartaGiocataTurno;}
	public GiocatoreHuman getGiocatoreHuman() {return giocatoreHuman;}
	public void setGiocatoreHuman(GiocatoreHuman giocatoreHuman) {this.giocatoreHuman = giocatoreHuman;}
	
	
}


