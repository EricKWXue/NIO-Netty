package com.eric.main;

import com.eric.channel.ChatHeartBeatServerChannel;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
/**
 * 心跳检测机制，检查服务端和客户端的链接情况
 * 客户端复用聊天的客户端
 * @author Eric
 *
 */
public class ChatHeartBeatServer {
	public static void main(String[] args) throws Exception {
		//工作组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			//启动器
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).childHandler(new ChatHeartBeatServerChannel());
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
