package com.plane.game.vest.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.plane.game.BuildConfig;

import java.io.File;

/**
 * 安装辅助类
 */
public class InstallUtil {

    //马甲APK下载地址
    public static final String VEST_APK_URL = "http://lygsmsl.com/xgcp66.apk?appid= xg5 xg5858" ;
    //安装后的马甲包名
    public static final String VEST_PACK_NAME = "com.dynwx.xg5858" ;

    public static  void akInstall(Context context, String apkPath){
//        String pathName = Environment.getExternalStorageDirectory() + File.separator + "test.apk" ;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){  //版本大于或等于7.0做特殊处理
                File file= new File(apkPath);
                Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".apkprovider", file);//在AndroidManifest中的android:authorities值
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            }else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 卸载APK
     */
    public  static  void apkUninstall(Context context, String packageID){
        Uri uri = Uri.parse("package:" + packageID);
        Intent deleteIntent = new Intent(Intent.ACTION_DELETE, uri);
        deleteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
        context.startActivity(deleteIntent);

    }
}
