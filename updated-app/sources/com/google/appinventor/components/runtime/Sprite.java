package com.google.appinventor.components.runtime;

import android.graphics.Canvas;
import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.Options;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.Direction;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.util.BoundingBox;
import com.google.appinventor.components.runtime.util.TimerInternal;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.HashSet;
import java.util.Set;

@SimpleObject
public abstract class Sprite extends VisibleComponent implements AlarmHandler, OnDestroyListener, Deleteable {
    private static final boolean DEFAULT_ENABLED = true;
    private static final int DEFAULT_HEADING = 0;
    private static final int DEFAULT_INTERVAL = 100;
    protected static final String DEFAULT_ORIGIN = "(0.0, 0.0)";
    protected static final boolean DEFAULT_ORIGIN_AT_CENTER = false;
    private static final float DEFAULT_SPEED = 0.0f;
    protected static final double DEFAULT_U = 0.0d;
    protected static final double DEFAULT_V = 0.0d;
    private static final boolean DEFAULT_VISIBLE = true;
    private static final double DEFAULT_Z = 1.0d;
    private static final int DIRECTION_NONE = 0;
    private static final String LOG_TAG = "Sprite";
    private final Handler androidUIHandler;
    protected final Canvas canvas;
    protected Form form;
    protected double heading;
    protected double headingCos;
    protected double headingRadians;
    protected double headingSin;
    protected boolean initialized;
    protected int interval;
    protected boolean originAtCenter;
    private final Set<Sprite> registeredCollisions;
    protected float speed;
    private final TimerInternal timerInternal;
    protected double u;
    protected double userHeading;
    protected double v;
    protected boolean visible;
    protected double xLeft;
    protected double xOrigin;
    protected double yOrigin;
    protected double yTop;
    protected double zLayer;

    /* access modifiers changed from: protected */
    public abstract void onDraw(Canvas canvas2);

    protected Sprite(ComponentContainer container, Handler handler) {
        this.initialized = false;
        this.visible = true;
        this.androidUIHandler = handler;
        if (container instanceof Canvas) {
            this.canvas = (Canvas) container;
            this.canvas.addSprite(this);
            this.registeredCollisions = new HashSet();
            this.timerInternal = new TimerInternal(this, true, 100, handler);
            this.form = container.$form();
            OriginAtCenter(false);
            Heading(0.0d);
            Enabled(true);
            Interval(100);
            Speed(0.0f);
            Visible(true);
            Z(DEFAULT_Z);
            U(0.0d);
            V(0.0d);
            container.$form().registerForOnDestroy(this);
            return;
        }
        throw new IllegalArgumentError("Sprite constructor called with container " + container);
    }

    protected Sprite(ComponentContainer container) {
        this(container, new Handler());
    }

    public void Initialize() {
        this.initialized = true;
        this.canvas.registerChange(this);
    }

