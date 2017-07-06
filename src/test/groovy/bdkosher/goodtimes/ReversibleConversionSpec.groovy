package bdkosher.goodtimes

import java.time.*
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.Month.*

/**
 * Ensures that when converting between java.util and java.time types that the conversions are reversible.
 */
class ReversibleConversionSpec extends Specification {

    def eachTimeZone(Closure closure) {
        TimeZone.availableIDs.collect { id -> TimeZone.getTimeZone(id) }.each { timeZone ->
            closure(timeZone)
        }
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