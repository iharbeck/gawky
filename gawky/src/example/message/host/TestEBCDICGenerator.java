package example.message.host;

import gawky.message.generator.EBCDICGenerator;
import gawky.service.dtaus.SatzA;

public class TestEBCDICGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EBCDICGenerator ebcdic = new EBCDICGenerator();
		SatzA satza = new SatzA();
		
		satza.setReferenznummer("1234567");
		
		System.out.println( ebcdic.generateString(satza, 150) );
	}

}
