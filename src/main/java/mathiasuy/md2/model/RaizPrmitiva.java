package mathiasuy.md2.model;

import java.util.ArrayList;
import java.util.List;

import mathiasuy.md2.controller.InicioController;

public class RaizPrmitiva {

	private static InicioController inicioController;

	public static List<Integer> getDivisores(Integer n) {
		List<Integer> lista = new ArrayList<Integer>();
		for (int i = 1; i < n; i++) {
			if (n%i==0) {
				lista.add(i);
			}
		}
		return lista;
	}
	
	public static List<Integer> getDivisoresPrimos(Integer n) {
		List<Integer> primos = new ArrayList<Integer>();
		List<Integer> lista = getDivisores(n);
		
		List<Integer> temp = new ArrayList<Integer>();
		for (Integer num : lista) {
			for (int i = 1; i <= num; i++) {
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
	
	public static boolean esRaiz(Integer num, Integer n) {
		List<Integer> invertibles = Calc.coprimosMenores(n);
		List<Integer> divisores = getDivisoresPrimos(invertibles.size());
		for(int i = 0; i < divisores.size(); i++) {
			inicioController.appendLog("Se probará si " + num + "^("+invertibles.size()+"/"+divisores.get(i)+") = " + (num + "^"+invertibles.size()/divisores.get(i)) + " no es 1 mod("+n+") para que sea raiz." );
			if (Math.pow(num, invertibles.size()/divisores.get(i)) % n == 1) {
				inicioController.appendLog("Se encontró que " + num + "^("+invertibles.size()+"/"+divisores.get(i)+") = 1 mod("+n+"), con "+divisores.get(i)+" divisor de phi("+ n +") y distinto, por lo que "+num+" no es raiz primitva porque " + num + "^" + divisores.get(i) + " = 1 mod("+n+")" );
				return false;
			}else {
				inicioController.appendLog("OK." );
			}
		}
		inicioController.appendLog("ES." );
		return true;
	}
	
	public static List<Integer> hallarRaices(Integer n) {
		List<Integer> lista = new ArrayList<Integer>();
		List<Integer> invertibles = Calc.coprimosMenores(n);
		for (int i = 1; i < invertibles.size(); i++) {
			if (esRaiz(invertibles.get(i), n)) {
				lista.add(invertibles.get(i));
			}
		}
		return lista;
	}

	public static void setInicioController(InicioController inicioController) {
		RaizPrmitiva.inicioController = inicioController;
	}
	
}
