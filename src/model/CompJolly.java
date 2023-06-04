package model;

/** IMPORT PACKAGES */
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * CLASSE CONCRETA COMPJOLLY
 * 
 * Classe concreta di comportamento per le carte JOLLY.
 * La classe implementa l'interfaccia funzionale JollyInterface da cui 
 * implementa il metodo .scegliColore().
 * 
 * @author giorg
 *
 */

public class CompJolly extends Comportamento implements JollyInterface {
	
	/**
	 * COSTRUTTORE
	 * @param junoModel
	 */
	public CompJolly(JunoModel junoModel) {super(junoModel);}

	
	/**
	 * METODO .AZIONE()
	 */
	@Override
	public void azione() {
		//1. Imposta colore turno
		Colore colore=this.scegliColore();
		this.junoModel.setColoreTurno(colore);
		//2. Crea messaggio per utente		
		String azioneCarta= "Il nuovo colore del turno e' il " + this.junoModel.getColoreTurno().toString();
		this.junoModel.setAzioneCartaTurno(azioneCarta);}
	
	/**
	 * METODO SCEGLICOLORE()
	 */
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
