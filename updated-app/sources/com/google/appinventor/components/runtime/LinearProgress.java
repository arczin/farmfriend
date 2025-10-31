package com.google.appinventor.components.runtime;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;

@SimpleObject
@DesignerComponent(category = ComponentCategory.USERINTERFACE, description = "A visible component that indicates the progress of an operation using an animated linear bar.", iconName = "images/linearProgress.png", version = 1)
public final class LinearProgress extends AndroidViewComponent {
    private static final String LOG_TAG = "LinearProgress";
    private Context context;
    private int indeterminateColor = Component.COLOR_BLUE;
    private ProgressBar progressBar;
    private int progressColor = Component.COLOR_BLUE;

    public LinearProgress(ComponentContainer container) {
        super(container);
        this.context = container.$context();
        this.progressBar = new ProgressBar(this.context, (AttributeSet) null, 16842872);
        container.$add(this);
        Minimum(0);
        Maximum(100);
        ProgressColor(Component.COLOR_BLUE);
        IndeterminateColor(Component.COLOR_BLUE);
        Indeterminate(true);
        Width(-2);
        Log.d(LOG_TAG, "Linear Progress created");
    }

    public ProgressBar getView() {
        return this.progressBar;
    }

    public int Height() {
        return getView().getHeight();
    }

    public void Height(int height) {
        this.container.setChildHeight(this, height);
    }

    public void HeightPercent(int height) {
    }

    @DesignerProperty(defaultValue = "0", editorType = "integer")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set the lower range of the progress bar to min. This function works only for devices with API >= 26")
    public void Minimum(int value) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.progressBar.setMin(value);
            Log.i(LOG_TAG, "setMin = " + value);
            return;
        }
        Log.i(LOG_TAG, "setMin of progress bar is not possible. API is " + Build.VERSION.SDK_INT);
    }

    @SimpleProperty
    public int Minimum() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.progressBar.getMin();
        }
        return 0;
    }

    @DesignerProperty(defaultValue = "100", editorType = "integer")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set the upper range of the progress bar max.")
    public void Maximum(int value) {
        this.progressBar.setMax(value);
        Log.i(LOG_TAG, "setMax = " + value);
    }

    @SimpleProperty
    public int Maximum() {
        return this.progressBar.getMax();
    }

    @SimpleProperty(description = "Sets the current progress to the specified value. Does not do anything if the progress bar is in indeterminate mode.")
    public void Progress(int value) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.progressBar.setProgress(value, true);
        } else {
            this.progressBar.setProgress(value);
        }
        ProgressChanged(this.progressBar.getProgress());
    }

    @SimpleProperty(description = "Get the progress bar's current level of progress.")
    public int Progress() {
        return this.progressBar.getProgress();
    }

    @SimpleFunction(description = "Increase the progress bar's progress by the specified amount.")
    public void IncrementProgressBy(int value) {
        this.progressBar.incrementProgressBy(value);
        ProgressChanged(this.progressBar.getProgress());
    }

    @DesignerProperty(defaultValue = "&HFF0000FF", editorType = "color")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Change the progress color of the progress bar.")
    public void ProgressColor(int color) {
        this.progressColor = color;
        Drawable drawable = this.progressBar.getProgressDrawable();
        if (Build.VERSION.SDK_INT >= 29) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_IN));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        Log.i(LOG_TAG, "Progress Color = " + color);
    }

    @SimpleProperty
    public int ProgressColor() {
        return this.progressColor;
    }

    @DesignerProperty(defaultValue = "&HFF0000FF", editorType = "color")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Change the indeterminate color of the progress bar.")
    public void IndeterminateColor(int color) {
        this.indeterminateColor = color;
        Drawable drawable = this.progressBar.getProgressDrawable();
        if (Build.VERSION.SDK_INT >= 29) {
            drawable.setColorFilter(new BlendModeColorFilter(color, BlendMode.SRC_IN));
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        Log.i(LOG_TAG, "Indeterminate Color = " + color);
    }

    @SimpleProperty
    public int IndeterminateColor() {
        return this.indeterminateColor;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Change the indeterminate mode for this progress bar. In indeterminate mode, the progress is ignored and the progress bar shows an infinite animation instead.")
    public void Indeterminate(boolean enabled) {
        this.progressBar.setIndeterminate(enabled);
        Log.i(LOG_TAG, "Indeterminate is: " + enabled);
    }

    @SimpleProperty(description = "Indicate whether this progress bar is in indeterminate mode.")
    public boolean Indeterminate() {
        return this.progressBar.isIndeterminate();
    }

    @SimpleEvent(description = "Event that indicates that the progress of the progress bar has been changed. Returns the current progress value. If \"Indeterminate\" is set to true, then it returns \"0\".")
    public void ProgressChanged(int progress) {
        EventDispatcher.dispatchEvent(this, "ProgressChanged", Integer.valueOf(progress));
    }
}
