package com.chenghui.ekaxin.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupWindow.OnDismissListener;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.IconCarteAdapter;
import com.chenghui.ekaxin.bean.FreindCarte;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.ui.FragmentBase;
import com.chenghui.ekaxin.ui.MainActivity;
import com.chenghui.ekaxin.view.TitleBarView;

/**
 * @ClassName: IconCarteFragment
 * @Description: 名片图片展示
 * @author kcj
 * @date 
 */
public class IconCarteFragment extends FragmentBase implements
OnItemClickListener, OnItemLongClickListener{
	private TitleBarView mTitleBarView;
	private View mPopView;
	private PopupWindow mPopupWindow;
	private ImageView mChats, mShare, mScan;
	private InputMethodManager inputMethodManager;
	// list
	ListView list_carteimage;
	IconCarteAdapter friendAdater;
	List<FreindCarte> friendsCarte = new ArrayList<FreindCarte>();

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mPopView = inflater.inflate(R.layout.include_pop_menu, null);
		return inflater.inflate(R.layout.fragment_cardspicture, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		inputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		initView();
		initListView();
	}
	
	private void initListView(){
		list_carteimage = (ListView) findViewById(R.id.lv_carteimg);
		friendAdater = new IconCarteAdapter(getActivity(), friendsCarte);
		list_carteimage.setAdapter(friendAdater);
		list_carteimage.setOnItemClickListener(this);
		list_carteimage.setOnItemLongClickListener(this);
		list_carteimage.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// 隐藏软键盘
				if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
					if (getActivity().getCurrentFocus() != null)
						inputMethodManager.hideSoftInputFromWindow(
								getActivity().getCurrentFocus()
										.getWindowToken(),
								InputMethodManager.HIDE_NOT_ALWAYS);
				}
				return false;
			}
		});
	}

	private void initView() {
		// 初始化popup
		initPopupWindow();
		mTitleBarView = (TitleBarView) findViewById(R.id.common_actionbar);
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
				View.VISIBLE);
		mTitleBarView.getTitleLeft().setEnabled(false);
		mTitleBarView.getTitleRight().setEnabled(true);
		// 标题左右两边
		mTitleBarView.setBtnRight(R.drawable.home_page_heard_title_right_n);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
			}
		});
		mTitleBarView.setBtnLeft(R.drawable.home_page_heard_title_left_n,
				0);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.getInstance().toggleSlidingMenu();
			}
		});
		// 标题中间
		mTitleBarView.getTitleLeft().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mTitleBarView.getTitleLeft().isEnabled()) {
					MainActivity.getInstance().transformFragment(v);
				}
			}
		});
		mTitleBarView.getTitleRight().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mTitleBarView.getTitleRight().isEnabled()) {
					MainActivity.getInstance().transformFragment(v);
				}
			}
		});
		// popup子项
		mChats.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		mShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		mScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
	}

	public void initPopupWindow() {
		mChats = (ImageView) mPopView.findViewById(R.id.pop_chat);
		mShare = (ImageView) mPopView.findViewById(R.id.pop_sangzhao);
		mScan = (ImageView) mPopView.findViewById(R.id.pop_scan);
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mTitleBarView
						.setBtnRight(R.drawable.home_page_heard_title_right_n);
			}
		});
	}
	
	private boolean hidden;
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if(!hidden){
			refresh();
		}
	}
	
	public void onResume() {
		super.onResume();
		// ShowToast("onResume");
		refresh();
	}
	
	public void refresh() {
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					queryMyfriends();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void queryMyfriends(){
		
		filledData(MainActivity.getInstance().getUserLise());
		
		if (friendAdater == null) {
			friendAdater = new IconCarteAdapter(getActivity(), friendsCarte);
			list_carteimage.setAdapter(friendAdater);
		} else {
			friendAdater.notifyDataSetChanged();
		}
	}
	List<FreindCarte> carteInfo = new ArrayList<FreindCarte>();
	private void filledData(List<User> datas){
		friendsCarte.clear();
		int total = datas.size();
		for (int i = 0; i < total; i++){
			User user = datas.get(i);
			FreindCarte friend=new FreindCarte();
			friend.setCartePictrue(R.drawable.pic_screen);
			friend.setBackIcon(R.drawable.item1);
			friendsCarte.add(friend);
			carteInfo.add(friend);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ShowToast("晕啊");
		/**  
		friendsCarte.clear();
		int total = carteInfo.size();
		for (int i = 0; i < total; i++){
			FreindCarte friend = carteInfo.get(i);
			FreindCarte friend1=new FreindCarte();
			if(i == position){
				friend1.setCartePictrue(R.drawable.item1);
			}else{
				friend1.setCartePictrue(friend.getCartePictrue());
			}
			friendsCarte.add(friend1);
		}
		
		friendAdater.notifyDataSetChanged();
		*/
	}
}
