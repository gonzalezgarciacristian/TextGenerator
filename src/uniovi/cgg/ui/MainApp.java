package uniovi.cgg.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import uniovi.cgg.logic.MainActions;
import uniovi.cgg.logic.models.Configuration;
import uniovi.cgg.logic.models.UseCase;
import uniovi.cgg.persistence.Persistence;
import uniovi.cgg.util.SendEmails;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;

public class MainApp extends Application {
	
	private static final String JSON_FILE = "JSON";
	private static final String JSON_EXTENSION = "*.json";

	private final int STAGE_WIDTH = 640;
	private final int STAGE_HEIGHT = 480;

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
		tabPane.getTabs().add(tabUseCase());
		tabPane.getTabs().add(tabConfiguration());
		tabPane.getTabs().add(tabAbout());
		
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

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
		
		Label labelBccEmails = new Label(resourceBundle.getString("tab.one.bccEmails"));
		grid.add(labelBccEmails, 0, 3, 1, 1);

		TextField txtFbccEmails = new TextField();
		grid.add(txtFbccEmails, 1, 3, 1, 1);
		
		Label labelPassword = new Label(resourceBundle.getString("tab.one.password"));
		grid.add(labelPassword, 0, 4, 1, 1);

		PasswordField txtFPassword = new PasswordField();
		grid.add(txtFPassword, 1, 4, 1, 1);

		CheckBox ccToMe = new CheckBox(resourceBundle.getString("tab.one.ccToMe"));
		grid.add(ccToMe, 0, 5, 1, 1);
		
		Button btnSend = new Button(resourceBundle.getString("tab.one.btnSend"));
		grid.add(btnSend, 1, 5, 1, 1);

		// Actions
		// Al clicar sobre el botón de cargar, abrimos un nuevo FileChooser
		btnLoad.setOnAction(e -> loadUseCaseToGenerateTab(btnGenerate));

		// Deshabilitado al inicio ya que no hay ningún texto guardado
		btnGenerate.setDisable(true);
		btnGenerate.setOnAction(e -> txtAGeneratedText.setText(useCase.generateExercise()));
		
		btnSend.setOnAction(e -> sendEmail(txtFPassword.getText(), txtFSendTo.getText(), ccToMe.isSelected(), txtFbccEmails.getText(), "título", txtAGeneratedText.getText()));		
		
