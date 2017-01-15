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

    static toLocalDate(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalDate()
    }    

    static toLocalTime(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self, zoneId).toLocalTime()
    }

    static toLocalTime(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalTime()
    }    

    static toLocalDateTime(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self, zoneId).toLocalDateTime()
    }

    static toLocalDateTime(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalDateTime()
    }    
    
    static toZonedDateTime(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toInstant(self).atZone(zoneId)
    }

    static toOffsetDateTime(final Date self, ZoneOffset offset) {
        toInstant(self).atOffset(offset)
    }

    static toInstant(final Date self) {
        Instant.ofEpochMilli(self.time)
    }

    static toLocalDate(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toLocalDate(self.time, zoneId)
    }

    static toLocalDate(final Calendar self, ZoneOffset offset) {
        toLocalDate(self.time, offset)
    }    

    static toLocalTime(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toLocalTime(self.time, zoneId)
    }

    static toLocalTime(final Calendar self, ZoneOffset offset) {
        toLocalTime(self.time, offset)
    }    

    static toLocalDateTime(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toLocalDateTime(self.time, zoneId)
    }

    static toLocalDateTime(final Calendar self, ZoneOffset offset) {
        toLocalDateTime(self.time, offset)
    }    
    
    static toZonedDateTime(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self.time, zoneId)
    }

    static toOffsetDateTime(final Calendar self, ZoneOffset offset) {
        toOffsetDateTime(self.time, offset)
    }    

    static toInstant(final Calendar self) {
        toInstant(self.time)
    }
}