package com.chenghui.ekaxin.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.BmobConstants;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.util.CommonUtils;
import com.chenghui.ekaxin.util.Constant;


/**
 * @ClassName: SetPasswordActivity
 * @Description: 设置密码
 * @author kcj
 * @date
 */
public class RegisterActivity extends BaseActivity {
	ImageView imgView;
	Button btnAccomplish;
	View passwordView, passwordOkView;
	EditText et_password, et_passwordok;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		initView();
	}
	
	@SuppressWarnings("deprecation")
	public void initView(){
		initTopBarForLeft("设置密码");
		Intent intent = getIntent();
		account = intent.getStringExtra("account");
		btnAccomplish = (Button) findViewById(R.id.btn_setpassword_ok);
		imgView = (ImageView) findViewById(R.id.imgbut_setpassword_icon);
		imgView.setBackgroundDrawable(getResources().getDrawable(R.drawable.setpassword_show));
		et_password = (EditText) findViewById(R.id.et_setpassword_password);
		et_passwordok = (EditText) findViewById(R.id.et_setpassword_passwordok);
		passwordView = (View) findViewById(R.id.view_setpassword_focus);
		passwordOkView = (View) findViewById(R.id.view_setpassword_passwordfocus);
		initViewListen();
	}
	
	boolean press = false;
	public void initViewListen(){
		et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					passwordView.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					passwordView.setBackgroundColor(Color.parseColor("#4a5059"));
				}
			}
		});
		et_passwordok.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					passwordOkView.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					passwordOkView.setBackgroundColor(Color.parseColor("#4a5059"));
				}
			}
		});
		imgView.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
                if(!press){
                	et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                	et_passwordok.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                	imgView.setBackgroundDrawable(getResources().getDrawable(R.drawable.setpassword_show));
                	press = true;
                }else {
                	et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);  
                	et_passwordok.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                	imgView.setBackgroundDrawable(getResources().getDrawable(R.drawable.setpassword_hide));
                	press = false;
                }
			}
		});
		btnAccomplish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setPassword();
			}
		});
	}
	
	String account = null;
	String passwordfrist;
	String passwordsecond;
	void setPassword(){
		final ProgressDialog progress = new ProgressDialog(this);
		progress.setMessage("正在登陆...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		passwordfrist = et_password.getText().toString();
		passwordsecond = et_passwordok.getText().toString();
		if (TextUtils.isEmpty(account) ) {
			progress.dismiss();
			ShowToast(R.string.login_error_account_null);
			return;
		}
		if (TextUtils.isEmpty(passwordfrist) ) {
			progress.dismiss();
			ShowToast(R.string.setpassword_frist_null);
			return;
		}
		if (TextUtils.isEmpty(passwordsecond) ) {
			progress.dismiss();
			ShowToast(R.string.setpassword_second_null);
			return;
		}
		if(passwordfrist.length() < 6){
			progress.dismiss();
			ShowToast(R.string.setpassword_lessthan_six);
			return;
		}
		if(passwordsecond.length() < 6){
			progress.dismiss();
			ShowToast(R.string.setpassword_lessthan_six);
			return;
		}
		if (!passwordfrist.equals(passwordsecond)) {
			progress.dismiss();
			ShowToast(R.string.setpassword_import_error);
			return;
		}
		boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
		if (!isNetConnected) {
			progress.dismiss();
			ShowToast(R.string.network_tips);
			return;
		}
		
		final User bu = new User();
		bu.setUsername(account);
		bu.setPassword(passwordfrist);
		bu.setSex(Constant.SEX_MALE);
		bu.setSignature("这个家伙很懒，什么也不说。。。");
		//将user和设备id进行绑定
		bu.setDeviceType("android");
		bu.setInstallId(BmobInstallation.getInstallationId(this));
		bu.signUp(RegisterActivity.this, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progress.dismiss();
				ShowToast("注册成功");
				// 将设备与username进行绑定
				userManager.bindInstallationForRegister(bu.getUsername());
				//更新地理位置信息
				updateUserLocation();
				//发广播通知登陆页面退出
				sendBroadcast(new Intent(BmobConstants.ACTION_REGISTER_SUCCESS_FINISH));
				// 启动主页
				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				BmobLog.i(arg1);
				ShowToast("注册失败:" + arg1);
				progress.dismiss();
			}
		});
	}
}
