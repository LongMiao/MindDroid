package com.github.longmiao.minddroid.serializer;

import android.view.View;

import com.github.longmiao.minddroid.view.MindNodeGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by 13208 on 2016/11/17.
 */
public class JsonSerializer implements ISerializer<JSONObject>{

    @Override
    public JSONObject dumps(MindNodeGroup nodeGroup) {
        JSONObject curNodeJsonObject = new JSONObject();
        try {
            curNodeJsonObject.put("text", nodeGroup.getNode().getNodeText());
            int childCount = nodeGroup.getChildCount();
            JSONArray childrenJsonArray = new JSONArray();
            for(int i = 0; i< childCount; i++) {
                View childView = nodeGroup.getChildAt(i);
                if(childView instanceof MindNodeGroup) {
                    childrenJsonArray.put(dumps((MindNodeGroup) childView));
                }
            }
            curNodeJsonObject.put("child", childrenJsonArray);
        }catch (JSONException e) {

        }

        return curNodeJsonObject;
    }

    @Override
    public MindNodeGroup loads(JSONObject value) {
        return null;
    }
}
