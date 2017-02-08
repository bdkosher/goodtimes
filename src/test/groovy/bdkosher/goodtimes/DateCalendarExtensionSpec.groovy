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
        LocalDate ld = date.toLocalDate()

        expect:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
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

}    