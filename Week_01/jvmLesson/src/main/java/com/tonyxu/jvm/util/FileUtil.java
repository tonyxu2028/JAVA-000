package com.tonyxu.jvm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author Tony.xu
 * @version 1.0
 * @date 2020/10/16 14:26
 */
public class FileUtil {

    public static String fileToBase64(String path) throws IOException {
        InputStream inputStream = null;
        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            int temp = -1;
            int i = 0;
            while ((temp = inputStream.read()) != -1) { // 从输入流中读取
                bytes[i] = (byte) (255-temp);           // 解密写入数组
                i++;
            }
            return Base64.getEncoder().encodeToString(bytes);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
