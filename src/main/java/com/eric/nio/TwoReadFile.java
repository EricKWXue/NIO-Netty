package com.eric.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 第二个入门案例
 * 以nio的方式读文件，输出文件内容
 * @author Eric
 *
 */
public class TwoReadFile {

	public static void main(String[] args) throws IOException {
		
		FileInputStream fileInputStream = new FileInputStream("TwoNio.txt");
		//获取channel通道
		FileChannel channel = fileInputStream.getChannel();
		
		//分配一个512空间的数组
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		//将文件内容输入byteBuffer中
		channel.read(byteBuffer);
		
		//输出之前，必须flip一下
		byteBuffer.flip();
		
		while(byteBuffer.remaining() > 0){
			byte b = byteBuffer.get();
			System.out.println("char:"+ b);
		}
		
		//关闭流
		fileInputStream.close();
	}

}
