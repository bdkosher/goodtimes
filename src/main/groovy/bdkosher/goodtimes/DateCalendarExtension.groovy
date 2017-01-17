package bdkosher.goodtimes

import java.time.*

/**
 * Extension methods for converting or inspecting java.util.Date and java.util.Calendar instances as Java 8 Date/Time types.
 */
class DateCalendarExtension {

    private static final DEFAULT_ZONE_ID = ZoneId.systemDefault()

    /**
     * Converts the Date to a Calendar instance.
     */
    static Calendar toCalendar(final Date date) {
        def cal = Calendar.instance
        cal.time = date
        cal
    }

    /**
     * Returns the Time Zone of the Date as a java.time.ZoneOffset.
     */
    static ZoneOffset getZoneOffset(final Date date) {
        getZoneOffset(toCalendar(date))
    }

    /**
     * Returns the Time Zone offset of the Calendar as a java.time.ZoneOffset.
     */
    static ZoneOffset getZoneOffset(final Calendar cal) {
        int offsetMs = cal.get(Calendar.ZONE_OFFSET) 
        offsetMs += cal.get(Calendar.DST_OFFSET)
        ZoneOffset.ofTotalSeconds(offsetMs / 1000)
    }

    /**
     * Returns the Time Zone of the Date as a java.time.ZoneId.
     */
    static ZoneId getZoneId(final Date date) {
        getZoneId(toCalendar(date))
    }

    /**
     * Returns the Time Zone of the Calendar as a java.time.ZoneId.
     */
    static ZoneId getZoneId(final Calendar cal) {
        cal.timeZone.toZoneId()
    }

    /**
     * Converts the Date to a LocalDate, using the Date's own Time Zone to adjust the LocalDate accordingly.
     *
     * If the Date is of a different Time Zone than the system Time Zone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     *
     * An optional ZoneId may be passed to allow the returned LocalDate to be converted as if it were at a
     * Time Zone other than the system default.
     */
    static toLocalDate(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self, zoneId).toLocalDate()
    }

    /**
     * Converts the Date to a LocalDate, using the provided ZoneOffset to adjust the LocalDate accordingly.
     *
     * If the Date's Time Zone offset differ from the provided ZoneOffset and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static toLocalDate(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalDate()
    }

    /**
     * Converts the Date to a LocalDate, using the provided TimeZone to adjust the LocalDate accordingly.
     *
     * If the Date's Time Zone offset differ from the provided TimeZone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static toLocalDate(final Date self, TimeZone timeZone) {
        toLocalDate(self, timeZone.zoneId)
    }    

    static toLocalTime(final Date self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toZonedDateTime(self, zoneId).toLocalTime()
    }

    static toLocalTime(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalTime()
    }

    static toLocalTime(final Date self, TimeZone timeZone) {
        toLocalTime(self, timeZone.zoneId)
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


    /**
     * Converts the Calendar to a LocalDate, using the Date's own Time Zone to adjust the LocalDate accordingly.
     *
     * If the Calendar is of a different Time Zone than the system Time Zone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     *
     * An optional ZoneId may be passed to allow the returned LocalDate to be converted as if it were at a
     * Time Zone other than the system default.
     */
    static toLocalDate(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toLocalDate(self.time, zoneId)
    }

    /**
     * Converts the Calendar to a LocalDate, using the provided ZoneOffset to adjust the LocalDate accordingly.
     *
     * If the Calendar's Time Zone offset differ from the provided ZoneOffset and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static toLocalDate(final Calendar self, ZoneOffset offset) {
        toLocalDate(self.time, offset)
    }

    /**
     * Converts the Calendar to a LocalDate, using the provided TimeZone to adjust the LocalDate accordingly.
     *
     * If the Calendar's Time Zone offset differ from the provided TimeZone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static toLocalDate(final Calendar self, TimeZone timeZone) {
        toLocalDate(self.time, timeZone)
    }    

    static toLocalTime(final Calendar self, ZoneId zoneId = DEFAULT_ZONE_ID) {
        toLocalTime(self.time, zoneId)
    }

    static toLocalTime(final Calendar self, ZoneOffset offset) {
        toLocalTime(self.time, offset)
    }

    static toLocalTime(final Calendar self, TimeZone timeZone) {
        toLocalTime(self.time, timeZone)
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