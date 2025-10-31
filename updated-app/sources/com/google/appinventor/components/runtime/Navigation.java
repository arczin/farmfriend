package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.TransportMethod;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeoJSONUtil;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.google.appinventor.components.runtime.util.YailList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.osmdroid.util.GeoPoint;

@DesignerComponent(category = ComponentCategory.MAPS, description = "Navigation", iconName = "images/navigation.png", nonVisible = true, version = 2)
@UsesLibraries({"osmdroid.jar"})
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public class Navigation extends AndroidNonvisibleComponent implements Component {
    public static final String OPEN_ROUTE_SERVICE_URL = "https://api.openrouteservice.org/v2/directions/";
    private static final String TAG = "Navigation";
    private String apiKey = "";
    private GeoPoint endLocation = new GeoPoint(0.0d, 0.0d);
    private String language = "en";
    /* access modifiers changed from: private */
    public YailDictionary lastResponse = YailDictionary.makeDictionary();
    private TransportMethod method = TransportMethod.Foot;
    private String serviceUrl = OPEN_ROUTE_SERVICE_URL;
    private GeoPoint startLocation = new GeoPoint(0.0d, 0.0d);

    public Navigation(ComponentContainer container) {
        super(container.$form());
    }

    @SimpleFunction(description = "Request directions from the routing service.")
    public void RequestDirections() {
        if (this.apiKey.equals("")) {
            this.form.dispatchErrorOccurredEvent(this, "Authorization", ErrorMessages.ERROR_INVALID_API_KEY, new Object[0]);
            return;
        }
        final GeoPoint startLocation2 = this.startLocation;
        final GeoPoint endLocation2 = this.endLocation;
        final TransportMethod method2 = this.method;
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                try {
                    Navigation.this.performRequest(startLocation2, endLocation2, method2);
                } catch (IOException e) {
                    Navigation.this.form.dispatchErrorOccurredEvent(Navigation.this, "RequestDirections", 0, new Object[0]);
                } catch (JSONException e2) {
                    Navigation.this.form.dispatchErrorOccurredEvent(Navigation.this, "RequestDirections", 0, new Object[0]);
                }
            }
        });
    }

    @SimpleProperty(userVisible = false)
    public void ServiceURL(String url) {
        this.serviceUrl = url;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "API Key for Open Route Service.")
    public void ApiKey(String key) {
        this.apiKey = key;
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "latitude")
    @SimpleProperty
    public void StartLatitude(double latitude) {
        if (GeometryUtil.isValidLatitude(latitude)) {
            this.startLocation.setLatitude(latitude);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "StartLatitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The latitude of the start location.")
    public double StartLatitude() {
        return this.startLocation.getLatitude();
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "longitude")
    @SimpleProperty
    public void StartLongitude(double longitude) {
        if (GeometryUtil.isValidLongitude(longitude)) {
            this.startLocation.setLongitude(longitude);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "StartLongitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The longitude of the start location.")
    public double StartLongitude() {
        return this.startLocation.getLongitude();
    }

    @SimpleProperty(description = "Set the start location.")
    public void StartLocation(MapFactory.MapFeature feature) {
        GeoPoint point = feature.getCentroid();
        double latitude = point.getLatitude();
        double longitude = point.getLongitude();
        if (!GeometryUtil.isValidLatitude(latitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetStartLocation", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        } else if (!GeometryUtil.isValidLongitude(longitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetStartLocation", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        } else {
            this.startLocation.setCoords(latitude, longitude);
        }
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "latitude")
    @SimpleProperty
    public void EndLatitude(double latitude) {
        if (GeometryUtil.isValidLatitude(latitude)) {
            this.endLocation.setLatitude(latitude);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "EndLatitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The latitude of the end location.")
    public double EndLatitude() {
        return this.endLocation.getLatitude();
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "longitude")
    @SimpleProperty
    public void EndLongitude(double longitude) {
        if (GeometryUtil.isValidLongitude(longitude)) {
            this.endLocation.setLongitude(longitude);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "EndLongitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The longitude of the end location.")
    public double EndLongitude() {
        return this.endLocation.getLongitude();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String TransportationMethod() {
        return TransportationMethodAbstract().toUnderlyingValue();
    }

    public TransportMethod TransportationMethodAbstract() {
        return this.method;
    }

    public void TransportationMethodAbstract(TransportMethod method2) {
        this.method = method2;
    }

    @DesignerProperty(defaultValue = "foot-walking", editorType = "navigation_method")
    @SimpleProperty(description = "The transportation method used for determining the route.")
    public void TransportationMethod(@Options(TransportMethod.class) String method2) {
        TransportMethod t = TransportMethod.fromUnderlyingValue(method2);
        if (t != null) {
            TransportationMethodAbstract(t);
        }
    }

    @SimpleProperty(description = "Set the end location.")
    public void EndLocation(MapFactory.MapFeature feature) {
        GeoPoint point = feature.getCentroid();
        double latitude = point.getLatitude();
        double longitude = point.getLongitude();
        if (!GeometryUtil.isValidLatitude(latitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetEndLocation", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        } else if (!GeometryUtil.isValidLongitude(longitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetEndLocation", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        } else {
            this.endLocation.setCoords(latitude, longitude);
        }
    }

    @DesignerProperty(defaultValue = "en")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The language to use for textual directions.")
    public void Language(String language2) {
        this.language = language2;
    }

    @SimpleProperty
    public String Language() {
        return this.language;
    }

    @SimpleProperty(description = "Content of the last response as a dictionary.")
    public YailDictionary ResponseContent() {
        return this.lastResponse;
    }

    @SimpleEvent(description = "Event triggered when the Openrouteservice returns the directions.")
    public void GotDirections(YailList directions, YailList points, double distance, double duration) {
        Log.d(TAG, "GotDirections");
        EventDispatcher.dispatchEvent(this, "GotDirections", directions, points, Double.valueOf(distance), Double.valueOf(duration));
    }

    /* JADX WARNING: type inference failed for: r13v1 */
    /* JADX WARNING: type inference failed for: r13v2 */
    /* JADX WARNING: type inference failed for: r13v6 */
    /* JADX WARNING: type inference failed for: r13v8 */
    /* JADX WARNING: type inference failed for: r13v9 */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void performRequest(org.osmdroid.util.GeoPoint r26, org.osmdroid.util.GeoPoint r27, com.google.appinventor.components.common.TransportMethod r28) throws java.io.IOException, org.json.JSONException {
        /*
            r25 = this;
            r10 = r25
            java.lang.String r11 = "RequestDirections"
            java.lang.String r0 = r10.serviceUrl
            java.lang.String r1 = r28.toUnderlyingValue()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r1 = "/geojson"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r12 = r0.toString()
            java.net.URL r0 = new java.net.URL
            r0.<init>(r12)
            r13 = r0
            java.net.URLConnection r0 = r13.openConnection()
            r14 = r0
            java.net.HttpURLConnection r14 = (java.net.HttpURLConnection) r14
            r15 = 1
            r14.setDoInput(r15)
            r14.setDoOutput(r15)
            java.lang.String r0 = "Content-Type"
            java.lang.String r1 = "application/json; charset=UTF-8"
            r14.setRequestProperty(r0, r1)
            java.lang.String r0 = "POST"
            r14.setRequestMethod(r0)
            java.lang.String r0 = "Authorization"
            java.lang.String r1 = r10.apiKey
            r14.setRequestProperty(r0, r1)
            r3 = 0
            java.lang.Double[][] r0 = r25.getCoordinates(r26, r27)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r0 = com.google.appinventor.components.runtime.util.JsonUtil.getJsonRepresentation(r0)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r1 = r10.language     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r2.<init>()     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r4 = "{\"coordinates\": "
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r2 = ", \"language\": \""
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r1 = "\"}"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r2 = r0
            java.lang.String r0 = "UTF-8"
            byte[] r0 = r2.getBytes(r0)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r1 = r0
            int r0 = r1.length     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r14.setFixedLengthStreamingMode(r0)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.io.BufferedOutputStream r0 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.io.OutputStream r4 = r14.getOutputStream()     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r0.<init>(r4)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r5 = r0
            int r0 = r1.length     // Catch:{ all -> 0x017b }
            r5.write(r1, r3, r0)     // Catch:{ all -> 0x017b }
            r5.flush()     // Catch:{ all -> 0x017b }
            r5.close()     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            int r0 = r14.getResponseCode()     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r4 = 200(0xc8, float:2.8E-43)
            r6 = 2
            if (r0 == r4) goto L_0x00cd
            com.google.appinventor.components.runtime.Form r0 = r10.form     // Catch:{ Exception -> 0x00c5, all -> 0x00be }
            int r4 = r14.getResponseCode()     // Catch:{ Exception -> 0x00c5, all -> 0x00be }
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ Exception -> 0x00c5, all -> 0x00be }
            java.lang.String r7 = r14.getResponseMessage()     // Catch:{ Exception -> 0x00c5, all -> 0x00be }
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Exception -> 0x00c5, all -> 0x00be }
            r6[r3] = r4     // Catch:{ Exception -> 0x00c5, all -> 0x00be }
            r6[r15] = r7     // Catch:{ Exception -> 0x00c5, all -> 0x00be }
            r4 = 4003(0xfa3, float:5.61E-42)
            r0.dispatchErrorOccurredEvent(r10, r11, r4, r6)     // Catch:{ Exception -> 0x00c5, all -> 0x00be }
            r14.disconnect()
            return
        L_0x00be:
            r0 = move-exception
            r21 = r12
            r24 = r13
            goto L_0x01b3
        L_0x00c5:
            r0 = move-exception
            r21 = r12
            r24 = r13
            r13 = 0
            goto L_0x019a
        L_0x00cd:
            java.lang.String r0 = getResponseContent(r14)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r4 = "Navigation"
            android.util.Log.d(r4, r0)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.Object r4 = com.google.appinventor.components.runtime.util.JsonUtil.getObjectFromJson(r0, r15)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            com.google.appinventor.components.runtime.util.YailDictionary r4 = (com.google.appinventor.components.runtime.util.YailDictionary) r4     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r7 = "features"
            java.lang.Object r7 = r4.get(r7)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            com.google.appinventor.components.runtime.util.YailList r7 = (com.google.appinventor.components.runtime.util.YailList) r7     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r8 = r7
            int r7 = r8.size()     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            if (r7 <= 0) goto L_0x0160
            java.lang.Object r7 = r8.getObject(r3)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            com.google.appinventor.components.runtime.util.YailDictionary r7 = (com.google.appinventor.components.runtime.util.YailDictionary) r7     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r9 = r7
            java.lang.String[] r6 = new java.lang.String[r6]     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r7 = "properties"
            r6[r3] = r7     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r7 = "summary"
            r6[r15] = r7     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.util.List r6 = java.util.Arrays.asList(r6)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.Object r6 = r9.getObjectAtKeyPath(r6)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            com.google.appinventor.components.runtime.util.YailDictionary r6 = (com.google.appinventor.components.runtime.util.YailDictionary) r6     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.String r7 = "distance"
            java.lang.Object r7 = r6.get(r7)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.Double r7 = (java.lang.Double) r7     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            double r16 = r7.doubleValue()     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r15 = r6
            r6 = r16
            java.lang.String r3 = "duration"
            java.lang.Object r3 = r15.get(r3)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            java.lang.Double r3 = (java.lang.Double) r3     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            double r18 = r3.doubleValue()     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r17 = r8
            r3 = r9
            r8 = r18
            java.util.List r18 = r10.getDirections(r3)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            com.google.appinventor.components.runtime.util.YailList r18 = com.google.appinventor.components.runtime.util.YailList.makeList((java.util.List) r18)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r19 = r4
            r4 = r18
            com.google.appinventor.components.runtime.util.YailList r18 = r10.getLineStringCoords(r3)     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r20 = r5
            r5 = r18
            r18 = r0
            com.google.appinventor.components.runtime.Form r0 = r10.form     // Catch:{ Exception -> 0x0194, all -> 0x018e }
            r21 = r12
            com.google.appinventor.components.runtime.Navigation$2 r12 = new com.google.appinventor.components.runtime.Navigation$2     // Catch:{ Exception -> 0x015b, all -> 0x0157 }
            r22 = r1
            r1 = r12
            r23 = r2
            r2 = r25
            r16 = r3
            r24 = r13
            r13 = 0
            r3 = r19
            r1.<init>(r3, r4, r5, r6, r8)     // Catch:{ Exception -> 0x018c }
            r0.runOnUiThread(r12)     // Catch:{ Exception -> 0x018c }
            goto L_0x01ad
        L_0x0157:
            r0 = move-exception
            r24 = r13
            goto L_0x01b3
        L_0x015b:
            r0 = move-exception
            r24 = r13
            r13 = 0
            goto L_0x019a
        L_0x0160:
            r18 = r0
            r22 = r1
            r23 = r2
            r19 = r4
            r20 = r5
            r17 = r8
            r21 = r12
            r24 = r13
            r13 = 0
            com.google.appinventor.components.runtime.Form r0 = r10.form     // Catch:{ Exception -> 0x018c }
            java.lang.Object[] r1 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x018c }
            r2 = 4004(0xfa4, float:5.611E-42)
            r0.dispatchErrorOccurredEvent(r10, r11, r2, r1)     // Catch:{ Exception -> 0x018c }
            goto L_0x01ad
        L_0x017b:
            r0 = move-exception
            r22 = r1
            r23 = r2
            r20 = r5
            r21 = r12
            r24 = r13
            r13 = 0
            r20.close()     // Catch:{ Exception -> 0x018c }
            throw r0     // Catch:{ Exception -> 0x018c }
        L_0x018c:
            r0 = move-exception
            goto L_0x019a
        L_0x018e:
            r0 = move-exception
            r21 = r12
            r24 = r13
            goto L_0x01b3
        L_0x0194:
            r0 = move-exception
            r21 = r12
            r24 = r13
            r13 = 0
        L_0x019a:
            com.google.appinventor.components.runtime.Form r1 = r10.form     // Catch:{ all -> 0x01b2 }
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x01b2 }
            r3 = 1
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x01b2 }
            r3[r13] = r2     // Catch:{ all -> 0x01b2 }
            r2 = 4002(0xfa2, float:5.608E-42)
            r1.dispatchErrorOccurredEvent(r10, r11, r2, r3)     // Catch:{ all -> 0x01b2 }
            r0.printStackTrace()     // Catch:{ all -> 0x01b2 }
        L_0x01ad:
            r14.disconnect()
            return
        L_0x01b2:
            r0 = move-exception
        L_0x01b3:
            r14.disconnect()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.Navigation.performRequest(org.osmdroid.util.GeoPoint, org.osmdroid.util.GeoPoint, com.google.appinventor.components.common.TransportMethod):void");
    }

    private static String getResponseContent(HttpURLConnection connection) throws IOException {
        StringBuilder sb;
        String encoding = connection.getContentEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        Log.d(TAG, Integer.toString(connection.getResponseCode()));
        InputStreamReader reader = new InputStreamReader(connection.getInputStream(), encoding);
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

    private Double[][] getCoordinates(GeoPoint startLocation2, GeoPoint endLocation2) {
        int[] iArr = new int[2];
        iArr[1] = 2;
        iArr[0] = 2;
        Double[][] coords = (Double[][]) Array.newInstance(Double.class, iArr);
        coords[0][0] = Double.valueOf(startLocation2.getLongitude());
        coords[0][1] = Double.valueOf(startLocation2.getLatitude());
        coords[1][0] = Double.valueOf(endLocation2.getLongitude());
        coords[1][1] = Double.valueOf(endLocation2.getLatitude());
        return coords;
    }

    private YailList getLineStringCoords(YailDictionary feature) {
        return GeoJSONUtil.swapCoordinates((YailList) feature.getObjectAtKeyPath(Arrays.asList(new String[]{"geometry", "coordinates"})));
    }

    private List<?> getDirections(YailDictionary feature) {
        Object obj = YailDictionary.ALL;
        return YailDictionary.walkKeyPath(feature, Arrays.asList(new Object[]{"properties", "segments", obj, "steps", obj, "instruction"}));
    }
}
