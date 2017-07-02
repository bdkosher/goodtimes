package bdkosher.goodtimes

import java.time.*
import java.time.chrono.*
import java.time.format.*
import java.time.temporal.*
import spock.lang.Specification

class LocalDateExtensionSpec extends Specification {

    def "toDate works decently enough as you could expect"() {
        given:
        LocalDate ld = LocalDate.of(2017, 2, 21)

        when:
        Date d = ld.toDate()

        then:
        d.format('yyyy-MM-dd HH:mm:ss.SSS') == '2017-02-21 00:00:00.000'
    }

    def "toCalendar works decently enough as you could expect with specified Locale"() {
        given:
        LocalDate ld = LocalDate.of(2017, 2, 21)

        when:
        Calendar cal = ld.toCalendar(Locale.US)

        then:
        cal.get(Calendar.YEAR) == 2017
        cal.get(Calendar.MONTH) == Calendar.FEBRUARY
        cal.get(Calendar.DATE) == 21
        cal.get(Calendar.HOUR_OF_DAY) == 0
        cal.get(Calendar.MINUTE) == 0
        cal.get(Calendar.SECOND) == 0
        cal.get(Calendar.MILLISECOND) == 0
        cal.get(Calendar.ZONE_OFFSET) == Calendar.instance.get(Calendar.ZONE_OFFSET)
    }

    def "toCalendar works decently enough as you could expect with specified TimeZone"() {
        given:
        LocalDate ld = LocalDate.of(2017, 2, 21)

        when:
        Calendar cal = ld.toCalendar(TimeZone.getTimeZone('GMT+07:00'))

        then:
        cal.get(Calendar.YEAR) == 2017
        cal.get(Calendar.MONTH) == Calendar.FEBRUARY
        cal.get(Calendar.DATE) == 21
        cal.get(Calendar.HOUR_OF_DAY) == 0
        cal.get(Calendar.MINUTE) == 0
        cal.get(Calendar.SECOND) == 0
        cal.get(Calendar.MILLISECOND) == 0
        cal.get(Calendar.ZONE_OFFSET) == 7 * 60 * 60 * 1000
    }    

