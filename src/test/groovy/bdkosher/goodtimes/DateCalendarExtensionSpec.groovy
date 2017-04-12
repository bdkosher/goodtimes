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
    }

    def "Calendar toLocalDate"() {
        given:
        Calendar cal = Date.parse('yyyyMMdd', '20170107').toCalendar()
        LocalDate ld = cal.toLocalDate()

        expect:
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

    def "Calendar toLocalDate passed a ZoneId that would impact day of month'"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone(TimeZone.getAvailableIDs(0).head())
        Calendar cal = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            set(Calendar.HOUR, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            delegate
        }

        when:
        ZoneId zoneId = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-1)) // minus one hour
        LocalDate ld = cal.toLocalDate(zoneId)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 + 1
    }        

    def "Date toLocalDate passed a ZoneId that would impact day of month'"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone(TimeZone.getAvailableIDs(0).head())
        Date date = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            set(Calendar.HOUR, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            delegate
        }.time

        when:
        ZoneId zoneId = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-1)) // minus one hour
        LocalDate ld = date.toLocalDate(zoneId)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 + 1
    }

    def "Calendar toLocalDate passed a ZoneOffset that wouldn't impact day of month'"() {
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

    def "Date toLocalDate passed a ZoneOffset that wouldn't impact day of month'"() {
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

    def "Calendar toLocalDate passed a ZoneOffset that would impact day of month'"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone(TimeZone.getAvailableIDs(0).head())
        Calendar cal = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            set(Calendar.HOUR, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            delegate
        }

        when:
        ZoneOffset zoneOffset = ZoneOffset.ofHours(-1) // minus one hour
        LocalDate ld = cal.toLocalDate(zoneOffset)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 + 1
    }

    def "Date toLocalDate passed a ZoneOffset that would impact day of month'"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone(TimeZone.getAvailableIDs(0).head())
        Date date = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            set(Calendar.HOUR, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            delegate
        }.time

        when:
        ZoneOffset zoneOffset = ZoneOffset.ofHours(-1) // minus one hour
        LocalDate ld = date.toLocalDate(zoneOffset)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 + 1
    }

    def "Calendar toLocalDate passed a TimeZone that wouldn't impact day of month'"() {
        given:
        Calendar cal = Date.parse('yyyyMMdd', '20170107').toCalendar()

        when:
        LocalDate ld = cal.toLocalDate(TimeZone.default)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Date toLocalDate passed a TimeZone that wouldn't impact day of month'"() {
        given:
        Date date = Date.parse('yyyyMMdd', '20170107')

        when:
        LocalDate ld = date.toLocalDate(TimeZone.default)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Calendar toLocalDate passed a TimeZone that would impact day of month'"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone(TimeZone.getAvailableIDs(0).head())
        Calendar cal = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            set(Calendar.HOUR, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            delegate
        }

        when:
        TimeZone minusOneHour = TimeZone.getTimeZone(TimeZone.getAvailableIDs(-1 * 60 * 60 * 1000).head())
        LocalDate ld = cal.toLocalDate(minusOneHour)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 + 1
    }

    def "Date toLocalDate passed a TimeZone that would impact day of month'"() {
        given:
        TimeZone timeZone = TimeZone.getTimeZone(TimeZone.getAvailableIDs(0).head())
        Date date = new GregorianCalendar(timeZone).with {       
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 7)
            set(Calendar.HOUR, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            delegate
        }.time

        when:
        TimeZone minusOneHour = TimeZone.getTimeZone(TimeZone.getAvailableIDs(-1 * 60 * 60 * 1000).head())
        LocalDate ld = date.toLocalDate(minusOneHour)

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7 + 1
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

    def "toLocalDate is not impacted by Date's time zone"() {
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
            assert localDate == '2017-01-07' : "Unexpected calendar date of $localDate for time zone $timeZone"
        }
    }

    def "Calendar.toInstant() same as Date"() {
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
}    