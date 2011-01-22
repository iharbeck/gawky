package gawky.service.crm;


import gawky.global.Constant;

import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author  Ingo Harbeck
 */
public class Request {
    
    /** Creates a new instance of Request */
    public Request() {
    }
    
    RequestHead    head;
    RequestPayment paym;
    ArrayList      posn = new ArrayList();
    RequestUrl     urlr;
    RequestAddress addr;
    ArrayList      books = new ArrayList();
    
       
    public void setHead(RequestHead head)
    {   
        this.head = head;
    }
    public void setPaym(RequestPayment paym)
    {   
        this.paym = paym;
    }
    public void setUrlr(RequestUrl urlr)
    {   
        this.urlr = urlr;
    }
    public void setAddr(RequestAddress addr)
    {   
        this.addr = addr;
    }
    
    public void addPosition(RequestPosition pos)
    {
        posn.add(pos);
    }
    
    private String getPositions()
    {
        String ret = ""; 
        
        Iterator i = posn.iterator();
        
        while(i.hasNext())
        {
            RequestPosition pos = (RequestPosition)i.next();
            ret += pos.toString();
        }
        return ret;
    }
    
    public void addBook(RequestBook book)
    {
        books.add(book);
    }
    
    private String getBooks()
    {
        String ret = ""; 
        
        Iterator i = books.iterator();
        
        while(i.hasNext())
        {
            RequestBook book = (RequestBook)i.next();
            ret += book.toString();
        }
        return ret;
    }
    
    public String getEncoding()
    {
    	if("U".equals(head.getCharacter_encoding()))
       	    return Constant.ENCODE_UTF8;
    	else
    		return Constant.ENCODE_ISO;
    }
    
    public String toRequestString()
    {
        String ret = "";
        
        ret = ((paym == null) ? "" : paym.buildString())
            + getPositions()
            + ((urlr == null) ? "" : urlr.buildString()) 
            + ((addr == null) ? "" : addr.buildString() )
			+ getBooks(); 
        
        try 
        {
            String encoding = getEncoding();
            
            int ilen = head.buildBytes(encoding).length + ret.getBytes(encoding).length;
            head.setMessage_length(new Integer(ilen).toString());
        } catch (Exception e) {
            
        }
        return head.buildString() + ret;
    }
}

