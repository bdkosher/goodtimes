package bdkosher.goodtimes

import java.text.SimpleDateFormat
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalField
import groovy.transform.PackageScope

/**
 * Extension methods for java.time.ZonedDateTime
 *
 * Aims to mimic the Groovy JDK extension methods available for java.util.Date and java.util.Calendar instances.
 */
class ZonedDateTimeExtension {

    /**
     * Iterates from this ZonedDateTime down to the given ZonedDateTime, inclusive, decrementing by one second each time.
     */
    static void downto(final ZonedDateTime self, ZonedDateTime to, Closure closure) {
        if (to > self) {
            throw new GroovyRuntimeException("The argument ($to) to downto() cannot be later than the value ($self) it's called on.")
        }
        (self..to).each(closure)
    }

    /**
     * Iterates from this ZonedDateTime down to the given ZonedDateTime, inclusive, decrementing by one second each time.
     */
    static void upto(final ZonedDateTime self, ZonedDateTime to, Closure closure) {
        if (to < self) {
            throw new GroovyRuntimeException("The argument ($to) to upto() cannot be earlier than the value ($self) it's called on.")
        }
        (self..to).each(closure)
    }    

    /**
     * Formats the ZonedDateTime with the given pattern and optional locale. The default Locale is used if none is provided.
     */
    static String format(final ZonedDateTime self, String pattern, Locale locale = Locale.default) {
        self.format(DateTimeFormatter.ofPattern(pattern, locale))
    }

    /**
     * Formats the ZonedDateTime in the localized datetime style.
     */
    static String format(final ZonedDateTime self, FormatStyle dateStyle) {
        self.format(DateTimeFormatter.ofLocalizedDateTime(dateStyle))
    }

    /**
     * Return a string representation of this datetime according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getDateTimeString(final ZonedDateTime self) {
        format(self, FormatStyle.SHORT)
    }    

    /**
     * Return a string representation of the date portion of this datetime according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getDateString(final ZonedDateTime self) {
        self.toLocalDate().dateString
    }

    /**
     * Return a string representation of the time portion of this date according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getTimeString(final ZonedDateTime self) {
        self.toLocalTime().timeString
    }

    /**
     * Sets the time-related fields of the ZonedDateTime instance to 0.
     */
    static ZonedDateTime clearTime(final ZonedDateTime self) {
        self.truncatedTo(ChronoUnit.DAYS)
    }

    /**
     * The next second.
     */
    static ZonedDateTime next(final ZonedDateTime self) {
        plus(self, 1)
    }

    /**
     * The previous second.
     */
    static ZonedDateTime previous(final ZonedDateTime self) {
        minus(self, 1)
    }    

    /**
     * Adds the given number of seconds to the ZonedDateTime, returning a new ZonedDateTime instance.
     */
    static ZonedDateTime plus(final ZonedDateTime self, int seconds) {
        self.plusSeconds(seconds)
    }

    /**
     * Subtracts the given number of seconds to the ZonedDateTime, returning a new ZonedDateTime instance.
     */
    static ZonedDateTime minus(final ZonedDateTime self, int seconds) {
        self.minusSeconds(seconds)
    }

    /**
     * Returns a Duration equivalent to the time between this LocalTime (inclusive) and the provided LocalTime (exclusive).
     */
    static Duration rightShift(final ZonedDateTime self, ZonedDateTime other) {
        Duration.between(self, other)
    }    

    /**
     * Checks if the specified java.util.Calendar field is supported.
     * This checks if this date can be queried for the specified field. If false, then calling the getAt method will throw an exception.
     * Note that the supported Calendar fields are a subset of the supported ChronoField values.
     */
    static boolean isSupported(final ZonedDateTime self, int calendarField) {
        calendarField == Calendar.ZONE_OFFSET || LocalDateTimeExtension.isSupported(self.toLocalDateTime(), calendarField)
    }

    /**
     * Returns the field of the ZonedDateTime corresponding to the provided java.util.Calendar field value. The returned values are
     * aligned as best as possible to their java.util.Calendar field values. For example, if you have a local date of January 1st, 2017
     * and call this method with Calendar.MONTH, then Calendar.JANUARY will be returned, even though this integer value is not identical
     * to the value of java.time.Month.JANUARY.getValue(). The same is true for the days of the week and java.time.DayOfWeek.
     */
    static getAt(final ZonedDateTime self, int field) {
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
     * Returns the value corresponding to the given TemporalField, provided it is supported by ZonedDateTime as per its isSupported method.
     */
    static getAt(final ZonedDateTime self, TemporalField field) {
        LocalDateExtension.LONG_TYPED_FIELDS.contains(field) || LocalTimeExtension.LONG_TYPED_FIELDS.contains(field) ? self.getLong(field) : self.get(field)
    }

    /**
     * Returns {@code getDayOfMonth()}.
     */
    static int getDay(final ZonedDateTime self) {
        self.dayOfMonth
    }

    /**
     * Converts a ZonedDateTime to a (mostly) equivalent instance of java.util.Date, truncated to the nearest millisecond. Note that the time of 
     * returned Date will be adjusted to the equivalent in the default TimeZone rather than the TimeZone of the ZonedDateTime.
     */
    static Date toDate(final ZonedDateTime self) {
        toCalendar(self).time
    }

    /**
     * Converts a ZonedDateTime to a (mostly) equivalent instance of java.util.Calendar. Time is truncated to the nearest millisecond.
     * The Locale is set to the system default unless explicitly specified.
     */
    static Calendar toCalendar(final ZonedDateTime self, Locale locale = null) {
        int day = self.dayOfMonth
        int month = self.monthValue - 1
        int year = self.year
        int milli = (self.nano - self.truncatedTo(ChronoUnit.SECONDS).nano) / 1e6
        TimeZone timeZone = self.zone.timeZone
        Calendar cal = locale ? Calendar.getInstance(timeZone, locale) : Calendar.getInstance(timeZone)
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