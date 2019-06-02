package com.eric.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FourTransferFile {

	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("FourNioFrom.txt");
		FileOutputStream fileOutputStream = new FileOutputStream("FourNioTo.txt");
		//获取channel通道
		FileChannel inChannel = fileInputStream.getChannel();
		FileChannel outChannel = fileOutputStream.getChannel();
		
		//分配一个512空间的数组
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		while(true){
			//先清空buffer（实际不清空，只是移动position、limit的位置，以便覆盖）
			byteBuffer.clear();//这行不能少！
			
			//读入数据
			int read = inChannel.read(byteBuffer);
			if(-1 == read){
				break;
			}
			
			//输出之前，必须flip一下
			byteBuffer.flip();
			
			//输出数据
			outChannel.write(byteBuffer);
		}
		
		//关闭流
		fileInputStream.close();
		fileOutputStream.close();

	}

}
