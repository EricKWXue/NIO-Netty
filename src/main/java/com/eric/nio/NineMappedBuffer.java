package com.eric.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
/**
 * 内存映射buffer，修改映射的内存内容，磁盘中相应的做同步
 * 位于堆外内存
 * @author Eric
 *
 */
public class NineMappedBuffer {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile("NineNio.txt","rw");
		//获取channel通道
		FileChannel Channel = file.getChannel();
		
		//将文件映射到堆外内存中，映射大小为文件前5个字节
		MappedByteBuffer mappedByteBuffer = Channel.map(MapMode.READ_WRITE, 0, 5);
		//将文件第0个位置的字符改为a
		mappedByteBuffer.put(0, (byte)'a');
		//将文件第4个位置的字符改为5
		mappedByteBuffer.put(4, (byte)'5');
		
		file.close();
	}

}
