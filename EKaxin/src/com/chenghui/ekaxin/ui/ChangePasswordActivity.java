package com.chenghui.ekaxin.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.chenghui.ekaxin.R;

import com.chenghui.ekaxin.util.CommonUtils;
import com.chenghui.ekaxin.view.HeaderLayout.onRightImageButtonClickListener;

/**
 * @ClassName: ChangePasswordActivity
 * @Description: 修改密码
 * @author kcj
 * @date 2014-10-16
 */
public class ChangePasswordActivity extends BaseActivity{
	EditText et_formerpassword, et_presentpassword, et_confirmpassword;
	View formerPasswordView, presentPasswordOkView, confirmPasswordOkView;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepassword);
		initView();

	}
	
	void initView(){
		initTopBarForBoth("修改密码", R.drawable.base_action_bar_save_bg_selector,
			new onRightImageButtonClickListener() {
			    @Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
			    	setpassword();			
				}
			});
		et_formerpassword = (EditText) findViewById(R.id.et_amendpassword_formerpassword);
		et_presentpassword = (EditText) findViewById(R.id.et_amendpassword_presentpassword);
		et_confirmpassword = (EditText) findViewById(R.id.et_setpassword_confirmpassword);
		formerPasswordView = (View) findViewById(R.id.view_amendpassword_formerpassword_focus);
		presentPasswordOkView = (View) findViewById(R.id.view_amendpassword_presentpassword_focus);
		confirmPasswordOkView = (View) findViewById(R.id.view_amendpassword_confirmpassword_focus);
		initlisten();
	}
	
	void initlisten(){
		et_formerpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					formerPasswordView.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					formerPasswordView.setBackgroundColor(Color.parseColor("#4a5059"));
				}
			}
		});
		et_presentpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					presentPasswordOkView.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					presentPasswordOkView.setBackgroundColor(Color.parseColor("#4a5059"));
				}
			}
		});
		et_confirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					confirmPasswordOkView.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					confirmPasswordOkView.setBackgroundColor(Color.parseColor("#4a5059"));
				}
			}
		});
	}
	
	String formerpassword,presentpassword,confirmpassword;
	
	void setpassword(){
		formerpassword = et_formerpassword.getText().toString();
		presentpassword = et_presentpassword.getText().toString();
		confirmpassword = et_confirmpassword.getText().toString();
		if(presentpassword.length() < 6){
			ShowToast(R.string.setpassword_lessthan_six);
			return;
		}
		if(confirmpassword.length() < 6){
			ShowToast(R.string.setpassword_lessthan_six);
			return;
		}
		if(!presentpassword.contains(confirmpassword)){
			ShowToast(R.string.setpassword_import_error);
			return;
		}
		 // 检查网络
		boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
		if (!isNetConnected) {
			ShowToast(R.string.network_tips);
			return;
		}
		try {
			/** 
			//EKaXinUserManger.getInstance().amendPassword(formerpassword,confirmpassword, new AmendPasswordListener()
			{
				public void onSuccess() {
					finish();
				}
				public void onFailure(int paramInt, String paramString) {
					ShowToast(paramString);
				}

			}); */
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
