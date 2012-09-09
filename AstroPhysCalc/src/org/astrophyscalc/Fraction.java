package org.astrophyscalc;

public class Fraction {

	private final int numerator;
	private final int denominator;

	public static Fraction ONE = new Fraction(1, 1);
	public static Fraction ZERO = new Fraction(0, 1);

	static final String DENOMINATOR_ZERO = "Denominator cannot be zero";

	public static Fraction create(final int numerator, final int denominator) {
		int newNumerator = numerator;
		int newDenominator = denominator;
		if (denominator == 0) {
			throw new IllegalArgumentException(DENOMINATOR_ZERO);
		}

		// Force denominator to always be positive
		if (denominator < 0) {
			newDenominator *= -1;
			newNumerator *= -1;
		}

		if (newNumerator == newDenominator) {
			return ONE;
		}
		if (newNumerator == -newDenominator) {
			return new Fraction(-1, 1);
		}
		if (newNumerator == 0) {
			return ZERO;
		}

		return new Fraction(newNumerator, newDenominator);
	}

	private Fraction(final int numerator, final int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public int getNumerator() {
		return numerator;
	}

	public int getDenominator() {
		return denominator;
	}

	public boolean isOne() {
		return this == ONE;
	}

	public boolean isZero() {
		return this == ZERO;
	}

	public double toDouble() {
		return (double) numerator / (double) denominator;
	}

	public Fraction add(final Fraction fraction2) {
		// TODO: factor out common factors
		final int newDenominator = getDenominator() * fraction2.getDenominator();

		final int newNumerator1 = getNumerator() * fraction2.getDenominator();
		final int newNumerator2 = fraction2.getNumerator() * getDenominator();

		final Fraction newFraction = Fraction.create(newNumerator1 + newNumerator2, newDenominator);
		return newFraction;
	}

	public Fraction subtract(final Fraction fraction2) {
		return add(fraction2.negative());
	}

	public Fraction pow(final Fraction exponent) {
		final int newNumerator = getNumerator() * exponent.getNumerator();
		final int newDenominator = getDenominator() * exponent.getDenominator();

		return Fraction.create(newNumerator, newDenominator);
	}

	public Fraction negative() {
		return Fraction.create(-getNumerator(), getDenominator());
	}

	@Override
	public boolean equals(final Object object) {
		if (!(object instanceof Fraction)) {
			return false;
		}

		Fraction fraction = (Fraction) object;

		return numerator == fraction.getNumerator() && denominator == fraction.getDenominator();
	}

	@Override
	public String toString() {
		return Fraction.class.getSimpleName() + ": " + numerator + ", " + denominator;
	}

}
