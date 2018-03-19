package tr.com.beinplanner.mail;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailSenderThread implements Runnable {

	
	
	private String toWhom;
	private String messageStr;
	private String subject;
	
	private MailObj mailObj=null;
	
	public MailSenderThread(String toWhom,String messageStr,String subject) {
		super();
		this.toWhom=toWhom;
		this.messageStr=messageStr;
	}
	
	public MailSenderThread(MailObj mailObj) {
		super();
		this.mailObj=mailObj;
		
		this.toWhom=mailObj.getToPerson();
		this.messageStr=mailObj.getContent();
		this.subject=mailObj.getSubject();
	}

	@Override
	public void run() {
		
		
      try {
			
    	  
			
			
					//String host = "mail.abasus.com.tr";
					String host = "smtp.yandex.com.tr";
					String port ="465"; 
					String auth="true";
					String from="abasus@abasus.com.tr";
					
					
					Properties props = System.getProperties();
					//props.put("mail.smtp.starttls.enable", "true");
					props.put("mail.smtp.host", host);
					props.put("mail.smtp.user", from);
				    props.put("mail.smtp.port", port);
					props.put("mail.smtp.auth", auth);
					props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
					
					
					SmtpAuthenticator authentication = new SmtpAuthenticator();
					Session session = Session.getDefaultInstance(props, authentication);
					
					
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(from));
					InternetAddress[] toAddress = new InternetAddress[1];
					toAddress[0]=new InternetAddress(toWhom);
		 
					for (int i = 0; i < toAddress.length; i++) {
						message.addRecipient(RecipientType.TO, toAddress[i]);
					}
					// başlık
					message.setSubject(this.subject);
					// içerik
					if(mailObj==null)
					    message.setText(this.messageStr);
					else
						message.setContent(mailObj.getMultipartMessage());
					
					
					
					Transport transport = session.getTransport("smtp");
					transport.connect(host ,from, null);
					transport.sendMessage(message, message.getAllRecipients());
					transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	public String getToWhom() {
		return toWhom;
	}

	public void setToWhom(String toWhom) {
		this.toWhom = toWhom;
	}

	public String getMessageStr() {
		return messageStr;
	}

	public void setMessageStr(String messageStr) {
		this.messageStr = messageStr;
	}
}
