package bdkosher.goodtimes

import java.time.*
import org.codehaus.groovy.runtime.DefaultGroovyMethods

/**
 * Extension methods for java.time.Year
 */
class YearExtension {

    /**
     * If this year is less than zero, makes it positive.
     */
    static Year positive(final Year self) {
        Year.of(Math.abs(self.value))
    }

    /**
     * Negates this year.
     */
    static Year negative(final Year self) {
        Year.of(self.value * -1)
    }

    /**
     * Next year.
     */
    static Year next(final Year self) {
        plus(self, 1)
    }

    /**
     * Last year.
     */
    static Year previous(final Year self) {
        minus(self, 1)
    }

    /**
     * Adds the given number of years from this year.
     */
    static Year plus(final Year self, int years) {
        self.plusYears(years)
    }

    /**
     * Subtracts the given number of years from this year.
     */
    static Year minus(final Year self, int years) {
        self.minusYears(years)
    }

    /**
     * Returns the Period of time between this year and the given year.
     */
    static Period minus(final Year self, Year to) {
        Period.ofYears(to.value - self.value)
    }

    /**
     * Returns the YearMonth of this year and the given month.
     */
    static YearMonth leftShift(final Year self, Month month) {
        self.atMonth(month)
    }

    /**
     * Returns the LocalDate of this year on the given month/day.
     */
    static LocalDate leftShift(final Year self, MonthDay monthDay) {
        self.atMonthDay(monthDay)
    }

    /**
     * Returns the LocalDate of this year on the given day of the year.
     */
    static LocalDate leftShift(final Year self, int dayOfYear) {
        self.atDay(dayOfYear)
    }

    /**
     * Permits the conversion of this Year into an integer representation.
     */
    static asType(final Year self, Class type) {
        def originalAsType = Year.&asType
        switch (type) {
            case Integer:
                return self.value as Integer
            case Long:
                return self.value as Long
            case BigInteger:
                return self.value as BigInteger
            case Integer.TYPE:
                return self.value as int
            case Long.TYPE:
                return self.value as long
            default:
                // XXX not sure this is the best way to implement 
                return DefaultGroovyMethods.asType(self, type)
        }
    }
}