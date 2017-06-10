package bdkosher.goodtimes

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalField

/**
 * Extension methods for java.time.OffsetDateTime
 *
 * Aims to mimic the Groovy JDK extension methods available for java.util.Date and java.util.Calendar instances.
 */
class OffsetDateTimeExtension {

    /**
     * Iterates from this OffsetDateTime down to the given OffsetDateTime, inclusive, decrementing by one second each time.
     */
    static void downto(final OffsetDateTime self, OffsetDateTime to, Closure closure) {
        if (to > self) {
            throw new GroovyRuntimeException("The argument ($to) to downto() cannot be later than the value ($self) it's called on.")
        }
        def paramTypes = closure.parameterTypes
        boolean acceptsDate = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(OffsetDateTime)
        def from = self
        while (from >= to) {
            acceptsDate ? closure(from) : closure()
            from = from - 1
        }
    }

    /**
     * Iterates from this OffsetDateTime down to the given OffsetDateTime, inclusive, decrementing by one second each time.
     */
    static void upto(final OffsetDateTime self, OffsetDateTime to, Closure closure) {
        if (to < self) {
            throw new GroovyRuntimeException("The argument ($to) to upto() cannot be earlier than the value ($self) it's called on.")
        }
        def paramTypes = closure.parameterTypes
        boolean acceptsDate = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(OffsetDateTime)
        def from = self
        while (from <= to) {
            acceptsDate ? closure(from) : closure()
            from = from + 1
        }
    }    

    /**
     * Formats the OffsetDateTime with the given pattern and optional locale. The default Locale is used if none is provided.
     */
    static String format(final OffsetDateTime self, String pattern, Locale locale = Locale.default) {
        self.format(DateTimeFormatter.ofPattern(pattern, locale))
    }

    /**
     * Formats the OffsetDateTime in the localized datetime style.
     */
    static String format(final OffsetDateTime self, FormatStyle dateStyle) {
        self.format(DateTimeFormatter.ofLocalizedDateTime(dateStyle))
    }

    /**
     * Return a string representation of this datetime according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getDateTimeString(final OffsetDateTime self) {
        format(self, FormatStyle.SHORT)
    }    

    /**
     * Return a string representation of the date portion of this datetime according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getDateString(final OffsetDateTime self) {
        self.toLocalDate().dateString
    }

    /**
     * Return a string representation of the time portion of this date according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getTimeString(final OffsetDateTime self) {
        self.toLocalTime().timeString
    }

    /**
     * Sets the time-related fields of the OffsetDateTime instance to 0.
     */
    static OffsetDateTime clearTime(final OffsetDateTime self) {
        self.truncatedTo(ChronoUnit.DAYS)
    }

    /**
     * The next second.
     */
    static OffsetDateTime next(final OffsetDateTime self) {
        plus(self, 1)
    }

    /**
     * The previous second.
     */
    static OffsetDateTime previous(final OffsetDateTime self) {
        minus(self, 1)
    }    

    /**
     * Adds the given number of seconds to the OffsetDateTime, returning a new OffsetDateTime instance.
     */
    static OffsetDateTime plus(final OffsetDateTime self, int seconds) {
        self.plusSeconds(seconds)
    }

    /**
     * Subtracts the given number of seconds to the OffsetDateTime, returning a new OffsetDateTime instance.
     */
    static OffsetDateTime minus(final OffsetDateTime self, int seconds) {
        self.minusSeconds(seconds)
    }

    /**
     * Returns a Duration equivalent to the time between this LocalTime (inclusive) and the provided LocalTime (exclusive).
     */
    static Duration minus(final OffsetDateTime self, OffsetDateTime other) {
        Duration.between(self, other)
    }    

    /**
     * Checks if the specified java.util.Calendar field is supported.
     * This checks if this date can be queried for the specified field. If false, then calling the getAt method will throw an exception.
     * Note that the supported Calendar fields are a subset of the supported ChronoField values.
     */
    static boolean isSupported(final OffsetDateTime self, int calendarField) {
        calendarField == Calendar.ZONE_OFFSET || LocalDateTimeExtension.isSupported(self.toLocalDateTime(), calendarField)
    }

    /**
     * Returns the field of the OffsetDateTime corresponding to the provided java.util.Calendar field value. The returned values are
     * aligned as best as possible to their java.util.Calendar field values. For example, if you have a local date of January 1st, 2017
     * and call this method with Calendar.MONTH, then Calendar.JANUARY will be returned, even though this integer value is not identical
     * to the value of java.time.Month.JANUARY.getValue(). The same is true for the days of the week and java.time.DayOfWeek.
     */
    static getAt(final OffsetDateTime self, int field) {
        if (!isSupported(self, field)) {
            throw new IllegalArgumentException("$field is an unrecognized or unsupported java.util.Calendar field value.")
        }
        if (field == Calendar.ZONE_OFFSET) {
            self.get(ChronoField.OFFSET_SECONDS) * 1000 // ZONE_OFFSET is in milliseconds
        } else {
            LocalDateTimeExtension.getAt(self.toLocalDateTime(), field)
        }
    }

    /**
     * Returns the value corresponding to the given TemporalField, provided it is supported by OffsetDateTime as per its isSupported method.
     */
    static getAt(final OffsetDateTime self, TemporalField field) {
        LocalDateExtension.LONG_TYPED_FIELDS.contains(field) || LocalTimeExtension.LONG_TYPED_FIELDS.contains(field) ? self.getLong(field) : self.get(field)
    }

    /**
     * Returns {@code getDayOfMonth()}.
     */
    static int getDay(final OffsetDateTime self) {
        self.dayOfMonth
    }

    /**
     * Converts a OffsetDateTime to a (mostly) equivalent instance of java.util.Date.
     * The Locale is set to system defaults unless explicitly specified.
     */
    static Date toDate(final OffsetDateTime self, Locale locale = null) {
        toCalendar(self, locale).time
    }

    /**
     * Converts a OffsetDateTime to a (mostly) equivalent instance of java.util.Calendar.
     * The Locale is set to system defaults unless explicitly specified.
     */
    static Calendar toCalendar(final OffsetDateTime self, Locale locale = null) {
        self.toZonedDateTime().toCalendar(locale)
    }
}