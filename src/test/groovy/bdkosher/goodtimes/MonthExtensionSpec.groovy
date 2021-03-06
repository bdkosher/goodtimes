package bdkosher.goodtimes

import java.time.Month
import java.time.MonthDay
import java.time.Year
import java.time.YearMonth
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
    
    def "leftShifting a day yields a MonthDay"() {
        when:
        MonthDay md = Month.JANUARY << 1

        then:
        md.month == Month.JANUARY
        md.dayOfMonth == 1
    }

    def "leftShifting a Year yields a YearMonth"() {
        given:
        def year = Year.of(2017)

        when:
        YearMonth ym = Month.DECEMBER << year

        then:
        ym.month == Month.DECEMBER
        ym.year == 2017
    }

    def "Month asType for numeric types"() {
        given:
        Month m = Month.JUNE

        when:
        int mint = m as int
        Integer mInteger = m as Integer
        long mlong = m as long
        Long mLong = m as Long
        BigInteger mbi = m as BigInteger

        then:
        mint == 6
        mInteger == new Integer(6)
        mlong == 6L
        mLong == new Long(6L)
        mbi == 6G
    }

    def "Month asType for String"() {
        given:
        Month m = Month.JUNE

        when:
        String ms = m as String

        then:
        ms == 'JUNE'
    }
}