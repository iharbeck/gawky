package gawky.global;

import gawky.database.DB;
import gawky.lang.SafeDateFormat;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Locale;

public class Validation
{
	public static boolean conticheck(long[] values)
	{
		int c = values.length;

		boolean start = false;
		for(int i = 0; i < c; i++)
		{
			if(values[i] > 0)
				start = true;
			else if(start)
				return false;
		}

		return true;
	}

	public static boolean notEmpty(String value)
	{
		return !isEmtpy(value);
	}

	public static boolean isEmtpy(String value)
	{
		return value == null || value.trim().equals("");
	}

	public static boolean notEmpty(ArrayList value)
	{
		return !isEmtpy(value);
	}

	public static boolean isEmtpy(ArrayList value)
	{
		return value == null || value.size() > 0;
	}

	/**
	 * Check string length to be between min and max
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */

	public static boolean notLength(String value, int min, int max)
	{
		return !notLength(value, min, max);
	}

	public static boolean isLength(String value, int min, int max)
	{
		if(value == null)
			value = "";

		int len = value.length();
		return len >= min && len <= max;
	}

	/**
	 * Check is valid date is provided
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 */

	public static boolean notDate(String value, String pattern)
	{
		return !isDate(value, pattern);
	}

	public static boolean isDate(String value, String pattern)
	{
		if(value == null)
			value = "";

		SafeDateFormat df = new SafeDateFormat(pattern);
		df.setLenient(false);

		try
		{
			df.parse(value);
		}
		catch(Exception e)
		{
			return false;
		}
		finally
		{
			df.setLenient(true);
		}

		return true;
	}

	/**
	 * Check id value is part of list values provided as ; separated list
	 * 
	 * @param value
	 * @param list
	 * @return
	 */

	public static boolean notListvalue(String value, String list)
	{
		return !isListvalue(value, list);
	}

	public static boolean isListvalue(String value, String list)
	{
		if(value == null)
			value = "";

		String[] values = list.split(";");

		for(int i = 0; i < values.length; i++)
		{
			if(values[i].equals(value))
				return true;
		}
		return false;
	}

	/**
	 * Check if value is between min and max
	 * 
	 * @param value
	 * @param min
	 * @param max
	 * @return
	 */

	public static boolean notBetween(String value, double min, double max)
	{
		return !isBetween(value, min, max);
	}

	public static boolean isBetween(String value, double min, double max)
	{
		if(value == null)
			value = "";

		double val = Format.getDouble(Locale.GERMAN, value);

		if(min <= val && val <= max)
			return true;

		return false;
	}

	public static boolean notBetween(String value, String min, String max)
	{
		return !isBetween(value, min, max);
	}

	public static boolean isBetween(String value, String min, String max)
	{
		try
		{
			return isBetween(value, Double.parseDouble(min), Double.parseDouble(max));
		}
		catch(Exception e)
		{
			return false;
		}
	}

	public static boolean notBigger(String value, String ref)
	{
		return !isBigger(value, ref);
	}

	public static boolean isBigger(String value, String ref)
	{
		return isBigger(value, Format.getDouble(Locale.GERMAN, ref));
	}

	public static boolean notBigger(String value, double ref)
	{
		return !isBigger(value, ref);
	}

	public static boolean isBigger(String value, double ref)
	{
		if(value == null)
			value = "";

		double val = Format.getDouble(Locale.GERMAN, value);

		if(val > ref)
			return true;

		return false;
	}

	public static boolean notSmaller(String value, String ref)
	{
		return !isSmaller(value, ref);
	}

	public static boolean isSmaller(String value, String ref)
	{
		return isSmaller(value, Format.getDouble(Locale.GERMAN, ref));
	}

	public static boolean notSmaller(String value, double ref)
	{
		return !isSmaller(value, ref);
	}

	public static boolean isSmaller(String value, double ref)
	{
		if(value == null)
			value = "";

		double val = Format.getDouble(Locale.GERMAN, value);

		if(val < ref)
			return true;

		return false;
	}

	/**
	 * Check if value exists in lookup table
	 * 
	 * @param value
	 *            Value to check
	 * @param connid
	 *            Connection pool id
	 * @param table
	 *            Name of table to check
	 * @param column
	 *            Name of column that should contain lookup value
	 * @return
	 */

	public static boolean notInDBLookup(String value, int connid, String table, String column)
	{
		return !isInDBLookup(value, connid, table, column);
	}

	public static boolean isInDBLookup(String value, int connid, String table, String column)
	{
		String sql = "SELECT " + column + " FROM " + table + " WHERE " + column + " = ? ";

		return isInDBLookup(value, connid, sql);
	}

	public static boolean notInDBLookup(String value, int connid, String sql)
	{
		return !isInDBLookup(value, connid, sql);
	}

	public static boolean isInDBLookup(String value, int connid, String sql)
	{
		if(value == null)
			value = "";

		Connection conn = null;

		try
		{
			conn = DB.getConnection(connid);
			return DB.getRow(conn, sql, new String[] { value }) != null;
		}
		catch(Exception e)
		{
		}
		finally
		{
			DB.doClose(conn);
		}

		return false;
	}
}
