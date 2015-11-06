package com.chenghui.ekaxin.util;

import com.chenghui.ekaxin.bean.User;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.text.TextUtils;

public class UserCharDB {
	
	private SQLiteDatabase db;
	private final Context context;
	private UserCharDBHelper usercharHelper;
	
	public UserCharDB(Context _context) {
		context = _context;
	}
	
	public void open() throws SQLiteException {
		usercharHelper = new UserCharDBHelper(context, DB_NAME, null,DB_VERSION);
		try {
			db = usercharHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = usercharHelper.getReadableDatabase();
		}
	}
	
	public void close() {
		if (db != null) {
			db.close();
			db = null;
		}
	}
	
	/**
	 * 在数据库中插入数据
	 */
	public long insert(User user ,String tab) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_USERNAME, TextUtils.isEmpty(user.getUsername())? "":user.getUsername());
		newValues.put(KEY_AVATAR, TextUtils.isEmpty(user.getAvatar())? "":user.getAvatar());
		newValues.put(KEY_SIGNATURE, TextUtils.isEmpty(user.getSignature())? "":user.getSignature());
		newValues.put(KEY_SEX, TextUtils.isEmpty(user.getSex())? "":user.getSex());
		newValues.put(KEY_CREATED, TextUtils.isEmpty(user.getUpdatedAt())? "":user.getUpdatedAt());
		newValues.put(KEY_USER_CHAINNAME, TextUtils.isEmpty(user.getUserChainName())? "":user.getUserChainName());
		newValues.put(KEY_USER_ENGLISHNAME, TextUtils.isEmpty(user.getUserEnglishName())? "":user.getUserEnglishName());
		newValues.put(KEY_USER_MOVEPHONE, TextUtils.isEmpty(user.getUserMovePhone())? "":user.getUserMovePhone());
		newValues.put(KEY_USER_QQ, TextUtils.isEmpty(user.getUserQQ())? "":user.getUserMovePhone());
		newValues.put(KEY_USER_WEIXIN, TextUtils.isEmpty(user.getUserWeiXin())? "":user.getUserWeiXin());
		newValues.put(KEY_USER_EMEIL, TextUtils.isEmpty(user.getUserEmeil())? "":user.getUserEmeil());
		newValues.put(KEY_USER_COMPANY, TextUtils.isEmpty(user.getUserCompany())? "":user.getUserCompany());
		newValues.put(KEY_USER_DEPARTMENT, TextUtils.isEmpty(user.getUserDepartment())? "":user.getUserDepartment());
		newValues.put(KEY_USER_DUTYL, TextUtils.isEmpty(user.getUserDuty())? "":user.getUserDuty());
		newValues.put(KEY_USER_OFFICEPHONE, TextUtils.isEmpty(user.getUserOfficePhone())? "":user.getUserOfficePhone());
		newValues.put(KEY_USER_FAX, TextUtils.isEmpty(user.getUserFax())? "":user.getUsername());
		return db.insert(tab, null, newValues);
	}
	
	/**
	 * 查找所有数据
	 */
	public User[] queryAllData(String tab) {
		Cursor results = db.query(tab, new String[] { KEY_ID, KEY_USERNAME,
				KEY_AVATAR, KEY_SIGNATURE, KEY_SEX, KEY_CREATED, KEY_USER_CHAINNAME , KEY_USER_ENGLISHNAME
				, KEY_USER_MOVEPHONE, KEY_USER_QQ, KEY_USER_WEIXIN, KEY_USER_EMEIL
				, KEY_USER_COMPANY, KEY_USER_DEPARTMENT, KEY_USER_DUTYL, KEY_USER_OFFICEPHONE
				, KEY_USER_FAX}, null,null, null, null, null);
		return ConvertToPeople(results);
	}
	
	/**
	 * 查找一条数据 long
	 */
	public User[] queryOneData(String string,String tab) {
		Cursor results = db.query(tab, new String[] { KEY_ID, KEY_USERNAME,
				KEY_AVATAR, KEY_SIGNATURE, KEY_SEX,KEY_CREATED,KEY_USER_CHAINNAME , KEY_USER_ENGLISHNAME
				, KEY_USER_MOVEPHONE, KEY_USER_QQ, KEY_USER_WEIXIN, KEY_USER_EMEIL
				, KEY_USER_COMPANY, KEY_USER_DEPARTMENT, KEY_USER_DUTYL, KEY_USER_OFFICEPHONE
				, KEY_USER_FAX}, " username='" + string + "'",null, null, null, null);
		return ConvertToPeople(results);
	}
	
	/**
	 * 删除所有记录
	 */
	public long deleteAllData(String tab) {
		return db.delete(tab, null, null);
	}
	
	/**
	 * 删除一条记录String
	 */
	public long deleteOneData(String string ,String tab) {
		return db.delete(tab, " username='" + string + "'" , null);
	}

	/**
	 * 数据库跟新
	 */
	public long updateOneData(String string, User user ,String tab) {
		ContentValues updateValues = new ContentValues();
		updateValues.put(KEY_USERNAME, TextUtils.isEmpty(user.getUsername())? "":user.getUsername());
		updateValues.put(KEY_AVATAR, TextUtils.isEmpty(user.getAvatar())? "":user.getAvatar());
		updateValues.put(KEY_SIGNATURE, TextUtils.isEmpty(user.getSignature())? "":user.getSignature());
		updateValues.put(KEY_SEX, TextUtils.isEmpty(user.getSex())? "":user.getSex());
		updateValues.put(KEY_CREATED, TextUtils.isEmpty(user.getUpdatedAt())? "":user.getUpdatedAt());
		updateValues.put(KEY_USER_CHAINNAME, TextUtils.isEmpty(user.getUserChainName())? "":user.getUserChainName());
		updateValues.put(KEY_USER_ENGLISHNAME, TextUtils.isEmpty(user.getUserEnglishName())? "":user.getUserEnglishName());
		updateValues.put(KEY_USER_MOVEPHONE, TextUtils.isEmpty(user.getUserMovePhone())? "":user.getUserMovePhone());
		updateValues.put(KEY_USER_QQ, TextUtils.isEmpty(user.getUserQQ())? "":user.getUserMovePhone());
		updateValues.put(KEY_USER_WEIXIN, TextUtils.isEmpty(user.getUserWeiXin())? "":user.getUserWeiXin());
		updateValues.put(KEY_USER_EMEIL, TextUtils.isEmpty(user.getUserEmeil())? "":user.getUserEmeil());
		updateValues.put(KEY_USER_COMPANY, TextUtils.isEmpty(user.getUserCompany())? "":user.getUserCompany());
		updateValues.put(KEY_USER_DEPARTMENT, TextUtils.isEmpty(user.getUserDepartment())? "":user.getUserDepartment());
		updateValues.put(KEY_USER_DUTYL, TextUtils.isEmpty(user.getUserDuty())? "":user.getUserDuty());
		updateValues.put(KEY_USER_OFFICEPHONE, TextUtils.isEmpty(user.getUserOfficePhone())? "":user.getUserOfficePhone());
		updateValues.put(KEY_USER_FAX, TextUtils.isEmpty(user.getUserFax())? "":user.getUsername());
		return db.update(tab, updateValues, " username='" + string + "'", null);
	}
	
	
	private User[] ConvertToPeople(Cursor cursor) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		User[] user = new User[resultCounts];
		for (int i = 0; i < resultCounts; i++) {
			user[i] = new User();
			user[i].setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
			user[i].setAvatar(cursor.getString(cursor.getColumnIndex(KEY_AVATAR)));
			user[i].setSignature(cursor.getString(cursor.getColumnIndex(KEY_SIGNATURE)));
			user[i].setSex(cursor.getString(cursor.getColumnIndex(KEY_SEX)));
			user[i].setUserUpdateAt(cursor.getString(cursor.getColumnIndex(KEY_CREATED)));
			user[i].setUserChainName(cursor.getString(cursor.getColumnIndex(KEY_USER_CHAINNAME)));
			user[i].setUserEnglishName(cursor.getString(cursor.getColumnIndex(KEY_USER_ENGLISHNAME)));
			user[i].setUserMovePhone(cursor.getString(cursor.getColumnIndex(KEY_USER_MOVEPHONE)));
			user[i].setUserQQ(cursor.getString(cursor.getColumnIndex(KEY_USER_QQ)));
			user[i].setUserWeiXin(cursor.getString(cursor.getColumnIndex(KEY_USER_WEIXIN)));
			user[i].setUserEmeil(cursor.getString(cursor.getColumnIndex(KEY_USER_EMEIL)));
			user[i].setUserCompany(cursor.getString(cursor.getColumnIndex(KEY_USER_COMPANY)));
			user[i].setUserDepartment(cursor.getString(cursor.getColumnIndex(KEY_USER_DEPARTMENT)));
			user[i].setUserDuty(cursor.getString(cursor.getColumnIndex(KEY_USER_DUTYL)));
			user[i].setUserOfficePhone(cursor.getString(cursor.getColumnIndex(KEY_USER_OFFICEPHONE)));
			user[i].setUserFax(cursor.getString(cursor.getColumnIndex(KEY_USER_FAX)));
			cursor.moveToNext();
		}
		return user;
	}
	
	
	// 数据库名称
	private static final String DB_NAME = "userchar.db";
	// 数据库中的列表
	public static final String DB_TABLE = "userchar";
	public static final String DB_CHAR_TABLE = "charinfo";
	// 数据库的版本号
	private static final int DB_VERSION = 1;
	public static final String KEY_ID = "_id";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_AVATAR = "avatar";
	public static final String KEY_SIGNATURE = "signature";
	public static final String KEY_SEX = "sex";
	public static final String KEY_CREATED = "created";
	
	public static final String KEY_USER_CHAINNAME = "userchainname";
	public static final String KEY_USER_ENGLISHNAME = "userenglishname";
    public static final String KEY_USER_MOVEPHONE = "usermovephone";
    public static final String KEY_USER_QQ = "userqq";
	public static final String KEY_USER_WEIXIN = "userweixin";
    public static final String KEY_USER_EMEIL = "useremeil";
    public static final String KEY_USER_COMPANY = "usercompany";
	public static final String KEY_USER_DEPARTMENT = "userdepartment";
    public static final String KEY_USER_DUTYL = "userduty";
    public static final String KEY_USER_OFFICEPHONE = "userofficephone";
    public static final String KEY_USER_FAX = "userfax";
    
    /** 静态Helper类，用于建立、更新和打开数据库 */
	private static class UserCharDBHelper extends SQLiteOpenHelper {
		
		public UserCharDBHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		private static final String DB_CREATE = "create table " + DB_TABLE
				+ " (" + KEY_ID + " integer primary key autoincrement, "
				+ KEY_USERNAME + " text not null, " + KEY_AVATAR
				+ " text not null," + KEY_SIGNATURE + " text not null,"
				+ KEY_SEX + " text not null," + KEY_CREATED + " text not null,"
				+ KEY_USER_CHAINNAME + " text not null,"
				+ KEY_USER_ENGLISHNAME + " text not null,"+ KEY_USER_MOVEPHONE + " text not null,"
				+ KEY_USER_QQ + " text not null,"+ KEY_USER_WEIXIN + " text not null,"
				+ KEY_USER_EMEIL + " text not null,"+ KEY_USER_COMPANY + " text not null,"
				+ KEY_USER_DEPARTMENT + " text not null,"+ KEY_USER_DUTYL + " text not null,"
				+ KEY_USER_OFFICEPHONE + " text not null,"+ KEY_USER_FAX + " integer);";
		
		private static final String DB_CHAR_CREATE = "create table " + DB_CHAR_TABLE
				+ " (" + KEY_ID + " integer primary key autoincrement, "
				+ KEY_USERNAME + " text not null, " + KEY_AVATAR
				+ " text not null," + KEY_SIGNATURE + " text not null,"
				+ KEY_SEX + " text not null," + KEY_CREATED + " text not null,"
				+ KEY_USER_CHAINNAME + " text not null,"
				+ KEY_USER_ENGLISHNAME + " text not null,"+ KEY_USER_MOVEPHONE + " text not null,"
				+ KEY_USER_QQ + " text not null,"+ KEY_USER_WEIXIN + " text not null,"
				+ KEY_USER_EMEIL + " text not null,"+ KEY_USER_COMPANY + " text not null,"
				+ KEY_USER_DEPARTMENT + " text not null,"+ KEY_USER_DUTYL + " text not null,"
				+ KEY_USER_OFFICEPHONE + " text not null,"+ KEY_USER_FAX + " integer);";

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DB_CREATE);
			_db.execSQL(DB_CHAR_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
			_db.execSQL("DROP TABLE IF CHAR " + DB_CHAR_TABLE);
			onCreate(_db);
		}
	}


}
