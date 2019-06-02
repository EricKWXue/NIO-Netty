package com.eric.bio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
/**
 * ����˴�����һ���ͻ���socket�����ݽ���
 * @author Eric
 *
 */
public class IoThread implements Runnable {
	/**
	 * ��ͻ������ӵ�socket
	 */
	private Socket socket;
	
	public IoThread(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream inputStream = socket.getInputStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			
			byte[] buffer = new byte[1024];
			int count = 0;
			while((count = bufferedInputStream.read(buffer)) != 0){
				String message = new String(buffer);
				System.out.println(socket + ":" + message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
