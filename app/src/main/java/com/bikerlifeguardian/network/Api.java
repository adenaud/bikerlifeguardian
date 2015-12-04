package com.bikerlifeguardian.network;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Api {

    private static final String API_URL = "http://10.133.128.36:3000/api/";

    public static void save(final String jsonData){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(API_URL + "records");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);

                    OutputStream outputStream = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    writer.write(jsonData);
                    writer.flush();
                    writer.close();
                    outputStream.close();

                    int code = conn.getResponseCode();

                    if(code!= 200){
                        Log.e("API_E", String.valueOf(code));
                    }

                    conn.disconnect();

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }


            }
        },"API_THREAD");
        thread.start();
    }

    public static void drop() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(API_URL + "records");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("DELETE");

                    int code = conn.getResponseCode();

                    if(code!= 200){
                        Log.e("API_E", String.valueOf(code));
                    }

                    conn.disconnect();

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }


            }
        },"API_THREAD");
        thread.start();
    }
}
