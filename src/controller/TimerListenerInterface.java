package controller;


/** IMPORT PACKAGES */
//MVC Pattern Packages
import model.*;
//Java Util Packages
import java.util.Optional;
import java.util.Timer;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer.*;


/** 
 * TIMERLISTENERINTERFACE
 * 
 * Interfaccia che definisce i metodi di base che tutte le classi concrete
 * di tipo TimerListener devono implementare
 * 
 * @author giorg
 *
 */


public interface TimerListenerInterface extends ActionListener {
	public void start();
	public void restart();
	public void stop();
}
