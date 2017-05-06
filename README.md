# Goodtimes
*Java 8 Date/Time API enhancements for Groovy*

![goodtimes logo](https://raw.githubusercontent.com/bdkosher/goodtimes/master/logo.gif)

The [Groovy JDK](http://groovy-lang.org/gdk.html) adds useful methods to [`java.util.Date`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Date.html) and [`java.util.Calendar`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Calendar.html) but as of yet does not include comparable methods for the newer Java 8 Date/Time API classes.

Goodtimes fills this gap by providing these `java.time` extension methods, as well as new methods on `java.util.Date` and `java.time.Calendar` for converting to `java.time` equivalents.

## Contents
 * [Prerequisites](#prerequisites)
 * [Installation](#installation)
 * [Building from Source](#building-from-source) 
 * [API Features](#api-features)
   * [Overloaded Operators](#overloaded-operators)
   * [Groovy JDK Mimicking Methods](#groovy-jdk-mimicking-methods)
   * [Java 8 and Legacy API Bridging Methods](#java-8-and-legacy-api-bridging-methods)
 * [Future Changes](#future-changes)

## Prerequisites

Goodtimes requires Java 8 or later.

## Installation

Add the goodtimes jar to your classpath in your preferred way and you're set.

### Grape
```groovy
@Grab('com.github.bdkosher:goodtimes:1.0') 
```

### Gradle
```groovy
compile group: 'com.github.bdkosher', name: 'goodtimes', version: '1.0'
```

### Maven
```xml
 <dependency>
    <groupId>com.github.bdkosher</groupId>
    <artifactId>goodtimes</artifactId>
    <version>1.0</version>
</dependency>
```

## Building from Source

Clone the repo or [download a source release](https://github.com/bdkosher/goodtimes/archive/v1.0.tar.gz) and build with Gradle. 

```bash
    gradlew install
    cp build/libs/goodtimes-1.0.jar $USER_HOME/.groovy/lib
```

## API Features

### Overloaded Operators

Most extension methods are used to overload operators on the `java.time` types.

#### The `++` and `--` Operators

Increment or decrement `Instant`, `LocalTime`, `LocalDateTime`, `Year` and `Duration` by 1 second,. For  `LocalDate`, `Period`, and `DayOfWeek`, increment or decrement by a day.

```groovy
    def now = LocalTime.now()
    def today = LocalDate.now()

    LocalTime oneSecondAgo = --now
    LocalDate tomorrow = now++
```

#### The `+` and `-` Binary Operators

Add seconds using a `long` or `int` primitive directly to  `Instant`, `LocalTime`, `LocalDateTime`, and `Duration`. Add or substract days for `LocalDate`, `Period`, and `DayOfWeek`.

```groovy
    def now = LocalDateTime.now()
    def today = LocalDate.now()

    LocalDateTime oneMinuteAgo = now - 60
    LocalDate oneWeekFromNow = now + 7
```    

The `-` operator can be used to create a `Period` from two `LocalDate` values or a `Duration` from two `Instant`, `LocalTime`, or `LocalDateTime` values.

```groovy
    def today = LocalDate.now()
    def tomorrow = today + 1

    Period oneDay = today - tomorrow
    Period negOneDay = tomorrow - today
```

#### The `[]` Operator

This operator delegates to the `java.time` types' `get()` or `getLong()` methods, enabling retrieval of the specified `TemporalField` (for `Instant`, `LocalTime`, and `LocalDateTime`) or `TemporalUnit`(for `Period` and `Duration`). Although `Duration.getLong()` only supports `ChronoUnit.SECONDS` and `ChronoUnit.NANOS`, the `[]` operator supports additional `ChronoUnit` values.

```groovy
    def sixtySeconds = Duration.parse('PT60S')
    assert sixtySeconds[ChronoUnit.MINUTES] == 1
```

In addition to supporting `TemporalField` arguments, the `LocalDate`, `LocalTime`, and `LocalDateTime` classes can accept `java.util.Calendar` constants:

```groovy
    def lastChristmas = LocalDate.of(2016, 12, 25)

    assert lastChristmas[Calendar.YEAR] == 2016
    assert lastChristmas[Calendar.MONTH] == Calendar.DECEMBER
    assert lastChristmas[Calendar.DATE] == 25
```

#### The `<<` Operator

Left shifting can be used to merge two different `java.time` types into a larger aggregate type. For example, left-shifting a `LocalTime` into a `LocalDate` (or vice versa) results in a `LocalDateTime`.

```groovy
    def thisYear = Year.of(2017)

    YearMonth december2017 = thisYear << Month.DECEMBER
    LocalDate christmas = december2017 << 25
    LocalDateTime christmasAtNoon = christmas << LocalDate.of(12, 0, 0)
    ZonedDateTime chirstmasAtNoonInNYC = christmasAtNoon << ZoneId.of('America/New_York')
    OffsetDateTime chirstmasAtNoonInGreenwich = christmasAtNoon << ZoneOffset.UTC
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

### Groovy JDK Mimicking Methods

Other extension methods seek to mimic those found in the Groovy JDK for [`java.util.Date`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Date.html) and [`java.util.Calendar`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Calendar.html).

#### Iterating Methods

The `upto()` and `downto()` methods of `LocalTime`, `LocalDateTime`, and `Instant` iterate on a per second basis. The methods on `LocalDate` iterate on a per day basis.

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

#### Formatting Methods

 * The `getDateString` method
   * Exists for `LocalDate` and `LocalDateTime` 
   * Equivalent to calling `localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))`
   * Example: `5/5/17`
 * The `getTimeString` method
   * Exists for `LocalTime` and `LocalDateTime` 
   * Equivalent to calling `localTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))`
   * Example: `10:59 PM`
 * The `getDateTimeString` method
   * Exists for `LocalDateTime` 
   * Equivalent to calling `localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))`
   * Example: `5/5/17 10:59 PM`
 * The `format(String pattern)` method
   * Exists on `LocalDate`, `LocalTime`, and `LocalDateTime`
   * Equivalent to `.format(DateTimeFormatter.ofPattern(pattern))`
 * The `format(String pattern, Locale locale)` method
   * Exists on `LocalDate`, `LocalTime`, and `LocalDateTime`
   * Equivalent to `.format(DateTimeFormatter.ofPattern(pattern, locale))`

#### Mutating Methods

`LocalDateTime` has a `clearTime()` method that sets the hours, minutes, seconds, and nanos to zero.

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

An optional `ZoneOffset`, `ZoneId`, or `java.util.TimeZone` may be passed to the above conversion methods to alter the Time Zone of the returned `Date` or `Calendar`. 

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

The `LocalDate`, `LocalTime`, and `LocalDateTime` types have `toDate()` and `toCalendar()` methods as well.

```groovy
    def nows = [LocalDate.now(), LocalTime.now(), LocalDateTime.now()]

    List<Date> dates = nows.collect { it.toDate() }
    List<Calendar> cals = nows.collect { it.toCalendar() } 
```

## Future Changes

 * Provide an equivalent to `groovy.time.TimeCategory`
 * Extension methods for `ZonedDateTime` and `OffsetDateTime`
 * Consider adding missing Date/Calendar methods from Groovy JDK (e.g. `set` and `copyWith`)