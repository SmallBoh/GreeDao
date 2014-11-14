package com.example.third_party;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity{
	private Button btn;
	private TextView tv;
	private Context context;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			tv.setText(msg.obj+"");
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		btn = (Button) findViewById(R.id.button);
		tv = (TextView) findViewById(R.id.txt);
		btn.setOnClickListener(click);
	}
	View.OnClickListener click = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ShareSDK.initSDK(context);
			//新浪
			Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
			sina.setPlatformActionListener(new PlatformActionListener() {
				
				@Override
				public void onError(Platform arg0, int arg1, Throwable arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onComplete(Platform arg0, int arg1, final HashMap<String, Object> arg2) {
					// TODO Auto-generated method stub
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							Message msg = handler.obtainMessage(1, arg2);
							handler.sendMessage(msg);
							
						}
					}).start();
				}
				
				@Override
				public void onCancel(Platform arg0, int arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			sina.SSOSetting(true);
			sina.showUser(null);
		}
	};
}
