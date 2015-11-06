package com.chenghui.ekaxin.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;







import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.EditCardMessage;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.ui.fragment.UserSettingFragment;
import com.chenghui.ekaxin.util.CacheUtils;
import com.chenghui.ekaxin.util.UserCharDB;
import com.chenghui.ekaxin.view.CircleImageView;
import com.chenghui.ekaxin.view.HeaderLayout.onRightImageButtonClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/** 
 * @ClassName: EditCardActivity
 * @Description: 个人名片信息
 * @author smile
 * @date 2014-12-5 下午2:55:19
 */
public class EditCardActivity extends BaseActivity implements OnClickListener{

	CircleImageView imgHeader;
	TextView[] textView;
	ScrollView layout_all;
	RelativeLayout[] relativeLayout;
	
	static final int UPDATE_SEX = 11;
	static final int UPDATE_ICON = 12;
	static final int GO_LOGIN = 13;
	static final int UPDATE_SIGN = 14;
	static final int EDIT_SIGN = 15;
	
	View viewChinese ,viewEnglish ,viewPhone ,viewQQ , viewWeiXin ,viewMessage ,
	viewLocation ,viewDepartment , viewGongsi ,viewCall ,viewChuanzhen;
	EditText editChinese ,editEnglish ,editPhone ,editQQ , editWeiXin ,editMessage ,
	editLocation ,editDepartment , editGongsi ,editCall ,editChuanzhen;

