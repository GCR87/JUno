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


/**
 * CLASSE VIEWGAME
 * 
 * Classe concreta che estende ViewUno implementando l'interfaccia JunoObserver.
 * ViewGame e' la view principale dove viene eseguito il gioco e comunica con 
 * il Model tramite il Controller (in uscita) e tramite l'OBSERVER Pattern 
 * (in entrata).
 * 
 * Tecniche specifiche:
 * - STREAMS
 * - OBSERVER Design Pattern
 * 
 * @author giorg
 *
 */


public class ViewGame extends ViewUno implements JunoObserver {
	
	/** ATTRIBUTI */

	//Componenti Java Swing
	private PlayerPanel jpHuman;
	private List<PlayerPanel> jpAIs;
	private DeckPanel jpMazzo;
	private JPanel jpTurno;
	private JLabel jlNumRound, jlNumTurno, jlPlayerTurno, jlVincitore;
	private JLabel jlPointsHuman, jlPointsAI01, jlPointsAI02, jlPointsAI03;
	private JLabel jlColoreTurno, jlSimboloTurno;
	private JTextField jtfNumRound, jtfNumTurno, jtfPlayerTurno, jtfVincitore;
	private JTextField jtfPointsHuman, jtfPointsAI01, jtfPointsAI02, jtfPointsAI03;
	private JTextField jtfColoreTurno, jtfSimboloTurno;	
	private JTextArea jtaAzioneTurno;
	private JButton btnPescaCarta, btnPassaTurno, btnUNO;
	
	//Notificatore
	private Notificatore notificatore;
	
	
		
	/** COSTRUTTORE */
	public ViewGame(JunoModel model, JunoController controller) {
		
		/* 1. RICHIAMO SUPER-COSTRUTTORE CON PASSAGGIO TITOLO FINESTRA E 
			  ASSEGNAMENTO RIFERIMENTI A MODEL E CONTROLLER */
		super("JUNO Game - Game Play", model, controller);
		
		// 2. ASSEGNA PROPRIETA' al JFRAME */
		// 2.1 Dimensioni
		this.setSize(new Dimension(1300,700));
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
		
		/* 4. CREA NOTIFIATORE */
		this.notificatore=new Notificatore(this.junoModel, this.junoController);
	}
	
