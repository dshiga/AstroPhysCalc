package org.astrophyscalc;

public enum LengthUnit implements Unit {

	A("A", 1E-10d),
	NM("nm", 1E-9d),
	MIC_M("mic_m", 1E-6d),
	MM("mm", 1E-3d),
	CM("cm", 1E-2d),
	M("m", 1d),
	KM("km", 1E3d),
	AU("AU", 1.49598E11d),
	LY("ly", 9.46E15),
	KLY("kly", 9.46E18),
	MLY("mly", 9.46E21),
	GLY("gly", 9.46E24);

	private final String name;
	private final double ratioToBase;
	private final static LengthUnit base = M;

	LengthUnit(final String name, final double ratioToBase) {
		this.name = name;
		this.ratioToBase = ratioToBase;
	}

	public static LengthUnit getUnit(final String name) {
		LengthUnit[] units = LengthUnit.values();
		for (LengthUnit unit : units) {
			if (unit.getName().equals(name)) {
				return unit;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double inBaseUnits() {
		return ratioToBase;
	}

	@Override
	public Unit getBase() {
		return base;
	}

	@Override
	public boolean isSameBaseAs(Unit unit) {
		return unit instanceof LengthUnit;
	}

}
