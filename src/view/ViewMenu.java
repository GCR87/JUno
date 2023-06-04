package view;

/** IMPORT PACKAGES */

//MVC Pattern Packages
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
import java.util.stream.Collectors;


/**
 * CLASSE VIEWMENU
 * 
 * Classe concreta che estende ViewUno implementando l'interfaccia JunoObserver.
 * ViewMenu consente al giocatore di selezionare le prinicpali impostazioni di gioco
 * prima di incominciare la partita.
 * 
 * Tecniche specifiche:
 * - STREAMS
 * - OBSERVER Design Pattern
 * 
 * @author giorg
 *
 */


public class ViewMenu extends ViewUno {

	
	/** ATTRIBUTI */
	
	//Componenti Java Swing
	private JPanel jpNewPlayer, jpLoadPlayer, jpEnemies, jpSettings, jpButtons;
	private JButton btnCaricaProfilo, btnGiocaPartita;
	private JLabel lblAI01, lblAI02, lblAI03, lblHuman, lblBrano;
	private JRadioButton rbMute, rbUnmute;
	private ButtonGroup btnGrpOpzioniMusica;
	private JComboBox cbAI01, cbAI02, cbAI03, cbSoundtrack;
	private JTextField tfHuman;
	
	/** COSTRUTTORE */
	public ViewMenu(JunoModel model, JunoController controller) {
		
		/* 1. RICHIAMO SUPER-COSTRUTTORE CON PASSAGGIO TITOLO FINESTRA E 
		  ASSEGNAMENTO RIFERIMENTI A MODEL E CONTROLLER */
		super("JUNO Game - Menu", model, controller);
		
		// 2. ASSEGNA PROPRIETA' al JFRAME */
		// 2.1 Dimensioni
		this.setSize(new Dimension(300,500));
		// 2.2 Posizione Relativa (input null per posizionare al centro dello schermo)
		this.setLocationRelativeTo(null);
		// 2.3 Ridimensionabilita' del JFrame
		this.setResizable(true);
		// 2.4 Stop Applicazione a chiusura del JFrame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 2.5 Assegnazione Layout Manager
		this.setLayout(new GridBagLayout());
		// 2.6 Assegnazione icona
		ImageIcon img= new ImageIcon(this.IMAGES_FOLDERPATH +"FRAME_ICON.png");
		this.setIconImage(img.getImage());
		// 2.7 Creazione Componenti
		this.creaComponenti();
		
		/* 3. ATTIVA VISIBILITA del JFrame
		 * Senza questa istruzione il this/window non compare sullo schermo */ 
		this.setVisible(true);	
	}
	
