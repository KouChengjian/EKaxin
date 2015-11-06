package com.chenghui.ekaxin.ui;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.PersonCenterContentAdapter;
import com.chenghui.ekaxin.adapter.SpaceBaseAdapter;
import com.chenghui.ekaxin.bean.SpaceDynamic;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.util.Constant;
import com.chenghui.ekaxin.view.list.SpaceListView;
import com.chenghui.ekaxin.view.list.SpaceListView.SpaceListViewListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @ClassName: PersonalActivity
 * @Description: 个人界面
 * @author kcj
 * @date 
 */
public class PersonalActivity extends BaseActivity implements OnClickListener , SpaceListViewListener{
	
	private ImageView personalIcon;
	private TextView personalName;
	private TextView personalSign;
	private ImageView goSettings;
	private TextView personalTitle;
	
	private User mUser ;
	protected Context mContext;
	
	private int pageNum = 0;
	private SpaceListView listView;
	private List<SpaceDynamic> listViewData = new ArrayList<SpaceDynamic>();
	private PersonCenterContentAdapter spaceBaseAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal);
		mContext = this;
		initTopBarForLeft("个人信息");
		/** UI */
		personalIcon = (ImageView)findViewById(R.id.personal_icon);
		personalName = (TextView)findViewById(R.id.personl_name);
		personalSign = (TextView)findViewById(R.id.personl_signature);
		goSettings = (ImageView)findViewById(R.id.go_settings);
		personalTitle = (TextView)findViewById(R.id.personl_title);
		
		personalIcon.setOnClickListener(this);
		personalSign.setOnClickListener(this);
		personalTitle.setOnClickListener(this);
		goSettings.setOnClickListener(this);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mUser = CustomApplcation.getInstance().getCurrentSpaceDynamic().getAuthor();
        updatePersonalInfo(mUser);
		initMyPublish();
		fetchData();
	}
	
	private void updatePersonalInfo(User user) {
		personalName.setText(user.getUsername());
		if(TextUtils.isEmpty(user.getSignature())){
			personalSign.setText("这个人很懒，什么都没有留下~~~~");
		}else{
			personalSign.setText(user.getSignature());
		}
		if(user.getAvatar() != null){
			ImageLoader.getInstance()
			.displayImage(user.getAvatar(), personalIcon, 
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
	
	private void initMyPublish() {
		if(isCurrentUser(mUser)){
			personalTitle.setText("我发表过的");
			goSettings.setVisibility(View.VISIBLE);
			User user = BmobUser.getCurrentUser(mContext, User.class);
			updatePersonalInfo(user);
		}else{
			goSettings.setVisibility(View.GONE);
			//if(mUser !=null && mUser.getSex().equals(Constant.SEX_FEMALE)){
				personalTitle.setText("她发表过的");
			//}else if(mUser !=null && mUser.getSex().equals(Constant.SEX_MALE)){
			//	personalTitle.setText("他发表过的");
			//}
		}
		
		listView = (SpaceListView) findViewById(R.id.lv_personal);
		spaceBaseAdapter = new PersonCenterContentAdapter(this,listViewData);
		listView.setAdapter(spaceBaseAdapter);
		listView.setSpaceListViewListener(this);// 添加XListView的上拉和下拉刷新监听器
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
			//	intent.setClass(this, CommentActivity.class);
				//intent.putExtra("data", spaceBaseAdapter.getModel(position-2));
				//startActivity(intent);
			}
		});
	}
	
	/**
	 * 判断点击条目的用户是否是当前登录用户
	 * @return
	 */
	private boolean isCurrentUser(User user){
		if(null != user){
			User cUser = BmobUser.getCurrentUser(mContext, User.class);
			if(cUser != null && cUser.getObjectId().equals(user.getObjectId())){
				return true;
			}
		}
		return false;
	}
	
	protected void fetchData() {
		// TODO Auto-generated method stub
		BmobQuery<SpaceDynamic> query = new BmobQuery<SpaceDynamic>();
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.order("-createdAt");
		query.include("author");
		query.addWhereEqualTo("author", mUser);
        query.findObjects(mContext, new FindListener<SpaceDynamic>() {
			@Override
			public void onSuccess(List<SpaceDynamic> data) {
				// TODO Auto-generated method stub
				if(data.size()!=0&&data.get(data.size()-1)!=null){
					if(data.size()<Constant.NUMBERS_PER_PAGE){
						ShowToast( "已加载完所有数据~");
					}
					listViewData.addAll(data);
					spaceBaseAdapter.notifyDataSetChanged();
				}else{
					ShowToast( "暂无更多数据~");
					pageNum--;
				}
			}
			@Override
			public void onError(int arg0, String msg) {
				// TODO Auto-generated method stub
				ShowToast( "加载失败~");
				pageNum--;
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

}
