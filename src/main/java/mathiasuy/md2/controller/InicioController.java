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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
		Integer n = resolverPotencia(txtN.getText());

		//// FACTORES PIRMOS ////
		appendLog("Factores primos: ");
		List<Integer> primos = Calc.descomponerEnFactoresPrimos(n);
		Map<Integer, Integer> primosPotencias = Calc.interpretarDescomponerEnFactoresPrimos(primos);
		
		String factores = "";
		int verif = 1;
		for (Entry<Integer, Integer> entry : primosPotencias.entrySet()) {
			factores += " " + entry.getKey() + POT+ entry.getValue() + ", ";
			verif *= (int) Math.round(Math.pow(entry.getKey(),entry.getValue()));
		}
		if (verif ==n.intValue()) {
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
									(int) Math.round(Math.pow(invertibles.get(i), pot))	+ " = " + 
									(int) Math.round(Math.pow(invertibles.get(i), pot)%n) + " mod(" + n + ")"
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
		
		txtResDivisores.setText(String.valueOf(RaizPrmitiva.getDivisoresPrimos(invertibles.size())));
		txtResRaicesPrimitivas.setText(String.valueOf(RaizPrmitiva.hallarRaices(n)));
		
	}
	
	@FXML public void ordenEnN() {
		Integer n = resolverPotencia(txtN.getText());
		List<Integer> invertibles = Calc.coprimosMenores(n);
		lstOrden.getItems().clear();
		for (Integer num : invertibles) {
			ordenEnN(num, n);
		}
	}
	
	@FXML public void verSiEsRaiz() {
		Integer n = resolverPotencia(txtN.getText());
		Integer num = resolverPotencia(txtRaiz.getText());
		lblEsRaiz.setText(num+" " + (RaizPrmitiva.esRaiz(num,n)?"Es ra�z":"No es ra�z") + " de U("+n+")");
	}
	
	private void ordenEnN(Integer num, Integer n) {
		int i=0;
		do {
			i++;
		} while (Math.pow(num, i)%n != 1 && i < Properties.MAX_POW);	
		if (Math.pow(num, i)%n == 1) {
			lstOrden.getItems().add(num + "^" + i + " = " + (int) Math.round(Math.pow(num, i)) + " = " + (int) Math.round(Math.pow(num, i)%n) + " mod(" + n + ")");
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
			nums.add(resolverPotencia(numeros.substring(numeros.lastIndexOf(',')+1, numeros.length())));
			numeros = numeros.substring(0, numeros.lastIndexOf(','));
		}
		nums.add(resolverPotencia(numeros.trim()));
		if (!nums.isEmpty()) {			
			txtResMcd.setText(String.valueOf(Calc.mcd(nums)));
		}
	}

	private List<Integer> pasarALista(char pivote, String txt){
		String numeros = txt;
		List<Integer> nums = new ArrayList<Integer>();
		while (numeros.contains(pivote+"")) {
			nums.add(Integer.valueOf(numeros.substring(numeros.lastIndexOf(pivote)+1, numeros.length()).trim()));
			numeros = numeros.substring(0, numeros.lastIndexOf(pivote));
		}
		nums.add(Integer.valueOf(numeros.trim()));
		return nums;
		}
		
	@FXML public void modN() {
		appendLog("****SE SOILICIT� CALCULAR EL MOD*****");
		Integer n = resolverPotencia(txtN.getText());
		Integer num = resolverPotencia(txtModN.getText());
		modn(num,n);		
	} 
	
	private Integer resolverPotencia(String text) {
		List<Integer> lista = pasarALista(POT, text);
		Collections.reverse(lista);
		Integer res = lista.get(0);
		Integer prod = 1;
		for (int i = 1; i < lista.size(); i++) {
			res = (int) Math.round(Math.pow(res, lista.get(i)));
			prod *= lista.get(i);
		}
		if (lista.size() > 1) {
			appendLog(lista.get(0) + POT + prod + " = " + res);
		}
		return res;
	}
	
	private Integer modn(Integer num, Integer n) {	
		appendLog(num + " = " + num%n + " mod("+n+")");
		return num%n;
	}
	 
	
}

