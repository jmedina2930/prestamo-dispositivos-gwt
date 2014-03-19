package co.edu.udea.prestamoDispositivos.shared;
/**
 * clase usada para capturar las excepciones en el proyecto de GWT
 * @author Jonathan
 *
 */
@SuppressWarnings("serial")
public class MyException extends Exception{

	/**
	 * Es el constructor de la clase, implementa el constructor de la clase padre
	 */
	public MyException() {
		super();
	}

	/**
	 * constructor de la clase que recibe parametros, llama al constructor de la clase padre
	 * @param arg0
	 */
	public MyException(String arg0) {
		super(arg0);
	}
	
	
}
