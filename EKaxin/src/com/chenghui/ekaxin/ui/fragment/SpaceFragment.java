package com.chenghui.ekaxin.ui.fragment;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.SpaceBaseAdapter;
import com.chenghui.ekaxin.bean.SpaceDynamic;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.ui.CommentActivity;
import com.chenghui.ekaxin.ui.FragmentBase;
import com.chenghui.ekaxin.ui.EditCommentActivity.GridAdapter;
import com.chenghui.ekaxin.util.Constant;
import com.chenghui.ekaxin.view.CircleImageView;
import com.chenghui.ekaxin.view.list.SpaceListView;
import com.chenghui.ekaxin.view.list.SpaceListView.SpaceListViewListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * @ClassName: SpaceFragment
 * @Description: 空间
 * @author kcj
 * @date 
 */
public class SpaceFragment extends FragmentBase implements SpaceListViewListener{
	
	private int pageNum = 0;
	private SpaceListView listView;
	private SpaceBaseAdapter spaceBaseAdapter;
	
	// header 
	TextView tvUserName;
	TextView tvUserDay;
	TextView tvUserAll;
	CircleImageView imgView;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_space, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initListView();
	}
	
	void initListView() {
		listView = (SpaceListView) findViewById(R.id.lv_space);
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		RelativeLayout headView = (RelativeLayout) mInflater.inflate(R.layout.include_space_header, null);
		imgView = (CircleImageView) headView.findViewById(R.id.img_space_header_img);
		tvUserName = (TextView) headView.findViewById(R.id.rl_space_header_info_username);
		tvUserDay = (TextView) headView.findViewById(R.id.rl_space_header_info_statistics_day);
		tvUserAll = (TextView) headView.findViewById(R.id.rl_space_header_info_statistics_all);
		listView.addHeaderView(headView);
		spaceBaseAdapter = new SpaceBaseAdapter(getActivity());
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
				intent.setClass(getActivity(), CommentActivity.class);
				intent.putExtra("data", spaceBaseAdapter.getModel(position-2));
				startActivity(intent);
			}
		});
		fetchData();
	}
	
	public void fetchData(){
		BmobQuery<SpaceDynamic> query = new BmobQuery<SpaceDynamic>();
		query.order("-createdAt");
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		BmobDate date = new BmobDate(new Date(System.currentTimeMillis()));
		query.addWhereLessThan("createdAt", date);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.include("author");
        query.findObjects(getActivity(), new FindListener<SpaceDynamic>() {
			@Override
			public void onSuccess(List<SpaceDynamic> list) {
				// TODO Auto-generated method stub
				if(list.size()!=0&&list.get(list.size()-1)!=null){
					if(list.size()<Constant.NUMBERS_PER_PAGE){
						ShowToast( "已加载完所有数据~");
					}
					spaceBaseAdapter.addAllModel(list);
					spaceBaseAdapter.notifyDataSetChanged();
				}else{
					ShowToast( "暂无更多数据~");
					pageNum--;
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				//LogUtils.i(TAG,"find failed."+arg1);
				pageNum--;
			}
		});
	}
	
	public void onResume() {
		super.onResume();
		fetchHeaderData();
	}
	
	public void fetchHeaderData() {
		User user = BmobUser.getCurrentUser(mContext, User.class);
		tvUserName.setText(user.getUserChainName());
		tvUserDay.setText(user.getUserChainName());
		tvUserAll.setText(user.getUserChainName());
		/**
		 *   头像
		 */
		if(user != null){
			String avatarFile = user.getAvatar();
			if(null != avatarFile){
				ImageLoader.getInstance()
				.displayImage(avatarFile, imgView, 
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
	public void onRefresh() {
		// TODO Auto-generated method stub
		//refreshListViewInBackground();
		listView.stopRefresh();
	}
	
	void refreshListViewInBackground() {// 模拟刷新数据
		pageNum = 0;
		fetchData(); 
		onLoad();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		loadMoreInBackground();
	}
	
	void loadMoreInBackground() {
		onLoad();
	}

	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		//listView.setRefreshTime("刚刚");
	}
}
