package bdkosher.goodtimes

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.MonthDay
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.Year
import java.time.YearMonth
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import groovy.transform.PackageScope

/**
 * Extension methods for converting or inspecting java.util.Date and java.util.Calendar instances as Java 8 Date/Time types.
 */
class DateCalendarExtension {

    /**
     * A mapping of Calendar constants representing days of the week to their corresponding java.time.DayOfWeek enum
     */
    @PackageScope
    static final Map<Integer, DayOfWeek> daysOfWeek = ['SUNDAY','MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY']
            .collectEntries { day -> [Calendar."$day", java.time.DayOfWeek."$day"] }

    /**
     * Returns the Time Zone offset of the Date as a java.time.ZoneOffset, which is most likely the same as the system's default offset.
     */
    static ZoneOffset getZoneOffset(final Date date) {
        getZoneOffset(date.toCalendar())
    }

    /**
     * Returns the Time Zone offset of the Calendar as a java.time.ZoneOffset.
     */
    static ZoneOffset getZoneOffset(final Calendar self) {
        int offsetMs = self.get(Calendar.ZONE_OFFSET) 
        offsetMs += self.get(Calendar.DST_OFFSET)
        int offsetSec = offsetMs / 1000
        ZoneOffset.ofTotalSeconds(offsetSec)
    }

    /**
     * Returns the Time Zone of the Calendar as a java.time.ZoneId.
     */
    static ZoneId getZoneId(final Calendar self) {
        self.timeZone.toZoneId()
    }

    /**
     * Returns the Time Zone of the Date as a java.time.ZoneId. This will typically be equivalent to the system's default ZoneId.
     */
    static ZoneId getZoneId(final Date self) {
        getZoneId(self.toCalendar())
    }

    /**
     * Returns the ISO-8601 year (represented as a java.time.Year) of the Calendar.
     */
    static Year getYear(final Calendar self) {
        Year.of(self.get(Calendar.YEAR))
    }

    /**
     * Returns the month (represented as a java.time.Month) of the Calendar.
     */
    static Month getMonth(final Calendar self) {
        Month.of(self.get(Calendar.MONTH) + 1)
    }

    /**
     * Returns the java.time.MonthDay of the Calendar.
     */
    static MonthDay getMonthDay(final Calendar self) {
        MonthDay.of(getMonth(self), self.get(Calendar.DAY_OF_MONTH))
    }

    /**
     * Returns the java.time.YearMonth of the Calendar.
     */
    static YearMonth getYearMonth(final Calendar self) {
        getYear(self).atMonth(getMonth(self))
    }

    /**
     * Returns the day of the week (represented as a java.time.DayOfWeek) of the Calendar.
     */
    static DayOfWeek getDayOfWeek(final Calendar self) {
        daysOfWeek[self.get(Calendar.DAY_OF_WEEK)]
    }

    /**
     * Converts the Date to a LocalDate. The extracted year/month/day vales are used. Time Zone and time values
     * are ignored. If you wish to honor the Time Zone and time values when converting to a LocalDate, use
     * provide a TimeZone, ZoneId, or ZoneOffset argument to the toLocalDate method.
     */
    static LocalDate toLocalDate(final Date self) {
        toLocalDate(self.toCalendar())
    }

    /**
     * Converts the Date to a LocalDate, using the provided ZoneId to adjust the LocalDate accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static LocalDate toLocalDate(final Date self, ZoneId zoneId) {
        toZonedDateTime(self, zoneId).toLocalDate()
    }

    /**
     * Converts the Date to a LocalDate, using the provided ZoneOffset to adjust the LocalDate accordingly.
     *
     * If the Date's Time Zone offset differ from the provided ZoneOffset and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static LocalDate toLocalDate(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalDate()
    }

    /**
     * Converts the Date to a LocalDate, using the provided TimeZone to adjust the LocalDate accordingly.
     *
     * If the Date's Time Zone offset differ from the provided TimeZone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static LocalDate toLocalDate(final Date self, TimeZone timeZone) {
        toLocalDate(self, timeZone.toZoneId())
    }

    /**
     * Converts the Date to a LocalTime. The extracted hour/minute/second/ms values are used. Time Zone and  
     * date values are ignored. If you wish to honor the Time Zone when converting to a LocalDate, provide a 
     * TimeZone, ZoneId, or ZoneOffset argument to the toLocalDate method.
     */
    static LocalTime toLocalTime(final Date self) {
        toLocalTime(self.toCalendar())
    }

    /**
     * Converts the Date to a LocalTime, using the provided ZoneId to adjust the LocalTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Date.
     */
    static LocalTime toLocalTime(final Date self, ZoneId zoneId) {
        toZonedDateTime(self, zoneId).toLocalTime()
    }

    /**
     * Converts the Date to a LocalTime, using the provided ZoneOffset to adjust the LocalTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Date.
     */
    static LocalTime toLocalTime(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalTime()
    }

