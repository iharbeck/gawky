package gawky.message.part;

import gawky.database.generator.IDGenerator;

import java.lang.reflect.Method;

/**
 * Generic Part
 * 
 * @author Ingo Harbeck
 *
 */
public class Desc 
{
	// CONSTANTS
	public static final String CRLF     = new String(new char[] {'\r', '\n'}); // "x0D x0A"
	public static final String LF       = new String(new char[] {'\n'});
	public static final String END01    = new String(new char[] {'\001'});
	public static final String END02    = new String(new char[] {'\002'});
	public static final String ENDCOMMA = new String(new char[] {','});
	public static final String ENDCOLON = new String(new char[] {';'});
	public static final String ENDHASH  = new String(new char[] {'#'});
	public static final String ENDALL   = null;
	
	// FORMAT
	public static final char FMT_C          = 'C';    
	public static final char FMT_CONSTANT   = 'C';    
	
	public static final char FMT_A          = 'A';    
	public static final char FMT_ASCII      = 'A';    
	
	public static final char FMT_L          = 'L';    
	public static final char FMT_LETTER     = 'L';    
	
	public static final char FMT_X          = 'X';    
	public static final char FMT_UNICODE    = 'X';    
	
	public static final char FMT_9          = '9';    
	public static final char FMT_DIGIT      = '9';    
	
	public static final char FMT_BLANK      = ' ';    
	
	public static final char FMT_0          = '0'; 
	public static final char FMT_BLANK_ZERO = '0'; 
	
	public static final char FMT_1          = '1';   
	public static final char FMT_BINARY     = '1';   
	
	public static final char FMT_T          = 'T';   
	public static final char FMT_TIME       = 'T';   //'T' //000000 and 235959
	
	public static final char FMT_D          = 'D';   
	public static final char FMT_DATE       = 'D';   //'D'//00000000 valid if not R
	
	public static final char FMT_UPPER      = 'U';   
	
	public static final char FMT_LOWER      = 'l';   
	
	public static final char FMT_B            = 'B'; 
	public static final char FMT_BLANK_LETTER = 'B'; 
	
	public static final char FMT_Q   = '?';  
	public static final char FMT_RAW = '?';  
	
	
	// CODE
	public static final char  CODE_R = 'r';  //required
	public static final char  CODE_O = 'o';  //option
	public static final char  CODE_F = 'f';  //fixed content (constant)
	public static final char  CODE_L = 'l';  //list of values
	
	IDGenerator idgenerator;
	
	// Optimize relection
	// store Method Definition
	public Method smethod;
	public Method gmethod;
	
	public Accessor accessor = new Accessor() {
		public final void setValue(Object bean, String value) throws Exception {
			smethod.invoke(bean, new Object[] {value});
		}
		public final String getValue(Object bean) throws Exception {
			return (String)gmethod.invoke(bean, (Object[]) null );	
		}
	};
	

	public final void setValue(Object bean, String value) throws Exception {
		accessor.setValue(bean, value);
	}

	public final String getValue(Object bean) throws Exception {
		return accessor.getValue(bean);	
	}

	
	public Desc(char format, char code, int len, String name, String delimiter) // ValLength
	{	
		this.format    = format;
		this.mask      = false;
		this.code      = code;
		this.len       = len;
		this.name      = name;
		this.delimiter = delimiter;
		this.dbname    = name;
		
		if(format == FMT_CONSTANT)
			nostore = true;
	}
	
	public Desc(char format, char code, int len, String name) // FixLength
	{	
		this(format, code, len, name, null);
	}
	
	public Desc(String value) // DescConst
	{	
		this(FMT_CONSTANT, CODE_F, value.length(), value, null);
	}
	
	public Desc(int len, String value) // DescConst mit Länge
	{	
		this(FMT_CONSTANT, CODE_F, len, value, null);
	}
	
	public Desc(int len) // Reserved
	{	
		//this("", len);
		this(FMT_CONSTANT, CODE_O, len, "", null);
	}
	
	public Desc setMask() 
	{
		this.mask = true;
		return this;
	}
	
	public Desc setSkipparsing() 
	{
		this.skipparsing = true;
		return this;
	}

	public Desc setDBname(String name) 
	{
		this.dbname = name;
		return this;
	}

	public Desc setPrimary(IDGenerator idgenerator) 
	{
		this.idgenerator = idgenerator;
		return setPrimary();
	}
	
	public IDGenerator getIDGenerator() {
		return this.idgenerator;
	}
	

	public Desc setPrimary() 
	{
		this.primary = true;
		return this;
	}

	
	public boolean isPrimary() {
		return primary;
	}

	
	public boolean isPacked() {
		return packed;
	}

	public Desc setPacked(boolean packed) {
		this.packed = packed;
		return this;
	}

	public boolean isUnsigned() {
		return unsigned;
	}

	public Desc setUnsigned(boolean unsigned) {
		this.unsigned = unsigned;
		return this;
	}

	public char    format;
	public char    code;
	public int     len;
	public String  delimiter;
	public String  name;
	public boolean mask = false;
	public String  dbname;
	public boolean primary;
	public boolean skipparsing = false;

	public boolean packed = false;
	public boolean unsigned = false;
	
	public String  xmlpath;
	
	// constant informationen müssen nicht im Part
	// gespeichert werden.
	public boolean nostore = false;
	
	public boolean nostring = false;

	public String pattern = null;
	
	public Desc setPattern(String pattern) {
		this.pattern = pattern;
		return this;
	}

	public boolean isNostring() {
		return nostring;
	}
 
	public Desc setNostring() {
		this.nostring = true;
		return this;
	}
	
	public Desc setNostore() {
		this.nostore = true;
		return this;
	}
}
