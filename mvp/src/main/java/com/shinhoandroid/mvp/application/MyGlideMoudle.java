package com.shinhoandroid.mvp.application;


import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public final class MyGlideMoudle extends AppGlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(10*1024*1024));
        super.applyOptions(context, builder);
    }
}
