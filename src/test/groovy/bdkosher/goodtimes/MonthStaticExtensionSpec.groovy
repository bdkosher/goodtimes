package bdkosher.goodtimes

import java.time.Month
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.Month.*

class MonthStaticExtensionSpec extends Specification {

    def "values(start) returns array of expected length"() {
        expect:
        Month.values().each { startMonth ->
            assert Month.values(startMonth).length == 12
        }
    }

    def "values(start) returns array with expected first element"() {
        expect:
        Month.values().each { startMonth ->
            assert Month.values(startMonth)[0] == startMonth
        }
    }

    def "eachMonth(closure) starts with January"() {
        given:
        def values = []
        Month.eachMonth { month ->
            values << month
        }

        expect:
        values[0] == JANUARY
    }

    def "eachMonth(closure) traverses each month"() {
        given:
        def values = EnumSet.noneOf(Month)
        Month.eachMonth { month ->
            values << month
        }

        expect:
        values.size() == Month.values().length
    }

    def "eachMonth(closure, FEBRUARY) starts with February"() {
        given:
        def values = []
        Month.eachMonth(FEBRUARY) { month ->
            values << month
        }

        expect:
        values[0] == FEBRUARY
    }

    def "eachMonth(closure) closure may be no-arg"() {
        given:
        int count = 0
        Month.eachMonth { 
            ++count
        }

        expect:
        count == Month.values().length
    }    
}