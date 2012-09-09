package org.astrophyscalc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AstroPhysCalcActivity extends Activity {

	final ValueAndUnits G = ValueAndUnits.create(6.67E-11d, UnitExpression.create(
			UnitAndDim.create(LengthUnit.M, 3), UnitAndDim.create(MassUnit.KG, -1), UnitAndDim.create(TimeUnit.S, -2)));
	final UnitSelector radiusSelector = UnitSelector.create(
			UnitSelectionRule.create(UnitExpression.createFromUnit(LengthUnit.LY)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(1E6d, LengthUnit.KM), UnitExpression.createFromUnit(LengthUnit.AU)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(LengthUnit.KM)));

	final UnitSelector massSelector = UnitSelector.create(
			UnitSelectionRule.create(UnitExpression.createFromUnit(MassUnit.M_EARTH)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(0.1d, MassUnit.M_JUP), UnitExpression.createFromUnit(MassUnit.M_JUP)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(0.01d, MassUnit.M_SUN), UnitExpression.createFromUnit(MassUnit.M_SUN)));

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        OnClickListener periodListener = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcPeriodClicked(v);
        	}
        };
        Button periodBtn = (Button) findViewById(R.id.periodBtn);
        periodBtn.setOnClickListener(periodListener);

        OnClickListener radiusListener = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcRadiusClicked(v);
        	}
        };
        Button radiusButton = (Button) findViewById(R.id.radiusBtn);
        radiusButton.setOnClickListener(radiusListener);

        OnClickListener massListener = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcMassClicked(v);
        	}
        };
        Button massButton = (Button) findViewById(R.id.massBtn);
        massButton.setOnClickListener(massListener);

        Spinner massSpinner = (Spinner) findViewById(R.id.massUnits);
		massSpinner.setSelection(2);

        OnItemSelectedListener massUnitsListener = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onMassUnitChanged(adapterView, position);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };
        massSpinner.setOnItemSelectedListener(massUnitsListener);

        Spinner radiusSpinner = (Spinner) findViewById(R.id.radiusUnits);
		radiusSpinner.setSelection(1);

        OnItemSelectedListener radiusUnitsListener = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onRadiusUnitChanged(adapterView, position);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };
        radiusSpinner.setOnItemSelectedListener(radiusUnitsListener);

        Spinner periodSpinner = (Spinner) findViewById(R.id.periodUnits);
        periodSpinner.setSelection(4);

        OnItemSelectedListener periodUnitsListener = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onPeriodUnitChanged(adapterView, position);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };
        periodSpinner.setOnItemSelectedListener(periodUnitsListener);
    }

	Double getValueFromTextView(final int textViewId) {
		TextView text1 = (TextView) findViewById(textViewId);
		String textValue = (String) text1.getText().toString();
		return getDouble(textValue);
	}

	String getStringFromSpinner(final int spinnerId) {
		Spinner spinner1 = (Spinner) findViewById(spinnerId);
		return (String) spinner1.getSelectedItem();
	}

	ValueAndUnits getPeriod() {
		// Get input values
		Double massValue = getValueFromTextView(R.id.mass);
		Double radiusValue = getValueFromTextView(R.id.radius);
		if (!hasValue(radiusValue) || !hasValue(massValue)) {
			return null;
		}

		// Get input units
		String radiusUnitsText = getStringFromSpinner(R.id.radiusUnits);
		final LengthUnit radiusUnit = LengthUnit.getUnit(radiusUnitsText);
		String massUnitsText = getStringFromSpinner(R.id.massUnits);
		final MassUnit massUnit = MassUnit.getUnit(massUnitsText);
		if (massUnit == null || radiusUnit == null) {
			return null;
		}

		// Inputs
		final ValueAndUnits radius = ValueAndUnits.createFromUnit(radiusValue, radiusUnit);
		final ValueAndUnits mass = ValueAndUnits.createFromUnit(massValue, massUnit);

		// Calculate period
		return calculatePeriod(radius, mass);
	}

	ValueAndUnits getRadius() {
		// Get input values
		Double massValue = getValueFromTextView(R.id.mass);
		Double periodValue = getValueFromTextView(R.id.period);
		if (!hasValue(periodValue) || !hasValue(massValue)) {
			return null;
		}

		// Get input units
		String periodUnitsText = getStringFromSpinner(R.id.periodUnits);
		final TimeUnit periodUnit = TimeUnit.getUnit(periodUnitsText);
		String massUnitsText = getStringFromSpinner(R.id.massUnits);
		final MassUnit massUnit = MassUnit.getUnit(massUnitsText);
		if (massUnit == null || periodUnit == null) {
			return null;
		}

		// Inputs
		final ValueAndUnits period = ValueAndUnits.createFromUnit(periodValue, periodUnit);
		final ValueAndUnits mass = ValueAndUnits.createFromUnit(massValue, massUnit);

		// Calculate radius
		return calculateRadius(period, mass);
	}


	ValueAndUnits getMass() {
		// Get input values
		Double periodValue = getValueFromTextView(R.id.period);
		Double radiusValue = getValueFromTextView(R.id.radius);
		if (!hasValue(periodValue) || !hasValue(radiusValue)) {
			return null;
		}

		// Get input units
		String radiusUnitsText = getStringFromSpinner(R.id.radiusUnits);
		final LengthUnit radiusUnit = LengthUnit.getUnit(radiusUnitsText);
		String periodUnitsText = getStringFromSpinner(R.id.periodUnits);
		final TimeUnit periodUnit = TimeUnit.getUnit(periodUnitsText);
		if (radiusUnit == null || periodUnit == null) {
			return null;
		}

		// Inputs
		final ValueAndUnits period = ValueAndUnits.createFromUnit(periodValue, periodUnit);
		final ValueAndUnits radius = ValueAndUnits.createFromUnit(radiusValue, radiusUnit);

		// Calculate radius
		return calculateMass(radius, period);
	}

	void onCalcMassClicked(View v) {
		ValueAndUnits mass = getMass();
		if (mass == null) {
			return;
		}

		// Convert to preferred units
		mass = mass.toSameUnitsAs(massSelector.getPreferredUnits(mass));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.mass);
		setText(text3, mass.getValue());

		// Set units spinner
		final String unitName = mass.getUnitName();
		Spinner spinner1 = (Spinner) findViewById(R.id.massUnits);
		for (int i = 0; i < spinner1.getCount(); i++) {
			String s = (String) spinner1.getItemAtPosition(i);
			if (unitName.equals(s)) {
				spinner1.setSelection(i);
				break;
			}
		}
	}

	void onMassUnitChanged(final AdapterView<? extends Adapter> adapterView, final int position) {
		ValueAndUnits mass = getMass();
		if (mass == null) {
			return;
		}

		// Convert to units in spinner
		final String unitName = (String) adapterView.getItemAtPosition(position);
		Unit unit = MassUnit.getUnit(unitName);
		if (unit == null) {
			return;
		}
		mass = mass.toSameUnitsAs(ValueAndUnits.createFromUnit(1d, unit));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.mass);
		setText(text3, mass.getValue());
	}

	void onCalcPeriodClicked(View v) {
		ValueAndUnits period = getPeriod();
		if (period == null) {
			return;
		}

		// Convert to preferred units
		period = period.toBestUnits(UnitUtil.getTimeExpressions(
				TimeUnit.S, TimeUnit.MIN, TimeUnit.HR, TimeUnit.DAYS, TimeUnit.YEARS));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.period);
		setText(text3, period.getValue());

		// Set units spinner
		final String unitName = period.getUnitName();
		Spinner spinner1 = (Spinner) findViewById(R.id.periodUnits);
		for (int i = 0; i < spinner1.getCount(); i++) {
			String s = (String) spinner1.getItemAtPosition(i);
			if (unitName.equals(s)) {
				spinner1.setSelection(i);
				break;
			}
		}
	}

	void onPeriodUnitChanged(final AdapterView<? extends Adapter> adapterView, final int position) {
		ValueAndUnits period = getPeriod();
		if (period == null) {
			return;
		}

		// Convert to units in spinner
		final String unitName = (String) adapterView.getItemAtPosition(position);
		Unit unit = TimeUnit.getUnit(unitName);
		if (unit == null) {
			return;
		}
		period = period.toSameUnitsAs(ValueAndUnits.createFromUnit(1d, unit));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.period);
		setText(text3, period.getValue());
	}

	void onCalcRadiusClicked(View v) {
		ValueAndUnits radius = getRadius();
		if (radius == null) {
			return;
		}

		// Convert to preferred units
		radius = radius.toSameUnitsAs(radiusSelector.getPreferredUnits(radius));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.radius);
		setText(text3, radius.getValue());

		// Set spinner to correct unit
		final String unitName = radius.getUnitName();
		Spinner radiusSpinner = (Spinner) findViewById(R.id.radiusUnits);
		for (int i = 0; i < radiusSpinner.getCount(); i++) {
			String s = (String) radiusSpinner.getItemAtPosition(i);
			if (unitName.equals(s)) {
				radiusSpinner.setSelection(i);
				break;
			}
		}
	}

	void onRadiusUnitChanged(final AdapterView<? extends Adapter> adapterView, final int position) {
		ValueAndUnits radius = getRadius();
		if (radius == null) {
			return;
		}

		// Convert to units in spinner
		final String unitName = (String) adapterView.getItemAtPosition(position);
		Unit unit = LengthUnit.getUnit(unitName);
		if (unit == null) {
			return;
		}
		radius = radius.toSameUnitsAs(ValueAndUnits.createFromUnit(1d, unit));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.radius);
		setText(text3, radius.getValue());
	}

	Double getDouble(final String text) {
		if (text == null || text.length() == 0) {
			return Double.NaN;
		}
		return Double.parseDouble(text);
	}

	void setText(final TextView textView, final double value) {
		final String displayText = String.format("%.2f", value);
		textView.setText(displayText);
	}

	ValueAndUnits calculatePeriod(final ValueAndUnits r, final ValueAndUnits m) {
		return ValueAndUnits.create(2d * Math.PI).multiplyBy((r.pow(3).divideBy(G.multiplyBy(m))).pow(1, 2));
	}

	ValueAndUnits calculateRadius(final ValueAndUnits T, final ValueAndUnits m) {
		return G.multiplyBy(m.multiplyBy(T.pow(2))).divideBy(ValueAndUnits.create(4d * Math.pow(Math.PI, 2))).pow(1, 3);
	}

	ValueAndUnits calculateMass(final ValueAndUnits radius, final ValueAndUnits T) {
		return ValueAndUnits.create(4d * Math.pow(Math.PI, 2d)).multiplyBy(radius.pow(3)).divideBy(G.multiplyBy(T.pow(2)));
	}

	boolean hasValue(final Double d) {
		if (d == null || d.equals(Double.NaN)) {
			return false;
		}
		else {
			return true;
		}
	}

	double p(final double x, final double y) {
		return Math.pow(x, y);
	}

}