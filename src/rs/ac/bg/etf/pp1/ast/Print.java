// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class Print extends Statement {

    private PrintExpr PrintExpr;

    public Print (PrintExpr PrintExpr) {
        this.PrintExpr=PrintExpr;
        if(PrintExpr!=null) PrintExpr.setParent(this);
    }

    public PrintExpr getPrintExpr() {
        return PrintExpr;
    }

    public void setPrintExpr(PrintExpr PrintExpr) {
        this.PrintExpr=PrintExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(PrintExpr!=null) PrintExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PrintExpr!=null) PrintExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PrintExpr!=null) PrintExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Print(\n");

        if(PrintExpr!=null)
            buffer.append(PrintExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Print]");
        return buffer.toString();
    }
}
