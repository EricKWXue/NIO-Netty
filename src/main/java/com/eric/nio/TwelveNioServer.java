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
 * nio��������ģ�ͣ���������˿ڣ�ȡ����Ϣ��������Ϣ
 * @author Eric
 *
 */
public class TwelveNioServer {

	public static void main(String[] args) throws IOException {
		//���ü����˿ڣ�����5���˿�
		int[] ports = new int[5];
		ports[0] = 5000;
		ports[1] = 5001;
		ports[2] = 5002;
		ports[3] = 5003;
		ports[4] = 5004;
		//��ѡ����
		Selector selector = Selector.open();
		
		for (int i = 0; i < ports.length; i++) {
			//��ȡServerSocketChannelͨ��
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			//�󶨼����˿�
			serverSocketChannel.bind(new InetSocketAddress(ports[i]));
			//ע�ᵽѡ�������¼�Ϊaccept
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		}
		
		//
		while(true){
			//�����������ȴ�ע���¼�����
			int selectedNum = selector.select();
			System.out.println("selectedNum:" + selectedNum);
			
			//��ȡ�������¼�
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			System.out.println("selectedKeys:" + selectedKeys);
			
			//�¼�һ��һ������
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while(iterator.hasNext()){
				SelectionKey selectedKey = iterator.next();
				//����������¼�
				if(selectedKey.isAcceptable()){
					ServerSocketChannel serverChannel = (ServerSocketChannel)selectedKey.channel();
					SocketChannel clientChannel = serverChannel.accept();
					clientChannel.configureBlocking(false);
					
					//ע�ᵽselector��
					clientChannel.register(selector, SelectionKey.OP_READ);
					
					//�����¼����������ɾ�������¼�
					iterator.remove();
					System.out.println("��ÿͻ������ӣ�" + clientChannel);
				}
				//����Ƕ�ȡ�¼�����ʾ����Ϣ����
				else if(selectedKey.isReadable()){
					SocketChannel channel = (SocketChannel)selectedKey.channel();
					//��ȡ����Ϣ����
					int readNum = 0;
					while(true){
						ByteBuffer buffer = ByteBuffer.allocate(512);
						buffer.clear();
						//������
						int read = channel.read(buffer);
						if(read < 0){
							break;
						}
						//��ת
						buffer.flip();
						//д����
						channel.write(buffer);
						readNum += read;
					}
					System.out.println("��ȡ��"+ readNum + ",�����ڣ�" + channel);
					//�������ȡ�¼���ɾ�����¼�
					iterator.remove();
				}
			}
			
			
		}
	}

}
