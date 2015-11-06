package com.chenghui.ekaxin.ui;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.im.BmobChat;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobNotifyManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.config.BmobConfig;
import cn.bmob.im.inteface.EventListener;
import cn.bmob.v3.listener.FindListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.MyMessageReceiver;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.ui.fragment.IconCarteFragment;
import com.chenghui.ekaxin.ui.fragment.ExchangFragment;
import com.chenghui.ekaxin.ui.fragment.ListCarteFragment;
import com.chenghui.ekaxin.ui.fragment.HomePageFragment;
import com.chenghui.ekaxin.ui.fragment.MainMenuFragment;
import com.chenghui.ekaxin.ui.fragment.StatisticsFragment;
import com.chenghui.ekaxin.ui.fragment.SwapcardFragment;
import com.chenghui.ekaxin.ui.fragment.UserSettingFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @ClassName: MainActivity
 * @Description: 登陆
 * @author kcj-
 * @date 
 */
public class MainActivity extends SlidingFragmentActivity implements EventListener {

	private Button[] mTabs;
	// 界面
	private SwapcardFragment exchangeFragment;
	private StatisticsFragment recentFragment;
	private UserSettingFragment settingFragment;
	private HomePageFragment homepageFragment;
	private ListCarteFragment gatherFragment;
	private IconCarteFragment cardpictureFragment;
	private Fragment[] fragments;
	public static int index;
	private int currentTabIndex;
	public static MainActivity mInstance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mInstance = this;
		//开启定时检测服务（单位为秒）-在这里检测后台是否还有未读的消息，有的话就取出来
		BmobChat.getInstance(this).startPollService(30);
		//开启广播接收器
		initNewMessageBroadCast();
		initTagMessageBroadCast();
		// 初始化sliding
		initSlidingMenu(savedInstanceState);
		initView();
		initTab();
	}
	
	/**
	 * 初始化滑动菜单
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 设置滑动菜单的视图
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MainMenuFragment()).commit();		
		// 实例化滑动菜单对象
		SlidingMenu sm = getSlidingMenu();
		// 设置滑动阴影的宽度
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// 设置滑动阴影的图像资源
		sm.setShadowDrawable(R.drawable.shadow);
		// 设置滑动菜单视图的宽度
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// 设置渐入渐出效果的值
		sm.setFadeDegree(0.35f);
		// 设置触摸屏幕的模式
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
	
	RelativeLayout LayoutView;
	
	private void initView() {
		LayoutView = (RelativeLayout) findViewById(R.id.main_Layout);
		View view = LayoutInflater.from(this).inflate(R.layout.include_action_tab,null);
		LayoutView.addView(view);
		mTabs = new Button[6];
		mTabs[0] = (Button) view.findViewById(R.id.btn_main_home);
		mTabs[1] = (Button) view.findViewById(R.id.btn_main_cards);
		mTabs[2] = mTabs[1];
		mTabs[3] = (Button) view.findViewById(R.id.btn_main_exchange);
		mTabs[4] = (Button) view.findViewById(R.id.btn_main_statistics);
		mTabs[5] = (Button) view.findViewById(R.id.btn_main_user);
		// 把第一个tab设为选中状态
		mTabs[0].setSelected(true);
	}

	private void initTab() {
		homepageFragment = new HomePageFragment();
		gatherFragment = new ListCarteFragment();
		exchangeFragment = new SwapcardFragment();
		recentFragment = new StatisticsFragment();
		settingFragment = new UserSettingFragment();
		cardpictureFragment = new IconCarteFragment();
		fragments = new Fragment[] { homepageFragment, gatherFragment,
				cardpictureFragment , exchangeFragment,recentFragment,  settingFragment };
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, homepageFragment)
				.add(R.id.fragment_container, gatherFragment)
				.hide(gatherFragment).show(homepageFragment).commit();
	}
	
	public int spage = 2;
	
	/**
	 * button点击事件
	 * 
	 * @param view
	 */
	public void onTabSelect(View view) {
		switch (view.getId()) {
		case R.id.btn_main_home:
			index = 0;
			break;
		case R.id.constact_group:
		case R.id.constact_all:
		case R.id.btn_main_cards:
			index = spage;
			break;
		case R.id.btn_main_exchange:
			index = 3;
			break;
		case R.id.btn_main_statistics:
			index = 4;
			break;
		case R.id.btn_main_user:
			index = 5;
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		mTabs[index].setSelected(true);
		currentTabIndex = index;
		Intent intent = new Intent();
		intent.setAction("action.refreshMenu");
		sendBroadcast(intent);
	}
	
	public void transformFragment(View view){
		if(spage == 1){
			spage=2;
		}else if(spage == 2){
			spage = 1;
		}
		onTabSelect(view);
	}
	public void toggleSlidingMenu(){
		toggle();
	}
	
	public static MainActivity getInstance(){
		return mInstance;
	}

	
    NewBroadcastReceiver  newReceiver;
	
	private void initNewMessageBroadCast(){
		// 注册接收消息广播
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_NEW_MESSAGE);
		//优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}
	
	/**
	 * 新消息广播接收者
	 * 
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//刷新界面
			refreshNewMsg(null);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}
	
	/** 刷新界面
	  * @Title: refreshNewMsg
	  * @Description: TODO
	  * @param @param message 
	  * @return void
	  * @throws
	  */
	private void refreshNewMsg(BmobMsg message){
		// 声音提示
		boolean isAllow = CustomApplcation.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			CustomApplcation.getInstance().getMediaPlayer().start();
		}
		//也要存储起来
		if(message!=null){
			BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(true,message);
		}
		if(currentTabIndex==0){
			//当前页面如果为会话页面，刷新此页面
			if(recentFragment != null){
				//recentFragment.refresh();
			}
		}
	}
	
    TagBroadcastReceiver  userReceiver;
	
	private void initTagMessageBroadCast(){
		// 注册接收消息广播
		userReceiver = new TagBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_ADD_USER_MESSAGE);
		//优先级要低于ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(userReceiver, intentFilter);
	}
	
	/**
	 * 标签消息广播接收者
	 */
	private class TagBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BmobInvitation message = (BmobInvitation) intent.getSerializableExtra("invite");
			refreshInvite(message);
			// 记得把广播给终结掉
			abortBroadcast();
		}
	}

	/** 刷新好友请求
	  * @Title: notifyAddUser
	  * @Description: TODO
	  * @param @param message 
	  * @return void
	  * @throws
	  */
	private void refreshInvite(BmobInvitation message){
		boolean isAllow = CustomApplcation.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			CustomApplcation.getInstance().getMediaPlayer().start();
		}

		if(currentTabIndex == 1){
			if(gatherFragment != null){
				gatherFragment.refresh();
			}
		}else{
			//同时提醒通知
			String tickerText = message.getFromname()+"请求添加好友";
			boolean isAllowVibrate = CustomApplcation.getInstance().getSpUtil().isAllowVibrate();
			BmobNotifyManager.getInstance(this).showNotify(isAllow,isAllowVibrate,R.drawable.ic_launcher, tickerText, message.getFromname(), tickerText.toString(),NewFriendActivity.class);
		}
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyMessageReceiver.ehList.add(this);// 监听推送的消息
		//清空
		MyMessageReceiver.mNewNum = 0;
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//MyMessageReceiver.ehList.remove(this);// 取消监听推送的消息
	}
	
	/**
	 *  监听之添加好友
	 */
	@Override
	public void onAddUser(BmobInvitation message) {
		// TODO Auto-generated method stub
		refreshInvite(message);
	}

	/**
	 *  监听之消息
	 */
	@Override
	public void onMessage(BmobMsg message) {
		// TODO Auto-generated method stub
		refreshNewMsg(message);
	}

	/**
	 *  监听之网络状态
	 */
	@Override
	public void onNetChange(boolean isNetConnected) {
		// TODO Auto-generated method stub
		if(isNetConnected){
			Toast.makeText(this.getApplicationContext(), R.string.network_tips,Toast.LENGTH_LONG).show();
		}
	}

	/**
	 *  监听之下线
	 */
	@Override
	public void onOffline() {
		// TODO Auto-generated method stub
		//showOfflineDialog(this);
	}

	/**
	 *  监听之   ...
	 */
	@Override
	public void onReaded(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private static long firstTime;
	/**
	 * 连续按两次返回键就退出
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			//ShowToast("再按一次退出程序");
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_LONG).show();
		}
		firstTime = System.currentTimeMillis();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		try {
			unregisterReceiver(newReceiver);
		} catch (Exception e) {
		}
		try {
			unregisterReceiver(userReceiver);
		} catch (Exception e) {
		}
		//取消定时检测服务
		BmobChat.getInstance(this).stopPollService();
	}
	
	
	private List<User> friends = new ArrayList<User>();
	public void setUserLise(List<User> friends){
		this.friends = friends;
	}
	public List<User> getUserLise(){
		return friends;
	}
}
