package mathiasuy.md2.main;

import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {
	private static Logger logger = LogManager.getLogger(Main.class);
	
	private static boolean modoDebug;	

	
	public static void main(String[] args) {
			MainApp appGrafica = new MainApp();
			appGrafica.run();
	}
	
}