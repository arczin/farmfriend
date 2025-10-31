package kawa.standard;

import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import kawa.lang.GenericError;
import kawa.lang.NamedException;

public class throw_name extends ProcedureN {
    public static final throw_name throwName = new throw_name();

    public Object applyN(Object[] args) throws Throwable {
        if (args.length > 0) {
            Symbol symbol = args[0];
            if (symbol instanceof Throwable) {
                if (args.length == 1) {
                    prim_throw.throw_it(symbol);
                }
            } else if (symbol instanceof Symbol) {
                throw new NamedException(symbol, args);
            }
        }
        throw new GenericError("bad arguments to throw");
    }
}
