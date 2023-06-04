package controller;

/**
 * ENUMERATION SOUND
 * 
 * Enumerazione costituita da una serie di costanti identificative
 * per diverse tipologie di suono. Ciascuna costante e' associata al
 * filepath del corrispondente file *.wav.
 * L'uso delle costanti consente di richiamare i filepath dei file
 * audio nascondendoli all'utente.
 * 
 * @author giorg
 *
 */



public enum Sound {
	
	/** VALORI ENUMERAZIONE */
	CLICKBUTTON("sounds/clickButton.wav"),
	GAMEWIN("sounds/gameWin.wav"),
	HANDLECARD("sounds/handleCard.wav"),
	ERROR("sounds/error.wav"),
	ROUNDWIN("sounds/roundWin.wav"),
	SELECTCARD("sounds/selectCard.wav"),
	UNOBUTTON("sounds/unoButton.wav");
		
	/** ATTRIBUTI*/
	private String path;
	
	/** COSTRUTTORE PRIVATO */
	private Sound(String path) {this.path=path;}
	
	/** METODI */
	public void setPath(String path) {this.path=path;}
	public String getPath() {return this.path;}
	
}
