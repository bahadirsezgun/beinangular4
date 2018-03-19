package tr.com.beinplanner.dashboard.businessEntity;
/**
 * 
 * @author Bahadır Sezgun
 * @comment Dashboard Finance sayfasında eksik ödemeler ve ödemesi hiç yapılmamış 
 * <br> paket satışları toplam miktarı ve adeti gösterilir.
 */
public class LeftPaymentInfo {

	
	private double 	leftPayment;
	private int    	leftPaymentCount;
	private double 	noPayment;
	private int 		noPaymentCount;
	
	
	@Override
	public String toString() {
		
		System.out.println("LEFT PAYMENT "+this.leftPayment);
		System.out.println("LEFT PAYMENT COUNT "+this.leftPaymentCount);
		System.out.println("NO PAYMENT "+this.noPayment);
		System.out.println("NO PAYMENT COUNT "+this.noPaymentCount);
		
		return super.toString();
	}
	
	public double getLeftPayment() {
		return leftPayment;
	}

	public void setLeftPayment(double leftPayment) {
		this.leftPayment = leftPayment;
	}

	

	public double getNoPayment() {
		return noPayment;
	}

	public void setNoPayment(double noPayment) {
		this.noPayment = noPayment;
	}

	public int getLeftPaymentCount() {
		return leftPaymentCount;
	}

	public void setLeftPaymentCount(int leftPaymentCount) {
		this.leftPaymentCount = leftPaymentCount;
	}

	public int getNoPaymentCount() {
		return noPaymentCount;
	}

	public void setNoPaymentCount(int noPaymentCount) {
		this.noPaymentCount = noPaymentCount;
	}
	
	
}
