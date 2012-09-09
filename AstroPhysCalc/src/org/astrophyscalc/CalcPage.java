package org.astrophyscalc;

import java.util.ArrayList;
import java.util.List;

public class CalcPage {

	public static final CalcPage ORBITS = CalcPage.create(
			CalcRow.ORBIT_MASS,
			CalcRow.ORBIT_RADIUS,
			CalcRow.ORBIT_PERIOD);

	private List<CalcRow> calcRows;

	public static CalcPage create(final CalcRow ... calcRows) {
		final List<CalcRow> calcRowList = new ArrayList<CalcRow>();
		for (CalcRow row: calcRows) {
			calcRowList.add(row);
		}
		return new CalcPage(calcRowList);
	}

	private CalcPage(final List<CalcRow> calcRows) {
		this.calcRows = calcRows;
	}

	public List<CalcRow> getCalcRows() {
		return calcRows;
	}

}
