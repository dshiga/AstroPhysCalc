package org.astrophyscalc;

public class UnitSpinnerItem {

	public static final UnitSpinnerItem[] ORBIT_MASS = {
        UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_EARTH), "Earths"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_JUP), "Jupiters"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_SUN), "Suns")
	};

	public static final UnitSpinnerItem[] ORBIT_RADIUS = {
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.KM), "km"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.AU), "AU"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.LY), "ly")
	};

	public static final UnitSpinnerItem[] ORBIT_PERIOD = {
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.S), "s"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.MIN), "minutes"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.HR), "hours"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.DAYS), "days"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.YEARS), "years")
	};


	// Kinetic energy

	public static final UnitSpinnerItem[] KE_MASS = {
        UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.G), "g"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.KG), "kg")
    };

	public static final UnitSpinnerItem[] KE_ENERGY = {
        UnitSpinnerItem.create(UnitExpression.create(UnitAndDim.create(MassUnit.KG),
        		UnitAndDim.create(LengthUnit.M, 2),
        		UnitAndDim.create(TimeUnit.S, -2)), "J")
    };

	public static final UnitSpinnerItem[] KE_VELOCITY = {
        UnitSpinnerItem.create(UnitExpression.create(UnitAndDim.create(LengthUnit.M),
        		UnitAndDim.create(TimeUnit.S, -1)), "m/s")
    };


	// Flux

	public static final UnitSpinnerItem[] FLUX_POWER = {
        UnitSpinnerItem.create(UnitExpression.create(
        		UnitAndDim.create(MassUnit.KG),
        		UnitAndDim.create(LengthUnit.M, 2),
        		UnitAndDim.create(TimeUnit.S, -3)), "W"),
        UnitSpinnerItem.create(UnitExpression.create(
        		UnitAndDim.create(MassUnit.T),
        		UnitAndDim.create(LengthUnit.M, 2),
        		UnitAndDim.create(TimeUnit.S, -3)), "KW"),
        UnitSpinnerItem.create(UnitExpression.create(
        		UnitAndDim.create(MassUnit.KG),
        		UnitAndDim.create(LengthUnit.KM, 2),
        		UnitAndDim.create(TimeUnit.S, -3)), "MW"),
        UnitSpinnerItem.create(Constants.L_SUN, "Solar")
	};

	public static final UnitSpinnerItem[] FLUX_DISTANCE = {
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.M), "m"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.KM), "km"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.AU), "AU"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.LY), "ly"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.KLY), "kly"),
        UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.MLY), "mly")
    };

	public static final UnitSpinnerItem[] FLUX = {
        UnitSpinnerItem.create(UnitExpression.create(
        		UnitAndDim.create(MassUnit.KG),
        		UnitAndDim.create(TimeUnit.S, -3)), "W/m^2"),
        UnitSpinnerItem.create(Constants.FS_EARTH, "Sun_Earth")
    };


	private final ValueAndUnits vu;
	private final String label;

	public static UnitSpinnerItem create(final UnitExpression expr, final String label) {
		return new UnitSpinnerItem(1d, expr, label);
	}

	public static UnitSpinnerItem create(final double value, final UnitExpression expr, final String label) {
		return new UnitSpinnerItem(value, expr, label);
	}

	public static UnitSpinnerItem create(final ValueAndUnits vu, final String label) {
		return new UnitSpinnerItem(vu, label);
	}

	private UnitSpinnerItem(final double value, final UnitExpression expr, final String label) {
		this.vu = ValueAndUnits.create(value, expr);
		this.label = label;
	}

	private UnitSpinnerItem(final ValueAndUnits vu, final String label) {
		this.vu = vu;
		this.label = label;
	}

	public UnitExpression getUnitExpression() {
		return vu.getUnitExpression();
	}

	public ValueAndUnits getValueAndUnits() {
		return vu;
	}

	@Override
	public String toString() {
		return label;
	}

}
