package com.chenghui.ekaxin.bean;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

/** 重载BmobChatUser对象：若还有其他需要增加的属性可在此添加
  * @ClassName: TextUser
  * @Description: TODO
  * @author smile
  * @date 2014-5-29 下午6:15:45
 */
public class User extends BmobChatUser {
	
	private static final long serialVersionUID = 1L;
	/**
	 * //显示数据拼音的首字母
	 */
	private String sortLetters;
	/**
	 * 地理坐标
	 */
	private BmobGeoPoint location;//
	
	public BmobGeoPoint getLocation() {
		return location;
	}
	public void setLocation(BmobGeoPoint location) {
		this.location = location;
	}
	/**
	 *  首字母
	 */
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
	
	/**
	 *  更新时间
	 */
	private String updateAt;
	public String getUserUpdateAt() {
		return updateAt;
	}
	public void setUserUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	
	
	/**
	 *  中文名
	 */
	String userChainName;
	/**
	 *  英文名
	 */
	String userEnglishName;
	/**
	 *  手机号
	 */
	String userMovePhone;
	/**
	 *  QQ
	 */
	String userQQ;
	/**
	 *  微信
	 */
	String userWeiXin;
	/**
	 *  邮箱
	 */
	String userEmeil;
	/**
	 *  公司
	 */
	String userCompany;
	/**
	 *  部门
	 */
	String userDepartment;
	/**
	 *  职务
	 */
	String userDuty;
	/**
	 *  办公电话
	 */
	String userOfficePhone;
	/**
	 *  传真
	 */
	String userFax;
	
	/**
	 *  签名
	 */
	private String signature;
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	/**
	 * 性别
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
