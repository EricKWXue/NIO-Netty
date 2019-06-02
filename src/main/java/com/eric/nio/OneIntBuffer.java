package com.eric.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * 第一个入门案例
 * 主要以intBuffer为例
 * @author Eric
 *
 */
public class OneIntBuffer {

	public static void main(String[] args) {
		//初始化10个数组空间
		IntBuffer buffer = IntBuffer.allocate(10);
		
		System.out.println("capacity:" + buffer.capacity());
		
		//往buffer中输入数据
		for(int i=0;i<buffer.capacity();i++){
			int randomInt = new SecureRandom().nextInt(20);
			buffer.put(randomInt);
		}
		
		System.out.println("limit(before flip):" + buffer.limit());
		
		//输出之前必须flip反转，修改position，limit的位置。作用详见源码说明
		buffer.flip();
		
		System.out.println("limit(after flip):" + buffer.limit());
		
		//输出
		while(buffer.hasRemaining()){
			System.out.println("position:" + buffer.position());
			System.out.println("limit:" + buffer.limit());
			System.out.println("capacity:" + buffer.capacity());
			
			System.out.println(buffer.get());
		}

	}

}
