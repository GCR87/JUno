package model;

public class CompInverti extends Comportamento {
	
	public CompInverti(JunoModel junoModel) {super(junoModel);}
	
	@Override
	public void azione() {
		//1. Inverti l'ordine dei giocatori
		this.junoModel.getListaGiocatori().invertiOrdine();
		this.junoModel.getListaGiocatori().aggiornaOrdine(this.junoModel.getListaGiocatori().getGiocatori().get(0));
		//2. Crea messaggio per utente
		String azioneCarta= "L'ordine dei giocatori si inverte";
		this.junoModel.setAzioneCartaTurno(azioneCarta);
	}

}
