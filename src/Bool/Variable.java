package Bool;

/**
 * Created by Emil_2 on 24.05.2018.
 */
public class Variable implements BoolExp {
    private int varId;

    public Variable(int id)
    {
        varId = id;
    }

    public int getVarId() {
        return varId;
    }

    @Override
    public boolean contains(BoolExp exp) {
        return equals(exp);
    }

    @Override
    public String toString() {
        return (char)('A' + varId) + "";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && ((Variable)obj).getVarId() == varId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(varId);
    }
}
