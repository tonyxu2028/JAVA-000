package io.github.kimmking.gateway.Util;

/**
 * 字符处理器
 * Created on 2020/11/4.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public class ByteUtil {

    public static String backendUrlConfig(String backendUrl){
        return backendUrl.endsWith("/")?backendUrl.
                substring(0,backendUrl.length()-1):backendUrl;
    }

    public static int getPortByStr(String str){
        return Integer.parseInt(str.substring(str.length()-4));
    }

}
