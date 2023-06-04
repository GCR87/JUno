package controller;

public class MazzoEsauritoHandler extends ExceptionHandler {
	
	public MazzoEsauritoHandler(JunoController junoController) {
		super(junoController);}

	@Override
	public void execute() {
		
		//1. Avvisa l'utente che il mazzo dev'essere rimescolato
		this.junoController.view.getViewGame().getJtaAzioneTurno().setText("Il mazzo di pesca si e'esaurito." +
				"\nIl mazzo di scarto viene rimescolato e il mazzo di pesca e' nuovamente pronto all'uso");
		this.junoController.view.getViewGame().repaint();
		this.junoController.view.getViewGame().setVisible(true);
		
		//2. Resetta il Mazzo nel Model
		this.junoController.model.getMazzo().reset();
		
		//3. Informa che la giocata non e' ancora conclusa
		this.junoController.model.setGiocataOver(false);}

}
