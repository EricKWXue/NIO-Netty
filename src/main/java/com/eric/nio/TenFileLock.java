package com.eric.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁的概念
 * @author Eric
 *
 */
public class TenFileLock {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile("TenNio.txt","rw");
		//获取channel通道
		FileChannel Channel = file.getChannel();
		
		//锁前5个，采用共享锁
		FileLock lock = Channel.lock(0, 5, true);
		
		System.out.println("valid:"+ lock.isValid());
		System.out.println("lock type:"+ lock.isShared());
		
		//锁释放
		lock.release();
		file.close();
	}

}
