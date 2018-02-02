package com.shask.cvgenerator.util;

import java.time.Period;

@FunctionalInterface
public interface PeriodFormatter {
    String format(final Period period);
}
