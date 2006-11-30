header {
package gawky.incubator.antlr;
}


class MyParser extends Parser;

startRule
    :   n:NAME
        {System.out.println("Hi there, "+n.getText());}
    ;

class MyLexer extends Lexer;



tokens {
	"int"; "char"; "if"; "else"; "while";
}

protected
LETTER:
	'a'..'z'|'A'..'Z' 
	;

protected
DIGIT:
	'0'..'9'
	;

	
// one-or-more letters followed by a newline
NAME:    (LETTER) (NEWLINE)+ {}
    ;
    

	
NEWLINE
    :   '\r' '\n'   // DOS
    |   '\n'        // UNIX
    |   ' '
    |   '#'
    { $setType(Token.SKIP); }
    ;
