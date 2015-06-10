package gawky.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadHandler
{
	public static ExecutorService createExecutorService()
	{
		return createExecutorService(5);
	}
	
	public static ExecutorService createExecutorService(int num_threads)
	{
		ExecutorService executorService =
		        new ThreadPoolExecutor(num_threads,
		                num_threads,
		                20,
		                TimeUnit.SECONDS,
		                new LinkedBlockingQueue<Runnable>());

		return executorService;
	}

	public static void waitForExecutorService(ExecutorService executorService) throws Exception
	{
		executorService.shutdown();

		// Wait for everything to finish.
		while(!executorService.awaitTermination(10, TimeUnit.SECONDS))
		{
			System.out.println("Awaiting completion of threads.");
		}
	}
}
