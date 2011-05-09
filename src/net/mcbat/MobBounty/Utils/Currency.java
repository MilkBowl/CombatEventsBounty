package net.mcbat.MobBounty.Utils;

public class Currency {
	static public double convertToCurrency(double amount) {
		int intAmount = (int) (amount * 100);
		double doubleAmount = (double) (intAmount);
		return doubleAmount/100;
	}
}
