package gnu.mapping;

public class SharedLocation extends NamedLocation {
    int timestamp;

    public SharedLocation(Symbol symbol, Object property, int timestamp2) {
        super(symbol, property);
        this.timestamp = timestamp2;
    }

    public final synchronized Object get(Object defaultValue) {
        return this.base != null ? this.base.get(defaultValue) : this.value == Location.UNBOUND ? defaultValue : this.value;
    }

    public synchronized boolean isBound() {
        return this.base != null ? this.base.isBound() : this.value != Location.UNBOUND;
    }

    public final synchronized void set(Object newValue) {
        if (this.base == null) {
            this.value = newValue;
        } else if (this.value == DIRECT_ON_SET) {
            this.base = null;
            this.value = newValue;
        } else if (this.base.isConstant()) {
            getEnvironment().put(getKeySymbol(), getKeyProperty(), newValue);
        } else {
            this.base.set(newValue);
        }
    }
}
