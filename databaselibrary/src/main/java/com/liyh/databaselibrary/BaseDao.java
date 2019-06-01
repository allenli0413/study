package com.liyh.databaselibrary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 28 日
 * @time 23 时 07 分
 * @descrip :
 */
public class BaseDao<T> implements IBaseDao<T> {
    private static final String TAG = "BaseDao";
    //持有数据库引用
    private SQLiteDatabase sqLiteDatabase;
    //表名
    private String tableName;
    private Class<T> beanClazz;
    private Map<String, Field> cacheMap;
    private boolean isInited = false;

    public boolean init(SQLiteDatabase sqLiteDatabase, Class<T> beanClazz) {
        this.sqLiteDatabase = sqLiteDatabase;
        this.beanClazz = beanClazz;
        if (!isInited) {
            tableName = this.beanClazz.getAnnotation(DbTable.class).value();
            cacheMap = new ArrayMap<>();
            initCacheMap();
            //创建数据表
            sqLiteDatabase.execSQL(getCreateTableSql());
            return isInited = true;
        }
        return false;
    }

    private void initCacheMap() {
        Field[] declaredFields = beanClazz.getDeclaredFields();
        for (Field field : declaredFields) {
            DbField annotation = field.getAnnotation(DbField.class);
            if (annotation == null) continue;
            String fieldName = annotation.value();
            cacheMap.put(fieldName, field);
        }
    }

    private String getCreateTableSql() {
        //create table if not exists tableName(id integer,name varchar(20))
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists ");
        sb.append(tableName);
        sb.append(" (id integer primary key autoincrement ,");

        Field[] declaredFields = beanClazz.getDeclaredFields();
        for (Field field : declaredFields) {
            Class<?> type = field.getType();
            DbField annotation = field.getAnnotation(DbField.class);
            if (annotation == null) continue;
            String fieldName = annotation.value();
            sb.append(fieldName);
            if (type == String.class) {
                sb.append(" text ,");
            } else if (type == Integer.class) {
                sb.append(" integer ,");
            } else if (type == Long.class) {
                sb.append(" long ,");
            } else {
                continue;
            }
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(" )");
        Log.e(TAG, "getCreateTableSql: " + sb.toString());
        return sb.toString();
    }

    @Override
    public long insert(T bean) {
        Map<String, String> map = getValue(bean);
        ContentValues contentValues = getConentValues(map);
        long insert = sqLiteDatabase.insert(tableName, null, contentValues);
        Log.e(TAG, "insert: 成功插入" + insert + "条数据");
        return insert;
    }

    private ContentValues getConentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            contentValues.put(entry.getKey(), entry.getValue());
        }
        return contentValues;
    }

    private Map<String, String> getValue(T bean) {
        Map<String, String> map = new ArrayMap<>();
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            Field next = iterator.next();
            //打开权限
            next.setAccessible(true);
            try {
                Object o = next.get(bean);
                if (o == null) {
                    continue;
                }
                String value = o.toString();
                String key = next.getAnnotation(DbField.class).value();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                    map.put(key, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public List<T> query() {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tableName, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            long phone = cursor.getLong(cursor.getColumnIndex("phone"));
            Log.e(TAG, "queryAll: id =" + id + ",name = " + name + ",phone = " + phone  );

        }
        return null;
    }
}
