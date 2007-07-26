package gawky.lang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Vacation 
{
	public static final int COUNTRY_NRW = 1;

	GregorianCalendar ostern;

	int[] feiertage = new int[30];
	int   feiercount = 0;
	int   year;
	int   country = COUNTRY_NRW;
	
	
	public Vacation() 
	{
		year = GregorianCalendar.getInstance().get(Calendar.YEAR);
	}

	public Vacation(int year, int country) 
	{
		setYear(year, country);
	}
	
	public void setYear(int year, int country) {
		this.year    = year;
		this.country = country;
		
		// Ostersonntag ermitteln
		ostern = new GregorianCalendar(year, 2, 1);
		ostern.add(Calendar.DAY_OF_MONTH, gaussformel(year)-1);

		// Feiertage NRW einfügen
		if(country == COUNTRY_NRW) {
			dayinyear(year, 0, 1);   // DAY_NEUJAHR
			dayinyear(-2);	         // DAY_KARFREITAG
			dayinyear(1);            // DAY_OSTERMONTAG
			dayinyear(year, 4, 1);   // DAY_MAIFEIERTAG
			dayinyear(39);           // DAY_CHRISTIHIMMELFAHRT
			dayinyear(50);           // DAY_PFINGSTMONTAG
			dayinyear(60);           // DAY_FRONLEICHNAM
			dayinyear(year, 9, 3);   // DAY_TAGDERDEUTSCHENEINHEIT
			dayinyear(year, 10, 1);  // DAY_ALLERHEILIGEN
			dayinyear(year, 11, 25); // DAY_1WEIHNACHTSTAG
			dayinyear(year, 11, 26); // DAY_2WEIHNACHTSTAG
		}
	}
	
	public static void main(String[] args) {
		
		Vacation vacation = new Vacation(2007, Vacation.COUNTRY_NRW);
		
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		vacation.list();
	}
	
	public Date getNextWorkingday(Date date)
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);

		do {
			cal.add(Calendar.DATE, 1);
		} while(!isWorkingday(cal));
		
		return cal.getTime();
	}
	
	public Date getPrevWorkingday(Date date)
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);

		do {
			cal.add(Calendar.DATE, -1);
		} while(!isWorkingday(cal));
		
		return cal.getTime();
	}
	
	public boolean isWorkingday(Date date) 
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		
		return isWorkingday(cal);
	}
	
	public boolean isWorkingday(GregorianCalendar cal) 
	{
		// Wochentag ermitteln
		int d = cal.get(Calendar.DAY_OF_WEEK);
		
		if(d == Calendar.SATURDAY || d == Calendar.SUNDAY)
			return false;
		
		if(year == 0) // Feiertage nicht betrachten
			return true;
		
		// Tag im Jahr ermitteln
		int dy = cal.get(Calendar.DAY_OF_YEAR);
		
		for (int i = 0; i < feiercount; i++) {
			if(feiertage[i] == dy)
				return false;
		}
		return true;
	}
	
	public void list() 
	{
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);

		for (int i = 0; i < feiercount; i++) {
			cal.set(Calendar.DAY_OF_YEAR, feiertage[i]);
			System.out.println(df.format(cal.getTime()));
		}
	}
	
	public void dayinyear(int diffostern) 
	{
		GregorianCalendar acal = (GregorianCalendar)ostern.clone();
		acal.add(Calendar.DAY_OF_MONTH, diffostern);
		
		feiertage[feiercount++] = acal.get(Calendar.DAY_OF_YEAR);
	}

	public void dayinyear(int year, int month, int day) 
	{
		GregorianCalendar acal = new GregorianCalendar(year, month, day);
		feiertage[feiercount++] = acal.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * Tag im März auf den Ostersonntag fällt
	 * @param year
	 * @return
	 */
	static int gaussformel (int year) 
	{
		 int a = year % 19;
		 int b = year % 4;
		 int c = year % 7;
		 int k = year / 100;
		 int p = (8 * k + 13) / 25;
		 int q = k / 4 ;
		 int M = (15 + k - p - q) % 30;
		 int N = (4 + k - q) % 7;
		 int d = (19 * a + M) % 30;
		 int e = (2 * b + 4 * c + 6 * d + N) % 7;

		 if(d + e == 35)
			 return 50;
		 
		 if(d == 28 && e == 6 && (11 * M + 11) % 30 < 19)
			 return 49;
		 
		 return 22 + d + e;
	}
	
}
