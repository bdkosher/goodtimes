package bdkosher.goodtimes

import java.time.*
import java.time.format.*
import spock.lang.Specification

class DateCalendarExtensionSpec extends Specification {

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

    def "toLocalDate is not impacted by date's own time zone"() {
        Calendar cal = Calendar.instance.with {
            set(Calendar.YEAR, 2017)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DATE, 7)
            set(Calendar.HOUR, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            delegate
        }

        expect:
        TimeZone.availableIDs.each { id ->
            cal.setTimeZone(TimeZone.getTimeZone(id))
            assert cal.toLocalDate(cal.zoneId).format(DateTimeFormatter.ISO_LOCAL_DATE) == '2017-01-07' : "Unexpected date for timezone $id"
            assert false
        }
    }    

}    