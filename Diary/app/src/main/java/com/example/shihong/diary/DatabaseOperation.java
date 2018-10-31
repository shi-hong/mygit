package com.example.shihong.diary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DatabaseOperation {
    private SQLiteDatabase db;
    private Context context;
    public DatabaseOperation(Context context,SQLiteDatabase db) {
        this.context = context;
        this.db =db;
    }
    //数据库的打开或创建
    public void create_db(){
        //创建或打开数据库
        db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().toString()+"/Diary.db3", null);
        if(db == null){
            Toast.makeText(context,"数据库创建不成功",Toast.LENGTH_LONG).show();
        }
        //创建表
        db.execSQL("create table if not exists diary(id integer primary key autoincrement,title text,context text,time varchar(20),imagepath text)");
    }

    public void insert_db(String title, String text, String time, String path){
        db.execSQL("insert into diary(title,context,time,imagepath) values('"+ title + "','" + text + "','" + time + "','" + path +"');");
    }

    public void update_db(String title,String text,String time,String path, int id){
        db.execSQL("update diary set context = '" + text + "',title = '" + title + "',time = '" + time + "',imagepath = '" + path + "' where id= '" + id + "'");
    }

    public Cursor query_db(){
        Cursor cursor = db.rawQuery("select * from diary order by time desc",null);
        return cursor;
    }

    public Cursor query_db(int id){
        Cursor cursor = db.rawQuery("select * from diary where id='"+id+"';",null);
        return cursor;
    }

    public Cursor query_db(String text){
        Cursor cursor = db.rawQuery("select * from diary where context like '%"+text+"%';",null);
        return cursor;
    }

    public void delete_db(int id){
        db.execSQL("delete from diary where id = '" + id + "'");
    }
    //关闭数据库
    public void close_db(){
        db.close();
    }
}
