package org.astrophyscalc;


public class CalcRow {

	final UnitSelector unitSelector;
	final String textLabel;
	final int defaultUnitPos;

	public static CalcRow create(final UnitSelector unitSelector, final String textLabel,
			final int defaultUnitPos) {
		return new CalcRow(unitSelector, textLabel, defaultUnitPos);
	}

	private CalcRow(final UnitSelector unitSelector, final String textLabel,
			final int defaultUnitPos) {
		this.unitSelector = unitSelector;
		this.textLabel = textLabel;
		this.defaultUnitPos = defaultUnitPos;
	}


}
