package bdkosher.goodtimes

import java.time.*

/**
 * By statically importing this class you can create dates using code like this--if you dare:
 * <pre>
 * LocalDate thisChristmas = December 25
 * LocalDate halleysCometReturn = July 28, 2061
 * </pre>
 */
class LocalDates {

    static January(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.JANUARY, day)
    }

    static February(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.FEBRUARY, day)
    }

    static March(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.MARCH, day)
    }

    static April(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.APRIL, day)
    }

    static May(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.MAY, day)
    }

    static June(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.JUNE, day)
    }

    static July(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.JULY, day)
    }

    static August(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.AUGUST, day)
    }

    static September(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.SEPTEMBER, day)
    }

    static October(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.OCTOBER, day)
    }

    static November(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.NOVEMBER, day)
    }

    static December(int day, int year = Year.now().value) {
        LocalDate.of(year, Month.DECEMBER, day)
    }
}