package gawky.global;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class OptionBasic 
{
	static OptionBasic me;
	
	private static Log log = LogFactory.getLog(OptionBasic.class);
	
	private static String HOST    = "host";
	private static String PORT    = "p";
	private static String TIMEOUT = "M";
	private static String VERBOSE = "v";
	private static String HELP    = "h";
	private static String LOGFILE = "l";
	private static String VALIDIP = "i";
	private static String SSLMODE = "s";
	
	private static String PROPERTY_FILE = "properties.xml";
	
	XMLConfiguration config = null;

	
	/**
	 * get Property from cmdline or configfile
	 * cmdline overwrites
	 */
	public static String getProperty(String alias, String def)
	{
		return me._getProperty(alias, def);
	}

	public static String getProperty(String alias)
	{
		return getProperty(alias, null);
	}
	
	public String _getProperty(String alias, String def)
	{
		if(config != null)
			return config.getString(alias, def);
		else
			return def;
	}
	
	
	public static String[] getProperties(String alias)
	{
		return me._getProperties(alias);
	}

	public String[] _getProperties(String alias)
	{
		if(config != null)
			return config.getStringArray(alias);
		
		return new String[] {};
	}
	
	public static boolean hasProperty(String alias)
	{
		return me._hasProperty(alias);
	}

	public boolean _hasProperty(String alias)
	{
		return "1".equals(config.getString(alias)) || config.getString(alias) != null;
	}

	
	/**
	 * Check for allowed Address
	 */
	public static boolean isValidIP(String addr) 
	{
		String val[] = OptionBasic.getProperties(VALIDIP);
		
		if(val == null)
			return true;

		for(int i=0 ; i< val.length; i++)
		{
			if(val[i].equals(addr))
				return false;
		}

		return true;
	}
	
	public static int getTimeout()
	{
		if(OptionBasic.hasProperty(TIMEOUT))
			return Integer.parseInt(getProperty(TIMEOUT)) * 100;
		else 
			return 10000;
	}
	
	public static int getPort()
	{
		if(OptionBasic.hasProperty(PORT))
			return Integer.parseInt(getProperty(PORT));
		else 
			return 3000;
	}
	
	public static String getHost()
	{
		return OptionBasic.getProperty(HOST);
	}
	
	public static Level getLoglevel()
	{
		if(!OptionBasic.hasProperty(VERBOSE))
			return Level.ERROR; 
		
		switch (OptionBasic.getProperty(VERBOSE).charAt(0)) 
		{
			case '0' : return Level.OFF;
			case '1' : return Level.FATAL;
			case '2' : return Level.ERROR;
			case '3' : return Level.WARN;
			case '4' : return Level.INFO;
			case '5' : return Level.DEBUG;
			default: return Level.ALL;
		}
	}
	
	boolean initdone = false;
	
	public static void init() throws Exception
	{
		init(PROPERTY_FILE, "Properties", new String[0]);
	}
	
	public static boolean isClassInPath(String classname, String info) {
		try {
			Class.forName(classname);
			return true;
		} catch (Exception e) {
			if(info != null)
				log.info(info);
			return false;
		}
	}
	
	public static void init(String propfile, String processname, String args[]) throws Exception
	{
		if(me == null)
			me = new OptionBasic();
		else if(me.initdone)
			return;
		
		me._init(propfile, processname, args);	
	}
	
	public void _init(String propfile, String processname, String args[]) throws Exception
	{
		me.propertyparser(propfile);
		
		if(isClassInPath("org.apache.commons.cli.CommandLineParser", "Commandline parser disabled Lib is missing!"))
			cmdparser(processname, args);
		
		// overwrite logfile default
		
		// Suche nach LOG4J Klassen
		if(isClassInPath("org.apache.log4j.Logger", "LOG4J wurde nicht gefunden Parameter werden ignoriert!"))
		{
			// logfile setzen
			String logfile = OptionBasic.getProperty(LOGFILE);
		
			if(logfile != null)
			{
				FileAppender fa = (FileAppender) Logger.getRootLogger().getAppender("file");
				if(fa != null)
				{
					fa.setFile(logfile);
					fa.activateOptions();
				}
			}
			
			// set loglevel default = ERROR
			Logger.getRootLogger().setLevel( OptionBasic.getLoglevel() );

		}
		
		initdone = true;
	}
	
	// Property File parsen
	void propertyparser(String propfile) throws Exception
	{
		try {
			// XML Configfile
			config = new XMLConfiguration(propfile);

			// Alternative

			// Property Configfile
			// PropertiesConfiguration config = new PropertiesConfiguration("TestServer.properties");
			
		} catch(Exception e) {
			log.error(e);
			throw e;
		}
	}
	
	// Commandozeile parsen
	void cmdparser(String processname, String args[]) throws Exception
	{
	}

	public static XMLConfiguration getConfig() {
		return me.config;
	}

	public static void setConfig(XMLConfiguration config) {
		me.config = config;
	}
}
