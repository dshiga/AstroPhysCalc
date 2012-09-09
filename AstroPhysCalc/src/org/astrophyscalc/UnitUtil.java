package org.astrophyscalc;

import java.util.HashSet;
import java.util.Set;


public class UnitUtil {

	final static String EMPTY_UNIT_SET = "Units cannot be an empty set";
	final static String MULTIPLE_UNITS = "UnitExpression contains multiple items.";
	final static String UNITTED_VALUE_NULL = "ValueAndUnits cannot be null.";
	final static String UNIT_EXPR_NOT_SINGLE_UNIT_DIM_ONE = "UnitExpression must contain single unit of dimension one.";

	public static Set<UnitExpression> getTimeExpressionsExclude(final TimeUnit ... excludes) {
		final Set<TimeUnit> excludedSet = new HashSet<TimeUnit>();
		for (TimeUnit u: excludes) {
			excludedSet.add(u);
		}

		final Set<UnitExpression> exprs = new HashSet<UnitExpression>();
		for (TimeUnit u : TimeUnit.values()) {
			if (!excludedSet.contains(u)) {
				exprs.add(UnitExpression.createFromUnit(u));
			}
		}
		return exprs;
	}

	public static Set<UnitExpression> getTimeExpressions(final TimeUnit ... units) {
		final Set<TimeUnit> unitSet = new HashSet<TimeUnit>();
		for (TimeUnit u: units) {
			unitSet.add(u);
		}

		final Set<UnitExpression> exprs = new HashSet<UnitExpression>();
		for (TimeUnit u : unitSet) {
			exprs.add(UnitExpression.createFromUnit(u));
		}
		return exprs;
	}

	public static Set<UnitExpression> getLengthExpressions(final LengthUnit ... units) {
		final Set<LengthUnit> unitSet = new HashSet<LengthUnit>();
		for (LengthUnit u: units) {
			unitSet.add(u);
		}

		final Set<UnitExpression> exprs = new HashSet<UnitExpression>();
		for (LengthUnit u : unitSet) {
			exprs.add(UnitExpression.createFromUnit(u));
		}
		return exprs;
	}


/*
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
*/
/*	public ValueAndUnits getInBestUnits(final UnitSelector selector) {
		return selector.
	}

	public static class UnitSelector {

		private final Set<UnitSelectionRule> rules;

		public UnitSelector create(final Set<UnitSelectionRule> rules) {
			return new UnitSelector(rules);
		}

		private UnitSelector(final Set<UnitSelectionRule> rules) {
			this.rules = rules;
		}

		public ValueAndUnits getInBestUnits() {

		}

	}

	public static class UnitSelectionRule {

		private final ValueAndUnits vu;
		private final UnitExpression expr;

		public UnitSelectionRule create(final ValueAndUnits vu, final UnitExpression expr) {
			return new UnitSelectionRule(vu, expr);
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
	}
*/
}
