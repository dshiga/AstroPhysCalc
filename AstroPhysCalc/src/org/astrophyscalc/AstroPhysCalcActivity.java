package org.astrophyscalc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class AstroPhysCalcActivity extends Activity {

	// Orbits
	static final ValueAndUnits G = ValueAndUnits.create(6.67E-11d, UnitExpression.create(
			UnitAndDim.create(LengthUnit.M, 3), UnitAndDim.create(MassUnit.KG, -1), UnitAndDim.create(TimeUnit.S, -2)));

	static final UnitSelector orbitMassSelector = UnitSelector.create(
			UnitSelectionRule.create(UnitExpression.createFromUnit(MassUnit.M_EARTH)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(0.1d, MassUnit.M_JUP), UnitExpression.createFromUnit(MassUnit.M_JUP)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(0.01d, MassUnit.M_SUN), UnitExpression.createFromUnit(MassUnit.M_SUN)));

	static final UnitSelector orbitRadiusSelector = UnitSelector.create(
			UnitSelectionRule.create(UnitExpression.createFromUnit(LengthUnit.LY)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(1E6d, LengthUnit.KM), UnitExpression.createFromUnit(LengthUnit.AU)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(LengthUnit.KM)));

	static final UnitSelector orbitPeriodSelector = UnitSelector.create(
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.S)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.MIN)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.HR)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.DAYS)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.YEARS)));

	OnItemSelectedListener unitsListener1;
	OnItemSelectedListener unitsListener2;
	OnItemSelectedListener unitsListener3;


	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set layout
        setContentView(R.layout.main);


        setTextBoxLabels(R.string.massLabel, R.string.radiusLabel, R.string.periodLabel);


        // Set up button click listeners
        OnClickListener buttonListener1 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcMassClicked(v);
        	}
        };
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(buttonListener1);

        OnClickListener buttonListener2 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcRadiusClicked(v);
        	}
        };
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(buttonListener2);

        OnClickListener buttonListener3 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcPeriodClicked(v);
        	}
        };
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(buttonListener3);


        // Define spinner change listeners
        unitsListener1 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onMassUnitChanged(adapterView, position);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        unitsListener2 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onRadiusUnitChanged(adapterView, position);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        unitsListener3 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onPeriodUnitChanged(adapterView, position);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        List<UnitSpinnerItem> items = new ArrayList<UnitSpinnerItem>();
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_EARTH), "Earths"));
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_JUP), "Jupiters"));
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(MassUnit.M_SUN), "Suns"));
        ArrayAdapter<UnitSpinnerItem> adapter = new UnitArrayAdapter<UnitSpinnerItem>(getBaseContext(), R.layout.unit_spinner_item,
        			R.id.spinnerItemLabel, items);
        Spinner unitSpinner1 = (Spinner) findViewById(R.id.units1);
		unitSpinner1.setAdapter(adapter);

        items = new ArrayList<UnitSpinnerItem>();
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.KM), "km"));
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.AU), "AU"));
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(LengthUnit.LY), "ly"));
        adapter = new UnitArrayAdapter<UnitSpinnerItem>(getBaseContext(), R.layout.unit_spinner_item,
        			R.id.spinnerItemLabel, items);
        Spinner unitSpinner2 = (Spinner) findViewById(R.id.units2);
		unitSpinner2.setAdapter(adapter);

		items = new ArrayList<UnitSpinnerItem>();
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.S), "s"));
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.MIN), "minutes"));
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.HR), "hours"));
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.DAYS), "days"));
        items.add(UnitSpinnerItem.create(UnitExpression.createFromUnit(TimeUnit.YEARS), "years"));
        adapter = new UnitArrayAdapter<UnitSpinnerItem>(getBaseContext(), R.layout.unit_spinner_item,
        			R.id.spinnerItemLabel, items);
        Spinner unitSpinner3 = (Spinner) findViewById(R.id.units3);
		unitSpinner3.setAdapter(adapter);

        setSpinnersToPositions(2, 1, 4);
        enableSpinnerChangeListeners();
    }


	/**
	 * Set the labels above each of the TextViews.
	 */
	void setTextBoxLabels(final int stringId1, final int stringId2, final int stringId3) {
        TextView text1 = (TextView) findViewById(R.id.label1);
        text1.setText(stringId1);

        TextView text2 = (TextView) findViewById(R.id.label2);
        text2.setText(stringId2);

        TextView text3 = (TextView) findViewById(R.id.label3);
        text3.setText(stringId3);
	}

	/**
	 * Set each of the three spinners to the given positions.
	 *
	 * @param position1
	 * @param default2
	 * @param default3
	 */
	void setSpinnersToPositions(final int position1, final int position2, final int position3) {
        Spinner unitSpinner1 = (Spinner) findViewById(R.id.units1);
		unitSpinner1.setSelection(position1);

        Spinner unitSpinner2 = (Spinner) findViewById(R.id.units2);
		unitSpinner2.setSelection(position2);

        Spinner unitSpinner3 = (Spinner) findViewById(R.id.units3);
        unitSpinner3.setSelection(position3);
	}


	void enableSpinnerChangeListeners() {
        Spinner unitSpinner1 = (Spinner) findViewById(R.id.units1);
        Spinner unitSpinner2 = (Spinner) findViewById(R.id.units2);
        Spinner unitSpinner3 = (Spinner) findViewById(R.id.units3);

        unitSpinner1.setOnItemSelectedListener(unitsListener1);
        unitSpinner2.setOnItemSelectedListener(unitsListener2);
        unitSpinner3.setOnItemSelectedListener(unitsListener3);
	}

	void disableSpinnerChangeListeners() {

	}

	Double getValueFromTextView(final int textViewId) {
		TextView text1 = (TextView) findViewById(textViewId);
		String textValue = (String) text1.getText().toString();
		return getDouble(textValue);
	}

	UnitExpression getUnitsFromSpinner(final int spinnerId) {
		Spinner spinner1 = (Spinner) findViewById(spinnerId);
		UnitSpinnerItem item = (UnitSpinnerItem) spinner1.getSelectedItem();
		if (item == null) {
			return null;
		}
		return item.getUnitExpression();
	}

	ValueAndUnits getPeriod() {
		// Get input values
		Double massValue = getValueFromTextView(R.id.text1);
		Double radiusValue = getValueFromTextView(R.id.text2);
		if (!hasValue(radiusValue) || !hasValue(massValue)) {
			return null;
		}

		// Get input units
		UnitExpression radiusUnits = getUnitsFromSpinner(R.id.units2);
		UnitExpression massUnits = getUnitsFromSpinner(R.id.units1);
		if (massUnits == null || radiusUnits == null) {
			return null;
		}

		// Inputs
		final ValueAndUnits radius = ValueAndUnits.create(radiusValue, radiusUnits);
		final ValueAndUnits mass = ValueAndUnits.create(massValue, massUnits);

		// Calculate period
		return calculatePeriod(radius, mass);
	}

	ValueAndUnits getRadius() {
		// Get input values
		Double massValue = getValueFromTextView(R.id.text1);
		Double periodValue = getValueFromTextView(R.id.text3);
		if (!hasValue(periodValue) || !hasValue(massValue)) {
			return null;
		}

		// Get input units
		UnitExpression periodUnits = getUnitsFromSpinner(R.id.units3);
		UnitExpression massUnits = getUnitsFromSpinner(R.id.units1);
		if (massUnits == null || periodUnits == null) {
			return null;
		}

		// Inputs
		final ValueAndUnits period = ValueAndUnits.create(periodValue, periodUnits);
		final ValueAndUnits mass = ValueAndUnits.create(massValue, massUnits);

		// Calculate radius
		return calculateRadius(period, mass);
	}


	ValueAndUnits getMass() {
		// Get input values
		Double periodValue = getValueFromTextView(R.id.text3);
		Double radiusValue = getValueFromTextView(R.id.text2);
		if (!hasValue(periodValue) || !hasValue(radiusValue)) {
			return null;
		}

		// Get input units
		UnitExpression radiusUnits = getUnitsFromSpinner(R.id.units2);
		UnitExpression periodUnits = getUnitsFromSpinner(R.id.units3);
		if (radiusUnits == null || periodUnits == null) {
			return null;
		}

		// Inputs
		final ValueAndUnits period = ValueAndUnits.create(periodValue, periodUnits);
		final ValueAndUnits radius = ValueAndUnits.create(radiusValue, radiusUnits);

		// Calculate radius
		return calculateMass(radius, period);
	}

	void onCalcMassClicked(View v) {
		ValueAndUnits mass = getMass();
		if (mass == null) {
			return;
		}

		// Convert to preferred units
		mass = mass.toSameUnitsAs(orbitMassSelector.getPreferredUnits(mass));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text1);
		setText(text3, mass.getValue());

		// Set units spinner
		Spinner spinner1 = (Spinner) findViewById(R.id.units1);
		for (int i = 0; i < spinner1.getCount(); i++) {
			UnitSpinnerItem item = (UnitSpinnerItem) spinner1.getItemAtPosition(i);
			if (item != null && item.getUnitExpression() != null &&
					item.getUnitExpression().equals(mass.getUnitExpression())) {
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
		final UnitSpinnerItem item = (UnitSpinnerItem) adapterView.getItemAtPosition(position);
		if (item == null) {
			return;
		}
		final UnitExpression expr = item.getUnitExpression();
		if (expr == null) {
			return;
		}
		mass = mass.toSameUnitsAs(ValueAndUnits.create(1d, expr));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text1);
		setText(text3, mass.getValue());
	}

	void onCalcPeriodClicked(View v) {
		ValueAndUnits period = getPeriod();
		if (period == null) {
			return;
		}

		// Convert to preferred units
		period = period.toSameUnitsAs(orbitPeriodSelector.getPreferredUnits(period));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text3);
		setText(text3, period.getValue());

		// Set units spinner
		Spinner spinner1 = (Spinner) findViewById(R.id.units3);
		for (int i = 0; i < spinner1.getCount(); i++) {
			UnitSpinnerItem item = (UnitSpinnerItem) spinner1.getItemAtPosition(i);
			if (item != null && item.getUnitExpression() != null &&
					item.getUnitExpression().equals(period.getUnitExpression())) {
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
		final UnitSpinnerItem item = (UnitSpinnerItem) adapterView.getItemAtPosition(position);
		if (item == null) {
			return;
		}
		final UnitExpression expr = item.getUnitExpression();
		if (expr == null) {
			return;
		}
		period = period.toSameUnitsAs(ValueAndUnits.create(1d, expr));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text3);
		setText(text3, period.getValue());
	}

	void onCalcRadiusClicked(View v) {
		ValueAndUnits radius = getRadius();
		if (radius == null) {
			return;
		}

		// Convert to preferred units
		radius = radius.toSameUnitsAs(orbitRadiusSelector.getPreferredUnits(radius));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text2);
		setText(text3, radius.getValue());

		// Set spinner to correct unit
		Spinner unitSpinner2 = (Spinner) findViewById(R.id.units2);
		for (int i = 0; i < unitSpinner2.getCount(); i++) {
			UnitSpinnerItem item = (UnitSpinnerItem) unitSpinner2.getItemAtPosition(i);
			if (item != null && item.getUnitExpression() != null &&
					item.getUnitExpression().equals(radius.getUnitExpression())) {
				unitSpinner2.setSelection(i);
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
		final UnitSpinnerItem item = (UnitSpinnerItem) adapterView.getItemAtPosition(position);
		if (item == null) {
			return;
		}
		final UnitExpression expr = item.getUnitExpression();
		if (expr == null) {
			return;
		}
		radius = radius.toSameUnitsAs(ValueAndUnits.create(1d, expr));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text2);
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

	public class UnitArrayAdapter<UnitSpinnerItem> extends ArrayAdapter<UnitSpinnerItem> {

		public UnitArrayAdapter(Context context, int resource,
				int textViewResourceId, List<UnitSpinnerItem> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			UnitSpinnerItem item = getItem(position);

			LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.unit_spinner_item, parent, false);
			TextView text = (TextView) view.findViewById(R.id.spinnerItemLabel);
			text.setText(item.toString());
			return view;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			UnitSpinnerItem item = getItem(position);

			LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.unit_spinner_selected, parent, false);
			TextView text = (TextView) view.findViewById(R.id.spinnerItemLabel);
			text.setText(item.toString());
			return view;
		}

	}

}