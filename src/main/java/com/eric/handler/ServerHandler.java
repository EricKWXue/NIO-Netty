package com.eric.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class ServerHandler extends SimpleChannelInboundHandler<HttpObject>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		//ÄÚÈÝ
		ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
		
		//ÏìÓ¦
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
		
		ctx.writeAndFlush(response);
	}

}
