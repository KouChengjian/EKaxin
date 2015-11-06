package com.chenghui.ekaxin.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.NotesSqlistAdapter;
import com.chenghui.ekaxin.util.NotepadInfo;
import com.chenghui.ekaxin.view.HeaderLayout.onRightImageButtonClickListener;
import com.chenghui.ekaxin.view.dialog.DateTimePickerDialog;
import com.chenghui.ekaxin.view.dialog.TimeSelectDialog;
import com.chenghui.ekaxin.view.dialog.DateTimePickerDialog.OnDateTimeSetListener;


/**
 * @ClassName: EditNotepadActivity
 * @Description: ���±��༭
 * @author kcj
 * @date 2014-9-10 10:20
 */
public class EditNotepadActivity extends BaseActivity {

	// ����ӵ�ȫ����
	private NotesSqlistAdapter sqlbdAdepter;
	NotepadInfo notepadainfo;

	public EditText redactTitle;
	public EditText redactContent;

	public ImageView imgAddPicture;
	public ImageView imgClock;
	boolean b_imgclock = true;

	int id;
	View view;
	String[] str_temp = null;

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		LayoutInflater flater = LayoutInflater.from(this);
		view = flater.inflate(R.layout.notepad_listview_redact, null);
		setContentView(view);
		// ��ʼ��ȫ��
		allInit();
		// ���ղ��� -1Ϊ���� >=0Ϊ��ʾ
		Intent intent = getIntent();
		String examine = intent.getStringExtra("tag");
		String str_id = intent.getStringExtra("ID");
		id = Integer.valueOf(str_id).intValue();
		if (examine != null && examine.contains("examine")) {
			// Ԥ����Ϣ
			readInit();
			mHandler.sendEmptyMessageDelayed(HIDE, 200);
			return;
		} else {
			// ��ʼ����Ϣ
			initView();
			mHandler.sendEmptyMessageDelayed(HIDE, 200);
		}
	}

	public void allInit() {
		sqlbdAdepter = new NotesSqlistAdapter(this);
		sqlbdAdepter.open();

		notepadainfo = new NotepadInfo();

		redactTitle = (EditText) findViewById(R.id.edt_title);
		redactContent = (EditText) findViewById(R.id.et_content);

		imgAddPicture = (ImageView) findViewById(R.id.redact_pic);
		imgClock = (ImageView) findViewById(R.id.redact_clock);
	}

	public void initView() {
		initTopBarForBoth("�༭", R.drawable.base_action_bar_save_bg_selector,
				new onRightImageButtonClickListener() {
					@Override
					public void onClick(View v) {
						// ��ȡ�ؼ��е�����
						notepadainfo.title = redactTitle.getText().toString();
						// ʱ��
						final Calendar c = Calendar.getInstance();
						int mYear = c.get(Calendar.YEAR); // ��ȡ��ǰ���
						int mMonth = c.get(Calendar.MONTH); // ��ȡ��ǰ�·�
						int mDay = c.get(Calendar.DAY_OF_MONTH); // ��ȡ��ǰ�·ݵ����ں���
						int mHour = c.get(Calendar.HOUR_OF_DAY); // ��ȡ��ǰ��Сʱ��
						int mMinute = c.get(Calendar.MINUTE); // ��ȡ��ǰ�ķ�����
						notepadainfo.recordtime = String.valueOf(mYear) + "/"
								+ String.valueOf(mMonth) + "/"
								+ String.valueOf(mDay) + "   "
								+ String.valueOf(mHour) + ":"
								+ String.valueOf(mMinute);
						// ʱ������
						if (b_imgclock) {
							notepadainfo.clock = "0";
						} else {
							notepadainfo.clock = "1";
						}
						// ����
						notepadainfo.content = redactContent.getText()
								.toString();
						if (TextUtils.isEmpty(notepadainfo.title)) {
							Toast.makeText(EditNotepadActivity.this, "���������",
									Toast.LENGTH_LONG).show();
							return;
						}
						if (TextUtils.isEmpty(notepadainfo.content)) {
							Toast.makeText(EditNotepadActivity.this, "û������",
									Toast.LENGTH_LONG).show();
							return;
						}
						// ��������������ݿ���
						long colunm = sqlbdAdepter.insert(notepadainfo);
						if (colunm == -1) {
							Toast.makeText(EditNotepadActivity.this, "��ӹ��̴���",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(EditNotepadActivity.this,
									"�ɹ��������ID��" + String.valueOf(colunm),
									Toast.LENGTH_LONG).show();
						}
					}
				});

		imgAddPicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pickPhoto();
			}
		});

		imgClock.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (b_imgclock) {
					imgClock.setImageResource(R.drawable.notes_clock);
					showDialog();
					b_imgclock = false;
				} else {
					imgClock.setImageResource(R.drawable.status_phone);
					b_imgclock = true;
				}
			}
		});

	}

	/**
	 * �������
	 */
	private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, 111);
	}

	/**
	 * ����
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 111:
				showGalleryPhoto(data);
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * ��ʾͼƬ
	 */
	private void showGalleryPhoto(Intent data) {
		if (data == null) {
			return;
		}
		// ��ȡͼƬURi
		Uri photoUri = data.getData();
		if (photoUri == null) {
			return;
		}
		String[] pojo = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
		String picPath = null;
		if (cursor != null) {
			int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
			cursor.moveToFirst();
			picPath = cursor.getString(columnIndex);
			// cursor.close(); android.database.StaleDataException: Attempted to
			// access a cursor after it has been closed.

		}
		if (!TextUtils.isEmpty(picPath)) {
			redactContent.append(getDrawableStr(picPath));
		}
	}

	/**
	 * ��ͼƬת�ɿ���EditView��ʾ��CharSequence
	 * 
	 * @param picPath
	 *            ��Ҫ��ʾ��ͼƬ·��
	 * @return
	 */
	private CharSequence getDrawableStr(String picPath) {
		InputStream is;
		try {
			String str = "<img src=\"" + picPath + "\"/>";
			notepadainfo.path = picPath;
			is = new FileInputStream(picPath);
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inTempStorage = new byte[100 * 1024]; // 100 * 1024
			opts.inPreferredConfig = Bitmap.Config.RGB_565; // Ĭ����Bitmap.Config.ARGB_8888
			opts.inSampleSize = 4;
			/* ���������ֶ���Ҫ���ʹ�� ��˵��Ϊ�˽�Լ�ڴ� */
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			Bitmap bm = BitmapFactory.decodeStream(is, null, opts);

			final SpannableString ss = new SpannableString(str);
			// �������ͼƬ
			Drawable drawable = new BitmapDrawable(bm);
			// drawable.setBounds(2, 0, 400, 350);
			drawable.setBounds(0, 4, 450, 350);
			ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
			ss.setSpan(span, 0, ss.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
			// ss.setSpan(clickableSpan, 0, ss.length(),
			// Spannable.SPAN_INCLUSIVE_EXCLUSIVE); //ֻ�е�һ��ͼƬ���е����Ч��
			ss.setSpan(new MyClickableSpan(), 0, ss.length(),
					Spannable.SPAN_INCLUSIVE_EXCLUSIVE); // ��Щ�˵Ĵ������ss.removeSpan����setSpan()��
			return ss;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	public class MyClickableSpan extends ClickableSpan {
		@Override
		public void onClick(View widget) {

			Toast.makeText(EditNotepadActivity.this,
					"Image Clicked " + "ddddddddddddddd", Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * Ԥ����Ϣ
	 */
	public void readInit() {
		initTopBarForBoth("�鿴", R.drawable.base_action_bar_add_bg_selector,
				new onRightImageButtonClickListener() {
					@Override
					public void onClick(View v) {
						redactTitle.setEnabled(true);
						redactContent.setEnabled(true);
					}
				});
		NotepadInfo[] camera = sqlbdAdepter.queryOneData(id);
		if (camera == null) {
			Toast.makeText(EditNotepadActivity.this,
					"���ݿ���û��IDΪ" + String.valueOf(id) + "������", Toast.LENGTH_LONG)
					.show();
		}
		String str_camerainfo = camera[0].toString();
		str_temp = str_camerainfo.split("-");
		loadData();

		imgClock.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(EditNotepadActivity.this, TimeSelectDialog.class);
				startActivity(intent);
			}
		});
	}

	public void loadData() {
		redactTitle.setText(str_temp[1]);
		redactContent.setMovementMethod(LinkMovementMethod.getInstance()); // ���ͼƬ�ܲ����з�Ӧ���ܹؼ�
		updateContent(redactContent, str_temp[4]);
		redactTitle.setEnabled(false);
		redactContent.setEnabled(false);
	}

	public static final String IMG_START = "<img src=\"";
	public static final String IMG_END = "\"/>";
	private static final String TAG = "fycus";

	/**
	 * ��content������ͼƬ��ǩ<img>�������õ�EditText�С� �㷨������ aaaaa, <img
	 * src="/mnt/sdcard/xxx.png"/> bbbbbbbb��������<img
	 * src="/mnt/sdcard/yyyy.png/>dddddddd
	 * �ķ�������aaa������һ����/>��Ϊֹ��Ϊһ������Ԫ��Ȼ���ٴ���bbbb
	 * �������ڶ�����/>���������������Լ�����ȥ�ĵĴ���ʽ��ʵ��һ���ģ����������˵ݹ顣
	 * 
	 * @param etContent
	 * @param content
	 */
	private void updateContent(EditText etContent, String content) {
		// �ݹ����
		if (TextUtils.isEmpty(content)) {
			return;
		}
		Log.e(TAG, "content == " + content);

		int startIndex = 0, endIndex = 0;
		int imgStartIndex = content.indexOf(IMG_START);
		if (imgStartIndex < 0) {// û��<img>��ǩ��˵��û��ͼƬ��
			endIndex = content.length();
		} else {
			endIndex = imgStartIndex;
		}

		String str = content.substring(startIndex, endIndex);
		Log.e(TAG, "String1 == " + str);

		etContent.append(str);
		content = content.substring(endIndex, content.length());// ������str��ʾ���ַ���ɾ��
		Log.e(TAG, "content == " + content);

		// ���� img
		if (TextUtils.isEmpty(content)) {
			return;
		}

		int imgEndIndex = content.indexOf(IMG_END);
		str = content.substring(IMG_START.length(), imgEndIndex);
		Log.e(TAG, "String2 == " + str);
		etContent.append(getDrawableStr(str));

		content = content.substring(imgEndIndex + IMG_END.length(),
				content.length());// ������str��ʾ���ַ���ɾ��
		Log.e(TAG, "content == " + content);
		updateContent(etContent, content);
	}

	public void showDialog() {
		DateTimePickerDialog dialog = new DateTimePickerDialog(this,
				System.currentTimeMillis());
		dialog.setOnDateTimeSetListener(new OnDateTimeSetListener() {
			public void OnDateTimeSet(AlertDialog dialog, long date) {
				Toast.makeText(EditNotepadActivity.this,
						"������������ǣ�" + getStringDate(date), Toast.LENGTH_LONG)
						.show();
			}
		});
		dialog.show();
	}

	/**
	 * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String getStringDate(Long date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);

		return dateString;
	}

	private static final int HIDE = 100;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HIDE:
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // ǿ�����ؼ���
				boolean isOpen = imm.isActive();// isOpen������true�����ʾ���뷨��
				Log.e("isOpen", String.valueOf(isOpen));
				break;
			}
		}
	};
}
