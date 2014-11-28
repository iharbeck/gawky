package example.incubator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jobber
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		String input;
		Pattern p;
		Matcher m;

		//NATURAL
		System.out.println("//EXEC  NAT2BAT".matches(".*?EXEC\\s*?NAT2BAT") ? "NATURAL" : "-");

		//REPRO
		System.out.println("//REPRO01    EXEC".matches(".*?REPR.*?\\sEXEC") ? "REPRO" : "-");

		//FILE1-2 3-4 5-6

		//KALTER
		System.out.println("//KALTER    EXEC   PGM=V1COMP".matches(".*?KALTER.*?\\sEXEC.*?PGM=V1COMP") ? "Kalter" : "-");

		input = "123,7676,=J";
		p = Pattern.compile("(\\d*?),(\\d*?),=(.*)", Pattern.CASE_INSENSITIVE);
		m = p.matcher(input);
		if(m.find())
		{
			System.out.println("Kalter von " + m.group(1) + " bis " + m.group(2) + " VAL " + m.group(3));
		}

		//FEBES
		System.out.println("//V700    EXEC  PGM=V700".matches(".*?\\sEXEC.*?PGM=V700") ? "FEBES" : "-");

		//SORT
		System.out.println("//SORT    EXEC  PGM=ICEMAN".matches(".*?SORT.*?\\sEXEC.*?PGM=ICEMAN") ? "SORT" : "-");

		input = "SORT FIELDS  = (10,97,A,21,33,D,44,55,A),FORMAT=BI";
		p = Pattern.compile("FIELDS\\s*?=\\s*?\\((.*?)\\).*", Pattern.CASE_INSENSITIVE);
		m = p.matcher(input);
		if(m.find())
		{
			System.out.println("SORT von " + m.group(1));

			input = m.group(1);

			Pattern sp = Pattern.compile("(\\d*),(\\d*),(.),?", Pattern.CASE_INSENSITIVE);
			Matcher sm = sp.matcher(input);
			while(sm.find())
			{
				System.out.println("Spalten von " + sm.group(1) + "-" + sm.group(2) + " order " + sm.group(3));
			}

		}

		System.out.println(" INCLUDE  COND ".matches(".*?INCLUDE.*?\\sCOND.*?") ? "INCLUDE" : "-");
		System.out.println(" OMID     COND ".matches(".*?OMID.*?\\sCOND.*?") ? "OMID" : "-");

		String sortinput = "    (16,4,CH,EQ,C'6390',OR ";

		p = Pattern.compile(".*?(\\d*),(\\d*),CH,(.*?),.*?C'(.*?)'", Pattern.CASE_INSENSITIVE);
		m = p.matcher(sortinput);
		if(m.find())
		{
			System.out.println("gawk " + m.group(1) + " " + m.group(2) + " " + m.group(3) + " " + m.group(4));
		}

		//PRUEF
		System.out.println("//PRUEF    EXEC PGM=IDCAMS".matches(".*?PRUEF.*?\\sEXEC.*?PGM=IDCAMS") ? "PRUEF" : "-");
		//FILE1
	}

}
