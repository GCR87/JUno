package model;

/** IMPORT PACKAGES */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;


/**
 * CLASSE PROFILOUTENTE
 * 
 * Classe concreta SERIALIZZABILE.
 * Viene usata per caricare e per salvare il profilo utente.
 * @author giorg
 *
 */


public class ProfiloUtente implements Serializable {
	
	/** ATTRIBUTI */
	
	//Serial Version
	private static final long serialVersionUID=-12839425834L;
	//Attributi
	private String nome;
	private int punti;
	private int partiteGiocate;
	private int partiteVinte;
	private int partitePerse;
	
	
	/** COSTRUTTORE */
	
	//Default
	public ProfiloUtente() {}
	//Overloaded
	public ProfiloUtente(String nome, int punti) {
		this.nome=nome;
		this.punti=punti;}
	
	
	/** METODI */
	
	
	// SALVA FILE
	public void salva(String fileName) {
		
		try {
			// Apertura File
			FileOutputStream fos= new FileOutputStream(fileName);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			
			// Scrittura Dati
			oos.writeObject(this.nome);
			oos.writeObject(this.punti);
			oos.writeObject(this.partiteGiocate);
			oos.writeObject(this.partiteVinte);
			oos.writeObject(this.partitePerse);
			
			// Chiusura Object Output Stream
			oos.close();}
		catch (IOException e) {e.printStackTrace();}}
	
	
	// LEGGI FILE
	public static ProfiloUtente leggi(String fileName) {
		
		try {
			// Apertura File
			FileInputStream fis=new FileInputStream(fileName);
			ObjectInputStream ois=new ObjectInputStream(fis);
			
			// Lettura Dati
			Object o1=ois.readObject();
			Object o2=ois.readObject();
			Object o3=ois.readObject();
			Object o4=ois.readObject();
			Object o5=ois.readObject();
			
			// Casting Dati
			String nome=(String)o1;
			int punti=(Integer)o2;
			int partiteGiocate=(Integer)o3;
			int partiteVinte=(Integer)o4;
			int partitePerse=(Integer)o5;
			
			// Creazione Oggetto
			ProfiloUtente o= new ProfiloUtente(nome, punti);
			o.setPartiteGiocate(partiteGiocate);
			o.setPartiteVinte(partiteVinte);
			o.setPartitePerse(partitePerse);
			
			// Chiusura Object InputStream
			ois.close();
			
			return o;}
		
		catch (ClassNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
		return null;
	}
	
	
	// SETTERS e GETTERS
	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}
	public int getPunti() {return punti;}
	public void setPunti(int punti) {this.punti = punti;}
	public int getPartiteGiocate() {return partiteGiocate;}
	public void setPartiteGiocate(int partiteGiocate) {this.partiteGiocate = partiteGiocate;}
	public int getPartiteVinte() {return partiteVinte;}
	public void setPartiteVinte(int partiteVinte) {this.partiteVinte = partiteVinte;}
	public int getPartitePerse() {return partitePerse;}
	public void setPartitePerse(int partitePerse) {this.partitePerse = partitePerse;}
	

}
