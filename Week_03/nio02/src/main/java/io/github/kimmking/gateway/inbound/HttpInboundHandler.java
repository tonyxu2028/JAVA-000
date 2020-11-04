package io.github.kimmking.gateway.inbound;

import io.github.kimmking.gateway.Util.ByteUtil;
import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.github.kimmking.gateway.filter.HttpRequestFilterHandler;
import io.github.kimmking.gateway.outbound.httpclient4.HttpOutboundHandler;
import io.github.kimmking.gateway.outbound.netty4.NettyHttpClient;
import io.github.kimmking.gateway.router.HttpEndpointRouterHandler;
import io.github.kimmking.gateway.router.LoadRouteConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_CURRENT_HTTP_CLIENT_TYPE;
import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_CURRENT_NETTY_CLIENT_TYPE;
import static io.github.kimmking.gateway.constant.GatewayConstants.PROXY_SERVER_UNIFY_HOST;

/**
 * 网关监听到HTTP请求的处理类
 */
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);

    private final String proxyServer;

    private final String proxyType;

    private HttpOutboundHandler httpOutboundHandler;
    
    public HttpInboundHandler(String proxyServer,String proxyType) {
        this.proxyServer = proxyServer;
        this.proxyType = proxyType;
        setHttpOutboundHandler();
    }

    private void setHttpOutboundHandler(){
            httpOutboundHandler = new HttpOutboundHandler();
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;

            // 网关_过滤器处理
            HttpRequestFilter httpRequestFilter = new HttpRequestFilterHandler();
            httpRequestFilter.filter(fullRequest,ctx);

            // 网关_路由器处理
            String url = ByteUtil.backendUrlConfig(HttpEndpointRouterHandler.getInstance()
                    .route(LoadRouteConfig.getRouteUrls()));

            // 网关_HttpClient方式的调用
            if(proxyType.equals(PROXY_CURRENT_HTTP_CLIENT_TYPE)) {
                httpOutboundHandler.handle(fullRequest, ctx,url);
            }
            // 网关_NettyClient方式的调用
            if(proxyType.equals(PROXY_CURRENT_NETTY_CLIENT_TYPE)){
                try {
                    NettyHttpClient client = new NettyHttpClient();
                    client.connect(PROXY_SERVER_UNIFY_HOST,
                            ByteUtil.getPortByStr(url),fullRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("PROXY_CURRENT_Netty_HTTP_CLIENT_TYPE ::: RUN ");
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
