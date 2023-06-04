package controller;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import view.*;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import javax.swing.*;
import javax.swing.border.Border;
//File I/O Packages
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/** 
 * COLORBUTTONLISTENER
 * 
 * Sottoclasse concreta della classe AbstractActionListener e che implementa
 * l'interfaccia ActionListener. 
 * La classe ColorButtonListener e' definita in modo specifico per definire le azioni
 * da lanciare una volta che l'utente ha premuto uno dei bottoni contenuti nel JFrame
 * ViewColorChoose.
 * Il metodo .actionPerformed() assegna il colore selezionato dall'utente al turno 
 * corrente della partita.
 * 
 * @author giorg
 *
 */


public class ColorButtonListener extends AbstractActionListener {
	
	/**ATTRIBUTI */
	// Tutti gli attributi sono ereditati dalla classe astratta AbstractActionListener
	
	/**COSTRUTTORE*/
	public ColorButtonListener(JunoController controller) {
		super(controller);}
	
	/**METODI*/

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//1. Lancia Effetto sonoro		
		this.junoController.getSoundsManager().play(Sound.CLICKBUTTON.getPath());
		
		//2. Assegna colore selezionato a ColoreTurno
		if (e.getSource().equals(this.junoView.getViewColorChoose().getBtnRosso())) {
			this.junoModel.setColoreTurno(Colore.ROSSO);}
		if (e.getSource().equals(this.junoView.getViewColorChoose().getBtnVerde())) {
			this.junoModel.setColoreTurno(Colore.VERDE);}
		if (e.getSource().equals(this.junoView.getViewColorChoose().getBtnGiallo())) {
			this.junoModel.setColoreTurno(Colore.GIALLO);}
		if (e.getSource().equals(this.junoView.getViewColorChoose().getBtnBlu())) {
			this.junoModel.setColoreTurno(Colore.BLU);}
		
		//3. Chiudi JFrame ViewColorChoose  riattiva la JFrame ViewGame
		this.junoView.getViewColorChoose().setVisible(false);
		this.junoView.getViewGame().setVisible(true);
		this.junoView.getViewColorChoose().dispose();
		this.junoView.getViewGame().setEnabled(true);
		
		//4. Gioca il turno usando il colore scelto
		try {this.junoController.giocaTurno();} 
		catch (MazzoEsauritoException e1) {this.junoModel.getMazzo().reset();}
		
		//5. Aggiorna parametro di controllo
		this.run=true;
		
		//6. Riattiva il Timer per continuare la partita
		this.junoController.getTurnoTimerListener().restart();}


}
