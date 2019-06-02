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
 * ���յİ�����һ������ˣ�����ͻ��� ֮���ͨ��
 * 
 * ����˴��룺
 * 1.����8899�˿ڣ���������8899�˿ڵ����пͻ�����������
 * 2.��ȡĳ���ͻ��˷�������Ϣ��������Ϣת����������֮�����Ŀͻ���
 * 
 * @author Eric
 */
public class NioServer {
	
	/**
	 * ��¼ÿ���ͻ��˵�������Ϣ���������˶Զ���ͻ��˷ַ���Ϣ��
	 */
	private static Map<String, SocketChannel> clientMap = new HashMap<>();

	public static void main(String[] args) throws IOException {
		//��һ��channelͨ��
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//���óɷ�������ģʽ
		serverSocketChannel.configureBlocking(false);
		
		//��ȡ����˵�socket����
		ServerSocket serverSocket = serverSocketChannel.socket();
		//�󶨼����˿�
		serverSocket.bind(new InetSocketAddress(8899));
		
		//��һ��ѡ����
		Selector selector = Selector.open();
		//��ͨ��ע�ᵽѡ�����ڣ��¼���Ϊaccept�¼����������¼���������Ӧ��Ŀǰ�ķ����ֻ��עaccept�¼�
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		//��ʼ����
		while(true){
			//�ڴ�������ֱ��������ע���¼�����������accept�¼�
			int num = selector.select();
			
			//�������ߵ���ʱ��˵�����¼��Ѿ��������ˣ���ȡ��ǰselector������channel���¼�
			Set<SelectionKey> selectedKeySet = selector.selectedKeys();
			//����ÿ���¼����ж��¼�����
			selectedKeySet.forEach(selectedKey -> {
				try {
					//����������¼�
					if(selectedKey.isAcceptable()){
						//��ȡ��������¼���channel����Ϊaccept�¼������������ServerSocketChannel�ϵģ����ֱ��ǿת��ȡ��ʵ����
						ServerSocketChannel server = (ServerSocketChannel)selectedKey.channel();
						//���ܿͻ��˵����ӣ���ȡ������
						SocketChannel client = server.accept();
						//������
						client.configureBlocking(false);
						
						//ע��read�¼����󶨵�selector�ϡ�Ŀǰ�Ŀͻ���ֻ��עread�¼�
						client.register(selector, SelectionKey.OP_READ);
						//���������������ַ���Ϣ��
						clientMap.put(UUID.randomUUID().toString(), client);
						
					}else if(selectedKey.isReadable()){
						//�������ݽ�����ʱ�򣬴���read�¼����Ϳɻ�ȡ�ͻ������Ӷ�����
						SocketChannel client = (SocketChannel)selectedKey.channel();
						//����buffer
						ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
						//�����ݶ�ȡ��buffer����
						int count = client.read(byteBuffer);
						
						//������
						if(count > 0){
							//�޸�position��limit��λ��
							byteBuffer.flip();
							
							//����������ӡ��Ϣ������̨
							//Charset charset = Charset.forName("utf-8");
							//String receivedMessage = String.valueOf(charset.decode(byteBuffer).array());
							String receivedMessage = new String(byteBuffer.array(), 0 ,count);
							System.out.println(client + ":" + receivedMessage);
							
							//��ȡ������uuid
							String senderUUID = null;
							for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
								if(entry.getValue() == client){
									senderUUID = entry.getKey();
								}
							}
							
							//��buffer������д�����пͻ�����
							for(Map.Entry<String, SocketChannel> entry : clientMap.entrySet()){
								//��ȡ���пͻ���
								SocketChannel channel = entry.getValue();
								
								//�༭д��ȥ��buffer
								ByteBuffer buffer = ByteBuffer.allocate(1024);
								buffer.put((senderUUID + ": " + receivedMessage).getBytes());
								buffer.flip();
								
								//��bufferд�ؿͻ���
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
