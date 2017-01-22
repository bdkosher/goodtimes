package bdkosher.goodtimes

import java.time.*

class DayOfWeekExtension {

    static DayOfWeek plus(final DayOfWeek self, int days) {
        //DayOfWeek.of(1 + (self.value - 1 + days) % 7)
        int val = ((self.value + days - 1) % 7) + 1
        DayOfWeek.of(val > 0 ? val : 7 + val)
    }

    static DayOfWeek minus(final DayOfWeek self, int days) {
        plus(self, days * -1)
    }

    static DayOfWeek next(final DayOfWeek self) {
        plus(self, 1)
    }

    static DayOfWeek previous(final DayOfWeek self) {
        minus(self, 1)
    }

    static DayOfWeek minus(final DayOfWeek self, DayOfWeek other) {
        DayOfWeek.of(self.value + 7 - other.value)
    }
}