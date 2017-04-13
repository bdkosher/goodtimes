package bdkosher.goodtimes

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.*

import groovy.transform.PackageScope

/**
 * Aims to mimic the Groovy JDK extension methods available for java.util.Date and java.util.Calendar instances.
 */
class LocalDateTimeExtension {

    /* LocalDateTime-applicable Calendar fields mapped to their equivalent java.time TemporalField */
    @PackageScope
    static final Map<Integer, TemporalField> calendarToTemporalField = [:] << LocalDateExtension.calendarToTemporalField << LocalTimeExtension.calendarToTemporalField

    /**
     * Iterates from this LocalDateTime down to the given LocalDateTime, inclusive, decrementing by one second each time.
     */
    static void downto(final LocalDateTime self, LocalDateTime to, Closure closure) {
        if (to > self) {
            throw new GroovyRuntimeException("The argument ($to) to downto() cannot be later than the value ($self) it's called on.")
        }
        def paramTypes = closure.parameterTypes
        boolean acceptsDate = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(LocalDateTime)
        def from = self
        while (from >= to) {
            acceptsDate ? closure(from) : closure()
            from = from - 1
        }
    }

    /**
     * Iterates from this LocalDateTime down to the given LocalDateTime, inclusive, decrementing by one second each time.
     */
    static void upto(final LocalDateTime self, LocalDateTime to, Closure closure) {
        if (to < self) {
            throw new GroovyRuntimeException("The argument ($to) to upto() cannot be earlier than the value ($self) it's called on.")
        }
        def paramTypes = closure.parameterTypes
        boolean acceptsDate = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(LocalDateTime)
        def from = self
        while (from <= to) {
            acceptsDate ? closure(from) : closure()
            from = from + 1
        }
    }    

    /**
     * Formats the LocalDateTime with the given pattern and optional locale. The default Locale is used if none is provided.
     */
    static String format(final LocalDateTime self, String pattern, Locale locale = Locale.default) {
        self.format(DateTimeFormatter.ofPattern(pattern, locale))
    }

    /**
     * Formats the LocalDateTime in the localized datetime style.
     */
    static String format(final LocalDateTime self, FormatStyle dateStyle) {
        self.format(DateTimeFormatter.ofLocalizedDateTime(dateStyle))
    }

    /**
     * Return a string representation of this datetime according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getDateTimeString(final LocalDateTime self) {
        format(self, FormatStyle.SHORT)
    }    

    /**
     * Return a string representation of the date portion of this datetime according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getDateString(final LocalDateTime self) {
        self.toLocalDate().dateString
    }

    /**
     * Return a string representation of the time portion of this date according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getTimeString(final LocalDateTime self) {
        self.toLocalTime().timeString
    }    

    /**
     * The next second.
     */
    static LocalDateTime next(final LocalDateTime self) {
        plus(self, 1)
    }

    /**
     * The previous second.
     */
    static LocalDateTime previous(final LocalDateTime self) {
        minus(self, 1)
    }    

    /**
     * Adds the given number of seconds to the LocalDateTime, returning a new LocalDateTime instance.
     */
    static LocalDateTime plus(final LocalDateTime self, int seconds) {
        self.plusSeconds(seconds)
    }

    /**
     * Subtracts the given number of seconds to the LocalDateTime, returning a new LocalDateTime instance.
     */
    static LocalDateTime minus(final LocalDateTime self, int seconds) {
        self.minusSeconds(seconds)
    }

    /**
     * Returns a Duration equivalent to the time between this LocalTime (inclusive) and the provided LocalTime (exclusive).
     */
    static Duration minus(final LocalDateTime self, LocalDateTime other) {
        Duration.between(self, other)
    }    

    /**
     * Checks if the specified java.util.Calendar field is supported.
     * This checks if this date can be queried for the specified field. If false, then calling the getAt method will throw an exception.
     * Note that the supported Calendar fields are a subset of the supported ChronoField values.
     */
    static boolean isSupported(final LocalDateTime self, int calendarField) {
        TemporalField temporalField = calendarToTemporalField[calendarField]
        temporalField != null && self.isSupported(temporalField)
    }

    /**
     * Returns the field of the LocalDateTime corresponding to the provided java.util.Calendar field value. The returned values are
     * aligned as best as possible to their java.util.Calendar field values. For example, if you have a local date of January 1st, 2017
     * and call this method with Calendar.MONTH, then Calendar.JANUARY will be returned, even though this integer value is not identical
     * to the value of java.time.Month.JANUARY.getValue(). The same is true for the days of the week and java.time.DayOfWeek.
     */
    static getAt(final LocalDateTime self, int field) {
        if (!isSupported(self, field)) {
            throw new IllegalArgumentException("$field is an unrecognized or unsupported java.util.Calendar field value.")
        }
        if (LocalDateExtension.calendarToTemporalField.keySet().contains(field)) {
            self.toLocalDate().getAt(field)
        } else {
            self.toLocalTime().getAt(field)
        }
    }

    /**
     * Returns the value corresponding to the given TemporalField, provided it is supported by LocalDateTime as per its isSupported method.
     */
    static getAt(final LocalDateTime self, TemporalField field) {
        LocalDateExtension.LONG_TYPED_FIELDS.contains(field) || LocalTimeExtension.LONG_TYPED_FIELDS.contains(field) ? self.getLong(field) : self.get(field)
    }

    /**
     * Returns {@code getDayOfMonth()}.
     */
    static int getDay(final LocalDateTime self) {
        self.dayOfMonth
    }

    /**
     * Returns an OffsetDateTime of this date time and the given zone offset.
     */
    static OffsetDateTime leftShift(final LocalDateTime self, ZoneOffset offset) {
        OffsetDateTime.of(self, offset)
    }

    /**
     * Returns a ZonedDateTime of this date time and the given zoneId.
     */
    static ZonedDateTime leftShift(final LocalDateTime self, ZoneId zone) {
        ZonedDateTime.of(self, zone)
    }

    /**
     * Returns a ZonedDateTime of this date time and the given time zone.
     */
    static ZonedDateTime leftShift(final LocalDateTime self, TimeZone timeZone) {
        leftShift(self, timeZone.toZoneId())
    }

    /**
     * Converts a LocalDateTime to a (mostly) equivalent instance of java.util.Date. The time value of the returned Date is cleared,
     * and the TimeZone and Locale are set to system defaults unless explicitly specified.
     */
    static Date toDate(final LocalDateTime self, TimeZone timeZone = null, Locale locale = null) {
        toCalendar(self, timeZone, locale).time
    }

    /**
     * Converts a LocalDateTime to a (mostly) equivalent instance of java.util.Calendar. The time value of the returned Calendar is cleared,
     * and the TimeZone and Locale are set to system defaults unless explicitly specified.
     */
    static Calendar toCalendar(final LocalDateTime self, TimeZone timeZone = null, Locale locale = null) {
        int day = self.dayOfMonth
        int month = self.monthValue - 1
        int year = self.year
        int milli = (self.nano - self.truncatedTo(ChronoUnit.SECONDS).nano) / 1e6
        Calendar cal = timeZone 
                ? (locale ? Calendar.getInstance(timeZone, locale) : Calendar.getInstance(timeZone)) 
                : (locale ? Calendar.getInstance(locale) : Calendar.instance)
        cal.with {
            set(Calendar.DATE, day)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
            set(Calendar.HOUR_OF_DAY, self.hour)
            set(Calendar.MINUTE, self.minute)
            set(Calendar.SECOND, self.second)
            set(Calendar.MILLISECOND, milli)
            delegate
        }
    }
}