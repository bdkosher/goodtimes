package bdkosher.goodtimes

import java.time.*

/**
 * Extension methods for converting or inspecting java.util.Date and java.util.Calendar instances as Java 8 Date/Time types.
 */
class DateCalendarExtension {

    /**
     * A mapping of Calendar constants representing days of the week to their corresponding java.time.DayOfWeek enum
     */
    static final Map<Integer, DayOfWeek> daysOfWeek = ['SUNDAY','MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY']
            .collectEntries { day -> [Calendar."$day", java.time.DayOfWeek."$day"] }

    /**
     * Returns the Time Zone of the Date as a java.time.ZoneOffset.
     */
    static ZoneOffset getZoneOffset(final Date self) {
        getZoneOffset(self.toCalendar())
    }

    /**
     * Returns the Time Zone offset of the Calendar as a java.time.ZoneOffset.
     */
    static ZoneOffset getZoneOffset(final Calendar self) {
        int offsetMs = self.get(Calendar.ZONE_OFFSET) 
        offsetMs += self.get(Calendar.DST_OFFSET)
        ZoneOffset.ofTotalSeconds(offsetMs / 1000)
    }

    /**
     * Returns the Time Zone of the Calendar as a java.time.ZoneId.
     */
    static ZoneId getZoneId(final Calendar self) {
        self.timeZone.toZoneId()
    }

    /**
     * Returns the ISO-8601 year (represented as a java.time.Year) of the Calendar.
     */
    static getYear(final Calendar self) {
        Year.of(self.get(Calendar.YEAR))
    }

    /**
     * Returns the month (represented as a java.time.Month) of the Calendar.
     */
    static getMonth(final Calendar self) {
        Month.of(self.get(Calendar.MONTH) + 1)
    }        

    /**
     * Returns the java.time.YearMonth of the Calendar.
     */
    static getYearMonth(final Calendar self) {
        getYear(self).atMonth(getMonth(self))
    }

    /**
     * Returns the day of the week (represented as a java.time.DayOfWeek) of the Calendar.
     */
    static getDayOfWeek(final Calendar self) {
        daysOfWeek[self.get(Calendar.DAY_OF_WEEK)]
    }

    /**
     * Converts the Date to a LocalDate. The extracted year/month/day vales are used. Time Zone and time values
     * are ignored. If you wish to honor the Time Zone and time values when converting to a LocalDate, use
     * provide a TimeZone, ZoneId, or ZoneOffset argument to the toLocalDate method.
     */
    static toLocalDate(final Date self) {
        toLocalDate(self.toCalendar())
    }

    /**
     * Converts the Date to a LocalDate, using the provided ZoneId to adjust the LocalDate accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static toLocalDate(final Date self, ZoneId zoneId) {
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
        toLocalDate(self, timeZone.toZoneId())
    }

    /**
     * Converts the Date to a LocalTime. The extracted hour/minute/second/ms values are used. Time Zone and  
     * date values are ignored. If you wish to honor the Time Zone when converting to a LocalDate, provide a 
     * TimeZone, ZoneId, or ZoneOffset argument to the toLocalDate method.
     */
    static toLocalTime(final Date self) {
        toLocalTime(self.toCalendar())
    }

    /**
     * Converts the Date to a LocalTime, using the provided ZoneId to adjust the LocalTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Date.
     */
    static toLocalTime(final Date self, ZoneId zoneId ) {
        toZonedDateTime(self, zoneId).toLocalTime()
    }

    /**
     * Converts the Date to a LocalTime, using the provided ZoneOffset to adjust the LocalTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Date.
     */
    static toLocalTime(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalTime()
    }

    /**
     * Converts the Date to a LocalTime, using the provided TimeZone to adjust the LocalTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Date.
     */
    static toLocalTime(final Date self, TimeZone timeZone) {
        toLocalTime(self, timeZone.toZoneId())
    }

    /**
     * Converts the Date to a LocalDateTime. The extracted date and time values are used. The time zone
     * is ignored. If you wish to honor the Time Zone  when converting to a LocalDateTime, provide a
     * TimeZone, ZoneId, or ZoneOffset argument to the toLocalDateTime method.
     */
    static toLocalDateTime(final Date self) {
        toLocalDateTime(self.toCalendar())
    }

    /**
     * Converts the Date to a LocalDateTime, using the provided ZoneId to adjust the LocalDateTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Date.
     */
    static toLocalDateTime(final Date self, ZoneId zoneId) {
        toZonedDateTime(self, zoneId).toLocalDateTime()
    }

    /**
     * Converts the Date to a LocalDateTime, using the provided ZoneOffset to adjust the LocalDateTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Date.
     */
    static toLocalDateTime(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalDateTime()
    }

    /**
     * Converts the Date to a LocalDateTime, using the provided TimeZone to adjust the LocalDateTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Date.
     */
    static toLocalDateTime(final Date self, TimeZone timeZone) {
        toLocalDateTime(self, timeZone.toZoneId())
    }    
    
    /**
     * Converts the Date to a ZonedDateTime according to the system default ZoneId. Pass in a different ZoneId to 
     * translate the returned ZoneDateTime to that time zone.
     */
    static toZonedDateTime(final Date self, ZoneId zoneId = ZoneId.systemDefault()) {
        self.toInstant().atZone(zoneId)
    }

