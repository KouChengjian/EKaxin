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
		// ��ʼ������
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
		// ��ȡ����
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		String date = sDateFormat.format(new java.util.Date());
		// ��ʼ��
		//DateSurface.init(txtView);
		// ��������
		txtView.setText(date);
		// ����ѡ����
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		Date mydate = new Date(); // ��ȡ��ǰ����Date����
		calendar.setTime(mydate);// //ΪCalendar��������ʱ��Ϊ��ǰ����
		year = calendar.get(Calendar.YEAR); // ��ȡCalendar�����е���
		month = calendar.get(Calendar.MONTH);// ��ȡCalendar�����е���
		day = calendar.get(Calendar.DAY_OF_MONTH);// ��ȡ����µĵڼ���
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
		 * params��view�����¼���������� params��myyear����ǰѡ����� params��monthOfYear����ǰѡ�����
		 * params��dayOfMonth����ǰѡ�����
		 */
		@Override
		public void onDateSet(DatePicker view, int myyear, int monthOfYear,
				int dayOfMonth) {
			// �޸�year��month��day�ı���ֵ���Ա��Ժ󵥻���ťʱ��DatePickerDialog����ʾ��һ���޸ĺ��ֵ
			year = myyear;
			month = monthOfYear;
			day = dayOfMonth;
			// ��������
			updateDate();
		}

		// ��DatePickerDialog�ر�ʱ������������ʾ
		private void updateDate() {
			// ��TextView����ʾ����
			txtView.setText(year + "-" + (month + 1) + "-" + day);
		}
	};
}
