package org.astrophyscalc;

import java.util.HashSet;
import java.util.Set;

public class UnitAndDim {

	private final Unit unit;
	private final Dimension dim;
	static final String UNIT_NULL = "Unit cannot be null.";
	static final String DIM_NULL = "Dimension cannot be null.";
	static final String DENOMINATOR_ZERO = "Denominator cannot be zero";
	static final String NOT_SAME_BASE = "Units not derived from same base.";
	static final String NOT_SAME_UNIT = "Units must be equal for this operation.";

	public static UnitAndDim create(final Unit unit, final Dimension dim) {
		if (unit == null) {
			throw new IllegalArgumentException(UNIT_NULL);
		}
		if (dim == null) {
			throw new IllegalArgumentException(DIM_NULL);
		}
		return new UnitAndDim(unit, dim);
	}

	public static UnitAndDim create(final Unit unit, final int numerator, final int denominator) {
		if (unit == null) {
			throw new IllegalArgumentException(UNIT_NULL);
		}
		if (denominator == 0) {
			throw new IllegalArgumentException(DENOMINATOR_ZERO);
		}
		return new UnitAndDim(unit, numerator, denominator);
	}

	public static UnitAndDim create(final Unit unit, final int power) {
		if (unit == null) {
			throw new IllegalArgumentException(UNIT_NULL);
		}
		return new UnitAndDim(unit, power, 1);
	}

	public static UnitAndDim create(final Unit unit) {
		if (unit == null) {
			throw new IllegalArgumentException(UNIT_NULL);
		}
		return new UnitAndDim(unit, 1, 1);
	}

	private UnitAndDim(final Unit unit, final Dimension dim) {
		this.unit = unit;
		this.dim = dim;
	}

	private UnitAndDim(final Unit unit, final int numerator, final int denominator) {
		this.unit = unit;
		this.dim = Dimension.create(numerator, denominator);
	}

	public static Set<UnitAndDim> getSet(final Set<Unit> units) {
		Set<UnitAndDim> unitSet = new HashSet<UnitAndDim>();
		for (Unit unit: units) {
			unitSet.add(UnitAndDim.create(unit));
		}
		return unitSet;
	}

	public Unit getUnit() {
		return unit;
	}

	public Dimension getDim() {
		return dim;
	}

	public boolean isDimensionOne() {
		return dim.isOne();
	}

	public boolean isDimensionZero() {
		return dim.isZero();
	}

	public String getUnitName() {
		return unit.getName();
	}

	public UnitAndDim toBase() {
		final Unit baseUnit = unit.getBase();
		final UnitAndDim baseUnitAndDim = UnitAndDim.create(baseUnit, dim);
		return baseUnitAndDim;
	}

	/**
	 * Returns true if the two units share the same base, regardless of dimension,
	 * false otherwise.
	 *
	 * @param unitAndDim
	 * @return
	 */
	public boolean isSameBaseAs(final UnitAndDim unitAndDim) {
		return unit.isSameBaseAs(unitAndDim.getUnit());
	}

	/**
	 * Returns true only if the units are strictly equal.
	 *
	 * @param unitAndDim2
	 * @return
	 */
	public boolean isSameUnitAs(final UnitAndDim unitAndDim2) {
		return unit.equals(unitAndDim2.getUnit());
	}

	/**
	 * Returns the factor that a value must be multiplied by to express its associated UnitAndDim
	 * in the units of the given UnitAndDim.
	 *
	 * @param unitAndDim2
	 * @return
	 */
	public double getConversionTo(final UnitAndDim unitAndDim2) {
		if (!isSameBaseAs(unitAndDim2)) {
			throw new IllegalArgumentException(NOT_SAME_BASE);
		}

		double factor = getUnit().inBaseUnits() / unitAndDim2.getUnit().inBaseUnits();
		factor = Math.pow(factor, getDim().toDouble());
		return factor;
	}

