package bdkosher.goodtimes

import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.time.temporal.UnsupportedTemporalTypeException
import spock.lang.Specification
import spock.lang.Unroll

class ZoneOffsetExtensionSpec extends Specification {

    def "adding int hours totalling less than 18"() {
        given:
        ZoneOffset orig = ZoneOffset.UTC

        when:
        ZoneOffset mod = orig + 3

        then:
        mod.totalSeconds == orig.totalSeconds + 3 * 60 * 60
    }

    def "adding int hours totalling more than 18"() {
        given:
        ZoneOffset orig = ZoneOffset.MAX

        when:
        orig + 1

        then:
        thrown DateTimeException
    }

    def "adding int hours totalling less than -18"() {
        given:
        ZoneOffset orig = ZoneOffset.MIN

        when:
        orig + (-1)

        then:
        thrown DateTimeException
    }

    def "subtracting int hours totalling less than 18"() {
        given:
        ZoneOffset orig = ZoneOffset.UTC

        when:
        ZoneOffset mod = orig - 3

        then:
        mod.totalSeconds == orig.totalSeconds - 3 * 60 * 60
    }

    def "subtracting int hours totalling more than 18"() {
        given:
        ZoneOffset zoneOffset = ZoneOffset.MIN

        when:
        zoneOffset - 1

        then:
        thrown DateTimeException
    }

    def "subtracting int hours totalling less than -18"() {
        given:
        ZoneOffset zoneOffset = ZoneOffset.MAX

        when:
        zoneOffset - (-1)

        then:
        thrown DateTimeException
    }

    def "incrementing a ZoneOffset"() {
        given:
        ZoneOffset zoneOffset = ZoneOffset.UTC

        when:
        zoneOffset++

        then:
        zoneOffset.totalSeconds == 1 * 60 * 60
    }

    def "incrementing a ZoneOffset that is +18 yields an Exception"() {
        given:
        ZoneOffset zoneOffset = ZoneOffset.MAX

        when:
        zoneOffset++

        then:
        thrown DateTimeException
    }

    def "decrementing a ZoneOffset"() {
        given:
        ZoneOffset zoneOffset = ZoneOffset.UTC

        when:
        zoneOffset--

        then:
        zoneOffset.totalSeconds == -1 * 60 * 60
    }

    def "decrementing a ZoneOffset that is -18 yields an Exception"() {
        given:
        ZoneOffset zoneOffset = ZoneOffset.MIN

        when:
        --zoneOffset

        then:
        thrown DateTimeException
    }

    def "adding two ZoneOffsets"() {
        given:
        ZoneOffset one = ZoneOffset.ofHours(1)
        ZoneOffset twoThree = ZoneOffset.ofHoursMinutes(2, 3)
        ZoneOffset fourFiveSix = ZoneOffset.ofHoursMinutesSeconds(4, 5, 6)

        when:
        ZoneOffset sum = one + twoThree + fourFiveSix
        
        then:
        sum == ZoneOffset.ofHoursMinutesSeconds(7, 8, 6)
    }

    def "adding two ZoneOffsets outside of supported range"() {
        given:
        ZoneOffset plusOne = ZoneOffset.ofHours(1)

        when:
        ZoneOffset sum = plusOne + ZoneOffset.MAX
        
        then:
        thrown DateTimeException
    }

    def "subtracting two ZoneOffsets"() {
        given:
        ZoneOffset sixFiveFour = ZoneOffset.ofHoursMinutesSeconds(6, 5, 4)
        ZoneOffset threeTwo = ZoneOffset.ofHoursMinutes(3, 2)
        ZoneOffset one = ZoneOffset.ofHours(1)

        when:
        ZoneOffset difference = sixFiveFour - threeTwo - one
        
        then:
        difference == ZoneOffset.ofHoursMinutesSeconds(2, 3, 4)
    }

    def "subtracting two ZoneOffsets outside of supported range"() {
        given:
        ZoneOffset plusOne = ZoneOffset.ofHours(1)

        when:
        ZoneOffset difference = plusOne - ZoneOffset.MIN
        
        then:
        thrown DateTimeException
    }    

