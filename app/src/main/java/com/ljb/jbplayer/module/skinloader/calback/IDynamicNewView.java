package com.ljb.jbplayer.module.skinloader.calback;

import android.view.View;

import com.ljb.jbplayer.module.skinloader.attr.DynamicAttr;

import java.util.List;

public interface IDynamicNewView {
    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
