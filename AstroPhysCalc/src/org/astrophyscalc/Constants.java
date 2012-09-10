package org.astrophyscalc;

public class Constants {

	public static final ValueAndUnits L_SUN =
		ValueAndUnits.create(3.839E26d,
			UnitExpression.create(
					UnitAndDim.create(MassUnit.KG),
					UnitAndDim.create(LengthUnit.M, 2),
					UnitAndDim.create(TimeUnit.S, -3)));

	public static final ValueAndUnits G =
		ValueAndUnits.create(6.67E-11d,
			UnitExpression.create(
					UnitAndDim.create(LengthUnit.M, 3),
					UnitAndDim.create(MassUnit.KG, -1),
					UnitAndDim.create(TimeUnit.S, -2)));

	public static final ValueAndUnits FS_EARTH =
		ValueAndUnits.create(1365d, UnitExpression.create(
				UnitAndDim.create(MassUnit.KG),
				UnitAndDim.create(TimeUnit.S, -3)));

}
