package uniovi.cgg.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
import uniovi.cgg.logic.models.Options;
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

	private final int STAGE_WIDTH = 1024;
	private final int STAGE_HEIGHT = 768;
	private final int MAX_WIDTH_TEXTAREA = STAGE_WIDTH*2;
	private final int MAX_HEIGHT_TEXTAREA = STAGE_HEIGHT*2;	

	private static final String MAILTO = "mailto:";

	private static ResourceBundle resourceBundle = null;
	
	private TabPane tabPane = null;

	private UseCase useCase = null;
	
	private File loadUseCaseTab2 = null;

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
		
		//grid.setPrefSize(300000, 300000);
		//grid.prefWidthProperty().bind(tab.widthProperty());		
		//maxHeight="Infinity" maxWidth="Infinity"
		//grid.prefHeight(Priority.ALWAYS);
		
		tab.setContent(grid);
		
		//GridPane.setHgrow(grid, Priority.ALWAYS);
		//GridPane.setVgrow(grid, Priority.ALWAYS);

		Button btnLoad = new Button(resourceBundle.getString("tab.one.btnLoad"));
		// Node to include, Column index, Row index, [Column span, Row span] -> How many row and columns needs the component 
		grid.add(btnLoad, 0, 0, 1, 1);

		Button btnGenerate = new Button(resourceBundle.getString("tab.one.btnGenerate"));
		grid.add(btnGenerate, 1, 0, 1, 1);

		TextArea txtAGeneratedText = new TextArea(resourceBundle.getString("tab.one.txtAGeneratedText"));
		grid.add(txtAGeneratedText, 0, 1, 2, 1);
		txtAGeneratedText.setWrapText(true);		
		txtAGeneratedText.setPrefWidth(MAX_WIDTH_TEXTAREA);
		txtAGeneratedText.setPrefHeight(MAX_HEIGHT_TEXTAREA);

		Label sendTo = new Label(resourceBundle.getString("tab.one.sendTo"));
		grid.add(sendTo, 0, 4, 1, 1);

		TextField txtFSendTo = new TextField();
		grid.add(txtFSendTo, 1, 4, 1, 1);
		
		Label labelBccEmails = new Label(resourceBundle.getString("tab.one.bccEmails"));
		grid.add(labelBccEmails, 0, 5, 1, 1);

		TextField txtFbccEmails = new TextField();
		grid.add(txtFbccEmails, 1, 5, 1, 1);
		
		Label labelPassword = new Label(resourceBundle.getString("tab.one.password"));
		grid.add(labelPassword, 0, 6, 1, 1);

		PasswordField txtFPassword = new PasswordField();
		grid.add(txtFPassword, 1, 6, 1, 1);

		CheckBox ccToMe = new CheckBox(resourceBundle.getString("tab.one.ccToMe"));
		grid.add(ccToMe, 0, 7, 1, 1);
		
		Button btnSend = new Button(resourceBundle.getString("tab.one.btnSend"));
		grid.add(btnSend, 1, 7, 1, 1);

		// Actions
		// Al clicar sobre el botón de cargar, abrimos un nuevo FileChooser
		btnLoad.setOnAction(e -> loadUseCaseToGenerateTab(btnGenerate));

		// Deshabilitado al inicio ya que no hay ningún texto guardado
		btnGenerate.setDisable(true);
		btnGenerate.setOnAction(e -> txtAGeneratedText.setText(useCase.generateExercise()));
		
		btnSend.setOnAction(e -> sendEmail(txtFPassword.getText(), txtFSendTo.getText(), ccToMe.isSelected(), txtFbccEmails.getText(), "título", txtAGeneratedText.getText()));		
		
		return tab;
	}
	
	/**
	 * Abre el FileChooser, recibe el fichero cargado en caso de haberlo, y llama al método necesario para cargar un Caso de uso desde ese fichero. Después, habilita el boton generar. Si recibe null dle fichero, no hace nada
	 * @param btnGenerate Button
	 */
	private void loadUseCaseToGenerateTab (Button btnGenerate) {
		File file = openFileChooser();
		
		if(file != null) {
			useCase = new MainActions().loadFile(file);
			btnGenerate.setDisable(false);
			loadUseCaseTab2 = file;
		} else {
			// TODO mostrar popup
			System.out.println("Cancelada apertura de fichero -> File == null");
		}		
	}

	/**
	 * Recibe todos lso datos necesarios para enviar un correo usando la clase SendEmails. Lo que hace el método es recibir su excepción y si hay un CC añadirlo
	 * @param password String
	 * @param sendTo String
	 * @param cc boolean
	 * @param bccEmails String
	 * @param title String
	 * @param text String
	 */
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
	
	/**
	 * Creación y configuración del FileChooser de la aplicación
	 * @return FielChooser FileChooser creado y configurado
	 */
	private FileChooser fileChooserConfiguration() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(resourceBundle.getString("tab.one.btnLoad.fileChooser"));
		//fileChooser.setInitialDirectory(new File(System.getProperty("user.home"), System.getProperty("user.dir")+Persistance.FOLDER)); 
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(JSON_FILE, JSON_EXTENSION));
		
		return fileChooser;
	}
	
	/**
	 * Abre el buscador de ficheros del sistema operativo para buscar un fichero y abrirlo
	 * @return File fichero elegido, en nuestro caso es un texto plano
	 */
	private File openFileChooser() {
		FileChooser fileChooser = fileChooserConfiguration();		
		return fileChooser.showOpenDialog(new Stage());
	}
	
	/**
	 * Abre el buscador de ficheros del sistema operativo para buscar una ruta y crear un fichero
	 * @return File fichero elegido, en nuestro caso es un texto plano
	 */
	private File saveFileChooser() {
		FileChooser fileChooser = fileChooserConfiguration();
		return fileChooser.showSaveDialog(new Stage());
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
		grid.setMaxWidth(3000);
		grid.setMaxHeight(100);		
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefSize(5000000, 50000);

		tab.setContent(scrollPane);		
		
		scrollPane.setContent(grid);
		
		// Botonera
		Button btnLoad = new Button(resourceBundle.getString("tab.one.btnLoad"));
		// Node to include, Column index, Row index, [Column span, Row span] -> How many row and columns needs the component 
		grid.add(btnLoad, 0, 0, 1, 1);

		Button btnAddRow = new Button(resourceBundle.getString("tab.two.btnAddRow"));
		grid.add(btnAddRow, 1, 0, 1, 1);
		
		Button btnSave = new Button(resourceBundle.getString("tab.three.btnSave"));
		grid.add(btnSave, 2, 0, 1, 1);
		
		Button btnOverwrite = new Button(resourceBundle.getString("tab.three.btnOverwrite"));
		grid.add(btnOverwrite, 3, 0, 1, 1);
		btnOverwrite.setDisable(true);
		btnOverwrite.setDisable(true);
		
		Button btnCancel = new Button(resourceBundle.getString("tab.three.btnCancel"));
		grid.add(btnCancel, 4, 0, 1, 1);
		btnCancel.setDisable(true);
		
		// Cabeceras
		Label labelID = new Label(resourceBundle.getString("tab.two.id"));
		grid.add(labelID, 0, 1, 1, 1);
		
		Label labelName = new Label(resourceBundle.getString("tab.two.name"));
		grid.add(labelName, 1, 1, 1, 1);
		
		Label labelIntroduction = new Label(resourceBundle.getString("tab.two.introduction"));
		grid.add(labelIntroduction, 2, 1, 1, 1);
		
		Label labelConclusions = new Label(resourceBundle.getString("tab.two.conclusions"));
		grid.add(labelConclusions, 3, 1, 1, 1);
		
		Label labelOptions = new Label(resourceBundle.getString("tab.two.options"));
		grid.add(labelOptions, 4, 1, 1, 1);
		
		Label labelProbabilityModified = new Label(resourceBundle.getString("tab.two.probabilityModified"));
		grid.add(labelProbabilityModified, 5, 1, 1, 1);
		
		int previousChildren = grid.getChildren().size();
		
		addNewRow(grid, grid.getRowCount());
		
		// Actions
		btnLoad.setOnAction(e -> loadUseCaseWithFileChooser(grid, previousChildren, btnOverwrite, btnCancel));
		btnAddRow.setOnAction(e -> addNewRow(grid, grid.getRowCount()));
		btnSave.setOnAction(e -> saveUseCase(grid, previousChildren, false));
		btnOverwrite.setOnAction(e -> saveUseCase(grid, previousChildren, true));
		btnCancel.setOnAction(e -> loadLoadedUseCase(loadUseCaseTab2, grid, previousChildren));
		
		return tab;
	}
	
	private void saveUseCase(GridPane grid, int previousChildren, boolean overwrite) {
		UseCase useCase = new UseCase();
		ObservableList<Node> columns;
		List<String[]> optionsList;
		
		long id = 0;
		String name = "";
		String introduction = "";
		String conclusions = "";		
		
		//+6 pq cada fila tiene 6 columnas (no he encontrado com oiterar de fila en fila :S)
		for(int i = previousChildren, length = grid.getChildren().size(); i < length; i+=6) {
			columns = grid.getChildren();
			optionsList = new ArrayList<String[]>();
			
			try {
				id = Long.parseLong(((TextField)columns.get(i)).getText());
			}catch (NumberFormatException e) {
				// Popup y que corrija los ID posiblemente pero mejor hacer que sean autoincrementales
				id = 0;
			}
			name = ((TextField)columns.get(i+1)).getText();
			introduction = ((TextArea)columns.get(i+2)).getText();
			conclusions = ((TextArea)columns.get(i+3)).getText();
			
			GridPane gridOptions = (GridPane) columns.get(i+4);
			
			//+1 pq el primer objeto es el botón añadir opción y +4 pq itero por filas 
			for(int j = 1, lengthj = gridOptions.getChildren().size(); j < lengthj; j+=4) {
				ObservableList<Node> columns2 = gridOptions.getChildren();
				String[] option2 = new String[4];
				option2[0] = (String) ((TextArea)columns2.get(j+0)).getText(); // TEXT
				option2[1] = (String) ((TextField)columns2.get(j+1)).getText(); // PROBABILITY
				
				String dependecies = (String) ((TextField)columns2.get(j+2)).getText();
				if(dependecies.contentEquals("")) {
					dependecies = Options.NOTHING;
				}				
				option2[2] = dependecies; // DEPENDENCIES
				
				String depends_on = (String) ((TextField)columns2.get(j+3)).getText();
				if(depends_on.contentEquals("")) {
					depends_on = Options.NOTHING;
				}
				option2[3] = depends_on; // DEPENDS_ON

				optionsList.add(option2);
			}
			
			boolean probabilityModified = ((CheckBox)columns.get(i+5)).isSelected();			
			new Options(id, name, introduction, conclusions, optionsList, probabilityModified, useCase);
		}
		//System.out.println(useCase.toString());
		if(overwrite) {
			new MainActions().saveFile(loadUseCaseTab2, useCase);
		}else {
			File file = saveFileChooser();
			if(file != null) {
				new MainActions().saveFile(file, useCase);
			}
		}
	}
	
	/**
	 * Borra todos los hijos que necesitan ser renovados para dejar solo los que hay en común en la tab3 cuando se carga un nuevo caso de uso, que son las cabeceras
	 * @param grid GridPane grid en dónde se le quitarán los hijos innecesarios y se añadirán los nuevos
	 * @param previousChildren int hijos que no se han de borrar
	 */
	private void cleanTabUseCase(GridPane grid, int previousChildren) {
		ObservableList<Node> a = grid.getChildren();

		//-1 en ambos debido a que empieza en 0, y la contabilidad con size es total: 10 hijos, pero el 10 está en [9]
		for(int i = a.size()-1, length = previousChildren-1; i > length; i-- ) {
			a.remove(i);
		}
	}
	
	/**
	 * Abre el fileChooser para poder cargar un fichero usando la ventana del SO, después llama al método necesari opara cargar dicho ficharo en la pestaña de casos de uso y después habilita el botón cancelar
	 * @param grid GridPane grid en dónde se le quitarán lso hijos innecesarios y se añadirán los nuevos
	 * @param previousChildren int hijos que no se han de borrar
	 * @param btnCancel Button botón que hay que habilitar si se ha cargado el fichero correctamene para así pdoer cancelar los cambios usando este botón
	 */
	private void loadUseCaseWithFileChooser(GridPane grid, int previousChildren, Button btnOverwrite, Button btnCancel) {
		File file = openFileChooser();
		
		if(file != null) {
			loadLoadedUseCase(file, grid, previousChildren);
			loadUseCaseTab2 = file;
			btnOverwrite.setDisable(false);
			btnCancel.setDisable(false);
		} else {
			// TODO mostrar popup
			System.out.println("Cancelada apertura de fichero -> File == null");
		}		
	}
	
	/**
	 * Llama al método encargado de limpiar la tab y abre el fichero recibido, para así cargar un caso de uso para la pestaña de modificaciñon de casos de uso.
	 * @param file File fichero a cargar
	 * @param grid GridPane grid en dónde se le quitarán lso hijos innecesarios y s eañadirán los nuevos
	 * @param previousChildren int hijos que no se han de borrar
	 */
	private void loadLoadedUseCase(File file, GridPane grid, int previousChildren) {
		cleanTabUseCase(grid, previousChildren);
		useCase = new MainActions().loadFile(file);
		
		List<Options> options = useCase.getOptions();
		
		// previousRow debido a que hay elementos rpeviamente en el grid y esos no hay que borrarlos ni pisarlos
		int previousRows = grid.getRowCount();
		for(int i = 0, length = options.size(); i < length; i++) {
			addNewRowFromOption(grid, i+previousRows, options.get(i));				
		}
	}
	
	/**
	 * Añade una nueva fila al panel y en la fila que recibe con el contenido del caso de uso que recibe. La fila se añade a la pestaña 2, la de modificación y creación de casos de uso
	 * @param grid GridPane dónd eva a añadir la fila
	 * @param row int número de la fila en dónde se añadirá
	 * @param options Options Contenio a isnerta en la fila
	 */
	private void addNewRowFromOption(GridPane grid, int row, Options options) {		
		// Filas
		TextField txtFID = new TextField(String.valueOf(options.getID()));
		grid.add(txtFID, 0, row, 1, 1);
		
		TextField txtFName = new TextField(options.getName());
		grid.add(txtFName, 1, row, 1, 1);
		
		TextArea txtFIntroduction = new TextArea(options.getIntroduction());
		grid.add(txtFIntroduction, 2, row, 1, 1);
		
		TextArea txtFConclusions = new TextArea(options.getConclusions());
		grid.add(txtFConclusions, 3, row, 1, 1);
		
		// Interface
		GridPane gridOptions = gridOptions();
		grid.add(gridOptions, 4, row, 1, 1);
		
		Button btnAddOption = new Button(resourceBundle.getString("tab.two.btnAddOption"));
		gridOptions.add(btnAddOption, 0, 0, 1, 1);		

		// Para mantener los nodos que estén por encima en el gridOptions, como el botón de añadir. El -1 es debido a que el for comienza en 0 y el getRowCoutn devuelve el número desde 1
		int previousChildren = gridOptions.getRowCount();
		for(int i = 0, length = options.getOptions().size(); i < length; i++) {
		//for(int i = 1, length = options.getOptions().size(); i < length; i++) {
			 addOptionFromOption(gridOptions, i+previousChildren, options.getOptions().get(i));
		}
		
		CheckBox cbProbabilityModfied = new CheckBox();
		grid.add(cbProbabilityModfied, 5, row, 1, 1);
		cbProbabilityModfied.setSelected(options.isProbabilityModified());
		
		btnAddOption.setOnAction(e -> addOption(gridOptions, gridOptions.getRowCount()));		
	}
	
	private GridPane gridOptions(){
		GridPane gridOptions = generalGrid();
		gridOptions.setMaxWidth(750);
		gridOptions.setMaxHeight(100);
		return gridOptions;
	}
	
	/**
	 * Añade una nueva fila al panel que recibe y en la fila que recibe. La fila se añade a la pestaña 2, la de modificación y creación de casos de uso
	 * @param grid GridPane dónd eva a añadir la fila
	 * @param row int número de la fila en dónde se añadirá
	 */
	// TODO tengo una parte de esto medio repetido en otro sitio, mirar a ver si puedo juntarlo al refactorizar
	private void addNewRow(GridPane grid, int row) {	
		// TextArea permite \n, TextField no.
		TextField txtFID = new TextField();
		grid.add(txtFID, 0, row, 1, 1);
		
		TextField txtFName = new TextField();
		grid.add(txtFName, 1, row, 1, 1);
		
		TextArea txtFIntroduction = new TextArea();
		grid.add(txtFIntroduction, 2, row, 1, 1);		
		txtFIntroduction.setWrapText(true);
		
		TextArea txtFConclusions = new TextArea();
		grid.add(txtFConclusions, 3, row, 1, 1);
		txtFIntroduction.setWrapText(true);
		
		// Interface
		GridPane gridOptions = gridOptions();	
		
		grid.add(gridOptions, 4, row, 1, 1);
		
		Button btnAddOption = new Button(resourceBundle.getString("tab.two.btnAddOption"));
		gridOptions.add(btnAddOption, 0, 0, 1, 1);
		
		addOption(gridOptions, gridOptions.getRowCount());
		
		CheckBox cbProbabilityModfied = new CheckBox();
		grid.add(cbProbabilityModfied, 5, row, 1, 1);
		
		btnAddOption.setOnAction(e -> addOption(gridOptions, gridOptions.getRowCount()));		
	}
	
	/**
	 * Añade una nueva opción en el panel que recibe en la correspondiente fila que recibe
	 * @param gridOptions GridPane
	 * @param row int file dónde añadirá la opción
	 * @return boolean retorna si la probabilidad ha sido moficiada o no
	 */
	private void addOptionFromOption(GridPane gridOptions, int row, String[] strings) {		
		//TextField txtFOptions = new TextField();
		TextArea txtFOptions = new TextArea(strings[Options.TEXT]);
		// Node to include, Column index, Row index, [Column span, Row span]-> How many row and columns needs the component 
		gridOptions.add(txtFOptions, 0, row, 1, 1);
		txtFOptions.setWrapText(true);
		
		//TextField txtFProbability = new TextField();
		TextField txtFProbability = new TextField(strings[Options.PROBABILITY]);
		gridOptions.add(txtFProbability, 1, row, 1, 1);
		
		TextField txtFDependence;
		if(strings[Options.DEPENDENCIES].contentEquals(Options.NOTHING)) {
			txtFDependence = new TextField();
		}else {
			txtFDependence = new TextField(strings[Options.DEPENDENCIES]);
		}
		gridOptions.add(txtFDependence, 3, row, 1, 1);
		
		TextField txtFDependsOn;
		if(strings[Options.DEPENDS_ON].contentEquals(Options.NOTHING)) {
			txtFDependsOn = new TextField();
		}else {
			txtFDependsOn = new TextField(strings[Options.DEPENDS_ON]);
		}
		gridOptions.add(txtFDependsOn, 4, row, 1, 1);
	}
	
	/**
	 * Añade una nueva opción en el panel que recibe en la correspondiente fila que recibe
	 * @param gridOptions GridPane
	 * @param row int file dónde añadirá la opción
	 */
	private void addOption(GridPane gridOptions, int row) {
		TextArea txtFOptions = new TextArea();
		// Node to include, Column index, Row index, [Column span, Row span] -> How many row and columns needs the component 
		gridOptions.add(txtFOptions, 0, row, 1, 1);
		txtFOptions.setWrapText(true);
		
		TextField txtFProbability = new TextField();
		gridOptions.add(txtFProbability, 1, row, 1, 1);
		
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
		grid.add(txtAEmailIntroduction, 1, 4, 1, 1);
		txtAEmailIntroduction.setWrapText(true);

		Label labelEmailSign = new Label(resourceBundle.getString("tab.three.emailEnd"));
		grid.add(labelEmailSign, 0, 5, 1, 1);

		TextArea txtAEmailSign = new TextArea();
		grid.add(txtAEmailSign, 1, 5, 1, 1);
		txtAEmailSign.setWrapText(true);

		Button btnSave = new Button(resourceBundle.getString("tab.three.btnSave"));
		grid.add(btnSave, 0, 6, 1, 1);

		Button btnWithoutSave = new Button(resourceBundle.getString("tab.three.btnCancel"));
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
		if(checkExistingConfiguration()){
			Configuration configuration = new MainActions().loadConfiguration();
			
			//comboBox.getSelectionModel().select(configuration.getLanguageID());
			tftFrom.setText(configuration.getFrom());
			tftUserEmail.setText(configuration.getUserEmail());
			tftSMTPServer.setText(configuration.getSMTPServer());
			tftEmailTitle.setText(configuration.getTitle());
			txtAEmailIntroduction.setText(configuration.getIntroduction());
			txtAEmailSign.setText(configuration.getSign());		
		}
	}
	
	/**
	 * Comprueba que exista el fichero de configuración y devuelve true si existe y sino false
	 * @return boolean true - existe; false - no existe
	 */
	private boolean checkExistingConfiguration() {
		return Persistence.getInstance().checkExistingFile(Persistence.CONFIGURATION);
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

		FileInputStream fisImageEII = null;
		try {
			fisImageEII = new FileInputStream("resources/logoEII.png");
			Image imageEII = new Image(fisImageEII);
			ImageView imageViewEII = new ImageView(imageEII);
			grid.add(imageViewEII, 0, 0, 1, 1);
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}

		FileInputStream fisImageUniOvi = null;
		try {
			fisImageUniOvi = new FileInputStream("resources/logoUniOvi.png");
			Image imageUniOvi = new Image(fisImageUniOvi);
			ImageView imageViewUniOvi = new ImageView(imageUniOvi);
			grid.add(imageViewUniOvi, 1, 0, 1, 1);
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
			e1.printStackTrace();
		}
		
		Label labelDeveloper1 = new Label(resourceBundle.getString("tab.four.developer1"));
		grid.add(labelDeveloper1, 0, 1, 1, 1);

		Hyperlink linkDeveloper1Email = new Hyperlink(resourceBundle.getString("tab.four.developer1Email"));
		grid.add(linkDeveloper1Email, 1, 1, 1, 1);

		Hyperlink linkGitHub = new Hyperlink(resourceBundle.getString("tab.four.githubProject"));
		grid.add(linkGitHub, 0, 2, 1, 1);

		Label license = new Label(resourceBundle.getString("tab.four.license")); // TODO
		grid.add(license, 0, 3, 1, 1);

		Label version = new Label(resourceBundle.getString("tab.four.version")); // TODO
		grid.add(version, 1, 3, 1, 1);

		// Actions
		linkDeveloper1Email.setOnAction(e -> getHostServices().showDocument(MAILTO + linkDeveloper1Email.getText()));
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

		tabPane = createAndConfigureTabs();

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