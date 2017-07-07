package bdkosher.goodtimes

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.time.temporal.TemporalField

/**
 * Extension methods for java.time.ZonedId
 */
class ZoneIdExtension {

    /**
     * Returns a java.time.TimeZone equivalent to this ZoneId.
     */
    static TimeZone getTimeZone(final ZoneId self) {
        TimeZone.getTimeZone(self)
    }

    /**
     * Returns a the display name using the FULL TextStyle. If an explicit Locale is not provided, the default is assumed.
     */
    static String getFullName(final ZoneId self, Locale locale = Locale.default) {
        self.getDisplayName(TextStyle.FULL, locale)
    }

    /**
     * Returns a the display name using the SHORT TextStyle. If an explicit Locale is not provided, the default is assumed.
     */
    static String getShortName(final ZoneId self, Locale locale = Locale.default) {
        self.getDisplayName(TextStyle.SHORT, locale)
    }

    /**
     * Returns the ZoneOffset for this ZoneId as of the given Instant. If no Instant is provided, returns the ZoneOffset as of now.
     */
    static ZoneOffset getOffset(final ZoneId self, Instant instant = Instant.now()) {
        self.rules.getOffset(instant)
    }

    /**
     * Returns a ZonedDateTime of this ZoneId and the given date time.
     */
    static ZonedDateTime leftShift(final ZoneId self, LocalDateTime dateTime) {
        ZonedDateTime.of(dateTime, self)
    }
}