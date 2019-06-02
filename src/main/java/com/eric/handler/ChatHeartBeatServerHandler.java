package com.eric.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class ChatHeartBeatServerHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent){
			IdleStateEvent event = (IdleStateEvent)evt;
			
			String state = null;
			switch (event.state()) {
			case READER_IDLE:
				state = "read free";
				break;
			case WRITER_IDLE:
				state = "write free";
				break;
			case ALL_IDLE:
				state = "read and write free";
				break;
			}
			System.out.println(ctx.channel().remoteAddress() + ":" + state);
		}
	}
	

}