    def "plus days"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)
        LocalDate mod = orig + 2

        expect:
        mod.year == orig.year
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth + 2
    }

    def "minus days"() {
        given:
        LocalDate orig = LocalDate.of(2017, 1, 8)

        when:
        LocalDate mod = orig - 2

        then:
        mod.year == orig.year
        mod.monthValue == orig.monthValue
        mod.dayOfMonth == orig.dayOfMonth - 2
    }

    def "next day"() {
        given:
        LocalDate date = LocalDate.of(2017, 1, 8)

        when:
        LocalDate tomorrow = date.next()

        then:
        tomorrow.dayOfMonth == date.dayOfMonth + 1
    }

    def "previous day"() {
        given:
        LocalDate date = LocalDate.of(2017, 1, 8)

        when:
        LocalDate yesterday = date.previous()

        then:
        yesterday.dayOfMonth == date.dayOfMonth - 1
    }    

    def "getAt Calendar field"() {
        given:
        LocalDate ld = LocalDate.of(2017, 1, 10)

        expect:
        ld[Calendar.YEAR] == 2017
        ld[Calendar.MONTH] == Calendar.JANUARY
        ld[Calendar.DAY_OF_MONTH] == 10
        ld[Calendar.DATE] == 10
        ld[Calendar.DAY_OF_YEAR] == 10
        ld[Calendar.WEEK_OF_MONTH] == 2
        ld[Calendar.WEEK_OF_YEAR] == 2
        ld[Calendar.DAY_OF_WEEK] == Calendar.TUESDAY
        ld[Calendar.DAY_OF_WEEK_IN_MONTH] == 3
        ld[Calendar.ERA] == GregorianCalendar.AD
    }

    def "getAt unsupported Calendar field"() {
        given:
        LocalDate ld = LocalDate.of(2017, 1, 10)

        when:
        ld[Calendar.MILLISECOND]

        then:
        thrown IllegalArgumentException
    }

    def "getAt invalid Calendar field"() {
        given:
        LocalDate ld = LocalDate.of(2017, 1, 10)

        when:
        ld[Integer.MAX_VALUE]

        then:
        thrown IllegalArgumentException
    }

    def "getAt Temporal Field"() {
        given:
        LocalDate ld = LocalDate.of(2017, 1, 10)

        expect:
        ld[ChronoField.YEAR] == 2017
        ld[ChronoField.YEAR_OF_ERA] == 2017
        ld[ChronoField.MONTH_OF_YEAR] == Month.JANUARY.value
        ld[ChronoField.PROLEPTIC_MONTH] == 24204
        ld[ChronoField.DAY_OF_MONTH] == 10
        ld[ChronoField.DAY_OF_YEAR] == 10
        ld[ChronoField.EPOCH_DAY] == 17176
        ld[ChronoField.ALIGNED_WEEK_OF_MONTH] == 2
        ld[ChronoField.ALIGNED_WEEK_OF_YEAR] == 2
        ld[ChronoField.DAY_OF_WEEK] == DayOfWeek.TUESDAY.value
        ld[ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH] == 3
        ld[ChronoField.ERA] == IsoEra.CE.value        
    }

    def "period between today and tomorrow is one day"() {
        given:
        LocalDate today = LocalDate.now()
        LocalDate tomorrow = today.plusDays(1)

        when:
        Period period = today >> tomorrow

        then:
        period.days == 1
        period.months == 0
        period.years == 0
    }

    def "format by pattern with US Locale"() {
        given:
        LocalDate d = LocalDate.of(2017, Month.FEBRUARY, 20)
        String pattern = 'EEEE, MMMM d'

        expect:
        d.format(pattern, Locale.US) == 'Monday, February 20'
    }

    def "format by FormatStyle"() {
        given:
        LocalDate d = LocalDate.of(2017, Month.FEBRUARY, 20)

        expect:
        d.format(FormatStyle.FULL) == d.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
    }

    def "getDateString is just an alias for format FormatStyle.SHORT "() {
        given:
        LocalDate d = LocalDate.of(2017, Month.FEBRUARY, 21)

        expect:
        d.dateString == d.format(FormatStyle.SHORT)
    }

    def "period between tomorrow and today is negative one day"() {
        given:
        LocalDate today = LocalDate.now()
        LocalDate tomorrow = today.plusDays(1)

        when:
        Period period = tomorrow >> today

        then:
        period.days == -1
        period.months == 0
        period.years == 0           
    }

    def "downto() cannot be called with date after this date"() {
        given:
        LocalDate today = LocalDate.now()
        LocalDate tomorrow = today + 1

        when:
        today.downto(tomorrow) { d -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "downto() is called once when the two dates are the same"() {
        given:
        LocalDate today = LocalDate.now()
        boolean closureCalledOnce = false

        when:
        today.downto(today) { d -> 
            closureCalledOnce = true
        }

        then:
        closureCalledOnce
    }

    def "downto() can be passed no-arg closure"() {
        given:
        LocalDate today = LocalDate.now()
        LocalDate dayBeforeYesterday = today - 2
        int count = 0

        when:
        today.downto(dayBeforeYesterday) {
            count++
        }

        then:
        count == 3
    }

    def "downto() closure passed decreasing arg"() {
        given:
        LocalDate higher = LocalDate.of(2017, Month.JULY, 4)
        LocalDate lower = LocalDate.of(2017, Month.JULY, 1)
        int iter = higher.day

        expect:
        higher.downto(lower) { d ->
            assert iter-- == d.day
        }
    }    

    def "upto() cannot be called with date before this date"() {
        given:
        LocalDate today = LocalDate.now()
        LocalDate yesterday = today - 1

        when:
        today.upto(yesterday) { d -> 
            throw new Exception('This closure body should never get executed.') 
        }

        then:
        thrown GroovyRuntimeException
    }

    def "upto() is called once when the two dates are the same"() {
        given:
        LocalDate today = LocalDate.now()
        boolean closureCalledOnce = false

        when:
        today.upto(today) { d -> 
            closureCalledOnce = true
        }

        then:
        closureCalledOnce
    }

    def "upto() can be passed no-arg closure"() {
        given:
        LocalDate today = LocalDate.now()
        LocalDate dayAfterTomorrow = today + 2
        int count = 0

        when:
        today.upto(dayAfterTomorrow) {
            count++
        }

        then:
        count == 3
    }

    def "upto() closure passed increasing arg"() {
        given:
        LocalDate lower = LocalDate.of(2017, Month.JULY, 1)
        LocalDate higher = LocalDate.of(2017, Month.JULY, 4)
        int iter = lower.day

        expect:
        lower.upto(higher) { t ->
            assert iter++ == t.day
        }        
    }

    def "leftShifting a LocalTime produces a LocalDateTime"() {
        given:
        LocalDate date = LocalDate.of(2017, Month.JULY, 4)
        LocalTime time = LocalTime.of(12, 34, 56)

        when:
        LocalDateTime datetime = date << time

        then:
        datetime.year == date.year
        datetime.month == date.month
        datetime.dayOfMonth == date.day
        datetime.hour == time.hour
        datetime.minute == time.minute
        datetime.second == time.second
    }

    def "leftShifting an OffsetTime produces a OffsetDateTime"() {
        given:
        LocalDate date = LocalDate.of(2017, Month.JULY, 4)
        OffsetTime offsetTime = OffsetTime.of(LocalTime.of(12, 34, 56), ZoneOffset.UTC)

        when:
        OffsetDateTime datetime = date << offsetTime

        then:
        datetime.year == date.year
        datetime.month == date.month
        datetime.dayOfMonth == date.day
        datetime.hour == offsetTime.hour
        datetime.minute == offsetTime.minute
        datetime.second == offsetTime.second
        datetime.nano == offsetTime.nano
        datetime.offset == ZoneOffset.UTC
    }    
}