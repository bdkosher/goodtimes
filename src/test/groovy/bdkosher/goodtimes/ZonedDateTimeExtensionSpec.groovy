package bdkosher.goodtimes

import java.text.SimpleDateFormat
import java.time.*
import java.time.chrono.*
import java.time.format.*
import java.time.temporal.*
import spock.lang.Specification

class ZonedDateTimeExtensionSpec extends Specification {

    ZoneId gmt = ZoneId.ofOffset('GMT', ZoneOffset.ofHours(0))
    ZoneId gmtMinus5 = ZoneId.ofOffset('GMT', ZoneOffset.ofHours(-5))

    // two time zones without daylight savings to simplify testing
    ZoneId eastAfricanTime = ZoneId.of('Africa/Addis_Ababa')
    ZoneId puertoRicoTime = ZoneId.of('America/Puerto_Rico')

    def "toCalendar preserves time zone"() {
        given:
        ZonedDateTime time = ZonedDateTime.of(2017, Month.MARCH.value, 25, 0, 34, 56, 1e6 as int, gmt)

        when:
        Calendar cal = time.toCalendar()

        then:
        cal.timeZone == TimeZone.getTimeZone('GMT')
    }

    def "toDate preserves time zone"() {
        given:
        ZonedDateTime time = ZonedDateTime.of(2017, Month.MARCH.value, 25, 0, 34, 56, 1e6 as int, eastAfricanTime)
        def fmt = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss.SSS zzzzz')
        fmt.timeZone = eastAfricanTime.timeZone

        when:
        Date date = time.toDate()

        then:
        fmt.format(date) == '2017-03-25 00:34:56.001 Eastern African Time'
    }

    def "toDate preserves time zone - alternate time zone"() {
        given:
        ZonedDateTime time = ZonedDateTime.of(2017, Month.MARCH.value, 25, 0, 34, 56, 1e6 as int, puertoRicoTime)
        def fmt = new SimpleDateFormat('yyyy-MM-dd HH:mm:ss.SSS zzzzz')
        fmt.timeZone = puertoRicoTime.timeZone

        when:
        Date date = time.toDate()

        then:
        fmt.format(date) == '2017-03-25 00:34:56.001 Atlantic Standard Time'
    }    

