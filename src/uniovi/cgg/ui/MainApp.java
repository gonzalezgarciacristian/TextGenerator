package uniovi.cgg.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import uniovi.cgg.logic.MainActions;
import uniovi.cgg.logic.models.UseCase;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

public class MainApp extends Application {

	private final int STAGE_WIDTH = 640;
	private final int STAGE_HEIGHT = 480;

	private static final String i18n_PATH = "i18n/texts";
	private static final String MAILTO = "mailto:";

	private static ResourceBundle resourceBundle = null;

	private UseCase useCase;

	/**
	 * Creamos y configuramos las pestañas
	 * 
	 * @return TabPane Retorna todas las pestañas creadas y configuradas dentro del
	 *         TabPane
	 */
	private TabPane createAndConfigureTabs() {
		TabPane tabPane = new TabPane();

		tabPane.getTabs().add(tabGenerator());
		tabPane.getTabs().add(tab2());
		tabPane.getTabs().add(tabConfiguration());
		tabPane.getTabs().add(tabAbout());

		return tabPane;
	}
	
	private GridPane generalGrid() {		
		GridPane grid = new GridPane();		
		// Alineamiento
		grid.setAlignment(Pos.CENTER);
		// Padding
		grid.setHgap(10);
		grid.setVgap(10);		
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		return grid;
	}

	/**
	 * Creación y configuración de la pestaña 1: generador de textos
	 * 
	 * @return Tab pestaña ya creada configurada
	 */
	private Tab tabGenerator() {
		Tab tab = new Tab(resourceBundle.getString("tab.one.title"));

		// Interface
		GridPane grid = generalGrid();

		tab.setContent(grid);

		Button btnLoad = new Button(resourceBundle.getString("tab.one.btnLoad"));
		// Node to include, Column index, Row index, [Row span, Column span] -> How many row and columns needs the component 
		grid.add(btnLoad, 0, 0, 1, 1);

		Button btnGenerate = new Button(resourceBundle.getString("tab.one.btnGenerate"));
		grid.add(btnGenerate, 1, 0, 1, 1);

		TextArea txtAGeneratedText = new TextArea(resourceBundle.getString("tab.one.txtAGeneratedText"));
		txtAGeneratedText.setPrefWidth(220);
		grid.add(txtAGeneratedText, 0, 1, 2, 1);

		Label sendTo = new Label(resourceBundle.getString("tab.one.sendTo"));
		grid.add(sendTo, 0, 2, 1, 1);

		TextField txtFSendTo = new TextField();
		grid.add(txtFSendTo, 1, 2, 1, 1);

		CheckBox ccToMe = new CheckBox(resourceBundle.getString("tab.one.ccToMe"));
		grid.add(ccToMe, 0, 3, 1, 1);

		// Actions
		// Al clicar sobre el botón de cargar, abrimos un nuevo FileChooser
		btnLoad.setOnAction(e -> openFileChooser(btnGenerate));

		// Deshabilitado al inicio ya que no hay ningún texto guardado
		btnGenerate.setDisable(true);
		btnGenerate.setOnAction(e -> txtAGeneratedText.setText(useCase.generateExercise()));
		
		return tab;
	}

	private void openFileChooser(Button btnGenerate) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(resourceBundle.getString("tab.one.btnLoad.fileChooser"));
		//fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), System.getProperty("user.dir")+Persistance.FOLDER)); 
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JSON", "*.json"));
		

		File file = fileChooser.showOpenDialog(new Stage());

		if (file == null) {
			// TODO mostrar popup
			System.out.println("Cancelada apertura de fichero -> File == null");
			return;
		}

