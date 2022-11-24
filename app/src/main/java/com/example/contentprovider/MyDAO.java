package com.example.contentprovider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class MyDAO {
    private SQLiteDatabase database;
    private SQLiteOpenHelper myopenhelper;
    private Context context;

    private Uri uri=Uri.parse("content://wcc.provider1");  //.../person/10  指的是person表第10行

    public MyDAO(Context context){
        this.context=context;
        myopenhelper=new MyDBhelper(context,"wccDB",null,1);
        database=myopenhelper.getReadableDatabase();

        database.execSQL("drop table if exists person");
        database.execSQL("create table person(id integer primary key autoincrement,"+" name varchar, age integer)");
    }

    public Uri addvalue(Uri uri, ContentValues values){
        long rowID=database.insert("person",null,values);

        if(rowID == -1){
            Log.d("DAO","数据插入失败");
            return  null;
        }
        else {
            Uri insertUri= ContentUris.withAppendedId(uri,rowID);
            Log.d("wcc","ContentUris:"+insertUri.toString());
            context.getContentResolver().notifyChange(insertUri,null);
            return insertUri;
        }
    }
}
