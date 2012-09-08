package org.astrophyscalc;

public class Dimension {

	private final Fraction fraction;

	public static Dimension ONE = new Dimension(Fraction.ONE);
	public static Dimension ZERO = new Dimension(Fraction.ZERO);

	static final String DENOMINATOR_ZERO = "Denominator cannot be zero";

	public static Dimension create(final int numerator, final int denominator) {
		if (denominator == 0) {
			throw new IllegalArgumentException(DENOMINATOR_ZERO);
		}

		Fraction fraction = Fraction.create(numerator, denominator);
		if(fraction.isOne()) {
			return ONE;
		}
		if(fraction.isZero()) {
			return ZERO;
		}

		return new Dimension(fraction);
	}

	private Dimension(final Fraction fraction) {
		this.fraction = fraction;
	}

	public int getNumerator() {
		return fraction.getNumerator();
	}

	public int getDenominator() {
		return fraction.getDenominator();
	}

	public boolean isOne() {
		return fraction.isOne();
	}

	public boolean isZero() {
		return fraction.isZero();
	}

	public double toDouble() {
		return fraction.toDouble();
	}

	public Dimension add(final Dimension dim2) {
		// get sum of the two fractions
		final Fraction fraction2 = Fraction.create(dim2.getNumerator(), dim2.getDenominator());

		final Fraction newFraction = fraction.add(fraction2);

		final Dimension newDim = Dimension.create(newFraction.getNumerator(), newFraction.getDenominator());
		return newDim;
	}

	public Dimension subtract(final Dimension dim2) {
		final Dimension negDim2 = dim2.negative();
		return add(negDim2);
	}

	/**
	 * Raises this dimension to the given power.
	 *
	 * @param exponent
	 * @return
	 */
	public Dimension pow(final Dimension exponent) {
		final Fraction newFraction = fraction.pow(exponent.toFraction());
		return Dimension.create(newFraction.getNumerator(), newFraction.getDenominator());
	}

	public Dimension negative() {
		return Dimension.create(-getNumerator(), getDenominator());
	}

	private Fraction toFraction() {
		return Fraction.create(getNumerator(), getDenominator());
	}

	@Override
	public boolean equals(final Object object) {

		if (!(object instanceof Dimension)) {
			return false;
		}

		Dimension dim = (Dimension) object;

		return fraction.equals(dim.fraction);
	}

	@Override public int hashCode() {
		return fraction.getNumerator() ^ fraction.getDenominator();
	}

	@Override public String toString() {
		return Dimension.class.getSimpleName() + ": " + fraction.getNumerator() + ", " + fraction.getDenominator();
	}

}
