package com.shinhoandroid.mvp.model.update;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by gsd on 2017/11/28.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

abstract class ProgressHandler {

    /**
     * sendMessage
     * @param progressBean
     */
    protected abstract void sendMessage(ProgressBean progressBean);

    /**
     * handleMessage
     * @param message
     */
    protected abstract void handleMessage(Message message);

    /**
     * onProgress
     * @param progress
     * @param total
     * @param done
     */
    protected abstract void onProgress(long progress, long total, boolean done);

    protected static class ResponseHandler extends Handler {

        private ProgressHandler mProgressHandler;

        public ResponseHandler(ProgressHandler mProgressHandler, Looper looper) {
            super(looper);
            this.mProgressHandler = mProgressHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            mProgressHandler.handleMessage(msg);
        }
    }
}

