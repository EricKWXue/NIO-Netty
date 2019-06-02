package com.eric.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * �㿽����ԭ��
 * ����һ���ļ����ݿ�������һ���ļ��У�ԭ�е�ʵ�ַ�ʽ���ڶ��ڴ���buffer�����������ǲ���ϵͳ����ֱ�ӷ��ʶ����ڴ棬�����Ҫ�Ƚ����ڻ����������������ڴ��У�
 * Ȼ����ͨ��io������Ŀ���ļ��С��м��и����ڡ������ڴ�Ŀ����������ܡ�
 * ��ν�㿽���������м䲻��Ҫ������Ŀ������� 
 * @author Eric
 *
 */
public class EightDirectBuffer {

	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("FourNioFrom.txt");
		FileOutputStream fileOutputStream = new FileOutputStream("FourNioTo.txt");
		//��ȡchannelͨ��
		FileChannel inChannel = fileInputStream.getChannel();
		FileChannel outChannel = fileOutputStream.getChannel();
		
		//����һ��DirectBuffer���������512�ռ�����飬�������Դ��˵��
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
		
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
