package org.astrophyscalc;

import java.util.ArrayList;
import java.util.List;

public class CalcPage {

	public static final CalcRow[] ORBIT_MASS = {CalcRow.ORBIT_MASS, CalcRow.ORBIT_RADIUS, CalcRow.ORBIT_PERIOD};
	public static final CalcRow[] ORBIT_RADIUS = {CalcRow.ORBIT_RADIUS, CalcRow.ORBIT_PERIOD, CalcRow.ORBIT_MASS};
	public static final CalcRow[] ORBIT_PERIOD = {CalcRow.ORBIT_PERIOD, CalcRow.ORBIT_RADIUS, CalcRow.ORBIT_MASS};

	public static final CalcPage ORBITS = CalcPage.create(ORBIT_MASS, ORBIT_RADIUS, ORBIT_PERIOD);

	private List<List<CalcRow>> listOfLists;

	public static CalcPage create(final CalcRow[] ... calcRowArray) {
		final List<List<CalcRow>> listOfLists = new ArrayList<List<CalcRow>>();
		for (CalcRow[] rowArray: calcRowArray) {
			final List<CalcRow> rowList = new ArrayList<CalcRow>();
			for (CalcRow row: rowArray) {
				rowList.add(row);
			}
			listOfLists.add(rowList);
		}
		return new CalcPage(listOfLists);
	}

	private CalcPage(final List<List<CalcRow>> listOfLists) {
		this.listOfLists = listOfLists;
	}

	public List<List<CalcRow>> getListOfLists() {
		return listOfLists;
	}

}