		System.out.println(file.toString());
		MainActions mainActions = new MainActions();
		useCase = mainActions.loadFile(file);
		btnGenerate.setDisable(false);
	}
	
	/**
	 * Creación y configuración de la pestaña 2: 
	 * 
	 * @return Tab pestaña ya creada configurada
	 */
	private Tab tab2() {
		Tab tab = new Tab(resourceBundle.getString("tab.two.title"),
				new Label(resourceBundle.getString("tab.two.label")));
		
		return tab;
	}

	/**
	 * Creación y configuración de la pestaña 3: configuración
	 * 
	 * @return Tab pestaña ya creada configurada
	 */
	private Tab tabConfiguration() {
		Tab tab = new Tab(resourceBundle.getString("tab.three.title"));

		// Interface
		GridPane grid = generalGrid();

		tab.setContent(grid);

		Label currentLanguage = new Label(
				resourceBundle.getString("tab.three.currentLanguage") + Locale.getDefault().toString());
		grid.add(currentLanguage, 0, 0, 1, 1);

		ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().add(resourceBundle.getString("languageSpanish"));
		comboBox.getItems().add(resourceBundle.getString("languageEnglish"));
		grid.add(comboBox, 1, 0, 1, 1);

		Label labelUserEmail = new Label(resourceBundle.getString("tab.three.userEmail"));
		grid.add(labelUserEmail, 0, 1, 1, 1);

		TextField tftFUserEmail = new TextField();
		grid.add(tftFUserEmail, 1, 1, 1, 1);

		Label labelEmailHead = new Label(resourceBundle.getString("tab.three.emailIntroduction"));
		grid.add(labelEmailHead, 0, 2, 1, 1);

		TextArea txtAEmailIntroduction = new TextArea();
		txtAEmailIntroduction.setPrefWidth(220);
		grid.add(txtAEmailIntroduction, 1, 2, 1, 1);

		Label labelEmailSign = new Label(resourceBundle.getString("tab.three.emailEnd"));
		grid.add(labelEmailSign, 0, 3, 1, 1);

		TextArea txtAEmailSign = new TextArea();
		txtAEmailSign.setPrefWidth(220);
		grid.add(txtAEmailSign, 1, 3, 1, 1);

		Button btnSave = new Button(resourceBundle.getString("tab.three.btnSave"));
		grid.add(btnSave, 0, 4, 1, 1);

		Button btnWithoutSave = new Button(resourceBundle.getString("tab.three.btnWithoutSave"));
		grid.add(btnWithoutSave, 1, 4, 1, 1);

		return tab;
	}

	/**
	 * Creación y configuración de la pestaña 4: Acerca de...
	 * 
	 * @return Tab pestaña ya creada configurada
	 */
	private Tab tabAbout() {
		Tab tab = new Tab(resourceBundle.getString("tab.four.title"));

		// Interface
		GridPane grid = generalGrid();

		tab.setContent(grid);

		Label labelDeveloper1 = new Label(resourceBundle.getString("tab.four.developer1"));
		grid.add(labelDeveloper1, 0, 0, 1, 1);

		Hyperlink linkDeveloper1Email = new Hyperlink(resourceBundle.getString("tab.four.developer1Email"));
		grid.add(linkDeveloper1Email, 1, 0, 1, 1);

		Label labelDeveloper2 = new Label(resourceBundle.getString("tab.four.developer2"));
		grid.add(labelDeveloper2, 0, 1, 1, 1);

		Hyperlink linkDeveloper2Email = new Hyperlink(resourceBundle.getString("tab.four.developer2Email"));
		grid.add(linkDeveloper2Email, 1, 1, 1, 1);

		FileInputStream fisImageEII = null;
		try {
			fisImageEII = new FileInputStream("resources/logoEII.png");
			Image imageEII = new Image(fisImageEII);
			ImageView imageViewEII = new ImageView(imageEII);
			grid.add(imageViewEII, 2, 0, 1, 1);
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}

		FileInputStream fisImageUniOvi = null;
		try {
			fisImageUniOvi = new FileInputStream("resources/logoUniOvi.png");
			Image imageUniOvi = new Image(fisImageUniOvi);
			ImageView imageViewUniOvi = new ImageView(imageUniOvi);
			grid.add(imageViewUniOvi, 2, 1, 1, 1);
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}

		Hyperlink linkGitHub = new Hyperlink(resourceBundle.getString("tab.four.githubProject"));
		grid.add(linkGitHub, 0, 2, 1, 1);

		Label license = new Label(resourceBundle.getString("tab.four.license")); // TODO
		grid.add(license, 0, 3, 1, 1);

		Label version = new Label(resourceBundle.getString("tab.four.version")); // TODO
		grid.add(version, 1, 3, 1, 1);

		// Actions
		linkDeveloper1Email.setOnAction(e -> getHostServices().showDocument(MAILTO + linkDeveloper1Email.getText()));
		linkDeveloper2Email.setOnAction(e -> getHostServices().showDocument(MAILTO + linkDeveloper2Email.getText()));
		linkGitHub.setOnAction(e -> getHostServices().showDocument(resourceBundle.getString("tab.four.githubURL")));

		return tab;
	}

	/**
	 * Saca la resolución de la pantalla y la retorna
	 * 
	 * @return Double Retorna un array de dos posiciones de tipo double con el ancho
	 *         y alto
	 */
	private double[] getScreenResolution() {
		Rectangle2D screenProperties = Screen.getPrimary().getBounds();

		double[] screenResolution = { screenProperties.getWidth(), screenProperties.getHeight() };

		return screenResolution;
	}

	/**
	 * Establece que el programa se abra justo en el medio de la pantalla
	 * 
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
	 * Carga la ruta dónde estan los textos en diferentes idiomas. La ruta tiene que
	 * estar dentro del SRC. Por defecto carga el idioma local Idiomas disponibles:
	 * es, en
	 */
	private void loadi18n() {
		// Locale.setDefault(new Locale("en")); // es españa, en inglés
		resourceBundle = ResourceBundle.getBundle(i18n_PATH);
	}

	@Override
	public void start(Stage stage) {
		loadi18n();

		useCase = new UseCase();

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