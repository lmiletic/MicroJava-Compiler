// generated with ast extension for cup
// version 0.8
// 28/5/2021 22:14:45


package rs.ac.bg.etf.pp1.ast;

public class YesLocalVars extends LocalVars {

    private LocalVars LocalVars;
    private VarDeclList VarDeclList;

    public YesLocalVars (LocalVars LocalVars, VarDeclList VarDeclList) {
        this.LocalVars=LocalVars;
        if(LocalVars!=null) LocalVars.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
    }

    public LocalVars getLocalVars() {
        return LocalVars;
    }

    public void setLocalVars(LocalVars LocalVars) {
        this.LocalVars=LocalVars;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(LocalVars!=null) LocalVars.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(LocalVars!=null) LocalVars.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(LocalVars!=null) LocalVars.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("YesLocalVars(\n");

        if(LocalVars!=null)
            buffer.append(LocalVars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [YesLocalVars]");
        return buffer.toString();
    }
}
