package gawky.processor;

import java.util.ArrayList;
import java.util.Iterator;

public class Report1060 implements ConsumerI 
{
	int count = 0;
    long totalamount = 0;
	ArrayList username = new ArrayList();

	public void open() {

	}

	public void processline(BaseObjectI obj) 
	{
		if(obj.getAmount() != null)
			totalamount += Long.parseLong(obj.getAmount());

		if(!username.contains(obj.getLastname()))
			username.add(obj.getLastname());

		count ++;
	}

	public void close() 
	{
		System.out.println("#:" + count + " amount: " + totalamount);

		System.out.println("UNIQUE CUSTOMERS:");
		
		for (Iterator iter = username.iterator(); iter.hasNext();) {
			System.out.println((String) iter.next());
		} 
	}
}
