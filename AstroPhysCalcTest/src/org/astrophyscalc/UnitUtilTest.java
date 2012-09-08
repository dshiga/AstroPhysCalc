package org.astrophyscalc;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class UnitUtilTest extends Assert {

	@Test
	public void getBestUnitEmptyUnits() {

		// Empty unit set
		UnitExpression expr = UnitExpression.create(new HashSet<UnitAndDim>());
		ValueAndUnits vu = ValueAndUnits.create(1d, expr);

		Unit unit = UnitUtil.getBestUnit(vu, new HashSet<Unit>());;
		assertEquals(null, unit);

		// Not single unit of dim one
		expr = UnitExpression.create(TimeUnit.DAYS, 1, 2);
		vu = ValueAndUnits.create(0d, expr);

		Set<Unit> units = new HashSet<Unit>();
		units.add(TimeUnit.DAYS);
		units.add(TimeUnit.YEARS);

		String exceptionMsg = "";
		try {
			UnitUtil.getBestUnit(vu, units);
		}
		catch (IllegalArgumentException ex) {
			exceptionMsg = ex.getMessage();
		}
		assertEquals(UnitUtil.UNIT_EXPR_NOT_SINGLE_UNIT_DIM_ONE, exceptionMsg);
	}

	@Test
	public void getBestUnit() {

		UnitExpression expr = UnitExpression.createFromUnit(TimeUnit.DAYS);
		final ValueAndUnits vu = ValueAndUnits.create(0d, expr);

		final Set<Unit> unitSet = new HashSet<Unit>();
		unitSet.add(TimeUnit.DAYS);
		unitSet.add(TimeUnit.S);
		unitSet.add(TimeUnit.YEARS);

		final Unit bestUnit = UnitUtil.getBestUnit(vu, unitSet);

		assertEquals(TimeUnit.S, bestUnit);
	}

}
