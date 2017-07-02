package bdkosher.goodtimes

import java.time.*
import java.time.chrono.*
import java.time.format.*
import java.time.temporal.*
import spock.lang.Specification

class OffsetTimeExtensionSpec extends Specification {

    ZoneOffset minusFive = ZoneOffset.ofHours(-5)
    ZoneOffset hmsOffset = ZoneOffset.ofHoursMinutesSeconds(1, 1, 1)

    def "toCalendar preserves offset"() {
        given:
        OffsetTime time = OffsetTime.of(LocalTime.of(12, 34, 56), minusFive)

        when:
        Calendar cal = time.toCalendar()

        then:
        cal.get(Calendar.HOUR_OF_DAY) == 12
        cal.get(Calendar.MINUTE) == 34
        cal.get(Calendar.SECOND) == 56
        cal.get(Calendar.MILLISECOND) == 0
        cal.get(Calendar.ZONE_OFFSET) == -5 * 60 * 60 * 1000
    }

    def "toCalendar preserves offset - alternate offset"() {
        given:
        OffsetTime time = OffsetTime.of(LocalTime.of(12, 34, 56, 78e6 as int), hmsOffset)

        when:
        Calendar cal = time.toCalendar()

        then:
        cal.get(Calendar.HOUR_OF_DAY) == 12
        cal.get(Calendar.MINUTE) == 34
        cal.get(Calendar.SECOND) == 56
        cal.get(Calendar.MILLISECOND) == 78
        cal.get(Calendar.ZONE_OFFSET) == (1 * 60 * 60 * 1000) + (1 * 60 * 1000) + (1 * 1000)
    }

    def "toDate works decently enough as you could expect"() {
        given:
        OffsetTime time = OffsetTime.of(LocalTime.of(12, 34, 56), minusFive)

        when:
        Date date = time.toDate()

        then:
        date.format('HH:mm:ss.SSS') == '12:34:56.000'
    }

    def "plus seconds"() {
        given:
        OffsetTime orig = OffsetTime.of(12, 34, 56, 78, minusFive)

        when:
        OffsetTime mod = orig + 2

        then:
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second + 2
        mod.nano == orig.nano
        mod.offset == orig.offset
    }

    def "minus seconds"() {
        given:
        OffsetTime orig = OffsetTime.of(12, 34, 56, 78, minusFive)

        when:
        OffsetTime mod = orig - 2

        then:
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second - 2
        mod.nano == orig.nano
        mod.offset == orig.offset
    }

    def "next second"() {
        given:
        OffsetTime orig = OffsetTime.of(12, 34, 56, 78, minusFive)

        when:
        OffsetTime next = orig.next()

        then:
        next.second == orig.second + 1
    }

    def "previous second"() {
        given:
        OffsetTime orig = OffsetTime.of(12, 34, 56, 78, minusFive)

        when:
        OffsetTime prev = orig.previous()

        then:
        prev.second == orig.second - 1
    }    

    def "getAt Calendar field"() {
        given:
        OffsetTime lt = OffsetTime.of(12, 34, 56, 78, minusFive)

        expect:
        lt[Calendar.HOUR] == 12
        lt[Calendar.MINUTE] == 34
        lt[Calendar.SECOND] == 56
        lt[Calendar.MILLISECOND] == 0
        lt[Calendar.ZONE_OFFSET] == -5 * 60 * 60 * 1000
    }

    def "getAt unsupported Calendar field"() {
        given:
        OffsetTime lt = OffsetTime.of(12, 34, 56, 78, minusFive)

        when:
        lt[Calendar.ERA]

        then:
        thrown IllegalArgumentException
    }

    def "getAt invalid Calendar field"() {
        given:
        OffsetTime lt = OffsetTime.of(12, 34, 56, 78, minusFive)

        when:
        lt[Integer.MAX_VALUE]

        then:
        thrown IllegalArgumentException
    }

    def "getAt Temporal Field"() {
        given:
        OffsetTime lt = OffsetTime.of(12, 34, 56, 0, minusFive)
        int minuteOfDay = (12 * 60) + 34
        int secondOfDay = (12 * 60 * 60) + (34 * 60) + 56
        int offsetSeconds = -5 * 60 * 60

        expect:
        lt[ChronoField.HOUR_OF_AMPM] == 0
        lt[ChronoField.HOUR_OF_DAY] == 12
        lt[ChronoField.MINUTE_OF_DAY] == minuteOfDay
        lt[ChronoField.MINUTE_OF_HOUR] == 34
        lt[ChronoField.SECOND_OF_DAY] == secondOfDay
        lt[ChronoField.SECOND_OF_MINUTE] == 56
        lt[ChronoField.MILLI_OF_DAY] == secondOfDay * 1e3
        lt[ChronoField.MILLI_OF_SECOND] == 0
        lt[ChronoField.MICRO_OF_DAY] == secondOfDay * 1e6
        lt[ChronoField.MICRO_OF_SECOND] == 0
        lt[ChronoField.NANO_OF_DAY] == secondOfDay * 1e9
        lt[ChronoField.NANO_OF_SECOND] == 0
        lt[ChronoField.OFFSET_SECONDS] == offsetSeconds
    }

