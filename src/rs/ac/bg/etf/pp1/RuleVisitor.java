package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor{

	int printCallCount = 0;
	int varDeclCount = 0;
	int constDeclCount = 0;
	
	Logger log = Logger.getLogger(getClass());

	public void visit(SingleVarDecl SingleVarDecl) {
		varDeclCount++;
	}
    public void visit(ArrayVarDecl ArrayVarDecl){
		varDeclCount++;
    }
    public void visit(ConstDecl ConstDecl) {
    	constDeclCount++;
    }
	
//    public void visit(PrintStmt print) {
//		printCallCount++;
//	}

}
