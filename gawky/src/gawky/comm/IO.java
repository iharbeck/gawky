package gawky.comm;


import gawky.global.Constant;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class IO 
{
	private static Log log = LogFactory.getLog(IO.class);

	public static String readLine(Socket s) throws SocketTimeoutException, Exception
	{
		return readLine(s, Constant.ENCODE_ISO); 
    }
	
	public static boolean isUTF8(BufferedInputStream buff, int count, boolean allAscii)	
	{
		try	{
			int byte1=buff.read();

			if ((count==0)&&(allAscii==false)) //stop at the count'th iteration
			{
				return true;
			}
			else if ((count==0)&&(allAscii==true)) // if all the cars are ASCII, then not UTF-8
			{
				return false;
			}
			else 
			{
				if((byte1>=192)&&(byte1<=253))	{
					allAscii=false;
					int byte2=buff.read();
					if ((byte2>=128)&&(byte2<=191))	{
						return isUTF8(buff,count-1,allAscii);
					}
					else {return false;}
				}
				else {return isUTF8(buff,count-1,allAscii);}
			}
		}
		catch (Exception e) {
			log.info("ISUTF8", e);
		}
		return false;
	}

	
    public static String readLine(Socket s, String encode) throws SocketTimeoutException, Exception
    {
	   BufferedReader is = null;
    	
       is = new BufferedReader(new InputStreamReader(s.getInputStream(), encode));
      
       String str = is.readLine();
       
       log.debug("READ: " + str);
       
       if(str == null)  // Interupted Batchmode??
    	   throw new IOException();
       
       
       if(encode.equals(Constant.ENCODE_ISO)
          && isUTF8(new BufferedInputStream(new ByteArrayInputStream(str.getBytes())), 1000, true)) 
	   {
     	  str = new String(str.getBytes(), Constant.ENCODE_UTF8);
 		  log.debug("CONVERTING! GOT UTF8 DATA: " + str);
	   }
      
       return str;
    }

    public static String readLineAll(Socket s) throws SocketTimeoutException, Exception
    {
    	return readLineAll(s, Constant.ENCODE_ISO); 
    }
    
    public static String readLineAll(Socket s, String encode) throws SocketTimeoutException, Exception
    {
	   BufferedReader is = null;
	
       is = new BufferedReader(new InputStreamReader(s.getInputStream(), encode));
      
       String inputLine;
      
       StringBuffer buf = new StringBuffer();
        
       if ((inputLine = is.readLine()) != null)
       {
         buf.append(inputLine);
     	 buf.append("\n");
       }

       inputLine = buf.toString();
      
       log.debug(inputLine);

       return inputLine;
    }

    
    public static void writeLine(Socket s, String val) 
    {
       write(s, val + "\n", Constant.ENCODE_ISO); 
    }
    
    public static void writeLine(Socket s, String val, String encode) 
    {
        write(s, val + "\n", encode); 
    }
    
    
    public static void write(Socket s, String val) 
    {
       write(s, val, Constant.ENCODE_ISO); 
    }
    
    public static void write(Socket s, String val, String encode) 
    {
	    PrintWriter ps = null;
    	
	    try 
		{
           ps = new PrintWriter((new OutputStreamWriter(s.getOutputStream(), encode)));
           
           // sendResponse
           log.debug("sending: " + val);

           ps.print(val);
           ps.flush(); 
	    } catch(SocketTimeoutException e) {
			log.error(e);
		} catch(Exception e) {
			log.error(e);
		}
    }
}
