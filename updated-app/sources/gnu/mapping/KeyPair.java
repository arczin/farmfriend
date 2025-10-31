package gnu.mapping;

public class KeyPair implements EnvironmentKey {
    Symbol name;
    Object property;

    public KeyPair(Symbol name2, Object property2) {
        this.name = name2;
        this.property = property2;
    }

    public Symbol getKeySymbol() {
        return this.name;
    }

    public Object getKeyProperty() {
        return this.property;
    }

    public final boolean matches(EnvironmentKey key) {
        return Symbol.equals(key.getKeySymbol(), this.name) && key.getKeyProperty() == this.property;
    }

    public final boolean matches(Symbol symbol, Object property2) {
        return Symbol.equals(symbol, this.name) && property2 == this.property;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0022 A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof gnu.mapping.KeyPair
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = r5
            gnu.mapping.KeyPair r0 = (gnu.mapping.KeyPair) r0
            java.lang.Object r2 = r4.property
            java.lang.Object r3 = r0.property
            if (r2 != r3) goto L_0x0023
            gnu.mapping.Symbol r2 = r4.name
            if (r2 != 0) goto L_0x0018
            gnu.mapping.Symbol r2 = r0.name
            if (r2 != 0) goto L_0x0023
            goto L_0x0022
        L_0x0018:
            gnu.mapping.Symbol r2 = r4.name
            gnu.mapping.Symbol r3 = r0.name
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0023
        L_0x0022:
            r1 = 1
        L_0x0023:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.KeyPair.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return this.name.hashCode() ^ System.identityHashCode(this.property);
    }

    public String toString() {
        return "KeyPair[sym:" + this.name + " prop:" + this.property + "]";
    }
}
