package com.eric.nio;

import java.nio.ByteBuffer;
/**
 * ����һ��ֻ��buffer
 * @author Eric
 *
 */
public class SevenReadOnlyBuffer {
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		//��������
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte)i);
		}
		//����ֻ��buffer
		ByteBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();
		System.out.println(asReadOnlyBuffer.getClass());
		
		//��0λ�õ�Ԫ�ظ�Ϊ2���������쳣��
		asReadOnlyBuffer.position(0);
		asReadOnlyBuffer.put((byte)2);
	}
}
