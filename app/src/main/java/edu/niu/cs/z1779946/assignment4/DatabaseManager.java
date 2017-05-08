/*
* Name - Darshan Patil
* ZID - Z1779946
* Assignment 4
* Due Date - Thrusday May 4th
*/
package edu.niu.cs.z1779946.assignment4;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by DARSHAN on 4/27/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "noteDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ToDo = "todo",
                                ID = "id",
                                ITEM = "item",
                                DONE = "done";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }//end of constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        //build SQL statement
        String sqlcreate = "Create table " + TABLE_ToDo + "(" + ID + " integer primary key autoincrement, " + ITEM + " text, " + DONE + " integer )" ;
        db.execSQL(sqlcreate);
    }//end of onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_ToDo);
        onCreate(db);
    }//end of onUpgrade

    public void insert(TodoList todoList){
        SQLiteDatabase db = getWritableDatabase();

        String sqlInsert = "Insert into " + TABLE_ToDo + " values( null, '" + todoList.getItem() + "', '" + todoList.getSelected() + "'     )";
        db.execSQL(sqlInsert);
        db.close();
    }//end of insert

    public void deleteById(int id){
        SQLiteDatabase db = getWritableDatabase();

        String sqlDelete = "delete from " + TABLE_ToDo + " where  "+ ID + " = " + id;
        db.execSQL(sqlDelete);
        db.close();
    }//end of DeleteById

    public void deleteAll(){
        SQLiteDatabase db = getWritableDatabase();

        String sqlDeleteAll = "delete from " + TABLE_ToDo;
        db.execSQL(sqlDeleteAll);
        db.close();
    }//end of DeleteById

    public void updateById(int id, String item){
        String sqlUpdate = "update " + TABLE_ToDo + " set " + ITEM + " = '" + item + "' ";

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }//update by ID

    public void isDone(boolean isChecked, int id){
        int done;

        if (isChecked)
            done = 1;
        else
            done = 0;

        String sqlUpdate = "update " + TABLE_ToDo + " set " + DONE + " = '" + done + "' where " + ID + " = " + id;

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }//update by ID

    public ArrayList<TodoList> selectAll(){
        String sqlSelectAll = "select * from " + TABLE_ToDo;

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(sqlSelectAll, null);

        ArrayList<TodoList> todoLists = new ArrayList<>();

        while (cursor.moveToNext()){
            TodoList currentTodoList = new TodoList(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));
            todoLists.add(currentTodoList);
        }

        db.close();
        return todoLists;
    }//end of select all

    public TodoList selectById(int id){
        String sqlSelect = "select * from " + TABLE_ToDo + " where " + ID + " = " + id;

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(sqlSelect, null);

        TodoList todoListById = null;
        if(cursor.moveToFirst()){
            todoListById = new TodoList(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)));
        }

        db.close();
        return todoListById;
    }//end of select by id

}
