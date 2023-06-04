package model;

/** 
 * CLASSE FACTORY CONCRETA per CARTE AZIONE
 * 
 * Classe usata per creazione delle carte del gioco Uno.
 * 
 * Tecniche Specifiche:
 * - STREAMS 
 * - FACTORY Design Pattern
 * 
 */

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnoCardsFactory extends CardsFactory {
	
	/** FACTORY METHOD */
		protected static Stack<Carta> creaCarte(JunoModel model) {
			//Variabili Locali
			Stack<Carta> carte = new Stack<Carta>();
					
			// Creazione carte NUMERICHE tramite uso STREAMS
			Stream.of(Simbolo.values())
			.filter(simbolo->!simbolo.toString().contains("JOLLY"))
			.filter(simbolo->simbolo.getvalore()<10)
			.forEach(smbc->{for(Colore colore:Colore.values()) {
							  if (colore!=colore.NULL) {	
								int i=0;
								while(i<smbc.getIstanze()) {
									carte.push(new CartaNumerica(smbc,colore,model));
									i++;}}}});

			
			// Creazione carte JOLLY tramite uso STREAMS
			Stream.of(Simbolo.values())
			.filter(simbolo->simbolo.toString().contains("JOLLY"))
			.forEach(smbc->{switch (smbc.toString()) 
				{case "JOLLY": 
					int i=0;
					while(i<smbc.getIstanze()) {
						carte.push(new CartaJolly(smbc,Colore.NULL,model));
						i++;}
				    break;
				 case "JOLLYPESCAQUATTRO": 
					 i=0;
					while(i<smbc.getIstanze()) {
						carte.push(new CartaJollyPescaQuattro(smbc,Colore.NULL,model));
						i++;}
					break;}});
			
			
			// Creazione carte AZIONE tramite uso STREAMS
			Stream.of(Simbolo.values())
			.filter(simbolo->!simbolo.toString().contains("JOLLY"))
			.filter(simbolo->simbolo.getvalore()>10)
			.forEach(smbc->{
				for (Colore colore:Colore.values()) {
					if (colore!=Colore.NULL) {
						switch (smbc.toString()) 
						{case "PESCADUE": 
							int i=0;
							while(i<smbc.getIstanze()) {
								carte.push(new CartaPescaDue(smbc,colore,model));
								i++;}
							break;
						 case "INVERTI": 
							i=0;
							while(i<smbc.getIstanze()) {
								carte.push(new CartaInverti(smbc,colore,model));
								i++;}
							break;
						 case "SALTO":
							i=0;
							while(i<smbc.getIstanze()) {
								carte.push(new CartaSalto(smbc,colore,model));
								i++;}
							 break;}}}});
					
	return carte;}	
} 
