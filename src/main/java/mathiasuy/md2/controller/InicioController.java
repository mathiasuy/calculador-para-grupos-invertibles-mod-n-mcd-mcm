package mathiasuy.md2.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mathiasuy.md2.model.Calc;

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

	@FXML public void calcularN() {
		
	}

	@FXML public void calcularMcdMcm() {
		String numeros = txtPares.getText();
		List<Integer> nums = new ArrayList<Integer>();
		numeros.lastIndexOf(',');
		while (numeros.contains(",")) {
			nums.add(Integer.valueOf(numeros.substring(numeros.lastIndexOf(',')+1, numeros.length())));
			numeros = numeros.substring(0, numeros.lastIndexOf(','));
		}
		nums.add(Integer.valueOf(numeros));
		
		
		txtResMcd.setText(String.valueOf(Calc.mcd(nums)));
		
//		Integer.valueOf(txtPares.getText())
//		Calc.mcd()
	} 
	 
	
}

