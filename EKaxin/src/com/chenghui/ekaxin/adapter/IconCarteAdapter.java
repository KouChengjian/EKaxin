package com.chenghui.ekaxin.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;

import com.chenghui.ekaxin.R;


import com.chenghui.ekaxin.bean.FreindCarte;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @ClassName: FriendCarteAdapter
 * @Description: 卡信集图片模式
 * @author kcj
 * @date 
 */
public class IconCarteAdapter extends BaseAdapter{

	private Context ct;
	private List<FreindCarte> data;
	private List<View> view = new ArrayList<View>();

	public IconCarteAdapter(Context ct, List<FreindCarte> datas) {
		this.ct = ct;
		this.data = datas;
	}
	
	/** 当ListView数据发生变化时,调用此方法来更新ListView
	  * @Title: updateListView
	  * @Description: TODO
	  * @param @param list 
	  * @return void
	  * @throws
	  */
	public void updateListView(List<FreindCarte> list) {
		this.data = list;
		notifyDataSetChanged();

	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final FreindCarte friend = data.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_carte_image, null);
			viewHolder = new ViewHolder();
			viewHolder.rl_carte_pictrue = (RelativeLayout) convertView.findViewById(R.id.rl_carte_image_comntent);
			viewHolder.rl_carte_state = (RelativeLayout) convertView.findViewById(R.id.rl_carte_image_title_state);
			viewHolder.img_carte_state = (ImageView) convertView.findViewById(R.id.img_carte_image_title_state_icon);
			viewHolder.img_carte_attention = (ImageView) convertView.findViewById(R.id.img_carte_image_title_attention);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.rl_carte_pictrue.setBackgroundResource(friend.getCartePictrue());
		
		viewHolder.rl_carte_pictrue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//((RelativeLayout)v).setBackgroundResource(friend.getBackIcon());
				((RelativeLayout)v).setBackgroundResource(R.drawable.item1);
			}
		});
		
        viewHolder.img_carte_attention.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//收藏
				onClickFav(v);
			}
		});
		return convertView;
	}
	
	private void onClickFav(View v) {
		// TODO Auto-generated method stub
		((ImageView)v).setImageResource(R.drawable.ic_action_fav_choose);		
	}
	
	static class ViewHolder {
		RelativeLayout rl_carte_pictrue;
		RelativeLayout rl_carte_state;
		ImageView img_carte_state;
		ImageView img_carte_attention;
		
	}
}
