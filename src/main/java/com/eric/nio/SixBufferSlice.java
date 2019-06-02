package com.eric.nio;

import java.nio.ByteBuffer;
/**
 * slice buffer与原buffer共享相同的底层数组
 * @author Eric
 *
 */
public class SixBufferSlice {

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		//输入数据
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte)i);
		}
		//指定position和limit位置
		buffer.position(2);
		buffer.limit(8);
		
		//创建slice副本（与原buffer共享数据数组），见源码说明
		ByteBuffer sliceBuffer = buffer.slice();
		
		//修改slice内的数据
		for (int i = 0; i < sliceBuffer.capacity(); i++) {
			byte b = sliceBuffer.get();
			b += 2;
			sliceBuffer.put(i, b);
		}
		//还原位置，打印输出
		buffer.position(0);
		buffer.limit(buffer.capacity());
		while(buffer.hasRemaining()){
			System.out.println(buffer.get());
		}
	}

}
