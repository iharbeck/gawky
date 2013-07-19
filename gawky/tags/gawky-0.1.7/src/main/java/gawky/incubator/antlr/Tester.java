package gawky.incubator.antlr;

import java.io.DataInputStream;

import antlr.Token;

public class Tester {
	    public static void main(String[] args) {
	        try {
	            MyLexer lexer = new MyLexer(new DataInputStream(System.in));

	            Token token;
	            
	            while(true) {
		            token = lexer.nextToken();
		            System.out.println(token);
	
		            if(token.getType() == Token.EOF_TYPE)
		            {
		                break;
		            }
	            }
	            
//	            MyParser parser = new MyParser(lexer);
//	            parser.startRule();
	            
	        } catch(Exception e) {
	            System.err.println("exception: "+e);
	        }
	    }
}
