package com.chenghui.ekaxin.view.dialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.ui.BaseActivity;
import com.chenghui.ekaxin.util.PixelUtil;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

public class TimeSelectDialog extends BaseActivity {

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.dialog_notepad);
		initView();
	}

	public static int dialogWidth;
	public static int dialogHeight;
	//DateSurface dateSurface;

	public void initView() {
		dialogWidth = mScreenWidth - PixelUtil.dp2px(20);
		dialogHeight = mScreenHeight - PixelUtil.dp2px(120);
		//dateSurface = (DateSurface) findViewById(R.id.date_surface);
		initTime();
		// 初始化日历
		initDate();
	}

	public void initTime() {

	}

	private int year;
	private int month;
	private int day;
	TextView txtView;
	ImageView imgView;
	DatePickerDialog dialog;

	public void initDate() {
		txtView = (TextView) findViewById(R.id.txt_date);
		imgView = (ImageView) findViewById(R.id.imgView);
		// 获取日历
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		String date = sDateFormat.format(new java.util.Date());
		// 初始化
		//DateSurface.init(txtView);
		// 设置日历
		txtView.setText(date);
		// 日历选择器
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Date mydate = new Date(); // 获取当前日期Date对象
		calendar.setTime(mydate);// //为Calendar对象设置时间为当前日期
		year = calendar.get(Calendar.YEAR); // 获取Calendar对象中的年
		month = calendar.get(Calendar.MONTH);// 获取Calendar对象中的月
		day = calendar.get(Calendar.DAY_OF_MONTH);// 获取这个月的第几天
		final DatePickerDialog dialog1 = new DatePickerDialog(this,
				Datelistener, year, month, day);
		txtView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog1.show();
			}
		});
	}

	private DatePickerDialog.OnDateSetListener Datelistener = new DatePickerDialog.OnDateSetListener() {
		/**
		 * params：view：该事件关联的组件 params：myyear：当前选择的年 params：monthOfYear：当前选择的月
		 * params：dayOfMonth：当前选择的日
		 */
		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,
				int dayOfMonth) {
			// 修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
			year = myyear;
			month = monthOfYear;
			day = dayOfMonth;
			// 更新日期
			updateDate();
		}

		// 当DatePickerDialog关闭时，更新日期显示
		private void updateDate() {
			// 在TextView上显示日期
			txtView.setText(year + "-" + (month + 1) + "-" + day);
		}
	};
}
