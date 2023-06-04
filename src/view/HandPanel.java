package view;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import controller.*;
import model.*;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
//File I/O Packages
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
//General
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/** CLASSE HANDPANEL
 * 
 * La classe HandPanel estende la superclasse JPanel e contiene tutte le CartaPanels
 * corrispondenti alle carte contenute nella mano del giocatore corrispondente.
 * 
 * @author giorg
 *
 */

public class HandPanel extends JPanel {
	
	/** ATTRIBUTI */
	private List<CartaPanel> cartaPanels;
	
	/** COSTRUTTORE */
	public HandPanel() {
		this.cartaPanels=new ArrayList<>();
		this.setLayout(new FlowLayout());
		((FlowLayout)this.getLayout()).setHgap(-20);}
	
	/** METODI */
	
	// Setters and Getters
	public void setCartaPanels(List<CartaPanel> cartaPanels) {
		this.clearHand();
		this.cartaPanels=cartaPanels;}
	public List<CartaPanel> getCartaPanels() {
		return this.cartaPanels;}
	
	// Clear
	public void clearHand() {
		this.cartaPanels.clear();
		this.removeAll();}
	
    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }
}
