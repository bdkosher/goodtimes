package bdkosher.goodtimes

import java.time.Period
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit

/**
 * Extension methods for java.time.Period.
 */
class PeriodExtension {

    /**
     * Describes this Period as a Map of ChronoUnit keys (YEARS, MONTHS, DAYS) and int values.
     */
    static describe(final Period self) {
        [(ChronoUnit.DAYS): self.days, (ChronoUnit.MONTHS): self.months, (ChronoUnit.YEARS): self.years]
    }

    /**
     * Returns a new Period containing the values of the provided map (using ChronoUnit enums as keys). For values not specified in Map, 
     * the existing values of this Period will be used. Thus, an empty Map creates a copy of this Period.
     */
    static Period copyWith(final Period self, Map updates) {
        def values = describe(self) + updates
        int[] args = [ChronoUnit.YEARS, ChronoUnit.MONTHS, ChronoUnit.DAYS].collect { values[it] }
        Period.of(*args)
    }

    /**
     * Increases the length of this Period by 1 day. No normalization is performed.
     */
    static Period next(final Period self) {
        self.plusDays(1)
    }

    /**
     * Decreases the length of this Period by 1 day. No normalization is performed.
     */
    static Period previous(final Period self) {
        self.minusDays(1)
    }

    /**
     * Equivalent to calling Period's negated() method.
     */
    static Period negative(final Period self) {
        self.negated()
    }

    /**
     * Causes the Period to become positive if it's negative
     */
    static Period positive(final Period self) {
        self.isNegative() ? copyWith(self, describe(self).collectEntries { unit, amount -> [(unit) : Math.abs(amount)] }) : self
    }

    /**
     * Equivalent to calling Period's multipliedBy() method.
     */
    static Period multiply(final Period self, int scalar) {
        self.multipliedBy(scalar)
    }

    /**
     * Returns the value corresponding to the given TemporalUnit, provided it is supported by Period as per its isSupported method.
     */
    static getAt(final Period self, TemporalUnit temporalUnit) {
        self.get(temporalUnit)
    }
}