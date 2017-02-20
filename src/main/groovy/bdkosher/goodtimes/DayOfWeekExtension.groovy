package bdkosher.goodtimes

import java.time.*

/**
 * Extension methods for java.time.DayOfWeek
 */
class DayOfWeekExtension {

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

}