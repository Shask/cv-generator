package com.shask.cvgenerator.util.impl;

import com.shask.cvgenerator.util.PeriodFormatter;

import java.time.Period;

public class FrenchPeriodFormatter implements PeriodFormatter {

    @Override
    public String format(final Period period) {
        final int yearsSpent = period.getYears();
        final int monthsSpent = period.getMonths();
        String label;
        if (yearsSpent < 1) {
            label = (monthsSpent < 1 ? "1" : monthsSpent) + " mois";
        } else {
            label = yearsSpent >= 2 ? yearsSpent + " ans " : yearsSpent + " an ";
            label += monthsSpent > 0 ? "et " + monthsSpent + " mois" : "";
        }
        return label;
    }
}
