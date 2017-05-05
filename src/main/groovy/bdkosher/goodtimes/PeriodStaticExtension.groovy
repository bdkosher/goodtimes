package bdkosher.goodtimes

import java.time.Period
import java.time.temporal.*

/**
 * Static extension methods for java.time.Period.
 */
class PeriodStaticExtension {

    /**
     * Creates a Period from a Map of integer values. Only keys of ChronoUnit.YEARS, ChronoUnit.MONTHS, and ChronoUnit.DAYS
     * are recognized.
     */
    static Period of(final Period type, Map attributes) {
        Period.of(
            attributes[ChronoUnit.YEARS] as int, 
            attributes[ChronoUnit.MONTHS] as int, 
            attributes[ChronoUnit.DAYS] as int
        )
    }
}