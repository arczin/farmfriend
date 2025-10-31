package gnu.text;

import gnu.bytecode.Access;
import gnu.lists.FString;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class URIPath extends Path implements Comparable<URIPath> {
    final URI uri;

    URIPath(URI uri2) {
        this.uri = uri2;
    }

    public static URIPath coerceToURIPathOrNull(Object path) {
        String str;
        if (path instanceof URIPath) {
            return (URIPath) path;
        }
        if (path instanceof URL) {
            return URLPath.valueOf((URL) path);
        }
        if (path instanceof URI) {
            return valueOf((URI) path);
        }
        if ((path instanceof File) || (path instanceof Path) || (path instanceof FString)) {
            str = path.toString();
        } else if (!(path instanceof String)) {
            return null;
        } else {
            str = (String) path;
        }
        return valueOf(str);
    }

    public static URIPath makeURI(Object arg) {
        URIPath path = coerceToURIPathOrNull(arg);
        if (path != null) {
            return path;
        }
        String str = null;
        throw new WrongType((String) null, -4, arg, "URI");
    }

    public static URIPath valueOf(URI uri2) {
        return new URIPath(uri2);
    }

    public static URIPath valueOf(String uri2) {
        try {
            return new URIStringPath(new URI(encodeForUri(uri2, Access.INNERCLASS_CONTEXT)), uri2);
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    public boolean isAbsolute() {
        return this.uri.isAbsolute();
    }

    public boolean exists() {
        try {
            URLConnection conn = toURL().openConnection();
            if (conn instanceof HttpURLConnection) {
                if (((HttpURLConnection) conn).getResponseCode() == 200) {
                    return true;
                }
                return false;
            } else if (conn.getLastModified() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable th) {
            return false;
        }
    }

    public long getLastModified() {
        return URLPath.getLastModified(toURL());
    }

    public long getContentLength() {
        return (long) URLPath.getContentLength(toURL());
    }

    public URI toUri() {
        return this.uri;
    }

    public String toURIString() {
        return this.uri.toString();
    }

    public Path resolve(String rstr) {
        if (Path.uriSchemeSpecified(rstr)) {
            return valueOf(rstr);
        }
        char fileSep = File.separatorChar;
        if (fileSep != '/') {
            if (rstr.length() >= 2 && ((rstr.charAt(1) == ':' && Character.isLetter(rstr.charAt(0))) || (rstr.charAt(0) == fileSep && rstr.charAt(1) == fileSep))) {
                return FilePath.valueOf(new File(rstr));
            }
            rstr = rstr.replace(fileSep, '/');
        }
        try {
            return valueOf(this.uri.resolve(new URI((String) null, rstr, (String) null)));
        } catch (Throwable ex) {
            throw WrappedException.wrapIfNeeded(ex);
        }
    }

    public int compareTo(URIPath path) {
        return this.uri.compareTo(path.uri);
    }

    public boolean equals(Object obj) {
        return (obj instanceof URIPath) && this.uri.equals(((URIPath) obj).uri);
    }

    public int hashCode() {
        return this.uri.hashCode();
    }

    public String toString() {
        return toURIString();
    }

    public URL toURL() {
        return Path.toURL(this.uri.toString());
    }

    public InputStream openInputStream() throws IOException {
        return URLPath.openInputStream(toURL());
    }

    public OutputStream openOutputStream() throws IOException {
        return URLPath.openOutputStream(toURL());
    }

    public String getScheme() {
        return this.uri.getScheme();
    }

    public String getHost() {
        return this.uri.getHost();
    }

    public String getAuthority() {
        return this.uri.getAuthority();
    }

    public String getUserInfo() {
        return this.uri.getUserInfo();
    }

    public int getPort() {
        return this.uri.getPort();
    }

    public String getPath() {
        return this.uri.getPath();
    }

    public String getQuery() {
        return this.uri.getQuery();
    }

    public String getFragment() {
        return this.uri.getFragment();
    }

    public Path getCanonical() {
        if (!isAbsolute()) {
            return getAbsolute().getCanonical();
        }
        URI norm = this.uri.normalize();
        if (norm == this.uri) {
            return this;
        }
        return valueOf(norm);
    }

    public static String encodeForUri(String str, char mode) {
        int i;
        int ch;
        int b;
        String str2 = str;
        char c = mode;
        StringBuffer sbuf = new StringBuffer();
        int len = str.length();
        for (int ch2 = 0; ch2 < len; ch2 = i) {
            i = ch2 + 1;
            int ch3 = str2.charAt(ch2);
            if (ch3 >= 55296 && ch3 < 56320 && i < len) {
                ch3 = ((ch3 - 55296) * 1024) + (str2.charAt(i) - 56320) + 65536;
                i++;
            }
            if (c != 'H' ? (ch < 97 || ch > 122) && ((ch < 65 || ch > 90) && !((ch >= 48 && ch <= 57) || ch == 45 || ch == 95 || ch == 46 || ch == 126 || (c == 'I' && (ch == 59 || ch == 47 || ch == 63 || ch == 58 || ch == 42 || ch == 39 || ch == 40 || ch == 41 || ch == 64 || ch == 38 || ch == 61 || ch == 43 || ch == 36 || ch == 44 || ch == 91 || ch == 93 || ch == 35 || ch == 33 || ch == 37)))) : ch < 32 || ch > 126) {
                int pos = sbuf.length();
                int nbytes = 0;
                int i2 = 128;
                int i3 = 1;
                if (ch >= 128) {
                    if (ch >= 2048) {
                        if (ch < 65536) {
                        }
                    }
                }
                while (true) {
                    if (ch < (i3 << (nbytes == 0 ? 7 : 6 - nbytes))) {
                        b = ch;
                        if (nbytes > 0) {
                            b |= (65408 >> nbytes) & 255;
                        }
                        ch = 0;
                    } else {
                        b = (ch & 63) | i2;
                        ch >>= 6;
                    }
                    nbytes++;
                    int j = 0;
                    while (j <= i3) {
                        int hex = b & 15;
                        sbuf.insert(pos, (char) (hex <= 9 ? hex + 48 : (hex - 10) + 65));
                        b >>= 4;
                        j++;
                        i3 = 1;
                    }
                    sbuf.insert(pos, '%');
                    if (ch == 0) {
                        break;
                    }
                    i2 = 128;
                    i3 = 1;
                }
            } else {
                sbuf.append((char) ch);
            }
        }
        return sbuf.toString();
    }
}
