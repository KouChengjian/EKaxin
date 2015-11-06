package com.chenghui.ekaxin.bean;

import com.chenghui.ekaxin.ui.MainActivity;

public class NotepadMessage {
	static NotepadMessage message;
	int[] type = new int[]{};
	
	public NotepadMessage(){
		message = this;
	}
	
	public static NotepadMessage getInstance(){
		return message;
	}
	
	public void setIntType(int[] type){
		this.type = type;
	}
	public int[] getIntType(){
		return type;
	}
	
	String str_type;
	String str_title;
	String str_content;
	String str_date;
	
	public void setType(String str_type){
		this.str_type = str_type;
	}
	public String getType(){
		return str_type;
	}
	
	public void setTitle(String str_title){
		this.str_title = str_title;
	}
	public String getTitle(){
		return str_title;
	}
	
	public void setContent(String str_content){
		this.str_content = str_content;
	}
	public String getContent(){
		return str_content;
	}
	
	public void setDate(String str_date){
		this.str_date = str_date;
	}
	public String getDate(){
		return str_date;
	}
}
