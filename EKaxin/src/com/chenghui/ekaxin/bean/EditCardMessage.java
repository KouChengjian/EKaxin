package com.chenghui.ekaxin.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class EditCardMessage {
	
	// �������岼����ɫ
	int rlBackground;
	// ����ͼƬ������ɫ
	int rlPicBackground;
	// ѡ��
	int view;
	// edit hintֵ
	String editHintContent;
	// ͼƬ
	int imgContent;
    
    public void setRlPicBackground(int rlPicBackground){
    	this.rlPicBackground = rlPicBackground;
    }
    public int getRlPicBackground(){
    	return rlPicBackground ;
    }
    
    public void setRlBackground(int rlBackground){
    	this.rlBackground = rlBackground;
    }
    public int getRlBackground(){
    	return rlBackground ;
    }
    
    public void setView(int view){
    	this.view = view;
    }
    public int getView(){
    	return view ;
    }
    
    public void setContent(String editHintContent){
    	this.editHintContent = editHintContent;
    }
    public String getContent(){
    	return editHintContent ;
    }
    
    public void setImgContent(int imgContent){
    	this.imgContent = imgContent;
    }
    public int getImgContent(){
    	return imgContent ;
    }
}
