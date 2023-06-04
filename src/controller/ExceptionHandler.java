package controller;

public abstract class ExceptionHandler {
	
	/** ATTRIBUTI */
	protected JunoController junoController;
	
	/** COSTRUTTORE */
	public ExceptionHandler(JunoController junoController) {
		this.junoController=junoController;}
	
	public abstract void execute();

}
