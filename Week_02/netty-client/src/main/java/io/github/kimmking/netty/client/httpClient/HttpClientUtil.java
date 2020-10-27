package io.github.kimmking.netty.client.httpClient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author tonyxu
 * @version 1.0
 * @date 2020/10/25 14:10
 */
public class HttpClientUtil {

    public static String HttpClientByGet(String serverUrl) {

        String responseStr = "";

        if(serverUrl!=null) {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = null;

            try {
                //执行httpGet请求
                response = httpClient.execute(configHttpGet(serverUrl));
                //System.out.println(response.getStatusLine().getStatusCode());
                //获取响应实体
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    //System.out.println("长度：\t" + httpEntity.getContentLength());
                    responseStr = EntityUtils.toString(httpEntity, "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseStr;
    }

    private static HttpGet configHttpGet(String serverUrl){
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .setSocketTimeout(60000)
                .build();
        HttpGet httpGet = new HttpGet(serverUrl);
        httpGet.setConfig(config);
        return httpGet;
    }

    public static String HttpClientByPost(String serverUrl, Map<String,String> paramMap) {

        String responseStr = "";

        if(serverUrl!=null) {
            HttpPost post = new HttpPost(serverUrl);
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = null;
            try {
                //设置post请求传递参数
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getPostParamList(paramMap));
                post.setEntity(entity);

                //执行请求并处理响应
               response = httpClient.execute(post);
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    responseStr = EntityUtils.toString(httpEntity, "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    httpClient.close();
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return responseStr;
    }

    private static List getPostParamList(Map<String,String> paramMap){
        List<NameValuePair> list = new ArrayList<>();
        if(paramMap!=null&&!paramMap.isEmpty()) {
            for (String key : paramMap.keySet()) {
                list.add(new BasicNameValuePair(key, paramMap.get(key)));
            }
        }
        return list;
    }
}
