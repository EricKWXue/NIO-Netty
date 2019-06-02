package com.eric.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IoServer {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(8899);
		
		//循环接收客户端连接
		while(true){
			//阻塞，直到有客服端连接进来
			Socket socket = serverSocket.accept();
			System.out.println(socket + "连接！");
			
			//启动线程处理这个socket的数据信息
			Thread thread = new Thread(new IoThread(socket));
			thread.start();
		}
	}

}
