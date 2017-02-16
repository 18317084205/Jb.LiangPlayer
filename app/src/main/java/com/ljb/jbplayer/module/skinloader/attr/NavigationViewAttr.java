package com.ljb.jbplayer.module.skinloader.attr;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.view.View;

import com.ljb.jbplayer.module.skinloader.SkinManager;
import com.ljb.jbplayer.util.Logger;


public class NavigationViewAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof NavigationView) {
            Logger.i("TabLayoutAttr", "apply");
            NavigationView nv = (NavigationView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)) {
                Logger.i("TabLayoutAttr", "apply color");
                int color = SkinManager.getInstance().getColor(attrValueRefId);
                nv.setItemTextColor(createSelector(color));
                nv.setItemIconTintList(createSelector(color));
            } else if (RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)) {
                Logger.i("TabLayoutAttr", "apply drawable");
                //  tv.setDivider(SkinManager.getInstance().getDrawable(attrValueRefId));
            }
        }
    }

    private ColorStateList createSelector(int color) {
        int statePressed = android.R.attr.state_checked;
        int stateChecked = android.R.attr.state_checked;
        int[][] state = {{statePressed}, {-statePressed}, {stateChecked}, {-stateChecked}};
        int color1 = color;
        int color2 = Color.parseColor("#6E6E6E");
        int color3 = color;
        int color4 = Color.parseColor("#6E6E6E");
        int[] colors = {color1, color2, color3, color4};
        ColorStateList colorStateList = new ColorStateList(state, colors);
        return colorStateList;
    }
}
