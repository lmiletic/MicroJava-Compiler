// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArray extends Designator {

    private DesignatorNameArray DesignatorNameArray;
    private Expr Expr;

    public DesignatorArray (DesignatorNameArray DesignatorNameArray, Expr Expr) {
        this.DesignatorNameArray=DesignatorNameArray;
        if(DesignatorNameArray!=null) DesignatorNameArray.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorNameArray getDesignatorNameArray() {
        return DesignatorNameArray;
    }

    public void setDesignatorNameArray(DesignatorNameArray DesignatorNameArray) {
        this.DesignatorNameArray=DesignatorNameArray;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorNameArray!=null) DesignatorNameArray.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorNameArray!=null) DesignatorNameArray.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorNameArray!=null) DesignatorNameArray.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArray(\n");

        if(DesignatorNameArray!=null)
            buffer.append(DesignatorNameArray.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArray]");
        return buffer.toString();
    }
}
