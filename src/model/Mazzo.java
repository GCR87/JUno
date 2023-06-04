package model;

/** IMPORT PACKAGES */
import java.util.Stack;
import java.util.Collections;


/**
 * CLASSE MAZZO
 * 
 * La classe Mazzo contiene due classi annidate interne. Una per la pesca e una per
 * lo scarto. Cio' consente di resettare e travasare le carte dall'uno all'altro 
 * molto velocemente andando a richiamare il metodo .reset() della classe principale.
 * 
 * Tecniche specifiche:
 * - CLASSI INTERNE
 * - STREAMS
 * 
 * @author giorg
 *
 */

public class Mazzo {
	
	/** ATTRIBUTI */
	private Pesca pesca;
	private Scarto scarto;
	private JunoModel model;
	
	/** COSTRUTTORE */
	public Mazzo(JunoModel model) {
		this.model=model;
		// Creazione sottomazzi
		this.pesca=new Mazzo.Pesca(model);
		this.scarto=new Mazzo.Scarto();}
		
	/** METODI */
	
	//Metodi Setter & Getter
	
	public Pesca getPesca() {return this.pesca;}
	public void setPesca(Pesca pesca) {this.pesca = pesca;}
	public void setScarto(Scarto scarto) {this.scarto = scarto;}
	public Scarto getScarto() {return this.scarto;}

	//Metodo Reset
	public void reset() {
		// 1. Calcola numero carte nel mazzo di scarto
		int imax=this.scarto.getCarte().size();
		// 2. Travasa carte da mazzo Scarto a mazzo Pesca eccetto l'ultima
		for (int i=0;i<imax-1;i++) {
			this.pesca.getCarte().add(this.scarto.getCarte().remove(0));}
		// 3. Gira Carte mazzo Pesca con faccia verso il basso
		this.pesca.getCarte().stream().forEach((crt)->crt.faceUp=false);
		// 4. Gira Carte mazzo Scarto con faccia verso l'alto
		this.scarto.getCarte().stream().forEach((crt)->crt.faceUp=true);
		// 5. Mischia mazzo Pesca
		this.pesca.shuffle();}
	
	
	/** CLASSI PRIVATE */
	
	public static class Pesca {
		
		/** ATTRIBUTI */
		private Stack<Carta> carte = new Stack<Carta>();
		private TipoMazzo tipoMazzo;
		

		/** COSTRUTTORE */
		public Pesca(JunoModel model) {
			// 1. Assegnazione tipo
			this.tipoMazzo=TipoMazzo.PESCA;
			// 2. Creazione Carte
			this.carte.addAll(UnoCardsFactory.creaCarte(model));
			// 3. Mescolamento Carte
			this.shuffle();}

		
		/** METODI */
		
		// Metodi Getter e Setter
		public Stack<Carta> getCarte() {return this.carte;}
		public void setCarte(Stack<Carta>carte) {this.carte=carte;}
		public TipoMazzo getTipoMazzo() {return this.tipoMazzo;}
		
		// Metodo Push
		public void push(Carta carta) { this.carte.push(carta);}
		
		// Metodo Pop
		public Carta pop() throws MazzoEsauritoException {
			if (this.getCarte().size()==0) throw new MazzoEsauritoException();
			return this.carte.pop();}
		
		//Metodo Shuffle
		public void shuffle() {
			if(this.carte.size()!=0) {Collections.shuffle(this.carte);}}}
	
	public static class Scarto {
		
		/** ATTRIBUTI */
		private Stack<Carta> carte = new Stack<Carta>();
		private TipoMazzo tipoMazzo;
		
		/** COSTRUTTORE */
		public Scarto() {
			this.tipoMazzo=TipoMazzo.SCARTO;
			this.carte=new Stack<Carta>();}
		
		// Metodi Getter e Setter
		public Stack<Carta> getCarte() {return this.carte;}
		public void setCarte(Stack<Carta>carte) {this.carte=carte;}
		public TipoMazzo getTipoMazzo() {return this.tipoMazzo;}
		
		// Metodo Push
		public void push(Carta carta) { this.carte.push(carta);}}

}
