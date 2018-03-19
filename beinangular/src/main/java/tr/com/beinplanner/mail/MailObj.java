package tr.com.beinplanner.mail;

import javax.mail.internet.MimeMultipart;

public class MailObj {

	private String toWhom;
	private String subject;
	private String content;
	private String htmlContent;
	
	private String[] toWho;
	
	private String toPerson;
	
	
	private MimeMultipart multipartMessage;


	public String getToWhom() {
		return toWhom;
	}


	public void setToWhom(String toWhom) {
		this.toWhom = toWhom;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getHtmlContent() {
		return htmlContent;
	}


	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}


	public String[] getToWho() {
		return toWho;
	}


	public void setToWho(String[] toWho) {
		this.toWho = toWho;
	}


	public String getToPerson() {
		return toPerson;
	}


	public void setToPerson(String toPerson) {
		this.toPerson = toPerson;
	}


	public MimeMultipart getMultipartMessage() {
		return multipartMessage;
	}


	public void setMultipartMessage(MimeMultipart multipartMessage) {
		this.multipartMessage = multipartMessage;
	}
	
}
