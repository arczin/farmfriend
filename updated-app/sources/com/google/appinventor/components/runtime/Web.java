package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.HtmlEntities;
import com.google.appinventor.components.runtime.collect.Lists;
import com.google.appinventor.components.runtime.collect.Maps;
import com.google.appinventor.components.runtime.errors.DispatchableError;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.repackaged.org.json.XML;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ChartDataSourceUtil;
import com.google.appinventor.components.runtime.util.CsvUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.FileUtil;
import com.google.appinventor.components.runtime.util.GingerbreadUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.NanoHTTPD;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.XmlParser;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONException;
import org.xml.sax.InputSource;

@DesignerComponent(category = ComponentCategory.CONNECTIVITY, description = "Non-visible component that provides functions for HTTP GET, POST, PUT, and DELETE requests.", iconName = "images/web.png", nonVisible = true, version = 9)
@UsesLibraries(libraries = "json.jar")
@SimpleObject
@UsesPermissions({"android.permission.INTERNET"})
public class Web extends AndroidNonvisibleComponent implements Component, ObservableDataSource<YailList, Future<YailList>> {
    private static final String LOG_TAG = "Web";
    private static final Map<String, String> mimeTypeToExtension = Maps.newHashMap();
    private final Activity activity;
    /* access modifiers changed from: private */
    public boolean allowCookies;
    private YailList columns;
    /* access modifiers changed from: private */
    public final CookieHandler cookieHandler;
    private HashSet<DataSourceChangeListener> dataSourceObservers;
    /* access modifiers changed from: private */
    public boolean haveReadPermission;
    /* access modifiers changed from: private */
    public boolean haveWritePermission;
    private FutureTask<Void> lastTask;
    /* access modifiers changed from: private */
    public YailList requestHeaders;
    /* access modifiers changed from: private */
    public String responseFileName;
    private String responseTextEncoding;
    /* access modifiers changed from: private */
    public boolean saveResponse;
    /* access modifiers changed from: private */
    public int timeout;
    /* access modifiers changed from: private */
    public String urlString;

    private static class InvalidRequestHeadersException extends Exception {
        final int errorNumber;
        final int index;

        InvalidRequestHeadersException(int errorNumber2, int index2) {
            this.errorNumber = errorNumber2;
            this.index = index2;
        }
    }

    static class BuildRequestDataException extends Exception {
        final int errorNumber;
        final int index;

        BuildRequestDataException(int errorNumber2, int index2) {
            this.errorNumber = errorNumber2;
            this.index = index2;
        }
    }

    private static class CapturedProperties {
        final boolean allowCookies;
        final Map<String, List<String>> cookies;
        final Map<String, List<String>> requestHeaders;
        final String responseFileName;
        final boolean saveResponse;
        final int timeout;
        final URL url = new URL(this.urlString);
        final String urlString;

        CapturedProperties(Web web) throws MalformedURLException, InvalidRequestHeadersException {
            this.urlString = web.urlString;
            this.allowCookies = web.allowCookies;
            this.saveResponse = web.saveResponse;
            this.responseFileName = web.responseFileName;
            this.timeout = web.timeout;
            this.requestHeaders = Web.processRequestHeaders(web.requestHeaders);
            Map<String, List<String>> cookiesTemp = null;
            if (this.allowCookies && web.cookieHandler != null) {
                try {
                    cookiesTemp = web.cookieHandler.get(this.url.toURI(), this.requestHeaders);
                } catch (IOException | URISyntaxException e) {
                }
            }
            this.cookies = cookiesTemp;
        }
    }

    static {
        mimeTypeToExtension.put("application/pdf", "pdf");
        mimeTypeToExtension.put("application/zip", "zip");
        mimeTypeToExtension.put("audio/mpeg", "mpeg");
        mimeTypeToExtension.put("audio/mp3", "mp3");
        mimeTypeToExtension.put("audio/mp4", "mp4");
        mimeTypeToExtension.put("image/gif", "gif");
        mimeTypeToExtension.put("image/jpeg", "jpg");
        mimeTypeToExtension.put("image/png", "png");
        mimeTypeToExtension.put("image/tiff", "tiff");
        mimeTypeToExtension.put(NanoHTTPD.MIME_PLAINTEXT, "txt");
        mimeTypeToExtension.put(NanoHTTPD.MIME_HTML, "html");
        mimeTypeToExtension.put(NanoHTTPD.MIME_XML, "xml");
    }

    public Web(ComponentContainer container) {
        super(container.$form());
        this.urlString = "";
        this.requestHeaders = new YailList();
        this.responseFileName = "";
        this.timeout = 0;
        this.haveReadPermission = false;
        this.haveWritePermission = false;
        CookieHandler cookieHandler2 = null;
        this.lastTask = null;
        this.columns = new YailList();
        this.dataSourceObservers = new HashSet<>();
        this.responseTextEncoding = "UTF-8";
        this.activity = container.$context();
        this.cookieHandler = SdkLevel.getLevel() >= 9 ? GingerbreadUtil.newCookieManager() : cookieHandler2;
    }

