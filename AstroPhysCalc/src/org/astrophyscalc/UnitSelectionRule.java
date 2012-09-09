package org.astrophyscalc;

public class UnitSelectionRule implements Comparable<UnitSelectionRule> {

	private final ValueAndUnits vu;
	private final UnitExpression expr;

	public static UnitSelectionRule create(final ValueAndUnits vu, final UnitExpression expr) {
		return new UnitSelectionRule(vu, expr);
	}

	public static UnitSelectionRule create(final UnitExpression expr) {
		return new UnitSelectionRule(ValueAndUnits.create(1d, expr), expr);
	}

	private UnitSelectionRule(final ValueAndUnits vu, final UnitExpression expr) {
		this.vu = vu;
		this.expr = expr;
	}

	public ValueAndUnits getValueAndUnits() {
		return vu;
	}

	public UnitExpression getUnitExpression() {
		return expr;
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

		return vu.toBaseUnits().equals(rule2.getValueAndUnits().toBaseUnits())
				&& expr.equals(rule2.getUnitExpression());
	}

	@Override
	public int hashCode() {
		return vu.hashCode() ^ expr.hashCode();
	}

	@Override
	public String toString() {
		return UnitSelectionRule.class.getSimpleName() + ": " + vu.toString() + ", "+ expr.toString();
	}

	/**
	 * Descending order - rules with the greatest values first.
	 */
	@Override
	public int compareTo(UnitSelectionRule rule2) {
		if (rule2 == null) {
			throw new NullPointerException();
		}

		return rule2.getValueAndUnits().compareTo(vu);
	}

}
