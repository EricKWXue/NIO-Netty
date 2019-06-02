package com.eric.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		//打印服务端返回的消息
		System.out.println(msg);
	}

}
