package org.astrophyscalc;

import org.junit.Assert;
import org.junit.Test;


public class UnitAndDimTest extends Assert {

	@Test
	public void testCreate() {
		final Unit unit = TimeUnit.DAYS;
		final int numerator = 5;
		final int denominator = 7;
		final Dimension dim = Dimension.create(numerator, denominator);
		final UnitAndDim ud = UnitAndDim.create(unit, dim);

		assertEquals(dim, ud.getDim());

		String exceptionMsg = "";
		try {
			UnitAndDim.create(null, Dimension.create(2, 3));
		}
		catch (Exception ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(UnitAndDim.UNIT_NULL, exceptionMsg);

		try {
			UnitAndDim.create(TimeUnit.S, null);
		}
		catch (Exception ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(UnitAndDim.DIM_NULL, exceptionMsg);

		try {
			UnitAndDim.create(TimeUnit.DAYS, -5, 0);
		}
		catch (Exception ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(UnitAndDim.DENOMINATOR_ZERO, exceptionMsg);

		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.YEARS, 3, -17);
		assertEquals(-3, ud1.getDim().getNumerator());
		assertEquals(17, ud1.getDim().getDenominator());

		ud1 = UnitAndDim.create(TimeUnit.DAYS, 4);
		assertEquals(4, ud1.getDim().getNumerator());
		assertEquals(1, ud1.getDim().getDenominator());

		assertEquals(UnitAndDim.create(TimeUnit.S, 1, 1), UnitAndDim.create(TimeUnit.S));
	}

	@Test
	public void testEquals() {

		Unit unit1 = TimeUnit.DAYS;
		int num1 = 5;
		int den1 = 7;
		Dimension dim1 = Dimension.create(num1, den1);

		Unit unit2 = TimeUnit.YEARS;
		int num2 = 3;
		int den2 = 9;
		Dimension dim2 = Dimension.create(num2, den2);

		UnitAndDim ud1;
		UnitAndDim ud2;

		// UnitAndDim, null
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = null;
		assertEquals(false, ud1.equals(ud2));

		// UnitAndDim, Object
		ud1 = UnitAndDim.create(unit1, dim1);
		assertEquals(false, ud1.equals(new Object()));

		// equal, equal
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = UnitAndDim.create(unit1, dim1);
		assertEquals(true, ud1.equals(ud2));

		// equal, not equal
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = UnitAndDim.create(unit1, dim2);
		assertEquals(false, ud1.equals(ud2));

		// not equal, equal
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = UnitAndDim.create(unit2, dim1);
		assertEquals(false, ud1.equals(ud2));

		// not equal, not equal
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = UnitAndDim.create(unit2, dim2);
		assertEquals(false, ud1.equals(ud2));
	}

	@Test
	public void testIsDimensionOne() {
		assertEquals(true, UnitAndDim.create(TimeUnit.YEARS).isDimensionOne());
		assertEquals(false, UnitAndDim.create(TimeUnit.YEARS, -1).isDimensionOne());
	}

	@Test
	public void testIsDimensionZero() {
		assertEquals(true, UnitAndDim.create(TimeUnit.DAYS, 0).isDimensionZero());
	}

	@Test
	public void testToBase() {
		assertEquals(UnitAndDim.create(TimeUnit.S, -3, 5), UnitAndDim.create(TimeUnit.YEARS, 3, -5).toBase());
	}

	@Test
	public void testIsSameBaseAs() {
		assertEquals(true, UnitAndDim.create(TimeUnit.DAYS).isSameBaseAs(UnitAndDim.create(TimeUnit.YEARS)));
		assertEquals(false, UnitAndDim.create(TimeUnit.DAYS).isSameBaseAs(UnitAndDim.create(LengthUnit.M)));
	}

	@Test
	public void isSameUnitAs() {
		assertEquals(false, UnitAndDim.create(TimeUnit.DAYS).isSameUnitAs(UnitAndDim.create(TimeUnit.YEARS)));
		assertEquals(false, UnitAndDim.create(LengthUnit.AU).isSameUnitAs(UnitAndDim.create(TimeUnit.YEARS)));
		assertEquals(true, UnitAndDim.create(TimeUnit.DAYS).isSameUnitAs(UnitAndDim.create(TimeUnit.DAYS)));
	}

	@Test
	public void testGetConversionTo() {
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.S);
		UnitAndDim ud2 = UnitAndDim.create(LengthUnit.M);

		String exceptionMsg = "";
		try {
			ud1.getConversionTo(ud2);
		}
		catch (IllegalArgumentException ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(UnitAndDim.NOT_SAME_BASE, exceptionMsg);

		UnitAndDim ud3 = UnitAndDim.create(TimeUnit.DAYS);
		assertEquals(86400d, ud3.getConversionTo(ud1), 1E-9d);

		assertEquals(1d / 86400d, ud1.getConversionTo(ud3), 1E-9d);
	}

	@Test
	public void testInverse() {
		assertEquals(UnitAndDim.create(TimeUnit.DAYS, -3, 5).inverse(), UnitAndDim.create(TimeUnit.DAYS, 3, 5));
	}

	@Test
	public void testMultiplyBy() {
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.S, 2, 7);
		UnitAndDim ud2 = UnitAndDim.create(TimeUnit.DAYS, 2, 5);

		String exceptionMsg = "";
		try {
			ud1.multiplyBy(ud2);
		}
		catch (IllegalArgumentException ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(UnitAndDim.NOT_SAME_UNIT, exceptionMsg);

		UnitAndDim ud3 = UnitAndDim.create(TimeUnit.DAYS, -2, 5);
		UnitAndDim result = ud3.multiplyBy(ud2);
		assertEquals(true, result.getDim().isZero());
		assertEquals(TimeUnit.DAYS, result.getUnit());
	}

	@Test
	public void testDivideBy() {
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.YEARS, 2, 7);
		UnitAndDim ud2 = UnitAndDim.create(TimeUnit.YEARS, 2, 5);

		assertEquals(UnitAndDim.create(TimeUnit.YEARS, -4, 35), ud1.divideBy(ud2));
	}

	@Test
	public void testPow() {
		UnitAndDim ud2 = UnitAndDim.create(TimeUnit.YEARS, 1, 2);

		assertEquals(UnitAndDim.create(TimeUnit.YEARS, 3, 10), ud2.pow(3, 5));
	}
}
