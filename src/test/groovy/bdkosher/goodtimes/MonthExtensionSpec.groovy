package bdkosher.goodtimes

import java.time.Month
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.Month.*

class MonthExtensionSpec extends Specification {

    @Unroll("the month after #this_month should be #next_month")
    def "next() returns the next month of the year"() {
        expect:
        next_month == this_month.next()

        where:
        this_month | next_month
        JANUARY    | FEBRUARY
        FEBRUARY   | MARCH
        MARCH      | APRIL
        APRIL      | MAY
        MAY        | JUNE
        JUNE       | JULY
        JULY       | AUGUST
        AUGUST     | SEPTEMBER
        SEPTEMBER  | OCTOBER
        OCTOBER    | NOVEMBER
        NOVEMBER   | DECEMBER
        DECEMBER   | JANUARY
    }

    @Unroll("the month before #this_month should be #previous_month")
    def "previous() returns the previous month of the year"() {
        expect:
        previous_month == this_month.previous()

        where:
        this_month | previous_month
        JANUARY    | DECEMBER
        FEBRUARY   | JANUARY
        MARCH      | FEBRUARY
        APRIL      | MARCH
        MAY        | APRIL
        JUNE       | MAY
        JULY       | JUNE
        AUGUST     | JULY
        SEPTEMBER  | AUGUST
        OCTOBER    | SEPTEMBER
        NOVEMBER   | OCTOBER
        DECEMBER   | NOVEMBER
    }

    @Unroll("#months months after #this_month should be #future_month")
    def "plus(number of months)"() {
        expect:
        this_month + months == future_month

        where:
        this_month | months | future_month
        JANUARY    | 0      | JANUARY
        JANUARY    | 1      | FEBRUARY
        JANUARY    | 2      | MARCH
        JANUARY    | 3      | APRIL
        JANUARY    | 4      | MAY
        JANUARY    | 5      | JUNE
        JANUARY    | 6      | JULY
        JANUARY    | 7      | AUGUST
        JANUARY    | 8      | SEPTEMBER
        JANUARY    | 9      | OCTOBER
        JANUARY    | 10     | NOVEMBER
        JANUARY    | 11     | DECEMBER
        JANUARY    | 12     | JANUARY
        JANUARY    | 23     | DECEMBER
        JANUARY    | 24     | JANUARY
        JANUARY    | 25     | FEBRUARY
        JANUARY    | -11    | FEBRUARY
        JANUARY    | -12    | JANUARY
        JANUARY    | -13    | DECEMBER
    }

    @Unroll("#months months before #this_month should be #past_month")
    def "minus(number of months)"() {
        expect:
        this_month - months == past_month

        where:
        this_month | months | past_month
        JANUARY    | 0      | JANUARY
        JANUARY    | 1      | DECEMBER
        JANUARY    | 2      | NOVEMBER
        JANUARY    | 3      | OCTOBER
        JANUARY    | 4      | SEPTEMBER
        JANUARY    | 5      | AUGUST
        JANUARY    | 6      | JULY
        JANUARY    | 7      | JUNE
        JANUARY    | 8      | MAY
        JANUARY    | 9      | APRIL
        JANUARY    | 10     | MARCH
        JANUARY    | 11     | FEBRUARY
        JANUARY    | 12     | JANUARY
        JANUARY    | 23     | FEBRUARY        
        JANUARY    | 24     | JANUARY
        JANUARY    | 25     | DECEMBER
        JANUARY    | 36     | JANUARY
        JANUARY    | -11    | DECEMBER
        JANUARY    | -12    | JANUARY
        JANUARY    | -13    | FEBRUARY
    }
    

}