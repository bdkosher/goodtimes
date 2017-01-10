package bdkosher.goodtimes

import java.time.*
import spock.lang.Specification

class LocalDateExtensionSpec extends Specification {

    def "Calendar toLocalDate"() {
        given:
        Calendar cal = Date.parse('yyyyMMdd', '20170107').toCalendar()
        LocalDate ld = cal.toLocalDate()

        expect:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "Date toLocalDate"() {
        given:
        Date date = Date.parse('yyyyMMdd', '20170107')
        LocalDate ld = date.toLocalDate()

        expect:
        ld.year == 2017
        ld.monthValue == 1
        ld.dayOfMonth == 7
    }

    def "plus days"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig + 2

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth + 2
    }

    def "minus days"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig - 2

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth - 2
    }

    def "next day"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig.next()

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth + 1
    }

    def "previous day"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig.previous()

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth - 1
    }     

    def "plus Period of days only"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig + Period.ofDays(1)

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth + 1
    }

    def "plus Period of months only"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig + Period.ofMonths(1)

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue + 1
        mod.dayOfMonth == orig.dayOfMonth
    }

    def "plus Period of years only"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig + Period.ofYears(1)

        expect:
        mod.year == orig.year + 1
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth
    }

    def "plus Period of days, months, and years"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig + Period.of(1, 1, 1)

        expect:
        mod.year == orig.year + 1
        mod.monthValue == orig.monthValue + 1
        mod.dayOfMonth == orig.dayOfMonth + 1
    }

    def "minus Period of days only"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig - Period.ofDays(1)

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth - 1
    }

    def "minus Period of months only"() {
        given:
        LocalDate orig = LocalDate.of(2017, 2, 8)
        LocalDate mod = orig - Period.ofMonths(1)

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue - 1
        mod.dayOfMonth == orig.dayOfMonth
    }

    def "minus Period of years only"() {
        given:
        LocalDate orig = LocalDate.of(2017, 2, 8)
        LocalDate mod = orig - Period.ofYears(1)

        expect:
        mod.year == orig.year - 1
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth
    }

    def "minus Period of days, months, and years"() {
        given:
        LocalDate orig = LocalDate.of(2017, 2, 8)
        LocalDate mod = orig - Period.of(1, 1, 1)

        expect:
        mod.year == orig.year - 1
        mod.monthValue == orig.monthValue - 1
        mod.dayOfMonth == orig.dayOfMonth - 1
    }        

}