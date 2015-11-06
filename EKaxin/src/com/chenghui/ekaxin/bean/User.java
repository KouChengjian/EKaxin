package com.chenghui.ekaxin.bean;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

/** ����BmobChatUser����������������Ҫ���ӵ����Կ��ڴ����
  * @ClassName: TextUser
  * @Description: TODO
  * @author smile
  * @date 2014-5-29 ����6:15:45
 */
public class User extends BmobChatUser {
	
	private static final long serialVersionUID = 1L;
	/**
	 * //��ʾ����ƴ��������ĸ
	 */
	private String sortLetters;
	/**
	 * ��������
	 */
	private BmobGeoPoint location;//
	
	public BmobGeoPoint getLocation() {
		return location;
	}
	public void setLocation(BmobGeoPoint location) {
		this.location = location;
	}
	/**
	 *  ����ĸ
	 */
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	
	/**
	 *  ����ʱ��
	 */
	private String updateAt;
	public String getUserUpdateAt() {
		return updateAt;
	}
	public void setUserUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	
	
	/**
	 *  ������
	 */
	String userChainName;
	/**
	 *  Ӣ����
	 */
	String userEnglishName;
	/**
	 *  �ֻ���
	 */
	String userMovePhone;
	/**
	 *  QQ
	 */
	String userQQ;
	/**
	 *  ΢��
	 */
	String userWeiXin;
	/**
	 *  ����
	 */
	String userEmeil;
	/**
	 *  ��˾
	 */
	String userCompany;
	/**
	 *  ����
	 */
	String userDepartment;
	/**
	 *  ְ��
	 */
	String userDuty;
	/**
	 *  �칫�绰
	 */
	String userOfficePhone;
	/**
	 *  ����
	 */
	String userFax;
	
	/**
	 *  ǩ��
	 */
	private String signature;
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	/**
	 * �Ա�
	 */
	private String sex;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setUserChainName(String userChainName){
 		this.userChainName = userChainName;
 	}
 	public String getUserChainName(){
 		return userChainName;
 	}
 	
 	public void setUserEnglishName(String userEnglishName){
 		this.userEnglishName = userEnglishName;
 	}
 	public String getUserEnglishName(){
 		return userEnglishName;
 	}
 	
 	public void setUserMovePhone(String userMovePhone){
 		this.userMovePhone = userMovePhone;
 	}
 	public String getUserMovePhone(){
 		return userMovePhone;
 	}
 	
 	public void setUserQQ(String userQQ){
 		this.userQQ = userQQ;
 	}
 	public String getUserQQ(){
 		return userQQ;
 	}
 	
 	public void setUserWeiXin(String userWeiXin){
 		this.userWeiXin = userWeiXin;
 	}
 	public String getUserWeiXin(){
 		return userWeiXin;
 	}
 	
 	public void setUserEmeil(String userEmeil){
 		this.userEmeil = userEmeil;
 	}
 	public String getUserEmeil(){
 		return userEmeil;
 	}
 	
 	public void setUserCompany(String userCompany){
 		this.userCompany = userCompany;
 	}
 	public String getUserCompany(){
 		return userCompany;
 	}
 	
 	public void setUserDepartment(String userDepartment){
 		this.userDepartment = userDepartment;
 	}
 	public String getUserDepartment(){
 		return userDepartment;
 	}
 	
 	public void setUserDuty(String userDuty){
 		this.userDuty = userDuty;
 	}
 	public String getUserDuty(){
 		return userDuty;
 	}
 	
 	public void setUserOfficePhone(String userOfficePhone){
 		this.userOfficePhone = userOfficePhone;
 	}
 	public String getUserOfficePhone(){
 		return userOfficePhone;
 	}
 	
 	public void setUserFax(String userFax){
 		this.userFax = userFax;
 	}
 	public String getUserFax(){
 		return userFax;
 	}
	
}
