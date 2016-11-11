package com.github.longmiao.minddroid.util;

import android.content.Context;

import com.github.longmiao.minddroid.view.MindNodeGroup;

/**
 * Created by 13208 on 2016/11/11.
 */
public class MindNodeGroupBuilder {
    private MindNodeGroup mNodeGroup = null;

    public MindNodeGroupBuilder buildMindNodeGroup(Context context) {
        mNodeGroup = new MindNodeGroup(context);
        return this;
    }

    public MindNodeGroupBuilder setNodeText(String text) {
        if(mNodeGroup != null) {
            mNodeGroup.getNode().setNodeText(text);
        }
        return this;
    }

    public MindNodeGroupBuilder setNodeType(String type) {
        if(mNodeGroup != null) {
            mNodeGroup.getNode().setNodeType(type);
        }
        return this;
    }

    public MindNodeGroupBuilder setRounded(boolean rounded) {
        if(mNodeGroup != null) {
            mNodeGroup.getNode().setRoundedCorners(rounded);
        }
        return this;
    }

    public MindNodeGroup getNodeGroup() {
        return mNodeGroup;
    }
}
