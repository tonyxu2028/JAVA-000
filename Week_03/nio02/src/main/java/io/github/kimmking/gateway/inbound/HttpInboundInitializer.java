package io.github.kimmking.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 网关HttpInboundInitializer服务
 */
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
	
	private String proxyServer;

	private String proxyType;
	
	public HttpInboundInitializer(String proxyServer,String proxyType) {
		this.proxyServer = proxyServer;
		this.proxyType = proxyType;
	}
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new HttpServerCodec());
		pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
		pipeline.addLast(new HttpInboundHandler(this.proxyServer,this.proxyType));
	}

}
