package com.github.longmiao.minddroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.longmiao.minddroid.R;
import com.github.longmiao.minddroid.util.ViewUtil;

/**
 * Created by 13208 on 2016/11/10.
 */
public class MindNode extends FrameLayout{
    private ViewGroup mContainer;
    private TextView mTv;
    private float mTextSize = 12F;
    private String mText = "";
    private boolean mRoundedCorners = false;
    private NodeType mCurNodeType = NodeType.DEFAULT;

    private enum NodeType
    {
        DEFAULT("default", R.drawable.amn_default,  R.drawable.amn_default_rounded, R.color.colorPrimaryDark),
        SUCCESS("success", R.drawable.amn_success, R.drawable.amn_success_rounded, R.color.colorWhite),
        INFO("info", R.drawable.amn_info, R.drawable.amn_info_rounded, R.color.colorWhite),
        PRIMARY("primary",  R.drawable.amn_primary,  R.drawable.amn_primary_rounded,  R.color.colorWhite),
        DANGER( "danger",   R.drawable.amn_danger,   R.drawable.amn_danger_rounded,   R.color.colorWhite),
        WARNING("warning",  R.drawable.amn_warning,  R.drawable.amn_warning_rounded,  R.color.colorWhite),
        INVERSE("inverse",  R.drawable.amn_inverse,  R.drawable.amn_inverse_rounded,  R.color.colorWhite);
        private String type;
        private int defaultBg;
        private int roundedBg;
        private int textColor;

        NodeType(String type, int db, int rb, int tc) {
            this.type = type;
            this.defaultBg = db;
            this.roundedBg = rb;
            this.textColor = tc;
        }

        public static NodeType getNodeTypeFromString(String type) {
            for (NodeType value : NodeType.values()) {
                if(value.type.equals(type)) {
                    return value;
                }
            }

            return DEFAULT;
        }

        public int getDefaultBg() {
            return this.defaultBg;
        }

        public int getRoundedBg() {
            return this.roundedBg;
        }

        public int getTextColor() {
            return this.textColor;
        }

    }

    private enum NodeSize {
        LARGE(  "large",    20.0f,  15, 20),
        DEFAULT("default",  14.0f,  10, 15),
        SMALL(  "small",    12.0f,  5,  10),
        XSMALL( "xsmall",   10.0f,  2,  5);

        private float fontSize;
        private String type;
        private int paddingA;
        private int paddingB;

        private NodeSize(String type, float fontSize, int paddingA, int paddingB) {
            this.type = type;
            this.fontSize = fontSize;
            this.paddingA = paddingA;
            this.paddingB = paddingB;
        }

        public float getFontSize() {
            return fontSize;
        }

        public static NodeSize getBootstrapSizeFromString(String size) {
            for (NodeSize value : NodeSize.values()) {
                if (value.type.equals(size)) {
                    return value;
                }
            }
            return DEFAULT;
        }
    }

    public MindNode(Context context) {
        super(context);
        initialize(null);
    }

    public MindNode(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public MindNode(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        String nodeType = "default";

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MindNodeDroid);

        try {
            nodeType = a.getString(R.styleable.MindNodeDroid_amn_type);
            mText = a.getString(R.styleable.MindNodeDroid_android_text);
            mText = mText == null ? "MindDroid Node": mText;

            mRoundedCorners = a.getBoolean(R.styleable.MindNodeDroid_amn_roundedCorners, false);
        }
        finally {
            a.recycle();
        }
        View child = inflater.inflate(R.layout.mind_node, this, false);
        mContainer = (ViewGroup) child.findViewById(R.id.container);

        mTv = (TextView) mContainer.findViewById(R.id.mindnode_tv);
        mTv.setTextSize(mTextSize);
        if(mText.length() > 0) {
            mTv.setText(mText);
        }

        setClickable(true);
        setNodeType(nodeType);

        addView(child);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        int height = 0;
        int textWidth = (int)ViewUtil.getTextWidth(getContext(), mText, mTextSize);
        measureChildren(MeasureSpec.makeMeasureSpec(textWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST));

        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            widthSize = Math.min(widthSize, child.getMeasuredWidth());
            height += child.getMeasuredHeight();
        }

        setMeasuredDimension(widthSize, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        View child = getChildAt(0);
        child.layout(0, 0, right - left, bottom - top);
        View grandChild = ((LinearLayout) child).getChildAt(0);
        grandChild.layout(0, 0, right - left, bottom - top);
        if(grandChild instanceof TextView) {
            ((TextView) grandChild).setGravity(Gravity.CENTER_HORIZONTAL);
        }
    }

    public void setNodeType(String nodeType) {
        mCurNodeType = NodeType.getNodeTypeFromString(nodeType);
        int bgStyle = mRoundedCorners ? mCurNodeType.getRoundedBg() : mCurNodeType.getDefaultBg();
        mContainer.setBackgroundResource(bgStyle);
    }

    public void setNodeText(String text) {
        mText = text;
        if(mTv != null) {
            mTv.setText(text);
        }
    }

    public void setRoundedCorners(boolean rounded) {
        mRoundedCorners = rounded;
        setNodeType(mCurNodeType.type);
    }
}
