package com.example.ms.service;



import com.example.greeDao.entity.DaoMaster;
import com.example.greeDao.entity.DaoMaster.OpenHelper;
import com.example.greeDao.entity.DaoSession;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application{

	private BaseApplication mInstance;
	private static DaoMaster mDaoMaster;
	private static DaoSession mDaoSession;
	private static Context context;
/*
 * 一键退出
 * 	public static void finishActivity(){
		List<Activity> list = new ArrayList<Activity>();
		list.add(new MainActivity());
		for (int i = 0; i < list.size(); i++) {
			list.get(i).finish();
		}
	}*/
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if(mInstance == null){
			mInstance = this;
			context = mInstance;
		}
	}
	//创建数据库
	public static DaoMaster getDaoMaster(Context context){
		if(mDaoMaster == null){
			OpenHelper helper = new DaoMaster.DevOpenHelper(context, "note", null);
			mDaoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return mDaoMaster;
	}

	public static DaoSession getDaoSession(Context context){

		if(mDaoSession == null){
			if(mDaoMaster == null){
				getDaoMaster(context);
			}
			mDaoSession = mDaoMaster.newSession();
		}

		return mDaoSession;
	}


}
