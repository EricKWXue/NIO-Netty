package com.eric.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * buffer��scattering��gathering
 * 
 * ������˫������������Լ����һ������Э���ʽ
 * @author Eric
 *
 */
public class ElevenScattering {

	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(8899));
		//������Ϣ����Ϊ9���ֽ�
		int messageLength = 2+3+4;
		//������buffer��������Ϣ����С�ֱ�Ϊ2��3��4
		ByteBuffer[] buffers = new ByteBuffer[3];
		buffers[0] = ByteBuffer.allocate(2);
		buffers[1] = ByteBuffer.allocate(3);
		buffers[2] = ByteBuffer.allocate(4);
		//��������
		SocketChannel socketChannel = serverSocketChannel.accept();
		
		while(true){
			int byteRead = 0;
			//������
			while(byteRead < messageLength){
				long r = socketChannel.read(buffers);
				byteRead += r;
				
				System.out.println("byteRead:" + byteRead);
				
				Arrays.asList(buffers).stream().map(buffer -> "position:"+buffer.position()+", limit:"+buffer.limit()).forEach(System.out::println);
			}
			
			//��������ת��׼��Щ����
			Arrays.asList(buffers).forEach(buffer ->{
				buffer.flip();
			});
			
			//д����
			long byteWriteLen = 0;
			while(byteWriteLen < messageLength){
				long r = socketChannel.write(buffers);
				byteWriteLen += r;
			}
			
			//��ջ�����
			Arrays.asList(buffers).forEach(buffer ->{
				buffer.clear();
			});
			
			System.out.println("byteRead" + byteRead + ", byteWriteLen:" + byteWriteLen + ", messageLength:"+messageLength);
		}
	}

}
