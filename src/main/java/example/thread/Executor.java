package example.thread;

import gawky.thread.ThreadHandler;

import java.util.concurrent.ExecutorService;

public class Executor
{
	public static void main(String[] args) throws Exception
    {
		ExecutorService executorService = ThreadHandler.createExecutorService();
        
        for(int i=0; i < 100; i++)
        {
               executorService.submit(new Mythread());
        }
        
        ThreadHandler.waitForExecutorService(executorService);
    }
}

class Mythread implements Runnable  
{
	@Override
    public void run()
    {
    }
}
