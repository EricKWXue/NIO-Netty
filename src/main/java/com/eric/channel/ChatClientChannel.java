package com.eric.channel;

import com.eric.handler.ChatClientHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class ChatClientChannel extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline channelPipeline = ch.pipeline();
		//·Ö¸ô·û½âÂëÆ÷
		channelPipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
		//×Ö·û±àÂë½âÂëÆ÷
		channelPipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
		//×Ö·û±àÂë±àÂëÆ÷
		channelPipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
		//×Ô¶¨ÒåÀ¹½ØÆ÷
		channelPipeline.addLast(new ChatClientHandler());
	}

}
