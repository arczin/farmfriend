package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.WebViewer;
import java.util.ArrayList;
import java.util.List;

public class EclairUtil {
    private EclairUtil() {
    }

    public static void overridePendingTransitions(Activity activity, int enterAnim, int exitAnim) {
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    public static void setupWebViewGeoLoc(final WebViewer caller, WebView webview, final Activity activity) {
        webview.getSettings().setGeolocationDatabasePath(activity.getFilesDir().getAbsolutePath());
        webview.getSettings().setDatabaseEnabled(true);
        webview.setWebChromeClient(new WebChromeClient() {
            /* access modifiers changed from: private */
            public boolean askedPermission = false;

            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                if (!WebViewer.this.PromptforPermission()) {
                    callback.invoke(origin, true, true);
                } else {
                    EclairUtil.showPermissionDialog(activity, origin, "location", callback);
                }
            }

            public void onPermissionRequest(PermissionRequest request) {
                List<String> permissionsNeeded = new ArrayList<>();
                for (String resource : request.getResources()) {
                    if (resource.equals("android.webkit.resource.VIDEO_CAPTURE")) {
                        if (!WebViewer.this.UsesCamera()) {
                            request.deny();
                            return;
                        }
                        permissionsNeeded.add("android.permission.CAMERA");
                    } else if (!resource.equals("android.webkit.resource.AUDIO_CAPTURE")) {
                        request.deny();
                        return;
                    } else if (!WebViewer.this.UsesMicrophone()) {
                        request.deny();
                        return;
                    } else {
                        permissionsNeeded.add("android.permission.RECORD_AUDIO");
                    }
                }
                if (activity instanceof Form) {
                    final PermissionRequest permissionRequest = request;
                    ((Form) activity).askPermission(new BulkPermissionRequest(WebViewer.this, "WebView Permission Request", (String[]) permissionsNeeded.toArray(new String[0])) {
                        public void onGranted() {
                            if (!WebViewer.this.PromptforPermission() || AnonymousClass1.this.askedPermission) {
                                permissionRequest.grant(permissionRequest.getResources());
                            } else {
                                EclairUtil.showPermissionDialog(activity, permissionRequest.getOrigin().getHost(), "camera and/or microphone", new GeolocationPermissions.Callback() {
                                    public void invoke(String origin, boolean allow, boolean remember) {
                                        if (allow) {
                                            AnonymousClass1.this.askedPermission = true;
                                            permissionRequest.grant(permissionRequest.getResources());
                                            return;
                                        }
                                        permissionRequest.deny();
                                    }
                                });
                            }
                        }

                        public void onDenied(String[] permissions) {
                            permissionRequest.deny();
                            super.onDenied(permissions);
                        }
                    });
                    return;
                }
                request.deny();
            }
        });
    }

    public static void clearWebViewGeoLoc() {
        GeolocationPermissions.getInstance().clearAll();
    }

    public static String getInstallerPackageName(String pname, Activity form) {
        return form.getPackageManager().getInstallerPackageName(pname);
    }

    public static void disableSuggestions(EditText textview) {
        textview.setInputType(textview.getInputType() | 524288);
    }

    /* access modifiers changed from: private */
    public static void showPermissionDialog(Activity activity, String origin, String item, final GeolocationPermissions.Callback callback) {
        final String theOrigin = origin;
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Permission Request");
        if (origin.equals("file://")) {
            origin = "This Application";
        }
        alertDialog.setMessage(origin + " would like to access your " + item + ".");
        alertDialog.setButton(-1, "Allow", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callback.invoke(theOrigin, true, true);
            }
        });
        alertDialog.setButton(-2, "Refuse", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                callback.invoke(theOrigin, false, true);
            }
        });
        alertDialog.show();
    }
}
