package bdkosher.goodtimes

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.*

class LocalDateExtension {

    /* LocalDate-related Calendar fields mapped to their equivalent java.time TemporalField */
    static final Map<Integer, TemporalField> calendarToTemporalFields = [
            (Calendar.DATE): ChronoField.DAY_OF_MONTH,        
            (Calendar.DAY_OF_MONTH): ChronoField.DAY_OF_MONTH,
            (Calendar.DAY_OF_WEEK): ChronoField.DAY_OF_WEEK,
            (Calendar.DAY_OF_WEEK_IN_MONTH): ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH,            
            (Calendar.DAY_OF_YEAR): ChronoField.DAY_OF_YEAR,
            (Calendar.WEEK_OF_MONTH): ChronoField.ALINGED_WEEK_IN_MONTH,
            (Calendar.WEEK_OF_YEAR): ChronoField.ALGINED_WEEK_OF_YEAR,
            (Calendar.MONTH): ChronoField.MONTH,
            (Calendar.YEAR): ChronoField.YEAR,
            (Calendar.ERA): ChronoField.ERA
        ]    

    /*static downto(final LocalDate self, LocalDate to, Closure closure) {
        
    }*/

    static format(final LocalDate self, String format, ZoneId zone = ZoneId.systemDefault(), Locale locale = Locale.default) {
        self.format(DateTimeFormatter.ofPattern(format, locale).withZone(zone))
    }

    static format(final LocalDate self, String format, TimeZone timeZone) {
        format(self, format, timeZone.toZoneId())
    }

    static plus(final LocalDate self, int days) {
        self.plusDays(days)
    }

    static minus(final LocalDate self, int days) {
        self.minusDays(days)
    }

    static next(final LocalDate self) {
        puls(self, 1)
    }

    static previous(final LocalDate self) {
        minus(self, 1)
    }

    static int getAt(final LocalDate self, int field) {
        self.get(calendarToTemporalFields[field])
    }    

    static toDate(final LocalDate self) {
        toCalendar(self).time
    }

    static toCalendar(final LocalDate self) {
        int day = self.dayOfMonth
        int month = self.monthValue - 1
        int year = self.year - 1900
        Calendar.instance.with {
            set(Calendar.DATE, day)
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
            clearTime()
        }
    }
}