package example.database.part;


import gawky.database.generator.Generator;

import java.util.Locale;



public class CustomColum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Generator gen = new Generator();
		
		//Datumsformat definieren
		gen.setDateFormat("yyyy-MM-dd");
		
		//Zahlenformat
		gen.setLocale(new Locale("de", "DE"));

		//Sequence f�r ORACLE einf�gen
		gen.setCustomcolumns("id,");
		gen.setCustomparams("proud_ikaros_seq.nextval,");
		
		String sql = gen.generateInsertSQL(new DaoObject()).toString(); 
		// insert only DAO shouldent have an ID!!!
		System.out.println(sql);
		
		
	}

}