    @SimpleProperty(description = "Controls whether the %type% moves and can be interacted with through collisions, dragging, touching, and flinging.")
    public boolean Enabled() {
        return this.timerInternal.Enabled();
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void Enabled(boolean enabled) {
        this.timerInternal.Enabled(enabled);
    }

    @SimpleProperty(description = "Returns the %type%'s heading in degrees above the positive x-axis.  Zero degrees is toward the right of the screen; 90 degrees is toward the top of the screen.")
    public double Heading() {
        return this.userHeading;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void Heading(double userHeading2) {
        this.userHeading = userHeading2;
        this.heading = -userHeading2;
        this.headingRadians = Math.toRadians(this.heading);
        this.headingCos = Math.cos(this.headingRadians);
        this.headingSin = Math.sin(this.headingRadians);
        registerChange();
    }

    @SimpleProperty(description = "The interval in milliseconds at which the %type%'s position is updated.  For example, if the interval is 50 and the speed is 10, then every 50 milliseconds the sprite will move 10 pixels in the heading direction.")
    public int Interval() {
        return this.timerInternal.Interval();
    }

    @DesignerProperty(defaultValue = "100", editorType = "non_negative_integer")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void Interval(int interval2) {
        this.timerInternal.Interval(interval2);
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "float")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The number of pixels that the %type% should move every interval, if enabled.")
    public void Speed(float speed2) {
        this.speed = speed2;
    }

    @SimpleProperty(description = "The speed at which the %type% moves. The %type% moves this many pixels every interval if enabled.")
    public float Speed() {
        return this.speed;
    }

    @SimpleProperty(description = "Whether the %type% is visible.")
    public boolean Visible() {
        return this.visible;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void Visible(boolean visible2) {
        this.visible = visible2;
        registerChange();
    }

    /* access modifiers changed from: protected */
    public double X() {
        return this.xOrigin;
    }

    /* access modifiers changed from: protected */
    public double xLeftToOrigin(double xLeft2) {
        double Width = (double) Width();
        double d = this.u;
        Double.isNaN(Width);
        return (Width * d) + xLeft2;
    }

    /* access modifiers changed from: protected */
    public double xOriginToLeft(double xOrigin2) {
        double Width = (double) Width();
        double d = this.u;
        Double.isNaN(Width);
        return xOrigin2 - (Width * d);
    }

    private void updateX(double x) {
        this.xOrigin = x;
        this.xLeft = xOriginToLeft(x);
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "float")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void X(double x) {
        updateX(x);
        registerChange();
    }

    /* access modifiers changed from: protected */
    public double yTopToOrigin(double yTop2) {
        double Height = (double) Height();
        double d = this.v;
        Double.isNaN(Height);
        return (Height * d) + yTop2;
    }

    /* access modifiers changed from: protected */
    public double yOriginToTop(double yOrigin2) {
        double Height = (double) Height();
        double d = this.v;
        Double.isNaN(Height);
        return yOrigin2 - (Height * d);
    }

    private void updateY(double y) {
        this.yOrigin = y;
        this.yTop = yOriginToTop(y);
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "float")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void Y(double y) {
        updateY(y);
        registerChange();
    }

    /* access modifiers changed from: protected */
    public double Y() {
        return this.yOrigin;
    }

    @DesignerProperty(defaultValue = "1.0", editorType = "float")
    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void Z(double layer) {
        this.zLayer = layer;
        this.canvas.changeSpriteLayer(this);
    }

    @SimpleProperty(description = "How the %type% should be layered relative to other Balls and ImageSprites, with higher-numbered layers in front of lower-numbered layers.")
    public double Z() {
        return this.zLayer;
    }

    /* access modifiers changed from: protected */
    public void OriginAtCenter(boolean b) {
        this.originAtCenter = b;
        if (this.originAtCenter) {
            this.v = 0.5d;
            this.u = 0.5d;
        } else {
            this.v = 0.0d;
            this.u = 0.0d;
        }
        this.xLeft = xOriginToLeft(this.xOrigin);
        this.yTop = yOriginToTop(this.yOrigin);
    }

    /* access modifiers changed from: protected */
    public void U(double u2) {
        this.u = u2;
        this.xLeft = xOriginToLeft(this.xOrigin);
        registerChange();
    }

    /* access modifiers changed from: protected */
    public double U() {
        return this.u;
    }

    /* access modifiers changed from: protected */
    public void V(double v2) {
        this.v = v2;
        this.yTop = yOriginToTop(this.yOrigin);
        registerChange();
    }

    /* access modifiers changed from: protected */
    public double V() {
        return this.v;
    }

    /* access modifiers changed from: protected */
    public void postEvent(final Sprite sprite, final String eventName, final Object... args) {
        this.androidUIHandler.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(sprite, eventName, args);
            }
        });
    }

    @SimpleEvent
    public void CollidedWith(Sprite other) {
        if (!this.registeredCollisions.contains(other)) {
            this.registeredCollisions.add(other);
            postEvent(this, "CollidedWith", other);
        }
    }

    @SimpleEvent(description = "Event handler called when a %type% is dragged. On all calls, the starting coordinates are where the screen was first touched, and the \"current\" coordinates describe the endpoint of the current line segment. On the first call within a given drag, the \"previous\" coordinates are the same as the starting coordinates; subsequently, they are the \"current\" coordinates from the prior call. Note that the %type% won't actually move anywhere in response to the Dragged event unless MoveTo is explicitly called. For smooth movement, each of its coordinates should be set to the sum of its initial value and the difference between its current and previous values.")
    public void Dragged(float startX, float startY, float prevX, float prevY, float currentX, float currentY) {
        postEvent(this, "Dragged", Float.valueOf(startX), Float.valueOf(startY), Float.valueOf(prevX), Float.valueOf(prevY), Float.valueOf(currentX), Float.valueOf(currentY));
    }

