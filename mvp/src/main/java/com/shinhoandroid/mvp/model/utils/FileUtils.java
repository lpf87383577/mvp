package com.shinhoandroid.mvp.model.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/11/5 10:59
 */
public class FileUtils {

    public static void save2File(InputStream in, File file) throws IOException {

        FileOutputStream out = null;
        byte[] buf = new byte[2048 * 10];
        int len;
        try {
            out = new FileOutputStream(file);
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } finally {
            out.flush();
            in.close();
            out.close();
        }
    }
}
