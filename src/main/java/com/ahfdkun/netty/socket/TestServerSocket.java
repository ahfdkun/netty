package com.ahfdkun.netty.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServerSocket {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out =  new PrintWriter(socket.getOutputStream(), true);

        String request;
        while ((request = in.readLine()) != null) {
            if ("Done".equals(request)) {
                break;
            }
            out.println("test123");
        }
    }

}
