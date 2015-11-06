package com.chenghui.ekaxin.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.PopupWindow.OnDismissListener;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.ui.FragmentBase;
import com.chenghui.ekaxin.ui.MainActivity;
import com.chenghui.ekaxin.ui.OtherMainActivity;
import com.chenghui.ekaxin.ui.SelectCardActivity;
import com.chenghui.ekaxin.view.TitleBarView;

/**
 * @ClassName: HomePageFragment
 * @Description: ������ҳ
 * @author kcj
 * @date 2014-9-30 10:20
 */
public class HomePageFragment extends FragmentBase {
	//String content;
	public Context context;
	private View mPopView;
	private PopupWindow mPopupWindow;
	private ImageView imgNewFriend, imgRichScan, imgVip;
	private TitleBarView mTitleBarView;
	
	// ��ƬԤ��
	private static int     TOTAL_COUNT = 10;
    private RelativeLayout viewPagerContainer;
    private ViewPager      viewPager;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mPopView = inflater.inflate(R.layout.include_pop_menu, null);
		return inflater.inflate(R.layout.fragment_homepage, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		initHomePageAllUi();
	}
	
	private void initHomePageAllUi(){
		// ����
		initHeaderUi();
		// ��ʼ��popupWindows
		initPopupWindowUi();
		
		viewPager = (ViewPager)findViewById(R.id.vp_h_selectcard);
		viewPagerContainer = (RelativeLayout)findViewById(R.id.fragment_h_container);
		viewPager.setAdapter(new MyPagerAdapter());
	    viewPager.setOffscreenPageLimit(TOTAL_COUNT);
	    viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
	    viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	    viewPagerContainer.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return viewPager.dispatchTouchEvent(event);
			}
        });
	}
	
	private void initHeaderUi(){
		mTitleBarView = (TitleBarView) findViewById(R.id.common_actionbar);
		mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE,
				View.VISIBLE);
		// ����
		mTitleBarView.setTitleLeft(R.string.space);		
		mTitleBarView.setTitleRight(R.string.notepad);
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
					Intent intent =new Intent(getActivity(),OtherMainActivity.class);
					intent.putExtra("shownum", "0");
					startAnimActivity(intent);
				}
			}
		});
		mTitleBarView.getTitleRight().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mTitleBarView.getTitleRight().isEnabled()) {
					Intent intent =new Intent(getActivity(),OtherMainActivity.class);
					intent.putExtra("shownum", "1");
					startAnimActivity(intent);
				}
			}
		});
	}
	
	private void initPopupWindowUi(){
		imgNewFriend = (ImageView) mPopView.findViewById(R.id.pop_chat);
		imgRichScan = (ImageView) mPopView.findViewById(R.id.pop_sangzhao);
		imgVip = (ImageView) mPopView.findViewById(R.id.pop_scan);
		mPopupWindow = new PopupWindow(mPopView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				mTitleBarView
						.setBtnRight(R.drawable.home_page_heard_title_right_n);
			}
		});
		
		// popup����
		imgNewFriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		imgRichScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
		imgVip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopupWindow.dismiss();
			}
		});
	}

	private void initView() {  
		initfindViewById();
		initBroadcastReceiver();
		//content = "����Ϣ�������༭";
		//initImgView(imgview, content);
	}

	public void initPopupWindow() {
		
	}

	// HeaderLayout pulldownDrawer;
	//Button btnSpace, btnNotepad, btnTimeAxis;
	// MultiDirectionSlidingDrawer mDrawer;
	// RelativeLayout backgroundLayout;
	ImageView imgview;

	public void initfindViewById() {
		// mDrawer = (MultiDirectionSlidingDrawer) findViewById(R.id.drawer);
		//btnSpace = (Button) findViewById(R.id.btn_hp_personagespace);
		//btnNotepad = (Button) findViewById(R.id.btn_hp_notes);
		//btnTimeAxis = (Button) findViewById(R.id.btn_hp_record);
		//view1 = (View) findViewById(R.id.view1);
		//view2 = (View) findViewById(R.id.view2);
		// layout = (HeaderLayout) findViewById(R.id.common_actionbar);
		// backgroundLayout = (RelativeLayout)
		// findViewById(R.id.fragment_h_container);
		imgview = (ImageView) findViewById(R.id.iv_card_picture);
		imgview.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				// startAnimActivity(CardTemplateSelectActivity.class);
				// templateselect.setText(" ");
				// templateselect.setEnabled(false);
				// getActivity().finish();
			}
		});
