package org.astrophyscalc;

import java.util.ArrayList;
import java.util.List;


public class CalcRow {

	private static final ValueAndUnits G = ValueAndUnits.create(6.67E-11d, UnitExpression.create(
			UnitAndDim.create(LengthUnit.M, 3), UnitAndDim.create(MassUnit.KG, -1), UnitAndDim.create(TimeUnit.S, -2)));

	private static class OrbitMassCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits r = vu[0];
			final ValueAndUnits T = vu[1];
			return ValueAndUnits.create(4d * Math.pow(Math.PI, 2d)).multiplyBy(r.pow(3)).divideBy(G.multiplyBy(T.pow(2)));
		}
	}

	private static class OrbitRadiusCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits T = vu[0];
			final ValueAndUnits m = vu[1];
			return G.multiplyBy(m.multiplyBy(T.pow(2))).divideBy(ValueAndUnits.create(4d * Math.pow(Math.PI, 2))).pow(1, 3);
		}
	}

	private static class OrbitPeriodCalculator implements Calculator {
		@Override
		public ValueAndUnits calculate(final ValueAndUnits ... vu) {
			final ValueAndUnits r = vu[0];
			final ValueAndUnits m = vu[1];
			return ValueAndUnits.create(2d * Math.PI).multiplyBy((r.pow(3).divideBy(G.multiplyBy(m))).pow(1, 2));
		}
	}

	public static final CalcRow ORBIT_MASS = CalcRow.create(
			R.string.massLabel, new OrbitMassCalculator(), UnitSelector.ORBIT_MASS_SELECTOR,
			UnitSpinnerItem.ORBIT_MASS, 2);

	public static final CalcRow ORBIT_RADIUS = CalcRow.create(
			R.string.radiusLabel, new OrbitRadiusCalculator(), UnitSelector.ORBIT_RADIUS_SELECTOR,
			UnitSpinnerItem.ORBIT_RADIUS, 1);

	public static final CalcRow ORBIT_PERIOD = CalcRow.create(
			R.string.periodLabel, new OrbitPeriodCalculator(), UnitSelector.ORBIT_PERIOD_SELECTOR,
			UnitSpinnerItem.ORBIT_PERIOD, 4);

	private final Calculator calculator;
	private final UnitSelector unitSelector;
	private final int textLabelId;
	private final List<UnitSpinnerItem> spinnerItems;
	private final int defaultSpinnerPos;

	public static CalcRow create(final int textLabelId, final Calculator calculator, final UnitSelector unitSelector,
			UnitSpinnerItem[] spinnerItems, final int defaultSpinnerPos) {
		final List<UnitSpinnerItem> spinnerItemList = new ArrayList<UnitSpinnerItem>();
		for (UnitSpinnerItem item: spinnerItems) {
			spinnerItemList.add(item);
		}
		return new CalcRow(textLabelId, calculator, unitSelector, spinnerItemList, defaultSpinnerPos);
	}

	private CalcRow(final int textLabelId, final Calculator calculator, final UnitSelector unitSelector,
			final List<UnitSpinnerItem> spinnerItems, final int defaultSpinnerPos) {
		this.textLabelId = textLabelId;
		this.calculator = calculator;
		this.unitSelector = unitSelector;
		this.spinnerItems = spinnerItems;
		this.defaultSpinnerPos = defaultSpinnerPos;
	}

	public int getTextLabel() {
		return textLabelId;
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

	public UnitExpression getPreferredUnits(final ValueAndUnits vu) {
		return getUnitSelector().getPreferredUnits(vu);
	}

}
