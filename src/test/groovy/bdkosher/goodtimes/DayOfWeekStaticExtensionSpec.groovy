package bdkosher.goodtimes

import java.time.DayOfWeek
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.DayOfWeek.*

class DayOfWeekStaticExtensionSpec extends Specification {

    def "values(start) returns array of expected length"() {
        expect:
        DayOfWeek.values().each { startDay ->
            assert DayOfWeek.values(startDay).length == 7
        }
    }

    def "values(start) returns array with expected first element"() {
        expect:
        DayOfWeek.values().each { startDay ->
            assert DayOfWeek.values(startDay)[0] == startDay
        }
    }

    def "eachDay(closure) starts with Sunday"() {
        given:
        def values = []
        DayOfWeek.eachDay { day ->
            values << day
        }

        expect:
        values[0] == SUNDAY
    }

    def "eachDay(closure) traverses each day"() {
        given:
        def values = EnumSet.noneOf(DayOfWeek)
        DayOfWeek.eachDay { day ->
            values << day
        }

        expect:
        values.size() == DayOfWeek.values().length
    }

    def "eachDay(closure, MONDAY) starts with Monday"() {
        given:
        def values = []
        DayOfWeek.eachDay(MONDAY) { day ->
            values << day
        }

        expect:
        values[0] == MONDAY
    }

    def "eachDay(closure) closure may be no-arg"() {
        given:
        int count = 0
        DayOfWeek.eachDay { 
            ++count
        }

        expect:
        count == DayOfWeek.values().length
    }    
}