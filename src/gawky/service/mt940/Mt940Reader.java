package gawky.service.mt940;

import gawky.message.parser.PatternParser;
import gawky.message.part.Part;

import java.io.File;
import java.io.FileInputStream;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mt940Reader 
{
    // Charset and decoder for ISO-8859-15
    private static Charset charset = Charset.forName("UTF-8");
    private static CharsetDecoder decoder = charset.newDecoder();

    // Pattern used to parse lines
    private static Pattern linePattern = Pattern.compile("(?s)((.*?)\r?\n)((:[0-9]{0,2}.?:))");
    // DOTALL, nongreedy, to tag :99a: OR - OR blank
    
	static PatternParser parser = new PatternParser();
	
	
	String currenttag = null;
	
	private void handler(String line, Part part) throws Exception
	{
		currenttag = line.substring(0, 4);
		
		// Parser
		part.parse(parser, line);
	
		// Listener informieren
		handler.process(line, part);
	}
	
    private void processLine(String line) throws Exception
    {
    	if(line.startsWith(":20:"))
    		handler(line, new Satz20());
    	else if(line.startsWith(":21:"))
    		handler(line, new Satz21());
    	else if(line.startsWith(":25:"))
    		handler(line, new Satz25());
    	else if(line.startsWith(":28:"))
    		handler(line, new Satz28());
    	else if(line.startsWith(":28C:"))
    		handler(line, new Satz28());
    	else if(line.startsWith(":60"))
    		handler(line, new Satz60());
    	else if(line.startsWith(":62"))
    		handler(line, new Satz62());
    	else if(line.startsWith(":64"))
    		handler(line, new Satz64());
    	else if(line.startsWith(":65"))
    		handler(line, new Satz65());
    	else if(line.startsWith(":61:"))
    		handler(line, new Satz61());
    	else if(line.startsWith(":86")) 
    		handler(line, new Satz86());
    	else if(line.equals("-"))
    		System.out.println("--------------------------------------------------------<br>");
    	else if(currenttag != null && !line.startsWith(":")) // if(line.startsWith(":86"))
        {
           line = currenttag + line;
           processLine(line);
        }
    }
    
    public final void matchLines(CharBuffer cb) throws Exception
    {
		Matcher lm = linePattern.matcher(cb);	// Line matcher

		int pos = 0;
		while (lm.find(pos)) 
		{
			String cs = lm.group(2); 	// The current line

			processLine(cs);

			// Reset to TAG
			pos = lm.start(3);

			//System.out.print("" + lm.group());
		    if (lm.end() == cb.limit())
			break;
		}
    }
    
    MTListener handler;
    
    public void registerHandler(MTListener handler) {
    	this.handler = handler;
    }

    public void read(File f) throws Exception
    {
		// Open the file and then get a channel from the stream
		FileInputStream fis = new FileInputStream(f);
		FileChannel fc = fis.getChannel();
	
		// Get the file's size and then map it into memory
		int sz = (int)fc.size();
		MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);
	
		// Decode the file into a char buffer
		CharBuffer cb = decoder.decode(bb);
	
		matchLines(cb);
	
		// Close the channel and the stream
		fc.close();    	
    }
    
    public static void main(String[] args) throws Exception
    {
    	Mt940Reader reader = new Mt940Reader();
    
    	reader.registerHandler(new Handler());
    	
    	//File f = new File(Locator.findBinROOT()+"../format/mt940google");
    	//File f = new File("c:/db.txt");
    	File f = new File("C:/mt940.txt");
    	
    	reader.read(f);
	    
    }
}

