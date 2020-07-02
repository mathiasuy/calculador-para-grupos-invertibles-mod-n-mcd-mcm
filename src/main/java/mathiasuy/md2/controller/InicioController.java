package mathiasuy.md2.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class InicioController implements Initializable  {

    @FXML TextField txtResConjunto;
    @FXML TextArea txtConsola;
    @FXML TextField txtGeneradores;
    @FXML TextField txtPares;
    @FXML TextField txtResMcd;
    @FXML TextField txtResMcm;
    @FXML CheckBox chkBox;
    @FXML TextField txtN;
    @FXML TextField txtResPhi;
    @FXML TextField txtResCantidadGeneradores;
    @FXML ListView<String> lstOrden;
	
	private static Logger logger = LogManager.getLogger(InicioController.class);

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setData(){
		
	} 
	 
	
}

