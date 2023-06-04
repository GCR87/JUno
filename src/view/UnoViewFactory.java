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
//General
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UnoViewFactory {
	
	private JunoController junoController;
	private JunoModel junoModel;
	
	private static UnoViewFactory instance;
	
	private UnoViewFactory() {};
	
	public static UnoViewFactory getInstance() {
		if (instance==null) {return instance=new UnoViewFactory();}
		return instance;}
	
	public void setController(JunoController junoController) {this.junoController=junoController;}
	public void setModel(JunoModel junoModel) {this.junoModel=junoModel;}
	
	
	public ViewUno creaView(ViewName viewName){
		switch (viewName.toString()) {
		case "GAME": return (new ViewGame(this.junoModel, this.junoController));
		case "MENU": return (new ViewMenu(this.junoModel, this.junoController));
		default: return null;}
		}
	}

