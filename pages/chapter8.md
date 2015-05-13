---
layout: page
title: Chapter 8. Use Java SE 8 Date/Time API
description: 1Z0-810 Java SE8
comments: true
---

### 8.1  Create and manage date-based and time-based events; including combination of date and time into a single object using  LocalDate, LocalTime, LocalDateTime, Instant, Period, Duration

The `DayOfWeek` enum consists of seven constants that describe the days of the week: MONDAY through SUNDAY. 

The `Month` enum includes constants for the twelve months, JANUARY through DECEMBER.

A `LocalDate` represents a year-month-day in the ISO calendar and is useful for representing a date without a time.

`YearMonth`: This class represents the month of a specific year.

    YearMonth date2 = YearMonth.of(2010, Month.FEBRUARY);
  
`MonthDay` : This class represents the day of a particular month
     
     MonthDay date = MonthDay.of(Month.FEBRUARY, 29);
  
`Year`     : This class represents the Year

     Year year = Year.of(2012)
  
`LocalTime` : Deals with hour-min-sec-nanosecond representing time part of date. 

The LocalTime class does not store time zone or daylight saving time information
     
`LocalDateTime`: The class that handles both date and time, without a time zone
This class is used to represent date (month-day-year) together with time (hour-minute-second-nanosecond).
Combo of LocalDate + LocalTime :fire: Does not store time zone information

[See Table ](https://docs.oracle.com/javase/tutorial/datetime/iso/overview.html)

----------------------------------------------

##### Instant, Period and Duration : Machine Time	

`Instant`: Represents the start of a nanosecond on the timeline.
Stores values internally as 

    private final long seconds;
    private final int nanos;
	  
The Instant class does not work with human units of time, such as years, months, or days. You should convert into `LocalDateTime/ZonedOffsetTime` and then do the calculation.

Either a `ZonedDateTime` or an `OffsetTimeZone` object can be converted to an Instant object, as each maps to an exact moment on the timeline.

However to convert an Instant object to a ZonedDateTime or an OffsetDateTime object REQUIRES supplying time zone, or time zone offset, information


`Period`: A Period uses date-based values (years, months, days) to specify an amount of time

Used to define an amount of time with date-based values (years, months, days)

Stores days, month and year as int values
	
`Duration` : measures an amount of time using time-based values (seconds, nanoseconds).

A Duration is not connected to the timeline, in that it does not track time zones or daylight saving time

Stores values internally as 

    private final long seconds;
    private final int nanos;
    
{% highlight java linenos %}	
Duration d = Duration.ofDays(10);
System.out.println("10days: " + d);
10days: PT240H
{% endhighlight %} 	

The `ChronoUnit` enum, discussed in the The Temporal Package, defines the units used to measure time.

`ChronoUnit.between` - Method is useful when you want to measure an amount of time in a single unit of time only, such as days or seconds.

    
### 8.2  Work with dates and times across time-zones and manage changes resulting from daylight savings

`ZoneId - abstract class`

specifies a time zone identifier and provides rules for converting between an Instant and a LocalDateTime.
Incorrect zone id to ZoneId.of(...) returns `ZoneRulesException` :fire:

`ZoneOffset - subclass of ZoneId`

	specifies a time zone offset from Greenwich/UTC time	

`ZonedDateTime`  - handles a date and time with a corresponding time zone with a time zone offset from Greenwich/UTC
	
Stores internally values as:

    private final LocalDateTime dateTime;
    private final ZoneOffset offset;	// UTC offset
    private final ZoneId zone;
    
`OffsetDateTime` 
handles a date and time with a corresponding time zone offset from Greenwich/UTC, without a time zone ID.

The OffsetDateTime class, in effect, combines the LocalDateTime class with the ZoneOffset class.
It is used to represent a full date (year, month, day) and time (hour, minute, second, nanosecond) with an offset from Greenwich/UTC time
	
`OffsetTime` -  handles time with a corresponding time zone offset from Greenwich/UTC, without a time zone ID.	

The `OffsetTime` class, in effect, combines the LocalTime class with the ZoneOffset class.
It is used to represent time (hour, minute, second, nanosecond) with an offset from Greenwich/UTC 
The OffsetTime class is used in the same situations as the OffsetDateTime class, but when tracking the date is not needed
	
Although all three classes maintain an offset from Greenwich/UTC time, only ZonedDateTime uses the ZoneRules, part of the java.time.zone package,
to determine how an offset varies for a particular time zone. esp during DST changes. The others dont do that.

### 8.3  Define and create timestamps, periods and durations; apply formatting to local and zoned dates and times

The parse and the format methods throw an exception if a problem occurs during the conversion process. 
parse(...) throw DateTimeParseException  and 
format(...) throws DateTimeException

The `DateTimeFormatter` class is both immutable and thread-safe; it can (and should) be assigned to a static constant where appropriate. :fire:

The java.time.temporal package provides a collection of interfaces, classes, and enums that support date and time code

Classes like LocalDate, LocalTime  or ZonedDateTime are all concrete implementations of it (The java.time.temporal.Temporal interface)

`java.time.temporal.ChronoUnit` enum 

contains a standard set of date periods units extends from java.time.temporal.TemporalUnit
ChronoUnit.DAYS and ChronoUnit.WEEKS

`ChronoField enum `

Concrete implementation of the TemporalField interface and provides a rich set of defined constants, 
DAY_OF_WEEK, MINUTE_OF_HOUR, and MONTH_OF_YEAR.
 
The TemporalAdjusters class (note the plural) provides a set of predefined adjusters:

     first or last day of the month, 
	 first or last day of the year, 
	 last Wednesday of the month, or 
	 first Tuesday after a specific date
	 
The predefined adjusters are defined as static methods and are designed to be used with the static import statement.
	 
Interoperability with Legacy Code. :bang: - Not mentioned in Exam topics though but good to know.

{% highlight java  %} 
Calendar.toInstant() 
 - converts the Calendar object to an Instant.

GregorianCalendar.toZonedDateTime() 
 - converts a GregorianCalendar instance to a ZonedDateTime.

GregorianCalendar.from(ZonedDateTime) 
 - creates a GregorianCalendar object using the default locale from a ZonedDateTime instance.

Date.from(Instant) 
 - creates a Date object from an Instant.

Date.toInstant() 
 - converts a Date object to an Instant.

TimeZone.toZoneId() 
 - converts a TimeZone object to a ZoneId.
{% endhighlight %} 

--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part8)

--------------------------------	    
[Next Chapter 9. JavaScript on Java with Nashorn](chapter9.html)

--------------------------------
