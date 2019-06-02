package com.eric.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
/**
 * �ڴ�ӳ��buffer���޸�ӳ����ڴ����ݣ���������Ӧ����ͬ��
 * λ�ڶ����ڴ�
 * @author Eric
 *
 */
public class NineMappedBuffer {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile("NineNio.txt","rw");
		//��ȡchannelͨ��
		FileChannel Channel = file.getChannel();
		
		//���ļ�ӳ�䵽�����ڴ��У�ӳ���СΪ�ļ�ǰ5���ֽ�
		MappedByteBuffer mappedByteBuffer = Channel.map(MapMode.READ_WRITE, 0, 5);
		//���ļ���0��λ�õ��ַ���Ϊa
		mappedByteBuffer.put(0, (byte)'a');
		//���ļ���4��λ�õ��ַ���Ϊ5
		mappedByteBuffer.put(4, (byte)'5');
		
		file.close();
	}

}
