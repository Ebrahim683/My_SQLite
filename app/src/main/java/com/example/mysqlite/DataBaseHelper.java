package com.example.mysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
//SQLite OpenHelper Class For Create DataBase
public class DataBaseHelper extends SQLiteOpenHelper {

    //References for Create Database
    private Context context;
    public static final String DATABASE_NAME = "student.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "student_table";
    public static final String ID_KEY = "_id";
    public static final String NAME_KEY = "name";
    public static final String AGE_KEY = "age";
    public static final String PROFESSION_KEY = "profession";
    public static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ( "+ID_KEY+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME_KEY+" TEXT, "+AGE_KEY+" INTEGER, "+PROFESSION_KEY+" TEXT "+")";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final String SELECT_TABLE = "SELECT * FROM "+TABLE_NAME;
    public static final String UPDATE_DATA = ID_KEY+" =?";
    public static final String DELETE_DATA = ID_KEY+" =?";

    //Constructor
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    //Create Database
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
            Log.e("error1","Error = "+e);
            e.printStackTrace();
        }
    }

    //Upgrade Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }


    //Ths Method Use For Add New Data
    public long addData(DataBaseModel dataBaseModel){
        //Permission For Add data
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Set Data By ContentValues Object
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_KEY,dataBaseModel.getName());
        contentValues.put(AGE_KEY,dataBaseModel.getAge());
        contentValues.put(PROFESSION_KEY,dataBaseModel.getProfession());
        //Query for add data
        long rowID = sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        return rowID;
    }



    //This Method Use for Show Data in ListView
    public ArrayList<DataBaseModel> showAllData(){
        //Permission For Show data
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        //ArrayList for set data
        ArrayList<DataBaseModel> arrayList = new ArrayList<DataBaseModel>();
        //Cursor Object for get All data and show
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_TABLE,null);

        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_KEY)));
                String name = cursor.getString(cursor.getColumnIndex(NAME_KEY));
                String age = cursor.getString(cursor.getColumnIndex(AGE_KEY));
                String profession = cursor.getString(cursor.getColumnIndex(PROFESSION_KEY));
                DataBaseModel dataBaseModel = new DataBaseModel(id,name,age,profession);
                arrayList.add(dataBaseModel);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        cursor.close();
        return arrayList;
    }

   /* public ArrayList<DataBaseModel> showData(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<DataBaseModel> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_TABLE,null);
        if (cursor.moveToFirst()){
            do {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID_KEY)));
                String name = cursor.getString(cursor.getColumnIndex(NAME_KEY));
                int age = cursor.getShort(cursor.getColumnIndex(AGE_KEY));
                String profession = cursor.getString(cursor.getColumnIndex(PROFESSION_KEY));
                DataBaseModel dataBaseModel = new DataBaseModel(id,name,age,profession);
                arrayList.add(dataBaseModel);
            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        cursor.close();
        return arrayList;
    }*/


    //This Method Use For Update Added Data
    public boolean updateData(DataBaseModel dataBaseModel){
        //Permission For Update data
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Update Data By ContentValues Object
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_KEY,dataBaseModel.getName());
        contentValues.put(AGE_KEY,dataBaseModel.getAge());
        contentValues.put(PROFESSION_KEY,dataBaseModel.getProfession());
        //Query For Update Data
        int updateRowID = sqLiteDatabase.update(TABLE_NAME,contentValues,UPDATE_DATA,new String[]{String.valueOf(dataBaseModel.getId())});
        this.close();
        if (updateRowID>0){
            return true;
        }else
            return false;
    }


    //This Method Use For Delete Data
    public boolean deleteData(DataBaseModel dataBaseModel){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Query For Delete
        int deleteRow = sqLiteDatabase.delete(TABLE_NAME,DELETE_DATA,new String[]{String.valueOf(dataBaseModel.getId())});
        if (deleteRow>0){
            return true;
        }else return false;
    }


}
