package bdkosher.goodtimes

import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.TextStyle
import spock.lang.Specification
import spock.lang.Unroll

class ZoneIdExtensionSpec extends Specification {

    def "timeZone property equivalent to TimeZone factory method"() {
        expect:
        ZoneId.availableZoneIds.each { id ->
            ZoneId zoneId = ZoneId.of(id)
            assert zoneId.timeZone == TimeZone.getTimeZone(zoneId)
        }
    }

    def "fullName returns the FULL name with default locale"() {
        given:
        def locale = Locale.default

        expect:
        ZoneId.availableZoneIds.each { id ->
            ZoneId zoneId = ZoneId.of(id)
            assert zoneId.fullName == zoneId.getDisplayName(TextStyle.FULL, locale)
        }
    }

    def "fullName returns the FULL name with specified locale"() {
        given:
        def locales = Locale.availableLocales
        def locale = locales[new Random().nextInt(locales.length)]

        expect:
        ZoneId.availableZoneIds.each { id ->
            ZoneId zoneId = ZoneId.of(id)
            assert zoneId.getFullName(locale) == zoneId.getDisplayName(TextStyle.FULL, locale)
        }
    }

    def "shortName returns the SHORT name with default locale"() {
        given:
        def locale = Locale.default

        expect:
        ZoneId.availableZoneIds.each { id ->
            ZoneId zoneId = ZoneId.of(id)
            assert zoneId.shortName == zoneId.getDisplayName(TextStyle.SHORT, locale)
        }
    }

    def "shortName returns the SHORT name with specified locale"() {
        given:
        def locales = Locale.availableLocales
        def locale = locales[new Random().nextInt(locales.length)]

        expect:
        ZoneId.availableZoneIds.each { id ->
            ZoneId zoneId = ZoneId.of(id)
            assert zoneId.getShortName(locale) == zoneId.getDisplayName(TextStyle.SHORT, locale)
        }
    }

    def "getOffset() constant for UTC without daylight savings"() {
        given:
        ZoneOffset offset = ZoneOffset.UTC
        ZoneId zone = ZoneId.of('UTC')
        Instant someTime = Instant.ofEpochMilli(new Random().nextLong())

        expect:
        zone.offset == offset
        zone.getOffset(someTime) == offset
    }

    def "left shifting a LocalDateTime produces an ZonedDateTime"() {
        given:
        LocalDateTime datetime = LocalDateTime.of(2017, Month.JULY, 4, 12, 34, 56)
        ZoneId zoneId = ZoneId.of(ZoneId.availableZoneIds[0])

        when:
        ZonedDateTime odt = zoneId << datetime

        then:
        odt.year == datetime.year
        odt.month == datetime.month
        odt.dayOfMonth == datetime.day
        odt.hour == datetime.hour
        odt.minute == datetime.minute
        odt.second == datetime.second
        odt.zone == zoneId
    }    
}