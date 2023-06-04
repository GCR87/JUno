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
 * UNOLISTENER
 * 
 * Sottoclasse concreta della classe AbstractActionListener e che implementa
 * l'interfaccia ActionListener. 
 * La classe UnoListener e' definita in modo specifico per definire le azioni
 * da lanciare una volta che l'utente ha premuto il bottone UNO! nella JFrame
 * ViewGame.
 * Il metodo .actionPerformed() chiama il metodo .callUno di GiocatoreHuman 
 * e disattiva il bottone in modo da impedire che venga cliccato nuovamente
 * nello stesso turno.
 * 
 * @author giorg
 *
 */

public class UnoListener extends AbstractActionListener {

	/** ATTRIBUTI */
	// Tutti gli attributi sono ereditati dalla classe astratta AbstractListener	
	
	/** COSTRUTTORE */
	public UnoListener(JunoController junoController) {
		super(junoController);}
	
	/** METODI */
	@Override
	public void actionPerformed(ActionEvent e) {
		/*Esegui Azioni solo se l'UnoListener e' attivo (ovvero, se il giocatore del turno e' l'utente)*/
		if (this.active) {
			//1. Lancia effetto sonoro
			this.junoController.getSoundsManager().play(Sound.UNOBUTTON.getPath());
			//2. Chiama il metodo .callUno() di GiocatoreHuman
			this.junoView.getViewGame().getJpHuman().getGiocatore().callUno();
			//3. Scrivi notifica 
			this.junoView.getViewGame().getJtaAzioneTurno().setText("Il giocatore " +
					this.junoView.getViewGame().getJpHuman().getGiocatore().getNome() +
					" ha chiamato UNO!!");
			this.junoView.getViewGame().repaint();
			this.junoView.getViewGame().setVisible(true);
			//4. Disattiva ActionListener e corrispondente bottone
			this.setActive(false);
			((JButton)e.getSource()).setEnabled(false);
			//5. Aggiorna parametro di controllo
			this.run=true;}}
}
