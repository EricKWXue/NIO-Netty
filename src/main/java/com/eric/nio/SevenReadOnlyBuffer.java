package com.eric.nio;

import java.nio.ByteBuffer;
/**
 * 创建一个只读buffer
 * @author Eric
 *
 */
public class SevenReadOnlyBuffer {
	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		//输入数据
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte)i);
		}
		//生成只读buffer
		ByteBuffer asReadOnlyBuffer = buffer.asReadOnlyBuffer();
		System.out.println(asReadOnlyBuffer.getClass());
		
		//将0位置的元素改为2（绝壁抛异常）
		asReadOnlyBuffer.position(0);
		asReadOnlyBuffer.put((byte)2);
	}
}
