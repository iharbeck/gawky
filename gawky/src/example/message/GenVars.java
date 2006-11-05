package example.message;

import gawky.message.generator.Generator;
import gawky.service.dtaus.SatzC;
import gawky.service.dtaus.SatzCe;

public class GenVars {

	public static void main(String[] args) 
	{
		//Generator.generateVars(new RecordEXTI00());
		Generator.generateVars(new SatzC());
		Generator.generateVars(new SatzCe());
	}

}
