package com.eric.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IoClient {

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("localhost", 8899));
	}

}
