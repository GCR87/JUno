package model;

/** INTERFACCIA FUNZIONALE comportamentoCarta 
 * 
 * L'interfaccia seguente contiene un unico metodo astratto.
 * Cio' la rende un'interfaccia funzionale che consente l'utilizzo delle espressioni lambda.
 * Il metodo viene implementato dalle diverse classi di comportamento Carta seguendo il
 * modello dello STRATEGY PATTERN.
 * 
 * @author giorg
 *
 */

@FunctionalInterface
public interface ComportamentoCarta {	
	void azione() throws MazzoEsauritoException;
}
