package mathiasuy.md2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Calc {

	/**
	 * Calcula el mcd de todos los elementos de la lista
	 * @param items
	 * @return
	 */
	public static Integer mcd(List<Integer> items) {

		Collections.sort(items);
		
		int divisor = items.get(0);
		for (Integer item : items) {
			divisor = mcd (divisor, item);
		}
		
		return divisor;
	}
	
	/**
	 * Resolusión utilizando elalgoritmo de euclides
	 * @param a
	 * @param b
	 * @return
	 */
	public static Integer mcd(Integer a, Integer b) {
		 if (a == 0) {
				return b;
		} else if (b == 0) {
			return a;
		}else if (a == 1 || b == 1) {
			return 1;
		}else if (a < b) {
			return mcd (a,b%a);
		}else {
			return mcd (a%b,b);
		}
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
		while (factor <= valor)	{
			// Mientras es divisible, se añade el factor a la lista de factores primos
			// y se realiza la división.
			while (valor % factor == 0 ){
				factores.add(factor);
				valor = valor/factor;
			}
			// Si no es divisible, se pasa al posible siguiente factor.
			factor++;
		}
		return factores;
	}	
	
	
}
