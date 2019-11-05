package com.shinhoandroid.mvp.model.update;

/**
 * @author Created by gsd on 2017/11/28.
 * Copyright © 2017 YIMISHIJI. All rights reserved.
 */

public interface ProgressListener {
    /**
     * onProgress
     * @param progress     已经下载或上传字节数
     * @param total        总字节数
     * @param done         是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
