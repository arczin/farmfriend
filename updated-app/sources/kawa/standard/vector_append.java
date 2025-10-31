package kawa.standard;

import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongType;

public class vector_append extends ProcedureN {
    public static final vector_append vectorAppend = new vector_append("vector-append");

    public vector_append(String name) {
        super(name);
    }

    public Object applyN(Object[] args) {
        return apply$V(args);
    }

    public static FVector apply$V(Object[] args) {
        int length = 0;
        int args_length = args.length;
        int i = args_length;
        while (true) {
            i--;
            if (i >= 0) {
                FVector fVector = args[i];
                if (fVector instanceof FVector) {
                    length += fVector.size();
                } else {
                    int n = LList.listLength(fVector, false);
                    if (n >= 0) {
                        length += n;
                    } else {
                        throw new WrongType((Procedure) vectorAppend, i, (Object) fVector, "list or vector");
                    }
                }
            } else {
                Object[] result = new Object[length];
                int position = 0;
                for (int i2 = 0; i2 < args_length; i2++) {
                    Object arg = args[i2];
                    if (arg instanceof FVector) {
                        FVector vec = (FVector) arg;
                        int vec_length = vec.size();
                        int j = 0;
                        while (j < vec_length) {
                            result[position] = vec.get(j);
                            j++;
                            position++;
                        }
                    } else if (arg instanceof Pair) {
                        while (arg != LList.Empty) {
                            Pair pair = (Pair) arg;
                            result[position] = pair.getCar();
                            arg = pair.getCdr();
                            position++;
                        }
                    }
                }
                return new FVector(result);
            }
        }
    }
}
