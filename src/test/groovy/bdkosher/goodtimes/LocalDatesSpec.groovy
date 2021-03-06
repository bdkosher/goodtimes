package bdkosher.goodtimes

import java.time.*
import spock.lang.Specification

import static bdkosher.goodtimes.LocalDates.*

class LocalDatesSpec extends Specification {
    
    def "January date with year"() {
        given:
        LocalDate d = January 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.JANUARY
        d.year == 1970
    }

    def "February date with year"() {
        given:
        LocalDate d = February 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.FEBRUARY
        d.year == 1970
    }

    def "March date with year"() {
        given:
        LocalDate d = March 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.MARCH
        d.year == 1970
    }

    def "April date with year"() {
        given:
        LocalDate d = April 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.APRIL
        d.year == 1970
    }

    def "May date with year"() {
        given:
        LocalDate d = May 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.MAY
        d.year == 1970
    }

    def "June date with year"() {
        given:
        LocalDate d = June 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.JUNE
        d.year == 1970
    }

    def "July date with year"() {
        given:
        LocalDate d = July 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.JULY
        d.year == 1970
    }

    def "August date with year"() {
        given:
        LocalDate d = August 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.AUGUST
        d.year == 1970
    }

    def "September date with year"() {
        given:
        LocalDate d = September 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.SEPTEMBER
        d.year == 1970
    }

    def "October date with year"() {
        given:
        LocalDate d = October 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.OCTOBER
        d.year == 1970
    }

    def "November date with year"() {
        given:
        LocalDate d = November 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.NOVEMBER
        d.year == 1970
    }

    def "December date with year"() {
        given:
        LocalDate d = December 1, 1970

        expect:
        d.dayOfMonth == 1
        d.month == Month.DECEMBER
        d.year == 1970
    }

    def "January date with default year"() {
        given:
        LocalDate d = January 1
        int thisYear = Year.now().value  

        expect:
        d.dayOfMonth == 1
        d.month == Month.JANUARY
        d.year == thisYear
    }

    def "February date with default year"() {
        given:
        LocalDate d = February 1
        int thisYear = Year.now().value  

        expect:
        d.dayOfMonth == 1
        d.month == Month.FEBRUARY
        d.year == thisYear
    }

    def "March date with default year"() {
        given:
        LocalDate d = March 1
        int thisYear = Year.now().value  

        expect:
        d.dayOfMonth == 1
        d.month == Month.MARCH
        d.year == thisYear
    }

    def "April date with default year"() {
        given:
        LocalDate d = April 1
        int thisYear = Year.now().value  

        expect:
        d.dayOfMonth == 1
        d.month == Month.APRIL
        d.year == thisYear
    }

    def "May date with default year"() {
        given:
        LocalDate d = May 1
        int thisYear = Year.now().value  

        expect:
        d.dayOfMonth == 1
        d.month == Month.MAY
        d.year == thisYear
    }

    def "June date with default year"() {
        given:
        LocalDate d = June 1
        int thisYear = Year.now().value  

        expect:
        d.dayOfMonth == 1
        d.month == Month.JUNE
        d.year == thisYear
    }

    def "July date with default year"() {
        given:
        LocalDate d = July 1
        int thisYear = Year.now().value  

        expect:
        d.dayOfMonth == 1
        d.month == Month.JULY
        d.year == thisYear
    }

    def "August date with default year"() {
        given:
        LocalDate d = August 1
        int thisYear = Year.now().value        

        expect:
        d.dayOfMonth == 1
        d.month == Month.AUGUST
        d.year == thisYear
    }

    def "September date with default year"() {
        given:
        LocalDate d = September 1
        int thisYear = Year.now().value

        expect:
        d.dayOfMonth == 1
        d.month == Month.SEPTEMBER
        d.year == thisYear
    }

    def "October date with default year"() {
        given:
        LocalDate d = October 1
        int thisYear = Year.now().value

        expect:
        d.dayOfMonth == 1
        d.month == Month.OCTOBER
        d.year == thisYear
    }

    def "November date with default year"() {
        given:
        LocalDate d = November 1
        int thisYear = Year.now().value

        expect:
        d.dayOfMonth == 1
        d.month == Month.NOVEMBER
        d.year == thisYear
    }

    def "December date with default year"() {
        given:
        LocalDate d = December 1
        int thisYear = Year.now().value

        expect:
        d.dayOfMonth == 1
        d.month == Month.DECEMBER
        d.year == thisYear
    }    
}
