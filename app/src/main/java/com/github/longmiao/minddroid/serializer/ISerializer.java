package com.github.longmiao.minddroid.serializer;

import com.github.longmiao.minddroid.view.MindNodeGroup;

/**
 * Created by 13208 on 2016/11/17.
 */
public interface ISerializer<T> {
    MindNodeGroup loads(T value);
    T dumps(MindNodeGroup nodeGroup);
}
