package gnu.kawa.lispexpr;

import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;

public class LispPackage extends Namespace {
    Namespace exported;
    NamespaceUse imported;
    NamespaceUse importing;
    LList shadowingSymbols = LList.Empty;

    public Symbol lookup(String name, int hash, boolean create) {
        Symbol sym = this.exported.lookup(name, hash, false);
        if (sym != null) {
            return sym;
        }
        Symbol sym2 = lookupInternal(name, hash);
        if (sym2 != null) {
            return sym2;
        }
        for (NamespaceUse used = this.imported; used != null; used = used.nextImported) {
            Symbol sym3 = lookup(name, hash, false);
            if (sym3 != null) {
                return sym3;
            }
        }
        if (create) {
            return add(new Symbol(this, name), hash);
        }
        return null;
    }

    public Symbol lookupPresent(String name, int hash, boolean intern) {
        Symbol sym = this.exported.lookup(name, hash, false);
        if (sym == null) {
            return super.lookup(name, hash, intern);
        }
        return sym;
    }

    public boolean isPresent(String name) {
        return lookupPresent(name, name.hashCode(), false) != null;
    }

    public boolean unintern(Symbol symbol) {
        String name = symbol.getName();
        int hash = name.hashCode();
        if (this.exported.lookup(name, hash, false) == symbol) {
            this.exported.remove(symbol);
        } else if (super.lookup(name, hash, false) != symbol) {
            return false;
        } else {
            super.remove(symbol);
        }
        symbol.setNamespace((Namespace) null);
        removeFromShadowingSymbols(symbol);
        return true;
    }

    private void addToShadowingSymbols(Symbol sym) {
        Object s = this.shadowingSymbols;
        while (s != LList.Empty) {
            Pair p = (Pair) s;
            if (p.getCar() != sym) {
                s = p.getCdr();
            } else {
                return;
            }
        }
        this.shadowingSymbols = new Pair(sym, this.shadowingSymbols);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: gnu.lists.AbstractSequence} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: gnu.lists.LList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: gnu.lists.AbstractSequence} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: gnu.lists.AbstractSequence} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean removeFromShadowingSymbols(gnu.mapping.Symbol r5) {
        /*
            r4 = this;
            r0 = 0
            gnu.lists.LList r1 = r4.shadowingSymbols
        L_0x0003:
            gnu.lists.LList r2 = gnu.lists.LList.Empty
            if (r1 == r2) goto L_0x0023
            r2 = r1
            gnu.lists.Pair r2 = (gnu.lists.Pair) r2
            java.lang.Object r1 = r2.getCdr()
            java.lang.Object r3 = r2.getCar()
            if (r3 != r5) goto L_0x0021
            if (r0 != 0) goto L_0x001c
            r3 = r1
            gnu.lists.LList r3 = (gnu.lists.LList) r3
            r4.shadowingSymbols = r3
            goto L_0x001f
        L_0x001c:
            r0.setCdr(r1)
        L_0x001f:
            r3 = 1
            return r3
        L_0x0021:
            r0 = r2
            goto L_0x0003
        L_0x0023:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.LispPackage.removeFromShadowingSymbols(gnu.mapping.Symbol):boolean");
    }

    public void shadow(String name) {
        addToShadowingSymbols(lookupPresent(name, name.hashCode(), true));
    }

    public void shadowingImport(Symbol symbol) {
        String name = symbol.getName();
        int hashCode = name.hashCode();
        Symbol old = lookupPresent(name, name.hashCode(), false);
        if (!(old == null || old == symbol)) {
            unintern(old);
        }
        addToShadowingSymbols(symbol);
    }
}
