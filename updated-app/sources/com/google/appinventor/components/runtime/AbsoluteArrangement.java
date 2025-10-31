package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LAYOUT, description = "<p>A formatting element in which to place components that should be displayed at any coordinates.</p>", iconName = "images/absoluteArrangement.png", version = 1)
public class AbsoluteArrangement extends AndroidViewComponent implements Component, ComponentContainer {
    private static final String LOG_TAG = "AArrangement";
    private int backgroundColor;
    private Drawable backgroundImageDrawable;
    private final List<Component> children = new ArrayList();
    private final Activity context;
    private String imagePath = "";
    private final RelativeLayout viewLayout;

    public AbsoluteArrangement(ComponentContainer container) {
        super(container);
        this.context = container.$context();
        Log.d(LOG_TAG, "Setting up layoutManager");
        this.viewLayout = new RelativeLayout(this.context, 100, 100);
        container.$add(this);
        BackgroundColor(0);
    }

    public Activity $context() {
        return this.context;
    }

    public Form $form() {
        return this.container.$form();
    }

    public void $add(AndroidViewComponent component) {
        this.children.add(component);
        this.viewLayout.add(component);
    }

    public void setChildWidth(AndroidViewComponent component, int width) {
        Log.d(LOG_TAG, "setChildWidth: width = " + width + " component = " + component);
        if (width <= -1000) {
            int childWidth = this.container.$form().Width();
            if (childWidth <= -1000 || childWidth > 0) {
                Log.d(LOG_TAG, "%%setChildWidth(): width = " + width + " parent Width = " + childWidth + " child = " + component);
                width = ((-(width + 1000)) * childWidth) / 100;
            } else {
                width = -1;
            }
        }
        component.setLastWidth(width);
        ViewUtil.setChildWidthForRelativeLayout(component.getView(), width);
    }

    public void setChildHeight(AndroidViewComponent component, int height) {
        if (height <= -1000) {
            int childHeight = this.container.$form().Height();
            if (childHeight <= -1000 || childHeight > 0) {
                height = ((-(height + 1000)) * childHeight) / 100;
            } else {
                height = -1;
            }
        }
        component.setLastHeight(height);
        ViewUtil.setChildHeightForRelativeLayout(component.getView(), height);
    }

    public void setChildNeedsLayout(AndroidViewComponent component) {
        this.viewLayout.updateComponentPosition(component);
    }

    public List<Component> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    public View getView() {
        return this.viewLayout.getLayoutManager();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the component's background color")
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @DesignerProperty(defaultValue = "&H00000000", editorType = "color")
    @SimpleProperty(description = "Specifies the component's background color. The background color will not be visible if an Image is being displayed.")
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        updateAppearance();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Image() {
        return this.imagePath;
    }

    @DesignerProperty(editorType = "asset")
    @SimpleProperty(description = "Specifies the path of the component's image.  If there is both an Image and a BackgroundColor, only the Image will be visible.")
    public void Image(String path) {
        if (path == null) {
            path = "";
        }
        if (!path.equals(this.imagePath) || this.backgroundImageDrawable == null) {
            this.imagePath = path;
            this.backgroundImageDrawable = null;
            if (this.imagePath.length() > 0) {
                try {
                    this.backgroundImageDrawable = MediaUtil.getBitmapDrawable(this.container.$form(), this.imagePath);
                } catch (IOException e) {
                }
            }
            updateAppearance();
        }
    }

    private void updateAppearance() {
        if (this.backgroundImageDrawable != null) {
            ViewUtil.setBackgroundImage(this.viewLayout.getLayoutManager(), this.backgroundImageDrawable);
        } else if (this.backgroundColor == 0) {
            ViewUtil.setBackgroundDrawable(this.viewLayout.getLayoutManager(), new ColorDrawable($form().isDarkTheme() ? -16777216 : -1));
        } else {
            ViewUtil.setBackgroundDrawable(this.viewLayout.getLayoutManager(), (Drawable) null);
            this.viewLayout.getLayoutManager().setBackgroundColor(this.backgroundColor);
        }
    }
}
