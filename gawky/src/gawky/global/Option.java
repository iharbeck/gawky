package gawky.global;

import gawky.database.DB;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Option 
{
	private static Log log = LogFactory.getLog(Option.class);
	
	static boolean initdone = false;
	
	private static String HOST    = "host";
	private static String PORT    = "p";
	private static String TIMEOUT = "M";
	private static String VERBOSE = "v";
	private static String HELP    = "h";
	private static String LOGFILE = "l";
	private static String VALIDIP = "i";
	private static String SSLMODE = "s";
	
	static XMLConfiguration config = null;
	private static String PROPERTY_FILE = "properties.xml";
	
	static CommandLine cmd     = null;
	static Options     options = null;

	/**
	 * get Property from cmdline or configfile
	 * cmdline overwrites
	 */
	public static String getProperty(String alias, String def)
	{
		if(cmd != null && cmd.hasOption(alias))
			return cmd.getOptionValue(alias);
		else if(config != null)
			return config.getString(alias, def);
		else
			return def;
	}

	public static String getProperty(String alias)
	{
		return getProperty(alias, null);
	}
	
	public static String[] getProperties(String alias)
	{
		if(cmd != null && cmd.hasOption(alias))
			return cmd.getOptionValues(alias);
		else if(config != null)
			return config.getStringArray(alias);
		
		return new String[] {};
	}

	
	public static boolean hasProperty(String alias)
	{
		return (cmd != null && cmd.hasOption(alias)) ||
			   (config != null && (config.getString(alias) != null || "1".equals(config.getString(alias))));
	}
	
	private static Level getLoglevel()
	{
		if(!Option.hasProperty(VERBOSE))
			return null; 
		
		switch (Option.getProperty(VERBOSE).charAt(0)) 
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
	
	/**
	 * Initialize Global properties with default properties file name "properties.xml"
	 * 
	 * @throws Exception
	 */
	public static void init() throws Exception
	{
		init(PROPERTY_FILE, "Properties", new String[0]);
	}
	
	/**
	 * Initialize Global properties with custom properties file name
	 * 
	 * @param propfile absolut or relative path to the configuration file
	 * @throws Exception
	 */
	public static void init(String propfile) throws Exception
	{
		init(propfile, "Properties", new String[0]);
	}
	
	/**
	 * Check if classname is available in current classpath
	 * 
	 * @param classname		name of class to find
	 * @param info			comment send to option logger if class not available
	 * @return
	 */
	public final static boolean isClassInPath(String classname, String info) {
		try {
			Class.forName(classname);
			return true;
		} catch (Exception e) {
			if(info != null)
				log.info(info);
			return false;
		}
	}
	
	/**
	 * Initialze Global properties with custom properties file name
	 * 
	 * @param propfile 		absolut or relative path to the configuration file
	 * @param processname	Optional name for the process
	 * @param args			Command parameter for commandline parsing
	 * @throws Exception
	 */
	public static void init(String propfile, String processname, String args[]) throws Exception
	{
		if(initdone)
			return;
	

		// Properties parsen
		try {
			// XML Configfile
			config = new XMLConfiguration(propfile);
			// Property Configfile
			// config = new PropertiesConfiguration("TestServer.properties");
		} catch(Exception e) {
			log.error(e);
			throw e;
		}
		
		boolean hascmd = isClassInPath("org.apache.commons.cli.CommandLineParser", 
				                       "Commandline parser disabled Lib is missing!");
		if(hascmd)
		{
			initOption();
			
			try {			
				cmd = new PosixParser().parse(options, args);
			} catch (Exception e) {
				log.error(e);
				throw e;
			}
		
			if(cmd.hasOption(HELP))
			{
				// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp( processname, options );
				
				System.exit(0);
			}
		}
		
		// DB initialisieren
		
		if(isClassInPath("gawky.database.DB", 
						 "DB wurde nicht gefunden Parameter werden ignoriert!"))
		{
			DB.init();
		}
		
		
		// overwrite logfile default 
		// Rootlogger / loglevel
		// Suche nach LOG4J Klassen
		if(isClassInPath("org.apache.log4j.Logger", 
						 "LOG4J wurde nicht gefunden Parameter werden ignoriert!"))
		{
			// logfile setzen
			String logfile = Option.getProperty(LOGFILE);
		
			if(logfile != null)
			{
				FileAppender fa = (FileAppender) Logger.getRootLogger().getAppender("file");
				if(fa != null)
				{
					fa.setFile(logfile);
					fa.activateOptions();
				}
			}
			
			// set loglevel
			Level level = Option.getLoglevel();
			if(level != null)
			{
				Logger.getRootLogger().setLevel( level );
			}
		}
		
		initdone = true;
		
		return; 
	}
	
	public static Options getOptions() {
		if(options == null)
			options = new Options();
		
		return options;
	}
	
	private static void initOption() 
	{
		// check if options already registered
		// add default options (shorty, value, description)

		if(options == null)
			options = new Options();
		
		if(!options.hasOption(PORT)) 
			options.addOption(PORT, PORT, true,  "set listener port number (5001-49151)");
		if(!options.hasOption("a")) 
			options.addOption("a", "a", true,  "set interface address for listener (NONE = INADDR_ANY)");
		if(!options.hasOption("b")) 
			options.addOption("b", "b", true,  "set backlog values for listen function");
		if(!options.hasOption("r")) 
			options.addOption("r", "r", false, "log requests into separate files");
		if(!options.hasOption("k")) 
			options.addOption("k", "k", false, "kill and replace old server process (for release upgrades with minimized downtime)");
		if(!options.hasOption(VALIDIP)) 
			options.addOption(VALIDIP, VALIDIP, true,  "set a list of request IP addresses (HEX-form) to ignore (for load balancer checks)");
		if(!options.hasOption("n")) 
			options.addOption("n", "n", false, "do NOT perform active close of socket connection on server side (avoid TCP_WAIT states)");
		if(!options.hasOption("N")) 
			options.addOption("N", "N", false, "do NOT fork (for debugging purposes)");
		if(!options.hasOption("W")) 
			options.addOption("W", "W", false, "wait for SIGTERM or debugger after fork (for debugging purposes)");
		if(!options.hasOption(TIMEOUT)) 
			options.addOption(TIMEOUT, TIMEOUT, true,  "Maximum number of seconds a childs waits for data (timeout)");
		if(!options.hasOption("m")) 
			options.addOption("m", "m", true,  "Maximum number of seconds for application to process before terminating the process");
		if(!options.hasOption("R")) 
			options.addOption("R", "R", false, "Try to reconnect in batch mode, if DB connection lost (production stability)");
		if(!options.hasOption("d")) 
			options.addOption("d", "d", true,  "set database connect string");
		if(!options.hasOption("B")) 
			options.addOption("B", "B", true,  "set backup server ID (empty for primary) for processes supporting replication");
		if(!options.hasOption("I")) 
			options.addOption("I", "I", false, "ignore (date) plausibility checks for requests (for testing)");
		if(!options.hasOption(LOGFILE)) 
			options.addOption(LOGFILE, LOGFILE, true,  "set log output target file (NONE = stderr)");
		if(!options.hasOption("v")) 
			options.addOption("v", "v", true,  "set verbose level (0-9)");
		if(!options.hasOption("T")) 
			options.addOption("T", "T", false, "turn on SQL-Trace for all transactions (whatever database supports)");
		if(!options.hasOption("U")) 
			options.addOption("U", "U", false, "set default encoding for parsed requests to UTF8");
		if(!options.hasOption("1")) 
			options.addOption("1", "1", false, "set default encoding for parsed requests to iso8859-1");
		if(!options.hasOption(HELP)) 
			options.addOption(HELP, HELP, true,  "show Arguments");
		if(!options.hasOption(SSLMODE)) 
			options.addOption(SSLMODE, SSLMODE, true,  "run in SSL mode");
	}
	
	
	
	/**
	 * Check for allowed Address
	 * @param addr
	 * @return
	 */
	public static boolean isValidIP(String addr) 
	{
		String val[] = getProperties(VALIDIP);
		
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
		if(hasProperty(TIMEOUT))
			return Integer.parseInt(getProperty(TIMEOUT)) * 100;
		else 
			return 10000;
	}
	
	public static int getPort()
	{
		if(hasProperty(PORT))
			return Integer.parseInt(getProperty(PORT));
		else 
			return 3000;
	}
	
	public static String getHost()
	{
		return getProperty(HOST);
	}
}
