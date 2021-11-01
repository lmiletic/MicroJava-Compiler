// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private MultiConstDecl MultiConstDecl;
    private ConstDecl ConstDecl;

    public ConstDeclList (Type Type, MultiConstDecl MultiConstDecl, ConstDecl ConstDecl) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.MultiConstDecl=MultiConstDecl;
        if(MultiConstDecl!=null) MultiConstDecl.setParent(this);
        this.ConstDecl=ConstDecl;
        if(ConstDecl!=null) ConstDecl.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public MultiConstDecl getMultiConstDecl() {
        return MultiConstDecl;
    }

    public void setMultiConstDecl(MultiConstDecl MultiConstDecl) {
        this.MultiConstDecl=MultiConstDecl;
    }

    public ConstDecl getConstDecl() {
        return ConstDecl;
    }

    public void setConstDecl(ConstDecl ConstDecl) {
        this.ConstDecl=ConstDecl;
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
        if(Type!=null) Type.accept(visitor);
        if(MultiConstDecl!=null) MultiConstDecl.accept(visitor);
        if(ConstDecl!=null) ConstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MultiConstDecl!=null) MultiConstDecl.traverseTopDown(visitor);
        if(ConstDecl!=null) ConstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MultiConstDecl!=null) MultiConstDecl.traverseBottomUp(visitor);
        if(ConstDecl!=null) ConstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclList(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultiConstDecl!=null)
            buffer.append(MultiConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDecl!=null)
            buffer.append(ConstDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclList]");
        return buffer.toString();
    }
}
