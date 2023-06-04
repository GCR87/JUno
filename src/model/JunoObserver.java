package model;

/** INTERFACCIA JUNOOBSERVER
 * 
 * Interfaccia fondamentale dell'OBSERVER Pattern e che viene
 * implementata dalla View per poter aggiornarsi non appena 
 * si verifica un cambiamento all'interno del Model.
 * 
 * @author giorg
 *
 */

public interface JunoObserver {
	public void update();
}
