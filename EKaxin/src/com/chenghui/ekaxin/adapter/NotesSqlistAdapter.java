package com.chenghui.ekaxin.adapter;

import com.chenghui.ekaxin.util.NotepadInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class NotesSqlistAdapter {
	// 数据库名称
	private static final String DB_NAME = "notepad.db";
	// 数据库中的列表
	private static final String DB_TABLE = "notepadinfo";
	// 数据库的版本号
	private static final int DB_VERSION = 1;

	// 数据库ID
	public static final String KEY_ID = "_id";
	// 记事本的标题
	public static final String KEY_TITLE = "title";
	// 记事本的记录时间
	public static final String KEY_RECORDTIME = "recordtime";
	// 提示 0 关 1开
	public static final String KEY_CLOCK = "clock";
	// 内容 包括文本信息和图片信息
	public static final String KEY_CONTENT = "content";

	public static final String KEY_PICPATH = "path";

	private SQLiteDatabase db;

	private final Context context;

	private SqlistDBOpenHelper sqlistdbOpenHelper;

	/**
	 * 对DBAdapter构造函数
	 */
	public NotesSqlistAdapter(Context _context) {
		context = _context;
	}

	/**
	 * 打开数据库
	 */
	public void open() throws SQLiteException {
		sqlistdbOpenHelper = new SqlistDBOpenHelper(context, DB_NAME, null,
				DB_VERSION);
		try {
			db = sqlistdbOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = sqlistdbOpenHelper.getReadableDatabase();
		}
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

	/**
	 * 在数据库中插入数据
	 */
	public long insert(NotepadInfo people) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_TITLE, people.title);
		newValues.put(KEY_RECORDTIME, people.recordtime);
		newValues.put(KEY_CLOCK, people.clock);
		newValues.put(KEY_CONTENT, people.content);
		newValues.put(KEY_PICPATH, people.path);
		return db.insert(DB_TABLE, null, newValues);
	}

	/**
	 * 查找所有数据
	 */
	public NotepadInfo[] queryAllData() {
		Cursor results = db.query(DB_TABLE, new String[] { KEY_ID, KEY_TITLE,
				KEY_RECORDTIME, KEY_CLOCK, KEY_CONTENT, KEY_PICPATH }, null,
				null, null, null, null);
		return ConvertToPeople(results);
	}

	/**
	 * 查找一条数据 long
	 */
	public NotepadInfo[] queryOneData(long id) {
		Cursor results = db.query(DB_TABLE, new String[] { KEY_ID, KEY_TITLE,
				KEY_RECORDTIME, KEY_CLOCK, KEY_CONTENT, KEY_PICPATH }, KEY_ID
				+ "=" + id, null, null, null, null);
		return ConvertToPeople(results);
	}

	/**
	 * 查找一条数据 String
	 */
	public NotepadInfo[] queryOneData(String id) {
		Cursor results = db.query(DB_TABLE, new String[] { KEY_ID, KEY_TITLE,
				KEY_RECORDTIME, KEY_CLOCK, KEY_CONTENT, KEY_PICPATH },
				KEY_TITLE + "=" + id, null, null, null, null);
		return ConvertToPeople(results);
	}

	/**
	 * 删除所有记录
	 */
	public long deleteAllData() {
		return db.delete(DB_TABLE, null, null);
	}

	/**
	 * 删除一条记录long
	 */
	public long deleteOneData(long id) {
		return db.delete(DB_TABLE, KEY_ID + "=" + id, null);
	}

	/**
	 * 删除一条记录String
	 */
	public long deleteOneData(String id) {
		return db.delete(DB_TABLE, KEY_TITLE + "=" + id, null);
	}

	/**
	 *  
	 */
	private NotepadInfo[] ConvertToPeople(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		NotepadInfo[] peoples = new NotepadInfo[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			peoples[i] = new NotepadInfo();
			peoples[i].str_id = cursor.getInt(0);
			peoples[i].title = cursor.getString(cursor
					.getColumnIndex(KEY_TITLE));
			peoples[i].recordtime = cursor.getString(cursor
					.getColumnIndex(KEY_RECORDTIME));
			peoples[i].clock = cursor.getString(cursor
					.getColumnIndex(KEY_CLOCK));
			peoples[i].content = cursor.getString(cursor
					.getColumnIndex(KEY_CONTENT));
			peoples[i].path = cursor.getString(cursor
					.getColumnIndex(KEY_PICPATH));
			cursor.moveToNext();
		}
		return peoples;
	}

	/**
	 * 数据库跟新
	 */
	public long updateOneData(long id, NotepadInfo people) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_TITLE, people.title);
		updateValues.put(KEY_RECORDTIME, people.recordtime);
		updateValues.put(KEY_CLOCK, people.clock);
		updateValues.put(KEY_CONTENT, people.content);
		updateValues.put(KEY_PICPATH, people.path);
		return db.update(DB_TABLE, updateValues, KEY_ID + "=" + id, null);
	}

	/** 静态Helper类，用于建立、更新和打开数据库 */
	private static class SqlistDBOpenHelper extends SQLiteOpenHelper {
		
		public SqlistDBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		private static final String DB_CREATE = "create table " + DB_TABLE
				+ " (" + KEY_ID + " integer primary key autoincrement, "
				+ KEY_TITLE + " text not null, " + KEY_RECORDTIME
				+ " text not null," + KEY_CLOCK + " text not null,"
				+ KEY_CONTENT + " text not null," + KEY_PICPATH + " integer);";

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			onCreate(_db);
		}
	}
}
