package androidx.constraintlayout.solver.widgets;

import androidx.appcompat.widget.ActivityChooserView;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.SolverVariable;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import java.util.ArrayList;

public class ConstraintWidget {
    protected static final int ANCHOR_BASELINE = 4;
    protected static final int ANCHOR_BOTTOM = 3;
    protected static final int ANCHOR_LEFT = 0;
    protected static final int ANCHOR_RIGHT = 1;
    protected static final int ANCHOR_TOP = 2;
    private static final boolean AUTOTAG_CENTER = false;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.5f;
    static final int DIMENSION_HORIZONTAL = 0;
    static final int DIMENSION_VERTICAL = 1;
    protected static final int DIRECT = 2;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_RATIO = 3;
    public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    protected static final int SOLVER = 1;
    public static final int UNKNOWN = -1;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    private static final int WRAP = -2;
    protected ArrayList<ConstraintAnchor> mAnchors;
    ConstraintAnchor mBaseline;
    int mBaselineDistance;
    ConstraintWidgetGroup mBelongingGroup;
    ConstraintAnchor mBottom;
    boolean mBottomHasCentered;
    ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    private float mCircleConstraintAngle;
    private Object mCompanionWidget;
    private int mContainerItemSkip;
    private String mDebugName;
    protected float mDimensionRatio;
    protected int mDimensionRatioSide;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    private int mDrawHeight;
    private int mDrawWidth;
    private int mDrawX;
    private int mDrawY;
    boolean mGroupsToSolver;
    int mHeight;
    float mHorizontalBiasPercent;
    boolean mHorizontalChainFixedPosition;
    int mHorizontalChainStyle;
    ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    boolean mHorizontalWrapVisited;
    boolean mIsHeightWrapContent;
    boolean mIsWidthWrapContent;
    ConstraintAnchor mLeft;
    boolean mLeftHasCentered;
    protected ConstraintAnchor[] mListAnchors;
    protected DimensionBehaviour[] mListDimensionBehaviors;
    protected ConstraintWidget[] mListNextMatchConstraintsWidget;
    int mMatchConstraintDefaultHeight;
    int mMatchConstraintDefaultWidth;
    int mMatchConstraintMaxHeight;
    int mMatchConstraintMaxWidth;
    int mMatchConstraintMinHeight;
    int mMatchConstraintMinWidth;
    float mMatchConstraintPercentHeight;
    float mMatchConstraintPercentWidth;
    private int[] mMaxDimension;
    protected int mMinHeight;
    protected int mMinWidth;
    protected ConstraintWidget[] mNextChainWidget;
    protected int mOffsetX;
    protected int mOffsetY;
    boolean mOptimizerMeasurable;
    boolean mOptimizerMeasured;
    ConstraintWidget mParent;
    int mRelX;
    int mRelY;
    ResolutionDimension mResolutionHeight;
    ResolutionDimension mResolutionWidth;
    float mResolvedDimensionRatio;
    int mResolvedDimensionRatioSide;
    int[] mResolvedMatchConstraintDefault;
    ConstraintAnchor mRight;
    boolean mRightHasCentered;
    ConstraintAnchor mTop;
    boolean mTopHasCentered;
    private String mType;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    boolean mVerticalWrapVisited;
    private int mVisibility;
    float[] mWeight;
    int mWidth;
    private int mWrapHeight;
    private int mWrapWidth;
    protected int mX;
    protected int mY;

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public int getMaxHeight() {
        return this.mMaxDimension[1];
    }

    public int getMaxWidth() {
        return this.mMaxDimension[0];
    }

    public void setMaxWidth(int maxWidth) {
        this.mMaxDimension[0] = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.mMaxDimension[1] = maxHeight;
    }

