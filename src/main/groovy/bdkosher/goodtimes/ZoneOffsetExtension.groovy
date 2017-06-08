package bdkosher.goodtimes

import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalField
import java.time.temporal.TemporalUnit
import groovy.transform.PackageScope

/**
 * Extension methods for java.time.ZoneOffset
 */
class ZoneOffsetExtension {

    /**
     * Prefixes an offsetId if there is no prefix (and it's not equal to "Z")
     */
    @PackageScope
    static final prefixOffsetId = { String offsetId ->
        if (offsetId != null && offsetId != 'Z' && offsetId.length() > 0 && !['+', '-'].contains(offsetId[0])) {
            '+' + offsetId
        } else {
            offsetId
        }
    }

    @PackageScope
    static final describeAsDuration = { ZoneOffset zoneOfset ->
        Duration.ofSeconds(zoneOfset.totalSeconds).describe()
    }

    /**
     * Describes this ZoneOffset as a Map of ChronoUnit keys (HOURS, MINUTES, and SECONDS) and integer values. The values
     * are normalized. For example, <code>ZoneOffset.ofTotalSeconds(60 * 60 * 3 + 60 * 15).describe()</code> would return
     * <code>[ChronoUnit.HOURS) : 3, (ChronoUnit.MINUTES) : 15, (ChronoUnit.SECONDS) : 0]</code>.
     * If a ZoneOffset is negative, then all of the non-zero fields will be negative.
     */
    static Map<TemporalUnit, Integer> describe(final ZoneOffset self) {
        DurationExtension.describe(toDuration(self))
                .findAll { k, v -> k != ChronoUnit.NANOS }
                .collectEntries { k, v -> [(k): v as Integer]}
    }

    /**
     * Adds the given ZoneOffset to this offset.
     * If the result is outside of the supported range of -18 to +18 hours, a DateTimeException will be thrown.
     */
    static ZoneOffset plus(final ZoneOffset self, ZoneOffset other) {
        ZoneOffset.ofTotalSeconds(self.totalSeconds + other.totalSeconds)
    }

    /**
     * Subtracts the given ZoneOffset to this offset.
     * If the result is outside of the supported range of -18 to +18 hours, a DateTimeException will be thrown.
     */
    static ZoneOffset minus(final ZoneOffset self, ZoneOffset other) {
        ZoneOffset.ofTotalSeconds(self.totalSeconds - other.totalSeconds)
    }

    /**
     * Adds the given value of the id to this offset. The offset id may be in the forms supported by the getId() method.
     * <ul>
     * <li>Z</li>
     * <li>+h</li>
     * <li>+hh</li>
     * <li>+hh:mm</li>
     * <li>-hh:mm</li>
     * <li>+hhmm</li>
     * <li>-hhmm</li>
     * <li>+hh:mm:ss</li>
     * <li>-hh:mm:ss</li>
     * <li>+hhmmss</li>
     * <li>-hhmmss</li>
     * </ul>
     * The +/- prefix is optional and, if absent, will be assumed to be positive.
     * If the result is outside of the supported range of -18 to +18 hours, a DateTimeException will be thrown.
     */
    static ZoneOffset plus(final ZoneOffset self, String offsetId) {
        plus(self, ZoneOffset.of(prefixOffsetId(offsetId)))
    }

    /**
     * Subtracts the given value of the id to this offset. The offset id may be in the forms supported by the getId() method.
     * <ul>
     * <li>Z</li>
     * <li>+h</li>
     * <li>+hh</li>
     * <li>+hh:mm</li>
     * <li>-hh:mm</li>
     * <li>+hhmm</li>
     * <li>-hhmm</li>
     * <li>+hh:mm:ss</li>
     * <li>-hh:mm:ss</li>
     * <li>+hhmmss</li>
     * <li>-hhmmss</li>
     * </ul>
     * The +/- prefix is optional and, if absent, will be assumed to be positive.
     * If the result is outside of the supported range of -18 to +18 hours, a DateTimeException will be thrown.
     */
    static ZoneOffset minus(final ZoneOffset self, String offsetId) {
        minus(self, ZoneOffset.of(prefixOffsetId(offsetId)))
    }

    /**
     * Adds the given number of hours to this ZoneOffset, returning a new ZoneOffset for the total hours.
     * If the result is outside of the supported range of -18 to +18 hours, a DateTimeException will be thrown.
     */
    static ZoneOffset plus(final ZoneOffset self, int hours) {
        plus(self, ZoneOffset.ofHours(hours))
    }

    /**
     * Subracts the given number of hours to this ZoneOffset, returning a new ZoneOffset for the total hours.
     * If the result is outside of the supported range of -18 to +18 hours, a DateTimeException will be thrown.
     */
    static ZoneOffset minus(final ZoneOffset self, int hours) {
        minus(self, ZoneOffset.ofHours(hours))
    }

    /**
     * Advances the ZoneOffset by one hour, provided it is not +18 hours. Otherwise, a DateTimeException will be thrown.
     */
    static ZoneOffset next(final ZoneOffset self) {
        plus(self, 1)
    }

    /**
     * Reduces the ZoneOffset by one hour, provided it is not -18 hours. Otherwise, a DateTimeException will be thrown.
     */
    static ZoneOffset previous(final ZoneOffset self) {
        minus(self, 1)
    }

    /**
     * Returns the hours component of the ZoneOffset.
     */
    static int getHours(final ZoneOffset self) {
        describeAsDuration(self)[ChronoUnit.HOURS]
    }

    /**
     * Returns the minutes component of the ZoneOffset. All current, legitimate offsets are in increments of 15 minutes.
     */
    static int getMinutes(final ZoneOffset self) {
        describeAsDuration(self)[ChronoUnit.MINUTES]
    }

    /**
     * Returns the seconds component of the ZoneOffset--not the total seconds. All current, legitimate offsets will have a seconds component of 0.
     */
    static int getSeconds(final ZoneOffset self) {
        describeAsDuration(self)[ChronoUnit.SECONDS]
    }

    /**
     * Returns the value corresponding to the given TemporalField, provided it is supported by ZoneOffset as per its isSupported method.
     */
    static int getAt(final ZoneOffset self, TemporalField field) {
        self.get(field)
    }

    /**
     * Returns an OffsetDateTime of this ZoneOffset and the given date time.
     */
    static OffsetDateTime leftShift(final ZoneOffset self, LocalDateTime dateTime) {
        OffsetDateTime.of(dateTime, self)
    }    
}