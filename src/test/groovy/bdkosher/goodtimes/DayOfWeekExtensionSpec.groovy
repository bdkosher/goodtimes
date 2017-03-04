package bdkosher.goodtimes

import java.time.DayOfWeek
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

    @Unroll("#days days after #day should be #future_day")
    def "plus(number of days)"() {
        expect:
        day + days == future_day

        where:
        day       | days | future_day
        SUNDAY    | 0    | SUNDAY
        SUNDAY    | 1    | MONDAY
        SUNDAY    | 2    | TUESDAY
        SUNDAY    | 3    | WEDNESDAY
        SUNDAY    | 4    | THURSDAY
        SUNDAY    | 5    | FRIDAY
        SUNDAY    | 6    | SATURDAY
        SUNDAY    | 7    | SUNDAY
        SUNDAY    | 14   | SUNDAY
        SUNDAY    | 20   | SATURDAY
        SUNDAY    | 21   | SUNDAY
        SUNDAY    | 22   | MONDAY
        SUNDAY    | -6   | MONDAY
        SUNDAY    | -7   | SUNDAY
        SUNDAY    | -8   | SATURDAY
    }

    @Unroll("#days days before #day should be #past_day")
    def "minus(number of days)"() {
        expect:
        day - days == past_day

        where:
        day       | days | past_day
        SUNDAY    | 0    | SUNDAY
        SUNDAY    | 1    | SATURDAY
        SUNDAY    | 2    | FRIDAY
        SUNDAY    | 3    | THURSDAY
        SUNDAY    | 4    | WEDNESDAY
        SUNDAY    | 5    | TUESDAY
        SUNDAY    | 6    | MONDAY
        SUNDAY    | 7    | SUNDAY
        SUNDAY    | 14   | SUNDAY
        SUNDAY    | 20   | MONDAY
        SUNDAY    | 21   | SUNDAY
        SUNDAY    | 22   | SATURDAY
        SUNDAY    | -6   | SATURDAY
        SUNDAY    | -7   | SUNDAY
        SUNDAY    | -8   | MONDAY
    }
    
}