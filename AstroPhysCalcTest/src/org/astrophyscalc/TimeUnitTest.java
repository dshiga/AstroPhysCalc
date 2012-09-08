package org.astrophyscalc;

import org.junit.Assert;
import org.junit.Test;

public class TimeUnitTest extends Assert {

	@Test
	public void testGetUnit() {

		final String days = TimeUnit.DAYS.getName();
		TimeUnit unit = TimeUnit.getUnit(days);
		assertEquals(TimeUnit.DAYS, unit);
	}

	@Test
	public void testCompare() {

		int result;

		result = TimeUnit.S.compare(TimeUnit.S);
		assertEquals(0, result);

		result = TimeUnit.S.compare(TimeUnit.DAYS);
		assertEquals(-1, result);

		result = TimeUnit.S.compare(TimeUnit.YEARS);
		assertEquals(-1, result);


		result = TimeUnit.DAYS.compare(TimeUnit.S);
		assertEquals(1, result);

		result = TimeUnit.DAYS.compare(TimeUnit.DAYS);
		assertEquals(0, result);

		result = TimeUnit.DAYS.compare(TimeUnit.YEARS);
		assertEquals(-1, result);


		result = TimeUnit.YEARS.compare(TimeUnit.S);
		assertEquals(1, result);

		result = TimeUnit.YEARS.compare(TimeUnit.DAYS);
		assertEquals(1, result);

		result = TimeUnit.YEARS.compare(TimeUnit.YEARS);
		assertEquals(0, result);
	}

}
