package io.github.kimmking.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import static io.github.kimmking.gateway.constant.GatewayConstants.HTTP_HEAD_KEY;
import static io.github.kimmking.gateway.constant.GatewayConstants.HTTP_HEAD_VALUE;

/**
 * 过滤器实现类
 * Created on 2020/11/2.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public class HttpRequestFilterHandler implements HttpRequestFilter{
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        HttpHeaders httpHeaders = fullRequest.headers();
        httpHeaders.set(HTTP_HEAD_KEY,HTTP_HEAD_VALUE);
    }
}
