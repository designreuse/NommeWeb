package com.camut.utils;

import com.camut.utils.mail.MailSenderInfo;
import com.camut.utils.mail.SimpleMailSender;

/**
 * @entity Admin . 
 * @author yyl
 * @createTime 2015-06-02
 * @updateTime 
 * @memo 发送邮件工具类
 */
public class MailUtil {

	private static String userName = "service@nomme.ca";
	private static String password = "Mu20150101";
	private static String serverHost = "smtpout.secureserver.net";
	private static String serverport = "25";

	/**
	 * 发送调用方法
	 * @param subject    邮件标题
	 * @param content    邮件内容
	 * @param toAddress  目标邮箱地址
	 */
	public static void sendMail(String subject, String content, String toAddress) {
		//System.out.println(toAddress);
		MailSenderInfo mailSenderInfo = new MailSenderInfo();
		mailSenderInfo.setMailServerHost(serverHost);
		mailSenderInfo.setMailServerPort(serverport);
		mailSenderInfo.setValidate(true);
		mailSenderInfo.setUserName(userName);
		mailSenderInfo.setPassword(password);
		mailSenderInfo.setFromAddress(userName);
		mailSenderInfo.setSubject(subject);
		mailSenderInfo.setToAddress(toAddress);
		mailSenderInfo.setContent(content);
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendHtmlMail(mailSenderInfo);
	}
}
