package com.example.ms.service;

import java.util.List;

import com.example.greeDao.entity.DaoSession;
import com.example.greeDao.entity.Note;
import com.example.greeDao.entity.NoteDao;


import android.content.Context;


/**
 * 增删改查
 * @author 波波
 *
 */
public class DbService {
	private DaoSession mDaoSession;
	private NoteDao mNoteDao;
	private static Context mContext;
	private static DbService mInstance;

	private DbService(){

	}
	public static DbService getInstance(Context context){
		if(mInstance == null){
			mInstance = new DbService();
			if(mContext == null){
				mContext = context.getApplicationContext();
			}
			mInstance.mDaoSession = BaseApplication.getDaoSession(context);
			mInstance.mNoteDao = mInstance.mDaoSession.getNoteDao();
		}

		return mInstance;
	}

	//添加单个数据
	public long saveNote(Note note){
		return mNoteDao.insert(note);
	}
	//添加多个数据
	public void saveNote(List<Note> list){
		if(list != null){
			for (int i = 0; i < list.size(); i++) {
				mNoteDao.insert(list.get(i));
			}
		}
	}	
	public List<Note> loadNote(String where,String...paams){
		return mNoteDao.queryRaw(where, paams);
	}
	public List<Note> loadNote(){
		//查询所有数据 
		return mNoteDao.loadAll();
	}
	//删除数据
	public void deleteNote(){
		mNoteDao.deleteAll();
		//删除一条数据   mNoteDao.delete(note);
	}

}