	User user;
	
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_editcard);
		User users = userManager.getCurrentUser(User.class);
		User[] camera = userCharDB.queryOneData(users.getUsername(),UserCharDB.DB_TABLE);
		user = camera[0];
		initHeader();
		/** UI */
		layout_all = (ScrollView) findViewById(R.id.sv_all);
		imgHeader = (CircleImageView) findViewById(R.id.card_redact_head_phone);
		editChinese = (EditText)findViewById(R.id.edt_editcard_content_chinese);
		editEnglish = (EditText)findViewById(R.id.edt_editcard_content_english);
		editPhone = (EditText)findViewById(R.id.edt_editcard_content_phone);
		editQQ = (EditText)findViewById(R.id.edt_editcard_content_qq);
		editWeiXin = (EditText)findViewById(R.id.edt_editcard_content_weixin);
		editMessage = (EditText)findViewById(R.id.edt_editcard_content_message);
		editLocation = (EditText)findViewById(R.id.edt_editcard_content_location);
		editDepartment = (EditText)findViewById(R.id.edt_editcard_content_department);
		editGongsi = (EditText)findViewById(R.id.edt_editcard_content_gongsi);
		editCall = (EditText)findViewById(R.id.edt_editcard_content_call);
		editChuanzhen = (EditText)findViewById(R.id.edt_editcard_content_chuanzhen);
		viewChinese = (View)findViewById(R.id.v_editcard_select_chinese);
		viewEnglish = (View)findViewById(R.id.v_editcard_select_english);
		viewPhone = (View)findViewById(R.id.v_editcard_select_phone);
		viewQQ = (View)findViewById(R.id.v_editcard_select_qq);
		viewWeiXin = (View)findViewById(R.id.v_editcard_select_weixin);
		viewMessage = (View)findViewById(R.id.v_editcard_select_message);
		viewLocation = (View)findViewById(R.id.v_editcard_select_location);
		viewDepartment = (View)findViewById(R.id.v_editcard_select_department);
		viewGongsi = (View)findViewById(R.id.v_editcard_select_gongsi);
		viewCall = (View)findViewById(R.id.v_editcard_select_call);
		viewChuanzhen = (View)findViewById(R.id.v_editcard_select_chuanzhen);
		editChinese.setFocusable(false);
		editEnglish.setFocusable(false);
		editPhone.setFocusable(false);
		editQQ.setFocusable(false);
		editWeiXin.setFocusable(false);
		editMessage.setFocusable(false);
		editLocation.setFocusable(false);
		editDepartment.setFocusable(false);
		editGongsi.setFocusable(false);
		editCall.setFocusable(false);
		editChuanzhen.setFocusable(false);
		
		imgHeader.setOnClickListener(this);
	}
	
	public void initHeader(){
		initTopBarForBoth("编辑名片", R.drawable.base_action_bar_add_bg_selector,
				new onRightImageButtonClickListener() {
					public void onClick(View v) {
						//startAnimActivity(SelectCardActivity.class);
						changeHeader();
					}
				});
	}
	
	public void changeHeader(){
		initTopBarForBoth("编辑名片", R.drawable.base_action_bar_save_bg_selector,
				new onRightImageButtonClickListener() {
					public void onClick(View v) {
						update();
						
					}
				});
		focusChangeListener();
	}

	@SuppressWarnings("deprecation")
	public void focusChangeListener() {
		editEnglish.setFocusable(true);
		editEnglish.setFocusableInTouchMode(true);
		editEnglish.requestFocus();
		editPhone.setFocusable(true);
		editPhone.setFocusableInTouchMode(true);
		editPhone.requestFocus();
		editQQ.setFocusable(true);
		editQQ.setFocusableInTouchMode(true);
		editQQ.requestFocus();
		editWeiXin.setFocusable(true);
		editWeiXin.setFocusableInTouchMode(true);
		editWeiXin.requestFocus();
		editMessage.setFocusable(true);
		editMessage.setFocusableInTouchMode(true);
		editMessage.requestFocus();
		editLocation.setFocusable(true);
		editLocation.setFocusableInTouchMode(true);
		editLocation.requestFocus();
		editDepartment.setFocusable(true);
		editDepartment.setFocusableInTouchMode(true);
		editDepartment.requestFocus();
		editGongsi.setFocusable(true);
		editGongsi.setFocusableInTouchMode(true);
		editGongsi.requestFocus();
		editCall.setFocusable(true);
		editCall.setFocusableInTouchMode(true);
		editCall.requestFocus();
		editChuanzhen.setFocusable(true);
		editChuanzhen.setFocusableInTouchMode(true);
		editChuanzhen.requestFocus();
		editChinese.setFocusable(true);
		editChinese.setFocusableInTouchMode(true);
		editChinese.requestFocus();
		viewChinese.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
		
		editChinese.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewChinese.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewChinese.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editEnglish.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewEnglish.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewEnglish.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewPhone.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewPhone.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editQQ.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewQQ.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewQQ.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editWeiXin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewWeiXin.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewWeiXin.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewMessage.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewMessage.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewLocation.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewLocation.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editDepartment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewDepartment.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewDepartment.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editGongsi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewGongsi.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewGongsi.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editCall.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewCall.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewCall.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
		editChuanzhen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					viewChuanzhen.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select));
				} else {// 失去焦点
					viewChuanzhen.setBackgroundDrawable(getResources().getDrawable(R.drawable.edt_select_p));
				}
			}
		});
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initPersonalInfo();
	}
	
	private void initPersonalInfo(){
        if(user == null){
	        return;
        }
		editChinese.setText(TextUtils.isEmpty(user.getUserChainName()) ? "":user.getUserChainName());
		editEnglish.setText(TextUtils.isEmpty(user.getUserEnglishName()) ? "":user.getUserEnglishName());
		editPhone.setText(TextUtils.isEmpty(user.getUserMovePhone()) ? "":user.getUserMovePhone());
		editQQ.setText(TextUtils.isEmpty(user.getUserQQ()) ? "":user.getUserQQ());
		editWeiXin.setText(TextUtils.isEmpty(user.getUserWeiXin()) ? "":user.getUserWeiXin());
		editMessage.setText(TextUtils.isEmpty(user.getUserEmeil()) ? "":user.getUserEmeil());
		editLocation.setText(TextUtils.isEmpty(user.getUserCompany()) ? "":user.getUserCompany());
		editDepartment.setText(TextUtils.isEmpty(user.getUserDepartment()) ? "":user.getUserDepartment());
		editGongsi.setText(TextUtils.isEmpty(user.getUserDuty()) ? "":user.getUserDuty());
		editCall.setText(TextUtils.isEmpty(user.getUserOfficePhone()) ? "":user.getUserOfficePhone());
		editChuanzhen.setText(TextUtils.isEmpty(user.getUserFax()) ? "":user.getUserFax());
		/**
		 *   头像
		 */
		if(user != null){
			String avatarFile = user.getAvatar();
			if(null != avatarFile){
				ImageLoader.getInstance()
				.displayImage(avatarFile, imgHeader, 
						CustomApplcation.getInstance().getOptions(R.drawable.user_icon_default_main),
						new SimpleImageLoadingListener(){
							@Override
							public void onLoadingComplete(String imageUri, View view,
									Bitmap loadedImage) {
								// TODO Auto-generated method stub
								super.onLoadingComplete(imageUri, view, loadedImage);
							}
				});
			}
		}
		
	}

	PopupWindow avatorPop;
	RelativeLayout layout_choose;
	RelativeLayout layout_photo;
	String dateTime;
	
	@SuppressWarnings({ "deprecation" })
	private void showAvatarPop(){
		View view = LayoutInflater.from(this).inflate(R.layout.pop_showavator, null);
		layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
		layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);
		// 拍照
		layout_photo.setOnClickListener(new OnClickListener() {
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View arg0) {
				layout_choose.setBackgroundColor(getResources().getColor(
						R.color.base_color_text_white));
				layout_photo.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.pop_bg_press));
				/** 
				File dir = new File(EConstants.MyAvatarDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				// 原图   
				File file = new File(dir, new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
				filePath = file.getAbsolutePath();// 获取相片的保存路径
				Uri imageUri = Uri.fromFile(file);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent,EConstants.REQUESTCODE_UPLOADAVATAR_CAMERA); */
				avatorPop.dismiss();
				Date date = new Date(System.currentTimeMillis());
				dateTime = date.getTime() + "";
				getAvataFromCamera();
			}
		});
		// 选择相片
		layout_choose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				layout_photo.setBackgroundColor(getResources().getColor(
						R.color.base_color_text_white));
				layout_choose.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.pop_bg_press));
				/**  
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent,EConstants.REQUESTCODE_UPLOADAVATAR_LOCATION);*/
				avatorPop.dismiss();
				Date date1 = new Date(System.currentTimeMillis());
				dateTime = date1.getTime() + "";
				getAvataFromAlbum();
			}
		});
		
		avatorPop = new PopupWindow(view, mScreenWidth, 600);
		avatorPop.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					avatorPop.dismiss();
					return true;
				}
				return false;
			}
		});
		avatorPop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		avatorPop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		avatorPop.setTouchable(true);
		avatorPop.setFocusable(true);
		avatorPop.setOutsideTouchable(true);
		avatorPop.setBackgroundDrawable(new BitmapDrawable());
		// 动画效果 从底部弹起
		avatorPop.setAnimationStyle(R.style.Animations_GrowFromBottom);
		avatorPop.showAtLocation(layout_all, Gravity.BOTTOM, 0, 0);
	}
	
	private void getAvataFromAlbum(){
		Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
		intent2.setType("image/*");
		startActivityForResult(intent2, 2);
	}
	
	private void getAvataFromCamera(){
		File f = new File(CacheUtils.getCacheDirectory(this, true, "icon") + dateTime);
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Uri uri = Uri.fromFile(f);
		Log.e("uri", uri + "");
		
		Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		startActivityForResult(camera, 1);
	}
	
	String iconUrl;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK){
			switch (requestCode) {
			case UPDATE_SEX:
				initPersonalInfo();
				break;
			case UPDATE_ICON:
				initPersonalInfo();
				//iconLayout.performClick();
				break;
			case UPDATE_SIGN:
				initPersonalInfo();
				//signLayout.performClick();
				break;
			case EDIT_SIGN:
				initPersonalInfo();
				break;
			case 1:
				String files =CacheUtils.getCacheDirectory(this, true, "icon") + dateTime;
				File file = new File(files);
				if(file.exists()&&file.length() > 0){
					Uri uri = Uri.fromFile(file);
					startPhotoZoom(uri);
				}else{
					
				}
				break;
			case 2:
				if (data == null) {
					return;
				}
				startPhotoZoom(data.getData());
				break;
			case 3:
				if (data != null) {
					Bundle extras = data.getExtras();
					if (extras != null) {
						Bitmap bitmap = extras.getParcelable("data");
						// 锟斤拷锟斤拷图片
						iconUrl = saveToSdCard(bitmap);
						imgHeader.setImageBitmap(bitmap);
						updateIcon(iconUrl);
					}
				}
				break;
			case GO_LOGIN:
				initPersonalInfo();
				//logout.setText("退出登录");
				break;
			default:
				break;
			}
		}
	}
	
	public String saveToSdCard(Bitmap bitmap){
		String files =CacheUtils.getCacheDirectory(this, true, "icon") + dateTime+"_12";
		File file=new File(files);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file.getAbsolutePath();
	}
	
	
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 锟斤拷锟斤拷锟斤拷锟絚rop=true锟斤拷锟斤拷锟斤拷锟节匡拷锟斤拷锟斤拷Intent锟斤拷锟斤拷锟斤拷锟斤拷示锟斤拷VIEW锟缴裁硷拷
		// aspectX aspectY 锟角匡拷叩谋锟斤拷锟�
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 锟角裁硷拷图片锟斤拷锟�
		intent.putExtra("outputX", 120);
		intent.putExtra("outputY", 120);
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);// 去锟斤拷锟节憋拷
		intent.putExtra("scaleUpIfNeeded", true);// 去锟斤拷锟节憋拷
		// intent.putExtra("noFaceDetection", true);//锟斤拷锟斤拷识锟斤拷
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);

	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.card_redact_head_phone:
			showAvatarPop();
			break;
		}
	}

	/**
	 *  更新头像
	 */
	private void updateIcon(String avataPath){
		if(avataPath!=null){
			final BmobFile file = new BmobFile(new File(avataPath));
			file.upload(this, new UploadFileListener() {
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					//LogUtils.i(TAG, "上传文件成功。"+file.getFileUrl());
					User currentUser = BmobUser.getCurrentUser(EditCardActivity.this, User.class);
					currentUser.setAvatar(file.getFileUrl());
					currentUser.update(EditCardActivity.this, new UpdateListener() {
						
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							ShowToast("更改头像成功。");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
							//if(mIProgressControllor!=null)
							//mIProgressControllor.hideActionBarProgress();
							ShowToast( "更新头像失败。请检查网络~");
							//LogUtils.i(TAG,"更新失败2-->"+arg1);
						}
					});
				}

				@Override
				public void onProgress(Integer arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					ShowToast("上传头像失败。请检查网络~");
					//LogUtils.i(TAG, "上传文件失败。"+arg1);
				}
			});
		}
	}

	/**
	 *  更新资料
	 */
	private void update(){
		User currentUser = userManager.getCurrentUser(User.class);
		if(TextUtils.isEmpty(editChinese.getText().toString())){
			ShowToast("中文名不能为空");
			return;
		}
		if(TextUtils.isEmpty(editPhone.getText().toString())){
			ShowToast("手机不能为空");
			return;
		}
		if(TextUtils.isEmpty(editLocation.getText().toString())){
			ShowToast("公司不能为空");
			return;
		}
		if(TextUtils.isEmpty(editGongsi.getText().toString())){
			ShowToast("职务不能为空");
			return;
		}
		currentUser.setUserChainName(TextUtils.isEmpty(editChinese.getText()) ? "":editChinese.getText().toString());
		currentUser.setUserEnglishName(TextUtils.isEmpty(editEnglish.getText()) ? "":editEnglish.getText().toString());
		currentUser.setUserMovePhone(TextUtils.isEmpty(editPhone.getText()) ? "":editPhone.getText().toString());
		currentUser.setUserQQ(TextUtils.isEmpty(editQQ.getText()) ? "":editQQ.getText().toString());
		currentUser.setUserWeiXin(TextUtils.isEmpty(editWeiXin.getText()) ? "":editWeiXin.getText().toString());
		currentUser.setUserEmeil(TextUtils.isEmpty(editMessage.getText()) ? "":editMessage.getText().toString());
		currentUser.setUserCompany(TextUtils.isEmpty(editLocation.getText()) ? "":editLocation.getText().toString());
		currentUser.setUserDepartment(TextUtils.isEmpty(editDepartment.getText()) ? "":editDepartment.getText().toString());
		currentUser.setUserDuty(TextUtils.isEmpty(editGongsi.getText()) ? "":editGongsi.getText().toString());
		currentUser.setUserOfficePhone(TextUtils.isEmpty(editCall.getText()) ? "":editCall.getText().toString());
		currentUser.setUserFax(TextUtils.isEmpty(editChuanzhen.getText()) ? "":editChuanzhen.getText().toString());
		
		currentUser.update(EditCardActivity.this, new UpdateListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("更新资料成功。");
				initHeader();
				editChinese.setFocusable(false);
				editEnglish.setFocusable(false);
				editPhone.setFocusable(false);
				editQQ.setFocusable(false);
				editWeiXin.setFocusable(false);
				editMessage.setFocusable(false);
				editLocation.setFocusable(false);
				editDepartment.setFocusable(false);
				editGongsi.setFocusable(false);
				editCall.setFocusable(false);
				editChuanzhen.setFocusable(false);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast( "更新资料成功。请检查网络~");

			}
		});
	}

}
