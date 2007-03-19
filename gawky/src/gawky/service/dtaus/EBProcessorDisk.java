package gawky.service.dtaus;

import java.util.ArrayList;

import gawky.service.dtaus.dtaus_disc.SatzA;
import gawky.service.dtaus.dtaus_disc.SatzC;
import gawky.service.dtaus.dtaus_disc.SatzCe;
import gawky.service.dtaus.dtaus_disc.SatzE;

public class EBProcessorDisk 
{
	SatzA satza;
	SatzE satze;
	
	public void processSatzA(byte[] line) throws Exception
	{
		satza = new SatzA();
		satza.parse(line);
		
		System.out.println(satza.toString());
	}

	public void processSatzE(byte[] line) throws Exception
	{
		satze = new SatzE();
		satze.parse(line);
		
		System.out.println(satze.toString());
	}

	ArrayList CSaetze = new ArrayList();
	
	SatzC satzc;
	
	public SatzC processSatzC(byte[] line) throws Exception
	{
		satzc = new SatzC();
		satzc.parse(line);
		
		CSaetze.add(satzc);
		
		System.out.println(satzc.toString());
		return satzc;
	}
	
	public void processSatzCe(byte[] line, int x) throws Exception
	{
		SatzCe satze = new SatzCe();
		satze.parse(line);
		
		satzc.addExtention(satze);
		
		System.out.println(satze.toString());
		
	}

	public ArrayList getCSaetze() {
		return CSaetze;
	}

	public void setCSaetze(ArrayList saetze) {
		CSaetze = saetze;
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
