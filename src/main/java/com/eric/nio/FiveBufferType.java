package com.eric.nio;

import java.nio.ByteBuffer;
/**
 * һ��buffer�п��������������
 * ����
 * ��Ӧ����������밴�����������˳��������������ͻ��������
 * @author Eric
 *
 */
public class FiveBufferType {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(64);
		//����
		buffer.putInt(11);
		buffer.putLong(4891681818L);
		buffer.putDouble(3.1415926);
		buffer.putChar('��');
		buffer.putShort((short)2);
		buffer.putChar('��');
		//��ת
		buffer.flip();
		//���
		System.out.println(buffer.getInt());
		System.out.println(buffer.getLong());
		System.out.println(buffer.getDouble());
		System.out.println(buffer.getChar());
		System.out.println(buffer.getShort());
		System.out.println(buffer.getChar());

	}

}
