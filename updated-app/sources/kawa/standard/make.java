package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.mapping.WrongType;
import kawa.lang.Record;

public class make extends ProcedureN {
    public int numArgs() {
        return -4095;
    }

    public Object applyN(Object[] args) {
        Class clas;
        int nargs = args.length;
        if (nargs != 0) {
            ClassType classType = args[0];
            if (classType instanceof Class) {
                clas = classType;
            } else if (classType instanceof ClassType) {
                clas = classType.getReflectClass();
            } else {
                clas = null;
            }
            if (clas != null) {
                try {
                    Object result = clas.newInstance();
                    int i = 1;
                    while (i < nargs) {
                        int i2 = i + 1;
                        Record.set1(args[i2], args[i].getName(), result);
                        i = i2 + 1;
                    }
                    return result;
                } catch (Exception ex) {
                    throw new WrappedException((Throwable) ex);
                }
            } else {
                throw new WrongType((Procedure) this, 1, (Object) classType, "class");
            }
        } else {
            throw new WrongArguments(this, nargs);
        }
    }
}
