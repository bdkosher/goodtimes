package bdkosher.goodtimes

import java.time.*
import java.time.temporal.*
import spock.lang.Specification
import spock.lang.Unroll

class DurationExtensionSpec extends Specification {

    def "positive() will turn a negative duration positive"() {
        given:
        Duration neg = Duration.parse('PT-7S')

        when:
        Duration pos = +neg

        then:
        pos.seconds == 7
    }

    def "positive() has no effect on a positive duration"() {
        given:
        Duration pos = Duration.parse('PT7S')

        when:
        Duration stillPos = +pos

        then:
        stillPos.seconds == 7
    }

    def "negative() works like negated()"() {
        given:
        Duration pos = Duration.parse('PT77S')
        Duration neg = Duration.parse('PT-77S')

        when:
        Duration negatedPos = -pos
        Duration negatedNeg = -neg

        then:
        negatedPos.seconds == -77
        negatedNeg.seconds == 77
    }

    def "getAt works as expected"() {
        given:
        Duration d = Duration.parse('PT1.000000002S')

        expect:
        d[ChronoUnit.SECONDS] == 1
        d[ChronoUnit.NANOS] == 2
    }

    def "multiply works as expected"() {
        given:
        Duration d = Duration.parse('PT7.7S')

        when:
        Duration doubled = d * 2

        then:
        doubled.seconds == 15
        doubled.nano == 4e8
    }

    def "divide works as expected"() {
        given:
        Duration d = Duration.parse('PT14.14S')

        when:
        Duration halved = d / 2

        then:
        halved.seconds == 7
        halved.nano == 7e7
    }

    def "describing a duration of whole seconds only"() {
        given:
        Duration d = Duration.parse('PT7S')

        when:
        Map desc = d.describe()

        then:
        desc[ChronoUnit.NANOS] == 0
        desc[ChronoUnit.SECONDS] == 7
        desc[ChronoUnit.MINUTES] == 0
        desc[ChronoUnit.HOURS] == 0
    }

    def "describing a duration of fractional seconds only"() {
        given:
        Duration d = Duration.parse('PT0.000000001S')

        when:
        Map desc = d.describe()

        then:
        desc[ChronoUnit.NANOS] == 1
        desc[ChronoUnit.SECONDS] == 0
        desc[ChronoUnit.MINUTES] == 0
        desc[ChronoUnit.HOURS] == 0
    }

    def "describing a duration of minutes and seconds"() {
        given:
        Duration d = Duration.parse('PT7M7S')

        when:
        Map desc = d.describe()

        then:
        desc[ChronoUnit.NANOS] == 0
        desc[ChronoUnit.SECONDS] == 7
        desc[ChronoUnit.MINUTES] == 7
        desc[ChronoUnit.HOURS] == 0
    }

    def "describing a duration of minutes and fractional seconds"() {
        given:
        Duration d = Duration.parse('PT7M7.07S')

        when:
        Map desc = d.describe()

        then:
        desc[ChronoUnit.NANOS] == 7e7
        desc[ChronoUnit.SECONDS] == 7
        desc[ChronoUnit.MINUTES] == 7
        desc[ChronoUnit.HOURS] == 0
    }

    def "describing a duration of hours and fractional seconds"() {
        given:
        Duration d = Duration.parse('PT7H7.07S')

        when:
        Map desc = d.describe()

        then:
        desc[ChronoUnit.NANOS] == 7e7
        desc[ChronoUnit.SECONDS] == 7
        desc[ChronoUnit.MINUTES] == 0
        desc[ChronoUnit.HOURS] == 7
    }       

    def "describing a negative duration"() {
        given:
        Duration d = Duration.parse('PT7H7.07S').negated()

        when:
        Map desc = d.describe()

        then:
        desc[ChronoUnit.NANOS] == -7e7
        desc[ChronoUnit.SECONDS] == -7
        desc[ChronoUnit.MINUTES] == 0
        desc[ChronoUnit.HOURS] == -7
    }
}