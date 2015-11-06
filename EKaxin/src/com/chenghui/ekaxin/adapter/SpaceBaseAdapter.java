package com.chenghui.ekaxin.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.listener.UpdateListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.SpaceDynamic;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.data.SpaceFinalVar;
import com.chenghui.ekaxin.data.SpaceModelItem;
import com.chenghui.ekaxin.ui.CommentActivity;
import com.chenghui.ekaxin.ui.LoginActivity;
import com.chenghui.ekaxin.ui.PersonalActivity;
import com.chenghui.ekaxin.util.ActivityUtil;
import com.nostra13.universalimageloader.core.ImageLoader;


import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @ClassName: SpaceBaseAdapter
 * @Description: 空间
 * @author kcj
 * @date 
 */
public class SpaceBaseAdapter extends BaseAdapter{
	private Context mContext;
	private List<SpaceDynamic> listViewData;
	
	public SpaceBaseAdapter(Context context) {
		this.mContext = context;
		listViewData = new ArrayList<SpaceDynamic>();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null == listViewData) {
			return 0;
		}
		return listViewData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listViewData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	/**
	 * 添加一条记录
	 * 
	 * @param model
	 */
	public void addModel(SpaceDynamic model) {
		listViewData.add(model);
	}
	
	public void addAllModel(List<SpaceDynamic> model) {
		listViewData.addAll(model);
	}

	/**
	 * 添加一条记录
	 * 
	 * @param model
	 * @param insertHead
	 *            true:插入在头部
	 */
	public void addModel(SpaceDynamic model, boolean insertHead) {
		if (insertHead) {
			listViewData.add(1, model);
		} else {
			listViewData.add(model);
		}
	}

	/**
	 * 获取一条记录
	 * 
	 * @param i
	 * @return
	 */
	public SpaceDynamic getModel(int i) {
		if (i < 0 || i > listViewData.size() - 1) {
			return null;
		}
		return listViewData.get(i);
	}

