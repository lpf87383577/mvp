package com.shinhoandroid.mvp.model.update;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.shinhoandroid.mvp.R;
import com.shinhoandroid.mvp.model.bean.BaseResult;
import com.shinhoandroid.mvp.model.bean.YmConstants;
import com.shinhoandroid.mvp.model.bean.YmUpgradeObj;
import com.shinhoandroid.mvp.model.exception.ApiException;
import com.shinhoandroid.mvp.model.global.ObservableHandler;
import com.shinhoandroid.mvp.model.subscriber.SubscriberNet;
import com.shinhoandroid.mvp.model.utils.ApiService;
import com.shinhoandroid.mvp.model.utils.AppUtils;
import com.shinhoandroid.mvp.model.utils.CollectionUtils;
import com.shinhoandroid.mvp.model.utils.FileUtils;
import com.shinhoandroid.mvp.model.utils.GlideUtils;
import com.shinhoandroid.mvp.model.utils.L;
import com.shinhoandroid.mvp.model.utils.NetUtils;
import com.shinhoandroid.mvp.model.utils.RetrofitUtils;
import com.shinhoandroid.mvp.model.utils.ToastUtils;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 更新APP
 */

public class AppUpdateManager {

    public AppUpdateManager setContext(Context context) {
        mCurrentContext = context;
        return this;
    }

    private Context mCurrentContext;

    private static AppUpdateManager instance;


    /**
     * 下载进度
     */
    public static final int MSG_DOWNLOAD_PROGRESS = 0;
    /**
     *下载成功
     */
    public static final int MSG_DOWNLOAD_SUCCESS = 1;
    /**
     *下载出错
     */
    public static final int MSG_DOWNLOAD_FAILTURE = -1;
    /**
     *下载取消
     */
    public static final int MSG_DOWNLOAD_CANCEL = -2;
    /**
     *handler用到的key
     */
    public static final String KEY_FILE = "apk_file";
    public static final String KEY_PROGRESS = "download_progress";
    public static final String KEY_TOTAL = "download_total";

    /**
     *下载连接
     */
    private String mDownloadUrl = null;
    /**
     *下载进度对话框
     */
    private AlertDialog mProgressDialog;
    private ProgressBar mProgressBar;
    private TextView mTvProgressPrecent;

    /**
     *更新提示弹窗
     */
    private Dialog mUpdateDialog;
    private boolean mAutoUpdateInWifi = true;

    //前台下载(显示进度条)
    private boolean isForegroundDownload = false;

    public static AppUpdateManager getInstance() {
        if (instance == null) {
            synchronized (AppUpdateManager.class) {
                instance = new AppUpdateManager();
            }
        }
        return instance;
    }

    /**
     * 开始版本检测
     *
     * @param isActive true 主动检查版本 false 背后检查版本
     */
    public void checkAppVersion(final boolean isActive) {
        //网络请求检测版本
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(YmConstants.PLATFORM, YmConstants.ANDROID);
        queryMap.put(YmConstants.VERSION, "15");
        queryMap.put(YmConstants.FIRSTRUN, true + "");

        Observable<BaseResult<YmUpgradeObj>> observable = RetrofitUtils.retrofit().create(ApiService.class).getUpgrade(queryMap);

        ObservableHandler.handle(observable).subscribe(new SubscriberNet<BaseResult<YmUpgradeObj>>() {
            @Override
            public void onSuccess(BaseResult<YmUpgradeObj> tResponse) {
                if (!CollectionUtils.isEmpty(tResponse.results)) {
                    YmUpgradeObj upgradeObj = tResponse.results.get(0);
                    onVersionCheckSuc(upgradeObj, isActive);
                }
            }

            @Override
            public void onFailture(ApiException e) {

            }
        });
    }

    /**
     * 版本检测请求成功处理
     *
     * @param upgradeObj
     * @param isActive
     */
    public void onVersionCheckSuc(YmUpgradeObj upgradeObj, boolean isActive) {

        mDownloadUrl = upgradeObj.appUrl;

        //不需要更新
        if (TextUtils.equals(upgradeObj.action, YmConstants.UPGRADE_ACTION_NONE)) {
            //如果是手动检测,给toast提示
            if (isActive) {
                ToastUtils.show("当前已经是最新版本");
            }
        } else if (TextUtils.equals(upgradeObj.action, YmConstants.UPGRADE_ACTION_HAVETO)) {
            //强制更新
            if (NetUtils.isWifi()) {
                //如果是手动检测,给toast提示
                if (isActive) {
                    ToastUtils.show("开始更新");
                }
                bindUpdateService();
                isForegroundDownload = true;
            } else {
                showUpgradeDialog(upgradeObj);
            }
            //每次显示更新
        } else if (TextUtils.equals(upgradeObj.action, YmConstants.UPGRADE_ACTION_SHOW_EVERYTIME)) {
            //如果是手动检测,给更新弹窗
            if (isActive) {
                showUpgradeDialog(upgradeObj);
                return;
            }
            if (mAutoUpdateInWifi && NetUtils.isWifi()) {
                bindUpdateService();
            } else {
                showUpgradeDialog(upgradeObj);
            }
            //选择更新
        } else if (TextUtils.equals(upgradeObj.action, YmConstants.UPGRADE_ACTION_OPTIONAL)) {
            //如果是手动检测,给更新弹窗
            if (isActive) {
                showUpgradeDialog(upgradeObj);
                return;
            }
            if (mAutoUpdateInWifi && NetUtils.isWifi()) {
                bindUpdateService();
                return;
            }

            showUpgradeDialog(upgradeObj);
        }
    }

