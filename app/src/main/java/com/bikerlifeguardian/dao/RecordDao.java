package com.bikerlifeguardian.dao;

import com.google.inject.Inject;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import com.bikerlifeguardian.model.Record;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordDao {

    private RuntimeExceptionDao<Record,Integer> dao;

    @Inject
    public RecordDao(DatabaseHelper databaseHelper){
        dao = databaseHelper.getRuntimeExceptionDao(Record.class);
    }

    public void save(Record record){
        dao.createOrUpdate(record);
    }

    public List<Record> selectAll(){
        return  dao.queryForAll();
    }

    public List<Record> selectRange(long start, long lenght){

        List<Record> records = new ArrayList<>();

        QueryBuilder<Record,Integer> builder = dao.queryBuilder();
        try {
            builder.offset(start).limit(lenght);
            records = dao.query(builder.prepare());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void drop(){
        dao.executeRawNoArgs("DELETE FROM `record`");
    }

}
