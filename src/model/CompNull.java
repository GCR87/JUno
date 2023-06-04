package model;

/**
 * CLASSE CONCRETA COMPNULL
 * 
 * Classe concreta di comportamento per le carte numeriche
 * 
 * @author giorg
 *
 */

public class CompNull extends Comportamento {
	
	/**
	 * CONSTRUTTORE
	 * @param junoModel
	 */
	public CompNull (JunoModel junoModel) {super(junoModel);}

	/**
	 * METODO .AZIONE()
	 */
	@Override
	public void azione() {
		//1. Passa il turno al giocatore successivo
		junoModel.getListaGiocatori().aggiornaOrdine(junoModel.getListaGiocatori().getGiocatori().get(1));
		//2. Crea messaggio per utente
		String azioneCarta= "";
		this.junoModel.setAzioneCartaTurno(azioneCarta);
	}

}
