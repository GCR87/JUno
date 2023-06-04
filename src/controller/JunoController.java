package controller;


/** IMPORT PACKAGES */

//MVC Pattern Packages
import model.*;
import view.*;
//Java Swing Packages
import java.util.Timer;
import java.util.TimerTask;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


/**
 * CLASSE JUNOCONTROLLER
 * 
 * Classe concreta e classe principale del package controller.
 * La classe ha il compito di inizializzare il Model, inizializzare la View
 * passandole il riferimento al Model e gestire la comunicazione dalla View
 * al Model senza che il Model abbia alcun riferimento alla View.
 * 
 * Tecniche specifiche:
 * - OBSERVER Design Pattern
 * - MODEL-VIEW-CONTROLLER Design Pattern
 * 
 * @author giorg
 *
 */

public class JunoController implements JunoControllerInterface {

	
	/** ATTRIBUTI */
	// Riferimenti a Model e View
	protected JunoModel model;
	protected JunoView view;
	// Gestore Profilo
	protected GestoreProfilo gestoreProfilo;
	//ActionListeners
	private CaricaProfiloListener caricaProfiloListener;
	private GiocaPartitaListener giocaPartitaListener;
	private CardsMouseListener cardsMouseListener;
	private PassaTurnoListener passaTurnoListener;
	private PescaCartaListener pescaCartaListener;
	private TimerListenerInterface turnoTimerListener;
	private UnoListener unoListener;
	private ColorButtonListener colorButtonListener;
	//ExceptionHandlers
	private MazzoEsauritoHandler mazzoEsauritoHandler;
	//AudioManager
	private SoundsManager soundsManager;
	private MusicManager musicManager;
	
	
	/** COSTRUTTORE */
	public JunoController() {
		// 1. Istanziazione Model
		this.model=JunoModel.getInstance();
		// 2. Istanziazione View
		this.view=new JunoView(this.model,this);
		// 3. Istanziazione AudioManagers
		this.soundsManager=SoundsManager.getInstance();
		this.musicManager=MusicManager.getInstance();
		// 4. Creazione ActionListeners
		this.creaListeners();
		// 5. Creazione ExceptionHandlers
		this.creaExceptionHandlers();}

	
	/** METODI */	
	
	//inizializza
	@Override
	public void inizializza() {
		//1. Crea la JFrame ViewMenu
		this.view.creaViewMenu();
		//2. Assegna ActionListeners ai bottoni di ViewMenu
		this.view.getViewMenu().getBtnCaricaProfilo().addActionListener(new 
				CaricaProfiloListener(this));
		this.view.getViewMenu().getBtnGiocaPartita().addActionListener(new 
				GiocaPartitaListener(this));}

	//iniziaPartita
	@Override
	public void iniziaPartita() {
		//1. Inizializza il Model
		this.model.inizializza();
		//2. Visualizza ViewGame
		this.view.creaViewGame();
		this.assegnaButtonListeners();
		//3. Imposta valori iniziali parametri partita
		this.model.setGameOver(false);
		this.model.setRoundOver(false);
		this.model.setFasePartita(FasePartita.MAZZIERE);
		//4. Crea e lancia il timer per la gestione dei turni
		TurnoTimerListener turnoTimer=new TurnoTimerListener(this);
		this.setTurnoTimerListener(turnoTimer);
		turnoTimer.start();}
	
	//setMazziere
	public void setMazziere() throws MazzoEsauritoException {
		this.model.setMazziere();}
	
	//preparativi
	public void preparativi() throws MazzoEsauritoException {
		this.model.preparativi();}
	
	//giocaTurno
	public void giocaTurno() throws MazzoEsauritoException {
		try {this.model.giocaTurno();}
		catch (model.CartaNonConsentitaException e) {
			//1. Lancia Effetto Sonoro
			this.soundsManager.play(Sound.ERROR.getPath());
			//2. Avvisa l'utente
			String nomeGiocatore=this.model.getGiocatoreTurno().getNome().toString();
			this.view.getViewGame().getJtaAzioneTurno().setText("");
			this.view.getViewGame().getJtaAzioneTurno().setText(nomeGiocatore + ", la carta che hai selezionato non e'" +
					" consentita. Scegline un'altra dalla tua mano");
			//3. Rilancia il timer
			this.turnoTimerListener.start();}}
		
	//assegnaCardsListeners
	public void assegnaCardsListeners() {
		// 1. CardsMouseListener
		for (Component comp: this.view.getViewGame().getJpHuman().getHandPanel().getComponents()) {
			((CartaPanel)comp).addMouseListener(this.getCardsMouseListener());}}
	
	//assegnaButtonListeners
	public void assegnaButtonListeners() {
		// 1. PassaTurnoListener
		this.view.getViewGame().getBtnPassaTurno().addActionListener(passaTurnoListener);
		// 2. PescaCartaListener
		this.view.getViewGame().getBtnPescaCarta().addActionListener(pescaCartaListener);
		// 3. UnoListener
		this.view.getViewGame().getBtnUNO().addActionListener(unoListener);}
	
