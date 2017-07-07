package bdkosher.goodtimes

import java.time.Duration
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalField
import groovy.transform.PackageScope

/**
 * Extension methods for java.time.OffsetTime
 *
 * Aims to mimic the Groovy JDK extension methods available for java.util.Date and java.util.Calendar instances.
 */
class OffsetTimeExtension {

    /**
     * Iterates from this OffsetTime down to the given OffsetTime, inclusive, decrementing by one second each time.
     */
    static void downto(final OffsetTime self, OffsetTime to, Closure closure) {
        if (to > self) {
            throw new GroovyRuntimeException("The argument ($to) to downto() cannot be later than the value ($self) it's called on.")
        }
        (self..to).each(closure)
    }

    /**
     * Iterates from this OffsetTime down to the given OffsetTime, inclusive, decrementing by one second each time.
     */
    static void upto(final OffsetTime self, OffsetTime to, Closure closure) {
        if (to < self) {
            throw new GroovyRuntimeException("The argument ($to) to upto() cannot be earlier than the value ($self) it's called on.")
        }
        (self..to).each(closure)
    }

    /**
     * Formats the OffsetTime with the given pattern and optional locale. The default Locale is used if none is provided.
     */
    static String format(final OffsetTime self, String pattern, Locale locale = Locale.default) {
        self.format(DateTimeFormatter.ofPattern(pattern, locale))
    }

    /**
     * Formats the OffsetTime in the localized time style.
     */
    static String format(final OffsetTime self, FormatStyle timeStyle) {
        self.format(DateTimeFormatter.ofLocalizedTime(timeStyle))
    }

    /**
     * Return a string representation of this time according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getTimeString(final OffsetTime self) {
        format(self, FormatStyle.SHORT)
    }

    /**
     * The next second.
     */
    static OffsetTime next(final OffsetTime self) {
        plus(self, 1)
    }

    /**
     * The previous second.
     */
    static OffsetTime previous(final OffsetTime self) {
        minus(self, 1)
    }

    /**
     * Adds the given number of seconds to the OffsetTime, returning a new OffsetTime instance.
     */
    static OffsetTime plus(final OffsetTime self, int seconds) {
        self.plusSeconds(seconds)
    }

    /**
     * Subtracts the given number of seconds to the OffsetTime, returning a new OffsetTime instance.
     */
    static OffsetTime minus(final OffsetTime self, int seconds) {
        self.minusSeconds(seconds)
    }

    /**
     * Returns a TemporalAmount equivalent to the time between this OffsetTime (inclusive) and the provided OffsetTime (exclusive).
     */
    static Duration rightShift(final OffsetTime self, OffsetTime other) {
        Duration.between(self, other)
    }

    /**
     * Checks if the specified java.util.Calendar field is supported.
     * This checks if this date can be queried for the specified field. If false, then calling the getAt method will throw an exception.
     * Note that the supported Calendar fields are a subset of the supported ChronoField values.
     */
    static boolean isSupported(final OffsetTime self, int calendarField) {
        calendarField == Calendar.ZONE_OFFSET || LocalTimeExtension.isSupported(self.toLocalTime(), calendarField)
    }

    /**
     * Returns the field of the OffsetTime corresponding to the provided java.util.Calendar field value.
     */
    static getAt(final OffsetTime self, int field) {
        if (!isSupported(self, field)) {
            throw new IllegalArgumentException("$field is an unrecognized or unsupported java.util.Calendar field value.")
        }
        if (field == Calendar.ZONE_OFFSET) {
            self.get(ChronoField.OFFSET_SECONDS) * 1000 // ZONE_OFFSET is in milliseconds
        } else {
            LocalTimeExtension.getAt(self.toLocalTime(), field)
        }
    }

    /**
     * Returns the value corresponding to the given TemporalField, provided it is supported by OffsetTime as per its isSupported method.
     */
    static getAt(final OffsetTime self, TemporalField field) {
        LocalTimeExtension.LONG_TYPED_FIELDS.contains(field) ? self.getLong(field) : self.get(field)
    }

    /**
     * Returns a LocalDateTime of this OffsetTime and the given LocalDate.
     */
    static OffsetDateTime leftShift(final OffsetTime self, LocalDate date) {
        OffsetDateTime.of(date, self.toLocalTime(), self.offset)
    }

    /**
     * Converts a OffsetTime to a (mostly) equivalent instance of java.util.Date. The day-month-year value of the returned Date is now,
     * and the time is truncated to nearest millisecond.  Note that the time of returned Date will be adjusted to the equivalent in the
     * default TimeZone rather than the TimeZone of the ZonedDateTime.
     */
    static Date toDate(final OffsetTime self) {
        toCalendar(self).time
    }

    /**
     * Converts a OffsetTime to a (mostly) equivalent instance of java.util.Calendar. The day-month-year value of the returned Date is today
     * and the Locale is set to the system default unless explicitly specified. Time is truncated to nearest millisecond. The Calendar's
     * offset is truncated to the nearest minute.
     */
    static Calendar toCalendar(final OffsetTime self, Locale locale = null) {
        int milli = (self.nano - self.truncatedTo(ChronoUnit.SECONDS).nano) / 1e6
        TimeZone timeZone = ZoneOffsetExtension.toTimeZone(self.offset)
        Calendar cal = locale ? Calendar.getInstance(timeZone, locale) : Calendar.getInstance(timeZone)
        cal.with {
            set(Calendar.HOUR_OF_DAY, self.hour)
            set(Calendar.MINUTE, self.minute)
            set(Calendar.SECOND, self.second)
            set(Calendar.MILLISECOND, milli)
            delegate
        }
    }
}