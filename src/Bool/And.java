package Bool;

import javafx.util.Pair;
import java.util.Set;

/**
 * Created by Emil_2 on 24.05.2018.
 */
public class And extends BoolSet
{
    public And()
    {
        super();
    }

    And(Set<BoolExp> set)
    {
        super(set);
    }

    @Override
    public String toString() {
        return super.getString(false);
    }

    @Override
    boolean topoptimise() {
        return super.topoptimise(false);
    }

    @Override
    Pair<BoolSet, Set<BoolExp>> optimise() {
        return super.optimise(false);
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ (getSet().size() < 2 ? 31 : Boolean.hashCode(false));
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof And && ((And) obj).getSet().equals(getSet());
    }
}
