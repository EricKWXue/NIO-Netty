package com.eric.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * 零拷贝的原理
 * 即：一个文件内容拷贝到另一个文件中，原有的实现方式是在堆内创建buffer缓冲区，但是操作系统不能直接访问堆内内存，因此需要先将堆内缓冲区拷贝到堆外内存中，
 * 然后在通过io拷贝到目标文件中。中间有个堆内、堆外内存的拷贝，耗性能。
 * 所谓零拷贝，就是中间不需要做额外的拷贝工作 
 * @author Eric
 *
 */
public class EightDirectBuffer {

	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("FourNioFrom.txt");
		FileOutputStream fileOutputStream = new FileOutputStream("FourNioTo.txt");
		//获取channel通道
		FileChannel inChannel = fileInputStream.getChannel();
		FileChannel outChannel = fileOutputStream.getChannel();
		
		//创建一个DirectBuffer，分配堆外512空间的数组，可以详见源码说明
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
		
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
