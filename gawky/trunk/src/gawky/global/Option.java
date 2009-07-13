package gawky.global;

import gawky.database.DB;
import gawky.file.Locator;
import gawky.file.Tool;
import gawky.regex.Grouper;
import gawky.regex.Replacer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.regex.Pattern;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Option 
{
	private static Log log;
	
	static boolean initdone = false;
	
	private static String HELP    = "h";
	
	static XMLConfiguration config = null;
	private static String PROPERTY_FILE = "properties.xml";
	
	static CommandLine cmd     = null;
	static Options     options = null;

	static Replacer replace = null;
	
	static String stage = null;
	
	private static String processalias(String alias) 
	{
		return replace.replaceFirst(alias, stage); 
	}
	
	public static void main(String[] args) throws Exception
	{
		Option.init();
		System.out.println(new Option().processalias("db_${staging}"));
	}
	
	/**
	 * get Property from cmdline or configfile
	 * cmdline overwrites
	 */
	public static String getProperty(String alias, String def)
	{
		alias = processalias(alias);
	
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
		alias = processalias(alias);
		
		if(cmd != null && cmd.hasOption(alias))
			return cmd.getOptionValues(alias);
		else if(config != null)
			return config.getStringArray(alias);
		
		return new String[] {};
	}

	
	public static boolean hasProperty(String alias)
	{
		alias = processalias(alias);
		
		return (cmd != null && cmd.hasOption(alias)) ||
			   (config != null && (config.getString(alias) != null || "1".equals(config.getString(alias))));
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
	
		// Standard file encoding
		System.setProperty("file.encoding", Constant.ENCODE_UTF8);
		 
		// Application BINROOT for LOG4J
		System.setProperty("BINROOT", Locator.findBinROOT());

		log = LogFactory.getLog(Option.class);
		log.info("BINROOT " + Locator.findBinROOT());
	

		// Properties parsen
		try {
			// XML Configfile
			config = new XMLConfiguration();
			config.setEncoding("UTF8");
			
			
			if(Validation.notEmpty(propfile))
			{
				// Include von externen Configfiles: <import file="ALIAS" /> 
				Grouper includer = new Grouper("<import file=\"(.*?)\".?/>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
	
				String org = new String(Tool.readchararray(Locator.findBinROOT() + propfile));
	
				String[] includefile = includer.matchall(org);
				
				for(int i=0; i < includefile.length; i = i + 2)
				{
					try {
						// read include file
						String include = new String(Tool.readchararray(Locator.findBinROOT() + includefile[i+1]));
					
						org = org.replace(includefile[i], include.trim());
					} catch (Exception e) {
						log.error("Configfile missing", e);
					}
				}
			
				config.load(new ByteArrayInputStream(org.trim().getBytes()));
				
				stage = config.getString("staging", "test");
				
				replace = new Replacer("\\$\\{staging\\}");
				
				System.out.println("FOUND STAGE: ["  + stage + "]");
			}
			
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
				cmd = new PosixParser().parse(options, args);
				//cmd = new GnuParser().parse(options, args);
				//cmd = new BasicParser().parse(options, args);
			} catch (Exception e) {
				System.out.println(e);
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
		
		initdone = true;
		
		return; 
	}
	
	public static Options getOptions() {
		if(options == null)
			options = new Options();
		
		return options;
	}

	public static void addOption(String opt, boolean args, String description) {
		addOption(opt, opt, args, description, false);
	}

	public static void addOption(String opt, boolean args, String description, boolean required) {
		addOption(opt, opt, args, description, required);
	}
	
	public static void addOption(String opt, String optLong, boolean args, String description) {
		addOption(opt, optLong, args, description, false);
	}
	
	public static void addOption(String opt, String optLong, boolean args, String description, boolean required) {
		options = getOptions();

		if(!options.hasOption(opt)) {
			
			org.apache.commons.cli.Option option = new org.apache.commons.cli.Option(opt, optLong, args, description);
			option.setRequired(required);
			
			options.addOption(option);
		}
	}
	
	public static void initDefaultOptions() 
	{
		// check if options already registered
		// add default options (shorty, value, description)

		addOption(HELP,    true,  "show Arguments");
	}
	
	
	 public static void initLib() throws Exception 
	 {
		 // Libraries laden
		 Option.initLib(Locator.findBinROOT() + "../lib/");
	 }
	
	 public static void initLib(String path) throws Exception 
	 {
		File[] list = new File(path).listFiles();

		URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
		
		Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});
		method.setAccessible(true);
		
		System.out.println("CLASSPATH: " + path);
		
		if(list == null) {
			System.out.println("CLASSPATH: invalid no lib path");
			return;
		}
		
		for(File file : list) 
		{
			if(file.getName().toLowerCase().endsWith(".jar")) 
			{
				System.out.println(" + " + file.getName());
				method.invoke(sysloader, new Object[]{ file.toURI().toURL() });
			}
		}
	}
}
