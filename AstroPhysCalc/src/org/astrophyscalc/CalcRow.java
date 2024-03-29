package org.astrophyscalc;

import java.util.ArrayList;
import java.util.List;


public class CalcRow {

	private static class OrbitMassCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits r = vu[0];
			final ValueAndUnits T = vu[1];
			return ValueAndUnits.create(4d * Math.pow(Math.PI, 2d)).multiplyBy(r.pow(3)).divideBy(Constants.G.multiplyBy(T.pow(2)));
		}
	}

	private static class OrbitRadiusCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits T = vu[0];
			final ValueAndUnits m = vu[1];
			return Constants.G.multiplyBy(m.multiplyBy(T.pow(2))).divideBy(ValueAndUnits.create(4d * Math.pow(Math.PI, 2))).pow(1, 3);
		}
	}

	private static class OrbitPeriodCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits r = vu[0];
			final ValueAndUnits m = vu[1];
			return ValueAndUnits.create(2d * Math.PI).multiplyBy((r.pow(3).divideBy(Constants.G.multiplyBy(m))).pow(1, 2));
		}
	}

	public static final CalcRow ORBIT_MASS = CalcRow.create(
			R.id.label1, R.string.massLabel, R.id.text1, R.id.units1, new OrbitMassCalculator(),
			UnitSelector.ORBIT_MASS_SELECTOR, UnitSpinnerItem.ORBIT_MASS, 2);

	public static final CalcRow ORBIT_RADIUS = CalcRow.create(
			R.id.label2, R.string.radiusLabel, R.id.text2, R.id.units2, new OrbitRadiusCalculator(),
			UnitSelector.ORBIT_RADIUS_SELECTOR, UnitSpinnerItem.ORBIT_RADIUS, 1);

	public static final CalcRow ORBIT_PERIOD = CalcRow.create(
			R.id.label3, R.string.periodLabel, R.id.text3, R.id.units3, new OrbitPeriodCalculator(),
			UnitSelector.ORBIT_PERIOD_SELECTOR, UnitSpinnerItem.ORBIT_PERIOD, 4);


	// Kinetic energy

	private static class KEMassCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits E = vu[0];
			final ValueAndUnits v = vu[1];
			return ValueAndUnits.create(2d).multiplyBy(E).divideBy(v.pow(2));
		}
	}

	private static class KEEnergyCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits m = vu[0];
			final ValueAndUnits v = vu[1];
			return ValueAndUnits.create(1d/2d).multiplyBy(m).multiplyBy(v.pow(2));
		}
	}

	private static class KEVelocityCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits m = vu[0];
			final ValueAndUnits E = vu[1];
			return (ValueAndUnits.create(2d).multiplyBy(E).divideBy(m)).pow(1, 2);
		}
	}

	public static final CalcRow KE_MASS = CalcRow.create(
			R.id.label1, R.string.massLabel, R.id.text1, R.id.units1, new KEMassCalculator(),
			UnitSelector.KE_MASS_SELECTOR, UnitSpinnerItem.KE_MASS, 1);

	public static final CalcRow KE_ENERGY = CalcRow.create(
			R.id.label2, R.string.energyLabel, R.id.text2, R.id.units2, new KEEnergyCalculator(),
			UnitSelector.KE_ENERGY_SELECTOR, UnitSpinnerItem.KE_ENERGY, 0);

	public static final CalcRow KE_VELOCITY = CalcRow.create(
			R.id.label3, R.string.velocityLabel, R.id.text3, R.id.units3, new KEVelocityCalculator(),
			UnitSelector.KE_VELOCITY_SELECTOR, UnitSpinnerItem.KE_VELOCITY, 0);


	// Brightness

	private static class FluxPowerCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits d = vu[0];
			final ValueAndUnits f = vu[1];
			return ValueAndUnits.create(4d * Math.PI).multiplyBy(d.pow(2)).multiplyBy(f);
		}
	}

	private static class FluxDistanceCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits p = vu[0];
			final ValueAndUnits f = vu[1];
			return (p.divideBy(ValueAndUnits.create(4d * Math.PI).multiplyBy(f))).pow(1, 2);
		}
	}

	private static class FluxCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits p = vu[0];
			final ValueAndUnits d = vu[1];
			return p.divideBy(ValueAndUnits.create(4d * Math.PI).multiplyBy(d.pow(2)));
		}
	}


	public static final CalcRow FLUX_POWER = CalcRow.create(
			R.id.label1, R.string.powerLabel, R.id.text1, R.id.units1, new FluxPowerCalculator(),
			UnitSelector.FLUX_POWER_SELECTOR, UnitSpinnerItem.FLUX_POWER, 3);

	public static final CalcRow FLUX_DISTANCE = CalcRow.create(
			R.id.label2, R.string.distanceLabel, R.id.text2, R.id.units2, new FluxDistanceCalculator(),
			UnitSelector.FLUX_DISTANCE_SELECTOR, UnitSpinnerItem.FLUX_DISTANCE, 2);

	public static final CalcRow FLUX = CalcRow.create(
			R.id.label3, R.string.fluxLabel, R.id.text3, R.id.units3, new FluxCalculator(),
			UnitSelector.FLUX_SELECTOR, UnitSpinnerItem.FLUX, 1);



	private final Calculator calculator;
	private final UnitSelector unitSelector;
	private final int labelStringId;
	private final int labelId;
	private final int textId;
	private final int spinnerId;
	private final List<UnitSpinnerItem> spinnerItems;
	private final int defaultSpinnerPos;

	public static CalcRow create(final int labelId, final int labelStringId, final int textId, final int spinnerId,
			final Calculator calculator, final UnitSelector unitSelector,
			UnitSpinnerItem[] spinnerItems, final int defaultSpinnerPos) {
		final List<UnitSpinnerItem> spinnerItemList = new ArrayList<UnitSpinnerItem>();
		for (UnitSpinnerItem item: spinnerItems) {
			spinnerItemList.add(item);
		}
		return new CalcRow(labelId, labelStringId, textId, spinnerId, calculator, unitSelector,
				spinnerItemList, defaultSpinnerPos);
	}

	private CalcRow(final int labelId, final int labelStringId, final int textId, final int spinnerId,
			final Calculator calculator, final UnitSelector unitSelector,
			final List<UnitSpinnerItem> spinnerItems, final int defaultSpinnerPos) {
		this.labelId = labelId;
		this.labelStringId = labelStringId;
		this.textId = textId;
		this.spinnerId = spinnerId;
		this.calculator = calculator;
		this.unitSelector = unitSelector;
		this.spinnerItems = spinnerItems;
		this.defaultSpinnerPos = defaultSpinnerPos;
	}

	public int getLabelId() {
		return labelId;
	}

	public int getLabelStringId() {
		return labelStringId;
	}

	public int getTextId() {
		return textId;
	}

	public int getSpinnerId() {
		return spinnerId;
	}

	private Calculator getCalculator() {
		return calculator;
	}

	private UnitSelector getUnitSelector() {
		return unitSelector;
	}

	public List<UnitSpinnerItem> getSpinnerItems() {
		return spinnerItems;
	}

	public int getDefaultSpinnerPos() {
		return defaultSpinnerPos;
	}

	public ValueAndUnits calculate(final ValueAndUnits ... vu) {
		return getCalculator().calculate(vu);
	}

	public ValueAndUnits getPreferredUnits(final ValueAndUnits vu) {
		return getUnitSelector().getPreferredUnits(vu);
	}

}
