package gawky.incubator;

import example.message.mt940.Satz61;
import gawky.file.Locator;
import gawky.message.parser.PatternParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MT940Reader 
{
    // Charset and decoder for ISO-8859-15
    private static Charset charset = Charset.forName("UTF-8");
    private static CharsetDecoder decoder = charset.newDecoder();

    // Pattern used to parse lines
    private static Pattern linePattern = Pattern.compile("(?s)((.*?)\r?\n)((:[0-9]{0,2}.?:)|(-)|(\\s))");
    // DOTALL, nongreedy, to tag :99a: OR - OR blank
    
	static PatternParser parser = new PatternParser();
	
    private static void processLine(String line) throws Exception
    {
    	if(!line.startsWith(":61:"))
    		return;
    	
    	Satz61 bean = new Satz61();
    	
    	bean.parse(parser, line);
    	
    	System.out.println(line);
    	
		bean.echo();
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

    private static void openFile(File f) throws Exception 
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
		File f = new File(Locator.findBinROOT()+"../format/mt940google");
	    
		try 
	    {
	    	openFile(f);
	    } 
	    catch (IOException x) {
	    	System.err.println(f + ": " + x);
	    }
    }
}

