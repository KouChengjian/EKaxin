package com.chenghui.ekaxin.ui.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.PopupWindow.OnDismissListener;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.db.BmobDB;
import cn.bmob.v3.listener.PushListener;
import cn.bmob.v3.listener.UpdateListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.UserFriendAdapter;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.ui.AddFriendActivity;
import com.chenghui.ekaxin.ui.FragmentBase;
import com.chenghui.ekaxin.ui.MainActivity;
import com.chenghui.ekaxin.ui.NewFriendActivity;
import com.chenghui.ekaxin.util.CharacterParser;
import com.chenghui.ekaxin.util.CollectionUtils;
import com.chenghui.ekaxin.util.PinyinComparator;
import com.chenghui.ekaxin.util.UserCharDB;
import com.chenghui.ekaxin.view.ClearEditText;
import com.chenghui.ekaxin.view.TitleBarView;
import com.chenghui.ekaxin.view.MyLetterView;
import com.chenghui.ekaxin.view.MyLetterView.OnTouchingLetterChangedListener;
import com.chenghui.ekaxin.view.dialog.DialogTips;

/**
 * @ClassName: ListCarteFragment
 * @Description: ��Ƭ�б�չʾ
 * @author kcj
 * @date 2014-10-6 10:20
 */
public class ListCarteFragment extends FragmentBase implements OnItemClickListener, OnItemLongClickListener {
	/**
	 *  ����
	 */
	private TitleBarView mTitleBarView;
	private View mPopView;
	private PopupWindow mPopupWindow;
	private ImageView mChats, mShare, mScan;
	ClearEditText mClearEditText;
	private InputMethodManager inputMethodManager;
	
	/**
	 * ����˵�
	 */
	View contentview;
	PopupWindow popupWindow;
	private ImageView imgShare, imgPhone, imgSms,imgMail,imgDynamic,imgAttention;
	/**
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	/**
	 * ����ƴ��������ListView�����������
	 */
	private PinyinComparator pinyinComparator;
	/**
	 *  ����
	 */
	private ListView list_friends;
	private UserFriendAdapter userAdapter;// ����
	private List<User> friends = new ArrayList<User>();
	private ImageView iv_msg_tips;
	private TextView tv_new_name;
	private LinearLayout layout_new;//������
	private LinearLayout layout_near;//��������

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mPopView = inflater.inflate(R.layout.include_pop_menu, null);
		contentview = inflater.inflate(R.layout.popup_carte_menu, null);
		return inflater.inflate(R.layout.fragment_cartelist, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		inputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		initView();
	}

