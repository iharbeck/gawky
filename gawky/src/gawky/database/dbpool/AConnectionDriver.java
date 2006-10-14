package gawky.database.dbpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;


public class AConnectionDriver implements Driver 
{
    private String URL_PREFIX = "jdbc:bms:";
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
     *      prop.setProperty("url",     "jdbc:oracle:thin:@debmu335:1480:dbotirs1");
     *      prop.setProperty("user",    "appirs");
     *      prop.setProperty("pass",    "tirs00");
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
     *      Connection conn = DriverManager.getConnection("jdbc:bms:db1");
     * </pre>
     *
     */

    public AConnectionDriver(Properties prop)
           throws Exception
    {
        URL_PREFIX += prop.getProperty("id", "");

        init(prop.getProperty("driver", ""),
             prop.getProperty("url", ""),
             prop.getProperty("user", ""),
             prop.getProperty("pass", ""),
             Long.parseLong(prop.getProperty("timeout", Long.toString(timeout))));
    }

    public AConnectionDriver(String driver, String url, String user, String password, String id, long timeout)
           throws Exception
    {
        URL_PREFIX += id;
        init(driver, url, user, password, timeout);
    }

    public AConnectionDriver(String driver, String url, String user, String password)
           throws Exception
    {
        init(driver, url, user, password, timeout);
    }

    public void init(String driver, String url, String user, String password, long timeout)
           throws Exception
    {
        DriverManager.registerDriver(this);
        try {
        	Class.forName(driver);
        } catch (Exception e) {
        	throw new Exception("MISSING(" + driver + ")");
        }
        pool = new AConnectionPool(URL_PREFIX, url, user, password, timeout);
    }

    public Connection connect(String url, Properties props) throws SQLException
    {
        //if(!url.startsWith(URL_PREFIX)) {
        if(!url.equals(URL_PREFIX)) {
             return null;
        }
        return pool.getConnection();
    }

    public boolean acceptsURL(String url)
    {
        //return url.startsWith(URL_PREFIX);
        return url.equals(URL_PREFIX);
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
}
