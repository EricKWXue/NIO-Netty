package com.eric.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FourTransferFile {

	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("FourNioFrom.txt");
		FileOutputStream fileOutputStream = new FileOutputStream("FourNioTo.txt");
		//��ȡchannelͨ��
		FileChannel inChannel = fileInputStream.getChannel();
		FileChannel outChannel = fileOutputStream.getChannel();
		
		//����һ��512�ռ������
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		while(true){
			//�����buffer��ʵ�ʲ���գ�ֻ���ƶ�position��limit��λ�ã��Ա㸲�ǣ�
			byteBuffer.clear();//���в����٣�
			
			//��������
			int read = inChannel.read(byteBuffer);
			if(-1 == read){
				break;
			}
			
			//���֮ǰ������flipһ��
			byteBuffer.flip();
			
			//�������
			outChannel.write(byteBuffer);
		}
		
		//�ر���
		fileInputStream.close();
		fileOutputStream.close();

	}

}
