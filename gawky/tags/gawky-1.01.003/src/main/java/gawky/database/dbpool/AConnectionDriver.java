package gawky.database.dbpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;


public class AConnectionDriver implements Driver 
{
    public final static String URL_PREFIX = "jdbc:gawky:";
    
    String  drivers_url;
    
    private static final int MAJOR_VERSION = 1;
    private static final int MINOR_VERSION = 3;
    private static final long timeout = 50000;
    private AConnectionPool pool;

    /**
     *
     * <b>Example:</b>
     * <pre>
     *      Properties prop = new Properties();
     *      prop.setProperty("driver",  "oracle.jdbc.driver.OracleDriver");
     *      prop.setProperty("url",     "jdbc:oracle:thin:@host:port:db");
     *      prop.setProperty("user",    "user");
     *      prop.setProperty("pass",    "pass");
     *      prop.setProperty("id",      "db1");
     *
     *      new pool.ConnectionDriver(prop);
     * </pre>
     * <b>Alternative:</b>
     * <pre>
     *      new pool.ConnectionDriver(db_driver, db_url, db_user, db_pass, "db1", 60000);
     * </pre>
     * <b>getting a connection:</b>
     * <pre>
     *      Connection conn = DriverManager.getConnection("jdbc:internal:db1");
     * </pre>
     *
     */

    public AConnectionDriver(Properties prop, Properties props)
           throws Exception
    {
    	drivers_url =  URL_PREFIX + prop.getProperty("id", "");

        init(prop.getProperty("driver", ""),
             prop.getProperty("url", ""),
             prop.getProperty("user", ""),
             prop.getProperty("pass", ""),
             Long.parseLong(prop.getProperty("timeout", Long.toString(timeout))), props);
    }

    public AConnectionDriver(String driver, String url, String user, String password, String id, long timeout, Properties props)
           throws Exception
    {
    	drivers_url = URL_PREFIX + id;
        init(driver, url, user, password, timeout, props);
    }

    public AConnectionDriver(String driver, String url, String user, String password, Properties props)
           throws Exception
    {
        init(driver, url, user, password, timeout, props);
    }

    public void init(String driver, String url, String user, String password, long timeout, Properties props)
           throws Exception
    {
        DriverManager.registerDriver(this);
        try {
        	Class.forName(driver);
        } catch (Exception e) {
        	throw new Exception("MISSING(" + driver + ")");
        }
        pool = new AConnectionPool(drivers_url, url, user, password, timeout, props);
        
        AShutdownHook shutdownHook = new AShutdownHook(pool);
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public Connection connect(String url, Properties props) throws SQLException
    {
        if(!url.equals(drivers_url)) {
             return null;
        }
        return pool.getConnection();
    }

    public boolean acceptsURL(String url)
    {
        return url.equals(drivers_url);
    }

    public int getMajorVersion()
    {
        return MAJOR_VERSION;
    }

    public int getMinorVersion()
    {
        return MINOR_VERSION;
    }

    public DriverPropertyInfo[] getPropertyInfo(String str, Properties props)
    {
        return new DriverPropertyInfo[0];
    }

    public boolean jdbcCompliant()
    {
        return false;
    }
    
    public void emptyPool()
    {
    	pool.closeConnections();
    }
    
    public void lockPool()
    {
    	pool.setLock(true);
    }
     
    public void unlockPool()
    {
    	pool.setLock(false);
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException
    {
	    // TODO Auto-generated method stub
	    return null;
    }
}
