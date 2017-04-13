package bdkosher.goodtimes

import java.time.Period
import java.time.temporal.*

class PeriodStaticExtension {

    static Period of(final Period type, Map attributes) {
        Period.of(
            attributes[ChronoUnit.YEARS] as int, 
            attributes[ChronoUnit.MONTHS] as int, 
            attributes[ChronoUnit.DAYS] as int
        )
    }
}