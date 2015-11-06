package com.chenghui.ekaxin.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.SpaceDynamic;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.util.CacheUtils;
import com.chenghui.ekaxin.view.HeaderLayout.onRightImageButtonClickListener;
import com.chenghui.ekaxin.view.photo.Bimp;
import com.chenghui.ekaxin.view.photo.FileUtils;
import com.chenghui.ekaxin.view.photo.GalleryActivity;
import com.chenghui.ekaxin.view.photo.ImageItem;
import com.chenghui.ekaxin.view.photo.PublicWay;
import com.chenghui.ekaxin.view.photo.Res;


/**
 * @ClassName: 
 * @Description: �ռ����۱༭
 * @author kcj
 * @date 
 */
public class EditCommentActivity extends BaseActivity {
	// ѡ��ͼƬ
	ImageView imgPictrue;
	ImageView imgCamera;
	private GridAdapter adapter;
	private GridView noScrollgridview;
	public static Bitmap bimap;
	private View parentView;

	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	protected Context mContext;
	String dateTime;
	
	EditText content;

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mContext = this;
		Res.init(this);
		PublicWay.activityList.add(this);
		bimap = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon_addpic_unfocused);
		parentView = getLayoutInflater().inflate(R.layout.activity_editcomment,
				null);
		setContentView(parentView);
		initHeaderUi();
		initEditCommentAllUi();
	}

	public void initHeaderUi() {
		initTopBarForBoth("����", R.drawable.base_action_bar_save_bg_selector,
				new onRightImageButtonClickListener() {
					@Override
					public void onClick(View v) {
						String commitContent = content.getText().toString().trim();
						if(TextUtils.isEmpty(commitContent)){
							ShowToast("���ݲ���Ϊ��");
							return;
						}
						Date date1 = new Date(System.currentTimeMillis());
						dateTime = date1.getTime() + "";
						sendPosts(commitContent);
					}
				});
	}

	public void initEditCommentAllUi() {
		content = (EditText)findViewById(R.id.ed_comment_content);
		// ѡ��
		imgPictrue = (ImageView) findViewById(R.id.img_comment_resource_1);
		imgCamera = (ImageView) findViewById(R.id.img_comment_resource_2);
		imgPictrue.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(EditCommentActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		imgCamera.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Date date1 = new Date(System.currentTimeMillis());
				dateTime = date1.getTime() + "";
			}
		});
		// ����
		noScrollgridview = (GridView) findViewById(R.id.gv_comment_noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(EditCommentActivity.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(EditCommentActivity.this,GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});
		// popup
		pop = new PopupWindow(EditCommentActivity.this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows,
				null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		// popup����Ĳ���
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Date date1 = new Date(System.currentTimeMillis());
				dateTime = date1.getTime() + "";
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(EditCommentActivity.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in,
						R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}

	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position)
						.getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		@SuppressLint("HandlerLeak")
		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	protected void onResume() {
		super.onResume();
		if (adapter == null) {
			adapter = new GridAdapter(this);
			adapter.update();
		} else {
			// ShowToast("onResume2");
			adapter.update();
		}
	}
	
	String targeturl = null;
	List<BmobFile> figureFiles = new ArrayList<BmobFile>();
	/**
	 *  ����
	 */
    public void sendPosts(String commitContent){
    	figureFiles.clear();
    	int count = Bimp.tempSelectBitmap.size();
	    for(int i = 0 ;i < count ; i++){
	    	Bitmap bitmap = compressImageFromFile(Bimp.tempSelectBitmap.get(i).getImagePath());
	    	targeturl = saveToSdCard(bitmap);
	    	publish(commitContent);
	    }
	    if(targeturl == null){
	    	publishWithoutFigure(commitContent, null);
	    }
    }
    
    /**
	 * �����ͼƬ �����ı�
	 */
	private void publish(final String commitContent){
		@SuppressWarnings("deprecation")
		final BmobFile figureFile = new BmobFile(SpaceDynamic.class, new File(targeturl));
		figureFile.upload(mContext, new UploadFileListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				//ShowToast("�ϴ��ļ��ɹ���");
				Log.e("TAG", "�ϴ��ļ��ɹ���"+figureFile.getFileUrl());	
				figureFiles.add(figureFile);
				if(figureFiles.size() == Bimp.tempSelectBitmap.size()){
					publishWithoutFigure(commitContent, figureFiles);
				}
			}
			@Override
			public void onProgress(Integer arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast( "�ϴ��ļ�ʧ�ܡ�"+arg1);
				Log.e("TAG", "�ϴ��ļ�ʧ�ܡ�"+arg1);	
				//LogUtils.i(TAG, "�ϴ��ļ�ʧ�ܡ�"+arg1);
			}
		});
	}
	
	private void publishWithoutFigure(final String commitContent,
			final List<BmobFile > figureFile) {
		User user = BmobUser.getCurrentUser(mContext, User.class);
		//QiangYu qiangYu1= BmobUser.getCurrentUser(mContext, QiangYu.class);
		final SpaceDynamic qiangYu = new SpaceDynamic();
		qiangYu.setAuthor(user);
		qiangYu.setContent(commitContent);
		if(figureFile != null && figureFile.size() != 0){
			qiangYu.setNum(figureFile.size());
			for(int i = 0 ; i < figureFile.size() ;i++){
				switch(i){
				case 0:
					qiangYu.setContentfigureurl_1(figureFile.get(i));
					break;
				case 1:
					qiangYu.setContentfigureurl_2(figureFile.get(i));
					break;
				case 2:
					qiangYu.setContentfigureurl_3(figureFile.get(i));
					break;
				case 3:
					qiangYu.setContentfigureurl_4(figureFile.get(i));
					
					break;
				case 4:
					qiangYu.setContentfigureurl_5(figureFile.get(i));
					break;
				case 5:
					qiangYu.setContentfigureurl_6(figureFile.get(i));
					break;
				case 6:
					qiangYu.setContentfigureurl_7(figureFile.get(i));
					break;
				case 7:
					qiangYu.setContentfigureurl_8(figureFile.get(i));
					break;
				case 8:
					qiangYu.setContentfigureurl_9(figureFile.get(i));
					break;
				}
			}
		}
		qiangYu.setLove(0);
		qiangYu.setHate(0);
		qiangYu.setShare(0);
		qiangYu.setComment(0);
		qiangYu.setPass(true);
		qiangYu.save(mContext, new SaveListener() {
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ShowToast("����ɹ���");
				setResult(RESULT_OK);
				Bimp.tempSelectBitmap.clear();
				Bimp.max = 0;
				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				//ActivityUtil.show(EditActivity.this, "����ʧ�ܣ�yg"+arg1);
				//LogUtils.i(TAG,"����ʧ�ܡ�"+arg1);
				ShowToast("����ʧ�ܡ�");
			}
		});
	}
	
	private static final int TAKE_PICTURE = 0x000001;
	private static final int REQUEST_CODE_CAMERA = 2;
	public void photo() {
		File f = new File(CacheUtils.getCacheDirectory(mContext, true, "pic") + dateTime);
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
		startActivityForResult(camera, REQUEST_CODE_CAMERA);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case REQUEST_CODE_CAMERA:
				String files =CacheUtils.getCacheDirectory(mContext, true, "pic") + dateTime;
				File file = new File(files);
				if(file.exists()){
					Bitmap bitmap = compressImageFromFile(files);
					ImageItem takePhoto = new ImageItem();
					takePhoto.setBitmap(bitmap);
					Bimp.tempSelectBitmap.add(takePhoto);
					adapter.update();
				}else{
					
				}
				break;
			case TAKE_PICTURE:
				if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
					String fileName = String.valueOf(System.currentTimeMillis());
					Bitmap bm = (Bitmap) data.getExtras().get("data");
					FileUtils.saveBitmap(bm, fileName);
					ImageItem takePhoto = new ImageItem();
					takePhoto.setBitmap(bm);
					Bimp.tempSelectBitmap.add(takePhoto);
				}
				break;
			}
		}
	}
	
	/**
     *  ���ļ���ѹ��ͼƬ
     */
    private Bitmap compressImageFromFile(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;//ֻ����,��������
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 800f;//
		float ww = 480f;//
		int be = 1;
		// ��ͼ
		if (w > h && w > ww) {
			be = (int) (newOpts.outWidth / ww);
		} 
		// ��ͼ
		else if (w < h && h > hh) {
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0){
			be = 1;
		}
		newOpts.inSampleSize = be;//���ò�����
		
		newOpts.inPreferredConfig = Config.ARGB_8888;//��ģʽ��Ĭ�ϵ�,�ɲ���
		newOpts.inPurgeable = true;// ͬʱ���òŻ���Ч
		newOpts.inInputShareable = true;//����ϵͳ�ڴ治��ʱ��ͼƬ�Զ�������
		
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//		return compressBmpFromBmp(bitmap);//ԭ���ķ������������������ͼ���ж���ѹ��
									//��ʵ����Ч��,��Ҿ��ܳ���
		return bitmap;
	}
    
    /**
     * ���浽�ڴ���
     */
    public String saveToSdCard(Bitmap bitmap){
		String files =CacheUtils.getCacheDirectory(mContext, true, "pic") + dateTime+"_11";
		File file=new File(files);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)){
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
}