    @SimpleEvent(description = "Event handler called when the %type% reaches an edge of the screen. If Bounce is then called with that edge, the %type% will appear to bounce off of the edge it reached. Edge here is represented as an integer that indicates one of eight directions north (1), northeast (2), east (3), southeast (4), south (-1), southwest (-2), west (-3), and northwest (-4).")
    public void EdgeReached(@Options(Direction.class) int edge) {
        Direction dir = Direction.fromUnderlyingValue(Integer.valueOf(edge));
        if (dir != null) {
            EdgeReachedAbstract(dir);
        }
    }

    public void EdgeReachedAbstract(Direction edge) {
        postEvent(this, "EdgeReached", edge.toUnderlyingValue());
    }

    @SimpleEvent(description = "Event handler called when a pair of sprites (Balls and ImageSprites) are no longer colliding.")
    public void NoLongerCollidingWith(Sprite other) {
        this.registeredCollisions.remove(other);
        postEvent(this, "NoLongerCollidingWith", other);
    }

    @SimpleEvent(description = "Event handler called when the user touches an enabled %type% and then immediately lifts their finger. The provided x and y coordinates are relative to the upper left of the canvas.")
    public void Touched(float x, float y) {
        postEvent(this, "Touched", Float.valueOf(x), Float.valueOf(y));
    }

    @SimpleEvent(description = "Event handler called when a fling gesture (quick swipe) is made on an enabled %type%. This provides the x and y coordinates of the start of the fling (relative to the upper left of the canvas), the speed (pixels per millisecond), the heading (-180 to 180 degrees), and the x and y velocity components of the fling's vector.")
    public void Flung(float x, float y, float speed2, float heading2, float xvel, float yvel) {
        postEvent(this, "Flung", Float.valueOf(x), Float.valueOf(y), Float.valueOf(speed2), Float.valueOf(heading2), Float.valueOf(xvel), Float.valueOf(yvel));
    }

    @SimpleEvent(description = "Event handler called when the user stops touching an enabled %type% (lifting their finger after a TouchDown event). This provides the x and y coordinates of the touch, relative to the upper left of the canvas.")
    public void TouchUp(float x, float y) {
        postEvent(this, "TouchUp", Float.valueOf(x), Float.valueOf(y));
    }

    @SimpleEvent(description = "Event handler called when the user begins touching an enabled %type% (placing their finger on a %type% and leaving it there). This provides the x and y coordinates of the touch, relative to the upper left of the canvas.")
    public void TouchDown(float x, float y) {
        postEvent(this, "TouchDown", Float.valueOf(x), Float.valueOf(y));
    }

    @SimpleFunction(description = "Makes the %type% bounce, as if off a wall. For normal bouncing, the edge argument should be the one returned by EdgeReached.")
    public void Bounce(@Options(Direction.class) int edge) {
        Direction dir = Direction.fromUnderlyingValue(Integer.valueOf(edge));
        if (dir != null) {
            BounceAbstract(dir);
        }
    }

    public void BounceAbstract(Direction edge) {
        MoveIntoBounds();
        double normalizedAngle = this.userHeading % 360.0d;
        if (normalizedAngle < 0.0d) {
            normalizedAngle += 360.0d;
        }
        if ((edge == Direction.East && (normalizedAngle < 90.0d || normalizedAngle > 270.0d)) || (edge == Direction.West && normalizedAngle > 90.0d && normalizedAngle < 270.0d)) {
            Heading(180.0d - normalizedAngle);
        } else if ((edge == Direction.North && normalizedAngle > 0.0d && normalizedAngle < 180.0d) || (edge == Direction.South && normalizedAngle > 180.0d)) {
            Heading(360.0d - normalizedAngle);
        } else if ((edge == Direction.Northeast && normalizedAngle > 0.0d && normalizedAngle < 90.0d) || ((edge == Direction.Northwest && normalizedAngle > 90.0d && normalizedAngle < 180.0d) || ((edge == Direction.Southwest && normalizedAngle > 180.0d && normalizedAngle < 270.0d) || (edge == Direction.Southeast && normalizedAngle > 270.0d)))) {
            Heading(180.0d + normalizedAngle);
        }
    }

    @SimpleFunction(description = "Indicates whether a collision has been registered between this %type% and the passed sprite (Ball or ImageSprite).")
    public boolean CollidingWith(Sprite other) {
        return this.registeredCollisions.contains(other);
    }

