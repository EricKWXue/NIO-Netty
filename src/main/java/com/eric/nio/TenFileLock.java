package com.eric.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * �ļ����ĸ���
 * @author Eric
 *
 */
public class TenFileLock {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile("TenNio.txt","rw");
		//��ȡchannelͨ��
		FileChannel Channel = file.getChannel();
		
		//��ǰ5�������ù�����
		FileLock lock = Channel.lock(0, 5, true);
		
		System.out.println("valid:"+ lock.isValid());
		System.out.println("lock type:"+ lock.isShared());
		
		//���ͷ�
		lock.release();
		file.close();
	}

}
