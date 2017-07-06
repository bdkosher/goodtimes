package bdkosher.goodtimes

import java.time.*
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.Month.*

/**
 * Ensures that when converting between java.util and java.time types that the conversions are reversible.
 * We start with a java.util type because the java.time types are more precise; they store nanoseconds and 
 * zone offsets in seconds.
 */
class ReversibleConversionSpec extends Specification {

    def eachTimeZone(Closure closure) {
        TimeZone.availableIDs.collect { id -> TimeZone.getTimeZone(id) }.each { timeZone ->
            closure(timeZone)
        }
    }

    def "Date and LocalDate reversibility"() {
        given: 
        Date oldType = new Date().clearTime()

        when:
        def newType = oldType.toLocalDate()

        then:
        newType.toDate() == oldType
    }

    def "Date and LocalDateTime reversibility"() {
        given: 
        Date oldType = new Date()

        when:
        def newType = oldType.toLocalDateTime()

        then:
        newType.toDate() == oldType
    }

    def "Date and LocalTime reversibility"() {
        given: 
        Date oldType = new Date()

        when:
        def newType = oldType.toLocalTime()

        then:
        newType.toDate() == oldType
    }

    def "Date and ZonedDateTime reversibility"() {
        given: 
        Date oldType = new Date()

        when:
        def newType = oldType.toZonedDateTime()

        then:
        newType.toDate() == oldType
    }

    def "Date and OffsetDateTime reversibility"() {
        given: 
        Date oldType = new Date()

        when:
        def newType = oldType.toOffsetDateTime()

        then:
        newType.toDate() == oldType
    }

    def "Date and OffsetTime reversibility"() {
        given: 
        Date oldType = new Date()
        String offsetTimeFormat = 'HH:mm:ss XXX'

        when:
        def newType = oldType.toOffsetTime()

        then:
        newType.toDate().format(offsetTimeFormat) == oldType.format(offsetTimeFormat)
    }

    def "Calendar and LocalDate reversibility"() {
        given: 
        Calendar oldType = Calendar.instance.clearTime()

        when:
        def newType = oldType.toLocalDate()

        then:
        newType.toCalendar() == oldType
    }

    def "Calendar and LocalDateTime reversibility"() {
        given: 
        Calendar oldType = Calendar.instance

        when:
        def newType = oldType.toLocalDateTime()

        then:
        newType.toCalendar() == oldType
    }

    def "Calendar and LocalTime reversibility"() {
        given: 
        Calendar oldType = Calendar.instance

        when:
        def newType = oldType.toLocalTime()

        then:
        newType.toCalendar() == oldType
    }

    def "Calendar and ZonedDateTime reversibility"() {
        given: 
        Calendar oldType = Calendar.instance

        when:
        def newType = oldType.toZonedDateTime()

        then:
        newType.toCalendar() == oldType
    }

    def "Calendar and OffsetDateTime reversibility"() {
        given: 
        Calendar oldType = Calendar.instance

        when:
        def newType = oldType.toOffsetDateTime()

        then:
        newType.toCalendar() == oldType
    }

    def "Calendar and OffsetTime reversibility"() {
        given: 
        Calendar oldType = Calendar.instance
        String offsetTimeFormat = 'HH:mm:ss XXX'

        when:
        def newType = oldType.toOffsetTime()

        then:
        newType.toCalendar().format(offsetTimeFormat) == oldType.format(offsetTimeFormat)
    }

    def "TimeZone and ZoneOffset reversibility"() {
        expect:
        eachTimeZone { timeZone ->
            Instant now = Instant.now()
            ZoneOffset zoneOffset = timeZone.toZoneOffset(now)
            TimeZone newTimeZone = zoneOffset.toTimeZone()

            assert timeZone.getOffset(now.toEpochMilli()) == newTimeZone.getOffset(now.toEpochMilli())
        }
    } 

}