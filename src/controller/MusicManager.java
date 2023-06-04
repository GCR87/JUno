package controller;

/** IMPORT PACKAGES */

//Java.IO Packages
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//Javax.Sound Packages
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


/** 
 * MUSICMANAGER
 * 
 * Classe concreta che implementa l'interfaccia AudioManagerInterface e che 
 * e' responsabile per la riproduzione dei BRANI MUSICALI del gioco (sempre
 * se attivo...).
 * 
 * Tecniche Specifiche
 * - SINGLETON Design Pattern
 * 
 * @author giorg
 *
 */

public class MusicManager implements AudioManagerInterface {

	/** ATTRIBUTI */
	private static MusicManager instance;
	private boolean active=true;
	
	/** COSTRUTTORE PRIVATO */
	private MusicManager() {}

	/** METODO STATICO .getInstance() */
	public static MusicManager getInstance() {
		if (instance == null)
			instance = new MusicManager();
		return instance;
	}


	/** METODI */
	
	// Play
	@Override
	public void play(String filename) {
		//Esegui Azioni solo se il PassaTurnoListener e' attivo (ovvero, se il giocatore del turno e' l'utente)
		if (this.active) {
			try {
				//1. Apri file audio
				InputStream in = new BufferedInputStream(new FileInputStream(filename));
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				//2. Abbassa volume se l'audio e' una musica di sottofondo
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				if (filename.contains("music")) {gainControl.setValue(-20.0f);}
				//3. Imposta Loop continuo
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				//4. Riproduci file audio
				clip.start();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	// Setters & Getters
	@Override
	public boolean isActive() {return active;}
	@Override
	public void setActive(boolean active) {this.active = active;}
	

}