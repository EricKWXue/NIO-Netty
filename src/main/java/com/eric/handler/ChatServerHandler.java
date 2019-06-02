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
		//��ȡ��ǰ��ͨ��
		Channel channel = ctx.channel();
		
		cg.forEach(ch -> {
			if(ch != channel){
				//����������߿ͻ���
				ch.writeAndFlush(channel.remoteAddress() + ":" + msg);
			}else{
				//�����Լ��Ŀͻ���
				ch.writeAndFlush("�Լ�:" + msg);
			}
		});
	}

	/**
	 * ����
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//�㲥����֪ͨ
		cg.writeAndFlush(channel.remoteAddress() + "����\n");
	}

	/**
	 * ����
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//�㲥����֪ͨ
		cg.writeAndFlush(channel.remoteAddress() + "����\n");
	}

	/**
	 * �쳣����
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	/**
	 * ����
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//�㲥����֪ͨ
		cg.writeAndFlush("������"+ channel.remoteAddress() + "����\n");
		//����
		cg.add(channel);
	}

	/**
	 * �뿪
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//�㲥�뿪֪ͨ
		cg.writeAndFlush("������"+ channel.remoteAddress() + "�뿪\n");
		//�뿪
		//cg.remove(channel);
	}
	
	

}
