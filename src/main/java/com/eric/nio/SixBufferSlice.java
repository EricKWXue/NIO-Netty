package com.eric.nio;

import java.nio.ByteBuffer;
/**
 * slice buffer��ԭbuffer������ͬ�ĵײ�����
 * @author Eric
 *
 */
public class SixBufferSlice {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		//��������
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte)i);
		}
		//ָ��position��limitλ��
		buffer.position(2);
		buffer.limit(8);
		
		//����slice��������ԭbuffer�����������飩����Դ��˵��
		ByteBuffer sliceBuffer = buffer.slice();
		
		//�޸�slice�ڵ�����
		for (int i = 0; i < sliceBuffer.capacity(); i++) {
			byte b = sliceBuffer.get();
			b += 2;
			sliceBuffer.put(i, b);
		}
		//��ԭλ�ã���ӡ���
		buffer.position(0);
		buffer.limit(buffer.capacity());
		while(buffer.hasRemaining()){
			System.out.println(buffer.get());
		}
	}

}
