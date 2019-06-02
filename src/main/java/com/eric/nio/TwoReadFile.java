package com.eric.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * �ڶ������Ű���
 * ��nio�ķ�ʽ���ļ�������ļ�����
 * @author Eric
 *
 */
public class TwoReadFile {

	public static void main(String[] args) throws IOException {
		
		FileInputStream fileInputStream = new FileInputStream("TwoNio.txt");
		//��ȡchannelͨ��
		FileChannel channel = fileInputStream.getChannel();
		
		//����һ��512�ռ������
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		//���ļ���������byteBuffer��
		channel.read(byteBuffer);
		
		//���֮ǰ������flipһ��
		byteBuffer.flip();
		
		while(byteBuffer.remaining() > 0){
			byte b = byteBuffer.get();
			System.out.println("char:"+ b);
		}
		
		//�ر���
		fileInputStream.close();
	}

}
