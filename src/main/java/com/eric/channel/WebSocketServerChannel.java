package com.eric.channel;

import com.eric.handler.WebSocketServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServerChannel extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ChannelPipeline channelPipeline = ch.pipeline();
		//将请求和应答消息解码为HTTP消息
		channelPipeline.addLast(new HttpServerCodec());
		//分块写处理，文件过大会将内存撑爆
		channelPipeline.addLast(new ChunkedWriteHandler());
		//请求消息聚合处理
		channelPipeline.addLast(new HttpObjectAggregator(8192));
		//用于处理websocket, /wsocket为访问websocket时的uri
		channelPipeline.addLast(new WebSocketServerProtocolHandler("/wsocket"));
		//自己的处理类
		channelPipeline.addLast(new WebSocketServerHandler());
	}

}
