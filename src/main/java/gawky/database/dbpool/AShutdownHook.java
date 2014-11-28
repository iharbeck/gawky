package gawky.database.dbpool;

public class AShutdownHook extends Thread
{

	AConnectionPool pool;

	public AShutdownHook(AConnectionPool pool)
	{
		this.pool = pool;
	}

	@Override
	public void run()
	{
		System.out.println("Shutting down pool start");
		pool.closeConnections();
		System.out.println("Shutting down pool done");
	}

}
