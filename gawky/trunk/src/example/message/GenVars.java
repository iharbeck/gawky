package example.message;

import gawky.service.dtaus.dtaus_band.SatzC;
import gawky.service.dtaus.dtaus_band.SatzCe;

public class GenVars {

	public static void main(String[] args) 
	{
		System.out.println(new SatzC().buildVars());
		System.out.println(new SatzCe().buildVars()); 
	}

}
