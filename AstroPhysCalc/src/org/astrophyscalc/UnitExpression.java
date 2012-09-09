package org.astrophyscalc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UnitExpression {

	final private Set<UnitAndDim> units;
	public final static UnitExpression DIMENSIONLESS = new UnitExpression(new HashSet<UnitAndDim>());

	final static String UNITS_NULL = "Units cannot be null";

	public static UnitExpression create(final Set<UnitAndDim> units) {
		if (units == null) {
			throw new IllegalArgumentException(UNITS_NULL);
		}
		if (units.size() == 0) {
			return DIMENSIONLESS;
		}
		return new UnitExpression(units);
	}

	public static UnitExpression createFromUnit(final Unit unit) {
		if (unit == null) {
			throw new IllegalArgumentException(UNITS_NULL);
		}
		final UnitAndDim unitAndDim = UnitAndDim.create(unit);

		final Set<UnitAndDim> units = new HashSet<UnitAndDim>();
		units.add(unitAndDim);

		return new UnitExpression(units);
	}

	public static UnitExpression create(final Unit unit, final int numerator, final int denominator) {
		if (unit == null) {
			throw new IllegalArgumentException(UNITS_NULL);
		}
		final UnitAndDim unitAndDim = UnitAndDim.create(unit, numerator, denominator);

		final Set<UnitAndDim> units = new HashSet<UnitAndDim>();
		units.add(unitAndDim);

		return new UnitExpression(units);
	}

	public static UnitExpression create(final UnitAndDim ... unitAndDims) {
		final Set<UnitAndDim> unitSet = new HashSet<UnitAndDim>();

		for (UnitAndDim unitAndDim : unitAndDims) {
			if (unitAndDim == null) {
				throw new IllegalArgumentException(UNITS_NULL);
			}
			unitSet.add(unitAndDim);
		}
		return new UnitExpression(unitSet);
	}

	private UnitExpression(final Set<UnitAndDim> units) {
		// Make defensive copy of set
		Set<UnitAndDim> tempUnits = new HashSet<UnitAndDim>();
		for (UnitAndDim unit: units) {
			tempUnits.add(unit);
		}
		this.units = tempUnits;
	}

	public Set<UnitAndDim> getUnits() {
		return units;
	}

	public int size() {
		return units.size();
	}

	/**
	 * Returns the unit expression created by inverting each UnitAndDim
	 * in the original unit expression.
	 *
	 * @return
	 */
	public UnitExpression inverse() {
		final Set<UnitAndDim> newUnits = new HashSet<UnitAndDim>();
		for (UnitAndDim unit : units) {
			newUnits.add(unit.inverse());
		}
		return UnitExpression.create(newUnits);
	}

	/**
	 * Returns the unit expression created by replacing each
	 * UnitAndDim in the original expression with a UnitAndDim with the
	 * same dimension but with the unit replaced by its base.
	 *
	 * @return
	 */
	public UnitExpression toBase() {
		final Set<UnitAndDim> baseUnits = new HashSet<UnitAndDim>();
		for (UnitAndDim unit: units) {
			UnitAndDim baseUnit = unit.toBase();
			baseUnits.add(baseUnit);
		}
		final UnitExpression baseExpr = UnitExpression.create(baseUnits);
		return baseExpr;
	}

	/**
	 * Returns true if the unit expression contains only one item
	 * and that item has dimension one.
	 *
	 * @return
	 */
	public boolean isSingleUnitDimOne() {
		if (units.size() != 1) {
			return false;
		}
		return units.iterator().next().isDimensionOne();
	}

	/**
	 * Returns true if the unit expressions have the same number of elements.
	 *
	 * @param value2
	 * @return
	 */
	public boolean isSameNumberOfUnits(final UnitExpression expr2) {
		return size() == expr2.size();
	}

	public String getUnitName() {
		if (!isSingleUnitDimOne()) {
			return null;
		}
		return units.iterator().next().getUnitName();
	}

	/**
	 * Raises each item in the UnitExpression to the given power.
	 *
	 * @param exponent
	 * @return
	 */
	public UnitExpression pow(final Dimension exponent) {
		final Set<UnitAndDim> newUnits = new HashSet<UnitAndDim>();

		for (UnitAndDim unit : units) {
			final UnitAndDim newUnit = unit.pow(exponent);
			newUnits.add(newUnit);
		}

		return UnitExpression.create(newUnits);
	}

	/**
	 * Raises each item in the UnitExpression to the given power.
	 *
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public UnitExpression pow(final int numerator, final int denominator) {
		return pow(Dimension.create(numerator, denominator));
	}

	/**
	 * Raises each item in the UnitExpression to the given power.
	 *
	 * @param numerator
	 * @param denominator
	 * @return
	 */
	public UnitExpression pow(final int power) {
		return pow(Dimension.create(power, 1));
	}

	/**
	 * Returns true if the two UnitExpressions are equal when converted to base units.
	 *
	 * @param expr2 the UnitExpression to compare to
	 * @return true if the UnitExpressions are equal when converted to base units.
	 */
	public boolean isCompatibleUnits(final UnitExpression expr2) {
		return toBase().equals(expr2.toBase());
	}

	/**
	 * Returns the UnitAndDim from this UnitExpression that shares the same base as the given UnitAndDim
	 * if it exists (regardless of dimension) returns null otherwise.
	 *
	 * @param unitAndDim2
	 * @return
	 */
	public UnitAndDim findWithSameBaseAs(final UnitAndDim unitAndDim2) {
		final Iterator<UnitAndDim> iterator = units.iterator();
		while(iterator.hasNext()) {
			UnitAndDim unitAndDim1 = iterator.next();
			if (unitAndDim1.isSameBaseAs(unitAndDim2)) {
				return unitAndDim1;
			}
		}
		return null;
	}

	/**
	 * Returns the UnitAndDim from this UnitExpression that has identical unit as the given UnitAndDim
	 * if it exists (regardless of dimension) returns null otherwise.
	 *
	 * @param unitAndDim2
	 * @return
	 */
	public UnitAndDim findWithSameUnitAs(final UnitAndDim unitAndDim1) {
		final Iterator<UnitAndDim> iterator = units.iterator();
		while(iterator.hasNext()) {
			UnitAndDim unitAndDim2 = iterator.next();
			if (unitAndDim1.isSameUnitAs(unitAndDim2)) {
				return unitAndDim2;
			}
		}
		return null;
	}

	/**
	 * Returns the product of the given UnitExpression and the UnitExpression
	 * that the method is invoked on.
	 *
	 * @param expr2
	 * @return
	 */
	public UnitExpression multiplyBy(final UnitExpression expr2) {
		final Set<UnitAndDim> newUnitSet = new HashSet<UnitAndDim>();

		// First add all the UnitAndDims from the first expression, multiplied
		// by any units in the second set with the same base.
		final Iterator<UnitAndDim> iterator = units.iterator();
		while (iterator.hasNext()) {
			UnitAndDim unitAndDim1 = iterator.next();
			UnitAndDim sameBase = expr2.findWithSameBaseAs(unitAndDim1);
			if (sameBase != null) {
				// found one with the same base
				UnitAndDim sameUnit = expr2.findWithSameUnitAs(unitAndDim1);
				if (sameUnit != null) {
					// found one with identical unit
					UnitAndDim product = unitAndDim1.multiplyBy(sameUnit);
					if (!product.isDimensionZero()) {
						newUnitSet.add(product);
					}
				}
				else {
					// only same base, not same unit
					UnitAndDim product = unitAndDim1.multiplyByConvert(sameBase);
					if (!product.isDimensionZero()) {
						newUnitSet.add(product);
					}
				}
			}
			else {
				// nothing with same base found, so just add unitAndDim1 to the new set
				newUnitSet.add(unitAndDim1);
			}
		}

		// Now, add all the UnitAndDims from the second expression with no counterparts in the first.
		final Iterator<UnitAndDim> iterator2 = expr2.getUnits().iterator();
		while (iterator2.hasNext()) {
			UnitAndDim unitAndDim = iterator2.next();
			UnitAndDim sameBase = findWithSameBaseAs(unitAndDim);
			if (sameBase == null) {
				newUnitSet.add(unitAndDim);
			}
		}

		return UnitExpression.create(newUnitSet);
	}

	/**
	 * Returns the result of taking the unit expression the method is invoked on and
	 * dividing on the given unit expression.
	 *
	 * @param expr2
	 * @return
	 */
	public UnitExpression divideBy(final UnitExpression expr2) {
		return multiplyBy(expr2.inverse());
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof UnitExpression)) {
			return false;
		}

		final UnitExpression expression = (UnitExpression) object;

		return units.equals(expression.getUnits());
	}

	@Override
	public int hashCode() {
		return units.hashCode();
	}

	@Override
	public String toString() {
		return UnitExpression.class.getSimpleName() + ": " + units.toString();
	}

}