package com.chenghui.ekaxin.data;

import java.util.List;

public class SpaceModelItem {
	private int imgHead;// ͷ����ԴID
	private String name;// ����
	private String date;// ����
	private String phonemodel;// �ֻ��ͺ�
	private int type;// ��Ϣ����
	private boolean agree;//�Ƿ�����
	private String address;//λ����Ϣ
	private List<String> agreeShow;//��á��ޡ��������б�
	private List<String> comments;//�û������б�
	
	
	public List<String> getAgreeShow() {
		return agreeShow;
	}
	
	public void setAgreeShow(List<String> agreeShow) {
		this.agreeShow = agreeShow;
	}
	
	public List<String> getComments() {
		return comments;
	}
	
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isAgree() {
		return agree;
	}
	
	public void setAgree(boolean agree) {
		this.agree = agree;
	}
	
	private String content;// ��Ϣ����
	private List<String> imageUrls;// ͼƬ��Url��ַ
	
	public int getImgHead() {
		return imgHead;
	}
	
	public void setImgHead(int imgHead) {
		this.imgHead = imgHead;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getPhonemodel() {
		return phonemodel;
	}
	
	public void setPhonemodel(String phonemodel) {
		this.phonemodel = phonemodel;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<String> getImageUrls() {
		return imageUrls;
	}
	
	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}
}
