package com.example.nz.sqlitepracticeone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Nz on 3/1/2019.
 */

public class MySQLite extends SQLiteOpenHelper{
    // constructor .........
    public static final String DATABASE_NAME = "Student.db";
    public  static  final  String TABLE_NAME = "student_details";
    public  static  final  int VERSION_NUMBER = 3;

    // table content .....
    public  static  final  String ID = "_id";
    public static  final String NAME = "Name";
    public  static  final  String AGE = "Age";
    public  static  final  String GENDER = "Gender";

    // on upGrade....
    public static  final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    // for read Data ..
    public static  final  String SELECT_ALL_DATA = "SELECT * FROM "+TABLE_NAME;

    // on create .....
    public  static  final  String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR (255), "+AGE+" INTEGER, "+GENDER+" VARCHAR (15))";

    //context ....
    Context context;




// constructor .....
    public MySQLite(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    // on create Method ......

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context,"TABLE IS CREATED ",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(context,"Exception is "+e,Toast.LENGTH_LONG).show();
        }
    }

    // on upGrade Method  ........

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {Toast.makeText(context,"TABLE IS UPGRADED",Toast.LENGTH_LONG).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e){
            Toast.makeText(context,"Exception is "+e,Toast.LENGTH_LONG).show();
        }
    }


    // data insert Method ..........

    public long insertData (String name, String age, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);
        long rowId = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return  rowId;
    }

    // data show method  .......

    public Cursor showData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
      Cursor cursor =  sqLiteDatabase.rawQuery(SELECT_ALL_DATA,null);
      return  cursor;
    }

    // data update method .....

    public  boolean updateData(String id, String name, String age, String gender){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,id);
        contentValues.put(NAME,name);
        contentValues.put(AGE,age);
        contentValues.put(GENDER,gender);
        sqLiteDatabase.update(TABLE_NAME,contentValues,ID+" = ? ",new String[]{id});
        return  true;
    }

    // delete data ......

    public  int  delteData (String id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
       return sqLiteDatabase.delete(TABLE_NAME,ID+" =  ? ", new String[]{id});
    }



}
