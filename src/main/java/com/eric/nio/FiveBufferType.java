package com.eric.nio;

import java.nio.ByteBuffer;
/**
 * 一个buffer中可以输入多种类型
 * 但是
 * 相应的输出，必须按照输入的类型顺序进行输出，否则就会出现乱码
 * @author Eric
 *
 */
public class FiveBufferType {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(64);
		//输入
		buffer.putInt(11);
		buffer.putLong(4891681818L);
		buffer.putDouble(3.1415926);
		buffer.putChar('你');
		buffer.putShort((short)2);
		buffer.putChar('我');
		//反转
		buffer.flip();
		//输出
		System.out.println(buffer.getInt());
		System.out.println(buffer.getLong());
		System.out.println(buffer.getDouble());
		System.out.println(buffer.getChar());
		System.out.println(buffer.getShort());
		System.out.println(buffer.getChar());

	}

}
