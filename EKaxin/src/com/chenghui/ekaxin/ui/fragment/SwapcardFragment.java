package com.chenghui.ekaxin.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.im.db.BmobDB;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.ui.FragmentBase;
import com.chenghui.ekaxin.ui.NewFriendActivity;

/**
 * @ClassName: 一键交换
 * @Description: 
 * @author kcj
 * @date  2015-1-8
 */
public class SwapcardFragment extends FragmentBase implements OnClickListener{

	TextView starSearch;
	ImageView newfriendTips;
	Button newfriend,history ,scan ,expert;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_swapcard, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initTopBarForOnlyTitle("名片交换");
		initSwapcardFragmentUi();
	}
	
	public void initSwapcardFragmentUi(){
		starSearch = (TextView)findViewById(R.id.tv_swapcard_search_star);
		newfriendTips = (ImageView)findViewById(R.id.ll_swapcard_selector_newfriend_tips);
		newfriend = (Button)findViewById(R.id.ll_swapcard_selector_newfriend);
		history = (Button)findViewById(R.id.ll_swapcard_selector_history);
		scan = (Button)findViewById(R.id.ll_swapcard_selector_scan);
		expert = (Button)findViewById(R.id.ll_swapcard_selector_kx);
		
		starSearch.setOnClickListener(this);
		newfriendTips.setOnClickListener(this);
		newfriend.setOnClickListener(this);
		history.setOnClickListener(this);
		scan.setOnClickListener(this);
		expert.setOnClickListener(this);
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
		if(!hidden){
			refresh();
		}
	}
	
	public void refresh() {
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					queryData();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void queryData(){
		//是否有新的好友请求
		if(BmobDB.create(getActivity()).hasNewInvite()){
			newfriendTips.setVisibility(View.VISIBLE);
		}else{
			newfriendTips.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_swapcard_search_star:
			//Intent intent1 = new Intent(getActivity(), NearPeopleActivity.class);
			//startAnimActivity(intent1);
			break;
		case R.id.ll_swapcard_selector_newfriend:
			Intent intent = new Intent(getActivity(), NewFriendActivity.class);
			intent.putExtra("from", "contact");
			startAnimActivity(intent);
			break;
		case R.id.ll_swapcard_selector_history:
			break;
		case R.id.ll_swapcard_selector_scan:
			break;
		case R.id.ll_swapcard_selector_kx:
			break;
		}
	}
}
