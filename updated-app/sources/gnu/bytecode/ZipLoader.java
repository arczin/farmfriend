package gnu.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipLoader extends ClassLoader {
    private Vector<Object> loadedClasses;
    int size = 0;
    ZipFile zar;
    private String zipname;

    public ZipLoader(String name) throws IOException {
        this.zipname = name;
        this.zar = new ZipFile(name);
        Enumeration e = this.zar.entries();
        while (e.hasMoreElements()) {
            if (!((ZipEntry) e.nextElement()).isDirectory()) {
                this.size++;
            }
        }
        this.loadedClasses = new Vector<>(this.size);
    }

    public Class loadClass(String str, boolean z) throws ClassNotFoundException {
        Class<?> cls;
        int indexOf = this.loadedClasses.indexOf(str);
        boolean z2 = true;
        if (indexOf >= 0) {
            cls = (Class) this.loadedClasses.elementAt(indexOf + 1);
        } else if (this.zar == null && this.loadedClasses.size() == this.size * 2) {
            cls = Class.forName(str);
        } else {
            String str2 = str.replace('.', '/') + ".class";
            if (this.zar == null) {
                try {
                    this.zar = new ZipFile(this.zipname);
                } catch (IOException e) {
                    throw new ClassNotFoundException("IOException while loading " + str2 + " from ziparchive \"" + str + "\": " + e.toString());
                }
            } else {
                z2 = false;
            }
            ZipEntry entry = this.zar.getEntry(str2);
            if (entry == null) {
                if (z2) {
                    try {
                        close();
                    } catch (IOException e2) {
                        throw new RuntimeException("failed to close \"" + this.zipname + "\"");
                    }
                }
                cls = Class.forName(str);
            } else {
                try {
                    int size2 = (int) entry.getSize();
                    byte[] bArr = new byte[size2];
                    new DataInputStream(this.zar.getInputStream(entry)).readFully(bArr);
                    Class<?> defineClass = defineClass(str, bArr, 0, size2);
                    this.loadedClasses.addElement(str);
                    this.loadedClasses.addElement(defineClass);
                    if (this.size * 2 == this.loadedClasses.size()) {
                        close();
                    }
                    cls = defineClass;
                } catch (IOException e3) {
                    throw new ClassNotFoundException("IOException while loading " + str2 + " from ziparchive \"" + str + "\": " + e3.toString());
                }
            }
        }
        if (z) {
            resolveClass(cls);
        }
        return cls;
    }

    public Class loadAllClasses() throws IOException {
        Enumeration e = this.zar.entries();
        Class mainClass = null;
        while (e.hasMoreElements()) {
            ZipEntry member = (ZipEntry) e.nextElement();
            String name = member.getName().replace('/', '.');
            String name2 = name.substring(0, name.length() - "/class".length());
            int member_size = (int) member.getSize();
            byte[] bytes = new byte[member_size];
            new DataInputStream(this.zar.getInputStream(member)).readFully(bytes);
            Class clas = defineClass(name2, bytes, 0, member_size);
            if (mainClass == null) {
                mainClass = clas;
            }
            this.loadedClasses.addElement(name2);
            this.loadedClasses.addElement(clas);
        }
        close();
        return mainClass;
    }

    public void close() throws IOException {
        if (this.zar != null) {
            this.zar.close();
        }
        this.zar = null;
    }
}
