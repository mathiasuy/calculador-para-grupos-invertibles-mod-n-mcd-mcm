package mathiasuy.md2.main;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mathiasuy.md2.controller.InicioController;

public class MainApp extends Application implements Runnable {

	private static Logger logger = LogManager.getLogger(MainApp.class);
	
	public AnchorPane panelPane;
	  
	@Override
	public void run() {
		launch();
		
	} 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();			
			loader.setLocation(MainApp.class.getResource("/views/Inicio.fxml"));
			panelPane = (AnchorPane)loader.load();
			
			Scene scene = new Scene(panelPane);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			logger.error("Error levantando la interfaz grafica del Input.", e);
		}
	}
	
}
