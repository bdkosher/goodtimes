package bdkosher.goodtimes

import java.time.*
import spock.lang.Specification

class LocalDateExtensionSpec extends Specification {

    def "Calendar toLocalDate"() {
        setup:
        def cal = Date.parse('yyyyMMdd', '20170107').toCalendar()
        
        when:
        LocalDate ld = cal.toLocalDate()

        then:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

}