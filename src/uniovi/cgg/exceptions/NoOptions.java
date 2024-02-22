package uniovi.cgg.exceptions;

/**
 * Clase que contiene la excepción NoOption que debe ser lanzada cuando no
 * quedan opciones en el array entre las que elegir
 * 
 * @author Cristian González García
 *
 */
public class NoOptions extends Exception {

	private static final long serialVersionUID = 7246234487435357453L;

	private static final String message = "There are no options to choose: ";

	public NoOptions() {
		super();
	}

	public NoOptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoOptions(String message, Throwable cause) {
		super(message, cause);
	}

	public NoOptions(String cause) {
		super(message+cause);
	}

	public NoOptions(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
