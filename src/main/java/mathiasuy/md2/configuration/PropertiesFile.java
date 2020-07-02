package mathiasuy.md2.configuration;


import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PropertiesFile {
	private static Logger logger = LogManager.getLogger(PropertiesFile.class);
	
	private static final char INPUT_CONFIG_DELIMETER = ',';
	private static final String INPUT_CONFIG = "config.properties";  

	private static org.apache.commons.configuration2.Configuration config;
	
	static {
		try {
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
			    .configure(params.properties()
			        .setFileName(INPUT_CONFIG)
			        .setListDelimiterHandler(new DefaultListDelimiterHandler(INPUT_CONFIG_DELIMETER)));
			
			config = builder.getConfiguration();	} 
		catch (ConfigurationException cE) {		
			logger.fatal("No es posible cargar la configuracion del sistema", cE);
		}
	}
	
//	public static final String DATABASE_NAME = config.getString("db.name");

	
}
