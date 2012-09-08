package org.astrophyscalc;

import java.util.Set;

public class UnitUtil {

	final static String EMPTY_UNIT_SET = "Units cannot be an empty set";
	final static String MULTIPLE_UNITS = "UnitExpression contains multiple items.";
	final static String UNITTED_VALUE_NULL = "ValueAndUnits cannot be null.";
	final static String UNIT_EXPR_NOT_SINGLE_UNIT_DIM_ONE = "UnitExpression must contain single unit of dimension one.";

	public static Unit getBestUnit(final ValueAndUnits vu, final Set<Unit> units) {
		if (vu == null) {
			throw new IllegalArgumentException(UNITTED_VALUE_NULL);
		}
		if(vu.getUnitExpression() == UnitExpression.DIMENSIONLESS) {
			return null;
		}
		if (!vu.getUnitExpression().isSingleUnitDimOne()) {
			throw new IllegalArgumentException(UNIT_EXPR_NOT_SINGLE_UNIT_DIM_ONE);
		}

		final double value = vu.getValue();
		if (value == 0) {
			return units.iterator().next().getBase();
		}
		double bestRatio = 1;
		Unit bestUnit = null;
		for (Unit unit : units) {
			double absValInUnits = Math.abs(value / unit.inBaseUnits());
			double ratio = absValInUnits > 1 ? absValInUnits: 1 / absValInUnits;
			if (absValInUnits == 1) {
				return unit;
			}
			if (bestUnit == null) {
				bestUnit = unit;
				bestRatio = ratio;
			}
			else {
				if (ratio < bestRatio) {
					bestUnit = unit;
					bestRatio = ratio;
				}
			}
		}
		return bestUnit;
	}

}
