package bdkosher.goodtimes

import java.time.*
import groovy.transform.PackageScope

/**
 * Extension methods for java.time.DayOfWeek
 */
class DayOfWeekExtension {

    /* Maps java.time.DayOfWeek enum values to their equivalent Calendar field integer value. */
    @PackageScope
    static final Map<DayOfWeek, Integer> dayOfWeekToCalendarDay = DayOfWeek.values().collectEntries { [it, Calendar.@"$it"]}    

    /** 
     * Returns the DayOfWeek that is {@code days} many days after this DayOfWeek.
     */
    static DayOfWeek plus(final DayOfWeek self, int days) {
        int val = ((self.value + days - 1) % 7) + 1
        DayOfWeek.of(val > 0 ? val : 7 + val)
    }

    /** 
     * Returns the DayOfWeek that is {@code days} many days before this DayOfWeek.
     */
    static DayOfWeek minus(final DayOfWeek self, int days) {
        plus(self, days * -1)
    }

    /**
     * Returns the DayOfWeek after this DayOfWeek.
     */
    static DayOfWeek next(final DayOfWeek self) {
        plus(self, 1)
    }

    /**
     * Returns the DayOfWeek before this DayOfWeek.
     */
    static DayOfWeek previous(final DayOfWeek self) {
        minus(self, 1)
    }

    /**
     * Returns the value of the day-of-week constant defined in the java.util.Calendar class. For example,
     * <code>assert DayOfWeek.MONDAY.calendarValue() == Calendar.MONDAY</code>
     */
    static int calendarValue(final DayOfWeek self) {
        dayOfWeekToCalendarDay[self]
    }

    /**
     * Returns true if this DayOfWeek is Saturday or Sunday.
     */
    static boolean isWeekend(final DayOfWeek self) {
        self == DayOfWeek.SATURDAY || self == DayOfWeek.SUNDAY
    }

    /**
     * Returns true if this DayOfWeek is Monday through Friday.
     */
    static boolean isWeekday(final DayOfWeek self) {
        !isWeekend(self)
    }

}