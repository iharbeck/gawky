package gawky.global;

import gawky.database.DB;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
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
	
	public final static boolean isClassInClassloader(ClassLoader cl, String classname, String info) {
		try {
			cl.loadClass(classname);
			return true;
		} catch (Exception e) {
			if(info != null)
				log.info(info);
			return false;
		}
	}
	
	static void printHelp(String processname) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( processname, options );

		System.exit(0);
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
		
		System.setProperty("file.encoding", Constant.ENCODE_UTF8);
	

		// Properties parsen
		try {
			// XML Configfile
			config = new XMLConfiguration();
			config.setEncoding("UTF8");
			if(propfile != null && propfile.length() > 0)
				config.load(propfile);
			// Property Configfile
			// config = new PropertiesConfiguration("TestServer.properties");
		} catch(Exception e) {
			log.error(e);
			throw e;
		}
		
		boolean hascmd = isClassInPath("org.apache.commons.cli.CommandLineParser", 
				                       "Commandline parser disabled Lib is missing!");
		if(hascmd && options != null)
		{
			// keine default options mehr
			// initDefaultOptions(); 
			
			try {			
				//cmd = new PosixParser().parse(options, args);
				cmd = new GnuParser().parse(options, args);
			} catch (Exception e) {
				printHelp(processname);
			}
		
			if(cmd.hasOption(HELP))
				printHelp(processname);
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

	public static void addOption(String opt, boolean args, String description) {
		addOption(opt, args, description, false);
	}
	
	public static void addOption(String opt, boolean args, String description, boolean required) {
		options = getOptions();

		if(!options.hasOption(opt)) {
			
			org.apache.commons.cli.Option option = new org.apache.commons.cli.Option(opt, args, description);
			option.setRequired(required);
			
			options.addOption(option);
		}
	}
	
	public static void initDefaultOptions() 
	{
		// check if options already registered
		// add default options (shorty, value, description)

		addOption(PORT,    true,  "set listener port number (5001-49151)");
		addOption("a",     true,  "set interface address for listener (NONE = INADDR_ANY)");
		addOption("b",     true,  "set backlog values for listen function");
		addOption("r",     false, "log requests into separate files");
		addOption("k",     false, "kill and replace old server process (for release upgrades with minimized downtime)");
		addOption(VALIDIP, true,  "set a list of request IP addresses (HEX-form) to ignore (for load balancer checks)");
		addOption("n",     false, "do NOT perform active close of socket connection on server side (avoid TCP_WAIT states)");
		addOption("N",     false, "do NOT fork (for debugging purposes)");
		addOption("W",     false, "wait for SIGTERM or debugger after fork (for debugging purposes)");
		addOption(TIMEOUT, true,  "Maximum number of seconds a childs waits for data (timeout)");
		addOption("m",     true,  "Maximum number of seconds for application to process before terminating the process");
		addOption("R",     false, "Try to reconnect in batch mode, if DB connection lost (production stability)");
		addOption("d",     true,  "set database connect string");
		addOption("B",     true,  "set backup server ID (empty for primary) for processes supporting replication");
		addOption("I",     false, "ignore (date) plausibility checks for requests (for testing)");
		addOption(LOGFILE, true,  "set log output target file (NONE = stderr)");
		addOption("v",     true,  "set verbose level (0-9)");
		addOption("T",     false, "turn on SQL-Trace for all transactions (whatever database supports)");
		addOption("U",     false, "set default encoding for parsed requests to UTF8");
		addOption("1",     false, "set default encoding for parsed requests to iso8859-1");
		addOption(HELP,    true,  "show Arguments");
		addOption(SSLMODE, true,  "run in SSL mode");
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
