package com.google.appinventor.components.runtime.util;

import android.util.Log;
import androidx.core.location.LocationRequestCompat;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NanoHTTPD {
    public static final String HTTP_BADREQUEST = "400 Bad Request";
    public static final String HTTP_FORBIDDEN = "403 Forbidden";
    public static final String HTTP_INTERNALERROR = "500 Internal Server Error";
    public static final String HTTP_NOTFOUND = "404 Not Found";
    public static final String HTTP_NOTIMPLEMENTED = "501 Not Implemented";
    public static final String HTTP_NOTMODIFIED = "304 Not Modified";
    public static final String HTTP_OK = "200 OK";
    public static final String HTTP_PARTIALCONTENT = "206 Partial Content";
    public static final String HTTP_RANGE_NOT_SATISFIABLE = "416 Requested Range Not Satisfiable";
    public static final String HTTP_REDIRECT = "301 Moved Permanently";
    private static final String LICENCE = "Copyright (C) 2001,2005-2011 by Jarno Elonen <elonen@iki.fi>\nand Copyright (C) 2010 by Konstantinos Togias <info@ktogias.gr>\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions\nare met:\n\nRedistributions of source code must retain the above copyright notice,\nthis list of conditions and the following disclaimer. Redistributions in\nbinary form must reproduce the above copyright notice, this list of\nconditions and the following disclaimer in the documentation and/or other\nmaterials provided with the distribution. The name of the author may not\nbe used to endorse or promote products derived from this software without\nspecific prior written permission. \n \nTHIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR\nIMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\nOF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.\nIN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,\nINCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT\nNOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\nDATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\nTHEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\nOF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.";
    private static final String LOG_TAG = "AppInvHTTPD";
    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";
    public static final String MIME_HTML = "text/html";
    public static final String MIME_PLAINTEXT = "text/plain";
    public static final String MIME_XML = "text/xml";
    private static final int REPL_STACK_SIZE = 262144;
    /* access modifiers changed from: private */
    public static SimpleDateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
    protected static PrintStream myErr = System.err;
    protected static PrintStream myOut = System.out;
    /* access modifiers changed from: private */
    public static int theBufferSize = 16384;
    private static Hashtable theMimeTypes = new Hashtable();
    /* access modifiers changed from: private */
    public ThreadPoolExecutor myExecutor = new ThreadPoolExecutor(2, 10, 5, TimeUnit.SECONDS, new SynchronousQueue(), new myThreadFactory());
    private File myRootDir;
    /* access modifiers changed from: private */
    public final ServerSocket myServerSocket;
    private int myTcpPort;
    private Thread myThread;

    public Response serve(String uri, String method, Properties header, Properties parms, Properties files, Socket mySocket) {
        myOut.println(method + " '" + uri + "' ");
        Enumeration e = header.propertyNames();
        while (e.hasMoreElements()) {
            String value = (String) e.nextElement();
            myOut.println("  HDR: '" + value + "' = '" + header.getProperty(value) + "'");
        }
        Enumeration e2 = parms.propertyNames();
        while (e2.hasMoreElements()) {
            String value2 = (String) e2.nextElement();
            myOut.println("  PRM: '" + value2 + "' = '" + parms.getProperty(value2) + "'");
        }
        Enumeration e3 = files.propertyNames();
        while (e3.hasMoreElements()) {
            String value3 = (String) e3.nextElement();
            myOut.println("  UPLOADED: '" + value3 + "' = '" + files.getProperty(value3) + "'");
        }
        return serveFile(uri, header, this.myRootDir, true);
    }

    public class Response {
        public InputStream data;
        public Properties header;
        public String mimeType;
        public String status;

        public Response() {
            this.header = new Properties();
            this.status = NanoHTTPD.HTTP_OK;
        }

        public Response(String status2, String mimeType2, InputStream data2) {
            this.header = new Properties();
            this.status = status2;
            this.mimeType = mimeType2;
            this.data = data2;
        }

        public Response(String status2, String mimeType2, String txt) {
            this.header = new Properties();
            this.status = status2;
            this.mimeType = mimeType2;
            try {
                this.data = new ByteArrayInputStream(txt.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }
        }

        public void addHeader(String name, String value) {
            this.header.put(name, value);
        }
    }

    public NanoHTTPD(int port, File wwwroot) throws IOException {
        this.myTcpPort = port;
        this.myRootDir = wwwroot;
        this.myServerSocket = new ServerSocket(this.myTcpPort);
        this.myThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        new HTTPSession(NanoHTTPD.this.myServerSocket.accept());
                    } catch (IOException e) {
                        return;
                    }
                }
            }
        });
        this.myThread.setDaemon(true);
        this.myThread.start();
    }

    public void stop() {
        try {
            this.myServerSocket.close();
            this.myThread.join();
        } catch (IOException | InterruptedException e) {
        }
    }

    public static void main(String[] args) {
        myOut.println("NanoHTTPD 1.25 (C) 2001,2005-2011 Jarno Elonen and (C) 2010 Konstantinos Togias\n(Command line options: [-p port] [-d root-dir] [--licence])\n");
        int port = 80;
        File wwwroot = new File(".").getAbsoluteFile();
        int i = 0;
        while (true) {
            if (i < args.length) {
                if (args[i].equalsIgnoreCase("-p")) {
                    port = Integer.parseInt(args[i + 1]);
                } else if (args[i].equalsIgnoreCase("-d")) {
                    wwwroot = new File(args[i + 1]).getAbsoluteFile();
                } else if (args[i].toLowerCase().endsWith("licence")) {
                    myOut.println("Copyright (C) 2001,2005-2011 by Jarno Elonen <elonen@iki.fi>\nand Copyright (C) 2010 by Konstantinos Togias <info@ktogias.gr>\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions\nare met:\n\nRedistributions of source code must retain the above copyright notice,\nthis list of conditions and the following disclaimer. Redistributions in\nbinary form must reproduce the above copyright notice, this list of\nconditions and the following disclaimer in the documentation and/or other\nmaterials provided with the distribution. The name of the author may not\nbe used to endorse or promote products derived from this software without\nspecific prior written permission. \n \nTHIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR\nIMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\nOF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.\nIN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,\nINCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT\nNOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\nDATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\nTHEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\nOF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n");
                    break;
                }
                i++;
            }
        }
        try {
            new NanoHTTPD(port, wwwroot);
        } catch (IOException ioe) {
            myErr.println("Couldn't start server:\n" + ioe);
            System.exit(-1);
        }
        myOut.println("Now serving files in port " + port + " from \"" + wwwroot + "\"");
        myOut.println("Hit Enter to stop.\n");
        try {
            System.in.read();
        } catch (Throwable th) {
        }
    }

    private class myThreadFactory implements ThreadFactory {
        private myThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            Thread retval = new Thread(new ThreadGroup("biggerstack"), r, "HTTPD Session", 262144);
            retval.setDaemon(true);
            return retval;
        }
    }

    private class HTTPSession implements Runnable {
        private Socket mySocket;

        public HTTPSession(Socket s) {
            this.mySocket = s;
            Log.d(NanoHTTPD.LOG_TAG, "NanoHTTPD: getPoolSize() = " + NanoHTTPD.this.myExecutor.getPoolSize());
            NanoHTTPD.this.myExecutor.execute(this);
        }

        public void run() {
            byte[] buf;
            int rlen;
            OutputStream f;
            Properties files;
            int rlen2;
            Properties header;
            Properties files2;
            String method;
            String contentType;
            String postLine = "";
            try {
                InputStream is = this.mySocket.getInputStream();
                if (is != null && (rlen = is.read(buf, 0, 8192)) > 0) {
                    ByteArrayInputStream hbis = new ByteArrayInputStream(buf, 0, rlen);
                    BufferedReader hin = new BufferedReader(new InputStreamReader(hbis));
                    Properties pre = new Properties();
                    Properties parms = new Properties();
                    Properties header2 = new Properties();
                    Properties files3 = new Properties();
                    decodeHeader(hin, pre, parms, header2);
                    String method2 = pre.getProperty("method");
                    String method3 = pre.getProperty("uri");
                    long size = LocationRequestCompat.PASSIVE_INTERVAL;
                    String contentLength = header2.getProperty("content-length");
                    if (contentLength != null) {
                        try {
                            ByteArrayInputStream byteArrayInputStream = hbis;
                            size = (long) Integer.parseInt(contentLength);
                        } catch (NumberFormatException e) {
                            ByteArrayInputStream byteArrayInputStream2 = hbis;
                        }
                    } else {
                        ByteArrayInputStream byteArrayInputStream3 = hbis;
                    }
                    int splitbyte = 0;
                    boolean sbfound = false;
                    while (true) {
                        if (splitbyte >= rlen) {
                            break;
                        }
                        boolean sbfound2 = sbfound;
                        if (buf[splitbyte] == 13) {
                            splitbyte++;
                            if (buf[splitbyte] == 10) {
                                splitbyte++;
                                if (buf[splitbyte] == 13) {
                                    splitbyte++;
                                    if (buf[splitbyte] == 10) {
                                        sbfound = true;
                                        break;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                        splitbyte++;
                        sbfound = sbfound2;
                    }
                    int splitbyte2 = splitbyte + 1;
                    if (method2.equalsIgnoreCase("PUT")) {
                        BufferedReader bufferedReader = hin;
                        File tmpfile = File.createTempFile("upload", "bin");
                        tmpfile.deleteOnExit();
                        Properties properties = pre;
                        files3.put("content", tmpfile.getAbsolutePath());
                        f = new FileOutputStream(tmpfile);
                    } else {
                        Properties properties2 = pre;
                        f = new ByteArrayOutputStream();
                    }
                    if (splitbyte2 < rlen) {
                        f.write((buf = new byte[8192]), splitbyte2, rlen - splitbyte2);
                    }
                    if (splitbyte2 < rlen) {
                        rlen2 = rlen;
                        files = files3;
                        size -= (long) ((rlen - splitbyte2) + 1);
                    } else {
                        rlen2 = rlen;
                        files = files3;
                        if (!sbfound || size == LocationRequestCompat.PASSIVE_INTERVAL) {
                            size = 0;
                        }
                    }
                    int i = 512;
                    byte[] buf2 = new byte[512];
                    long size2 = size;
                    while (rlen2 >= 0 && size2 > 0) {
                        rlen2 = is.read(buf2, 0, i);
                        size2 -= (long) rlen2;
                        if (rlen2 > 0) {
                            f.write(buf2, 0, rlen2);
                        }
                        i = 512;
                    }
                    if (method2.equalsIgnoreCase("POST")) {
                        byte[] fbuf = ((ByteArrayOutputStream) f).toByteArray();
                        ByteArrayInputStream bin = new ByteArrayInputStream(fbuf);
                        int i2 = splitbyte2;
                        BufferedReader in = new BufferedReader(new InputStreamReader(bin));
                        String contentType2 = postLine;
                        String contentTypeHeader = header2.getProperty("content-type");
                        String method4 = method2;
                        ByteArrayInputStream bin2 = bin;
                        StringTokenizer st = new StringTokenizer(contentTypeHeader, "; ");
                        if (st.hasMoreTokens()) {
                            contentType = st.nextToken();
                        } else {
                            contentType = contentType2;
                        }
                        String contentType3 = contentTypeHeader;
                        if (contentType.equalsIgnoreCase("multipart/form-data")) {
                            if (!st.hasMoreTokens()) {
                                sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
                            }
                            String boundaryExp = st.nextToken();
                            StringTokenizer stringTokenizer = st;
                            String contentType4 = contentType;
                            StringTokenizer st2 = new StringTokenizer(boundaryExp, "=");
                            if (st2.countTokens() != 2) {
                                sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary syntax error. Usage: GET /example/file.html");
                            }
                            st2.nextToken();
                            String str = boundaryExp;
                            String method5 = method4;
                            boolean z = sbfound;
                            ByteArrayInputStream byteArrayInputStream4 = bin2;
                            String str2 = contentType4;
                            method = method5;
                            files2 = files;
                            byte[] bArr = fbuf;
                            header = header2;
                            decodeMultipartData(st2.nextToken(), fbuf, in, parms, files2);
                            StringTokenizer stringTokenizer2 = st2;
                        } else {
                            StringTokenizer st3 = st;
                            header = header2;
                            boolean z2 = sbfound;
                            files2 = files;
                            method = method4;
                            ByteArrayInputStream byteArrayInputStream5 = bin2;
                            String str3 = contentType;
                            byte[] bArr2 = fbuf;
                            char[] pbuf = new char[512];
                            for (int read = in.read(pbuf); read >= 0 && !postLine.endsWith("\r\n"); read = in.read(pbuf)) {
                                postLine = postLine + String.valueOf(pbuf, 0, read);
                            }
                            decodeParms(postLine.trim(), parms);
                            StringTokenizer stringTokenizer3 = st3;
                        }
                        in.close();
                    } else {
                        header = header2;
                        boolean z3 = sbfound;
                        files2 = files;
                        method = method2;
                        if (method.equalsIgnoreCase("PUT ")) {
                            f.close();
                        }
                    }
                    Response r = NanoHTTPD.this.serve(method3, method, header, parms, files2, this.mySocket);
                    if (r == null) {
                        sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: Serve() returned a null response.");
                    } else {
                        sendResponse(r.status, r.mimeType, r.header, r.data);
                    }
                    is.close();
                }
            } catch (IOException e2) {
                sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + e2.getMessage());
            } catch (InterruptedException e3) {
            } catch (Throwable th) {
            }
        }

        private void decodeHeader(BufferedReader in, Properties pre, Properties parms, Properties header) throws InterruptedException {
            String uri;
            try {
                String inLine = in.readLine();
                if (inLine != null) {
                    StringTokenizer st = new StringTokenizer(inLine);
                    if (!st.hasMoreTokens()) {
                        sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
                    }
                    pre.put("method", st.nextToken());
                    if (!st.hasMoreTokens()) {
                        sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
                    }
                    String uri2 = st.nextToken();
                    int qmi = uri2.indexOf(63);
                    if (qmi >= 0) {
                        decodeParms(uri2.substring(qmi + 1), parms);
                        uri = decodePercent(uri2.substring(0, qmi));
                    } else {
                        uri = decodePercent(uri2);
                    }
                    if (st.hasMoreTokens()) {
                        String line = in.readLine();
                        while (line != null && line.trim().length() > 0) {
                            int p = line.indexOf(58);
                            if (p >= 0) {
                                header.put(line.substring(0, p).trim().toLowerCase(), line.substring(p + 1).trim());
                            }
                            line = in.readLine();
                        }
                    }
                    pre.put("uri", uri);
                }
            } catch (IOException ioe) {
                sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:20:0x0067 A[Catch:{ IOException -> 0x0172 }] */
        /* JADX WARNING: Removed duplicated region for block: B:62:0x015f  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void decodeMultipartData(java.lang.String r18, byte[] r19, java.io.BufferedReader r20, java.util.Properties r21, java.util.Properties r22) throws java.lang.InterruptedException {
            /*
                r17 = this;
                r1 = r17
                r2 = r18
                r3 = r19
                java.lang.String r4 = "500 Internal Server Error"
                byte[] r0 = r18.getBytes()     // Catch:{ IOException -> 0x0172 }
                int[] r0 = r1.getBoundaryPositions(r3, r0)     // Catch:{ IOException -> 0x0172 }
                r5 = 1
                java.lang.String r6 = r20.readLine()     // Catch:{ IOException -> 0x0172 }
            L_0x0015:
                if (r6 == 0) goto L_0x016b
                int r7 = r6.indexOf(r2)     // Catch:{ IOException -> 0x0172 }
                java.lang.String r8 = "400 Bad Request"
                r9 = -1
                if (r7 != r9) goto L_0x0025
                java.lang.String r7 = "BAD REQUEST: Content type is multipart/form-data but next chunk does not start with boundary. Usage: GET /example/file.html"
                r1.sendError(r8, r7)     // Catch:{ IOException -> 0x0172 }
            L_0x0025:
                int r5 = r5 + 1
                java.util.Properties r7 = new java.util.Properties     // Catch:{ IOException -> 0x0172 }
                r7.<init>()     // Catch:{ IOException -> 0x0172 }
                java.lang.String r10 = r20.readLine()     // Catch:{ IOException -> 0x0172 }
                r6 = r10
            L_0x0031:
                r10 = 0
                if (r6 == 0) goto L_0x0065
                java.lang.String r11 = r6.trim()     // Catch:{ IOException -> 0x0172 }
                int r11 = r11.length()     // Catch:{ IOException -> 0x0172 }
                if (r11 <= 0) goto L_0x0065
                r11 = 58
                int r11 = r6.indexOf(r11)     // Catch:{ IOException -> 0x0172 }
                if (r11 == r9) goto L_0x005f
                java.lang.String r10 = r6.substring(r10, r11)     // Catch:{ IOException -> 0x0172 }
                java.lang.String r10 = r10.trim()     // Catch:{ IOException -> 0x0172 }
                java.lang.String r10 = r10.toLowerCase()     // Catch:{ IOException -> 0x0172 }
                int r12 = r11 + 1
                java.lang.String r12 = r6.substring(r12)     // Catch:{ IOException -> 0x0172 }
                java.lang.String r12 = r12.trim()     // Catch:{ IOException -> 0x0172 }
                r7.put(r10, r12)     // Catch:{ IOException -> 0x0172 }
            L_0x005f:
                java.lang.String r10 = r20.readLine()     // Catch:{ IOException -> 0x0172 }
                r6 = r10
                goto L_0x0031
            L_0x0065:
                if (r6 == 0) goto L_0x015f
                java.lang.String r11 = "content-disposition"
                java.lang.String r11 = r7.getProperty(r11)     // Catch:{ IOException -> 0x0172 }
                if (r11 != 0) goto L_0x0074
                java.lang.String r12 = "BAD REQUEST: Content type is multipart/form-data but no content-disposition info found. Usage: GET /example/file.html"
                r1.sendError(r8, r12)     // Catch:{ IOException -> 0x0172 }
            L_0x0074:
                java.util.StringTokenizer r8 = new java.util.StringTokenizer     // Catch:{ IOException -> 0x0172 }
                java.lang.String r12 = "; "
                r8.<init>(r11, r12)     // Catch:{ IOException -> 0x0172 }
                java.util.Properties r12 = new java.util.Properties     // Catch:{ IOException -> 0x0172 }
                r12.<init>()     // Catch:{ IOException -> 0x0172 }
            L_0x0080:
                boolean r13 = r8.hasMoreTokens()     // Catch:{ IOException -> 0x0172 }
                if (r13 == 0) goto L_0x00ad
                java.lang.String r13 = r8.nextToken()     // Catch:{ IOException -> 0x0172 }
                r14 = 61
                int r14 = r13.indexOf(r14)     // Catch:{ IOException -> 0x0172 }
                if (r14 == r9) goto L_0x00ab
                java.lang.String r15 = r13.substring(r10, r14)     // Catch:{ IOException -> 0x0172 }
                java.lang.String r15 = r15.trim()     // Catch:{ IOException -> 0x0172 }
                java.lang.String r15 = r15.toLowerCase()     // Catch:{ IOException -> 0x0172 }
                int r10 = r14 + 1
                java.lang.String r10 = r13.substring(r10)     // Catch:{ IOException -> 0x0172 }
                java.lang.String r10 = r10.trim()     // Catch:{ IOException -> 0x0172 }
                r12.put(r15, r10)     // Catch:{ IOException -> 0x0172 }
            L_0x00ab:
                r10 = 0
                goto L_0x0080
            L_0x00ad:
                java.lang.String r10 = "name"
                java.lang.String r10 = r12.getProperty(r10)     // Catch:{ IOException -> 0x0172 }
                int r13 = r10.length()     // Catch:{ IOException -> 0x0172 }
                r14 = 1
                int r13 = r13 - r14
                java.lang.String r13 = r10.substring(r14, r13)     // Catch:{ IOException -> 0x0172 }
                r10 = r13
                java.lang.String r13 = ""
                java.lang.String r15 = "content-type"
                java.lang.String r15 = r7.getProperty(r15)     // Catch:{ IOException -> 0x0172 }
                if (r15 != 0) goto L_0x0112
            L_0x00c8:
                if (r6 == 0) goto L_0x010d
                int r14 = r6.indexOf(r2)     // Catch:{ IOException -> 0x0172 }
                if (r14 != r9) goto L_0x010d
                java.lang.String r14 = r20.readLine()     // Catch:{ IOException -> 0x0172 }
                r6 = r14
                if (r6 == 0) goto L_0x010b
                int r14 = r6.indexOf(r2)     // Catch:{ IOException -> 0x0172 }
                if (r14 != r9) goto L_0x00f0
                java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0172 }
                r15.<init>()     // Catch:{ IOException -> 0x0172 }
                java.lang.StringBuilder r15 = r15.append(r13)     // Catch:{ IOException -> 0x0172 }
                java.lang.StringBuilder r15 = r15.append(r6)     // Catch:{ IOException -> 0x0172 }
                java.lang.String r15 = r15.toString()     // Catch:{ IOException -> 0x0172 }
                r13 = r15
                goto L_0x0109
            L_0x00f0:
                int r15 = r14 + -2
                r9 = 0
                java.lang.String r15 = r6.substring(r9, r15)     // Catch:{ IOException -> 0x0172 }
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0172 }
                r9.<init>()     // Catch:{ IOException -> 0x0172 }
                java.lang.StringBuilder r9 = r9.append(r13)     // Catch:{ IOException -> 0x0172 }
                java.lang.StringBuilder r9 = r9.append(r15)     // Catch:{ IOException -> 0x0172 }
                java.lang.String r9 = r9.toString()     // Catch:{ IOException -> 0x0172 }
                r13 = r9
            L_0x0109:
                r9 = -1
                goto L_0x00c8
            L_0x010b:
                r9 = -1
                goto L_0x00c8
            L_0x010d:
                r14 = r22
                r16 = r0
                goto L_0x0153
            L_0x0112:
                int r9 = r0.length     // Catch:{ IOException -> 0x0172 }
                if (r5 <= r9) goto L_0x011a
                java.lang.String r9 = "Error processing request"
                r1.sendError(r4, r9)     // Catch:{ IOException -> 0x0172 }
            L_0x011a:
                int r9 = r5 + -2
                r9 = r0[r9]     // Catch:{ IOException -> 0x0172 }
                int r9 = r1.stripMultipartHeaders(r3, r9)     // Catch:{ IOException -> 0x0172 }
                int r15 = r5 + -1
                r15 = r0[r15]     // Catch:{ IOException -> 0x0172 }
                int r15 = r15 - r9
                int r15 = r15 + -4
                java.lang.String r15 = r1.saveTmpFile(r3, r9, r15)     // Catch:{ IOException -> 0x0172 }
                r14 = r22
                r14.put(r10, r15)     // Catch:{ IOException -> 0x015b }
                r16 = r0
                java.lang.String r0 = "filename"
                java.lang.String r0 = r12.getProperty(r0)     // Catch:{ IOException -> 0x015b }
                int r13 = r0.length()     // Catch:{ IOException -> 0x015b }
                r3 = 1
                int r13 = r13 - r3
                java.lang.String r3 = r0.substring(r3, r13)     // Catch:{ IOException -> 0x015b }
                r13 = r3
            L_0x0145:
                java.lang.String r0 = r20.readLine()     // Catch:{ IOException -> 0x015b }
                r6 = r0
                if (r6 == 0) goto L_0x0153
                int r0 = r6.indexOf(r2)     // Catch:{ IOException -> 0x015b }
                r3 = -1
                if (r0 == r3) goto L_0x0145
            L_0x0153:
                r3 = r21
                r3.put(r10, r13)     // Catch:{ IOException -> 0x0159 }
                goto L_0x0165
            L_0x0159:
                r0 = move-exception
                goto L_0x0177
            L_0x015b:
                r0 = move-exception
                r3 = r21
                goto L_0x0177
            L_0x015f:
                r3 = r21
                r14 = r22
                r16 = r0
            L_0x0165:
                r3 = r19
                r0 = r16
                goto L_0x0015
            L_0x016b:
                r3 = r21
                r14 = r22
                r16 = r0
                goto L_0x0191
            L_0x0172:
                r0 = move-exception
                r3 = r21
                r14 = r22
            L_0x0177:
                java.lang.String r5 = r0.getMessage()
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r7 = "SERVER INTERNAL ERROR: IOException: "
                java.lang.StringBuilder r6 = r6.append(r7)
                java.lang.StringBuilder r5 = r6.append(r5)
                java.lang.String r5 = r5.toString()
                r1.sendError(r4, r5)
            L_0x0191:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.NanoHTTPD.HTTPSession.decodeMultipartData(java.lang.String, byte[], java.io.BufferedReader, java.util.Properties, java.util.Properties):void");
        }

        public int[] getBoundaryPositions(byte[] b, byte[] boundary) {
            int matchcount = 0;
            int matchbyte = -1;
            Vector matchbytes = new Vector();
            int i = 0;
            while (i < b.length) {
                if (b[i] == boundary[matchcount]) {
                    if (matchcount == 0) {
                        matchbyte = i;
                    }
                    matchcount++;
                    if (matchcount == boundary.length) {
                        matchbytes.addElement(new Integer(matchbyte));
                        matchcount = 0;
                        matchbyte = -1;
                    }
                } else {
                    i -= matchcount;
                    matchcount = 0;
                    matchbyte = -1;
                }
                i++;
            }
            int[] ret = new int[matchbytes.size()];
            for (int i2 = 0; i2 < ret.length; i2++) {
                ret[i2] = ((Integer) matchbytes.elementAt(i2)).intValue();
            }
            return ret;
        }

        private String saveTmpFile(byte[] b, int offset, int len) {
            if (len <= 0) {
                return "";
            }
            try {
                File temp = File.createTempFile("NanoHTTPD", "", new File(System.getProperty("java.io.tmpdir")));
                OutputStream fstream = new FileOutputStream(temp);
                fstream.write(b, offset, len);
                fstream.close();
                return temp.getAbsolutePath();
            } catch (Exception e) {
                NanoHTTPD.myErr.println("Error: " + e.getMessage());
                return "";
            }
        }

        private int stripMultipartHeaders(byte[] b, int offset) {
            int i = offset;
            while (i < b.length) {
                if (b[i] == 13) {
                    i++;
                    if (b[i] == 10) {
                        i++;
                        if (b[i] == 13) {
                            i++;
                            if (b[i] == 10) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                i++;
            }
            return i + 1;
        }

        private String decodePercent(String str) throws InterruptedException {
            try {
                StringBuffer sb = new StringBuffer();
                int i = 0;
                while (i < str.length()) {
                    char c = str.charAt(i);
                    switch (c) {
                        case '%':
                            sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                            i += 2;
                            break;
                        case '+':
                            sb.append(' ');
                            break;
                        default:
                            sb.append(c);
                            break;
                    }
                    i++;
                }
                return sb.toString();
            } catch (Exception e) {
                sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Bad percent-encoding.");
                return null;
            }
        }

        private void decodeParms(String parms, Properties p) throws InterruptedException {
            if (parms != null) {
                StringTokenizer st = new StringTokenizer(parms, "&");
                while (st.hasMoreTokens()) {
                    String e = st.nextToken();
                    int sep = e.indexOf(61);
                    if (sep >= 0) {
                        p.put(decodePercent(e.substring(0, sep)).trim(), decodePercent(e.substring(sep + 1)));
                    }
                }
            }
        }

        private void sendError(String status, String msg) throws InterruptedException {
            sendResponse(status, NanoHTTPD.MIME_PLAINTEXT, (Properties) null, new ByteArrayInputStream(msg.getBytes()));
            throw new InterruptedException();
        }

        private void sendResponse(String status, String mime, Properties header, InputStream data) {
            if (status != null) {
                try {
                    OutputStream out = this.mySocket.getOutputStream();
                    PrintWriter pw = new PrintWriter(out);
                    pw.print("HTTP/1.0 " + status + " \r\n");
                    if (mime != null) {
                        pw.print("Content-Type: " + mime + "\r\n");
                    }
                    if (header == null || header.getProperty("Date") == null) {
                        pw.print("Date: " + NanoHTTPD.gmtFrmt.format(new Date()) + "\r\n");
                    }
                    if (header != null) {
                        Enumeration e = header.keys();
                        while (e.hasMoreElements()) {
                            String key = (String) e.nextElement();
                            pw.print(key + ": " + header.getProperty(key) + "\r\n");
                        }
                    }
                    pw.print("\r\n");
                    pw.flush();
                    if (data != null) {
                        int pending = data.available();
                        byte[] buff = new byte[NanoHTTPD.theBufferSize];
                        while (true) {
                            if (pending <= 0) {
                                break;
                            }
                            int read = data.read(buff, 0, pending > NanoHTTPD.theBufferSize ? NanoHTTPD.theBufferSize : pending);
                            if (read <= 0) {
                                break;
                            }
                            out.write(buff, 0, read);
                            pending -= read;
                        }
                    }
                    out.flush();
                    out.close();
                    if (data != null) {
                        data.close();
                    }
                } catch (IOException e2) {
                    this.mySocket.close();
                } catch (Throwable th) {
                }
            } else {
                throw new Error("sendResponse(): Status can't be null.");
            }
        }
    }

    private String encodeUri(String uri) {
        String newUri = "";
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("/")) {
                newUri = newUri + "/";
            } else if (tok.equals(" ")) {
                newUri = newUri + "%20";
            } else {
                newUri = newUri + URLEncoder.encode(tok);
            }
        }
        return newUri;
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x01aa  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x034c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.appinventor.components.runtime.util.NanoHTTPD.Response serveFile(java.lang.String r33, java.util.Properties r34, java.io.File r35, boolean r36) {
        /*
            r32 = this;
            r1 = r32
            r2 = r34
            r3 = r35
            java.lang.String r0 = "bytes="
            r4 = 0
            boolean r5 = r35.isDirectory()
            java.lang.String r6 = "text/plain"
            if (r5 != 0) goto L_0x001b
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r5 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r7 = "500 Internal Server Error"
            java.lang.String r8 = "INTERNAL ERRROR: serveFile(): given homeDir is not a directory."
            r5.<init>((java.lang.String) r7, (java.lang.String) r6, (java.lang.String) r8)
            r4 = r5
        L_0x001b:
            r5 = 47
            java.lang.String r7 = "403 Forbidden"
            r8 = 0
            if (r4 != 0) goto L_0x005b
            java.lang.String r9 = r33.trim()
            char r10 = java.io.File.separatorChar
            java.lang.String r9 = r9.replace(r10, r5)
            r10 = 63
            int r11 = r9.indexOf(r10)
            if (r11 < 0) goto L_0x003c
            int r10 = r9.indexOf(r10)
            java.lang.String r9 = r9.substring(r8, r10)
        L_0x003c:
            java.lang.String r10 = ".."
            boolean r11 = r9.startsWith(r10)
            if (r11 != 0) goto L_0x0052
            boolean r10 = r9.endsWith(r10)
            if (r10 != 0) goto L_0x0052
            java.lang.String r10 = "../"
            int r10 = r9.indexOf(r10)
            if (r10 < 0) goto L_0x005d
        L_0x0052:
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r10 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r11 = "FORBIDDEN: Won't serve ../ for security reasons."
            r10.<init>((java.lang.String) r7, (java.lang.String) r6, (java.lang.String) r11)
            r4 = r10
            goto L_0x005d
        L_0x005b:
            r9 = r33
        L_0x005d:
            java.io.File r10 = new java.io.File
            r10.<init>(r3, r9)
            if (r4 != 0) goto L_0x0074
            boolean r11 = r10.exists()
            if (r11 != 0) goto L_0x0074
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r11 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r12 = "404 Not Found"
            java.lang.String r13 = "Error 404, file not found."
            r11.<init>((java.lang.String) r12, (java.lang.String) r6, (java.lang.String) r13)
            r4 = r11
        L_0x0074:
            java.lang.String r11 = "200 OK"
            java.lang.String r12 = "/"
            if (r4 != 0) goto L_0x03a7
            boolean r13 = r10.isDirectory()
            if (r13 == 0) goto L_0x03a7
            boolean r13 = r9.endsWith(r12)
            java.lang.String r14 = "\">"
            java.lang.String r15 = "text/html"
            if (r13 != 0) goto L_0x00c9
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            java.lang.StringBuilder r13 = r13.append(r9)
            java.lang.StringBuilder r13 = r13.append(r12)
            java.lang.String r9 = r13.toString()
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r13 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r8 = "<html><body>Redirected: <a href=\""
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.StringBuilder r5 = r5.append(r9)
            java.lang.StringBuilder r5 = r5.append(r14)
            java.lang.StringBuilder r5 = r5.append(r9)
            java.lang.String r8 = "</a></body></html>"
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            java.lang.String r8 = "301 Moved Permanently"
            r13.<init>((java.lang.String) r8, (java.lang.String) r15, (java.lang.String) r5)
            r4 = r13
            java.lang.String r5 = "Location"
            r4.addHeader(r5, r9)
        L_0x00c9:
            if (r4 != 0) goto L_0x039a
            java.io.File r5 = new java.io.File
            java.lang.String r8 = "index.html"
            r5.<init>(r10, r8)
            boolean r5 = r5.exists()
            if (r5 == 0) goto L_0x00f9
            java.io.File r5 = new java.io.File
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r13 = "/index.html"
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.String r8 = r8.toString()
            r5.<init>(r3, r8)
            r10 = r5
            r31 = r0
            r3 = r1
            r5 = r7
            r19 = r12
            goto L_0x03b1
        L_0x00f9:
            java.io.File r5 = new java.io.File
            java.lang.String r8 = "index.htm"
            r5.<init>(r10, r8)
            boolean r5 = r5.exists()
            if (r5 == 0) goto L_0x0127
            java.io.File r5 = new java.io.File
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r13 = "/index.htm"
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.String r8 = r8.toString()
            r5.<init>(r3, r8)
            r10 = r5
            r31 = r0
            r3 = r1
            r5 = r7
            r19 = r12
            goto L_0x03b1
        L_0x0127:
            if (r36 == 0) goto L_0x037c
            boolean r5 = r10.canRead()
            if (r5 == 0) goto L_0x037c
            java.lang.String[] r5 = r10.list()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r13 = "<html><body><h1>Directory "
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r13 = "</h1><br/>"
            java.lang.StringBuilder r8 = r8.append(r13)
            java.lang.String r8 = r8.toString()
            int r13 = r9.length()
            r3 = 1
            if (r13 <= r3) goto L_0x01a2
            int r13 = r9.length()
            int r13 = r13 - r3
            r3 = 0
            java.lang.String r13 = r9.substring(r3, r13)
            r3 = 47
            int r3 = r13.lastIndexOf(r3)
            if (r3 < 0) goto L_0x019b
            r33 = r4
            int r4 = r13.length()
            if (r3 >= r4) goto L_0x0196
            int r4 = r3 + 1
            r16 = r3
            r3 = 0
            java.lang.String r4 = r9.substring(r3, r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r8)
            r18 = r8
            java.lang.String r8 = "<b><a href=\""
            java.lang.StringBuilder r3 = r3.append(r8)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = "\">..</a></b><br/>"
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r8 = r3.toString()
            goto L_0x01a8
        L_0x0196:
            r16 = r3
            r18 = r8
            goto L_0x01a6
        L_0x019b:
            r16 = r3
            r33 = r4
            r18 = r8
            goto L_0x01a6
        L_0x01a2:
            r33 = r4
            r18 = r8
        L_0x01a6:
            r8 = r18
        L_0x01a8:
            if (r5 == 0) goto L_0x034c
            r3 = 0
        L_0x01ab:
            int r4 = r5.length
            if (r3 >= r4) goto L_0x033c
            java.io.File r4 = new java.io.File
            r13 = r5[r3]
            r4.<init>(r10, r13)
            boolean r13 = r4.isDirectory()
            if (r13 == 0) goto L_0x01ec
            r16 = r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r10 = r10.append(r8)
            r18 = r8
            java.lang.String r8 = "<b>"
            java.lang.StringBuilder r8 = r10.append(r8)
            java.lang.String r8 = r8.toString()
            r10 = r5[r3]
            r18 = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.StringBuilder r8 = r8.append(r10)
            java.lang.StringBuilder r8 = r8.append(r12)
            java.lang.String r8 = r8.toString()
            r5[r3] = r8
            r8 = r18
            goto L_0x01f0
        L_0x01ec:
            r18 = r8
            r16 = r10
        L_0x01f0:
            r10 = r5[r3]
            r19 = r12
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.StringBuilder r12 = r12.append(r9)
            java.lang.StringBuilder r10 = r12.append(r10)
            java.lang.String r10 = r10.toString()
            java.lang.String r10 = r1.encodeUri(r10)
            r12 = r5[r3]
            r20 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r8)
            r18 = r8
            java.lang.String r8 = "<a href=\""
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.StringBuilder r5 = r5.append(r10)
            java.lang.StringBuilder r5 = r5.append(r14)
            java.lang.StringBuilder r5 = r5.append(r12)
            java.lang.String r8 = "</a>"
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r5 = r5.toString()
            boolean r8 = r4.isFile()
            if (r8 == 0) goto L_0x02f7
            r10 = r9
            long r8 = r4.length()
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.StringBuilder r12 = r12.append(r5)
            r21 = r4
            java.lang.String r4 = " &nbsp;<font size=2>("
            java.lang.StringBuilder r4 = r12.append(r4)
            java.lang.String r4 = r4.toString()
            r22 = 1024(0x400, double:5.06E-321)
            int r5 = (r8 > r22 ? 1 : (r8 == r22 ? 0 : -1))
            if (r5 >= 0) goto L_0x0279
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.StringBuilder r5 = r5.append(r4)
            java.lang.StringBuilder r5 = r5.append(r8)
            java.lang.String r12 = " bytes"
            java.lang.StringBuilder r5 = r5.append(r12)
            java.lang.String r4 = r5.toString()
            r31 = r0
            r12 = r6
            r30 = r7
            r22 = r10
            goto L_0x02e3
        L_0x0279:
            java.lang.String r5 = "."
            r24 = 100
            r26 = 10
            r28 = 1048576(0x100000, double:5.180654E-318)
            int r12 = (r8 > r28 ? 1 : (r8 == r28 ? 0 : -1))
            if (r12 >= 0) goto L_0x02b5
            r12 = r6
            r30 = r7
            long r6 = r8 / r22
            long r22 = r8 % r22
            long r22 = r22 / r26
            r31 = r0
            long r0 = r22 % r24
            r22 = r10
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r10 = r10.append(r4)
            java.lang.StringBuilder r6 = r10.append(r6)
            java.lang.StringBuilder r5 = r6.append(r5)
            java.lang.StringBuilder r0 = r5.append(r0)
            java.lang.String r1 = " KB"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r4 = r0.toString()
            goto L_0x02e3
        L_0x02b5:
            r31 = r0
            r12 = r6
            r30 = r7
            r22 = r10
            long r0 = r8 / r28
            long r6 = r8 % r28
            long r6 = r6 / r26
            long r6 = r6 % r24
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.StringBuilder r10 = r10.append(r4)
            java.lang.StringBuilder r0 = r10.append(r0)
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.StringBuilder r0 = r0.append(r6)
            java.lang.String r1 = " MB"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r4 = r0.toString()
        L_0x02e3:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r1 = ")</font>"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r5 = r0.toString()
            goto L_0x0300
        L_0x02f7:
            r31 = r0
            r21 = r4
            r12 = r6
            r30 = r7
            r22 = r9
        L_0x0300:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r5)
            java.lang.String r1 = "<br/>"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            if (r13 == 0) goto L_0x0328
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r0)
            java.lang.String r4 = "</b>"
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r0 = r1.toString()
        L_0x0328:
            r8 = r0
            int r3 = r3 + 1
            r1 = r32
            r6 = r12
            r10 = r16
            r12 = r19
            r5 = r20
            r9 = r22
            r7 = r30
            r0 = r31
            goto L_0x01ab
        L_0x033c:
            r31 = r0
            r20 = r5
            r30 = r7
            r18 = r8
            r22 = r9
            r16 = r10
            r19 = r12
            r12 = r6
            goto L_0x0359
        L_0x034c:
            r31 = r0
            r20 = r5
            r30 = r7
            r22 = r9
            r16 = r10
            r19 = r12
            r12 = r6
        L_0x0359:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r8)
            java.lang.String r1 = "</body></html>"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r1 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            r3 = r32
            r1.<init>((java.lang.String) r11, (java.lang.String) r15, (java.lang.String) r0)
            r4 = r1
            r6 = r12
            r10 = r16
            r9 = r22
            r5 = r30
            goto L_0x03b1
        L_0x037c:
            r31 = r0
            r3 = r1
            r33 = r4
            r30 = r7
            r22 = r9
            r16 = r10
            r19 = r12
            r12 = r6
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r1 = "FORBIDDEN: No directory listing."
            r6 = r12
            r5 = r30
            r0.<init>((java.lang.String) r5, (java.lang.String) r6, (java.lang.String) r1)
            r4 = r0
            r10 = r16
            r9 = r22
            goto L_0x03b1
        L_0x039a:
            r31 = r0
            r3 = r1
            r33 = r4
            r5 = r7
            r22 = r9
            r16 = r10
            r19 = r12
            goto L_0x03b1
        L_0x03a7:
            r31 = r0
            r3 = r1
            r5 = r7
            r16 = r10
            r19 = r12
            r10 = r16
        L_0x03b1:
            if (r4 != 0) goto L_0x05fc
            r0 = 0
            java.lang.String r1 = r10.getCanonicalPath()     // Catch:{ IOException -> 0x05e6 }
            r7 = 46
            int r1 = r1.lastIndexOf(r7)     // Catch:{ IOException -> 0x05e6 }
            if (r1 < 0) goto L_0x03e2
            java.util.Hashtable r7 = theMimeTypes     // Catch:{ IOException -> 0x03d8 }
            java.lang.String r8 = r10.getCanonicalPath()     // Catch:{ IOException -> 0x03d8 }
            int r12 = r1 + 1
            java.lang.String r8 = r8.substring(r12)     // Catch:{ IOException -> 0x03d8 }
            java.lang.String r8 = r8.toLowerCase()     // Catch:{ IOException -> 0x03d8 }
            java.lang.Object r7 = r7.get(r8)     // Catch:{ IOException -> 0x03d8 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ IOException -> 0x03d8 }
            r0 = r7
            goto L_0x03e2
        L_0x03d8:
            r0 = move-exception
            r30 = r5
            r16 = r6
            r22 = r9
            r5 = r10
            goto L_0x05f0
        L_0x03e2:
            if (r0 != 0) goto L_0x03e8
            java.lang.String r7 = "application/octet-stream"
            r0 = r7
            goto L_0x03e9
        L_0x03e8:
            r7 = r0
        L_0x03e9:
            java.lang.String r0 = r10.getAbsolutePath()     // Catch:{ IOException -> 0x05e6 }
            long r12 = r10.lastModified()     // Catch:{ IOException -> 0x05e6 }
            long r14 = r10.length()     // Catch:{ IOException -> 0x05e6 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x05e6 }
            r8.<init>()     // Catch:{ IOException -> 0x05e6 }
            java.lang.StringBuilder r0 = r8.append(r0)     // Catch:{ IOException -> 0x05e6 }
            java.lang.StringBuilder r0 = r0.append(r12)     // Catch:{ IOException -> 0x05e6 }
            java.lang.StringBuilder r0 = r0.append(r14)     // Catch:{ IOException -> 0x05e6 }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x05e6 }
            int r0 = r0.hashCode()     // Catch:{ IOException -> 0x05e6 }
            java.lang.String r0 = java.lang.Integer.toHexString(r0)     // Catch:{ IOException -> 0x05e6 }
            r8 = r0
            r12 = 0
            r14 = -1
            java.lang.String r0 = "range"
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x05e6 }
            if (r0 == 0) goto L_0x0475
            r33 = r1
            r1 = r31
            boolean r16 = r0.startsWith(r1)     // Catch:{ IOException -> 0x0469 }
            if (r16 == 0) goto L_0x0466
            int r1 = r1.length()     // Catch:{ IOException -> 0x0469 }
            java.lang.String r1 = r0.substring(r1)     // Catch:{ IOException -> 0x0469 }
            r0 = 45
            int r0 = r1.indexOf(r0)     // Catch:{ IOException -> 0x0469 }
            r16 = r0
            r18 = r4
            r4 = r16
            if (r4 <= 0) goto L_0x0464
            r0 = 0
            java.lang.String r0 = r1.substring(r0, r4)     // Catch:{ NumberFormatException -> 0x0463, IOException -> 0x0457 }
            long r16 = java.lang.Long.parseLong(r0)     // Catch:{ NumberFormatException -> 0x0463, IOException -> 0x0457 }
            r12 = r16
            int r0 = r4 + 1
            java.lang.String r0 = r1.substring(r0)     // Catch:{ NumberFormatException -> 0x0463, IOException -> 0x0457 }
            long r16 = java.lang.Long.parseLong(r0)     // Catch:{ NumberFormatException -> 0x0463, IOException -> 0x0457 }
            r14 = r16
            goto L_0x0464
        L_0x0457:
            r0 = move-exception
            r30 = r5
            r16 = r6
            r22 = r9
            r5 = r10
            r4 = r18
            goto L_0x05f0
        L_0x0463:
            r0 = move-exception
        L_0x0464:
            r0 = r1
            goto L_0x0479
        L_0x0466:
            r18 = r4
            goto L_0x0479
        L_0x0469:
            r0 = move-exception
            r18 = r4
            r30 = r5
            r16 = r6
            r22 = r9
            r5 = r10
            goto L_0x05f0
        L_0x0475:
            r33 = r1
            r18 = r4
        L_0x0479:
            long r16 = r10.length()     // Catch:{ IOException -> 0x05db }
            r20 = r16
            java.lang.String r1 = ""
            java.lang.String r4 = "ETag"
            if (r0 == 0) goto L_0x057a
            r16 = 0
            int r22 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1))
            if (r22 < 0) goto L_0x057a
            java.lang.String r11 = "Content-Range"
            r22 = r9
            r23 = r10
            r9 = r20
            int r20 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
            if (r20 < 0) goto L_0x04dd
            r20 = r0
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response     // Catch:{ IOException -> 0x04d2 }
            r30 = r5
            java.lang.String r5 = "416 Requested Range Not Satisfiable"
            r0.<init>((java.lang.String) r5, (java.lang.String) r6, (java.lang.String) r1)     // Catch:{ IOException -> 0x04c9 }
            r1 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x04c1 }
            r0.<init>()     // Catch:{ IOException -> 0x04c1 }
            java.lang.String r5 = "bytes 0-0/"
            java.lang.StringBuilder r0 = r0.append(r5)     // Catch:{ IOException -> 0x04c1 }
            java.lang.StringBuilder r0 = r0.append(r9)     // Catch:{ IOException -> 0x04c1 }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x04c1 }
            r1.addHeader(r11, r0)     // Catch:{ IOException -> 0x04c1 }
            r1.addHeader(r4, r8)     // Catch:{ IOException -> 0x04c1 }
            r4 = r1
            r5 = r23
            goto L_0x0601
        L_0x04c1:
            r0 = move-exception
            r4 = r1
            r16 = r6
            r5 = r23
            goto L_0x05f0
        L_0x04c9:
            r0 = move-exception
            r16 = r6
            r4 = r18
            r5 = r23
            goto L_0x05f0
        L_0x04d2:
            r0 = move-exception
            r30 = r5
            r16 = r6
            r4 = r18
            r5 = r23
            goto L_0x05f0
        L_0x04dd:
            r20 = r0
            r30 = r5
            r0 = 1
            int r5 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r5 >= 0) goto L_0x04e9
            long r14 = r9 - r0
        L_0x04e9:
            long r24 = r14 - r12
            long r24 = r24 + r0
            int r0 = (r24 > r16 ? 1 : (r24 == r16 ? 0 : -1))
            if (r0 >= 0) goto L_0x04f3
            r24 = 0
        L_0x04f3:
            r0 = r24
            com.google.appinventor.components.runtime.util.NanoHTTPD$2 r5 = new com.google.appinventor.components.runtime.util.NanoHTTPD$2     // Catch:{ IOException -> 0x056f }
            r16 = r6
            r6 = r23
            r5.<init>(r6, r0)     // Catch:{ IOException -> 0x0564 }
            r5.skip(r12)     // Catch:{ IOException -> 0x0564 }
            r23 = r6
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r6 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response     // Catch:{ IOException -> 0x055b }
            java.lang.String r2 = "206 Partial Content"
            r6.<init>((java.lang.String) r2, (java.lang.String) r7, (java.io.InputStream) r5)     // Catch:{ IOException -> 0x055b }
            r2 = r6
            java.lang.String r6 = "Content-Length"
            r17 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0553 }
            r5.<init>()     // Catch:{ IOException -> 0x0553 }
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ IOException -> 0x0553 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0553 }
            r2.addHeader(r6, r5)     // Catch:{ IOException -> 0x0553 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0553 }
            r5.<init>()     // Catch:{ IOException -> 0x0553 }
            java.lang.String r6 = "bytes "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x0553 }
            java.lang.StringBuilder r5 = r5.append(r12)     // Catch:{ IOException -> 0x0553 }
            java.lang.String r6 = "-"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x0553 }
            java.lang.StringBuilder r5 = r5.append(r14)     // Catch:{ IOException -> 0x0553 }
            r6 = r19
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x0553 }
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ IOException -> 0x0553 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0553 }
            r2.addHeader(r11, r5)     // Catch:{ IOException -> 0x0553 }
            r2.addHeader(r4, r8)     // Catch:{ IOException -> 0x0553 }
            r4 = r2
            r5 = r23
            r2 = r34
            goto L_0x0601
        L_0x0553:
            r0 = move-exception
            r4 = r2
            r5 = r23
            r2 = r34
            goto L_0x05f0
        L_0x055b:
            r0 = move-exception
            r2 = r34
            r4 = r18
            r5 = r23
            goto L_0x05f0
        L_0x0564:
            r0 = move-exception
            r23 = r6
            r2 = r34
            r4 = r18
            r5 = r23
            goto L_0x05f0
        L_0x056f:
            r0 = move-exception
            r16 = r6
            r2 = r34
            r4 = r18
            r5 = r23
            goto L_0x05f0
        L_0x057a:
            r30 = r5
            r16 = r6
            r22 = r9
            r23 = r10
            r9 = r20
            r20 = r0
            java.lang.String r0 = "if-none-match"
            r2 = r34
            java.lang.String r0 = r2.getProperty(r0)     // Catch:{ IOException -> 0x05d1 }
            boolean r0 = r8.equals(r0)     // Catch:{ IOException -> 0x05d1 }
            if (r0 == 0) goto L_0x05a6
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response     // Catch:{ IOException -> 0x05a0 }
            java.lang.String r4 = "304 Not Modified"
            r0.<init>((java.lang.String) r4, (java.lang.String) r7, (java.lang.String) r1)     // Catch:{ IOException -> 0x05a0 }
            r4 = r0
            r5 = r23
            goto L_0x0601
        L_0x05a0:
            r0 = move-exception
            r4 = r18
            r5 = r23
            goto L_0x05f0
        L_0x05a6:
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r0 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response     // Catch:{ IOException -> 0x05d1 }
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x05d1 }
            r5 = r23
            r1.<init>(r5)     // Catch:{ IOException -> 0x05cd }
            r0.<init>((java.lang.String) r11, (java.lang.String) r7, (java.io.InputStream) r1)     // Catch:{ IOException -> 0x05cd }
            r1 = r0
            java.lang.String r0 = "Content-Length"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x05ca }
            r6.<init>()     // Catch:{ IOException -> 0x05ca }
            java.lang.StringBuilder r6 = r6.append(r9)     // Catch:{ IOException -> 0x05ca }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x05ca }
            r1.addHeader(r0, r6)     // Catch:{ IOException -> 0x05ca }
            r1.addHeader(r4, r8)     // Catch:{ IOException -> 0x05ca }
            r4 = r1
            goto L_0x0601
        L_0x05ca:
            r0 = move-exception
            r4 = r1
            goto L_0x05f0
        L_0x05cd:
            r0 = move-exception
            r4 = r18
            goto L_0x05f0
        L_0x05d1:
            r0 = move-exception
            goto L_0x05d6
        L_0x05d3:
            r0 = move-exception
            r2 = r34
        L_0x05d6:
            r5 = r23
            r4 = r18
            goto L_0x05f0
        L_0x05db:
            r0 = move-exception
            r30 = r5
            r16 = r6
            r22 = r9
            r5 = r10
            r4 = r18
            goto L_0x05f0
        L_0x05e6:
            r0 = move-exception
            r18 = r4
            r30 = r5
            r16 = r6
            r22 = r9
            r5 = r10
        L_0x05f0:
            com.google.appinventor.components.runtime.util.NanoHTTPD$Response r1 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response
            java.lang.String r6 = "FORBIDDEN: Reading file failed."
            r8 = r16
            r7 = r30
            r1.<init>((java.lang.String) r7, (java.lang.String) r8, (java.lang.String) r6)
            goto L_0x0602
        L_0x05fc:
            r18 = r4
            r22 = r9
            r5 = r10
        L_0x0601:
            r1 = r4
        L_0x0602:
            java.lang.String r0 = "Accept-Ranges"
            java.lang.String r4 = "bytes"
            r1.addHeader(r0, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.NanoHTTPD.serveFile(java.lang.String, java.util.Properties, java.io.File, boolean):com.google.appinventor.components.runtime.util.NanoHTTPD$Response");
    }

    static {
        StringTokenizer st = new StringTokenizer("css            text/css htm            text/html html           text/html xml            text/xml txt            text/plain asc            text/plain gif            image/gif jpg            image/jpeg jpeg           image/jpeg png            image/png mp3            audio/mpeg m3u            audio/mpeg-url mp4            video/mp4 ogv            video/ogg flv            video/x-flv mov            video/quicktime swf            application/x-shockwave-flash js                     application/javascript pdf            application/pdf doc            application/msword ogg            application/x-ogg zip            application/octet-stream exe            application/octet-stream class          application/octet-stream ");
        while (st.hasMoreTokens()) {
            theMimeTypes.put(st.nextToken(), st.nextToken());
        }
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
}
