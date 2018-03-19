package tr.com.beinplanner.settings.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="db_mail_tbl")
public class DbMailTbl {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="FIRM_ID")
	private int firmId;
	
	
	
	@Column(name="HOST_NAME")
	private String hostName;
	@Column(name="FROM_NAME")
	private String fromName;
	@Column(name="MAIL_USERNAME")
	private String mailUsername;
	
	@Column(name="MAIL_PASSWORD")
	private String mailPassword;
	

	@Column(name="SMTP_PORT")
	private int smtpPort;

	@Column(name="SMTP_AUTH")
	private int smtpAuth;

	@Column(name="USE_SSL")
	private int useSsl;
	
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getMailUsername() {
		return mailUsername;
	}
	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}
	public String getMailPassword() {
		return mailPassword;
	}
	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public int getSmtpPort() {
		return smtpPort;
	}
	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}
	public int getSmtpAuth() {
		return smtpAuth;
	}
	public void setSmtpAuth(int smtpAuth) {
		this.smtpAuth = smtpAuth;
	}
	public int getUseSsl() {
		return useSsl;
	}
	public void setUseSsl(int useSsl) {
		this.useSsl = useSsl;
	}
	public int getFirmId() {
		return firmId;
	}
	public void setFirmId(int firmId) {
		this.firmId = firmId;
	}
}
