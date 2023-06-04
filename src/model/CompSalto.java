package model;

/**
 * CLASSE CONCRETA COMPSALTO
 * 
 * Classe concreta di comportamento per le carte SALTO.
 * 
 * @author giorg
 *
 */

public class CompSalto extends Comportamento {

	public CompSalto(JunoModel junoModel) {super(junoModel);}
	
	@Override
	public void azione() {
		//1. Penalizza giocatore colpito
		Giocatore giocatoreColpito=this.junoModel.getListaGiocatori().getGiocatori().get(1);
		this.junoModel.getListaGiocatori().aggiornaOrdine(this.junoModel.getListaGiocatori().getGiocatori().get(2));
		//2. Crea messaggio per utente	
		String azioneCarta= "Il giocatore " + giocatoreColpito.getNome() + " salta il prossimo turno.";
		this.junoModel.setAzioneCartaTurno(azioneCarta);
		
	}

}