    protected Web() {
        super((Form) null);
        this.urlString = "";
        this.requestHeaders = new YailList();
        this.responseFileName = "";
        this.timeout = 0;
        this.haveReadPermission = false;
        this.haveWritePermission = false;
        this.lastTask = null;
        this.columns = new YailList();
        this.dataSourceObservers = new HashSet<>();
        this.responseTextEncoding = "UTF-8";
        this.activity = null;
        this.cookieHandler = null;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The URL for the web request.")
    public String Url() {
        return this.urlString;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void Url(String url) {
        this.urlString = url;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "User-specified character encoding for web response.")
    public String ResponseTextEncoding() {
        return this.responseTextEncoding;
    }

    @DesignerProperty(defaultValue = "UTF-8", editorType = "string")
    @SimpleProperty
    public void ResponseTextEncoding(String encoding) {
        this.responseTextEncoding = encoding;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The request headers, as a list of two-element sublists. The first element of each sublist represents the request header field name. The second element of each sublist represents the request header field values, either a single value or a list containing multiple values.")
    public YailList RequestHeaders() {
        return this.requestHeaders;
    }

    @SimpleProperty
    public void RequestHeaders(YailList list) {
        try {
            processRequestHeaders(list);
            this.requestHeaders = list;
        } catch (InvalidRequestHeadersException e) {
            this.form.dispatchErrorOccurredEvent(this, "RequestHeaders", e.errorNumber, Integer.valueOf(e.index));
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the cookies from a response should be saved and used in subsequent requests. Cookies are only supported on Android version 2.3 or greater.")
    public boolean AllowCookies() {
        return this.allowCookies;
    }

    @DesignerProperty(defaultValue = "false", editorType = "boolean")
    @SimpleProperty
    public void AllowCookies(boolean allowCookies2) {
        this.allowCookies = allowCookies2;
        if (allowCookies2 && this.cookieHandler == null) {
            this.form.dispatchErrorOccurredEvent(this, "AllowCookies", 4, new Object[0]);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the response should be saved in a file.")
    public boolean SaveResponse() {
        return this.saveResponse;
    }

    @DesignerProperty(defaultValue = "false", editorType = "boolean")
    @SimpleProperty
    public void SaveResponse(boolean saveResponse2) {
        this.saveResponse = saveResponse2;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The name of the file where the response should be saved. If SaveResponse is true and ResponseFileName is empty, then a new file name will be generated.")
    public String ResponseFileName() {
        return this.responseFileName;
    }

    @DesignerProperty(defaultValue = "", editorType = "string")
    @SimpleProperty
    public void ResponseFileName(String responseFileName2) {
        this.responseFileName = responseFileName2;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The number of milliseconds that a web request will wait for a response before giving up. If set to 0, then there is no time limit on how long the request will wait.")
    public int Timeout() {
        return this.timeout;
    }

    @DesignerProperty(defaultValue = "0", editorType = "non_negative_integer")
    @SimpleProperty
    public void Timeout(int timeout2) {
        if (timeout2 >= 0) {
            this.timeout = timeout2;
            return;
        }
        throw new IllegalArgumentError("Web Timeout must be a non-negative integer.");
    }

    @SimpleFunction(description = "Clears all cookies for this Web component.")
    public void ClearCookies() {
        if (this.cookieHandler != null) {
            GingerbreadUtil.clearCookies(this.cookieHandler);
        } else {
            this.form.dispatchErrorOccurredEvent(this, "ClearCookies", 4, new Object[0]);
        }
    }

    @SimpleFunction
    public void Get() {
        final CapturedProperties webProps = capturePropertyValues("Get");
        if (webProps != null) {
            this.lastTask = new FutureTask<>(new Runnable() {
                public void run() {
                    Web.this.performRequest(webProps, (byte[]) null, (String) null, "GET", "Get");
                }
            }, (Object) null);
            AsynchUtil.runAsynchronously(this.lastTask);
        }
    }

    @SimpleFunction(description = "Performs an HTTP POST request using the Url property and the specified text.\nThe characters of the text are encoded using UTF-8 encoding.\nIf the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The responseFileName property can be used to specify the name of the file.\nIf the SaveResponse property is false, the GotText event will be triggered.")
    public void PostText(String text) {
        requestTextImpl(text, "UTF-8", "PostText", "POST");
    }

    @SimpleFunction(description = "Performs an HTTP POST request using the Url property and the specified text.\nThe characters of the text are encoded using the given encoding.\nIf the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The ResponseFileName property can be used to specify the name of the file.\nIf the SaveResponse property is false, the GotText event will be triggered.")
    public void PostTextWithEncoding(String text, String encoding) {
        requestTextImpl(text, encoding, "PostTextWithEncoding", "POST");
    }

    @SimpleFunction(description = "Performs an HTTP POST request using the Url property and data from the specified file.\nIf the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The ResponseFileName property can be used to specify the name of the file.\nIf the SaveResponse property is false, the GotText event will be triggered.")
    public void PostFile(final String path) {
        final CapturedProperties webProps = capturePropertyValues("PostFile");
        if (webProps != null) {
            this.lastTask = new FutureTask<>(new Runnable() {
                public void run() {
                    Web.this.performRequest(webProps, (byte[]) null, path, "POST", "PostFile");
                }
            }, (Object) null);
            AsynchUtil.runAsynchronously(this.lastTask);
        }
    }

    @SimpleFunction(description = "Performs an HTTP PATCH request using the Url property and the specified text.<br>The characters of the text are encoded using UTF-8 encoding.<br>If the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The responseFileName property can be used to specify the name of the file.<br>If the SaveResponse property is false, the GotText event will be triggered.")
    public void PatchText(String text) {
        requestTextImpl(text, "UTF-8", "PatchText", "PATCH");
    }

    @SimpleFunction(description = "Performs an HTTP PATCH request using the Url property and the specified text.<br>The characters of the text are encoded using the given encoding.<br>If the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The ResponseFileName property can be used to specify the name of the file.<br>If the SaveResponse property is false, the GotText event will be triggered.")
    public void PatchTextWithEncoding(String text, String encoding) {
        requestTextImpl(text, encoding, "PatchTextWithEncoding", "PATCH");
    }

    @SimpleFunction(description = "Performs an HTTP PATCH request using the Url property and data from the specified file.<br>If the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The ResponseFileName property can be used to specify the name of the file.<br>If the SaveResponse property is false, the GotText event will be triggered.")
    public void PatchFile(final String path) {
        final CapturedProperties webProps = capturePropertyValues("PatchFile");
        if (webProps != null) {
            AsynchUtil.runAsynchronously(new Runnable() {
                public void run() {
                    Web.this.performRequest(webProps, (byte[]) null, path, "PATCH", "PatchFile");
                }
            });
        }
    }

    @SimpleFunction(description = "Performs an HTTP PUT request using the Url property and the specified text.<br>The characters of the text are encoded using UTF-8 encoding.<br>If the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The responseFileName property can be used to specify the name of the file.<br>If the SaveResponse property is false, the GotText event will be triggered.")
    public void PutText(String text) {
        requestTextImpl(text, "UTF-8", "PutText", "PUT");
    }

    @SimpleFunction(description = "Performs an HTTP PUT request using the Url property and the specified text.<br>The characters of the text are encoded using the given encoding.<br>If the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The ResponseFileName property can be used to specify the name of the file.<br>If the SaveResponse property is false, the GotText event will be triggered.")
    public void PutTextWithEncoding(String text, String encoding) {
        requestTextImpl(text, encoding, "PutTextWithEncoding", "PUT");
    }

    @SimpleFunction(description = "Performs an HTTP PUT request using the Url property and data from the specified file.<br>If the SaveResponse property is true, the response will be saved in a file and the GotFile event will be triggered. The ResponseFileName property can be used to specify the name of the file.<br>If the SaveResponse property is false, the GotText event will be triggered.")
    public void PutFile(final String path) {
        final CapturedProperties webProps = capturePropertyValues("PutFile");
        if (webProps != null) {
            this.lastTask = new FutureTask<>(new Runnable() {
                public void run() {
                    Web.this.performRequest(webProps, (byte[]) null, path, "PUT", "PutFile");
                }
            }, (Object) null);
            AsynchUtil.runAsynchronously(this.lastTask);
        }
    }

    @SimpleFunction
    public void Delete() {
        final CapturedProperties webProps = capturePropertyValues("Delete");
        if (webProps != null) {
            this.lastTask = new FutureTask<>(new Runnable() {
                public void run() {
                    Web.this.performRequest(webProps, (byte[]) null, (String) null, "DELETE", "Delete");
                }
            }, (Object) null);
            AsynchUtil.runAsynchronously(this.lastTask);
        }
    }

    private void requestTextImpl(String text, String encoding, String functionName, String httpVerb) {
        CapturedProperties webProps = capturePropertyValues(functionName);
        if (webProps != null) {
            final String str = encoding;
            final String str2 = text;
            final String str3 = functionName;
            final CapturedProperties capturedProperties = webProps;
            final String str4 = httpVerb;
            this.lastTask = new FutureTask<>(new Runnable() {
                public void run() {
                    byte[] requestData;
                    try {
                        if (str != null) {
                            if (str.length() != 0) {
                                requestData = str2.getBytes(str);
                                Web.this.performRequest(capturedProperties, requestData, (String) null, str4, str3);
                            }
                        }
                        requestData = str2.getBytes("UTF-8");
                        Web.this.performRequest(capturedProperties, requestData, (String) null, str4, str3);
                    } catch (UnsupportedEncodingException e) {
                        Web.this.form.dispatchErrorOccurredEvent(Web.this, str3, ErrorMessages.ERROR_WEB_UNSUPPORTED_ENCODING, str);
                    }
                }
            }, (Object) null);
            AsynchUtil.runAsynchronously(this.lastTask);
        }
    }

    @SimpleEvent
    public void GotText(String url, int responseCode, String responseType, String responseContent) {
        EventDispatcher.dispatchEvent(this, "GotText", url, Integer.valueOf(responseCode), responseType, responseContent);
    }

    @SimpleEvent
    public void GotFile(String url, int responseCode, String responseType, String fileName) {
        EventDispatcher.dispatchEvent(this, "GotFile", url, Integer.valueOf(responseCode), responseType, fileName);
    }

    @SimpleEvent
    public void TimedOut(String url) {
        EventDispatcher.dispatchEvent(this, "TimedOut", url);
    }

    @SimpleFunction
    public String BuildRequestData(YailList list) {
        try {
            return buildRequestData(list);
        } catch (BuildRequestDataException e) {
            this.form.dispatchErrorOccurredEvent(this, "BuildRequestData", e.errorNumber, Integer.valueOf(e.index));
            return "";
        }
    }

    /* access modifiers changed from: package-private */
    public String buildRequestData(YailList list) throws BuildRequestDataException {
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        int i = 0;
        while (i < list.size()) {
            Object item = list.getObject(i);
            if (item instanceof YailList) {
                YailList sublist = (YailList) item;
                if (sublist.size() == 2) {
                    sb.append(delimiter).append(UriEncode(sublist.getObject(0).toString())).append('=').append(UriEncode(sublist.getObject(1).toString()));
                    delimiter = "&";
                    i++;
                } else {
                    throw new BuildRequestDataException(ErrorMessages.ERROR_WEB_BUILD_REQUEST_DATA_NOT_TWO_ELEMENTS, i + 1);
                }
            } else {
                throw new BuildRequestDataException(ErrorMessages.ERROR_WEB_BUILD_REQUEST_DATA_NOT_LIST, i + 1);
            }
        }
        return sb.toString();
    }

    @SimpleFunction
    public String UriEncode(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "UTF-8 is unsupported?", e);
            return "";
        }
    }

    @SimpleFunction
    public String UriDecode(String text) {
        try {
            return URLDecoder.decode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "UTF-8 is unsupported?", e);
            return "";
        }
    }

    @SimpleFunction
    public Object JsonTextDecode(String jsonText) {
        try {
            return decodeJsonText(jsonText, false);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, "JsonTextDecode", ErrorMessages.ERROR_WEB_JSON_TEXT_DECODE_FAILED, jsonText);
            return "";
        }
    }

    @SimpleFunction
    public Object JsonTextDecodeWithDictionaries(String jsonText) {
        try {
            return decodeJsonText(jsonText, true);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, "JsonTextDecodeWithDictionaries", ErrorMessages.ERROR_WEB_JSON_TEXT_DECODE_FAILED, jsonText);
            return "";
        }
    }

    public static Object decodeJsonText(String jsonText, boolean useDicts) throws IllegalArgumentException {
        try {
            return JsonUtil.getObjectFromJson(jsonText, useDicts);
        } catch (JSONException e) {
            throw new IllegalArgumentException("jsonText is not a legal JSON value");
        }
    }

    @Deprecated
    static Object decodeJsonText(String jsonText) throws IllegalArgumentException {
        return decodeJsonText(jsonText, false);
    }

    @SimpleFunction
    public String JsonObjectEncode(Object jsonObject) {
        try {
            return JsonUtil.encodeJsonObject(jsonObject);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, "JsonObjectEncode", ErrorMessages.ERROR_WEB_JSON_TEXT_ENCODE_FAILED, jsonObject);
            return "";
        }
    }

    @SimpleFunction(description = "Decodes the given XML into a set of nested dictionaries that capture the structure and data contained in the XML. See the help for more details.")
    public Object XMLTextDecodeAsDictionary(String XmlText) {
        try {
            XmlParser p = new XmlParser();
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            InputSource is = new InputSource(new StringReader(XmlText));
            is.setEncoding("UTF-8");
            parser.parse(is, p);
            return p.getRoot();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            this.form.dispatchErrorOccurredEvent(this, "XMLTextDecodeAsDictionary", ErrorMessages.ERROR_WEB_JSON_TEXT_DECODE_FAILED, e.getMessage());
            return new YailDictionary();
        }
    }

    @SimpleFunction(description = "Decodes the given XML string to produce a dictionary structure. See the App Inventor documentation on \"Other topics, notes, and details\" for information.")
    public Object XMLTextDecode(String XmlText) {
        try {
            return JsonTextDecode(XML.toJSONObject(XmlText).toString());
        } catch (com.google.appinventor.components.runtime.repackaged.org.json.JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
            this.form.dispatchErrorOccurredEvent(this, "XMLTextDecode", ErrorMessages.ERROR_WEB_JSON_TEXT_DECODE_FAILED, e.getMessage());
            return YailList.makeEmptyList();
        }
    }

    @SimpleFunction(description = "Decodes the given HTML text value. HTML character entities such as `&`, `<`, `>`, `'`, and `\"` are changed to &, <, >, ', and \". Entities such as &#xhhhh, and &#nnnn are changed to the appropriate characters.")
    public String HtmlTextDecode(String htmlText) {
        try {
            return HtmlEntities.decodeHtmlText(htmlText);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, "HtmlTextDecode", ErrorMessages.ERROR_WEB_HTML_TEXT_DECODE_FAILED, htmlText);
            return "";
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x013d  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0148  */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:57:0x0115=Splitter:B:57:0x0115, B:46:0x00f7=Splitter:B:46:0x00f7} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void performRequest(com.google.appinventor.components.runtime.Web.CapturedProperties r19, byte[] r20, java.lang.String r21, java.lang.String r22, java.lang.String r23) {
        /*
            r18 = this;
            r13 = r18
            r14 = r19
            r15 = r20
            r12 = r21
            r11 = r23
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r10 = r0
            if (r12 == 0) goto L_0x0023
            com.google.appinventor.components.runtime.Form r0 = r13.form
            boolean r0 = com.google.appinventor.components.runtime.util.FileUtil.needsReadPermission(r0, r12)
            if (r0 == 0) goto L_0x0023
            boolean r0 = r13.haveReadPermission
            if (r0 != 0) goto L_0x0023
            java.lang.String r0 = "android.permission.READ_EXTERNAL_STORAGE"
            r10.add(r0)
        L_0x0023:
            boolean r0 = r13.saveResponse
            if (r0 == 0) goto L_0x0046
            com.google.appinventor.components.runtime.Form r0 = r13.form
            java.lang.String r1 = r14.responseFileName
            com.google.appinventor.components.runtime.Form r2 = r13.form
            com.google.appinventor.components.common.FileScope r2 = r2.DefaultFileScope()
            java.lang.String r0 = com.google.appinventor.components.runtime.util.FileUtil.resolveFileName(r0, r1, r2)
            com.google.appinventor.components.runtime.Form r1 = r13.form
            boolean r1 = com.google.appinventor.components.runtime.util.FileUtil.needsWritePermission(r1, r0)
            if (r1 == 0) goto L_0x0046
            boolean r1 = r13.haveWritePermission
            if (r1 != 0) goto L_0x0046
            java.lang.String r1 = "android.permission.WRITE_EXTERNAL_STORAGE"
            r10.add(r1)
        L_0x0046:
            int r0 = r10.size()
            r9 = 0
            if (r0 <= 0) goto L_0x007d
            boolean r0 = r13.haveReadPermission
            if (r0 != 0) goto L_0x007d
            r7 = r18
            com.google.appinventor.components.runtime.Form r0 = r13.form
            com.google.appinventor.components.runtime.Web$7 r8 = new com.google.appinventor.components.runtime.Web$7
            java.lang.String[] r1 = new java.lang.String[r9]
            java.lang.Object[] r1 = r10.toArray(r1)
            r5 = r1
            java.lang.String[] r5 = (java.lang.String[]) r5
            r1 = r8
            r2 = r18
            r3 = r18
            r4 = r23
            r6 = r10
            r9 = r8
            r8 = r19
            r13 = r9
            r9 = r20
            r16 = r10
            r10 = r21
            r11 = r22
            r12 = r23
            r1.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            r0.askPermission(r13)
            return
        L_0x007d:
            r16 = r10
            r12 = r22
            java.net.HttpURLConnection r0 = openConnection(r14, r12)     // Catch:{ PermissionException -> 0x01e7, FileException -> 0x01d5, DispatchableError -> 0x01c2, RequestTimeoutException -> 0x01ad, Exception -> 0x012e }
            r13 = r0
            if (r13 == 0) goto L_0x0128
            if (r15 == 0) goto L_0x009e
            writeRequestData(r13, r15)     // Catch:{ SocketTimeoutException -> 0x0098, all -> 0x0092 }
            r8 = r18
            r10 = r21
            goto L_0x00aa
        L_0x0092:
            r0 = move-exception
            r4 = 0
            r11 = r18
            goto L_0x0115
        L_0x0098:
            r0 = move-exception
            r4 = 0
            r11 = r18
            goto L_0x0104
        L_0x009e:
            r10 = r21
            if (r10 == 0) goto L_0x00a8
            r8 = r18
            r8.writeRequestFile(r13, r10)     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            goto L_0x00aa
        L_0x00a8:
            r8 = r18
        L_0x00aa:
            int r4 = r13.getResponseCode()     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            java.lang.String r0 = getResponseType(r13)     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            r8.processResponseCookies(r13)     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            boolean r1 = r8.saveResponse     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            if (r1 == 0) goto L_0x00d3
            java.lang.String r1 = r14.responseFileName     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            java.lang.String r6 = r8.saveResponseContent(r13, r1, r0)     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            android.app.Activity r7 = r8.activity     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            com.google.appinventor.components.runtime.Web$8 r5 = new com.google.appinventor.components.runtime.Web$8     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            r1 = r5
            r2 = r18
            r3 = r19
            r9 = r5
            r5 = r0
            r1.<init>(r3, r4, r5, r6)     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            r7.runOnUiThread(r9)     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            r11 = r8
            r4 = 0
            goto L_0x00f7
        L_0x00d3:
            java.lang.String r1 = r8.responseTextEncoding     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            java.lang.String r1 = getResponseContent(r13, r1)     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            android.app.Activity r2 = r8.activity     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            com.google.appinventor.components.runtime.Web$9 r3 = new com.google.appinventor.components.runtime.Web$9     // Catch:{ SocketTimeoutException -> 0x0101, all -> 0x00fd }
            r5 = r3
            r6 = r18
            r7 = r19
            r9 = r8
            r8 = r4
            r17 = r4
            r11 = r9
            r4 = 0
            r9 = r0
            r10 = r1
            r5.<init>(r7, r8, r9, r10)     // Catch:{ SocketTimeoutException -> 0x00fb }
            r2.runOnUiThread(r3)     // Catch:{ SocketTimeoutException -> 0x00fb }
            r11.updateColumns(r1, r0)     // Catch:{ SocketTimeoutException -> 0x00fb }
            r2 = 0
            r11.notifyDataObservers((com.google.appinventor.components.runtime.util.YailList) r2, (java.lang.Object) r2)     // Catch:{ SocketTimeoutException -> 0x00fb }
        L_0x00f7:
            r13.disconnect()     // Catch:{ PermissionException -> 0x0125, FileException -> 0x0122, DispatchableError -> 0x011f, RequestTimeoutException -> 0x011c, Exception -> 0x011a }
            goto L_0x012a
        L_0x00fb:
            r0 = move-exception
            goto L_0x0104
        L_0x00fd:
            r0 = move-exception
            r11 = r8
            r4 = 0
            goto L_0x0115
        L_0x0101:
            r0 = move-exception
            r11 = r8
            r4 = 0
        L_0x0104:
            android.app.Activity r1 = r11.activity     // Catch:{ all -> 0x0114 }
            com.google.appinventor.components.runtime.Web$10 r2 = new com.google.appinventor.components.runtime.Web$10     // Catch:{ all -> 0x0114 }
            r2.<init>(r14)     // Catch:{ all -> 0x0114 }
            r1.runOnUiThread(r2)     // Catch:{ all -> 0x0114 }
            com.google.appinventor.components.runtime.errors.RequestTimeoutException r1 = new com.google.appinventor.components.runtime.errors.RequestTimeoutException     // Catch:{ all -> 0x0114 }
            r1.<init>()     // Catch:{ all -> 0x0114 }
            throw r1     // Catch:{ all -> 0x0114 }
        L_0x0114:
            r0 = move-exception
        L_0x0115:
            r13.disconnect()     // Catch:{ PermissionException -> 0x0125, FileException -> 0x0122, DispatchableError -> 0x011f, RequestTimeoutException -> 0x011c, Exception -> 0x011a }
            throw r0     // Catch:{ PermissionException -> 0x0125, FileException -> 0x0122, DispatchableError -> 0x011f, RequestTimeoutException -> 0x011c, Exception -> 0x011a }
        L_0x011a:
            r0 = move-exception
            goto L_0x0132
        L_0x011c:
            r0 = move-exception
            goto L_0x01b1
        L_0x011f:
            r0 = move-exception
            goto L_0x01c5
        L_0x0122:
            r0 = move-exception
            goto L_0x01d9
        L_0x0125:
            r0 = move-exception
            goto L_0x01ea
        L_0x0128:
            r11 = r18
        L_0x012a:
            r2 = r23
            goto L_0x01f2
        L_0x012e:
            r0 = move-exception
            r4 = 0
            r11 = r18
        L_0x0132:
            r1 = r0
            java.lang.String r0 = "Get"
            r2 = r23
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0148
            r0 = 1101(0x44d, float:1.543E-42)
            java.lang.String r3 = r14.urlString
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]
            r5[r4] = r3
            r3 = r5
            goto L_0x01a4
        L_0x0148:
            java.lang.String r0 = "Delete"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x015b
            r0 = 1114(0x45a, float:1.561E-42)
            java.lang.String r3 = r14.urlString
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]
            r5[r4] = r3
            r3 = r5
            goto L_0x01a4
        L_0x015b:
            java.lang.String r0 = "PostFile"
            boolean r0 = r2.equals(r0)
            r3 = 2
            if (r0 != 0) goto L_0x0199
            java.lang.String r0 = "PutFile"
            boolean r0 = r2.equals(r0)
            if (r0 != 0) goto L_0x0199
            java.lang.String r0 = "PatchFile"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0175
            goto L_0x0199
        L_0x0175:
            r5 = 1103(0x44f, float:1.546E-42)
            java.lang.String r6 = ""
            if (r15 == 0) goto L_0x018d
            java.lang.String r0 = new java.lang.String     // Catch:{ UnsupportedEncodingException -> 0x0184 }
            java.lang.String r7 = "UTF-8"
            r0.<init>(r15, r7)     // Catch:{ UnsupportedEncodingException -> 0x0184 }
            r6 = r0
            goto L_0x018d
        L_0x0184:
            r0 = move-exception
            java.lang.String r7 = "Web"
            java.lang.String r8 = "UTF-8 is the default charset for Android but not available???"
            android.util.Log.e(r7, r8)
            goto L_0x018e
        L_0x018d:
        L_0x018e:
            java.lang.String r0 = r14.urlString
            java.lang.String[] r3 = new java.lang.String[r3]
            r3[r4] = r6
            r4 = 1
            r3[r4] = r0
            r0 = r5
            goto L_0x01a4
        L_0x0199:
            r0 = 1104(0x450, float:1.547E-42)
            java.lang.String r5 = r14.urlString
            java.lang.String[] r3 = new java.lang.String[r3]
            r3[r4] = r21
            r4 = 1
            r3[r4] = r5
        L_0x01a4:
            com.google.appinventor.components.runtime.Form r4 = r11.form
            r5 = r3
            java.lang.Object[] r5 = (java.lang.Object[]) r5
            r4.dispatchErrorOccurredEvent(r11, r2, r0, r5)
            goto L_0x01f2
        L_0x01ad:
            r0 = move-exception
            r4 = 0
            r11 = r18
        L_0x01b1:
            r2 = r23
            com.google.appinventor.components.runtime.Form r1 = r11.form
            java.lang.String r3 = r14.urlString
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]
            r5[r4] = r3
            r3 = 1117(0x45d, float:1.565E-42)
            r1.dispatchErrorOccurredEvent(r11, r2, r3, r5)
            goto L_0x01f1
        L_0x01c2:
            r0 = move-exception
            r11 = r18
        L_0x01c5:
            r2 = r23
            com.google.appinventor.components.runtime.Form r1 = r11.form
            int r3 = r0.getErrorCode()
            java.lang.Object[] r4 = r0.getArguments()
            r1.dispatchErrorOccurredEvent(r11, r2, r3, r4)
            goto L_0x01f1
        L_0x01d5:
            r0 = move-exception
            r4 = 0
            r11 = r18
        L_0x01d9:
            r2 = r23
            com.google.appinventor.components.runtime.Form r1 = r11.form
            int r3 = r0.getErrorMessageNumber()
            java.lang.Object[] r4 = new java.lang.Object[r4]
            r1.dispatchErrorOccurredEvent(r11, r2, r3, r4)
            goto L_0x01f1
        L_0x01e7:
            r0 = move-exception
            r11 = r18
        L_0x01ea:
            r2 = r23
            com.google.appinventor.components.runtime.Form r1 = r11.form
            r1.dispatchPermissionDeniedEvent((com.google.appinventor.components.runtime.Component) r11, (java.lang.String) r2, (com.google.appinventor.components.runtime.errors.PermissionException) r0)
        L_0x01f1:
        L_0x01f2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.Web.performRequest(com.google.appinventor.components.runtime.Web$CapturedProperties, byte[], java.lang.String, java.lang.String, java.lang.String):void");
    }

    private static HttpURLConnection openConnection(CapturedProperties webProps, String httpVerb) throws IOException, ClassCastException, ProtocolException {
        HttpURLConnection connection = (HttpURLConnection) webProps.url.openConnection();
        connection.setConnectTimeout(webProps.timeout);
        connection.setReadTimeout(webProps.timeout);
        if (httpVerb.equals("PUT") || httpVerb.equals("PATCH") || httpVerb.equals("DELETE")) {
            connection.setRequestMethod(httpVerb);
        }
        for (Map.Entry<String, List<String>> header : webProps.requestHeaders.entrySet()) {
            String name = header.getKey();
            for (String value : header.getValue()) {
                connection.addRequestProperty(name, value);
            }
        }
        if (webProps.cookies != null) {
            for (Map.Entry<String, List<String>> cookie : webProps.cookies.entrySet()) {
                String name2 = cookie.getKey();
                for (String value2 : cookie.getValue()) {
                    connection.addRequestProperty(name2, value2);
                }
            }
        }
        return connection;
    }

    private static void writeRequestData(HttpURLConnection connection, byte[] postData) throws IOException {
        connection.setDoOutput(true);
        connection.setFixedLengthStreamingMode(postData.length);
        BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
        try {
            out.write(postData, 0, postData.length);
            out.flush();
        } finally {
            out.close();
        }
    }

    private void writeRequestFile(HttpURLConnection connection, String path) throws IOException {
        BufferedOutputStream out;
        BufferedInputStream in = new BufferedInputStream(MediaUtil.openMedia(this.form, path));
        try {
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);
            out = new BufferedOutputStream(connection.getOutputStream());
            while (true) {
                int b = in.read();
                if (b == -1) {
                    out.flush();
                    out.close();
                    in.close();
                    return;
                }
                out.write(b);
            }
        } catch (Throwable th) {
            in.close();
            throw th;
        }
    }

    private static String getResponseType(HttpURLConnection connection) {
        String responseType = connection.getContentType();
        return responseType != null ? responseType : "";
    }

    private void processResponseCookies(HttpURLConnection connection) {
        if (this.allowCookies && this.cookieHandler != null) {
            try {
                this.cookieHandler.put(connection.getURL().toURI(), connection.getHeaderFields());
            } catch (IOException | URISyntaxException e) {
            }
        }
    }

    private static String getResponseContent(HttpURLConnection connection, String encodingProperty) throws IOException {
        StringBuilder sb;
        String encoding = connection.getContentEncoding();
        if (encoding == null) {
            if (encodingProperty == null || encodingProperty.isEmpty()) {
                encoding = "UTF-8";
            } else {
                encoding = encodingProperty;
            }
        }
        InputStreamReader reader = new InputStreamReader(getConnectionStream(connection), encoding);
        try {
            int contentLength = connection.getContentLength();
            if (contentLength != -1) {
                sb = new StringBuilder(contentLength);
            } else {
                sb = new StringBuilder();
            }
            char[] buf = new char[1024];
            while (true) {
                int read = reader.read(buf);
                int read2 = read;
                if (read == -1) {
                    return sb.toString();
                }
                sb.append(buf, 0, read2);
            }
        } finally {
            reader.close();
        }
    }

    private String saveResponseContent(HttpURLConnection connection, String responseFileName2, String responseType) throws IOException {
        BufferedOutputStream out;
        File file = createFile(responseFileName2, responseType);
        File parent = file.getParentFile();
        if (parent.exists() || parent.mkdirs()) {
            BufferedInputStream in = new BufferedInputStream(getConnectionStream(connection), 4096);
            try {
                out = new BufferedOutputStream(new FileOutputStream(file), 4096);
                while (true) {
                    int b = in.read();
                    if (b == -1) {
                        out.flush();
                        out.close();
                        in.close();
                        return file.getAbsolutePath();
                    }
                    out.write(b);
                }
            } catch (Throwable th) {
                in.close();
                throw th;
            }
        } else {
            throw new DispatchableError(ErrorMessages.ERROR_CANNOT_MAKE_DIRECTORY, parent.getAbsolutePath());
        }
    }

    private static InputStream getConnectionStream(HttpURLConnection connection) throws SocketTimeoutException {
        try {
            return connection.getInputStream();
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (IOException e2) {
            return connection.getErrorStream();
        }
    }

    private File createFile(String fileName, String responseType) throws IOException, FileUtil.FileException {
        if (!TextUtils.isEmpty(fileName)) {
            return FileUtil.getExternalFile(this.form, fileName);
        }
        int indexOfSemicolon = responseType.indexOf(59);
        if (indexOfSemicolon != -1) {
            responseType = responseType.substring(0, indexOfSemicolon);
        }
        String extension = mimeTypeToExtension.get(responseType);
        if (extension == null) {
            extension = "tmp";
        }
        return FileUtil.getDownloadFile(this.form, extension);
    }

    /* access modifiers changed from: private */
    public static Map<String, List<String>> processRequestHeaders(YailList list) throws InvalidRequestHeadersException {
        Map<String, List<String>> requestHeadersMap = Maps.newHashMap();
        int i = 0;
        while (i < list.size()) {
            Object item = list.getObject(i);
            if (item instanceof YailList) {
                YailList sublist = (YailList) item;
                if (sublist.size() == 2) {
                    String fieldName = sublist.getObject(0).toString();
                    Object fieldValues = sublist.getObject(1);
                    String key = fieldName;
                    List<String> values = Lists.newArrayList();
                    if (fieldValues instanceof YailList) {
                        YailList multipleFieldsValues = (YailList) fieldValues;
                        for (int j = 0; j < multipleFieldsValues.size(); j++) {
                            values.add(multipleFieldsValues.getObject(j).toString());
                        }
                    } else {
                        values.add(fieldValues.toString());
                    }
                    requestHeadersMap.put(key, values);
                    i++;
                } else {
                    throw new InvalidRequestHeadersException(ErrorMessages.ERROR_WEB_REQUEST_HEADER_NOT_TWO_ELEMENTS, i + 1);
                }
            } else {
                throw new InvalidRequestHeadersException(ErrorMessages.ERROR_WEB_REQUEST_HEADER_NOT_LIST, i + 1);
            }
        }
        return requestHeadersMap;
    }

    private CapturedProperties capturePropertyValues(String functionName) {
        try {
            return new CapturedProperties(this);
        } catch (MalformedURLException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_WEB_MALFORMED_URL, this.urlString);
            return null;
        } catch (InvalidRequestHeadersException e2) {
            this.form.dispatchErrorOccurredEvent(this, functionName, e2.errorNumber, Integer.valueOf(e2.index));
            return null;
        }
    }

    public Future<YailList> getDataValue(final YailList key) {
        final FutureTask<Void> currentTask = this.lastTask;
        FutureTask<YailList> getDataValueTask = new FutureTask<>(new Callable<YailList>() {
            public YailList call() throws Exception {
                if (currentTask != null && !currentTask.isDone() && !currentTask.isCancelled()) {
                    try {
                        currentTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e2) {
                        e2.printStackTrace();
                    }
                }
                return Web.this.getColumns(key);
            }
        });
        AsynchUtil.runAsynchronously(getDataValueTask);
        return getDataValueTask;
    }

    private void updateColumns(String responseContent, String responseType) {
        if (responseType.contains("json")) {
            try {
                this.columns = JsonUtil.getColumnsFromJson(responseContent);
            } catch (JSONException e) {
            }
        } else if (responseType.contains("csv") || responseType.startsWith("text/")) {
            try {
                this.columns = CsvUtil.fromCsvTable(responseContent);
                this.columns = ChartDataSourceUtil.getTranspose(this.columns);
            } catch (Exception e2) {
                this.columns = new YailList();
            }
        }
    }

    public YailList getColumn(String column) {
        for (int i = 0; i < this.columns.size(); i++) {
            YailList list = (YailList) this.columns.getObject(i);
            if (!list.isEmpty() && list.getString(0).equals(column)) {
                return list;
            }
        }
        return new YailList();
    }

    public YailList getColumns(YailList keyColumns) {
        ArrayList<YailList> resultingColumns = new ArrayList<>();
        for (int i = 0; i < keyColumns.size(); i++) {
            resultingColumns.add(getColumn(keyColumns.getString(i)));
        }
        return YailList.makeList((List) resultingColumns);
    }

    public void addDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.add(dataComponent);
    }

    public void removeDataObserver(DataSourceChangeListener dataComponent) {
        this.dataSourceObservers.remove(dataComponent);
    }

    public void notifyDataObservers(YailList key, Object newValue) {
        Iterator<DataSourceChangeListener> it = this.dataSourceObservers.iterator();
        while (it.hasNext()) {
            it.next().onDataSourceValueChange(this, (String) null, this.columns);
        }
    }
}
