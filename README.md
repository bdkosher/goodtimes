# Goodtimes
Java 8 Date/Time API enhancements for Groovy

![goodtimes logo](https://raw.githubusercontent.com/bdkosher/goodtimes/master/logo.gif)

Groovy provides useful extension methods for working with [`java.util.Date`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Date.html) and [`java.util.Calendar`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Calendar.html) but as of yet does not include extension methods for the newer Java 8 Date/Time API classes.

Goodtimes primarily provides extension methods for the classes in the `java.time` package, as well as extra methods on `java.util.Date` and `java.time.Calendar` for converting to `java.time` types, such as `Date` into a `LocalDate` or `Calendar` into `LocalDateTime`.

## Prerequisites

Goodtimes requires Java 8 or later.

## Installation

Until goodtimes is published in a public Maven repository, the library needs to be built from source and added to the runtime classpath.

    gradlew install
    cp build/libs/goodtimes-0.1.jar $USER_HOME/.groovy/lib

## Overloaded Operators

Most extension methods are used to overload operators on the `java.time` types.

### The `++` and `--` Operators

Increment or decrement `Instant`, `LocalTime`, `LocalDateTime`, `Year` and `Duration` by 1 second,. For  `LocalDate`, `Period`, and `DayOfWeek`, increment or decrement by a day.

```groovy
    def now = LocalTime.now()
    def today = LocalDate.now()

    LocalTime oneSecondAgo = --now
    LocalDate tomorrow = now++
```

### The `+` and `-` Operators

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

### The `[]` Operator

Delegates to the `get()` or `getLong()` methods, enabling retrieval of the specified `TemporalField` of `Instant`, `LocalTime`, and `LocalDateTime`. For `Period` and `Duration`, allows retrieval of the specified `TemporalUnit`. Although `Duration.getLong()` only supports `ChronoUnit.SECONDS` and `ChronoUnit.NANOS` but `[]` works for additional `ChronoUnit` enums.

```groovy
    def sixtySeconds = Duration.parse('PT60S')
    assert sixtySeconds[ChronoUnit.MINUTES] == 1
```

### The `<<` Operator

Left shifting can be used to merge two smaller types into an aggregate type. For example, left-shifting a `LocalTime` into a `LocalDate` (or vice versa) results in a `LocalDateTime`.

```groovy
    def thisYear = Year.of(2017)

    YearMonth december2017 = thisYear << Month.DECEMBER
    LocalDate christmas = december2017 << 25
    LocalDateTime christmasAtNoon = christmas << LocalDate.of(12, 0, 0)
    ZonedDateTime chirstmasAtNoonInNYC = christmasAtNoon << ZoneId.of('America/New_York')
    OffsetDateTime chirstmasAtNoonInGreenwich = christmasAtNoon << ZoneOffset.UTC
```

### The `*` and `/` Operators

A `Period` and `Duration` can be multiplied by a scalar. Only a `Duration` can be divided.

```groovy
    def week = Period.ofDays(7)
    def minute = Duration.ofMinutes(1)

    Period fortnight = week * 2
    Duration thirtySeconds = minute / 2
```

### The `+` and `-` Operators

A `Period`, `Duration`, or `Year` can be forced positive or negated via the `+` and `-` operators.

```groovy
    def oneWeek = Period.ofDays(7)
    def oneHour = Duration.ofHours(1)

    assert +oneWeek == oneWeek
    assert -oneHour == Duration.ofHours(-1)
```    

## Bridging Over From `java.util.Date` and `java.util.Calendar`

Extension methods exist on `Date` and `Calendar` that converted to their reasonably equivalent `java.time` types.

```groovy
    def cal = Calendar.instance
    def date = new Date()

    Instant i1 = cal.toInstant()
    Instant i2 = date.toInstant()
    LocalDate ld1 = cal.toLocalDate()
    LocalDate ld2 = date.toLocalDate()
    LocalTime lt1 = cal.toLocalTime()
    LocalTime lt2 = date.toLocalTime()
    LocalDateTime ldt1 = cal.toLocalDateTime()
    LocalDateTime ldt2 = date.toLocalDateTime()
```