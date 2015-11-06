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
 * @Description: 个人主页
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
	
	// 名片预览
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
		// 标题
		initHeaderUi();
		// 初始化popupWindows
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
		// 标题
		mTitleBarView.setTitleLeft(R.string.space);		
		mTitleBarView.setTitleRight(R.string.notepad);
		// 标题左右两边
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
		// 标题中间
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
		
		// popup子项
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
		//content = "无信息，请点击编辑";
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
		int width = backgroundImgView.getWidth(); // 获取图片大小
		int hight = backgroundImgView.getHeight();
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些
		Rect src = new Rect(0, 0, backgroundImgView.getWidth(),
				backgroundImgView.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
		canvas.drawBitmap(backgroundImgView, src, dst, photoPaint);

		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		textPaint.setTextSize(40);// 字体大小
		textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		textPaint.setColor(Color.WHITE);// 采用的颜色
		// 计算位置
		int i1 = hight / 2 + 20;
		int i2 = width / 2 - (int) textPaint.measureText(str) / 2;
		canvas.drawText(str, i2, i1, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制20, 26
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		imageView.setImageBitmap(icon);
	}

	int largePaints = 40;
	int finePaints = 30;
	int interval = 10;

	public void updateCharacter(int num, Bitmap bitmap) {
		Paint largePaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		Paint finePaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
		largePaint.setTextSize(largePaints);// 字体大小
		finePaint.setTextSize(finePaints);// 字体大小
		largePaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		finePaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
		largePaint.setColor(Color.WHITE);// 采用的颜色
		finePaint.setColor(Color.WHITE);// 采用的颜色
		// 测试 高
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
		// 测试 宽
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

		int i1 = hight / 16; // y起点坐标
		int i2 = width / 16; // x起点坐标
		if (num == 0) {
			// 公司
			canva.drawText(EKaXinUserManger.getInstance().getusercompany(), i2,
					i1 + largePaints, largePaint);
			// 职务 部门
			canva.drawText(EKaXinUserManger.getInstance().getuserdepartment()
					+ " " + EKaXinUserManger.getInstance().getuserduty(), i2,
					i1 + largePaints + finePaints + interval, finePaint);
			// 头像
			// Bitmap btm1 =
			// PhotoUtil.compressImageFromFile("/sdcard/ekaxin/avatar/140929120832");
			Bitmap btm0 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.head);
			i1 = hight / 4 + hight / 16;
			i2 = width / 16;
			Rect dst = new Rect(i2, i1, i2 + i2 * 4, i1
					+ (int) (hight / 16 * 2.5));// 屏幕 >>目标矩形
			canva.drawBitmap(btm0, null, dst, largePaint);
			// 姓名 (加上前一个的高度)(字体本身有高度 40)
			i1 = hight / 4 + hight / 16 + (int) (hight / 16 * 2.5);
			i2 = width / 16;
			canva.drawText(
					EKaXinUserManger.getInstance().getuschainname()
							+ " "
							+ EKaXinUserManger.getInstance()
									.getuserenglishname(), i2, i1 + largePaints
							+ interval, largePaint);
			// 移动电话
			canva.drawText(EKaXinUserManger.getInstance().getusermovephone(),
					i2, i1 + finePaints + largePaints + interval + interval,
					finePaint);
			// 其他信息
			i1 = hight / 2 + (int) (hight / 16 * 2);
			i2 = width / 16;
			// 图标 1 X不变 Y加前面数的高和间距 大小： 宽不变 高加前面数的高和间距
			Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst1 = new Rect(i2, i1, i2 * 2, i1 + i2);// 屏幕 >>目标矩形
			canva.drawBitmap(btm1, null, dst1, largePaint);
			// 内容 1 x不变 y加前面数的高和间距
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2, finePaint);
			// 图标 2
			Bitmap btm2 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst2 = new Rect(i2, i1 + i2 + interval, i2 * 2, i1 + i2 * 2
					+ interval);// 屏幕 >>目标矩形
			canva.drawBitmap(btm2, null, dst2, largePaint);
			// 内容 2
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 2 + interval, finePaint);
			// 图标3
			Bitmap btm3 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst3 = new Rect(i2, i1 + i2 * 2 + interval * 2, i2 + i2, i1
					+ i2 * 3 + interval * 2);// 屏幕 >>目标矩形
			canva.drawBitmap(btm3, null, dst3, largePaint);
			// 内容 3
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 3 + interval * 2, finePaint);
			// 图标 4
			Bitmap btm4 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst4 = new Rect(i2, i1 + i2 * 3 + interval * 3, i2 + i2, i1
					+ i2 * 4 + interval * 3);// 屏幕 >>目标矩形
			canva.drawBitmap(btm4, null, dst4, largePaint);
			// 内容 4
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 4 + interval * 3, finePaint);
			// 图标 5
			Bitmap btm5 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst5 = new Rect(i2, i1 + i2 * 4 + interval * 4, i2 + i2, i1
					+ i2 * 5 + interval * 4);// 屏幕 >>目标矩形
			canva.drawBitmap(btm5, null, dst5, largePaint);
			// 内容 5
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 5 + interval * 4, finePaint);
			// 图标 6
			Bitmap btm6 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst6 = new Rect(i2, i1 + i2 * 5 + interval * 5, i2 + i2, i1
					+ i2 * 6 + interval * 5);// 屏幕 >>目标矩形
			canva.drawBitmap(btm6, null, dst6, largePaint);
			// 内容 6
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 6 + interval * 5, finePaint);
		} else if (num == 1) {
			// 公司
			canva.drawText(EKaXinUserManger.getInstance().getusercompany(), i2,
					i1 + largePaints, largePaint);
			// 其他信息
			i1 = hight / 16 + largePaints + interval * 2;
			i2 = width / 16;
			// 图标 1 X不变 Y加前面数的高和间距 大小： 宽不变 高加前面数的高和间距
			Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst1 = new Rect(i2, i1, i2 * 2, i1 + i2);// 屏幕 >>目标矩形
			canva.drawBitmap(btm1, null, dst1, largePaint);
			// 内容 1 x不变 y加前面数的高和间距
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2, finePaint);
			// 图标 2
			Bitmap btm2 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst2 = new Rect(i2, i1 + i2 + interval, i2 * 2, i1 + i2 * 2
					+ interval);// 屏幕 >>目标矩形
			canva.drawBitmap(btm2, null, dst2, largePaint);
			// 内容 2
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 2 + interval, finePaint);
			// 图标3
			Bitmap btm3 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst3 = new Rect(i2, i1 + i2 * 2 + interval * 2, i2 + i2, i1
					+ i2 * 3 + interval * 2);// 屏幕 >>目标矩形
			canva.drawBitmap(btm3, null, dst3, largePaint);
			// 内容 3
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 3 + interval * 2, finePaint);
			// 图标 4
			Bitmap btm4 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst4 = new Rect(i2, i1 + i2 * 3 + interval * 3, i2 + i2, i1
					+ i2 * 4 + interval * 3);// 屏幕 >>目标矩形
			canva.drawBitmap(btm4, null, dst4, largePaint);
			// 内容 4
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 4 + interval * 3, finePaint);
			// 图标 5
			Bitmap btm5 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst5 = new Rect(i2, i1 + i2 * 4 + interval * 4, i2 + i2, i1
					+ i2 * 5 + interval * 4);// 屏幕 >>目标矩形
			canva.drawBitmap(btm5, null, dst5, largePaint);
			// 内容 5
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 5 + interval * 4, finePaint);
			// 图标 6
			Bitmap btm6 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst6 = new Rect(i2, i1 + i2 * 5 + interval * 5, i2 + i2, i1
					+ i2 * 6 + interval * 5);// 屏幕 >>目标矩形
			canva.drawBitmap(btm6, null, dst6, largePaint);
			// 内容 6
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 6 + interval * 5, finePaint);
			// 头像
			// Bitmap btm1 =
			// PhotoUtil.compressImageFromFile("/sdcard/ekaxin/avatar/140929120832");
			Bitmap btm0 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.head);
			i1 = hight / 2 + hight / 16;
			i2 = width / 2 - width / 16 * 2;
			Rect dst = new Rect(i2, i1, i2 + width / 16 * 4, i1
					+ (int) (hight / 16 * 2.5));// 屏幕 >>目标矩形
			canva.drawBitmap(btm0, null, dst, largePaint);
			// 姓名 (加上前一个的高度)(字体本身有高度 40)
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
			// 职务 部门
			i2 = width
					/ 2
					- (int) finePaint.measureText(EKaXinUserManger
							.getInstance().getuserdepartment()
							+ " "
							+ EKaXinUserManger.getInstance().getuserduty()) / 2;
			canva.drawText(EKaXinUserManger.getInstance().getuserdepartment()
					+ " " + EKaXinUserManger.getInstance().getuserduty(), i2,
					i1 + largePaints + finePaints + interval * 2, finePaint);
			// 移动电话
			i2 = width
					/ 2
					- (int) largePaint.measureText(EKaXinUserManger
							.getInstance().getusermovephone()) / 2;
			canva.drawText(EKaXinUserManger.getInstance().getusermovephone(),
					i2,
					i1 + finePaints + largePaints * 3 + interval + interval,
					largePaint);
		} else if (num == 2) {
			// 公司
			canva.drawText(EKaXinUserManger.getInstance().getusercompany(), i2,
					i1 + largePaints, largePaint);
			// 职务 部门
			canva.drawText(EKaXinUserManger.getInstance().getuserdepartment()
					+ " " + EKaXinUserManger.getInstance().getuserduty(), i2,
					i1 + largePaints + finePaints + interval, finePaint);
			// 头像
			// Bitmap btm1 =
			// PhotoUtil.compressImageFromFile("/sdcard/ekaxin/avatar/140929120832");
			Bitmap btm0 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.head);
			i1 = hight / 2 - hight / 16;
			i2 = width / 16;
			Rect dst = new Rect(i2, i1, i2 + i2 * 4, i1
					+ (int) (hight / 16 * 2.5));// 屏幕 >>目标矩形
			canva.drawBitmap(btm0, null, dst, largePaint);
			// 姓名 (加上前一个的高度)(字体本身有高度 40)
			i1 = hight / 2 + (int) (hight / 16 / 2);
			i2 = width / 16 + width / 16 * 4;
			canva.drawText(
					EKaXinUserManger.getInstance().getuschainname()
							+ " "
							+ EKaXinUserManger.getInstance()
									.getuserenglishname(), i2 + interval, i1
							+ largePaints, largePaint);
			// 其他信息
			i1 = hight / 2 + (int) (hight / 16 * 2);
			i2 = width / 16;
			// 图标 1 X不变 Y加前面数的高和间距 大小： 宽不变 高加前面数的高和间距
			Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst1 = new Rect(i2, i1, i2 * 2, i1 + i2);// 屏幕 >>目标矩形
			canva.drawBitmap(btm1, null, dst1, largePaint);
			// 内容 1 x不变 y加前面数的高和间距
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2, finePaint);
			// 图标 2
			Bitmap btm2 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst2 = new Rect(i2, i1 + i2 + interval, i2 * 2, i1 + i2 * 2
					+ interval);// 屏幕 >>目标矩形
			canva.drawBitmap(btm2, null, dst2, largePaint);
			// 内容 2
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 2 + interval, finePaint);
			// 图标3
			Bitmap btm3 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst3 = new Rect(i2, i1 + i2 * 2 + interval * 2, i2 + i2, i1
					+ i2 * 3 + interval * 2);// 屏幕 >>目标矩形
			canva.drawBitmap(btm3, null, dst3, largePaint);
			// 内容 3
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 3 + interval * 2, finePaint);
			// 图标 4
			Bitmap btm4 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst4 = new Rect(i2, i1 + i2 * 3 + interval * 3, i2 + i2, i1
					+ i2 * 4 + interval * 3);// 屏幕 >>目标矩形
			canva.drawBitmap(btm4, null, dst4, largePaint);
			// 内容 4
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 4 + interval * 3, finePaint);
			// 图标 5
			Bitmap btm5 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst5 = new Rect(i2, i1 + i2 * 4 + interval * 4, i2 + i2, i1
					+ i2 * 5 + interval * 4);// 屏幕 >>目标矩形
			canva.drawBitmap(btm5, null, dst5, largePaint);
			// 内容 5
			canva.drawText(EKaXinUserManger.getInstance().getuserphone(), i2
					* 2 + interval, i1 + i2 * 5 + interval * 4, finePaint);
			// 图标 6
			Bitmap btm6 = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.login_icon_account);
			Rect dst6 = new Rect(i2, i1 + i2 * 5 + interval * 5, i2 + i2, i1
					+ i2 * 6 + interval * 5);// 屏幕 >>目标矩形
			canva.drawBitmap(btm6, null, dst6, largePaint);
			// 内容 6
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
		width = backgroundImgView.getWidth(); // 获取图片大小
		hight = backgroundImgView.getHeight();
		Bitmap icon = Bitmap
				.createBitmap(width, hight, Bitmap.Config.ARGB_8888); // 建立一个空的BItMap
		canva = new Canvas(icon);// 初始化画布绘制的图像到icon上
		Paint photoPaint = new Paint(); // 建立画笔
		photoPaint.setDither(true); // 获取跟清晰的图像采样
		photoPaint.setFilterBitmap(true);// 过滤一些
		Rect src = new Rect(0, 0, backgroundImgView.getWidth(),
				backgroundImgView.getHeight());// 创建一个指定的新矩形的坐标
		Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
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
