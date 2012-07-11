package gawky.log;

import gawky.file.Locator;
import gawky.global.Option;

import java.io.File;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Formatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.log4j.xml.DOMConfigurator;

public class LOG
{
	static Logger log;
	static String LOG_PATTERN = "%d{dd.MM.yyyy HH:mm:ss,SSS} %-5p [%t %c] - %m%n";

	public static void init() throws Exception
	{
		// Application BINROOT for LOG4J
		System.setProperty("BINROOT", Locator.findBinROOT());

		String logconfigfile = Locator.findBinROOT() + "log4j.xml";

		if(new File(logconfigfile).exists())
		{
			System.out.println("LOG4J Configfile: " + logconfigfile);
			DOMConfigurator.configure(logconfigfile);
		}

		Logger root = LOG.getRootLogger();
		root.setLevel(Level.ERROR);

		//root.removeAllAppenders();
		//root.getLoggerRepository().resetConfiguration();

		ConsoleAppender consoleAppender = null;

		for(Enumeration<Appender> it = root.getAllAppenders(); it.hasMoreElements();)
		{
			Appender appender = it.nextElement();
			if(appender instanceof ConsoleAppender)
			{
				consoleAppender = (ConsoleAppender)appender;
				break;
			}
		}

		if(consoleAppender == null) // root.getAllAppenders().hasMoreElements() No appenders means log4j is not initialized!
		{
			// ConsoleLogger
			consoleAppender = new ConsoleAppender(new PatternLayout(LOG_PATTERN)); //PatternLayout.TTCC_CONVERSION_PATTERN;
			root.addAppender(consoleAppender);
		}

		consoleAppender.setLayout(new PatternLayout(LOG_PATTERN));
		consoleAppender.setThreshold(Level.ALL);

		// FileLogger
		String logfolder = Locator.findBinROOT() + "../logs";

		File folder = new File(logfolder);
		folder.mkdir();
		folder.setWritable(true, false);
		folder.setReadable(true, false);
		folder.setExecutable(true, false);
		
		String logfile = logfolder + "/default.log";
		
		// Permissions des Logfile setzen
		File lf = new File(logfile);
		
		if(!lf.exists())
			lf.createNewFile();
		
		lf.setWritable(true, false);
		lf.setReadable(true, false);
		lf.setExecutable(true, false);
		
		try {
			root.addAppender(new RollingFileAppender(new PatternLayout(LOG_PATTERN), logfile)); //PatternLayout.TTCC_CONVERSION_PATTERN;
		} catch (Exception e) {
		}
		
		log = Logger.getLogger(LOG.class);
		log.setLevel(Level.INFO);
		
		System.setOut(LOG.createLoggingProxy(System.out));
		System.setErr(LOG.createLoggingProxy(System.err));
		
		LOG.log("GAWKY INIT IN PROGRESS.\n****\n****\n****");
		System.out.println("OUT: Console Output Redirect");
		System.err.println("ERR: Console Output Redirect");
		

		// AUSGABE der LOG - SETTINGS
		
		Formatter formatter = new Formatter();

		formatter.format(" > %-6s | %s\n", "LEVEL", "NAME");

		for(Enumeration<Logger> it = root.getLoggerRepository().getCurrentLoggers(); it.hasMoreElements();)
		{
			Logger logger = it.nextElement();
			if(logger.getLevel() != null)
			{
				formatter.format(" > %-6s . %s\n", logger.getLevel(), logger.getName());
			}
		}
		
		formatter.format("\n > %s\n", "APPENDER");

		for(Enumeration<Appender> it = root.getAllAppenders(); it.hasMoreElements(); )
		{
			Appender appender = it.nextElement();
			
			formatter.format(" > %s\n", appender.getClass().getName());
			
		}

		LOG.log("Registered Logger:\n" + formatter.toString());
	}
	
	public static void log(String message)
	{
		log.info(message);
	}
	
	public static void log(String message, Throwable t)
	{
		log.info(message, t);
	}
	
	public static Logger getLogger(Class clazz)
	{
		return Logger.getLogger(clazz);
	}
	
	public static Logger getRootLogger()
	{
		return Logger.getRootLogger();
	}
	
	public static Log getLog(Class clazz)
	{
		return LogFactory.getLog(Option.class);
	}
	
	public static PrintStream createLoggingProxy(final PrintStream realPrintStream)
	{
		return new PrintStream(realPrintStream)
		{
			@Override
			public void println(final String string)
			{
				LOG.log(string);
				//realPrintStream.print(string);
			}
		};
	}
}