	@Override
	public void creaComponenti() {
			
		// 1. CREA PLAYERPANELS
		jpAIs=new ArrayList<PlayerPanel>();
		for (Giocatore giocatore:this.junoModel.getListaGiocatori().getGiocatori()) {
			if (giocatore.getClass().getSimpleName().equals("GiocatoreHuman")) {
				jpHuman = new PlayerPanel(giocatore);}
//				jpHuman.setMaximumSize(new Dimension(500,500));
			else {jpAIs.add(new PlayerPanel(giocatore));}}
		
		// 2. CREA DECKPANEL
		this.jpMazzo= new DeckPanel(this.junoModel.getMazzo());
		
		// 3. CREA LABELS
		this.jlNumRound= new JLabel("Round Numero: ");
		this.jlNumTurno= new JLabel("Turno Numero: ");
		this.jlPlayerTurno= new JLabel("Giocatore Turno: ");
		this.jlVincitore=new JLabel("Vincitore: ");
		this.jlPointsHuman= new JLabel(jpHuman.getGiocatore().getNome() + " :"); 
		this.jlPointsAI01= new JLabel(jpAIs.get(0).getGiocatore().getNome() + " :");
		this.jlPointsAI02= new JLabel(jpAIs.get(1).getGiocatore().getNome() + " :");
		this.jlPointsAI03= new JLabel(jpAIs.get(2).getGiocatore().getNome() + " :");
		this.jlColoreTurno= new JLabel("Colore Turno: ");
		this.jlSimboloTurno= new JLabel("Simbolo Turno: ");
		
		// 4. CREA TEXTFIELDS
		this.jtfNumRound= new JTextField(10);
		this.jtfNumRound.setEditable(false);
		this.jtfNumTurno= new JTextField(10);
		this.jtfNumTurno.setEditable(false);
		this.jtfPlayerTurno= new JTextField(10);
		this.jtfPlayerTurno.setEditable(false);
		this.jtfVincitore=new JTextField(10);
		this.jtfVincitore.setEditable(false);
		this.jtfPointsHuman= new JTextField(10);
		this.jtfPointsHuman.setEditable(false);
		this.jtfPointsAI01= new JTextField(10);
		this.jtfPointsAI01.setEditable(false);
		this.jtfPointsAI02= new JTextField(10);
		this.jtfPointsAI02.setEditable(false);
		this.jtfPointsAI03= new JTextField(10);
		this.jtfPointsAI03.setEditable(false);
		this.jtaAzioneTurno= new JTextArea();
		this.jtaAzioneTurno.setEditable(false);
		this.jtaAzioneTurno.setLineWrap(true);
		this.jtaAzioneTurno.setWrapStyleWord(true);
		this.jtfColoreTurno= new JTextField(5);
		this.jtfColoreTurno.setBackground(Color.WHITE);
		this.jtfColoreTurno.setEditable(false);
		this.jtfSimboloTurno= new JTextField(5);	
		this.jtfSimboloTurno.setEditable(false);
		this.jtfSimboloTurno.setHorizontalAlignment(SwingConstants.CENTER);
		
		// 5. ASSEGNA LABELS A TEXTFIELDS
		jlNumRound.setLabelFor(jtfNumRound);
		jlNumTurno.setLabelFor(jtfNumTurno);
		jlPlayerTurno.setLabelFor(jtfPlayerTurno);
		jlPointsHuman.setLabelFor(jtfPointsHuman);
		jlPointsAI01.setLabelFor(jtfPointsAI01);
		jlPointsAI02.setLabelFor(jtfPointsAI02);
		jlPointsAI03.setLabelFor(jtfPointsAI03);
		jlColoreTurno.setLabelFor(jtfColoreTurno);
		jlSimboloTurno.setLabelFor(jtfSimboloTurno);
		
		// 6. CREA BOTTONI
		this.btnPescaCarta= new JButton("PESCA");
		this.btnPescaCarta.setMargin(new Insets(0,0,0,0));
//		this.btnPescaCarta.setMinimumSize(new Dimension(50,50));
		this.btnPescaCarta.setPreferredSize(this.btnPescaCarta.getPreferredSize());
//		this.btnPescaCarta.setMaximumSize(new Dimension(100,50));
		this.btnPassaTurno= new JButton("PASSA TURNO");
		this.btnPassaTurno.setMargin(new Insets(0,0,0,0));
		this.btnPassaTurno.setPreferredSize(this.btnPassaTurno.getPreferredSize());
//		this.btnPassaTurno.setMinimumSize(new Dimension(150,50));
//		this.btnPassaTurno.setMaximumSize(new Dimension(200,50));
		this.btnUNO= new JButton("UNO!!");
		this.btnUNO.setMargin(new Insets(0,0,0,0));
		this.btnUNO.setPreferredSize(this.btnUNO.getPreferredSize());
//		this.btnUNO.setMinimumSize(new Dimension(50,50));
//		this.btnUNO.setMaximumSize(new Dimension(100,50));
		
		// 7. CREA PANNELLI CONTENITORE
		
		// 7.1 PANNELLO GAMEBOARD
		
		// 1. Creazione JPanel
		JPanel jpGameBoard= new JPanel(new GridBagLayout());
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		Border bordoInterno = BorderFactory.createTitledBorder("Game Board");
		Border bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		Border bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpGameBoard.setBorder(bordoFinale);
		
		// 7.2 PANNELLO INFOPARTITA

		// 1. Creazione JPanel
		JPanel jpInfoPartita= new JPanel(new GridLayout(5,1));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createTitledBorder("Info Partita");
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpInfoPartita.setBorder(bordoFinale);
		
		// 7.3 PANNELLO DATITURNO

		// 1. Creazione JPanel
		JPanel jpDatiTurno= new JPanel(new GridLayout(4,2));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createTitledBorder("Dati Turno");
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpDatiTurno.setBorder(bordoFinale);

		// 7.4 PANNELLO PUNTEGGI

		// 1. Creazione JPanel
		JPanel jpPunteggi =new JPanel(new GridLayout(4,2));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createTitledBorder("Punteggi");
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpPunteggi.setBorder(bordoFinale);
		
		// 7.5 PANNELLO AZIONETURNO

		// 1. Creazione JPanel
		JPanel jpAzioneTurno= new JPanel(new GridLayout(1,1));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createTitledBorder("Azione Turno");
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpAzioneTurno.setBorder(bordoFinale);
		
		// 7.6 PANNELLO BOTTONI 
		
		// 1. Creazione JPanel
		JPanel jpComandiGiocatore= new JPanel(new GridLayout(1,3));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoInterno = BorderFactory.createTitledBorder("Comandi Giocatore");
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpComandiGiocatore.setBorder(bordoFinale);		
	
		// 7.7 PANNELLO COLORE E SIMBOLO TURN0
		
		// 1. Creazione JPanel
		JPanel jpTurno= new JPanel(new GridLayout(2,2));
		// 2. Assegnamento Border
		/* 2.1 Borders singoli da combinare in un border di tipo compound */
		bordoEsterno= BorderFactory.createEmptyBorder(5, 5, 5, 5);
		bordoInterno= BorderFactory.createLineBorder(Color.BLACK, 3);
		/* 2.2 Border compound che combina i borders creati sopra */
		bordoFinale=BorderFactory.createCompoundBorder(bordoEsterno, bordoInterno);
		/* 2.3 Assegnamento border compound al JPanel */
		jpTurno.setBorder(bordoFinale);			
		
		
		
		// 8. CREA GRIDBAGCONSTRAINTS
		
		
		// 8.1 PLAYERPANEL GIOCATORE HUMAN
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcJPHuman = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcJPHuman.gridx=1; //1
		gbcJPHuman.gridy=3; //2
		/* Pesi nelle due direzioni */
		gbcJPHuman.weightx=0.1;
		gbcJPHuman.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcJPHuman.gridwidth=4;  //3
		gbcJPHuman.gridheight=2; //1
		/* Zona di ancoraggio */
		gbcJPHuman.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcJPHuman.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcJPHuman.fill=GridBagConstraints.BOTH;

		
		// 8.2 PLAYERPANEL GIOCATORE AI 01
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcJPAI01 = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcJPAI01.gridx=0; //0
		gbcJPAI01.gridy=1; //0
		/* Pesi nelle due direzioni */
		gbcJPAI01.weightx=0.1;
		gbcJPAI01.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcJPAI01.gridwidth=1;  //2
		gbcJPAI01.gridheight=3; //1
		/* Zona di ancoraggio */
		gbcJPAI01.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcJPAI01.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcJPAI01.fill=GridBagConstraints.BOTH;
		
		
		// 8.3 PLAYERPANEL GIOCATORE AI 02
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcJPAI02 = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcJPAI02.gridx=1; //0
		gbcJPAI02.gridy=0; //1
		/* Pesi nelle due direzioni */
		gbcJPAI02.weightx=0.1;
		gbcJPAI02.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcJPAI02.gridwidth=4;
		gbcJPAI02.gridheight=2;
		/* Zona di ancoraggio */
		gbcJPAI02.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcJPAI02.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcJPAI02.fill=GridBagConstraints.BOTH;
		
		
		// 8.4 PLAYERPANEL GIOCATORE AI 03
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcJPAI03 = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcJPAI03.gridx=5; //2
		gbcJPAI03.gridy=1; //0
		/* Pesi nelle due direzioni */
		gbcJPAI03.weightx=0.1;
		gbcJPAI03.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcJPAI03.gridwidth=1;	//1
		gbcJPAI03.gridheight=3; //2
		/* Zona di ancoraggio */
		gbcJPAI03.anchor=GridBagConstraints.PAGE_START;
		/* Insets */
		gbcJPAI03.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcJPAI03.fill=GridBagConstraints.BOTH;
		
		
		// 8.5 DECKPANEL
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcJPDeck = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcJPDeck.gridx=2;	//1
		gbcJPDeck.gridy=2;	//1
		/* Pesi nelle due direzioni */
		gbcJPDeck.weightx=0.01;
		gbcJPDeck.weighty=0.01;
		/* Numero di colonne/righe occupate */
		gbcJPDeck.gridwidth=2;	//1
		gbcJPDeck.gridheight=1;	//1
		/* Zona di ancoraggio */
		gbcJPDeck.anchor=GridBagConstraints.CENTER;
		/* Insets */
		gbcJPDeck.insets=new Insets(0,15,0,15);
		/* Fill */
		gbcJPDeck.fill=GridBagConstraints.NONE;		
		
		
		
		// 9. ASSEGNA COMPONENTI A PANNELLI
		
		// 9.1 PANNELLO INFOPARTITA
		jpInfoPartita.add(jpDatiTurno);
		jpInfoPartita.add(jpPunteggi);
		jpInfoPartita.add(jpAzioneTurno);
		jpInfoPartita.add(jpTurno);
		jpInfoPartita.add(jpComandiGiocatore);
		
		// 9.2 PANNELLO DATITURNO
		jpDatiTurno.add(jlVincitore,0,0);
		jpDatiTurno.add(jtfVincitore,0,1);
		jpDatiTurno.add(jlPlayerTurno,1,0);
		jpDatiTurno.add(jtfPlayerTurno,1,1);
		jpDatiTurno.add(jlNumTurno,2,0);
		jpDatiTurno.add(jtfNumTurno,2,1);	
		jpDatiTurno.add(jlNumRound,3,0);
		jpDatiTurno.add(jtfNumRound,3,1);			
	
		// 9.3 PANNELLO PUNTEGGI
		jpPunteggi.add(jlPointsAI03,0,0);
		jpPunteggi.add(jtfPointsAI03,0,1);	
		jpPunteggi.add(jlPointsAI02,1,0);
		jpPunteggi.add(jtfPointsAI02,1,1);	
		jpPunteggi.add(jlPointsAI01,2,0);
		jpPunteggi.add(jtfPointsAI01,2,1);	
		jpPunteggi.add(jlPointsHuman,3,0);
		jpPunteggi.add(jtfPointsHuman,3,1);		
	
		// 9.4 PANNELLO AZIONETURNO
		jpAzioneTurno.add(jtaAzioneTurno);
		
		// 9.5 PANNELLO COLORE E SIMBOLO TURNO
		jpTurno.add(jlColoreTurno,0,0);
		jpTurno.add(jtfColoreTurno,0,1);
		jpTurno.add(jlSimboloTurno,1,0);
		jpTurno.add(jtfSimboloTurno,1,1);
				
		// 9.6 PANNELLO COMANDI GIOCATORE
		jpComandiGiocatore.add(btnPescaCarta);
		jpComandiGiocatore.add(btnPassaTurno);
		jpComandiGiocatore.add(btnUNO);
		
		// 9.7 PANNELLO GAMEBOARD
		jpGameBoard.add(jpHuman, gbcJPHuman);
		jpGameBoard.add(jpAIs.get(0), gbcJPAI01);
		jpGameBoard.add(jpAIs.get(1), gbcJPAI02);
		jpGameBoard.add(jpAIs.get(2), gbcJPAI03);
		jpGameBoard.add(jpMazzo,gbcJPDeck);
		
		
		
		// 10. ASSEGNA PANNELLI A FRAME
		
		// 10.1 GAMEBOARD PANEL
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcJPGameBoard = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcJPGameBoard.gridx=0;
		gbcJPGameBoard.gridy=0;
		/* Pesi nelle due direzioni */
		gbcJPGameBoard.weightx=0.1;
		gbcJPGameBoard.weighty=0.1;
		/* Numero di colonne/righe occupate */
		gbcJPGameBoard.gridwidth=2;
		gbcJPGameBoard.gridheight=1;
		/* Zona di ancoraggio */
		gbcJPGameBoard.anchor=GridBagConstraints.CENTER;
		/* Insets */
		gbcJPGameBoard.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcJPGameBoard.fill=GridBagConstraints.BOTH;
		/* Assegna componente */
		this.add(jpGameBoard,gbcJPGameBoard);
		
		
		// 10.2 INFO PARTITA PANEL
		
		/* Creazione istanza di Classe GridBagConstraints */
		GridBagConstraints gbcJPInfoPartita = new GridBagConstraints();
		/* Coordinate di posizione della componente */
		gbcJPInfoPartita.gridx=2;
		gbcJPInfoPartita.gridy=0;
		/* Pesi nelle due direzioni */
		gbcJPInfoPartita.weightx=0.01;
		gbcJPInfoPartita.weighty=0.01;
		/* Numero di colonne/righe occupate */
		gbcJPInfoPartita.gridwidth=1;
		gbcJPInfoPartita.gridheight=1;
		/* Zona di ancoraggio */
		gbcJPInfoPartita.anchor=GridBagConstraints.CENTER;
		/* Insets */
		gbcJPInfoPartita.insets=new Insets(0,0,0,5);
		/* Fill */
		gbcJPInfoPartita.fill=GridBagConstraints.BOTH;
		/* Assegna componente */
		this.add(jpInfoPartita,gbcJPInfoPartita);
		
		// 	11. TURN ON FRAME VISIBILITY
		this.setVisible(true);			
	}
	
	
	public void setPlayerBorders() {
		
		//1. Se il giocatore Human e' il giocatore del turno, colora il Border di Rosso
		if ((this.jpHuman.getGiocatore().equals(this.junoModel.getGiocatoreSuccessivo()))
					||(this.jpHuman.getGiocatore().equals(this.junoModel.getMazziere()))){
			((TitledBorder)this.jpHuman.getBorder()).setTitleColor(Color.RED);}
		else {((TitledBorder)this.jpHuman.getBorder()).setTitleColor(Color.BLACK);}
		
		//2. Se il giocatore AI e' il giocatore del turno, colora il Border di Rosso
		for (PlayerPanel playerPanel : this.jpAIs) {
			if ((playerPanel.getGiocatore().equals(this.junoModel.getGiocatoreSuccessivo()))
					||(playerPanel.getGiocatore().equals(this.junoModel.getMazziere()))){
				((TitledBorder)playerPanel.getBorder()).setTitleColor(Color.RED);}
			else {((TitledBorder)playerPanel.getBorder()).setTitleColor(Color.BLACK);}
		};
	}
		
	


