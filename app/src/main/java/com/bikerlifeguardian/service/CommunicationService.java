package com.bikerlifeguardian.service;

import com.bikerlifeguardian.network.Message;
import com.bikerlifeguardian.network.TcpBlgClient;
import com.google.gson.Gson;
import com.google.inject.Singleton;

import java.io.IOException;

@Singleton
public class CommunicationService {

    public void sendData(Message human){


        TcpBlgClient client = new TcpBlgClient();

        String json = new Gson().toJson(human);

        try {
            client.send(json.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
