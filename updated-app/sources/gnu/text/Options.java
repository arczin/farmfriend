package gnu.text;

import com.google.appinventor.components.runtime.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Options {
    public static final int BOOLEAN_OPTION = 1;
    public static final int STRING_OPTION = 2;
    public static final String UNKNOWN = "unknown option name";
    OptionInfo first;
    HashMap<String, OptionInfo> infoTable;
    OptionInfo last;
    Options previous;
    HashMap<String, Object> valueTable;

    public static final class OptionInfo {
        Object defaultValue;
        String documentation;
        String key;
        int kind;
        OptionInfo next;
    }

    public Options() {
    }

    public Options(Options previous2) {
        this.previous = previous2;
    }

    public OptionInfo add(String key, int kind, String documentation) {
        return add(key, kind, (Object) null, documentation);
    }

    public OptionInfo add(String key, int kind, Object defaultValue, String documentation) {
        if (this.infoTable == null) {
            this.infoTable = new HashMap<>();
        } else if (this.infoTable.get(key) != null) {
            throw new RuntimeException("duplicate option key: " + key);
        }
        OptionInfo info = new OptionInfo();
        info.key = key;
        info.kind = kind;
        info.defaultValue = defaultValue;
        info.documentation = documentation;
        if (this.first == null) {
            this.first = info;
        } else {
            this.last.next = info;
        }
        this.last = info;
        this.infoTable.put(key, info);
        return info;
    }

    static Object valueOf(OptionInfo info, String argument) {
        if ((info.kind & 1) == 0) {
            return argument;
        }
        if (argument == null || argument.equals(Component.TYPEFACE_SANSSERIF) || argument.equals("on") || argument.equals("yes") || argument.equals("true")) {
            return Boolean.TRUE;
        }
        if (argument.equals(Component.TYPEFACE_DEFAULT) || argument.equals("off") || argument.equals("no") || argument.equals("false")) {
            return Boolean.FALSE;
        }
        return null;
    }

    private void error(String message, SourceMessages messages) {
        if (messages != null) {
            messages.error('e', message);
            return;
        }
        throw new RuntimeException(message);
    }

    public void set(String key, Object value) {
        set(key, value, (SourceMessages) null);
    }

    public void set(String key, Object value, SourceMessages messages) {
        OptionInfo info = getInfo(key);
        if (info == null) {
            error("invalid option key: " + key, messages);
            return;
        }
        if ((info.kind & 1) != 0) {
            if (value instanceof String) {
                value = valueOf(info, (String) value);
            }
            if (!(value instanceof Boolean)) {
                error("value for option " + key + " must be boolean or yes/no/true/false/on/off/1/0", messages);
                return;
            }
        } else if (value == null) {
            value = "";
        }
        if (this.valueTable == null) {
            this.valueTable = new HashMap<>();
        }
        this.valueTable.put(key, value);
    }

    public void reset(String key, Object oldValue) {
        if (this.valueTable == null) {
            this.valueTable = new HashMap<>();
        }
        if (oldValue == null) {
            this.valueTable.remove(key);
        } else {
            this.valueTable.put(key, oldValue);
        }
    }

    public String set(String key, String argument) {
        OptionInfo info = getInfo(key);
        if (info == null) {
            return UNKNOWN;
        }
        Object value = valueOf(info, argument);
        if (value == null && (info.kind & 1) != 0) {
            return "value of option " + key + " must be yes/no/true/false/on/off/1/0";
        }
        if (this.valueTable == null) {
            this.valueTable = new HashMap<>();
        }
        this.valueTable.put(key, value);
        return null;
    }

    public OptionInfo getInfo(String key) {
        Object info = this.infoTable == null ? null : (OptionInfo) this.infoTable.get(key);
        if (info == null && this.previous != null) {
            info = this.previous.getInfo(key);
        }
        return (OptionInfo) info;
    }

    public Object get(String key, Object defaultValue) {
        OptionInfo info = getInfo(key);
        if (info != null) {
            return get(info, defaultValue);
        }
        throw new RuntimeException("invalid option key: " + key);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: gnu.text.Options$OptionInfo} */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
        if (r1.defaultValue == null) goto L_0x0027;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0025, code lost:
        r6 = r1.defaultValue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0027, code lost:
        r0 = r0.previous;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object get(gnu.text.Options.OptionInfo r5, java.lang.Object r6) {
        /*
            r4 = this;
            r0 = r4
        L_0x0001:
            if (r0 == 0) goto L_0x002a
            r1 = r5
        L_0x0004:
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r0.valueTable
            if (r2 != 0) goto L_0x000a
            r2 = 0
            goto L_0x0012
        L_0x000a:
            java.util.HashMap<java.lang.String, java.lang.Object> r2 = r0.valueTable
            java.lang.String r3 = r1.key
            java.lang.Object r2 = r2.get(r3)
        L_0x0012:
            if (r2 == 0) goto L_0x0015
            return r2
        L_0x0015:
            java.lang.Object r3 = r1.defaultValue
            boolean r3 = r3 instanceof gnu.text.Options.OptionInfo
            if (r3 == 0) goto L_0x0021
            java.lang.Object r3 = r1.defaultValue
            r1 = r3
            gnu.text.Options$OptionInfo r1 = (gnu.text.Options.OptionInfo) r1
            goto L_0x0004
        L_0x0021:
            java.lang.Object r3 = r1.defaultValue
            if (r3 == 0) goto L_0x0027
            java.lang.Object r6 = r1.defaultValue
        L_0x0027:
            gnu.text.Options r0 = r0.previous
            goto L_0x0001
        L_0x002a:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.Options.get(gnu.text.Options$OptionInfo, java.lang.Object):java.lang.Object");
    }

    public Object get(OptionInfo key) {
        return get(key, (Object) null);
    }

    public Object getLocal(String key) {
        if (this.valueTable == null) {
            return null;
        }
        return this.valueTable.get(key);
    }

    public boolean getBoolean(String key) {
        return ((Boolean) get(key, (Object) Boolean.FALSE)).booleanValue();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return ((Boolean) get(key, (Object) defaultValue ? Boolean.TRUE : Boolean.FALSE)).booleanValue();
    }

    public boolean getBoolean(OptionInfo key, boolean defaultValue) {
        return ((Boolean) get(key, (Object) defaultValue ? Boolean.TRUE : Boolean.FALSE)).booleanValue();
    }

    public boolean getBoolean(OptionInfo key) {
        Object value = get(key, (Object) null);
        if (value == null) {
            return false;
        }
        return ((Boolean) value).booleanValue();
    }

    public void pushOptionValues(Vector options) {
        int len = options.size();
        int i = 0;
        while (i < len) {
            int i2 = i + 1;
            int i3 = i2 + 1;
            options.setElementAt(options.elementAt(i2), i2);
            set((String) options.elementAt(i), options.elementAt(i3));
            i = i3 + 1;
        }
    }

    public void popOptionValues(Vector options) {
        int i = options.size();
        while (true) {
            i -= 3;
            if (i >= 0) {
                Object oldValue = options.elementAt(i + 1);
                options.setElementAt((Object) null, i + 1);
                reset((String) options.elementAt(i), oldValue);
            } else {
                return;
            }
        }
    }

    public ArrayList<String> keys() {
        ArrayList<String> allKeys = new ArrayList<>();
        for (Options options = this; options != null; options = options.previous) {
            if (options.infoTable != null) {
                for (String k : options.infoTable.keySet()) {
                    if (!allKeys.contains(k)) {
                        allKeys.add(k);
                    }
                }
            }
        }
        return allKeys;
    }

    public String getDoc(String key) {
        OptionInfo info = getInfo(key);
        if (key == null) {
            return null;
        }
        return info.documentation;
    }
}
