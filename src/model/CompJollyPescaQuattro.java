package model;

/** IMPORT PACKAGES */
import java.util.Random;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.stream.Stream;


/**
 * CLASSE CONCRETA COMPJOLLYPESCAQUATTRO
 * 
 * Classe concreta di comportamento per le carte JOLLY PESCAQUATTRO.
 * La classe implementa l'interfaccia funzionale JollyInterface da cui 
 * implementa il metodo .scegliColore().
 * 
 * @author giorg
 *
 */

public class CompJollyPescaQuattro extends Comportamento implements JollyInterface {

	public CompJollyPescaQuattro(JunoModel junoModel) {super(junoModel);}
	
	@Override
	public void azione() throws MazzoEsauritoException {
		
		//1. Imposta colore turno
		Colore colore=this.scegliColore();
		this.junoModel.setColoreTurno(colore);
		
		//2. Penalizza giocatore colpito
		Giocatore giocatoreColpito;
		giocatoreColpito=this.junoModel.getListaGiocatori().getGiocatori().get(1);
		giocatoreColpito.pesca(4, this.junoModel.getMazzoPesca());
		junoModel.getListaGiocatori().aggiornaOrdine(junoModel.getListaGiocatori().getGiocatori().get(2));
		
		//3. Crea messaggio per utente	
		String azioneCarta= "Il giocatore " + giocatoreColpito.getNome() + " pesca 4 carte e salta il prossimo turno."
							+ "\nIl nuovo colore del turno e' il " + this.junoModel.getColoreTurno().toString();
		this.junoModel.setAzioneCartaTurno(azioneCarta);
	}
	
	@Override
	public Colore scegliColore() {
		//1. Inizializzazione parametri ausiliari
		Random rnd =new Random();
		ArrayList<Colore> colori=new ArrayList<Colore>();
		//2. Estrai colori consentiti tramite STREAMS
		colori=Stream.of(Colore.values())
				.filter((clr)->!((clr.equals(Colore.NULL))||(clr.equals(junoModel.getColoreTurno()))))
				.collect(Collectors.toCollection(()->new ArrayList<Colore>()));
		//3. Ricava colore random
		int upperBound=colori.size()-1;
		return colori.get(rnd.nextInt(upperBound));}
		


}
