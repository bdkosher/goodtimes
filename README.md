# Goodtimes
Java 8 Date/Time API enhancements for Groovy

![goodtimes logo](https://raw.githubusercontent.com/bdkosher/goodtimes/master/logo.gif)

Groovy provides useful extension methods for working with [`java.util.Date`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Date.html) and [`java.util.Calendar`](http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Calendar.html) but as of yet does not include extension methods for the newer Java 8 Date/Time API classes. Groovy will likely will not add methods for the Date/Time API until at least version 3.0, which will require a Java 8 minimum.

Goodtimes primarily provides extension methods for the classes in the `java.time` package, as well as extra methods on `java.util.Date` and `java.time.Calendar` for converting to `java.time` types, such as `Date` into a `LocalDate` or `Calendar` into `LocalDateTime`.

## Prerequisites

Goodtimes requires Java 8 or later.

## Installation

Until goodtimes is published in a public Maven repository, the library needs to be built from source and added to the classpath.

    gradlew install
    cp build/libs/goodtimes-0.1.jar $MY_APP_CLASSPATH

## Sample Usage

    // bridging to new API from old
    Calendar cal = Calendar.instance

    Instant instant = cal.toInstant()
    LocalDate localDate = cal.toLocalDate()
    LocalTime localTime = cal.toLocalTime()
    LocalDateTime localDateTime = cal.toLocalDateTime()

    