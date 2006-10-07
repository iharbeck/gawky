package gawky.message.part;

/**
 * Generic Part
 * 
 * @author Ingo Harbeck
 *
 */
public class Desc 
{
	// CONSTANTS
	public static final String END      = new String(new char[] {'\001'});
	public static final String ENDPART  = new String(new char[] {'\002'});
	public static final String ENDCOMMA = new String(new char[] {','});
	public static final String ENDCOLON = new String(new char[] {';'});
	public static final String ENDHASH  = new String(new char[] {'#'});
	
	// FORMAT
	public static final char FMT_C          = 'C';    //'C'
	public static final char FMT_CONSTANT   = 'C';    //'C'
	
	public static final char FMT_A          = 'A';    //'A'
	public static final char FMT_ASCII      = 'A';    //'A'
	
	public static final char FMT_X          = 'X';    //'X'
	public static final char FMT_UNICODE    = 'X';    //'X'
	
	public static final char FMT_9          = '9';    //'9'
	public static final char FMT_DIGIT      = '9';    //'9'
	
	public static final char FMT_BLANK      = ' ';    //' '
	
	public static final char FMT_0          = '0'; //'0'
	public static final char FMT_BLANK_ZERO = '0'; //'0'
	
	public static final char FMT_1          = '1';   //'1'
	public static final char FMT_BINARY     = '1';   //'1'
	
	public static final char FMT_T          = 'T';   //'T'
	public static final char FMT_TIME       = 'T';   //'T' //000000 and 235959
	
	public static final char FMT_D          = 'D';   //'D'
	public static final char FMT_DATE       = 'D';   //'D'//00000000 valid if not R
	
	public static final char FMT_U          = 'U';   //'U'
	public static final char FMT_UPPER      = 'U';   //'U'
	
	public static final char FMT_L          = 'L';   //'L'
	public static final char FMT_LOWER      = 'L';   //'L'
	
	public static final char FMT_B            = 'B'; //'B'
	public static final char FMT_BLANK_LETTER = 'B'; //'B'
	
	public static final char FMT_Q   = '?';  //'?'
	public static final char FMT_RAW = '?';  //'?'
	
	
	// CODE
	public static final char  CODE_R = 'r';  //required
	public static final char  CODE_O = 'o';  //option
	public static final char  CODE_F = 'f';  //fixed content (constant)
	//public static final char  CODE_L = 'l';  //list of values
	
	public Desc(char format, char code, int len, String name, String delimiter) // ValLength
	{	
		this.format    = format;
		this.mask      = false;
		this.code      = code;
		this.len       = len;
		this.name      = name;
		this.delimiter = delimiter;
		this.dbname    = name;
	}
	
	public Desc(char format, char code, int len, String name) // FixLength
	{	
		this(format, code, len, name, null);
	}
	
	public Desc(String value) // DescConst
	{	
		this(FMT_CONSTANT, CODE_F, value.length(), value, null);
	}
	
	public Desc(String value, int len) // DescConst mit Länge
	{	
		this(FMT_CONSTANT, CODE_F, len, value, null);
	}
	
	public Desc(int len) // Reserved
	{	
		this("", len);
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
	
	public char    format;
	public char    code;
	public int     len;
	public String  delimiter;
	public String  name;
	public boolean mask = false;
	public String  dbname;
	public boolean skipparsing = false;
}
