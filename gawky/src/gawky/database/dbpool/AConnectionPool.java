package gawky.database.dbpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.log4j.Logger;


class ConnectionReaper extends Thread 
{
	static final Logger log = Logger.getLogger(ConnectionReaper.class);

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
              sleep(delay);
              log.info("Count==: " + pool.getConnectionCount());
           } catch( InterruptedException e) { }
           pool.reapConnections();
        }
    }
}

public class AConnectionPool {

   private Vector connections;
   private String url, user, password;
   public String id;
   private long timeout;
   private ConnectionReaper reaper;
   final private int poolsize=100;

   public int getConnectionCount()
   {
     return connections.size();
   }

   public AConnectionPool(String id, String url, String user, String password, long timeout)
   {
      this.url = url;
      this.user = user;
      this.password = password;
      this.id = id;
      this.timeout = timeout;
      connections = new Vector(poolsize);
      reaper = new ConnectionReaper(this, timeout/5);
      reaper.start();
   }

   public synchronized void reapConnections()
   {
      // if(true)
      //  return;

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
       //System.out.println("Count--: " + connections.size());
   }

   public synchronized Connection getConnection() throws SQLException
   {
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
               //System.out.println("Count==: " + connections.size());
              return c;
           }
       }

       Connection conn = DriverManager.getConnection(url, user, password);

       c = new AConnection(conn, this);
       c.lease();
       connections.addElement(c);
       System.out.println("Count++: " + connections.size());
       return c;
   }

   public synchronized void returnConnection(AConnection conn)
   {
      conn.expireLease();
   }

}
