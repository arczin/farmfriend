package com.google.appinventor.components.runtime;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.google.appinventor.components.runtime.util.ViewUtil;
import java.util.LinkedList;
import java.util.List;

public class RelativeLayout implements Layout {
    /* access modifiers changed from: private */
    public final List<AndroidViewComponent> componentsToAdd = new LinkedList();
    private final Handler handler;
    private final android.widget.RelativeLayout layoutManager;

    RelativeLayout(Context context, final Integer preferredEmptyWidth, final Integer preferredEmptyHeight) {
        if ((preferredEmptyWidth != null || preferredEmptyHeight == null) && (preferredEmptyWidth == null || preferredEmptyHeight != null)) {
            this.handler = new Handler();
            this.layoutManager = new android.widget.RelativeLayout(context) {
                /* access modifiers changed from: protected */
                public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    if (preferredEmptyWidth == null || preferredEmptyHeight == null) {
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    } else if (getChildCount() != 0) {
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    } else {
                        setMeasuredDimension(getSize(widthMeasureSpec, preferredEmptyWidth.intValue()), getSize(heightMeasureSpec, preferredEmptyHeight.intValue()));
                    }
                }

                private int getSize(int measureSpec, int preferredSize) {
                    int specMode = View.MeasureSpec.getMode(measureSpec);
                    int specSize = View.MeasureSpec.getSize(measureSpec);
                    if (specMode == 1073741824) {
                        return specSize;
                    }
                    int result = preferredSize;
                    if (specMode == Integer.MIN_VALUE) {
                        return Math.min(result, specSize);
                    }
                    return result;
                }
            };
            return;
        }
        throw new IllegalArgumentException("RelativeLayout - preferredEmptyWidth and preferredEmptyHeight must be either both null or both not null");
    }

    public int getWidth() {
        return this.layoutManager.getWidth();
    }

    public int getHeight() {
        return this.layoutManager.getHeight();
    }

    public ViewGroup getLayoutManager() {
        return this.layoutManager;
    }

    public void add(AndroidViewComponent component) {
        component.getView().setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        addComponentLater(component);
    }

    public void updateComponentPosition(AndroidViewComponent component) {
        int x = component.Left();
        int y = component.Top();
        if (x != Integer.MIN_VALUE && y != Integer.MIN_VALUE) {
            View view = component.getView();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = ViewUtil.calculatePixels(view, x);
            params.topMargin = ViewUtil.calculatePixels(view, y);
            view.requestLayout();
        }
    }

    private void addComponentLater(AndroidViewComponent component) {
        synchronized (this.componentsToAdd) {
            if (this.componentsToAdd.size() == 0) {
                this.componentsToAdd.add(component);
                this.handler.post(new Runnable() {
                    public void run() {
                        synchronized (RelativeLayout.this.componentsToAdd) {
                            List<AndroidViewComponent> copy = new LinkedList<>(RelativeLayout.this.componentsToAdd);
                            RelativeLayout.this.componentsToAdd.clear();
                            for (AndroidViewComponent component : copy) {
                                RelativeLayout.this.addComponent(component);
                            }
                        }
                    }
                });
            } else {
                this.componentsToAdd.add(component);
            }
        }
    }

    /* access modifiers changed from: private */
    public void addComponent(AndroidViewComponent component) {
        int x = component.Left();
        int y = component.Top();
        if (x == Integer.MIN_VALUE || y == Integer.MIN_VALUE) {
            addComponentLater(component);
            return;
        }
        ViewGroup.LayoutParams params = component.getView().getLayoutParams();
        RelativeLayout.LayoutParams newParams = new RelativeLayout.LayoutParams(params.width, params.height);
        newParams.topMargin = ViewUtil.calculatePixels(component.getView(), y);
        newParams.leftMargin = ViewUtil.calculatePixels(component.getView(), x);
        this.layoutManager.addView(component.getView(), newParams);
    }
}
