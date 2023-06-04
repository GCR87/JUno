package model;

/** IMP0RT PACKAGES */
import java.util.Stack;


/** CLASSE ASTRATTA DI TIPO FACTORY
 * 
 * Superclasse astratta delle classi factory concrete per la creazione
 * di carte da gioco.
 * Il metodo creaCarte() e' dichiarato statico (puo' essere chiamato direttamente
 * dalla classe, invece che da una sua istanza).
 * Java non consente a un metodo statico di essere dichiarato anche abstract 
 * (...cosi' da forzare le sottoclassi concrete a implementarlo in modo esplicito).
 * 
 */



public abstract class CardsFactory {
	
	/** COSTRUTTORE IMPLICITO */
	
	/** METODI */
	protected static Stack<Carta> creaCarte(JunoModel model){return null;};


}
