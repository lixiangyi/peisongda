package com.theaty.peisongda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.theaty.peisongda.bean.History;

import org.droidparts.persist.sql.AbstractDBOpenHelper;

public class DBOpenHelper extends AbstractDBOpenHelper {

	public DBOpenHelper(Context ctx, String account) {

		super(ctx, account + "_"
				+ DB.FILE, DB.VERSION);
		Log.d("DBOpenHelper", "" + DB.VERSION);
	}

	public void onCreateTables(SQLiteDatabase db) {
		boolean isSusses = createTables(db, History.class);
		Log.d("DBOpenHelper", "" + isSusses);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTables(db);
		onCreate(db);

	}

}
