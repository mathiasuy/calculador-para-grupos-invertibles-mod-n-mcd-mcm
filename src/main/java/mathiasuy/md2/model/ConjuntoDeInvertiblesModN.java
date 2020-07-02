package mathiasuy.md2.model;

import java.util.ArrayList;
import java.util.List;

public class ConjuntoDeInvertiblesModN {

	private Integer n;
	
	public ConjuntoDeInvertiblesModN(Integer n) {
		this.n = n;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}
	
	public List<Integer> getContenido() {
		List<Integer> lista = new ArrayList<Integer>();
		
		for (int i = 1; i < this.n; i++) {
//			if (mcd)
		}
		
		return lista;
	}
	
}
