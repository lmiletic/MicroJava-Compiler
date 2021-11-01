// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethodMain MethodMain;
    private LocalVars LocalVars;
    private StatementList StatementList;

    public MethodDecl (MethodMain MethodMain, LocalVars LocalVars, StatementList StatementList) {
        this.MethodMain=MethodMain;
        if(MethodMain!=null) MethodMain.setParent(this);
        this.LocalVars=LocalVars;
        if(LocalVars!=null) LocalVars.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodMain getMethodMain() {
        return MethodMain;
    }

    public void setMethodMain(MethodMain MethodMain) {
        this.MethodMain=MethodMain;
    }

    public LocalVars getLocalVars() {
        return LocalVars;
    }

    public void setLocalVars(LocalVars LocalVars) {
        this.LocalVars=LocalVars;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodMain!=null) MethodMain.accept(visitor);
        if(LocalVars!=null) LocalVars.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodMain!=null) MethodMain.traverseTopDown(visitor);
        if(LocalVars!=null) LocalVars.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodMain!=null) MethodMain.traverseBottomUp(visitor);
        if(LocalVars!=null) LocalVars.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethodMain!=null)
            buffer.append(MethodMain.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(LocalVars!=null)
            buffer.append(LocalVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
