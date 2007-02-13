package example.global;

import java.util.Date;

import gawky.global.Format;
import gawky.global.Validation;

public class TestFormat {

	public static void main(String[] args) {

		String value = "10.12.1974 23:59";
		
		System.out.println(Validation.isDate(value, "dd.MM.yyyy HH:mm"));
		
		Date date = Format.getDate(value, "dd.MM.yyyy HH:mm");
		
		System.out.println(date);
	}

}
