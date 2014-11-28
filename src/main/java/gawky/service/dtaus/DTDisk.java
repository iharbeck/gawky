package gawky.service.dtaus;

import gawky.global.Constant;
import gawky.message.parser.Parser;
import gawky.message.parser.ParserException;
import gawky.service.dtaus.dtaus_disc.SatzA;
import gawky.service.dtaus.dtaus_disc.SatzC;
import gawky.service.dtaus.dtaus_disc.SatzE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;

public class DTDisk
{
	private String encoding;

	public DTDisk(String encoding)
	{
		this.encoding = encoding;
	}

	public static DTProcessorDisk[] read(File f, String encoding) throws IOException, Exception
	{
		DTDisk handler = new DTDisk(encoding);

		handler.open(f);

		ArrayList<DTProcessorDisk> list = new ArrayList<DTProcessorDisk>();

		DTProcessorDisk processor = null;

		while(handler.next())
		{
			if(handler.isSatzC())
			{
				SatzC satzc = handler.getSatzc();

				processor.getSatzcArray().add(satzc);
			}
			else if(handler.isSatzE())
			{
				SatzE satze = handler.getSatze();

				processor.setSatze(satze);
			}
			else if(handler.isSatzA()) // Kopfsatz Mandant ermitteln
			{
				SatzA satza = handler.getSatza();

				processor = new DTProcessorDisk();

				list.add(processor);

				processor.setSatza(satza);
			}
		}

		handler.close();

		return list.toArray(new DTProcessorDisk[0]);
	}

	public static void write(File f, DTProcessorDisk processor) throws IOException, Exception
	{
		FileOutputStream fos = new FileOutputStream(f);

		fos.write(processor.getSatza().getSatzA());

		Iterator it = processor.getSatzcArray().iterator();

		SatzE satze = new SatzE();

		while(it.hasNext())
		{
			SatzC c = (SatzC)it.next();
			satze.add(c); // Abstimmsatz

			fos.write(c.getSatzC());
		}

		fos.write(satze.getSatzE());

		fos.close();
	}

	static Parser parser = new Parser();

	FileChannel fc;
	MappedByteBuffer mappedbuffer;

	SatzA satza = new SatzA();
	SatzE satze = new SatzE();
	SatzC satzc = new SatzC();

	byte[] line = new byte[128 * 6];
	byte[] length = new byte[4];

	byte[] part = new byte[29];

	public void open(String infile) throws Exception
	{
		File f = new File(infile);
		open(f);
	}

	FileInputStream fis;

	public void open(File f) throws Exception
	{
		// Open the file and then get a channel from the stream
		fis = new FileInputStream(f);
		fc = fis.getChannel();

		// Get the file's size and then map it into memory
		int sz = (int)fc.size();
		mappedbuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);
	}

	static final int SATZA = 0;
	static final int SATZC = 1;
	static final int SATZE = 2;

	int satztype;

	public final boolean isSatzA()
	{
		return satztype == SATZA;
	}

	public final boolean isSatzC()
	{
		return satztype == SATZC;
	}

	public final boolean isSatzE()
	{
		return satztype == SATZE;
	}

	public SatzA getSatza()
	{
		return satza;
	}

	public SatzC getSatzc()
	{
		return satzc;
	}

	public SatzE getSatze()
	{
		return satze;
	}

	public boolean next() throws ParserException, UnsupportedEncodingException
	{
		if(mappedbuffer.remaining() >= 5)
		{
			mappedbuffer.get(length, 0, 4);

			//Integer.parseInt(new String("ðñòø".getBytes(), Constant.ENCODE_EBCDIC));

			int linelen = Integer.parseInt(new String(length, encoding));

			if(linelen % 128 != 0) // AUF SEGMENT LÄNGE VERGRÖSSERN
			{
				linelen = 256; //((linelen / 128) + 1) * 128;
			}

			mappedbuffer.get(line, 4, linelen - 4);

			String type = new String(line, 4, 1, encoding);

			if(type.charAt(0) == 'C')
			{
				satzc = new SatzC();
				satzc.parse(parser, new String(line, encoding));

				int ext = Integer.parseInt(satzc.getErweiterungskennnzeichen());

				int x = 0;
				for(; x < ext && x <= 1; x++)
				{
					System.arraycopy(line, 187 + 29 * x, part, 0, 29);
					SatzCe satzce = new SatzCe();
					satzce.parse(new String(part, encoding));

					satzc.addExtention(satzce);
				}

				// mehr als 2 Extention??
				if(x < ext)
				{
					do
					{
						mappedbuffer.get(line, 4, 128);

						for(int p = 0; x < ext && p < 4; x++, p++)
						{
							System.arraycopy(line, 4 + 29 * (p), part, 0, 29);
							SatzCe satzce = new SatzCe();
							satzce.parse(new String(part, encoding));

							satzc.addExtention(satzce);
						}
					}
					while(x < ext);
				}
				satztype = SATZC;
			}
			else if(type.charAt(0) == 'E')
			{
				satze = new SatzE();
				satze.parse(parser, new String(line, encoding));
				satztype = SATZE;
			}
			else if(type.charAt(0) == 'A') // Kopfsatz Mandant ermitteln
			{
				satza = new SatzA();
				satza.parse(parser, new String(line, encoding));
				satztype = SATZA;
			}
		}
		else
		{
			return false;
		}

		return true;
	}

	public void close() throws Exception
	{
		// Close the channel and the stream
		if(fc != null)
		{
			fc.close();
		}

		if(fis != null)
		{
			fis.close();
		}
	}

	public static void main(String[] args) throws Exception
	{
		Parser.setDocheck(false);

		File fi = new File("E:/ZE_COMDA.ASC"); // DTI

		DTProcessorDisk processor[] = DTDisk.read(fi, Constant.ENCODE_LATIN1);
		//DTDisk.read(fi, processor, Constant.ENCODE_EBCDIC);

		DTProcessorDisk mainprocessor = processor[0];

		SatzA satza = mainprocessor.getSatza();
		satza.setDateidatum("010911");
		satza.setValutadatum("010911");

		for(SatzC satzc : mainprocessor.getSatzcArray())
		{
			satzc.setKontonummer("4" + "47700160");
			satzc.setBetrageuro("23800");
			satzc.setSatzCe(new ArrayList<SatzCe>());
			satzc.setVerwendungszweck("12345");
			satzc.setAuftraggebername("Testkunde");

			System.out.println("\n");
			System.out.println("Betrag: " + satzc.getBetrageuro()); // Betrag
			System.out.println(satzc.getKontonummer()); // Debitornummer
			System.out.print(satzc.getVerwendungszweck());
			for(SatzCe ce : satzc.getSatzCe())
			{
				if(ce.getKennzeichen().equals("02"))
				{
					System.out.print(ce.getDaten());
				}
			}
		}

		File fo = new File("E:/ZE_COMDA.ASC.out");
		DTDisk.write(fo, mainprocessor);
	}
}
