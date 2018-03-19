package tr.com.beinplanner.income.dao;

import java.util.List;

import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;
import tr.com.beinplanner.packetpayment.dao.PacketPaymentDetailFactory;

public class PtMonthlyInOutObj {

    private List<UserBonusPaymentFactory> ubpf;
	
	private List<PtExpenses> ptExp;
	
	private List<PacketPaymentDetailFactory> ppf;

	public List<UserBonusPaymentFactory> getUbpf() {
		return ubpf;
	}

	public void setUbpf(List<UserBonusPaymentFactory> ubpf) {
		this.ubpf = ubpf;
	}

	public List<PtExpenses> getPtExp() {
		return ptExp;
	}

	public void setPtExp(List<PtExpenses> ptExp) {
		this.ptExp = ptExp;
	}

	public List<PacketPaymentDetailFactory> getPpf() {
		return ppf;
	}

	public void setPpf(List<PacketPaymentDetailFactory> ppf) {
		this.ppf = ppf;
	}

	

	
	
}
