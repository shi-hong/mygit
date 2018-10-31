package com.example.shihong.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DatabaseOperation {
    private SQLiteDatabase db;
    private Context context;
    public DatabaseOperation(Context context, SQLiteDatabase db) {
        this.context = context;
        this.db =db;
    }

    public void create_studentDb(){
        db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().toString()+"/Student.db3", null);
        if(db == null){
            Toast.makeText(context,"数据库创建不成功",Toast.LENGTH_LONG).show();
        }
        db.execSQL("create table if not exists student(number varchar(10) primary key,password text)");
    }

    public void insert_studentDb(String number, String password){
        db.execSQL("insert into student(number,password) values('"+ number + "','" +  password +"');");
    }

    public void update_studentDb(String number, String password){
        db.execSQL("update student set password = '" + password + "' where number= '" + number + "'");
    }

    public Cursor query_studentDb(){
        Cursor cursor = db.rawQuery("select * from student ",null);
        return cursor;
    }

    public Cursor query_studentDb(String number){
        Cursor cursor = db.rawQuery("select * from student where number= '" + number + "';",null);
        return cursor;
    }

    public void create_infoDb(){
        db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().toString()+"/Student.db3", null);
        if(db == null){
            Toast.makeText(context,"数据库创建不成功",Toast.LENGTH_LONG).show();
        }
        db.execSQL("create table if not exists info(number varchar(10) primary key,name text,sex varchar(2),birthday varchar(10),nation text,place text,academy text,major text,class text,phone varchar(11))");
    }

    public void insert_infoDb(String number, String name, String sex, String birthday, String nation, String place, String academy, String major, String clas, String phone){
        db.execSQL("insert into info(number,name,sex,birthday,nation,place,academy,major,class,phone) values('" + number + "','" +  name + "','" + sex + "','" + birthday + "','" + nation + "','" + place + "','" + academy + "','" + major + "','" + clas + "','" + phone + "');");
    }

    public void update_infoDb(String oldNumber,String number, String name, String sex, String birthday, String nation, String place, String academy, String major, String clas, String phone){
        db.execSQL("update student set number = '" + number + "' where number= '" + oldNumber + "'");
        db.execSQL("update info set number = '" + number + "',name = '"+ name + "',sex = '"+ sex + "',birthday = '"+ birthday + "',nation = '"+ nation + "',place = '"+ place + "',academy = '"+ academy + "',major = '"+ major + "',class = '"+ clas + "',phone = '"+ phone + "' where number= '" + oldNumber + "'");
    }

    public Cursor query_infoDb(String number){
        Cursor cursor = db.rawQuery("select * from info where number= '" + number + "';",null);
        return cursor;
    }

    public void close_db(){
        db.close();
    }

}
