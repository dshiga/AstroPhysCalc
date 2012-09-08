package org.astrophyscalc;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class UnitExpressionTest extends Assert {

	@Test
	public void testCreate() {

		// create from unit
		String exceptionMsg = "";
		try {
			UnitExpression.createFromUnit(null);
		}
		catch (IllegalArgumentException ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(UnitExpression.UNITS_NULL, exceptionMsg);

		UnitExpression expression = UnitExpression.create(new HashSet<UnitAndDim>());
		assertEquals(UnitExpression.DIMENSIONLESS, expression);


		// create(UnitAndDim ... unitAndDim)
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		UnitAndDim ud2 = UnitAndDim.create(LengthUnit.M, 2, -3);

		Set<UnitAndDim> unitSet1 = new HashSet<UnitAndDim>();
		unitSet1.add(ud1);
		unitSet1.add(ud2);

		UnitExpression expr1 = UnitExpression.create(unitSet1);
		UnitExpression expr2 = UnitExpression.create(ud1, ud2);
		assertEquals(expr1, expr2);
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

		Set<UnitAndDim> unitSet1 = new HashSet<UnitAndDim>();
		Set<UnitAndDim> unitSet2 = new HashSet<UnitAndDim>();

		UnitExpression expr1;
		UnitExpression expr2;

		// Empty set, empty set
		expr1 = UnitExpression.create(unitSet1);
		expr2 = UnitExpression.create(unitSet2);
		assertEquals(true, expr1.equals(expr2));

		// Empty set, DIMENSIONLESS
		assertEquals(true, expr1.equals(UnitExpression.DIMENSIONLESS));

		// Empty set, null
		assertEquals(false, expr1.equals(null));

		// Empty set, object
		assertEquals(false, expr1.equals(new Object()));

		// Empty set, set of 1
		ud2 = UnitAndDim.create(unit1, dim1);
		unitSet2.add(ud2);
		expr2 = UnitExpression.create(unitSet2);
		assertEquals(false, expr1.equals(expr2));


		// Sets of 1 containing equal UnitAndDim
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		unitSet1.add(ud1);
		expr1 = UnitExpression.create(unitSet1);

		unitSet2.clear();
		ud2 = UnitAndDim.create(unit1, dim1);
		unitSet2.add(ud2);
		expr1 = UnitExpression.create(unitSet2);

		assertEquals(true, expr1.equals(expr2));


		// Sets of 1 containing unequal UnitAndDim
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		unitSet1.add(ud1);
		expr1 = UnitExpression.create(unitSet1);

		unitSet2.clear();
		ud2 = UnitAndDim.create(unit2, dim2);
		unitSet2.add(ud2);
		expr2 = UnitExpression.create(unitSet2);

		assertEquals(false, expr1.equals(expr2));


		// Set of 1 and set of 2
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		unitSet1.add(ud1);
		expr1 = UnitExpression.create(unitSet1);

		unitSet2.clear();
		ud2 = UnitAndDim.create(unit2, dim2);
		unitSet2.add(ud2);
		unitSet2.add(ud1);
		expr2 = UnitExpression.create(unitSet2);

		assertEquals(false, expr1.equals(expr2));
	}

	@Test
	public void testIsSingleUnitDimOne() {

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

		Set<UnitAndDim> unitSet1 = new HashSet<UnitAndDim>();
		Set<UnitAndDim> unitSet2 = new HashSet<UnitAndDim>();

		UnitExpression expr1;
		UnitExpression expr2;

		// Empty units
		expr1 = UnitExpression.create(unitSet1);
		assertEquals(false, expr1.isSingleUnitDimOne());

		// Multiple units
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = UnitAndDim.create(unit2, dim2);
		unitSet1.add(ud1);
		unitSet1.add(ud2);
		expr1 = UnitExpression.create(unitSet1);
		assertEquals(false, expr1.isSingleUnitDimOne());

		// Single unit, not dim one
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		unitSet1.add(ud1);
		expr1 = UnitExpression.create(unitSet1);
		assertEquals(false, expr1.isSingleUnitDimOne());

		// Single unit, dim one
		unitSet1.clear();
		dim1 = Dimension.create(-1, -1);
		ud1 = UnitAndDim.create(unit1, dim1);
		unitSet1.add(ud1);
		expr1 = UnitExpression.create(unitSet1);
		assertEquals(true, expr1.isSingleUnitDimOne());
	}

	@Test
	public void testToBase() {
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		UnitAndDim ud2 = UnitAndDim.create(LengthUnit.AU, 2, -3);
		UnitExpression expr1 = UnitExpression.create(ud1, ud2);

		UnitAndDim ud3 = UnitAndDim.create(TimeUnit.S, 5, 7);
		UnitAndDim ud4 = UnitAndDim.create(LengthUnit.M, 2, -3);
		UnitExpression expr2 = UnitExpression.create(ud3, ud4);

		assertEquals(expr2, expr1.toBase());
	}

	@Test
	public void testPow() {

		// pow(int, int)
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		UnitAndDim ud2 = UnitAndDim.create(LengthUnit.AU, 2, -3);
		UnitExpression expr1 = UnitExpression.create(ud1, ud2);

		UnitExpression expr2 = UnitExpression.create(ud1.pow(3, 4), ud2.pow(3, 4));
		assertEquals(expr2, expr1.pow(3, 4));


		// pow(int)
		expr2 = UnitExpression.create(ud1.pow(2), ud2.pow(2));
		assertEquals(expr2, expr1.pow(2));


		// pow(Dimension)
		expr2 = UnitExpression.create(ud1.pow(-5, 3), ud2.pow(-5, 3));
		assertEquals(expr2, expr1.pow(Dimension.create(-5, 3)));
	}


	@Test
	public void testIsCompatibleUnits() {
		// equal in units and dimensions
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		UnitAndDim ud2 = UnitAndDim.create(LengthUnit.AU, 2, -3);
		UnitExpression expr1 = UnitExpression.create(ud1, ud2);

		UnitAndDim ud3 = UnitAndDim.create(TimeUnit.S, 5, 7);
		UnitAndDim ud4 = UnitAndDim.create(LengthUnit.M, 2, -3);
		UnitExpression expr2 = UnitExpression.create(ud3, ud4);

		assertEquals(true, expr2.isCompatibleUnits(expr1));


		// equal units but different dimensions
		ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		ud2 = UnitAndDim.create(LengthUnit.AU, 2, -3);
		expr1 = UnitExpression.create(ud1, ud2);

		ud3 = UnitAndDim.create(TimeUnit.S, 4, 7);
		ud4 = UnitAndDim.create(LengthUnit.M, 2, -3);
		expr2 = UnitExpression.create(ud3, ud4);

		assertEquals(false, expr2.isCompatibleUnits(expr1));


		// different units, same dimensions
		ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		expr1 = UnitExpression.create(ud1);

		ud3 = UnitAndDim.create(TimeUnit.S, 5, 7);
		ud4 = UnitAndDim.create(LengthUnit.M, 2, -3);
		expr2 = UnitExpression.create(ud3, ud4);

		assertEquals(false, expr2.isCompatibleUnits(expr1));
	}


	@Test
	public void testFindWithSameBaseAs() {
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		UnitAndDim ud2 = UnitAndDim.create(LengthUnit.AU, 2, -3);
		UnitExpression expr1 = UnitExpression.create(ud1, ud2);

		UnitAndDim ud3 = expr1.findWithSameBaseAs(UnitAndDim.create(TimeUnit.YEARS, 1, 2));
		assertEquals(ud3, ud1);

		UnitExpression expr2 = UnitExpression.create(ud2);
		ud3 = expr2.findWithSameBaseAs(UnitAndDim.create(TimeUnit.YEARS, 1, 2));
		assertEquals(null, ud3);
	}


	@Test
	public void testFindWithSameUnitAs() {
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		UnitAndDim ud2 = UnitAndDim.create(LengthUnit.AU, 2, -3);
		UnitExpression expr1 = UnitExpression.create(ud1, ud2);

		UnitAndDim ud3 = expr1.findWithSameUnitAs(UnitAndDim.create(TimeUnit.YEARS, 1, 2));
		assertEquals(null, ud3);

		ud3 = expr1.findWithSameUnitAs(UnitAndDim.create(TimeUnit.DAYS, 1, 2));
		assertEquals(ud1, ud3);
	}


	@Test
	public void testMultiplyBy() {
		// No identical units
		UnitAndDim ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		UnitAndDim ud2 = UnitAndDim.create(LengthUnit.AU, 2, -3);
		UnitExpression expr1 = UnitExpression.create(ud1, ud2);

		UnitAndDim ud3 = UnitAndDim.create(TimeUnit.S, 1, 2);
		UnitAndDim ud4 = UnitAndDim.create(LengthUnit.M, 2, -4);
		UnitAndDim ud5 = UnitAndDim.create(MassUnit.KG, 3, 2);
		UnitExpression expr2 = UnitExpression.create(ud3, ud4, ud5);

		UnitAndDim ud13 = UnitAndDim.create(TimeUnit.DAYS, 5, 7)
				.multiplyByConvert(UnitAndDim.create(TimeUnit.S, 1, 2));
		UnitAndDim ud24 = UnitAndDim.create(LengthUnit.AU, 2, -3)
				.multiplyByConvert(UnitAndDim.create(LengthUnit.M, 2, -4));

		UnitExpression product = UnitExpression.create(ud13, ud24, ud5);
		assertEquals(product, expr1.multiplyBy(expr2));


		// One pair of identical units
		ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		ud2 = UnitAndDim.create(LengthUnit.M, 2, -3);
		expr1 = UnitExpression.create(ud1, ud2);

		ud3 = UnitAndDim.create(TimeUnit.S, 1, 2);
		ud4 = UnitAndDim.create(LengthUnit.M, 2, -4);
		ud5 = UnitAndDim.create(MassUnit.KG, 3, 2);
		expr2 = UnitExpression.create(ud3, ud4, ud5);

		ud13 = UnitAndDim.create(TimeUnit.DAYS, 5, 7)
				.multiplyByConvert(UnitAndDim.create(TimeUnit.S, 1, 2));
		ud24 = UnitAndDim.create(LengthUnit.M, 2, -3)
				.multiplyByConvert(UnitAndDim.create(LengthUnit.M, 2, -4));

		product = UnitExpression.create(ud13, ud24, ud5);
		assertEquals(product, expr1.multiplyBy(expr2));


		// First expression has one more UnitAndDim than second
		ud1 = UnitAndDim.create(TimeUnit.DAYS, 5, 7);
		ud2 = UnitAndDim.create(LengthUnit.M, 2, -3);
		ud3 = UnitAndDim.create(MassUnit.KG, 1, 2);
		expr1 = UnitExpression.create(ud1, ud2, ud3);

		ud4 = UnitAndDim.create(LengthUnit.AU, 2, -4);
		ud5 = UnitAndDim.create(TimeUnit.S, 3, 2);
		expr2 = UnitExpression.create(ud4, ud5);

		UnitAndDim ud15 = UnitAndDim.create(TimeUnit.DAYS, 5, 7)
					.multiplyByConvert(UnitAndDim.create(TimeUnit.S, 3, 2));
		ud24 = UnitAndDim.create(LengthUnit.M, 2, -3)
					.multiplyByConvert(UnitAndDim.create(LengthUnit.AU, 2, -4));

		product = UnitExpression.create(ud15, ud24, ud3);
		assertEquals(product, expr1.multiplyBy(expr2));
	}

}
