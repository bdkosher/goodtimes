package bdkosher.goodtimes

import java.time.*
import java.time.chrono.*
import java.time.format.*
import java.time.temporal.*
import spock.lang.Specification

class LocalDateTimeExtensionSpec extends Specification {

    def "toDate works decently enough as you could expect"() {
        given:
        LocalDateTime time = LocalDateTime.of(2017, Month.MARCH, 25, 0, 34, 56, 1e6 as int)

        when:
        Date date = time.toDate()

        then:
        date.format('yyyy-MM-dd HH:mm:ss.SSS') == '2017-03-25 00:34:56.001'
    }

    def "toCalendar works decently enough as you could expect"() {
        given:
        LocalDateTime time = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56, 78e6 as int)

        when:
        Calendar cal = time.toCalendar()

        then:
        cal.get(Calendar.YEAR) == 2017
        cal.get(Calendar.MONTH) == Calendar.MARCH
        cal.get(Calendar.DATE) == 25
        cal.get(Calendar.HOUR_OF_DAY) == 12
        cal.get(Calendar.MINUTE) == 34
        cal.get(Calendar.SECOND) == 56
        cal.get(Calendar.MILLISECOND) == 78
    }

    def "toCalendar works decently enough as you could expect, explicit Locale"() {
        given:
        LocalDateTime time = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56, 78e6 as int)

        when:
        Calendar cal = time.toCalendar(Locale.US)

        then:
        cal.get(Calendar.YEAR) == 2017
        cal.get(Calendar.MONTH) == Calendar.MARCH
        cal.get(Calendar.DATE) == 25
        cal.get(Calendar.HOUR_OF_DAY) == 12
        cal.get(Calendar.MINUTE) == 34
        cal.get(Calendar.SECOND) == 56
        cal.get(Calendar.MILLISECOND) == 78
    }        

    def "plus seconds"() {
        given:
        LocalDateTime orig = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        when:
        LocalDateTime mod = orig + 2

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second + 2
        mod.nano == 0
    }

    def "minus seconds"() {
        given:
        LocalDateTime orig = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        when:
        LocalDateTime mod = orig - 2

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second - 2
        mod.nano == 0
    }

    def "next second"() {
        given:
        LocalDateTime orig = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        when:
        LocalDateTime next = orig.next()

        then:
        next.second == orig.second + 1
    }

    def "previous second"() {
        given:
        LocalDateTime orig = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        when:
        LocalDateTime prev = orig.previous()

        then:
        prev.second == orig.second - 1
    }    

    def "getAt Calendar field"() {
        given:
        LocalDateTime ldt = LocalDateTime.of(2017, Month.JANUARY, 10, 12, 34, 56)

        expect:
        ldt[Calendar.YEAR] == 2017
        ldt[Calendar.MONTH] == Calendar.JANUARY
        ldt[Calendar.DAY_OF_MONTH] == 10
        ldt[Calendar.DATE] == 10
        ldt[Calendar.DAY_OF_YEAR] == 10
        ldt[Calendar.WEEK_OF_MONTH] == 2
        ldt[Calendar.WEEK_OF_YEAR] == 2
        ldt[Calendar.DAY_OF_WEEK] == Calendar.TUESDAY
        ldt[Calendar.DAY_OF_WEEK_IN_MONTH] == 3
        ldt[Calendar.ERA] == GregorianCalendar.AD
        ldt[Calendar.HOUR] == 12
        ldt[Calendar.MINUTE] == 34
        ldt[Calendar.SECOND] == 56
        ldt[Calendar.MILLISECOND] == 0
    }

    def "getAt unsupported Calendar field"() {
        given:
        LocalDateTime ldt = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        when:
        ldt[Calendar.SHORT_STANDALONE]

        then:
            thrown IllegalArgumentException
    }

    def "getAt invalid Calendar field"() {
        given:
        LocalDateTime ldt = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        when:
        ldt[Integer.MAX_VALUE]

        then:
        thrown IllegalArgumentException
    }

    def "getAt Temporal Field"() {
        given:
        LocalDateTime ldt = LocalDateTime.of(2017, Month.JANUARY, 10, 12, 34, 56)
        int minuteOfDay = (12 * 60) + 34
        int secondOfDay = (12 * 60 * 60) + (34 * 60) + 56

        expect:
        ldt[ChronoField.YEAR] == 2017
        ldt[ChronoField.YEAR_OF_ERA] == 2017
        ldt[ChronoField.MONTH_OF_YEAR] == Month.JANUARY.value
        ldt[ChronoField.PROLEPTIC_MONTH] == 24204
        ldt[ChronoField.DAY_OF_MONTH] == 10
        ldt[ChronoField.DAY_OF_YEAR] == 10
        ldt[ChronoField.EPOCH_DAY] == 17176
        ldt[ChronoField.ALIGNED_WEEK_OF_MONTH] == 2
        ldt[ChronoField.ALIGNED_WEEK_OF_YEAR] == 2
        ldt[ChronoField.DAY_OF_WEEK] == DayOfWeek.TUESDAY.value
        ldt[ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH] == 3
        ldt[ChronoField.ERA] == IsoEra.CE.value        
        ldt[ChronoField.HOUR_OF_AMPM] == 0
        ldt[ChronoField.HOUR_OF_DAY] == 12
        ldt[ChronoField.MINUTE_OF_DAY] == minuteOfDay
        ldt[ChronoField.MINUTE_OF_HOUR] == 34
        ldt[ChronoField.SECOND_OF_DAY] == secondOfDay
        ldt[ChronoField.SECOND_OF_MINUTE] == 56
        ldt[ChronoField.MILLI_OF_DAY] == secondOfDay * 1e3
        ldt[ChronoField.MILLI_OF_SECOND] == 0
        ldt[ChronoField.MICRO_OF_DAY] == secondOfDay * 1e6
        ldt[ChronoField.MICRO_OF_SECOND] == 0
        ldt[ChronoField.NANO_OF_DAY] == secondOfDay * 1e9
        ldt[ChronoField.NANO_OF_SECOND] == 0
    }

    def "getDay method works"() {
        given:
        LocalDateTime dt = LocalDateTime.now()

        expect:
        dt.day == dt.dayOfMonth
    }

    def "duration between earlier and later LocalDateTime is positive"() {
        given:
        LocalDateTime earlier = LocalDateTime.now()
        LocalDateTime later = earlier.plusSeconds(1)

        when:
        Duration duration = earlier - later

        then:
        duration.seconds == 1
    }

    def "duration between later and earlier LocalDateTime is negative"() {
        given:
        LocalDateTime earlier = LocalDateTime.now()
        LocalDateTime later = earlier.plusSeconds(1)

        when:
        Duration duration = later - earlier

        then:
        duration.negative
    }    

    def "format by pattern with US Locale"() {
        given:
        LocalDateTime dt = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)
        String pattern = 'yyyyMMddhhmmssSSS'

        expect:
        dt.format(pattern, Locale.US) == '20170325123456000'
    }

    def "format by FormatStyle"() {
        given:
        LocalDateTime dt = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        expect:
        dt.format(FormatStyle.SHORT) == dt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
    }

    def "getDateString is just an alias for LocalDate format FormatStyle.SHORT"() {
        given:
        LocalDateTime dt = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        expect:
        dt.dateString == dt.toLocalDate().format(FormatStyle.SHORT)
    }

    def "getTimeString is just an alias for LocalTime format FormatStyle.SHORT"() {
        given:
        LocalDateTime dt = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        expect:
        dt.timeString == dt.toLocalTime().format(FormatStyle.SHORT)
    }

    def "getDateTimeString is just an alias for LocalTime format FormatStyle.SHORT"() {
        given:
        LocalDateTime dt = LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56)

        expect:
        dt.dateTimeString == dt.format(FormatStyle.SHORT)
    }    

    def "downto() cannot be called with date after this date"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        LocalDateTime later = now + 1

        when:
        now.downto(later) { t -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "downto() is called once when the two dates are the same"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        boolean closureCalledOnce = false

        when:
        now.downto(now) { t -> 
            closureCalledOnce = true
        }

        then:
        closureCalledOnce
    }

    def "downto() can be passed no-arg closure"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        LocalDateTime oneMinuteAgo = now - 60
        int count = 0

        when:
        now.downto(oneMinuteAgo) {
            count++
        }

        then:
        count == 61
    }

    def "downto() closure passed decreasing arg"() {
        given:
        LocalDateTime higher = LocalDateTime.of(2017, Month.MARCH, 25, 0, 0, 3)
        LocalDateTime lower = LocalDateTime.of(2017, Month.MARCH, 25, 0, 0, 1)
        int iter = higher.second

        expect:
        higher.downto(lower) { t ->
            assert iter-- == t.second
        }
    }

    def "upto() cannot be called with date before this date"() {
        given:
        LocalDateTime now = LocalDateTime.now()
        LocalDateTime oneSecondAgo = now - 1

        when:
        now.upto(oneSecondAgo) { t -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "upto() is called once when the two dates are the same"() {
        given:
        LocalDateTime now = LocalDateTime.now()
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
        LocalDateTime now = LocalDateTime.now()
        LocalDateTime oneMinuteFromNow = now + 60
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
        LocalDateTime lower = LocalDateTime.of(2017, Month.MARCH, 25, 0, 0, 0)
        LocalDateTime higher = LocalDateTime.of(2017, Month.MARCH, 25, 0, 0, 2)
        int iter = lower.second

        expect:
        lower.upto(higher) { t ->
            assert iter++ == t.second
        }        
    }

    def "clearTime() sets the time fields to zero"() {
        given:
        LocalDateTime ldt = LocalDateTime.of(2017, Month.MAY, 5, 8, 9, 10, 11)
        
        when:
        LocalDateTime justTheDate = ldt.clearTime()

        then:
        justTheDate.year == 2017
        justTheDate.month == Month.MAY
        justTheDate.dayOfMonth == 5
        justTheDate.hour == 0
        justTheDate.minute == 0
        justTheDate.second == 0
        justTheDate.nano == 0
    }

    def "left shifting a ZoneOffset produces an OffsetDateTime"() {
        given:
        LocalDateTime datetime = LocalDateTime.of(2017, Month.JULY, 4, 12, 34, 56)

        when:
        OffsetDateTime odt = datetime << ZoneOffset.UTC

        then:
        odt.year == datetime.year
        odt.month == datetime.month
        odt.dayOfMonth == datetime.day
        odt.hour == datetime.hour
        odt.minute == datetime.minute
        odt.second == datetime.second
        odt.offset == ZoneOffset.UTC
    }

    def "left shifting a ZoneId produces an ZonedDateTime"() {
        given:
        LocalDateTime datetime = LocalDateTime.of(2017, Month.JULY, 4, 12, 34, 56)
        ZoneId zoneId = ZoneId.of(ZoneId.availableZoneIds[0])

        when:
        ZonedDateTime odt = datetime << zoneId

        then:
        odt.year == datetime.year
        odt.month == datetime.month
        odt.dayOfMonth == datetime.day
        odt.hour == datetime.hour
        odt.minute == datetime.minute
        odt.second == datetime.second
        odt.zone == zoneId
    }

    def "left shifting a TimeZone produces an ZonedDateTime"() {
        given:
        LocalDateTime datetime = LocalDateTime.of(2017, Month.JULY, 4, 12, 34, 56)
        TimeZone timeZone = TimeZone.default

        when:
        ZonedDateTime odt = datetime << timeZone

        then:
        odt.year == datetime.year
        odt.month == datetime.month
        odt.dayOfMonth == datetime.day
        odt.hour == datetime.hour
        odt.minute == datetime.minute
        odt.second == datetime.second
        odt.zone == timeZone.toZoneId()
    }     
}