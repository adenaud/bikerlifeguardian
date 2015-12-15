package com.bikerlifeguardian.service;


import com.bikerlifeguardian.LoginResult;
import com.bikerlifeguardian.model.UserData;
import com.bikerlifeguardian.network.Api;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.json.JSONException;
import org.json.JSONObject;

import roboguice.util.Ln;

@Singleton
public class UserService {

    @Inject
    private Api api;

    public LoginResult login(String identifier, String password){
        String  jsonString = api.login(identifier, password);
        LoginResult result = new LoginResult("",-1);
        try {
            JSONObject json = new JSONObject(jsonString);
            int code = json.getInt("status");
            if(code == 0){
                result.setUuid(json.getString("uuid"));
            }
            result.setCode(code);
        }
        catch (JSONException e){
            Ln.e(e);
        }
        return result;
    }

    public int saveProfile(UserData userData){
        return api.sendUserData(userData);
    }

    public UserData getProfile(String userUuid){
        return api.getUserDatas(userUuid);
    }
}
