package gnu.mapping;

import gnu.lists.LList;
import gnu.lists.Pair;

public class PropertyLocation extends Location {
    Pair pair;

    public final Object get(Object defaultValue) {
        return this.pair.getCar();
    }

    public boolean isBound() {
        return true;
    }

    public final void set(Object newValue) {
        this.pair.setCar(newValue);
    }

    public static Object getPropertyList(Object symbol, Environment env) {
        return env.get(Symbol.PLIST, symbol, LList.Empty);
    }

    public static Object getPropertyList(Object symbol) {
        return Environment.getCurrent().get(Symbol.PLIST, symbol, LList.Empty);
    }

    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void setPropertyList(java.lang.Object r10, java.lang.Object r11, gnu.mapping.Environment r12) {
        /*
            monitor-enter(r12)
            gnu.mapping.Symbol r0 = gnu.mapping.Symbol.PLIST     // Catch:{ all -> 0x0072 }
            gnu.mapping.Location r0 = r12.lookup(r0, r10)     // Catch:{ all -> 0x0072 }
            boolean r1 = r10 instanceof gnu.mapping.Symbol     // Catch:{ all -> 0x0072 }
            if (r1 == 0) goto L_0x006d
            r1 = r10
            gnu.mapping.Symbol r1 = (gnu.mapping.Symbol) r1     // Catch:{ all -> 0x0072 }
            gnu.lists.LList r2 = gnu.lists.LList.Empty     // Catch:{ all -> 0x0072 }
            java.lang.Object r2 = r0.get(r2)     // Catch:{ all -> 0x0072 }
            r3 = r2
        L_0x0015:
            boolean r4 = r3 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0072 }
            if (r4 != 0) goto L_0x0050
            r3 = r11
        L_0x001b:
            boolean r4 = r3 instanceof gnu.lists.Pair     // Catch:{ all -> 0x0072 }
            if (r4 != 0) goto L_0x0020
            goto L_0x006d
        L_0x0020:
            r4 = r3
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ all -> 0x0072 }
            java.lang.Object r5 = r4.getCar()     // Catch:{ all -> 0x0072 }
            gnu.mapping.Location r6 = r12.lookup(r1, r5)     // Catch:{ all -> 0x0072 }
            if (r6 == 0) goto L_0x003a
            gnu.mapping.Location r7 = r6.getBase()     // Catch:{ all -> 0x0072 }
            r6 = r7
            boolean r7 = r7 instanceof gnu.mapping.PropertyLocation     // Catch:{ all -> 0x0072 }
            if (r7 == 0) goto L_0x003a
            r7 = r6
            gnu.mapping.PropertyLocation r7 = (gnu.mapping.PropertyLocation) r7     // Catch:{ all -> 0x0072 }
            goto L_0x0042
        L_0x003a:
            gnu.mapping.PropertyLocation r7 = new gnu.mapping.PropertyLocation     // Catch:{ all -> 0x0072 }
            r7.<init>()     // Catch:{ all -> 0x0072 }
            r12.addLocation(r1, r5, r7)     // Catch:{ all -> 0x0072 }
        L_0x0042:
            java.lang.Object r8 = r4.getCdr()     // Catch:{ all -> 0x0072 }
            gnu.lists.Pair r8 = (gnu.lists.Pair) r8     // Catch:{ all -> 0x0072 }
            r7.pair = r8     // Catch:{ all -> 0x0072 }
            java.lang.Object r9 = r8.getCdr()     // Catch:{ all -> 0x0072 }
            r3 = r9
            goto L_0x001b
        L_0x0050:
            r4 = r3
            gnu.lists.Pair r4 = (gnu.lists.Pair) r4     // Catch:{ all -> 0x0072 }
            java.lang.Object r5 = r4.getCar()     // Catch:{ all -> 0x0072 }
            r6 = 0
            java.lang.Object r6 = plistGet(r11, r5, r6)     // Catch:{ all -> 0x0072 }
            if (r6 == 0) goto L_0x0061
            r12.remove(r1, r5)     // Catch:{ all -> 0x0072 }
        L_0x0061:
            java.lang.Object r6 = r4.getCdr()     // Catch:{ all -> 0x0072 }
            gnu.lists.Pair r6 = (gnu.lists.Pair) r6     // Catch:{ all -> 0x0072 }
            java.lang.Object r6 = r6.getCdr()     // Catch:{ all -> 0x0072 }
            r3 = r6
            goto L_0x0015
        L_0x006d:
            r0.set(r11)     // Catch:{ all -> 0x0072 }
            monitor-exit(r12)     // Catch:{ all -> 0x0072 }
            return
        L_0x0072:
            r0 = move-exception
            monitor-exit(r12)     // Catch:{ all -> 0x0072 }
            goto L_0x0076
        L_0x0075:
            throw r0
        L_0x0076:
            goto L_0x0075
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.PropertyLocation.setPropertyList(java.lang.Object, java.lang.Object, gnu.mapping.Environment):void");
    }

    public static void setPropertyList(Object symbol, Object plist) {
        setPropertyList(symbol, plist, Environment.getCurrent());
    }

    public static Object getProperty(Object symbol, Object property, Object defaultValue, Environment env) {
        if (!(symbol instanceof Symbol)) {
            if (!(symbol instanceof String)) {
                return plistGet(env.get(Symbol.PLIST, symbol, LList.Empty), property, defaultValue);
            }
            symbol = Namespace.getDefaultSymbol((String) symbol);
        }
        return env.get((Symbol) symbol, property, defaultValue);
    }

    public static Object getProperty(Object symbol, Object property, Object defaultValue) {
        return getProperty(symbol, property, defaultValue, Environment.getCurrent());
    }

    public static void putProperty(Object symbol, Object property, Object newValue, Environment env) {
        if (!(symbol instanceof Symbol)) {
            if (symbol instanceof String) {
                symbol = Namespace.getDefaultSymbol((String) symbol);
            } else {
                Location lloc = env.getLocation(Symbol.PLIST, symbol);
                lloc.set(plistPut(lloc.get(LList.Empty), property, newValue));
                return;
            }
        }
        Location loc = env.lookup((Symbol) symbol, property);
        if (loc != null) {
            Location base = loc.getBase();
            Location loc2 = base;
            if (base instanceof PropertyLocation) {
                ((PropertyLocation) loc2).set(newValue);
                return;
            }
        }
        Location lloc2 = env.getLocation(Symbol.PLIST, symbol);
        Pair pair2 = new Pair(newValue, lloc2.get(LList.Empty));
        lloc2.set(new Pair(property, pair2));
        PropertyLocation ploc = new PropertyLocation();
        ploc.pair = pair2;
        env.addLocation((Symbol) symbol, property, ploc);
    }

    public static void putProperty(Object symbol, Object property, Object newValue) {
        putProperty(symbol, property, newValue, Environment.getCurrent());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: gnu.lists.Pair} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean removeProperty(java.lang.Object r7, java.lang.Object r8, gnu.mapping.Environment r9) {
        /*
            gnu.mapping.Symbol r0 = gnu.mapping.Symbol.PLIST
            gnu.mapping.Location r0 = r9.lookup(r0, r7)
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            gnu.lists.LList r2 = gnu.lists.LList.Empty
            java.lang.Object r2 = r0.get(r2)
            boolean r3 = r2 instanceof gnu.lists.Pair
            if (r3 != 0) goto L_0x0015
            return r1
        L_0x0015:
            r3 = r2
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            r4 = 0
        L_0x0019:
            java.lang.Object r5 = r3.getCar()
            if (r5 != r8) goto L_0x0040
            java.lang.Object r1 = r3.getCdr()
            gnu.lists.Pair r1 = (gnu.lists.Pair) r1
            java.lang.Object r1 = r1.getCdr()
            if (r4 != 0) goto L_0x0031
            r2 = r1
            r0.set(r2)
            goto L_0x0034
        L_0x0031:
            r4.setCdr(r1)
        L_0x0034:
            boolean r5 = r7 instanceof gnu.mapping.Symbol
            if (r5 == 0) goto L_0x003e
            r5 = r7
            gnu.mapping.Symbol r5 = (gnu.mapping.Symbol) r5
            r9.remove(r5, r8)
        L_0x003e:
            r5 = 1
            return r5
        L_0x0040:
            java.lang.Object r5 = r3.getCdr()
            boolean r6 = r5 instanceof gnu.lists.Pair
            if (r6 != 0) goto L_0x0049
            return r1
        L_0x0049:
            r4 = r3
            r3 = r5
            gnu.lists.Pair r3 = (gnu.lists.Pair) r3
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.PropertyLocation.removeProperty(java.lang.Object, java.lang.Object, gnu.mapping.Environment):boolean");
    }

    public static boolean removeProperty(Object symbol, Object property) {
        return removeProperty(symbol, property, Environment.getCurrent());
    }

    public static Object plistGet(Object plist, Object prop, Object dfault) {
        while (plist instanceof Pair) {
            Pair pair2 = (Pair) plist;
            if (pair2.getCar() == prop) {
                return ((Pair) pair2.getCdr()).getCar();
            }
        }
        return dfault;
    }

    public static Object plistPut(Object plist, Object prop, Object value) {
        Object p = plist;
        while (p instanceof Pair) {
            Pair pair2 = (Pair) p;
            Pair next = (Pair) pair2.getCdr();
            if (pair2.getCar() == prop) {
                next.setCar(value);
                return plist;
            }
            p = next.getCdr();
        }
        return new Pair(prop, new Pair(value, plist));
    }

    public static Object plistRemove(Object plist, Object prop) {
        Pair prev = null;
        Object p = plist;
        while (p instanceof Pair) {
            Pair pair2 = (Pair) p;
            Pair next = (Pair) pair2.getCdr();
            p = next.getCdr();
            if (pair2.getCar() != prop) {
                prev = next;
            } else if (prev == null) {
                return p;
            } else {
                prev.setCdr(p);
                return plist;
            }
        }
        return plist;
    }
}
