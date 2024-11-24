package com.example.fakeqq;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.fakeqq.provider";
    private static final String TABLE_NAME = "information";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

    private static final int CODE_TABLE_DIR = 1;
    private static final int CODE_TABLE_ITEM = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, CODE_TABLE_DIR);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", CODE_TABLE_ITEM);
    }

    private DatabaseHelper dbHelper;

    //初始化DatabaseHelper对象
    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    //查询数据库
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CODE_TABLE_DIR:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CODE_TABLE_ITEM:
                String id = uri.getLastPathSegment();
                cursor = db.query(TABLE_NAME, projection, "_id=?", new String[]{id}, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return cursor;
    }

    //插入
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id;
        if (uriMatcher.match(uri) == CODE_TABLE_DIR) {
            id = db.insert(TABLE_NAME, null, values);
        } else {
            throw new IllegalArgumentException("Invalid URI for insert operation: " + uri);
        }
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }

    //更新
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsUpdated;
        switch (uriMatcher.match(uri)) {
            case CODE_TABLE_DIR:
                rowsUpdated = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case CODE_TABLE_ITEM:
                String id = uri.getLastPathSegment();
                rowsUpdated = db.update(TABLE_NAME, values, "_id=?", new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return rowsUpdated;
    }

    //删除
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;
        switch (uriMatcher.match(uri)) {
            case CODE_TABLE_DIR:
                rowsDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_TABLE_ITEM:
                String id = uri.getLastPathSegment();
                rowsDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case CODE_TABLE_DIR:
                return "vnd.android.cursor.dir/" + TABLE_NAME;
            case CODE_TABLE_ITEM:
                return "vnd.android.cursor.item/" + TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }
}
