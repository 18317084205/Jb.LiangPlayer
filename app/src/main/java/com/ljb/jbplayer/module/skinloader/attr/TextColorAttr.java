package com.ljb.jbplayer.module.skinloader.attr;

import android.view.View;
import android.widget.TextView;

import com.ljb.jbplayer.module.skinloader.SkinManager;
import com.ljb.jbplayer.util.Logger;


public class TextColorAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                Logger.i("applyAttr", "TextColorAttr");
                tv.setTextColor(SkinManager.getInstance().convertToColorStateList(attrValueRefId));
            }
        }
    }
}
