package org.astrophyscalc;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ValueAndUnitsTest extends Assert {

	@Test
	public void testCreate() {

		final double d = -12.7545d;

		// Null unit expression
		String exceptionMsg = "";
		try {
			ValueAndUnits.createFromUnitAndDim(d, null);
		}
		catch (IllegalArgumentException ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(ValueAndUnits.UNIT_EXPRESSION_NULL, exceptionMsg);


		ValueAndUnits vu = ValueAndUnits.create(d, UnitExpression.createFromUnit(TimeUnit.DAYS));
		assertEquals(d, vu.getValue(), 1E-9);
		assertEquals(vu.getUnitExpression(), UnitExpression.createFromUnit(TimeUnit.DAYS));


		vu = ValueAndUnits.createFromUnitAndDim(d, UnitAndDim.create(LengthUnit.AU));
		assertEquals(d, vu.getValue(), 1E-9);
		assertEquals(vu.getUnitExpression(), UnitExpression.createFromUnit(LengthUnit.AU));

		vu = ValueAndUnits.createFromUnit(d, LengthUnit.M);
		assertEquals(d, vu.getValue(), 1E-9);
		assertEquals(vu.getUnitExpression(), UnitExpression.createFromUnit(LengthUnit.M));

		vu = ValueAndUnits.create(d);
		assertEquals(d, vu.getValue(), 1E-9);
		assertEquals(UnitExpression.DIMENSIONLESS, vu.getUnitExpression());
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

		// Empty, empty
		expr1 = UnitExpression.create(unitSet1);
		expr2 = UnitExpression.create(unitSet2);
		assertEquals(true, expr1.equals(expr2));

		// Single item, same expression
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		unitSet1.add(ud1);

		unitSet2.clear();
		unitSet2.add(ud1);
		expr1 = UnitExpression.create(unitSet1);
		expr2 = UnitExpression.create(unitSet2);

		assertEquals(true, expr1.equals(expr2));


		// Single item, different expression
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		unitSet1.add(ud1);

		unitSet2.clear();
		ud2 = UnitAndDim.create(unit1, dim2);
		unitSet2.add(ud2);
		expr1 = UnitExpression.create(unitSet1);
		expr2 = UnitExpression.create(unitSet2);

		assertEquals(false, expr1.equals(expr2));


		// Multiple items, different expressions
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = UnitAndDim.create(unit2, dim2);
		unitSet1.add(ud1);
		unitSet1.add(ud2);
		unitSet2.add(ud1);
		unitSet2.add(ud2);
		expr1 = UnitExpression.create(unitSet1);
		expr2 = UnitExpression.create(unitSet2);
		assertEquals(false, expr1.equals(expr2));

		// Multiple items, same expressions
		unitSet1.clear();
		unitSet2.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = UnitAndDim.create(unit1, dim1);
		unitSet1.add(ud1);
		unitSet1.add(ud2);
		unitSet2.add(ud1);
		unitSet2.add(ud2);
		expr1 = UnitExpression.create(unitSet1);
		expr2 = UnitExpression.create(unitSet2);
		assertEquals(true, expr1.equals(expr2));
	}

	@Test
	public void testSameNumberOfUnits() {
		double d = -1634.1234;

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

		// Different number units
		unitSet1.clear();
		ud1 = UnitAndDim.create(unit1, dim1);
		ud2 = UnitAndDim.create(unit2, dim2);
		unitSet1.add(ud1);
		unitSet1.add(ud2);
		expr1 = UnitExpression.create(unitSet1);
		ValueAndUnits value1 = ValueAndUnits.create(d, expr1);

		unitSet2.clear();
		unitSet2.add(ud1);
		expr2 = UnitExpression.create(unitSet2);
		ValueAndUnits value2 = ValueAndUnits.create(d, expr2);
		assertEquals(false, expr1.equals(expr2));

		String exceptionMsg = "";
		try {
			value1.add(value2);
		}
		catch (IllegalArgumentException ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(ValueAndUnits.DIFFERENT_NUMBER_UNITS, exceptionMsg);
	}

	@Test
	public void testGetUnitExpressionSize() {
		ValueAndUnits vu = ValueAndUnits.create(-12d);
		assertEquals(0, vu.getUnitExpressionSize());

		vu = ValueAndUnits.create(25.12d, UnitExpression.createFromUnit(TimeUnit.S));
		assertEquals(1, vu.getUnitExpressionSize());
	}

	@Test
	public void testGetConversionFactor() {

		ValueAndUnits cm = ValueAndUnits.create(1.5d, UnitExpression.create(UnitAndDim.create(LengthUnit.CM, 2, 3)));
		ValueAndUnits km = ValueAndUnits.create(1.5d, UnitExpression.create(UnitAndDim.create(LengthUnit.KM, 2, 3)));

		double factor = cm.getConversionFactorTo(km);
		assertEquals(Math.pow(1E-5d, 2d / 3d), factor, 1E-21);

		ValueAndUnits m = ValueAndUnits.create(1.5d, UnitExpression.create(UnitAndDim.create(LengthUnit.M, 1, 2)));
		ValueAndUnits mm = ValueAndUnits.create(-7.12d, UnitExpression.create(UnitAndDim.create(LengthUnit.MM, -2, 3)));

		double factor2 = mm.getConversionFactorTo(m);

		assertEquals(Math.pow(1E-3, -2d/3d), factor2, 1E-9);
	}

	@Test
	public void testToSameUnitsAs() {

		ValueAndUnits km = ValueAndUnits.create(1.5d, UnitExpression.create(UnitAndDim.create(LengthUnit.KM, -1, 2)));
		ValueAndUnits m = ValueAndUnits.create(-7.12d, UnitExpression.create(UnitAndDim.create(LengthUnit.M, -1, 2)));

		ValueAndUnits converted = km.toSameUnitsAs(m);

		assertEquals(1.5d * Math.pow(1000d, -1d/2d), converted.getValue(), 1E-21);
	}

	@Test
	public void testAdd() {
		ValueAndUnits km = ValueAndUnits.create(1.5d, UnitExpression.create(UnitAndDim.create(LengthUnit.KM, 3, 2)));
		ValueAndUnits cm = ValueAndUnits.create(-7.12d, UnitExpression.create(UnitAndDim.create(LengthUnit.CM, 3, 2)));

		ValueAndUnits sum = km.add(cm);

		assertEquals(Math.pow(1E-5d, 1.5d) * -7.12d + 1.5d, sum.getValue(), 1E-6);
	}

	@Test
	public void testMultiplyBy() {

		ValueAndUnits m = ValueAndUnits.create(1.5d, UnitExpression.create(UnitAndDim.create(LengthUnit.M, 1, 2)));
		ValueAndUnits mm = ValueAndUnits.create(-7.12d, UnitExpression.create(UnitAndDim.create(LengthUnit.MM, -2, 3)));

		ValueAndUnits product = m.multiplyBy(mm);

		assertEquals(UnitExpression.create(LengthUnit.M, -1, 6), product.getUnitExpression());
		assertEquals(-7.12d * Math.pow(1E-3, -2d/3d) * 1.5d, product.getValue(), 1E-6);

	}

	@Test
	public void testDivideBy() {
		ValueAndUnits m = ValueAndUnits.create(1.5d, UnitExpression.create(UnitAndDim.create(LengthUnit.M, 1, 2)));
		ValueAndUnits mm = ValueAndUnits.create(-7.12d, UnitExpression.create(UnitAndDim.create(LengthUnit.MM, -2, 3)));

		ValueAndUnits product = m.divideBy(mm);

		assertEquals(UnitExpression.create(LengthUnit.M, 7, 6), product.getUnitExpression());
		assertEquals(1.5d / (-7.12d * Math.pow(1E-3, -2d/3d)), product.getValue(), 1E-6);
	}


	@Test
	public void testPow() {
		ValueAndUnits vu = ValueAndUnits.create(1.5d,
				UnitExpression.create(UnitAndDim.create(LengthUnit.KM, 1, 2),
									UnitAndDim.create(TimeUnit.DAYS, -1, 3)));
		UnitExpression expr2 = UnitExpression.create(UnitAndDim.create(LengthUnit.KM, -5, 4),
									UnitAndDim.create(TimeUnit.DAYS, 5, 6));

		ValueAndUnits vu2 = vu.pow(-5, 2);

		assertEquals(Math.pow(1.5d, -5d/2d), vu2.getValue(), 1E-21);
		assertEquals(expr2, vu2.getUnitExpression());

	}

}
