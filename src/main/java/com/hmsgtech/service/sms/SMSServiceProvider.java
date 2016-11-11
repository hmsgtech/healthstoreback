package com.hmsgtech.service.sms;

public interface SMSServiceProvider {
	String sms_username = "ja0281";
	String sms_password = "221578";
	String sms_url = "http://211.100.34.185:8016/MWGate/wmgw.asmx";

	String sms_username_y = "JH0567";
	String sms_password_y = "232054";
	String sms_url_y = "http://61.145.229.29:8892/MWGate/wmgw.asmx";

	/**
	 * 发送短信
	 * 
	 * @param mobileIds
	 * @param text
	 * @param userName
	 * @param password
	 * @return
	 */
	public String sendMessage(String[] mobileIds, String text, String userName, String password, String url);

	/**
	 * 发送短信
	 * 
	 * @param mobileIds
	 * @param text
	 * @param userName
	 * @param password
	 * @return
	 */
	public String sendMessage(String mobileIds, String text, String userName, String password, String url);

	/**
	 * 查询余额
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public Integer queryBalance(String userName, String password, String url);

	/**
	 * 查询发送状态报告
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public String[] queryStatusReport(String userName, String password, String url);

	/**
	 * 上行短信接收
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public String[] receiveMessage(String userName, String password, String url);

}
