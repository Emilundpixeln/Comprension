import Compresion.*;
import Bool.*;
import static Bool.BoolEq.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Main {


    public static void main(String[] args)
    {
/*
        And in0 = new And();

        in0.addItem(new Variable(0));
        in0.addItem(new Variable(1));
        in0.addItem(new Variable(2));

        And in1 = new And();

        in1.addItem(new Not(new Variable(0)));
        in1.addItem(new Variable(1));
        in1.addItem(new Variable(2));


        BoolSet exp = new Or();
        exp.addItem(in0);
        exp.addItem(in1);

*/




/*
        And in0 = new And();

        in0.addItem(new Not(new Variable(0)));
        in0.addItem(new Not(new Variable(1)));


        And in1 = new And();

        in1.addItem(new Variable(0));
        in1.addItem(Not(new Variable(1)));

        And in2 = new And();

        in2.addItem(new Variable(0));
        in2.addItem(new Variable(1));
        in2.addItem(new Not(new Variable(2)));


        BoolSet exp = new Or();
        exp.addItem(in0);
        exp.addItem(in1);
        exp.addItem(in2);


        BoolExp exp = Or(
                And(
                        Not(Var(0)),
                        Not(Var(1))
                ),
                And(
                        Var(0),
                        Not(Var(1))
                ),
                And(
                        Var(0),
                        Var(1),
                        Not(Var(2))
                ));

        BoolExp exp2 = Or(
                And(
                        Not(Var(0)),
                        Not(Var(1)),
                        Not(Var(2))
                ),
                And(
                        Not(Var(0)),
                        (Var(1)),
                        (Var(2))
                ),
                And(
                        (Var(0)),
                        Not(Var(1)),
                        (Var(2))
                ),
                And(
                        (Var(0)),
                        (Var(1)),
                        Not(Var(2))
                ),
                And(
                        (Var(0)),
                        (Var(1)),
                        (Var(2))
                ));



        BoolEq eq = new BoolEq(exp2);
        System.out.println(eq);
        eq.simplify(25);
*/

        Scanner scanner = new Scanner(System.in);
        System.out.println("in vars");
        int vars = scanner.nextInt();

        byte[] vals = new byte[1 << vars];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = 3;
        }

        for (int i = 0; i < (1 << vars); i++) {
            vals[i] = 2;
            print(vals, vars);
            vals[i] = scanner.nextByte();
        }
        print(vals, vars);

        BoolSet or = new Or();
        for (int j = 0; j < vals.length; j++)
        {
            if(vals[j] == 1)
            {
                And and = new And();
                for (int i = 0; i < vars; i++) {
                    and.addItem(((j >> i) & 1) == 1 ? Var(i) : Not(Var(i)));
                }
                or.addItem(and);
            }
        }

        BoolEq eq = new BoolEq(or);
        System.out.println(eq);
        eq.simplify(25);

    }

    public static void print(byte[] vals, int width)
    {
        String s = "";
        for (int j = 0; j < width; j++) {
            s += (char)('A' + j) + " ";
        }
        System.out.println(s + "x");
        for (int i = 0; i < 1 << width; i++) {
            s = "";
            for (int j = 0; j < width; j++) {
                s += ((i >> (width - 1 - j)) & 1) == 1 ? "1 " : "0 ";
            }

            switch (vals[i])
            {
                case 0:
                    s += "0";
                    break;
                case 1:
                    s += "1";
                    break;
                case 2:
                    s += "_";
                    break;
                case 3:
                    s += "X";
                    break;
            }

            System.out.println(s);
        }

    }
}
