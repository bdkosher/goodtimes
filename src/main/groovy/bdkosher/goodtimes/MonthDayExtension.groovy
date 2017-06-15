package bdkosher.goodtimes

import java.time.LocalDate
import java.time.MonthDay
import java.time.Year
import java.time.temporal.TemporalField
import org.codehaus.groovy.runtime.DefaultGroovyMethods

/**
 * Extension methods for java.time.MonthDay
 */
class MonthDayExtension {

    /**
     * Returns a LocalDate of this MonthDay and the given year.
     */
    static LocalDate leftShift(final MonthDay self, int year) {
        self.atYear(year)
    }

    /**
     * Returns a LocalDate of this MonthDay and the given year.
     */
    static LocalDate leftShift(final MonthDay self, Year year) {
        year.atMonthDay(self)
    }    

    /**
     * Returns the value corresponding to the given TemporarlField, provided it is supported as per its isSupported method.
     */
    static getAt(final MonthDay self, TemporalField field) {
        self.get(field)
    }

    /**
     * An alias for getDayOfMonth.
     */
    static getDay(final MonthDay self) {
        self.getDayOfMonth()
    }
}