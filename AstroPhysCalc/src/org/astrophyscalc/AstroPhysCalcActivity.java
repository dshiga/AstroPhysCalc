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

	OnItemSelectedListener unitsListener1;
	OnItemSelectedListener unitsListener2;
	OnItemSelectedListener unitsListener3;

    List<CalcRow> rowList1;
    List<CalcRow> rowList2;
    List<CalcRow> rowList3;

    private boolean spinnersEnabled;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set layout
        setContentView(R.layout.main);

        final List<CalcPage> calcPages = new ArrayList<CalcPage>();
        calcPages.add(CalcPage.ORBITS);
        calcPages.add(CalcPage.KE);
        calcPages.add(CalcPage.FLUX_PAGE);

	    ArrayAdapter<CalcPage> adapter = new PageArrayAdapter(
	    		getBaseContext(), R.layout.page_spinner_item,
	    		R.id.spinnerItemLabel, calcPages);
	    Spinner pageSpinner = (Spinner) findViewById(R.id.pageSpinner);
	    pageSpinner.setAdapter(adapter);

        OnItemSelectedListener pageSpinnerListener = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onPageChanged(adapterView, position);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };
        pageSpinner.setOnItemSelectedListener(pageSpinnerListener);
		pageSpinner.setSelection(0);

        // Set up button click listeners
        OnClickListener buttonListener1 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcClicked(v, rowList1);
        	}
        };
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(buttonListener1);

        OnClickListener buttonListener2 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcClicked(v, rowList2);
        	}
        };
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(buttonListener2);

        OnClickListener buttonListener3 = new OnClickListener() {
        	public void onClick(View v) {
        		onCalcClicked(v, rowList3);
        	}
        };
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(buttonListener3);


        // Define spinner change listeners
        unitsListener1 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		if (!getSpinnersEnabled()) {
        			return;
        		}
        		onUnitChanged(adapterView, position, rowList1);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        unitsListener2 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		if (!getSpinnersEnabled()) {
        			return;
        		}
        		onUnitChanged(adapterView, position, rowList2);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        unitsListener3 = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		if (!getSpinnersEnabled()) {
        			return;
        		}
        		onUnitChanged(adapterView, position, rowList3);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {
        	}
        };

        setSpinnersEnabled(true);

        setSpinnerChangeListener(R.id.units1, unitsListener1);
        setSpinnerChangeListener(R.id.units2, unitsListener2);
        setSpinnerChangeListener(R.id.units3, unitsListener3);
    }

	void onPageChanged(final AdapterView<? extends Adapter> adapterView, final int position) {

		final CalcPage page = (CalcPage) adapterView.getItemAtPosition(position);
        final List<List<CalcRow>> listOfLists = page.getListOfLists();

        rowList1 = listOfLists.get(0);
        rowList2 = listOfLists.get(1);
        rowList3 = listOfLists.get(2);

        setTextBoxLabel(rowList1.get(0));
        setTextBoxLabel(rowList2.get(0));
        setTextBoxLabel(rowList3.get(0));

        clearTextBox(rowList1.get(0));
        clearTextBox(rowList2.get(0));
        clearTextBox(rowList3.get(0));

        disableSpinners();

        initSpinner(rowList1.get(0));
        initSpinner(rowList2.get(0));
        initSpinner(rowList3.get(0));

        enableSpinners();
	}

	/**
	 * Set the label above the TextView.
	 */
	void setTextBoxLabel(final CalcRow calcRow) {
        TextView tv = (TextView) findViewById(calcRow.getLabelId());
        tv.setText(calcRow.getLabelStringId());
	}

	void clearTextBox(final CalcRow calcRow) {
		TextView tv = (TextView) findViewById(calcRow.getTextId());
		tv.setText("");
	}

	void disableSpinners() {
		setSpinnersEnabled(false);
	}

	void enableSpinners() {
		setSpinnersEnabled(true);
	}

	private boolean getSpinnersEnabled() {
		return spinnersEnabled;
	}

	private void setSpinnersEnabled(final boolean enabled) {
		spinnersEnabled = enabled;
	}

	/**
	 * Set the spinner to its default position.
	 *
	 * @param position1
	 * @param default2
	 * @param default3
	 */
	void initSpinner(final CalcRow calcRow) {
	    ArrayAdapter<UnitSpinnerItem> adapter = new UnitArrayAdapter(
	    		getBaseContext(), R.layout.unit_spinner_item,
	    		R.id.spinnerItemLabel, calcRow.getSpinnerItems());
	    Spinner unitSpinner = (Spinner) findViewById(calcRow.getSpinnerId());
	    unitSpinner.setAdapter(adapter);
		unitSpinner.setSelection(calcRow.getDefaultSpinnerPos());
	}


	void setSpinnerChangeListener(final int spinnerId, final OnItemSelectedListener listener) {
        Spinner unitSpinner = (Spinner) findViewById(spinnerId);
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

	void onCalcClicked(final View v, final List<CalcRow> rows) {
		final CalcRow resultRow = rows.get(0);
		final CalcRow inputRow1 = rows.get(1);
		final CalcRow inputRow2 = rows.get(2);

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
			final List<CalcRow> rows) {
		final CalcRow resultRow = rows.get(0);
		final CalcRow inputRow1 = rows.get(1);
		final CalcRow inputRow2 = rows.get(2);

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

	private class UnitArrayAdapter extends ArrayAdapter<UnitSpinnerItem> {

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

	private class PageArrayAdapter extends ArrayAdapter<CalcPage> {

		public PageArrayAdapter(Context context, int resource,
				int textViewResourceId, List<CalcPage> objects) {
			super(context, resource, textViewResourceId, objects);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			CalcPage item = getItem(position);

			LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.page_spinner_item, parent, false);
			TextView text = (TextView) view.findViewById(R.id.spinnerItemLabel);
			text.setText(item.getTitleStringId());
			return view;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CalcPage item = getItem(position);

			LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.page_spinner_selected, parent, false);
			TextView text = (TextView) view.findViewById(R.id.spinnerItemLabel);
			text.setText(item.getTitleStringId());
			return view;
		}

	}


}