	@Override
	public void update() {
		
		// Update Pannello MAZZO
		this.jpMazzo.setPescaPanel();
		this.jpMazzo.setScartoPanel();
		
		// Update Pannello GIOCATORE HUMAN
		// 1. Aggiorna Mano di Carte
		this.jpHuman.setHandPanel();
		// Update Pannelli GIOCATORE AI
		for (PlayerPanel playerPanel : this.jpAIs) {playerPanel.setHandPanel();}

		
		// Update Borders GIOCATORI
		this.setPlayerBorders();
		
		// Update Pannello DATITURNO
		this.jtfNumRound.setText(((Integer)(this.junoModel.getRoundGiocati()+1)).toString());
		this.jtfNumTurno.setText(((Integer)(this.junoModel.getTurniGiocati()+1)).toString());
		this.jtfPlayerTurno.setText(this.junoModel.getGiocatoreTurno().getNome());
		this.jtfVincitore.setText((this.junoModel.getGameWinner()==null)?
									"N/A":this.junoModel.getGameWinner().getNome());
		
		// Update Pannello PUNTEGGI
		this.jtfPointsHuman.setText(((Integer)(this.jpHuman.getGiocatore().getPunteggio())).toString());
		this.jtfPointsAI01.setText(((Integer)(this.jpAIs.get(0).getGiocatore().getPunteggio())).toString());
		this.jtfPointsAI02.setText(((Integer)(this.jpAIs.get(1).getGiocatore().getPunteggio())).toString());
		this.jtfPointsAI03.setText(((Integer)(this.jpAIs.get(2).getGiocatore().getPunteggio())).toString());
		
		// Update Pannello AZIONETURNO
		String notifica=this.notificatore.creaNotifica();
		this.jtaAzioneTurno.setText(notifica);
		
		// Update SIMBOLOTURNO
		this.jtfSimboloTurno.setText(this.junoModel.getSimboloTurno()==null?
										"N/A":this.junoModel.getSimboloTurno().toString());
		// Update COLORETURNO
		if (this.junoModel.getColoreTurno()==null) {this.jtfColoreTurno.setForeground(Color.WHITE);}
		else { switch (this.junoModel.getColoreTurno().toString()) {
					case "ROSSO" :  this.jtfColoreTurno.setBackground(Color.RED);
									break;
					case "BLU" : 	this.jtfColoreTurno.setBackground(Color.BLUE);
									break;
					case "GIALLO" : this.jtfColoreTurno.setBackground(Color.YELLOW);
									break;			
					case "VERDE" :  this.jtfColoreTurno.setBackground(Color.GREEN);
								    break;
					case "NULL"  :  this.jtfColoreTurno.setBackground(Color.BLACK);
									break;}}
		
		// Aggiorna Visibilita' JFrame
		this.repaint();
		this.setVisible(true);
			
	}
	


