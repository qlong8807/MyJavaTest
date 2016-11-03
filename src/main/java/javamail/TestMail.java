package javamail;

public class TestMail {

	public static void main(String[] args) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("jans_test@163.com");
		mailInfo.setPassword("yqbnaoootjcozwic");// 您的邮箱密码
		mailInfo.setFromAddress("jans_test@163.com");
		mailInfo.setToAddress("abserver@163.com");
		mailInfo.setSubject("设置邮箱标题 ");
		mailInfo.setContent("设置邮箱内容 ");
		mailInfo.setValidate(true);
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);// 发送文体格式
		// sms.sendHtmlMail(mailInfo);//发送html格式
		System.out.println("OK");
	}
}
