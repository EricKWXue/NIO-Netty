package com.eric.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		//获取当前的通道
		Channel channel = ctx.channel();
		channel.writeAndFlush(new TextWebSocketFrame("服务器端"+ channel.remoteAddress() +"回复：" + msg.text()));
	}

	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	/**
	 * 加入
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//加入通知
		channel.writeAndFlush("服务器"+ channel.id().asLongText() +":"+ channel.remoteAddress() + "加入");
	}

	/**
	 * 离开
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//离开通知
		channel.writeAndFlush("服务器"+ channel.id().asLongText() +":"+ channel.remoteAddress() + "离开");
	}
	
	

}
