package org.astrophyscalc;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public class UnitSelector {

	public static final UnitSelector ORBIT_MASS_SELECTOR = UnitSelector.create(
			UnitSelectionRule.create(UnitExpression.createFromUnit(MassUnit.M_EARTH)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(0.1d, MassUnit.M_JUP), UnitExpression.createFromUnit(MassUnit.M_JUP)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(0.01d, MassUnit.M_SUN), UnitExpression.createFromUnit(MassUnit.M_SUN)));

	public static final UnitSelector ORBIT_RADIUS_SELECTOR = UnitSelector.create(
			UnitSelectionRule.create(UnitExpression.createFromUnit(LengthUnit.LY)),
			UnitSelectionRule.create(
					ValueAndUnits.createFromUnit(1E6d, LengthUnit.KM), UnitExpression.createFromUnit(LengthUnit.AU)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(LengthUnit.KM)));

	public static final UnitSelector ORBIT_PERIOD_SELECTOR = UnitSelector.create(
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.S)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.MIN)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.HR)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.DAYS)),
			UnitSelectionRule.create(UnitExpression.createFromUnit(TimeUnit.YEARS)));

	private SortedSet<UnitSelectionRule> rules;

	public static UnitSelector create(final Set<UnitSelectionRule> rules) {
		final SortedSet<UnitSelectionRule> sortedRules = new TreeSet<UnitSelectionRule>();
		sortedRules.addAll(rules);
		return new UnitSelector(sortedRules);
	}

	private UnitSelector(final SortedSet<UnitSelectionRule> rules) {
		this.rules = rules;
	}

	public static UnitSelector create(final UnitSelectionRule ... rules) {
		final SortedSet<UnitSelectionRule> ruleSet = new TreeSet<UnitSelectionRule>();
		for (UnitSelectionRule rule : rules) {
			ruleSet.add(rule);
		}
		return new UnitSelector(ruleSet);
	}

	public UnitSelector addRule(final UnitSelectionRule rule) {
		getRules().add(rule);
		return this;
	}

	public UnitExpression getPreferredUnits(final ValueAndUnits vu) {
		if (rules.size() == 0) {
			return null;
		}
		for (UnitSelectionRule rule : rules) {
			if (!vu.isLessThan(rule.getValueAndUnits())) {
				return rule.getUnitExpression();
			}
		}
		return rules.last().getUnitExpression();
	}

	private Set<UnitSelectionRule> getRules() {
		return rules;
	}
}