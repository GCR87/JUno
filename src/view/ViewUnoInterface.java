package view;

/** INTERFACCIA VIEWUNOINTERFACE
 * 
 * L'interfaccia definisce le costanti utili e i metodi che 
 * tutte le views/jframes del gioco Uno devono implementare.
 * 
 * @author giorg
 *
 */

public interface ViewUnoInterface {
	
	/**ATTRIBUTI*/
	final String IMAGES_FOLDERPATH="images/";
	final String SOUNDS_FOLDERPATH="sounds/";
	final String MUSIC_FOLDERPATH="music/";
	
	/**METODI*/
	abstract public void creaComponenti(); 

}