	// SETTERS AND GETTERS
	
	public PlayerPanel getJpHuman() {return jpHuman;}
	public void setJpHuman(PlayerPanel jpHuman) {this.jpHuman = jpHuman;}
	public List<PlayerPanel> getJpAIs() {return jpAIs;}
	public void setJpAIs(List<PlayerPanel> jpAIs) {this.jpAIs = jpAIs;}
	public DeckPanel getJpMazzo() {return jpMazzo;}
	public void setJpMazzo(DeckPanel jpMazzo) {this.jpMazzo = jpMazzo;}
	public JPanel getJpTurno() {return jpTurno;}
	public void setJpTurno(JPanel jpTurno) {this.jpTurno = jpTurno;}
	public JLabel getJlNumRound() {return jlNumRound;}
	public void setJlNumRound(JLabel jlNumRound) {this.jlNumRound = jlNumRound;}
	public JLabel getJlNumTurno() {return jlNumTurno;}
	public void setJlNumTurno(JLabel jlNumTurno) {this.jlNumTurno = jlNumTurno;}
	public JLabel getJlPlayerTurno() {return jlPlayerTurno;}
	public void setJlPlayerTurno(JLabel jlPlayerTurno) {this.jlPlayerTurno = jlPlayerTurno;}
	public JLabel getJlVincitore() {return jlVincitore;}
	public void setJlVincitore(JLabel jlVincitore) {this.jlVincitore = jlVincitore;}
	public JLabel getJlPointsHuman() {return jlPointsHuman;}
	public void setJlPointsHuman(JLabel jlPointsHuman) {this.jlPointsHuman = jlPointsHuman;}
	public JLabel getJlPointsAI01() {return jlPointsAI01;}
	public void setJlPointsAI01(JLabel jlPointsAI01) {this.jlPointsAI01 = jlPointsAI01;}
	public JLabel getJlPointsAI02() {return jlPointsAI02;}
	public void setJlPointsAI02(JLabel jlPointsAI02) {this.jlPointsAI02 = jlPointsAI02;}
	public JLabel getJlPointsAI03() {return jlPointsAI03;}
	public void setJlPointsAI03(JLabel jlPointsAI03) {this.jlPointsAI03 = jlPointsAI03;}
	public JLabel getJlColoreTurno() {return jlColoreTurno;}
	public void setJlColoreTurno(JLabel jlColoreTurno) {this.jlColoreTurno = jlColoreTurno;}
	public JLabel getJlSimboloTurno() {return jlSimboloTurno;}
	public void setJlSimboloTurno(JLabel jlSimboloTurno) {this.jlSimboloTurno = jlSimboloTurno;}
	public JTextField getJtfNumRound() {return jtfNumRound;}
	public void setJtfNumRound(JTextField jtfNumRound) {this.jtfNumRound = jtfNumRound;}
	public JTextField getJtfNumTurno() {return jtfNumTurno;}
	public void setJtfNumTurno(JTextField jtfNumTurno) {this.jtfNumTurno = jtfNumTurno;}
	public JTextField getJtfPlayerTurno() {return jtfPlayerTurno;}
	public void setJtfPlayerTurno(JTextField jtfPlayerTurno) {this.jtfPlayerTurno = jtfPlayerTurno;}
	public JTextField getJtfVincitore() {return jtfVincitore;}
	public void setJtfVincitore(JTextField jtfVincitore) {this.jtfVincitore = jtfVincitore;}
	public JTextField getJtfPointsHuman() {return jtfPointsHuman;}
	public void setJtfPointsHuman(JTextField jtfPointsHuman) {this.jtfPointsHuman = jtfPointsHuman;}
	public JTextField getJtfPointsAI01() {return jtfPointsAI01;}
	public void setJtfPointsAI01(JTextField jtfPointsAI01) {this.jtfPointsAI01 = jtfPointsAI01;}
	public JTextField getJtfPointsAI02() {return jtfPointsAI02;}
	public void setJtfPointsAI02(JTextField jtfPointsAI02) {this.jtfPointsAI02 = jtfPointsAI02;}
	public JTextField getJtfPointsAI03() {return jtfPointsAI03;}
	public void setJtfPointsAI03(JTextField jtfPointsAI03) {this.jtfPointsAI03 = jtfPointsAI03;}
	public JTextField getJtfColoreTurno() {return jtfColoreTurno;}
	public void setJtfColoreTurno(JTextField jtfColoreTurno) {this.jtfColoreTurno = jtfColoreTurno;}
	public JTextField getJtfSimboloTurno() {return jtfSimboloTurno;}
	public void setJtfSimboloTurno(JTextField jtfSimboloTurno) {this.jtfSimboloTurno = jtfSimboloTurno;}
	public JTextArea getJtaAzioneTurno() {return jtaAzioneTurno;}
	public void setJtaAzioneTurno(JTextArea jtaAzioneTurno) {this.jtaAzioneTurno = jtaAzioneTurno;}
	public JButton getBtnPescaCarta() {return btnPescaCarta;}
	public void setBtnPescaCarta(JButton btnPescaCarta) {this.btnPescaCarta = btnPescaCarta;}
	public JButton getBtnPassaTurno() {return btnPassaTurno;}
	public void setBtnPassaTurno(JButton btnPassaTurno) {this.btnPassaTurno = btnPassaTurno;}
	public JButton getBtnUNO() {return btnUNO;}
	public void setBtnUNO(JButton btnUNO) {this.btnUNO = btnUNO;}
	
}
