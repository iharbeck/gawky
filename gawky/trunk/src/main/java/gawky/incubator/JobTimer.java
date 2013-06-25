package gawky.incubator;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class JobTimer {

	public static void main(String[] args) {
		System.out.println("start");
		int numberOfMillisecondsInTheFuture = 10000; // 10 sec
		Date timeToRun = new Date(
				System.currentTimeMillis()+numberOfMillisecondsInTheFuture);
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("done");
			}
		}, timeToRun);
		
		System.out.println("fine");
	}

    
}
