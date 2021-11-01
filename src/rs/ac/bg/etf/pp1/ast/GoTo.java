// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class GoTo extends Statement {

    private String labelname;

    public GoTo (String labelname) {
        this.labelname=labelname;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname=labelname;
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
        buffer.append("GoTo(\n");

        buffer.append(" "+tab+labelname);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [GoTo]");
        return buffer.toString();
    }
}
