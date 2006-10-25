package example.message;

import gawky.message.generator.Generator;
import gawky.service.bcoscs.RecordEXTI00;

public class GenVars {

	public static void main(String[] args) 
	{
		Generator.generateVars(new RecordEXTI00());
	}

}
