package Bool;

/**
 * Created by Emil_2 on 25.05.2018.
 */
public class Not implements BoolExp
{
    private BoolExp exp;

    public Not(BoolExp exp)
    {
        this.exp = exp;
    }

    public BoolExp getExp() {
        return exp;
    }

    @Override
    public boolean contains(BoolExp exp) {
        return equals(exp);
    }

    @Override
    public String toString() {
        return "~" + exp.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Not && ((Not)obj).getExp().equals(exp);
    }

    @Override
    public int hashCode() {
        return exp.hashCode() * 31 + Integer.hashCode(504011106);
    }
}
