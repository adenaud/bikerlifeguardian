package com.bikerlifeguardian.network;

import com.google.inject.Singleton;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import roboguice.util.Ln;

public class TcpBlgClient {

    private static final String SERVER_IP = "10.133.129.83";
    private static final int SERVER_PORT = 6000;

    private Socket client;

    private boolean runReceiveThread;


    public TcpBlgClient(){
        client = new Socket();
    }

    public void open(){
        Thread connectionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    client.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
                    runReceiveThread = true;
                    beginReceive();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
        },"CONN_THREAD");
        connectionThread.start();
    }

    private void beginReceive() {
        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                receive();
            }
        },"REC_THREAD");
        receiveThread.start();
    }

    private void receive() {
        if (runReceiveThread) {
            InputStream inputStream;
            try {
                inputStream = client.getInputStream();
                String result = IOUtils.toString(inputStream);
                Ln.d("RESULT : " + result);
                receive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(byte[] data) throws IOException {
        if (client != null && client.isConnected()) {
            OutputStream outputStream = client.getOutputStream();
            outputStream.write(data);
        } else {
        }
    }
}
