package com.chenghui.ekaxin.ui.fragment;

import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import cn.bmob.v3.listener.FindListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.ui.FragmentBase;
import com.chenghui.ekaxin.ui.MainActivity;
import com.chenghui.ekaxin.ui.UserSettingActivity;
import com.chenghui.ekaxin.util.UserCharDB;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * 用户信息
 * @ClassName: UserSetFragment
 * @Description: TODO
 * @author kcj
 * @date 2014-10-6 10:20
 */
public class UserSettingFragment extends FragmentBase implements OnClickListener,OnCheckedChangeListener{
	
	RelativeLayout iconLayout;
	ImageView userIcon;
	TextView userTextName;
	TextView userTextId;
	RelativeLayout update ;
	RelativeLayout cleanCache;
	RelativeLayout feedback;
	
	CheckBox searchSwitch;
	CheckBox remindSwitch;
	CheckBox safetySwitch;
	
	public User user  ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		User users = userManager.getCurrentUser(User.class);
		User[] camera = userCharDB.queryOneData(users.getUsername(),UserCharDB.DB_TABLE);
		user = camera[0];
		return inflater.inflate(R.layout.fragment_usersetting, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initPersonalInfo();
	}
	
	public void initView(){
		initTopBarForOnlyTitle("设置") ;
		iconLayout = (RelativeLayout)findViewById(R.id.usersetting_icon);
		userIcon = (ImageView)findViewById(R.id.usersetting_icon_image);
		userTextName = (TextView)findViewById(R.id.usersetting_icon_text_name);
		userTextId = (TextView)findViewById(R.id.usersetting_icon_text_id);
		update = (RelativeLayout)findViewById(R.id.usersetting_update);
		cleanCache = (RelativeLayout)findViewById(R.id.usersetting_cache);
		feedback = (RelativeLayout)findViewById(R.id.usersetting_feedback);	
		searchSwitch = (CheckBox)findViewById(R.id.usersetting_search_switch);
		remindSwitch = (CheckBox)findViewById(R.id.usersetting_remind_switch);
		safetySwitch = (CheckBox)findViewById(R.id.usersetting_safety_switch);
		
		searchSwitch.setOnCheckedChangeListener(this);
		remindSwitch.setOnCheckedChangeListener(this);
		safetySwitch.setOnCheckedChangeListener(this);
		iconLayout.setOnClickListener(this);
		update.setOnClickListener(this);
		cleanCache.setOnClickListener(this);
		feedback.setOnClickListener(this);
	}
	
	private void initPersonalInfo(){
		//User user = BmobUser.getCurrentUser(getActivity(),User.class);
		
		//initOtherData(user.getUsername());
		if(user != null){
			userTextName.setText(TextUtils.isEmpty(user.getUserChainName()) ? "请完善资料":user.getUserChainName());
			userTextId.setText("id : "+user.getUsername());
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
	
	private void initOtherData(String name) {
		userManager.queryUser(name, new FindListener<User>() {
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onSuccess(List<User> arg0) {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.size() > 0) {
					//user = arg0.get(0);
					//userTextName.setText(user.getUserChainName());
				} else {
				}
			}
		});
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch (buttonView.getId()) {
		case R.id.usersetting_search_switch:
			break;
		case R.id.usersetting_remind_switch:
			break;
		case R.id.usersetting_safety_switch:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.usersetting_icon:
			Intent intent = new Intent();
			intent.setClass(getActivity(), UserSettingActivity.class);
			intent.putExtra("data", user);
			startActivity(intent);
			break;
		case R.id.usersetting_update:

			break;
		case R.id.usersetting_cache:

			break;
		case R.id.usersetting_feedback:

			break;
		//case R.id.user_cache:

		//	break;
		}
	}
}
