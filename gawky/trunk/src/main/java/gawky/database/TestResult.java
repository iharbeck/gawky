package gawky.database;

import gawky.global.Init;
import gawky.global.Option;

import java.util.ArrayList;
import java.util.Map;

public class TestResult
{
	public static void main(String[] args) throws Exception
	{
		Option.init();
		Init.initLib();

		main_start(null);
		main_start(null);
		main_start(null);
	}

	public static void main_start(String[] args) throws Exception
	{
		String sql = "select id as key, id as value from proud_import_daily_status where rownum < 10 ";

		DB.getRowList(sql);

		long start = System.currentTimeMillis();

		for(int c = 0; c < 10; c++)
		{
			ArrayList<Map<String, String>> old = DB.getRowList(sql);

			for(Map<String, String> row : old)
			{
				row.get("KEY");
				row.get("VALUE");
			}
		}

		long r1 = System.currentTimeMillis() - start;

		start = System.currentTimeMillis();

		for(int c = 0; c < 10; c++)
		{
			Result result = DB.getMemoryList(sql);

			int i = result.size();

			while(i-- > 0)
			{
				result.next();
				result.get(0);
				result.get(1);
			}
		}

		System.out.println("start");

		long r2 = System.currentTimeMillis() - start;

		System.out.println(": " + r1 + " " + r2);
	}
}