    def "duration between T and an-hour-from-T is 1 hour when T is more than an hour from midnight"() {
        given:
        OffsetTime t = OffsetTime.of(0, 0, 0, 0, minusFive)
        OffsetTime hourFromT = t.plusHours(1)

        when:
        Duration duration = t >> hourFromT

        then:
        duration.seconds == 60 * 60
    }

    def "duration between an-hour-from-T and T is -1 hour when T is more than an hour from midnight"() {
        given:
        OffsetTime t = OffsetTime.of(0, 0, 0, 0, minusFive)
        OffsetTime hourFromT = t.plusHours(1)

        when:
        Duration duration = hourFromT >> t

        then:
        duration.seconds == -1 * 60 * 60
    }

    def "format by pattern with US Locale"() {
        given:
        OffsetTime t = OffsetTime.of(12, 34, 56, 0, minusFive)
        String pattern = 'hhmmssSSS'

        expect:
        t.format(pattern, Locale.US) == '123456000'
    }

    def "format by FormatStyle"() {
        given:
        OffsetTime t = OffsetTime.of(12, 34, 56, 78, minusFive)

        expect:
        t.format(FormatStyle.SHORT) == t.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
    }

    def "getTimeString is just an alias for format FormatStyle.SHORT"() {
        given:
        OffsetTime t = OffsetTime.of(12, 34, 56, 78, minusFive)

        expect:
        t.timeString == t.format(FormatStyle.SHORT)
    }

    def "downto() cannot be called with date after this date"() {
        setup:
        OffsetTime now = OffsetTime.now()
        OffsetTime later = now + 1

        when:
        now.downto(later) { t -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "downto() is called once when the two dates are the same"() {
        setup:
        OffsetTime now = OffsetTime.now()
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
        OffsetTime now = OffsetTime.now()
        OffsetTime oneMinuteAgo = now - 60
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
        OffsetTime higher = OffsetTime.of(0, 0, 3, 0, minusFive)
        OffsetTime lower = OffsetTime.of(0, 0, 1, 0, minusFive)
        int iter = higher.second

        expect:
        higher.downto(lower) { t ->
            assert iter-- == t.second
        }
    }

    def "upto() cannot be called with date before this date"() {
        setup:
        OffsetTime now = OffsetTime.now()
        OffsetTime oneSecondAgo = now - 1

        when:
        now.upto(oneSecondAgo) { t -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "upto() is called once when the two dates are the same"() {
        setup:
        OffsetTime now = OffsetTime.now()
        boolean closureCalledOnce = false

        when:
        now.upto(now) { t -> 
            closureCalledOnce = true
        }

        then:
        closureCalledOnce
    }

    def "upto() can be passed no-arg closure"() {
        given:
        OffsetTime now = OffsetTime.now()
        OffsetTime oneMinuteFromNow = now + 60
        int count = 0

        when:
        now.upto(oneMinuteFromNow) {
            count++
        }

        then:
        count == 61
    }

    def "upto() closure passed increasing arg"() {
        given:
        OffsetTime lower = OffsetTime.of(0, 0, 0, 0, minusFive)
        OffsetTime higher = OffsetTime.of(0, 0, 2, 0, minusFive)
        int iter = lower.second

        expect:
        lower.upto(higher) { t ->
            assert iter++ == t.second
        }        
    }

    def "leftShifting a LocalDate produces a OffsetDateTime"() {
        given:
        OffsetTime time = OffsetTime.of(12, 34, 56, 78, minusFive)
        LocalDate date = LocalDate.of(2017, Month.JULY, 4)
        
        when:
        OffsetDateTime datetime = time << date

        then:
        datetime.year == date.year
        datetime.month == date.month
        datetime.dayOfMonth == date.day
        datetime.hour == time.hour
        datetime.minute == time.minute
        datetime.second == time.second
        datetime.nano == time.nano
        datetime.offset == time.offset
    }
}