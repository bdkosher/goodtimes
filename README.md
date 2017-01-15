# Goodtimes
Java 8 Date/Time API enhancements for Groovy

Groovy provides good support for `java.util.Date` and `java.util.Calendar`. It's time (har har) that the same level of support be available for the new Java 8 Date/Time API.

## Usage

Goodtimes primarily provides extension methods on the new java.time classes, as well as java.util.Date and Calendar.

### Date and Calendar extension methods

    Calendar cal = ...
    Date date = ...

    Instant instantFromCal = cal.toInstant()
    Instant instantFromDate = date.toInstant()    

    LocalDate dateFromCal = cal.toLocalDate()
    LocalDate dateFromDate = date.toLocalDate()

    LocalTime timeFromCal = cal.toLocalDate()
    LocalTime timeFromDate = date.toLocalDate()    

    LocalDateTime dateTimeFromCal = cal.toLocalDateTime()
    LocalDateTime dateTimeFromDate = date.toLocalDateTime()

    ZonedDateTime zonedFromCal = cal.toZoneDateTime(ZoneId.of('Europe/Paris'))
    ZonedDateTime zonedFromDate = date.toZoneDateTime(ZoneId.of('Europe/Paris'))

    OffsetDateTime offsetFromCal = cal.toOffsetDateTime(ZoneOffset.UTC)
    OffsetDateTime offsetFromCal = date.toOffsetDateTime(ZoneOffset.UTC)

### LocalDate extension methods