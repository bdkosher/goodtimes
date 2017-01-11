package bdkosher.goodtimes

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.*

/**
 * Aims to mimic the Groovy JDK extension methods available for java.util.Date and java.util.Calendar instances.
 */
class LocalDateExtension {

    /* LocalDate-applicable Calendar fields mapped to their equivalent java.time TemporalField */
    private static final Map<Integer, TemporalField> calendarToTemporalField = [
        (Calendar.DATE): ChronoField.DAY_OF_MONTH,        
        (Calendar.DAY_OF_MONTH): ChronoField.DAY_OF_MONTH,
        (Calendar.DAY_OF_WEEK): ChronoField.DAY_OF_WEEK,
        (Calendar.DAY_OF_WEEK_IN_MONTH): ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH,            
        (Calendar.DAY_OF_YEAR): ChronoField.DAY_OF_YEAR,
        (Calendar.WEEK_OF_MONTH): ChronoField.ALIGNED_WEEK_OF_MONTH,
        (Calendar.WEEK_OF_YEAR): ChronoField.ALIGNED_WEEK_OF_YEAR,
        (Calendar.MONTH): ChronoField.MONTH_OF_YEAR,
        (Calendar.YEAR): ChronoField.YEAR,
        (Calendar.ERA): ChronoField.ERA // consider removal due to GregorianCalendar assumptions
    ]    

    /* Maps java.time.DayOfWeek enum values to their equivalent Calendar field integer value. */
    private static final Map<DayOfWeek, Integer> dayOfWeekToCalendarDay = DayOfWeek.values().collectEntries { [it, Calendar.@"$it"]}

    /* Maps java.time.DayOfWeek enum values to their equivalent Calendar field integer value. */
    private static final Map<Month, Integer> monthToCalendarMonth = Month.values().collectEntries { [it, Calendar.@"$it"]}

    /*static downto(final LocalDate self, LocalDate to, Closure closure) {
        
    }*/

    static format(final LocalDate self, String format, ZoneId zone = ZoneId.systemDefault(), Locale locale = Locale.default) {
        self.format(DateTimeFormatter.ofPattern(format, locale).withZone(zone))
    }

    static format(final LocalDate self, String format, TimeZone timeZone) {
        format(self, format, timeZone.toZoneId())
    }

    /**
     * Adds the given number of days to the LocalDte, returning a new LocalDate instance.
     */
    static plus(final LocalDate self, int days) {
        self.plusDays(days)
    }

    /**
     * Subtracts the given number of days to the LocalDte, returning a new LocalDate instance.
     */
    static minus(final LocalDate self, int days) {
        self.minusDays(days)
    }

    /**
     * Adds the given period of days, months, and/or years to the LocalDate, returning a new LocalDate instance.
     */
    static plus(final LocalDate self, Period period) {
        LocalDate result = LocalDate.ofEpochDay(self.toEpochDay()) // best way to clone?
        [(ChronoUnit.DAYS): period.days, (ChronoUnit.MONTHS): period.months, (ChronoUnit.YEARS): period.years]
                .findAll { it.value != 0 }
                .each { unit, amount ->
                    result = result.plus(amount, unit)
                }
        result
    }

    /**
     * Subtracts the given period of days, months, and/or years to the LocalDate, returning a new LocalDate instance.
     */
    static minus(final LocalDate self, Period period) {
        LocalDate result = LocalDate.ofEpochDay(self.toEpochDay()) // best way to clone?
        [(ChronoUnit.DAYS): period.days, (ChronoUnit.MONTHS) : period.months, (ChronoUnit.YEARS): period.years]
                .findAll { it.value != 0 }
                .each { unit, amount ->
                    result = result.minus(amount, unit)
                }
        result
    }

    /**
     * Checks if the specified java.util.Calendar field is supported.
     * This checks if this date can be queried for the specified field. If false, then calling the getAt method will throw an exception.
     * Note that the supported Calendar fields are a subset of the supported ChronoField values.
     */
    static isSupported(final LocalDate self, int calendarField) {
        TemporalField temporalField = calendarToTemporalField[calendarField]
        temporalField != null && self.isSupported(temporalField)
    }

    /**
     * Returns the field of the LocalDate corresponding to the provided java.util.Calendar field value. The returned values are
     * aligned as best as possible to their java.util.Calendar field values. For example, if you have a local date of January 1st, 2017
     * and call this method with Calendar.MONTH, then Calendar.JANUARY will be returned, even though this integer value is not identical
     * to the value of java.time.Month.JANUARY.getValue(). The same is true for the days of the week and java.time.DayOfWeek.
     */
    static int getAt(final LocalDate self, int field) {
        if (!isSupported(self, field)) {
            throw new IllegalArgumentException("$field is an unrecognized or unsupported java.util.Calendar field value.")
        }
        switch (field) {
            case Calendar.DAY_OF_WEEK:
                return dayOfWeekToCalendarDay[self.dayOfWeek]
            case Calendar.MONTH:
                return monthToCalendarMonth[self.month]
            default:
                return getAt(self, calendarToTemporalField[field])
        }
    }

    /**
     * Returns the value corresponding to the given TemporarlField, provided it is supported by LocalDate as per its isSupported method.
     */
    static getAt(final LocalDate self, TemporalField field) {
        self.get(field)
    }

    /**
     * Converts a LocalDate to a (mostly) equivalent instance of java.util.Date. The time value of the returned Date is cleared,
     * and the TimeZone and Locale are set to system defaults unless explicitly specified.
     */
    static toDate(final LocalDate self, TimeZone timeZone = null, Locale locale = null) {
        toCalendar(self, timeZone, locale).time
    }

    /**
     * Converts a LocalDate to a (mostly) equivalent instance of java.util.Calendar. The time value of the returned Calender is cleared,
     * and the TimeZone and Locale are set to system defaults unless explicitly specified.
     */
    static toCalendar(final LocalDate self, TimeZone timeZone = null, Locale locale = null) {
        int day = self.dayOfMonth
        int month = self.monthValue - 1
        int year = self.year - 1900
        Calendar cal = timeZone 
                ? (locale ? Calendar.getInstance(timeZone, locale) : Calendar.getInstance(timeZone)) 
                : (locale ? Calendar.getInstance(locale) : Calendar.instance)
        cal.with {
            set(Calendar.DATE, day)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
            clearTime()
        }
    }
}