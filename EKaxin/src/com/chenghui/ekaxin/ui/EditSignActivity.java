package com.chenghui.ekaxin.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.bean.User;

public class EditSignActivity extends BaseActivity{
	
	private Button commit;
	private EditText input;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editsign);
		initTopBarForLeft("签名");
		input = (EditText) findViewById(R.id.sign_comment_content);
		commit = (Button) findViewById(R.id.sign_comment_commit);
		commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(input.getText().toString().trim())) {
					ShowToast("请先输入。。。");
				} else {
					updateSign(input.getText().toString().trim());
				}
			}
		});
	}
	
	private void updateSign(String sign){
		User user = BmobUser.getCurrentUser(mContext, User.class);
		if(user != null && sign != null){
			user.setSignature(sign);
			user.update(mContext, new UpdateListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					ShowToast("更改信息成功。");
					finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					ShowToast( "更改信息失败。请检查网络");
				}
			});
		}
	}

}
