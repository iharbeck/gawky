package gawky.service.mt940;

import gawky.file.Locator;
import gawky.message.parser.PatternParser;
import gawky.message.part.Part;

import java.io.File;
import java.io.FileInputStream;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mt940Reader 
{
    // Charset and decoder for ISO-8859-15
    private static Charset charset = Charset.forName("UTF-8");
    private static CharsetDecoder decoder = charset.newDecoder();

    // Pattern used to parse lines
    private static Pattern linePattern = Pattern.compile("(?s)((.*?)\r?\n)((:[0-9]{0,2}.?:)|(-)|(\\s))");
    // DOTALL, nongreedy, to tag :99a: OR - OR blank
    
	static PatternParser parser = new PatternParser();
	
	private static void handler(String line, Part part) throws Exception
	{
		part.parse(parser, line);
	
		System.out.println("<b><pre style='margin-top:0px;margin-bottom:0px;'>" + line + "</pre></b>");
		System.out.println("<pre  style='margin-top:0px;margin-bottom:7px;'>");
		part.echo();
		System.out.print("</pre>");
		
	}
    private static void processLine(String line) throws Exception
    {
    	if(line.startsWith(":20:"))
    		handler(line, new Satz20());
    	if(line.startsWith(":21:"))
    		handler(line, new Satz21());
    	if(line.startsWith(":25:"))
    		handler(line, new Satz25());
    	if(line.startsWith(":28:"))
    		handler(line, new Satz28());
    	if(line.startsWith(":60"))
    		handler(line, new Satz60_62_64());
    	if(line.startsWith(":62"))
    		handler(line, new Satz60_62_64());
    	if(line.startsWith(":64"))
    		handler(line, new Satz60_62_64());
    	if(line.startsWith(":61:"))
    		handler(line, new Satz61());
    	if(line.startsWith(":86")) 
    		handler(line, new Satz86());
    	
    	if(line.equals("-"))
    		System.out.println("--------------------------------------------------------<br>");
    }
    
    private final static void matchLines(CharBuffer cb) throws Exception
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

    public static void main(String[] args) throws Exception
    {
		File f = new File(Locator.findBinROOT()+"../format/mt940google");
	    
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
}
