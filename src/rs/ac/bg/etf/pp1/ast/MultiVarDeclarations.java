// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class MultiVarDeclarations extends MultiVarDecl {

    private MultiVarDecl MultiVarDecl;
    private VarDecl VarDecl;

    public MultiVarDeclarations (MultiVarDecl MultiVarDecl, VarDecl VarDecl) {
        this.MultiVarDecl=MultiVarDecl;
        if(MultiVarDecl!=null) MultiVarDecl.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public MultiVarDecl getMultiVarDecl() {
        return MultiVarDecl;
    }

    public void setMultiVarDecl(MultiVarDecl MultiVarDecl) {
        this.MultiVarDecl=MultiVarDecl;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MultiVarDecl!=null) MultiVarDecl.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MultiVarDecl!=null) MultiVarDecl.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MultiVarDecl!=null) MultiVarDecl.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiVarDeclarations(\n");

        if(MultiVarDecl!=null)
            buffer.append(MultiVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiVarDeclarations]");
        return buffer.toString();
    }
}