    /**
     * Converts the Date to a LocalTime, using the provided TimeZone to adjust the LocalTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Date.
     */
    static LocalTime toLocalTime(final Date self, TimeZone timeZone) {
        toLocalTime(self, timeZone.toZoneId())
    }

    /**
     * Converts the Date to a LocalDateTime. The extracted date and time values are used. The time zone
     * is ignored. If you wish to honor the Time Zone  when converting to a LocalDateTime, provide a
     * TimeZone, ZoneId, or ZoneOffset argument to the toLocalDateTime method.
     */
    static LocalDateTime toLocalDateTime(final Date self) {
        toLocalDateTime(self.toCalendar())
    }

    /**
     * Converts the Date to a LocalDateTime, using the provided ZoneId to adjust the LocalDateTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Date.
     */
    static LocalDateTime toLocalDateTime(final Date self, ZoneId zoneId) {
        toZonedDateTime(self, zoneId).toLocalDateTime()
    }

    /**
     * Converts the Date to a LocalDateTime, using the provided ZoneOffset to adjust the LocalDateTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Date.
     */
    static LocalDateTime toLocalDateTime(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toLocalDateTime()
    }

    /**
     * Converts the Date to a LocalDateTime, using the provided TimeZone to adjust the LocalDateTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Date.
     */
    static LocalDateTime toLocalDateTime(final Date self, TimeZone timeZone) {
        toLocalDateTime(self, timeZone.toZoneId())
    }

    /**
     * Converts the Date to a ZonedDateTime. 
     */
    static ZonedDateTime toZonedDateTime(final Date self) {
        toZonedDateTime(self, getZoneId(self))
    }
    
    /**
     * Converts the Date to a ZonedDateTime, using the provided ZoneId to adjust the LocalDateTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Date.
     */
    static ZonedDateTime toZonedDateTime(final Date self, ZoneId zoneId) {
        self.toInstant().atZone(zoneId)
    }

    /**
     * Converts the Date to a ZonedDateTime, using the provided TimeZone to adjust the LocalDateTime accordingly.
     *
     * If the Date is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Date.
     */
    static ZonedDateTime toZonedDateTime(final Date self, TimeZone timeZone) {
        toZonedDateTime(self, timeZone.toZoneId())
    }

    /**
     * Converts the Date to an OffsetDateTime.
     */
    static OffsetDateTime toOffsetDateTime(final Date self) {
        toOffsetDateTime(self, getZoneOffset(self))
    }

    /**
     * Converts the Date to an OffsetDateTime according to the provided ZoneOffset.
     */
    static OffsetDateTime toOffsetDateTime(final Date self, ZoneOffset offset) {
        self.toInstant().atOffset(offset)
    }

    /**
     * Converts the Date to an OffsetTime.
     */
    static OffsetTime toOffsetTime(final Date self) {
        toOffsetTime(self, getZoneOffset(self))
    }    

    /**
     * Converts the Date to an OffsetTime according to the provided ZoneOffset.
     */
    static OffsetTime toOffsetTime(final Date self, ZoneOffset offset) {
        toOffsetDateTime(self, offset).toOffsetTime()
    }

