package model;

/** INTERFACCIA COMPORTAMENTOGIOCATORE
 * 
 * L'interfaccia definisce i principali metodi che devono essere implementati
 * da tutte le classi di tipo Giocatore
 * 
 * @author giorg
 *
 */

public interface ComportamentoGiocatore {
	
	// scegliCarta
	public Carta scegliCarta(Simbolo simboloTurno, Colore coloreTurno) throws CartaNonConsentitaException;
	// controllaUno
	public void controllaUno();
	// callUno
	public void callUno();
	

}
