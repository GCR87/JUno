package model;

/** INTERFACCIA JUNOOBSERVABLE
 * 
 * Interfaccia fondamentale dell'OBSERVER Pattern e che viene
 * implementata dal Model per poter notificare tutti gli observers
 * registrati non appena si verifica un cambiamento all'interno del 
 * model stesso.
 * 
 * @author giorg
 *
 */

public interface JunoObservable {
	
	public void registerObserver(JunoObserver o);
	public void removeObserver(JunoObserver o);
	public void notifyObservers();

}