	private void initView() {
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		// ��ʼ��popup
	    initPopupWindow();
		// ����
		mTitleBarView=(TitleBarView)findViewById(R.id.common_actionbar);
		mTitleBarView.getTitleLeft().setEnabled(true);
		mTitleBarView.getTitleRight().setEnabled(false);
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
				View.VISIBLE);
		// ������������
		mTitleBarView.setBtnRight(R.drawable.home_page_heard_title_right_n);
		mTitleBarView.setBtnRightOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTitleBarView.setPopWindow(mPopupWindow, mTitleBarView);
			}
		}); 
		mTitleBarView.setBtnLeft(R.drawable.home_page_heard_title_left_n,0);
		mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.getInstance().toggleSlidingMenu();
			}
		});
		// �����м�
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
		
		// ��ʼ��list
		initListView();
		// ��ʼ���ұߵ�ѡ�
		//initRightLetterView();
		// ��ʼ��ˢѡ�༭��
		//initEditText();
	}
	
	@SuppressWarnings("deprecation")
	public void initPopupWindow(){
		// ����˵�
		mChats = (ImageView) mPopView.findViewById(R.id.pop_chat);
		mShare = (ImageView) mPopView.findViewById(R.id.pop_sangzhao);
		mScan = (ImageView) mPopView.findViewById(R.id.pop_scan);
		// popup����
		mChats.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startAnimActivity(AddFriendActivity.class);
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
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mTitleBarView.setBtnRight(R.drawable.home_page_heard_title_right_n);
				//mCanversLayout.setVisibility(View.GONE);
			}
		});
		
		// ����˵�
		imgShare = (ImageView) contentview.findViewById(R.id.img_carte_recommend);
		imgPhone = (ImageView) contentview.findViewById(R.id.img_carte_call);
		imgSms = (ImageView) contentview.findViewById(R.id.img_carte_sms);
		imgMail = (ImageView) contentview.findViewById(R.id.img_carte_mail);
		imgDynamic = (ImageView) contentview.findViewById(R.id.img_carte_trends);
		imgAttention = (ImageView) contentview.findViewById(R.id.img_carte_attention);
		imgShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		imgPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		imgSms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		imgMail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		imgDynamic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
		imgAttention.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				deleteContact(selectorUser);
			}
		});
		
		popupWindow = new PopupWindow(contentview,LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setOutsideTouchable(true);
		
	}

	private void initEditText() {
		mClearEditText = (ClearEditText) findViewById(R.id.et_msg_search);
		// �������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void initListView() {
		list_friends = (ListView) findViewById(R.id.list_friends);
		RelativeLayout headView = (RelativeLayout) mInflater.inflate(R.layout.include_new_friend, null);
		iv_msg_tips = (ImageView)headView.findViewById(R.id.iv_msg_tips);
		layout_new =(LinearLayout)headView.findViewById(R.id.layout_new);
		layout_near =(LinearLayout)headView.findViewById(R.id.layout_near);
        layout_new.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), NewFriendActivity.class);
				intent.putExtra("from", "contact");
				startAnimActivity(intent);
			}
		});
		layout_near.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(getActivity(), NearPeopleActivity.class);
				//startAnimActivity(intent);
			}
		});
		//list_friends.addHeaderView(headView);
		userAdapter = new UserFriendAdapter(getActivity(), friends);
		list_friends.setAdapter(userAdapter);
		list_friends.setOnItemClickListener(this);
		list_friends.setOnItemLongClickListener(this);
		list_friends.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// ���������
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
		list_friends.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    Log.e("TAG",String.valueOf(list_friends.getFirstVisiblePosition()));
                    Log.e("TAG",String.valueOf(list_friends.getLastVisiblePosition()));
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
	}
	
	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<User> filterDateList = new ArrayList<User>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = friends;
		} else {
			filterDateList.clear();
			for (User sortModel : friends) {
				String name = sortModel.getUsername();

				if (name != null) {
					if (name.indexOf(filterStr.toString()) != -1
							|| characterParser.getSelling(name).startsWith(
									filterStr.toString())) {
						filterDateList.add(sortModel);
					}
				}
			}
		}
		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		userAdapter.updateListView(filterDateList);
	}

	MyLetterView right_letter;
	TextView dialog;

	private void initRightLetterView() {
		right_letter = (MyLetterView) findViewById(R.id.right_letter);
		dialog = (TextView) findViewById(R.id.dialog);
		right_letter.setTextView(dialog);
		right_letter
				.setOnTouchingLetterChangedListener(new LetterListViewListener());
	}

	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s) {
			// ����ĸ�״γ��ֵ�λ��
			int position = userAdapter.getPositionForSection(s.charAt(0));
			if (position != -1) {
				list_friends.setSelection(position);
			}
		}
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
		//refresh();
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

	/**
	 * @ClassName: queryMyfriends
	 * @Description: ��ȡ�����б�
	 * @author kcj
	 * @date 
	 */

	private void queryMyfriends() {
		//�Ƿ����µĺ�������
		//if(BmobDB.create(getActivity()).hasNewInvite()){
		//	iv_msg_tips.setVisibility(View.VISIBLE);
		//}else{
		//	iv_msg_tips.setVisibility(View.GONE);
		//}
		//����������һ�α��صĺ������ݿ�ļ�飬��Ϊ�˱��غ������ݿ����Ѿ�����˶Է������ǽ���ȴû����ʾ����������
		// �����������ڴ��б���ĺ����б�
		CustomApplcation.getInstance().setContactList(CollectionUtils.list2map(BmobDB.create(getActivity()).getContactList()));
		
		Map<String,BmobChatUser> users = CustomApplcation.getInstance().getContactList();
		//��װ�µ�User
		filledData(CollectionUtils.map2list(users));
		
		MainActivity.getInstance().setUserLise(friends);
		if (userAdapter == null) {
			userAdapter = new UserFriendAdapter(getActivity(), friends);
			list_friends.setAdapter(userAdapter);
		} else {
			userAdapter.notifyDataSetChanged();
		}
	}
	
	/**
	 * ΪListView�������
	 * 
	 * @param date
	 * @returnList <BmobChatUser> datas
	 */
	private void filledData(List<BmobChatUser> datas) {
		friends.clear();
		int total = datas.size();
		for (int i = 0; i < total; i++) {
			/**  
			BmobChatUser user = datas.get(i);
			User[] camera = userCharDB.queryOneData(user.getUsername() , UserCharDB.DB_CHAR_TABLE);
			User sortModel = null;
			String username = null;
			if (camera != null) {
				sortModel = new User();
				sortModel.setAvatar(camera[0].getAvatar());
				sortModel.setUserChainName(TextUtils.isEmpty(camera[0].getUserChainName())? camera[0].getUsername():camera[0].getUserChainName());
				sortModel.setUserCompany(TextUtils.isEmpty(camera[0].getUserCompany())? "":camera[0].getUserCompany());
				sortModel.setObjectId(camera[0].getObjectId());
				sortModel.setContacts(camera[0].getContacts());
				// ����ת����ƴ��
				username = camera[0].getUsername();
			}else{
				Toast.makeText(mContext, "wushuju ��",Toast.LENGTH_LONG).show();
			}*/
			/** */
			BmobChatUser user = datas.get(i);
			User sortModel = new User();
			sortModel.setAvatar(user.getAvatar());
			sortModel.setNick(user.getNick());
			sortModel.setUsername(user.getUsername());
			sortModel.setObjectId(user.getObjectId());
			sortModel.setContacts(user.getContacts());
			// ����ת����ƴ��
			String username = sortModel.getUsername();
			if (username != null) {
				String pinyin = characterParser.getSelling(sortModel.getUsername());
				String sortString = pinyin.substring(0, 1).toUpperCase();
				// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
				if (sortString.matches("[A-Z]")) {
					sortModel.setSortLetters(sortString.toUpperCase());
				} else {
					sortModel.setSortLetters("#");
				}
			} else {
				sortModel.setSortLetters("#");
			}
			friends.add(sortModel);
		}
		// ����a-z��������
		Collections.sort(friends, pinyinComparator);
	}

	User selectorUser;
	
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		selectorUser = (User) userAdapter.getItem(position);
		//showDeleteDialog(user);
		popupWindow.showAtLocation(mTitleBarView,  Gravity.CENTER, 0, 0);
		return true;
	}
	
	public void showDeleteDialog(final User user) {
		DialogTips dialog = new DialogTips(getActivity(),user.getUsername(),"ɾ����ϵ��", "ȷ��",true,true);
		// ���óɹ��¼�
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				deleteContact(user);
			}
		});
		// ��ʾȷ�϶Ի���
		dialog.show();
		dialog = null;
	}
	
	

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}
	
	
	/** ɾ����ϵ��
	  * deleteContact
	  * @return void
	  * @throws
	  */
	private void deleteContact(final User user){
		final ProgressDialog progress = new ProgressDialog(getActivity());
		progress.setMessage("����ɾ��...");
		progress.setCanceledOnTouchOutside(false);
		progress.show(); 
		//deleteOppositeContact(user , progress); 
		//BmobChatUser targetUser = user;
		// ��װBmobMessage����
		//BmobMsg message = BmobMsg.createTextSendMsg(getActivity(), user.getObjectId(), "xixi");
		// Ĭ�Ϸ�����ɣ������ݱ��浽������Ϣ�������Ự����
		//manager.sendTextMessage(targetUser, message);
		/**  */
		String jsonData = "{\"username\":\"arthinking\"}";  
		Log.e("jsonData",jsonData);
		manager.sendJsonMessage(jsonData, user.getObjectId(),new PushListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progress.dismiss();
				ShowToast("��������ɹ����ȴ��Է���֤!");
			}
			
			@Override
			public void onFailure(int arg0, final String arg1) {
				// TODO Auto-generated method stub
				progress.dismiss();
				ShowToast("��������ʧ�ܣ����������!");
			}
		});			
		/**    
		manager.sendJsonMessage("1", user.getObjectId() ,new PushListener() {	
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				progress.dismiss();
				//progress.setMessage("�������ݳɹ�������ɾ��...");
				//deleteOppositeContact(user,progress);
			}
			
			@Override
			public void onFailure(int arg0, final String arg1) {
				// TODO Auto-generated method stub
				progress.dismiss();
				//ShowToast("ɾ��ʧ�ܣ�"+arg1);
				//ShowLog("��������ʧ��:"+arg1);
			}
		});	*/
	}
	
	/**
	 *  ɾ���Է�����
	 */
	public void deleteOppositeContact(final User user,final ProgressDialog progress){
		userManager.deleteContact(user.getObjectId(), new UpdateListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("ɾ���ɹ�");
				//ɾ���ڴ�
				CustomApplcation.getInstance().getContactList().remove(user.getUsername());
				//���½���
				getActivity().runOnUiThread(new Runnable() {
					public void run() {
						progress.dismiss();
						userAdapter.remove(user);
					}
				});
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast("ɾ��ʧ�ܣ�"+arg1);
				progress.dismiss();
			}
		});
	}
}
