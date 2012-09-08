package org.astrophyscalc;

import org.junit.Assert;
import org.junit.Test;

public class FractionTest extends Assert {

	@Test
	public void testCreate() {
		int num = -5;
		int den = 0;

		// Denominator of zero
		String exceptionMsg = "";
		try {
			Fraction.create(num, den);
		}
		catch (final Exception ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(Fraction.DENOMINATOR_ZERO, exceptionMsg);

		// Positive, negative
		Fraction fraction = Fraction.create(5, -8);
		assertEquals(-5, fraction.getNumerator());
		assertEquals(8, fraction.getDenominator());

		// Positive, positive
		fraction = Fraction.create(5, 8);
		assertEquals(5, fraction.getNumerator());
		assertEquals(8, fraction.getDenominator());

		// Negative, positive
		fraction = Fraction.create(-5, 8);
		assertEquals(-5, fraction.getNumerator());
		assertEquals(8, fraction.getDenominator());

		// Negative, negative
		fraction = Fraction.create(-5, -8);
		assertEquals(5, fraction.getNumerator());
		assertEquals(8, fraction.getDenominator());
	}

	@Test
	public void testIsOne() {
		// 1, 1
		Fraction fraction = Fraction.create(1, 1);
		assertEquals(true, fraction.isOne());

		// 2, 2
		fraction = Fraction.create(2, 2);
		assertEquals(true, fraction.isOne());

		// -5, -5
		fraction = Fraction.create(-5, -5);
		assertEquals(true, fraction.isOne());

		// 1, -1
		fraction = Fraction.create(1, -1);
		assertEquals(false, fraction.isOne());
	}

	@Test
	public void testIsZero() {
		// 0, 5
		Fraction fraction = Fraction.create(0, 5);
		assertEquals(true, fraction.isZero());

		// -0, 3
		fraction = Fraction.create(-0, 3);
		assertEquals(true, fraction.isZero());

		// -1, 3
		fraction = Fraction.create(-1, 3);
		assertEquals(false, fraction.isZero());
	}

	@Test
	public void testEquals() {
		int num1 = 1;
		int den1 = 2;

		int num2 = 5;
		int den2 = -7;

		Fraction fraction1;
		Fraction fraction2;

		// Fraction, null
		fraction1 = Fraction.create(num1, den1);
		assertEquals(false, fraction1.equals(null));

		// Fraction, object
		fraction1 = Fraction.create(num1, den1);
		assertEquals(false, fraction1.equals(new Object()));

		// equal, equal
		fraction1 = Fraction.create(num1, den1);
		fraction2 = Fraction.create(num1, den1);
		assertEquals(true, fraction1.equals(fraction2));

		// equal, not equal
		fraction1 = Fraction.create(num1, den1);
		fraction2 = Fraction.create(num1, den2);
		assertEquals(false, fraction1.equals(fraction2));

		// not equal, equal
		fraction1 = Fraction.create(num1, den1);
		fraction2 = Fraction.create(num2, den1);
		assertEquals(false, fraction1.equals(fraction2));

		// not equal, not equal
		fraction1 = Fraction.create(num1, den1);
		fraction2 = Fraction.create(num2, den2);
		assertEquals(false, fraction1.equals(fraction2));

		// One, one
		fraction1 = Fraction.ONE;
		fraction2 = Fraction.ONE;
		assertEquals(true, fraction1.equals(fraction2));

		// One, One
		fraction1 = Fraction.ONE;
		fraction2 = Fraction.create(-1, -1);
		assertEquals(true, fraction1.equals(fraction2));

		// One, fraction not one
		fraction1 = Fraction.ONE;
		fraction2 = Fraction.create(1, 7);
		assertEquals(false, fraction1.equals(fraction2));
	}

	@Test
	public void testToDouble() {
		int num1 = 1;
		int den1 = 2;

		// Fraction, null
		Fraction fraction1 = Fraction.create(num1, den1);
		assertEquals(1d/2d, fraction1.toDouble(), 0.0000000001d);
	}

	@Test
	public void testAdd() {
		int num1 = 1;
		int den1 = 2;

		int num2 = 5;
		int den2 = -7;

		Fraction fraction1;
		Fraction fraction2;
		Fraction fraction3;

		// Fraction, null
		fraction1 = Fraction.create(num1, den1);
		fraction2 = Fraction.create(num2, den2);
		fraction3 = Fraction.create(-3, 14);
		assertEquals(true, fraction3.equals(fraction1.add(fraction2)));
	}

	@Test
	public void testNegative() {
		int num1 = 1;
		int den1 = 2;

		int num2 = 5;
		int den2 = -7;

		Fraction fraction1;
		Fraction fraction2;

		// Fraction, null
		fraction1 = Fraction.create(num1, den1);
		assertEquals(true, Fraction.create(-num1, den1).equals(fraction1.negative()));

		fraction2 = Fraction.create(num2, den2);
		assertEquals(true, Fraction.create(-num2, den2).equals(fraction2.negative()));
	}

	@Test
	public void testSubtract() {
		int num1 = 1;
		int den1 = 2;

		int num2 = 5;
		int den2 = -7;

		Fraction fraction1;
		Fraction fraction2;
		Fraction fraction3;

		// Fraction, null
		fraction1 = Fraction.create(num1, den1);
		fraction2 = Fraction.create(num2, den2);
		fraction3 = Fraction.create(17, 14);
		assertEquals(true, fraction3.equals(fraction1.subtract(fraction2)));
	}

	@Test
	public void testPow() {
		int num1 = 1;
		int den1 = 2;

		int num2 = 5;
		int den2 = -7;

		Fraction fraction1;
		Fraction fraction2;
		Fraction exponent = Fraction.create(3, 7);

		// Fraction, null
		fraction1 = Fraction.create(num1, den1);
		assertEquals(true, Fraction.create(3, 14).equals(fraction1.pow(exponent)));

		fraction2 = Fraction.create(num2, den2);
		assertEquals(true, Fraction.create(-15, 49).equals(fraction2.pow(exponent)));
	}

}
