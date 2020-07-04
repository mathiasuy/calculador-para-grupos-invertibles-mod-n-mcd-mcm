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
	public static Double mcd(List<Double> items) {

		Collections.sort(items);

		Calc.inicioController.appendLog("****MCD SOLICITADO: mcd("+String.valueOf(items) +")****");
		Double divisor = items.get(0);
		for (Double item : items) {
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
	public static Double mcd(Double a, Double b) {
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
			return 1.0;
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
//	public static Double mcm(Double a, Double b) {
//		Map<Double, Double> factA = DoubleerpretarDescomponerEnFactoresPrimos(descomponerEnFactoresPrimos(a));
//		Map<Double, Double> factB = DoubleerpretarDescomponerEnFactoresPrimos(descomponerEnFactoresPrimos(b));
//		
//		
//		for (Entry<Double, Double> entry : factA.entrySet()) {
//			if (factB.containsKey(entry.getKey()) && factB.get(entry.getKey()).equals(entry.getValue())) {
//				//numeros comunes
//			}
//		}
//		
//	}
	
	public static List<Double> coprimosMenores(Double n) {
		List<Double> invertibles = new ArrayList<Double>();

		 Calc.inicioController.appendLog(" ****SE SOLICITÓ HALLAR LOS FACTORES COPRIMOS DE " + n + "**** ");
		for (Double i = 1.0; i < n; i++) {
			 Calc.inicioController.appendLog("Se hallará mcd("+ i + "," + n + "): ");
			if (mcd(i,n)==1) {
				invertibles.add(i);
			}
		}
		
		return invertibles;
	}
	
	public static List<Double> generadosPor(Double g, Double n, List<Double> coprimosMenores) {
		
		List<Double> encontrados = new ArrayList<Double>();

		 Calc.inicioController.appendLog(" ****SE SOLICITÓ HALLAR LOS GENERADORES DE " + String.valueOf(coprimosMenores) + "**** ");
		Calc.inicioController.appendLog("Se probara con potencia hasta " + Properties.MAX_POW);
		for (Double item : coprimosMenores) {
			for ( Double i = 1.0; i <= Properties.MAX_POW; i++) {
				if (Math.pow(item, i)%n == 1) {
					Calc.inicioController.appendLog(Math.round(Math.pow(item, i)) + " = 1 mod(" + n + ") es verdadero");
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
	public static List<Double> descomponerEnFactoresPrimos(Double valor)
	{
		// Se empieza probando como posible factor primo el 2.
		Double factor = 2.0;
		List<Double> factores=new ArrayList<Double>();
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
	
	public static Map<Double, Double> interpretarDescomponerEnFactoresPrimos(List<Double> lista)
	{
		Map<Double, Double> ret = new HashMap<Double, Double>();//primer elemento el numero, segundo su potencia
		
		for (Double item : lista) {
			if (ret.containsKey(item)) {
				ret.put(item,ret.get(item)+1);
			}else {
				ret.put(item,1.0);
			}
		}

		return ret;
	}

	public static void setInicioController(InicioController inicioController) {
		Calc.inicioController = inicioController;
	}
	
}
