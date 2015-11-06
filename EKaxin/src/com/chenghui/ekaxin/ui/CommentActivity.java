package com.chenghui.ekaxin.ui;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.chenghui.ekaxin.CustomApplcation;
import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.adapter.CommentAdapter;
import com.chenghui.ekaxin.bean.Comment;
import com.chenghui.ekaxin.bean.SpaceDynamic;
import com.chenghui.ekaxin.bean.User;
import com.chenghui.ekaxin.util.Constant;
import com.chenghui.ekaxin.view.EmoticonsEditText;
import com.nostra13.universalimageloader.core.ImageLoader;




import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


/**
 * @ClassName: CommentActivity
 * @Description: 评论
 * @author kcj
 * @date 
 */
public class CommentActivity extends BaseActivity implements OnClickListener{
	
	private TextView userName;
	private ImageView userLogo;
	private TextView comment;
	public TextView index;
	public ImageView[] contentImage;
	
	LinearLayout two_area_commit; // 关闭
	private TextView finish;
	private LinearLayout rl_comment_inport; //输入
	private LinearLayout layout_more, layout_emo, layout_add,layout_add_pictrue;
	private EmoticonsEditText edit_user_comment;
	private Button btn_chat_add;
	private Button btn_chat_emo;
	private Button btn_chat_send;
	
	private ListView commentList;
	private SpaceDynamic spaceDynamic;
    private CommentAdapter mAdapter;
	private List<Comment> comments = new ArrayList<Comment>();
	private int pageNum;
	