	//assegnaColorButtonListeners
	public void assegnaColorButtonListeners() {
		// Assegna ColorButtonListeners ai JButtons
		for (Component comp : this.view.getViewColorChoose().getContentPane().getComponents()) {
			((JButton)comp).addActionListener(this.getColorButtonListener());}}

	//resetButtonListeners
	public void resetButtonListeners() {
		// Assegna run=false a tutti i JButtons della ViewGame
		((PassaTurnoListener)this.view.getViewGame().getBtnPassaTurno().getActionListeners()[0]).setRun(false);
		((PescaCartaListener)this.view.getViewGame().getBtnPescaCarta().getActionListeners()[0]).setRun(false);
		((UnoListener)this.view.getViewGame().getBtnUNO().getActionListeners()[0]).setRun(false);}
	
	//creaExceptionHandlers
	@Override
	public void creaExceptionHandlers() {
		this.mazzoEsauritoHandler=new MazzoEsauritoHandler(this);}
	
	//creaListeners
	@Override
	public void creaListeners() {
		this.caricaProfiloListener=new CaricaProfiloListener(this);
		this.giocaPartitaListener=new GiocaPartitaListener(this);
		this.cardsMouseListener=new CardsMouseListener(this);
		this.turnoTimerListener=new TurnoTimerListener(this);
		this.passaTurnoListener=new PassaTurnoListener(this);
		this.pescaCartaListener=new PescaCartaListener(this);
		this.unoListener=new UnoListener(this);
		this.colorButtonListener=new ColorButtonListener(this);}
	
	//attivaComponenti
	public void attivaComponenti() {
		// Abilita JButton PescaCarta
		if (!((PescaCartaListener)this.view.getViewGame().getBtnPescaCarta().getActionListeners()[0]).isRun()) {
		this.view.getViewGame().getBtnPescaCarta().setEnabled(true);}		
		// Abilita JButton PassaTurno
		if(!((PassaTurnoListener)this.view.getViewGame().getBtnPassaTurno().getActionListeners()[0]).isRun()) {
			this.view.getViewGame().getBtnPassaTurno().setEnabled(true);}
		// Abilita JButton UNO
		if(!((UnoListener)this.view.getViewGame().getBtnUNO().getActionListeners()[0]).isRun()) {
		this.view.getViewGame().getBtnUNO().setEnabled(true);}}
	
	//disattivaComponenti
	public void disattivaComponenti() {
		this.view.getViewGame().getBtnPescaCarta().setEnabled(false);
		this.view.getViewGame().getBtnPassaTurno().setEnabled(false);
		this.view.getViewGame().getBtnUNO().setEnabled(false);	}

	// Setters and Getters
	public GiocaPartitaListener getGiocaPartitaListener() {return giocaPartitaListener;}
	public void setGiocaPartitaListener(GiocaPartitaListener giocaPartitaListener) {this.giocaPartitaListener = giocaPartitaListener;}
	public CardsMouseListener getCardsMouseListener() {return cardsMouseListener;}
	public void setCardsMouseListener(CardsMouseListener cardsMouseListener) {this.cardsMouseListener = cardsMouseListener;}
	public PassaTurnoListener getPassaTurnoListener() {return passaTurnoListener;}
	public void setPassaTurnoListener(PassaTurnoListener passaTurnoListener) {this.passaTurnoListener = passaTurnoListener;}
	public PescaCartaListener getPescaCartaListener() {return pescaCartaListener;}
	public void setPescaCartaListener(PescaCartaListener pescaCartaListener) {this.pescaCartaListener = pescaCartaListener;}
	public TimerListenerInterface getTurnoTimerListener() {return turnoTimerListener;}
	public void setTurnoTimerListener(TimerListenerInterface turnoTimerListener) {this.turnoTimerListener = turnoTimerListener;}
	public UnoListener getUnoListener() {return unoListener;}
	public void setUnoListener(UnoListener unoListener) {this.unoListener = unoListener;}
	public SoundsManager getSoundsManager() {return this.soundsManager;}
	public void setSoundsManager(SoundsManager soundsManager) {this.soundsManager = soundsManager;}
	public ColorButtonListener getColorButtonListener() {return this.colorButtonListener;}
	public void setColorButtonListener(ColorButtonListener colorButtonListener) {this.colorButtonListener=colorButtonListener;}
	public MusicManager getMusicManager() {return musicManager;}
	public void setMusicManager(MusicManager musicManager) {this.musicManager = musicManager;}
	public GestoreProfilo getGestoreProfilo() {return gestoreProfilo;}
	public void setGestoreProfilo(GestoreProfilo gestoreProfilo) {this.gestoreProfilo = gestoreProfilo;}
	public ExceptionHandler getMazzoEsauritoHandler() {return mazzoEsauritoHandler;}
	public void setMazzoEsauritoHandler(MazzoEsauritoHandler mazzoEsauritoHandler) {this.mazzoEsauritoHandler = mazzoEsauritoHandler;}
	
}
