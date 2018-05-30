package Bool;

/**
 * Created by Emil_2 on 24.05.2018.
 */
public class Constant implements BoolExp
{
    private boolean value;

    public Constant(boolean value)
    {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean contains(BoolExp exp) {
        return equals(exp);
    }

    @Override
    public String toString() {
        return value ? "1" : "0";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Constant && ((Constant)obj).getValue() == value;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }
}
