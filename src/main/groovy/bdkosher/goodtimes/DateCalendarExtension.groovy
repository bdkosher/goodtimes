package bdkosher.goodtimes

import java.time.*

/**
 * Extension methods for converting or inspecting java.util.Date and java.util.Calendar instances as Java 8 Date/Time types.
 */
class DateCalendarExtension {

    private static final DEFAULT_ZONE_ID = ZoneId.systemDefault()

    /**
     * Converts the given Date to a Calendar instance.
     */
    static Calendar toCalendar(final Date date) {
        def cal = Calendar.instance
        cal.time = date
        cal
    }

    /**
     * Returns the TimeZone of the given Date as a java.time.ZoneOffset.
     */
    static ZoneOffset getZoneOffset(final Date date) {
        getZoneOffset(toCalendar(date))
    }

    /**
     * Returns the TimeZone of the given Calendar as a java.time.ZoneOffset.
     */
    static ZoneOffset getZoneOffset(final Calendar cal) {
        int offsetMs = cal.get(Calendar.ZONE_OFFSET) 
        offsetMs += cal.get(Calendar.DST_OFFSET)
        ZoneOffset.ofTotalSeconds(1000 * offsetMs)
    }

    /**
     * Returns the TimeZone of the given Date as a java.time.ZoneId.
     */
    static ZoneId getZoneId(final Date date) {
        getZoneId(toCalendar(date))
    }

    /**
     * Returns the TimeZone of the given Calendar as a java.time.ZoneId.
     */
    static ZoneId getZoneId(final Calendar cal) {
        cal.timeZone.toZoneId()
    }

    /**
     * Converts the Date to a LocalDate, using the Date's own TimeZone to adjust the LocalDate accordingly.
     * If the Date is of a different TimeZone than the system TimeZone, then the year/month/day of
     * the generated LocalDate may differ from the year/month/day of the Date, depending on the difference
     * between the TimeZone offsets and the time value of the Date instance.
     */
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
        self.toInstant().atZone(zoneId)
    }

    static toOffsetDateTime(final Date self, ZoneOffset offset) {
        self.toInstant().atOffset(offset)
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
        self.time.toInstant()
    }
}