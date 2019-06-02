package com.eric.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.eric.channel.ChatClientChannel;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ChatClient {
	public static void main(String[] args) throws Exception {
		//工作组
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		
		try {
			//启动器
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new ChatClientChannel());
			//连接服务器
			ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
			//获取通道
			Channel channel = channelFuture.channel();
			
			//发送聊天信息
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			while(true){
				channel.writeAndFlush(br.readLine() + "\r\n");
			}
		} finally {
			//关闭工作组
			eventLoopGroup.shutdownGracefully();
		}
	}
}
