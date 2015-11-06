package com.chenghui.ekaxin.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class SpaceDynamic extends BmobObject implements Serializable{
private static final long serialVersionUID = -6280656428527540320L;
	
	private User author;
	private String content;
	private BmobFile[] Contentfigureurl;
	private int love;
	private int hate;
	private int share;
	private int comment;
	private boolean isPass;
	private boolean myFav;//收藏
	private boolean myLove;//赞
	private BmobRelation relation;
	// 多幅图
	int num;
	private BmobFile Contentfigureurls_1 = null;
	private BmobFile Contentfigureurls_2 = null;
	private BmobFile Contentfigureurls_3 = null;
	private BmobFile Contentfigureurls_4 = null;
	private BmobFile Contentfigureurls_5 = null;
	private BmobFile Contentfigureurls_6 = null;
	private BmobFile Contentfigureurls_7 = null;
	private BmobFile Contentfigureurls_8 = null;
	private BmobFile Contentfigureurls_9 = null ;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	public BmobRelation getRelation() {
		return relation;
	}
	public void setRelation(BmobRelation relation) {
		this.relation = relation;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/**  
	public BmobFile[] getContentfigureurl() {
		return Contentfigureurl;
	}
	public void setContentfigureurl(BmobFile[] contentfigureurl) {
		Contentfigureurl = contentfigureurl;
	}*/
	public int getLove() {
		return love;
	}
	public void setLove(int love) {
		this.love = love;
	}
	public int getHate() {
		return hate;
	}
	public void setHate(int hate) {
		this.hate = hate;
	}
	public int getShare() {
		return share;
	}
	public void setShare(int share) {
		this.share = share;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public boolean isPass() {
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public boolean getMyFav() {
		return myFav;
	}
	public void setMyFav(boolean myFav) {
		this.myFav = myFav;
	}
	public boolean getMyLove() {
		return myLove;
	}
	public void setMyLove(boolean myLove) {
		this.myLove = myLove;
	}
	
	public int getWidth(){
		return 200;
	}
	
	private int height;
	/**
	 *  图片高度
	 */
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "QiangYu [author=" + author + ", content=" + content
				+ ", Contentfigureurl=" + Contentfigureurl + ", love=" + love
				+ ", hate=" + hate + ", share=" + share + ", comment="
				+ comment + ", isPass=" + isPass + ", myFav=" + myFav
				+ ", myLove=" + myLove + ", relation=" + relation + "]";
	}
	
	public BmobFile getContentfigureurl_1() {
		return Contentfigureurls_1;
	}
	public void setContentfigureurl_1(BmobFile Contentfigureurls_1) {
		this.Contentfigureurls_1 = Contentfigureurls_1;
	}
	public BmobFile getContentfigureurl_2() {
		return Contentfigureurls_2;
	}
	public void setContentfigureurl_2(BmobFile Contentfigureurls_2) {
		this.Contentfigureurls_2 = Contentfigureurls_2;
	}
	
	public BmobFile getContentfigureurl_3() {
		return Contentfigureurls_3;
	}
	public void setContentfigureurl_3(BmobFile Contentfigureurls_3) {
		this.Contentfigureurls_3 = Contentfigureurls_3;
	}
	
	public BmobFile getContentfigureurl_4() {
		return Contentfigureurls_4;
	}
	public void setContentfigureurl_4(BmobFile Contentfigureurls_4) {
		this.Contentfigureurls_4 = Contentfigureurls_4;
	}
	
	public BmobFile getContentfigureurl_5() {
		return Contentfigureurls_5;
	}
	public void setContentfigureurl_5(BmobFile Contentfigureurls_5) {
		this.Contentfigureurls_5 = Contentfigureurls_5;
	}
	
	public BmobFile getContentfigureurl_6() {
		return Contentfigureurls_6;
	}
	public void setContentfigureurl_6(BmobFile Contentfigureurls_6) {
		this.Contentfigureurls_6 = Contentfigureurls_6;
	}
	
	public BmobFile getContentfigureurl_7() {
		return Contentfigureurls_7;
	}
	public void setContentfigureurl_7(BmobFile Contentfigureurls_7) {
		this.Contentfigureurls_7 = Contentfigureurls_7;
	}
	
	public BmobFile getContentfigureurl_8() {
		return Contentfigureurls_8;
	}
	public void setContentfigureurl_8(BmobFile Contentfigureurls_8) {
		this.Contentfigureurls_8 = Contentfigureurls_8;
	}
	
	public BmobFile getContentfigureurl_9() {
		return Contentfigureurls_9;
	}
	public void setContentfigureurl_9(BmobFile Contentfigureurls_9) {
		this.Contentfigureurls_9 = Contentfigureurls_9;
	}
}
