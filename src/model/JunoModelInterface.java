package model;

/** INTERFACCIA JUNOMODELINTERFACE
 * 
 * L'interfaccia definisce i principali metodi che devono essere implementati
 * da qualsivoglia Model per il gioco UNO.
 * 
 * @author giorg
 *
 */

public interface JunoModelInterface {
	
	public void inizializza();
	public void giocaPartita() throws MazzoEsauritoException;
	public void giocaRound() throws MazzoEsauritoException;
	public void giocaTurno() throws MazzoEsauritoException, 
									CartaNonConsentitaException;

}
