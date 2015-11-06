package com.chenghui.ekaxin.ui;


import cn.bmob.v3.Bmob;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.NotepadMessage;
import com.chenghui.ekaxin.ui.fragment.NotepadFragment;
import com.chenghui.ekaxin.ui.fragment.SpaceFragment;
import com.chenghui.ekaxin.util.Constant;

import com.chenghui.ekaxin.view.TitleBarView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;



/**
 * @ClassName: OtherMainActivity
 * @Description: 空间 便签
 * @author kcj
 * @date 
 */
public class OtherMainActivity  extends FragmentActivity{
	
	// 标题
	int  showFargment;
	private Fragment[] fragments;
	SpaceFragment spaceFragment;
	NotepadFragment notepadFragment;
	private TitleBarView mTitleBarView;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_othermain);
		Bmob.initialize(this, Constant.BMOB_APP_ID);
		showFargment = Integer.valueOf(getIntent().getStringExtra("shownum")).intValue();
		NotepadMessage notepadMessage = new NotepadMessage();
		initHeader();
		initFragment();
		
	}
	
	public void initHeader(){
		mTitleBarView=(TitleBarView) findViewById(R.id.common_actionbar);
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.VISIBLE,
				View.VISIBLE);
		// 标题
		mTitleBarView.setTitleLeft(R.string.space);		
		mTitleBarView.setTitleRight(R.string.notepad);
		if(showFargment == 0){
			mTitleBarView.getTitleLeft().setEnabled(false);
			mTitleBarView.getTitleRight().setEnabled(true);
			mTitleBarView.setBtnRight(R.drawable.other_space_right);
		}
        if(showFargment == 1){
        	mTitleBarView.getTitleLeft().setEnabled(true);
    		mTitleBarView.getTitleRight().setEnabled(false);
        	mTitleBarView.setBtnRight(R.drawable.other_notepad_right);
		}
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(showFargment == 0){
					Intent intent = new Intent(OtherMainActivity.this,EditCommentActivity.class);
					startActivity(intent);
				}else if(showFargment == 1){
					
				}
			}
		});
		mTitleBarView.setBtnLeft(R.drawable.other_left,0);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 标题中间
		mTitleBarView.getTitleLeft().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTitleBarView.setBtnRight(R.drawable.other_space_right);
				showFargment = 0;
				showSpaceFargment();
			}
		});
		mTitleBarView.getTitleRight().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTitleBarView.setBtnRight(R.drawable.other_notepad_right);
				showFargment = 1;
				showNotepadFargment();
			}
		});
	}
	
	public void initFragment(){
		spaceFragment = new SpaceFragment();
		notepadFragment = new NotepadFragment();
	    fragments = new Fragment[] { spaceFragment, notepadFragment };
		
	    if(showFargment == 0){
	    	// 添加显示第一个fragment
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, spaceFragment)
					.add(R.id.fragment_container, notepadFragment)
					.hide(notepadFragment).show(spaceFragment).commit();
		}
        if(showFargment == 1){
        	// 添加显示第一个fragment
        				getSupportFragmentManager().beginTransaction()
        						.add(R.id.fragment_container, spaceFragment)
        						.add(R.id.fragment_container, notepadFragment)
        						.hide(spaceFragment).show(notepadFragment).commit();
		}
		
	}
	
	public void showSpaceFargment(){
		if (mTitleBarView.getTitleLeft().isEnabled()) {
			mTitleBarView.getTitleLeft().setEnabled(false);
			mTitleBarView.getTitleRight().setEnabled(true);
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[1]);
			if (!fragments[0].isAdded()) {
				trx.add(R.id.fragment_container, fragments[0]);
			}
			trx.show(fragments[0]).commit();
		}
	}
	
    public void showNotepadFargment(){
    	if (mTitleBarView.getTitleRight().isEnabled()) {
			mTitleBarView.getTitleLeft().setEnabled(true);
			mTitleBarView.getTitleRight().setEnabled(false);
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[0]);
			if (!fragments[1].isAdded()) {
				trx.add(R.id.fragment_container, fragments[1]);
			}
			trx.show(fragments[1]).commit();
		}
	}
}
