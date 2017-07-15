package bdkosher.goodtimes

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.MonthDay
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Year
import java.time.YearMonth
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Static parse extension methods for java.time types
 */
class ParseMethodsStaticExtension {

    /**
     * Parse the given text as a LocalDate using the provided DateTimeFormatter format pattern.
     */
    static LocalDate parse(final LocalDate self, CharSequence text, String format) {
        LocalDate.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as a LocalDate using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static LocalDate parse(final LocalDate self, CharSequence text, String format, ZoneId zone) {
        LocalDate.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }    

    /**
     * Parse the given text as a LocalDateTime using the provided DateTimeFormatter format pattern.
     */
    static LocalDateTime parse(final LocalDateTime self, CharSequence text, String format) {
        LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as a LocalDateTime using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static LocalDateTime parse(final LocalDateTime self, CharSequence text, String format, ZoneId zone) {
        LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }

    /**
     * Parse the given text as a LocalTime using the provided DateTimeFormatter format pattern.
     */
    static LocalTime parse(final LocalTime self, CharSequence text, String format) {
        LocalTime.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as a LocalTime using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static LocalTime parse(final LocalTime self, CharSequence text, String format, ZoneId zone) {
        LocalTime.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }

    /**
     * Parse the given text as a MonthDay using the provided DateTimeFormatter format pattern.
     */
    static MonthDay parse(final MonthDay self, CharSequence text, String format) {
        MonthDay.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as a MonthDay using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static MonthDay parse(final MonthDay self, CharSequence text, String format, ZoneId zone) {
        MonthDay.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }

    /**
     * Parse the given text as an OffsetDateTime using the provided DateTimeFormatter format pattern.
     */
    static OffsetDateTime parse(final OffsetDateTime self, CharSequence text, String format) {
        OffsetDateTime.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as an OffsetDateTime using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static OffsetDateTime parse(final OffsetDateTime self, CharSequence text, String format, ZoneId zone) {
        OffsetDateTime.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }

    /**
     * Parse the given text as an OffsetTime using the provided DateTimeFormatter format pattern.
     */
    static OffsetTime parse(final OffsetTime self, CharSequence text, String format) {
        OffsetTime.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as an OffsetTime using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static OffsetTime parse(final OffsetTime self, CharSequence text, String format, ZoneId zone) {
        OffsetTime.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }

    /**
     * Parse the given text as a Year using the provided DateTimeFormatter format pattern.
     */
    static Year parse(final Year self, CharSequence text, String format) {
        Year.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as a Year using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static Year parse(final Year self, CharSequence text, String format, ZoneId zone) {
        Year.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }

    /**
     * Parse the given text as a YearMonth using the provided DateTimeFormatter format pattern.
     */
    static YearMonth parse(final YearMonth self, CharSequence text, String format) {
        YearMonth.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as a YearMonth using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static YearMonth parse(final YearMonth self, CharSequence text, String format, ZoneId zone) {
        YearMonth.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }

    /**
     * Parse the given text as a ZonedDateTime using the provided DateTimeFormatter format pattern.
     */
    static ZonedDateTime parse(final ZonedDateTime self, CharSequence text, String format) {
        ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(format))
    }

    /**
     * Parse the given text as a ZonedDateTime using the provided DateTimeFormatter format pattern and ZoneId.
     */
    static ZonedDateTime parse(final ZonedDateTime self, CharSequence text, String format, ZoneId zone) {
        ZonedDateTime.parse(text, DateTimeFormatter.ofPattern(format).withZone(zone))
    }
}