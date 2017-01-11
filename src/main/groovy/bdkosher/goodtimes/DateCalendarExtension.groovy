package bdkosher.goodtimes

import java.time.*

/**
 * Adds methods to Date and Calendar instances for converting values into Java 8 Date/Time equivalents.
 */
class DateCalendarExtension {

    private static final DEFAULT_ZONE_ID = ZoneId.systemDefault()

    static toLocalDate(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self, zoneId).toLocalDate()
    }

    static toLocalTime(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self, zoneId).toLocalTime()
    }

    static toLocalDateTime(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self, zoneId).toLocalDateTime()
    }
    
    static toZonedDateTime(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toInstant(self).atZone(zoneId)
    }

    static toInstant(final Date self) {
        Instant.ofEpochMilli(self.time)
    }

    static toLocalDate(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toLocalDate(self.time)
    }

    static toLocalTime(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toLocalTime(self.time)
    }

    static toLocalDateTime(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toLocalDateTime(self.time)
    }
    
    static toZonedDateTime(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self.time)
    }

    static toInstant(final Calendar self) {
        toInstant(self.time)
    }
}