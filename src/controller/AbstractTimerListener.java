package controller;

/** IMPORT PACKAGES */

//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.Border;


/** 
 * ABSTRACTTIMELISTENER
 * 
 * Classe astratta che implementa l'interfaccia TimerListenerInterface e definisce
 * attributi e metodi di base che devono essere ereditati da tutte le classi
 * concrete di tipo TimerListener e che il Controller utilizza per la gestione
 * dei turni e delle animazioni della View.
 * 
 * @author giorg
 *
 */

public abstract class AbstractTimerListener implements TimerListenerInterface {
	
	/** ATTRIBUTI */
	protected JunoController junoController;
	protected int delay;
	protected javax.swing.Timer timer;
	
	/** COSTRUTTORE */
	public AbstractTimerListener(JunoController junoController, int delay) {
		this.junoController=junoController;
		this.delay=delay;
		this.timer=new javax.swing.Timer(this.delay, this);
		this.timer.setRepeats(false);}	
	
	/** METODI */
	@Override
	public void start() {this.timer.start();}
	@Override
	public void stop() {this.timer.stop();}
	@Override
	public void restart() {this.timer.restart();}

}