	private String commentEdit = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		pageNum = 0;
		spaceDynamic = (SpaceDynamic)getIntent().getSerializableExtra("data");
		/** UI */
		userLogo = (ImageView)findViewById(R.id.details_comment_logo); 
		userName = (TextView)findViewById(R.id.details_comment_name); 
		index = (TextView)findViewById(R.id.details_comment_reply);
		comment = (TextView)findViewById(R.id.details_comment_text); 
		contentImage = new ImageView[10];
		for(int i = 0 ;i < 9 ;i++){
			contentImage[i] = (ImageView) findViewById(R.id.details_comment_image_1 + i);
		}
		two_area_commit = (LinearLayout) findViewById(R.id.two_area_commit); // 关闭
		finish = (TextView)findViewById(R.id.two_finish);
		rl_comment_inport = (LinearLayout) findViewById(R.id.comment_operation_import);//输入评论
		//layout_more = (LinearLayout) findViewById(R.id.layout_more);
		//layout_emo = (LinearLayout) findViewById(R.id.layout_emo);
		//layout_add = (LinearLayout) findViewById(R.id.layout_add);
		edit_user_comment = (EmoticonsEditText) findViewById(R.id.edit_user_comment);
		btn_chat_add = (Button) findViewById(R.id.btn_chat_add);
		btn_chat_add.setVisibility(View.GONE);
		btn_chat_emo = (Button) findViewById(R.id.btn_chat_emo);
		btn_chat_send = (Button) findViewById(R.id.btn_chat_send);
		
		
		/** list */
		commentList = (ListView)findViewById(R.id.lv_comment_list);
		mAdapter = new CommentAdapter(CommentActivity.this, comments);
		commentList.setAdapter(mAdapter);
		setListViewHeightBasedOnChildren(commentList);
		commentList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
			}
		});
		commentList.setCacheColorHint(0);
		commentList.setScrollingCacheEnabled(false);
		commentList.setScrollContainer(false);
		commentList.setFastScrollEnabled(true);
		commentList.setSmoothScrollbarEnabled(true);
		
		initMoodView(spaceDynamic);
	}

	private void initMoodView(SpaceDynamic spaceDynamic) {
		if(spaceDynamic == null){
			ShowToast("spaceDynamic = null");
			return;
		}
		userName.setText(spaceDynamic.getAuthor().getUsername());
		comment.setText(spaceDynamic.getContent());
		uploadImage();
		
		User user = spaceDynamic.getAuthor();
		/**  
		String avatar = user.getAvatar();
		if(null != avatar){
			ImageLoader.getInstance()
			.displayImage(avatar, userLogo, 
					CustomApplcation.getInstance().getOptions(R.drawable.head_default_yixin),
					new SimpleImageLoadingListener(){
						@Override
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
						}
			});
		}*/
		
		fetchComment();
	}
	
	private void fetchComment(){
		BmobQuery<Comment> query = new BmobQuery<Comment>();
		query.addWhereRelatedTo("relation", new BmobPointer(spaceDynamic));
		query.include("user");
		query.order("createdAt");
		query.setLimit(Constant.NUMBERS_PER_PAGE);
		query.setSkip(Constant.NUMBERS_PER_PAGE*(pageNum++));
		query.findObjects(this, new FindListener<Comment>() {
			@Override
			public void onSuccess(List<Comment> data) {
				// TODO Auto-generated method stub
				if(data.size()!=0 && data.get(data.size()-1)!=null){
					if(data.size()<Constant.NUMBERS_PER_PAGE){
						ShowToast("已加载完所有评论~");
					}
					
					mAdapter.getDataList().addAll(data);
					mAdapter.notifyDataSetChanged();
					setListViewHeightBasedOnChildren(commentList);

				}else{
					ShowToast( "暂无更多评论~");
					pageNum--;
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast( "获取评论失败。请检查网络~");
				pageNum--;
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_chat_send:
			InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);  
			imm.hideSoftInputFromWindow(edit_user_comment.getWindowToken(), 0);
			rl_comment_inport.setVisibility(View.GONE);
			onClickSendMomment();
			break;
		}
	}
	
	/**
	 * 发送评论
	 */
	private void onClickSendMomment() {
		User currentUser = BmobUser.getCurrentUser(this,User.class);
		if(currentUser != null){//已登录
			commentEdit = edit_user_comment.getText().toString().trim();
			if(TextUtils.isEmpty(commentEdit)){
				ShowToast( "评论内容不能为空。");
				return;
			}
			publishComment(commentEdit ,currentUser);
		}else{//未登录
			ShowToast("请登入");
		}
	}
	
	private void publishComment(String commitContent,User currentUser) {
		final Comment comment = new Comment();
		comment.setUser(currentUser);
		comment.setCommentContent(commitContent);
		comment.save(this, new SaveListener() {
			@Override
			public void onSuccess() {
				//将该评论与强语绑定到一起
				edit_user_comment.setText("");
				
				BmobRelation relation = new BmobRelation();
				relation.add(comment);
				spaceDynamic.setRelation(relation);
				spaceDynamic.update(CommentActivity.this, new UpdateListener() {
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						ShowToast("更新评论成功。");
						mAdapter.getDataList().add(comment);
						mAdapter.notifyDataSetChanged();
						setListViewHeightBasedOnChildren(commentList);
					}
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						ShowToast("关联失败:"+arg1);
					}
				});
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				ShowToast("评论失败。请检查网络~"+arg1);
			}
		});
	}
	
	/*** 
     * 动态设置listview的高度 
     *  item 总布局必须是linearLayout
     * @param listView 
     */  
    public void setListViewHeightBasedOnChildren(ListView listView) {  
        ListAdapter listAdapter = listView.getAdapter();  
        if (listAdapter == null) {  
            return;  
        }  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight  
                + (listView.getDividerHeight() * (listAdapter.getCount()-1))  
                +15;  
        listView.setLayoutParams(params);  
    }
    
    public void uploadImage(){
		if(null == spaceDynamic.getContentfigureurl_1()){
			contentImage[0].setVisibility(View.GONE);
		}else{
			contentImage[0].setVisibility(View.VISIBLE);
			ImageLoader.getInstance()
			.displayImage(spaceDynamic.getContentfigureurl_1().getFileUrl(), contentImage[0], 
					CustomApplcation.getInstance().getOptions(R.drawable.user_icon_default_main),
					new SimpleImageLoadingListener(){
						public void onLoadingComplete(String imageUri, View view,
								Bitmap loadedImage) {
							// TODO Auto-generated method stub
							super.onLoadingComplete(imageUri, view, loadedImage);
						}
			});
		}
		if(null == spaceDynamic.getContentfigureurl_2()){
			contentImage[1].setVisibility(View.GONE);
		}else{
			contentImage[1].setVisibility(View.VISIBLE);
			spaceDynamic.getContentfigureurl_2().loadImage(this,contentImage[1]);
		}
		if(null == spaceDynamic.getContentfigureurl_3()){
			contentImage[2].setVisibility(View.GONE);
		}else{
			contentImage[2].setVisibility(View.VISIBLE);
			spaceDynamic.getContentfigureurl_3().loadImage(this,contentImage[2]);
		}
		if(null == spaceDynamic.getContentfigureurl_4()){
			contentImage[3].setVisibility(View.GONE);
		}else{
			contentImage[3].setVisibility(View.VISIBLE);
			spaceDynamic.getContentfigureurl_4().loadImage(this,contentImage[3]);
		}
		if(null == spaceDynamic.getContentfigureurl_5()){
			contentImage[4].setVisibility(View.GONE);
		}else{
			contentImage[4].setVisibility(View.VISIBLE);
			spaceDynamic.getContentfigureurl_5().loadImage(this,contentImage[4]);
		}
		if(null == spaceDynamic.getContentfigureurl_6()){
			contentImage[5].setVisibility(View.GONE);
		}else{
			contentImage[5].setVisibility(View.VISIBLE);
			spaceDynamic.getContentfigureurl_6().loadImage(this,contentImage[5]);
		}
		if(null == spaceDynamic.getContentfigureurl_7()){
			contentImage[6].setVisibility(View.GONE);
		}else{
			contentImage[6].setVisibility(View.VISIBLE);
			spaceDynamic.getContentfigureurl_7().loadImage(this,contentImage[6]);
		}
		if(null == spaceDynamic.getContentfigureurl_8()){
			contentImage[7].setVisibility(View.GONE);
		}else{
			contentImage[7].setVisibility(View.VISIBLE);
			spaceDynamic.getContentfigureurl_8().loadImage(this,contentImage[7]);
		}
		if(null == spaceDynamic.getContentfigureurl_9()){
			contentImage[8].setVisibility(View.GONE);
		}else{
			contentImage[8].setVisibility(View.VISIBLE);
			spaceDynamic.getContentfigureurl_9().loadImage(this,contentImage[8]);
		}
	}

}
