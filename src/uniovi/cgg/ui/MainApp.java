package uniovi.cgg.ui;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class MainApp extends Application {

	private final int STAGE_WIDTH = 640;
	private final int STAGE_HEIGHT = 480;
	
	private static final String i18n_PATH = "i18n/texts";
	
	private static ResourceBundle resourceBundle = null;

	/**
	 * Creamos y configuramos las pestañas 
	 * @return TabPane Retorna todas las pestañas creadas y configuradas dentro del TabPane
	 */
	private TabPane createAndConfigureTabs() {
		TabPane tabPane = new TabPane();

		Tab tab1 = new Tab(resourceBundle.getString("tab.one.title"), new Label(resourceBundle.getString("tab.one.label")));
		Tab tab2 = new Tab(resourceBundle.getString("tab.two.title"), new Label(resourceBundle.getString("tab.two.label")));

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tabConfiguration());
		tabPane.getTabs().add(tabAbout());

		return tabPane;
	}

	/**
	 * Creación y configuración de la pestaña 3: configuración
	 * @return Tab pestaña ya creada configurada
	 */
	private Tab tabConfiguration() {
		Tab tab = new Tab(resourceBundle.getString("tab.three.title"));
		
		Label currentLanguage = new Label(resourceBundle.getString("tab.three.currentLanguage")+Locale.getDefault().toString());
		tab.setContent(currentLanguage);

		return tab;
	}
	/**
	 * Creación y configuración de la pestaña 4: Acerca de...
	 * @return Tab pestaña ya creada configurada
	 */
	private Tab tabAbout() {
		Tab tab = new Tab(resourceBundle.getString("tab.four.title"), new Label(resourceBundle.getString("tab.four.label")));		

		return tab;
	}

	/**
	 * Saca la resolución de la pantalla y la retorna
	 * @return Double Retorna un array de dos posiciones de tipo double con el ancho y alto
	 */
	private double[] getScreenResolution() {
		Rectangle2D screenProperties = Screen.getPrimary().getBounds();

		double[] screenResolution = {screenProperties.getWidth(), screenProperties.getHeight()};

		return screenResolution;
	}

	/**
	 * Establece que el programa se abra justo en el medio de la pantalla
	 * @param stage
	 * @return stage
	 */
	private Stage stageProperties(Stage stage) {
		stage.setTitle(resourceBundle.getString("main.title"));

		double[] screenResolution = getScreenResolution();

		// Stage in the middle
		stage.setX((screenResolution[0] - STAGE_WIDTH) * 0.5);
		stage.setY((screenResolution[1] - STAGE_HEIGHT) * 0.5);

		stage.setWidth(STAGE_WIDTH);
		stage.setHeight(STAGE_HEIGHT);

		return stage;
	}

	/**
	 * Carga la ruta dónde estan los textos en diferentes idiomas. La ruta tiene que estar dentro del SRC. Por defecto carga el idioma local
	 * Idiomas disponibles: es, en
	 */
	private void loadi18n() {
		//Locale.setDefault(new Locale("es")); // es españa, en inglés
		resourceBundle = ResourceBundle.getBundle(i18n_PATH);
	}

	@Override
	public void start(Stage stage) {
		loadi18n();
		
		TabPane tabPane = createAndConfigureTabs();

		VBox vBox = new VBox(tabPane);

		// Scene contiene lo que se quiere mostrar dentro del Stage
		Scene scene = new Scene(vBox);
		stage.setScene(scene);

		stage = stageProperties(stage);

		stage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}