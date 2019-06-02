package com.eric.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
/**
 * 最终的案例：一个服务端，多个客户端 之间的通信
 * 
 * 服务端代码：
 * 1.监听8899端口，接受来自8899端口的所有客户端请求连接
 * 2.获取某个客户端发来的消息，并将消息转发到其他与之相连的客户端
 * 
 * @author Eric
 */
public class NioServer {
	
	/**
	 * 记录每个客户端的连接信息，方便服务端对多个客户端分发消息。
	 */
	private static Map<String, SocketChannel> clientMap = new HashMap<>();

	public static void main(String[] args) throws IOException {
		//打开一个channel通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//设置成非阻塞的模式
		serverSocketChannel.configureBlocking(false);
		
		//获取服务端的socket对象
		ServerSocket serverSocket = serverSocketChannel.socket();
		//绑定监听端口
		serverSocket.bind(new InetSocketAddress(8899));
		
		//打开一个选择器
		Selector selector = Selector.open();
		//将通道注册到选择器内，事件绑定为accept事件，即连接事件触发后响应。目前的服务端只关注accept事件
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		//开始监听
		while(true){
			//在此阻塞，直到有它关注的事件被触发，如accept事件
			int num = selector.select();
			
			//当代码走到这时，说明有事件已经被触发了，获取当前selector上所有channel的事件
			Set<SelectionKey> selectedKeySet = selector.selectedKeys();
			//遍历每个事件，判断事件类型
			selectedKeySet.forEach(selectedKey -> {
				try {
					//如果是连接事件
					if(selectedKey.isAcceptable()){
						//获取触发这个事件的channel，因为accept事件就是上面绑定在ServerSocketChannel上的，因此直接强转获取真实类型
						ServerSocketChannel server = (ServerSocketChannel)selectedKey.channel();
						//接受客户端的连接，获取该连接
						SocketChannel client = server.accept();
						//非阻塞
						client.configureBlocking(false);
						
						//注册read事件，绑定到selector上。目前的客户端只关注read事件
						client.register(selector, SelectionKey.OP_READ);
						//存起来，方便后面分发消息用
						clientMap.put(UUID.randomUUID().toString(), client);
						
					}else if(selectedKey.isReadable()){
						//当有数据进来的时候，触发read事件，就可获取客户端连接对象了
						SocketChannel client = (SocketChannel)selectedKey.channel();
						//构建buffer
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						//将数据读取到buffer里面
						int count = client.read(byteBuffer);
						
						//有内容
						if(count > 0){
							//修改position、limit的位置
							byteBuffer.flip();
							
							//解码后，输出打印消息到控制台
							//Charset charset = Charset.forName("utf-8");
							//String receivedMessage = String.valueOf(charset.decode(byteBuffer).array());
							String receivedMessage = new String(byteBuffer.array(), 0 ,count);
							System.out.println(client + ":" + receivedMessage);
							
							//获取发送者uuid
							String senderUUID = null;
							for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
								if(entry.getValue() == client){
									senderUUID = entry.getKey();
								}
							}
							
							//将buffer的内容写回所有客户端中
							for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
								//获取所有客户端
								SocketChannel channel = entry.getValue();
								
								//编辑写回去的buffer
								ByteBuffer buffer = ByteBuffer.allocate(1024);
								buffer.put((senderUUID + ": " + receivedMessage).getBytes());
								buffer.flip();
								
								//将buffer写回客户端
								channel.write(buffer);
							}
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			selectedKeySet.clear();
		}

	}

}
