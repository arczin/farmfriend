package gnu.kawa.slib;

/* compiled from: conditions.scm */
public class condition extends RuntimeException {
    public Object type$Mnfield$Mnalist;

    /* compiled from: conditions.scm */
    public class Mntype {
        public Object all$Mnfields;
        public Object fields;
        public Object name;
        public Object supertype;

        public Mntype(Object name2, Object supertype2, Object fields2, Object all$Mnfields2) {
            this.name = name2;
            this.supertype = supertype2;
            this.fields = fields2;
            this.all$Mnfields = all$Mnfields2;
        }

        public String toString() {
            StringBuffer sbuf = new StringBuffer("#<condition-type ");
            sbuf.append(this.name);
            sbuf.append(">");
            return sbuf.toString();
        }
    }

    public condition(Object type$Mnfield$Mnalist2) {
        this.type$Mnfield$Mnalist = type$Mnfield$Mnalist2;
    }
}
