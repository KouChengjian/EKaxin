package com.chenghui.ekaxin.ui;

import java.util.HashMap;

import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import com.chenghui.ekaxin.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.chenghui.ekaxin.util.CommonUtils;
import com.chenghui.ekaxin.view.CircleImageView;

/**
 * @ClassName: LoginActivity
 * @Description: 登入
 * @author kcj-
 * @date 2014-9-10
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
	EditText et_username, et_password;
	Button btn_login;
	TextView btn_register;
	CircleImageView circleImgView;
	View passwordView, nameView;
	// 填写从短信SDK应用后台注册得到的APPKEY
	private static String APPKEY = "46b1365cb283";
	// 填写从短信SDK应用后台注册得到的APPSECRET
	private static String APPSECRET = "335846477989df52a51728b777fbd118";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		et_username = (EditText) findViewById(R.id.et_login_username);
		et_password = (EditText) findViewById(R.id.et_login_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (TextView) findViewById(R.id.login_register);
		nameView = (View) findViewById(R.id.view_login_namefocus);
		passwordView = (View) findViewById(R.id.view_login_passwordfocus);
		circleImgView = (CircleImageView) findViewById(R.id.img_login_icon);
		Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.logo);
		Bitmap mBitmap = Bitmap.createScaledBitmap(btm1, 160, 160, true);
		circleImgView.setImageBitmap(mBitmap);
		nameView.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.edt_select));
		et_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					nameView.setBackgroundDrawable(getResources().getDrawable(
							R.drawable.edt_select));
				} else {// 失去焦点
					nameView.setBackgroundColor(Color.parseColor("#3a4147"));
				}
			}
		});
		et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					passwordView.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					passwordView.setBackgroundColor(Color.parseColor("#3a4147"));
				}
			}
		});
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// 注册
		if (v == btn_register) {
			RegisterPage registerPage = new RegisterPage();
			registerPage.setRegisterCallback(new EventHandler() {
				public void afterEvent(int event, int result, Object data) {
					// 解析注册结果
					if (result == SMSSDK.RESULT_COMPLETE) {
						@SuppressWarnings("unchecked")
						HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
						String country = (String) phoneMap.get("country");
						String phone = (String) phoneMap.get("phone");
						//Toast.makeText(LoginActivity.this, phone, 1).show();
						Intent intent = new Intent();
						intent.putExtra("account", phone);
						intent.setClass(LoginActivity.this, RegisterActivity.class);
						startActivity(intent);
					}
				}
			});
			registerPage.show(this);
		}
		// 登入
		else {
			// 检查网络
			boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
			if (!isNetConnected) {
				ShowToast(R.string.network_tips);
				return;
			}
			login();
		}
	}

	private void login() {
		String name = et_username.getText().toString();
		String password = et_password.getText().toString();

		if (TextUtils.isEmpty(name)) {
			ShowToast(R.string.login_error_account_null);
			return;
		}
		if (TextUtils.isEmpty(password)) {
			ShowToast(R.string.login_error_password_null);
			return;
		}

		// 对话框
		final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
		progress.setMessage("正在登陆...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		userManager.login(name, password, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						progress.setMessage("正在获取好友列表...");
					}
				});
				//更新用户的地理位置以及好友的资料
				updateUserInfos();
				progress.dismiss();
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			@Override
			public void onFailure(int errorcode, String arg0) {
				// TODO Auto-generated method stub
				progress.dismiss();
				BmobLog.i(arg0);
				ShowToast(arg0);
			}
		});
	}
}
