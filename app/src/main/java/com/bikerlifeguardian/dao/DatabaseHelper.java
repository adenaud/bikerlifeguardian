package com.bikerlifeguardian.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bikerlifeguardian.model.UserData;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import com.bikerlifeguardian.model.Record;

@Singleton
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "blg.db";

    @Inject
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Record.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            if(oldVersion == 1 && newVersion == 2){
                TableUtils.createTable(connectionSource, UserData.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
