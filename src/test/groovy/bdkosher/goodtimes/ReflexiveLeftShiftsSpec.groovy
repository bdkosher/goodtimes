package bdkosher.goodtimes

import java.time.*
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.Month.*

/**
 * Ensures that types which support the leftShift method work such that A << B == B << A
 */
class ReflexiveLeftShiftSpec extends Specification {

    def "Year and Month leftShift is reflexive"() {
        given:
        def a = Year.now()
        def b = Month.JULY

        expect:
        a << b == b << a
    }

    def "Year and MonthDay leftShift is reflexive"() {
        given:
        def a = Year.now()
        def b = MonthDay.now()

        expect:
        a << b == b << a
    }    

    def "LocalDate and LocalTime leftShift is reflexive"() {
        given:
        def a = LocalDate.now()
        def b = LocalTime.now()

        expect:
        a << b == b << a
    }

    def "LocalDate and OffsetTime leftShift is reflexive"() {
        given:
        def a = LocalDate.now()
        def b = OffsetTime.now()

        expect:
        a << b == b << a
    }

    def "LocalDateTime and ZoneOffset leftShift is reflexive"() {
        given:
        def a = LocalDateTime.now()
        def b = ZoneOffset.ofHours(5)

        expect:
        a << b == b << a
    }

    def "LocalDateTime and ZoneId leftShift is reflexive"() {
        given:
        def a = LocalDateTime.now()
        def b = ZoneId.systemDefault()

        expect:
        a << b == b << a
    }

    def "LocalTime and ZoneOffset leftShift is reflexive"() {
        given:
        def a = LocalTime.now()
        def b = ZoneOffset.ofHours(5)

        expect:
        a << b == b << a
    }    
}