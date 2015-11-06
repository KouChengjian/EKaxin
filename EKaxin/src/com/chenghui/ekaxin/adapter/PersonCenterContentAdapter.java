package com.chenghui.ekaxin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.listener.UpdateListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.base.BaseContentAdapter;
import com.chenghui.ekaxin.bean.SpaceDynamic;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.ui.CommentActivity;
import com.chenghui.ekaxin.ui.LoginActivity;
import com.chenghui.ekaxin.util.ActivityUtil;
import com.chenghui.ekaxin.util.DatabaseUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;


public class PersonCenterContentAdapter extends BaseContentAdapter<SpaceDynamic>{

	public PersonCenterContentAdapter(Context context, List<SpaceDynamic> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getConvertView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 final ViewHolder viewHolder;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_space_dynamic, null);
				/**  
				viewHolder.userName = (TextView)convertView.findViewById(R.id.user_name); // 用户名
				viewHolder.userLogo = (ImageView)convertView.findViewById(R.id.user_logo); // logo
				viewHolder.favMark = (ImageView)convertView.findViewById(R.id.item_action_fav); // 关注
				viewHolder.contentText = (TextView)convertView.findViewById(R.id.content_text); // 内容
				//viewHolder.contentImage = (ImageView)convertView.findViewById(R.id.content_image);// 内容图片
				viewHolder.love = (TextView)convertView.findViewById(R.id.item_action_love); // 喜欢
				viewHolder.hate = (TextView)convertView.findViewById(R.id.item_action_hate); // 不喜欢
				viewHolder.share = (TextView)convertView.findViewById(R.id.item_action_share);// 分享
				viewHolder.comment = (TextView)convertView.findViewById(R.id.item_action_comment);// 评论
				// 新加的
				viewHolder.ll_content_images = (LinearLayout)convertView.findViewById(R.id.content_images);
				viewHolder.img_content_images_1 = (ImageView)convertView.findViewById(R.id.content_images_1); 
				viewHolder.img_content_images_2 = (ImageView)convertView.findViewById(R.id.content_images_2); 
				viewHolder.img_content_images_3 = (ImageView)convertView.findViewById(R.id.content_images_3);*/ 
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			//final SpaceDynamic entity = dataList.get(position);
			//User user = entity.getAuthor();
		    Log.e("TAG",String.valueOf(dataList.size()));
			/**
			 *  头像
			 
			if(user.getAvatar()==null){
				//LogUtils.i("user","USER avatar IS NULL");
			}
			String avatarUrl = null;
			if(user.getAvatar()!=null){
				avatarUrl = user.getAvatar();
			}
			ImageLoader.getInstance()
			.displayImage(avatarUrl, viewHolder.userLogo, 
					CustomApplcation.getInstance().getOptions(R.drawable.user_icon_default_main),
					new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
						}
				
			});
			viewHolder.userLogo.setOnClickListener(new OnClickListener() {
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
				}
			});*/
			/**
			 * 用户名
			 
			viewHolder.userName.setText(entity.getAuthor().getUsername());*/
			/**
			 *  显示内容文字
			 
			viewHolder.contentText.setText(entity.getContent());*/
			/**
			 *  显示内容图片
			 
			if(null == entity.getContentfigureurl_1()){
				viewHolder.ll_content_images.setVisibility(View.GONE);
			}else{
				viewHolder.ll_content_images.setVisibility(View.VISIBLE);
				ImageLoader.getInstance()
				.displayImage(entity.getContentfigureurl_1().getFileUrl()==null?"":entity.getContentfigureurl_1().getFileUrl(), viewHolder.img_content_images_1, 
						CustomApplcation.getInstance().getOptions(R.drawable.bg_pic_loading),
						new SimpleImageLoadingListener(){
							@Override
							public void onLoadingComplete(String imageUri, View view,
									Bitmap loadedImage) {
								// TODO Auto-generated method stub
								super.onLoadingComplete(imageUri, view, loadedImage);
								 float[] cons=ActivityUtil.getBitmapConfiguration(loadedImage, viewHolder.img_content_images_1, 1.0f);
		                         RelativeLayout.LayoutParams layoutParams=
		                             new RelativeLayout.LayoutParams((int)cons[0], (int)cons[1]);
		                         layoutParams.addRule(RelativeLayout.BELOW,R.id.content_text);
		                         viewHolder.img_content_images_1.setLayoutParams(layoutParams);
							}
				});
			}
			if(null == entity.getContentfigureurl_2()){
				viewHolder.img_content_images_2.setVisibility(View.GONE);
			}else{
				viewHolder.img_content_images_2.setVisibility(View.VISIBLE);
				entity.getContentfigureurl_2().loadImage(mContext,viewHolder.img_content_images_2);
			}
			if(null == entity.getContentfigureurl_3()){
	        	viewHolder.img_content_images_3.setVisibility(View.GONE);
			}else{
				viewHolder.img_content_images_3.setVisibility(View.VISIBLE);
				entity.getContentfigureurl_3().loadImage(mContext,viewHolder.img_content_images_3);
			}*/
			/**
			 * 点赞
			 
			viewHolder.love.setText(entity.getLove()+"");
			if(entity.getMyLove()){
				viewHolder.love.setTextColor(Color.parseColor("#D95555"));
			}else{
				viewHolder.love.setTextColor(Color.parseColor("#000000"));
			}
			viewHolder.hate.setText(entity.getHate()+"");
			viewHolder.love.setOnClickListener(new OnClickListener() {
				boolean oldFav = entity.getMyFav();
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(CustomApplcation.getInstance().getCurrentUser()==null){
						ShowToast("请先登录。");
						startAnimActivity(LoginActivity.class);
						return;
					}
					if(entity.getMyLove()){
						ShowToast("您已赞过啦");
						//ActivityUtil.show(mContext, "您已赞过啦");
						return;
					}
					
					if(DatabaseUtil.getInstance(mContext).isLoved(entity)){
						ShowToast("您已赞过啦");
						//ActivityUtil.show(mContext, "您已赞过啦");
						entity.setMyLove(true);
						entity.setLove(entity.getLove()+1);
						viewHolder.love.setTextColor(Color.parseColor("#D95555"));
						viewHolder.love.setText(entity.getLove()+"");
						return;
					}
					
					entity.setLove(entity.getLove()+1);
					viewHolder.love.setTextColor(Color.parseColor("#D95555"));
					viewHolder.love.setText(entity.getLove()+"");

					entity.increment("love",1);
					if(entity.getMyFav()){
						entity.setMyFav(false);
					}
					entity.update(mContext, new UpdateListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							entity.setMyLove(true);
							entity.setMyFav(oldFav);
							DatabaseUtil.getInstance(mContext).insertFav(entity);
							//LogUtils.i(TAG, "点赞成功~");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							entity.setMyLove(true);
							entity.setMyFav(oldFav);
						}
					});
				}
			});*/
			/**
			 *  评论
			
			viewHolder.comment.setOnClickListener(new OnClickListener() {
				
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
					intent.putExtra("data", entity);
					mContext.startActivity(intent);
				}
			}); */
		return convertView;
	}
	
	public static class ViewHolder{
		public ImageView userLogo;
		public TextView userName;
		public TextView contentText;
		
		public ImageView favMark;
		public TextView love;
		public TextView hate;
		public TextView share;
		public TextView comment;
		
		LinearLayout ll_content_images;
		public ImageView img_content_images_1;
		public ImageView img_content_images_2;
		public ImageView img_content_images_3;
	}

	public void ShowToast(String string){
		Toast.makeText(mContext, string,Toast.LENGTH_LONG).show();
	}
	
	public void startAnimActivity(Class<?> cla) {
		mContext.startActivity(new Intent(mContext, cla));
	}
}
