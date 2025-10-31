package androidx.appcompat.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import androidx.appcompat.R;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.collection.ArrayMap;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = {16843375};
    private final Object[] mConstructorArgs = new Object[2];

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View createView(android.view.View r4, java.lang.String r5, android.content.Context r6, android.util.AttributeSet r7, boolean r8, boolean r9, boolean r10, boolean r11) {
        /*
            r3 = this;
            r0 = r6
            if (r8 == 0) goto L_0x0009
            if (r4 == 0) goto L_0x0009
            android.content.Context r6 = r4.getContext()
        L_0x0009:
            if (r9 != 0) goto L_0x000d
            if (r10 == 0) goto L_0x0011
        L_0x000d:
            android.content.Context r6 = themifyContext(r6, r7, r9, r10)
        L_0x0011:
            if (r11 == 0) goto L_0x0017
            android.content.Context r6 = androidx.appcompat.widget.TintContextWrapper.wrap(r6)
        L_0x0017:
            r1 = 0
            int r2 = r5.hashCode()
            switch(r2) {
                case -1946472170: goto L_0x00a0;
                case -1455429095: goto L_0x0095;
                case -1346021293: goto L_0x008a;
                case -938935918: goto L_0x0080;
                case -937446323: goto L_0x0076;
                case -658531749: goto L_0x006b;
                case -339785223: goto L_0x0061;
                case 776382189: goto L_0x0057;
                case 1125864064: goto L_0x004d;
                case 1413872058: goto L_0x0042;
                case 1601505219: goto L_0x0037;
                case 1666676343: goto L_0x002c;
                case 2001146706: goto L_0x0021;
                default: goto L_0x001f;
            }
        L_0x001f:
            goto L_0x00ab
        L_0x0021:
            java.lang.String r2 = "Button"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 2
            goto L_0x00ac
        L_0x002c:
            java.lang.String r2 = "EditText"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 3
            goto L_0x00ac
        L_0x0037:
            java.lang.String r2 = "CheckBox"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 6
            goto L_0x00ac
        L_0x0042:
            java.lang.String r2 = "AutoCompleteTextView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 9
            goto L_0x00ac
        L_0x004d:
            java.lang.String r2 = "ImageView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 1
            goto L_0x00ac
        L_0x0057:
            java.lang.String r2 = "RadioButton"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 7
            goto L_0x00ac
        L_0x0061:
            java.lang.String r2 = "Spinner"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 4
            goto L_0x00ac
        L_0x006b:
            java.lang.String r2 = "SeekBar"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 12
            goto L_0x00ac
        L_0x0076:
            java.lang.String r2 = "ImageButton"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 5
            goto L_0x00ac
        L_0x0080:
            java.lang.String r2 = "TextView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 0
            goto L_0x00ac
        L_0x008a:
            java.lang.String r2 = "MultiAutoCompleteTextView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 10
            goto L_0x00ac
        L_0x0095:
            java.lang.String r2 = "CheckedTextView"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 8
            goto L_0x00ac
        L_0x00a0:
            java.lang.String r2 = "RatingBar"
            boolean r2 = r5.equals(r2)
            if (r2 == 0) goto L_0x001f
            r2 = 11
            goto L_0x00ac
        L_0x00ab:
            r2 = -1
        L_0x00ac:
            switch(r2) {
                case 0: goto L_0x0115;
                case 1: goto L_0x010d;
                case 2: goto L_0x0105;
                case 3: goto L_0x00fd;
                case 4: goto L_0x00f5;
                case 5: goto L_0x00ed;
                case 6: goto L_0x00e5;
                case 7: goto L_0x00dd;
                case 8: goto L_0x00d5;
                case 9: goto L_0x00cd;
                case 10: goto L_0x00c5;
                case 11: goto L_0x00bd;
                case 12: goto L_0x00b5;
                default: goto L_0x00af;
            }
        L_0x00af:
            android.view.View r1 = r3.createView(r6, r5, r7)
            goto L_0x011d
        L_0x00b5:
            androidx.appcompat.widget.AppCompatSeekBar r1 = r3.createSeekBar(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00bd:
            androidx.appcompat.widget.AppCompatRatingBar r1 = r3.createRatingBar(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00c5:
            androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView r1 = r3.createMultiAutoCompleteTextView(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00cd:
            androidx.appcompat.widget.AppCompatAutoCompleteTextView r1 = r3.createAutoCompleteTextView(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00d5:
            androidx.appcompat.widget.AppCompatCheckedTextView r1 = r3.createCheckedTextView(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00dd:
            androidx.appcompat.widget.AppCompatRadioButton r1 = r3.createRadioButton(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00e5:
            androidx.appcompat.widget.AppCompatCheckBox r1 = r3.createCheckBox(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00ed:
            androidx.appcompat.widget.AppCompatImageButton r1 = r3.createImageButton(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00f5:
            androidx.appcompat.widget.AppCompatSpinner r1 = r3.createSpinner(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x00fd:
            androidx.appcompat.widget.AppCompatEditText r1 = r3.createEditText(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x0105:
            androidx.appcompat.widget.AppCompatButton r1 = r3.createButton(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x010d:
            androidx.appcompat.widget.AppCompatImageView r1 = r3.createImageView(r6, r7)
            r3.verifyNotNull(r1, r5)
            goto L_0x011d
        L_0x0115:
            androidx.appcompat.widget.AppCompatTextView r1 = r3.createTextView(r6, r7)
            r3.verifyNotNull(r1, r5)
        L_0x011d:
            if (r1 != 0) goto L_0x0125
            if (r0 == r6) goto L_0x0125
            android.view.View r1 = r3.createViewFromTag(r6, r5, r7)
        L_0x0125:
            if (r1 == 0) goto L_0x012a
            r3.checkOnClickListener(r1, r7)
        L_0x012a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatViewInflater.createView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean, boolean, boolean, boolean):android.view.View");
    }

    /* access modifiers changed from: protected */
    public AppCompatTextView createTextView(Context context, AttributeSet attrs) {
        return new AppCompatTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatImageView createImageView(Context context, AttributeSet attrs) {
        return new AppCompatImageView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatButton createButton(Context context, AttributeSet attrs) {
        return new AppCompatButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatEditText createEditText(Context context, AttributeSet attrs) {
        return new AppCompatEditText(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatSpinner createSpinner(Context context, AttributeSet attrs) {
        return new AppCompatSpinner(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatImageButton createImageButton(Context context, AttributeSet attrs) {
        return new AppCompatImageButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatCheckBox createCheckBox(Context context, AttributeSet attrs) {
        return new AppCompatCheckBox(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatRadioButton createRadioButton(Context context, AttributeSet attrs) {
        return new AppCompatRadioButton(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatCheckedTextView createCheckedTextView(Context context, AttributeSet attrs) {
        return new AppCompatCheckedTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new AppCompatAutoCompleteTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context context, AttributeSet attrs) {
        return new AppCompatMultiAutoCompleteTextView(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatRatingBar createRatingBar(Context context, AttributeSet attrs) {
        return new AppCompatRatingBar(context, attrs);
    }

    /* access modifiers changed from: protected */
    public AppCompatSeekBar createSeekBar(Context context, AttributeSet attrs) {
        return new AppCompatSeekBar(context, attrs);
    }

    private void verifyNotNull(View view, String name) {
        if (view == null) {
            throw new IllegalStateException(getClass().getName() + " asked to inflate view for <" + name + ">, but returned null");
        }
    }

    /* access modifiers changed from: protected */
    public View createView(Context context, String name, AttributeSet attrs) {
        return null;
    }

    private View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if (name.equals("view")) {
            name = attrs.getAttributeValue((String) null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attrs;
            if (-1 == name.indexOf(46)) {
                for (String createViewByPrefix : sClassPrefixList) {
                    View view = createViewByPrefix(context, name, createViewByPrefix);
                    if (view != null) {
                        return view;
                    }
                }
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return null;
            }
            View createViewByPrefix2 = createViewByPrefix(context, name, (String) null);
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return createViewByPrefix2;
        } catch (Exception e) {
            return null;
        } finally {
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
        }
    }

    private void checkOnClickListener(View view, AttributeSet attrs) {
        Context context = view.getContext();
        if (!(context instanceof ContextWrapper)) {
            return;
        }
        if (Build.VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(view)) {
            TypedArray a = context.obtainStyledAttributes(attrs, sOnClickAttrs);
            String handlerName = a.getString(0);
            if (handlerName != null) {
                view.setOnClickListener(new DeclaredOnClickListener(view, handlerName));
            }
            a.recycle();
        }
    }

    private View createViewByPrefix(Context context, String name, String prefix) throws ClassNotFoundException, InflateException {
        Constructor<? extends U> constructor = sConstructorMap.get(name);
        if (constructor == null) {
            try {
                constructor = context.getClassLoader().loadClass(prefix != null ? prefix + name : name).asSubclass(View.class).getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            } catch (Exception e) {
                return null;
            }
        }
        constructor.setAccessible(true);
        return (View) constructor.newInstance(this.mConstructorArgs);
    }

    private static Context themifyContext(Context context, AttributeSet attrs, boolean useAndroidTheme, boolean useAppTheme) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.View, 0, 0);
        int themeId = 0;
        if (useAndroidTheme) {
            themeId = a.getResourceId(R.styleable.View_android_theme, 0);
        }
        if (useAppTheme && themeId == 0 && (themeId = a.getResourceId(R.styleable.View_theme, 0)) != 0) {
            Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        a.recycle();
        if (themeId == 0) {
            return context;
        }
        if (!(context instanceof ContextThemeWrapper) || ((ContextThemeWrapper) context).getThemeResId() != themeId) {
            return new ContextThemeWrapper(context, themeId);
        }
        return context;
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View hostView, String methodName) {
            this.mHostView = hostView;
            this.mMethodName = methodName;
        }

        public void onClick(View v) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, new Object[]{v});
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        private void resolveMethod(Context context, String name) {
            String idText;
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, new Class[]{View.class})) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException e) {
                }
                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            if (id == -1) {
                idText = "";
            } else {
                idText = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            }
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + idText);
        }
    }
}
