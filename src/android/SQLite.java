package br.com.alamoweb.plugin;

import org.apache.cordova.*;

import java.util.ArrayList;
import java.util.Iterator;
import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

public class SQLite extends CordovaPlugin
{
    public SQLiteHelper sqliteHelper;
    public Context context;
    public SQLiteDatabase database;

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException
    {
        this.context = this.cordova.getActivity().getApplicationContext();

        if (action.equals("create"))
        {
            String sqlCreateDatabase = data.getString('sql_create_database');

            sqliteHelper = new SQLiteHelper(this.context, sqlCreateDatabase);

            String message = "Table created";
            callbackContext.success(message);

            return true;
        }

        else if ("insert")
        {
            sqliteHelper = new SQLiteHelper(this.context);
            database = sqliteHelper.getWritableDatabase();

            String columns = "";
            String values = "";

            String table = data.getString("table");

            JSONObject insertData = data.getJSONObject("insert_data");

            // TODO:
        }

        else
        {
            return false;
        }
    }

    public class SQLiteHelper extends SQLiteOpenHelper
    {
        public static final String DATABASE_NAME = "plugin.db";

        public String sqlCreateDatabase;

        public SQLiteHelper (Context context, String sqlCreateDatabase)
        {
            super(context, DATABASE_NAME, null, 1);

            this.sqlCreateDatabase = sqlCreateDatabase;
        }

        public SQLiteHelper (Context context)
        {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate (SQLiteDatabase database)
        {
            if (this.sqlCreateDatabase != null)
            {
                database.execSQL(this.sqlCreateDatabase);
            }
        }

        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
        {
            //
        }
    }
}
