package JUno;
import controller.*;

/** 
 * CLASSE JUNO
 * 
 * Classe contenente il main per lanciare il gioco.
 * @author giorg
 *
 */

public class JUno {
	
	public static void main(String[] args) {
		
		JunoController junoController =new JunoController();
		junoController.inizializza();
	}

}
