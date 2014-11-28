// $ANTLR 2.7.6 (2005-12-22): "t.g" -> "MyParser.java"$

package gawky.incubator.antlr;

import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;

public class MyParser extends antlr.LLkParser implements MyParserTokenTypes
{

	protected MyParser(TokenBuffer tokenBuf, int k)
	{
		super(tokenBuf, k);
		tokenNames = _tokenNames;
	}

	public MyParser(TokenBuffer tokenBuf)
	{
		this(tokenBuf, 1);
	}

	protected MyParser(TokenStream lexer, int k)
	{
		super(lexer, k);
		tokenNames = _tokenNames;
	}

	public MyParser(TokenStream lexer)
	{
		this(lexer, 1);
	}

	public MyParser(ParserSharedInputState state)
	{
		super(state, 1);
		tokenNames = _tokenNames;
	}

	public final void startRule() throws RecognitionException, TokenStreamException
	{

		Token n = null;

		try
		{ // for error handling
			n = LT(1);
			match(NAME);
			System.out.println("Hi there, " + n.getText());
		}
		catch(RecognitionException ex)
		{
			reportError(ex);
			recover(ex, _tokenSet_0);
		}
	}

	public static final String[] _tokenNames = {
	        "<0>",
	        "EOF",
	        "<2>",
	        "NULL_TREE_LOOKAHEAD",
	        "NAME",
	        "\"int\"",
	        "\"char\"",
	        "\"if\"",
	        "\"else\"",
	        "\"while\"",
	        "LETTER",
	        "DIGIT",
	        "NEWLINE"
	};

	private static final long[] mk_tokenSet_0()
	{
		long[] data = { 2L, 0L };
		return data;
	}

	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

}
