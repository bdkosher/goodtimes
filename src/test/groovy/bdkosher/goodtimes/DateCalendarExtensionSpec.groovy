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

    def eachZoneOffset(Closure closure) {
        (-17..17).each { hour ->
            (0..59).each { minute ->
                ZoneOffset zoneOffset = ZoneOffset.ofHoursMinutes(hour, hour < 0 ? -1 * minute : minute)
                //TimeZone timeZone = TimeZone.getTimeZone('GMT' + zoneOffset.id)
                closure(zoneOffset)
            }
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

    def "Calendar toLocalDate - one example"() {
        given:
        Calendar cal = Calendar.instance.with {
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DATE, 7)
            delegate
        }

        when:
        LocalDate ld = cal.toLocalDate()

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Date toLocalDate - one example"() {
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

    def "Calendar toLocalDate"() {
        given:
        Calendar cal = Calendar.instance
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), ZoneId.systemDefault())

        expect:  
        cal.toLocalDate() == LocalDate.now(fixedClock)
    }

    def "Calendar toLocalDate with TimeZone and ZoneId arguments"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Calendar cal = Calendar.getInstance(timeZone)
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), zoneId)

            LocalDate ldFromZoneId = cal.toLocalDate(zoneId)
            assert ldFromZoneId == LocalDate.now(tzClock) : "Unexpected LocalDate $ldFromZoneId from Calendar $cal for ZoneId $zoneId"

            LocalDate ldFromTimeZone = cal.toLocalDate(timeZone)
            assert ldFromTimeZone == LocalDate.now(tzClock) : "Unexpected LocalDate $ldFromZoneId from Calendar $cal for TimeZone $timeZone"
        }
    }

    def "Date toLocalDate"() {
        given:
        Date date = new Date()
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())

        expect:  
        date.toLocalDate() == LocalDate.now(fixedClock)
    }

    def "Date toLocalDate with TimeZone and ZoneId arguments"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Date date = new Date()
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(date.time), zoneId)

            LocalDate ldFromZoneId = date.toLocalDate(zoneId)
            assert ldFromZoneId == LocalDate.now(tzClock) : "Unexpected LocalDate $ldFromZoneId from Date $date for ZoneId $zoneId"

            LocalDate ldFromTimeZone = date.toLocalDate(timeZone)
            assert ldFromTimeZone == LocalDate.now(tzClock) : "Unexpected LocalDate $ldFromZoneId from Date $date for TimeZone $timeZone"
        }
    }

    def "Calendar toLocalTime"() {
        given:
        Calendar cal = Calendar.instance
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), ZoneId.systemDefault())

        expect:  
        cal.toLocalTime() == LocalTime.now(fixedClock)
    }

    def "Calendar toLocalTime with TimeZone and ZoneId arguments"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Calendar cal = Calendar.getInstance(timeZone)
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), zoneId)

            LocalTime ltFromZoneId = cal.toLocalTime(zoneId)
            assert ltFromZoneId == LocalTime.now(tzClock) : "Unexpected LocalTime $ltFromZoneId from Calendar $cal for ZoneId $zoneId"

            LocalTime ltFromTimeZone = cal.toLocalTime(timeZone)
            assert ltFromTimeZone == LocalTime.now(tzClock) : "Unexpected LocalTime $ltFromTimeZone from Calendar $cal for TimeZone $timeZone"
        }
    }

    def "Date toLocalTime"() {
        given:
        Date date = new Date()
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())

        expect:  
        date.toLocalTime() == LocalTime.now(fixedClock)
    }

    def "Date toLocalTime with TimeZone and ZoneId arguments"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Date date = new Date()
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(date.time), zoneId)

            LocalTime ltFromZoneId = date.toLocalTime(zoneId)
            assert ltFromZoneId == LocalTime.now(tzClock) : "Unexpected LocalTime $ltFromZoneId from Date $date for ZoneId $zoneId"

            LocalTime ltFromTimeZone = date.toLocalTime(timeZone)
            assert ltFromTimeZone == LocalTime.now(tzClock) : "Unexpected LocalTime $ltFromTimeZone from Date $date for TimeZone $timeZone"
        }
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

    def "ZoneOffset from Date is always the default time zone's offset because Dates are funky"() {
        expect:  
        eachTimeZone { timeZone ->
            Calendar cal = new GregorianCalendar(timeZone)
            Date date = cal.time
            assert date.zoneOffset.totalSeconds * 1000 == TimeZone.default.getOffset(date.time)
        }
    }

    def "ZoneId from Calendar equivalent to the TimeZone's ZoneId"() {
        expect:  
        eachTimeZone { timeZone ->
            Calendar cal = new GregorianCalendar(timeZone)
            assert cal.zoneId == timeZone.toZoneId()
        }        
    }

    def "ZoneId from Date equivalent to the system default ZoneId"() {
        expect:  
        eachTimeZone { timeZone ->
            Calendar cal = new GregorianCalendar(timeZone)
            assert cal.time.zoneId == ZoneId.systemDefault()
        }        
    }    

    def "Calendar toLocalDateTime"() {
        given:
        Calendar cal = Calendar.instance
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), ZoneId.systemDefault())

        expect:  
        cal.toLocalDateTime() == LocalDateTime.now(fixedClock)
    }

    def "Calendar toLocalDateTime with TimeZone and ZoneId arguments"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Calendar cal = Calendar.getInstance(timeZone)
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), zoneId)
            
            LocalDateTime ldtFromZoneId = cal.toLocalDateTime(zoneId)
            assert ldtFromZoneId == LocalDateTime.now(tzClock) : "Unexpected LocalDateTime $ldtFromZoneId from Calendar $cal for ZoneId $zoneId"

            LocalDateTime ldtFromTimeZone = cal.toLocalDateTime(timeZone)
            assert ldtFromTimeZone == LocalDateTime.now(tzClock) : "Unexpected LocalDateTime $ldtFromTimeZone from Calendar $cal for TimeZone $timeZone"
        }
    }

    def "Date toLocalDateTime"() {
        given:
        Date date = new Date()
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())

        expect:  
        date.toLocalDateTime() == LocalDateTime.now(fixedClock)
    }

    def "Date toLocalDateTime with TimeZone and ZoneId arguments"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Date date = new Date()
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(date.time), zoneId)
            
            LocalDateTime ldtFromZoneId = date.toLocalDateTime(zoneId)
            assert ldtFromZoneId == LocalDateTime.now(tzClock) : "Unexpected LocalDateTime $ldtFromZoneId from Date $date for ZoneId $zoneId"

            LocalDateTime ldtFromTimeZone = date.toLocalDateTime(timeZone)
            assert ldtFromTimeZone == LocalDateTime.now(tzClock) : "Unexpected LocalDateTime $ldtFromTimeZone from Date $date for TimeZone $timeZone"
        }
    }

    def "Calendar toZonedDateTime"() {
        given:
        Calendar cal = Calendar.instance
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), ZoneId.systemDefault())

        expect:  
        cal.toZonedDateTime() == ZonedDateTime.now(fixedClock)
    }

    def "Calendar toZonedDateTime with TimeZone and ZoneId arguments"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Calendar cal = Calendar.getInstance(timeZone)
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), zoneId)
            
            ZonedDateTime zdtFromZoneId = cal.toZonedDateTime(zoneId)
            assert zdtFromZoneId == ZonedDateTime.now(tzClock) : "Unexpected ZonedDateTime $zdtFromZoneId from Calendar $cal for ZoneId $zoneId"

            ZonedDateTime zdtFromTimeZone = cal.toZonedDateTime(timeZone)
            assert zdtFromTimeZone == ZonedDateTime.now(tzClock) : "Unexpected ZonedDateTime $zdtFromTimeZone from Calendar $cal for TimeZone $timeZone"
        }
    }

    def "Date toZonedDateTime"() {
        given:
        Date date = new Date()
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())

        expect:  
        date.toZonedDateTime() == ZonedDateTime.now(fixedClock)
    }

    def "Date toZonedDateTime with TimeZone and ZoneId arguments"() {
        expect:  
        eachTimeZone { timeZone ->
            ZoneId zoneId = timeZone.toZoneId()
            Date date = new Date()
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(date.time), zoneId)
            
            ZonedDateTime zdtFromZoneId = date.toZonedDateTime(zoneId)
            assert zdtFromZoneId == ZonedDateTime.now(tzClock) : "Unexpected ZonedDateTime $zdtFromZoneId from Date $date for ZoneId $zoneId"

            ZonedDateTime zdtFromTimeZone = date.toZonedDateTime(timeZone)
            assert zdtFromTimeZone == ZonedDateTime.now(tzClock) : "Unexpected ZonedDateTime $zdtFromTimeZone from Date $date for TimeZone $timeZone"
        }
    }

    def "Calendar toOffsetDateTime"() {
        given:
        Calendar cal = Calendar.instance
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), ZoneId.systemDefault())

        expect:  
        cal.toOffsetDateTime() == OffsetDateTime.now(fixedClock)
    }

    def "Calendar toOffsetDateTime with ZoneOffset arguments"() {
        expect:
        eachZoneOffset { zoneOffset ->
            TimeZone timeZone = TimeZone.getTimeZone('GMT' + zoneOffset.id)
            ZoneId zoneId = timeZone.toZoneId()
            Calendar cal = Calendar.getInstance(timeZone)
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), zoneId)
            
            OffsetDateTime odtFromZoneOffset = cal.toOffsetDateTime(zoneOffset)
            assert odtFromZoneOffset == OffsetDateTime.now(tzClock) : "Unexpected OffsetDateTime $odtFromZoneOffset from Calendar $cal for offset $zoneOffset"    
        }
    }

    def "Date toOffsetDateTime"() {
        given:
        Date date = new Date()
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())

        expect:  
        date.toOffsetDateTime() == OffsetDateTime.now(fixedClock)
    }

    def "Date toOffsetDateTime with ZoneOffset arguments"() {
        expect:
        eachZoneOffset { zoneOffset ->
            TimeZone timeZone = TimeZone.getTimeZone('GMT' + zoneOffset.id)
            ZoneId zoneId = timeZone.toZoneId()
            Date date = new Date()
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(date.time), zoneId)
            
            OffsetDateTime odtFromZoneOffset = date.toOffsetDateTime(zoneOffset)
            assert odtFromZoneOffset == OffsetDateTime.now(tzClock) : "Unexpected OffsetDateTime $odtFromZoneOffset from Date $date for offset $zoneOffset"    
        }
    }

    def "Calendar toOffsetTime"() {
        given:
        Calendar cal = Calendar.instance
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), ZoneId.systemDefault())

        expect:  
        cal.toOffsetTime() == OffsetTime.now(fixedClock)
    }

    def "Calendar toOffsetTime with ZoneOffset arguments"() {
        expect:
        eachZoneOffset { zoneOffset ->
            TimeZone timeZone = TimeZone.getTimeZone('GMT' + zoneOffset.id)
            ZoneId zoneId = timeZone.toZoneId()
            Calendar cal = Calendar.getInstance(timeZone)
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(cal.time.time), zoneId)
            
            OffsetTime otFromZoneOffset = cal.toOffsetTime(zoneOffset)
            assert otFromZoneOffset == OffsetTime.now(tzClock) : "Unexpected OffsetTime $otFromZoneOffset from Calendar $cal for offset $zoneOffset"    
        }
    }

    def "Date toOffsetTime"() {
        given:
        Date date = new Date()
        Clock fixedClock = Clock.fixed(Instant.ofEpochMilli(date.time), ZoneId.systemDefault())

        expect:  
        date.toOffsetTime() == OffsetTime.now(fixedClock)
    }

    def "Date toOffsetTime with ZoneOffset arguments"() {
        expect:
        eachZoneOffset { zoneOffset ->
            TimeZone timeZone = TimeZone.getTimeZone('GMT' + zoneOffset.id)
            ZoneId zoneId = timeZone.toZoneId()
            Date date = new Date()
            Clock tzClock = Clock.fixed(Instant.ofEpochMilli(date.time), zoneId)
            
            OffsetTime otFromZoneOffset = date.toOffsetTime(zoneOffset)
            assert otFromZoneOffset == OffsetTime.now(tzClock) : "Unexpected OffsetTime $otFromZoneOffset from Date $date for offset $zoneOffset"    
        }
    }    
}    