    /**
     * 初始化ProgressDialog
     */
    private void initProgressDialog() {

        if (mProgressDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mCurrentContext, R.style.CustomAlertDialogStyle);
            mProgressDialog = builder.create();

            mProgressDialog.setTitle("日尝食食正在更新");

            LayoutInflater layoutInflater = mProgressDialog.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.view_download_progress, null, false);

            mProgressBar = ButterKnife.findById(view, R.id.progress_bar);
            mTvProgressPrecent = ButterKnife.findById(view, R.id.tv_progress_precent);

            mProgressDialog.setView(view);
            mProgressDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消下载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    cancelDownload();
                }
            });

            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 用来显示ui的Handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                // 更新进度
                case MSG_DOWNLOAD_PROGRESS:
                    updateProgress(msg);
                    break;
                // 下载成功
                case MSG_DOWNLOAD_SUCCESS:
                    handleDownloadSuccess(msg);
                    break;
                //下载失败
                case MSG_DOWNLOAD_FAILTURE:
                    handleDownloadFailture(msg);
                    break;
                //下载取消
                case MSG_DOWNLOAD_CANCEL:
                    handleDownloadCancel(msg);
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 更新下载进度
     *
     * @param msg
     */
    private void updateProgress(Message msg) {
        if (!isForegroundDownload) {
            return;
        }
        if (mCurrentContext==null){
            return;
        }
        initProgressDialog();

            // 得到下载进度百分比
            long progress = msg.getData().getLong(AppUpdateManager.KEY_PROGRESS);
            long total = msg.getData().getLong(AppUpdateManager.KEY_TOTAL);
            // 设置progress进度
            mProgressBar.setMax((int) (total / 1024));
            mProgressBar.setProgress((int) (progress / 1024));
            int percentNum = (int) (progress * 100 / total);
            mTvProgressPrecent.setText(percentNum + "%");
            mProgressDialog.show();

    }

    /**
     * 下载成功
     *
     * @param msg
     */
    private void handleDownloadSuccess(Message msg) {
        if (mCurrentContext==null){
            return;
        }
        File apkFile = (File) msg.getData().get(AppUpdateManager.KEY_FILE);
        AppUtils.installApk(mCurrentContext, apkFile);
        if (mProgressDialog != null && mProgressDialog.isShowing() ) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 下载失败
     *
     * @param msg
     */
    private void handleDownloadFailture(Message msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 下载取消
     *
     * @param msg
     */
    private void handleDownloadCancel(Message msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    private void bindUpdateService() {

        startDownloadTask(mDownloadUrl);
    }



    /**
     * 显示更新提示弹窗
     *
     * @param upgradeObj
     */
    private void showUpgradeDialog(YmUpgradeObj upgradeObj) {

        if (mCurrentContext==null){
            return;
        }
        if (mUpdateDialog == null) {
            mUpdateDialog = new Dialog(mCurrentContext, R.style.Custom_Dialog) {
                @Override
                public void dismiss() {
                    super.dismiss();
                    mUpdateDialog = null;
                }
            };
            View contentView = LayoutInflater.from(mCurrentContext).inflate(R.layout.view_app_update_window, null);
            Button btnCancel = ButterKnife.findById(contentView, R.id.btn_cancel);
            Button btnUpdate = ButterKnife.findById(contentView, R.id.btn_update);
            ImageView ivUpdateImg = ButterKnife.findById(contentView, R.id.iv_update_img);
            TextView tvUpdateTitle = ButterKnife.findById(contentView, R.id.tv_update_title);
            TextView tvUpdateDesc = ButterKnife.findById(contentView, R.id.tv_update_desc);
            final boolean mustUpgrade = upgradeObj.isMustUpgrade();
            String imgId = upgradeObj.image;
            String title = upgradeObj.title;
            String desc = upgradeObj.desc;
            String btnCancelText = upgradeObj.cancelBtn;
            String btnConfirmText = upgradeObj.confirmBtn;

            Window window = mUpdateDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            DisplayMetrics displayMetrics = mCurrentContext.getResources().getDisplayMetrics();
            int windowWidth = displayMetrics.widthPixels;
            //强制更新
            if (mustUpgrade) {
                btnCancel.setVisibility(View.GONE);
                mUpdateDialog.setCancelable(false);
            }
            if (!TextUtils.isEmpty(title)) {
                tvUpdateTitle.setText(title);
            }
            if (!TextUtils.isEmpty(desc)) {
                tvUpdateDesc.setText(desc);
            }
            if (!TextUtils.isEmpty(btnCancelText)) {
                btnCancel.setText(btnCancelText);
            }
            if (!TextUtils.isEmpty(btnConfirmText)) {
                btnUpdate.setText(btnConfirmText);
            }
            //取消
            RxView.clicks(btnCancel)
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            mUpdateDialog.dismiss();
                        }
                    });
            //更新
            RxView.clicks(btnUpdate)
                    .throttleFirst(500, TimeUnit.MICROSECONDS)
                    .subscribe(new Action1<Void>() {
                        @Override
                        public void call(Void aVoid) {
                            if (!NetUtils.isNetworkAvailable()) {
                                ToastUtils.show("网络不可用");
                            } else if (NetUtils.isWifi()) {
                                isForegroundDownload = true;
                                bindUpdateService();
                            } else {
                                showNotWifiDownloadDialog();//4G网络下载
                            }
                            if (!mustUpgrade) {
                                mUpdateDialog.dismiss();
                            }
                        }
                    });
            mUpdateDialog.setCanceledOnTouchOutside(false);
            mUpdateDialog.setContentView(contentView, new ViewGroup.MarginLayoutParams((int) (windowWidth * 0.8), ViewGroup.MarginLayoutParams.WRAP_CONTENT));
        }
        if (mUpdateDialog != null && !mUpdateDialog.isShowing()) {
            mUpdateDialog.show();
        }
    }

    private void showNotWifiDownloadDialog() {
        final AlertDialog.Builder builer = new AlertDialog.Builder(mCurrentContext);
        builer.setTitle("下载新版本");
        builer.setMessage("检查到您的网络处于非wifi状态,下载新版本将消耗一定的流量,是否继续下载?");
        builer.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builer.setPositiveButton("继续下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                isForegroundDownload = true;
                bindUpdateService();
            }
        });
        builer.setCancelable(false);
        builer.show();
    }



    private File mOutputFile;

    private Subscription mDownloadSubscription;

    private int mDownloadPrecentage = 0;

    /**
     * 开始下载任务
     *
     * @param downloadUrl
     */
    public void startDownloadTask(String downloadUrl) {
        mOutputFile = AppUtils.getApkFile(YmConstants.APK_NAME);

        mDownloadSubscription = download(downloadUrl, mOutputFile, new DownloadProgressHandler() {
            @Override
            protected void onProgress(long progress, long total, boolean done) {
                L.e("lpf--"+progress+"--"+total);
                int precentage = (int) ((progress * 100 / total));
                if (mDownloadPrecentage != precentage) {
                    mDownloadPrecentage = precentage;
                    sendDownloadProgress(progress, total);
                }
                if (progress == total) {
                    L.e("lpf--下载完成");
                    sendDownloadResult(AppUpdateManager.MSG_DOWNLOAD_SUCCESS, mOutputFile);
                }
            }
        });
    }

    /**
     * 通过handle通知消息
     * (下载进度更新)
     *
     * @param progress
     * @param total
     */
    private void sendDownloadProgress(long progress, long total) {
        Message msg = new Message();
        msg.what = AppUpdateManager.MSG_DOWNLOAD_PROGRESS;
        Bundle bundle = new Bundle();
        bundle.putLong(AppUpdateManager.KEY_PROGRESS, progress);
        bundle.putLong(AppUpdateManager.KEY_TOTAL, total);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    /**
     * 通过handle通知消息
     *
     * @param msgWhat
     */
    private void sendDownloadResult(int msgWhat, File file) {
        Message msg = new Message();
        msg.what = msgWhat;
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppUpdateManager.KEY_FILE, file);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    /*
    * 取消下载
    * */
    public void cancelDownload() {
        if (mDownloadSubscription != null && !mDownloadSubscription.isUnsubscribed()) {
            mDownloadSubscription.unsubscribe();
        }
        sendDownloadResult(AppUpdateManager.MSG_DOWNLOAD_CANCEL, mOutputFile);
    }


    public static Subscription download(String url, final File file, DownloadProgressHandler handler) {

        Subscription subscribe = RetrofitUtils.getDownloadProgressRetrofit(handler)
                .create(ApiService.class)
                .apkDownload(url)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        try {
                            FileUtils.save2File(inputStream, file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InputStream>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(InputStream inputStream) {

                    }
                });
        return subscribe;
    }

}
