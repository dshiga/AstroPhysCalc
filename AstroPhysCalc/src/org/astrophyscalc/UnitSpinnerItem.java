package org.astrophyscalc;

public class UnitSpinnerItem {

	public static final UnitSpinnerItem[] ORBIT_MASS = {
        UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_EARTH), "Earths"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_JUP), "Jupiters"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_SUN), "Suns")
	};

	public static final UnitSpinnerItem[] ORBIT_RADIUS = {
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.KM), "km"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.AU), "AU"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.LY), "ly")
	};

	public static final UnitSpinnerItem[] ORBIT_PERIOD = {
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.S), "s"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.MIN), "minutes"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.HR), "hours"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.DAYS), "days"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.YEARS), "years")
	};

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
