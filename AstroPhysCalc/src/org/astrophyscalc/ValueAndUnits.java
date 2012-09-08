package org.astrophyscalc;

import java.util.HashSet;
import java.util.Set;


public class ValueAndUnits {

	final private double value;
	final private UnitExpression unitExpression;

	final static String UNIT_EXPRESSION_NULL = "Units cannot be null";
	final static String DIFFERENT_NUMBER_UNITS = "UnitExpressions must contain the same number of units.";
	final static String INCOMPATIBLE_UNIT_EXPRESSIONS = "Unit expressions must contain only equivalent units with same dimensions.";
	final static String CANNOT_DIVIDE_BY_ZERO = "Cannot divide by zero.";

	public static ValueAndUnits create(final double value, final UnitExpression units) {
		if (units == null) {
			throw new IllegalArgumentException(UNIT_EXPRESSION_NULL);
		}
		return new ValueAndUnits(value, units);
	}

	public static ValueAndUnits create(final double value) {
		return new ValueAndUnits(value, UnitExpression.DIMENSIONLESS);
	}

	public static ValueAndUnits createFromUnit(final double value, final Unit unit) {
		if (unit == null) {
			throw new IllegalArgumentException(UNIT_EXPRESSION_NULL);
		}
		final UnitExpression unitExpr = UnitExpression.createFromUnit(unit);
		return new ValueAndUnits(value, unitExpr);
	}

	public static ValueAndUnits createFromUnitAndDim(final double value, final UnitAndDim ...unitAndDim) {
		if (unitAndDim == null) {
			throw new IllegalArgumentException(UNIT_EXPRESSION_NULL);
		}
		final Set<UnitAndDim> units = new HashSet<UnitAndDim>();
		for (UnitAndDim unit : unitAndDim) {
			units.add(unit);
		}
		final UnitExpression expr = UnitExpression.create(units);
		return new ValueAndUnits(value, expr);
	}

	private ValueAndUnits(final double value, final UnitExpression units) {
		this.value = value;
		this.unitExpression = units;
	}

	public double getValue() {
		return value;
	}

	public UnitExpression getUnitExpression() {
		return unitExpression;
	}

	public int getUnitExpressionSize() {
		return unitExpression.size();
	}

	public boolean isSingleUnitDimOne() {
		return unitExpression.isSingleUnitDimOne();
	}

	public UnitExpression getBaseUnitExpression() {
		return unitExpression.toBase();
	}

	public ValueAndUnits toSameUnitsAs(final ValueAndUnits value2) {
		if (!unitExpression.isSameNumberOfUnits(value2.getUnitExpression())) {
			throw new IllegalArgumentException(DIFFERENT_NUMBER_UNITS);
		}
		if (!unitExpression.isCompatibleUnits(value2.getUnitExpression())) {
			throw new IllegalArgumentException(INCOMPATIBLE_UNIT_EXPRESSIONS);
		}

		final double conversion = getConversionFactorTo(value2);
		final ValueAndUnits newValueAndUnits = ValueAndUnits.create(getValue() * conversion, value2.getUnitExpression());
		return newValueAndUnits;
	}

	/**
	 * Returns the factor the ValueAndUnit's value should be multiplied by
	 * in order to be expressed in the same units as those of the given ValueAndUnits.
	 *
	 * @param value2
	 * @return
	 */
	double getConversionFactorTo(final ValueAndUnits value2) {
		double factor = 1d;

		final UnitExpression expr2 = value2.getUnitExpression();
		for (UnitAndDim unitAndDim1 : unitExpression.getUnits()) {
			UnitAndDim unitAndDim2 = expr2.findWithSameBaseAs(unitAndDim1);
			if (unitAndDim2 != null) {
				factor *= unitAndDim1.getConversionTo(unitAndDim2);
			}
		}
		return factor;
	}

	/**
	 * Returns the sum in the units of the ValueAndUnits it is invoked on.
	 *
	 * @param value2
	 * @return
	 */
	public ValueAndUnits add(final ValueAndUnits value2) {
		final ValueAndUnits convertedValueAndUnits = value2.toSameUnitsAs(this);

		// Units are now identical
		final ValueAndUnits sum = ValueAndUnits.create(getValue() + convertedValueAndUnits.getValue(), getUnitExpression());
		return sum;
	}

	/**
	 * Returns the difference of the two ValueAndUnits in the units of the ValueAndUnits
	 * the method was invoked on.
	 *
	 * @param value2
	 * @return
	 */
	public ValueAndUnits subtract(final ValueAndUnits value2) {
		final ValueAndUnits convertedValueAndUnits = value2.toSameUnitsAs(this);

		// Units are now identical
		final ValueAndUnits diff = ValueAndUnits.create(getValue() - convertedValueAndUnits.getValue(), getUnitExpression());
		return diff;
	}

	/**
	 * Multiplies the ValueAndUnits and returns a result in the units of the ValueAndUnits
	 * the method was invoked on.
	 *
	 * @param value2
	 * @return
	 */
	public ValueAndUnits multiplyBy(final ValueAndUnits value2) {
		final double conversion = value2.getConversionFactorTo(this);

		final UnitExpression newUnitExpr = getUnitExpression().multiplyBy(value2.getUnitExpression());

		return ValueAndUnits.create(getValue() * conversion * value2.getValue(), newUnitExpr);
	}

	/**
	 * Divides the ValueAndUnits the method was invoked on by the given ValueAndUnits.
	 * Returns a result in the units of the ValueAndUnits the method was invoked on.
	 *
	 * @param value2
	 * @return
	 */
	public ValueAndUnits divideBy(final ValueAndUnits value2) {
		if (value2.getValue() == 0) {
			throw new IllegalArgumentException(CANNOT_DIVIDE_BY_ZERO);
		}

		final double conversion = value2.getConversionFactorTo(this);

		final UnitExpression newUnitExpr = unitExpression.divideBy(value2.getUnitExpression());

		return ValueAndUnits.create(value / (conversion * value2.getValue()), newUnitExpr);
	}

	/**
	 * Returns the result of raising this ValueAndUnits to the given power.
	 *
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public ValueAndUnits pow(final int numerator, final int denominator) {
		final Dimension exponent = Dimension.create(numerator, denominator);

		final UnitExpression newUnitExpr = unitExpression.pow(exponent);

		double newValue = Math.pow(value, numerator);
		newValue = Math.pow(newValue, 1d / denominator);

		return ValueAndUnits.create(newValue, newUnitExpr);
	}

	public ValueAndUnits pow(final int power) {
		return pow(power, 1);
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof ValueAndUnits)) {
			return false;
		}
		final ValueAndUnits vu = (ValueAndUnits) object;

		return getValue() == vu.getValue() && unitExpression.equals(vu.getUnitExpression());
	}

	@Override
	public int hashCode() {
		Double d = new Double(value);

		return d.hashCode() ^ unitExpression.hashCode();
	}

	@Override
	public String toString() {
		return ValueAndUnits.class.getSimpleName() + ": " + value + ", "+ unitExpression.toString();
	}

}
