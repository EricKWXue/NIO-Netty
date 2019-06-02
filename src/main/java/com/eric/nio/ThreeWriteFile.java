package com.eric.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ���������Ű���
 * ��nio�ķ�ʽд�ļ���������д���ļ�
 * @author Eric
 *
 */
public class ThreeWriteFile {

	public static void main(String[] args) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream("ThreeNio.txt");
		//��ȡchannelͨ��
		FileChannel channel = fileOutputStream.getChannel();
		
		//����һ��512�ռ������
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		//����������byteBuffer��
		byte[] message = "hello world".getBytes();
		byteBuffer.put(message);
		
		//���֮ǰ������flipһ��
		byteBuffer.flip();
		
		//buffer������������ļ�
		channel.write(byteBuffer);
		//�ر���
		fileOutputStream.close();
	}

}
