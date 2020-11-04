package io.github.kimmking.gateway;

import io.github.kimmking.gateway.inbound.HttpInboundServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.kimmking.gateway.constant.GatewayConstants.GATEWAY_NAME;
import static io.github.kimmking.gateway.constant.GatewayConstants.GATEWAY_VERSION;
import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_CURRENT_USE_PORT_NAME;
import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_CURRENT_USE_PORT_VALUE;
import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_SERVER_NAME;
import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_SERVER_URL_8808;

/**
 * GateWayServerApplication 网关启动类
 * http://localhost:8888/api/hello  ==> gateway service
 * http://localhost:8088/api/hello  ==> backend service
 */
public class GateWayServerApplication {

    private static Logger logger = LoggerFactory.getLogger(GateWayServerApplication.class);

    public static void main(String[] args) {

        try {
            String proxyServer = System.getProperty(PROXY_SERVER_NAME,PROXY_SERVER_URL_8808);
            String proxyPort = System.getProperty(PROXY_CURRENT_USE_PORT_NAME,
                    PROXY_CURRENT_USE_PORT_VALUE);

            int port = Integer.parseInt(proxyPort);
            logger.info(GATEWAY_NAME + " " + GATEWAY_VERSION +" starting...");

            // 根据配置参数获取PROXY_CURRENT_HTTP_CLIENT_TYPE 或者 PROXY_CURRENT_NETTY_CLIENT_TYPE
            // 按照这两个不同选取不同的客户端代理实现方式 HTTP客户端 或者NETTY
            String proxyTypeSign = args[0];
            HttpInboundServer inboundServer = new HttpInboundServer(port, proxyServer,
                    proxyTypeSign);

            inboundServer.run();
            logger.info(GATEWAY_NAME + " " + GATEWAY_VERSION +" started at http://localhost:"
                    + port + " for server:" + proxyServer);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

}
