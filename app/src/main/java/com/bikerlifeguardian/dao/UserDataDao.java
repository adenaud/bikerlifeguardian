package com.bikerlifeguardian.dao;

import com.bikerlifeguardian.model.UserData;
import com.google.inject.Inject;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class UserDataDao {

    private RuntimeExceptionDao<UserData,Integer> dao;

    @Inject
    public UserDataDao(DatabaseHelper databaseHelper){
        dao = databaseHelper.getRuntimeExceptionDao(UserData.class);
    }

    public void save(UserData userData){
        dao.createOrUpdate(userData);
    }

    public UserData read(){
        return dao.queryForAll().get(0);
    }
}
