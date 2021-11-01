package rs.ac.bg.etf.pp1;

import java.util.LinkedList;
import java.util.List;

import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;


public class CompilerErrorList {

	public static List<CompilerError> cmpErrorList = new LinkedList<CompilerError>();
	public static boolean lexerError = false;
	
	public static void addError(int line, String message, int type) {
		CompilerErrorType errorType = null;
		switch(type) {
		case 0 : errorType = CompilerErrorType.LEXICAL_ERROR;
				 lexerError = true;
		break;
		case 1 : errorType = CompilerErrorType.SYNTAX_ERROR;
		break;
		case 2 : errorType = CompilerErrorType.SEMANTIC_ERROR;
		break;
		}
		cmpErrorList.add(new CompilerError(line, message, errorType));
	}
}