    def "plus seconds"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig + 2

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second + 2
        mod.nano == 0
        mod.zone == gmt
    }

    def "minus seconds"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig - 2

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second - 2
        mod.nano == 0
        mod.zone == gmt
    }

    def "next second"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime next = orig.next()

        then:
        next.second == orig.second + 1
    }

    def "previous second"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime prev = orig.previous()

        then:
        prev.second == orig.second - 1
    }    

    def "plus Duration of seconds only"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig + Duration.ofSeconds(1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second + 1
        mod.zone == gmt
    }

    def "plus Duration of negative seconds only"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig + Duration.ofSeconds(-1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second - 1
        mod.zone == gmt
    }    

    def "plus Duration of minutes only"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig + Duration.ofMinutes(1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute + 1
        mod.second == orig.second
        mod.zone == gmt
    }

    def "plus Duration of hours only"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig + Duration.ofHours(1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour + 1
        mod.minute == orig.minute
        mod.second == orig.second
        mod.zone == gmt
    }

    def "plus Duration of hours, minutes, and seconds"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig + Duration.ofHours(1).plusMinutes(1).plusSeconds(1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour + 1
        mod.minute == orig.minute + 1
        mod.second == orig.second + 1
        mod.zone == gmt
    }

    def "plus Duration of negative hours, minutes, and seconds"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig + Duration.ofHours(-1).plusMinutes(-1).plusSeconds(-1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour - 1
        mod.minute == orig.minute - 1
        mod.second == orig.second - 1
        mod.zone == gmt
    }    

    def "minus Duration of seconds only"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig - Duration.ofSeconds(1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute
        mod.second == orig.second - 1
        mod.zone == gmt
    }

    def "minus Duration of minutes only"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig - Duration.ofMinutes(1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour
        mod.minute == orig.minute - 1
        mod.second == orig.second
        mod.zone == gmt
    }

    def "minus Duration of hours only"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig - Duration.ofHours(1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour - 1
        mod.minute == orig.minute
        mod.second == orig.second
        mod.zone == gmt
    }

    def "minus Duration of days, months, and hours"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig - Duration.ofHours(1).plusMinutes(1).plusSeconds(1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour - 1
        mod.minute == orig.minute - 1
        mod.second == orig.second - 1
        mod.zone == gmt
    }

    def "minus Duration of negative days, months, and hours"() {
        given:
        ZonedDateTime orig = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ZonedDateTime mod = orig -Duration.ofHours(-1).plusMinutes(-1).plusSeconds(-1)

        then:
        mod.year == orig.year
        mod.month == orig.month
        mod.dayOfMonth == orig.dayOfMonth
        mod.hour == orig.hour + 1
        mod.minute == orig.minute + 1
        mod.second == orig.second + 1
        mod.zone == gmt
    }

    def "getAt Calendar field"() {
        given:
        ZonedDateTime ldt = ZonedDateTime.of(LocalDateTime.of(2017, Month.JANUARY, 10, 12, 34, 56), gmt)

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
        ldt[Calendar.ZONE_OFFSET] == 0
    }

    def "getAt Calendar.ZONE_OFFSET is in milliseconds"() {
        given:
        ZonedDateTime ldt = ZonedDateTime.of(LocalDateTime.of(2017, Month.JANUARY, 10, 12, 34, 56), gmtMinus5)

        expect:
        ldt[Calendar.ZONE_OFFSET] == -5 * 60 * 60 * 1000
    }

    def "getAt unsupported Calendar field"() {
        given:
        ZonedDateTime ldt = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ldt[Calendar.SHORT_STANDALONE]

        then:
        thrown IllegalArgumentException
    }

    def "getAt invalid Calendar field"() {
        given:
        ZonedDateTime ldt = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        when:
        ldt[Integer.MAX_VALUE]

        then:
        thrown IllegalArgumentException
    }

    def "getAt Temporal Field"() {
        given:
        ZonedDateTime ldt = ZonedDateTime.of(LocalDateTime.of(2017, Month.JANUARY, 10, 12, 34, 56), gmtMinus5)
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
        ldt[ChronoField.OFFSET_SECONDS] == -5 * 60 * 60
    }

    def "getDay method works"() {
        given:
        ZonedDateTime dt = ZonedDateTime.now()

        expect:
        dt.day == dt.dayOfMonth
    }

    def "duration between earlier and later ZonedDateTime is positive"() {
        given:
        ZonedDateTime earlier = ZonedDateTime.now()
        ZonedDateTime later = earlier.plusSeconds(1)

        when:
        Duration duration = earlier - later

        then:
        duration.seconds == 1
    }

    def "duration between later and earlier ZonedDateTime is negative"() {
        given:
        ZonedDateTime earlier = ZonedDateTime.now()
        ZonedDateTime later = earlier.plusSeconds(1)

        when:
        Duration duration = later - earlier

        then:
        duration.negative
    }    

    def "format by pattern with US Locale"() {
        given:
        ZonedDateTime dt = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)
        String pattern = 'yyyyMMddhhmmssSSS'

        expect:
        dt.format(pattern, Locale.US) == '20170325123456000'
    }

    def "format by FormatStyle"() {
        given:
        ZonedDateTime dt = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        expect:
        dt.format(FormatStyle.SHORT) == dt.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
    }

    def "getDateString is just an alias for LocalDate format FormatStyle.SHORT"() {
        given:
        ZonedDateTime dt = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        expect:
        dt.dateString == dt.toLocalDate().format(FormatStyle.SHORT)
    }

    def "getTimeString is just an alias for LocalTime format FormatStyle.SHORT"() {
        given:
        ZonedDateTime dt = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        expect:
        dt.timeString == dt.toLocalTime().format(FormatStyle.SHORT)
    }

    def "getDateTimeString is just an alias for LocalTime format FormatStyle.SHORT"() {
        given:
        ZonedDateTime dt = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 12, 34, 56), gmt)

        expect:
        dt.dateTimeString == dt.format(FormatStyle.SHORT)
    }    

    def "downto() cannot be called with date after this date"() {
        given:
        ZonedDateTime now = ZonedDateTime.now()
        ZonedDateTime later = now + 1

        when:
        now.downto(later) { t -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "downto() is called once when the two dates are the same"() {
        given:
        ZonedDateTime now = ZonedDateTime.now()
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
        ZonedDateTime now = ZonedDateTime.now()
        ZonedDateTime oneMinuteAgo = now - 60
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
        ZonedDateTime higher = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 0, 0, 3), gmt)
        ZonedDateTime lower = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 0, 0, 1), gmt)
        int iter = higher.second

        expect:
        higher.downto(lower) { t ->
            assert iter-- == t.second
        }
    }

    def "upto() cannot be called with date before this date"() {
        given:
        ZonedDateTime now = ZonedDateTime.now()
        ZonedDateTime oneSecondAgo = now - 1

        when:
        now.upto(oneSecondAgo) { t -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "upto() is called once when the two dates are the same"() {
        given:
        ZonedDateTime now = ZonedDateTime.now()
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
        ZonedDateTime now = ZonedDateTime.now()
        ZonedDateTime oneMinuteFromNow = now + 60
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
        ZonedDateTime lower = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 0, 0, 0), gmt)
        ZonedDateTime higher = ZonedDateTime.of(LocalDateTime.of(2017, Month.MARCH, 25, 0, 0, 2), gmt)
        int iter = lower.second

        expect:
        lower.upto(higher) { t ->
            assert iter++ == t.second
        }        
    }

    def "clearTime() sets the time fields to zero and preserves the TimeZone"() {
        given:
        ZonedDateTime ldt = ZonedDateTime.of(LocalDateTime.of(2017, Month.MAY, 5, 8, 9, 10, 11), gmtMinus5)
        
        when:
        ZonedDateTime justTheDate = ldt.clearTime()

        then:
        justTheDate.year == 2017
        justTheDate.month == Month.MAY
        justTheDate.dayOfMonth == 5
        justTheDate.hour == 0
        justTheDate.minute == 0
        justTheDate.second == 0
        justTheDate.nano == 0
        justTheDate.zone == gmtMinus5
    }
}