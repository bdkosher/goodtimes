package bdkosher.goodtimes

import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalField

class YearMonthExtension {

    /**
     * Returns a LocalDate of this YearMonth and the given day-of-month.
     */
    static LocalDate leftShift(final YearMonth self, int dayOfMonth) {
        self.atDay(dayOfMonth)
    }

    /**
     * Returns the value corresponding to the given TemporarlField, provided it is supported as per its isSupported method.
     */
    static getAt(final YearMonth self, TemporalField field) {
        self.get(field)
    }
}