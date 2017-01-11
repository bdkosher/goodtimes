package bdkosher.goodtimes

import java.time.*
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

    def "Date toLocalDate"() {
        given:
        Date date = Date.parse('yyyyMMdd', '20170107')
        LocalDate ld = date.toLocalDate()

        expect:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

}    