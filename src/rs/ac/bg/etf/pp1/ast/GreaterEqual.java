// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class GreaterEqual extends Relop {

    public GreaterEqual () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("GreaterEqual(\n");

        buffer.append(tab);
        buffer.append(") [GreaterEqual]");
        return buffer.toString();
    }
}
