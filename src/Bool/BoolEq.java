package Bool;

import java.util.Set;

/**
 * Created by Emil_2 on 25.05.2018.
 */
public class BoolEq {

    private BoolExp exp;

    public BoolEq(BoolExp exp)
    {
        this.exp = exp;
    }

    public void simplify(int max)
    {

        while(exp instanceof BoolSet && ((BoolSet) exp).topoptimise() && max-->0)
        {
            System.out.println(exp);
            // {B} -> B
            Set<BoolExp> set = ((BoolSet) exp).getSet();
            if(set.size() == 1)
            {
                exp = set.iterator().next();
                System.out.println(exp);
            }

        }

    }

    @Override
    public String toString() {
        return exp.toString();
    }

    public static BoolExp Not(BoolExp b)
    {
        return new Not(b);
    }

    public static BoolExp Var(int id)
    {
        return new Variable(id);
    }

    public static BoolExp Con(boolean b)
    {
        return new Constant(b);
    }

    public static BoolExp Or(BoolExp b0) {
        Or or = new Or();
        or.addItem(b0);
        return or;
    }
    public static BoolExp Or(BoolExp b0, BoolExp b1) {
        Or or = new Or();
        or.addItem(b0);
        or.addItem(b1);
        return or;
    }
    public static BoolExp Or(BoolExp b0, BoolExp b1, BoolExp b2) {
        Or or = new Or();
        or.addItem(b0);
        or.addItem(b1);
        or.addItem(b2);
        return or;
    }
    public static BoolExp Or(BoolExp b0, BoolExp b1, BoolExp b2, BoolExp b3) {
        Or or = new Or();
        or.addItem(b0);
        or.addItem(b1);
        or.addItem(b2);
        or.addItem(b3);
        return or;
    }
    public static BoolExp Or(BoolExp b0, BoolExp b1, BoolExp b2, BoolExp b3, BoolExp b4) {
        Or or = new Or();
        or.addItem(b0);
        or.addItem(b1);
        or.addItem(b2);
        or.addItem(b3);
        or.addItem(b4);
        return or;
    }
    public static BoolExp Or(BoolExp b0, BoolExp b1, BoolExp b2, BoolExp b3, BoolExp b4, BoolExp b5) {
        Or or = new Or();
        or.addItem(b0);
        or.addItem(b1);
        or.addItem(b2);
        or.addItem(b3);
        or.addItem(b4);
        or.addItem(b5);
        return or;
    }
    public static BoolExp And(BoolExp b0) {
        And and = new And();
        and.addItem(b0);
        return and;
    }
    public static BoolExp And(BoolExp b0, BoolExp b1) {
        And and = new And();
        and.addItem(b0);
        and.addItem(b1);
        return and;
    }
    public static BoolExp And(BoolExp b0, BoolExp b1, BoolExp b2) {
        And and = new And();
        and.addItem(b0);
        and.addItem(b1);
        and.addItem(b2);
        return and;
    }
    public static BoolExp And(BoolExp b0, BoolExp b1, BoolExp b2, BoolExp b3) {
        And and = new And();
        and.addItem(b0);
        and.addItem(b1);
        and.addItem(b2);
        and.addItem(b3);
        return and;
    }
    public static BoolExp And(BoolExp b0, BoolExp b1, BoolExp b2, BoolExp b3, BoolExp b4) {
        And and = new And();
        and.addItem(b0);
        and.addItem(b1);
        and.addItem(b2);
        and.addItem(b3);
        and.addItem(b4);
        return and;
    }
    public static BoolExp And(BoolExp b0, BoolExp b1, BoolExp b2, BoolExp b3, BoolExp b4, BoolExp b5) {
        And and = new And();
        and.addItem(b0);
        and.addItem(b1);
        and.addItem(b2);
        and.addItem(b3);
        and.addItem(b4);
        and.addItem(b5);
        return and;
    }

}
