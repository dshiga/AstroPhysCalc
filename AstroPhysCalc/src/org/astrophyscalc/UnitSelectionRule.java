package org.astrophyscalc;

public class UnitSelectionRule implements Comparable<UnitSelectionRule> {

	private final ValueAndUnits limit;
	private final ValueAndUnits units;

	public static UnitSelectionRule create(final ValueAndUnits limit) {
		return new UnitSelectionRule(limit, limit);
	}

	public static UnitSelectionRule create(final ValueAndUnits limit, final ValueAndUnits units) {
		return new UnitSelectionRule(limit, units);
	}

	public static UnitSelectionRule create(final ValueAndUnits limit, final UnitExpression expr) {
		return new UnitSelectionRule(limit, ValueAndUnits.create(1d, expr));
	}

	public static UnitSelectionRule create(final UnitExpression expr) {
		return new UnitSelectionRule(ValueAndUnits.create(1d, expr), ValueAndUnits.create(1d, expr));
	}

	private UnitSelectionRule(final ValueAndUnits limit, final ValueAndUnits units) {
		this.limit = limit;
		this.units = units;
	}

	public ValueAndUnits getLimit() {
		return limit;
	}

	public ValueAndUnits getUnits() {
		return units;
	}

	/**
	 *  The requirement for equality here is less stringent than that of ValueAndUnits.equals().
	 *  Here, we consider ValueAndUnits(1, KM) to be equal to ValueAndUnits(1000, M), for example, even though
	 *  ValueAndUnits.equals() returns false in that case.
	 */
	@Override
	public boolean equals(Object object2) {
		if (!(object2 instanceof UnitSelectionRule)) {
			return false;
		}

		final UnitSelectionRule rule2 = (UnitSelectionRule) object2;

		return limit.toBaseUnits().equals(rule2.getLimit().toBaseUnits())
				&& units.toBaseUnits().equals(rule2.getUnits().toBaseUnits());
	}

	@Override
	public int hashCode() {
		return limit.hashCode() ^ units.hashCode();
	}

	@Override
	public String toString() {
		return UnitSelectionRule.class.getSimpleName() + ": " + limit.toString() + ", "+ units.toString();
	}

	/**
	 * Descending order - rules with the greatest values first.
	 */
	@Override
	public int compareTo(UnitSelectionRule rule2) {
		if (rule2 == null) {
			throw new NullPointerException();
		}

		return rule2.getLimit().compareTo(limit);
	}

}
