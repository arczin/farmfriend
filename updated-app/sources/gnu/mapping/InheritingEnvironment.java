package gnu.mapping;

public class InheritingEnvironment extends SimpleEnvironment {
    int baseTimestamp;
    Environment[] inherited;
    Namespace[] namespaceMap;
    int numInherited;
    Object[] propertyMap;

    public InheritingEnvironment(String name, Environment parent) {
        super(name);
        addParent(parent);
        if (parent instanceof SimpleEnvironment) {
            SimpleEnvironment simpleEnvironment = (SimpleEnvironment) parent;
            int i = simpleEnvironment.currentTimestamp + 1;
            simpleEnvironment.currentTimestamp = i;
            int timestamp = i;
            this.baseTimestamp = timestamp;
            this.currentTimestamp = timestamp;
        }
    }

    public final int getNumParents() {
        return this.numInherited;
    }

    public final Environment getParent(int index) {
        return this.inherited[index];
    }

    public void addParent(Environment env) {
        if (this.numInherited == 0) {
            this.inherited = new Environment[4];
        } else if (this.numInherited <= this.inherited.length) {
            Environment[] newInherited = new Environment[(this.numInherited * 2)];
            System.arraycopy(this.inherited, 0, newInherited, 0, this.numInherited);
            this.inherited = newInherited;
        }
        this.inherited[this.numInherited] = env;
        this.numInherited++;
    }

    public NamedLocation lookupInherited(Symbol name, Object property, int hash) {
        for (int i = 0; i < this.numInherited; i++) {
            Symbol sym = name;
            Object prop = property;
            if (this.namespaceMap != null && this.namespaceMap.length > i * 2) {
                Object srcNamespace = this.namespaceMap[i * 2];
                Object dstNamespace = this.namespaceMap[(i * 2) + 1];
                if (!(srcNamespace == null && dstNamespace == null)) {
                    if (name.getNamespace() != dstNamespace) {
                        continue;
                    } else {
                        sym = Symbol.make(srcNamespace, name.getName());
                    }
                }
            }
            if (this.propertyMap != null && this.propertyMap.length > i * 2) {
                Object srcProperty = this.propertyMap[i * 2];
                Object dstProperty = this.propertyMap[(i * 2) + 1];
                if (!(srcProperty == null && dstProperty == null)) {
                    if (property != dstProperty) {
                        continue;
                    } else {
                        prop = srcProperty;
                    }
                }
            }
            NamedLocation loc = this.inherited[i].lookup(sym, prop, hash);
            if (loc != null && loc.isBound() && (!(loc instanceof SharedLocation) || ((SharedLocation) loc).timestamp < this.baseTimestamp)) {
                return loc;
            }
        }
        return null;
    }

