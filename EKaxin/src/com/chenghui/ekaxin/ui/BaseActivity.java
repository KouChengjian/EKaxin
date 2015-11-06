package com.chenghui.ekaxin.ui;

import java.util.Date;
import java.util.List;























import java.util.Map;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.User;


import com.chenghui.ekaxin.util.CollectionUtils;
import com.chenghui.ekaxin.util.Constant;
import com.chenghui.ekaxin.util.NotepadInfo;
import com.chenghui.ekaxin.util.UserCharDB;
import com.chenghui.ekaxin.view.HeaderLayout;
import com.chenghui.ekaxin.view.HeaderLayout.HeaderStyle;
import com.chenghui.ekaxin.view.HeaderLayout.onLeftImageButtonClickListener;
import com.chenghui.ekaxin.view.HeaderLayout.onRightImageButtonClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/** 基类
 * @ClassName: BaseActivity
 * @Description: TODO
 * @author smile
 * @date 2014-9-12 10:30
 */
public class BaseActivity extends FragmentActivity{
	
	protected HeaderLayout mHeaderLayout;
	protected int mScreenWidth;
	protected int mScreenHeight;
	CustomApplcation mApplication;
	// 用户管理
	BmobUserManager userManager;
	// 聊天管理
	BmobChatManager manager;
	public Context mContext;
	Toast mToast;