    def "adding and subtracting ZoneOffset ID strings with no sign symbol are considered positive"() {
        given:
        ZoneOffset zero = ZoneOffset.ofHours(0)

        expect:
        ['1', '01', '01:00', '0100', '01:00:00', '010000'].each { id ->
            assert zero + id == ZoneOffset.ofHours(1)
            assert zero - id == ZoneOffset.ofHours(-1)
        }
    }

    def "adding and subtracting ZoneOffset ID strings of positive sign symbol"() {
        given:
        ZoneOffset zero = ZoneOffset.ofHours(0)

        expect:
        ['+1', '+01', '+01:00', '+0100', '+01:00:00', '+010000'].each { id ->
            assert zero + id == ZoneOffset.ofHours(1)
            assert zero - id == ZoneOffset.ofHours(-1)
        }
    }

    def "adding and subtracting ZoneOffset ID strings of positive sign symbol"() {
        given:
        ZoneOffset zero = ZoneOffset.ofHours(0)

        expect:
        ['+1', '+01', '+01:00', '+0100', '+01:00:00', '+010000'].each { id ->
            assert zero + id == ZoneOffset.ofHours(1)
            assert zero - id == ZoneOffset.ofHours(-1)
        }
    }

    def "adding and subtracting ZoneOffset ID strings of negative sign symbol"() {
        given:
        ZoneOffset zero = ZoneOffset.ofHours(0)

        expect:
        ['-1', '-01', '-01:00', '-0100', '-01:00:00', '-010000'].each { id ->
            assert zero + id == ZoneOffset.ofHours(-1)
            assert zero - id == ZoneOffset.ofHours(1)
        }   
    }

    def "adding and subtracting ZoneOffset ID string with minutes and seconds"() {
        given:
        ZoneOffset oneTwoThree = ZoneOffset.ofHoursMinutesSeconds(1, 2, 3)

        when:
        ZoneOffset sum = oneTwoThree + '+01:02:03'
        ZoneOffset difference = oneTwoThree - '-01:02:03'

        then:
        sum == ZoneOffset.ofHoursMinutesSeconds(2, 4, 6)
        difference == sum
    }

    def "adding and subtracting 'Z' has no effect"() {
        given:
        ZoneOffset zo = ZoneOffset.ofHoursMinutes(10, 30)

        when:
        ZoneOffset sum = zo + 'Z'
        ZoneOffset difference = zo - 'Z'

        then:
        sum == zo
        difference == zo
    }

    def "attempting to add invalid ZoneOffset ID string"() {
        when:
        ZoneOffset.MIN + 'ohio'

        then:
        thrown DateTimeException
    }

    def "getAt with supported field"() {
        given:
        ZoneOffset zo = ZoneOffset.ofTotalSeconds(8910)

        expect:
        zo[ChronoField.OFFSET_SECONDS] == 8910
    }

    def "getAt with unsupported field"() {
        given:
        ZoneOffset zo = ZoneOffset.ofTotalSeconds(8910)

        when:
        zo[ChronoField.NANO_OF_SECOND]

        then:
        thrown UnsupportedTemporalTypeException
    }

    def "hours, minutes, seconds ZoneOffset accessor properties"() {
        given:
        ZoneOffset zo = ZoneOffset.ofHoursMinutesSeconds(8, 9, 10)

        expect:
        zo.hours == 8
        zo.minutes == 9
        zo.seconds == 10
    }

    def "left-shifting a LocalDateTime yields an OffsetDateTime"() {
        given:
        ZoneOffset zoneOffset = ZoneOffset.UTC
        LocalDateTime ldt = LocalDateTime.now()

        when:
        OffsetDateTime odt = zoneOffset << ldt

        then:
        odt.year == ldt.year
        odt.month == ldt.month
        odt.dayOfMonth == ldt.dayOfMonth
        odt.hour == ldt.hour
        odt.minute == ldt.minute
        odt.second == ldt.second
        odt.nano == ldt.nano
        odt.offset == zoneOffset
    }

}