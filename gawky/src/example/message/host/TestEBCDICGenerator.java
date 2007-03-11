package example.message.host;

import gawky.message.generator.EBCDICGenerator;
import gawky.message.parser.EBCDICParser;
import gawky.service.dtaus_band.SatzA;

public class TestEBCDICGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		EBCDICGenerator ebcdic = new EBCDICGenerator();
		SatzA satza = new SatzA();
		
		satza.setKennzeichen("LB");
		satza.setBlzsender("20040000");
		satza.setReferenznummer("1234567");
		satza.setEmpfaenger("COMMERZBANK HAMBURG        ");
		
		byte[] s = ebcdic.generateString(satza, 150);
		System.out.println( new String( s ) );

		EBCDICParser parser = new EBCDICParser();
		satza.parse(parser, s);
		
		System.out.println(satza.getReferenznummer());
		System.out.println(satza.getBlzsender());
		System.out.println(satza.getEmpfaenger());
	}

}
