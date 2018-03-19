package tr.com.beinplanner.packetpayment.entity;

public class PaymentConfirmQuery {
	private String userName;
	private String userSurname;
	private int confirmed;
	private int unConfirmed;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSurname() {
		return userSurname;
	}
	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}
	public int getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	public int getUnConfirmed() {
		return unConfirmed;
	}
	public void setUnConfirmed(int unConfirmed) {
		this.unConfirmed = unConfirmed;
	}
	
}
