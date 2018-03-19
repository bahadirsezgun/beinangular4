package tr.com.beinplanner.dashboard.businessEntity;

public class TodayPayment {

	private String monthName;
	private String dayName;
	private double incomeAmount;
	private double expenseAmount;
	
	
	
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	public double getIncomeAmount() {
		return incomeAmount;
	}
	public void setIncomeAmount(double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}
	public double getExpenseAmount() {
		return expenseAmount;
	}
	public void setExpenseAmount(double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	
	
}
