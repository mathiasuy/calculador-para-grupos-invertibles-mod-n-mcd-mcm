package mathiasuy.md2.model;

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
	
}
