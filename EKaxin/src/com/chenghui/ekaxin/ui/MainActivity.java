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
 * @Description: ��½
 * @author kcj-
 * @date 
 */
public class MainActivity extends SlidingFragmentActivity implements EventListener {

	private Button[] mTabs;
	// ����
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
		//������ʱ�����񣨵�λΪ�룩-���������̨�Ƿ���δ������Ϣ���еĻ���ȡ����
		BmobChat.getInstance(this).startPollService(30);
		//�����㲥������
		initNewMessageBroadCast();
		initTagMessageBroadCast();
		// ��ʼ��sliding
		initSlidingMenu(savedInstanceState);
		initView();
		initTab();
	}
	
	/**
	 * ��ʼ�������˵�
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// ���û����˵�����ͼ
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MainMenuFragment()).commit();		
		// ʵ���������˵�����
		SlidingMenu sm = getSlidingMenu();
		// ���û�����Ӱ�Ŀ��
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// ���û�����Ӱ��ͼ����Դ
		sm.setShadowDrawable(R.drawable.shadow);
		// ���û����˵���ͼ�Ŀ��
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// ���ý��뽥��Ч����ֵ
		sm.setFadeDegree(0.35f);
		// ���ô�����Ļ��ģʽ
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
		// �ѵ�һ��tab��Ϊѡ��״̬
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
		// �����ʾ��һ��fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, homepageFragment)
				.add(R.id.fragment_container, gatherFragment)
				.hide(gatherFragment).show(homepageFragment).commit();
	}
	
	public int spage = 2;
	
	/**
	 * button����¼�
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
		// �ѵ�ǰtab��Ϊѡ��״̬
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
		// ע�������Ϣ�㲥
		newReceiver = new NewBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_NEW_MESSAGE);
		//���ȼ�Ҫ����ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(newReceiver, intentFilter);
	}
	
	/**
	 * ����Ϣ�㲥������
	 * 
	 */
	private class NewBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			//ˢ�½���
			refreshNewMsg(null);
			// �ǵðѹ㲥���ս��
			abortBroadcast();
		}
	}
	
	/** ˢ�½���
	  * @Title: refreshNewMsg
	  * @Description: TODO
	  * @param @param message 
	  * @return void
	  * @throws
	  */
	private void refreshNewMsg(BmobMsg message){
		// ������ʾ
		boolean isAllow = CustomApplcation.getInstance().getSpUtil().isAllowVoice();
		if(isAllow){
			CustomApplcation.getInstance().getMediaPlayer().start();
		}
		//ҲҪ�洢����
		if(message!=null){
			BmobChatManager.getInstance(MainActivity.this).saveReceiveMessage(true,message);
		}
		if(currentTabIndex==0){
			//��ǰҳ�����Ϊ�Ựҳ�棬ˢ�´�ҳ��
			if(recentFragment != null){
				//recentFragment.refresh();
			}
		}
	}
	
    TagBroadcastReceiver  userReceiver;
	
	private void initTagMessageBroadCast(){
		// ע�������Ϣ�㲥
		userReceiver = new TagBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter(BmobConfig.BROADCAST_ADD_USER_MESSAGE);
		//���ȼ�Ҫ����ChatActivity
		intentFilter.setPriority(3);
		registerReceiver(userReceiver, intentFilter);
	}
	
	/**
	 * ��ǩ��Ϣ�㲥������
	 */
	private class TagBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BmobInvitation message = (BmobInvitation) intent.getSerializableExtra("invite");
			refreshInvite(message);
			// �ǵðѹ㲥���ս��
			abortBroadcast();
		}
	}

	/** ˢ�º�������
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
			//ͬʱ����֪ͨ
			String tickerText = message.getFromname()+"������Ӻ���";
			boolean isAllowVibrate = CustomApplcation.getInstance().getSpUtil().isAllowVibrate();
			BmobNotifyManager.getInstance(this).showNotify(isAllow,isAllowVibrate,R.drawable.ic_launcher, tickerText, message.getFromname(), tickerText.toString(),NewFriendActivity.class);
		}
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MyMessageReceiver.ehList.add(this);// �������͵���Ϣ
		//���
		MyMessageReceiver.mNewNum = 0;
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//MyMessageReceiver.ehList.remove(this);// ȡ���������͵���Ϣ
	}
	
	/**
	 *  ����֮��Ӻ���
	 */
	@Override
	public void onAddUser(BmobInvitation message) {
		// TODO Auto-generated method stub
		refreshInvite(message);
	}

	/**
	 *  ����֮��Ϣ
	 */
	@Override
	public void onMessage(BmobMsg message) {
		// TODO Auto-generated method stub
		refreshNewMsg(message);
	}

	/**
	 *  ����֮����״̬
	 */
	@Override
	public void onNetChange(boolean isNetConnected) {
		// TODO Auto-generated method stub
		if(isNetConnected){
			Toast.makeText(this.getApplicationContext(), R.string.network_tips,Toast.LENGTH_LONG).show();
		}
	}

	/**
	 *  ����֮����
	 */
	@Override
	public void onOffline() {
		// TODO Auto-generated method stub
		//showOfflineDialog(this);
	}

	/**
	 *  ����֮   ...
	 */
	@Override
	public void onReaded(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	private static long firstTime;
	/**
	 * ���������η��ؼ����˳�
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (firstTime + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			//ShowToast("�ٰ�һ���˳�����");
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
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
		//ȡ����ʱ������
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
