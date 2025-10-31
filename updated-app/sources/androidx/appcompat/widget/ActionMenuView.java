package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.LinearLayoutCompat;
import gnu.expr.Declaration;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public ActionMenuView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ActionMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBaselineAligned(false);
        float density = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * density);
        this.mGeneratedItemPadding = (int) (4.0f * density);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    public void setPopupTheme(int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setPresenter(ActionMenuPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.setMenuView(this);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mPresenter != null) {
            this.mPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        boolean wasFormatted = this.mFormatItems;
        this.mFormatItems = View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824;
        if (wasFormatted != this.mFormatItems) {
            this.mFormatItemsWidth = 0;
        }
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (!(!this.mFormatItems || this.mMenu == null || widthSize == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = widthSize;
            this.mMenu.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.mFormatItems || childCount <= 0) {
            for (int i = 0; i < childCount; i++) {
                LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                lp.rightMargin = 0;
                lp.leftMargin = 0;
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
    }

    /* JADX WARNING: Removed duplicated region for block: B:137:0x02a7  */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x02dd  */
    /* JADX WARNING: Removed duplicated region for block: B:148:0x02e5  */
    /* JADX WARNING: Removed duplicated region for block: B:149:0x02e7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onMeasureExactFormat(int r40, int r41) {
        /*
            r39 = this;
            r0 = r39
            int r1 = android.view.View.MeasureSpec.getMode(r41)
            int r2 = android.view.View.MeasureSpec.getSize(r40)
            int r3 = android.view.View.MeasureSpec.getSize(r41)
            int r4 = r39.getPaddingLeft()
            int r5 = r39.getPaddingRight()
            int r4 = r4 + r5
            int r5 = r39.getPaddingTop()
            int r6 = r39.getPaddingBottom()
            int r5 = r5 + r6
            r6 = -2
            r7 = r41
            int r6 = getChildMeasureSpec(r7, r5, r6)
            int r2 = r2 - r4
            int r8 = r0.mMinCellSize
            int r8 = r2 / r8
            int r9 = r0.mMinCellSize
            int r9 = r2 % r9
            r10 = 0
            if (r8 != 0) goto L_0x0037
            r0.setMeasuredDimension(r2, r10)
            return
        L_0x0037:
            int r11 = r0.mMinCellSize
            int r12 = r9 / r8
            int r11 = r11 + r12
            r12 = r8
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            int r10 = r39.getChildCount()
            r21 = 0
            r37 = r16
            r16 = r3
            r3 = r37
            r38 = r21
            r21 = r4
            r4 = r38
        L_0x0058:
            if (r4 >= r10) goto L_0x00f4
            android.view.View r7 = r0.getChildAt(r4)
            r23 = r8
            int r8 = r7.getVisibility()
            r24 = r9
            r9 = 8
            if (r8 != r9) goto L_0x006e
            r26 = r2
            goto L_0x00e8
        L_0x006e:
            boolean r8 = r7 instanceof androidx.appcompat.view.menu.ActionMenuItemView
            int r3 = r3 + 1
            if (r8 == 0) goto L_0x0081
            int r9 = r0.mGeneratedItemPadding
            r25 = r3
            int r3 = r0.mGeneratedItemPadding
            r26 = r2
            r2 = 0
            r7.setPadding(r9, r2, r3, r2)
            goto L_0x0086
        L_0x0081:
            r26 = r2
            r25 = r3
            r2 = 0
        L_0x0086:
            android.view.ViewGroup$LayoutParams r3 = r7.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r3 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r3
            r3.expanded = r2
            r3.extraPixels = r2
            r3.cellsUsed = r2
            r3.expandable = r2
            r3.leftMargin = r2
            r3.rightMargin = r2
            if (r8 == 0) goto L_0x00a5
            r2 = r7
            androidx.appcompat.view.menu.ActionMenuItemView r2 = (androidx.appcompat.view.menu.ActionMenuItemView) r2
            boolean r2 = r2.hasText()
            if (r2 == 0) goto L_0x00a5
            r2 = 1
            goto L_0x00a6
        L_0x00a5:
            r2 = 0
        L_0x00a6:
            r3.preventEdgeOffset = r2
            boolean r2 = r3.isOverflowButton
            if (r2 == 0) goto L_0x00ae
            r2 = 1
            goto L_0x00af
        L_0x00ae:
            r2 = r12
        L_0x00af:
            int r9 = measureChildForCells(r7, r11, r2, r6, r5)
            int r14 = java.lang.Math.max(r14, r9)
            r27 = r2
            boolean r2 = r3.expandable
            if (r2 == 0) goto L_0x00bf
            int r15 = r15 + 1
        L_0x00bf:
            boolean r2 = r3.isOverflowButton
            if (r2 == 0) goto L_0x00c5
            r17 = 1
        L_0x00c5:
            int r12 = r12 - r9
            int r2 = r7.getMeasuredHeight()
            int r2 = java.lang.Math.max(r13, r2)
            r13 = 1
            if (r9 != r13) goto L_0x00e0
            int r13 = r13 << r4
            r28 = r2
            r22 = r3
            long r2 = (long) r13
            long r2 = r18 | r2
            r18 = r2
            r3 = r25
            r13 = r28
            goto L_0x00e8
        L_0x00e0:
            r28 = r2
            r22 = r3
            r3 = r25
            r13 = r28
        L_0x00e8:
            int r4 = r4 + 1
            r7 = r41
            r8 = r23
            r9 = r24
            r2 = r26
            goto L_0x0058
        L_0x00f4:
            r26 = r2
            r23 = r8
            r24 = r9
            r2 = 2
            if (r17 == 0) goto L_0x0101
            if (r3 != r2) goto L_0x0101
            r4 = 1
            goto L_0x0102
        L_0x0101:
            r4 = 0
        L_0x0102:
            r7 = 0
        L_0x0103:
            r27 = 0
            if (r15 <= 0) goto L_0x01c7
            if (r12 <= 0) goto L_0x01c7
            r25 = 2147483647(0x7fffffff, float:NaN)
            r29 = 0
            r31 = 0
            r32 = 0
            r2 = r25
            r33 = r31
            r8 = r32
        L_0x0118:
            if (r8 >= r10) goto L_0x0159
            android.view.View r9 = r0.getChildAt(r8)
            android.view.ViewGroup$LayoutParams r25 = r9.getLayoutParams()
            r34 = r5
            r5 = r25
            androidx.appcompat.widget.ActionMenuView$LayoutParams r5 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r5
            r25 = r7
            boolean r7 = r5.expandable
            if (r7 != 0) goto L_0x0131
            r7 = r33
            goto L_0x0150
        L_0x0131:
            int r7 = r5.cellsUsed
            if (r7 >= r2) goto L_0x013f
            int r2 = r5.cellsUsed
            r31 = 1
            long r29 = r31 << r8
            r7 = 1
            r33 = r7
            goto L_0x0152
        L_0x013f:
            r31 = 1
            int r7 = r5.cellsUsed
            if (r7 != r2) goto L_0x014e
            long r35 = r31 << r8
            long r29 = r29 | r35
            r7 = r33
            int r33 = r7 + 1
            goto L_0x0152
        L_0x014e:
            r7 = r33
        L_0x0150:
            r33 = r7
        L_0x0152:
            int r8 = r8 + 1
            r7 = r25
            r5 = r34
            goto L_0x0118
        L_0x0159:
            r34 = r5
            r25 = r7
            r7 = r33
            long r18 = r18 | r29
            if (r7 <= r12) goto L_0x0167
            r35 = r6
            goto L_0x01cd
        L_0x0167:
            int r2 = r2 + 1
            r5 = 0
        L_0x016a:
            if (r5 >= r10) goto L_0x01bb
            android.view.View r8 = r0.getChildAt(r5)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r9 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r9
            r33 = r7
            r22 = 1
            int r7 = r22 << r5
            r35 = r6
            long r6 = (long) r7
            long r6 = r29 & r6
            int r31 = (r6 > r27 ? 1 : (r6 == r27 ? 0 : -1))
            if (r31 != 0) goto L_0x0191
            int r6 = r9.cellsUsed
            if (r6 != r2) goto L_0x018e
            int r6 = r22 << r5
            long r6 = (long) r6
            long r18 = r18 | r6
        L_0x018e:
            r31 = r2
            goto L_0x01b2
        L_0x0191:
            if (r4 == 0) goto L_0x01a6
            boolean r6 = r9.preventEdgeOffset
            if (r6 == 0) goto L_0x01a6
            r6 = 1
            if (r12 != r6) goto L_0x01a6
            int r6 = r0.mGeneratedItemPadding
            int r6 = r6 + r11
            int r7 = r0.mGeneratedItemPadding
            r31 = r2
            r2 = 0
            r8.setPadding(r6, r2, r7, r2)
            goto L_0x01a8
        L_0x01a6:
            r31 = r2
        L_0x01a8:
            int r2 = r9.cellsUsed
            r6 = 1
            int r2 = r2 + r6
            r9.cellsUsed = r2
            r9.expanded = r6
            int r12 = r12 + -1
        L_0x01b2:
            int r5 = r5 + 1
            r2 = r31
            r7 = r33
            r6 = r35
            goto L_0x016a
        L_0x01bb:
            r31 = r2
            r35 = r6
            r33 = r7
            r7 = 1
            r5 = r34
            r2 = 2
            goto L_0x0103
        L_0x01c7:
            r34 = r5
            r35 = r6
            r25 = r7
        L_0x01cd:
            if (r17 != 0) goto L_0x01d4
            r2 = 1
            if (r3 != r2) goto L_0x01d4
            r2 = 1
            goto L_0x01d5
        L_0x01d4:
            r2 = 0
        L_0x01d5:
            if (r12 <= 0) goto L_0x02a1
            int r5 = (r18 > r27 ? 1 : (r18 == r27 ? 0 : -1))
            if (r5 == 0) goto L_0x02a1
            int r5 = r3 + -1
            if (r12 < r5) goto L_0x01e9
            if (r2 != 0) goto L_0x01e9
            r5 = 1
            if (r14 <= r5) goto L_0x01e5
            goto L_0x01e9
        L_0x01e5:
            r20 = r2
            goto L_0x02a3
        L_0x01e9:
            int r5 = java.lang.Long.bitCount(r18)
            float r5 = (float) r5
            if (r2 != 0) goto L_0x0229
            r6 = 1
            long r6 = r18 & r6
            r8 = 1056964608(0x3f000000, float:0.5)
            int r9 = (r6 > r27 ? 1 : (r6 == r27 ? 0 : -1))
            if (r9 == 0) goto L_0x020b
            r6 = 0
            android.view.View r7 = r0.getChildAt(r6)
            android.view.ViewGroup$LayoutParams r7 = r7.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r7 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r7
            boolean r9 = r7.preventEdgeOffset
            if (r9 != 0) goto L_0x020c
            float r5 = r5 - r8
            goto L_0x020c
        L_0x020b:
            r6 = 0
        L_0x020c:
            int r7 = r10 + -1
            r9 = 1
            int r7 = r9 << r7
            long r6 = (long) r7
            long r6 = r18 & r6
            int r9 = (r6 > r27 ? 1 : (r6 == r27 ? 0 : -1))
            if (r9 == 0) goto L_0x0229
            int r6 = r10 + -1
            android.view.View r6 = r0.getChildAt(r6)
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r6 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r6
            boolean r7 = r6.preventEdgeOffset
            if (r7 != 0) goto L_0x0229
            float r5 = r5 - r8
        L_0x0229:
            r6 = 0
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x0236
            int r6 = r12 * r11
            float r6 = (float) r6
            float r6 = r6 / r5
            int r6 = (int) r6
            r20 = r6
            goto L_0x0238
        L_0x0236:
            r20 = 0
        L_0x0238:
            r6 = r20
            r7 = 0
        L_0x023b:
            if (r7 >= r10) goto L_0x029b
            r8 = 1
            int r9 = r8 << r7
            long r8 = (long) r9
            long r8 = r18 & r8
            int r20 = (r8 > r27 ? 1 : (r8 == r27 ? 0 : -1))
            if (r20 != 0) goto L_0x024c
            r20 = r2
            r29 = 2
            goto L_0x0296
        L_0x024c:
            android.view.View r8 = r0.getChildAt(r7)
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r9 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r9
            r20 = r2
            boolean r2 = r8 instanceof androidx.appcompat.view.menu.ActionMenuItemView
            if (r2 == 0) goto L_0x0273
            r9.extraPixels = r6
            r2 = 1
            r9.expanded = r2
            if (r7 != 0) goto L_0x026e
            boolean r2 = r9.preventEdgeOffset
            if (r2 != 0) goto L_0x026e
            int r2 = -r6
            r29 = 2
            int r2 = r2 / 2
            r9.leftMargin = r2
        L_0x026e:
            r25 = 1
            r29 = 2
            goto L_0x0296
        L_0x0273:
            boolean r2 = r9.isOverflowButton
            if (r2 == 0) goto L_0x0286
            r9.extraPixels = r6
            r2 = 1
            r9.expanded = r2
            int r2 = -r6
            r29 = 2
            int r2 = r2 / 2
            r9.rightMargin = r2
            r25 = 1
            goto L_0x0296
        L_0x0286:
            r29 = 2
            if (r7 == 0) goto L_0x028e
            int r2 = r6 / 2
            r9.leftMargin = r2
        L_0x028e:
            int r2 = r10 + -1
            if (r7 == r2) goto L_0x0296
            int r2 = r6 / 2
            r9.rightMargin = r2
        L_0x0296:
            int r7 = r7 + 1
            r2 = r20
            goto L_0x023b
        L_0x029b:
            r20 = r2
            r12 = 0
            r7 = r25
            goto L_0x02a5
        L_0x02a1:
            r20 = r2
        L_0x02a3:
            r7 = r25
        L_0x02a5:
            if (r7 == 0) goto L_0x02dd
            r5 = 0
        L_0x02a8:
            if (r5 >= r10) goto L_0x02d8
            android.view.View r6 = r0.getChildAt(r5)
            android.view.ViewGroup$LayoutParams r8 = r6.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r8 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r8
            boolean r9 = r8.expanded
            if (r9 != 0) goto L_0x02bd
            r25 = r3
            r2 = r35
            goto L_0x02d1
        L_0x02bd:
            int r9 = r8.cellsUsed
            int r9 = r9 * r11
            int r2 = r8.extraPixels
            int r9 = r9 + r2
            r25 = r3
            r2 = 1073741824(0x40000000, float:2.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r2)
            r2 = r35
            r6.measure(r3, r2)
        L_0x02d1:
            int r5 = r5 + 1
            r35 = r2
            r3 = r25
            goto L_0x02a8
        L_0x02d8:
            r25 = r3
            r2 = r35
            goto L_0x02e1
        L_0x02dd:
            r25 = r3
            r2 = r35
        L_0x02e1:
            r3 = 1073741824(0x40000000, float:2.0)
            if (r1 == r3) goto L_0x02e7
            r3 = r13
            goto L_0x02e9
        L_0x02e7:
            r3 = r16
        L_0x02e9:
            r5 = r26
            r0.setMeasuredDimension(r5, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActionMenuView.onMeasureExactFormat(int, int):void");
    }

    static int measureChildForCells(View child, int cellSize, int cellsRemaining, int parentHeightMeasureSpec, int parentHeightPadding) {
        View view = child;
        int i = cellsRemaining;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeightSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding, View.MeasureSpec.getMode(parentHeightMeasureSpec));
        ActionMenuItemView itemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean expandable = false;
        boolean hasText = itemView != null && itemView.hasText();
        int cellsUsed = 0;
        if (i > 0 && (!hasText || i >= 2)) {
            child.measure(View.MeasureSpec.makeMeasureSpec(cellSize * i, Integer.MIN_VALUE), childHeightSpec);
            int measuredWidth = child.getMeasuredWidth();
            cellsUsed = measuredWidth / cellSize;
            if (measuredWidth % cellSize != 0) {
                cellsUsed++;
            }
            if (hasText && cellsUsed < 2) {
                cellsUsed = 2;
            }
        }
        if (!lp.isOverflowButton && hasText) {
            expandable = true;
        }
        lp.expandable = expandable;
        lp.cellsUsed = cellsUsed;
        child.measure(View.MeasureSpec.makeMeasureSpec(cellsUsed * cellSize, Declaration.MODULE_REFERENCE), childHeightSpec);
        return cellsUsed;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int i;
        int overflowWidth;
        int dividerWidth;
        boolean isLayoutRtl;
        int midVertical;
        int r;
        int l;
        ActionMenuView actionMenuView = this;
        if (!actionMenuView.mFormatItems) {
            super.onLayout(changed, left, top, right, bottom);
            return;
        }
        int childCount = getChildCount();
        int midVertical2 = (bottom - top) / 2;
        int dividerWidth2 = getDividerWidth();
        int overflowWidth2 = 0;
        int nonOverflowWidth = 0;
        int nonOverflowCount = 0;
        int widthRemaining = ((right - left) - getPaddingRight()) - getPaddingLeft();
        boolean hasOverflow = false;
        boolean isLayoutRtl2 = ViewUtils.isLayoutRtl(this);
        int i2 = 0;
        while (true) {
            i = 8;
            if (i2 >= childCount) {
                break;
            }
            View v = actionMenuView.getChildAt(i2);
            if (v.getVisibility() == 8) {
                midVertical = midVertical2;
                isLayoutRtl = isLayoutRtl2;
            } else {
                LayoutParams p = (LayoutParams) v.getLayoutParams();
                if (p.isOverflowButton) {
                    overflowWidth2 = v.getMeasuredWidth();
                    if (actionMenuView.hasSupportDividerBeforeChildAt(i2)) {
                        overflowWidth2 += dividerWidth2;
                    }
                    int height = v.getMeasuredHeight();
                    if (isLayoutRtl2) {
                        l = getPaddingLeft() + p.leftMargin;
                        r = l + overflowWidth2;
                    } else {
                        r = (getWidth() - getPaddingRight()) - p.rightMargin;
                        l = r - overflowWidth2;
                    }
                    isLayoutRtl = isLayoutRtl2;
                    int t = midVertical2 - (height / 2);
                    midVertical = midVertical2;
                    v.layout(l, t, r, t + height);
                    widthRemaining -= overflowWidth2;
                    hasOverflow = true;
                } else {
                    midVertical = midVertical2;
                    isLayoutRtl = isLayoutRtl2;
                    int size = v.getMeasuredWidth() + p.leftMargin + p.rightMargin;
                    nonOverflowWidth += size;
                    widthRemaining -= size;
                    if (actionMenuView.hasSupportDividerBeforeChildAt(i2)) {
                        nonOverflowWidth += dividerWidth2;
                    }
                    nonOverflowCount++;
                }
            }
            i2++;
            midVertical2 = midVertical;
            isLayoutRtl2 = isLayoutRtl;
        }
        int midVertical3 = midVertical2;
        boolean isLayoutRtl3 = isLayoutRtl2;
        if (childCount != 1 || hasOverflow) {
            int spacerCount = nonOverflowCount - (!hasOverflow);
            int spacerSize = Math.max(0, spacerCount > 0 ? widthRemaining / spacerCount : 0);
            if (isLayoutRtl3) {
                int startRight = getWidth() - getPaddingRight();
                int i3 = 0;
                while (i3 < childCount) {
                    View v2 = actionMenuView.getChildAt(i3);
                    LayoutParams lp = (LayoutParams) v2.getLayoutParams();
                    if (v2.getVisibility() == i) {
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                    } else if (lp.isOverflowButton) {
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                    } else {
                        int startRight2 = startRight - lp.rightMargin;
                        int width = v2.getMeasuredWidth();
                        int height2 = v2.getMeasuredHeight();
                        int t2 = midVertical3 - (height2 / 2);
                        dividerWidth = dividerWidth2;
                        overflowWidth = overflowWidth2;
                        v2.layout(startRight2 - width, t2, startRight2, t2 + height2);
                        startRight = startRight2 - ((lp.leftMargin + width) + spacerSize);
                    }
                    i3++;
                    dividerWidth2 = dividerWidth;
                    overflowWidth2 = overflowWidth;
                    i = 8;
                }
                int i4 = overflowWidth2;
                return;
            }
            int i5 = overflowWidth2;
            int startLeft = getPaddingLeft();
            int i6 = 0;
            while (i6 < childCount) {
                View v3 = actionMenuView.getChildAt(i6);
                LayoutParams lp2 = (LayoutParams) v3.getLayoutParams();
                if (v3.getVisibility() != 8 && !lp2.isOverflowButton) {
                    int startLeft2 = startLeft + lp2.leftMargin;
                    int width2 = v3.getMeasuredWidth();
                    int height3 = v3.getMeasuredHeight();
                    int t3 = midVertical3 - (height3 / 2);
                    v3.layout(startLeft2, t3, startLeft2 + width2, t3 + height3);
                    startLeft = startLeft2 + lp2.rightMargin + width2 + spacerSize;
                }
                i6++;
                actionMenuView = this;
            }
            return;
        }
        View v4 = actionMenuView.getChildAt(0);
        int width3 = v4.getMeasuredWidth();
        int height4 = v4.getMeasuredHeight();
        int l2 = ((right - left) / 2) - (width3 / 2);
        int t4 = midVertical3 - (height4 / 2);
        v4.layout(l2, t4, l2 + width3, t4 + height4);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(Drawable icon) {
        getMenu();
        this.mPresenter.setOverflowIcon(icon);
    }

    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void setOverflowReserved(boolean reserveOverflow) {
        this.mReserveOverflow = reserveOverflow;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 16;
        return params;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        if (p == null) {
            return generateDefaultLayoutParams();
        }
        LayoutParams result = p instanceof LayoutParams ? new LayoutParams((LayoutParams) p) : new LayoutParams(p);
        if (result.gravity <= 0) {
            result.gravity = 16;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p != null && (p instanceof LayoutParams);
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams result = generateDefaultLayoutParams();
        result.isOverflowButton = true;
        return result;
    }

    public boolean invokeItem(MenuItemImpl item) {
        return this.mMenu.performItemAction(item, 0);
    }

    public int getWindowAnimations() {
        return 0;
    }

    public void initialize(MenuBuilder menu) {
        this.mMenu = menu;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            this.mMenu = new MenuBuilder(context);
            this.mMenu.setCallback(new MenuBuilderCallback());
            this.mPresenter = new ActionMenuPresenter(context);
            this.mPresenter.setReserveOverflow(true);
            this.mPresenter.setCallback(this.mActionMenuPresenterCallback != null ? this.mActionMenuPresenterCallback : new ActionMenuPresenterCallback());
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    public void setMenuCallbacks(MenuPresenter.Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
    }

    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        return this.mPresenter != null && this.mPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        return this.mPresenter != null && this.mPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        if (this.mPresenter != null) {
            this.mPresenter.dismissPopupMenus();
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasSupportDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return false;
        }
        View childBefore = getChildAt(childIndex - 1);
        View child = getChildAt(childIndex);
        boolean result = false;
        if (childIndex < getChildCount() && (childBefore instanceof ActionMenuChildView)) {
            result = false | ((ActionMenuChildView) childBefore).needsDividerAfter();
        }
        if (childIndex <= 0 || !(child instanceof ActionMenuChildView)) {
            return result;
        }
        return result | ((ActionMenuChildView) child).needsDividerBefore();
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    public void setExpandedActionViewsExclusive(boolean exclusive) {
        this.mPresenter.setExpandedActionViewsExclusive(exclusive);
    }

    private class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menu);
            }
        }
    }

    private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            return false;
        }
    }

    public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams other) {
            super(other);
        }

        public LayoutParams(LayoutParams other) {
            super((ViewGroup.LayoutParams) other);
            this.isOverflowButton = other.isOverflowButton;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.isOverflowButton = false;
        }

        LayoutParams(int width, int height, boolean isOverflowButton2) {
            super(width, height);
            this.isOverflowButton = isOverflowButton2;
        }
    }
}
