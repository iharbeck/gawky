package example.message;

import gawky.message.generator.UtilGenerator;
import gawky.service.dtaus.SatzC;
import gawky.service.dtaus.SatzCe;

public class GenVars {

	public static void main(String[] args) 
	{
		//Generator.generateVars(new RecordEXTI00());
		UtilGenerator.generateVars(new SatzC());
		UtilGenerator.generateVars(new SatzCe());
	}

}
