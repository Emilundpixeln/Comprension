package Bool;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by Emil_2 on 24.05.2018.
 */
public abstract class BoolSet implements BoolExp
{
    static final boolean debug = true;

    private Set<BoolExp> set;

    BoolSet()
    {
        set = new HashSet<>();
    }

    BoolSet(Set<BoolExp> set)
    {
        this.set = set;
    }

    public Set<BoolExp> getSet() {
        return set;
    }

    public void setSet(Set<BoolExp> set) {
        this.set = set;
    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return set.equals(obj);
    }

    public void addItem(BoolExp item)
    {
        set.add(item);
    }
    public void addItems(Collection<BoolExp> items)
    {
        set.addAll(items);
    }

    public void removeItem(BoolExp item)
    {
        set.remove(item);
    }

    @Override
    public boolean contains(BoolExp exp) {
        return set.contains(exp);
    }



    protected String getString(boolean isOr) {
        String s = "(";
        boolean first = true;
        for (BoolExp exp :
                set) {
            if(!first)
            {
                s += (isOr ? " OR" : " AND");
                s += " " + exp.toString();
            }
            else {
                first = false;
                s += exp.toString();
            }
        }
        return s + ")";
    }

    boolean topoptimise() { return false; }

    protected boolean topoptimise(boolean isOr)
    {
        Pair<BoolSet, Set<BoolExp>> pair = optimise(isOr);

        if(pair == null)
        {

                return false;
        }
        else
        {


            Set<BoolExp> newSet = new HashSet<>(pair.getValue());

            pair.getKey().setSet(newSet);
            return true;
        }
    }

    abstract Pair<BoolSet, Set<BoolExp>> optimise();

