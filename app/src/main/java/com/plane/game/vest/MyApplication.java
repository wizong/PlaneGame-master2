package com.plane.game.vest;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;
import com.plane.game.vest.net.MultiPartStack;

public class MyApplication extends Application {

    private static Context mContext;
    private static RequestQueue mQueues;

    public static Context getContext() {
        return mContext;
    }

    public static RequestQueue getHttpQueue(){
        return mQueues;
    }

    @Override
    //程序的入口
    public void onCreate() {
        super.onCreate();
    	x.Ext.init(this);
        mContext = this ;
		mQueues= Volley.newRequestQueue(mContext,new MultiPartStack());

		//极光初始化
        JPushInterface.setDebugMode(true);  //调试模式
        JPushInterface.init(this);
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