	// 数据库
	UserCharDB userCharDB ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		userCharDB = new UserCharDB(this);
		userCharDB.open();
		userManager = BmobUserManager.getInstance(this);
		manager = BmobChatManager.getInstance(this);
		mApplication = CustomApplcation.getInstance();
		// 获取手机分辨率
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
	}
	
	/**
	 *  显示数据String
	 *  Toast
	 */
	public void ShowToast(final String text) 
	{
		if (!TextUtils.isEmpty(text)) 
		{
			runOnUiThread(new Runnable() 
			{
				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					if (mToast == null) {
						mToast = Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_LONG);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});
		}
	}
	
	/**
	 *  显示数据int
	 *  Toast
	 */
	public void ShowToast(final int resId) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast == null) {
					mToast = Toast.makeText(BaseActivity.this.getApplicationContext(), resId,
							Toast.LENGTH_LONG);
				} else {
					mToast.setText(resId);
				}
				mToast.show();
			}
		});
	}
	
	/** 打Log
	  * ShowLog
	  * @return void
	  * @throws
	  */
	public void ShowLog(String msg){
		BmobLog.i(msg);
	}
	
	// 左边按钮的点击事件
	public class OnLeftButtonClickListener implements 
	onLeftImageButtonClickListener {
        @Override
        public void onClick(View v) {
	        finish();
        }
    }	
	
	/**
	 *  界面跳转
	 */
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}
	
	/**
	 * 只有title initTopBarLayoutByTitle
	 * @Title: initTopBarLayoutByTitle
	 * @throws
	 * kcj 标题
	 */
	public void initTopBarForOnlyTitle(String titleName) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle(titleName);
	}

	/**
	 * 初始化标题栏-带左右按钮
	 * @return void
	 * @throws
	 * kcj 标题加左右按钮  (右边按钮有文字)
	 */
	public void initTopBarForBoth(String titleName, int rightDrawableId,String text,
			onRightImageButtonClickListener listener) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton(titleName,
				R.drawable.base_action_bar_backs_bg_selector,
				new OnLeftButtonClickListener());
		mHeaderLayout.setTitleAndRightButton(titleName, rightDrawableId,text,
				listener);
	}
	
	/**
	 * 初始化标题栏-带左右按钮
	 * @return void
	 * @throws
	 * kcj 标题加左右按钮
	 */
	public void initTopBarForBoth(String titleName, int rightDrawableId,
			onRightImageButtonClickListener listener) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton(titleName,
				R.drawable.base_action_bar_backs_bg_selector,
				new OnLeftButtonClickListener());
		mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,
				listener);
	}

	/**
	 * 只有左边按钮和Title initTopBarLayout
	 * 
	 * @throws
	 * kcj 标题加左按钮
	 */
	public void initTopBarForLeft(String titleName) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton(titleName,
				R.drawable.base_action_bar_backs_bg_selector,
				new OnLeftButtonClickListener());
	}
	
	/** 右边+title
	  * initTopBarForRight
	  * @return void
	  * @throws
	  */
	public void initTopBarForRight(String titleName,int rightDrawableId,
			onRightImageButtonClickListener listener) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_RIGHT_IMAGEBUTTON);
		mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,
				listener);
	}
	
	/** kcj
	 *  自定义标题栏-带左右按钮
	 */
	public void initTopBarForBoth(String titleName, int rightDrawableId,
			onRightImageButtonClickListener listener,int leftDrawableId,onLeftImageButtonClickListener leftlistener) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
		
		mHeaderLayout.setTitleAndLeftButton(titleName,leftDrawableId,
				leftlistener);
		
		mHeaderLayout.setTitleAndRightImageButton(titleName, rightDrawableId,
				listener);
	}
	
	/** 用于登陆或者自动登陆情况下的用户资料及好友资料的检测更新
	  * @Title: updateUserInfos
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	public void updateUserInfos(){
		//更新地理位置信息
		updateUserLocation();
		updateUserCard();
		//查询该用户的好友列表(这个好友列表是去除黑名单用户的哦),目前支持的查询好友个数为100，如需修改请在调用这个方法前设置BmobConfig.LIMIT_CONTACTS即可。
		//这里默认采取的是登陆成功之后即将好于列表存储到数据库中，并更新到当前内存中,
		userManager.queryCurrentContactList(new FindListener<BmobChatUser>() {
					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if(arg0==BmobConfig.CODE_COMMON_NONE){
							ShowLog(arg1);
						}else{
							ShowLog("查询好友列表失败："+arg1);
						}
					}

					@Override
					public void onSuccess(List<BmobChatUser> arg0) {
						// TODO Auto-generated method stub
						// 保存到application中方便比较
						CustomApplcation.getInstance().setContactList(CollectionUtils.list2map(arg0));
						// 更新名片信息
						updateCharDB(arg0);
					}
				});
	}
	
	/** 更新用户的经纬度信息
	  * @Title: uploadLocation
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	public void updateUserLocation(){
		if(CustomApplcation.lastPoint!=null){
			String saveLatitude  = mApplication.getLatitude();
			String saveLongtitude = mApplication.getLongtitude();
			String newLat = String.valueOf(CustomApplcation.lastPoint.getLatitude());
			String newLong = String.valueOf(CustomApplcation.lastPoint.getLongitude());
			if(!saveLatitude.equals(newLat)|| !saveLongtitude.equals(newLong)){//只有位置有变化就更新当前位置，达到实时更新的目的
				final User user = (User) userManager.getCurrentUser(User.class);
				user.setLocation(CustomApplcation.lastPoint);
				user.update(this, new UpdateListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						CustomApplcation.getInstance().setLatitude(String.valueOf(user.getLocation().getLatitude()));
						CustomApplcation.getInstance().setLongtitude(String.valueOf(user.getLocation().getLongitude()));
                        // ShowLog("经纬度更新成功");
						Log.e("BaseActivity","经纬度更新成功");
					}
					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
                        // ShowLog("经纬度更新 失败:"+msg);
					}
				});
			}else{
                // ShowLog("用户位置未发生过变化");
			}
		}
	}
	
	public void updateUserCard(){
		final User user = (User) userManager.getCurrentUser(User.class);
		userManager.queryUser(user.getUsername(), new FindListener<User>() {
				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
				}
				@Override
				public void onSuccess(List<User> arg0) {
					// TODO Auto-generated method stub
					if (arg0 != null && arg0.size() > 0) {
						//MainActivity.currentUserCard = arg0.get(0);
						updateUserInfo(arg0.get(0));
					} 
				}
			});
		}
	
	public void updateUserInfo(User user){
		User[] camera = userCharDB.queryOneData(user.getUsername(),UserCharDB.DB_TABLE);
		if (camera == null) {
			Log.e("camera","无个人信息");
			//Toast.makeText(mContext, "无个人信息",Toast.LENGTH_LONG).show();
			// 将数据添加入数据库中
			long colunm = userCharDB.insert(user,UserCharDB.DB_TABLE);
			if (colunm == -1) {
				Log.e("camera","添加过程错误！");
				//Toast.makeText(mContext, "添加过程错误！",Toast.LENGTH_LONG).show();
			} else {
				Log.e("camera","成功添加数据ID：" + String.valueOf(colunm));
				//Toast.makeText(mContext,"成功添加数据ID：" + String.valueOf(colunm),Toast.LENGTH_LONG).show();
			}
		}else {
			if(camera[0].getUserUpdateAt().equals(user.getUpdatedAt())){
				Log.e("camera","无更新数据");
				//Toast.makeText(mContext, "无更新数据",Toast.LENGTH_LONG).show();
			}else{
				long colunm = userCharDB.updateOneData(user.getUsername(),user,UserCharDB.DB_TABLE);
				if (colunm == -1) {
					Log.e("camera","更新过程错误！");
					//Toast.makeText(mContext, "更新过程错误！",Toast.LENGTH_LONG).show();
				} else {
					Log.e("camera","更新成功" + String.valueOf(colunm));
					//Toast.makeText(mContext,"更新成功" + String.valueOf(colunm),Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	
	/**
	 *  更新或获取好友的数据
	 */
    public void updateCharDB(final List<BmobChatUser> arg0) {
    	try {
    		((Activity) mContext).runOnUiThread(new Runnable() {
				public void run() {
					queryMyfriendsDB(arg0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void queryMyfriendsDB(List<BmobChatUser> list){
    	User user = userManager.getCurrentUser(User.class);
    	BmobQuery<User> query = new BmobQuery<User>();
    	query.addWhereRelatedTo("contacts", new BmobPointer(user));
		query.order("-createdAt");
		query.setLimit(500);
		BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
		query.addWhereLessThan("createdAt", date);
		query.setSkip(0);
		query.include("author");
		query.findObjects(mContext, new FindListener<User>() {

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(mContext,"获取联系人详细信息失败"+arg1,Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(List<User> list) {
				// TODO Auto-generated method stub
				for(int i = 0 ; i < list.size() ; i ++){
					User user = list.get(i);
					User[] camera = userCharDB.queryOneData(user.getUsername() , UserCharDB.DB_CHAR_TABLE);
					if (camera == null) {
						long colunm = userCharDB.insert(user , UserCharDB.DB_CHAR_TABLE);
						if (colunm == -1) {
							//Log.e("camera","添加过程错误！");
							Toast.makeText(mContext, "添加好友过程错误！",Toast.LENGTH_LONG).show();
						} else {
							//Log.e("camera","成功添加数据ID：" + String.valueOf(colunm));
							Toast.makeText(mContext,"成功好友添加数据ID：" + String.valueOf(colunm),Toast.LENGTH_LONG).show();
						}
					}else{
						if(camera[0].getUserUpdateAt().equals(user.getUpdatedAt())){
							//Log.e("camera","无更新数据");
							Toast.makeText(mContext, "无更新好友数据",Toast.LENGTH_LONG).show();
						}else{
							long colunm = userCharDB.updateOneData(user.getUsername() , user , UserCharDB.DB_CHAR_TABLE);
							if (colunm == -1) {
								//Log.e("camera","更新过程错误！");
								Toast.makeText(mContext, "更新好友过程错误！",Toast.LENGTH_LONG).show();
							} else {
								//Log.e("camera","更新成功" + String.valueOf(colunm));
								Toast.makeText(mContext,"更新好友成功" + String.valueOf(colunm),Toast.LENGTH_LONG).show();
							}
						}
					}
				}
			}
		});
    }
}