    // return the BoolSet which should be set to the set
    // null if nothing needs to be changed
    Pair<BoolSet, Set<BoolExp>> optimise(boolean isOr)
    {
        Pair<BoolSet, Set<BoolExp>> dirty;

        {
            // optimise inner first


            BoolExp[] arr = set.toArray(new BoolExp[set.size()]);
            for (int i = 0; i < arr.length; i++) {
                if(arr[i] instanceof BoolSet)
                {
                    dirty = ((BoolSet) arr[i]).optimise();
                    if(dirty != null)
                        return dirty;
                }
            }

        }

        {
            // delete duplicates
            Set<Integer> hashes = new HashSet<>(set.size());
            for (BoolExp exp : set) {
                if(!hashes.add(exp.hashCode()))
                {
                    Set<BoolExp> boolExpSet =  new HashSet<>(set.size());
                    hashes.clear();
                    for (BoolExp ex : set) {
                        int hash = ex.hashCode();
                        if(!hashes.contains(hash))
                        {
                            hashes.add(hash);
                            boolExpSet.add(ex);
                        }
                    }


                    dirty = new Pair<>(this, boolExpSet);
                    if(debug)
                        System.out.println("delete duplicates");
                    return dirty;
                }
            }
        }

        if(false){
            /*
            * finds Constants that directly indicate what this or/and evaluates to
            * example {a && 0 && b} -> {0}
            */
            // search for true  if or
            //            false if and
            Constant item = new Constant(isOr);
            if (set.size() > 1 && set.contains(item))
            {
                // replace set
                dirty = new Pair<>(this, new HashSet<>());
                dirty.getValue().add(item);
                if(debug)
                    System.out.println("finds Constants that directly indicate what this or/and evaluates to");
                return dirty;
            }
        }


        {
            /*
            * remove Constants
            * but don't empty the set
            * example {a && 1 && b} -> {a && b}
            */
            // search for false if or
            //            true  if and
            if(set.size() > 1) {
                Constant item = new Constant(!isOr);
                if(set.contains(item))
                {

                    dirty = new Pair<>(this, set);
                    dirty.getValue().remove(item);
                    if(debug)
                        System.out.println("remove Constants");
                    return dirty;
                }
            }
        }

        {
            /*
            * integrate inner set if size == 1
            * {a && {b}} -> {a && b}
            * */
            boolean doit = false;
            for (BoolExp exp : set) {
                if(exp instanceof BoolSet)
                {
                    Set<BoolExp> boolSet = ((BoolSet) exp).getSet();
                    if(boolSet.size() == 1)
                    {
                        doit = true;
                        break;

                    }
                }
            }

            if(doit)
            {
                dirty = new Pair<>(this, new HashSet<>(set.size()));
                for (BoolExp e : set) {
                    if(!(e instanceof BoolSet && ((BoolSet) e).getSet().size() == 1))
                        dirty.getValue().add(e);
                    else
                    {
                        dirty.getValue().add(((BoolSet) e).getSet().iterator().next());
                    }
                }
                if(debug)
                    System.out.println("integrate inner set if size == 1");
                return dirty;
            }
        }

        if(false){
            /*
            * integrate inner set if type is same
            * {a && {b && c}} -> {a && b && c}
            * */
            for (BoolExp exp : set) {
                if(exp instanceof BoolSet)
                {
                    Set<BoolExp> boolSet = ((BoolSet) exp).getSet();
                    if(boolSet instanceof Or == isOr)
                    {
                        dirty = new Pair<>(this, set);
                        dirty.getValue().remove(exp);
                        dirty.getValue().addAll(boolSet);
                        if(debug)
                            System.out.println("integrate inner set if type is same");
                        return dirty;

                    }
                }
            }
        }


        {
            /*
            * replace A || ~A with 1
            * {a || ~a} -> {1}
            * {a && ~a} -> {0}
            * */

            for (BoolExp exp : set)
            {
                if(exp instanceof Not) {

                    BoolExp item = ((Not) exp).getExp();
                    if (set.contains(item)) {
                        dirty = new Pair<>(this, set);
                        dirty.getValue().remove(exp);
                        dirty.getValue().remove(item);
                        dirty.getValue().add(new Constant(isOr));
                        if(debug)
                            System.out.println("replace A || ~A with 1");
                        return dirty;
                    }
                }

            }

        }

        {
            /*
            * remove double nots
             * {~~A} -> {A}
            * */
            for (BoolExp exp : set)
            {
                if(exp instanceof Not && ((Not) exp).getExp() instanceof Not)
                {
                    dirty = new Pair<>(this, set);
                    dirty.getValue().remove(exp);
                    dirty.getValue().add(((Not)((Not) exp).getExp()).getExp());
                    if(debug)
                        System.out.println("remove double nots");
                    return dirty;
                }
            }
        }

        {
            /*
            * remove redundant expressions
            * {B || {A && ~B}} -> {B || {A}}
            * */
            BoolExp[] arr = set.toArray(new BoolExp[set.size()]);
            for (int i = 0; i < set.size(); i++) {
                for (int j = 0; j < set.size(); j++) {
                    if(arr[j] instanceof And)
                    {
                        BoolExp exp = arr[i];
                        Set<BoolExp> andSet = ((And)arr[j]).getSet();

                        // {B || {A && ~B}} -> {B || {A}}
                        BoolExp item = new Not(exp);
                        if(andSet.contains(item))
                        {
                            dirty = new Pair<>((And) arr[j], andSet);
                            dirty.getValue().remove(item);
                            if(debug)
                                System.out.println("remove redundant expressions");
                            return dirty;
                        }

                        // {~B || {A && B}} -> {~B || {A}}
                        if(arr[i] instanceof Not)
                        {
                            item = ((Not) exp).getExp();
                            if (andSet.contains(item))
                            {
                                dirty = new Pair<>((And) arr[j], andSet);
                                dirty.getValue().remove(item);
                                if (debug)
                                    System.out.println("remove redundant expressions");
                                return dirty;
                            }
                        }
                    }

                }
            }
        }

        if(isOr)
        {
            /*
            * find two sets and factor
            * then if or not that the overlap
            *      if and set 0
            * eg {{a && b && d}} || {c && b && d} -> {{b && d} && (a || c)}
            *
            * */

            BoolExp[] arr = set.toArray(new BoolExp[set.size()]);

            for (int i = 0; i < set.size(); i++) {
                for (int j = i + 1; j < set.size(); j++) {
                    if(arr[i] instanceof And && arr[j] instanceof And)
                    {
                        Set<BoolExp> set0 = ((And)arr[i]).getSet();
                        Set<BoolExp> set1 = ((And)arr[j]).getSet();

                        // compute over lap
                        Set<BoolExp> overlap = new HashSet<>();
                        for (BoolExp exp : set0)
                        {
                            if(set1.contains(exp))
                            {
                                overlap.add(exp);
                            }
                        }

                        Set<BoolExp> set0exclusive = new HashSet<>(set0);
                        Set<BoolExp> set1exclusive = new HashSet<>(set1);

                        for(BoolExp exp : overlap)
                        {
                            boolean set0contains = set0.contains(exp);
                            boolean set1contains = set1.contains(exp);
                            if(set0contains && set1contains)
                            {
                                set0exclusive.remove(exp);
                                set1exclusive.remove(exp);
                            }
                        }
                        boolean set0empty = set0exclusive.size() == 0;
                        boolean set1empty = set1exclusive.size() == 0;

                        if(!(set0empty && set1empty) && !overlap.isEmpty())
                        {
                            //remove old
                            removeItem(arr[i]);
                            removeItem(arr[j]);


                            if(debug)
                                System.out.println("find two sets and factor");

                            if(set0empty || set1empty)
                            {
                                // {{a && b && c} || {b && c}} -> {{b && c}}
                                dirty = new Pair<>(this, set);
                                dirty.getValue().add(new And(overlap));
                                return dirty;
                            }
                            else
                            {
                                // {{a && b && d}} || {c && b && d} -> {{b && d && (a || c)}}

                                Or or = new Or(new HashSet<>(2));
                                or.addItem(new And(set0exclusive));
                                or.addItem(new And(set1exclusive));

                                And and = new And(overlap);
                                and.addItem(or);

                                dirty = new Pair<>(this, set);
                                dirty.getValue().add(and);
                                return dirty;
                            }
                        }

                    }
                }
            }
        }

        return null;
    }
}
