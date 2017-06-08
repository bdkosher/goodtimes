package bdkosher.goodtimes

import java.time.*
import java.time.temporal.*
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.Month.*

class MonthDayExtensionSpec extends Specification {

    def "getAt with supported TemporalFields"() {
        given:
        MonthDay md = MonthDay.of(Month.MARCH, 26)

        expect:
        md[ChronoField.MONTH_OF_YEAR] == Month.MARCH.value
        md[ChronoField.DAY_OF_MONTH] == 26
    }

    def "leftShift a year int produces a LocalDate"() {
        given:
        MonthDay md = MonthDay.of(Month.MARCH, 26)

        when:
        LocalDate date = md << 2017

        then:
        date.year == 2017
        date.month == Month.MARCH
        date.day == 26
    }

    def "leftShift a year type produces a LocalDate"() {
        given:
        MonthDay md = MonthDay.of(Month.MARCH, 26)

        when:
        LocalDate date = md << Year.of(2017)

        then:
        date.year == 2017
        date.month == Month.MARCH
        date.day == 26
    }

    def "getDay method supported"() {
        given:
        MonthDay md = MonthDay.of(Month.JUNE, 7)

        expect:
        md.day == 7
    }

}