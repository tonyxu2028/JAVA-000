package io.github.kimmking.gateway.outbound.httpclient4;

import io.github.kimmking.gateway.Util.ByteUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static io.github.kimmking.gateway.constant.GatewayConstants.HTTP_HEAD_KEY;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import static org.apache.http.protocol.HTTP.CONN_DIRECTIVE;
import static org.apache.http.protocol.HTTP.CONN_KEEP_ALIVE;

/**
 * HttpClient方式的处理器
 */
public class HttpOutboundHandler {

    private static Logger logger = LoggerFactory.getLogger(HttpOutboundHandler.class);

    private ExecutorService proxyService;
    private CloseableHttpAsyncClient httpclient;

    public HttpOutboundHandler(){
        int cores = threadPoolExecutorConfig();
        IOReactorConfig ioReactorConfig = ioReactorConfig(cores);
        httpClientConfig(ioReactorConfig);
    }

    /**
     * 线程池配置
     * @return
     */
    private int threadPoolExecutorConfig(){
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000;
        int queueSize = 2048;
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepAliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);
        return cores;
    }

    /**
     * io反应器配置
     * @param cores
     * @return
     */
    private IOReactorConfig ioReactorConfig(int cores){
        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000000)
                .setSoTimeout(1000000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();
        return ioConfig;
    }

    /**
     * HttpClient配置
     * @param ioConfig
     */
    private void httpClientConfig(IOReactorConfig ioConfig){
        httpclient = HttpAsyncClients.custom().setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy((response,context) -> 6000)
                .build();
        httpclient.start();
    }

    /**
     * fullRequest请求发出的处理方法
     * @param fullRequest
     * @param ctx
     * @param url
     */
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx,final String url) {
        proxyService.submit(()->fetchGet(fullRequest, ctx, url+fullRequest.uri()));
    }

    /**
     * HttpGet方式对后端服务进行请求,并且获取后端的response结果
     * @param inbound
     * @param ctx
     * @param url
     */
    private void fetchGet(final FullHttpRequest inbound, final ChannelHandlerContext ctx, final String url) {
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(CONN_DIRECTIVE,CONN_KEEP_ALIVE);
        httpGet.setHeader(HTTP_HEAD_KEY,inbound.headers().get(HTTP_HEAD_KEY));
        httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(final HttpResponse endpointResponse) {
                try {
                    handleResponse(inbound, ctx, endpointResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    
                }
            }
            
            @Override
            public void failed(final Exception ex) {
                httpGet.abort();
                ex.printStackTrace();
            }
            
            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

    /**
     * 后端处理结果反馈方法
     * @param fullRequest
     * @param ctx
     * @param endpointResponse
     * @throws Exception
     */
    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) throws Exception {
        FullHttpResponse response = null;
        try {

            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
            logger.info("body_length ::: "+body.length);
    
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length",
                    Integer.parseInt(endpointResponse.
                            getFirstHeader("Content-Length").getValue()));
        
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
        
    }

    /**
     * HttpClient异常处理方法
     * @param ctx
     * @param cause
     */
    private void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
    
}
