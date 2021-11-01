
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}


%%

" "		{ }
"\b"	{ }
"\t"	{ }
"\r\n"	{ }
"\f"	{ }

"program"	{ return new_symbol(sym.PROG, yytext()); } 
"main"		{ return new_symbol(sym.MAIN, yytext()); } 
"print"		{ return new_symbol(sym.PRINT, yytext()); }
"read"		{ return new_symbol(sym.READ, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"return"	{ return new_symbol(sym.RETURN, yytext()); }
"void"		{ return new_symbol(sym.VOID, yytext()); }
"if"		{ return new_symbol(sym.IF, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"goto"		{ return new_symbol(sym.GOTO, yytext()); }

"+"			{ return new_symbol(sym.PLUS, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.MUL, yytext()); }
"/"			{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
"="			{ return new_symbol(sym.EQUAL, yytext()); }
"++"		{ return new_symbol(sym.INC, yytext()); }
"--"		{ return new_symbol(sym.DEC, yytext()); }
"=="		{ return new_symbol(sym.EQUALS, yytext()); }
"!="		{ return new_symbol(sym.DISTINCT, yytext()); }
">"			{ return new_symbol(sym.GREATER, yytext()); }
">="		{ return new_symbol(sym.GREATEREQUAL, yytext()); }
"<"			{ return new_symbol(sym.LOWER, yytext()); }
"<="		{ return new_symbol(sym.LOWEREQUAL, yytext()); }
"&&"		{ return new_symbol(sym.AND, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }
";"			{ return new_symbol(sym.SEMI, yytext()); }
","			{ return new_symbol(sym.COMMA, yytext()); }
"("			{ return new_symbol(sym.LPAREN, yytext()); }
")"			{ return new_symbol(sym.RPAREN, yytext()); }
"{"			{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"["			{ return new_symbol(sym.LBRACKET, yytext()); }
"]"			{ return new_symbol(sym.RBRACKET, yytext()); } 
"#"			{ return new_symbol(sym.HASH, yytext()); }
":"			{ return new_symbol(sym.COLON, yytext()); }

"//" 		{ yybegin(COMMENT); }
<COMMENT> . { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

[0-9]+	{ return new_symbol(sym.NUMBER, new Integer(yytext())); }
"true"	{ return new_symbol(sym.BOOLEAN, 1); }
"false"	{ return new_symbol(sym.BOOLEAN, 0); }
([a-z]|[A-Z])[a-zA-Z0-9_]*	{ return new_symbol(sym.IDENT, yytext()); }
"'"[\040-\176]"'" {return new_symbol (sym.CHAR, new Character(yytext().charAt(1))); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); 
	CompilerErrorList.addError((yyline+1), "Leksicka greska ("+yytext()+") u liniji "+(yyline+1), 0);}


