package io.github.kimmking.netty.client.httpClient;

/**
 * @author tonyxu
 * @version 1.0
 * @date 2020/10/25 14:11
 */
public class HttpClientMain {

    public static void main(String[] args){

        String serverUrl = "http://localhost:8808/test";

        //HttpClient Get
        System.out.println("HttpClient_Get:::"+HttpClientUtil.HttpClientByGet(serverUrl));

        //HttpClient Post
        System.out.println("HttpClient_Post:::"+HttpClientUtil.HttpClientByPost(serverUrl,null));
    }
}
