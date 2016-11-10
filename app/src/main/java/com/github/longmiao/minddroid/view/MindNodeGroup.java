package com.github.longmiao.minddroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by 13208 on 2016/11/10.
 */
public class MindNodeGroup extends ViewGroup{
    public static final int NODE_WIDTH = 150;
    public static final int NODE_HEIGHT = 80;
    public static final int PADDING_TOP = 10;
    public static final int PADDING_BOTTOM = 10;
    public static final int PADDING_LEFT = 0;
    public static final int PADDING_RIGHT = 60;

    private float mChildNodeWidth = NODE_WIDTH;
    private float mChildNodeHeight = NODE_HEIGHT;

    public MindNodeGroup(Context context) {
        super(context);
        initialize(null);
    }

    public MindNodeGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public MindNodeGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        setBackgroundColor(Color.WHITE);
        MindNode node = new MindNode(getContext(), attrs);
        addView(node);
        node.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "you lick the node!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 0;
        int height = 0;

        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child instanceof MindNode) {
                mChildNodeWidth = Math.max(mChildNodeWidth, child.getMeasuredWidth());
                mChildNodeHeight = Math.max(mChildNodeHeight, child.getMeasuredHeight());
                continue;
            }
            width = Math.max(width, child.getMeasuredWidth());
            height += child.getMeasuredHeight();
        }
        setMeasuredDimension(width + (int)mChildNodeWidth + PADDING_RIGHT + PADDING_LEFT, height == 0?NODE_HEIGHT + PADDING_BOTTOM + PADDING_TOP:height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int measureHeight = getMeasuredHeight();
        int childCount = getChildCount();
        int childLayoutY = 0;
        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child instanceof MindNode) {
                child.layout(PADDING_LEFT, PADDING_TOP + measureHeight / 2 - NODE_HEIGHT / 2, PADDING_LEFT + (int)mChildNodeWidth, PADDING_TOP + measureHeight / 2 + NODE_HEIGHT / 2);
                continue;
            }
            int childMeasureWidth = child.getMeasuredWidth();
            int childMeasureHeight = child.getMeasuredHeight();
            child.layout((int)mChildNodeWidth + PADDING_LEFT + PADDING_RIGHT, childLayoutY, getRight() + childMeasureWidth, childLayoutY + childMeasureHeight);
            childLayoutY += childMeasureHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        int measureHeight = getMeasuredHeight();
        int childCount = getChildCount();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setColor(Color.GREEN);
        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child instanceof MindNode) {
                continue;
            }
            float nodePointInX = PADDING_LEFT + mChildNodeWidth;
            float nodePointInY = PADDING_TOP + measureHeight/2;
            float nodePointOutX = child.getLeft() + PADDING_LEFT;
            float nodePointOutY = child.getHeight()/2 + child.getTop() + PADDING_TOP;
            Path path = new Path();
            path.reset();
            path.moveTo(nodePointInX, nodePointInY);
            path.cubicTo(nodePointInX + (nodePointOutX - nodePointInX) * 2 / 3, nodePointInY, nodePointInX, nodePointOutY, nodePointOutX, nodePointOutY);
            canvas.drawPath(path, paint);
        }
    }
}