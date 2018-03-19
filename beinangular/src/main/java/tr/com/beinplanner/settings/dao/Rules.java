package tr.com.beinplanner.settings.dao;

public class Rules {

	private int  ruleNoClassBeforePayment;
	private int  ruleNoChangeAfterBonusPayment;
	private int  rulePayBonusForConfirmedPayment; 
	private int  ruleTaxRule;
	private int  ruleLocation;
	private int  ruleNotice;
	private int  creditCardCommission;
	private int  creditCardCommissionRate;
	private int  ruleNoSaleToPlanning;

	public int getRuleNoClassBeforePayment() {
		return ruleNoClassBeforePayment;
	}

	public void setRuleNoClassBeforePayment(int ruleNoClassBeforePayment) {
		this.ruleNoClassBeforePayment = ruleNoClassBeforePayment;
	}

	public int getRuleNoChangeAfterBonusPayment() {
		return ruleNoChangeAfterBonusPayment;
	}

	public void setRuleNoChangeAfterBonusPayment(int ruleNoChangeAfterBonusPayment) {
		this.ruleNoChangeAfterBonusPayment = ruleNoChangeAfterBonusPayment;
	}

	public int getRulePayBonusForConfirmedPayment() {
		return rulePayBonusForConfirmedPayment;
	}

	public void setRulePayBonusForConfirmedPayment(int rulePayBonusForConfirmedPayment) {
		this.rulePayBonusForConfirmedPayment = rulePayBonusForConfirmedPayment;
	}

	public int getRuleTaxRule() {
		return ruleTaxRule;
	}

	public void setRuleTaxRule(int ruleTaxRule) {
		this.ruleTaxRule = ruleTaxRule;
	}

	public int getRuleLocation() {
		return ruleLocation;
	}

	public void setRuleLocation(int ruleLocation) {
		this.ruleLocation = ruleLocation;
	}

	public int getRuleNotice() {
		return ruleNotice;
	}

	public void setRuleNotice(int ruleNotice) {
		this.ruleNotice = ruleNotice;
	}

	public int getCreditCardCommission() {
		return creditCardCommission;
	}

	public void setCreditCardCommission(int creditCardCommission) {
		this.creditCardCommission = creditCardCommission;
	}

	public int getCreditCardCommissionRate() {
		return creditCardCommissionRate;
	}

	public void setCreditCardCommissionRate(int creditCardCommissionRate) {
		this.creditCardCommissionRate = creditCardCommissionRate;
	}

	public int getRuleNoSaleToPlanning() {
		return ruleNoSaleToPlanning;
	}

	public void setRuleNoSaleToPlanning(int ruleNoSaleToPlanning) {
		this.ruleNoSaleToPlanning = ruleNoSaleToPlanning;
	}
	
}
