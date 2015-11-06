package com.chenghui.ekaxin.ui.fragment;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.ui.FragmentBase;
import com.chenghui.ekaxin.ui.EditCardActivity;
import com.chenghui.ekaxin.ui.MainActivity;
import com.chenghui.ekaxin.ui.NotepadActivity;
import com.chenghui.ekaxin.ui.SelectCardActivity;
import com.chenghui.ekaxin.view.CircleImageView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * @ClassName: MainMenuFragment
 * @Description: 列表Fragment，用来显示滑动菜单打开后的内容  Sliding菜单
 * @author kcj
 * @date 
 */
public class MainMenuFragment extends FragmentBase {

	RelativeLayout[] layoutItem;
	CircleImageView circleImgView;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menuleft_fragment, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	private void initView() {
		circleImgView = (CircleImageView) getActivity().findViewById(R.id.headImageView1);
		Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.head_default_yixin);
		Bitmap mBitmap = Bitmap.createScaledBitmap(btm1, 100, 100, true);
		circleImgView.setImageBitmap(mBitmap);
		initLayout();
	}
	
	public void initLayout(){
		layoutItem = new RelativeLayout[5];
		layoutItem[0] = (RelativeLayout)getActivity().findViewById(R.id.layout_notification_indicator1);
		layoutItem[1] = (RelativeLayout)getActivity().findViewById(R.id.layout_notification_indicator2);
		layoutItem[2] = (RelativeLayout)getActivity().findViewById(R.id.layout_notification_indicator3);
		layoutItem[3] = (RelativeLayout)getActivity().findViewById(R.id.layout_notification_indicator4);
		layoutItem[4] = (RelativeLayout)getActivity().findViewById(R.id.layout_notification_indicator5);
		
		layoutItem[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startAnimActivity(EditCardActivity.class);
				MainActivity.getInstance().toggleSlidingMenu();
			}
		});
		layoutItem[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//startAnimActivity(NotepadActivity.class);
			}
		});
		layoutItem[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
		        //startAnimActivity(ChangePasswordActivity.class);
			}
		});
		layoutItem[3].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			    //EKaXinUserManger.getInstance().setCurrentUser(null);
		        //startAnimActivity(SplashActivity.class);
		        //getActivity().finish();
			}
		});
		layoutItem[4].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startAnimActivity(SelectCardActivity.class);
				MainActivity.getInstance().toggleSlidingMenu();
			}
		});
	}



}
