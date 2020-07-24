package mathiasuy.md2.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import mathiasuy.md2.configuration.Properties;
import mathiasuy.md2.model.Calc;
import mathiasuy.md2.model.RaizPrmitiva;

public class InicioController implements Initializable  {

    @FXML private TextField txtResConjunto;
    @FXML private TextField txtGeneradores;
    @FXML private TextField txtPares;
    @FXML private TextField txtRaiz;
    @FXML private TextField txtFactoresPrimos;
    @FXML private TextField txtResMcd;
    @FXML private TextField txtResMcm;
    @FXML private CheckBox chkBox;
    @FXML private TextField txtN;
    @FXML private TextField txtModN;
    @FXML private TextField txtResPhi;
    @FXML private TextField txtResCantidadGeneradores;
    @FXML private TextField txtResDivisores;
    @FXML private TextField txtResRaicesPrimitivas;
    @FXML private ListView<String> lstConsole;
    @FXML private ListView<String> lstOrden;
    @FXML private Label lblEsRaiz;
    @FXML private Label lblModN;
    
    public static final char POT = '^';
	
	private static Logger logger = LogManager.getLogger(InicioController.class);
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chkBox.setDisable(true);
	    txtResConjunto.setEditable(false);
	    txtGeneradores.setEditable(false);
	    txtFactoresPrimos.setEditable(false);
	    txtResMcd.setEditable(false);
	    txtResMcm.setEditable(false);
	    txtResRaicesPrimitivas.setEditable(false);
	    chkBox.setStyle("-fx-opacity: 1");
	    chkBox.setDisable(true);
	    txtResDivisores.setEditable(false);
	    txtResPhi.setEditable(false);
	    txtResCantidadGeneradores.setEditable(false);		
	    Calc.setInicioController(this);
	    RaizPrmitiva.setInicioController(this);
	}

	public void appendLog(String text){
		lstConsole.getItems().add("\n" + new Date().toGMTString()  + ": " + text);
		logger.info(text);
	}

	@FXML public void calcularN() {
		Double n = resolverPotencia(txtN.getText());

		//// FACTORES PIRMOS ////
		appendLog("Factores primos: ");
		List<Double> primos = Calc.descomponerEnFactoresPrimos(n);
		Map<Double, Double> primosPotencias = Calc.interpretarDescomponerEnFactoresPrimos(primos);
		
		String factores = "";
		int verif = 1;
		for (Entry<Double, Double> entry : primosPotencias.entrySet()) {
			factores += " " + entry.getKey() + POT+ entry.getValue() + ", ";
			verif *=  Math.abs(Math.pow(entry.getKey(),entry.getValue()));
		}
		if (verif ==n.intValue()) {
			txtFactoresPrimos.setText(factores);
			appendLog(factores);
		}else {
			txtFactoresPrimos.setText("Hubo un error en el calculo");
		}
		
		///// CONJUNTO DE INVERTIBLES MOD N /////////
		
		List<Double> invertibles = Calc.coprimosMenores(n);
		txtResConjunto.setText(converToStringList(invertibles));
		
		///// CALCULO DE PHI ////////
		appendLog("El valor de phi es " + invertibles.size());
		txtResPhi.setText(String.valueOf(invertibles.size()));
		
		
		/////// VER SI ES GENERADOR /////////
		lstOrden.getItems().clear();
		
		chkBox.setDisable(false);
		Collections.sort(invertibles);
		List<Double> generadores = new ArrayList<Double>();
		chkBox.setSelected(!generadores.isEmpty());
//		txtGeneradores.setText(converToStringList(raicesPrimitivas));
		txtResCantidadGeneradores.setText(String.valueOf(Calc.coprimosMenores(Double.valueOf(invertibles.size())).size()) + " (si es cíclico)");
		
		generadores.clear();
		for (int i = 0; i < invertibles.size(); i++) {
			if (RaizPrmitiva.elementosQueGenera(invertibles.get(i), n).containsAll(invertibles)) {
				appendLog("Coincide con U(n)");
				generadores.add(invertibles.get(i));
			}else {
				appendLog("No coincide con U(n)");
			}
		}
		chkBox.setSelected(!generadores.isEmpty());
		
		txtGeneradores.setText(String.valueOf(generadores));
//		txtGeneradores.setText(String.valueOf(RaizPrmitiva.hallarRaices(n)));
		
		txtResDivisores.setText(String.valueOf(RaizPrmitiva.getDivisoresPrimos(Double.valueOf(invertibles.size()))));
		List<Double> raices = RaizPrmitiva.hallarLasOtrasRaices(generadores, n);
		txtResRaicesPrimitivas.setText(String.valueOf(raices) + " son ("+raices.size()+")");
		
	}
	
	@FXML public void ordenEnN() {
		Double n = resolverPotencia(txtN.getText());
		List<Double> invertibles = Calc.coprimosMenores(n);
		lstOrden.getItems().clear();
		for (Double num : invertibles) {
			ordenEnN(num, n);
		}
	}
	
	@FXML public void verSiEsRaiz() {
		Double n = resolverPotencia(txtN.getText());
		Double num = resolverPotencia(txtRaiz.getText());
		boolean es = false;
		Collection<?> invertibles = Calc.coprimosMenores(n);
		if (RaizPrmitiva.elementosQueGenera(num, n).containsAll(invertibles )) {
			appendLog("Coincide con U(n)");
			List<Double> generadores = new ArrayList<Double>();
			generadores .add(num);
			es = true;
		}else {
			appendLog("No coincide con U(n)");
		}		
		
		lblEsRaiz.setText(num+" " + (es?"Es raíz":"No es raíz") + " de U("+n+")");
//		lblEsRaiz.setText(num+" " + (RaizPrmitiva.esRaiz(num,n)?"Es raíz":"No es raíz") + " de U("+n+")");
	}
	
	private void ordenEnN(Double num, Double n) {
		int i=0;
		do {
			i++;
		} while (Math.pow(num, i)%n != 1 && i < Properties.MAX_POW);	
		if (Math.pow(num, i)%n == 1) {
			lstOrden.getItems().add(num + "^" + i + " = " +  Math.abs(Math.pow(num, i)) + " = " +  Math.abs(Math.pow(num, i)%n) + " mod(" + n + ")");
			appendLog("En U("+n+"), el orden de "+num+" es " + i);
		}
	}

	private String converToStringList(List<Double> lista) {
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
		List<Double> nums = new ArrayList<Double>();
		while (numeros.contains(",")) {
			nums.add(resolverPotencia(numeros.substring(numeros.lastIndexOf(',')+1, numeros.length())));
			numeros = numeros.substring(0, numeros.lastIndexOf(','));
		}
		nums.add(resolverPotencia(numeros.trim()));
		if (!nums.isEmpty()) {			
			txtResMcd.setText(String.valueOf(Calc.mcd(nums)));
			txtResMcm.setText(String.valueOf(Calc.mcm(nums)));
		}
	}

	private List<Double> pasarALista(char pivote, String txt){
		String numeros = txt;
		List<Double> nums = new ArrayList<Double>();
		while (numeros.contains(pivote+"")) {
			nums.add(Double.valueOf(numeros.substring(numeros.lastIndexOf(pivote)+1, numeros.length())));
			numeros = numeros.substring(0, numeros.lastIndexOf(pivote));
		}
		nums.add(Double.valueOf(numeros.trim()));
		Collections.reverse(nums);
		return nums;
		}
		
	@FXML public void modN() {
		appendLog("****SE SOILICITÓ CALCULAR EL MOD*****");
		Double n = resolverPotencia(txtN.getText());
		Double num = resolverPotencia(txtModN.getText());
		lblModN.setText(String.valueOf(modn(num,n)) + " mod("+n+")");		
	} 
	
	private Double resolverPotencia(String text) {
		List<Double> lista = pasarALista(POT, text);
		Double res = lista.get(0);
		Double prod = 1.0;
		for (int i = 1; i < lista.size(); i++) {
			res = Math.pow(res, lista.get(i));
			prod *= lista.get(i);
		}
		if (lista.size() > 1) {
			appendLog(lista.get(0) + POT + prod + " = " + res);
		}
		return res;
	}
	
	private Double modn(Double num, Double n) {	
		appendLog(num + " = " + num%n + " mod("+n+")");
		return num%n;
	}
	 
	
}

