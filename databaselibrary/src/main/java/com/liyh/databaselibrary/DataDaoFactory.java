package com.liyh.databaselibrary;

import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 29 日
 * @time 00 时 27 分
 * @descrip :
 */
public class DataDaoFactory {
    private SQLiteDatabase sqLiteDatabase;;
    private String path;
    private static final DataDaoFactory ourInstance = new DataDaoFactory();

    public static DataDaoFactory getInstance() {
        return ourInstance;
    }

    private DataDaoFactory() {
        path = "/data/data/com.liyh.app/database/lyh.db";
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
        }
        sqLiteDatabase =SQLiteDatabase.openOrCreateDatabase(file,null);
    }

    public <T> BaseDao getBaseDao(Class<T> clazz){
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase, clazz);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return baseDao;
    }

}
