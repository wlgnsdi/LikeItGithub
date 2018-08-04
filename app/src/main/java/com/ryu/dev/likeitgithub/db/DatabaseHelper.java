package com.ryu.dev.likeitgithub.db;

import static com.ryu.dev.likeitgithub.db.GithubTable.TABLE_NAME;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.ryu.dev.likeitgithub.model.Github;
import com.ryu.dev.likeitgithub.model.Github.Items;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "likeit.sDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }

        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseSchema.CREATE_GITHUB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

        db.setForeignKeyConstraintsEnabled(true);
        db.setLocale(Locale.getDefault());
    }

    public void add(Github.Items item) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            db.insert(TABLE_NAME, null, Github.getContentValues(item));
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Database Insert Error");
        } finally {
            db.endTransaction();
        }

    }

    public void delete(Items item) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            db.delete(TABLE_NAME, GithubTable.COLUMN_ID + "=" + item.getId(), null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Database Delete ERROR");
        } finally {
            db.endTransaction();
        }
    }

    public List<Items> getAll() {
        List<Items> list = new ArrayList<>();

        String SELECT_ALL = "SELECT * FROM " + TABLE_NAME + " order by " + GithubTable.COLUMN_ID + " desc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL, null);

        if (cursor.moveToFirst()) {
            do {
                Items item = new Items();
                item.setId(cursor.getInt(0));
                item.setLogin(cursor.getString(1));
                item.setAvatalUrl(cursor.getString(2));
                item.setLike(cursor.getInt(3) == 1);
                list.add(item);
            } while (cursor.moveToNext());
        }

        return list;
    }
}
