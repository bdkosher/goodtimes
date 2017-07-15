package bdkosher.goodtimes

import java.time.*
import spock.lang.Specification
import spock.lang.Unroll

class ParseMethodsStaticExtensionSpec extends Specification {

    def "parse valid LocalDate"() {
        given:
        String input = '2017/07/11'
        String format = 'yyyy/MM/dd'

        when:
        LocalDate parsed = LocalDate.parse(input, format)
        LocalDate zoneParsed = LocalDate.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.year == 2017
        parsed.month == Month.JULY
        parsed.dayOfMonth == 11
        parsed == zoneParsed
    }

    def "parse valid LocalDateTime"() {
        given:
        String input = '2017/07/11 9:47PM'
        String format = 'yyyy/MM/dd h:mma'

        when:
        LocalDateTime parsed = LocalDateTime.parse(input, format)
        LocalDateTime zoneParsed = LocalDateTime.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.year == 2017
        parsed.month == Month.JULY
        parsed.dayOfMonth == 11
        parsed.hour == 21
        parsed.minute == 47
        parsed == zoneParsed
    }

    def "parse valid LocalTime"() {
        given:
        String input = '9:47 PM'
        String format = 'h:mm a'

        when:
        LocalTime parsed = LocalTime.parse(input, format)
        LocalTime zoneParsed = LocalTime.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.hour == 21
        parsed.minute == 47
        parsed == zoneParsed
    }

    def "parse valid MonthDay"() {
        given:
        String input = 'July 4'
        String format = 'MMMM d'

        when:
        MonthDay parsed = MonthDay.parse(input, format)
        MonthDay zoneParsed = MonthDay.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.month == Month.JULY
        parsed.dayOfMonth == 4
        parsed == zoneParsed
    }

    def "parse valid OffsetDateTime"() {
        given:
        String input = '111213 141516 +171819'
        String format = 'MMddyy HHmmss XX'

        when:
        OffsetDateTime parsed = OffsetDateTime.parse(input, format)
        OffsetDateTime zoneParsed = OffsetDateTime.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.year == 2013
        parsed.month == Month.NOVEMBER
        parsed.dayOfMonth == 12
        parsed.hour == 14
        parsed.minute == 15
        parsed.second == 16
        parsed.offset == ZoneOffset.ofHoursMinutesSeconds(17, 18, 19)
        parsed == zoneParsed
    }

    def "parse valid OffsetTime"() {
        given:
        String input = '09:47:51-1234'
        String format = 'HH:mm:ssZ'

        when:
        OffsetTime parsed = OffsetTime.parse(input, format)
        OffsetTime zoneParsed = OffsetTime.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.hour == 9
        parsed.minute == 47
        parsed.second == 51
        parsed.offset == ZoneOffset.ofHoursMinutes(-12, -34)
        parsed == zoneParsed
    }

    def "parse valid Year"() {
        given:
        String input = '09'
        String format = 'yy'

        when:
        Year parsed = Year.parse(input, format)
        Year zoneParsed = Year.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.value == 2009
        parsed == zoneParsed
    }

    def "parse valid YearMonth"() {
        given:
        String input = 'Jan09'
        String format = 'MMMyy'

        when:
        YearMonth parsed = YearMonth.parse(input, format)
        YearMonth zoneParsed = YearMonth.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.year == 2009
        parsed.month == Month.JANUARY
        parsed == zoneParsed
    }        

    def "parse valid ZonedDateTime"() {
        given:
        String input = '2017/07/11 9:47PM Pacific Standard Time'
        String format = 'yyyy/MM/dd h:mma zzzz'

        when:
        ZonedDateTime parsed = ZonedDateTime.parse(input, format)
        ZonedDateTime zoneParsed = ZonedDateTime.parse(input, format, ZoneId.systemDefault())

        then:
        parsed.year == 2017
        parsed.month == Month.JULY
        parsed.dayOfMonth == 11
        parsed.hour == 21
        parsed.minute == 47
        parsed.zone == ZoneId.of('America/Los_Angeles')
        parsed == zoneParsed
    }
}