package tr.com.beinplanner.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SmtpAuthenticator extends Authenticator {
	public SmtpAuthenticator() {

	    super();
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		
		
		
	 String username ="bsezgun@yandex.com";//"info@abasus.com.tr";
	 String password ="berkin2162";// "xqEwff3k6LMy22fdsweeewsaa";
	    if ((username != null) && (username.length() > 0) && (password != null) 
	      && (password.length   () > 0)) {

	        return new PasswordAuthentication(username, password);
	    }

	    return null;
	}
	}
