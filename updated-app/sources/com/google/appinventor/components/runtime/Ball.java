package com.google.appinventor.components.runtime;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.PaintUtil;
import com.google.appinventor.components.runtime.util.Vector2D;

@SimpleObject
@DesignerComponent(category = ComponentCategory.ANIMATION, description = "<p>A round 'sprite' that can be placed on a <code>Canvas</code>, where it can react to touches and drags, interact with other sprites (<code>ImageSprite</code>s and other <code>Ball</code>s) and the edge of the Canvas, and move according to its property values.</p><p>For example, to have a <code>Ball</code> move 4 pixels toward the top of a <code>Canvas</code> every 500 milliseconds (half second), you would set the <code>Speed</code> property to 4 [pixels], the <code>Interval</code> property to 500 [milliseconds], the <code>Heading</code> property to 90 [degrees], and the <code>Enabled</code> property to <code>True</code>.</p><p>The difference between a <code>Ball</code> and an <code>ImageSprite</code> is that the latter can get its appearance from an image file, while a <code>Ball</code>'s appearance can be changed only by varying its <code>PaintColor</code> and <code>Radius</code> properties.</p>", iconName = "images/ball.png", version = 8)
public final class Ball extends Sprite {
    static final int DEFAULT_RADIUS = 5;
    private Paint paint = new Paint();
    private int paintColor;
    private int radius;

    public Ball(ComponentContainer container) {
        super(container);
        PaintColor(-16777216);
        Radius(5);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.visible) {
            double d = this.xLeft;
            double deviceDensity = (double) this.form.deviceDensity();
            Double.isNaN(deviceDensity);
            double d2 = this.yTop;
            double deviceDensity2 = (double) this.form.deviceDensity();
            Double.isNaN(deviceDensity2);
            float correctedRadius = ((float) this.radius) * this.form.deviceDensity();
            canvas.drawCircle(((float) (d * deviceDensity)) + correctedRadius, ((float) (d2 * deviceDensity2)) + correctedRadius, correctedRadius, this.paint);
        }
    }

    /* access modifiers changed from: package-private */
    public Vector2D getCenterVector() {
        double d = this.xLeft;
        double Width = (double) Width();
        Double.isNaN(Width);
        double xCenter = d + (Width / 2.0d);
        double d2 = this.yTop;
        double Height = (double) Height();
        Double.isNaN(Height);
        return new Vector2D(xCenter, d2 + (Height / 2.0d));
    }

    /* access modifiers changed from: package-private */
    public double getMinProjection(Vector2D axis) {
        double dotProduct = Vector2D.dotProduct(getCenterVector(), axis);
        double Radius = (double) Radius();
        double magnitude = axis.magnitude();
        Double.isNaN(Radius);
        return dotProduct - (Radius * magnitude);
    }

    /* access modifiers changed from: package-private */
    public double getMaxProjection(Vector2D axis) {
        double dotProduct = Vector2D.dotProduct(getCenterVector(), axis);
        double Radius = (double) Radius();
        double magnitude = axis.magnitude();
        Double.isNaN(Radius);
        return dotProduct + (Radius * magnitude);
    }

    public int Height() {
        return this.radius * 2;
    }

    public void Height(int height) {
    }

    public void HeightPercent(int pCent) {
    }

    public int Width() {
        return this.radius * 2;
    }

    public void Width(int width) {
    }

    public void WidthPercent(int pCent) {
    }

    public boolean containsPoint(double qx, double qy) {
        double d = this.xLeft;
        double Width = (double) Width();
        Double.isNaN(Width);
        double xCenter = d + (Width / 2.0d);
        double d2 = this.yTop;
        double Height = (double) Height();
        Double.isNaN(Height);
        double yCenter = d2 + (Height / 2.0d);
        return ((qx - xCenter) * (qx - xCenter)) + ((qy - yCenter) * (qy - yCenter)) <= ((double) (this.radius * this.radius));
    }

    @DesignerProperty(defaultValue = "5", editorType = "non_negative_integer")
    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The distance from the edge of the Ball to its center.")
    public void Radius(int radius2) {
        this.radius = radius2;
        this.xLeft = xOriginToLeft(this.xOrigin);
        this.yTop = yOriginToTop(this.yOrigin);
        registerChange();
    }

    @SimpleProperty
    public int Radius() {
        return this.radius;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The color of the Ball.")
    public int PaintColor() {
        return this.paintColor;
    }

    @DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void PaintColor(int argb) {
        this.paintColor = argb;
        if (argb != 0) {
            PaintUtil.changePaint(this.paint, argb);
        } else {
            PaintUtil.changePaint(this.paint, -16777216);
        }
        registerChange();
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the x- and y-coordinates should represent the center of the Ball (true) or its left and top edges (false).", userVisible = false)
    public void OriginAtCenter(boolean b) {
        super.OriginAtCenter(b);
    }

    @SimpleProperty(description = "The horizontal coordinate of the Ball, increasing as the Ball moves right. If the property OriginAtCenter is true, the coordinate is for the center of the Ball; otherwise, it is for the leftmost point of the Ball.")
    public double X() {
        return super.X();
    }

    @SimpleProperty(description = "The vertical coordinate of the Ball, increasing as the Ball moves down. If the property OriginAtCenter is true, the coordinate is for the center of the Ball; otherwise, it is for the uppermost point of the Ball.")
    public double Y() {
        return super.Y();
    }

    @SimpleFunction(description = "Sets the x and y coordinates of the Ball. If CenterAtOrigin is true, the center of the Ball will be placed here. Otherwise, the top left edge of the Ball will be placed at the specified coordinates.")
    public void MoveTo(double x, double y) {
        super.MoveTo(x, y);
    }
}
