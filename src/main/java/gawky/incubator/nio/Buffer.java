package gawky.incubator.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class Buffer 
{
	
	public static void main(String[] args) {
		
		ByteBuffer buf = ByteBuffer.allocate(100);

		// Create a character ByteBuffer
		CharBuffer cbuf = buf.asCharBuffer();

		// Write a string
		cbuf.put("a string");
		cbuf.put("INGO HABRBECˆˆ‰‰K");

		// Convert character ByteBuffer to a string.
		// Uses characters between current position and limit so flip it first
		cbuf.flip();
		String s = cbuf.toString();  // a string
		// Does not affect position

		// Get a substring
		int start = 2; // start is relative to cbuf's current position
		int end = 5;
		CharSequence sub = cbuf.subSequence(start, end); // str
		
		System.out.println(s);
	}

}
