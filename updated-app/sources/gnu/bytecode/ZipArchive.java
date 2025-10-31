package gnu.bytecode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchive {
    private static void usage() {
        System.err.println("zipfile [ptxq] archive [file ...]");
        System.exit(-1);
    }

    public static long copy(InputStream in, OutputStream out, byte[] buffer) throws IOException {
        long total = 0;
        while (true) {
            int count = in.read(buffer);
            if (count <= 0) {
                return total;
            }
            out.write(buffer, 0, count);
            total += (long) count;
        }
    }

    public static void copy(InputStream in, String name, byte[] buffer) throws IOException {
        File f = new File(name);
        String dir_name = f.getParent();
        if (dir_name != null) {
            File dir = new File(dir_name);
            if (!dir.exists()) {
                System.err.println("mkdirs:" + dir.mkdirs());
            }
        }
        if (name.charAt(name.length() - 1) != '/') {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
            copy(in, out, buffer);
            out.close();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            usage();
        }
        String command = args[0];
        String archive_name = args[1];
        try {
            if (!command.equals("t")) {
                if (!command.equals("p")) {
                    if (!command.equals("x")) {
                        if (command.equals("q")) {
                            ZipOutputStream zar = new ZipOutputStream(new FileOutputStream(archive_name));
                            int i = 2;
                            while (i < args.length) {
                                File in = new File(args[i]);
                                if (!in.exists()) {
                                    throw new IOException(args[i] + " - not found");
                                } else if (in.canRead()) {
                                    int size = (int) in.length();
                                    FileInputStream fin = new FileInputStream(in);
                                    byte[] contents = new byte[size];
                                    if (fin.read(contents) == size) {
                                        fin.close();
                                        ZipEntry ze = new ZipEntry(args[i]);
                                        ze.setSize((long) size);
                                        ze.setTime(in.lastModified());
                                        zar.putNextEntry(ze);
                                        zar.write(contents, 0, size);
                                        i++;
                                    } else {
                                        throw new IOException(args[i] + " - read error");
                                    }
                                } else {
                                    throw new IOException(args[i] + " - not readable");
                                }
                            }
                            zar.close();
                            return;
                        }
                        usage();
                        return;
                    }
                }
            }
            PrintStream out = System.out;
            byte[] buf = new byte[1024];
            if (args.length == 2) {
                ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(archive_name)));
                while (true) {
                    ZipEntry nextEntry = zin.getNextEntry();
                    ZipEntry zent = nextEntry;
                    if (nextEntry != null) {
                        String name = zent.getName();
                        if (command.equals("t")) {
                            out.print(name);
                            out.print(" size: ");
                            out.println(zent.getSize());
                        } else if (command.equals("p")) {
                            copy((InputStream) zin, (OutputStream) out, buf);
                        } else {
                            copy((InputStream) zin, name, buf);
                        }
                    } else {
                        return;
                    }
                }
            } else {
                ZipFile zar2 = new ZipFile(archive_name);
                for (int i2 = 2; i2 < args.length; i2++) {
                    String name2 = args[i2];
                    ZipEntry zent2 = zar2.getEntry(name2);
                    if (zent2 == null) {
                        System.err.println("zipfile " + archive_name + ":" + args[i2] + " - not found");
                        System.exit(-1);
                    } else if (command.equals("t")) {
                        out.print(name2);
                        out.print(" size: ");
                        out.println(zent2.getSize());
                    } else if (command.equals("p")) {
                        copy(zar2.getInputStream(zent2), (OutputStream) out, buf);
                    } else {
                        copy(zar2.getInputStream(zent2), name2, buf);
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("I/O Exception:  " + ex);
        }
    }
}
