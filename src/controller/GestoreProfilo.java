package controller;


/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import view.*;
// Java Reading Files
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JDialog;
import java.io.File;
import java.util.Hashtable;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
//File I/O Packages
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/** 
 * GESTOREPROFILO
 * 
 * Sottoclasse concreta che ha il compito di deserializzare, aggiornare e 
 * riserializzare il profilo utente con i dati correnti della partita una volta
 * essa si e' conclusa.
 * Tutte le operazioni necessarie per ottenere i risultati sopra menzionati vengono
 * nascoste all'utente all'interno di due metodi void utilizzando il FACADE 
 * DESIGN PATTERN.
 * 
 * Tecniche specifiche:
 * - FACADE Design Pattern
 * 
 * @author giorg
 *
 */


public class GestoreProfilo {
	
	/** ATTRIBUTI */
	protected JunoController junoController;
	protected ProfiloUtente profiloUtente;
	protected boolean profiloCaricato;
	protected String filePath;

	/** COSTRUTTORE */
	public GestoreProfilo(JunoController junoController) {
		this.junoController=junoController;}
	
	/** METODI */
	
	//Carica Profilo
	public void caricaProfilo() {
			//1. Seleziona file *.ser tramite OpenDialog
			File profileFile=this.getFileProfilo();
			//2. Estrai filepath
			this.filePath=profileFile.getAbsolutePath();
			//3. Deserializza il file di profilo utente
			this.profiloUtente=ProfiloUtente.leggi(this.filePath);
			//5. Assegna parametro di controllo
			this.profiloCaricato=true;}
	
	//Crea Profilo
	public void creaProfilo(String nomeProfilo) {
		this.profiloUtente=new ProfiloUtente(nomeProfilo,0);
		this.profiloUtente.salva("profiles/" + nomeProfilo + ".ser");}
	
	//Aggiorna Profilo
	public void aggiornaProfilo() {
		/*1. Estrai nome e punteggio da GiocatoreHuman del Model e assegnali
		 * 	 al Profilo Utente*/
		String nomeGiocatore =this.junoController.model.getGiocatoreHuman().getNome();
		int punteggioGiocatore=this.junoController.model.getGiocatoreHuman().getPunteggio();
		this.profiloUtente.setNome(nomeGiocatore);
		this.profiloUtente.setPunti(this.profiloUtente.getPunti()+punteggioGiocatore);
		// 2 Aggiorna conteggio partite giocate/vinte/perse del profilo utente
		if (this.junoController.model.isGameOver()) {
			this.profiloUtente.setPartiteGiocate(this.profiloUtente.getPartiteGiocate()+1);
			if (this.junoController.model.getGameWinner().equals(this.junoController.model.getGiocatoreHuman())) {
				this.profiloUtente.setPartiteVinte(this.profiloUtente.getPartiteVinte()+1);}
			else {
				this.profiloUtente.setPartitePerse(this.profiloUtente.getPartitePerse()+1);}}
		//3. Serializza il ProfiloUtente appena aggiornato
		this.profiloUtente.salva(this.filePath);}
	
	//Estrai file profilo utente serializzato
	private File getFileProfilo() {
		// Inizializzazione istanza di classe File contente il File selezionato
		File selectedFile =null;	
		// Selezione File tramite OpenFile Dialog
		JFileChooser jfc= new JFileChooser();		
		jfc.setCurrentDirectory(new File("profiles/"));
		jfc.removeChoosableFileFilter(jfc.getFileFilter());
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("Java Serialized", "ser"));
		int result= jfc.showOpenDialog(new JDialog());
		if (result==JFileChooser.APPROVE_OPTION) {
			selectedFile=jfc.getSelectedFile();}
		// Restituisci File selezionato
		return selectedFile;}
	

	//Setters e Getters
	public ProfiloUtente getProfiloUtente() {return profiloUtente;}
	public void setProfiloUtente(ProfiloUtente profiloUtente) {this.profiloUtente = profiloUtente;}
	public boolean isProfiloCaricato() {return profiloCaricato;}
	public void setProfiloCaricato(boolean profiloCaricato) {this.profiloCaricato = profiloCaricato;}
	

}
