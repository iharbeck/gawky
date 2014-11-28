package gawky.service.dtaus;

import gawky.global.Constant;
import gawky.service.dtaus.dtaus_disc.SatzA;
import gawky.service.dtaus.dtaus_disc.SatzC;
import gawky.service.dtaus.dtaus_disc.SatzE;

import java.util.ArrayList;

public class DTProcessorDisk
{
	SatzA satza;
	SatzE satze;

	ArrayList<SatzC> satzcArray = new ArrayList<SatzC>();

	SatzC satzc;

	public void processSatzA(byte[] line) throws Exception
	{
		satza = new SatzA();
		satza.parse(new String(line, Constant.ENCODE_LATIN1));
	}

	public void processSatzE(byte[] line) throws Exception
	{
		satze = new SatzE();
		satze.parse(new String(line, Constant.ENCODE_LATIN1));
	}

	public SatzC processSatzC(byte[] line) throws Exception
	{
		satzc = new SatzC();
		satzc.parse(new String(line, Constant.ENCODE_LATIN1));

		satzcArray.add(satzc);

		return satzc;
	}

	public void processSatzCe(byte[] line, int x) throws Exception
	{
		SatzCe satze = new SatzCe();
		satze.parse(new String(line, Constant.ENCODE_LATIN1));

		satzc.addExtention(satze);
	}

	public ArrayList<SatzC> getSatzcArray()
	{
		return satzcArray;
	}

	public void setSatzcArray(ArrayList<SatzC> saetze)
	{
		satzcArray = saetze;
	}

	public SatzA getSatza()
	{
		return satza;
	}

	public void setSatza(SatzA satza)
	{
		this.satza = satza;
	}

	public SatzE getSatze()
	{
		return satze;
	}

	public void setSatze(SatzE satze)
	{
		this.satze = satze;
	}
}
