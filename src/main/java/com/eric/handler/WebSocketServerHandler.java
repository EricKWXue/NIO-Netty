package com.eric.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		//��ȡ��ǰ��ͨ��
		Channel channel = ctx.channel();
		channel.writeAndFlush(new TextWebSocketFrame("��������"+ channel.remoteAddress() +"�ظ���" + msg.text()));
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
		//����֪ͨ
		channel.writeAndFlush("������"+ channel.id().asLongText() +":"+ channel.remoteAddress() + "����");
	}

	/**
	 * �뿪
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//�뿪֪ͨ
		channel.writeAndFlush("������"+ channel.id().asLongText() +":"+ channel.remoteAddress() + "�뿪");
	}
	
	

}
