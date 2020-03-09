package uniovi.cgg.ui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

public class MainApp extends Application {
	
	private final int STAGE_WIDTH = 640;
	private final int STAGE_HEIGHT = 480;

	/**
	 * Creamos y configuramos las pestañas
	 * 
	 * @return
	 */
	private TabPane createAndConfigureTabs() {
		TabPane tabPane = new TabPane();

		Tab tab1 = new Tab("Texto", new Label("Show all planes available"));
		Tab tab2 = new Tab("Otro", new Label("Show all cars available"));
		Tab tab3 = new Tab("Configuración", new Label("Show all boats available"));
		

		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);
		tabPane.getTabs().add(tabAbout());

		return tabPane;
	}
	
	private Tab tabAbout() {		
		Tab tab4 = new Tab("Acerca de...", new Label("Cristian González García, UniOvi, 2020"));
		
		return tab4;
	}
	
	private double[] getScreenResolution() {
		Rectangle2D screenProperties = Screen.getPrimary().getBounds();
		
		double[] screenResolution = {screenProperties.getWidth(), screenProperties.getHeight()};
		
		return screenResolution;		
	}
	
	private Stage stageProperties(Stage stage) {				
		stage.setTitle("Companies Legal Information Generator");		
		
		double[] screenResolution = getScreenResolution();
		
		// Stage in the middle
		stage.setX((screenResolution[0]-STAGE_WIDTH)*0.5);
		stage.setY((screenResolution[1]-STAGE_HEIGHT)*0.5);		
		
		stage.setWidth(STAGE_WIDTH);
		stage.setHeight(STAGE_HEIGHT);
		
		return stage;		
	}

	@Override
	public void start(Stage stage) {
		TabPane tabPane = createAndConfigureTabs();

		VBox vBox = new VBox(tabPane);
		
		Scene scene = new Scene(vBox);
		stage.setScene(scene);
		
		stage = stageProperties(stage);
		
		stage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}