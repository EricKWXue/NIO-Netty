package com.eric.channel;

import java.util.concurrent.TimeUnit;

import com.eric.handler.ChatHeartBeatServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ChatHeartBeatServerChannel extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline channelPipeline = ch.pipeline();
		
		channelPipeline.addLast(new IdleStateHandler(5, 7, 3, TimeUnit.SECONDS));
		channelPipeline.addLast(new ChatHeartBeatServerHandler());
	}

}
