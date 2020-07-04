package mathiasuy.md2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mathiasuy.md2.configuration.Properties;
import mathiasuy.md2.controller.InicioController;

public class RaizPrmitiva {

	private static InicioController inicioController;

	public static List<Double> getDivisores(Double n) {
		List<Double> lista = new ArrayList<Double>();
		for (Double i = 1.0; i < n; i++) {
			if (n%i==0) {
				lista.add(i);
			}
		}
		return lista;
	}
	
	public static List<Double> getDivisoresPrimos(Double n) {
		List<Double> primos = new ArrayList<Double>();
		List<Double> lista = getDivisores(n);
		
		List<Double> temp = new ArrayList<Double>();
		for (Double num : lista) {
			for (Double i = 1.0; i <= num; i++) {
				if (num%i==0) {
					temp.add(i);
				}
			}
			if (temp.size()==2) {
				primos.add(num);
			}
			temp.clear();
		}
		return primos;
	}
	
	public static boolean esRaiz(Double num, Double n) {
		Double phi = Double.valueOf(Calc.coprimosMenores(n).size());
		List<Double> divisores = getDivisoresPrimos(phi);
		int i;
		for(i = 0; i < divisores.size(); i++) {
			inicioController.appendLog("Se probará si " + num + "^("+phi+"/"+divisores.get(i)+") = " + (num + "^"+phi/divisores.get(i)) + " no es 1 mod("+n+") para que sea raiz." );
			if (Math.pow(num, phi/divisores.get(i)) % n == 1) {
				inicioController.appendLog("Se encontró que " + num + "^("+phi+"/"+divisores.get(i)+") = 1 mod("+n+"), con "+divisores.get(i)+" divisor de phi("+ n +") y distinto, por lo que "+num+" no es raiz primitva porque " + num + "^" + phi/divisores.get(i) + " = 1 mod("+n+")" );
				return false;
			}else {
				inicioController.appendLog("OK." );
			}
		}
		inicioController.appendLog("ES." );
		return true;
	}
		
	public static List<Double> hallarRaices(Double n) {
		List<Double> lista = new ArrayList<Double>();
		List<Double> invertibles = Calc.coprimosMenores(n);
		for (int i = 1; i < invertibles.size(); i++) {
			if (esRaiz(invertibles.get(i), n)) {
				lista.add(invertibles.get(i));
			}
		}
		Collections.sort(lista);
		return lista;
	}

	public static List<Double> elementosQueGenera(Double num, Double n) {
		List<Double> lista = new ArrayList<Double>();
		Double primero = Math.pow(num, 0)%n;
		int k = 0;
		Integer pot = 1;
		while (!lista.contains(Math.pow(num, pot)%n)) {
			if (!lista.contains(Math.pow(num, pot)%n)) {
				lista.add(Math.pow(num, pot)%n);
			}
			pot++;
		}
		Collections.sort(lista);
		inicioController.appendLog(num + " genera " + String.valueOf(lista));
		return lista;
	}
	
	public static void setInicioController(InicioController inicioController) {
		RaizPrmitiva.inicioController = inicioController;
	}

	public static List<Double> hallarLasOtrasRaices(List<Double> primera, Double n) {
		List<Double> lista = new ArrayList<Double>();
		List<Double> invertibles = Calc.coprimosMenores(Double.valueOf(Calc.coprimosMenores(n).size()));
		invertibles.remove(1.0);
		for (Double a : invertibles) {
			for (Double item : primera) {
				if (!lista.contains(Math.pow(item, a)%n)) {
					lista.add(Math.pow(item, a)%n);
				}
			}
		}
		Collections.sort(lista);
		return lista;
	}
	
}
