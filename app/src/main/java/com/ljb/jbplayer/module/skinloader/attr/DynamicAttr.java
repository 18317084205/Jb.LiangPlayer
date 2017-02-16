package com.ljb.jbplayer.module.skinloader.attr;

public class DynamicAttr {
    public String attrName;

    /**
     * resource id from default context , eg: "R.drawable.app_bg"
     */
    public int refResId;

    public DynamicAttr(String attrName, int refResId) {
        this.attrName = attrName;
        this.refResId = refResId;
    }
}
