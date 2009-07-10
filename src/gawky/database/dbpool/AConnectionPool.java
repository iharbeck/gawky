package gawky.database.dbpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


class ConnectionReaper extends Thread 
{
	private static Log log = LogFactory.getLog(ConnectionReaper.class);

    private AConnectionPool pool;
    private long delay;

    ConnectionReaper(AConnectionPool pool, long delay)
    {
        this.pool = pool;
        this.delay = delay;
    }

    public void run()
    {
        while(true)
        {
           try {
              wait(delay);
              log.info("Count==: " + pool.getConnectionCount());
              pool.reapConnections();
           } catch( Exception e) { 
        	   return;
           }
        }
    }
}

public class AConnectionPool 
{
   private static Log log = LogFactory.getLog(AConnectionPool.class);
	
   private Vector connections;
   private String url;
   public String id;
   private long timeout;
   private ConnectionReaper reaper;
   final private int poolsize=100;
   
   private boolean lock = false;
   
   Properties info = new java.util.Properties();
   
   public int getConnectionCount()
   {
     return connections.size();
   }

   public AConnectionPool(String id, String url, String user, String password, long timeout, Properties props)
   {
	  info.put ("user", user);
	  info.put ("password", password);
	  info.put ("defaultRowPrefetch", "15");

	  if(props != null)
		  info.putAll(props);
	  
	  this.url = url;
      this.id = id;
      this.timeout = timeout;
      connections = new Vector(poolsize);
      reaper = new ConnectionReaper(this, timeout/5);
      reaper.start();
   }

   public synchronized void reapConnections()
   {
      long stale = System.currentTimeMillis() - timeout;
      Enumeration connlist = connections.elements();

      while((connlist != null) && (connlist.hasMoreElements()))
      {
          AConnection conn = (AConnection)connlist.nextElement();
          if((!conn.inUse()) || (stale > conn.getLastUse()) || (!conn.validate())) {
              removeConnection(conn);
          }
      }
   }

   public synchronized void closeConnections()
   {
      Enumeration connlist = connections.elements();

      while((connlist != null) && (connlist.hasMoreElements()))
      {
          AConnection conn = (AConnection)connlist.nextElement();
          removeConnection(conn);
      }
   }

   private synchronized void removeConnection(AConnection conn)
   {
       try {
       	conn.finalclose();
       } catch (Exception e) {};

       connections.removeElement(conn);
   }

   public synchronized Connection getConnection() throws SQLException
   {
	  if(lock)
		  throw new SQLException("Connectionpool is locked");
	  
      AConnection c = null;

      do {
         if(c != null)
           removeConnection(c);

         c = (AConnection)getSubConnection();
      }
      while(!c.validate());

     return c;
   }

   public synchronized Connection getSubConnection() throws SQLException
   {
       AConnection c;

       for(int i = 0; i < connections.size(); i++)
       {
           c = (AConnection)connections.elementAt(i);
           if (c.lease()) {
              return c;
           }
       }

       Connection conn = DriverManager.getConnection(url, info);

       c = new AConnection(conn, this);
       c.lease();
       connections.addElement(c);
       log.debug("Count++: " + connections.size());
       return c;
   }

    public synchronized void returnConnection(AConnection conn)
    {
      conn.expireLease();
    }

	public boolean isLock()
	{
		return lock;
	}
	
	public void setLock(boolean lock)
	{
		this.lock = lock;
	}

}
