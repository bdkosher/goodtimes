package bdkosher.goodtimes

import java.time.*
import java.time.temporal.*

import groovy.transform.PackageScope

/**
 * Extension methods for java.time.Instant
 *
 * Aims to mimic the Groovy JDK extension methods available for java.util.Date and java.util.Calendar instances.
 */
class InstantExtension {

    /**
     * Iterates from this Instant down to the given Instant, inclusive, decrementing by one second each time.
     */
    static void downto(final Instant self, Instant to, Closure closure) {
        if (to > self) {
            throw new GroovyRuntimeException("The argument ($to) to downto() cannot be later than the value ($self) it's called on.")
        }
        (self..to).each(closure)
    }

    /**
     * Iterates from this Instant down to the given Instant, inclusive, decrementing by one second each time.
     */
    static void upto(final Instant self, Instant to, Closure closure) {
        if (to < self) {
            throw new GroovyRuntimeException("The argument ($to) to upto() cannot be earlier than the value ($self) it's called on.")
        }
        (self..to).each(closure)
    }

    /**
     * The next day.
     */
    static Instant next(final Instant self) {
        plus(self, 1)
    }

    /**
     * The previous day.
     */
    static Instant previous(final Instant self) {
        minus(self, 1)
    }

    /**
     * Adds the given number of seconds to the Instant, returning a new Instant instance.
     */
    static Instant plus(final Instant self, int seconds) {
        self.plusSeconds(seconds)
    }

    /**
     * Subtracts the given number of seconds to the Instant, returning a new Instant instance.
     */
    static Instant minus(final Instant self, int seconds) {
        self.minusSeconds(seconds)
    }

    /**
     * Returns a TemporalAmount equivalent to the time between this Instant (inclusive) and the provided Instant (exclusive).
     */
    static Duration rightShift(final Instant self, Instant other) {
        Duration.between(self, other)
    }

    /**
     * Returns the value corresponding to the given TemporalField, provided it is supported by Instant as per its isSupported method.
     */
    static getAt(final Instant self, TemporalField field) {
        field == ChronoField.INSTANT_SECONDS ? self.getLong(field) : self.get(field)
    }

    /**
     * Converts a Instant to an equivalent instance of java.util.Date. 
     */
    static Date toDate(final Instant self) {
        new Date(self.toEpochMilli())
    }

    /**
     * Converts a Instant to a (mostly) equivalent instance of java.util.Calendar.
     */
    static Calendar toCalendar(final Instant self) {
        toDate(self).toCalendar()
    }
}