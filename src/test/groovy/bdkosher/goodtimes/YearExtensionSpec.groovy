package bdkosher.goodtimes

import java.time.*
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.Month.*

class YearExtensionSpec extends Specification {

    def "+(positive year) has no effect"() {
        given:
        Year y = Year.now()

        when:
        Year positive = +y

        then:
        y == positive
    }

    def "+(negative year) makes it positive"() {
        given:
        Year y = Year.of(-1)

        when:
        Year positive = +y

        then:
        positive.value == 1
    }

    def "-(positive year) makes it negative"() {
        given:
        Year y = Year.of(1)

        when:
        Year negative = -y

        then:
        negative.value == -1
    }

    def "-(negative year) makes it positive"() {
        given:
        Year y = Year.of(-1)

        when:
        Year negative = -y

        then:
        negative.value == 1
    }

    def "++ is next year"() {
        given:
        Year y = Year.of(-1)

        when:
        Year next = ++y

        then:
        next.value == 0
    }

    def "-- is last year"() {
        given:
        Year y = Year.of(0)

        when:
        Year next = --y

        then:
        next.value == -1
    }

    def "adding positive years"() {
        given:
        Year y = Year.of(0)

        when:
        Year later = y + 10

        then:
        later.value == 10
    }

    def "adding negaive years"() {
        given:
        Year y = Year.of(10)

        when:
        Year later = y + -10

        then:
        later.value == 0
    }

    def "subtracting positive years"() {
        given:
        Year y = Year.of(0)

        when:
        Year later = y - 10

        then:
        later.value == -10
    }

    def "subtracting negative years"() {
        given:
        Year y = Year.of(-10)

        when:
        Year later = y - -10

        then:
        later.value == 0
    }

    def "adding positive years by type"() {
        given:
        Year y = Year.of(0)

        when:
        Year later = y + Year.of(10)

        then:
        later.value == 10
    }

    def "adding negaive years by type"() {
        given:
        Year y = Year.of(10)

        when:
        Year later = y + Year.of(-10)

        then:
        later.value == 0
    }

    def "subtracting positive years by type"() {
        given:
        Year y = Year.of(0)

        when:
        Year later = y - Year.of(10)

        then:
        later.value == -10
    }

    def "subtracting negative years by type"() {
        given:
        Year y = Year.of(-10)

        when:
        Year later = y - Year.of(-10)

        then:
        later.value == 0
    }    

    def "creating a YearMonth from a Month"() {
        given:
        Year y = Year.of(1980)

        when:
        YearMonth ym = y << Month.JUNE

        then:
        ym.year == 1980
        ym.month == Month.JUNE
    }
    
    def "creating a LocalDate from a MonthDay"() {
        given:
        Year y = Year.of(2000)
        
        when:
        LocalDate ld = y << MonthDay.of(Month.FEBRUARY, 1)

        then:
        ld.year == 2000
        ld.month == Month.FEBRUARY
        ld.day == 1
    }
      
    def "creating a LocalDate from a day of year"() {
        given:
        Year y = Year.of(2005)

        when:
        LocalDate ld = y << 1

        then:
        ld.year == 2005
        ld.month == Month.JANUARY
        ld.day == 1
    }

    def "Year asType for numeric types"() {
        given:
        Year y = Year.of(2000)

        when:
        int yint = y as int
        Integer yInteger = y as Integer
        long ylong = y as long
        Long yLong = y as Long
        BigInteger ybi = y as BigInteger

        then:
        yint == 2000
        yInteger == new Integer(2000)
        ylong == 2000L
        yLong == new Long(2000L)
        ybi == 2000G
    }

    def "Year asType for non-numeric types"() {
        given:
        Year y = Year.of(2000)

        when:
        String ys = y as String

        then:
        ys == '2000'
    }

}