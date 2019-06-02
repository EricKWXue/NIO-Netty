package com.eric.channel;

import com.eric.handler.ServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerChannel extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline channelPipeline = ch.pipeline();
		
		channelPipeline.addLast("httpServerCodec", new HttpServerCodec());
		channelPipeline.addLast("serverHandler", new ServerHandler());
	}

}
