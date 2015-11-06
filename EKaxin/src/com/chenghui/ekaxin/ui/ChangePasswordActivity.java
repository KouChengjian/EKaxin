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
 * @Description: �޸�����
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
		initTopBarForBoth("�޸�����", R.drawable.base_action_bar_save_bg_selector,
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
				if (hasFocus) {// ��ý���
					formerPasswordView.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// ʧȥ����
					formerPasswordView.setBackgroundColor(Color.parseColor("#4a5059"));
				}
			}
		});
		et_presentpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// ��ý���
					presentPasswordOkView.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// ʧȥ����
					presentPasswordOkView.setBackgroundColor(Color.parseColor("#4a5059"));
				}
			}
		});
		et_confirmpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// ��ý���
					confirmPasswordOkView.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// ʧȥ����
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
		 // �������
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
