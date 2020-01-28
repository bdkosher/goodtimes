# Goodtimes
*Java 8 Date/Time API enhancements for Groovy 2.4 and earlier*  [![Maven Central](https://img.shields.io/maven-central/v/com.github.bdkosher/goodtimes.svg)](http://repo1.maven.org/maven2/com/github/bdkosher/goodtimes/1.2/goodtimes-1.2.jar)  [![GitHub release](https://img.shields.io/github/tag/bdkosher/goodtimes.svg)](https://github.com/bdkosher/goodtimes/releases/tag/v1.2)

![goodtimes logo](https://raw.githubusercontent.com/bdkosher/goodtimes/master/logo.gif)

The [Groovy JDK](http://groovy-lang.org/gdk.html) adds useful methods to [`java.util.Date`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Date.html) and [`java.util.Calendar`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Calendar.html) but as of yet does not include comparable methods for the newer Java 8 Date/Time API classes.

Goodtimes fills this gap by providing these `java.time` extension methods, as well as new methods on `java.util.Date` and `java.time.Calendar` for converting to `java.time` equivalents.

Note: As of Groovy 2.5, Goodtimes-provided methods are now part of Groovy JDK itself.

## Contents
 * [Prerequisites](#prerequisites)
 * [Installation](#installation)
 * [Building from Source](#building-from-source) 
 * [API Features](#api-features)
   * [Overloaded Operators](#overloaded-operators)
   * [Accessor Properties](#accessor-properties)   
   * [Groovy JDK Mimicking Methods](#groovy-jdk-mimicking-methods)
   * [Java 8 and Legacy API Bridging Methods](#java-8-and-legacy-api-bridging-methods)
   * [Parsing Methods](#parsing-methods)
 * [Future Changes](#future-changes)

## Prerequisites

Goodtimes requires Java 8 or later.

## Installation

Add the goodtimes jar to the classpath in your preferred way and you're set.

### Grape
```groovy
@Grab('com.github.bdkosher:goodtimes:1.2') 
```

### Gradle
```groovy
compile group: 'com.github.bdkosher', name: 'goodtimes', version: '1.2'
```

### Maven
```xml
 <dependency>
    <groupId>com.github.bdkosher</groupId>
    <artifactId>goodtimes</artifactId>
    <version>1.2</version>
</dependency>
```

## Building from Source

Clone the repo or [download a source release](https://github.com/bdkosher/goodtimes/archive/v1.2.tar.gz) and build with Gradle. 

```bash
    gradlew install
    cp build/libs/goodtimes-1.2.jar $USER_HOME/.groovy/lib
```

## API Features

Consult the [goodtimes 1.2 Groovydocs](http://bdkosher.github.io/goodtimes/v1.2/groovydoc/) for complete API information. See the [Groovy metaprogramming documentation](http://groovy-lang.org/metaprogramming.html#_instance_methods) for details on how these methods manifest themselves at runtime.

### Overloaded Operators

Most extension methods are used to overload operators on the `java.time` types.

#### The `++` and `--` Operators

Increment or decrement `Instant`, `LocalTime`, `LocalDateTime`, `OffsetTime`, `OffsetDateTime`, `ZoneDateTime`, and `Duration` by one second. For `LocalDate`, `Period`, and `DayOfWeek`, increment or decrement by one day. Increment or decrement by one hour for `ZoneOffset`, one month for `Month`, and one year for `Year`.

```groovy
    def now = LocalTime.now()
    def today = LocalDate.now()
    def march = Month.MARCH
    def utc = ZoneOffset.UTC

    LocalTime oneSecondAgo = --now
    LocalDate tomorrow = today++
    Month april = ++march
    ZoneOffset utcMinusOne = --utc
```

#### The `+` and `-` Binary Operators

A `long` or `int` operand adds seconds directly to  `Instant`, `LocalTime`, `LocalDateTime`, `OffsetTime`, `OffsetDateTime`, `ZoneDateTime`, and `Duration`. Add or subtract days for `LocalDate`, `Period`, and `DayOfWeek`. Add or subtract hours for `ZoneOffset`. Add or subtract months for `Month`. Add or subtract years for `Year`.

```groovy
    def now = LocalDateTime.now()
    def today = LocalDate.now()
    def march = Month.MARCH
    def utc = ZoneOffset.UTC

    LocalDateTime oneMinuteAgo = now - 60
    LocalDate oneWeekFromToday = today + 7
    Month january = march - 2
    ZoneOffset utcPlusFive = utc + 5
```

#### The `[]` Operator

This operator delegates to the `java.time` types' `get()` or `getLong()` methods, enabling retrieval of the specified `TemporalField` (for `Instant`, `MonthDay`, `YearMonth`, `LocalTime`, `LocalDateTime`, `OffsetDateTime`, and `ZonedDateTime`) or `TemporalUnit`(for `Period` and `Duration`).

```groovy
    def sixtySeconds = Duration.parse('PT60S')
    assert sixtySeconds[ChronoUnit.SECONDS] == 60
```

In addition to supporting `TemporalField` arguments, the `LocalDate`, `LocalTime`, `LocalDateTime`, `OffsetTime`, `OffsetDateTime`, and `ZonedDateTime` classes can accept `java.util.Calendar` constants:

```groovy
    def lastChristmas = LocalDate.of(2016, 12, 25)

    assert lastChristmas[Calendar.YEAR] == 2016
    assert lastChristmas[Calendar.MONTH] == Calendar.DECEMBER
    assert lastChristmas[Calendar.DATE] == 25
```

#### The `<<` Operator

Left shifting can be used to merge two different `java.time` types into a larger aggregate type. For example, left-shifting a `LocalTime` into a `LocalDate` results in a `LocalDateTime`. Left shifting is reflexive so that `A << B` yields the same result as `B << A`.

```groovy
    def thisYear = Year.of(2017)
    def noon = LocalTime.of(12, 0, 0)

    YearMonth december2017 = thisYear << Month.DECEMBER
    LocalDate christmas = december2017 << 25
    OffsetTime noonInGreenwich = noon << ZoneOffset.ofHours(0)
    LocalDateTime christmasAtNoon = christmas << noon
    ZonedDateTime chirstmasAtNoonInNYC = christmasAtNoon << ZoneId.of('America/New_York')
    OffsetDateTime chirstmasAtNoonInGreenwich = christmasAtNoon << ZoneOffset.UTC
```

#### The `>>` Operator

The right shift operator, when read as meaning "through" or "to", is used to create a `Period` from two `LocalDate`, `YearMonth`, or `Year` instances. Similarly, the `>>` operator produces a `Duration` from two `Instant`, `LocalTime`, `LocalDateTime`, `OffsetTime`, `OffsetDateTime`, or `ZonedDateTime` instances.

```groovy
    def today = LocalDate.now()
    def tomorrow = today + 1

    Period oneDay = today >> tomorrow
    Period negOneDay = tomorrow >> today
```

#### The `*` and `/` Operators

A `Period` and `Duration` can be multiplied by a scalar. Only a `Duration` can be divided.

```groovy
    def week = Period.ofDays(7)
    def minute = Duration.ofMinutes(1)

    Period fortnight = week * 2
    Duration thirtySeconds = minute / 2
```

#### The  `+` and `-` Unary Operators

A `Period`, `Duration`, or `Year` can be made positive or negated via the `+` and `-` operators.

```groovy
    def oneWeek = Period.ofDays(7)
    def oneHour = Duration.ofHours(1)

    assert +oneWeek == oneWeek
    assert -oneHour == Duration.ofHours(-1)
```

### Accessor Properties

A `getDay` method exists on `LocalDate`, `LocalDateTime`, `MonthDay`, `OffsetDateTime`, and `ZoendDateTime` as an alias for `getDayOfMonth`.

```groovy
    def independenceDay = LocalDate.of(2017, Month.JULY, 4)

    assert independenceDay.day == 4
    assert independenceDay.day == independenceDay.dayOfMonth
```

The `ZoneOffset` has getters to obtain the hours, minutes, and seconds values of the offset.

```groovy
    def zoneOffset = ZoneOffset.ofHoursMinutesSeconds(5, 10, 20)

    assert zoneOffset.hours == 5
    assert zoneOffset.minutes == 10
    assert zoneOffset.seconds == 20
```

The legacy `Calendar` class has getters to obtain the time zone information as a `ZoneId` and `ZoneOffset`.

```groovy
    def cal = Calendar.getInstance(TimeZone.getTimeZone('GMT'))

    assert cal.zoneId == ZoneId.of('GMT')
    assert cal.zoneOffset == ZoneOffset.ofHours(0)
```

Additionally, `Calendar` has `getYear`, `getYearMonth`, `getMonth`, `getMonthDay`, and `getDayOfMonth` methods that return the correspondingly-typed `java.time` instances.

```groovy
    def cal = Date.parse('yyyyMMdd', '20170204').toCalendar()

    assert cal.year == Year.of(2017)
    assert cal.month == Month.FEBRUARY
    assert cal.yearMonth == YearMonth.of(2017, Month.FEBRUARY)
    assert cal.dayOfWeek == DayOfWeek.SATURDAY
    assert cal.monthDay == MonthDay.of(Month.FEBRUARY, 4)
```

### Groovy JDK Mimicking Methods

Other extension methods seek to mimic those found in the Groovy JDK for [`java.util.Date`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Date.html) and [`java.util.Calendar`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Calendar.html).

#### Iterating Methods

The `upto()` and `downto()` methods of `LocalTime`, `LocalDateTime`, `OffsetTime`, `OffsetDateTime`, `ZonedDateTime`, and `Instant` iterate on a per second basis. The methods on `LocalDate` iterate on a per day basis.

```groovy
    def now = LocalTime.now()
    def aMinuteAgo = now - 60

    now.downto(aMinuteAgo) { LocalTime t ->
        // this closure will be called 61 times for each sceond between a minute ago and now
    }

    def today = LocalDate.now()
    def tomorrow = today + 1

    today.upto(tomorrow) { LocalDate d -> 
        // this closure will be called twice, once for today and once for tomorrow
    }
```

A static `eachMonth` method exists on `Month` for iterating through every month. Similarly, a static `eachDay` method exists on `DayOfWeek` for iterating through the days of the week.

#### Formatting Methods

 * The `getDateString` method
   * Exists for `LocalDate`, `LocalDateTime`, `OffsetDateTime`, and `ZonedDateTime`
   * Equivalent to calling `localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))`
   * Example: `5/5/17`
 * The `getTimeString` method
   * Exists for `LocalTime`, `LocalDateTime`, `OffsetTime`, `OffsetDateTime`, and `ZonedDateTime`
   * Equivalent to calling `localTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))`
   * Example: `10:59 PM`
 * The `getDateTimeString` method
   * Exists for `LocalDateTime`, `OffsetDateTime`, and `ZonedDateTime`
   * Equivalent to calling `localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))`
   * Example: `5/5/17 10:59 PM`
 * The `format(String pattern)` method
   * Exists on `LocalDate`, `LocalTime`, `LocalDateTime`, `OffsetDateTime`, and `ZonedDateTime`
   * Equivalent to `.format(DateTimeFormatter.ofPattern(pattern))`
 * The `format(String pattern, Locale locale)` method
   * Exists on `LocalDate`, `LocalTime`, `LocalDateTime`, `OffsetTime`, `OffsetDateTime`, and `ZonedDateTime`
   * Equivalent to `.format(DateTimeFormatter.ofPattern(pattern, locale))`

#### Other Methods

**clearTime()** Similar to `Date`, the `LocalDateTime`, `OffsetDateTime`, and `ZonedDateTime` classes have a `clearTime()` method that returns a new instance of the same type with the hours, minutes, seconds, and nanos set to zero. 

**describe()** The `Duration` class has a `describe()` method which returns the normalized String representation as a map of `ChronoUnit` keys: `DAYS`, `HOURS`, `MINUTES`, `SECONDS`, and `NANOS`. The `Period` class also has a `describe()` method but with the `ChronoUnit` keys of `YEARS`, `MONTHS`, and `DAYS`.

```groovy
    Map<TemporalUnit, Long> durationDesc = Duration.parse('P2DT3H4M5.000000006S').describe()
    assert durationDesc[ChronoUnit.DAYS] == 2
    assert durationDesc[ChronoUnit.HOURS] == 3
    assert durationDesc[ChronoUnit.MINUTES] == 4
    assert durationDesc[ChronoUnit.SECONDS] == 5
    assert durationDesc[ChronoUnit.NANOS] == 6

    Map<TemporalUnit, Long> periodDesc = Period.parse('P6Y3M1D').describe()
    assert periodDesc[ChronoUnit.DAYS] == 1
    assert periodDesc[ChronoUnit.MONTHS] == 3
    assert periodDesc[ChronoUnit.YEARS] == 6
```

### Java 8 and Legacy API Bridging Methods 

Extension methods exist on `Date` and `Calendar` that produce a reasonably equivalent `java.time` type.

```groovy
    def c = Calendar.instance
    def d = new Date()

    Instant cInstant = c.toInstant()
    Instant dInstant = d.toInstant()

    LocalDate cLocalDate = c.toLocalDate()
    LocalDate dLocalDate = d.toLocalDate()
    
    LocalTime cLocalTime = c.toLocalTime()
    LocalTime dLocalTime = d.toLocalTime()

    LocalDateTime cLocalDateTime = c.toLocalDateTime()
    LocalDateTime dLocalDateTime = d.toLocalDateTime()

    ZonedDateTime cZonedDateTime = c.toZonedDateTime()
    ZonedDateTime dZonedDateTime = d.toZonedDateTime()
```

An optional `ZoneOffset`, `ZoneId`, or `java.util.TimeZone` may be passed to the above conversion methods to alter the Time Zone of the returned `Calendar` or to adjust the Time Zone of reference for the returned `Date`.

```groovy
    def d = new Date()
    def offset = ZoneOffset.UTC
    def zoneId = ZoneId.of('America/Resolute')
    def timeZone = TimeZone.getTimeZone('US/Eastern')

    LocalDate localDateUTC = d.toLocalDate(offset)
    LocalDate localDateResolute = d.toLocalDate(zoneId)
    LocalDate localDateUSEastern = d.toLocalDate(timeZone)
```

The `toOffsetDateTime` method requires a `ZoneOffset` argument.

```groovy
    def c = Calendar.instance
    def d = new Date()
    def offset = ZoneOffset.UTC

    OffsetDateTime cOffsetDateTime = c.toOffsetDateTime(offset)
    OffsetDateTime dOffsetDateTime = d.toOffsetDateTime(offset)
```

The various `java.time` Date/Time types have `toDate()` and `toCalendar()` methods as well. Nanoseconds are truncated to the nearest millisecond.

```groovy
    def nows = [LocalDate.now(), LocalTime.now(), LocalDateTime.now(), OffsetDateTime.now(), ZonedDateTime.now()]

    List<Date> dates = nows.collect { it.toDate() }
    List<Calendar> cals = nows.collect { it.toCalendar() } 
```

### Parsing Methods

Each java.time type already having a `parse(CharSequence input, DateTimeFormatter formatter)` method gains two additional static methods:

 * `parse(CharSequence input, String format)` - the format String is used to instantiate a new `DateTimeFormatter` of that formatting pattern.
 * `parse(CharSequence input, String format, ZoneId zone)` - same as above plus the instantiated `DateTimeFormatter` is adjusted to the proivded zone via its `withZone` method.

 ```groovy
    def date = '2017-07-15'
    def offsetDatetime = '111213 141516 +171819'

    LocalDate parsedDate = LocalDate.parse(date, 'yyyy-MM-dd')
    OffsetDateTime parsed = OffsetDateTime.parse(offsetDatetime, 'MMddyy HHmmss XX', ZoneId.of('UTC+0500'))
```

## Future Changes

 * Provide an equivalent to `groovy.time.TimeCategory`
 * TimeZone/ZoneId convenience methods (e.g. get all time zones at a given offset)
 * Consider adding missing Date/Calendar methods from Groovy JDK (e.g. `set` and `copyWith`)
