package example.incubator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jobber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String input;
		Pattern p;
		Matcher m;
		
		//NATURAL
		System.out.println( "//EXEC  NAT2BAT".matches(".*?EXEC\\s*?NAT2BAT")  ? "NATURAL" : "-" );
		
		//REPRO
		System.out.println( "//REPRO01    EXEC".matches(".*?REPRO.*?\\sEXEC")  ? "REPRO" : "-" );

		//FILE1-2 3-4 5-6
		
		//KALTER
		System.out.println( "//KALTER    EXEC".matches(".*?KALTER.*?\\sEXEC")  ? "Kalter" : "-" );

		input = "123,7676,=J";
		p = Pattern.compile ("(\\d*?),(\\d*?),=(.*)", Pattern.CASE_INSENSITIVE);
		m = p.matcher (input);
		if (m.find()) {
			System.out.println ("Kalter von " + m.group(1) + " bis " + m.group(2) + " VAL " + m.group(3));
		}
		
		
		
		//FEBES
		System.out.println( "//V700    EXEC".matches(".*?V700.*?\\sEXEC")  ? "FEBES" : "-" );

		//SORT
		System.out.println( "//SORT    EXEC".matches(".*?SORT.*?\\sEXEC") ? "SORT" : "-" );

		
		
		input = "SORT FIELDS  = (1,97,A),FORMAT=BI";
		p = Pattern.compile ("FIELDS\\s*?=\\s*?\\((\\d),(\\d).*?\\).*", Pattern.CASE_INSENSITIVE);
		m = p.matcher (input);
		if (m.find()) {
			System.out.println ("SORT von " + m.group(1) + " bis " + m.group(2));
		}
		
		System.out.println( " INCLUDE  COND ".matches(".*?INCLUDE.*?\\sCOND.*?") ? "INCLUDE" : "-");
		System.out.println( " OMID     COND ".matches(".*?OMID.*?\\sCOND.*?") ? "OMID" : "-" );

		String sortinput = "    (16,4,CH,EQ,C'6390',OR ";
		
		p = Pattern.compile (".*?(\\d*),(\\d*),CH,(.*?),.*?C'(.*?)'", Pattern.CASE_INSENSITIVE);
		m = p.matcher (sortinput);
		if (m.find()) {
			System.out.println ("gawk " + m.group(1) + " " + m.group(2) + " " + m.group(3)+ " " + m.group(4));
		}

		//PRUEF
		System.out.println( "//PRUEF    EXEC".matches(".*?PRUEF.*?\\sEXEC") ? "PRUEF" : "-");
		//FILE1
	}

}
