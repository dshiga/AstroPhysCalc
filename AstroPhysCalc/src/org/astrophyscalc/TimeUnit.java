package org.astrophyscalc;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public enum TimeUnit implements Unit {

	MIC_S("micro s", 1E-6d),
	MS("ms", 1E-3d),
	S("s", 1d),
	MIN("minutes", 60d),
	HR("hours", 3600d),
	DAYS("days", 86400d),
	YEARS("years", 365d * 86400d);

	private String name;
	private double ratioToBase;

	private static TimeUnit base = S;
	private static TimeUnitComparator comparator = new TimeUnitComparator();

	private TimeUnit(final String name, final double ratioToBase) {
		this.name = name;
		this.ratioToBase = ratioToBase;
	}

	private static class TimeUnitComparator implements Comparator<Unit> {

		@Override
		public int compare(Unit lhs, Unit rhs) {
			final double diff = lhs.inBaseUnits() - rhs.inBaseUnits();
			if (diff < 0) {
				return -1;
			}
			else if (diff > 0) {
				return 1;
			}
			return 0;
		}
	}

	public int compare(TimeUnit unit) {
		return comparator.compare(this, unit);
	}

	public static TimeUnit getUnit(final String name) {
		TimeUnit[] units = TimeUnit.values();
		for (TimeUnit unit : units) {
			if (unit.getName().equals(name)) {
				return unit;
			}
		}
		return null;
	}

	public static Set<Unit> getAll() {
		TimeUnit[] units = TimeUnit.values();
		final Set<Unit> unitSet = new HashSet<Unit>();
		for (TimeUnit unit : units) {
			unitSet.add(unit);
		}
		return unitSet;
	}

	@Override
	public boolean isSameBaseAs(final Unit unit2) {
		return unit2 instanceof TimeUnit;
	}

	@Override
	public double inBaseUnits() {
		return ratioToBase;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public TimeUnit getBase() {
		return base;
	}

}