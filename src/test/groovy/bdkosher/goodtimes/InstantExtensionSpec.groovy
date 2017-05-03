package bdkosher.goodtimes

import java.time.*
import java.time.chrono.*
import java.time.temporal.*
import spock.lang.Specification

class InstantExtensionSpec extends Specification {

    def "Instant to Date"() {
        given:
        long millis = 1234567L
        Instant time = Instant.ofEpochMilli(millis)

        when:
        Date date = time.toDate()

        then:
        date.time == millis
    }

    def "Instant to Calendar"() {
        given:
        long millis = 1234567L
        Instant time = Instant.ofEpochMilli(millis)

        when:
        Calendar cal = time.toCalendar()

        then:
        cal.time.time == millis
    }    

    def "plus seconds"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig + 2

        then:
        mod.epochSecond== orig.epochSecond + 2
    }

    def "minus seconds"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig - 2

        then:
        mod.epochSecond == orig.epochSecond - 2
    }

    def "next second"() {
        given:
        Instant orig = Instant.now()
        
        when:
        Instant next = orig.next()

        then:
        next.epochSecond == orig.epochSecond + 1
    }

    def "previous second"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant prev = orig.previous()

        then:
        prev.epochSecond == orig.epochSecond - 1
    }    

    def "plus Duration of seconds only"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig + Duration.ofSeconds(1)

        then:
        mod.epochSecond == orig.epochSecond + 1
    }

    def "plus Duration of negative seconds only"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig + Duration.ofSeconds(-1)

        then:
        mod.epochSecond == orig.epochSecond - 1
    }    

    def "plus Duration of minutes only"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig + Duration.ofMinutes(1)

        then:
        mod.epochSecond == orig.epochSecond + 60
    }

    def "plus Duration of hours only"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig + Duration.ofHours(1)

        then: 
        mod.epochSecond == orig.epochSecond + (60 * 60)
    }

    def "plus Duration of hours, minutes, and seconds"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig + Duration.ofHours(1).plusMinutes(1).plusSeconds(1)

        then: 
        mod.epochSecond == orig.epochSecond + (60 * 60) + 60 + 1
    }

    def "plus Duration of negative hours, minutes, and seconds"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig + Duration.ofHours(-1).plusMinutes(-1).plusSeconds(-1)

        then: 
        mod.epochSecond == orig.epochSecond - (60 * 60) - 60 - 1
    }    

    def "minus Duration of seconds only"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig - Duration.ofSeconds(1)

        then:
        mod.epochSecond == orig.epochSecond - 1
    }

    def "minus Duration of minutes only"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig - Duration.ofMinutes(1)

        then:
        mod.epochSecond == orig.epochSecond - 60
    }

    def "minus Duration of hours only"() {
        given:
        Instant orig = Instant.now()

        when:
        Instant mod = orig - Duration.ofHours(1)

        then:
        mod.epochSecond == orig.epochSecond - (60 * 60)
    }

    def "subtracting Instants produces a positive Duration"() {
        given:
        Instant now = Instant.now()
        Instant later = now.plusSeconds(1000)

        when:
        Duration duration = now - later

        then:
        duration.seconds == 1000
    }

    def "subtracting Instants produces a positive Duration"() {
        given:
        Instant now = Instant.now()
        Instant later = now.plusSeconds(1000)

        when:
        Duration duration = later - now

        then:
        duration.seconds == -1000
    }

    def "getAt Temporal Field"() {
        given:
        Instant instant = Instant.EPOCH
                .with(ChronoField.INSTANT_SECONDS, 7)
                .with(ChronoField.NANO_OF_SECOND, 7007007)

        expect:
        instant[ChronoField.NANO_OF_SECOND] == 7007007
        instant[ChronoField.MICRO_OF_SECOND] == 7007
        instant[ChronoField.MILLI_OF_SECOND] == 7
        instant[ChronoField.INSTANT_SECONDS] == 7
    }

    def "downto() cannot be called with date after this date"() {
        setup:
        Instant now = Instant.now()
        Instant later = now + 1

        when:
        now.downto(later) { t -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "downto() is called once when the two dates are the same"() {
        setup:
        Instant now = Instant.now()
        boolean closureCalledOnce = false

        when:
        now.downto(now) { t -> 
            closureCalledOnce = true
        }

        then:
        closureCalledOnce
    }

    def "downto() can be passed no-arg closure"() {
        setup:
        Instant now = Instant.now()
        Instant oneMinuteAgo = now - 60
        int count = 0

        when:
        now.downto(oneMinuteAgo) {
            count++
        }

        then:
        count == 61
    }

    def "downto() closure passed decreasing arg"() {
        setup:
        Instant higher = Instant.now()
        Instant lower = Instant.now() - 2
        int iter = higher.epochSecond

        expect:
        higher.downto(lower) { t ->
            assert iter-- == t.epochSecond
        }
    }

    def "upto() cannot be called with date before this date"() {
        setup:
        Instant now = Instant.now()
        Instant oneSecondAgo = now - 1

        when:
        now.upto(oneSecondAgo) { t -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "upto() is called once when the two dates are the same"() {
        setup:
        Instant now = Instant.now()
        boolean closureCalledOnce = false

        when:
        now.upto(now) { t -> 
            closureCalledOnce = true
        }

        then:
        closureCalledOnce
    }

    def "upto() can be passed no-arg closure"() {
        setup:
        Instant now = Instant.now()
        Instant oneMinuteFromNow = now + 60
        int count = 0

        when:
        now.upto(oneMinuteFromNow) {
            count++
        }

        then:
        count == 61
    }

    def "upto() closure passed increasing arg"() {
        setup:
        Instant lower = Instant.now()
        Instant higher = lower + 2
        int iter = lower.epochSecond

        expect:
        lower.upto(higher) { t ->
            assert iter++ == t.epochSecond
        }        
    }
}