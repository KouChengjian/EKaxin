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

/** ����
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
	// �û�����
	BmobUserManager userManager;
	// �������
	BmobChatManager manager;
	public Context mContext;
	Toast mToast;

	// ���ݿ�
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
		// ��ȡ�ֻ��ֱ���
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
	}
	
	/**
	 *  ��ʾ����String
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
	 *  ��ʾ����int
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
	
	/** ��Log
	  * ShowLog
	  * @return void
	  * @throws
	  */
	public void ShowLog(String msg){
		BmobLog.i(msg);
	}
	
	// ��߰�ť�ĵ���¼�
	public class OnLeftButtonClickListener implements 
	onLeftImageButtonClickListener {
        @Override
        public void onClick(View v) {
	        finish();
        }
    }	
	
	/**
	 *  ������ת
	 */
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}
	
	/**
	 * ֻ��title initTopBarLayoutByTitle
	 * @Title: initTopBarLayoutByTitle
	 * @throws
	 * kcj ����
	 */
	public void initTopBarForOnlyTitle(String titleName) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.DEFAULT_TITLE);
		mHeaderLayout.setDefaultTitle(titleName);
	}

	/**
	 * ��ʼ��������-�����Ұ�ť
	 * @return void
	 * @throws
	 * kcj ��������Ұ�ť  (�ұ߰�ť������)
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
	 * ��ʼ��������-�����Ұ�ť
	 * @return void
	 * @throws
	 * kcj ��������Ұ�ť
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
	 * ֻ����߰�ť��Title initTopBarLayout
	 * 
	 * @throws
	 * kcj �������ť
	 */
	public void initTopBarForLeft(String titleName) {
		mHeaderLayout = (HeaderLayout)findViewById(R.id.common_actionbar);
		mHeaderLayout.init(HeaderStyle.TITLE_DOUBLE_IMAGEBUTTON);
		mHeaderLayout.setTitleAndLeftImageButton(titleName,
				R.drawable.base_action_bar_backs_bg_selector,
				new OnLeftButtonClickListener());
	}
	
	/** �ұ�+title
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
	 *  �Զ��������-�����Ұ�ť
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
	
	/** ���ڵ�½�����Զ���½����µ��û����ϼ��������ϵļ�����
	  * @Title: updateUserInfos
	  * @Description: TODO
	  * @param  
	  * @return void
	  * @throws
	  */
	public void updateUserInfos(){
		//���µ���λ����Ϣ
		updateUserLocation();
		updateUserCard();
		//��ѯ���û��ĺ����б�(��������б���ȥ���������û���Ŷ),Ŀǰ֧�ֵĲ�ѯ���Ѹ���Ϊ100�������޸����ڵ����������ǰ����BmobConfig.LIMIT_CONTACTS���ɡ�
		//����Ĭ�ϲ�ȡ���ǵ�½�ɹ�֮�󼴽������б�洢�����ݿ��У������µ���ǰ�ڴ���,
		userManager.queryCurrentContactList(new FindListener<BmobChatUser>() {
					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						if(arg0==BmobConfig.CODE_COMMON_NONE){
							ShowLog(arg1);
						}else{
							ShowLog("��ѯ�����б�ʧ�ܣ�"+arg1);
						}
					}

					@Override
					public void onSuccess(List<BmobChatUser> arg0) {
						// TODO Auto-generated method stub
						// ���浽application�з���Ƚ�
						CustomApplcation.getInstance().setContactList(CollectionUtils.list2map(arg0));
						// ������Ƭ��Ϣ
						updateCharDB(arg0);
					}
				});
	}
	
	/** �����û��ľ�γ����Ϣ
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
			if(!saveLatitude.equals(newLat)|| !saveLongtitude.equals(newLong)){//ֻ��λ���б仯�͸��µ�ǰλ�ã��ﵽʵʱ���µ�Ŀ��
				final User user = (User) userManager.getCurrentUser(User.class);
				user.setLocation(CustomApplcation.lastPoint);
				user.update(this, new UpdateListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						CustomApplcation.getInstance().setLatitude(String.valueOf(user.getLocation().getLatitude()));
						CustomApplcation.getInstance().setLongtitude(String.valueOf(user.getLocation().getLongitude()));
                        // ShowLog("��γ�ȸ��³ɹ�");
						Log.e("BaseActivity","��γ�ȸ��³ɹ�");
					}
					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
                        // ShowLog("��γ�ȸ��� ʧ��:"+msg);
					}
				});
			}else{
                // ShowLog("�û�λ��δ�������仯");
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
			Log.e("camera","�޸�����Ϣ");
			//Toast.makeText(mContext, "�޸�����Ϣ",Toast.LENGTH_LONG).show();
			// ��������������ݿ���
			long colunm = userCharDB.insert(user,UserCharDB.DB_TABLE);
			if (colunm == -1) {
				Log.e("camera","��ӹ��̴���");
				//Toast.makeText(mContext, "��ӹ��̴���",Toast.LENGTH_LONG).show();
			} else {
				Log.e("camera","�ɹ��������ID��" + String.valueOf(colunm));
				//Toast.makeText(mContext,"�ɹ��������ID��" + String.valueOf(colunm),Toast.LENGTH_LONG).show();
			}
		}else {
			if(camera[0].getUserUpdateAt().equals(user.getUpdatedAt())){
				Log.e("camera","�޸�������");
				//Toast.makeText(mContext, "�޸�������",Toast.LENGTH_LONG).show();
			}else{
				long colunm = userCharDB.updateOneData(user.getUsername(),user,UserCharDB.DB_TABLE);
				if (colunm == -1) {
					Log.e("camera","���¹��̴���");
					//Toast.makeText(mContext, "���¹��̴���",Toast.LENGTH_LONG).show();
				} else {
					Log.e("camera","���³ɹ�" + String.valueOf(colunm));
					//Toast.makeText(mContext,"���³ɹ�" + String.valueOf(colunm),Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	
	/**
	 *  ���»��ȡ���ѵ�����
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
				Toast.makeText(mContext,"��ȡ��ϵ����ϸ��Ϣʧ��"+arg1,Toast.LENGTH_LONG).show();
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
							//Log.e("camera","��ӹ��̴���");
							Toast.makeText(mContext, "��Ӻ��ѹ��̴���",Toast.LENGTH_LONG).show();
						} else {
							//Log.e("camera","�ɹ��������ID��" + String.valueOf(colunm));
							Toast.makeText(mContext,"�ɹ������������ID��" + String.valueOf(colunm),Toast.LENGTH_LONG).show();
						}
					}else{
						if(camera[0].getUserUpdateAt().equals(user.getUpdatedAt())){
							//Log.e("camera","�޸�������");
							Toast.makeText(mContext, "�޸��º�������",Toast.LENGTH_LONG).show();
						}else{
							long colunm = userCharDB.updateOneData(user.getUsername() , user , UserCharDB.DB_CHAR_TABLE);
							if (colunm == -1) {
								//Log.e("camera","���¹��̴���");
								Toast.makeText(mContext, "���º��ѹ��̴���",Toast.LENGTH_LONG).show();
							} else {
								//Log.e("camera","���³ɹ�" + String.valueOf(colunm));
								Toast.makeText(mContext,"���º��ѳɹ�" + String.valueOf(colunm),Toast.LENGTH_LONG).show();
							}
						}
					}
				}
			}
		});
    }
}

