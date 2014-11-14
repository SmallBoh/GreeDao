package com.example.ms.service;

import java.util.List;

import com.example.greeDao.entity.DaoSession;
import com.example.greeDao.entity.Note;
import com.example.greeDao.entity.NoteDao;


import android.content.Context;


/**
 * ��ɾ�Ĳ�
 * @author ����
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

	//��ӵ�������
	public long saveNote(Note note){
		return mNoteDao.insert(note);
	}
	//��Ӷ������
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
		//��ѯ�������� 
		return mNoteDao.loadAll();
	}
	//ɾ������
	public void deleteNote(){
		mNoteDao.deleteAll();
		//ɾ��һ������   mNoteDao.delete(note);
	}

}
