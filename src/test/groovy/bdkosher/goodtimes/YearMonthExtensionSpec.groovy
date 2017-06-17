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

    def "a lesser YearMonth minus a greater YearMonth yields a positive Period"() {
        given:
        YearMonth first = YearMonth.of(2000, Month.MARCH)
        YearMonth second = YearMonth.of(2017, Month.FEBRUARY)

        when:
        Period p = first - second

        then:
        p.years == 16
        p.months == 11 
    }

    def "a greater YearMonth minus a lesser YearMonth yields a negative Period"() {
        given:
        YearMonth first = YearMonth.of(2000, Month.MARCH)
        YearMonth second = YearMonth.of(2017, Month.FEBRUARY)

        when:
        Period p = second - first

        then:
        p.years == -16
        p.months == -11 
    }

    def "a YearMonth minus itself is a Period of zero"() {
        given:
        YearMonth ym = YearMonth.of(2017, Month.JUNE)

        when:
        Period p = ym - ym

        then:
        p.isZero()
    }

}