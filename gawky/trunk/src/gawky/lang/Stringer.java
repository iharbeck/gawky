package gawky.lang;

import java.util.Arrays;


public class Stringer 
{
	int count = 0;  // use character
	char[] value;
	
	public Stringer(int init) {
		value = new char[init];
	}
	
	public char[] getValue() {
		return value;
	}

	public Stringer(String str) {
		this(str.length() + 16);
		append(str);
	}

	public int length() {
		return count;
	}
	
	public Stringer append(String str) {
		if (str == null) 
			str = "";
	    
		int len = str.length();
		if (len == 0) 
			return this;
		
		int newCount = count + len;
		
		if (newCount > value.length) {
			expandCapacity(newCount);
		}
		
		str.getChars(0, len, value, count);
		count = newCount;
		return this;
	}
	
	void expandCapacity(int minimumCapacity) 
	{
		int newCapacity = (value.length + 1) * 2;
		
	    value = Arrays.copyOf(value, newCapacity);
	}
	
	public Stringer replace(int start, int end, String str) 
	{
		if(end-start == str.length())
			str.getChars(0, end-start, value, start);
		else {
			if (end > count)
			    end = count;
			int len = str.length();
			int newCount = count + len - (end - start);
			if (newCount > value.length)
			    expandCapacity(newCount);

		        System.arraycopy(value, end, value, start + len, count - end);
		        str.getChars(0, len, value, start);
		        count = newCount;
		}
			
		
		return this;
	}

	public String toString() {
		return new String(value, 0, count); 
	}
	
	public static void main(String[] args) {
		
		Stringer stringer = new Stringer(1);
		
		stringer.append("ingo");
		stringer.append("ingo");
		stringer.append("ingo");
		
		System.out.println(stringer.toString());
		
		stringer.replace(4, 8, "jila");

		System.out.println(stringer.toString());
	}
}

