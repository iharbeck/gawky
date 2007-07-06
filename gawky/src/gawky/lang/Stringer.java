package gawky.lang;


public class Stringer {

	int count = 0;
	char[] value;
	
	public Stringer(int init) {
		value = new char[init];
	}

	public Stringer(String str) {
		this(str.length() + 16);
		append(str);
	}

	
	public Stringer append(String str) {
		if (str == null) 
			str = "";
	    
		int len = str.length();
		if (len == 0) 
			return this;
		
		int newCount = count + len;
		
		if (newCount > value.length) 
		{
			char[] tmp = new char[newCount];
	        System.arraycopy(value, 0, tmp, 0, newCount);
	        value = tmp;
		}
		
		str.getChars(0, len, value, count);
		count = newCount;
		return this;
	}
	
	public Stringer replace(int start, int end, String str) 
	{
	    str.getChars(0, end-start, value, start);
		return this;
	}

	public String toString() {
		return new String(value, 0, count); 
	}
	
	public static void main(String[] args) {
		
		Stringer stringer = new Stringer(100);
		
		stringer.append("ingo");
		stringer.append("ingo");
		stringer.append("ingo");
		
		System.out.println(stringer.toString());
		
		stringer.replace(4, 4, "jila");

		System.out.println(stringer.toString());
	}
}

