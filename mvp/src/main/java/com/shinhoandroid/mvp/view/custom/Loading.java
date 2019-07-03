package com.shinhoandroid.mvp.view.custom;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shinhoandroid.mvp.R;
import com.shinhoandroid.mvp.application.GlideApp;
import com.shinhoandroid.mvp.model.utils.AppUtils;
import com.shinhoandroid.mvp.model.utils.GlideUtils;
import com.shinhoandroid.mvp.view.MainActivity;


/**
 * 加载框的样式
 *
 */

public class Loading extends Dialog {

    public Loading(@NonNull Context context) {
        super(context, R.style.loading_dialog);
        setContentView(R.layout.loading_view);

        ImageView ivLoading = findViewById(R.id.iv_loading);
        GlideApp.with(AppUtils.getAppContext()).asGif().load(R.drawable.loading).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(ivLoading);


        //设置Dialog参数
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }


}
