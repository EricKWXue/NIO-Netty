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
 * ���յİ�����һ������ˣ�����ͻ��� ֮���ͨ��
 * 
 * �ͻ��˴��룺
 * 1.���ӷ���ˣ��ܷ�����Ϣ�������
 * 2.�������Է���˵���Ϣ
 * 
 * @author Eric
 */
public class NioClient {

	public static void main(String[] args) throws IOException {
		//��һ��channelͨ��
		SocketChannel socketChannel = SocketChannel.open();
		//���óɷ�������ģʽ
		socketChannel.configureBlocking(false);
		
		//��һ��ѡ����
		Selector selector = Selector.open();
		//��ͨ��ע�ᵽѡ�����ϣ��¼�Ϊ����ʱ��
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		//��������
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
		
		while(true){
			//�����������ռ�����Ȥ���¼�
			selector.select();
			
			//��ȡ�õ��ĸ���Ȥ���¼�
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			for (SelectionKey selectedKey : selectedKeys) {
				if(selectedKey.isConnectable()){
					//����������¼������ȡͨ������
					SocketChannel client = (SocketChannel)selectedKey.channel();
					
					//��ʽ����
					if(client.isConnectionPending()){
						client.finishConnect();
						
						//������Ϣ������ˣ�˵�����Ӻ���
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						byteBuffer.put((client + "������").getBytes());
						byteBuffer.flip();
						client.write(byteBuffer);
						
						//��һ���̣߳�������ʱ�������ݸ������
						ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
						executorService.submit(()->{
							while(true){
								//����һ��buffer
								byteBuffer.clear();
								//�����������ȴ�������������
								InputStreamReader input = new InputStreamReader(System.in);
								
								//����Ϣ����buffer��
								BufferedReader br = new BufferedReader(input);
								String message = br.readLine();
								byteBuffer.put(message.getBytes());
								
								//��ת�󣬷��͵������
								byteBuffer.flip();
								client.write(byteBuffer);
							}
						});
					}
					//��channelע�ᵽselector��ȡ�¼�
					client.register(selector, SelectionKey.OP_READ);
					
				} else if(selectedKey.isReadable()){
					//����Ƕ�ȡ�¼�
					SocketChannel client = (SocketChannel)selectedKey.channel();
					
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					int count = client.read(buffer);
					
					//������Ϣ
					if(count > 0){
						buffer.flip();
						
						String message = new String(buffer.array(), 0, count);
						System.out.println(message);
					}
				}
			}
			//����������selectedKeys
			selectedKeys.clear();
		}

	}

}
