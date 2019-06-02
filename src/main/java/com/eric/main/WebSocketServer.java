package com.eric.main;

import com.eric.channel.WebSocketServerChannel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WebSocketServer {
	public static void main(String[] args) throws Exception {
		//工作组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			//启动器
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new WebSocketServerChannel());
			//绑定端口
			ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
			channelFuture.channel().closeFuture().sync();
		} finally {
			//关闭工作组
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
