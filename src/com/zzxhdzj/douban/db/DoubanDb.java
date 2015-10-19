package com.zzxhdzj.douban.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.zzxhdzj.douban.db.tables.ChannelCategeryContract;
import com.zzxhdzj.douban.db.tables.ChannelContract;

/**
 * Created with IntelliJ IDEA.
 * User: yangning.roy
 * Date: 6/2/14
 * To change this template use File | Settings | File Templates.
 */
public class DoubanDb {
    private static final String TAG = "Douban";
    private static final String DATABASE_NAME = "douban_fm.db";
    public static final int DATABASE_VERSION = 3;
    private static DoubanDb sInstance = null;
    private DatabaseHelper mOpenHelper = null;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "Create Database.");
            createAllTables(db);
            createAllIndexes(db);
            initializeDatas(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(TAG, "Upgrade Database.");
        }

    }

    private DoubanDb(Context context) {
        mOpenHelper = new DatabaseHelper(context);
    }

    public static synchronized DoubanDb getInstance(Context context) {
        if (null == sInstance) {
            sInstance = new DoubanDb(context);
        }
        return sInstance;
    }

    public SQLiteOpenHelper getSQLiteOpenHelper() {
        return mOpenHelper;
    }

    public SQLiteDatabase getDb(boolean writable) {
        if (writable) {
            return mOpenHelper.getWritableDatabase();
        } else {
            return mOpenHelper.getReadableDatabase();
        }
    }

    public void close() {
        if (null != sInstance) {
            mOpenHelper.close();
            sInstance = null;
        }
    }

    private static void createAllTables(SQLiteDatabase db) {
    	db.execSQL(ChannelCategeryContract.getCreateSQL());
        db.execSQL(ChannelContract.getCreateSQL());        
    }
    private static void initializeDatas(SQLiteDatabase db) {
    	db.execSQL(ChannelCategeryContract.getInitializeDataSQL());
        db.execSQL(ChannelContract.getInitializeDataSQL());
    }

    private static void dropAllTables(SQLiteDatabase db) {
    	db.execSQL(ChannelCategeryContract.getDropSQL());
        db.execSQL(ChannelContract.getDropSQL());
    }

    private static void resetAllTables(SQLiteDatabase db, int oldVersion,
                                       int newVersion) {
        try {
            dropAllTables(db);
        } catch (SQLException e) {
            Log.e(TAG, "resetAllTables ERROR!");
        }
        createAllTables(db);
    }

    private static void createAllIndexes(SQLiteDatabase db) {
    	db.execSQL(ChannelCategeryContract.getCreateIndexSQL());
        db.execSQL(ChannelContract.getCreateIndexSQL());
    }


}
