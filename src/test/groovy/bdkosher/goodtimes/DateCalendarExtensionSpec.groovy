package bdkosher.goodtimes

import java.time.*
import java.time.format.*
import spock.lang.Specification

class DateCalendarExtensionSpec extends Specification {

    def "Date to Calendar"() {
        given:
        Date d = new Date()

        expect:
        d.toCalendar().time == d
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

    def "Date toLocalDate in system default timezone"() {
        given:
        Date date = Date.parse('yyyyMMdd', '20170107')
        LocalDate ld = date.toLocalDate()

        expect:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "toLocalDate is not impacted when Calendar's own ZoneId is passed"() {
        expect:  
        TimeZone.availableIDs.collect { id -> TimeZone.getTimeZone(id) }.each { timeZone ->
            Calendar cal = new GregorianCalendar(timeZone)
            cal.with {
                setTimeZone(timeZone)                
                set(Calendar.YEAR, 2017)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.DAY_OF_MONTH, 7)
                // this test case does not hold true at times close to the end of the day
                clearTime()
            }
            def localDate = cal.toLocalDate(cal.zoneId).format(DateTimeFormatter.ISO_LOCAL_DATE)
            assert localDate == '2017-01-07' : "Unexpected calendar date of $localDate for timezone $timeZone (cal's zone id=$cal.zoneId)"    
        }
    }

    def "toLocalDate is not impacted when date's own TimeZone is passed"() {
        expect:  
        TimeZone.availableIDs.collect { id -> TimeZone.getTimeZone(id) }.each { timeZone ->
            Calendar cal = new GregorianCalendar(timeZone)
            cal.with {
                setTimeZone(timeZone)                
                set(Calendar.YEAR, 2017)
                set(Calendar.MONTH, Calendar.JANUARY)
                set(Calendar.DAY_OF_MONTH, 7)
                clearTime()
            }
            def localDate = cal.toLocalDate(timeZone).format(DateTimeFormatter.ISO_LOCAL_DATE)
            assert localDate == '2017-01-07' : "Unexpected calendar date of $localDate for timezone $timeZone"
        }
    }       

}    