package gawky.service.oliva;

import gawky.global.Constant;
import gawky.message.part.Part;

import java.io.UnsupportedEncodingException;

/**
 * @author Ingo Harbeck
 * @version 1.0
 */


/**
 * Implements Oliva datastore
 */
public class Request
{
	RequestHead    head;
	RequestAddress addr;
	Part /*RequestPayment*/ paym;

	public String toRequestString() throws UnsupportedEncodingException
    {
        String parts = paym.toString() + addr.toString(); 
        
        int ilen = head.getBytes(Constant.ENCODE_US_ASCII).length + parts.getBytes().length;
        head.setMessage_length(ilen);
            
        return head.toString() + parts;
    }
	
    public RequestAddress getAddr() {
		return addr;
	}

	public void setAddr(RequestAddress addr) {
		this.addr = addr;
	}

	public RequestHead getHead() {
		return head;
	}

	public void setHead(RequestHead head) {
		this.head = head;
	}

	public Part getPaym() {
		return paym;
	}

	public void setPaym(Part paym) {
		this.paym = paym;
	}
}
