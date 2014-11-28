package gawky.processor;

import java.util.ArrayList;

public class Report1060 implements ConsumerI
{
	int count = 0;
	long totalamount = 0;
	ArrayList<String> username = new ArrayList<String>();

	@Override
	public void open()
	{

	}

	@Override
	public void processline(BaseObjectI obj)
	{
		if(obj.getAmount() != null)
		{
			totalamount += Long.parseLong(obj.getAmount());
		}

		if(!username.contains(obj.getLastname()))
		{
			username.add(obj.getLastname());
		}

		count++;
	}

	@Override
	public void close()
	{
		System.out.println("#:" + count + " amount: " + totalamount);

		System.out.println("UNIQUE CUSTOMERS:");

		for(Object element : username)
		{
			System.out.println((String)element);
		}
	}
}
