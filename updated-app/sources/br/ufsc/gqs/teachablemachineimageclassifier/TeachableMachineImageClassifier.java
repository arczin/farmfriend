package br.ufsc.gqs.teachablemachineimageclassifier;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesAssets;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.OnClearListener;
import com.google.appinventor.components.runtime.OnPauseListener;
import com.google.appinventor.components.runtime.OnResumeListener;
import com.google.appinventor.components.runtime.WebViewer;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.YailDictionary;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

@DesignerComponent(category = ComponentCategory.EXTENSION, description = "Component that classifies images using a user trained model from the Google Teachable Machine. You must provide a WebViewer component in the TeachableMachineImageClassifier component's WebViewer property in order for classification to work.", iconName = "aiwebres/tmic.png", nonVisible = true, version = 20210315)
@SimpleObject(external = true)
@UsesAssets(fileNames = "teachable_machine_image_classifier.html, teachable_machine_image_classifier.js, tfjs-0.13.2.js, jquery-3.5.1.min.js, teachablemachine-image.min.js, tf.min.js")
@UsesPermissions(permissionNames = "android.permission.INTERNET, android.permission.CAMERA")
public final class TeachableMachineImageClassifier extends AndroidNonvisibleComponent implements Component, OnPauseListener, OnResumeListener, OnClearListener {
    private static final int ERROR_MODEL_REQUIRED = -9;
    private static final String ERROR_WEBVIEWER_NOT_SET = "You must specify a WebViewer using the WebViewer designer property before you can call %1s";
    private static final int ERROR_WEBVIEWER_REQUIRED = -7;
    /* access modifiers changed from: private */
    public static final String LOG_TAG = TeachableMachineImageClassifier.class.getSimpleName();
    private List<String> labels = Collections.emptyList();
    private boolean running = false;
    private String urlGTMModel = null;
    /* access modifiers changed from: private */
    public WebView webview = null;

    public TeachableMachineImageClassifier(Form form) {
        super(form);
        requestHardwareAcceleration(form);
        WebView.setWebContentsDebuggingEnabled(true);
        Log.d(LOG_TAG, "Created TeachableMachineImageClassifier component");
    }

    /* access modifiers changed from: private */
    public void configureWebView(WebView webview2) {
        this.webview = webview2;
        webview2.getSettings().setJavaScriptEnabled(true);
        webview2.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webview2.addJavascriptInterface(new JsObject(), "TeachableMachineImageClassifier");
        webview2.setWebChromeClient(new WebChromeClient() {
            public void onPermissionRequest(PermissionRequest request) {
                Log.d(TeachableMachineImageClassifier.LOG_TAG, "onPermissionRequest called");
                for (String r : request.getResources()) {
                    if (r.equals("android.webkit.resource.VIDEO_CAPTURE")) {
                        request.grant(new String[]{"android.webkit.resource.VIDEO_CAPTURE"});
                    }
                }
            }
        });
    }

