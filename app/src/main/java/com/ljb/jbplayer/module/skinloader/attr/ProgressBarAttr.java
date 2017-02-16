package com.ljb.jbplayer.module.skinloader.attr;

import android.view.View;
import android.widget.ProgressBar;

import com.ljb.jbplayer.module.skinloader.SkinManager;

/**
 * Created by Administrator on 2017/1/28.
 */

public class ProgressBarAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof ProgressBar) {
            ProgressBar pb = (ProgressBar) view;
            if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
                pb.setProgressDrawable(SkinManager.getInstance().getDrawable(attrValueRefId));
            }
        }
    }
}
