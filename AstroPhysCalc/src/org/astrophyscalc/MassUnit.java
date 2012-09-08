package org.astrophyscalc;

public enum MassUnit implements Unit {

	M_E("Me", 9.10938188E-31d),
	M_P("Mp", 1.67262158E-27d),
	NG("ng", 1E-9d),
	MG("mg", 1E-6d),
	G("g", 1E-3d),
	KG("kg", 1d),
	M_EARTH("M_Earth", 5.97219E24d),
	M_JUP("M_Jup", 1.89813E27d),
	M_SUN("M_Sun", 2E30d);

	private final String name;
	private final double ratioToBase;
	private final static MassUnit base = KG;

	MassUnit(final String name, final double ratioToBase) {
		this.name = name;
		this.ratioToBase = ratioToBase;
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
	public String getName() {
		return name;
	}

	@Override
	public boolean isSameBaseAs(Unit unit) {
		return unit instanceof MassUnit;
	}

}