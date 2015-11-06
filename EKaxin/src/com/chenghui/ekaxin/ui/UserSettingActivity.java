package com.chenghui.ekaxin.ui;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.util.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserSettingActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener{

	RelativeLayout iconLayout;
	ImageView userIcon;
	RelativeLayout nickLayout;
	TextView nickName;
	RelativeLayout signLayout;
	TextView signature;
	CheckBox sexSwitch;
	TextView logout;
	User user;
	static final int UPDATE_SEX = 11;
	static final int UPDATE_ICON = 12;
	static final int GO_LOGIN = 13;
	static final int UPDATE_SIGN = 14;
	static final int EDIT_SIGN = 15;
	static final int UPDATE_NAME = 16;
	
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_usersettings);
		initTopBarForLeft("我");
		user = (User)getIntent().getSerializableExtra("data");
		iconLayout = (RelativeLayout)findViewById(R.id.user_icon);
		userIcon = (ImageView)findViewById(R.id.user_icon_image);
		nickLayout = (RelativeLayout)findViewById(R.id.user_nick);
		nickName = (TextView)findViewById(R.id.user_nick_text);
		signLayout = (RelativeLayout)findViewById(R.id.user_sign);
		signature = (TextView)findViewById(R.id.user_sign_text);
		sexSwitch = (CheckBox)findViewById(R.id.sex_choice_switch);
		logout = (TextView)findViewById(R.id.user_logout);
		iconLayout.setOnClickListener(this);
		nickLayout.setOnClickListener(this);
		signLayout.setOnClickListener(this);
		logout.setOnClickListener(this);
		sexSwitch.setOnCheckedChangeListener(this);
		filledData();
	}
	
	public void filledData(){
		if(user != null){
			nickName.setText(TextUtils.isEmpty(user.getUserChainName()) ? "请完善资料":user.getUserChainName());
			signature.setText(user.getSignature());
			if(! TextUtils.isEmpty(user.getSex())){
				if(user.getSex().equals(Constant.SEX_FEMALE) ){
					sexSwitch.setChecked(true);
					//sputil.setValue("sex_settings", 0);
				}else if(user.getSex().equals(Constant.SEX_MALE)){
					sexSwitch.setChecked(false);
					//sputil.setValue("sex_settings", 1);
				}
			}
			
			String avatarFile = user.getAvatar();
			if(null != avatarFile){
				ImageLoader.getInstance()
				.displayImage(avatarFile, userIcon, 
						CustomApplcation.getInstance().getOptions(R.drawable.user_icon_default_main),
						new SimpleImageLoadingListener(){

							@Override
							public void onLoadingComplete(String imageUri, View view,
									Bitmap loadedImage) {
								// TODO Auto-generated method stub
								super.onLoadingComplete(imageUri, view, loadedImage);
							}
				});
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.user_icon:
			break;
		case R.id.user_nick:
			if(isLogined()){
				Intent intent = new Intent();
				intent.setClass(mContext, EditNameActivity.class);
				startActivityForResult(intent, EDIT_SIGN);
			}else{
				redictToLogin(UPDATE_NAME);
			}
			break;
		case R.id.user_sign:
			if(isLogined()){
				Intent intent = new Intent();
				intent.setClass(mContext, EditSignActivity.class);
				startActivityForResult(intent, EDIT_SIGN);
			}else{
				redictToLogin(UPDATE_SIGN);
			}
			break;
		case R.id.user_logout:
		if(isLogined()){
			BmobUser.logOut(mContext);
			ShowToast( "登出成功。");
		}else{
			redictToLogin(GO_LOGIN);
		}
		break;
		}
		
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.sex_choice_switch:
			if(isChecked){
				//sputil.setValue("sex_settings", 0);
				updateSex(0);
			}else{
				//sputil.setValue("sex_settings", 1);
				updateSex(1);
			}
			break;
		}
	}

	/**
	 * 判断用户是否登录
	 * @return
	 */
	private boolean isLogined(){
		BmobUser user = BmobUser.getCurrentUser(mContext, User.class);
		if(user != null){
			return true;
		}
		return false;
	}
	
	/**
	 *  更新性别
	 */
	private void updateSex(int sex){
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if(user!=null){
			if(sex == 0){
				user.setSex(Constant.SEX_FEMALE);
			}else{
				user.setSex(Constant.SEX_MALE);
			}
			user.update(mContext, new UpdateListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					ShowToast("更新信息成功。");
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					ShowToast( "更新信息失败。请检查网络~");
				}
			});
		}else{
			redictToLogin(UPDATE_SEX);
		}
	}
	
	private void redictToLogin(int requestCode){
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivityForResult(intent, requestCode);
		ShowToast("请先登录。");
	}

}
