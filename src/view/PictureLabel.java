package view;

/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import controller.*;
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
// General
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/** CLASSE PICTURELABEL
 * 
 * La classe PictureLabel e' costruita seguendo il FACTORY DESIGN PATTERN.
 * Essa contiene un solo metodo STATICO che crea diverse istanze di JLabel in base
 * ai parametri passati in input.
 * 
 * @author giorg
 *
 */

public abstract class PictureLabel {

	
	public static JLabel create(String fileName, int width, int height) {
		
		JLabel picLabel = new JLabel();

		ImageIcon imageIcon=new ImageIcon(fileName);
		Image image = imageIcon.getImage();
		Image newimage = image.getScaledInstance(width, height,java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimage);
		picLabel.setIcon(imageIcon);
		return picLabel;}
	
}
