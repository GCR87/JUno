package model;


/**
 * CLASSE CONCRETA COMPPESCADUE
 * 
 * Classe concreta di comportamento per le carte PESCADUE.
 * 
 * @author giorg
 *
 */

public class CompPescaDue extends Comportamento {

	public CompPescaDue(JunoModel junoModel) {super(junoModel);}
	
	@Override
	public void azione() throws MazzoEsauritoException {
	
		//1. Penalizza giocatore colpito
		Giocatore giocatoreColpito=this.junoModel.getListaGiocatori().getGiocatori().get(1);
		giocatoreColpito.pesca(2, this.junoModel.getMazzoPesca());
		
		//2. Aggiorna ordine giocatori
		junoModel.getListaGiocatori().aggiornaOrdine(junoModel.getListaGiocatori().getGiocatori().get(2));
		
		//3. Crea messaggio per utente	
		String azioneCarta= "Il giocatore " + giocatoreColpito.getNome() + 
							" pesca 2 carte e salta il prossimo turno.";
		this.junoModel.setAzioneCartaTurno(azioneCarta);
	}

}
