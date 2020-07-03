package mathiasuy.md2.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import mathiasuy.md2.configuration.Properties;
import mathiasuy.md2.model.Calc;

public class InicioController implements Initializable  {

    @FXML private TextField txtResConjunto;
    @FXML private TextField txtGeneradores;
    @FXML private TextField txtPares;
    @FXML private TextField txtFactoresPrimos;
    @FXML private TextField txtResMcd;
    @FXML private TextField txtResMcm;
    @FXML private CheckBox chkBox;
    @FXML private TextField txtN;
    @FXML private TextField txtResPhi;
    @FXML private TextField txtResCantidadGeneradores;
    @FXML private ListView<String> lstConsole;
    @FXML private ListView<String> lstOrden;
	
	private static Logger logger = LogManager.getLogger(InicioController.class);
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chkBox.setDisable(true);
	    txtResConjunto.setEditable(false);
	    txtGeneradores.setEditable(false);
	    txtFactoresPrimos.setEditable(false);
	    txtResMcd.setEditable(false);
	    txtResMcm.setEditable(false);
	    chkBox.setStyle("-fx-opacity: 1");
	    chkBox.setDisable(true);
	    txtResPhi.setEditable(false);
	    txtResCantidadGeneradores.setEditable(false);		
	    Calc.setInicioController(this);
	}

	public void appendLog(String text){
		lstConsole.getItems().add("\n" + new Date().toGMTString()  + ": " + text);
		logger.info(text);
	}

	@FXML public void calcularN() {
		Integer n = Integer.valueOf(txtN.getText().trim());

		//// FACTORES PIRMOS ////
		appendLog("Factores primos: ");
		List<Integer> primos = Calc.descomponerEnFactoresPrimos(n);
		Map<Integer, Integer> primosPotencias = Calc.interpretarDescomponerEnFactoresPrimos(primos);
		
		String factores = "";
		Double verif = 1.0;
		for (Entry<Integer, Integer> entry : primosPotencias.entrySet()) {
			factores += " " + entry.getKey() + "^"+ entry.getValue() + ", ";
			verif *= Math.pow(entry.getKey(),entry.getValue());
		}
		if (verif.equals(Double.valueOf(n))) {
			txtFactoresPrimos.setText(factores);
			appendLog(factores);
		}else {
			txtFactoresPrimos.setText("Hubo un error en el calculo");
		}
		
		///// CONJUNTO DE INVERTIBLES MOD N /////////
		
		List<Integer> invertibles = Calc.coprimosMenores(n);
		txtResConjunto.setText(converToStringList(invertibles));
		
		///// CALCULO DE PHI ////////
		appendLog("El valor de phi es " + invertibles.size());
		txtResPhi.setText(String.valueOf(invertibles.size()));
		
		
		/////// VER SI ES GENERADOR /////////
		lstOrden.getItems().clear();
		
		chkBox.setDisable(false);
		Collections.sort(invertibles);
		List<Integer> raicesPrimitivas = new ArrayList<Integer>();
					List<Integer> encontrados = new ArrayList<Integer>();
			
		for (int i = 1; i < invertibles.size(); i++) {
			List<String> items = new ArrayList<String>();
			int cantGenerados = 0;
			for (int k = 1; k < invertibles.size(); k++) { //evito el 1
				int pot = 1;
				while ( pot < Properties.MAX_POW && Math.pow(invertibles.get(i), pot) % n != invertibles.get(k)) {
					pot++;
				}
				if ( Math.pow(invertibles.get(i), pot) % n == invertibles.get(k)) {
					cantGenerados++;
					items.add(
							invertibles.get(i) + "^" + pot + " = " + 
									Math.pow(invertibles.get(i), pot)	+ " = " + 
									Math.pow(invertibles.get(i), pot)%n + " mod(" + n + ")"
							);
					encontrados.add(invertibles.get(k));
				}
				
			}
			if (cantGenerados == invertibles.size()-1) {
				raicesPrimitivas.add(invertibles.get(i));
				lstOrden.getItems().add("--------------------");
				lstOrden.getItems().addAll(items);
				lstOrden.getItems().add("--------------------");
			}
		}
		chkBox.setSelected(!raicesPrimitivas.isEmpty());
		txtGeneradores.setText(converToStringList(raicesPrimitivas));
		txtResCantidadGeneradores.setText(String.valueOf(raicesPrimitivas.size()));
	}
	
	@FXML public void ordenEnN() {
		Integer n = Integer.valueOf(txtN.getText().trim());
		List<Integer> invertibles = Calc.coprimosMenores(n);
		lstOrden.getItems().clear();
		for (Integer num : invertibles) {
			ordenEnN(num, n);
		}
	}
	
	private void ordenEnN(Integer num, Integer n) {
		int i=0;
		do {
			i++;
		} while (Math.pow(num, i)%n != 1 && i < Properties.MAX_POW);	
		if (Math.pow(num, i)%n == 1) {
			lstOrden.getItems().add(num + "^" + i + " = " + Math.pow(num, i) + " = " + Math.pow(num, i)%n + " mod(" + n + ")");
			appendLog("En U("+n+"), el orden de "+num+" es " + i);
		}
	}

	private String converToStringList(List<Integer> lista) {
		String res = "{ ";
		if (!lista.isEmpty()) {
			for (int i = 0; i < lista.size()-1; i++) {
				res += lista.get(i) + ", ";
			}
			res += lista.get(lista.size()-1) + " }";
		}else {
			res += " }";
		}
		return res;
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

