package gawky.incubator.message.dispatcher;

import java.util.HashMap;

import gawky.message.part.Part;
import gawky.service.mt940.Satz20;
import gawky.service.mt940.Satz21;
import gawky.service.mt940.Satz25;
import gawky.service.mt940.Satz28;
import gawky.service.mt940.Satz61;
import gawky.service.mt940.Satz86;

public class Dispatcher {

	public static final int TYPE_SINGLE = 1;
	public static final int TYPE_LIST   = 2;
	public static final int TYPE_END    = 3;
	
	HashMap map = new HashMap();
	
	public void Register(Class clazz, String tag, int type) {
		try {
			// Store clazz
			// Get Desc Object
			((Part)clazz.newInstance()).getDesc();
			
		} catch (Exception e) {
			
		}
	}
	
	public void handle(String line) {
		// matcher über REGEX "(:20:)|(:21:)|(:25:)"
		
		String tag = ":20:";
		
		map.get(tag);
	}
	
	public static void main(String[] args) 
	{
		Dispatcher dispatcher = new Dispatcher();
		
		dispatcher.Register(Satz20.class, ":20:", Dispatcher.TYPE_SINGLE);
		dispatcher.Register(Satz21.class, ":21:", Dispatcher.TYPE_SINGLE);
		dispatcher.Register(Satz25.class, ":25:", Dispatcher.TYPE_SINGLE);
		dispatcher.Register(Satz28.class, ":28:", Dispatcher.TYPE_SINGLE);
		
		dispatcher.Register(Satz61.class, ":61:", Dispatcher.TYPE_LIST);

		dispatcher.Register(Satz86.class, ":86:", Dispatcher.TYPE_END);
		
		String line = "";
		dispatcher.handle(line);
	}
}
