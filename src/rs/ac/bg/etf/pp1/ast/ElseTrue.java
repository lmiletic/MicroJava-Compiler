// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class ElseTrue extends ElseStatement {

    private ElseNonTerm ElseNonTerm;
    private Statement Statement;

    public ElseTrue (ElseNonTerm ElseNonTerm, Statement Statement) {
        this.ElseNonTerm=ElseNonTerm;
        if(ElseNonTerm!=null) ElseNonTerm.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ElseNonTerm getElseNonTerm() {
        return ElseNonTerm;
    }

    public void setElseNonTerm(ElseNonTerm ElseNonTerm) {
        this.ElseNonTerm=ElseNonTerm;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ElseNonTerm!=null) ElseNonTerm.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ElseNonTerm!=null) ElseNonTerm.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ElseNonTerm!=null) ElseNonTerm.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ElseTrue(\n");

        if(ElseNonTerm!=null)
            buffer.append(ElseNonTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ElseTrue]");
        return buffer.toString();
    }
}
