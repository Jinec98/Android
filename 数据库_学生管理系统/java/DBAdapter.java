package com.example.pc.myapplication5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pc on 2018/11/6.
 */

public class DBAdapter {
    private static final String DB_NAME = "student.db";
    private static final String DB_TABLE = "studentinfo";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CLASS = "class";
    public static final String KEY_NUMBER = "number";

    private SQLiteDatabase db;
    private final Context context;
    private SQLiteOpenHelper dbOpenHelper;

    public DBAdapter(Context context){
        this.context = context;
    }

    public void close(){
        if(db != null){
            db.close();
            db = null;
        }
    }

    public void open(){
        dbOpenHelper = new SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
            String DB_CREATE = "create table " + DB_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
                    KEY_NAME + " text not null, " + KEY_CLASS + " text not null, " + KEY_NUMBER + " text not null);";

            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL(DB_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + DB_TABLE);
                onCreate(sqLiteDatabase);
            }
        };
        db = dbOpenHelper.getWritableDatabase();
    }

    public long insert(Student student){
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_CLASS, student.Class);
        newValues.put(KEY_NAME, student.Name);
        newValues.put(KEY_NUMBER,student.Number);

        return db.insert(DB_TABLE, null, newValues);
    }

    public Student[] ConvertToStudent(Cursor cursor){
        int resultCounts = cursor.getCount();
        if(resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }

        Student[] students = new Student[resultCounts];
        for(int i = 0; i < resultCounts; i++){
            students[i] = new Student();
            students[i].ID = cursor.getInt(0);
            students[i].Name = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            students[i].Class = cursor.getString(cursor.getColumnIndex(KEY_CLASS));
            students[i].Number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER));
            cursor.moveToNext();
        }
        return students;
    }

    public Student[] queryAllData(){
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_CLASS, KEY_NUMBER },
                null, null, null, null, null);
        return ConvertToStudent(results);
    }

    public long deleteAllData(){
        return db.delete(DB_TABLE, null, null);
        //db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE);
        //this.open();
    }

    public long deleteOneData(int id){
        return db.delete(DB_TABLE, KEY_ID + "=" + id, null);
    }

    public long updateOneData(int id, Student student){
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_CLASS, student.Class);
        updateValues.put(KEY_NAME, student.Name);
        updateValues.put(KEY_NUMBER, student.Number);

        return db.update(DB_TABLE, updateValues, KEY_ID + "=" + id, null);
    }

}
