package org.astrophyscalc;

public interface Unit {

	double inBaseUnits();

	Unit getBase();

	String getName();

	boolean isSameBaseAs(Unit unit);

}