    @SimpleFunction(description = "Moves the %type% back in bounds if part of it extends out of bounds, having no effect otherwise. If the %type% is too wide to fit on the canvas, this aligns the left side of the %type% with the left side of the canvas. If the %type% is too tall to fit on the canvas, this aligns the top side of the %type% with the top side of the canvas.")
    public void MoveIntoBounds() {
        moveIntoBounds(this.canvas.Width(), this.canvas.Height());
    }

    public void MoveTo(double x, double y) {
        updateX(x);
        updateY(y);
        registerChange();
    }

    @SimpleFunction(description = "Moves the origin of %type% to the position of the cooordinates given  by the list formatted as [x-coordinate, y-coordinate].")
    public void MoveToPoint(YailList coordinates) {
        MoveTo(coerceToDouble(coordinates.getObject(0)), coerceToDouble(coordinates.getObject(1)));
    }

    protected static double coerceToDouble(Object o) {
        if (o instanceof Number) {
            return ((Number) o).doubleValue();
        }
        try {
            return Double.parseDouble(o.toString());
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    @SimpleFunction(description = "Turns the %type% to point towards a designated target sprite (Ball or ImageSprite). The new heading will be parallel to the line joining the origins of the two sprites.")
    public void PointTowards(Sprite target) {
        Heading(-Math.toDegrees(Math.atan2(target.yOrigin - this.yOrigin, target.xOrigin - this.xOrigin)));
    }

    @SimpleFunction(description = "Sets the heading of the %type% toward the point with the coordinates (x, y).")
    public void PointInDirection(double x, double y) {
        Heading(-Math.toDegrees(Math.atan2(y - this.yOrigin, x - this.xOrigin)));
    }

    /* access modifiers changed from: protected */
    public void registerChange() {
        if (!this.initialized) {
            this.canvas.getView().invalidate();
            return;
        }
        Direction edge = hitEdgeAbstract();
        if (edge != null) {
            EdgeReachedAbstract(edge);
        }
        this.canvas.registerChange(this);
    }

    /* access modifiers changed from: protected */
    public int hitEdge() {
        Direction edge = hitEdgeAbstract();
        if (edge == null) {
            return 0;
        }
        return edge.toUnderlyingValue().intValue();
    }

    /* access modifiers changed from: protected */
    public int hitEdge(int canvasWidth, int canvasHeight) {
        Direction edge = hitEdgeAbstract(canvasWidth, canvasHeight);
        if (edge == null) {
            return 0;
        }
        return edge.toUnderlyingValue().intValue();
    }

    /* access modifiers changed from: protected */
    public Direction hitEdgeAbstract() {
        if (!this.canvas.ready()) {
            return null;
        }
        return hitEdgeAbstract(this.canvas.Width(), this.canvas.Height());
    }

    /* access modifiers changed from: protected */
    public Direction hitEdgeAbstract(int canvasWidth, int canvasHeight) {
        boolean west = overWestEdge();
        boolean north = overNorthEdge();
        boolean east = overEastEdge(canvasWidth);
        boolean south = overSouthEdge(canvasHeight);
        if (!north && !south && !east && !west) {
            return null;
        }
        MoveIntoBounds();
        if (west) {
            if (north) {
                return Direction.Northwest;
            }
            if (south) {
                return Direction.Southwest;
            }
            return Direction.West;
        } else if (east) {
            if (north) {
                return Direction.Northeast;
            }
            if (south) {
                return Direction.Southeast;
            }
            return Direction.East;
        } else if (north) {
            return Direction.North;
        } else {
            return Direction.South;
        }
    }

    /* access modifiers changed from: protected */
    public final void moveIntoBounds(int canvasWidth, int canvasHeight) {
        boolean moved = false;
        if (Width() > canvasWidth) {
            if (this.xLeft != 0.0d) {
                this.xLeft = 0.0d;
                this.xOrigin = xLeftToOrigin(this.xLeft);
                moved = true;
            }
        } else if (overWestEdge()) {
            this.xLeft = 0.0d;
            this.xOrigin = xLeftToOrigin(this.xLeft);
            moved = true;
        } else if (overEastEdge(canvasWidth)) {
            this.xLeft = (double) (canvasWidth - Width());
            this.xOrigin = xLeftToOrigin(this.xLeft);
            moved = true;
        }
        if (Height() > canvasHeight) {
            if (this.yTop != 0.0d) {
                this.yTop = 0.0d;
                this.yOrigin = yTopToOrigin(this.yTop);
                moved = true;
            }
        } else if (overNorthEdge()) {
            this.yTop = 0.0d;
            this.yOrigin = yTopToOrigin(this.yTop);
            moved = true;
        } else if (overSouthEdge(canvasHeight)) {
            this.yTop = (double) (canvasHeight - Height());
            this.yOrigin = yTopToOrigin(this.yTop);
            moved = true;
        }
        if (moved) {
            registerChange();
        }
    }

    /* access modifiers changed from: protected */
    public void updateCoordinates() {
        double d = this.xOrigin;
        double d2 = (double) this.speed;
        double d3 = this.headingCos;
        Double.isNaN(d2);
        this.xOrigin = d + (d2 * d3);
        this.xLeft = xOriginToLeft(this.xOrigin);
        double d4 = this.yOrigin;
        double d5 = (double) this.speed;
        double d6 = this.headingSin;
        Double.isNaN(d5);
        this.yOrigin = d4 + (d5 * d6);
        this.yTop = yOriginToTop(this.yOrigin);
    }

    private final boolean overWestEdge() {
        return this.xLeft < 0.0d;
    }

    private final boolean overEastEdge(int canvasWidth) {
        double d = this.xLeft;
        double Width = (double) Width();
        Double.isNaN(Width);
        return d + Width > ((double) canvasWidth);
    }

    private final boolean overNorthEdge() {
        return this.yTop < 0.0d;
    }

    private final boolean overSouthEdge(int canvasHeight) {
        double d = this.yTop;
        double Height = (double) Height();
        Double.isNaN(Height);
        return d + Height > ((double) canvasHeight);
    }

    public BoundingBox getBoundingBox(int border) {
        double d = this.xLeft;
        double d2 = (double) border;
        Double.isNaN(d2);
        double d3 = d - d2;
        double d4 = this.yTop;
        double d5 = (double) border;
        Double.isNaN(d5);
        double d6 = d4 - d5;
        double d7 = this.xLeft;
        double Width = (double) Width();
        Double.isNaN(Width);
        double d8 = (d7 + Width) - DEFAULT_Z;
        double d9 = (double) border;
        Double.isNaN(d9);
        double d10 = d8 + d9;
        double d11 = this.yTop;
        double Height = (double) Height();
        Double.isNaN(Height);
        double d12 = (d11 + Height) - DEFAULT_Z;
        double d13 = (double) border;
        Double.isNaN(d13);
        return new BoundingBox(d3, d6, d10, d13 + d12);
    }

    public static boolean colliding(Sprite sprite1, Sprite sprite2) {
        if ((sprite1 instanceof Ball) && (sprite2 instanceof Ball)) {
            return collidingBalls((Ball) sprite1, (Ball) sprite2);
        }
        if ((sprite1 instanceof ImageSprite) && (sprite2 instanceof ImageSprite)) {
            return collidingImageSprites((ImageSprite) sprite1, (ImageSprite) sprite2);
        }
        if (sprite1 instanceof Ball) {
            return collidingBallAndImageSprite((Ball) sprite1, (ImageSprite) sprite2);
        }
        return collidingBallAndImageSprite((Ball) sprite2, (ImageSprite) sprite1);
    }

    private static boolean collidingBalls(Ball ball1, Ball ball2) {
        Ball ball = ball1;
        Ball ball3 = ball2;
        double d = ball.xLeft;
        double Width = (double) ball1.Width();
        Double.isNaN(Width);
        double xCenter1 = d + (Width / 2.0d);
        double d2 = ball.yTop;
        double Height = (double) ball1.Height();
        Double.isNaN(Height);
        double yCenter1 = d2 + (Height / 2.0d);
        double d3 = ball3.xLeft;
        double Width2 = (double) ball2.Width();
        Double.isNaN(Width2);
        double xCenter2 = d3 + (Width2 / 2.0d);
        double d4 = ball3.yTop;
        double Height2 = (double) ball2.Height();
        Double.isNaN(Height2);
        double yCenter2 = d4 + (Height2 / 2.0d);
        return ((xCenter1 - xCenter2) * (xCenter1 - xCenter2)) + ((yCenter1 - yCenter2) * (yCenter1 - yCenter2)) <= Math.pow((double) (ball1.Radius() + ball2.Radius()), 2.0d);
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0015  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean collidingImageSprites(com.google.appinventor.components.runtime.ImageSprite r12, com.google.appinventor.components.runtime.ImageSprite r13) {
        /*
            java.util.List r0 = r12.getNormalAxes()
            java.util.List r1 = r13.getNormalAxes()
            r0.addAll(r1)
            java.util.Iterator r1 = r0.iterator()
        L_0x000f:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0037
            java.lang.Object r2 = r1.next()
            com.google.appinventor.components.runtime.util.Vector2D r2 = (com.google.appinventor.components.runtime.util.Vector2D) r2
            double r3 = r12.getMinProjection(r2)
            double r5 = r12.getMaxProjection(r2)
            double r7 = r13.getMinProjection(r2)
            double r9 = r13.getMaxProjection(r2)
            int r11 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r11 < 0) goto L_0x0035
            int r11 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r11 >= 0) goto L_0x0034
            goto L_0x0035
        L_0x0034:
            goto L_0x000f
        L_0x0035:
            r1 = 0
            return r1
        L_0x0037:
            r1 = 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.Sprite.collidingImageSprites(com.google.appinventor.components.runtime.ImageSprite, com.google.appinventor.components.runtime.ImageSprite):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean collidingBallAndImageSprite(com.google.appinventor.components.runtime.Ball r18, com.google.appinventor.components.runtime.ImageSprite r19) {
        /*
            r0 = r18
            r1 = r19
            java.util.List r2 = r19.getNormalAxes()
            java.util.List r3 = r19.getExtremityVectors()
            com.google.appinventor.components.runtime.util.Vector2D r4 = r18.getCenterVector()
            com.google.appinventor.components.runtime.util.Vector2D r5 = r4.getClosestVector(r3)
            com.google.appinventor.components.runtime.util.Vector2D r6 = com.google.appinventor.components.runtime.util.Vector2D.difference(r5, r4)
            r2.add(r6)
            java.util.Iterator r7 = r2.iterator()
        L_0x001f:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x0047
            java.lang.Object r8 = r7.next()
            com.google.appinventor.components.runtime.util.Vector2D r8 = (com.google.appinventor.components.runtime.util.Vector2D) r8
            double r9 = r1.getMinProjection(r8)
            double r11 = r1.getMaxProjection(r8)
            double r13 = r0.getMinProjection(r8)
            double r15 = r0.getMaxProjection(r8)
            int r17 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r17 < 0) goto L_0x0045
            int r17 = (r15 > r9 ? 1 : (r15 == r9 ? 0 : -1))
            if (r17 >= 0) goto L_0x0044
            goto L_0x0045
        L_0x0044:
            goto L_0x001f
        L_0x0045:
            r7 = 0
            return r7
        L_0x0047:
            r7 = 1
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.Sprite.collidingBallAndImageSprite(com.google.appinventor.components.runtime.Ball, com.google.appinventor.components.runtime.ImageSprite):boolean");
    }

    public boolean intersectsWith(BoundingBox rect) {
        BoundingBox rect1 = getBoundingBox(0);
        if (!rect1.intersectDestructively(rect)) {
            return false;
        }
        for (double x = rect1.getLeft(); x < rect1.getRight(); x += DEFAULT_Z) {
            for (double y = rect1.getTop(); y < rect1.getBottom(); y += DEFAULT_Z) {
                if (containsPoint(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsPoint(double qx, double qy) {
        if (qx >= this.xLeft) {
            double d = this.xLeft;
            double Width = (double) Width();
            Double.isNaN(Width);
            if (qx < d + Width && qy >= this.yTop) {
                double d2 = this.yTop;
                double Height = (double) Height();
                Double.isNaN(Height);
                if (qy < d2 + Height) {
                    return true;
                }
            }
        }
        return false;
    }

    public void alarm() {
        if (this.initialized && this.speed != 0.0f) {
            updateCoordinates();
            registerChange();
        }
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this.canvas.$form();
    }

    public void onDestroy() {
        this.timerInternal.Enabled(false);
    }

    public void onDelete() {
        this.timerInternal.Enabled(false);
        this.canvas.removeSprite(this);
    }
}
