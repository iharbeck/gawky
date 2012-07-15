package gawky.sort;

import gawky.global.Format;
import gawky.global.Option;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class FileIndexer
{
	static ArrayList<Entry> index_store = new ArrayList<Entry>(1000000);

	@Test
	public void testFile() throws Exception
	{
		//main(new String[] { "c:/test.fileABC", "c:/test.fileABC.out", "22", "20" });
		main(new String[] { "c:/test.file_s", "c:/test.file_s.out", "2", "2" });
	}

	public static void main(String[] args) throws Exception
	{
		Option.init();

		index(args[0], args[1], Format.getInt(args[2]), Format.getInt(args[3]), true);
	}

	public static void index(String file_in, String file_out, int id_pos, int id_len, boolean binary) throws Exception
	{
		System.out.println("START LESEN.");

		FileInputStream is = new FileInputStream(new File(file_in));
		FileChannel fchannel = is.getChannel();

		int filesize = (int)fchannel.size();

		int zeilenlaenge = 1;
		int zeilenumbruch = 1;

		int val = 0;

		while((val = is.read()) != '\n' && val != '\r')
			zeilenlaenge++;

		if((val = is.read()) == '\n' || val == '\r')
		{
			zeilenlaenge++;
			zeilenumbruch++;
		}

		ByteBuffer id_value = ByteBuffer.allocate(id_len);

		for(int i = 0; i * zeilenlaenge <= filesize; i++)
		{
			fchannel.position(i * zeilenlaenge + id_pos);

			id_value.position(0);

			if(fchannel.read(id_value) > 0)
				index_store.add(new Entry(id_value.array(), i, binary));
		}

		System.out.println("INDEX ERSTELLT: " + index_store.size());

		Collections.sort(index_store);

		System.out.println("INDEX SORTIERT.");

		FileWriter wr = new FileWriter(file_out);

		MappedByteBuffer bytebuffer = fchannel.map(FileChannel.MapMode.READ_ONLY, 0, filesize);

		int zeilenlaengenetto = zeilenlaenge - zeilenumbruch;

		byte[] zeile = new byte[zeilenlaengenetto + 1];

		zeile[zeilenlaengenetto] = '\n';

		int line_index = 0;

		for(Entry entry : index_store)
		{
			bytebuffer.position(zeilenlaenge * entry.getLine());

			bytebuffer.get(zeile, 0, zeilenlaengenetto);

			wr.write(new String(zeile));

			if(line_index % 1000 == 0)
				wr.flush();

			if(line_index % 100000 == 0)
				System.out.println("" + line_index);

			line_index++;
		}

		wr.flush();
		wr.close();

		fchannel.close();
		is.close();

		System.out.println("done");
	}
}
