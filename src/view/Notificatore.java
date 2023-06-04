package view;

/** IMPORT PACKAGES */

// MVC Pattern Packages
import model.*;
import controller.*;
//Java Swing Packages
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
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



/**
 * CLASSE NOTIFICATORE
 * 
 * Classe concreta che estende la classe astratta AbstractNotificatore e che ha il compito
 * di costruire le notifiche da inviare all'utente informandolo su quanto sta avvenendo nel
 * gioco.
 * La classe utilizza il BUILDER Design Pattern.
 * 
 * Tecniche Specifiche:
 * - BUILDER Design Pattern
 * 
 * @author giorg
 *
 */

public class Notificatore extends AbstractNotificatore {

	/** COSTRUTTORE */
	public Notificatore (JunoModel junoModel, JunoController junoController) {
		super(junoModel, junoController);}
	
	
	/** METODI */
	
	// Factory Method
	@Override
	public String creaNotifica() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.sceltaMazziere()).append(this.giocataMazziere()).append(this.giocataGiocatore())
					.append(this.chiamataUno()).append(this.vittoriaRound()).append(this.vittoriaPartita());
		return sb.toString();}
	
	// sceltaMazziere
	@Override
	public String sceltaMazziere() {
		String testo="";
		if ((this.junoModel.getFasePartita().toString().equals("MAZZIERE"))&&(!this.junoModel.isGameOver())) {
			testo=this.junoModel.getMazziere().getNome() + " e' il mazziere designato per incominciare il round";}
		return testo;}
	
	// giocataMazziere
	@Override
	public String giocataMazziere() {
		String testo="";
		if (this.junoModel.getFasePartita().toString().equals("PREPARATIVI")) {
			testo="Il mazziere " + this.junoModel.getMazziere().getNome() + " alza e gioca la carta " + 
			this.junoModel.getGiocatoreTurno().getCartaGiocata().getNome() + "\n" + this.junoModel.getAzioneCartaTurno() + "." +
		 	"\nIl turno passa al giocatore " + this.junoModel.getGiocatoreSuccessivo().getNome() + ".";}
		return testo;}

	// giocataGiocatore
	@Override
	public String giocataGiocatore() {
		String testo="";
		if ((this.junoModel.getFasePartita().toString().equals("PARTITA"))&&(this.junoModel.isGiocataOver())&&(!this.junoModel.isGameOver())) {
			if (!this.junoModel.getGiocatoreTurno().getClass().getSimpleName().equals("GiocatoreHuman")) {
				if (this.junoModel.getGiocatoreTurno().getCartaGiocata()!=null) {
					if (this.junoModel.getGiocatoreTurno().getNumeroCartePescate()==0) {
							testo="Il giocatore " + this.junoModel.getGiocatoreTurno().getNome() + " gioca la carta " 
									+ this.junoModel.getGiocatoreTurno().getCartaGiocata().getNome() + ".\n" + this.junoModel.getAzioneCartaTurno() 
									+ "." + "\nIl turno passa al giocatore " + this.junoModel.getGiocatoreSuccessivo().getNome() + ".";}
					else{	testo="Il giocatore " + this.junoModel.getGiocatoreTurno().getNome() + " pesca " +
							  		this.junoModel.getGiocatoreTurno().getNumeroCartePescate() + " carta e gioca la carta " + 
							  		this.junoModel.getGiocatoreTurno().getCartaGiocata().getNome() + ".\n" + this.junoModel.getAzioneCartaTurno() 
							  		+ "." + "\nIl turno passa al giocatore " + this.junoModel.getGiocatoreSuccessivo().getNome() + ".";}}
				else {testo=this.junoModel.getGiocatoreTurno().getNome() + " non ha nessuna carta giocabile \n" + 
							"e passa il turno al giocatore " + this.junoModel.getGiocatoreSuccessivo().getNome();}}
			else {
				if ((this.junoModel.getGiocatoreTurno().getCartaGiocata()!=null)) {
					if (this.junoModel.getGiocatoreTurno().getNumeroCartePescate()==0) {
							testo="Il giocatore " + this.junoModel.getGiocatoreTurno().getNome() + " gioca la carta " 
									+ this.junoModel.getGiocatoreTurno().getCartaGiocata().getNome() + ".\n" + this.junoModel.getAzioneCartaTurno() 
									+ "." + "\nIl turno passa al giocatore " + this.junoModel.getGiocatoreSuccessivo().getNome() + ".";}
					else{	testo="Il giocatore " + this.junoModel.getGiocatoreTurno().getNome() + " pesca " +
							  		this.junoModel.getGiocatoreTurno().getNumeroCartePescate() + " carta e gioca la carta " + 
							  		this.junoModel.getGiocatoreTurno().getCartaGiocata().getNome() + ".\n" + this.junoModel.getAzioneCartaTurno() 
							  		+ "." + "\nIl turno passa al giocatore " + this.junoModel.getGiocatoreSuccessivo().getNome() + ".";}}}}
		return testo;}

	// chiamataUno
	@Override
	public String chiamataUno() {
		String testo="";
		String fasePartita=this.junoModel.getFasePartita().toString();
		if ((fasePartita.equals("PARTITA"))&&
			(this.junoModel.getGiocatoreTurno().getCalledUno().equals(UnoCall.DONE))) {
					testo="\n\nIl giocatore " + this.junoModel.getGiocatoreTurno().getNome() + " ha chiamato UNO!!!";}
		else if ((fasePartita.equals("PARTITA"))&&
				(this.junoModel.getGiocatoreTurno().getCalledUno().equals(UnoCall.MISSED))) {
						testo="\n\nIl giocatore " + this.junoModel.getGiocatoreTurno().getNome() + " si e' dimenticato di chiamare"
							   + "UNO! pur avendo una sola carta...quindi pesca 2 carte per penalita'";}
		return testo;}

	// vittoriaRound
	@Override
	public String vittoriaRound() {
		String testo="";
		if (this.junoModel.isRoundOver()) {
			testo="\n\nIl giocatore " + this.junoModel.getGiocatoreTurno().getNome() + " ha vinto il Round!!";}
		return testo;}

	// vittoriaPartita
	@Override
	public String vittoriaPartita() {
		String testo="";
		if (this.junoModel.isGameOver()) {
			testo="\n\nIl giocatore " + this.junoModel.getGiocatoreTurno().getNome() + " ha vinto la Partita!!";}
		return testo;}
}
