package example.global;

import gawky.global.Format;
import gawky.global.Option;
import gawky.global.Validation;

import java.util.Date;

public class TestFormat {

	public static void main(String[] args) throws Exception {

		String value = "10.12.1974 23:59";
		
		System.out.println(Validation.isDate(value, "dd.MM.yyyy HH:mm"));
		
		Date date = Format.getDate(value, "dd.MM.yyyy HH:mm");
		
		System.out.println(date);
		
		Option.init();
		
		System.out.println(Option.getProperties("address[@ss=1]").length);
	}

}