    /**
     * Converts the Date to an OffsetDate time according to the provided ZoneOffset.
     */
    static toOffsetDateTime(final Date self, ZoneOffset offset) {
        self.toInstant().atOffset(offset)
    }

    /**
     * Converts the Calendar to a LocalDate. The extracted year/month/day vales are used. Time Zone and time values
     * are ignored. If you wish to honor the Time Zone and time values when converting to a LocalDate, use
     * provide a TimeZone, ZoneId, or ZoneOffset argument to the toLocalDate method.
     */
    static toLocalDate(final Calendar self) {
        int year = self.get(Calendar.YEAR)
        int month = self.get(Calendar.MONTH) + 1
        int dayOfMonth = self.get(Calendar.DAY_OF_MONTH)
        LocalDate.of(year, month, dayOfMonth)
    }

    /**
     * Converts the Calendar to a LocalDate, using the Date's own Time Zone to adjust the LocalDate accordingly.
     *
     * If the Calendar is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     *
     * An optional ZoneId may be passed to allow the returned LocalDate to be converted as if it were at a
     * Time Zone other than the system default.
     */
    static toLocalDate(final Calendar self, ZoneId zoneId) {
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

    /**
     * Converts the Calendar to a LocalTime. The extracted hour/minute/second/ms values are used. Time Zone and  
     * date values are ignored. If you wish to honor the Time Zone when converting to a LocalDate, provide a 
     * TimeZone, ZoneId, or ZoneOffset argument to the toLocalDate method.
     */
    static toLocalTime(final Calendar self) {
        int hour = Calendar.get(Calendar.HOUR)
        int minute = Calendar.get(Calendar.MINUTE)
        int second = Calendar.get(Calendar.SECOND)
        int ms = Calendar.get(Calendar.MILLISECOND)
        LocalTime.of(hour, minute, second, ms)
    }

    /**
     * Converts the Calendar to a LocalTime, using the provided ZoneId to adjust the LocalTime accordingly.
     *
     * If the DCalendarate is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Calendar.
     */
    static toLocalTime(final Calendar self, ZoneId zoneId) {
        toLocalTime(self.time, zoneId)
    }

    /**
     * Converts the Calendar to a LocalTime, using the provided ZoneOffset to adjust the LocalTime accordingly.
     *
     * If the DCalendarate is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Calendar.
     */
    static toLocalTime(final Calendar self, ZoneOffset offset) {
        toLocalTime(self.time, offset)
    }

    /**
     * Converts the Calendar to a LocalTime, using the provided TimeZone to adjust the LocalTime accordingly.
     *
     * If the DCalendarate is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Calendar.
     */
    static toLocalTime(final Calendar self, TimeZone timeZone) {
        toLocalTime(self.time, timeZone)
    }

    /**
     * Converts the Calendar to a LocalDateTime. The extracted date and time values are used. The time zone
     * is ignored. If you wish to honor the Time Zone  when converting to a LocalDateTime, provide a
     * TimeZone, ZoneId, or ZoneOffset argument to the toLocalDateTime method.
     */
    static toLocalDateTime(final Calendar self) {
        def localDate = toLocalDate(self)
        def localTime = toLocalTime(self)
        LocalDateTime.of(localDate, localTime)
    }

    /**
     * Converts the Calendar to a LocalDateTime, using the provided ZoneId to adjust the LocalDateTime accordingly.
     *
     * If the Calendar is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Calendar.
     */
    static toLocalDateTime(final Calendar self, ZoneId zoneId) {
        toLocalDateTime(self.time, zoneId)
    }

    /**
     * Converts the Calendar to a LocalDateTime, using the provided ZoneOffset to adjust the LocalDateTime accordingly.
     *
     * If the Calendar is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Calendar.
     */
    static toLocalDateTime(final Calendar self, ZoneOffset offset) {
        toLocalDateTime(self.time, offset)
    }

    /**
     * Converts the Calendar to a LocalDateTime, using the provided TimeZone to adjust the LocalDateTime accordingly.
     *
     * If the Calendar is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Calendar.
     */
    static toLocalDateTime(final Calendar self, TimeZone timeZone) {
        toLocalDateTime(self.time, timeZone)
    }       
    
    /**
     * Converts the Calendar to a ZonedDateTime according to the system default ZoneId. Pass in a different ZoneId to 
     * translate the returned ZoneDateTime to that time zone.
     */
    static toZonedDateTime(final Calendar self, ZoneId zoneId = ZoneId.systemDefault()) {
        toZonedDateTime(self.time, zoneId)
    }

    /**
     * Converts the Calendar to an OffsetDate time according to the provided ZoneOffset.
     */
    static toOffsetDateTime(final Calendar self, ZoneOffset offset) {
        toOffsetDateTime(self.time, offset)
    }    

    /**
     * Convenience method for obtaining a java.time.Instant from a Calendar. Purely equivalent to
     * <code>calendar.time.toInstant()</code>
     */
    static toInstant(final Calendar self) {
        self.time.toInstant()
    }
}