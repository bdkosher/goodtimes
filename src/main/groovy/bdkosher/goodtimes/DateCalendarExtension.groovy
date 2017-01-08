package bdkosher.goodtimes

import java.time.*

class DateCalendarExtension {

    static toLocalDate(final Date self, ZoneId zoneId = ZoneId.systemDefault()) {
        toZonedDateTime(self, zoneId).toLocalDate()
    }

    static toLocalTime(final Date self, ZoneId zoneId = ZoneId.systemDefault()) {
        toZonedDateTime(self, zoneId).toLocalTime()
    }

    static toLocalDateTime(final Date self, ZoneId zoneId = ZoneId.systemDefault()) {
        toZonedDateTime(self, zoneId).toLocalDateTime()
    }
    
    static toZonedDateTime(final Date self, ZoneId zoneId = ZoneId.systemDefault()) {
        toInstant(self).atZone(zoneId)
    }

    static toInstant(final Date self) {
        Instant.ofEpochMilli(self.time)
    }

    static toLocalDate(final Calendar self, ZoneId zoneId = ZoneId.systemDefault()) {
        toLocalDate(self.time)
    }

    static toLocalTime(final Calendar self, ZoneId zoneId = ZoneId.systemDefault()) {
        toLocalTime(self.time)
    }

    static toLocalDateTime(final Calendar self, ZoneId zoneId = ZoneId.systemDefault()) {
        toLocalDateTime(self.time)
    }
    
    static toZonedDateTime(final Calendar self, ZoneId zoneId = ZoneId.systemDefault()) {
        toZonedDateTime(self.time)
    }

    static toInstant(final Calendar self) {
        toInstant(self.time)
    }
}