	@Override
	public void creaComponenti() {

		// 1. CREA LABELS
		this.lblHuman=new JLabel("Nickname: ");
		this.lblAI01= new JLabel("Giocatore AI01: ");
		this.lblAI02= new JLabel("Giocatore AI02: ");
		this.lblAI03= new JLabel("Giocatore AI03: ");
		this.lblBrano=new JLabel("Brano Musica: ");
		
		
		// 2. CREA TEXTFIELDS
		this.tfHuman=new JTextField(10);
		this.tfHuman.setEditable(true);
		
		// 3. CREA COMBOBOXES
		String[] giocatoriAI= {"Dotto", "Brontolo", "Pisolo", "Mammolo","Gongolo",
							   "Eolo", "Cucciolo"};
		String[] nomiBrani= this.getNomiBraniMusicali();
		
		this.cbAI01=new JComboBox(giocatoriAI);
		this.cbAI01.setBackground(Color.ORANGE);
		this.cbAI01.setEditable(false);
		this.cbAI02=new JComboBox(giocatoriAI);
		this.cbAI02.setBackground(Color.MAGENTA);
		this.cbAI02.setEditable(false);
		this.cbAI03=new JComboBox(giocatoriAI);
		this.cbAI03.setBackground(Color.CYAN);
		this.cbAI03.setEditable(false);
		this.cbSoundtrack=new JComboBox(nomiBrani);
		this.cbSoundtrack.setBackground(Color.LIGHT_GRAY);;
		this.cbSoundtrack.setEditable(false);
		
		
		// 5. CREA RADIOBUTTONS
		
		this.rbMute=new JRadioButton("MUTE");
		this.rbUnmute=new JRadioButton("UNMUTE",true);
		this.btnGrpOpzioniMusica=new ButtonGroup();
		this.btnGrpOpzioniMusica.add(rbMute);
		this.btnGrpOpzioniMusica.add(rbUnmute);
		
		
		// 6. ASSEGNA LABELS A TEXTFIELDS/COMBOBOXES/RADIOBUTTONS
		lblHuman.setLabelFor(tfHuman);
		lblAI01.setLabelFor(cbAI01);
		lblAI02.setLabelFor(cbAI02);
		lblAI03.setLabelFor(cbAI03);
		lblBrano.setLabelFor(cbSoundtrack);
		
		// 7. CREA BOTTONI
		this.btnCaricaProfilo= new JButton("CARICA PROFILO");
		this.btnCaricaProfilo.setMargin(new Insets(0,0,0,0));
		this.btnCaricaProfilo.setPreferredSize(this.btnCaricaProfilo.getPreferredSize());
		this.btnCaricaProfilo.setMinimumSize(new Dimension(50,50));
		this.btnCaricaProfilo.setMaximumSize(new Dimension(100,50));
		this.btnGiocaPartita= new JButton("GIOCA PARTITA");
		this.btnGiocaPartita.setMargin(new Insets(0,0,0,0));
		this.btnGiocaPartita.setPreferredSize(this.btnGiocaPartita.getPreferredSize());
		this.btnGiocaPartita.setMinimumSize(new Dimension(50,50));
		this.btnGiocaPartita.setMaximumSize(new Dimension(100,50));
		
		
		// 8. CREA PANNELLI CONTENITORE
		
		// 8.1 PANNELLO NUOVOGIOCATORE
		
		// 1. Creazione JPanel
		this.jpNewPlayer= new JPanel(new GridLayout(1,2));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		Border bordoInterno = BorderFactory.createTitledBorder("Nuovo Giocatore");
		Border bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		Border bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpNewPlayer.setBorder(bordoFinale);
		
		// 8.2 PANNELLO CARICAGIOCATORE

		// 1. Creazione JPanel
		this.jpLoadPlayer= new JPanel(new GridLayout(1,1));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createTitledBorder("Carica Giocatore");
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpLoadPlayer.setBorder(bordoFinale);
		
		// 8.3 PANNELLO AVVERSARI

		// 1. Creazione JPanel
		this.jpEnemies= new JPanel(new GridLayout(3,2));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createTitledBorder("Avversari AI");
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		this.jpEnemies.setBorder(bordoFinale);

		// 8.4 PANNELLO SETTINGS

		// 1. Creazione JPanel
		this.jpSettings =new JPanel(new GridLayout(3,2));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createTitledBorder("Impostazioni");
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpSettings.setBorder(bordoFinale);
		
		// 8.5 PANNELLO BOTTONI 
		
		// 1. Creazione JPanel
		this.jpButtons= new JPanel(new GridLayout(1,3));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createLineBorder(Color.BLACK, 3);
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpButtons.setBorder(bordoFinale);		
		
		
		// 9. ASSEGNA COMPONENTI A PANNELLI
		
		// 9.1 PANNELLO NUOVOGIOCATORE
		jpNewPlayer.add(this.lblHuman,0,0);
		jpNewPlayer.add(this.tfHuman,0,1);
		
		// 9.2 PANNELLO CARICAGIOCATORE
		jpLoadPlayer.add(this.btnCaricaProfilo);
		
		// 9.3 PANNELLO AVVERSARI
		jpEnemies.add(this.lblAI03,2,0);
		jpEnemies.add(this.cbAI03,2,1);
		jpEnemies.add(this.lblAI02,1,0);
		jpEnemies.add(this.cbAI02,1,1);
		jpEnemies.add(this.lblAI01,0,0);
		jpEnemies.add(this.cbAI01,0,1);

		
		// 9.4 PANNELLO SETTINGS

		jpSettings.add(this.rbUnmute,1,0);
		jpSettings.add(this.rbMute,1,1);
		jpSettings.add(this.lblBrano,0,0);
		jpSettings.add(this.cbSoundtrack,0,1);
		
		// 9.5 PANNELLO BOTTONI
		jpButtons.add(this.btnGiocaPartita);
		
		
		
		// 10. CREA GRIDBAGCONSTRAINTS
		
		// 10.1 PANNELLO NUOVOGIOCATORE
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcNewPlayer = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcNewPlayer.gridx=0;
		gbcNewPlayer.gridy=0;
		/* Pesi nelle due direzioni */
		gbcNewPlayer.weightx=0.1;
		gbcNewPlayer.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcNewPlayer.gridwidth=1;
		gbcNewPlayer.gridheight=1; 
		/* Zona di ancoraggio */
		gbcNewPlayer.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcNewPlayer.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcNewPlayer.fill=GridBagConstraints.BOTH;
		
		// 10.2 PANNELLO CARICAGIOCATORE
	
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcLoadPlayer = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcLoadPlayer.gridx=0;
		gbcLoadPlayer.gridy=1;
		/* Pesi nelle due direzioni */
		gbcLoadPlayer.weightx=0.1;
		gbcLoadPlayer.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcLoadPlayer.gridwidth=1;
		gbcLoadPlayer.gridheight=1; 
		/* Zona di ancoraggio */
		gbcLoadPlayer.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcLoadPlayer.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcLoadPlayer.fill=GridBagConstraints.BOTH;
		
		// 10.3 PANNELLO AVVERSARI
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcEnemies = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcEnemies.gridx=0;
		gbcEnemies.gridy=2;
		/* Pesi nelle due direzioni */
		gbcEnemies.weightx=0.1;
		gbcEnemies.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcEnemies.gridwidth=1;
		gbcEnemies.gridheight=3; 
		/* Zona di ancoraggio */
		gbcEnemies.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcEnemies.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcEnemies.fill=GridBagConstraints.BOTH;
		
		// 10.4 PANNELLO SETTINGS
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcSettings = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcSettings.gridx=0;
		gbcSettings.gridy=5;
		/* Pesi nelle due direzioni */
		gbcSettings.weightx=0.1;
		gbcSettings.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcSettings.gridwidth=1;
		gbcSettings.gridheight=1; 
		/* Zona di ancoraggio */
		gbcSettings.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcSettings.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcSettings.fill=GridBagConstraints.BOTH;
		
		// 10.5 PANNELLO BOTTONI
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcButtons = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcButtons.gridx=0;
		gbcButtons.gridy=6;
		/* Pesi nelle due direzioni */
		gbcButtons.weightx=0.1;
		gbcButtons.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcButtons.gridwidth=1;
		gbcButtons.gridheight=1; 
		/* Zona di ancoraggio */
		gbcButtons.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcButtons.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcButtons.fill=GridBagConstraints.BOTH;
		

		// 11. ASSEGNA PANNELLI A FRAME
		this.getContentPane().add(jpNewPlayer,gbcNewPlayer);
		this.getContentPane().add(jpLoadPlayer,gbcLoadPlayer);
		this.getContentPane().add(jpEnemies,gbcEnemies);
		this.getContentPane().add(jpSettings,gbcSettings);
		this.getContentPane().add(jpButtons,gbcButtons);
		

		// 	12. TURN ON FRAME VISIBILITY
		this.setVisible(true);			
		
	}
	
	
	public String[] getNomiBraniMusicali() {
		
		//1. Estrai file musicali
		File[] filesArray = new File(this.MUSIC_FOLDERPATH).listFiles();
		
		//2. Estrai nomi file musicali - tramite STREAMS
		String[] nomiBrani=new String[filesArray.length];
		Arrays.stream(filesArray)
				//1. Filtra solo i files con extension .wav
				.filter((file)->((file.isFile())&&(file.getName().contains(".wav"))))
				//2. Mappa da File a Nome File
				.map((file)->(file.getName()))
				//3. Mappa da Nome File a Nome File Without Extension
				.map((fileName)->(fileName.substring(0,fileName.lastIndexOf("."))))
				//4. Converti lo stream in un Array
				.collect(Collectors.toList()).toArray(nomiBrani);
		
		//3. Ritorna array nomi brani musicali
		return nomiBrani;
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	
	// SETTERS and GETTERS
	public JPanel getJpNewPlayer() {return jpNewPlayer;}
	public void setJpNewPlayer(JPanel jpNewPlayer) {this.jpNewPlayer = jpNewPlayer;}
	public JPanel getJpLoadPlayer() {return jpLoadPlayer;}
	public void setJpLoadPlayer(JPanel jpLoadPlayer) {this.jpLoadPlayer = jpLoadPlayer;}
	public JPanel getJpEnemies() {return jpEnemies;}
	public void setJpEnemies(JPanel jpEnemies) {this.jpEnemies = jpEnemies;}
	public JPanel getJpSettings() {return jpSettings;}
	public void setJpSettings(JPanel jpSettings) {this.jpSettings = jpSettings;}
	public JPanel getJpButtons() {return jpButtons;}
	public void setJpButtons(JPanel jpButtons) {this.jpButtons = jpButtons;}
	public JButton getBtnCaricaProfilo() {return btnCaricaProfilo;}
	public void setBtnCaricaProfilo(JButton btnCaricaProfilo) {this.btnCaricaProfilo = btnCaricaProfilo;}
	public JButton getBtnGiocaPartita() {return btnGiocaPartita;}
	public void setBtnGiocaPartita(JButton btnGiocaPartita) {this.btnGiocaPartita = btnGiocaPartita;}
	public JLabel getLblAI01() {return lblAI01;}
	public void setLblAI01(JLabel lblAI01) {this.lblAI01 = lblAI01;}
	public JLabel getLblAI02() {return lblAI02;}
	public void setLblAI02(JLabel lblAI02) {this.lblAI02 = lblAI02;}
	public JLabel getLblAI03() {return lblAI03;}
	public void setLblAI03(JLabel lblAI03) {this.lblAI03 = lblAI03;}
	public JLabel getLblHuman() {return lblHuman;}
	public void setLblHuman(JLabel lblHuman) {this.lblHuman = lblHuman;}
	public JLabel getLblSoundtrack() {return lblBrano;}
	public void setLblSoundtrack(JLabel lblBrano) {this.lblBrano = lblBrano;}
	public JComboBox getCbAI01() {return cbAI01;}
	public void setCbAI01(JComboBox cbAI01) {this.cbAI01 = cbAI01;}
	public JComboBox getCbAI02() {return cbAI02;}
	public void setCbAI02(JComboBox cbAI02) {this.cbAI02 = cbAI02;}
	public JComboBox getCbAI03() {return cbAI03;}
	public void setCbAI03(JComboBox cbAI03) {this.cbAI03 = cbAI03;}
	public JComboBox getCbSoundtrack() {return cbSoundtrack;}
	public void setCbSoundtrack(JComboBox cbSoundtrack) {this.cbSoundtrack = cbSoundtrack;}
	public JTextField getTfHuman() {return tfHuman;}
	public void setTfHuman(JTextField tfHuman) {this.tfHuman = tfHuman;}
	public JRadioButton getRbMute() {return rbMute;}
	public void setRbMute(JRadioButton rbMute) {this.rbMute = rbMute;}
	public JRadioButton getRbUnmute() {return rbUnmute;}
	public void setRbUnmute(JRadioButton rbUnmute) {this.rbUnmute = rbUnmute;}
	public ButtonGroup getBtnGrpOpzioniMusica() {return btnGrpOpzioniMusica;}
	public void setBtnGrpOpzioniMusica(ButtonGroup btnGrpOpzioniMusica) {this.btnGrpOpzioniMusica = btnGrpOpzioniMusica;}
	
}
