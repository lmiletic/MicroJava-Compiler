package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	private int mainPc;
	private Struct boolTyp;
	
	private List<Boolean> firstMinus = new LinkedList<Boolean>();
	
	public CodeGenerator (Struct bool) {
		this.boolTyp = bool;
	}
	
	public int getMainPc() {
		return mainPc;
	}
	
	public void visit(MethodMain methodMain) {
		
		mainPc = Code.pc;
		SyntaxNode methodNode = methodMain.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		//nemam formalne parametre
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(0);
		Code.put(varCnt.getCount());
	}
	
	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	public void visit(Return returnExpr){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	Stack<Integer> skipCondTerm = new Stack<Integer>();
	public void visit(CondExpr condExpr) {
		Code.loadConst(0);
		Code.putFalseJump(Code.ne, 0);//izlaze netacni i vracamo ih na sledeci OR ili Condition
		skipCondTerm.push(Code.pc - 2);
		//nastavljaju tacni
	}
	
	public void visit(CondExprRelop condExprRelop) {
		int relop = -1;
		if(condExprRelop.getRelop() instanceof Equals)
			relop = Code.eq;
		else if(condExprRelop.getRelop() instanceof Distinct)
			relop = Code.ne;
		else if(condExprRelop.getRelop() instanceof Lower)
			relop = Code.lt;
		else if(condExprRelop.getRelop() instanceof LowerEqual)
			relop = Code.le;
		else if(condExprRelop.getRelop() instanceof Greater)
			relop = Code.gt;
		else if(condExprRelop.getRelop() instanceof GreaterEqual)
			relop = Code.ge;
		Code.putFalseJump(relop, 0);//izlaze netacni i vracamo ih na sledeci OR ili Condition
		skipCondTerm.push(Code.pc - 2);
		//nastavljaju tacni
	}
	
	Stack<Integer> skipCondition = new Stack<Integer>();
	public void visit(CondTerm condTerm) {
		//ovde su svi koji su tacni
		Code.putJump(0);//bezuslovno saljemo tacne na then
		skipCondition.push(Code.pc - 2);
		
		while(!skipCondTerm.empty())
			Code.fixup(skipCondTerm.pop());
	}
	
	Stack<Integer> skipThen = new Stack<Integer>();
	public void visit(Condition Condition) {
		//ovde su svi koji su netacni
		Code.putJump(0);//bezuslovno saljemo netacne na else -> skipuju Then
		skipThen.push(Code.pc - 2);
		
		while(!skipCondition.empty())
			Code.fixup(skipCondition.pop());
		
		//nastavljaju tacni, rade then
	}
	
	public void visit(ElseFalse elseFalse) {
		Code.fixup(skipThen.pop());
		//ovde nastavljaju netacni
	}
	
	Stack<Integer> skipElse = new Stack<Integer>();
	public void visit(ElseNonTerm elseNonTerm) {
		Code.putJump(0);
		skipElse.push(Code.pc - 2);
		//tacni bezuslovno skacu na kraj elsa -> skipuju Else
		
		Code.fixup(skipThen.pop());
		//pocetak els nastavljaju netacni
	}
	
	public void visit(ElseTrue elseTrue) {
		Code.fixup(skipElse.pop());
		//kraj Elsa nastavljaju tacni koju su ga skipovali
	}
	
	public void visit(YesMinus yesMinus) {
		this.firstMinus.add(true);
	}
	
	public void visit(NoMinus noMinus) {
		this.firstMinus.add(false);
	}
	
    public void visit(NoMoreAddOps noMoreAddOps) {
    	if(this.firstMinus.remove(this.firstMinus.size()-1)) {
    		Code.put(Code.neg);
    	}
    }
	
    public void visit(FactorDsg factordsg) {
    	Code.load(factordsg.getDesignator().obj);
    }
    
    public void visit(FactorNum factorNum) {
    	Code.loadConst(factorNum.getN1());
    }
    
    public void visit(FactorChar factorChar) {
    	Code.loadConst(factorChar.getC1());
    }
    
    public void visit(FactorBool factorBool) {
    	Code.loadConst(factorBool.getB1());
    }
    
    public void visit(FactorNewExpr factorNewExpr) {
    	Code.put(Code.newarray);
    	if(factorNewExpr.getType().struct == Tab.charType) {
    		Code.put(0);
    	}else {
    		Code.put(1);
    	}
    }
    
    public void visit(MoreAddOps moreAddOps) {
    	if(moreAddOps.getAddop() instanceof Plus) {
    		Code.put(Code.add);
    	}else if(moreAddOps.getAddop() instanceof Minus) {
    		Code.put(Code.sub);
    	}
    }
    
    public void visit(MoreMulOps moreMulOps) {
    	if(moreMulOps.getMulop() instanceof Mul) {
    		Code.put(Code.mul);
    	}else if(moreMulOps.getMulop() instanceof Div) {
    		Code.put(Code.div);
    	}else if(moreMulOps.getMulop() instanceof Mod) {
    		Code.put(Code.rem);
    	}
    }
    
    public void visit(DesignatorNameArray designatorNameArray) {
    	Code.load(designatorNameArray.obj);
    }
    
    public void visit(DsgExpr dsgExpr) {
    	Code.store(dsgExpr.getDesignator().obj);
    }
    
    public void visit(DsgHash dsgHash) {
    	Code.loadConst(1);
    	Code.put(Code.add);
    	Code.put(Code.dup2);
    	Code.put(Code.pop);
    	Code.put(Code.arraylength);
    	Code.put(Code.rem);
    	Code.put(Code.dup2);
    	Code.load(dsgHash.getDesignator().obj);
    	Code.loadConst(1);
    	Code.put(Code.add);
    	Code.store(dsgHash.getDesignator().obj);
    }
    
    public void visit(DsgInc dsgInc) {
    	if(dsgInc.getDesignator().obj.getKind() == Obj.Elem) {
    		Code.put(Code.dup2);
    	}
    	Code.load(dsgInc.getDesignator().obj);
    	Code.loadConst(1);
    	Code.put(Code.add);
    	Code.store(dsgInc.getDesignator().obj);
    }

    public void visit(DsgDec dsgDec) {
    	if(dsgDec.getDesignator().obj.getKind() == Obj.Elem) {
    		Code.put(Code.dup2);
    	}
    	Code.load(dsgDec.getDesignator().obj);
    	Code.loadConst(1);
    	Code.put(Code.sub);
    	Code.store(dsgDec.getDesignator().obj);
    }
    
    public void visit(WithNumConst withNumConst) {
    	Code.loadConst(withNumConst.getN2());
    	if(withNumConst.getExpr().struct.equals(Tab.charType)){
    		Code.put(Code.bprint);
    	}else {
    		Code.put(Code.print);
    	}
    }
    
    public void visit(WithOutNumConst withOutNumConst) {
    	Code.loadConst(0);
    	if(withOutNumConst.getExpr().struct.equals(Tab.charType)){
    		Code.put(Code.bprint);
    	}else {
    		Code.put(Code.print);
    	}
    }
    
    public void visit(Read read) {
    	if(read.getDesignator().obj.getType().equals(Tab.charType)) {
    		Code.put(Code.bread);
    	}else {
    		Code.put(Code.read);
    	}
    	Code.store(read.getDesignator().obj);
    }
    
   Map<String,Integer> labels = new HashMap<String, Integer>();
   Map<String, ArrayList<Integer>> patchAdr = new HashMap<String, ArrayList<Integer>>();
   
   public void visit(Label label) {
	   labels.put(label.getLabelname(), Code.pc);
	   
	   if(patchAdr.containsKey(label.getLabelname())) {
		   while(!patchAdr.get(label.getLabelname()).isEmpty()) {
			   Code.fixup(patchAdr.get(label.getLabelname()).remove(0));
		   }
	   }
   }
   
    public void visit(GoTo goTo) {
    	if(labels.containsKey(goTo.getLabelname())) {
    		Code.putJump(labels.get(goTo.getLabelname()));
    	}else {
    		Code.putJump(0);
    		if(patchAdr.containsKey(goTo.getLabelname())){
    			patchAdr.get(goTo.getLabelname()).add(Code.pc-2);
    		}else {
    			ArrayList<Integer> list = new ArrayList<Integer>();
    			list.add(Code.pc-2);
    			patchAdr.put(goTo.getLabelname(), list);
    		}
    	}
    }
	

}
