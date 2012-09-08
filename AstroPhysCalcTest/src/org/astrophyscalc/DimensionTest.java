package org.astrophyscalc;

import junit.framework.Assert;

import org.junit.Test;

public class DimensionTest extends Assert {

	@Test
	public void testCreate() {
		int numerator = 4;
		int denominator = 7;

		Dimension dim = Dimension.create(numerator, denominator);

		assertEquals(numerator, dim.getNumerator());
		assertEquals(denominator, dim.getDenominator());

		String exceptionMsg = "";
		try {
			Dimension dim2 = Dimension.create(3, 0);
		}
		catch (IllegalArgumentException ex) {
			exceptionMsg = ex.getMessage();
		}

		assertEquals(Dimension.DENOMINATOR_ZERO, exceptionMsg);
	}

	@Test
	public void testEquals() {
		int num1 = 4;
		int den1 = 9;

		int num2 = 3;
		int den2 = 7;

		Dimension dim1;
		Dimension dim2;

		// dim, null
		dim1 = Dimension.create(num1, den1);
		dim2 = null;
		assertEquals(false, dim1.equals(dim2));

		//dim, object
		Object object = new Object();
		assertEquals(false, dim1.equals(object));

		//dim, dim (but reference is of type Object)
		object = Dimension.create(num1, den1);
		assertEquals(true, dim1.equals(object));

		// equal, equal
		dim1 = Dimension.create(num1, den1);
		dim2 = Dimension.create(num1, den1);
		assertEquals(true, dim1.equals(dim2));

		// equal, not equal
		dim1 = Dimension.create(num1, den1);
		dim2 = Dimension.create(num1, den2);
		assertEquals(false, dim1.equals(dim2));

		// not equal, equal
		dim1 = Dimension.create(num1, den1);
		dim2 = Dimension.create(num2, den1);
		assertEquals(false, dim1.equals(dim2));

		// not equal, not equal
		dim1 = Dimension.create(num1, den1);
		dim2 = Dimension.create(num2, den2);
		assertEquals(false, dim1.equals(dim2));
	}

	@Test
	public void testIsOne() {
		int numerator = 1;
		int denominator = 1;

		Dimension dim = Dimension.create(numerator, denominator);

		assertEquals(true, dim.isOne());
	}

	@Test
	public void testIsZero() {
		int numerator = 0;
		int denominator = -12;

		Dimension dim = Dimension.create(numerator, denominator);

		assertEquals(true, dim.isZero());
	}

	@Test
	public void testNegative() {
		int numerator = -5;
		int denominator = 1;

		Dimension dim = Dimension.create(numerator, denominator);

		assertEquals(true, dim.negative().equals(Dimension.create(-numerator, denominator)));
	}

	@Test
	public void testToDouble() {
		int numerator = 3;
		int denominator = -12;

		Dimension dim = Dimension.create(numerator, denominator);

		assertEquals(-1d/4d, dim.toDouble(), 1E-9d);
	}

	@Test
	public void testPow() {
		int numerator = 2;
		int denominator = 6;

		Dimension dim = Dimension.create(numerator, denominator);

		assertEquals(Dimension.create(6, 24), dim.pow(Dimension.create(3, 4)));
	}

	@Test
	public void testAdd() {
		Dimension dim1 = Dimension.create(1, 3);
		Dimension dim2 = Dimension.create(2, 4);

		assertEquals(Dimension.create(10, 12), dim1.add(dim2));
	}

	@Test
	public void testSubtract() {
		Dimension dim1 = Dimension.create(1, 3);
		Dimension dim2 = Dimension.create(3, 9);

		assertEquals(Dimension.ZERO, dim1.subtract(dim2));
	}

}
