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

        final List<List<CalcRow>> listOfLists = calcPage.getListOfLists();
        final List<CalcRow> rowList1 = listOfLists.get(0);
        final List<CalcRow> rowList2 = listOfLists.get(1);
        final List<CalcRow> rowList3 = listOfLists.get(2);

        // Set up button click listeners
        OnClickListener buttonListener1 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcClicked(v, rowList1.get(0), rowList1.get(1), rowList1.get(2));
        	}
        };
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(buttonListener1);

        OnClickListener buttonListener2 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcClicked(v, rowList2.get(0), rowList2.get(1), rowList2.get(2));
        	}
        };
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(buttonListener2);

        OnClickListener buttonListener3 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcClicked(v, rowList3.get(0), rowList3.get(1), rowList3.get(2));
        	}
        };
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(buttonListener3);


        // Define spinner change listeners
        unitsListener1 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onUnitChanged(adapterView, position, rowList1.get(0), rowList1.get(1), rowList1.get(2));
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        unitsListener2 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onUnitChanged(adapterView, position, rowList2.get(0), rowList2.get(1), rowList2.get(2));
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        unitsListener3 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onUnitChanged(adapterView, position, rowList3.get(0), rowList3.get(1), rowList3.get(2));
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        setTextBoxLabel(rowList1.get(0));
        setTextBoxLabel(rowList2.get(0));
        setTextBoxLabel(rowList3.get(0));

        initSpinner(rowList1.get(0));
        initSpinner(rowList2.get(0));
        initSpinner(rowList3.get(0));

        setSpinnerChangeListener(rowList1.get(0), unitsListener1);
        setSpinnerChangeListener(rowList2.get(0), unitsListener2);
        setSpinnerChangeListener(rowList3.get(0), unitsListener3);
    }


	/**
	 * Set the label above the TextView.
	 */
	void setTextBoxLabel(final CalcRow calcRow) {
        TextView tv = (TextView) findViewById(calcRow.getLabelId());
        tv.setText(calcRow.getLabelStringId());
	}

	/**
	 * Set the spinner to its default position.
	 *
	 * @param position1
	 * @param default2
	 * @param default3
	 */
	void initSpinner(final CalcRow calcRow) {
	    ArrayAdapter<UnitSpinnerItem> adapter = new UnitArrayAdapter<UnitSpinnerItem>(
	    		getBaseContext(), R.layout.unit_spinner_item,
	    		R.id.spinnerItemLabel, calcRow.getSpinnerItems());
	    Spinner unitSpinner = (Spinner) findViewById(calcRow.getSpinnerId());
	    unitSpinner.setAdapter(adapter);
		unitSpinner.setSelection(calcRow.getDefaultSpinnerPos());
	}


	void setSpinnerChangeListener(final CalcRow calcRow, final OnItemSelectedListener listener) {
        Spinner unitSpinner = (Spinner) findViewById(calcRow.getSpinnerId());
        unitSpinner.setOnItemSelectedListener(listener);
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

	void onCalcClicked(final View v, final CalcRow resultRow, final CalcRow inputRow1, final CalcRow inputRow2) {
		Double inputValue1 = getValueFromTextView(inputRow1.getTextId());
		Double inputValue2 = getValueFromTextView(inputRow2.getTextId());
		if (!hasValue(inputValue1) || !hasValue(inputValue2)) {
			return;
		}

		// Get input units
		UnitExpression inputUnits1 = getUnitsFromSpinner(inputRow1.getSpinnerId());
		UnitExpression inputUnits2 = getUnitsFromSpinner(inputRow2.getSpinnerId());
		if (inputUnits2 == null || inputUnits1 == null) {
			return;
		}

		// Inputs
		final ValueAndUnits arg1 = ValueAndUnits.create(inputValue1, inputUnits1);
		final ValueAndUnits arg2 = ValueAndUnits.create(inputValue2, inputUnits2);

		ValueAndUnits result = resultRow.calculate(arg1, arg2);
		if (result == null) {
			return;
		}

		// Convert to preferred units
		result = result.toSameUnitsAs(resultRow.getPreferredUnits(result));

		// Fill in text box
		final TextView tv = (TextView) findViewById(resultRow.getTextId());
		setText(tv, result.getValue());

		// Set units spinner
		Spinner spinner = (Spinner) findViewById(resultRow.getSpinnerId());
		for (int i = 0; i < spinner.getCount(); i++) {
			UnitSpinnerItem item = (UnitSpinnerItem) spinner.getItemAtPosition(i);
			if (item != null && item.getUnitExpression() != null &&
					item.getUnitExpression().equals(result.getUnitExpression())) {
				spinner.setSelection(i);
				break;
			}
		}
	}

	void onUnitChanged(final AdapterView<? extends Adapter> adapterView, final int position,
			final CalcRow resultRow, final CalcRow inputRow1, final CalcRow inputRow2) {
		Double inputValue1 = getValueFromTextView(inputRow1.getTextId());
		Double inputValue2 = getValueFromTextView(inputRow2.getTextId());
		if (!hasValue(inputValue2) || !hasValue(inputValue1)) {
			return;
		}

		// Get input units
		UnitExpression inputUnits1 = getUnitsFromSpinner(inputRow1.getSpinnerId());
		UnitExpression inputUnits2 = getUnitsFromSpinner(inputRow2.getSpinnerId());
		if (inputUnits1 == null || inputUnits2 == null) {
			return;
		}

		// Inputs
		final ValueAndUnits arg1 = ValueAndUnits.create(inputValue1, inputUnits1);
		final ValueAndUnits arg2 = ValueAndUnits.create(inputValue2, inputUnits2);

		ValueAndUnits result = resultRow.calculate(arg1, arg2);
		if (result == null) {
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
		result = result.toSameUnitsAs(ValueAndUnits.create(1d, expr));

		// Fill in text box
		final TextView tv = (TextView) findViewById(resultRow.getTextId());
		setText(tv, result.getValue());
	}

	/*void onCalc3Clicked(View v, final CalcRow calcRow3) {
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
	}*/

/*	void onUnit3Changed(final AdapterView<? extends Adapter> adapterView, final int position, final CalcRow calcRow3) {
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
	}*/

	/*void onCalc2Clicked(View v, final CalcRow calcRow2) {
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
	}*/

/*	void onUnit2Changed(final AdapterView<? extends Adapter> adapterView, final int position, final CalcRow calcRow2) {
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
	}*/

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