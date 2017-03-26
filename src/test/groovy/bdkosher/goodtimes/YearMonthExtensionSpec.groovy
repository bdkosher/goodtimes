package bdkosher.goodtimes

import java.time.*
import java.time.temporal.*
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.Month.*

class YearMonthExtensionSpec extends Specification {

    def "getAt with supported TemporalFields"() {
        given:
        YearMonth ym = YearMonth.of(2017, Month.MARCH)

        expect:
        ym[ChronoField.YEAR] == 2017
        ym[ChronoField.MONTH_OF_YEAR] == Month.MARCH.value
    }

    def "leftShift produces a LocalDate"() {
        given:
        YearMonth ym = YearMonth.of(2017, Month.MARCH)

        when:
        LocalDate date = ym << 26

        then:
        date.year == 2017
        date.month == Month.MARCH
        date.day == 26
    }

}