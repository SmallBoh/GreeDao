package com.example.ms;

import java.util.Date;

import com.example.greeDao.entity.Note;
import com.example.ms.service.DbService;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

public class MainActivity extends Activity {
	private DbService mDbService;
	private Context mContext;
	private TextView mtTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inttView();

	}

	private void inttView(){
		mContext = this;
		mtTextView = (TextView) findViewById(R.id.gree_txt);
		mDbService = DbService.getInstance(mContext);
		mDbService.saveNote(new Note("SmallBoh",new Date().toString()));
	
		mtTextView.setText(mDbService.loadNote().toString());
		mtTextView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mDbService.deleteNote();
				mtTextView.setText(mDbService.loadNote().toString());
				Toast.makeText(mContext, "ÒÑÉ¾³ý", Toast.LENGTH_LONG).show();
			}
		});
	}
}
