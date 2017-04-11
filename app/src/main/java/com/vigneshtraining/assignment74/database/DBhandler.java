package com.vigneshtraining.assignment74.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


import com.vigneshtraining.assignment74.model.Person;
import com.vigneshtraining.assignment74.utils.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vimadhavan on 4/9/2017.
 */

public class DBhandler {
    private SQLiteDatabase db;
    private final Context context;
    private final DBhelper dbHelper;
    private static DBhandler db_handler = null;

    public static DBhandler getInstance(Context context){
        try{
            if(db_handler == null){
                db_handler = new DBhandler(context);

            }
            db_handler.open();
        }catch(IllegalStateException e){
            //db_helper already open
        }
        return db_handler;
    }

    public DBhandler(Context context) {

        this.context = context;
        this.dbHelper = new DBhelper(context, Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }
    public void close() {
        try {
            if (db.isOpen()) {
                db.close();
            }
        }catch (Exception e){

        }

    }



    /*
     * open database
     */
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.v("open database Exception", "error==" + e.getMessage());
            db = dbHelper.getReadableDatabase();
        }
    }




    public List<Person> getAllPersons(){
        List<Person> persons=new LinkedList<Person>();

        String query = "SELECT  * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);


        Person person=null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    person = new Person();
                    person.setId(cursor.getInt(0));
                    person.setFirstName(cursor.getString(1));
                    person.setLastName(cursor.getString(2));
                    persons.add(person);
                } while (cursor.moveToNext());
            }
        }finally {
            cursor.close();
        }




        return persons;

    }


    public long addPerson(Person person) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(Constants.PERSON_FIRST_NAME, person.getFirstName());
        initialValues.put(Constants.PERSON_LAST_NAME, person.getLastName());

        return db.insert(Constants.TABLE_NAME , null, initialValues);
    }



}
