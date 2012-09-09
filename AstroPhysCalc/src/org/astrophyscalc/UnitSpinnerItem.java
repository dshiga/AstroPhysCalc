package org.astrophyscalc;

public class UnitSpinnerItem {

	private final UnitExpression expr;
	private final String label;

	public static UnitSpinnerItem create(final UnitExpression expr, final String label) {
		return new UnitSpinnerItem(expr, label);
	}

	private UnitSpinnerItem(final UnitExpression expr, final String label) {
		this.expr = expr;
		this.label = label;
	}

	public UnitExpression getUnitExpression() {
		return expr;
	}

	@Override
	public String toString() {
		return label;
	}

}