    public NamedLocation lookup(Symbol name, Object property, int hash) {
        NamedLocation loc = super.lookup(name, property, hash);
        if (loc == null || !loc.isBound()) {
            return lookupInherited(name, property, hash);
        }
        return loc;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0067, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0071, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0010, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized gnu.mapping.NamedLocation getLocation(gnu.mapping.Symbol r6, java.lang.Object r7, int r8, boolean r9) {
        /*
            r5 = this;
            monitor-enter(r5)
            gnu.mapping.NamedLocation r0 = r5.lookupDirect(r6, r7, r8)     // Catch:{ all -> 0x0072 }
            if (r0 == 0) goto L_0x0011
            if (r9 != 0) goto L_0x000f
            boolean r1 = r0.isBound()     // Catch:{ all -> 0x0072 }
            if (r1 == 0) goto L_0x0011
        L_0x000f:
            monitor-exit(r5)
            return r0
        L_0x0011:
            int r1 = r5.flags     // Catch:{ all -> 0x0072 }
            r1 = r1 & 32
            r2 = 1
            if (r1 == 0) goto L_0x0025
            if (r9 == 0) goto L_0x0025
            gnu.mapping.Environment[] r1 = r5.inherited     // Catch:{ all -> 0x0072 }
            r3 = 0
            r1 = r1[r3]     // Catch:{ all -> 0x0072 }
            gnu.mapping.NamedLocation r1 = r1.getLocation(r6, r7, r8, r2)     // Catch:{ all -> 0x0072 }
            r0 = r1
            goto L_0x002a
        L_0x0025:
            gnu.mapping.NamedLocation r1 = r5.lookupInherited(r6, r7, r8)     // Catch:{ all -> 0x0072 }
            r0 = r1
        L_0x002a:
            r1 = 0
            if (r0 == 0) goto L_0x006a
            if (r9 == 0) goto L_0x0068
            gnu.mapping.NamedLocation r3 = r5.addUnboundLocation(r6, r7, r8)     // Catch:{ all -> 0x0072 }
            int r4 = r5.flags     // Catch:{ all -> 0x0072 }
            r2 = r2 & r4
            if (r2 != 0) goto L_0x0041
            boolean r2 = r0.isBound()     // Catch:{ all -> 0x0072 }
            if (r2 == 0) goto L_0x0041
            r5.redefineError(r6, r7, r3)     // Catch:{ all -> 0x0072 }
        L_0x0041:
            r3.base = r0     // Catch:{ all -> 0x0072 }
            java.lang.Object r2 = r0.value     // Catch:{ all -> 0x0072 }
            java.lang.Object r4 = gnu.mapping.IndirectableLocation.INDIRECT_FLUIDS     // Catch:{ all -> 0x0072 }
            if (r2 != r4) goto L_0x004e
            java.lang.Object r1 = r0.value     // Catch:{ all -> 0x0072 }
            r3.value = r1     // Catch:{ all -> 0x0072 }
            goto L_0x005b
        L_0x004e:
            int r2 = r5.flags     // Catch:{ all -> 0x0072 }
            r2 = r2 & 16
            if (r2 == 0) goto L_0x0059
            java.lang.Object r1 = gnu.mapping.IndirectableLocation.DIRECT_ON_SET     // Catch:{ all -> 0x0072 }
            r3.value = r1     // Catch:{ all -> 0x0072 }
            goto L_0x005b
        L_0x0059:
            r3.value = r1     // Catch:{ all -> 0x0072 }
        L_0x005b:
            boolean r1 = r3 instanceof gnu.mapping.SharedLocation     // Catch:{ all -> 0x0072 }
            if (r1 == 0) goto L_0x0066
            r1 = r3
            gnu.mapping.SharedLocation r1 = (gnu.mapping.SharedLocation) r1     // Catch:{ all -> 0x0072 }
            int r2 = r5.baseTimestamp     // Catch:{ all -> 0x0072 }
            r1.timestamp = r2     // Catch:{ all -> 0x0072 }
        L_0x0066:
            monitor-exit(r5)
            return r3
        L_0x0068:
            monitor-exit(r5)
            return r0
        L_0x006a:
            if (r9 == 0) goto L_0x0070
            gnu.mapping.NamedLocation r1 = r5.addUnboundLocation(r6, r7, r8)     // Catch:{ all -> 0x0072 }
        L_0x0070:
            monitor-exit(r5)
            return r1
        L_0x0072:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.InheritingEnvironment.getLocation(gnu.mapping.Symbol, java.lang.Object, int, boolean):gnu.mapping.NamedLocation");
    }

    public LocationEnumeration enumerateAllLocations() {
        LocationEnumeration it = new LocationEnumeration(this.table, 1 << this.log2Size);
        it.env = this;
        if (this.inherited != null && this.inherited.length > 0) {
            it.inherited = this.inherited[0].enumerateAllLocations();
            it.index = 0;
        }
        return it;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0013, code lost:
        r6.prevLoc = null;
        r6.nextLoc = r6.inherited.nextLoc;
        r3 = r6.index + 1;
        r6.index = r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0024, code lost:
        if (r3 != r5.numInherited) goto L_0x0034;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0026, code lost:
        r6.inherited = null;
        r6.bindings = r5.table;
        r6.index = 1 << r5.log2Size;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasMoreElements(gnu.mapping.LocationEnumeration r6) {
        /*
            r5 = this;
            gnu.mapping.LocationEnumeration r0 = r6.inherited
            if (r0 == 0) goto L_0x0055
        L_0x0004:
            gnu.mapping.NamedLocation r0 = r6.nextLoc
        L_0x0006:
            gnu.mapping.LocationEnumeration r1 = r6.inherited
            r1.nextLoc = r0
            gnu.mapping.LocationEnumeration r1 = r6.inherited
            boolean r1 = r1.hasMoreElements()
            r2 = 1
            if (r1 != 0) goto L_0x0041
            r1 = 0
            r6.prevLoc = r1
            gnu.mapping.LocationEnumeration r3 = r6.inherited
            gnu.mapping.NamedLocation r3 = r3.nextLoc
            r6.nextLoc = r3
            int r3 = r6.index
            int r3 = r3 + r2
            r6.index = r3
            int r4 = r5.numInherited
            if (r3 != r4) goto L_0x0034
            r6.inherited = r1
            gnu.mapping.NamedLocation[] r0 = r5.table
            r6.bindings = r0
            int r0 = r5.log2Size
            int r0 = r2 << r0
            r6.index = r0
            goto L_0x0055
        L_0x0034:
            gnu.mapping.Environment[] r1 = r5.inherited
            int r2 = r6.index
            r1 = r1[r2]
            gnu.mapping.LocationEnumeration r1 = r1.enumerateAllLocations()
            r6.inherited = r1
            goto L_0x0004
        L_0x0041:
            gnu.mapping.LocationEnumeration r1 = r6.inherited
            gnu.mapping.NamedLocation r0 = r1.nextLoc
            gnu.mapping.Symbol r1 = r0.name
            java.lang.Object r3 = r0.property
            gnu.mapping.Location r1 = r5.lookup(r1, r3)
            if (r1 != r0) goto L_0x0052
            r6.nextLoc = r0
            return r2
        L_0x0052:
            gnu.mapping.NamedLocation r0 = r0.next
            goto L_0x0006
        L_0x0055:
            boolean r0 = super.hasMoreElements(r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.InheritingEnvironment.hasMoreElements(gnu.mapping.LocationEnumeration):boolean");
    }

    /* access modifiers changed from: protected */
    public void toStringBase(StringBuffer sbuf) {
        sbuf.append(" baseTs:");
        sbuf.append(this.baseTimestamp);
        for (int i = 0; i < this.numInherited; i++) {
            sbuf.append(" base:");
            sbuf.append(this.inherited[i].toStringVerbose());
        }
    }
}
