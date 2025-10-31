package kawa.standard;

import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongType;

public class append extends ProcedureN {
    public static final append append = new append();

    static {
        append.setName("append");
    }

    public Object applyN(Object[] args) {
        return append$V(args);
    }

    public static Object append$V(Object[] args) {
        int count = args.length;
        if (count == 0) {
            return LList.Empty;
        }
        Object result = args[count - 1];
        int i = count - 1;
        while (true) {
            i--;
            if (i < 0) {
                return result;
            }
            Object list = args[i];
            Object copy = null;
            Pair last = null;
            while (list instanceof Pair) {
                Pair list_pair = (Pair) list;
                Object new_pair = new Pair(list_pair.getCar(), (Object) null);
                if (last == null) {
                    copy = new_pair;
                } else {
                    last.setCdr(new_pair);
                }
                last = new_pair;
                list = list_pair.getCdr();
            }
            if (list != LList.Empty) {
                throw new WrongType((Procedure) append, i + 1, args[i], "list");
            } else if (last != null) {
                last.setCdr(result);
                result = copy;
            }
        }
    }
}
