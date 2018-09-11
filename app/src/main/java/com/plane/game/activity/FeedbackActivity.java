package com.plane.game.activity;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.plane.game.R;
import com.plane.game.utils.SelectorUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import cn.pedant.SweetAlert.SweetAlertDialog;



@EActivity(R.layout.activity_feedback)
public class FeedbackActivity extends AppCompatActivity{

	@ViewById(R.id.contactEt)
	EditText contactEt;
	@ViewById(R.id.contentEt)
	EditText contentEt;

	@ViewById
	Button commitBtn;
	@ViewById
	ImageView back_iv;
	private Dialog mDialog = null;

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	@AfterViews
	void afterView(){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			SelectorUtil.setBackgroundColorSelector(commitBtn, Color.parseColor("#0A246A"), Color.parseColor("#314E8C")) ;
		} else {
		}
//		ColorStateList colorStateList = SelectorUtil.createColorStateList(Color.parseColor("#0A246A"), Color.parseColor("#314E8C"));
//		commitBtn.setBackgroundTintList(colorStateList);
	}

	@Click({R.id.back_iv, R.id.commitBtn})
	public  void click(View view){
		switch (view.getId()) {
			case R.id.back_iv:
				finish();
				break;
			case R.id.commitBtn:
				commit(view) ;
				break;
		}
	}


	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	SweetAlertDialog dialog ;
	public void commit(View view) {
		String contact=contactEt.getText().toString();
		String content=contentEt.getText().toString();
		if(TextUtils.isEmpty(content)){
			Toast.makeText(this, "请输入反馈内容", Toast.LENGTH_SHORT).show();
			return;
		}

		dialog  = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
		dialog.setContentText("正在处理...") ;
		dialog.show();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(FeedbackActivity.this, "反馈成功,感谢您的支持~", Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				finish();
			}
		},2000) ;
	}
}
