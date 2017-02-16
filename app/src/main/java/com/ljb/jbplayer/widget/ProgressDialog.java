package com.ljb.jbplayer.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ljb.jbplayer.R;

/**
 * Created by Administrator on 2017/2/5.
 */

public class ProgressDialog extends Dialog {

    public ProgressBar progressBar;
    private TextView textView;

    public ProgressDialog(Context context) {
        this(context, R.style.AppTheme_Dialog_ProgressDialog);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.layout_progress_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        textView = (TextView) findViewById(R.id.textview);
    }

    public ProgressDialog setMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            textView.setText(message);
        }
        return this;
    }

    public ProgressDialog setIndeterminateDrawable(Drawable d) {
        progressBar.setIndeterminateDrawable(d);
        return this;
    }
}
