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
	final double M_SUN = 1.989E30d;
	final double AU = 1.49598E11d;
	final double DAY = 86400d;
	final double YEAR = 365d * DAY;

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

        OnItemSelectedListener spinnerListener = new OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> adapterView, View selectedView, int position, long id) {
        		onPeriodUnitChanged(adapterView, position);
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> v) {

        	}
        };

        Spinner spinner1 = (Spinner) findViewById(R.id.periodUnits);
        spinner1.setOnItemSelectedListener(spinnerListener);
    }

	void onCalcPeriodClicked(View v) {
		TextView text1 = (TextView) findViewById(R.id.mass);
		String massText = (String) text1.getText().toString();
		Double mass = getTextValue(massText);

		TextView text2 = (TextView) findViewById(R.id.radius);
		String radiusText = (String) text2.getText().toString();
		Double radius = getTextValue(radiusText);

		if (!hasValue(radius) || !hasValue(mass)) {
			return;
		}

		ValueAndUnits period = getPeriod(radius, mass);
		//period = period.toSameUnitsAs(ValueAndUnits.createFromUnit(period.getValue(), TimeUnit.DAYS));
		Unit bestUnit = UnitUtil.getBestUnit(period, TimeUnit.getAll());
		period = period.toSameUnitsAs(ValueAndUnits.createFromUnit(1d, bestUnit));

		final TextView text3 = (TextView) findViewById(R.id.period);
		setText(text3, period.getValue());

		//Iterator<UnitAndDim> iterator = period.getUnitExpression().getUnits().iterator();
		//Unit unit = iterator.next().getUnit();
		Spinner spinner1 = (Spinner) findViewById(R.id.periodUnits);
		for (int i = 0; i < spinner1.getCount(); i++) {
			String s = (String) spinner1.getItemAtPosition(i);
			if (bestUnit.getName().equals(s)) {
				spinner1.setSelection(i);
				break;
			}
		}
	}

	void onCalcRadiusClicked(View v) {
		TextView text1 = (TextView) findViewById(R.id.mass);
		String massText = (String) text1.getText().toString();
		Double mass = getTextValue(massText);

		TextView text2 = (TextView) findViewById(R.id.period);
		String periodText = (String) text2.getText().toString();
		Double periodValue = getTextValue(periodText);

		Spinner spinner1 = (Spinner) findViewById(R.id.periodUnits);
		String periodUnitsText = (String) spinner1.getSelectedItem();

		if (!hasValue(periodValue) || !hasValue(mass)) {
			return;
		}

		final TimeUnit periodUnit = TimeUnit.getUnit(periodUnitsText);
		if (periodUnit == null) {
			return;
		}

		ValueAndUnits period = ValueAndUnits.createFromUnit(periodValue, periodUnit);

		ValueAndUnits radius = getRadius(period, mass);
		radius = radius.toSameUnitsAs(ValueAndUnits.createFromUnit(1d, LengthUnit.AU));

		final TextView text3 = (TextView) findViewById(R.id.radius);
		setText(text3, radius.getValue());
	}

	Double getTextValue(final String text) {
		if (text == null || text.length() == 0) {
			return Double.NaN;
		}
		return Double.parseDouble(text);
	}

	void setText(final TextView textView, final double value) {
		final String displayText = String.format("%.2f", value);
		textView.setText(displayText);
	}

	ValueAndUnits getPeriod(final double radiusValue, final double massValue) {
		//return 2d * Math.PI * p(p(radius * AU, 3d) / (G * mass * M_SUN), 1d / 2d);

		final ValueAndUnits r = ValueAndUnits.createFromUnit(radiusValue, LengthUnit.AU);
		final ValueAndUnits m = ValueAndUnits.createFromUnit(massValue, MassUnit.M_SUN);

		return ValueAndUnits.create(2d * Math.PI).multiplyBy((r.pow(3).divideBy(G.multiplyBy(m))).pow(1, 2));
	}

	ValueAndUnits getRadius(final ValueAndUnits T, final double massValue) {

		final ValueAndUnits M = ValueAndUnits.createFromUnit(massValue, MassUnit.M_SUN);

		return G.multiplyBy(M.multiplyBy(T.pow(2))).divideBy(ValueAndUnits.create(4d * Math.pow(Math.PI, 2))).pow(1, 3);

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

	void onPeriodUnitChanged(final AdapterView<? extends Adapter> adapterView, final int position) {
		TextView text1 = (TextView) findViewById(R.id.mass);
		String massText = (String) text1.getText().toString();
		Double mass = getTextValue(massText);

		TextView text2 = (TextView) findViewById(R.id.radius);
		String radiusText = (String) text2.getText().toString();
		Double radius = getTextValue(radiusText);

		if (!hasValue(radius) || !hasValue(mass)) {
			return;
		}

		ValueAndUnits period = getPeriod(radius, mass);

		final String unitName = (String) adapterView.getItemAtPosition(position);
		Unit unit = TimeUnit.getUnit(unitName);
		if (unit == null) {
			return;
		}
		period = period.toSameUnitsAs(ValueAndUnits.createFromUnit(1d, unit));

		final TextView text3 = (TextView) findViewById(R.id.period);
		setText(text3, period.getValue());
	}

}