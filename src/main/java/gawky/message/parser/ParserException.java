package gawky.message.parser;

import gawky.message.part.Desc;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParserException extends Exception
{
	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(ParserException.class);

	private int retcode;
	private Desc desc;
	private String value;

	static HashMap errors = new HashMap();

	public final static int ERROR_MISSING_SETTER = 2100;
	public final static int ERROR_LINE_TO_SHORT = 2101;
	public final static int ERROR_FIELD_TO_SHORT = 2200;
	public final static int ERROR_FIELD_TO_LONG = 2300;
	public final static int ERROR_FIELD_REQUIRED = 2400;

	public final static int ERROR_TYPE_ASCII = 2501;
	public final static int ERROR_TYPE_DIGIT = 2502;
	public final static int ERROR_TYPE_BLANK = 2503;
	public final static int ERROR_TYPE_BLANK_ZERO = 2504;
	public final static int ERROR_TYPE_BINARY = 2505;
	public final static int ERROR_TYPE_UPPER = 2506;
	public final static int ERROR_TYPE_LOWER = 2507;
	public final static int ERROR_TYPE_BLANK_LETTER = 2508;
	public final static int ERROR_TYPE_DATE = 2509;
	public final static int ERROR_TYPE_TIME = 2510;
	public final static int ERROR_TYPE_PATTERN = 2511;

	public final static int ERROR_EXTRA_DATA = 2601;

	public final static int ERROR_PARSING = 2600;

	static
	{
		addError(ERROR_MISSING_SETTER, "MISSING SETTER");
		addError(ERROR_LINE_TO_SHORT, "LINE TO SHORT");
		addError(ERROR_FIELD_TO_SHORT, "TO SHORT");
		addError(ERROR_FIELD_TO_LONG, "TO LONG");
		addError(ERROR_FIELD_REQUIRED, "REQUIRED");

		addError(ERROR_TYPE_ASCII, "ASCII");
		addError(ERROR_TYPE_DIGIT, "DIGIT");
		addError(ERROR_TYPE_BLANK, "BLANK");
		addError(ERROR_TYPE_BLANK_ZERO, "BLANK/ZERO");
		addError(ERROR_TYPE_BINARY, "BINARY");
		addError(ERROR_TYPE_UPPER, "UPPER");
		addError(ERROR_TYPE_LOWER, "LOWER");
		addError(ERROR_TYPE_BLANK_LETTER, "BANK/LETTER");
		addError(ERROR_TYPE_DATE, "DATE");
		addError(ERROR_TYPE_TIME, "TIME");
		addError(ERROR_TYPE_PATTERN, "LIST");

		addError(ERROR_EXTRA_DATA, "EXTRA DATA");
	}

	public static final void addError(int val, String text)
	{
		errors.put(Integer.toString(val), text);
	}

	public ParserException(int retcode, String value)
	{
		super(errors.get(Integer.toString(retcode)) + "(" + Integer.toString(retcode) + ") VAL<" + value + ">");

		this.retcode = retcode;
		this.value = value;

		log.error(errors.get(Integer.toString(retcode)) + "(" + Integer.toString(retcode) + ") VAL<" + value + ">");
	}

	public ParserException(int retcode, Desc desc, String value)
	{

		super(errors.get(Integer.toString(retcode)) + "(" + Integer.toString(retcode) + ") ATT " + desc.name + "[" + desc.len + "] VAL<" + value + ">");

		this.retcode = retcode;
		this.value = value;
		this.desc = desc;

		log.error(errors.get(Integer.toString(retcode)) + "(" + Integer.toString(retcode) + ") ATT " + desc.name + "[" + desc.len + "] VAL<" + value + ">");
	}

	public int getRetcode()
	{
		return retcode;
	}

	public void setRetcode(int retcode)
	{
		this.retcode = retcode;
	}

	public Desc getDesc()
	{
		return desc;
	}

	public void setDesc(Desc desc)
	{
		this.desc = desc;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}