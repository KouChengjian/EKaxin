package com.chenghui.ekaxin.adapter;

import java.util.ArrayList;
import java.util.List;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.data.TimeAxisModel;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class TimeAxisBaseAdapter extends BaseAdapter{
	private Context context;

	private Activity activity;

	private List<TimeAxisModel> listViewData;

	private int layoutResId;// ListViewÿ��Item�Ĳ����ļ�

	public TimeAxisBaseAdapter(Context context, int layoutResId, Activity activity) {
		this.context = context;
		this.layoutResId = layoutResId;
		listViewData = new ArrayList<TimeAxisModel>();
		this.activity = activity;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TimeAxisModel model = listViewData.get(position);
		ViewItemHolder viewItemHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(layoutResId,
					null);
			viewItemHolder = new ViewItemHolder();
			
			viewItemHolder.view0 = (View) convertView
					.findViewById(R.id.view_0);
			viewItemHolder.view2 = (View) convertView
					.findViewById(R.id.view_2);
			
			viewItemHolder.img = (ImageView) convertView
					.findViewById(R.id.image);
			viewItemHolder.imgshow = (ImageView) convertView
					.findViewById(R.id.image_1);
			
			viewItemHolder.time = (TextView) convertView
					.findViewById(R.id.show_time);
			viewItemHolder.content = (TextView) convertView
					.findViewById(R.id.title);
			convertView.setTag(viewItemHolder);
		} 
		else 
		{
			viewItemHolder = (ViewItemHolder) convertView.getTag();
		}
		viewItemHolder.img.setImageBitmap(BitmapFactory.decodeResource(
				context.getResources(), model.getImgTitle()));
		
		viewItemHolder.time.setText(model.getDate());
		viewItemHolder.content.setText(model.getContent());
		
		if(model.getImgPicture() != 0)
		{
			viewItemHolder.imgshow.setVisibility(View.VISIBLE);
		}
		else
		{
			viewItemHolder.imgshow.setVisibility(View.GONE);
		}

		/** 
		if (model.getType() == FinalVar.MSG_IMAGE) {// ͼƬ��Դ
			viewItemHolder.ivPhoto.setImageResource(R.drawable.pic_screen);
			viewItemHolder.ivPhoto.setVisibility(View.VISIBLE);
		} else {
			viewItemHolder.ivPhoto.setVisibility(View.GONE);
		}
		if (!model.getAddress().isEmpty()) {
			viewItemHolder.ivAddress.setVisibility(View.VISIBLE);
			viewItemHolder.tvAddress.setVisibility(View.VISIBLE);
			viewItemHolder.tvAddress.setText(model.getAddress());
		} else {
			viewItemHolder.ivAddress.setVisibility(View.GONE);
			viewItemHolder.tvAddress.setVisibility(View.GONE);
		}
		viewItemHolder.tvPhonemodel.setText(model.getPhonemodel());
		viewItemHolder.ivAgree
				.setOnClickListener(new ListViewButtonOnClickListener(position));
		if (model.isAgree()) {
			viewItemHolder.ivAgree
					.setImageResource(R.drawable.qzone_picviewer_bottom_praise_icon);
		} else {
			viewItemHolder.ivAgree
					.setImageResource(R.drawable.qzone_picviewer_bottom_unpraise_icon);
		}
		viewItemHolder.ivAgree.setFocusable(false);
		if (null != model.getAgreeShow() && model.getAgreeShow().size() > 0) {
			viewItemHolder.ivAgreeShow.setVisibility(View.VISIBLE);
			viewItemHolder.tvAgreeShow.setVisibility(View.VISIBLE);
			viewItemHolder.tvAgreeShow.setText(model.getAgreeShow().toString()
					+ "���ú��ޣ�");
		} else {
			viewItemHolder.ivAgreeShow.setVisibility(View.GONE);
			viewItemHolder.tvAgreeShow.setVisibility(View.GONE);
		}
		viewItemHolder.ivComment
				.setOnClickListener(new ListViewButtonOnClickListener(position));
		viewItemHolder.ivComment.setFocusable(false);
		viewItemHolder.tvComment
				.setOnClickListener(new ListViewButtonOnClickListener(position));
		viewItemHolder.btnComment
				.setOnClickListener(new ListViewButtonOnClickListener(position));
		viewItemHolder.btnComment.setFocusable(false);
		
		if (null != model.getComments() && model.getComments().size() > 0) {
			viewItemHolder.tvComments.setVisibility(View.VISIBLE);
			String string = "";
			for (String comment : model.getComments()) {
				string += comment + "\n";
			}
			viewItemHolder.tvComments.setText(string);
		} else {
			viewItemHolder.tvComments.setVisibility(View.GONE);
		}
		*/
		return convertView;
	}
	
	

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listViewData.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null == listViewData) {
			return 0;
		}
		return listViewData.size();
	}

	/**
	 * ���һ����¼
	 * 
	 * @param model
	 */
	public void addModel(TimeAxisModel model) {
		listViewData.add(model);
	}

	/**
	 * ���һ����¼
	 * 
	 * @param model
	 * @param insertHead
	 *            true:������ͷ��
	 */
	public void addModel(TimeAxisModel model, boolean insertHead) {
		if (insertHead) {
			listViewData.add(0, model);
		} else {
			listViewData.add(model);
		}
	}

	/**
	 * ��ȡһ����¼
	 * 
	 * @param i
	 * @return
	 */
	public TimeAxisModel getModel(int i) {
		if (i < 0 || i > listViewData.size() - 1) {
			return null;
		}
		return listViewData.get(i);
	}

	/**
	 * �����������
	 */
	public void clear() {
		listViewData.clear();
	}

	class ViewItemHolder {
		
		View view0;
		View view1;
		View view2;
		
		ImageView img;
		ImageView imgshow;
		
		TextView time;
		TextView content;
		
		
	}

	class ListViewButtonOnClickListener implements OnClickListener {
		private int position;// ��¼ListView��Button���ڵ�Item��λ��

		public ListViewButtonOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			/**  
			case R.id.ivAgree:
				ImageView ivAgree = (ImageView) v;
				Model model = listViewData.get(position);
				List<String> agreeShow = model.getAgreeShow();
				if (null == agreeShow || agreeShow.size() <= 0) {
					agreeShow = new ArrayList<String>();
				}
				if (model.isAgree()) {
					agreeShow.remove("��");
					ivAgree.setImageResource(R.drawable.qzone_picviewer_bottom_unpraise_icon);
				} else {
					agreeShow.add("��");
					ivAgree.setImageResource(R.drawable.qzone_picviewer_bottom_praise_icon);
				}
				model.setAgree(!model.isAgree());
				model.setAgreeShow(agreeShow);
				notifyDataSetChanged();
				// Toast.makeText(context, "�������", Toast.LENGTH_SHORT).show();
				break;
			case R.id.ivComment:
			case R.id.tvComment:
			case R.id.btnComment:
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
				Model model1 = listViewData.get(position);
				String nikename = model1.getName();
				activity.findViewById(R.id.etComment).setVisibility(
						View.VISIBLE);
				activity.findViewById(R.id.btnSendComment).setVisibility(
						View.VISIBLE);
				((EditText) activity.findViewById(R.id.etComment)).setHint("@"
						+ nikename);
				activity.findViewById(R.id.etComment).setFocusable(true);
				activity.findViewById(R.id.btnSendComment).setOnClickListener(
						new ListViewButtonOnClickListener(position));
				break;
			case R.id.btnSendComment:
				Model mdl = listViewData.get(position);
				List<String> commentsList = mdl.getComments();
				String commentString = ((EditText) activity
						.findViewById(R.id.etComment)).getEditableText()
						.toString();
				if (null == commentsList || commentsList.size() <= 0) {
					commentsList = new ArrayList<String>();
				}
				commentsList.add(commentString);
				mdl.setComments(commentsList);
				notifyDataSetChanged();
				((EditText) activity.findViewById(R.id.etComment)).setText("");
				activity.findViewById(R.id.etComment).setVisibility(View.GONE);
				activity.findViewById(R.id.btnSendComment).setVisibility(
						View.GONE);
				InputMethodManager imm2 = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm2.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				break;
			default:
				break;*/
			}
			
		}
	}
}
