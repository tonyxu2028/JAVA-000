package io.github.kimmking.gateway.constant;

/**
 * 网关配置常量类
 * Created on 2020/11/2.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public final class GatewayConstants {

    // GateWay_Info_网关常量配置
    public static final String GATEWAY_NAME = "NIOGateway";
    public static final String GATEWAY_VERSION = "1.0.0";

    // Proxy_Port_Info_网关端口配置
    public static final String PROXY_CURRENT_USE_PORT_NAME = "proxyPort";
    public static final String PROXY_CURRENT_USE_PORT_VALUE = "8888";

    // Proxy_Type_info_网关代理客户端处理方式配置,httpClient 或者 nettyClient
    public static final String PROXY_CURRENT_HTTP_CLIENT_TYPE = "httpClient";
    public static final String PROXY_CURRENT_NETTY_CLIENT_TYPE = "nettyClient";

    // Http_Head_Info_过滤器加入的HEAD配置
    public static final String HTTP_HEAD_KEY = "Nio";
    public static final String HTTP_HEAD_VALUE = "LeoXu";

    // Proxy_Server_Info_后端服务代理配置
    public static final String PROXY_SERVER_NAME = "proxyServer";
    public static final String PROXY_SERVER_URL_8808 = "http://127.0.0.1:8808"; //DEFAULT
    public static final String PROXY_SERVER_URL_8809 = "http://127.0.0.1:8809";
    public static final String PROXY_SERVER_URL_8810 = "http://127.0.0.1:8810";
    public static final String PROXY_SERVER_UNIFY_HOST = "127.0.0.1";

}