/** 
		btnSpace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// mDrawer.animateClose();
				startAnimActivity(SpaceActivity.class);
			}
		});
		btnNotepad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// mDrawer.animateClose();
				startAnimActivity(NotepadActivity.class);
			}
		});
		btnTimeAxis.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// mDrawer.animateClose();
				startAnimActivity(TimeAxisActivity.class);
			}
		}); */
	}

	/**  
	public void initImgView(ImageView imageView, String str) {
		Bitmap backgroundImgView = BitmapFactory.decodeResource(
				this.getResources(), R.drawable.bcard_t3);
		int width = backgroundImgView.getWidth(); // ��ȡͼƬ��С
		int hight = backgroundImgView.getHeight();
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // ����һ���յ�BItMap
		Canvas canvas = new Canvas(icon);// ��ʼ���������Ƶ�ͼ��icon��

		Paint photoPaint = new Paint(); // ��������
		photoPaint.setDither(true); // ��ȡ��������ͼ�����
		photoPaint.setFilterBitmap(true);// ����һЩ
		Rect src = new Rect(0, 0, backgroundImgView.getWidth(),
				backgroundImgView.getHeight());// ����һ��ָ�����¾��ε�����
		Rect dst = new Rect(0, 0, width, hight);// ����һ��ָ�����¾��ε�����
		canvas.drawBitmap(backgroundImgView, src, dst, photoPaint);

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// ���û���
		textPaint.setTextSize(40);// �����С
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// ����Ĭ�ϵĿ��
		textPaint.setColor(Color.WHITE);// ���õ���ɫ
		// ����λ��
		int i1 = hight / 2 + 20;
		int i2 = width / 2 - (int) textPaint.measureText(str) / 2;
		canvas.drawText(str, i2, i1, textPaint);// ������ȥ�֣���ʼδ֪x,y������ֻ�ʻ���20, 26
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		imageView.setImageBitmap(icon);
	}

	int largePaints = 40;
	int finePaints = 30;
	int interval = 10;

	public void updateCharacter(int num, Bitmap bitmap) {
		Paint largePaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// ���û���
		Paint finePaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// ���û���
		largePaint.setTextSize(largePaints);// �����С
		finePaint.setTextSize(finePaints);// �����С
		largePaint.setTypeface(Typeface.DEFAULT_BOLD);// ����Ĭ�ϵĿ��
		finePaint.setTypeface(Typeface.DEFAULT_BOLD);// ����Ĭ�ϵĿ��
		largePaint.setColor(Color.WHITE);// ���õ���ɫ
		finePaint.setColor(Color.WHITE);// ���õ���ɫ
		// ���� ��
		canva.drawLine(2, hight / 2, width, hight / 2, largePaint);
		canva.drawLine(2, hight / 4, width, hight / 4, largePaint);
		canva.drawLine(2, hight / 2 + hight / 4, width, hight / 2 + hight / 4,
				largePaint);
		int j = 0;
		canva.drawLine(2, j, width, j, finePaint);
		for (int i10 = 0; i10 < 16; i10++) {
			j += hight / 16;
			canva.drawLine(2, j, width, j, finePaint);
		}
		// ���� ��
		canva.drawLine(width / 2, 2, width / 2, hight, largePaint);
		canva.drawLine(width / 4, 2, width / 4, hight, largePaint);
		canva.drawLine(width / 2 + width / 4, 2, width / 2 + width / 4, hight,
				largePaint);
		int j1 = 0;
		canva.drawLine(j1, 2, j1, hight, finePaint);
		for (int i10 = 0; i10 < 16; i10++) {
			j1 += width / 16;
			canva.drawLine(j1, 2, j1, hight, finePaint);
		}

		int i1 = hight / 16; // y�������
		int i2 = width / 16; // x�������
		if (num == 0) {
			// ��˾
			canva.drawText(EKaXinUserManger.getInstance().getusercompany(), i2,
					i1 + largePaints, largePaint);
			// ְ�� ����
			canva.drawText(EKaXinUserManger.getInstance().getuserdepartment()
					+ " " + EKaXinUserManger.getInstance().getuserduty(), i2,
					i1 + largePaints + finePaints + interval, finePaint);
			// ͷ��
			// Bitmap btm1 =
			// PhotoUtil.compressImageFromFile("/sdcard/ekaxin/avatar/140929120832");
			Bitmap btm0 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.head);
			i1 = hight / 4 + hight / 16;
			i2 = width / 16;
			Rect dst = new Rect(i2, i1, i2 + i2 * 4, i1
					+ (int) (hight / 16 * 2.5));// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm0, null, dst, largePaint);
			// ���� (����ǰһ���ĸ߶�)(���屾���и߶� 40)
			i1 = hight / 4 + hight / 16 + (int) (hight / 16 * 2.5);
			i2 = width / 16;
			canva.drawText(
					EKaXinUserManger.getInstance().getuschainname()
							+ " "
							+ EKaXinUserManger.getInstance()
									.getuserenglishname(), i2, i1 + largePaints
							+ interval, largePaint);
			// �ƶ��绰
			canva.drawText(EKaXinUserManger.getInstance().getusermovephone(),
					i2, i1 + finePaints + largePaints + interval + interval,
					finePaint);
			// ������Ϣ
			i1 = hight / 2 + (int) (hight / 16 * 2);
			i2 = width / 16;
			// ͼ�� 1 X���� Y��ǰ�����ĸߺͼ�� ��С�� ���� �߼�ǰ�����ĸߺͼ��
			Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst1 = new Rect(i2, i1, i2 * 2, i1 + i2);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm1, null, dst1, largePaint);
			// ���� 1 x���� y��ǰ�����ĸߺͼ��
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2, finePaint);
			// ͼ�� 2
			Bitmap btm2 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst2 = new Rect(i2, i1 + i2 + interval, i2 * 2, i1 + i2 * 2
					+ interval);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm2, null, dst2, largePaint);
			// ���� 2
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 2 + interval, finePaint);
			// ͼ��3
			Bitmap btm3 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst3 = new Rect(i2, i1 + i2 * 2 + interval * 2, i2 + i2, i1
					+ i2 * 3 + interval * 2);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm3, null, dst3, largePaint);
			// ���� 3
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 3 + interval * 2, finePaint);
			// ͼ�� 4
			Bitmap btm4 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst4 = new Rect(i2, i1 + i2 * 3 + interval * 3, i2 + i2, i1
					+ i2 * 4 + interval * 3);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm4, null, dst4, largePaint);
			// ���� 4
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 4 + interval * 3, finePaint);
			// ͼ�� 5
			Bitmap btm5 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst5 = new Rect(i2, i1 + i2 * 4 + interval * 4, i2 + i2, i1
					+ i2 * 5 + interval * 4);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm5, null, dst5, largePaint);
			// ���� 5
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 5 + interval * 4, finePaint);
			// ͼ�� 6
			Bitmap btm6 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst6 = new Rect(i2, i1 + i2 * 5 + interval * 5, i2 + i2, i1
					+ i2 * 6 + interval * 5);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm6, null, dst6, largePaint);
			// ���� 6
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 6 + interval * 5, finePaint);
		} else if (num == 1) {
			// ��˾
			canva.drawText(EKaXinUserManger.getInstance().getusercompany(), i2,
					i1 + largePaints, largePaint);
			// ������Ϣ
			i1 = hight / 16 + largePaints + interval * 2;
			i2 = width / 16;
			// ͼ�� 1 X���� Y��ǰ�����ĸߺͼ�� ��С�� ���� �߼�ǰ�����ĸߺͼ��
			Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst1 = new Rect(i2, i1, i2 * 2, i1 + i2);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm1, null, dst1, largePaint);
			// ���� 1 x���� y��ǰ�����ĸߺͼ��
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2, finePaint);
			// ͼ�� 2
			Bitmap btm2 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst2 = new Rect(i2, i1 + i2 + interval, i2 * 2, i1 + i2 * 2
					+ interval);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm2, null, dst2, largePaint);
			// ���� 2
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 2 + interval, finePaint);
			// ͼ��3
			Bitmap btm3 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst3 = new Rect(i2, i1 + i2 * 2 + interval * 2, i2 + i2, i1
					+ i2 * 3 + interval * 2);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm3, null, dst3, largePaint);
			// ���� 3
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 3 + interval * 2, finePaint);
			// ͼ�� 4
			Bitmap btm4 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst4 = new Rect(i2, i1 + i2 * 3 + interval * 3, i2 + i2, i1
					+ i2 * 4 + interval * 3);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm4, null, dst4, largePaint);
			// ���� 4
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 4 + interval * 3, finePaint);
			// ͼ�� 5
			Bitmap btm5 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst5 = new Rect(i2, i1 + i2 * 4 + interval * 4, i2 + i2, i1
					+ i2 * 5 + interval * 4);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm5, null, dst5, largePaint);
			// ���� 5
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 5 + interval * 4, finePaint);
			// ͼ�� 6
			Bitmap btm6 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst6 = new Rect(i2, i1 + i2 * 5 + interval * 5, i2 + i2, i1
					+ i2 * 6 + interval * 5);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm6, null, dst6, largePaint);
			// ���� 6
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 6 + interval * 5, finePaint);
			// ͷ��
			// Bitmap btm1 =
			// PhotoUtil.compressImageFromFile("/sdcard/ekaxin/avatar/140929120832");
			Bitmap btm0 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.head);
			i1 = hight / 2 + hight / 16;
			i2 = width / 2 - width / 16 * 2;
			Rect dst = new Rect(i2, i1, i2 + width / 16 * 4, i1
					+ (int) (hight / 16 * 2.5));// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm0, null, dst, largePaint);
			// ���� (����ǰһ���ĸ߶�)(���屾���и߶� 40)
			i1 = hight / 2 + hight / 16 + (int) (hight / 16 * 2.5);
			i2 = width
					/ 2
					- (int) largePaint.measureText(EKaXinUserManger
							.getInstance().getuschainname()
							+ " "
							+ EKaXinUserManger.getInstance()
									.getuserenglishname()) / 2;
			canva.drawText(
					EKaXinUserManger.getInstance().getuschainname()
							+ " "
							+ EKaXinUserManger.getInstance()
									.getuserenglishname(), i2, i1 + largePaints
							+ interval, largePaint);
			// ְ�� ����
			i2 = width
					/ 2
					- (int) finePaint.measureText(EKaXinUserManger
							.getInstance().getuserdepartment()
							+ " "
							+ EKaXinUserManger.getInstance().getuserduty()) / 2;
			canva.drawText(EKaXinUserManger.getInstance().getuserdepartment()
					+ " " + EKaXinUserManger.getInstance().getuserduty(), i2,
					i1 + largePaints + finePaints + interval * 2, finePaint);
			// �ƶ��绰
			i2 = width
					/ 2
					- (int) largePaint.measureText(EKaXinUserManger
							.getInstance().getusermovephone()) / 2;
			canva.drawText(EKaXinUserManger.getInstance().getusermovephone(),
					i2,
					i1 + finePaints + largePaints * 3 + interval + interval,
					largePaint);
		} else if (num == 2) {
			// ��˾
			canva.drawText(EKaXinUserManger.getInstance().getusercompany(), i2,
					i1 + largePaints, largePaint);
			// ְ�� ����
			canva.drawText(EKaXinUserManger.getInstance().getuserdepartment()
					+ " " + EKaXinUserManger.getInstance().getuserduty(), i2,
					i1 + largePaints + finePaints + interval, finePaint);
			// ͷ��
			// Bitmap btm1 =
			// PhotoUtil.compressImageFromFile("/sdcard/ekaxin/avatar/140929120832");
			Bitmap btm0 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.head);
			i1 = hight / 2 - hight / 16;
			i2 = width / 16;
			Rect dst = new Rect(i2, i1, i2 + i2 * 4, i1
					+ (int) (hight / 16 * 2.5));// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm0, null, dst, largePaint);
			// ���� (����ǰһ���ĸ߶�)(���屾���и߶� 40)
			i1 = hight / 2 + (int) (hight / 16 / 2);
			i2 = width / 16 + width / 16 * 4;
			canva.drawText(
					EKaXinUserManger.getInstance().getuschainname()
							+ " "
							+ EKaXinUserManger.getInstance()
									.getuserenglishname(), i2 + interval, i1
							+ largePaints, largePaint);
			// ������Ϣ
			i1 = hight / 2 + (int) (hight / 16 * 2);
			i2 = width / 16;
			// ͼ�� 1 X���� Y��ǰ�����ĸߺͼ�� ��С�� ���� �߼�ǰ�����ĸߺͼ��
			Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst1 = new Rect(i2, i1, i2 * 2, i1 + i2);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm1, null, dst1, largePaint);
			// ���� 1 x���� y��ǰ�����ĸߺͼ��
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2, finePaint);
			// ͼ�� 2
			Bitmap btm2 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst2 = new Rect(i2, i1 + i2 + interval, i2 * 2, i1 + i2 * 2
					+ interval);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm2, null, dst2, largePaint);
			// ���� 2
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 2 + interval, finePaint);
			// ͼ��3
			Bitmap btm3 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst3 = new Rect(i2, i1 + i2 * 2 + interval * 2, i2 + i2, i1
					+ i2 * 3 + interval * 2);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm3, null, dst3, largePaint);
			// ���� 3
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 3 + interval * 2, finePaint);
			// ͼ�� 4
			Bitmap btm4 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst4 = new Rect(i2, i1 + i2 * 3 + interval * 3, i2 + i2, i1
					+ i2 * 4 + interval * 3);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm4, null, dst4, largePaint);
			// ���� 4
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 4 + interval * 3, finePaint);
			// ͼ�� 5
			Bitmap btm5 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst5 = new Rect(i2, i1 + i2 * 4 + interval * 4, i2 + i2, i1
					+ i2 * 5 + interval * 4);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm5, null, dst5, largePaint);
			// ���� 5
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 5 + interval * 4, finePaint);
			// ͼ�� 6
			Bitmap btm6 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst6 = new Rect(i2, i1 + i2 * 5 + interval * 5, i2 + i2, i1
					+ i2 * 6 + interval * 5);// ��Ļ >>Ŀ�����
			canva.drawBitmap(btm6, null, dst6, largePaint);
			// ���� 6
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 6 + interval * 5, finePaint);
		} else if (num == 3) {

		} else if (num == 4) {

		} else if (num == 5) {

		} else if (num == 6) {

		} else if (num == 7) {

		} else if (num == 8) {

		} else if (num == 9) {

		}

		canva.save(Canvas.ALL_SAVE_FLAG);
		canva.restore();
		imgview.setImageBitmap(bitmap);
	}*/

	int width, hight;
	Canvas canva;
	int pattern;
	Bitmap backgroundImgView;

	public void updateCard() {
/**  
		if (SelectCardActivity.positions == 0) {
			pattern = 0;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t0);
		} else if (SelectCardActivity.positions == 1) {
			pattern = 1;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t1);
		} else if (SelectCardActivity.positions == 2) {
			pattern = 2;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t2);
		} else if (SelectCardActivity.positions == 3) {
			pattern = 3;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t3);
		} else if (SelectCardActivity.positions == 4) {
			pattern = 4;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t4);
		} else if (SelectCardActivity.positions == 5) {
			pattern = 5;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t5);
		} else if (SelectCardActivity.positions == 6) {
			pattern = 6;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t6);
		} else if (SelectCardActivity.positions == 7) {
			pattern = 7;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t7);
		} else if (SelectCardActivity.positions == 8) {
			pattern = 8;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t8);
		} else if (SelectCardActivity.positions == 9) {
			pattern = 9;
			backgroundImgView = BitmapFactory.decodeResource(
					this.getResources(), R.drawable.bcard_t9);
		}
		width = backgroundImgView.getWidth(); // ��ȡͼƬ��С
		hight = backgroundImgView.getHeight();
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // ����һ���յ�BItMap
		canva = new Canvas(icon);// ��ʼ���������Ƶ�ͼ��icon��
		Paint photoPaint = new Paint(); // ��������
		photoPaint.setDither(true); // ��ȡ��������ͼ�����
		photoPaint.setFilterBitmap(true);// ����һЩ
		Rect src = new Rect(0, 0, backgroundImgView.getWidth(),
				backgroundImgView.getHeight());// ����һ��ָ�����¾��ε�����
		Rect dst = new Rect(0, 0, width, hight);// ����һ��ָ�����¾��ε�����
		canva.drawBitmap(backgroundImgView, src, dst, photoPaint);
		updateCharacter(pattern, icon);*/
	}

	public void initBroadcastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.refreshFriend");
		getActivity().registerReceiver(mRefreshBroadcastReceiver, intentFilter);
	}

	private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("action.refreshFriend")) {
				updateCard();
			}
		}
	};

	class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return TOTAL_COUNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(R.drawable.bcard_d0 + position);
            ((ViewPager)container).addView(imageView, position);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView((ImageView)object);
        }
    }
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
        	//for(int i = 0 ; i<imageViews.length; i++){
    		//	if(i == position){
    		//		imageViews[i].setBackgroundResource(R.drawable.item3);
    		//	}else{
    		//		imageViews[i].setBackgroundResource(R.drawable.item4);
    		//	}
    		//}
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
            if (viewPagerContainer != null) {
                viewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }
}
