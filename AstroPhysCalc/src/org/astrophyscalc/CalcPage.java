package org.astrophyscalc;

import java.util.ArrayList;
import java.util.List;

public class CalcPage {

	public static final CalcRow[] ORBIT_MASS = {CalcRow.ORBIT_MASS, CalcRow.ORBIT_RADIUS, CalcRow.ORBIT_PERIOD};
	public static final CalcRow[] ORBIT_RADIUS = {CalcRow.ORBIT_RADIUS, CalcRow.ORBIT_PERIOD, CalcRow.ORBIT_MASS};
	public static final CalcRow[] ORBIT_PERIOD = {CalcRow.ORBIT_PERIOD, CalcRow.ORBIT_RADIUS, CalcRow.ORBIT_MASS};
	public static final CalcPage ORBITS = CalcPage.create(R.string.orbitPageTitle, ORBIT_MASS, ORBIT_RADIUS, ORBIT_PERIOD);

	public static final CalcRow[] KE_MASS = {CalcRow.KE_MASS, CalcRow.KE_ENERGY, CalcRow.KE_VELOCITY};
	public static final CalcRow[] KE_ENERGY = {CalcRow.KE_ENERGY, CalcRow.KE_MASS, CalcRow.KE_VELOCITY};
	public static final CalcRow[] KE_VELOCITY = {CalcRow.KE_VELOCITY, CalcRow.KE_MASS, CalcRow.KE_ENERGY};
	public static final CalcPage KE = CalcPage.create(R.string.kineticPageTitle, KE_MASS, KE_ENERGY, KE_VELOCITY);

	public static final CalcRow[] FLUX_POWER = {CalcRow.FLUX_POWER, CalcRow.FLUX_DISTANCE, CalcRow.FLUX};
	public static final CalcRow[] FLUX_DISTANCE = {CalcRow.FLUX_DISTANCE, CalcRow.FLUX_POWER, CalcRow.FLUX};
	public static final CalcRow[] FLUX = {CalcRow.FLUX, CalcRow.FLUX_POWER, CalcRow.FLUX_DISTANCE};
	public static final CalcPage FLUX_PAGE = CalcPage.create(R.string.fluxPageTitle, FLUX_POWER, FLUX_DISTANCE, FLUX);

	private final int titleStringId;
	private final List<List<CalcRow>> listOfLists;

	public static CalcPage create(final int titleStringId, final CalcRow[] ... calcRowArray) {
		final List<List<CalcRow>> listOfLists = new ArrayList<List<CalcRow>>();
		for (CalcRow[] rowArray: calcRowArray) {
			final List<CalcRow> rowList = new ArrayList<CalcRow>();
			for (CalcRow row: rowArray) {
				rowList.add(row);
			}
			listOfLists.add(rowList);
		}
		return new CalcPage(titleStringId, listOfLists);
	}

	private CalcPage(final int titleStringId, final List<List<CalcRow>> listOfLists) {
		this.titleStringId = titleStringId;
		this.listOfLists = listOfLists;
	}

	public List<List<CalcRow>> getListOfLists() {
		return listOfLists;
	}

	public int getTitleStringId() {
		return titleStringId;
	}

}