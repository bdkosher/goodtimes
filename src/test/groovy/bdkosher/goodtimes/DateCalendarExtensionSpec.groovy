package bdkosher.goodtimes

import java.time.*
import java.time.format.*
import spock.lang.Specification

class DateCalendarExtensionSpec extends Specification {

    def eachTimeZone(Closure closure) {
        TimeZone.availableIDs.collect { id -> TimeZone.getTimeZone(id) }.each { timeZone ->
            closure(timeZone)
        }
    }

    def "Calendar getters"() {
        given:
        Calendar cal = Date.parse('yyyyMMdd', '20170204').toCalendar()

        expect:
        cal.year == Year.of(2017)
        cal.month == Month.FEBRUARY
        cal.yearMonth == YearMonth.of(2017, Month.FEBRUARY)
        cal.dayOfWeek == DayOfWeek.SATURDAY
        cal.monthDay == MonthDay.of(Month.FEBRUARY, 4)
    }

    def "Calendar toLocalDate"() {
        given:
        Calendar cal = Date.parse('yyyyMMdd', '20170107').toCalendar()

        when:
        LocalDate ld = cal.toLocalDate()

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Date toLocalDate"() {
        given:
        Date date = Date.parse('yyyyMMdd', '20170107')

        when:
        LocalDate ld = date.toLocalDate()

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Calendar toLocalDate passed a ZoneId that wouldn't impact day of month'"() {
        given:
        Calendar cal = Date.parse('yyyyMMdd', '20170107').toCalendar()

        when:
        LocalDate ld = cal.toLocalDate(ZoneId.systemDefault())

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Date toLocalDate passed a ZoneId that wouldn't impact day of month'"() {
        given:
        Date date = Date.parse('yyyyMMdd', '20170107')

        when:
        LocalDate ld = date.toLocalDate(ZoneId.systemDefault())

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Calendar toLocalDate passed a ZoneId that would impact day of month"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone('IST')
        Calendar cal = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            clearTime()
        }

