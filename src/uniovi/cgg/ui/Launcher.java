package uniovi.cgg.ui;

/**
 * This class is needed to create the runnable Jar and avoiding the JavaFX error: "Error: JavaFX runtime components are missing, and are required to run this
application". Then, to create the runnable Jar is required to select this class as main class.
 * @author Cristian González García
 *
 */
public class Launcher {

	public static void main(String[] args) {
		MainApp.main(args);
	}

}
