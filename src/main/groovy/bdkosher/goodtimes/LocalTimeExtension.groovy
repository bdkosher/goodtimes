package bdkosher.goodtimes

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.*

import groovy.transform.PackageScope

/**
 * Aims to mimic the Groovy JDK extension methods available for java.util.Date and java.util.Calendar instances.
 */
class LocalTimeExtension {

    /* LocalTime-applicable Calendar fields mapped to their equivalent java.time TemporalField */
    @PackageScope
    static final Map<Integer, TemporalField> calendarToTemporalField = [
        (Calendar.HOUR): ChronoField.HOUR_OF_DAY,
        (Calendar.MINUTE): ChronoField.MINUTE_OF_HOUR,
        (Calendar.SECOND): ChronoField.SECOND_OF_MINUTE,
        (Calendar.MILLISECOND): ChronoField.MILLI_OF_SECOND
    ].asImmutable()

    @PackageScope
    static final Set<TemporalField> LONG_TYPED_FIELDS = EnumSet.of(ChronoField.MICRO_OF_DAY, ChronoField.NANO_OF_DAY).asImmutable()

    /**
     * Iterates from this LocalTime down to the given LocalTime, inclusive, decrementing by one second each time.
     */
    static void downto(final LocalTime self, LocalTime to, Closure closure) {
        if (to > self) {
            throw new GroovyRuntimeException("The argument ($to) to downto() cannot be later than the value ($self) it's called on.")
        }
        def paramTypes = closure.parameterTypes
        boolean acceptsDate = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(LocalTime)
        def from = self
        while (from >= to) {
            acceptsDate ? closure(from) : closure()
            from = from - 1
        }
    }

    /**
     * Iterates from this LocalTime down to the given LocalTime, inclusive, decrementing by one second each time.
     */
    static void upto(final LocalTime self, LocalTime to, Closure closure) {
        if (to < self) {
            throw new GroovyRuntimeException("The argument ($to) to upto() cannot be earlier than the value ($self) it's called on.")
        }
        def paramTypes = closure.parameterTypes
        boolean acceptsDate = paramTypes.length > 0 && paramTypes[0].isAssignableFrom(LocalTime)
        def from = self
        while (from <= to) {
            acceptsDate ? closure(from) : closure()
            from = from + 1
        }
    }    

    /**
     * Formats the LocalTime with the given pattern and optional locale. The default Locale is used if none is provided.
     */
    static String format(final LocalTime self, String pattern, Locale locale = Locale.default) {
        self.format(DateTimeFormatter.ofPattern(pattern, locale))
    }

    /**
     * Formats the LocalTime in the localized time style.
     */
    static String format(final LocalTime self, FormatStyle timeStyle) {
        self.format(DateTimeFormatter.ofLocalizedTime(timeStyle))
    }

    /**
     * Return a string representation of this time according to the locale-specific FormatStyle#SHORT default format.
     */
    static String getTimeString(final LocalTime self) {
        format(self, FormatStyle.SHORT)
    }

    /**
     * The next second.
     */
    static LocalTime next(final LocalTime self) {
        plus(self, 1)
    }

    /**
     * The previous second.
     */
    static LocalTime previous(final LocalTime self) {
        minus(self, 1)
    }    

    /**
     * Adds the given number of seconds to the LocalTime, returning a new LocalTime instance.
     */
    static LocalTime plus(final LocalTime self, int seconds) {
        self.plusSeconds(seconds)
    }

    /**
     * Subtracts the given number of seconds to the LocalTime, returning a new LocalTime instance.
     */
    static LocalTime minus(final LocalTime self, int seconds) {
        self.minusSeconds(seconds)
    }

    /**
     * Returns a TemporalAmount equivalent to the time between this LocalTime (inclusive) and the provided LocalTime (exclusive).
     */
    static Duration minus(final LocalTime self, LocalTime other) {
        Duration.between(self, other)
    }

    /**
     * Checks if the specified java.util.Calendar field is supported.
     * This checks if this date can be queried for the specified field. If false, then calling the getAt method will throw an exception.
     * Note that the supported Calendar fields are a subset of the supported ChronoField values.
     */
    static boolean isSupported(final LocalTime self, int calendarField) {
        TemporalField temporalField = calendarToTemporalField[calendarField]
        temporalField != null && self.isSupported(temporalField)
    }

    /**
     * Returns the field of the LocalTime corresponding to the provided java.util.Calendar field value.
     */
    static getAt(final LocalTime self, int field) {
        if (!isSupported(self, field)) {
            throw new IllegalArgumentException("$field is an unrecognized or unsupported java.util.Calendar field value.")
        }
        self.get(calendarToTemporalField[field])
    }

    /**
     * Returns the value corresponding to the given TemporarlField, provided it is supported by LocalTime as per its isSupported method.
     */
    static getAt(final LocalTime self, TemporalField field) {
        LONG_TYPED_FIELDS.contains(field) ? self.getLong(field) : self.get(field)
    }

    /**
     * Converts a LocalTime to a (mostly) equivalent instance of java.util.Date. The day-month-year value of the returned Date is now,
     * and the TimeZone and Locale are set to system defaults unless explicitly specified. Time is truncated to nearest millisecond.
     */
    static Date toDate(final LocalTime self, TimeZone timeZone = null, Locale locale = null) {
        toCalendar(self, timeZone, locale).time
    }

    /**
     * Converts a LocalTime to a (mostly) equivalent instance of java.util.Calendar. The time value of the returned Calendar is cleared,
     * and the TimeZone and Locale are set to system defaults unless explicitly specified. Time is truncated to nearest millisecond.
     */
    static Calendar toCalendar(final LocalTime self, TimeZone timeZone = null, Locale locale = null) {
        int milli = (self.nano - self.truncatedTo(ChronoUnit.SECONDS).nano) / 1e6
        Calendar cal = timeZone 
                ? (locale ? Calendar.getInstance(timeZone, locale) : Calendar.getInstance(timeZone)) 
                : (locale ? Calendar.getInstance(locale) : Calendar.instance)
        cal.with {
            set(Calendar.HOUR_OF_DAY, self.hour)
            set(Calendar.MINUTE, self.minute)
            set(Calendar.SECOND, self.second)
            set(Calendar.MILLISECOND, milli)
            delegate
        }
    }
}