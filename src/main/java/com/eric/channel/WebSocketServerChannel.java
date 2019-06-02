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
		//�������Ӧ����Ϣ����ΪHTTP��Ϣ
		channelPipeline.addLast(new HttpServerCodec());
		//�ֿ�д�����ļ�����Ὣ�ڴ�ű�
		channelPipeline.addLast(new ChunkedWriteHandler());
		//������Ϣ�ۺϴ���
		channelPipeline.addLast(new HttpObjectAggregator(8192));
		//���ڴ���websocket, /wsocketΪ����websocketʱ��uri
		channelPipeline.addLast(new WebSocketServerProtocolHandler("/wsocket"));
		//�Լ��Ĵ�����
		channelPipeline.addLast(new WebSocketServerHandler());
	}

}
