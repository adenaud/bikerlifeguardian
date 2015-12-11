package com.bikerlifeguardian.network;

import android.util.Log;

import com.bikerlifeguardian.LoginResult;
import com.bikerlifeguardian.model.Alert;
import com.google.gson.Gson;
import com.google.inject.Singleton;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Singleton
public class Api {

    private static final String API_URL = "http://10.133.128.36:3000/";


    public LoginResult login(String username, String password) {

        LoginResult loginResult = LoginResult.ERROR;

        final JSONObject jo = new JSONObject();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] passwordBytes = md.digest(password.getBytes("UTF-8"));
            String hashedPassword = new String(passwordBytes, "UTF-8");

            jo.put("identifier", username);
            jo.put("password", hashedPassword);

            String resultJson = post("login", jo.toString());
            JSONObject result = new JSONObject(resultJson);

            if (result.getInt("status") == 0) {
                loginResult = LoginResult.LOGIN_OK;
            }

        } catch (JSONException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return loginResult;

    }


    public void sendAlert(Alert alert) {
        final String json = new Gson().toJson(alert);
        post("alerts", json);
    }

    private String post(final String api, final String body) {

        String result = null;

        try {
            URL url = new URL(API_URL + api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(body);
            writer.flush();
            writer.close();
            outputStream.close();

            int code = conn.getResponseCode();

            if (code != 200) {
                Log.e("API_E", String.valueOf(code));
            }

            InputStream is = conn.getInputStream();
            result = IOUtils.toString(is);

            conn.disconnect();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        return result;
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

                    if (code != 200) {
                        Log.e("API_E", String.valueOf(code));
                    }

                    conn.disconnect();

                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }


            }
        }, "API_THREAD");
        thread.start();
    }
}
