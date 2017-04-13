package bdkosher.goodtimes

import java.time.*
import java.time.temporal.*
import spock.lang.Specification
import spock.lang.Unroll

class PeriodExtensionSpec extends Specification {

    def "next() increases Period by one day"() {
        given:
        Period p = Period.ofDays(1)

        when:
        p++

        then:
        p.days == 2
    }

    def "previous() decreases Period by one day"() {
        given:
        Period p = Period.ofDays(1)

        when:
        p--

        then:
        p.days == 0
    }    

    def "copyWith empty map clones the Period"() {
        given:
        Period p = Period.of(2, 3, 4)

        when:
        Period copy = p.copyWith([:])

        then:
        copy == p
    }

    def "copyWith with single-valued Map"() {
        given:
        Period p = Period.of(2, 3, 4)

        when:
        Period noYears = p.copyWith([(ChronoUnit.YEARS): 0])
        Period noMonths = p.copyWith([(ChronoUnit.MONTHS) : 0])
        Period noDays = p.copyWith([(ChronoUnit.DAYS) : 0])

        then:
        noYears.days == p.days
        noYears.months == p.months
        noYears.years == 0
        noMonths.days == p.days
        noMonths.months == 0
        noMonths.years == p.years
        noDays.days == 0
        noDays.months == p.months
        noDays.years == p.years
    }

    def "copyWith with double-valued Map"() {
        given:
        Period p = Period.of(2, 3, 4)

        when:
        Period onlyYears = p.copyWith([(ChronoUnit.MONTHS): 0, (ChronoUnit.DAYS) : 0])
        Period onlyMonths = p.copyWith([(ChronoUnit.YEARS) : 0, (ChronoUnit.DAYS) : 0])
        Period onlyDays = p.copyWith([(ChronoUnit.YEARS) : 0, (ChronoUnit.MONTHS) : 0])

        then:
        onlyYears.days == 0
        onlyYears.months == 0
        onlyYears.years == p.years
        onlyMonths.days == 0
        onlyMonths.months == p.months
        onlyMonths.years == 0
        onlyDays.days == p.days
        onlyDays.months == 0
        onlyDays.years == 0
    }    

    def "copyWith with full Map overwrites all fields"() {
        given:
        Period p = Period.of(2, 3, 4)

        when:
        Period sevens = p.copyWith([(ChronoUnit.YEARS): 7, (ChronoUnit.MONTHS): 7, (ChronoUnit.DAYS) : 7])

        then:
        sevens.days == 7
        sevens.months == 7
        sevens.years == 7
    }

    def "positive() will turn a single negative field positive"() {
        given:
        Period negYears = Period.of(-1, 1, 1)
        Period negMonths = Period.of(1, -1, 1)
        Period negDays = Period.of(1, 1, -1)

        when:
        Period posYears = +negYears
        Period posMonths = +negMonths
        Period posDays = +negDays

        then:
        posYears.years == 1
        posYears.months == 1
        posYears.days == 1
        posMonths.years == 1
        posMonths.months == 1
        posMonths.days == 1
        posDays.years == 1
        posDays.months == 1
        posDays.days == 1                
    }

    def "positive() will turn a two negative fields positive"() {
        given:
        Period negYearsAndMonths = Period.of(-1, -1, 1)
        Period negYearsAndDays = Period.of(-1, 1, -1)
        Period negMonthsAndDays = Period.of(1, -1, -1)

        when:
        Period posYearsAndMonths = +negYearsAndMonths
        Period posYearsAndDays = +negYearsAndDays
        Period posMonthsAndDays = +negMonthsAndDays

        then:
        posYearsAndMonths.years == 1
        posYearsAndMonths.months == 1
        posYearsAndMonths.days == 1
        posYearsAndDays.years == 1
        posYearsAndDays.months == 1
        posYearsAndDays.days == 1
        posMonthsAndDays.years == 1
        posMonthsAndDays.months == 1
        posMonthsAndDays.days == 1                
    }

    def "positive() will turn all negative fields positive"() {
        given:
        Period neg = Period.of(-1, -1, -1)

        when:
        Period pos = +neg

        then:
        pos.years == 1
        pos.months == 1
        pos.days == 1
    }

    def "positive() has no effect on all positive fields"() {
        given:
        Period pos = Period.of(7, 7, 7)

        when:
        Period stillPos = +pos

        then:
        stillPos.years == 7
        stillPos.months == 7
        stillPos.days == 7
    }

    def "negative() works like negated()"() {
        given:
        Period p = Period.of(2, -3, 4)

        when:
        Period n = -p

        then:
        n.years == -2
        n.months == 3
        n.days == -4
    }

    def "getAt works as expected"() {
        given:
        Period p = Period.of(2, 3, 4)

        expect:
        p[ChronoUnit.YEARS] == 2
        p[ChronoUnit.MONTHS] == 3
        p[ChronoUnit.DAYS] == 4
    }

    def "multiply works as expected"() {
        given:
        Period p = Period.of(2, 3, 4)

        when:
        Period doubled = p * 2

        then:
        doubled.years == 4
        doubled.months == 6
        doubled.days == 8
    }
}