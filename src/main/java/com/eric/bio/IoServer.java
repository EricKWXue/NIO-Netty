package com.eric.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IoServer {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8899);
		
		//ѭ�����տͻ�������
		while(true){
			//������ֱ���пͷ������ӽ���
			Socket socket = serverSocket.accept();
			System.out.println(socket + "���ӣ�");
			
			//�����̴߳������socket��������Ϣ
			Thread thread = new Thread(new IoThread(socket));
			thread.start();
		}
	}

}
