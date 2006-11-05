package gawky.message.generator;

import gawky.service.dtaus.SatzA;

public class EB {

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
