package com.eric.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 最终的案例：一个服务端，多个客户端 之间的通信
 * 
 * 客户端代码：
 * 1.连接服务端，能发送消息给服务端
 * 2.接收来自服务端的消息
 * 
 * @author Eric
 */
public class NioClient {

	public static void main(String[] args) throws IOException {
		//打开一个channel通道
		SocketChannel socketChannel = SocketChannel.open();
		//设置成非阻塞的模式
		socketChannel.configureBlocking(false);
		
		//打开一个选择器
		Selector selector = Selector.open();
		//将通道注册到选择器上，事件为连接时间
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		//进行连接
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
		
		while(true){
			//方法阻塞，收集感兴趣的事件
			selector.select();
			
			//获取得到的感兴趣的事件
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			for (SelectionKey selectedKey : selectedKeys) {
				if(selectedKey.isConnectable()){
					//如果是连接事件，则获取通道对象
					SocketChannel client = (SocketChannel)selectedKey.channel();
					
					//正式连接
					if(client.isConnectionPending()){
						client.finishConnect();
						
						//发个消息给服务端，说明连接好了
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						byteBuffer.put((client + "已连接").getBytes());
						byteBuffer.flip();
						client.write(byteBuffer);
						
						//起一个线程，用于随时输入数据给服务端
						ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
						executorService.submit(()->{
							while(true){
								//清理一下buffer
								byteBuffer.clear();
								//方法阻塞，等待控制输入数据
								InputStreamReader input = new InputStreamReader(System.in);
								
								//将消息兑入buffer中
								BufferedReader br = new BufferedReader(input);
								String message = br.readLine();
								byteBuffer.put(message.getBytes());
								
								//反转后，发送到服务端
								byteBuffer.flip();
								client.write(byteBuffer);
							}
						});
					}
					//将channel注册到selector读取事件
					client.register(selector, SelectionKey.OP_READ);
					
				} else if(selectedKey.isReadable()){
					//如果是读取事件
					SocketChannel client = (SocketChannel)selectedKey.channel();
					
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					int count = client.read(buffer);
					
					//处理消息
					if(count > 0){
						buffer.flip();
						
						String message = new String(buffer.array(), 0, count);
						System.out.println(message);
					}
				}
			}
			//处理完后，清除selectedKeys
			selectedKeys.clear();
		}

	}

}
