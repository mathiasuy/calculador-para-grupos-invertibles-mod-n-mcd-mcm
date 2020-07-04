package mathiasuy.md2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mathiasuy.md2.configuration.Properties;
import mathiasuy.md2.controller.InicioController;

public class Calc {
	
	private static InicioController inicioController;

	/**
	 * Calcula el mcd de todos los elementos de la lista
	 * @param items
	 * @return
	 */
	public static Integer mcd(List<Integer> items) {

		Collections.sort(items);

		Calc.inicioController.appendLog("****MCD SOLICITADO: mcd("+String.valueOf(items) +")****");
		int divisor = items.get(0);
		for (Integer item : items) {
			 Calc.inicioController.appendLog("Se hallará mcd("+ divisor + "," + item + "): ");
			divisor = mcd (divisor, item);
		}
		 Calc.inicioController.appendLog("El resultado es " + divisor);
		
		return divisor;
	}
	
	/**
	 * Resolusión utilizando elalgoritmo de euclides
	 * @param a
	 * @param b
	 * @return
	 */
	private static String eucli = "Comienza alg de Euclides: ";
	public static Integer mcd(Integer a, Integer b) {
		 if (a == 0) {
			 eucli += " mcd("+a+", "+b+") = "+ b;
			 Calc.inicioController.appendLog(eucli);
			eucli = "Comienza alg de Euclides: ";
			return b;
		} else if (b == 0) {
			eucli += " mcd("+a+", "+b+") = "+ a;
			 Calc.inicioController.appendLog(eucli);
			eucli = "Comienza alg de Euclides: ";
			return a;
		}else if (a == 1 || b == 1) {
			eucli += " mcd("+a+", "+b+") = 1";
			 Calc.inicioController.appendLog(eucli);
			eucli = "Comienza alg de Euclides: ";
			return 1;
		}else if (a < b) {
			eucli += " mcd("+a+", "+b%a+") = "; 
			return mcd (a,b%a);
		}else {
			eucli += " mcd("+a%b+", "+b+") = ";
			return mcd (a%b,b);
		}
		
	}
	
//	/**
//	 * Resolusión utilizando elalgoritmo de euclides
//	 * @param a
//	 * @param b
//	 * @return
//	 */
//	public static Integer mcm(Integer a, Integer b) {
//		Map<Integer, Integer> factA = interpretarDescomponerEnFactoresPrimos(descomponerEnFactoresPrimos(a));
//		Map<Integer, Integer> factB = interpretarDescomponerEnFactoresPrimos(descomponerEnFactoresPrimos(b));
//		
//		
//		for (Entry<Integer, Integer> entry : factA.entrySet()) {
//			if (factB.containsKey(entry.getKey()) && factB.get(entry.getKey()).equals(entry.getValue())) {
//				//numeros comunes
//			}
//		}
//		
//	}
	
	public static List<Integer> coprimosMenores(Integer n) {
		List<Integer> invertibles = new ArrayList<Integer>();

		 Calc.inicioController.appendLog(" ****SE SOLICITÓ HALLAR LOS FACTORES COPRIMOS DE " + n + "**** ");
		invertibles.add(1);
		for (int i = 2; i < n; i++) {
			 Calc.inicioController.appendLog("Se hallará mcd("+ i + "," + n + "): ");
			if (mcd(i,n)==1) {
				invertibles.add(i);
			}
		}
		
		return invertibles;
	}
	
	public static List<Integer> generadosPor(Integer g, Integer n, List<Integer> coprimosMenores) {
		
		List<Integer> encontrados = new ArrayList<Integer>();

		 Calc.inicioController.appendLog(" ****SE SOLICITÓ HALLAR LOS GENERADORES DE " + String.valueOf(coprimosMenores) + "**** ");
		Calc.inicioController.appendLog("Se probara con potencia hasta " + Properties.MAX_POW);
		for (Integer item : coprimosMenores) {
			for ( int i = 1; i <= Properties.MAX_POW; i++) {
				if (Math.pow(item, i)%n == 1) {
					Calc.inicioController.appendLog((int) Math.round(Math.pow(item, i)) + " = 1 mod(" + n + ") es verdadero");
					encontrados.add(item);
				}
			}
		}
		
		return encontrados;
	}
	
	/**
	 * Se le pasa un valor entero superior a 1 y devuelve una lista de factores primos
	 * en los que ha descompuesto el número. 
	 * @param valor Número de descomponer
	 * @return Lista de factores primos.
	 */
	public static List<Integer> descomponerEnFactoresPrimos(int valor)
	{
		// Se empieza probando como posible factor primo el 2.
		int factor = 2;
		List<Integer> factores=new ArrayList<Integer>();
		 Calc.inicioController.appendLog(" ****SE SOLICITÓ HALLAR LOS FACTORES PRIMOS DE " + valor + "**** ");
		Calc.inicioController.appendLog("Mientras es divisible, se añade el factor a la lista de factores primos y se realiza la división.");
		while (factor <= valor)	{
			// Mientras es divisible, se añade el factor a la lista de factores primos
			// y se realiza la división.
			String val = ""; 
			while (valor % factor == 0 ){
				val += valor + " = 0 mod(" + factor + ") es verdadero";
				factores.add(factor);
				valor = valor/factor;
			}
			// Si no es divisible, se pasa al posible siguiente factor.
			Calc.inicioController.appendLog(val);
			factor++;
		}
		Collections.sort(factores);
		return factores;
	}
	
	public static Map<Integer, Integer> interpretarDescomponerEnFactoresPrimos(List<Integer> lista)
	{
		Map<Integer, Integer> ret = new HashMap<Integer, Integer>();//primer elemento el numero, segundo su potencia
		
		for (Integer item : lista) {
			if (ret.containsKey(item)) {
				ret.put(item,ret.get(item)+1);
			}else {
				ret.put(item,1);
			}
		}

		return ret;
	}

	public static void setInicioController(InicioController inicioController) {
		Calc.inicioController = inicioController;
	}
	
}