    public void Initialize() {
        Log.d(LOG_TAG, "webview = " + this.webview);
        if (this.webview == null) {
            this.form.dispatchErrorOccurredEvent(this, "WebViewer", ErrorMessages.ERROR_EXTENSION_ERROR, -7, LOG_TAG, "You must specify a WebViewer component in the WebViewer property.");
        }
        Log.d(LOG_TAG, "modelPath = " + this.urlGTMModel);
        if (this.urlGTMModel == null) {
            this.form.dispatchErrorOccurredEvent(this, "URL_Model", ErrorMessages.ERROR_EXTENSION_ERROR, -9, LOG_TAG, "You must provide a model URL in the URL_Model property");
        }
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty
    public void UrlModel(String model) {
        this.urlGTMModel = model;
    }

    @DesignerProperty(editorType = "component:com.google.appinventor.runtime.components.WebViewer")
    @SimpleProperty(userVisible = false)
    public void WebViewer(final WebViewer webviewer) {
        Runnable next = new Runnable() {
            public void run() {
                if (webviewer != null) {
                    TeachableMachineImageClassifier.this.configureWebView((WebView) webviewer.getView());
                    TeachableMachineImageClassifier.this.webview.requestLayout();
                    try {
                        Log.d(TeachableMachineImageClassifier.LOG_TAG, "isHardwareAccelerated? " + TeachableMachineImageClassifier.this.webview.isHardwareAccelerated());
                        TeachableMachineImageClassifier.this.webview.loadUrl(TeachableMachineImageClassifier.this.form.getAssetPathForExtension(TeachableMachineImageClassifier.this, "teachable_machine_image_classifier.html"));
                    } catch (FileNotFoundException e) {
                        Log.d(TeachableMachineImageClassifier.LOG_TAG, e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };
        if (Helper.shouldAskForPermission(this.form)) {
            Helper.askForPermission(this, next);
        } else {
            next.run();
        }
    }

    @SimpleFunction(description = "Performs the classification")
    public void ClassifyVideoData() {
        assertWebView("ClassifyVideoData");
        this.webview.evaluateJavascript("classifyVideoData(\"\");", (ValueCallback) null);
    }

    @SimpleFunction(description = "Stop the webcam")
    public void StopWebcam() {
        assertWebView("StopWebcam");
        this.webview.evaluateJavascript("stopWebcam(\"\");", (ValueCallback) null);
    }

    @SimpleEvent(description = "Event indicating that the classifier is ready.")
    public void ClassifierReady() {
        this.webview.evaluateJavascript("setUrlModel(\"" + this.urlGTMModel + "\");", (ValueCallback) null);
        EventDispatcher.dispatchEvent(this, "ClassifierReady", new Object[0]);
    }

    @SimpleEvent(description = "Event indicating that classification has finished successfully. Result is of the form [[class1, confidence1], [class2, confidence2], ..., [class10, confidence10]].")
    public void GotClassification(YailDictionary result) {
        EventDispatcher.dispatchEvent(this, "GotClassification", result);
    }

    public void onPause() {
        this.webview.evaluateJavascript("stopWebcam(\"\");", (ValueCallback) null);
    }

    public void onResume() {
        this.webview.evaluateJavascript("startWebcam(\"\");", (ValueCallback) null);
    }

    public void onClear() {
        this.webview.evaluateJavascript("stopVideo();", (ValueCallback) null);
    }

    /* access modifiers changed from: package-private */
    public Form getForm() {
        return this.form;
    }

    private static void requestHardwareAcceleration(Activity activity) {
        activity.getWindow().setFlags(16777216, 16777216);
    }

    private void assertWebView(String method) {
        if (this.webview == null) {
            throw new RuntimeException(String.format(ERROR_WEBVIEWER_NOT_SET, new Object[]{method}));
        }
    }

    private class JsObject {
        private JsObject() {
        }

        @JavascriptInterface
        public void ready() {
            TeachableMachineImageClassifier.this.form.runOnUiThread(new Runnable() {
                public void run() {
                    TeachableMachineImageClassifier.this.ClassifierReady();
                }
            });
        }

        @JavascriptInterface
        public void reportResult(String result) {
            Log.d(TeachableMachineImageClassifier.LOG_TAG, "Entered reportResult: " + result);
            try {
                Log.d(TeachableMachineImageClassifier.LOG_TAG, "Entered try of reportResult");
                JSONArray list = new JSONArray(result);
                final YailDictionary resultDict = new YailDictionary();
                for (int i = 0; i < list.length(); i++) {
                    JSONArray pair = list.getJSONArray(i);
                    resultDict.put(pair.getString(0), Double.valueOf(pair.getDouble(1)));
                }
                TeachableMachineImageClassifier.this.form.runOnUiThread(new Runnable() {
                    public void run() {
                        TeachableMachineImageClassifier.this.GotClassification(resultDict);
                    }
                });
            } catch (JSONException e) {
                Log.d(TeachableMachineImageClassifier.LOG_TAG, "Entered catch of reportResult");
                e.printStackTrace();
            }
        }
    }
}
