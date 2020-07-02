package mathiasuy.md2.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
    @FXML TextField txtFactoresPrimos;
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

		List<Integer> primos = Calc.descomponerEnFactoresPrimos(Integer.valueOf(txtN.getText().trim()));
		Map<Integer, Integer> primosPotencias = Calc.interpretarDescomponerEnFactoresPrimos(primos);
		
		String factores = "";
		Double verif = 1.0;
		for (Entry<Integer, Integer> entry : primosPotencias.entrySet()) {
			factores += " " + entry.getKey() + "^"+ entry.getValue() + ", ";
			verif *= Math.pow(entry.getKey(),entry.getValue());
		}
		if (verif.equals(Double.valueOf(txtN.getText().trim()))) {
			txtFactoresPrimos.setText(factores);
		}else {
			txtFactoresPrimos.setText("Hubo un error en el calculo");
		}
		
		
	}

	@FXML public void calcularMcdMcm() {
		String numeros = txtPares.getText();
		List<Integer> nums = new ArrayList<Integer>();
		while (numeros.contains(",")) {
			nums.add(Integer.valueOf(numeros.substring(numeros.lastIndexOf(',')+1, numeros.length()).trim()));
			numeros = numeros.substring(0, numeros.lastIndexOf(','));
		}
		nums.add(Integer.valueOf(numeros.trim()));
		if (!nums.isEmpty()) {			
			txtResMcd.setText(String.valueOf(Calc.mcd(nums)));
		}
	} 
	 
	
}

