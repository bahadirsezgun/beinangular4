package tr.com.beinplanner.bonus.comparator;

import java.util.Comparator;

import tr.com.beinplanner.bonus.dao.UserBonusPaymentFactory;

public class UserBonusPaymentComparator implements Comparator<UserBonusPaymentFactory> {

	@Override
    public int compare(UserBonusPaymentFactory ps1, UserBonusPaymentFactory ps2) {
        return ps2.getBonPaymentDate().compareTo(ps1.getBonPaymentDate());
    }

}
