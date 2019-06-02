package com.eric.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 * nio的网络编程模型，监听多个端口，取出信息，返回信息
 * @author Eric
 *
 */
public class TwelveNioServer {

	public static void main(String[] args) throws IOException {
		//设置监听端口，监听5个端口
		int[] ports = new int[5];
		ports[0] = 5000;
		ports[1] = 5001;
		ports[2] = 5002;
		ports[3] = 5003;
		ports[4] = 5004;
		//打开选择器
		Selector selector = Selector.open();
		
		for (int i = 0; i < ports.length; i++) {
			//获取ServerSocketChannel通道
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			//绑定监听端口
			serverSocketChannel.bind(new InetSocketAddress(ports[i]));
			//注册到选择器，事件为accept
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		}
		
		//
		while(true){
			//方法阻塞，等待注册事件发生
			int selectedNum = selector.select();
			System.out.println("selectedNum:" + selectedNum);
			
			//获取发生的事件
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			System.out.println("selectedKeys:" + selectedKeys);
			
			//事件一个一个处理
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while(iterator.hasNext()){
				SelectionKey selectedKey = iterator.next();
				//如果是连接事件
				if(selectedKey.isAcceptable()){
					ServerSocketChannel serverChannel = (ServerSocketChannel)selectedKey.channel();
					SocketChannel clientChannel = serverChannel.accept();
					clientChannel.configureBlocking(false);
					
					//注册到selector上
					clientChannel.register(selector, SelectionKey.OP_READ);
					
					//连接事件处理结束，删除连接事件
					iterator.remove();
					System.out.println("获得客户端连接：" + clientChannel);
				}
				//如果是读取事件，表示有消息过来
				else if(selectedKey.isReadable()){
					SocketChannel channel = (SocketChannel)selectedKey.channel();
					//读取的消息数量
					int readNum = 0;
					while(true){
						ByteBuffer buffer = ByteBuffer.allocate(512);
						buffer.clear();
						//读数据
						int read = channel.read(buffer);
						if(read < 0){
							break;
						}
						//反转
						buffer.flip();
						//写数据
						channel.write(buffer);
						readNum += read;
					}
					System.out.println("读取："+ readNum + ",来自于：" + channel);
					//处理完读取事件，删除该事件
					iterator.remove();
				}
			}
			
			
		}
	}

}
