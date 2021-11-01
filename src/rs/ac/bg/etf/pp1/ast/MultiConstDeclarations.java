// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class MultiConstDeclarations extends MultiConstDecl {

    private MultiConstDecl MultiConstDecl;
    private ConstDecl ConstDecl;

    public MultiConstDeclarations (MultiConstDecl MultiConstDecl, ConstDecl ConstDecl) {
        this.MultiConstDecl=MultiConstDecl;
        if(MultiConstDecl!=null) MultiConstDecl.setParent(this);
        this.ConstDecl=ConstDecl;
        if(ConstDecl!=null) ConstDecl.setParent(this);
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultiConstDecl!=null) MultiConstDecl.accept(visitor);
        if(ConstDecl!=null) ConstDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultiConstDecl!=null) MultiConstDecl.traverseTopDown(visitor);
        if(ConstDecl!=null) ConstDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultiConstDecl!=null) MultiConstDecl.traverseBottomUp(visitor);
        if(ConstDecl!=null) ConstDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiConstDeclarations(\n");

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
        buffer.append(") [MultiConstDeclarations]");
        return buffer.toString();
    }
}
