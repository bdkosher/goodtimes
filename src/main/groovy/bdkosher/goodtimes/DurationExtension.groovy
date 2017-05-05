package bdkosher.goodtimes

import java.time.Duration
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import static java.time.temporal.ChronoUnit.*

import groovy.transform.PackageScope

/**
 * Extension methods for jave.time.Duration
 */
class DurationExtension {

    @PackageScope
    static final Map<String, ChronoUnit> UNIT_KEYS = [D: DAYS, H: HOURS, M: MINUTES, S: SECONDS, N: NANOS].asImmutable()

    /**
     * Describes this Duration as a Map of ChronoUnit keys (HOURS, MINUTES, SECONDS, and NANOS) and long values. The values
     * are normalized. For example, <code>Duration.ofSeconds(62).describe()</code> would return
     * <code>[ChronoUnit.HOURS) : 0, (ChronoUnit.MINUTES) : 1, (ChronoUnit.SECONDS) : 2, ChronoUnit.NANOS) : 0]</code>.
     * If a Duration is negative, then all of the non-zero fields will be negative.
     */
    static Map<TemporalUnit, Long> describe(final Duration self) {
        final int NANO_DIGITS = 9
        boolean negative = self.negative

        String whole = self.toString() - 'PT'

        def desc = [HOURS, MINUTES, SECONDS, NANOS].collectEntries { u -> [(u) : 0] }

        // handle fractional seconds, if present
        if (whole.contains('.')) {
            def parts = (whole - 'S').split(/\./)
            whole = parts[0] + 'S'
            long nanos = parts[1].padRight(NANO_DIGITS, '0') as long
            desc[NANOS] = negative ? nanos * -1 : nanos
        }
 
        // extract non-fractional units
        def m = whole =~ /\d+\D{1}/
        while (m.find()) {
            def valueAndUnit = m.group()
            String unit = valueAndUnit[-1]
            long value = valueAndUnit[0..-2] as long
            desc[UNIT_KEYS[unit]] = negative ? value * -1 : value
        }
        desc
    }

    /**
     * Increases the length of this Duration by 1 second.
     */
    static Duration next(final Duration self) {
        self.plusSeconds(1)
    }

    /**
     * Decreases the length of this Duration by 1 second.
     */
    static Duration previous(final Duration self) {
        self.minusSeconds(1)
    }

    /**
     * Adds the given number of seconds to the Duration.
     */
    static Duration plus(final Duration self, long seconds) {
        self.plusSeconds(seconds)
    }

    /**
     * Subtracts the given number of seconds to the Duration.
     */
    static Duration minus(final Duration self, long seconds) {
        self.minusSeconds(seconds)
    }

    /**
     * Equivalent to calling Duration's negated() method.
     */
    static Duration negative(final Duration self) {
        self.negated()
    }

    /**
     * Turns any negative TemporalUnit value positive.
     */
    static Duration positive(final Duration self) {
        self.isNegative() ? self.negated() : self
    }

    /**
     * Equivalent to calling Duration's multipliedBy() method.
     */
    static Duration multiply(final Duration self, long scalar) {
        self.multipliedBy(scalar)
    }

    /**
     * Equivalent to calling Duration's multipliedBy() method.
     */
    static Duration div(final Duration self, long scalar) {
        self.dividedBy(scalar)
    }

    /**
     * Returns the value corresponding to the given TemporalUnit. Only SECONDS and NANOS are supported.
     */
    static getAt(final Duration self, TemporalUnit temporalUnit) {
        self.get(temporalUnit)
    }

}