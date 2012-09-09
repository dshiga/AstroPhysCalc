package org.astrophyscalc;

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

    private static final CalcPage calcPage = CalcPage.ORBITS;

	OnItemSelectedListener unitsListener1;
	OnItemSelectedListener unitsListener2;
	OnItemSelectedListener unitsListener3;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set layout
        setContentView(R.layout.main);

        final List<CalcRow> calcRows = calcPage.getCalcRows();

        // Set up button click listeners
        OnClickListener buttonListener1 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalc1Clicked(v, calcRows.get(0));
        	}
        };
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(buttonListener1);

        OnClickListener buttonListener2 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalc2Clicked(v, calcRows.get(1));
        	}
        };
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(buttonListener2);

        OnClickListener buttonListener3 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalc3Clicked(v, calcRows.get(2));
        	}
        };
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(buttonListener3);


        // Define spinner change listeners
        unitsListener1 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onUnit1Changed(adapterView, position, calcRows.get(0));
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        unitsListener2 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onUnit2Changed(adapterView, position, calcRows.get(1));
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        unitsListener3 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onUnit3Changed(adapterView, position, calcRows.get(2));
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        setTextBoxLabels(calcRows.get(0), calcRows.get(1), calcRows.get(2));
        initSpinners(calcRows.get(0), calcRows.get(1), calcRows.get(2));
        enableSpinnerChangeListeners();
    }


	/**
	 * Set the labels above each of the TextViews.
	 */
	void setTextBoxLabels(final CalcRow calcRow1, final CalcRow calcRow2, final CalcRow calcRow3) {
        TextView text1 = (TextView) findViewById(R.id.label1);
        text1.setText(calcRow1.getTextLabel());

        TextView text2 = (TextView) findViewById(R.id.label2);
        text2.setText(calcRow2.getTextLabel());

        TextView text3 = (TextView) findViewById(R.id.label3);
        text3.setText(calcRow3.getTextLabel());
	}

	/**
	 * Set each of the three spinners to the given positions.
	 *
	 * @param position1
	 * @param default2
	 * @param default3
	 */
	void initSpinners(final CalcRow calcRow1, final CalcRow calcRow2, final CalcRow calcRow3) {
	    ArrayAdapter<UnitSpinnerItem> adapter1 = new UnitArrayAdapter<UnitSpinnerItem>(
	    		getBaseContext(), R.layout.unit_spinner_item,
	    		R.id.spinnerItemLabel, calcRow1.getSpinnerItems());
	    Spinner unitSpinner1 = (Spinner) findViewById(R.id.units1);
	    unitSpinner1.setAdapter(adapter1);
		unitSpinner1.setSelection(calcRow1.getDefaultSpinnerPos());

	    ArrayAdapter<UnitSpinnerItem> adapter2 =new UnitArrayAdapter<UnitSpinnerItem>(
	    		getBaseContext(), R.layout.unit_spinner_item,
				R.id.spinnerItemLabel, calcRow2.getSpinnerItems());
	    Spinner unitSpinner2 = (Spinner) findViewById(R.id.units2);
	    unitSpinner2.setAdapter(adapter2);
		unitSpinner2.setSelection(calcRow2.getDefaultSpinnerPos());

	    ArrayAdapter<UnitSpinnerItem> adapter3 = new UnitArrayAdapter<UnitSpinnerItem>(
	    		getBaseContext(), R.layout.unit_spinner_item,
				R.id.spinnerItemLabel, calcRow3.getSpinnerItems());
	    Spinner unitSpinner3 = (Spinner) findViewById(R.id.units3);
	    unitSpinner3.setAdapter(adapter3);
		unitSpinner3.setSelection(calcRow3.getDefaultSpinnerPos());
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
/*
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
	}*/

	void onCalc1Clicked(final View v, final CalcRow calcRow1) {
		Double value3 = getValueFromTextView(R.id.text3);
		Double value2 = getValueFromTextView(R.id.text2);
		if (!hasValue(value3) || !hasValue(value2)) {
			return;
		}

		// Get input units
		UnitExpression units2 = getUnitsFromSpinner(R.id.units2);
		UnitExpression units3 = getUnitsFromSpinner(R.id.units3);
		if (units2 == null || units3 == null) {
			return;
		}

		// Inputs
		final ValueAndUnits vu3 = ValueAndUnits.create(value3, units3);
		final ValueAndUnits vu2 = ValueAndUnits.create(value2, units2);

		ValueAndUnits vu = calcRow1.calculate(vu3, vu2);
		if (vu == null) {
			return;
		}

		// Convert to preferred units
		vu = vu.toSameUnitsAs(calcRow1.getPreferredUnits(vu));

		// Fill in text box
		final TextView tv1 = (TextView) findViewById(R.id.text1);
		setText(tv1, vu.getValue());

		// Set units spinner
		Spinner spinner1 = (Spinner) findViewById(R.id.units1);
		for (int i = 0; i < spinner1.getCount(); i++) {
			UnitSpinnerItem item = (UnitSpinnerItem) spinner1.getItemAtPosition(i);
			if (item != null && item.getUnitExpression() != null &&
					item.getUnitExpression().equals(vu.getUnitExpression())) {
				spinner1.setSelection(i);
				break;
			}
		}
	}

	void onUnit1Changed(final AdapterView<? extends Adapter> adapterView, final int position, final CalcRow calcRow1) {
		Double value3 = getValueFromTextView(R.id.text3);
		Double value2 = getValueFromTextView(R.id.text2);
		if (!hasValue(value3) || !hasValue(value2)) {
			return;
		}

		// Get input units
		UnitExpression units2 = getUnitsFromSpinner(R.id.units2);
		UnitExpression units3 = getUnitsFromSpinner(R.id.units3);
		if (units2 == null || units3 == null) {
			return;
		}

		// Inputs
		final ValueAndUnits vu3 = ValueAndUnits.create(value3, units3);
		final ValueAndUnits vu2 = ValueAndUnits.create(value2, units2);

		ValueAndUnits vu = calcRow1.calculate(vu2, vu3);
		if (vu == null) {
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
		vu = vu.toSameUnitsAs(ValueAndUnits.create(1d, expr));

		// Fill in text box
		final TextView text1 = (TextView) findViewById(R.id.text1);
		setText(text1, vu.getValue());
	}

	void onCalc3Clicked(View v, final CalcRow calcRow3) {
		// Get input values
		Double value1 = getValueFromTextView(R.id.text1);
		Double value2 = getValueFromTextView(R.id.text2);
		if (!hasValue(value2) || !hasValue(value1)) {
			return;
		}

		// Get input units
		UnitExpression units2 = getUnitsFromSpinner(R.id.units2);
		UnitExpression units1 = getUnitsFromSpinner(R.id.units1);
		if (units1 == null || units2 == null) {
			return;
		}

		// Inputs
		final ValueAndUnits vu2 = ValueAndUnits.create(value2, units2);
		final ValueAndUnits vu1 = ValueAndUnits.create(value1, units1);

		ValueAndUnits vu = calcRow3.calculate(vu2, vu1);
		if (vu == null) {
			return;
		}

		// Convert to preferred units
		vu = vu.toSameUnitsAs(calcRow3.getPreferredUnits(vu));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text3);
		setText(text3, vu.getValue());

		// Set units spinner
		Spinner spinner3 = (Spinner) findViewById(R.id.units3);
		for (int i = 0; i < spinner3.getCount(); i++) {
			UnitSpinnerItem item = (UnitSpinnerItem) spinner3.getItemAtPosition(i);
			if (item != null && item.getUnitExpression() != null &&
					item.getUnitExpression().equals(vu.getUnitExpression())) {
				spinner3.setSelection(i);
				break;
			}
		}
	}

	void onUnit3Changed(final AdapterView<? extends Adapter> adapterView, final int position, final CalcRow calcRow3) {
		// Get input values
		Double value1 = getValueFromTextView(R.id.text1);
		Double value2 = getValueFromTextView(R.id.text2);
		if (!hasValue(value2) || !hasValue(value1)) {
			return;
		}

		// Get input units
		UnitExpression units2 = getUnitsFromSpinner(R.id.units2);
		UnitExpression units1 = getUnitsFromSpinner(R.id.units1);
		if (units1 == null || units2 == null) {
			return;
		}

		// Inputs
		final ValueAndUnits vu2 = ValueAndUnits.create(value2, units2);
		final ValueAndUnits vu1 = ValueAndUnits.create(value1, units1);

		ValueAndUnits vu = calcRow3.calculate(vu2, vu1);
		if (vu == null) {
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
		vu = vu.toSameUnitsAs(ValueAndUnits.create(1d, expr));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text3);
		setText(text3, vu.getValue());
	}

	void onCalc2Clicked(View v, final CalcRow calcRow2) {
		// Get input values
		Double value1 = getValueFromTextView(R.id.text1);
		Double value3 = getValueFromTextView(R.id.text3);
		if (!hasValue(value3) || !hasValue(value1)) {
			return;
		}

		// Get input units
		UnitExpression units3 = getUnitsFromSpinner(R.id.units3);
		UnitExpression units1 = getUnitsFromSpinner(R.id.units1);
		if (units1 == null || units3 == null) {
			return;
		}

		// Inputs
		final ValueAndUnits vu3 = ValueAndUnits.create(value3, units3);
		final ValueAndUnits vu1 = ValueAndUnits.create(value1, units1);

		ValueAndUnits vu = calcRow2.calculate(vu3, vu1);
		if (vu == null) {
			return;
		}

		// Convert to preferred units
		vu = vu.toSameUnitsAs(calcRow2.getPreferredUnits(vu));

		// Fill in text box
		final TextView text3 = (TextView) findViewById(R.id.text2);
		setText(text3, vu.getValue());

		// Set spinner to correct unit
		Spinner unitSpinner2 = (Spinner) findViewById(R.id.units2);
		for (int i = 0; i < unitSpinner2.getCount(); i++) {
			UnitSpinnerItem item = (UnitSpinnerItem) unitSpinner2.getItemAtPosition(i);
			if (item != null && item.getUnitExpression() != null &&
					item.getUnitExpression().equals(vu.getUnitExpression())) {
				unitSpinner2.setSelection(i);
				break;
			}
		}
	}

	void onUnit2Changed(final AdapterView<? extends Adapter> adapterView, final int position, final CalcRow calcRow2) {
		// Get input values
		Double value1 = getValueFromTextView(R.id.text1);
		Double value3 = getValueFromTextView(R.id.text3);
		if (!hasValue(value3) || !hasValue(value1)) {
			return;
		}

		// Get input units
		UnitExpression units3 = getUnitsFromSpinner(R.id.units3);
		UnitExpression units1 = getUnitsFromSpinner(R.id.units1);
		if (units1 == null || units3 == null) {
			return;
		}

		// Inputs
		final ValueAndUnits vu3 = ValueAndUnits.create(value3, units3);
		final ValueAndUnits vu1 = ValueAndUnits.create(value1, units1);

		ValueAndUnits vu = calcRow2.calculate(vu3, vu1);
		if (vu == null) {
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
		vu = vu.toSameUnitsAs(ValueAndUnits.create(1d, expr));

		// Fill in text box
		final TextView text2 = (TextView) findViewById(R.id.text2);
		setText(text2, vu.getValue());
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

	/*ValueAndUnits calculatePeriod(final ValueAndUnits r, final ValueAndUnits m) {
		return ValueAndUnits.create(2d * Math.PI).multiplyBy((r.pow(3).divideBy(G.multiplyBy(m))).pow(1, 2));
	}

	ValueAndUnits calculateRadius(final ValueAndUnits T, final ValueAndUnits m) {
		return G.multiplyBy(m.multiplyBy(T.pow(2))).divideBy(ValueAndUnits.create(4d * Math.pow(Math.PI, 2))).pow(1, 3);
	}

	ValueAndUnits calculateMass(final ValueAndUnits radius, final ValueAndUnits T) {
		return ValueAndUnits.create(4d * Math.pow(Math.PI, 2d)).multiplyBy(radius.pow(3)).divideBy(G.multiplyBy(T.pow(2)));
	}*/

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