package com.eric.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * buffer的scattering和gathering
 * 
 * 场景：双方交换数据所约定的一种特殊协议格式
 * @author Eric
 *
 */
public class ElevenScattering {

	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8899));
		//假设消息长度为9个字节
		int messageLength = 2+3+4;
		//用三个buffer来接收消息，大小分别为2、3、4
		ByteBuffer[] buffers = new ByteBuffer[3];
		buffers[0] = ByteBuffer.allocate(2);
		buffers[1] = ByteBuffer.allocate(3);
		buffers[2] = ByteBuffer.allocate(4);
		//接收连接
		SocketChannel socketChannel = serverSocketChannel.accept();
		
		while(true){
			int byteRead = 0;
			//读数据
			while(byteRead < messageLength){
				long r = socketChannel.read(buffers);
				byteRead += r;
				
				System.out.println("byteRead:" + byteRead);
				
				Arrays.asList(buffers).stream().map(buffer -> "position:"+buffer.position()+", limit:"+buffer.limit()).forEach(System.out::println);
			}
			
			//缓冲区反转，准备些数据
			Arrays.asList(buffers).forEach(buffer ->{
				buffer.flip();
			});
			
			//写数据
			long byteWriteLen = 0;
			while(byteWriteLen < messageLength){
				long r = socketChannel.write(buffers);
				byteWriteLen += r;
			}
			
			//清空缓冲区
			Arrays.asList(buffers).forEach(buffer ->{
				buffer.clear();
			});
			
			System.out.println("byteRead" + byteRead + ", byteWriteLen:" + byteWriteLen + ", messageLength:"+messageLength);
		}
	}

}
