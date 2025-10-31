package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.R;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import gnu.expr.Declaration;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(Context context) {
        this(context, (AttributeSet) null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.LinearLayoutCompat, defStyleAttr, 0);
        int index = a.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (index >= 0) {
            setOrientation(index);
        }
        int index2 = a.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (index2 >= 0) {
            setGravity(index2);
        }
        boolean baselineAligned = a.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!baselineAligned) {
            setBaselineAligned(baselineAligned);
        }
        this.mWeightSum = a.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = a.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = a.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(a.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = a.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = a.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        a.recycle();
    }

    public void setShowDividers(int showDividers) {
        if (showDividers != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = showDividers;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public void setDividerDrawable(Drawable divider) {
        if (divider != this.mDivider) {
            this.mDivider = divider;
            boolean z = false;
            if (divider != null) {
                this.mDividerWidth = divider.getIntrinsicWidth();
                this.mDividerHeight = divider.getIntrinsicHeight();
            } else {
                this.mDividerWidth = 0;
                this.mDividerHeight = 0;
            }
            if (divider == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int padding) {
        this.mDividerPadding = padding;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mDivider != null) {
            if (this.mOrientation == 1) {
                drawDividersVertical(canvas);
            } else {
                drawDividersHorizontal(canvas);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void drawDividersVertical(Canvas canvas) {
        int bottom;
        int count = getVirtualChildCount();
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                drawHorizontalDivider(canvas, (child.getTop() - ((LayoutParams) child.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
        }
        if (hasDividerBeforeChildAt(count) != 0) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 == null) {
                bottom = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                bottom = child2.getBottom() + ((LayoutParams) child2.getLayoutParams()).bottomMargin;
            }
            drawHorizontalDivider(canvas, bottom);
        }
    }

    /* access modifiers changed from: package-private */
    public void drawDividersHorizontal(Canvas canvas) {
        int position;
        int position2;
        int count = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (!(child == null || child.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (isLayoutRtl) {
                    position2 = child.getRight() + lp.rightMargin;
                } else {
                    position2 = (child.getLeft() - lp.leftMargin) - this.mDividerWidth;
                }
                drawVerticalDivider(canvas, position2);
            }
        }
        if (hasDividerBeforeChildAt(count) != 0) {
            View child2 = getVirtualChildAt(count - 1);
            if (child2 != null) {
                LayoutParams lp2 = (LayoutParams) child2.getLayoutParams();
                if (isLayoutRtl) {
                    position = (child2.getLeft() - lp2.leftMargin) - this.mDividerWidth;
                } else {
                    position = child2.getRight() + lp2.rightMargin;
                }
            } else if (isLayoutRtl) {
                position = getPaddingLeft();
            } else {
                position = (getWidth() - getPaddingRight()) - this.mDividerWidth;
            }
            drawVerticalDivider(canvas, position);
        }
    }

    /* access modifiers changed from: package-private */
    public void drawHorizontalDivider(Canvas canvas, int top) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, top, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + top);
        this.mDivider.draw(canvas);
    }

    /* access modifiers changed from: package-private */
    public void drawVerticalDivider(Canvas canvas, int left) {
        this.mDivider.setBounds(left, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + left, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public void setBaselineAligned(boolean baselineAligned) {
        this.mBaselineAligned = baselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    public void setMeasureWithLargestChildEnabled(boolean enabled) {
        this.mUseLargestChild = enabled;
    }

    public int getBaseline() {
        int majorGravity;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() > this.mBaselineAlignedChildIndex) {
            View child = getChildAt(this.mBaselineAlignedChildIndex);
            int childBaseline = child.getBaseline();
            if (childBaseline != -1) {
                int childTop = this.mBaselineChildTop;
                if (this.mOrientation == 1 && (majorGravity = this.mGravity & 112) != 48) {
                    switch (majorGravity) {
                        case 16:
                            childTop += ((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2;
                            break;
                        case 80:
                            childTop = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
                            break;
                    }
                }
                return ((LayoutParams) child.getLayoutParams()).topMargin + childTop + childBaseline;
            } else if (this.mBaselineAlignedChildIndex == 0) {
                return -1;
            } else {
                throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            }
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.mBaselineAlignedChildIndex = i;
    }

    /* access modifiers changed from: package-private */
    public View getVirtualChildAt(int index) {
        return getChildAt(index);
    }

    /* access modifiers changed from: package-private */
    public int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    public void setWeightSum(float weightSum) {
        this.mWeightSum = Math.max(0.0f, weightSum);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOrientation == 1) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            if ((this.mShowDividers & 1) != 0) {
                return true;
            }
            return false;
        } else if (childIndex == getChildCount()) {
            if ((this.mShowDividers & 4) != 0) {
                return true;
            }
            return false;
        } else if ((this.mShowDividers & 2) == 0) {
            return false;
        } else {
            for (int i = childIndex - 1; i >= 0; i--) {
                if (getChildAt(i).getVisibility() != 8) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x03d6  */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x03d8  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x03df  */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x03e8  */
    /* JADX WARNING: Removed duplicated region for block: B:176:0x0460  */
    /* JADX WARNING: Removed duplicated region for block: B:194:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0184  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0197  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void measureVertical(int r39, int r40) {
        /*
            r38 = this;
            r7 = r38
            r8 = r39
            r9 = r40
            r10 = 0
            r7.mTotalLength = r10
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 1
            r5 = 0
            int r11 = r38.getVirtualChildCount()
            int r12 = android.view.View.MeasureSpec.getMode(r39)
            int r13 = android.view.View.MeasureSpec.getMode(r40)
            r6 = 0
            r14 = 0
            int r15 = r7.mBaselineAlignedChildIndex
            boolean r10 = r7.mUseLargestChild
            r17 = 0
            r18 = 0
            r19 = r14
            r14 = r0
            r0 = r5
            r5 = r2
            r2 = r18
            r18 = r6
            r6 = r1
            r37 = r4
            r4 = r3
            r3 = r17
            r17 = r37
        L_0x0036:
            r20 = r4
            r1 = 8
            r22 = 1
            r23 = 0
            if (r2 >= r11) goto L_0x01be
            android.view.View r4 = r7.getVirtualChildAt(r2)
            if (r4 != 0) goto L_0x0054
            int r1 = r7.mTotalLength
            int r21 = r7.measureNullChild(r2)
            int r1 = r1 + r21
            r7.mTotalLength = r1
            r4 = r20
            goto L_0x01b8
        L_0x0054:
            r26 = r3
            int r3 = r4.getVisibility()
            if (r3 != r1) goto L_0x0067
            int r1 = r7.getChildrenSkipCount(r4, r2)
            int r2 = r2 + r1
            r4 = r20
            r3 = r26
            goto L_0x01b8
        L_0x0067:
            boolean r1 = r7.hasDividerBeforeChildAt(r2)
            if (r1 == 0) goto L_0x0074
            int r1 = r7.mTotalLength
            int r3 = r7.mDividerHeight
            int r1 = r1 + r3
            r7.mTotalLength = r1
        L_0x0074:
            android.view.ViewGroup$LayoutParams r1 = r4.getLayoutParams()
            r3 = r1
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r3 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r3
            float r1 = r3.weight
            float r27 = r0 + r1
            r1 = 1073741824(0x40000000, float:2.0)
            if (r13 != r1) goto L_0x00ad
            int r0 = r3.height
            if (r0 != 0) goto L_0x00ad
            float r0 = r3.weight
            int r0 = (r0 > r23 ? 1 : (r0 == r23 ? 0 : -1))
            if (r0 <= 0) goto L_0x00ad
            int r0 = r7.mTotalLength
            int r1 = r3.topMargin
            int r1 = r1 + r0
            r28 = r2
            int r2 = r3.bottomMargin
            int r1 = r1 + r2
            int r1 = java.lang.Math.max(r0, r1)
            r7.mTotalLength = r1
            r19 = 1
            r1 = r3
            r35 = r5
            r36 = r6
            r34 = r20
            r3 = r26
            r25 = r28
            r5 = r4
            goto L_0x0126
        L_0x00ad:
            r28 = r2
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            int r1 = r3.height
            if (r1 != 0) goto L_0x00c1
            float r1 = r3.weight
            int r1 = (r1 > r23 ? 1 : (r1 == r23 ? 0 : -1))
            if (r1 <= 0) goto L_0x00c1
            r0 = 0
            r1 = -2
            r3.height = r1
            r2 = r0
            goto L_0x00c2
        L_0x00c1:
            r2 = r0
        L_0x00c2:
            int r0 = (r27 > r23 ? 1 : (r27 == r23 ? 0 : -1))
            if (r0 != 0) goto L_0x00cb
            int r0 = r7.mTotalLength
            r29 = r0
            goto L_0x00cd
        L_0x00cb:
            r29 = 0
        L_0x00cd:
            r30 = 0
            r0 = r38
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            r21 = 1073741824(0x40000000, float:2.0)
            r1 = r4
            r31 = r2
            r25 = r28
            r2 = r25
            r33 = r3
            r32 = r26
            r3 = r39
            r34 = r20
            r20 = r4
            r4 = r30
            r35 = r5
            r5 = r40
            r36 = r6
            r6 = r29
            r0.measureChildBeforeLayout(r1, r2, r3, r4, r5, r6)
            r0 = r31
            if (r0 == r8) goto L_0x00fc
            r1 = r33
            r1.height = r0
            goto L_0x00fe
        L_0x00fc:
            r1 = r33
        L_0x00fe:
            int r2 = r20.getMeasuredHeight()
            int r3 = r7.mTotalLength
            int r4 = r3 + r2
            int r5 = r1.topMargin
            int r4 = r4 + r5
            int r5 = r1.bottomMargin
            int r4 = r4 + r5
            r5 = r20
            int r6 = r7.getNextLocationOffset(r5)
            int r4 = r4 + r6
            int r4 = java.lang.Math.max(r3, r4)
            r7.mTotalLength = r4
            if (r10 == 0) goto L_0x0123
            r4 = r32
            int r4 = java.lang.Math.max(r2, r4)
            r3 = r4
            goto L_0x0126
        L_0x0123:
            r4 = r32
            r3 = r4
        L_0x0126:
            if (r15 < 0) goto L_0x0133
            r2 = r25
            int r0 = r2 + 1
            if (r15 != r0) goto L_0x0135
            int r0 = r7.mTotalLength
            r7.mBaselineChildTop = r0
            goto L_0x0135
        L_0x0133:
            r2 = r25
        L_0x0135:
            if (r2 >= r15) goto L_0x0146
            float r0 = r1.weight
            int r0 = (r0 > r23 ? 1 : (r0 == r23 ? 0 : -1))
            if (r0 > 0) goto L_0x013e
            goto L_0x0146
        L_0x013e:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r4 = "A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex."
            r0.<init>(r4)
            throw r0
        L_0x0146:
            r0 = 0
            r6 = 1073741824(0x40000000, float:2.0)
            if (r12 == r6) goto L_0x0154
            int r4 = r1.width
            r6 = -1
            if (r4 != r6) goto L_0x0155
            r18 = 1
            r0 = 1
            goto L_0x0155
        L_0x0154:
            r6 = -1
        L_0x0155:
            int r4 = r1.leftMargin
            int r8 = r1.rightMargin
            int r4 = r4 + r8
            int r8 = r5.getMeasuredWidth()
            int r8 = r8 + r4
            int r14 = java.lang.Math.max(r14, r8)
            int r6 = r5.getMeasuredState()
            r20 = r8
            r8 = r36
            int r6 = android.view.View.combineMeasuredStates(r8, r6)
            if (r17 == 0) goto L_0x017b
            int r8 = r1.width
            r21 = r3
            r3 = -1
            if (r8 != r3) goto L_0x017d
            r3 = 1
            goto L_0x017e
        L_0x017b:
            r21 = r3
        L_0x017d:
            r3 = 0
        L_0x017e:
            float r8 = r1.weight
            int r8 = (r8 > r23 ? 1 : (r8 == r23 ? 0 : -1))
            if (r8 <= 0) goto L_0x0197
            if (r0 == 0) goto L_0x0188
            r8 = r4
            goto L_0x018a
        L_0x0188:
            r8 = r20
        L_0x018a:
            r24 = r6
            r6 = r34
            int r6 = java.lang.Math.max(r6, r8)
            r33 = r1
            r1 = r35
            goto L_0x01a9
        L_0x0197:
            r24 = r6
            r6 = r34
            if (r0 == 0) goto L_0x019f
            r8 = r4
            goto L_0x01a1
        L_0x019f:
            r8 = r20
        L_0x01a1:
            r33 = r1
            r1 = r35
            int r1 = java.lang.Math.max(r1, r8)
        L_0x01a9:
            int r8 = r7.getChildrenSkipCount(r5, r2)
            int r2 = r2 + r8
            r5 = r1
            r17 = r3
            r4 = r6
            r3 = r21
            r6 = r24
            r0 = r27
        L_0x01b8:
            int r2 = r2 + 1
            r8 = r39
            goto L_0x0036
        L_0x01be:
            r4 = r3
            r1 = r5
            r8 = r6
            r6 = r20
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = 8
            int r2 = r7.mTotalLength
            if (r2 <= 0) goto L_0x01d8
            boolean r2 = r7.hasDividerBeforeChildAt(r11)
            if (r2 == 0) goto L_0x01d8
            int r2 = r7.mTotalLength
            int r5 = r7.mDividerHeight
            int r2 = r2 + r5
            r7.mTotalLength = r2
        L_0x01d8:
            if (r10 == 0) goto L_0x023a
            if (r13 == r3) goto L_0x01e2
            if (r13 != 0) goto L_0x01df
            goto L_0x01e2
        L_0x01df:
            r36 = r8
            goto L_0x023c
        L_0x01e2:
            r2 = 0
            r7.mTotalLength = r2
            r2 = 0
        L_0x01e6:
            if (r2 >= r11) goto L_0x0235
            android.view.View r3 = r7.getVirtualChildAt(r2)
            if (r3 != 0) goto L_0x01fd
            int r5 = r7.mTotalLength
            int r21 = r7.measureNullChild(r2)
            int r5 = r5 + r21
            r7.mTotalLength = r5
            r24 = r2
            r36 = r8
            goto L_0x022e
        L_0x01fd:
            int r5 = r3.getVisibility()
            r36 = r8
            r8 = 8
            if (r5 != r8) goto L_0x020d
            int r5 = r7.getChildrenSkipCount(r3, r2)
            int r2 = r2 + r5
            goto L_0x0230
        L_0x020d:
            android.view.ViewGroup$LayoutParams r5 = r3.getLayoutParams()
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r5 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r5
            int r8 = r7.mTotalLength
            int r21 = r8 + r4
            r24 = r2
            int r2 = r5.topMargin
            int r21 = r21 + r2
            int r2 = r5.bottomMargin
            int r21 = r21 + r2
            int r2 = r7.getNextLocationOffset(r3)
            int r2 = r21 + r2
            int r2 = java.lang.Math.max(r8, r2)
            r7.mTotalLength = r2
        L_0x022e:
            r2 = r24
        L_0x0230:
            int r2 = r2 + 1
            r8 = r36
            goto L_0x01e6
        L_0x0235:
            r24 = r2
            r36 = r8
            goto L_0x023c
        L_0x023a:
            r36 = r8
        L_0x023c:
            int r2 = r7.mTotalLength
            int r3 = r38.getPaddingTop()
            int r5 = r38.getPaddingBottom()
            int r3 = r3 + r5
            int r2 = r2 + r3
            r7.mTotalLength = r2
            int r2 = r7.mTotalLength
            int r3 = r38.getSuggestedMinimumHeight()
            int r2 = java.lang.Math.max(r2, r3)
            r3 = 0
            int r5 = android.view.View.resolveSizeAndState(r2, r9, r3)
            r3 = 16777215(0xffffff, float:2.3509886E-38)
            r2 = r5 & r3
            int r3 = r7.mTotalLength
            int r3 = r2 - r3
            if (r19 != 0) goto L_0x02fc
            if (r3 == 0) goto L_0x0272
            int r8 = (r0 > r23 ? 1 : (r0 == r23 ? 0 : -1))
            if (r8 <= 0) goto L_0x0272
            r21 = r0
            r24 = r2
            r26 = r3
            goto L_0x0302
        L_0x0272:
            int r1 = java.lang.Math.max(r1, r6)
            if (r10 == 0) goto L_0x02e0
            r8 = 1073741824(0x40000000, float:2.0)
            if (r13 == r8) goto L_0x02e0
            r16 = 0
            r8 = r16
        L_0x0280:
            if (r8 >= r11) goto L_0x02d7
            r21 = r0
            android.view.View r0 = r7.getVirtualChildAt(r8)
            if (r0 == 0) goto L_0x02c6
            r16 = r1
            int r1 = r0.getVisibility()
            r24 = r2
            r2 = 8
            if (r1 != r2) goto L_0x0299
            r26 = r3
            goto L_0x02cc
        L_0x0299:
            android.view.ViewGroup$LayoutParams r1 = r0.getLayoutParams()
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r1 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r1
            float r2 = r1.weight
            int r22 = (r2 > r23 ? 1 : (r2 == r23 ? 0 : -1))
            if (r22 <= 0) goto L_0x02bf
            r22 = r1
            int r1 = r0.getMeasuredWidth()
            r25 = r2
            r2 = 1073741824(0x40000000, float:2.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r2)
            r26 = r3
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r4, r2)
            r0.measure(r1, r3)
            goto L_0x02cc
        L_0x02bf:
            r22 = r1
            r25 = r2
            r26 = r3
            goto L_0x02cc
        L_0x02c6:
            r16 = r1
            r24 = r2
            r26 = r3
        L_0x02cc:
            int r8 = r8 + 1
            r1 = r16
            r0 = r21
            r2 = r24
            r3 = r26
            goto L_0x0280
        L_0x02d7:
            r21 = r0
            r16 = r1
            r24 = r2
            r26 = r3
            goto L_0x02e8
        L_0x02e0:
            r21 = r0
            r16 = r1
            r24 = r2
            r26 = r3
        L_0x02e8:
            r32 = r4
            r34 = r6
            r25 = r10
            r30 = r13
            r1 = r16
            r3 = r26
            r6 = r36
            r10 = r39
            r26 = r15
            goto L_0x043e
        L_0x02fc:
            r21 = r0
            r24 = r2
            r26 = r3
        L_0x0302:
            float r0 = r7.mWeightSum
            int r0 = (r0 > r23 ? 1 : (r0 == r23 ? 0 : -1))
            if (r0 <= 0) goto L_0x030b
            float r0 = r7.mWeightSum
            goto L_0x030d
        L_0x030b:
            r0 = r21
        L_0x030d:
            r2 = 0
            r7.mTotalLength = r2
            r3 = 0
            r8 = r1
            r2 = r14
            r1 = r26
            r14 = r36
        L_0x0317:
            if (r3 >= r11) goto L_0x0420
            r32 = r4
            android.view.View r4 = r7.getVirtualChildAt(r3)
            r34 = r6
            int r6 = r4.getVisibility()
            r25 = r10
            r10 = 8
            if (r6 != r10) goto L_0x0333
            r10 = r39
            r30 = r13
            r26 = r15
            goto L_0x0412
        L_0x0333:
            android.view.ViewGroup$LayoutParams r6 = r4.getLayoutParams()
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r6 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r6
            float r10 = r6.weight
            int r26 = (r10 > r23 ? 1 : (r10 == r23 ? 0 : -1))
            if (r26 <= 0) goto L_0x03ac
            r26 = r15
            float r15 = (float) r1
            float r15 = r15 * r10
            float r15 = r15 / r0
            int r15 = (int) r15
            float r0 = r0 - r10
            int r1 = r1 - r15
            int r27 = r38.getPaddingLeft()
            int r28 = r38.getPaddingRight()
            int r27 = r27 + r28
            r28 = r0
            int r0 = r6.leftMargin
            int r27 = r27 + r0
            int r0 = r6.rightMargin
            int r0 = r27 + r0
            r27 = r1
            int r1 = r6.width
            r29 = r10
            r10 = r39
            int r0 = getChildMeasureSpec(r10, r0, r1)
            int r1 = r6.height
            if (r1 != 0) goto L_0x0385
            r1 = 1073741824(0x40000000, float:2.0)
            if (r13 == r1) goto L_0x0374
            r30 = r13
            goto L_0x0387
        L_0x0374:
            r30 = r13
            if (r15 <= 0) goto L_0x037a
            r13 = r15
            goto L_0x037b
        L_0x037a:
            r13 = 0
        L_0x037b:
            int r13 = android.view.View.MeasureSpec.makeMeasureSpec(r13, r1)
            r4.measure(r0, r13)
            r31 = r15
            goto L_0x039c
        L_0x0385:
            r30 = r13
        L_0x0387:
            int r1 = r4.getMeasuredHeight()
            int r1 = r1 + r15
            if (r1 >= 0) goto L_0x038f
            r1 = 0
        L_0x038f:
            r31 = r15
            r13 = 1073741824(0x40000000, float:2.0)
            int r15 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r13)
            r4.measure(r0, r15)
        L_0x039c:
            int r1 = r4.getMeasuredState()
            r1 = r1 & -256(0xffffffffffffff00, float:NaN)
            int r14 = android.view.View.combineMeasuredStates(r14, r1)
            r1 = r27
            r0 = r28
            goto L_0x03b4
        L_0x03ac:
            r29 = r10
            r30 = r13
            r26 = r15
            r10 = r39
        L_0x03b4:
            int r13 = r6.leftMargin
            int r15 = r6.rightMargin
            int r13 = r13 + r15
            int r15 = r4.getMeasuredWidth()
            int r15 = r15 + r13
            int r2 = java.lang.Math.max(r2, r15)
            r27 = r0
            r0 = 1073741824(0x40000000, float:2.0)
            if (r12 == r0) goto L_0x03d1
            int r0 = r6.width
            r28 = r1
            r1 = -1
            if (r0 != r1) goto L_0x03d3
            r0 = 1
            goto L_0x03d4
        L_0x03d1:
            r28 = r1
        L_0x03d3:
            r0 = 0
        L_0x03d4:
            if (r0 == 0) goto L_0x03d8
            r1 = r13
            goto L_0x03d9
        L_0x03d8:
            r1 = r15
        L_0x03d9:
            int r1 = java.lang.Math.max(r8, r1)
            if (r17 == 0) goto L_0x03e8
            int r8 = r6.width
            r31 = r0
            r0 = -1
            if (r8 != r0) goto L_0x03eb
            r8 = 1
            goto L_0x03ec
        L_0x03e8:
            r31 = r0
            r0 = -1
        L_0x03eb:
            r8 = 0
        L_0x03ec:
            int r0 = r7.mTotalLength
            int r17 = r4.getMeasuredHeight()
            int r17 = r0 + r17
            r33 = r1
            int r1 = r6.topMargin
            int r17 = r17 + r1
            int r1 = r6.bottomMargin
            int r17 = r17 + r1
            int r1 = r7.getNextLocationOffset(r4)
            int r1 = r17 + r1
            int r1 = java.lang.Math.max(r0, r1)
            r7.mTotalLength = r1
            r17 = r8
            r0 = r27
            r1 = r28
            r8 = r33
        L_0x0412:
            int r3 = r3 + 1
            r10 = r25
            r15 = r26
            r13 = r30
            r4 = r32
            r6 = r34
            goto L_0x0317
        L_0x0420:
            r32 = r4
            r34 = r6
            r25 = r10
            r30 = r13
            r26 = r15
            r10 = r39
            int r3 = r7.mTotalLength
            int r4 = r38.getPaddingTop()
            int r6 = r38.getPaddingBottom()
            int r4 = r4 + r6
            int r3 = r3 + r4
            r7.mTotalLength = r3
            r3 = r1
            r1 = r8
            r6 = r14
            r14 = r2
        L_0x043e:
            if (r17 != 0) goto L_0x0445
            r0 = 1073741824(0x40000000, float:2.0)
            if (r12 == r0) goto L_0x0445
            r14 = r1
        L_0x0445:
            int r0 = r38.getPaddingLeft()
            int r2 = r38.getPaddingRight()
            int r0 = r0 + r2
            int r14 = r14 + r0
            int r0 = r38.getSuggestedMinimumWidth()
            int r0 = java.lang.Math.max(r14, r0)
            int r2 = android.view.View.resolveSizeAndState(r0, r10, r6)
            r7.setMeasuredDimension(r2, r5)
            if (r18 == 0) goto L_0x0463
            r7.forceUniformWidth(r11, r9)
        L_0x0463:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.measureVertical(int, int):void");
    }

    private void forceUniformWidth(int count, int heightMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), Declaration.MODULE_REFERENCE);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.width == -1) {
                    int oldHeight = lp.height;
                    lp.height = child.getMeasuredHeight();
                    measureChildWithMargins(child, uniformMeasureSpec, 0, heightMeasureSpec, 0);
                    lp.height = oldHeight;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:200:0x0549  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x0581  */
    /* JADX WARNING: Removed duplicated region for block: B:229:0x0638  */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x0640  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void measureHorizontal(int r49, int r50) {
        /*
            r48 = this;
            r7 = r48
            r8 = r49
            r9 = r50
            r10 = 0
            r7.mTotalLength = r10
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 1
            r5 = 0
            int r11 = r48.getVirtualChildCount()
            int r12 = android.view.View.MeasureSpec.getMode(r49)
            int r13 = android.view.View.MeasureSpec.getMode(r50)
            r6 = 0
            r14 = 0
            int[] r15 = r7.mMaxAscent
            if (r15 == 0) goto L_0x0025
            int[] r15 = r7.mMaxDescent
            if (r15 != 0) goto L_0x002e
        L_0x0025:
            r15 = 4
            int[] r10 = new int[r15]
            r7.mMaxAscent = r10
            int[] r10 = new int[r15]
            r7.mMaxDescent = r10
        L_0x002e:
            int[] r10 = r7.mMaxAscent
            int[] r15 = r7.mMaxDescent
            r17 = 3
            r18 = r6
            r6 = -1
            r10[r17] = r6
            r19 = 2
            r10[r19] = r6
            r20 = 1
            r10[r20] = r6
            r16 = 0
            r10[r16] = r6
            r15[r17] = r6
            r15[r19] = r6
            r15[r20] = r6
            r15[r16] = r6
            boolean r6 = r7.mBaselineAligned
            r22 = r14
            boolean r14 = r7.mUseLargestChild
            r9 = 1073741824(0x40000000, float:2.0)
            if (r12 != r9) goto L_0x005a
            r23 = 1
            goto L_0x005c
        L_0x005a:
            r23 = 0
        L_0x005c:
            r24 = 0
            r25 = 0
            r9 = r25
            r45 = r5
            r5 = r0
            r0 = r45
            r46 = r4
            r4 = r1
            r1 = r24
            r24 = r22
            r22 = r18
            r18 = r46
            r47 = r3
            r3 = r2
            r2 = r47
        L_0x0077:
            r28 = 0
            if (r9 >= r11) goto L_0x025e
            android.view.View r8 = r7.getVirtualChildAt(r9)
            if (r8 != 0) goto L_0x0097
            r30 = r1
            int r1 = r7.mTotalLength
            int r26 = r7.measureNullChild(r9)
            int r1 = r1 + r26
            r7.mTotalLength = r1
            r21 = r6
            r31 = r11
            r1 = r30
            r30 = r12
            goto L_0x0252
        L_0x0097:
            r30 = r1
            int r1 = r8.getVisibility()
            r31 = r2
            r2 = 8
            if (r1 != r2) goto L_0x00b4
            int r1 = r7.getChildrenSkipCount(r8, r9)
            int r9 = r9 + r1
            r21 = r6
            r1 = r30
            r2 = r31
            r31 = r11
            r30 = r12
            goto L_0x0252
        L_0x00b4:
            boolean r1 = r7.hasDividerBeforeChildAt(r9)
            if (r1 == 0) goto L_0x00c1
            int r1 = r7.mTotalLength
            int r2 = r7.mDividerWidth
            int r1 = r1 + r2
            r7.mTotalLength = r1
        L_0x00c1:
            android.view.ViewGroup$LayoutParams r1 = r8.getLayoutParams()
            r2 = r1
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r2 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r2
            float r1 = r2.weight
            float r29 = r0 + r1
            r0 = 1073741824(0x40000000, float:2.0)
            if (r12 != r0) goto L_0x012c
            int r0 = r2.width
            if (r0 != 0) goto L_0x012c
            float r0 = r2.weight
            int r0 = (r0 > r28 ? 1 : (r0 == r28 ? 0 : -1))
            if (r0 <= 0) goto L_0x012c
            if (r23 == 0) goto L_0x00ea
            int r0 = r7.mTotalLength
            int r1 = r2.leftMargin
            r32 = r3
            int r3 = r2.rightMargin
            int r1 = r1 + r3
            int r0 = r0 + r1
            r7.mTotalLength = r0
            goto L_0x00fa
        L_0x00ea:
            r32 = r3
            int r0 = r7.mTotalLength
            int r1 = r2.leftMargin
            int r1 = r1 + r0
            int r3 = r2.rightMargin
            int r1 = r1 + r3
            int r1 = java.lang.Math.max(r0, r1)
            r7.mTotalLength = r1
        L_0x00fa:
            if (r6 == 0) goto L_0x0117
            r0 = 0
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r0)
            r8.measure(r1, r1)
            r1 = r2
            r40 = r4
            r21 = r6
            r3 = r30
            r36 = r31
            r38 = r32
            r31 = r11
            r30 = r12
            r11 = -1
            r12 = r5
            goto L_0x01b5
        L_0x0117:
            r24 = 1
            r1 = r2
            r40 = r4
            r21 = r6
            r3 = r30
            r36 = r31
            r38 = r32
            r31 = r11
            r30 = r12
            r11 = -1
            r12 = r5
            goto L_0x01b5
        L_0x012c:
            r32 = r3
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            int r1 = r2.width
            if (r1 != 0) goto L_0x0140
            float r1 = r2.weight
            int r1 = (r1 > r28 ? 1 : (r1 == r28 ? 0 : -1))
            if (r1 <= 0) goto L_0x0140
            r0 = 0
            r1 = -2
            r2.width = r1
            r3 = r0
            goto L_0x0141
        L_0x0140:
            r3 = r0
        L_0x0141:
            int r0 = (r29 > r28 ? 1 : (r29 == r28 ? 0 : -1))
            if (r0 != 0) goto L_0x014a
            int r0 = r7.mTotalLength
            r33 = r0
            goto L_0x014c
        L_0x014a:
            r33 = 0
        L_0x014c:
            r34 = 0
            r0 = r48
            r35 = r30
            r1 = r8
            r37 = r2
            r36 = r31
            r2 = r9
            r39 = r3
            r38 = r32
            r3 = r49
            r40 = r4
            r4 = r33
            r30 = r12
            r12 = r5
            r5 = r50
            r21 = r6
            r31 = r11
            r11 = -1
            r6 = r34
            r0.measureChildBeforeLayout(r1, r2, r3, r4, r5, r6)
            r0 = r39
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r0 == r1) goto L_0x017c
            r1 = r37
            r1.width = r0
            goto L_0x017e
        L_0x017c:
            r1 = r37
        L_0x017e:
            int r2 = r8.getMeasuredWidth()
            if (r23 == 0) goto L_0x0195
            int r3 = r7.mTotalLength
            int r4 = r1.leftMargin
            int r4 = r4 + r2
            int r5 = r1.rightMargin
            int r4 = r4 + r5
            int r5 = r7.getNextLocationOffset(r8)
            int r4 = r4 + r5
            int r3 = r3 + r4
            r7.mTotalLength = r3
            goto L_0x01aa
        L_0x0195:
            int r3 = r7.mTotalLength
            int r4 = r3 + r2
            int r5 = r1.leftMargin
            int r4 = r4 + r5
            int r5 = r1.rightMargin
            int r4 = r4 + r5
            int r5 = r7.getNextLocationOffset(r8)
            int r4 = r4 + r5
            int r4 = java.lang.Math.max(r3, r4)
            r7.mTotalLength = r4
        L_0x01aa:
            if (r14 == 0) goto L_0x01b3
            r3 = r35
            int r3 = java.lang.Math.max(r2, r3)
            goto L_0x01b5
        L_0x01b3:
            r3 = r35
        L_0x01b5:
            r0 = 0
            r2 = 1073741824(0x40000000, float:2.0)
            if (r13 == r2) goto L_0x01c1
            int r2 = r1.height
            if (r2 != r11) goto L_0x01c1
            r22 = 1
            r0 = 1
        L_0x01c1:
            int r2 = r1.topMargin
            int r4 = r1.bottomMargin
            int r2 = r2 + r4
            int r4 = r8.getMeasuredHeight()
            int r4 = r4 + r2
            int r5 = r8.getMeasuredState()
            r6 = r40
            int r5 = android.view.View.combineMeasuredStates(r6, r5)
            if (r21 == 0) goto L_0x020c
            int r6 = r8.getBaseline()
            if (r6 == r11) goto L_0x0207
            int r11 = r1.gravity
            if (r11 >= 0) goto L_0x01e4
            int r11 = r7.mGravity
            goto L_0x01e6
        L_0x01e4:
            int r11 = r1.gravity
        L_0x01e6:
            r11 = r11 & 112(0x70, float:1.57E-43)
            int r26 = r11 >> 4
            r27 = -2
            r26 = r26 & -2
            int r26 = r26 >> 1
            r27 = r2
            r2 = r10[r26]
            int r2 = java.lang.Math.max(r2, r6)
            r10[r26] = r2
            r2 = r15[r26]
            r33 = r3
            int r3 = r4 - r6
            int r2 = java.lang.Math.max(r2, r3)
            r15[r26] = r2
            goto L_0x0210
        L_0x0207:
            r27 = r2
            r33 = r3
            goto L_0x0210
        L_0x020c:
            r27 = r2
            r33 = r3
        L_0x0210:
            int r2 = java.lang.Math.max(r12, r4)
            if (r18 == 0) goto L_0x021d
            int r3 = r1.height
            r6 = -1
            if (r3 != r6) goto L_0x021d
            r3 = 1
            goto L_0x021e
        L_0x021d:
            r3 = 0
        L_0x021e:
            float r6 = r1.weight
            int r6 = (r6 > r28 ? 1 : (r6 == r28 ? 0 : -1))
            if (r6 <= 0) goto L_0x0231
            if (r0 == 0) goto L_0x0229
            r6 = r27
            goto L_0x022a
        L_0x0229:
            r6 = r4
        L_0x022a:
            r11 = r36
            int r6 = java.lang.Math.max(r11, r6)
            goto L_0x0242
        L_0x0231:
            r11 = r36
            if (r0 == 0) goto L_0x0238
            r6 = r27
            goto L_0x0239
        L_0x0238:
            r6 = r4
        L_0x0239:
            r12 = r38
            int r6 = java.lang.Math.max(r12, r6)
            r38 = r6
            r6 = r11
        L_0x0242:
            int r11 = r7.getChildrenSkipCount(r8, r9)
            int r9 = r9 + r11
            r18 = r3
            r4 = r5
            r0 = r29
            r1 = r33
            r3 = r38
            r5 = r2
            r2 = r6
        L_0x0252:
            int r9 = r9 + 1
            r8 = r49
            r6 = r21
            r12 = r30
            r11 = r31
            goto L_0x0077
        L_0x025e:
            r21 = r6
            r31 = r11
            r30 = r12
            r11 = r2
            r2 = r3
            r6 = r4
            r12 = r5
            r3 = r1
            int r1 = r7.mTotalLength
            if (r1 <= 0) goto L_0x027d
            r1 = r31
            boolean r4 = r7.hasDividerBeforeChildAt(r1)
            if (r4 == 0) goto L_0x027f
            int r4 = r7.mTotalLength
            int r5 = r7.mDividerWidth
            int r4 = r4 + r5
            r7.mTotalLength = r4
            goto L_0x027f
        L_0x027d:
            r1 = r31
        L_0x027f:
            r4 = r10[r20]
            r5 = -1
            if (r4 != r5) goto L_0x0296
            r4 = 0
            r8 = r10[r4]
            if (r8 != r5) goto L_0x0296
            r4 = r10[r19]
            if (r4 != r5) goto L_0x0296
            r4 = r10[r17]
            if (r4 == r5) goto L_0x0292
            goto L_0x0296
        L_0x0292:
            r40 = r6
            r5 = r12
            goto L_0x02c9
        L_0x0296:
            r4 = r10[r17]
            r5 = 0
            r8 = r10[r5]
            r9 = r10[r20]
            r5 = r10[r19]
            int r5 = java.lang.Math.max(r9, r5)
            int r5 = java.lang.Math.max(r8, r5)
            int r4 = java.lang.Math.max(r4, r5)
            r5 = r15[r17]
            r8 = 0
            r9 = r15[r8]
            r8 = r15[r20]
            r40 = r6
            r6 = r15[r19]
            int r6 = java.lang.Math.max(r8, r6)
            int r6 = java.lang.Math.max(r9, r6)
            int r5 = java.lang.Math.max(r5, r6)
            int r6 = r4 + r5
            int r6 = java.lang.Math.max(r12, r6)
            r5 = r6
        L_0x02c9:
            if (r14 == 0) goto L_0x0345
            r4 = r30
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r4 == r6) goto L_0x02d8
            if (r4 != 0) goto L_0x02d4
            goto L_0x02d8
        L_0x02d4:
            r26 = r5
            goto L_0x0349
        L_0x02d8:
            r6 = 0
            r7.mTotalLength = r6
            r6 = 0
        L_0x02dc:
            if (r6 >= r1) goto L_0x0340
            android.view.View r8 = r7.getVirtualChildAt(r6)
            if (r8 != 0) goto L_0x02f2
            int r9 = r7.mTotalLength
            int r12 = r7.measureNullChild(r6)
            int r9 = r9 + r12
            r7.mTotalLength = r9
            r26 = r5
            r30 = r6
            goto L_0x0339
        L_0x02f2:
            int r9 = r8.getVisibility()
            r12 = 8
            if (r9 != r12) goto L_0x0302
            int r9 = r7.getChildrenSkipCount(r8, r6)
            int r6 = r6 + r9
            r26 = r5
            goto L_0x033b
        L_0x0302:
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r9 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r9
            if (r23 == 0) goto L_0x0320
            int r12 = r7.mTotalLength
            r26 = r5
            int r5 = r9.leftMargin
            int r5 = r5 + r3
            r30 = r6
            int r6 = r9.rightMargin
            int r5 = r5 + r6
            int r6 = r7.getNextLocationOffset(r8)
            int r5 = r5 + r6
            int r12 = r12 + r5
            r7.mTotalLength = r12
            goto L_0x0339
        L_0x0320:
            r26 = r5
            r30 = r6
            int r5 = r7.mTotalLength
            int r6 = r5 + r3
            int r12 = r9.leftMargin
            int r6 = r6 + r12
            int r12 = r9.rightMargin
            int r6 = r6 + r12
            int r12 = r7.getNextLocationOffset(r8)
            int r6 = r6 + r12
            int r6 = java.lang.Math.max(r5, r6)
            r7.mTotalLength = r6
        L_0x0339:
            r6 = r30
        L_0x033b:
            int r6 = r6 + 1
            r5 = r26
            goto L_0x02dc
        L_0x0340:
            r26 = r5
            r30 = r6
            goto L_0x0349
        L_0x0345:
            r26 = r5
            r4 = r30
        L_0x0349:
            int r5 = r7.mTotalLength
            int r6 = r48.getPaddingLeft()
            int r8 = r48.getPaddingRight()
            int r6 = r6 + r8
            int r5 = r5 + r6
            r7.mTotalLength = r5
            int r5 = r7.mTotalLength
            int r6 = r48.getSuggestedMinimumWidth()
            int r5 = java.lang.Math.max(r5, r6)
            r6 = r49
            r8 = 0
            int r9 = android.view.View.resolveSizeAndState(r5, r6, r8)
            r8 = 16777215(0xffffff, float:2.3509886E-38)
            r5 = r9 & r8
            int r8 = r7.mTotalLength
            int r8 = r5 - r8
            if (r24 != 0) goto L_0x0417
            if (r8 == 0) goto L_0x0384
            int r30 = (r0 > r28 ? 1 : (r0 == r28 ? 0 : -1))
            if (r30 <= 0) goto L_0x0384
            r31 = r0
            r35 = r3
            r33 = r5
            r3 = r2
            r2 = 1073741824(0x40000000, float:2.0)
            goto L_0x0420
        L_0x0384:
            int r2 = java.lang.Math.max(r2, r11)
            if (r14 == 0) goto L_0x03f7
            r12 = 1073741824(0x40000000, float:2.0)
            if (r4 == r12) goto L_0x03f7
            r12 = 0
        L_0x038f:
            if (r12 >= r1) goto L_0x03ec
            r31 = r0
            android.view.View r0 = r7.getVirtualChildAt(r12)
            if (r0 == 0) goto L_0x03d9
            r16 = r2
            int r2 = r0.getVisibility()
            r33 = r5
            r5 = 8
            if (r2 != r5) goto L_0x03aa
            r35 = r3
            r2 = 1073741824(0x40000000, float:2.0)
            goto L_0x03e1
        L_0x03aa:
            android.view.ViewGroup$LayoutParams r2 = r0.getLayoutParams()
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r2 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r2
            float r5 = r2.weight
            int r17 = (r5 > r28 ? 1 : (r5 == r28 ? 0 : -1))
            if (r17 <= 0) goto L_0x03d0
            r17 = r2
            r19 = r5
            r2 = 1073741824(0x40000000, float:2.0)
            int r5 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r2)
            r35 = r3
            int r3 = r0.getMeasuredHeight()
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r2)
            r0.measure(r5, r3)
            goto L_0x03e1
        L_0x03d0:
            r17 = r2
            r35 = r3
            r19 = r5
            r2 = 1073741824(0x40000000, float:2.0)
            goto L_0x03e1
        L_0x03d9:
            r16 = r2
            r35 = r3
            r33 = r5
            r2 = 1073741824(0x40000000, float:2.0)
        L_0x03e1:
            int r12 = r12 + 1
            r2 = r16
            r0 = r31
            r5 = r33
            r3 = r35
            goto L_0x038f
        L_0x03ec:
            r31 = r0
            r16 = r2
            r35 = r3
            r33 = r5
            r2 = 1073741824(0x40000000, float:2.0)
            goto L_0x0401
        L_0x03f7:
            r31 = r0
            r16 = r2
            r35 = r3
            r33 = r5
            r2 = 1073741824(0x40000000, float:2.0)
        L_0x0401:
            r12 = r50
            r34 = r1
            r25 = r9
            r36 = r11
            r2 = r16
            r5 = r26
            r26 = r14
            r45 = r40
            r40 = r4
            r4 = r45
            goto L_0x060f
        L_0x0417:
            r31 = r0
            r35 = r3
            r33 = r5
            r3 = r2
            r2 = 1073741824(0x40000000, float:2.0)
        L_0x0420:
            float r0 = r7.mWeightSum
            int r0 = (r0 > r28 ? 1 : (r0 == r28 ? 0 : -1))
            if (r0 <= 0) goto L_0x0429
            float r0 = r7.mWeightSum
            goto L_0x042b
        L_0x0429:
            r0 = r31
        L_0x042b:
            r5 = -1
            r10[r17] = r5
            r10[r19] = r5
            r10[r20] = r5
            r12 = 0
            r10[r12] = r5
            r15[r17] = r5
            r15[r19] = r5
            r15[r20] = r5
            r15[r12] = r5
            r5 = -1
            r7.mTotalLength = r12
            r12 = 0
            r2 = r12
            r12 = r8
            r8 = r5
            r5 = r40
        L_0x0446:
            if (r2 >= r1) goto L_0x05ac
            r36 = r11
            android.view.View r11 = r7.getVirtualChildAt(r2)
            if (r11 == 0) goto L_0x058e
            r26 = r14
            int r14 = r11.getVisibility()
            r34 = r1
            r1 = 8
            if (r14 != r1) goto L_0x0467
            r40 = r4
            r25 = r9
            r1 = r12
            r27 = -2
            r12 = r50
            goto L_0x059b
        L_0x0467:
            android.view.ViewGroup$LayoutParams r14 = r11.getLayoutParams()
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r14 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r14
            float r1 = r14.weight
            int r37 = (r1 > r28 ? 1 : (r1 == r28 ? 0 : -1))
            if (r37 <= 0) goto L_0x04da
            float r6 = (float) r12
            float r6 = r6 * r1
            float r6 = r6 / r0
            int r6 = (int) r6
            float r0 = r0 - r1
            int r12 = r12 - r6
            int r37 = r48.getPaddingTop()
            int r38 = r48.getPaddingBottom()
            int r37 = r37 + r38
            r38 = r0
            int r0 = r14.topMargin
            int r37 = r37 + r0
            int r0 = r14.bottomMargin
            int r0 = r37 + r0
            r37 = r1
            int r1 = r14.height
            r25 = r9
            r39 = r12
            r9 = 1073741824(0x40000000, float:2.0)
            r12 = r50
            int r0 = getChildMeasureSpec(r12, r0, r1)
            int r1 = r14.width
            if (r1 != 0) goto L_0x04b6
            if (r4 == r9) goto L_0x04a7
            goto L_0x04b6
        L_0x04a7:
            if (r6 <= 0) goto L_0x04ab
            r1 = r6
            goto L_0x04ac
        L_0x04ab:
            r1 = 0
        L_0x04ac:
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r9)
            r11.measure(r1, r0)
            r40 = r4
            goto L_0x04c9
        L_0x04b6:
            int r1 = r11.getMeasuredWidth()
            int r1 = r1 + r6
            if (r1 >= 0) goto L_0x04be
            r1 = 0
        L_0x04be:
            r40 = r4
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r9)
            r11.measure(r4, r0)
        L_0x04c9:
            int r1 = r11.getMeasuredState()
            r4 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r1 = r1 & r4
            int r5 = android.view.View.combineMeasuredStates(r5, r1)
            r0 = r38
            r1 = r39
            goto L_0x04e5
        L_0x04da:
            r37 = r1
            r40 = r4
            r25 = r9
            r1 = r12
            r9 = 1073741824(0x40000000, float:2.0)
            r12 = r50
        L_0x04e5:
            if (r23 == 0) goto L_0x04fc
            int r4 = r7.mTotalLength
            int r6 = r11.getMeasuredWidth()
            int r9 = r14.leftMargin
            int r6 = r6 + r9
            int r9 = r14.rightMargin
            int r6 = r6 + r9
            int r9 = r7.getNextLocationOffset(r11)
            int r6 = r6 + r9
            int r4 = r4 + r6
            r7.mTotalLength = r4
            goto L_0x0514
        L_0x04fc:
            int r4 = r7.mTotalLength
            int r6 = r11.getMeasuredWidth()
            int r6 = r6 + r4
            int r9 = r14.leftMargin
            int r6 = r6 + r9
            int r9 = r14.rightMargin
            int r6 = r6 + r9
            int r9 = r7.getNextLocationOffset(r11)
            int r6 = r6 + r9
            int r6 = java.lang.Math.max(r4, r6)
            r7.mTotalLength = r6
        L_0x0514:
            r4 = 1073741824(0x40000000, float:2.0)
            if (r13 == r4) goto L_0x051f
            int r4 = r14.height
            r6 = -1
            if (r4 != r6) goto L_0x051f
            r4 = 1
            goto L_0x0520
        L_0x051f:
            r4 = 0
        L_0x0520:
            int r6 = r14.topMargin
            int r9 = r14.bottomMargin
            int r6 = r6 + r9
            int r9 = r11.getMeasuredHeight()
            int r9 = r9 + r6
            int r8 = java.lang.Math.max(r8, r9)
            r39 = r0
            if (r4 == 0) goto L_0x0534
            r0 = r6
            goto L_0x0535
        L_0x0534:
            r0 = r9
        L_0x0535:
            int r0 = java.lang.Math.max(r3, r0)
            if (r18 == 0) goto L_0x0544
            int r3 = r14.height
            r41 = r0
            r0 = -1
            if (r3 != r0) goto L_0x0546
            r0 = 1
            goto L_0x0547
        L_0x0544:
            r41 = r0
        L_0x0546:
            r0 = 0
        L_0x0547:
            if (r21 == 0) goto L_0x0581
            int r3 = r11.getBaseline()
            r18 = r0
            r0 = -1
            if (r3 == r0) goto L_0x057c
            int r0 = r14.gravity
            if (r0 >= 0) goto L_0x0559
            int r0 = r7.mGravity
            goto L_0x055b
        L_0x0559:
            int r0 = r14.gravity
        L_0x055b:
            r0 = r0 & 112(0x70, float:1.57E-43)
            int r42 = r0 >> 4
            r27 = -2
            r42 = r42 & -2
            int r42 = r42 >> 1
            r43 = r0
            r0 = r10[r42]
            int r0 = java.lang.Math.max(r0, r3)
            r10[r42] = r0
            r0 = r15[r42]
            r44 = r1
            int r1 = r9 - r3
            int r0 = java.lang.Math.max(r0, r1)
            r15[r42] = r0
            goto L_0x0587
        L_0x057c:
            r44 = r1
            r27 = -2
            goto L_0x0587
        L_0x0581:
            r18 = r0
            r44 = r1
            r27 = -2
        L_0x0587:
            r0 = r39
            r3 = r41
            r1 = r44
            goto L_0x059b
        L_0x058e:
            r34 = r1
            r40 = r4
            r25 = r9
            r1 = r12
            r26 = r14
            r27 = -2
            r12 = r50
        L_0x059b:
            int r2 = r2 + 1
            r6 = r49
            r12 = r1
            r9 = r25
            r14 = r26
            r1 = r34
            r11 = r36
            r4 = r40
            goto L_0x0446
        L_0x05ac:
            r34 = r1
            r40 = r4
            r25 = r9
            r36 = r11
            r1 = r12
            r26 = r14
            r12 = r50
            int r2 = r7.mTotalLength
            int r4 = r48.getPaddingLeft()
            int r6 = r48.getPaddingRight()
            int r4 = r4 + r6
            int r2 = r2 + r4
            r7.mTotalLength = r2
            r2 = r10[r20]
            r4 = -1
            if (r2 != r4) goto L_0x05dc
            r2 = 0
            r6 = r10[r2]
            if (r6 != r4) goto L_0x05dc
            r2 = r10[r19]
            if (r2 != r4) goto L_0x05dc
            r2 = r10[r17]
            if (r2 == r4) goto L_0x05da
            goto L_0x05dc
        L_0x05da:
            r6 = r8
            goto L_0x060b
        L_0x05dc:
            r2 = r10[r17]
            r4 = 0
            r6 = r10[r4]
            r9 = r10[r20]
            r11 = r10[r19]
            int r9 = java.lang.Math.max(r9, r11)
            int r6 = java.lang.Math.max(r6, r9)
            int r2 = java.lang.Math.max(r2, r6)
            r6 = r15[r17]
            r4 = r15[r4]
            r9 = r15[r20]
            r11 = r15[r19]
            int r9 = java.lang.Math.max(r9, r11)
            int r4 = java.lang.Math.max(r4, r9)
            int r4 = java.lang.Math.max(r6, r4)
            int r6 = r2 + r4
            int r6 = java.lang.Math.max(r8, r6)
        L_0x060b:
            r8 = r1
            r2 = r3
            r4 = r5
            r5 = r6
        L_0x060f:
            if (r18 != 0) goto L_0x0616
            r0 = 1073741824(0x40000000, float:2.0)
            if (r13 == r0) goto L_0x0616
            r5 = r2
        L_0x0616:
            int r0 = r48.getPaddingTop()
            int r1 = r48.getPaddingBottom()
            int r0 = r0 + r1
            int r5 = r5 + r0
            int r0 = r48.getSuggestedMinimumHeight()
            int r0 = java.lang.Math.max(r5, r0)
            r1 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r1 = r1 & r4
            r1 = r25 | r1
            int r3 = r4 << 16
            int r3 = android.view.View.resolveSizeAndState(r0, r12, r3)
            r7.setMeasuredDimension(r1, r3)
            if (r22 == 0) goto L_0x0640
            r1 = r49
            r3 = r34
            r7.forceUniformHeight(r3, r1)
            goto L_0x0644
        L_0x0640:
            r1 = r49
            r3 = r34
        L_0x0644:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.measureHorizontal(int, int):void");
    }

    private void forceUniformHeight(int count, int widthMeasureSpec) {
        int uniformMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), Declaration.MODULE_REFERENCE);
        for (int i = 0; i < count; i++) {
            View child = getVirtualChildAt(i);
            if (child.getVisibility() != 8) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (lp.height == -1) {
                    int oldWidth = lp.width;
                    lp.width = child.getMeasuredWidth();
                    measureChildWithMargins(child, widthMeasureSpec, 0, uniformMeasureSpec, 0);
                    lp.width = oldWidth;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getChildrenSkipCount(View child, int index) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int measureNullChild(int childIndex) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void measureChildBeforeLayout(View child, int childIndex, int widthMeasureSpec, int totalWidth, int heightMeasureSpec, int totalHeight) {
        measureChildWithMargins(child, widthMeasureSpec, totalWidth, heightMeasureSpec, totalHeight);
    }

    /* access modifiers changed from: package-private */
    public int getLocationOffset(View child) {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int getNextLocationOffset(View child) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mOrientation == 1) {
            layoutVertical(l, t, r, b);
        } else {
            layoutHorizontal(l, t, r, b);
        }
    }

    /* access modifiers changed from: package-private */
    public void layoutVertical(int left, int top, int right, int bottom) {
        int childTop;
        int paddingLeft;
        int gravity;
        int childLeft;
        int paddingLeft2 = getPaddingLeft();
        int width = right - left;
        int childRight = width - getPaddingRight();
        int childSpace = (width - paddingLeft2) - getPaddingRight();
        int count = getVirtualChildCount();
        int majorGravity = this.mGravity & 112;
        int minorGravity = this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        switch (majorGravity) {
            case 16:
                childTop = getPaddingTop() + (((bottom - top) - this.mTotalLength) / 2);
                break;
            case 80:
                childTop = ((getPaddingTop() + bottom) - top) - this.mTotalLength;
                break;
            default:
                childTop = getPaddingTop();
                break;
        }
        int i = 0;
        while (i < count) {
            View child = getVirtualChildAt(i);
            if (child == null) {
                childTop += measureNullChild(i);
                paddingLeft = paddingLeft2;
            } else if (child.getVisibility() != 8) {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                int gravity2 = lp.gravity;
                if (gravity2 < 0) {
                    gravity = minorGravity;
                } else {
                    gravity = gravity2;
                }
                int layoutDirection = ViewCompat.getLayoutDirection(this);
                switch (GravityCompat.getAbsoluteGravity(gravity, layoutDirection) & 7) {
                    case 1:
                        childLeft = ((((childSpace - childWidth) / 2) + paddingLeft2) + lp.leftMargin) - lp.rightMargin;
                        break;
                    case 5:
                        childLeft = (childRight - childWidth) - lp.rightMargin;
                        break;
                    default:
                        childLeft = lp.leftMargin + paddingLeft2;
                        break;
                }
                if (hasDividerBeforeChildAt(i) != 0) {
                    childTop += this.mDividerHeight;
                }
                int childTop2 = childTop + lp.topMargin;
                int i2 = layoutDirection;
                int i3 = gravity;
                paddingLeft = paddingLeft2;
                setChildFrame(child, childLeft, childTop2 + getLocationOffset(child), childWidth, childHeight);
                int childTop3 = childTop2 + childHeight + lp.bottomMargin + getNextLocationOffset(child);
                i += getChildrenSkipCount(child, i);
                childTop = childTop3;
            } else {
                paddingLeft = paddingLeft2;
            }
            i++;
            paddingLeft2 = paddingLeft;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00d4  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00ed  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00fd  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0111  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void layoutHorizontal(int r34, int r35, int r36, int r37) {
        /*
            r33 = this;
            r6 = r33
            boolean r7 = androidx.appcompat.widget.ViewUtils.isLayoutRtl(r33)
            int r8 = r33.getPaddingTop()
            int r9 = r37 - r35
            int r0 = r33.getPaddingBottom()
            int r10 = r9 - r0
            int r0 = r9 - r8
            int r1 = r33.getPaddingBottom()
            int r11 = r0 - r1
            int r12 = r33.getVirtualChildCount()
            int r0 = r6.mGravity
            r1 = 8388615(0x800007, float:1.1754953E-38)
            r13 = r0 & r1
            int r0 = r6.mGravity
            r14 = r0 & 112(0x70, float:1.57E-43)
            boolean r15 = r6.mBaselineAligned
            int[] r5 = r6.mMaxAscent
            int[] r4 = r6.mMaxDescent
            int r3 = androidx.core.view.ViewCompat.getLayoutDirection(r33)
            int r0 = androidx.core.view.GravityCompat.getAbsoluteGravity(r13, r3)
            r16 = 2
            switch(r0) {
                case 1: goto L_0x004d;
                case 5: goto L_0x0041;
                default: goto L_0x003c;
            }
        L_0x003c:
            int r0 = r33.getPaddingLeft()
            goto L_0x005a
        L_0x0041:
            int r0 = r33.getPaddingLeft()
            int r0 = r0 + r36
            int r0 = r0 - r34
            int r1 = r6.mTotalLength
            int r0 = r0 - r1
            goto L_0x005a
        L_0x004d:
            int r0 = r33.getPaddingLeft()
            int r1 = r36 - r34
            int r2 = r6.mTotalLength
            int r1 = r1 - r2
            int r1 = r1 / 2
            int r0 = r0 + r1
        L_0x005a:
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L_0x0066
            int r1 = r12 + -1
            r2 = -1
            r17 = r1
            r18 = r2
            goto L_0x006a
        L_0x0066:
            r17 = r1
            r18 = r2
        L_0x006a:
            r1 = 0
            r2 = r1
        L_0x006c:
            if (r2 >= r12) goto L_0x0166
            int r1 = r18 * r2
            int r1 = r17 + r1
            r19 = r7
            android.view.View r7 = r6.getVirtualChildAt(r1)
            r20 = 1
            if (r7 != 0) goto L_0x0090
            int r21 = r6.measureNullChild(r1)
            int r0 = r0 + r21
            r22 = r3
            r31 = r4
            r32 = r5
            r29 = r8
            r26 = r9
            r28 = r10
            goto L_0x0154
        L_0x0090:
            r21 = r2
            int r2 = r7.getVisibility()
            r22 = r3
            r3 = 8
            if (r2 == r3) goto L_0x0147
            int r23 = r7.getMeasuredWidth()
            int r24 = r7.getMeasuredHeight()
            r2 = -1
            android.view.ViewGroup$LayoutParams r3 = r7.getLayoutParams()
            androidx.appcompat.widget.LinearLayoutCompat$LayoutParams r3 = (androidx.appcompat.widget.LinearLayoutCompat.LayoutParams) r3
            r25 = r2
            r2 = -1
            if (r15 == 0) goto L_0x00bc
            r26 = r9
            int r9 = r3.height
            if (r9 == r2) goto L_0x00be
            int r9 = r7.getBaseline()
            goto L_0x00c0
        L_0x00bc:
            r26 = r9
        L_0x00be:
            r9 = r25
        L_0x00c0:
            int r2 = r3.gravity
            if (r2 >= 0) goto L_0x00c8
            r2 = r14
            r27 = r2
            goto L_0x00ca
        L_0x00c8:
            r27 = r2
        L_0x00ca:
            r2 = r27 & 112(0x70, float:1.57E-43)
            switch(r2) {
                case 16: goto L_0x00fd;
                case 48: goto L_0x00ed;
                case 80: goto L_0x00d4;
                default: goto L_0x00cf;
            }
        L_0x00cf:
            r28 = r10
            r2 = r8
            r10 = r2
            goto L_0x010b
        L_0x00d4:
            int r2 = r10 - r24
            r28 = r10
            int r10 = r3.bottomMargin
            int r2 = r2 - r10
            r10 = -1
            if (r9 == r10) goto L_0x00eb
            int r10 = r7.getMeasuredHeight()
            int r10 = r10 - r9
            r25 = r4[r16]
            int r25 = r25 - r10
            int r2 = r2 - r25
            r10 = r2
            goto L_0x010b
        L_0x00eb:
            r10 = r2
            goto L_0x010b
        L_0x00ed:
            r28 = r10
            int r2 = r3.topMargin
            int r2 = r2 + r8
            r10 = -1
            if (r9 == r10) goto L_0x00fb
            r10 = r5[r20]
            int r10 = r10 - r9
            int r2 = r2 + r10
            r10 = r2
            goto L_0x010b
        L_0x00fb:
            r10 = r2
            goto L_0x010b
        L_0x00fd:
            r28 = r10
            int r2 = r11 - r24
            int r2 = r2 / 2
            int r2 = r2 + r8
            int r10 = r3.topMargin
            int r2 = r2 + r10
            int r10 = r3.bottomMargin
            int r2 = r2 - r10
            r10 = r2
        L_0x010b:
            boolean r2 = r6.hasDividerBeforeChildAt(r1)
            if (r2 == 0) goto L_0x0114
            int r2 = r6.mDividerWidth
            int r0 = r0 + r2
        L_0x0114:
            int r2 = r3.leftMargin
            int r25 = r0 + r2
            int r0 = r6.getLocationOffset(r7)
            int r2 = r25 + r0
            r0 = r33
            r29 = r8
            r8 = r1
            r1 = r7
            r30 = r9
            r9 = r3
            r3 = r10
            r31 = r4
            r4 = r23
            r32 = r5
            r5 = r24
            r0.setChildFrame(r1, r2, r3, r4, r5)
            int r0 = r9.rightMargin
            int r0 = r23 + r0
            int r1 = r6.getNextLocationOffset(r7)
            int r0 = r0 + r1
            int r25 = r25 + r0
            int r0 = r6.getChildrenSkipCount(r7, r8)
            int r2 = r21 + r0
            r0 = r25
            goto L_0x0154
        L_0x0147:
            r31 = r4
            r32 = r5
            r29 = r8
            r26 = r9
            r28 = r10
            r8 = r1
            r2 = r21
        L_0x0154:
            int r2 = r2 + 1
            r7 = r19
            r3 = r22
            r9 = r26
            r10 = r28
            r8 = r29
            r4 = r31
            r5 = r32
            goto L_0x006c
        L_0x0166:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.LinearLayoutCompat.layoutHorizontal(int, int, int, int):void");
    }

    private void setChildFrame(View child, int left, int top, int width, int height) {
        child.layout(left, top, left + width, top + height);
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            requestLayout();
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            if ((8388615 & gravity) == 0) {
                gravity |= GravityCompat.START;
            }
            if ((gravity & 112) == 0) {
                gravity |= 48;
            }
            this.mGravity = gravity;
            requestLayout();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setHorizontalGravity(int horizontalGravity) {
        int gravity = horizontalGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((8388615 & this.mGravity) != gravity) {
            this.mGravity = (this.mGravity & -8388616) | gravity;
            requestLayout();
        }
    }

    public void setVerticalGravity(int verticalGravity) {
        int gravity = verticalGravity & 112;
        if ((this.mGravity & 112) != gravity) {
            this.mGravity = (this.mGravity & -113) | gravity;
            requestLayout();
        }
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -2);
        }
        if (this.mOrientation == 1) {
            return new LayoutParams(-1, -2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(LinearLayoutCompat.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(LinearLayoutCompat.class.getName());
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.gravity = -1;
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.LinearLayoutCompat_Layout);
            this.weight = a.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = a.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int width, int height, float weight2) {
            super(width, height);
            this.gravity = -1;
            this.weight = weight2;
        }

        public LayoutParams(ViewGroup.LayoutParams p) {
            super(p);
            this.gravity = -1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
            this.gravity = -1;
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.gravity = -1;
            this.weight = source.weight;
            this.gravity = source.gravity;
        }
    }
}