	/**
	 * Returns the product of the two UnitAndDims.
	 * They must be of the same unit, otherwise an IllegalArgumentException is thrown.
	 *
	 * @param unitAndDim2
	 * @return
	 */
	public UnitAndDim multiplyBy(final UnitAndDim unitAndDim2) {
		if (!isSameUnitAs(unitAndDim2)) {
			throw new IllegalArgumentException(NOT_SAME_UNIT);
		}

		final Dimension newDim = getDim().add(unitAndDim2.getDim());

		final UnitAndDim newUnitAndDim = UnitAndDim.create(unit, newDim);
		return newUnitAndDim;
	}

	/**
	 * Returns the product of the two UnitAndDims. If the units are not identical,
	 * those of the given UnitAndDim will be converted to match those of the UnitAndDim
	 * the method is invoked on.
	 *
	 * They must be of the same base, otherwise an IllegalArgumentException is thrown.
	 *
	 * @param unitAndDim2
	 * @return
	 */
	public UnitAndDim multiplyByConvert(final UnitAndDim unitAndDim2) {
		if (!isSameBaseAs(unitAndDim2)) {
			throw new IllegalArgumentException(NOT_SAME_BASE);
		}

		if (isSameUnitAs(unitAndDim2)) {
 			return multiplyBy(unitAndDim2);
		}
		else {
			return multiplyBy(unitAndDim2.toSameUnitAs(this));
		}
	}

	public UnitAndDim divideBy(final UnitAndDim unitAndDim2) {
		return multiplyBy(unitAndDim2.inverse());
	}

	/**
	 * Returns the result of dividing the UnitAndDim the method is invoked on by the given one.
	 * If the units are not identical, those of the given UnitAndDim will be converted
	 * to match those of the UnitAndDim the method is invoked on.
	 * They must be of the same base, otherwise an IllegalArgumentException is thrown.
	 *
	 * @param unitAndDim2
	 * @return
	 */
	public UnitAndDim divideByConvert(final UnitAndDim unitAndDim2) {
		if (!isSameBaseAs(unitAndDim2)) {
			throw new IllegalArgumentException(NOT_SAME_BASE);
		}

		if (isSameUnitAs(unitAndDim2)) {
 			return divideBy(unitAndDim2);
		}
		else {
			return divideBy(unitAndDim2.toSameUnitAs(this));
		}
	}

	public UnitAndDim inverse() {
		return UnitAndDim.create(unit, getDim().negative());
	}

	/**
	 * Returns a UnitAndDim with the same unit as the given UnitAndDim
	 * but with the dimension of the UnitAndDim the method is invoked on.
	 * If units are not of same base, an IllegalArgumentException is thrown.
	 *
	 * @param unitAndDim2
	 * @return
	 */
	public UnitAndDim toSameUnitAs(final UnitAndDim unitAndDim2) {
		if (!isSameBaseAs(unitAndDim2)) {
			throw new IllegalArgumentException(NOT_SAME_BASE);
		}

		return UnitAndDim.create(unitAndDim2.getUnit(), getDim());
	}

	/**
	 * Returns a UnitAndDim that has the same Unit as the one the method is invoked on,
	 * but with its dimension multiplied by the given exponent.
	 *
	 * @param exponent
	 * @return
	 */
	public UnitAndDim pow(final int numerator, final int denominator) {
		if (denominator == 0) {
			throw new IllegalArgumentException(DENOMINATOR_ZERO);
		}

		return pow(Dimension.create(numerator, denominator));
	}

	public UnitAndDim pow(final int exponent) {
		return pow(Dimension.create(exponent, 1));
	}

	public UnitAndDim pow(final Dimension exponent) {
		final Dimension newDim = dim.pow(exponent);
		final UnitAndDim newUnitAndDim = UnitAndDim.create(unit, newDim);
		return newUnitAndDim;
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof UnitAndDim)) {
			return false;
		}

		UnitAndDim ud = (UnitAndDim) object;

		return unit.equals(ud.getUnit()) && dim.equals(ud.getDim());
	}

	@Override
	public int hashCode() {
		return unit.hashCode() ^ dim.hashCode();
	}

	@Override
	public String toString() {
		return UnitAndDim.class.getSimpleName() + ": " + unit.toString() + ", " + dim.toString();
	}
}
