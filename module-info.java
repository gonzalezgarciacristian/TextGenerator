module TextGenerator{
	requires javafx.controls;
	requires javafx.swing;
	requires javafx.fxml;
	requires json.simple;
	requires transitive javafx.graphics;
	requires java.mail;
	requires junit;
	
	opens uniovi.cgg to javafx.fxml;
	exports uniovi.cgg;
}