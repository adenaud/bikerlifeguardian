package com.bikerlifeguardian.network;

import android.util.Log;

import com.bikerlifeguardian.LoginResult;
import com.bikerlifeguardian.model.Alert;
import com.bikerlifeguardian.model.UserData;
import com.google.gson.Gson;
import com.google.inject.Singleton;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import roboguice.util.Ln;

@Singleton
public class Api {

    private static final String API_URL = "http://10.133.128.36:3000/";


    public String login(String username, String password) {

       String resultJson = "{}";

        final JSONObject jo = new JSONObject();
        try {
            String hashedPassword = new String(Hex.encodeHex(DigestUtils.md5(password)));

            jo.put("identifier", username);
            jo.put("password", hashedPassword);

            resultJson = post("login", jo.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    public void sendAlert(Alert alert) {
        final String json = new Gson().toJson(alert);
        post("alerts", json);
    }

    public int sendUserData(UserData userData) {
        int code = -1;
        String json = new Gson().toJson(userData);
        String resultJson = put("profile/" + userData.getUuid(), json);
        try {
            JSONObject result = new JSONObject(resultJson);
            code = result.getInt("status");
        } catch (JSONException e) {
            Ln.e(e);
        }
        return code;
    }

    public UserData getUserDatas(String userUuid) {
        UserData userData;
        String json = get("profile/" + userUuid);
        userData = new Gson().fromJson(json,UserData.class);
        return userData;
    }

    private String get(final String api) {
       return execute("GET",api,"");
    }

    private String post(final String api, final String body) {
        return execute("POST",api,body);
    }

    private String put(final String api, final String body) {
        return execute("PUT",api,body);
    }

    public String execute(String method, String api, String body) {
        String result = null;

        try {
            URL url = new URL(API_URL + api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            if (method.equals("POST") || method.equals("PUT") || method.equals("PATCH")) {
                conn.setDoOutput(true);
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(body);
                writer.flush();
                writer.close();
                outputStream.close();
            }
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

    public void drop() {
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
