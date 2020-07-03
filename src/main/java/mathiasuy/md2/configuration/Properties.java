package mathiasuy.md2.configuration;


import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Properties {
	private static Logger logger = LogManager.getLogger(Properties.class);

	private static Configuration config;
	
	static {
		try {
			Parameters params = new Parameters();
			FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
			    .configure(params.properties()
			        .setFileName("config.properties")
			        .setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
			
			config = builder.getConfiguration();	} 
		catch (ConfigurationException cE) {		
			logger.fatal("No se pudieron obtener las propiedades del programa", cE);
		}
	}
	
	public static final Integer MAX_POW = config.getInt("max.pow");

	
}
