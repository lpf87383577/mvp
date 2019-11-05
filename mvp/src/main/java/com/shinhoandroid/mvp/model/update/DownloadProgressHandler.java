package com.shinhoandroid.mvp.model.update;

import android.os.Looper;
import android.os.Message;



public abstract class DownloadProgressHandler extends ProgressHandler {

    private static final int DOWNLOAD_PROGRESS = 1;
    protected ResponseHandler mHandler = new ResponseHandler(this, Looper.getMainLooper());

    @Override
    protected void sendMessage(ProgressBean progressBean) {
        mHandler.obtainMessage(DOWNLOAD_PROGRESS, progressBean).sendToTarget();

    }

    @Override
    protected void handleMessage(Message message) {

        if (message.what == DOWNLOAD_PROGRESS){
            ProgressBean progressBean = (ProgressBean) message.obj;
            onProgress(progressBean.getBytesRead(), progressBean.getContentLength(), progressBean.isDone());

        }
    }
}