    /**
     * Converts the Calendar to a LocalDate. The extracted year/month/day vales are used. Time Zone and time values
     * are ignored. If you wish to honor the Time Zone and time values when converting to a LocalDate, use
     * provide a TimeZone, ZoneId, or ZoneOffset argument to the toLocalDate method.
     */
    static LocalDate toLocalDate(final Calendar self) {
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
    static LocalDate toLocalDate(final Calendar self, ZoneId zoneId) {
        toLocalDate(self.time, zoneId)
    }

    /**
     * Converts the Calendar to a LocalDate, using the provided ZoneOffset to adjust the LocalDate accordingly.
     *
     * If the Calendar's Time Zone offset differ from the provided ZoneOffset and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static LocalDate toLocalDate(final Calendar self, ZoneOffset offset) {
        toLocalDate(self.time, offset)
    }

    /**
     * Converts the Calendar to a LocalDate, using the provided TimeZone to adjust the LocalDate accordingly.
     *
     * If the Calendar's Time Zone offset differ from the provided TimeZone and depending on its time values, 
     * then the year/month/day values of the LocalDate may differ from the year/month/day values of the Date.
     */
    static LocalDate toLocalDate(final Calendar self, TimeZone timeZone) {
        toLocalDate(self.time, timeZone)
    }

    /**
     * Converts the Calendar to a LocalTime. The extracted hour/minute/second/ms values are used. Time Zone and  
     * date values are ignored. If you wish to honor the Time Zone when converting to a LocalDate, provide a 
     * TimeZone, ZoneId, or ZoneOffset argument to the toLocalDate method.
     */
    static LocalTime toLocalTime(final Calendar self) {
        int hour = self.get(Calendar.HOUR_OF_DAY)
        int minute = self.get(Calendar.MINUTE)
        int second = self.get(Calendar.SECOND)
        int ns = self.get(Calendar.MILLISECOND) * 1e6
        LocalTime.of(hour, minute, second, ns)
    }

    /**
     * Converts the Calendar to a LocalTime, using the provided ZoneId to adjust the LocalTime accordingly.
     *
     * If the DCalendarate is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Calendar.
     */
    static LocalTime toLocalTime(final Calendar self, ZoneId zoneId) {
        toLocalTime(self.time, zoneId)
    }

    /**
     * Converts the Calendar to a LocalTime, using the provided ZoneOffset to adjust the LocalTime accordingly.
     *
     * If the DCalendarate is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Calendar.
     */
    static LocalTime toLocalTime(final Calendar self, ZoneOffset offset) {
        toLocalTime(self.time, offset)
    }

    /**
     * Converts the Calendar to a LocalTime, using the provided TimeZone to adjust the LocalTime accordingly.
     *
     * If the DCalendarate is of a different Time Zone than the provided Time Zone and depending on its time values, 
     * then the hour/min/sec/ms values of the LocalTime may differ from the hour/min/sec/ms values of the Calendar.
     */
    static LocalTime toLocalTime(final Calendar self, TimeZone timeZone) {
        toLocalTime(self.time, timeZone)
    }

    /**
     * Converts the Calendar to a LocalDateTime. The extracted date and time values are used. The time zone
     * is ignored. If you wish to honor the Time Zone  when converting to a LocalDateTime, provide a
     * TimeZone, ZoneId, or ZoneOffset argument to the toLocalDateTime method.
     */
    static LocalDateTime toLocalDateTime(final Calendar self) {
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
    static LocalDateTime toLocalDateTime(final Calendar self, ZoneId zoneId) {
        toLocalDateTime(self.time, zoneId)
    }

    /**
     * Converts the Calendar to a LocalDateTime, using the provided ZoneOffset to adjust the LocalDateTime accordingly.
     *
     * If the Calendar is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Calendar.
     */
    static LocalDateTime toLocalDateTime(final Calendar self, ZoneOffset offset) {
        toLocalDateTime(self.time, offset)
    }

    /**
     * Converts the Calendar to a LocalDateTime, using the provided TimeZone to adjust the LocalDateTime accordingly.
     *
     * If the Calendar is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Calendar.
     */
    static LocalDateTime toLocalDateTime(final Calendar self, TimeZone timeZone) {
        toLocalDateTime(self.time, timeZone)
    }       
    
    /**
     * Converts the Calendar to a ZonedDateTime, using the provided ZoneId to adjust the ZonedDateTime accordingly.
     *
     * If the Calendar is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Calendar.
     */
    static ZonedDateTime toZonedDateTime(final Calendar self, ZoneId zoneId) {
        toZonedDateTime(self.time, zoneId)
    }

    /**
     * Converts the Calendar to a ZonedDateTime, using the provided TimeZone to adjust the ZonedDateTime accordingly.
     *
     * If the Calendar is of a different Time Zone than the provided Time Zone and depending on its date and time values, 
     * then the date and time values of the LocalDateTime may differ from the date and time values of the Calendar.
     */
    static ZonedDateTime toZonedDateTime(final Calendar self, TimeZone timeZone) {
        toZonedDateTime(self.time, timeZone)
    }
    
    /**
     * Converts the Calendar to a ZonedDateTime. GregorianCalendar has its own toZonedDateTime method, so this method will
     * likely not be called when using typical Calendar instances.
     */
    static ZonedDateTime toZonedDateTime(final Calendar self) {
        toZonedDateTime(self.time)
    }

    /**
     * Converts the Calendar to an OffsetDateTime.
     */
    static OffsetDateTime toOffsetDateTime(final Calendar self) {
        toOffsetDateTime(self.time, getZoneOffset(self))
    }

    /**
     * Converts the Calendar to an OffsetDateTime according to the provided ZoneOffset.
     */
    static OffsetDateTime toOffsetDateTime(final Calendar self, ZoneOffset offset) {
        toOffsetDateTime(self.time, offset)
    }

    /**
     * Converts the Calendar to an OffsetTime according to the provided ZoneOffset.
     */
    static OffsetTime toOffsetTime(final Calendar self) {
        toOffsetTime(self.time, getZoneOffset(self))
    }        

    /**
     * Converts the Calendar to an OffsetTime according to the provided ZoneOffset.
     */
    static OffsetTime toOffsetTime(final Calendar self, ZoneOffset offset) {
        toOffsetTime(self.time, offset)
    }    

    /**
     * Convenience method for obtaining a java.time.Instant from a Calendar. Purely equivalent to
     * <code>calendar.time.toInstant()</code>
     */
    static Instant toInstant(final Calendar self) {
        self.time.toInstant()
    }

    /**
     * Converts this TimeZone to a ZoneOffset. The offset is based on the current date/time.
     */
    static ZoneOffset toZoneOffset(final TimeZone self) {
        toZoneOffset(self, Instant.now())
    }

    /**
     * Converts this TimeZone to a ZoneOffset at the specified Instant.
     */
    static ZoneOffset toZoneOffset(final TimeZone self, Instant instant) {
        self.toZoneId().rules.getOffset(instant)
    }
}