		return tab;
	}
	
	private void loadUseCaseToGenerateTab (Button btnGenerate) {
		File file = openFileChooser();
		
		if(file != null) {
			System.out.println(file.toString());
			MainActions mainActions = new MainActions();
			useCase = mainActions.loadFile(file);
			btnGenerate.setDisable(false);
		} else {
			// TODO mostrar popup
			System.out.println("Cancelada apertura de fichero -> File == null");
		}
		
	}
	
	private void sendEmail(String password, String sendTo, boolean cc, String bccEmails, String title, String text) {
		Configuration configuration = new MainActions().loadConfiguration();

		//String from = "@hotmail.com";
		//String userAccount = "@gmail.com";
		//String emailsTo = "@gmail.com, @hotmail.com";
		String ccEmail = "";
		//String bcc = "@uniovi.es";	
		//title = "Title here!!!!";
		//String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla euismod, sapien finibus commodo semper, sapien velit sodales ipsum, at tristique mauris sapien id tortor. Duis iaculis velit in lectus hendrerit, a cursus mauris vehicula. Nam ac viverra sem, in laoreet quam. Pellentesque sagittis, orci non facilisis tincidunt, nisi diam malesuada ante, sed ultrices justo erat non diam. Suspendisse rhoncus luctus eros at blandit. Aliquam eleifend fringilla velit, at consectetur ipsum tempus nec. Fusce in aliquam lacus. Proin aliquet dignissim porta.";

		if(cc) {
			// TODO: mirar en opciones y meterlo aquí si lo tiene
			ccEmail = configuration.getFrom();
		}
		
		try {
			new SendEmails(configuration.getSMTPServer(), configuration.getUserEmail(), configuration.getUserEmail(), password, sendTo, ccEmail, bccEmails, configuration.getTitle(), configuration.getIntroduction()+text+configuration.getSign());
		} catch (AddressException e) {
			// TODO: mostrar popup en este y en el siguiente catch
			System.out.println(resourceBundle.getString("authenticationFailedException") + ": " + e.getMessage());
		} catch (MessagingException e) {
			System.out.println(resourceBundle.getString("authenticationFailedException") + ": " + e.getMessage());
		}
	}
	
	private File openFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(resourceBundle.getString("tab.one.btnLoad.fileChooser"));
		//fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), System.getProperty("user.dir")+Persistance.FOLDER)); 
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(JSON_FILE, JSON_EXTENSION));
		
		return fileChooser.showOpenDialog(new Stage());
	}
	
	/**
	 * Creación y configuración de la pestaña 2: 
	 * 
	 * @return Tab pestaña ya creada configurada
	 */
	private Tab tabUseCase() {
		Tab tab = new Tab(resourceBundle.getString("tab.two.title"));
		
		// Interface
		GridPane grid = generalGrid();		
		ScrollPane scrollPane = new ScrollPane();

		tab.setContent(scrollPane);		
		
		scrollPane.setContent(grid);
		
		// Botonera
		Button btnLoad = new Button(resourceBundle.getString("tab.one.btnLoad"));
		grid.add(btnLoad, 0, 0, 1, 1);

		Button btnAddRow = new Button(resourceBundle.getString("tab.two.btnAddRow"));
		grid.add(btnAddRow, 1, 0, 1, 1);
		
		Button btnSave = new Button(resourceBundle.getString("tab.three.btnSave"));
		grid.add(btnSave, 2, 0, 1, 1);
		
		Button btnWithoutSave = new Button(resourceBundle.getString("tab.three.btnWithoutSave"));
		grid.add(btnWithoutSave, 3, 0, 1, 1);

		// Node to include, Column index, Row index, [Row span, Column span] -> How many row and columns needs the component 
		Label labelID = new Label(resourceBundle.getString("tab.three.id"));
		grid.add(labelID, 0, 1, 1, 1);
		
		// Cabeceras
		Label labelName = new Label(resourceBundle.getString("tab.three.name"));
		grid.add(labelName, 1, 1, 1, 1);
		
		Label labelIntroduction = new Label(resourceBundle.getString("tab.three.introduction"));
		grid.add(labelIntroduction, 2, 1, 1, 1);
		
		Label labelConclusions = new Label(resourceBundle.getString("tab.three.conclusions"));
		grid.add(labelConclusions, 3, 1, 1, 1);
		
		Label labelOptions = new Label(resourceBundle.getString("tab.three.options"));
		grid.add(labelOptions, 4, 1, 1, 1);
		
		
		// Filas
		int rowsToAdd = 2;
		
		// +1 pq esta dentro del mismo grid el nombre de las columnas
		for(int i = grid.getRowCount(); i < rowsToAdd+1; i++) {
			addNewRow(grid, i);
		}
		
		// Actions
		//btnLoad.setOnAction(e -> loadUseCase());
		btnAddRow.setOnAction(e -> addNewRow(grid, grid.getRowCount()));
		
		return tab;
	}
	
	private void addNewRow(GridPane grid, int row) {		
		TextField txtFID = new TextField();
		grid.add(txtFID, 0, row, 1, 1);
		
		TextField txtFName = new TextField();
		grid.add(txtFName, 1, row, 1, 1);
		
		TextField txtFIntroduction = new TextField();
		grid.add(txtFIntroduction, 2, row, 1, 1);
		
		TextField txtFConclusions = new TextField();
		grid.add(txtFConclusions, 3, row, 1, 1);
		
		// Interface
		GridPane gridOptions = generalGrid();
		grid.add(gridOptions, 4, row, 1, 1);
		
		Button btnAddOption = new Button(resourceBundle.getString("tab.three.btnAddOption"));
		gridOptions.add(btnAddOption, 0, 0, 1, 1);
		
		int optionsToAdd = 4;
		
		for(int i = gridOptions.getRowCount(); i < optionsToAdd; i++) {
			addOption(gridOptions, i);
		}
		
		btnAddOption.setOnAction(e -> addOption(gridOptions, gridOptions.getRowCount()));
		
	}
	
	private void addOption(GridPane gridOptions, int row) {
		TextField txtFOptions = new TextField();
		// Node to include, Column index, Row index, [Row span, Column span] -> How many row and columns needs the component 
		gridOptions.add(txtFOptions, 0, row, 1, 1);
		
		TextField txtFProbability = new TextField();
		gridOptions.add(txtFProbability, 1, row, 1, 1);
		
		CheckBox cbProbabilityModfied = new CheckBox();
		gridOptions.add(cbProbabilityModfied, 2, row, 1, 1);
		
		TextField txtFDependence = new TextField();
		gridOptions.add(txtFDependence, 3, row, 1, 1);
		
		TextField txtFDependsOn = new TextField();
		gridOptions.add(txtFDependsOn, 4, row, 1, 1);
	}

	/**
	 * Creación y configuración de la pestaña 3: configuración
	 * Despues carga la configuración previa, en caso de haberla, y rellena las opciones con loque haya guardado. Sino, las deja en blanco
	 * 
	 * @return Tab pestaña ya creada configurada
	 */
	private Tab tabConfiguration() {		
		Tab tab = new Tab(resourceBundle.getString("tab.three.title"));

		// Interface
		GridPane grid = generalGrid();

		tab.setContent(grid);

		/*Label currentLanguage = new Label(
				resourceBundle.getString("tab.three.currentLanguage") + Locale.getDefault().toString());
		grid.add(currentLanguage, 0, 0, 1, 1);

		ComboBox<String> comboBox = new ComboBox<String>();
		comboBox.getItems().add(resourceBundle.getString("languageSpanish"));
		comboBox.getItems().add(resourceBundle.getString("languageEnglish"));
		grid.add(comboBox, 1, 0, 1, 1);*/
		
		Label labelFrom = new Label(resourceBundle.getString("tab.three.from"));
		grid.add(labelFrom, 0, 0, 1, 1);

		TextField txtFFrom = new TextField();
		grid.add(txtFFrom, 1, 0, 1, 1);
		
		Label labelUserEmail = new Label(resourceBundle.getString("tab.three.userEmail"));
		grid.add(labelUserEmail, 0, 1, 1, 1);

		TextField txtFUserEmail = new TextField();
		grid.add(txtFUserEmail, 1, 1, 1, 1);
		
		Label labelSMTPServer = new Label(resourceBundle.getString("tab.three.smtpServer"));
		grid.add(labelSMTPServer, 0, 2, 1, 1);

		TextField txtFSMTPServer = new TextField();
		grid.add(txtFSMTPServer, 1, 2, 1, 1);

		Label labelEmailTitle = new Label(resourceBundle.getString("tab.three.emailTitle"));
		grid.add(labelEmailTitle, 0, 3, 1, 1);

		TextField txtFEmailTitle = new TextField();
		grid.add(txtFEmailTitle, 1, 3, 1, 1);

		Label labelEmailHead = new Label(resourceBundle.getString("tab.three.emailIntroduction"));
		grid.add(labelEmailHead, 0, 4, 1, 1);

		TextArea txtAEmailIntroduction = new TextArea();
		txtAEmailIntroduction.setPrefWidth(220);
		grid.add(txtAEmailIntroduction, 1, 4, 1, 1);

		Label labelEmailSign = new Label(resourceBundle.getString("tab.three.emailEnd"));
		grid.add(labelEmailSign, 0, 5, 1, 1);

		TextArea txtAEmailSign = new TextArea();
		txtAEmailSign.setPrefWidth(220);
		grid.add(txtAEmailSign, 1, 5, 1, 1);

		Button btnSave = new Button(resourceBundle.getString("tab.three.btnSave"));
		grid.add(btnSave, 0, 6, 1, 1);

		Button btnWithoutSave = new Button(resourceBundle.getString("tab.three.btnWithoutSave"));
		grid.add(btnWithoutSave, 1, 6, 1, 1);
		
		PasswordField txtFPassword = new PasswordField();
		grid.add(txtFPassword, 0, 7, 1, 1);
		
		Button btnCheckSendEmail = new Button(resourceBundle.getString("tab.three.btnCheckSendEmail"));
		grid.add(btnCheckSendEmail, 1, 7, 1, 1);
		
		//Load configuration			
		reloadConfiguration(txtFFrom, txtFUserEmail, txtFSMTPServer, txtFEmailTitle, txtAEmailIntroduction, txtAEmailSign);
		
		// Actions
		// Al clicar sobre el botón, salvamos las opciones en un fichero de texto
		btnSave.setOnAction(e -> saveConfiguration(txtFFrom.getText(), txtFUserEmail.getText(), txtFSMTPServer.getText(), txtFEmailTitle.getText(), txtAEmailIntroduction.getText(), txtAEmailSign.getText()));
		
		btnWithoutSave.setOnAction(e -> reloadConfiguration(txtFFrom, txtFUserEmail, txtFSMTPServer, txtFEmailTitle, txtAEmailIntroduction, txtAEmailSign));
		
		btnCheckSendEmail.setOnAction(e -> sendEmail(txtFPassword.getText(), txtFFrom.getText(), true, "", txtFEmailTitle.getText(), resourceBundle.getString("tab.three.testEmail")));

		return tab;
	}	
	
	private void saveConfiguration(String from, String userEmail, String SMTPServer, String title, String introduction, String sign) {
		new MainActions().saveConfiguration(new Configuration(from, userEmail, SMTPServer, title, introduction, sign));
	}
	
	private void reloadConfiguration(TextField tftFrom, TextField tftUserEmail, TextField tftSMTPServer, TextField tftEmailTitle, TextArea txtAEmailIntroduction, TextArea txtAEmailSign) {
		Configuration configuration = new MainActions().loadConfiguration();
		
		//comboBox.getSelectionModel().select(configuration.getLanguageID());
		tftFrom.setText(configuration.getFrom());
		tftUserEmail.setText(configuration.getUserEmail());
		tftSMTPServer.setText(configuration.getSMTPServer());
		tftEmailTitle.setText(configuration.getTitle());
		txtAEmailIntroduction.setText(configuration.getIntroduction());
		txtAEmailSign.setText(configuration.getSign());
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
		resourceBundle = ResourceBundle.getBundle(Persistence.i18n_PATH);
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