package com.theaty.peisongda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.theaty.peisongda.bean.History;

import org.droidparts.persist.sql.EntityManager;

import java.util.ArrayList;

/**
 * 历史数据
 */

public class HistoryDataManager extends EntityManager<History> {

	public HistoryDataManager(Context ctx, SQLiteDatabase db) {
		super(History.class, ctx,db);
	}

	/**
	 * 查询所有记录
	 * @return
	 */
	public ArrayList<History>  getHistorys(){
		ArrayList<History>  histories=this.readAll(this.select());
		return histories;
	}

	public  void addHistory(History history){
		this.create(history);
	}

	/**
	 * 清空
	 */
	public   void  clearHistory(){
		this.delete(this.readAll(this.select()));
	}

}
