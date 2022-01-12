package rs.ac.bg.etf.pp1;

import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	
	Struct currentType = null;
	Struct boolTyp;
	
	public SemanticAnalyzer (Struct bool) {
		this.boolTyp = bool;
	}
	
	boolean errorDetected = false;
	
	int nVars = 0;
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
		CompilerErrorList.addError(line, msg.toString(), 2);
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	
    public void visit(Print print) {
		printCallCount++;
	}
    
    public void visit(WithNumConst print) {
    	Struct printType = print.getExpr().struct;
    	if(!(printType.equals(Tab.intType) || printType.equals(Tab.charType) || printType.equals(boolTyp))) {
    		report_error("Nije moguce uraditi Print nad tipom koji nije Int,Char ili Bool", print);
    	}
    }
    
    public void visit(WithOutNumConst print) {
    	Struct printType = print.getExpr().struct;
    	if(!(printType.equals(Tab.intType) || printType.equals(Tab.charType) || printType.equals(boolTyp))) {
    		report_error("Nije moguce uraditi Print nad tipom koji nije Int,Char ili Bool", print);
    	}
    }
    
    public void visit(Read read) {
    	Struct readType = read.getDesignator().obj.getType();
    	if(read.getDesignator().obj.getKind() != Obj.Var) {
    		if(read.getDesignator().obj.getKind() != Obj.Elem) {
    			report_error("Read nad necim sto nije promenljiva", read);
    		}
    	}
    	if(!(readType.equals(Tab.intType) || readType.equals(Tab.charType) || readType.equals(boolTyp))) {
    		report_error("Nije moguce uraditi Read nad tipom koji nije Int,Char ili Bool", read);
    	}
    }
    
    public void visit(ProgramName programName) {
    	Tab.insert(Obj.Prog, programName.getI1(), Tab.noType).setLevel(0);
    	Tab.openScope();
    }
    
    public void visit(Program program) {
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(Tab.find(program.getProgramName().getI1()));
    	Tab.closeScope();
    }
    
    public void visit(ConstDecl constDecl) {
    	
    	int value = 0;
    	
    	if(constDecl.getConstant() instanceof ConstNum) {
    		if(currentType != Tab.intType)
    			report_error("Konstanta " + constDecl.getConstName() + " pogresnog tipa", constDecl);
    		value = ((ConstNum) constDecl.getConstant()).getN1();
    	}else if(constDecl.getConstant() instanceof ConstBool) {
    		if(currentType != boolTyp) 
    			report_error("Konstanta " + constDecl.getConstName() + " pogresnog tipa", constDecl);
    		value = ((ConstBool) constDecl.getConstant()).getB1();
    	}else if(constDecl.getConstant() instanceof ConstChar) {
    		if(currentType != Tab.charType) 
    			report_error("Konstanta " + constDecl.getConstName() + " pogresnog tipa", constDecl);
        	value = ((ConstChar) constDecl.getConstant()).getC1();
        }
    	
    	if(Tab.currentScope.findSymbol(constDecl.getConstName()) == null) {
    		Obj newConst = Tab.insert(Obj.Con, constDecl.getConstName(), currentType);
        	newConst.setAdr(value);
        	newConst.setLevel(0);
    	}else {
    		report_error("Naziv " + constDecl.getConstName() + " vec postoji, greska", constDecl);
    	}
    	
    }
    
    
    public void visit(ArrayVarDecl arrayVarDecl) {
    	
    	varDeclCount++;
    	
    	if(Tab.currentScope.findSymbol(arrayVarDecl.getVarName()) == null)
    		Tab.insert(Obj.Var, arrayVarDecl.getVarName(), new Struct(Struct.Array, currentType));
    	else
    		report_error("Naziv " + arrayVarDecl.getVarName() + " vec postoji, greska", arrayVarDecl);
    	
    }
    
    public void visit(SingleVarDecl singleVarDecl) {
    	
    	varDeclCount++;
    	
    	if(Tab.currentScope.findSymbol(singleVarDecl.getVarName()) == null)
    		Tab.insert(Obj.Var, singleVarDecl.getVarName(), currentType);
    	else
    		report_error("Naziv " + singleVarDecl.getVarName() + " vec postoji, greska", singleVarDecl);
    }
    
    
    public void visit(Type type) {
    	Obj objType = Tab.find(type.getTypeName());
    	currentType = objType.getType();
    	if(currentType == Tab.noType || objType.getKind() != Obj.Type) {
    		report_error("Nepostojeci tip " + type.getTypeName(), type);
    		currentType = Tab.noType;
    		type.struct = Tab.noType;
    	}else {
    		type.struct = currentType;
    	}
    }
        
    public void visit(MethodMain methodMain) {
    	currentType = Tab.noType;
    	Tab.insert(Obj.Meth, "main", currentType);
    	Tab.openScope();
    }
    
    public void visit(MethodDecl methodDecl) {
    	Tab.chainLocalSymbols(Tab.find("main"));
    	Tab.closeScope();
    }
    
    public void visit(DesignatorSingle designatorSingle) {
    	Obj dsgSingle = Tab.find(designatorSingle.getDsgName());
    	if(dsgSingle == Tab.noObj) {
    		report_error("Korisceno ime " + designatorSingle.getDsgName() + " nije deklarisano pre upotrebe, greska", designatorSingle);
    	}
    	designatorSingle.obj = dsgSingle;
    }
    
    public void visit(DesignatorArray designatorArray) {
    	if(designatorArray.getDesignatorNameArray().obj == Tab.noObj) {
    		designatorArray.obj = Tab.noObj;
    	}else if(!designatorArray.getExpr().struct.equals(Tab.intType)) {
    		designatorArray.obj = Tab.noObj;
    		report_error("Index niza "+ designatorArray.getDesignatorNameArray().getDsgName() + " nije tipa int", designatorArray);
    	}else {
    		designatorArray.obj = new Obj(Obj.Elem, "elem - " + designatorArray.getDesignatorNameArray().getDsgName(), designatorArray.getDesignatorNameArray().obj.getType().getElemType());
    	}
    }
    
    public void visit(DesignatorNameArray designatorNameArray) {
    	Obj dsgArray = Tab.find(designatorNameArray.getDsgName());
    	if(dsgArray == Tab.noObj) {
    		report_error("Korisceno ime " + designatorNameArray.getDsgName() + " nije deklarisano pre upotrebe, greska", designatorNameArray);
    	}else if(dsgArray.getType().getKind() != Struct.Array) {
    			report_error("Korisceno ime " + designatorNameArray.getDsgName() + " nije nizovnog tipa", designatorNameArray);
    			dsgArray = Tab.noObj;
    	}
    	designatorNameArray.obj = dsgArray;
    }
    
    public void visit(DsgExpr dsgExpr) {
    	if(dsgExpr.getDesignator().obj.getKind() != Obj.Var) {
    		if(dsgExpr.getDesignator().obj.getKind() != Obj.Elem) {
    			report_error("Nije moguca dodela necemu sto nije promenljiva, greska", dsgExpr);
    		}
    	}
    	if(!(dsgExpr.getExpr().struct.assignableTo(dsgExpr.getDesignator().obj.getType()))) {
    		report_error("Nisu kompatibilni tipovi pri dodeli", dsgExpr);
    	}
    }
    
    public void visit(DsgInc dsgInc) {
    	if(dsgInc.getDesignator().obj.getKind() != Obj.Var) {
    		if(dsgInc.getDesignator().obj.getKind() != Obj.Elem) {
    			report_error("Inc nad necim sto nije promenljiva", dsgInc);
    		}
    	}
    	if(!(dsgInc.getDesignator().obj.getType().equals(Tab.intType))) {
    		report_error("Nije moguce uraditi Inc nad tipom koji nije Int", dsgInc);
    	}
    }

    public void visit(DsgDec dsgDec) {
    	if(dsgDec.getDesignator().obj.getKind() != Obj.Var) {
    		if(dsgDec.getDesignator().obj.getKind() != Obj.Elem) {
    			report_error("Dec nad necim sto nije promenljiva", dsgDec);
    		}
    	}
    	if(!(dsgDec.getDesignator().obj.getType().equals(Tab.intType))) {
    		report_error("Nije moguce uraditi Dec nad tipom koji nije Int", dsgDec);
    	}
    }
    
    public void visit(DsgHash dsgHash) {
    	if(dsgHash.getDesignator().obj.getKind() != Obj.Elem) {
    			report_error("Hash nad necim sto nije element niza", dsgHash);
    	}
    	if(!(dsgHash.getDesignator().obj.getType().equals(Tab.intType))) {
    		report_error("Nije moguce uraditi Hash nad tipom koji nije Int", dsgHash);
    	}
    }
    
    
    public void visit(FactorDsg factordsg) {
    	factordsg.struct = factordsg.getDesignator().obj.getType();
    }
    
    public void visit(FactorNum factorNum) {
    	factorNum.struct = Tab.intType;
    }
    
    public void visit(FactorChar factorChar) {
    	factorChar.struct = Tab.charType;
    }
    
    public void visit(FactorBool factorBool) {
    	factorBool.struct = boolTyp;
    }
    
    public void visit(FactorNewExpr factorNewExpr) {
    	if(factorNewExpr.getExpr().struct.equals(Tab.intType))
    		factorNewExpr.struct = new Struct(Struct.Array, factorNewExpr.getType().struct);
    	else {
    		factorNewExpr.struct = Tab.noType;
    		report_error("Greska pri alokaciji niza", factorNewExpr);
    	}
    }
    
    public void visit(FactorExpr factorExpr) {
    	factorExpr.struct = factorExpr.getExpr().struct;
    }
    
    public void visit(MoreMulOps moreMulOps) {
    	if(moreMulOps.getFactor().struct.equals(Tab.intType) && moreMulOps.getMulOpList().struct.equals(Tab.intType)) {
    		moreMulOps.struct = Tab.intType;
    	}else {
    		moreMulOps.struct = Tab.noType;
    		report_error("MulOps nad tipovima koji nisu oba int", moreMulOps);
    	}
    }
    
    public void visit(NoMoreMulOps noMoreMulOps) {
    	noMoreMulOps.struct = noMoreMulOps.getFactor().struct;
    }
    
    public void visit(MoreAddOps moreAddOps) {
    	if(moreAddOps.getTerm().struct.equals(Tab.intType) && moreAddOps.getAddOpList().struct.equals(Tab.intType)) {
    		moreAddOps.struct = Tab.intType;
    	}else {
    		moreAddOps.struct = Tab.noType;
    		report_error("AddOps nad tipovima koji nisu oba int", moreAddOps);
    	}
    }
    
    public void visit(NoMoreAddOps noMoreAddOps) {
    	noMoreAddOps.struct = noMoreAddOps.getTerm().struct;
    }
    
    public void visit(Term term) {
    	term.struct = term.getMulOpList().struct;
    }

    public void visit(Expr expr) {
    	if(expr.getMayMinus() instanceof NoMinus || (expr.getMayMinus() instanceof YesMinus && expr.getAddOpList().struct.equals(Tab.intType))) {
    		expr.struct = expr.getAddOpList().struct;
    	}else {
    		report_error("Pogresni tipovi", expr);
    		expr.struct = Tab.noType;
    	}
    }
    
    //Conditions
    
    public void visit(Condition condition) {
    	if(condition.getCondTermList().struct.equals(boolTyp)) {
    		condition.struct = condition.getCondTermList().struct;
    	}else {
    		condition.struct = Tab.noType;
    		report_error("Pogresan tip u uslovu", condition);
    	}
    }
    
    public void visit(FirstCondTerm firstCondTerm) {
    	firstCondTerm.struct = firstCondTerm.getCondTerm().struct;
    }
    
    public void visit(MoreCondTerm moreCondTerm) {
    	Struct firstCond = moreCondTerm.getCondTermList().struct;
    	Struct secondCond = moreCondTerm.getCondTerm().struct;
    	if(firstCond.equals(boolTyp) && secondCond.equals(boolTyp)) {
    		moreCondTerm.struct = boolTyp;
    	}else {
    		moreCondTerm.struct = Tab.noType;
    	}
    }
    
    public void visit(CondTerm condTerm) {
    	condTerm.struct = condTerm.getCondFactList().struct;
    }
    
    public void visit(FirstCondFact firstCondFact) {
    	firstCondFact.struct = firstCondFact.getCondFact().struct;
    }
    
    public void visit(MoreCondFact moreCondFact) {
    	Struct firstCond = moreCondFact.getCondFactList().struct;
    	Struct secondCond = moreCondFact.getCondFact().struct;
    	if(firstCond.equals(boolTyp) && secondCond.equals(boolTyp)) {
    		moreCondFact.struct = boolTyp;
    	}else {
    		moreCondFact.struct = Tab.noType;
    	}
    }
    
    public void visit(CondExpr condExpr) {
    	condExpr.struct = condExpr.getExpr().struct;
    }
    
    public void visit(CondExprRelop condExprRelop) {
    	Struct expr1 = condExprRelop.getExpr().struct;
    	Struct expr2 = condExprRelop.getExpr1().struct;
    	if(expr1.compatibleWith(expr2)) {
    		condExprRelop.struct = boolTyp;
    	}else {
    		report_error("Tipovi nisu kompatibilni za poredjenje", condExprRelop);
    		condExprRelop.struct = Tab.noType;
    	}
    	if(expr1.getKind() == Struct.Array || expr2.getKind() == Struct.Array) {
    		if(!(condExprRelop.getRelop() instanceof Equals) && !(condExprRelop.getRelop() instanceof Distinct)) {
    			report_error("Ref na niz je moguce porediti samo pomocu == ili !=", condExprRelop);
    			condExprRelop.struct = Tab.noType;
    		}
    	}
    }
    
   
    public boolean passed(){
    	return !errorDetected;
    }
    
}
