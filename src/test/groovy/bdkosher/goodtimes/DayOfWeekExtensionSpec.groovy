package bdkosher.goodtimes

import java.time.*
import java.time.format.*
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.DayOfWeek.*

class DayOfWeekExtensionSpec extends Specification {

    @Unroll("the day after #this_day should be #next_day")
    def "next() returns the next day of the week"() {
        expect:
        next_day == this_day.next()

        where:
        this_day  | next_day
        SUNDAY    | MONDAY
        MONDAY    | TUESDAY
        TUESDAY   | WEDNESDAY
        WEDNESDAY | THURSDAY
        THURSDAY  | FRIDAY
        FRIDAY    | SATURDAY
        SATURDAY  | SUNDAY
    }

    @Unroll("the day before #this_day should be #previous_day")
    def "previous() returns the previous day of the week"() {
        expect:
        previous_day == this_day.previous()

        where:
        this_day  | previous_day
        SUNDAY    | SATURDAY
        MONDAY    | SUNDAY
        TUESDAY   | MONDAY
        WEDNESDAY | TUESDAY
        THURSDAY  | WEDNESDAY
        FRIDAY    | THURSDAY
        SATURDAY  | FRIDAY
    }
    
    def "range of a week full of days, starting Monday"() {
        expect:
        (MONDAY..SUNDAY).collect { it.value }.sum() == (1..7).sum()
        // TODO: figure out how to possibly support a range starting with SUNDAY
        //(SUNDAY..SATURDAY).collect { it.value }.sum() == (1..7).sum()
    }
}