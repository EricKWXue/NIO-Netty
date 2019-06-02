package com.eric.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 第三个入门案例
 * 以nio的方式写文件，将内容写入文件
 * @author Eric
 *
 */
public class ThreeWriteFile {

	public static void main(String[] args) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream("ThreeNio.txt");
		//获取channel通道
		FileChannel channel = fileOutputStream.getChannel();
		
		//分配一个512空间的数组
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		//将内容输入byteBuffer中
		byte[] message = "hello world".getBytes();
		byteBuffer.put(message);
		
		//输出之前，必须flip一下
		byteBuffer.flip();
		
		//buffer中内容输出到文件
		channel.write(byteBuffer);
		//关闭流
		fileOutputStream.close();
	}

}