    public boolean isSpreadWidth() {
        return this.mMatchConstraintDefaultWidth == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMaxWidth == 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isSpreadHeight() {
        return this.mMatchConstraintDefaultHeight == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinHeight == 0 && this.mMatchConstraintMaxHeight == 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mWrapWidth = 0;
        this.mWrapHeight = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mListDimensionBehaviors[0] = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors[1] = DimensionBehaviour.FIXED;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        this.mWeight[0] = -1.0f;
        this.mWeight[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mMaxDimension[0] = Integer.MAX_VALUE;
        this.mMaxDimension[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mMatchConstraintMaxHeight = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        if (this.mResolutionWidth != null) {
            this.mResolutionWidth.reset();
        }
        if (this.mResolutionHeight != null) {
            this.mResolutionHeight.reset();
        }
        this.mBelongingGroup = null;
        this.mOptimizerMeasurable = false;
        this.mOptimizerMeasured = false;
        this.mGroupsToSolver = false;
    }

    public void resetResolutionNodes() {
        for (int i = 0; i < 6; i++) {
            this.mListAnchors[i].getResolutionNode().reset();
        }
    }

    public void updateResolutionNodes() {
        for (int i = 0; i < 6; i++) {
            this.mListAnchors[i].getResolutionNode().update();
        }
    }

    public void analyze(int optimizationLevel) {
        Optimizer.analyze(optimizationLevel, this);
    }

    public void resolve() {
    }

    public boolean isFullyResolved() {
        if (this.mLeft.getResolutionNode().state == 1 && this.mRight.getResolutionNode().state == 1 && this.mTop.getResolutionNode().state == 1 && this.mBottom.getResolutionNode().state == 1) {
            return true;
        }
        return false;
    }

    public ResolutionDimension getResolutionWidth() {
        if (this.mResolutionWidth == null) {
            this.mResolutionWidth = new ResolutionDimension();
        }
        return this.mResolutionWidth;
    }

    public ResolutionDimension getResolutionHeight() {
        if (this.mResolutionHeight == null) {
            this.mResolutionHeight = new ResolutionDimension();
        }
        return this.mResolutionHeight;
    }

    public ConstraintWidget() {
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mBelongingGroup = null;
        this.mMaxDimension = new int[]{ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED};
        this.mCircleConstraintAngle = 0.0f;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, this.mCenter};
        this.mAnchors = new ArrayList<>();
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mOptimizerMeasurable = false;
        this.mOptimizerMeasured = false;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        addAnchors();
    }

    public ConstraintWidget(int x, int y, int width, int height) {
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mBelongingGroup = null;
        this.mMaxDimension = new int[]{ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED};
        this.mCircleConstraintAngle = 0.0f;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, this.mCenter};
        this.mAnchors = new ArrayList<>();
        this.mListDimensionBehaviors = new DimensionBehaviour[]{DimensionBehaviour.FIXED, DimensionBehaviour.FIXED};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mDrawX = 0;
        this.mDrawY = 0;
        this.mDrawWidth = 0;
        this.mDrawHeight = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mOptimizerMeasurable = false;
        this.mOptimizerMeasured = false;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.mX = x;
        this.mY = y;
        this.mWidth = width;
        this.mHeight = height;
        addAnchors();
        forceUpdateDrawPosition();
    }

    public ConstraintWidget(int width, int height) {
        this(0, 0, width, height);
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public boolean isRootContainer() {
        return (this instanceof ConstraintWidgetContainer) && (this.mParent == null || !(this.mParent instanceof ConstraintWidgetContainer));
    }

    public boolean isInsideConstraintLayout() {
        ConstraintWidget widget = getParent();
        if (widget == null) {
            return false;
        }
        while (widget != null) {
            if (widget instanceof ConstraintWidgetContainer) {
                return true;
            }
            widget = widget.getParent();
        }
        return false;
    }

    public boolean hasAncestor(ConstraintWidget widget) {
        ConstraintWidget parent = getParent();
        if (parent == widget) {
            return true;
        }
        if (parent == widget.getParent()) {
            return false;
        }
        while (parent != null) {
            if (parent == widget || parent == widget.getParent()) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    public WidgetContainer getRootWidgetContainer() {
        ConstraintWidget root = this;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        if (root instanceof WidgetContainer) {
            return (WidgetContainer) root;
        }
        return null;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public void setParent(ConstraintWidget widget) {
        this.mParent = widget;
    }

    public void setWidthWrapContent(boolean widthWrapContent) {
        this.mIsWidthWrapContent = widthWrapContent;
    }

    public boolean isWidthWrapContent() {
        return this.mIsWidthWrapContent;
    }

    public void setHeightWrapContent(boolean heightWrapContent) {
        this.mIsHeightWrapContent = heightWrapContent;
    }

    public boolean isHeightWrapContent() {
        return this.mIsHeightWrapContent;
    }

    public void connectCircularConstraint(ConstraintWidget target, float angle, int radius) {
        immediateConnect(ConstraintAnchor.Type.CENTER, target, ConstraintAnchor.Type.CENTER, radius, 0);
        this.mCircleConstraintAngle = angle;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public void setDebugName(String name) {
        this.mDebugName = name;
    }

    public void setDebugSolverName(LinearSystem system, String name) {
        this.mDebugName = name;
        SolverVariable left = system.createObjectVariable(this.mLeft);
        SolverVariable top = system.createObjectVariable(this.mTop);
        SolverVariable right = system.createObjectVariable(this.mRight);
        SolverVariable bottom = system.createObjectVariable(this.mBottom);
        left.setName(name + ".left");
        top.setName(name + ".top");
        right.setName(name + ".right");
        bottom.setName(name + ".bottom");
        if (this.mBaselineDistance > 0) {
            system.createObjectVariable(this.mBaseline).setName(name + ".baseline");
        }
    }

    public void createObjectVariables(LinearSystem system) {
        SolverVariable createObjectVariable = system.createObjectVariable(this.mLeft);
        SolverVariable createObjectVariable2 = system.createObjectVariable(this.mTop);
        SolverVariable createObjectVariable3 = system.createObjectVariable(this.mRight);
        SolverVariable createObjectVariable4 = system.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            system.createObjectVariable(this.mBaseline);
        }
    }

    public String toString() {
        String str = "";
        StringBuilder append = new StringBuilder().append(this.mType != null ? "type: " + this.mType + " " : str);
        if (this.mDebugName != null) {
            str = "id: " + this.mDebugName + " ";
        }
        return append.append(str).append("(").append(this.mX).append(", ").append(this.mY).append(") - (").append(this.mWidth).append(" x ").append(this.mHeight).append(") wrap: (").append(this.mWrapWidth).append(" x ").append(this.mWrapHeight).append(")").toString();
    }

    /* access modifiers changed from: package-private */
    public int getInternalDrawX() {
        return this.mDrawX;
    }

    /* access modifiers changed from: package-private */
    public int getInternalDrawY() {
        return this.mDrawY;
    }

    public int getInternalDrawRight() {
        return this.mDrawX + this.mDrawWidth;
    }

    public int getInternalDrawBottom() {
        return this.mDrawY + this.mDrawHeight;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getOptimizerWrapWidth() {
        int w;
        int w2 = this.mWidth;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return w2;
        }
        if (this.mMatchConstraintDefaultWidth == 1) {
            w = Math.max(this.mMatchConstraintMinWidth, w2);
        } else if (this.mMatchConstraintMinWidth > 0) {
            w = this.mMatchConstraintMinWidth;
            this.mWidth = w;
        } else {
            w = 0;
        }
        if (this.mMatchConstraintMaxWidth <= 0 || this.mMatchConstraintMaxWidth >= w) {
            return w;
        }
        return this.mMatchConstraintMaxWidth;
    }

    public int getOptimizerWrapHeight() {
        int h;
        int h2 = this.mHeight;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return h2;
        }
        if (this.mMatchConstraintDefaultHeight == 1) {
            h = Math.max(this.mMatchConstraintMinHeight, h2);
        } else if (this.mMatchConstraintMinHeight > 0) {
            h = this.mMatchConstraintMinHeight;
            this.mHeight = h;
        } else {
            h = 0;
        }
        if (this.mMatchConstraintMaxHeight <= 0 || this.mMatchConstraintMaxHeight >= h) {
            return h;
        }
        return this.mMatchConstraintMaxHeight;
    }

    public int getWrapWidth() {
        return this.mWrapWidth;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public int getWrapHeight() {
        return this.mWrapHeight;
    }

    public int getLength(int orientation) {
        if (orientation == 0) {
            return getWidth();
        }
        if (orientation == 1) {
            return getHeight();
        }
        return 0;
    }

    public int getDrawX() {
        return this.mDrawX + this.mOffsetX;
    }

    public int getDrawY() {
        return this.mDrawY + this.mOffsetY;
    }

    public int getDrawWidth() {
        return this.mDrawWidth;
    }

    public int getDrawHeight() {
        return this.mDrawHeight;
    }

    public int getDrawBottom() {
        return getDrawY() + this.mDrawHeight;
    }

    public int getDrawRight() {
        return getDrawX() + this.mDrawWidth;
    }

    /* access modifiers changed from: protected */
    public int getRootX() {
        return this.mX + this.mOffsetX;
    }

    /* access modifiers changed from: protected */
    public int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getLeft() {
        return getX();
    }

    public int getTop() {
        return getY();
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public float getBiasPercent(int orientation) {
        if (orientation == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (orientation == 1) {
            return this.mVerticalBiasPercent;
        }
        return -1.0f;
    }

    public boolean hasBaseline() {
        return this.mBaselineDistance > 0;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int x) {
        this.mX = x;
    }

    public void setY(int y) {
        this.mY = y;
    }

    public void setOrigin(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public void setOffset(int x, int y) {
        this.mOffsetX = x;
        this.mOffsetY = y;
    }

    public void setGoneMargin(ConstraintAnchor.Type type, int goneMargin) {
        switch (type) {
            case LEFT:
                this.mLeft.mGoneMargin = goneMargin;
                return;
            case TOP:
                this.mTop.mGoneMargin = goneMargin;
                return;
            case RIGHT:
                this.mRight.mGoneMargin = goneMargin;
                return;
            case BOTTOM:
                this.mBottom.mGoneMargin = goneMargin;
                return;
            default:
                return;
        }
    }

    public void updateDrawPosition() {
        int left = this.mX;
        int top = this.mY;
        int right = this.mX + this.mWidth;
        int bottom = this.mY + this.mHeight;
        this.mDrawX = left;
        this.mDrawY = top;
        this.mDrawWidth = right - left;
        this.mDrawHeight = bottom - top;
    }

    public void forceUpdateDrawPosition() {
        int left = this.mX;
        int top = this.mY;
        int right = this.mX + this.mWidth;
        int bottom = this.mY + this.mHeight;
        this.mDrawX = left;
        this.mDrawY = top;
        this.mDrawWidth = right - left;
        this.mDrawHeight = bottom - top;
    }

    public void setDrawOrigin(int x, int y) {
        this.mDrawX = x - this.mOffsetX;
        this.mDrawY = y - this.mOffsetY;
        this.mX = this.mDrawX;
        this.mY = this.mDrawY;
    }

    public void setDrawX(int x) {
        this.mDrawX = x - this.mOffsetX;
        this.mX = this.mDrawX;
    }

    public void setDrawY(int y) {
        this.mDrawY = y - this.mOffsetY;
        this.mY = this.mDrawY;
    }

    public void setDrawWidth(int drawWidth) {
        this.mDrawWidth = drawWidth;
    }

    public void setDrawHeight(int drawHeight) {
        this.mDrawHeight = drawHeight;
    }

    public void setWidth(int w) {
        this.mWidth = w;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHeight(int h) {
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setLength(int length, int orientation) {
        if (orientation == 0) {
            setWidth(length);
        } else if (orientation == 1) {
            setHeight(length);
        }
    }

    public void setHorizontalMatchStyle(int horizontalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultWidth = horizontalMatchStyle;
        this.mMatchConstraintMinWidth = min;
        this.mMatchConstraintMaxWidth = max;
        this.mMatchConstraintPercentWidth = percent;
        if (percent < 1.0f && this.mMatchConstraintDefaultWidth == 0) {
            this.mMatchConstraintDefaultWidth = 2;
        }
    }

    public void setVerticalMatchStyle(int verticalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultHeight = verticalMatchStyle;
        this.mMatchConstraintMinHeight = min;
        this.mMatchConstraintMaxHeight = max;
        this.mMatchConstraintPercentHeight = percent;
        if (percent < 1.0f && this.mMatchConstraintDefaultHeight == 0) {
            this.mMatchConstraintDefaultHeight = 2;
        }
    }

    public void setDimensionRatio(String ratio) {
        int commaIndex;
        if (ratio == null || ratio.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int dimensionRatioSide = -1;
        float dimensionRatio = 0.0f;
        int len = ratio.length();
        int commaIndex2 = ratio.indexOf(44);
        if (commaIndex2 <= 0 || commaIndex2 >= len - 1) {
            commaIndex = 0;
        } else {
            String dimension = ratio.substring(0, commaIndex2);
            if (dimension.equalsIgnoreCase("W")) {
                dimensionRatioSide = 0;
            } else if (dimension.equalsIgnoreCase("H")) {
                dimensionRatioSide = 1;
            }
            commaIndex = commaIndex2 + 1;
        }
        int colonIndex = ratio.indexOf(58);
        if (colonIndex < 0 || colonIndex >= len - 1) {
            String r = ratio.substring(commaIndex);
            if (r.length() > 0) {
                try {
                    dimensionRatio = Float.parseFloat(r);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            String nominator = ratio.substring(commaIndex, colonIndex);
            String denominator = ratio.substring(colonIndex + 1);
            if (nominator.length() > 0 && denominator.length() > 0) {
                try {
                    float nominatorValue = Float.parseFloat(nominator);
                    float denominatorValue = Float.parseFloat(denominator);
                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                        dimensionRatio = dimensionRatioSide == 1 ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                    }
                } catch (NumberFormatException e2) {
                }
            }
        }
        if (dimensionRatio > 0.0f) {
            this.mDimensionRatio = dimensionRatio;
            this.mDimensionRatioSide = dimensionRatioSide;
        }
    }

    public void setDimensionRatio(float ratio, int dimensionRatioSide) {
        this.mDimensionRatio = ratio;
        this.mDimensionRatioSide = dimensionRatioSide;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public void setHorizontalBiasPercent(float horizontalBiasPercent) {
        this.mHorizontalBiasPercent = horizontalBiasPercent;
    }

    public void setVerticalBiasPercent(float verticalBiasPercent) {
        this.mVerticalBiasPercent = verticalBiasPercent;
    }

    public void setMinWidth(int w) {
        if (w < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = w;
        }
    }

    public void setMinHeight(int h) {
        if (h < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = h;
        }
    }

    public void setWrapWidth(int w) {
        this.mWrapWidth = w;
    }

    public void setWrapHeight(int h) {
        this.mWrapHeight = h;
    }

    public void setDimension(int w, int h) {
        this.mWidth = w;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setFrame(int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        this.mX = left;
        this.mY = top;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && w < this.mWidth) {
            w = this.mWidth;
        }
        if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && h < this.mHeight) {
            h = this.mHeight;
        }
        this.mWidth = w;
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
        this.mOptimizerMeasured = true;
    }

    public void setFrame(int start, int end, int orientation) {
        if (orientation == 0) {
            setHorizontalDimension(start, end);
        } else if (orientation == 1) {
            setVerticalDimension(start, end);
        }
        this.mOptimizerMeasured = true;
    }

    public void setHorizontalDimension(int left, int right) {
        this.mX = left;
        this.mWidth = right - left;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setVerticalDimension(int top, int bottom) {
        this.mY = top;
        this.mHeight = bottom - top;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    /* access modifiers changed from: package-private */
    public int getRelativePositioning(int orientation) {
        if (orientation == 0) {
            return this.mRelX;
        }
        if (orientation == 1) {
            return this.mRelY;
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void setRelativePositioning(int offset, int orientation) {
        if (orientation == 0) {
            this.mRelX = offset;
        } else if (orientation == 1) {
            this.mRelY = offset;
        }
    }

    public void setBaselineDistance(int baseline) {
        this.mBaselineDistance = baseline;
    }

    public void setCompanionWidget(Object companion) {
        this.mCompanionWidget = companion;
    }

    public void setContainerItemSkip(int skip) {
        if (skip >= 0) {
            this.mContainerItemSkip = skip;
        } else {
            this.mContainerItemSkip = 0;
        }
    }

    public int getContainerItemSkip() {
        return this.mContainerItemSkip;
    }

    public void setHorizontalWeight(float horizontalWeight) {
        this.mWeight[0] = horizontalWeight;
    }

    public void setVerticalWeight(float verticalWeight) {
        this.mWeight[1] = verticalWeight;
    }

    public void setHorizontalChainStyle(int horizontalChainStyle) {
        this.mHorizontalChainStyle = horizontalChainStyle;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public void setVerticalChainStyle(int verticalChainStyle) {
        this.mVerticalChainStyle = verticalChainStyle;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    public void connectedTo(ConstraintWidget source) {
    }

    public void immediateConnect(ConstraintAnchor.Type startType, ConstraintWidget target, ConstraintAnchor.Type endType, int margin, int goneMargin) {
        ConstraintAnchor startAnchor = getAnchor(startType);
        startAnchor.connect(target.getAnchor(endType), margin, goneMargin, ConstraintAnchor.Strength.STRONG, 0, true);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin, int creator) {
        connect(from, to, margin, ConstraintAnchor.Strength.STRONG, creator);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin) {
        connect(from, to, margin, ConstraintAnchor.Strength.STRONG, 0);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin, ConstraintAnchor.Strength strength, int creator) {
        if (from.getOwner() == this) {
            connect(from.getType(), to.getOwner(), to.getType(), margin, strength, creator);
        }
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin) {
        connect(constraintFrom, target, constraintTo, margin, ConstraintAnchor.Strength.STRONG);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo) {
        connect(constraintFrom, target, constraintTo, 0, ConstraintAnchor.Strength.STRONG);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin, ConstraintAnchor.Strength strength) {
        connect(constraintFrom, target, constraintTo, margin, strength, 0);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin, ConstraintAnchor.Strength strength, int creator) {
        int margin2;
        ConstraintAnchor.Type type = constraintFrom;
        ConstraintWidget constraintWidget = target;
        ConstraintAnchor.Type type2 = constraintTo;
        int i = creator;
        if (type == ConstraintAnchor.Type.CENTER) {
            if (type2 == ConstraintAnchor.Type.CENTER) {
                ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
                ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
                ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
                ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
                boolean centerX = false;
                boolean centerY = false;
                if ((left == null || !left.isConnected()) && (right == null || !right.isConnected())) {
                    ConstraintWidget constraintWidget2 = target;
                    ConstraintAnchor.Strength strength2 = strength;
                    int i2 = creator;
                    connect(ConstraintAnchor.Type.LEFT, constraintWidget2, ConstraintAnchor.Type.LEFT, 0, strength2, i2);
                    connect(ConstraintAnchor.Type.RIGHT, constraintWidget2, ConstraintAnchor.Type.RIGHT, 0, strength2, i2);
                    centerX = true;
                }
                if ((top == null || !top.isConnected()) && (bottom == null || !bottom.isConnected())) {
                    connect(ConstraintAnchor.Type.TOP, target, ConstraintAnchor.Type.TOP, 0, strength, creator);
                    try {
                        connect(ConstraintAnchor.Type.BOTTOM, target, ConstraintAnchor.Type.BOTTOM, 0, strength, creator);
                        centerY = true;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (centerX && centerY) {
                    getAnchor(ConstraintAnchor.Type.CENTER).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.CENTER), 0, i);
                } else if (centerX) {
                    getAnchor(ConstraintAnchor.Type.CENTER_X).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.CENTER_X), 0, i);
                } else if (centerY) {
                    getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.CENTER_Y), 0, i);
                }
                ConstraintAnchor.Strength strength3 = strength;
            } else {
                if (type2 == ConstraintAnchor.Type.LEFT || type2 == ConstraintAnchor.Type.RIGHT) {
                    ConstraintWidget constraintWidget3 = target;
                    ConstraintAnchor.Type type3 = constraintTo;
                    ConstraintAnchor.Strength strength4 = strength;
                    int i3 = creator;
                    connect(ConstraintAnchor.Type.LEFT, constraintWidget3, type3, 0, strength4, i3);
                    connect(ConstraintAnchor.Type.RIGHT, constraintWidget3, type3, 0, strength4, i3);
                    getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(constraintTo), 0, i);
                } else if (type2 == ConstraintAnchor.Type.TOP || type2 == ConstraintAnchor.Type.BOTTOM) {
                    ConstraintWidget constraintWidget4 = target;
                    ConstraintAnchor.Type type4 = constraintTo;
                    ConstraintAnchor.Strength strength5 = strength;
                    int i4 = creator;
                    connect(ConstraintAnchor.Type.TOP, constraintWidget4, type4, 0, strength5, i4);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget4, type4, 0, strength5, i4);
                    getAnchor(ConstraintAnchor.Type.CENTER).connect(target.getAnchor(constraintTo), 0, i);
                    ConstraintAnchor.Strength strength6 = strength;
                }
                ConstraintAnchor.Strength strength7 = strength;
            }
        } else if (type == ConstraintAnchor.Type.CENTER_X && (type2 == ConstraintAnchor.Type.LEFT || type2 == ConstraintAnchor.Type.RIGHT)) {
            ConstraintAnchor left2 = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = target.getAnchor(constraintTo);
            ConstraintAnchor right2 = getAnchor(ConstraintAnchor.Type.RIGHT);
            left2.connect(targetAnchor, 0, i);
            right2.connect(targetAnchor, 0, i);
            getAnchor(ConstraintAnchor.Type.CENTER_X).connect(targetAnchor, 0, i);
            ConstraintAnchor.Strength strength8 = strength;
        } else if (type == ConstraintAnchor.Type.CENTER_Y && (type2 == ConstraintAnchor.Type.TOP || type2 == ConstraintAnchor.Type.BOTTOM)) {
            ConstraintAnchor targetAnchor2 = target.getAnchor(constraintTo);
            getAnchor(ConstraintAnchor.Type.TOP).connect(targetAnchor2, 0, i);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(targetAnchor2, 0, i);
            getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(targetAnchor2, 0, i);
            ConstraintAnchor.Strength strength9 = strength;
        } else if (type == ConstraintAnchor.Type.CENTER_X && type2 == ConstraintAnchor.Type.CENTER_X) {
            getAnchor(ConstraintAnchor.Type.LEFT).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT), 0, i);
            getAnchor(ConstraintAnchor.Type.RIGHT).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT), 0, i);
            getAnchor(ConstraintAnchor.Type.CENTER_X).connect(target.getAnchor(constraintTo), 0, i);
            ConstraintAnchor.Strength strength10 = strength;
        } else if (type == ConstraintAnchor.Type.CENTER_Y && type2 == ConstraintAnchor.Type.CENTER_Y) {
            getAnchor(ConstraintAnchor.Type.TOP).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.TOP), 0, i);
            getAnchor(ConstraintAnchor.Type.BOTTOM).connect(constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM), 0, i);
            getAnchor(ConstraintAnchor.Type.CENTER_Y).connect(target.getAnchor(constraintTo), 0, i);
            ConstraintAnchor.Strength strength11 = strength;
        } else {
            ConstraintAnchor fromAnchor = getAnchor(constraintFrom);
            ConstraintAnchor toAnchor = target.getAnchor(constraintTo);
            if (fromAnchor.isValidConnection(toAnchor)) {
                if (type == ConstraintAnchor.Type.BASELINE) {
                    ConstraintAnchor top2 = getAnchor(ConstraintAnchor.Type.TOP);
                    ConstraintAnchor bottom2 = getAnchor(ConstraintAnchor.Type.BOTTOM);
                    if (top2 != null) {
                        top2.reset();
                    }
                    if (bottom2 != null) {
                        bottom2.reset();
                    }
                    margin2 = 0;
                } else {
                    if (type == ConstraintAnchor.Type.TOP || type == ConstraintAnchor.Type.BOTTOM) {
                        ConstraintAnchor baseline = getAnchor(ConstraintAnchor.Type.BASELINE);
                        if (baseline != null) {
                            baseline.reset();
                        }
                        ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
                        if (center.getTarget() != toAnchor) {
                            center.reset();
                        }
                        ConstraintAnchor opposite = getAnchor(constraintFrom).getOpposite();
                        ConstraintAnchor centerY2 = getAnchor(ConstraintAnchor.Type.CENTER_Y);
                        if (centerY2.isConnected()) {
                            opposite.reset();
                            centerY2.reset();
                        }
                    } else if (type == ConstraintAnchor.Type.LEFT || type == ConstraintAnchor.Type.RIGHT) {
                        ConstraintAnchor center2 = getAnchor(ConstraintAnchor.Type.CENTER);
                        if (center2.getTarget() != toAnchor) {
                            center2.reset();
                        }
                        ConstraintAnchor opposite2 = getAnchor(constraintFrom).getOpposite();
                        ConstraintAnchor centerX2 = getAnchor(ConstraintAnchor.Type.CENTER_X);
                        if (centerX2.isConnected()) {
                            opposite2.reset();
                            centerX2.reset();
                        }
                    }
                    margin2 = margin;
                }
                fromAnchor.connect(toAnchor, margin2, strength, i);
                toAnchor.getOwner().connectedTo(fromAnchor.getOwner());
                return;
            }
            ConstraintAnchor.Strength strength12 = strength;
        }
        int i5 = margin;
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
        if (!(this instanceof ConstraintWidgetContainer)) {
            if (getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) {
                if (getWidth() == getWrapWidth()) {
                    setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
                } else if (getWidth() > getMinWidth()) {
                    setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
            }
            if (getVerticalDimensionBehaviour() != DimensionBehaviour.MATCH_CONSTRAINT) {
                return;
            }
            if (getHeight() == getWrapHeight()) {
                setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
            } else if (getHeight() > getMinHeight()) {
                setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
            }
        }
    }

    public void resetAnchor(ConstraintAnchor anchor) {
        if (getParent() == null || !(getParent() instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
            ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
            ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
            ConstraintAnchor centerX = getAnchor(ConstraintAnchor.Type.CENTER_X);
            ConstraintAnchor centerY = getAnchor(ConstraintAnchor.Type.CENTER_Y);
            if (anchor == center) {
                if (left.isConnected() && right.isConnected() && left.getTarget() == right.getTarget()) {
                    left.reset();
                    right.reset();
                }
                if (top.isConnected() && bottom.isConnected() && top.getTarget() == bottom.getTarget()) {
                    top.reset();
                    bottom.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == centerX) {
                if (left.isConnected() && right.isConnected() && left.getTarget().getOwner() == right.getTarget().getOwner()) {
                    left.reset();
                    right.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
            } else if (anchor == centerY) {
                if (top.isConnected() && bottom.isConnected() && top.getTarget().getOwner() == bottom.getTarget().getOwner()) {
                    top.reset();
                    bottom.reset();
                }
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == left || anchor == right) {
                if (left.isConnected() && left.getTarget() == right.getTarget()) {
                    center.reset();
                }
            } else if ((anchor == top || anchor == bottom) && top.isConnected() && top.getTarget() == bottom.getTarget()) {
                center.reset();
            }
            anchor.reset();
        }
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = 0; i < mAnchorsSize; i++) {
                this.mAnchors.get(i).reset();
            }
        }
    }

    public void resetAnchors(int connectionCreator) {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = 0; i < mAnchorsSize; i++) {
                ConstraintAnchor anchor = this.mAnchors.get(i);
                if (connectionCreator == anchor.getConnectionCreator()) {
                    if (anchor.isVerticalAnchor()) {
                        setVerticalBiasPercent(DEFAULT_BIAS);
                    } else {
                        setHorizontalBiasPercent(DEFAULT_BIAS);
                    }
                    anchor.reset();
                }
            }
        }
    }

    public void disconnectWidget(ConstraintWidget widget) {
        ArrayList<ConstraintAnchor> anchors = getAnchors();
        int anchorsSize = anchors.size();
        for (int i = 0; i < anchorsSize; i++) {
            ConstraintAnchor anchor = anchors.get(i);
            if (anchor.isConnected() && anchor.getTarget().getOwner() == widget) {
                anchor.reset();
            }
        }
    }

    public void disconnectUnlockedWidget(ConstraintWidget widget) {
        ArrayList<ConstraintAnchor> anchors = getAnchors();
        int anchorsSize = anchors.size();
        for (int i = 0; i < anchorsSize; i++) {
            ConstraintAnchor anchor = anchors.get(i);
            if (anchor.isConnected() && anchor.getTarget().getOwner() == widget && anchor.getConnectionCreator() == 2) {
                anchor.reset();
            }
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type anchorType) {
        switch (anchorType) {
            case LEFT:
                return this.mLeft;
            case TOP:
                return this.mTop;
            case RIGHT:
                return this.mRight;
            case BOTTOM:
                return this.mBottom;
            case BASELINE:
                return this.mBaseline;
            case CENTER:
                return this.mCenter;
            case CENTER_X:
                return this.mCenterX;
            case CENTER_Y:
                return this.mCenterY;
            case NONE:
                return null;
            default:
                throw new AssertionError(anchorType.name());
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public DimensionBehaviour getDimensionBehaviour(int orientation) {
        if (orientation == 0) {
            return getHorizontalDimensionBehaviour();
        }
        if (orientation == 1) {
            return getVerticalDimensionBehaviour();
        }
        return null;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mListDimensionBehaviors[0] = behaviour;
        if (behaviour == DimensionBehaviour.WRAP_CONTENT) {
            setWidth(this.mWrapWidth);
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mListDimensionBehaviors[1] = behaviour;
        if (behaviour == DimensionBehaviour.WRAP_CONTENT) {
            setHeight(this.mWrapHeight);
        }
    }

    public boolean isInHorizontalChain() {
        if (this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) {
            return true;
        }
        if (this.mRight.mTarget == null || this.mRight.mTarget.mTarget != this.mRight) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    public boolean isInVerticalChain() {
        if (this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) {
            return true;
        }
        if (this.mBottom.mTarget == null || this.mBottom.mTarget.mTarget != this.mBottom) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    private boolean isChainHead(int orientation) {
        int offset = orientation * 2;
        return (this.mListAnchors[offset].mTarget == null || this.mListAnchors[offset].mTarget.mTarget == this.mListAnchors[offset] || this.mListAnchors[offset + 1].mTarget == null || this.mListAnchors[offset + 1].mTarget.mTarget != this.mListAnchors[offset + 1]) ? false : true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01d6, code lost:
        if (r15.mResolvedDimensionRatioSide == -1) goto L_0x01da;
     */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x01dc  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01eb  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01ed  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x01fa  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x01fe  */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x0272  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0289 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x028a  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x02fb  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0306  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x030c  */
    /* JADX WARNING: Removed duplicated region for block: B:159:0x0317  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0354  */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x0380  */
    /* JADX WARNING: Removed duplicated region for block: B:170:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x01cf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(androidx.constraintlayout.solver.LinearSystem r53) {
        /*
            r52 = this;
            r15 = r52
            r10 = r53
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.mLeft
            androidx.constraintlayout.solver.SolverVariable r36 = r10.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.mRight
            androidx.constraintlayout.solver.SolverVariable r2 = r10.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.mTop
            androidx.constraintlayout.solver.SolverVariable r1 = r10.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.mBottom
            androidx.constraintlayout.solver.SolverVariable r0 = r10.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r15.mBaseline
            androidx.constraintlayout.solver.SolverVariable r13 = r10.createObjectVariable(r3)
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            r8 = 8
            r12 = 0
            r11 = 1
            if (r7 == 0) goto L_0x00bd
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            if (r7 == 0) goto L_0x003e
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r7.mListDimensionBehaviors
            r7 = r7[r12]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r7 != r9) goto L_0x003e
            r7 = 1
            goto L_0x003f
        L_0x003e:
            r7 = 0
        L_0x003f:
            r5 = r7
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            if (r7 == 0) goto L_0x0050
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r7.mListDimensionBehaviors
            r7 = r7[r11]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r7 != r9) goto L_0x0050
            r7 = 1
            goto L_0x0051
        L_0x0050:
            r7 = 0
        L_0x0051:
            r6 = r7
            boolean r7 = r15.isChainHead(r12)
            if (r7 == 0) goto L_0x0061
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r7 = (androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer) r7
            r7.addChain(r15, r12)
            r3 = 1
            goto L_0x0065
        L_0x0061:
            boolean r3 = r52.isInHorizontalChain()
        L_0x0065:
            boolean r7 = r15.isChainHead(r11)
            if (r7 == 0) goto L_0x0074
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r7 = (androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer) r7
            r7.addChain(r15, r11)
            r4 = 1
            goto L_0x0078
        L_0x0074:
            boolean r4 = r52.isInVerticalChain()
        L_0x0078:
            if (r5 == 0) goto L_0x0095
            int r7 = r15.mVisibility
            if (r7 == r8) goto L_0x0095
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r15.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 != 0) goto L_0x0095
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r15.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 != 0) goto L_0x0095
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.mRight
            androidx.constraintlayout.solver.SolverVariable r7 = r10.createObjectVariable(r7)
            r10.addGreaterThan(r7, r2, r12, r11)
        L_0x0095:
            if (r6 == 0) goto L_0x00b6
            int r7 = r15.mVisibility
            if (r7 == r8) goto L_0x00b6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r15.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 != 0) goto L_0x00b6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r15.mBottom
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 != 0) goto L_0x00b6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r15.mBaseline
            if (r7 != 0) goto L_0x00b6
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r15.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r7.mBottom
            androidx.constraintlayout.solver.SolverVariable r7 = r10.createObjectVariable(r7)
            r10.addGreaterThan(r7, r0, r12, r11)
        L_0x00b6:
            r37 = r3
            r38 = r4
            r9 = r5
            r7 = r6
            goto L_0x00c3
        L_0x00bd:
            r37 = r3
            r38 = r4
            r9 = r5
            r7 = r6
        L_0x00c3:
            int r3 = r15.mWidth
            int r4 = r15.mMinWidth
            if (r3 >= r4) goto L_0x00cb
            int r3 = r15.mMinWidth
        L_0x00cb:
            int r4 = r15.mHeight
            int r5 = r15.mMinHeight
            if (r4 >= r5) goto L_0x00d3
            int r4 = r15.mMinHeight
        L_0x00d3:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r15.mListDimensionBehaviors
            r5 = r5[r12]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 == r6) goto L_0x00dd
            r5 = 1
            goto L_0x00de
        L_0x00dd:
            r5 = 0
        L_0x00de:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r6 = r15.mListDimensionBehaviors
            r6 = r6[r11]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r6 == r14) goto L_0x00e8
            r6 = 1
            goto L_0x00e9
        L_0x00e8:
            r6 = 0
        L_0x00e9:
            r14 = 0
            int r11 = r15.mDimensionRatioSide
            r15.mResolvedDimensionRatioSide = r11
            float r11 = r15.mDimensionRatio
            r15.mResolvedDimensionRatio = r11
            int r11 = r15.mMatchConstraintDefaultWidth
            int r12 = r15.mMatchConstraintDefaultHeight
            float r8 = r15.mDimensionRatio
            r19 = 0
            r20 = r2
            int r8 = (r8 > r19 ? 1 : (r8 == r19 ? 0 : -1))
            if (r8 <= 0) goto L_0x01b7
            int r8 = r15.mVisibility
            r2 = 8
            if (r8 == r2) goto L_0x01b7
            r14 = 1
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r8 = 0
            r2 = r2[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r8) goto L_0x0113
            if (r11 != 0) goto L_0x0113
            r11 = 3
        L_0x0113:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r8 = 1
            r2 = r2[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r8) goto L_0x011f
            if (r12 != 0) goto L_0x011f
            r12 = 3
        L_0x011f:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r8 = 0
            r2 = r2[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r22 = r0
            r0 = 3
            if (r2 != r8) goto L_0x013d
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r8 = 1
            r2 = r2[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r8) goto L_0x013d
            if (r11 != r0) goto L_0x013d
            if (r12 != r0) goto L_0x013d
            r15.setupDimensionRatio(r9, r7, r5, r6)
            goto L_0x01b9
        L_0x013d:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r8 = 0
            r2 = r2[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r8) goto L_0x0174
            if (r11 != r0) goto L_0x0174
            r0 = 0
            r15.mResolvedDimensionRatioSide = r0
            float r0 = r15.mResolvedDimensionRatio
            int r2 = r15.mHeight
            float r2 = (float) r2
            float r0 = r0 * r2
            int r3 = (int) r0
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r2 = 1
            r0 = r0[r2]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 == r2) goto L_0x0169
            r11 = 4
            r14 = 0
            r39 = r3
            r40 = r4
            r41 = r11
            r42 = r12
            r43 = r14
            goto L_0x01c3
        L_0x0169:
            r39 = r3
            r40 = r4
            r41 = r11
            r42 = r12
            r43 = r14
            goto L_0x01c3
        L_0x0174:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r8 = 1
            r2 = r2[r8]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r2 != r8) goto L_0x01b9
            if (r12 != r0) goto L_0x01b9
            r0 = 1
            r15.mResolvedDimensionRatioSide = r0
            int r0 = r15.mDimensionRatioSide
            r2 = -1
            if (r0 != r2) goto L_0x018e
            r0 = 1065353216(0x3f800000, float:1.0)
            float r2 = r15.mResolvedDimensionRatio
            float r0 = r0 / r2
            r15.mResolvedDimensionRatio = r0
        L_0x018e:
            float r0 = r15.mResolvedDimensionRatio
            int r2 = r15.mWidth
            float r2 = (float) r2
            float r0 = r0 * r2
            int r4 = (int) r0
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r2 = 0
            r0 = r0[r2]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 == r2) goto L_0x01ac
            r12 = 4
            r14 = 0
            r39 = r3
            r40 = r4
            r41 = r11
            r42 = r12
            r43 = r14
            goto L_0x01c3
        L_0x01ac:
            r39 = r3
            r40 = r4
            r41 = r11
            r42 = r12
            r43 = r14
            goto L_0x01c3
        L_0x01b7:
            r22 = r0
        L_0x01b9:
            r39 = r3
            r40 = r4
            r41 = r11
            r42 = r12
            r43 = r14
        L_0x01c3:
            int[] r0 = r15.mResolvedMatchConstraintDefault
            r2 = 0
            r0[r2] = r41
            int[] r0 = r15.mResolvedMatchConstraintDefault
            r2 = 1
            r0[r2] = r42
            if (r43 == 0) goto L_0x01dc
            int r0 = r15.mResolvedDimensionRatioSide
            if (r0 == 0) goto L_0x01d9
            int r0 = r15.mResolvedDimensionRatioSide
            r12 = -1
            if (r0 != r12) goto L_0x01dd
            goto L_0x01da
        L_0x01d9:
            r12 = -1
        L_0x01da:
            r14 = 1
            goto L_0x01de
        L_0x01dc:
            r12 = -1
        L_0x01dd:
            r14 = 0
        L_0x01de:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r3 = 0
            r0 = r0[r3]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r3) goto L_0x01ed
            boolean r0 = r15 instanceof androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x01ed
            r0 = 1
            goto L_0x01ee
        L_0x01ed:
            r0 = 0
        L_0x01ee:
            r44 = r6
            r6 = r0
            r0 = 1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r15.mCenter
            boolean r3 = r3.isConnected()
            if (r3 == 0) goto L_0x01fe
            r0 = 0
            r23 = r0
            goto L_0x0200
        L_0x01fe:
            r23 = r0
        L_0x0200:
            int r0 = r15.mHorizontalResolution
            r11 = 2
            r24 = 0
            if (r0 == r11) goto L_0x0272
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x0215
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r15.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.solver.SolverVariable r0 = r10.createObjectVariable(r0)
            r4 = r0
            goto L_0x0217
        L_0x0215:
            r4 = r24
        L_0x0217:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x0225
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r15.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mLeft
            androidx.constraintlayout.solver.SolverVariable r0 = r10.createObjectVariable(r0)
            r3 = r0
            goto L_0x0227
        L_0x0225:
            r3 = r24
        L_0x0227:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r16 = 0
            r0 = r0[r16]
            r45 = r5
            r5 = r0
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r15.mLeft
            r46 = r7
            r7 = r0
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r15.mRight
            int r0 = r15.mX
            r47 = r9
            r9 = r0
            int r0 = r15.mMinWidth
            r2 = 2
            r11 = r0
            int[] r0 = r15.mMaxDimension
            r0 = r0[r16]
            r16 = -1
            r25 = 0
            r12 = r0
            float r0 = r15.mHorizontalBiasPercent
            r48 = r13
            r13 = r0
            int r0 = r15.mMatchConstraintMinWidth
            r17 = r0
            int r0 = r15.mMatchConstraintMaxWidth
            r18 = r0
            float r0 = r15.mMatchConstraintPercentWidth
            r19 = r0
            r49 = r22
            r0 = r52
            r50 = r1
            r1 = r53
            r51 = r20
            r2 = r47
            r10 = r39
            r15 = r37
            r16 = r41
            r20 = r23
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            goto L_0x0282
        L_0x0272:
            r50 = r1
            r45 = r5
            r46 = r7
            r47 = r9
            r48 = r13
            r51 = r20
            r49 = r22
            r25 = 0
        L_0x0282:
            r7 = r52
            int r0 = r7.mVerticalResolution
            r1 = 2
            if (r0 != r1) goto L_0x028a
            return
        L_0x028a:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r7.mListDimensionBehaviors
            r1 = 1
            r0 = r0[r1]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r0 != r2) goto L_0x029a
            boolean r0 = r7 instanceof androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x029a
            r21 = 1
            goto L_0x029c
        L_0x029a:
            r21 = 0
        L_0x029c:
            if (r43 == 0) goto L_0x02aa
            int r0 = r7.mResolvedDimensionRatioSide
            if (r0 == r1) goto L_0x02a7
            int r0 = r7.mResolvedDimensionRatioSide
            r2 = -1
            if (r0 != r2) goto L_0x02aa
        L_0x02a7:
            r29 = 1
            goto L_0x02ac
        L_0x02aa:
            r29 = 0
        L_0x02ac:
            int r0 = r7.mBaselineDistance
            if (r0 <= 0) goto L_0x02ef
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.mBaseline
            androidx.constraintlayout.solver.widgets.ResolutionAnchor r0 = r0.getResolutionNode()
            int r0 = r0.state
            if (r0 != r1) goto L_0x02ca
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.mBaseline
            androidx.constraintlayout.solver.widgets.ResolutionAnchor r0 = r0.getResolutionNode()
            r8 = r53
            r0.addResolvedValue(r8)
            r10 = r48
            r9 = r50
            goto L_0x02f5
        L_0x02ca:
            r8 = r53
            int r0 = r52.getBaselineDistance()
            r2 = 6
            r10 = r48
            r9 = r50
            r8.addEquality(r10, r9, r0, r2)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.mBaseline
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            if (r0 == 0) goto L_0x02f5
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.mBaseline
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            androidx.constraintlayout.solver.SolverVariable r0 = r8.createObjectVariable(r0)
            r3 = 0
            r8.addEquality(r10, r0, r3, r2)
            r23 = 0
            r11 = r23
            goto L_0x02f7
        L_0x02ef:
            r8 = r53
            r10 = r48
            r9 = r50
        L_0x02f5:
            r11 = r23
        L_0x02f7:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r7.mParent
            if (r0 == 0) goto L_0x0306
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r7.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            androidx.constraintlayout.solver.SolverVariable r0 = r8.createObjectVariable(r0)
            r19 = r0
            goto L_0x0308
        L_0x0306:
            r19 = r24
        L_0x0308:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r7.mParent
            if (r0 == 0) goto L_0x0317
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r7.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTop
            androidx.constraintlayout.solver.SolverVariable r0 = r8.createObjectVariable(r0)
            r18 = r0
            goto L_0x0319
        L_0x0317:
            r18 = r24
        L_0x0319:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r7.mListDimensionBehaviors
            r20 = r0[r1]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.mTop
            r22 = r0
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.mBottom
            r23 = r0
            int r0 = r7.mY
            r24 = r0
            int r0 = r7.mMinHeight
            r26 = r0
            int[] r0 = r7.mMaxDimension
            r27 = r0[r1]
            float r0 = r7.mVerticalBiasPercent
            r28 = r0
            int r0 = r7.mMatchConstraintMinHeight
            r32 = r0
            int r0 = r7.mMatchConstraintMaxHeight
            r33 = r0
            float r0 = r7.mMatchConstraintPercentHeight
            r34 = r0
            r15 = r52
            r16 = r53
            r17 = r46
            r25 = r40
            r30 = r38
            r31 = r42
            r35 = r11
            r15.applyConstraints(r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34, r35)
            if (r43 == 0) goto L_0x0378
            r12 = 6
            int r0 = r7.mResolvedDimensionRatioSide
            if (r0 != r1) goto L_0x0369
            float r5 = r7.mResolvedDimensionRatio
            r0 = r53
            r1 = r49
            r2 = r9
            r3 = r51
            r4 = r36
            r6 = r12
            r0.addRatio(r1, r2, r3, r4, r5, r6)
            goto L_0x0378
        L_0x0369:
            float r5 = r7.mResolvedDimensionRatio
            r0 = r53
            r1 = r51
            r2 = r36
            r3 = r49
            r4 = r9
            r6 = r12
            r0.addRatio(r1, r2, r3, r4, r5, r6)
        L_0x0378:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.mCenter
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x039e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.mCenter
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.getTarget()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.getOwner()
            float r1 = r7.mCircleConstraintAngle
            r2 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 + r2
            double r1 = (double) r1
            double r1 = java.lang.Math.toRadians(r1)
            float r1 = (float) r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r7.mCenter
            int r2 = r2.getMargin()
            r8.addCenterPoint(r7, r0, r1, r2)
        L_0x039e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.addToSolver(androidx.constraintlayout.solver.LinearSystem):void");
    }

    public void setupDimensionRatio(boolean hparentWrapContent, boolean vparentWrapContent, boolean horizontalDimensionFixed, boolean verticalDimensionFixed) {
        if (this.mResolvedDimensionRatioSide == -1) {
            if (horizontalDimensionFixed && !verticalDimensionFixed) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!horizontalDimensionFixed && verticalDimensionFixed) {
                this.mResolvedDimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                }
            }
        }
        if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
            this.mResolvedDimensionRatioSide = 1;
        } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
            this.mResolvedDimensionRatioSide = 0;
        }
        if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
            if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide == -1) {
            if (hparentWrapContent && !vparentWrapContent) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!hparentWrapContent && vparentWrapContent) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide == -1) {
            if (this.mMatchConstraintMinWidth > 0 && this.mMatchConstraintMinHeight == 0) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMinHeight > 0) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide == -1 && hparentWrapContent && vparentWrapContent) {
            this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
            this.mResolvedDimensionRatioSide = 1;
        }
    }

    private void applyConstraints(LinearSystem system, boolean parentWrapContent, SolverVariable parentMin, SolverVariable parentMax, DimensionBehaviour dimensionBehaviour, boolean wrapContent, ConstraintAnchor beginAnchor, ConstraintAnchor endAnchor, int beginPosition, int dimension, int minDimension, int maxDimension, float bias, boolean useRatio, boolean inChain, int matchConstraintDefault, int matchMinDimension, int matchMaxDimension, float matchPercentDimension, boolean applyPosition) {
        int matchConstraintDefault2;
        int dimension2;
        int numConnections;
        SolverVariable beginTarget;
        SolverVariable endTarget;
        boolean variableSize;
        int matchMaxDimension2;
        int matchConstraintDefault3;
        int dimension3;
        int numConnections2;
        int i;
        int matchConstraintDefault4;
        SolverVariable begin;
        SolverVariable begin2;
        int i2;
        int i3;
        SolverVariable begin3;
        int i4;
        int i5;
        int centeringStrength;
        boolean applyCentering;
        boolean applyBoundsCheck;
        SolverVariable beginTarget2;
        SolverVariable end;
        SolverVariable endTarget2;
        SolverVariable begin4;
        int endStrength;
        int startStrength;
        boolean applyCentering2;
        int i6;
        int matchMaxDimension3;
        int matchMinDimension2;
        int dimension4;
        int i7;
        SolverVariable percentEnd;
        SolverVariable percentEnd2;
        int i8;
        LinearSystem linearSystem = system;
        SolverVariable solverVariable = parentMin;
        SolverVariable solverVariable2 = parentMax;
        int i9 = minDimension;
        int i10 = maxDimension;
        SolverVariable begin5 = linearSystem.createObjectVariable(beginAnchor);
        SolverVariable end2 = linearSystem.createObjectVariable(endAnchor);
        SolverVariable beginTarget3 = linearSystem.createObjectVariable(beginAnchor.getTarget());
        SolverVariable endTarget3 = linearSystem.createObjectVariable(endAnchor.getTarget());
        if (linearSystem.graphOptimizer && beginAnchor.getResolutionNode().state == 1 && endAnchor.getResolutionNode().state == 1) {
            if (LinearSystem.getMetrics() != null) {
                LinearSystem.getMetrics().resolvedWidgets++;
            }
            beginAnchor.getResolutionNode().addResolvedValue(linearSystem);
            endAnchor.getResolutionNode().addResolvedValue(linearSystem);
            if (!inChain && parentWrapContent) {
                linearSystem.addGreaterThan(solverVariable2, end2, 0, 6);
                return;
            }
            return;
        }
        if (LinearSystem.getMetrics() != null) {
            LinearSystem.getMetrics().nonresolvedWidgets++;
        }
        boolean isBeginConnected = beginAnchor.isConnected();
        boolean isEndConnected = endAnchor.isConnected();
        boolean isCenterConnected = this.mCenter.isConnected();
        boolean variableSize2 = false;
        int numConnections3 = 0;
        if (isBeginConnected) {
            numConnections3 = 0 + 1;
        }
        if (isEndConnected) {
            numConnections3++;
        }
        if (isCenterConnected) {
            numConnections3++;
        }
        int numConnections4 = numConnections3;
        if (useRatio) {
            matchConstraintDefault2 = 3;
        } else {
            matchConstraintDefault2 = matchConstraintDefault;
        }
        switch (dimensionBehaviour) {
            case FIXED:
                variableSize2 = false;
                break;
            case WRAP_CONTENT:
                variableSize2 = false;
                break;
            case MATCH_PARENT:
                variableSize2 = false;
                break;
            case MATCH_CONSTRAINT:
                variableSize2 = true;
                if (matchConstraintDefault2 == 4) {
                    variableSize2 = false;
                    break;
                }
                break;
        }
        boolean variableSize3 = variableSize2;
        if (this.mVisibility == 8) {
            dimension2 = 0;
            variableSize3 = false;
        } else {
            dimension2 = dimension;
        }
        if (!applyPosition) {
            numConnections = numConnections4;
        } else if (isBeginConnected || isEndConnected || isCenterConnected) {
            int i11 = beginPosition;
            if (!isBeginConnected || isEndConnected) {
                numConnections = numConnections4;
            } else {
                numConnections = numConnections4;
                linearSystem.addEquality(begin5, beginTarget3, beginAnchor.getMargin(), 6);
            }
        } else {
            linearSystem.addEquality(begin5, beginPosition);
            numConnections = numConnections4;
        }
        if (!variableSize3) {
            if (wrapContent) {
                linearSystem.addEquality(end2, begin5, 0, 3);
                if (i9 > 0) {
                    i8 = 6;
                    linearSystem.addGreaterThan(end2, begin5, i9, 6);
                } else {
                    i8 = 6;
                }
                if (i10 < Integer.MAX_VALUE) {
                    linearSystem.addLowerThan(end2, begin5, i10, i8);
                }
            } else {
                linearSystem.addEquality(end2, begin5, dimension2, 6);
            }
            dimension3 = matchMinDimension;
            matchMaxDimension2 = matchMaxDimension;
            matchConstraintDefault3 = matchConstraintDefault2;
            endTarget = endTarget3;
            beginTarget = beginTarget3;
            variableSize = variableSize3;
            numConnections2 = numConnections;
            i = 6;
            int i12 = dimension2;
        } else {
            int matchMinDimension3 = matchMinDimension;
            if (matchMinDimension3 == -2) {
                matchMinDimension3 = dimension2;
            }
            SolverVariable endTarget4 = endTarget3;
            int matchMaxDimension4 = matchMaxDimension;
            if (matchMaxDimension4 == -2) {
                matchMaxDimension4 = dimension2;
            }
            if (matchMinDimension3 > 0) {
                i6 = 6;
                linearSystem.addGreaterThan(end2, begin5, matchMinDimension3, 6);
                dimension2 = Math.max(dimension2, matchMinDimension3);
            } else {
                i6 = 6;
            }
            if (matchMaxDimension4 > 0) {
                linearSystem.addLowerThan(end2, begin5, matchMaxDimension4, i6);
                dimension2 = Math.min(dimension2, matchMaxDimension4);
            }
            if (matchConstraintDefault2 == 1) {
                if (parentWrapContent) {
                    linearSystem.addEquality(end2, begin5, dimension2, 6);
                    dimension4 = dimension2;
                    matchConstraintDefault3 = matchConstraintDefault2;
                    matchMinDimension2 = matchMinDimension3;
                    matchMaxDimension3 = matchMaxDimension4;
                    beginTarget = beginTarget3;
                    numConnections2 = numConnections;
                    endTarget = endTarget4;
                    i7 = 2;
                } else if (inChain) {
                    linearSystem.addEquality(end2, begin5, dimension2, 4);
                    dimension4 = dimension2;
                    matchConstraintDefault3 = matchConstraintDefault2;
                    matchMinDimension2 = matchMinDimension3;
                    matchMaxDimension3 = matchMaxDimension4;
                    beginTarget = beginTarget3;
                    numConnections2 = numConnections;
                    endTarget = endTarget4;
                    i7 = 2;
                } else {
                    linearSystem.addEquality(end2, begin5, dimension2, 1);
                    dimension4 = dimension2;
                    matchConstraintDefault3 = matchConstraintDefault2;
                    matchMinDimension2 = matchMinDimension3;
                    matchMaxDimension3 = matchMaxDimension4;
                    beginTarget = beginTarget3;
                    numConnections2 = numConnections;
                    endTarget = endTarget4;
                    i7 = 2;
                }
            } else if (matchConstraintDefault2 == 2) {
                int dimension5 = dimension2;
                if (beginAnchor.getType() == ConstraintAnchor.Type.TOP || beginAnchor.getType() == ConstraintAnchor.Type.BOTTOM) {
                    percentEnd = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.TOP));
                    percentEnd2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.BOTTOM));
                } else {
                    percentEnd = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.LEFT));
                    percentEnd2 = linearSystem.createObjectVariable(this.mParent.getAnchor(ConstraintAnchor.Type.RIGHT));
                }
                matchConstraintDefault3 = matchConstraintDefault2;
                dimension4 = dimension5;
                numConnections2 = numConnections;
                i7 = 2;
                matchMinDimension2 = matchMinDimension3;
                matchMaxDimension3 = matchMaxDimension4;
                endTarget = endTarget4;
                beginTarget = beginTarget3;
                linearSystem.addConstraint(system.createRow().createRowDimensionRatio(end2, begin5, percentEnd2, percentEnd, matchPercentDimension));
                variableSize3 = false;
            } else {
                dimension4 = dimension2;
                matchConstraintDefault3 = matchConstraintDefault2;
                matchMinDimension2 = matchMinDimension3;
                matchMaxDimension3 = matchMaxDimension4;
                beginTarget = beginTarget3;
                numConnections2 = numConnections;
                endTarget = endTarget4;
                i7 = 2;
            }
            if (!variableSize3 || numConnections2 == i7 || useRatio) {
                i = 6;
                matchMaxDimension2 = matchMaxDimension3;
                variableSize = variableSize3;
                int i13 = dimension4;
                dimension3 = matchMinDimension2;
            } else {
                int matchMinDimension4 = matchMinDimension2;
                int d = Math.max(matchMinDimension4, dimension4);
                int matchMaxDimension5 = matchMaxDimension3;
                if (matchMaxDimension5 > 0) {
                    d = Math.min(matchMaxDimension5, d);
                }
                i = 6;
                linearSystem.addEquality(end2, begin5, d, 6);
                matchMaxDimension2 = matchMaxDimension5;
                variableSize = false;
                int i14 = dimension4;
                dimension3 = matchMinDimension4;
            }
        }
        if (!applyPosition) {
            ConstraintAnchor constraintAnchor = beginAnchor;
            ConstraintAnchor constraintAnchor2 = endAnchor;
            int i15 = dimension3;
            int i16 = matchConstraintDefault3;
            SolverVariable solverVariable3 = beginTarget;
            i3 = 0;
            i2 = 2;
            begin = begin5;
            matchConstraintDefault4 = numConnections2;
            SolverVariable solverVariable4 = endTarget;
            begin2 = end2;
        } else if (inChain) {
            ConstraintAnchor constraintAnchor3 = beginAnchor;
            ConstraintAnchor constraintAnchor4 = endAnchor;
            int i17 = dimension3;
            int i18 = matchConstraintDefault3;
            SolverVariable solverVariable5 = beginTarget;
            i3 = 0;
            i2 = 2;
            begin = begin5;
            matchConstraintDefault4 = numConnections2;
            SolverVariable solverVariable6 = endTarget;
            begin2 = end2;
        } else {
            if (isBeginConnected || isEndConnected || isCenterConnected) {
                if (!isBeginConnected || isEndConnected) {
                    if (isBeginConnected || !isEndConnected) {
                        SolverVariable endTarget5 = endTarget;
                        if (!isBeginConnected || !isEndConnected) {
                            ConstraintAnchor constraintAnchor5 = beginAnchor;
                            ConstraintAnchor constraintAnchor6 = endAnchor;
                            int i19 = dimension3;
                            SolverVariable solverVariable7 = beginTarget;
                            i5 = 0;
                            SolverVariable solverVariable8 = begin5;
                            begin3 = end2;
                            i4 = 6;
                            int i20 = numConnections2;
                            SolverVariable solverVariable9 = endTarget5;
                            int i21 = matchConstraintDefault3;
                            int matchConstraintDefault5 = i20;
                        } else {
                            boolean applyBoundsCheck2 = false;
                            boolean applyCentering3 = false;
                            if (variableSize) {
                                if (parentWrapContent && i9 == 0) {
                                    linearSystem.addGreaterThan(end2, begin5, 0, i);
                                }
                                if (matchConstraintDefault3 == 0) {
                                    int strength = 6;
                                    if (matchMaxDimension2 > 0 || dimension3 > 0) {
                                        strength = 4;
                                        applyBoundsCheck2 = true;
                                    }
                                    boolean applyBoundsCheck3 = applyBoundsCheck2;
                                    beginTarget2 = beginTarget;
                                    linearSystem.addEquality(begin5, beginTarget2, beginAnchor.getMargin(), strength);
                                    linearSystem.addEquality(end2, endTarget5, -endAnchor.getMargin(), strength);
                                    if (matchMaxDimension2 > 0 || dimension3 > 0) {
                                        applyCentering3 = true;
                                    }
                                    applyBoundsCheck = applyBoundsCheck3;
                                    applyCentering = applyCentering3;
                                    centeringStrength = 5;
                                    int i22 = dimension3;
                                } else {
                                    beginTarget2 = beginTarget;
                                    if (matchConstraintDefault3 == 1) {
                                        applyCentering = true;
                                        centeringStrength = 6;
                                        applyBoundsCheck = true;
                                        int i23 = dimension3;
                                    } else if (matchConstraintDefault3 == 3) {
                                        int strength2 = 4;
                                        if (!useRatio) {
                                            int i24 = dimension3;
                                            applyCentering2 = true;
                                            if (this.mResolvedDimensionRatioSide != -1 && matchMaxDimension2 <= 0) {
                                                strength2 = 6;
                                            }
                                        } else {
                                            applyCentering2 = true;
                                            int i25 = dimension3;
                                        }
                                        linearSystem.addEquality(begin5, beginTarget2, beginAnchor.getMargin(), strength2);
                                        linearSystem.addEquality(end2, endTarget5, -endAnchor.getMargin(), strength2);
                                        applyCentering = applyCentering2;
                                        centeringStrength = 5;
                                        applyBoundsCheck = true;
                                    } else {
                                        int matchMinDimension5 = dimension3;
                                        applyCentering = false;
                                        centeringStrength = 5;
                                        applyBoundsCheck = false;
                                    }
                                }
                            } else {
                                int i26 = dimension3;
                                beginTarget2 = beginTarget;
                                applyCentering = true;
                                centeringStrength = 5;
                                applyBoundsCheck = false;
                            }
                            int startStrength2 = 5;
                            int endStrength2 = 5;
                            boolean applyStartConstraint = parentWrapContent;
                            boolean applyEndConstraint = parentWrapContent;
                            if (applyCentering) {
                                endTarget2 = endTarget5;
                                end = end2;
                                begin4 = begin5;
                                int matchConstraintDefault6 = matchConstraintDefault3;
                                int matchConstraintDefault7 = numConnections2;
                                system.addCentering(begin5, beginTarget2, beginAnchor.getMargin(), bias, endTarget2, end2, endAnchor.getMargin(), centeringStrength);
                                i5 = 0;
                                boolean isBeginAnchorBarrier = beginAnchor.mTarget.mOwner instanceof Barrier;
                                int i27 = matchConstraintDefault6;
                                boolean isEndAnchorBarrier = endAnchor.mTarget.mOwner instanceof Barrier;
                                if (isBeginAnchorBarrier && !isEndAnchorBarrier) {
                                    endStrength2 = 6;
                                    applyEndConstraint = true;
                                } else if (!isBeginAnchorBarrier && isEndAnchorBarrier) {
                                    startStrength2 = 6;
                                    applyStartConstraint = true;
                                }
                            } else {
                                ConstraintAnchor constraintAnchor7 = beginAnchor;
                                ConstraintAnchor constraintAnchor8 = endAnchor;
                                endTarget2 = endTarget5;
                                end = end2;
                                begin4 = begin5;
                                int i28 = matchConstraintDefault3;
                                i5 = 0;
                                int matchConstraintDefault8 = numConnections2;
                            }
                            if (applyBoundsCheck) {
                                startStrength = 6;
                                endStrength = 6;
                            } else {
                                startStrength = startStrength2;
                                endStrength = endStrength2;
                            }
                            if ((!variableSize && applyStartConstraint) || applyBoundsCheck) {
                                linearSystem.addGreaterThan(begin4, beginTarget2, beginAnchor.getMargin(), startStrength);
                            }
                            if ((variableSize || !applyEndConstraint) && !applyBoundsCheck) {
                                begin3 = end;
                                SolverVariable solverVariable10 = endTarget2;
                            } else {
                                begin3 = end;
                                linearSystem.addLowerThan(begin3, endTarget2, -endAnchor.getMargin(), endStrength);
                            }
                            if (parentWrapContent) {
                                i4 = 6;
                                linearSystem.addGreaterThan(begin4, solverVariable, i5, 6);
                            } else {
                                i4 = 6;
                            }
                        }
                    } else {
                        SolverVariable endTarget6 = endTarget;
                        linearSystem.addEquality(end2, endTarget6, -endAnchor.getMargin(), i);
                        if (parentWrapContent) {
                            linearSystem.addGreaterThan(begin5, solverVariable, 0, 5);
                            ConstraintAnchor constraintAnchor9 = beginAnchor;
                            ConstraintAnchor constraintAnchor10 = endAnchor;
                            int i29 = dimension3;
                            SolverVariable solverVariable11 = beginTarget;
                            i5 = 0;
                            SolverVariable solverVariable12 = begin5;
                            begin3 = end2;
                            i4 = 6;
                            int i30 = numConnections2;
                            SolverVariable solverVariable13 = endTarget6;
                            int i31 = matchConstraintDefault3;
                            int matchConstraintDefault9 = i30;
                        } else {
                            ConstraintAnchor constraintAnchor11 = beginAnchor;
                            ConstraintAnchor constraintAnchor12 = endAnchor;
                            int i32 = dimension3;
                            SolverVariable solverVariable14 = beginTarget;
                            i5 = 0;
                            SolverVariable solverVariable15 = begin5;
                            begin3 = end2;
                            i4 = 6;
                            int i33 = numConnections2;
                            SolverVariable solverVariable16 = endTarget6;
                            int i34 = matchConstraintDefault3;
                            int matchConstraintDefault10 = i33;
                        }
                    }
                } else if (parentWrapContent) {
                    linearSystem.addGreaterThan(solverVariable2, end2, 0, 5);
                    ConstraintAnchor constraintAnchor13 = beginAnchor;
                    ConstraintAnchor constraintAnchor14 = endAnchor;
                    int i35 = dimension3;
                    int i36 = matchConstraintDefault3;
                    SolverVariable solverVariable17 = beginTarget;
                    i5 = 0;
                    SolverVariable solverVariable18 = begin5;
                    int matchConstraintDefault11 = numConnections2;
                    SolverVariable solverVariable19 = endTarget;
                    begin3 = end2;
                    i4 = 6;
                } else {
                    ConstraintAnchor constraintAnchor15 = beginAnchor;
                    ConstraintAnchor constraintAnchor16 = endAnchor;
                    int i37 = dimension3;
                    int i38 = matchConstraintDefault3;
                    SolverVariable solverVariable20 = beginTarget;
                    i5 = 0;
                    SolverVariable solverVariable21 = begin5;
                    int matchConstraintDefault12 = numConnections2;
                    SolverVariable solverVariable22 = endTarget;
                    begin3 = end2;
                    i4 = 6;
                }
            } else if (parentWrapContent) {
                linearSystem.addGreaterThan(solverVariable2, end2, 0, 5);
                ConstraintAnchor constraintAnchor17 = beginAnchor;
                ConstraintAnchor constraintAnchor18 = endAnchor;
                int i39 = dimension3;
                int i40 = matchConstraintDefault3;
                SolverVariable solverVariable23 = beginTarget;
                i5 = 0;
                SolverVariable solverVariable24 = begin5;
                int matchConstraintDefault13 = numConnections2;
                SolverVariable solverVariable25 = endTarget;
                begin3 = end2;
                i4 = 6;
            } else {
                ConstraintAnchor constraintAnchor19 = beginAnchor;
                ConstraintAnchor constraintAnchor20 = endAnchor;
                int i41 = dimension3;
                int i42 = matchConstraintDefault3;
                SolverVariable solverVariable26 = beginTarget;
                i5 = 0;
                SolverVariable solverVariable27 = begin5;
                int matchConstraintDefault14 = numConnections2;
                SolverVariable solverVariable28 = endTarget;
                begin3 = end2;
                i4 = 6;
            }
            if (parentWrapContent) {
                linearSystem.addGreaterThan(solverVariable2, begin3, i5, i4);
                return;
            }
            return;
        }
        if (matchConstraintDefault4 < i2 && parentWrapContent) {
            linearSystem.addGreaterThan(begin, solverVariable, i3, 6);
            linearSystem.addGreaterThan(solverVariable2, begin2, i3, 6);
        }
    }

    public void updateFromSolver(LinearSystem system) {
        int left = system.getObjectVariableValue(this.mLeft);
        int top = system.getObjectVariableValue(this.mTop);
        int right = system.getObjectVariableValue(this.mRight);
        int bottom = system.getObjectVariableValue(this.mBottom);
        int h = bottom - top;
        if (right - left < 0 || h < 0 || left == Integer.MIN_VALUE || left == Integer.MAX_VALUE || top == Integer.MIN_VALUE || top == Integer.MAX_VALUE || right == Integer.MIN_VALUE || right == Integer.MAX_VALUE || bottom == Integer.MIN_VALUE || bottom == Integer.MAX_VALUE) {
            left = 0;
            top = 0;
            right = 0;
            bottom = 0;
        }
        setFrame(left, top, right, bottom);
    }
}
