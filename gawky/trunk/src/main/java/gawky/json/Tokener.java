package gawky.json;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;

public class Tokener
{

	static int ar = 0;
	static int va = 0;

	public static void main(String args[])
	{
		try
		{
			FileReader fr = new FileReader("c:/ddd/json.example");
			BufferedReader br = new BufferedReader(fr);
			int maxlen = 256;
			int currlen = 0;
			char wordbuf[] = new char[maxlen];
			int c;
			do
			{
				c = br.read();
				if(c >= 'a' && c <= 'z' ||
				        c >= 'A' && c <= 'Z' ||
				        c >= '0' && c <= '9' ||
				        c == '"' || c == '_')
				{
					if(currlen == maxlen)
					{
						maxlen *= 1.5;
						char xbuf[] =
						        new char[maxlen];
						System.arraycopy(
						        wordbuf, 0,
						        xbuf, 0, currlen);
						wordbuf = xbuf;
					}
					wordbuf[currlen++] = (char)c;
				}
				else if(c == ':' || c == '{' || c == '}' || c == '[' || c == ']')
				{
					System.out.println("" + (char)c);
				}
				else if(currlen > 0)
				{
					String s = new String(wordbuf,
					        0, currlen);
					// do something with s
					System.out.println(" -- " + s);
					currlen = 0;
				}
			}
			while(c != -1);
			br.close();
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}

	public static void __main(String[] args) throws Exception
	{

		// Create the tokenizer to read from a file
		FileReader rd = new FileReader("c:/ddd/json.example");

		StreamTokenizer st = new StreamTokenizer(rd);

		// Prepare the tokenizer for Java-style tokenizing rules
		st.parseNumbers();
		st.wordChars('_', '_');
		st.eolIsSignificant(true);

		// If whitespace is not to be discarded, make this call
		//st.ordinaryChars(0, ' ');

		// These calls caused comments to be discarded
		st.slashSlashComments(true);
		st.slashStarComments(true);

		// Parse the file
		int token = 0;
		while((token = st.nextToken()) != StreamTokenizer.TT_EOF)
		{
			switch(token)
			{
				case StreamTokenizer.TT_NUMBER: // A number was found; the value is in nval
					double num = st.nval;
					break;
				case StreamTokenizer.TT_WORD: // A word was found; the value is in sval
					String word = st.sval;
					System.out.println(va + " " + ar + " " + word);
					break;
				case '"': // A double-quoted string was found; sval contains the contents
					String dquoteVal = st.sval;
					System.out.println(va + " " + ar + " " + dquoteVal);
					break;
				case '\'':
					// A single-quoted string was found; sval contains the contents
					String squoteVal = st.sval;
					break;
				case StreamTokenizer.TT_EOL:
					// End of line character found
					break;
				case StreamTokenizer.TT_EOF:
					// End of file has been reached
					break;
				default:
					// A regular character was found; the value is the token itself
					char ch = (char)st.ttype;

					System.out.println("-- " + ch);

					switch(ch)
					{
						case '[':
							// new Array
							ar = 0;
							break;
						case '{':
							// new Object
							break;

						case ',':
						case ']':
						case '}':
							ar++;
							va = 0;
							break;
						case ':':
							// value
							va = 1;
							break;
						default:
							break;
					}

					break;
			}
		}
		rd.close();
	}

}
