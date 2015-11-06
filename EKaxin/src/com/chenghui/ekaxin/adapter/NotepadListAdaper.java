package com.chenghui.ekaxin.adapter;

import java.util.List;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.NotepadMessage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: NotepadListAdaper
 * @Description: 便签
 * @author kcj
 * @date 
 */
public class NotepadListAdaper extends BaseAdapter implements SectionIndexer{

	private Context ct;
	private List<List<NotepadMessage>> data;

	
	static int allTepyNum = NotepadMessage.getInstance().getIntType().length;
	
	public NotepadListAdaper(Context ct, List<List<NotepadMessage>> datas) {
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
	public void updateListView(List<List<NotepadMessage>> list) {
		this.data = list;
		notifyDataSetChanged();
	}
	
	public void remove(NotepadMessage user){
		this.data.remove(user);
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
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(ct).inflate(R.layout.item_notepad_memo, null);
			viewHolder = new ViewHolder();
			viewHolder.listtitle = (TextView) convertView.findViewById(R.id.tv_notepadlist_title);
			
			viewHolder.header_1 = (RelativeLayout) convertView.findViewById(R.id.rl_notepadlist_item1_title);
			viewHolder.body_1 = (RelativeLayout) convertView.findViewById(R.id.rl_notepadlist_item1_content);
			viewHolder.title_1 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item1_content_title);
			viewHolder.content_1 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item1_content_content);
			viewHolder.time_1 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item1_content_time);
			viewHolder.type_1 = (ImageView) convertView.findViewById(R.id.tv_notepadlist_item1_content_pic1);
			viewHolder.clock_1 = (ImageView) convertView.findViewById(R.id.tv_notepadlist_item1_content_pic2);
			 
			viewHolder.header_2 = (RelativeLayout) convertView.findViewById(R.id.rl_notepadlist_item2_title);
			viewHolder.body_2 = (RelativeLayout) convertView.findViewById(R.id.rl_notepadlist_item2_content);
			viewHolder.title_2 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item2_content_title);
			viewHolder.content_2 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item2_content_content);
			viewHolder.time_2 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item2_content_time);
			viewHolder.type_2 = (ImageView) convertView.findViewById(R.id.tv_notepadlist_item2_content_pic1);
			viewHolder.clock_2 = (ImageView) convertView.findViewById(R.id.tv_notepadlist_item2_content_pic2);
			
			viewHolder.header_3 = (RelativeLayout) convertView.findViewById(R.id.rl_notepadlist_item3_title);
			viewHolder.body_3 = (RelativeLayout) convertView.findViewById(R.id.rl_notepadlist_item3_content);
			viewHolder.title_3 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item3_content_title);
			viewHolder.content_3 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item3_content_content);
			viewHolder.time_3 = (TextView) convertView.findViewById(R.id.tv_notepadlist_item3_content_time);
			viewHolder.type_3 = (ImageView) convertView.findViewById(R.id.tv_notepadlist_item3_content_pic1);
			viewHolder.clock_3 = (ImageView) convertView.findViewById(R.id.tv_notepadlist_item3_content_pic2);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		NotepadMessage[] notepad = new NotepadMessage [data.get(position).size()];
		for(int i = 0;i < data.get(position).size();i++){
			notepad[i] = data.get(position).get(i);
			viewHolder.listtitle.setText(notepad[i].getType());
			// 标题
			if(position != 0){
				NotepadMessage data1 = data.get(position).get(0);
				NotepadMessage data2 = data.get(position-1).get(0);
				if(data1.getType().contains(data2.getType())){
					viewHolder.listtitle.setVisibility(View.GONE);
				}
			}
			if(i==0){
				viewHolder.header_1.setBackgroundResource(R.drawable.notepad_text_heard_background);
				viewHolder.body_1.setBackgroundResource(R.drawable.notepad_text_content_background);
				viewHolder.body_1.setOnClickListener(new ListViewButtonOnClickListener(position,0));
				viewHolder.title_1.setText(notepad[i].getTitle());
				viewHolder.content_1.setText(notepad[i].getContent());
				viewHolder.time_1.setText(notepad[i].getDate());
				viewHolder.type_1.setBackgroundResource(R.drawable.notepad_pictrue);
				viewHolder.clock_1.setBackgroundResource(R.drawable.notepad_clock);
			}
			if(i==1){
				viewHolder.header_2.setBackgroundResource(R.drawable.notepad_text_heard_background);
				viewHolder.body_2.setBackgroundResource(R.drawable.notepad_text_content_background);
				viewHolder.body_2.setOnClickListener(new ListViewButtonOnClickListener(position,1));
				viewHolder.title_2.setText(notepad[i].getTitle());
				viewHolder.content_2.setText(notepad[i].getContent());
				viewHolder.time_2.setText(notepad[i].getDate());
				viewHolder.type_2.setBackgroundResource(R.drawable.notepad_pictrue);
				viewHolder.clock_2.setBackgroundResource(R.drawable.notepad_clock);
			}
			if(i==2){
				viewHolder.header_3.setBackgroundResource(R.drawable.notepad_text_heard_background);
				viewHolder.body_3.setBackgroundResource(R.drawable.notepad_text_content_background);
				viewHolder.body_3.setOnClickListener(new ListViewButtonOnClickListener(position,2));
				viewHolder.title_3.setText(notepad[i].getTitle());
				viewHolder.content_3.setText(notepad[i].getContent());
				viewHolder.time_3.setText(notepad[i].getDate());
				viewHolder.type_3.setBackgroundResource(R.drawable.notepad_pictrue);
				viewHolder.clock_3.setBackgroundResource(R.drawable.notepad_clock);
			}
			
		}
		return convertView;
	}
	
	static class ViewHolder {
		TextView listtitle;
		RelativeLayout header_1;
		RelativeLayout body_1;
		TextView title_1;
		TextView content_1;
		TextView time_1;
		ImageView type_1;
		ImageView clock_1;
		
		RelativeLayout header_2;
		RelativeLayout body_2;
		TextView title_2;
		TextView content_2;
		TextView time_2;
		ImageView type_2;
		ImageView clock_2;
		
		RelativeLayout header_3;
		RelativeLayout body_3;
		TextView title_3;
		TextView content_3;
		TextView time_3;
		ImageView type_3;
		ImageView clock_3;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int section) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean areAllItemsEnabled() {
	    return false;
	}
	     
	@Override
	public boolean isEnabled(int position)  {
	    return false;
	}
	
	class ListViewButtonOnClickListener implements OnClickListener {
		private int position;// 记录ListView中Button所在的Item的位置
		private int mNum;
		public ListViewButtonOnClickListener(int position ,int mNum) {
			this.position = position;
			this.mNum = mNum;
		}
		@Override
		public void onClick(View v) {
			Toast.makeText(ct, String.valueOf(position), 1).show();
			switch (v.getId()) {
			case R.id.rl_notepadlist_item1_content:
				Toast.makeText(ct, String.valueOf(mNum), 1).show();
				break;
			case R.id.rl_notepadlist_item2_content:
				Toast.makeText(ct, String.valueOf(mNum), 1).show();
				break;
			case R.id.rl_notepadlist_item3_content:
				Toast.makeText(ct, String.valueOf(mNum), 1).show();
				break;
			default:
				break;
			}
		}
	}

}
