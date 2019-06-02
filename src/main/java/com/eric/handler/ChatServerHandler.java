package com.eric.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String>{
	
	private static ChannelGroup cg = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		//获取当前的通道
		Channel channel = ctx.channel();
		
		cg.forEach(ch -> {
			if(ch != channel){
				//发给别的在线客户端
				ch.writeAndFlush(channel.remoteAddress() + ":" + msg);
			}else{
				//发给自己的客户端
				ch.writeAndFlush("自己:" + msg);
			}
		});
	}

	/**
	 * 上线
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//广播上线通知
		cg.writeAndFlush(channel.remoteAddress() + "上线\n");
	}

	/**
	 * 下线
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//广播上线通知
		cg.writeAndFlush(channel.remoteAddress() + "下线\n");
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
		//广播加入通知
		cg.writeAndFlush("服务器"+ channel.remoteAddress() + "加入\n");
		//加入
		cg.add(channel);
	}

	/**
	 * 离开
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//广播离开通知
		cg.writeAndFlush("服务器"+ channel.remoteAddress() + "离开\n");
		//离开
		//cg.remove(channel);
	}
	
	

}
