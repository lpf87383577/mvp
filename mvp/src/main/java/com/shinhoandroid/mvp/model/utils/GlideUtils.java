package com.shinhoandroid.mvp.model.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.widget.ImageView;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;

import com.bumptech.glide.request.transition.Transition;
import com.shinhoandroid.mvp.R;
import com.shinhoandroid.mvp.application.GlideApp;
import com.shinhoandroid.mvp.application.GlideRequest;

import java.io.File;
public class GlideUtils {

    //各种默认图片的枚举
    public enum HolderType {
        DEFAULT,
        SMALL
    }




    /**
     * 加载图片
     * 通过资源id
     *
     * @param resId
     * @param imageView
     */
    public static void loadImage(@IdRes int resId, ImageView imageView) {
        if (imageView != null) {

            GlideApp.with(AppUtils.getAppContext()).load(resId).into(imageView);
        }
    }

    /**
     * 加载图片
     * 通过File
     *
     * @param file
     * @param imageView
     */
    public static void loadImage(File file, ImageView imageView) {
        if (imageView != null) {
            GlideApp.with(AppUtils.getAppContext()).load(file).into(imageView);
        }
    }

    /**
     * 加载网络图片
     *
     * @param url
     * @param imageView
     */
    public static void loadImage(String url, ImageView imageView) {
        if (imageView != null) {
            GlideApp.with(AppUtils.getAppContext()).load(url).into(imageView);
        }
    }

    /**
     * 加载图片
     * 通过Uri
     *
     * @param uri
     * @param imageView
     */
    public static void loadImage(Uri uri, ImageView imageView) {
        if (imageView != null) {
            GlideApp.with(AppUtils.getAppContext()).load(uri).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(imageView);
        }
    }

    /**
     * 加载网络图片
     * (一般图片)
     *
     * @param url
     * @param imageView
     * @param type
     */
    public static void loadImage(String url, ImageView imageView, HolderType type) {

        //L.e("lpf--"+url);
        if (imageView != null) {
            int placeHolderResId;
            //不同placeholder处理
            switch (type) {
                case DEFAULT:
                    placeHolderResId = R.drawable.ic_success;
                    break;
                case SMALL:
                    placeHolderResId = R.drawable.ic_success;
                    break;

                default:
                    placeHolderResId = R.drawable.ic_success;
                    break;
            }

            GlideRequest<Drawable> glideRequest = GlideApp.with(AppUtils.getAppContext())
                    .load(url);

            //无占位图
            if (placeHolderResId <= 0) {
                glideRequest
                        .into(imageView);
            } else {
                //有占位图
                glideRequest
                        .placeholder(placeHolderResId)
                        .error(placeHolderResId)
                        .priority(Priority.HIGH)
                        .into(imageView);
            }
        }
    }

    /**
     * 加载长图片：将图片下载下来再去加载即可
     * @param url
     * @param imageView
     */
    public static void loadLongImage(String url, final ImageView imageView){

        GlideApp.with(AppUtils.getAppContext())
                .load(url).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File file, Transition<? super File> transition) {

                imageView.setImageURI(Uri.fromFile(file));
            }
        });
    }


    /**
     * 优先级加载图片
     *
     * @param url
     * @param imageView
     * @param priority  优先级
     *                  Priority.LOW/Priority.HIGH
     */
    public void loadImageWithPriority(String url, ImageView imageView, Priority priority) {
        GlideApp.with(AppUtils.getAppContext()).load(url).priority(priority).into(imageView);
    }


    /**
     * 是否禁止磁盘缓存加载图片
     *
     * @param url
     * @param imageView
     * @param type      缓存的类型
     *                  <li>磁盘缓存全部 DiskCacheStrategy.ALL</li>
     *                  <li>磁盘缓存全部 DiskCacheStrategy.NONE</li>
     */
    public static void loadImage(String url, ImageView imageView, DiskCacheStrategy type) {
        GlideApp.with(AppUtils.getAppContext()).load(url).diskCacheStrategy(type).into(imageView);
    }

    /**
     * 是否禁止内存缓存加载图片
     *
     * @param url
     * @param imageView
     * @param skipMemoryCache 禁止内存缓存 true为禁止
     */
    public static void loadImage(String url, ImageView imageView, boolean skipMemoryCache) {
        GlideApp.with(AppUtils.getAppContext()).load(url).skipMemoryCache(skipMemoryCache).into(imageView);
    }

    /**
     * 是否禁止内存/磁盘缓存加载图片
     *
     * @param url
     * @param imageView
     * @param type            缓存的类型
     *                        <li>磁盘缓存全部 DiskCacheStrategy.ALL</li>
     *                        <li>磁盘不缓存 DiskCacheStrategy.NONE</li>
     * @param skipMemoryCache 禁止内存缓存 true为禁止
     */
    public static void loadImage(String url, ImageView imageView, DiskCacheStrategy type, boolean skipMemoryCache) {
        GlideApp.with(AppUtils.getAppContext()).load(url).diskCacheStrategy(type).skipMemoryCache(skipMemoryCache).into(imageView);
    }

    /**
     * 清除内存中的缓存
     * 必须在UI线程中调用
     */
    public void clearMemory() {
        GlideApp.get(AppUtils.getAppContext()).clearMemory();
    }

    /**
     * 清除磁盘中的缓存
     * 必须在后台线程中调用
     * 建议同时clearMemory
     */
    public void clearDiskCache() {
        GlideApp.get(AppUtils.getAppContext()).clearDiskCache();
    }

    /**
     * 预缓存图片(下载大图，通过回调显示出来)
     *
     * @param url
     */
    public static void preload(String url, RequestListener<Drawable> listener) {
        GlideRequest<Drawable> load = GlideApp.with(AppUtils.getAppContext())
                .load(url);
        if (listener != null) {
            load.listener(listener);
        }
        load.submit();
    }


}
