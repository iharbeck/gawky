package gawky.service.dtaus;

import gawky.service.dtaus.dtaus_disc.SatzA;
import gawky.service.dtaus.dtaus_disc.SatzC;
import gawky.service.dtaus.dtaus_disc.SatzCe;
import gawky.service.dtaus.dtaus_disc.SatzE;

import java.util.ArrayList;

public class EBProcessorDisk 
{
	SatzA satza;
	SatzE satze;
	
	ArrayList satzcArray = new ArrayList();
	
	SatzC satzc;
	
	public void processSatzA(byte[] line) throws Exception
	{
		satza = new SatzA();
		satza.parse(line);
	}

	public void processSatzE(byte[] line) throws Exception
	{
		satze = new SatzE();
		satze.parse(line);
	}

	public SatzC processSatzC(byte[] line) throws Exception
	{
		satzc = new SatzC();
		satzc.parse(line);
		
		satzcArray.add(satzc);

		return satzc;
	}
	
	public void processSatzCe(byte[] line, int x) throws Exception
	{
		SatzCe satze = new SatzCe();
		satze.parse(line);
		
		satzc.addExtention(satze);
	}

	public ArrayList getSatzcArray() {
		return satzcArray;
	}

	public void setSatzcArray(ArrayList saetze) {
		satzcArray = saetze;
	}

	public SatzA getSatza() {
		return satza;
	}

	public void setSatza(SatzA satza) {
		this.satza = satza;
	}

	public SatzE getSatze() {
		return satze;
	}

	public void setSatze(SatzE satze) {
		this.satze = satze;
	}
}