	/**
	 * 清除所有数据
	 */
	public void clear() {
		listViewData.clear();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewItemHolder viewItemHolder ;
		if (convertView == null) {
			viewItemHolder = new ViewItemHolder(); 
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_space_dynamic, null);
			viewItemHolder.userName = (TextView)convertView.findViewById(R.id.user_name); // 用户名
			viewItemHolder.userLogo = (ImageView)convertView.findViewById(R.id.user_logo); // logo
			viewItemHolder.favMark = (TextView)convertView.findViewById(R.id.item_action_fav); // 时间
			viewItemHolder.contentText = (TextView)convertView.findViewById(R.id.content_text); // 内容
			viewItemHolder.love = (TextView)convertView.findViewById(R.id.item_action_love); // 喜欢
			viewItemHolder.hate = (TextView)convertView.findViewById(R.id.item_action_hate); // 不喜欢
			viewItemHolder.share = (TextView)convertView.findViewById(R.id.item_action_share);// 分享
			viewItemHolder.comment = (TextView)convertView.findViewById(R.id.item_action_comment);// 评论
			// 新加的
			viewItemHolder.ll_content_images = (LinearLayout)convertView.findViewById(R.id.content_images);
			viewItemHolder.img_content_images_1 = (ImageView)convertView.findViewById(R.id.content_images_1); 
			viewItemHolder.img_content_images_2 = (ImageView)convertView.findViewById(R.id.content_images_2); 
			viewItemHolder.img_content_images_3 = (ImageView)convertView.findViewById(R.id.content_images_3); 
			convertView.setTag(viewItemHolder);
		} else {
			viewItemHolder = (ViewItemHolder) convertView.getTag();
		}
		Log.e("TAG",String.valueOf(position));
		final SpaceDynamic spaceDynamic = listViewData.get(position);
		User user = spaceDynamic.getAuthor();
		/**
		 *  头像
		 */
		if(user.getAvatar()==null){
			//LogUtils.i("user","USER avatar IS NULL");
		}
		String avatarUrl = null;
		if(user.getAvatar()!=null){
			avatarUrl = user.getAvatar();
		}
		ImageLoader.getInstance()
		.displayImage(avatarUrl, viewItemHolder.userLogo, 
				CustomApplcation.getInstance().getOptions(R.drawable.user_icon_default_main),
				new SimpleImageLoadingListener(){
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub
						super.onLoadingComplete(imageUri, view, loadedImage);
					}
			
		});
		viewItemHolder.userLogo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(CustomApplcation.getInstance().getCurrentUser()==null){
					ShowToast( "请先登录。");
					Intent intent = new Intent();
					intent.setClass(mContext, LoginActivity.class);
					mContext.startActivity(intent);
					return;
				}
				// 我的
				CustomApplcation.getInstance().setCurrentSpaceDynamic(spaceDynamic);
				startAnimActivity(PersonalActivity.class);
			}
		}); 
		/**
		 * 用户名
		 */
		if(TextUtils.isEmpty(spaceDynamic.getAuthor().getNick())){
			viewItemHolder.userName.setText(spaceDynamic.getAuthor().getUsername());
		}else{
			viewItemHolder.userName.setText(spaceDynamic.getAuthor().getNick());
		}
		/**
		 *  显示内容文字
		 */
		viewItemHolder.contentText.setText(spaceDynamic.getContent());
		/**
		 *  显示内容图片
		 */
		if(null == spaceDynamic.getContentfigureurl_1()){
			viewItemHolder.ll_content_images.setVisibility(View.GONE);
		}else{
			viewItemHolder.ll_content_images.setVisibility(View.VISIBLE);
			ImageLoader.getInstance()
			.displayImage(spaceDynamic.getContentfigureurl_1().getFileUrl()==null?"":spaceDynamic.getContentfigureurl_1().getFileUrl(), viewItemHolder.img_content_images_1, 
					CustomApplcation.getInstance().getOptions(R.drawable.bg_pic_loading),
					new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
							 float[] cons=ActivityUtil.getBitmapConfiguration(loadedImage, viewItemHolder.img_content_images_1, 1.0f);
	                         RelativeLayout.LayoutParams layoutParams=
	                             new RelativeLayout.LayoutParams((int)cons[0], (int)cons[1]);
	                         layoutParams.addRule(RelativeLayout.BELOW,R.id.content_text);
	                         viewItemHolder.img_content_images_1.setLayoutParams(layoutParams);
						}
			});
		}
		if(null == spaceDynamic.getContentfigureurl_2()){
			viewItemHolder.img_content_images_2.setVisibility(View.GONE);
		}else{
			viewItemHolder.img_content_images_2.setVisibility(View.VISIBLE);
			//entity.getContentfigureurl_2().loadImage(mContext,viewHolder.img_content_images_2);
			ImageLoader.getInstance()
			.displayImage(spaceDynamic.getContentfigureurl_2().getFileUrl()==null?"":spaceDynamic.getContentfigureurl_2().getFileUrl(), viewItemHolder.img_content_images_2, 
					CustomApplcation.getInstance().getOptions(R.drawable.bg_pic_loading),
					new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
							 float[] cons=ActivityUtil.getBitmapConfiguration(loadedImage, viewItemHolder.img_content_images_2, 1.0f);
	                         RelativeLayout.LayoutParams layoutParams=
	                             new RelativeLayout.LayoutParams((int)cons[0], (int)cons[1]);
	                         layoutParams.addRule(RelativeLayout.BELOW,R.id.content_text);
	                         viewItemHolder.img_content_images_2.setLayoutParams(layoutParams);
						}
			});
		}
		if(null == spaceDynamic.getContentfigureurl_3()){
			viewItemHolder.img_content_images_3.setVisibility(View.GONE);
		}else{
			viewItemHolder.img_content_images_3.setVisibility(View.VISIBLE);
			//entity.getContentfigureurl_3().loadImage(mContext,viewHolder.img_content_images_3);
			ImageLoader.getInstance()
			.displayImage(spaceDynamic.getContentfigureurl_3().getFileUrl()==null?"":spaceDynamic.getContentfigureurl_3().getFileUrl(), viewItemHolder.img_content_images_3, 
					CustomApplcation.getInstance().getOptions(R.drawable.bg_pic_loading),
					new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
							 float[] cons=ActivityUtil.getBitmapConfiguration(loadedImage, viewItemHolder.img_content_images_3, 1.0f);
	                         RelativeLayout.LayoutParams layoutParams=
	                             new RelativeLayout.LayoutParams((int)cons[0], (int)cons[1]);
	                         layoutParams.addRule(RelativeLayout.BELOW,R.id.content_text);
	                         viewItemHolder.img_content_images_3.setLayoutParams(layoutParams);
						}
			});
		}
		/**
		 * 点赞
		 */
		viewItemHolder.love.setText(spaceDynamic.getLove()+"");
		// 先设置字体颜色
		if(spaceDynamic.getMyLove()){
			viewItemHolder.love.setTextColor(Color.parseColor("#D95555"));
		}else{
			viewItemHolder.love.setTextColor(Color.parseColor("#000000"));
		}
		viewItemHolder.love.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(CustomApplcation.getInstance().getCurrentUser() == null){
					ShowToast("请先登录。");
					startAnimActivity(LoginActivity.class);
					return;
				}
				if(spaceDynamic.getMyLove()){
					ShowToast("您已赞过啦");
					return;
				}
				
				spaceDynamic.setLove(spaceDynamic.getLove()+1);
				spaceDynamic.increment("love",1);
				spaceDynamic.setMyLove(true);
				spaceDynamic.update(mContext, new UpdateListener() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						viewItemHolder.love.setTextColor(Color.parseColor("#D95555"));
						viewItemHolder.love.setText(spaceDynamic.getLove()+"");
						//LogUtils.i(TAG, "点赞成功~");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						spaceDynamic.setMyLove(false);
					}
				});
			}
		});
		/**
		 *  评论
		 */
		viewItemHolder.comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//评论
				if(CustomApplcation.getInstance().getCurrentUser()==null){
					ShowToast("请先登录。");
					startAnimActivity(LoginActivity.class);
					return;
				}
				Intent intent = new Intent();
				intent.setClass(mContext, CommentActivity.class);
				intent.putExtra("data", spaceDynamic);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}
	
	class ViewItemHolder {
		public ImageView userLogo;
		public TextView userName;
		public TextView contentText;
		
		public TextView favMark;
		public TextView love;
		public TextView hate;
		public TextView share;
		public TextView comment;
		
		LinearLayout ll_content_images;
		public ImageView img_content_images_1;
		public ImageView img_content_images_2;
		public ImageView img_content_images_3;
		/**  
		ImageView imgHead;
		TextView tvName;
		TextView tvDate;
		TextView tvContent;
		//ImageView ivPhoto;
		ImageView ivAddress;
		TextView tvAddress;
		ImageView ivAgree;
		TextView tvPhonemodel;
		ImageView ivComment;
		TextView tvComment;
		ImageView ivAgreeShow;
		TextView tvAgreeShow;
		Button btnComment;
		TextView tvComments;
		
		RelativeLayout ll_content_images;
		public ImageView img_content_images_1;
		public ImageView img_content_images_2;
		public ImageView img_content_images_3;*/
	}
	
	public void ShowToast(String string){
		Toast.makeText(mContext, string,Toast.LENGTH_LONG).show();
	}
	
	public void startAnimActivity(Class<?> cla) {
		mContext.startActivity(new Intent(mContext, cla));
	}

}
