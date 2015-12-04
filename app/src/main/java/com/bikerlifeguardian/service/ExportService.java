package com.bikerlifeguardian.service;

import com.bikerlifeguardian.dao.RecordDao;
import com.bikerlifeguardian.model.Record;
import com.bikerlifeguardian.network.Api;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

@Singleton
public class ExportService {

    @Inject
    private RecordDao recordDao;

    public void drop(){
        recordDao.drop();
        Api.drop();
    }

    public void exportAll(){


        int size = recordDao.selectAll().size();
        long length = 10;
        long start = 0;
        long pages = (long) Math.floor(size / length);


        for(int i = 0; i < pages; i++){
            List<Record> records = recordDao.selectRange(start, length);
            String json =  new Gson().toJson(records);
            Api.save(json);
            start += length;

        }
    }
}
