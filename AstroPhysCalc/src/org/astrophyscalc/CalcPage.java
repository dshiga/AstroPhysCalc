package org.astrophyscalc;

import java.util.List;

public class CalcPage {

	private List<CalcRow> calcRows;

	public static CalcPage create(final List<CalcRow> calcRows) {
		return new CalcPage(calcRows);
	}

	private CalcPage(final List<CalcRow> calcRows) {
		this.calcRows = calcRows;
	}

	public List<CalcRow> getCalcRows() {
		return calcRows;
	}

}
