package com.eric.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * ��һ�����Ű���
 * ��Ҫ��intBufferΪ��
 * @author Eric
 *
 */
public class OneIntBuffer {

	public static void main(String[] args) {
		//��ʼ��10������ռ�
		IntBuffer buffer = IntBuffer.allocate(10);
		
		System.out.println("capacity:" + buffer.capacity());
		
		//��buffer����������
		for(int i=0;i<buffer.capacity();i++){
			int randomInt = new SecureRandom().nextInt(20);
			buffer.put(randomInt);
		}
		
		System.out.println("limit(before flip):" + buffer.limit());
		
		//���֮ǰ����flip��ת���޸�position��limit��λ�á��������Դ��˵��
		buffer.flip();
		
		System.out.println("limit(after flip):" + buffer.limit());
		
		//���
		while(buffer.hasRemaining()){
			System.out.println("position:" + buffer.position());
			System.out.println("limit:" + buffer.limit());
			System.out.println("capacity:" + buffer.capacity());
			
			System.out.println(buffer.get());
		}

	}

}