        when:
        ZoneId zoneId = TimeZone.getTimeZone('HAST').toZoneId()
        LocalDate ld = cal.toLocalDate(zoneId)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 - 1
    }        

    def "Date toLocalDate passed a ZoneId that would impact day of month"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone('IST')
        Date date = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            clearTime()
        }.time

        when:
        ZoneId zoneId = TimeZone.getTimeZone('HAST').toZoneId()
        LocalDate ld = date.toLocalDate(zoneId)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 - 1
    }

    def "Calendar toLocalDate passed a ZoneOffset that wouldn't impact day of month"() {
        given:
        Calendar cal = Date.parse('yyyyMMdd', '20170107').toCalendar()

        when:
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(TimeZone.default.getOffset(cal.time.time) / 1000 as int)
        LocalDate ld = cal.toLocalDate(zoneOffset)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Date toLocalDate passed a ZoneOffset that wouldn't impact day of month"() {
        given:
        Date date = Date.parse('yyyyMMdd', '20170107')

        when:
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(TimeZone.default.getOffset(date.time) / 1000 as int)
        LocalDate ld = date.toLocalDate(zoneOffset)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Calendar toLocalDate passed a ZoneOffset that would impact day of month"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone('IST')
        Calendar cal = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            clearTime()
        }

        when:
        ZoneOffset zoneOffset = ZoneOffset.ofHours(-10) // HAST
        LocalDate ld = cal.toLocalDate(zoneOffset)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 - 1
    }

    def "Date toLocalDate passed a ZoneOffset that would impact day of month"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone('IST')
        Date date = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            clearTime()
        }.time

        when:
        ZoneOffset zoneOffset = ZoneOffset.ofHours(-10) // HAST
        LocalDate ld = date.toLocalDate(zoneOffset)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 - 1
    }

    def "Calendar toLocalDate passed a TimeZone that wouldn't impact day of month"() {
        given:
        Calendar cal = Date.parse('yyyyMMdd', '20170107').toCalendar()

        when:
        LocalDate ld = cal.toLocalDate(TimeZone.default)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Date toLocalDate passed a TimeZone that wouldn't impact day of month"() {
        given:
        Date date = Date.parse('yyyyMMdd', '20170107')

        when:
        LocalDate ld = date.toLocalDate(TimeZone.default)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Calendar toLocalDate passed a TimeZone that would impact day of month"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone('IST')
        Calendar cal = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            clearTime()
        }

        when:
        TimeZone minusOneHour = TimeZone.getTimeZone('HAST')
        LocalDate ld = cal.toLocalDate(minusOneHour)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 - 1
    }

    def "Date toLocalDate passed a TimeZone that would impact day of month"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone('IST')
        Date date = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            clearTime()
        }.time

        when:
        TimeZone minusOneHour = TimeZone.getTimeZone('HAST')
        LocalDate ld = date.toLocalDate(minusOneHour)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 - 1
    }

    def "toLocalDate is not impacted by Calendar's time zone"() {
        expect:  
        eachTimeZone { timeZone ->
            Calendar cal = new GregorianCalendar(timeZone)
            cal.with {       
                set(Calendar.YEAR, 2017)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.DAY_OF_MONTH, 7)
                clearTime()
            }
            def localDate = cal.toLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
            assert localDate == '2017-01-07' : "Unexpected calendar date of $localDate for time zone $timeZone (cal's zone id=$cal.zoneId)"    
        }
    }

    def "Date toLocalDate"() {
        given:
        Date date = new Date()
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())

        expect:  
        date.toLocalDate() == LocalDate.now(fixedClock)
    }

    def "Date toLocalDate with time zone"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Date date = new Date()
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(date.time), zoneId)
            LocalDate localDate = date.toLocalDate(zoneId)
            assert localDate == LocalDate.now(tzClock) : "Unexpected LocalDate $localDate from Date $date for TZ $zoneId"
        }
    }

    def "Calendar toLocalTime"() {
        given:
        Calendar cal = Calendar.instance

        when:
        LocalTime lt = cal.toLocalTime()

        then:
        lt.hour == cal.get(Calendar.HOUR_OF_DAY)
        lt.minute == cal.get(Calendar.MINUTE)
        lt.second == cal.get(Calendar.SECOND)
    }

    def "Date toLocalTime"() {
        given:
        Calendar cal = Calendar.instance
        Date date = cal.time

        when:
        LocalTime lt = date.toLocalTime()

        then:
        lt.hour == cal.get(Calendar.HOUR_OF_DAY)
        lt.minute == cal.get(Calendar.MINUTE)
        lt.second == cal.get(Calendar.SECOND)
    }    

    def "Calendar.toInstant() same as Date.toInstant()"() {
        given:
        Calendar c = Calendar.instance
        Date d = c.time

        expect:
        c.toInstant() == d.toInstant()
    }

    def "ZoneOffset from Calendar"() {
        expect:  
        eachTimeZone { timeZone ->
            Calendar cal = new GregorianCalendar(timeZone)
            assert cal.zoneOffset.totalSeconds * 1000 == timeZone.getOffset(cal.time.time)
        }
    }

    def "toLocalDateTime is not impacted by Calendar's time zone"() {
        expect:  
        eachTimeZone { timeZone ->
            Calendar cal = new GregorianCalendar(timeZone)
            cal.with {       
                set(Calendar.YEAR, 2017)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.DAY_OF_MONTH, 7)
                set(Calendar.HOUR_OF_DAY, 3)
                set(Calendar.MINUTE, 45)
                set(Calendar.SECOND, 30)
                set(Calendar.MILLISECOND, 123)
                delegate
            }
            def localDateTime = cal.toLocalDateTime().format('yyyy-MM-dd HH:mm:ss.SSS')
            assert localDateTime == '2017-01-07 03:45:30.123' : "$localDateTime unexpected for Calendar for time zone $timeZone (cal's zone id=$cal.zoneId)"    
        }
    }

    def "Date toLocalDateTime"() {
        given:
        Date date = new Date()
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())

        expect:  
        date.toLocalDateTime() == LocalDateTime.now(fixedClock)
    }

    def "Date toLocalDateTime with time zone"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Date date = new Date()
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(date.time), zoneId)
            LocalDateTime localDateTime = date.toLocalDateTime(zoneId)
            assert localDateTime == LocalDateTime.now(tzClock) : "Unexpected LocalDateTime $localDateTime from Date $date for TZ $zoneId"
        }
    }
}    