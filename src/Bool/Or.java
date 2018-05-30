package Bool;

import javafx.util.Pair;

import java.util.Set;

/**
 * Created by Emil_2 on 24.05.2018.
 */
public class Or extends BoolSet
{
    public Or()
    {
        super();
    }

    Or(Set<BoolExp> set)
    {
        super(set);
    }

    @Override
    public String toString() {
        return super.getString(true);
    }

    @Override
    boolean topoptimise() {
        return super.topoptimise(true);
    }

    @Override
    Pair<BoolSet, Set<BoolExp>> optimise() {
        return super.optimise(true);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (getSet().size() < 2 ? 31 : Boolean.hashCode(true));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Or && ((Or) obj).getSet().equals(getSet());
    }
}
