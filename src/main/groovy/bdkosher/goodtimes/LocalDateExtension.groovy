package bdkosher.goodtimes

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.*

import groovy.transform.PackageScope

/**
 * Extension methods for java.time.LocalDate
 *
 * Aims to mimic the Groovy JDK extension methods available for java.util.Date and java.util.Calendar instances.
 */
class LocalDateExtension {

    /* LocalDate-applicable Calendar fields mapped to their equivalent java.time TemporalField */
    @PackageScope
    static final Map<Integer, TemporalField> calendarToTemporalField = [
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
    ] .asImmutable()

    @PackageScope
    static final Set<TemporalField> LONG_TYPED_FIELDS = EnumSet.of(ChronoField.PROLEPTIC_MONTH, ChronoField.EPOCH_DAY).asImmutable()

    /**
     * Iterates from this LocalDate down to the given LocalDate, inclusive, decrementing by one day each time.
     */
    static void downto(final LocalDate self, LocalDate to, Closure closure) {
        if (to > self) {
            throw new GroovyRuntimeException("The argument ($to) to downto() cannot be later than the value ($self) it's called on.")
        }
        def paramTypes = closure.parameterTypes
        boolean acceptsDate = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(LocalDate)
        def from = self
        while (from >= to) {
            acceptsDate ? closure(from) : closure()
            from = from - 1
        }
    }

    /**
     * Iterates from this LocalDate down to the given LocalDate, inclusive, decrementing by one day each time.
     */
    static void upto(final LocalDate self, LocalDate to, Closure closure) {
        if (to < self) {
            throw new GroovyRuntimeException("The argument ($to) to upto() cannot be earlier than the value ($self) it's called on.")
        }
        def paramTypes = closure.parameterTypes
        boolean acceptsDate = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(LocalDate)
        def from = self
        while (from <= to) {
            acceptsDate ? closure(from) : closure()
            from = from + 1
        }
    }    

    /**
     * Formats the LocalDate with the given pattern and optional locale. The default Locale is used if none is provided.
     */
    static String format(final LocalDate self, String pattern, Locale locale = Locale.default) {
        self.format(DateTimeFormatter.ofPattern(pattern, locale))
    }

    /**
     * Formats the LocalDate in the localized date style.
     */
    static String format(final LocalDate self, FormatStyle dateStyle) {
        self.format(DateTimeFormatter.ofLocalizedDate(dateStyle))
    }

    /**
     * Return a string representation of this date according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getDateString(final LocalDate self) {
        format(self, FormatStyle.SHORT)
    }

    /**
     * The next day.
     */
    static LocalDate next(final LocalDate self) {
        plus(self, 1)
    }

    /**
     * The previous day.
     */
    static LocalDate previous(final LocalDate self) {
        minus(self, 1)
    }    

    /**
     * Adds the given number of days to the LocalDate, returning a new LocalDate instance.
     */
    static LocalDate plus(final LocalDate self, int days) {
        self.plusDays(days)
    }

    /**
     * Subtracts the given number of days to the LocalDate, returning a new LocalDate instance.
     */
    static LocalDate minus(final LocalDate self, int days) {
        self.minusDays(days)
    }

    /**
     * Returns a TemporalAmount equivalent to the time between this LocalDate (inclusive) and the provided LocalDate (exclusive).
     */
    static Period minus(final LocalDate self, LocalDate other) {
        Period.between(self, other)
    }
 
    /**
     * Checks if the specified java.util.Calendar field is supported.
     * This checks if this date can be queried for the specified field. If false, then calling the getAt method will throw an exception.
     * Note that the supported Calendar fields are a subset of the supported ChronoField values.
     */
    static boolean isSupported(final LocalDate self, int calendarField) {
        TemporalField temporalField = calendarToTemporalField[calendarField]
        temporalField != null && self.isSupported(temporalField)
    }

    /**
     * Returns the field of the LocalDate corresponding to the provided java.util.Calendar field value. The returned values are
     * aligned as best as possible to their java.util.Calendar field values. For example, if you have a local date of January 1st, 2017
     * and call this method with Calendar.MONTH, then Calendar.JANUARY will be returned, even though this integer value is not identical
     * to the value of java.time.Month.JANUARY.getValue(). The same is true for the days of the week and java.time.DayOfWeek.
     */
    static getAt(final LocalDate self, int field) {
        if (!isSupported(self, field)) {
            throw new IllegalArgumentException("$field is an unrecognized or unsupported java.util.Calendar field value.")
        }
        switch (field) {
            case Calendar.DAY_OF_WEEK:
                return self.dayOfWeek.calendarValue()
            case Calendar.MONTH:
                return self.month.calendarValue()
            // XXX support Calendar.ERA for non-GregorianCalendar?
            default:
                return self.get(calendarToTemporalField[field])
        }
    }

    /**
     * Returns the value corresponding to the given TemporalField, provided it is supported by LocalDate as per its isSupported method.
     */
    static getAt(final LocalDate self, TemporalField field) {
        LONG_TYPED_FIELDS.contains(field) ? self.getLong(field) : self.get(field)
    }

    /**
     * Returns {@code getDayOfMonth()}. Note that even without this extension method, localDate.day works because day is a field in LocalDate.
     */
    static int getDay(final LocalDate self) {
        self.dayOfMonth
    }    

    /**
     * Returns a LocalDateTime of this LocalDate and the given LocalTime.
     */
    static LocalDateTime leftShift(final LocalDate self, LocalTime time) {
        LocalDateTime.of(self, time)
    }

    /**
     * Returns an OffsetDateTime of this LocalDate and the given OffsetTime.
     */
    static OffsetDateTime leftShift(final LocalDate self, OffsetTime time) {
        time.atDate(self)
    }

    /**
     * Converts a LocalDate to a (mostly) equivalent instance of java.util.Date. The time value of the returned Date is cleared,
     * and the TimeZone and Locale are set to system defaults unless explicitly specified.
     */
    static Date toDate(final LocalDate self, TimeZone timeZone = null, Locale locale = null) {
        toCalendar(self, timeZone, locale).time
    }

    /**
     * Converts a LocalDate to a (mostly) equivalent instance of java.util.Calendar. The time value of the returned Calendar is cleared,
     * and the TimeZone and Locale are set to system defaults unless explicitly specified.
     */
    static Calendar toCalendar(final LocalDate self, TimeZone timeZone = null, Locale locale = null) {
        int day = self.dayOfMonth
        int month = self.monthValue - 1
        int year = self.year
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