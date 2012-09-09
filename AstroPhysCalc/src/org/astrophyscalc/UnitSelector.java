package org.astrophyscalc;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public class UnitSelector {

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