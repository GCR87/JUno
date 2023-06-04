package controller;

/** 
 * AUDIOMANAGERLISTENER
 * 
 * Interfaccia che definisce i metodi di base che tutte le classi concrete
 * di tipo AudioManager devono implementare.
 * 
 * @author giorg
 *
 */

public interface AudioManagerInterface {
	
	public void play (String fileName);
	public boolean isActive();
	public void setActive(boolean active);
}
