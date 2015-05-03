package ocp.study.part8;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DateAndTime {

  static void date() {
    LocalDate date = LocalDate.of(2015, Month.APRIL, 23);
    LocalDate nextWed = date.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
    System.out.println(date + " next Wed: " + nextWed);
    LocalDate date1 = LocalDate.now();

    System.out.println("Today: " + date1);
    System.out.println("DOW " +  date1.getDayOfWeek());

    LocalDate date2 = LocalDate.parse("2015-04-20");
    System.out.println(date2);
  }

  static void time() {
    LocalTime time = LocalTime.now();
    System.out.println("Current time: " + time);

    LocalTime time1 = LocalTime.parse("19:23:59.999");
    System.out.println("time1: " + time1);
  }

  static void datetime() {
    System.out.println("Now: " + LocalDateTime.now());

    System.out.printf("Apr 15, 1994 @ 11:30am: %s%n", LocalDateTime.of(1994, Month.APRIL, 15, 11, 30));

    System.out.printf("now (from Instant): %s%n", LocalDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault()));

    System.out.printf("6 months from now: %s%n", LocalDateTime.now().plusMonths(6));

    System.out.printf("6 days ago: %s%n", LocalDateTime.now().minusDays(6));
  }

  static void otherDates() {
    System.out.println("\n********* otherDates ************");
    YearMonth date = YearMonth.now();
    System.out.printf("%s: %d%n", date, date.lengthOfMonth());

    YearMonth date2 = YearMonth.of(2007, Month.JANUARY);
    System.out.printf("%s: %d%n", date2, date2.lengthOfMonth());

    YearMonth date3 = YearMonth.parse("2006-11");
    System.out.printf("%s: %b%n", date2, date2.isLeapYear());

    MonthDay date4 = MonthDay.of(Month.FEBRUARY, 29);
    System.out.println("2010 valid Year for Feb29? " + date4.isValidYear(2010));

    boolean validLeapYear = Year.of(2012).isLeap();
    System.out.println("2012 validLeapYear = " + validLeapYear);
  }


  static void instant() {
    System.out.println("\n********* MachineTime ************\n");
    Instant timestamp = Instant.now();
    System.out.println("timestamp: " + timestamp);

    System.out.println("Min=" + Instant.MIN.getEpochSecond() + " Max=" + Instant.MAX.getEpochSecond());
//    System.out.println("Min=" + Long.MIN_VALUE + " Max=" + Long.MAX_VALUE);

    Instant oneHourLater = Instant.now().plus(Duration.ofHours(1));
    System.out.println("oneHourLater="+ oneHourLater);

    // To manipulate days, month, etc first convert to LocalDateTime/ZonedOffsetTime
    LocalDateTime ldt = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
    System.out.printf("%s %d %d at %d:%d%n", ldt.getMonth(), ldt.getDayOfMonth(),
        ldt.getYear(), ldt.getHour(), ldt.getMinute());

    Instant ldtInstant = ldt.toInstant(ZoneOffset.UTC);
    System.out.println("ldt Instant: " + ldtInstant);
  }

  private static void period() {
    LocalDate today = LocalDate.now();
    LocalDate birthday = LocalDate.of(1985, Month.MAY, 1);
    Period p = Period.between(birthday, today);
    long p2 = ChronoUnit.DAYS.between(birthday, today);
    System.out.println("You are " + p.getYears() + " years, " + p.getMonths() +
        " months, and " + p.getDays() +
        " days old. (" + p2 + " days total)");
  }

  private static void duration() {
    Duration d = Duration.ofDays(10);
    System.out.println("10days: " + d);

    Instant current = Instant.now();
    Instant previous = Instant.now().plus(Duration.ofHours(1));
    long gap = ChronoUnit.SECONDS.between(current, previous);
    System.out.println("gap=" + gap);

    LocalDateTime today = LocalDateTime.now();
    LocalDateTime examDay = LocalDateTime.of(2015, Month.MAY, 01, 14, 00);
    Duration d2 = Duration.between(today, examDay);
    System.out.println(d2 + " You have " + d2.get(ChronoUnit.SECONDS)/(3600) + " hours for exam");

  }

  // Java tutorial exercises
  static void exercises() {
    LocalDate date = LocalDate.now();
    System.out.println(date + " PrevThurs = " + findPrevThurs(date));
    date = LocalDate.of(2015, Month.APRIL, 8);
    System.out.println(date + " PrevThurs = " + findPrevThurs(date));
    date = LocalDate.of(2015, Month.APRIL, 9);
    System.out.println(date + " PrevThurs = " + findPrevThurs(date));
  }

  // Given a random date, how would you find the date of the previous Thursday?
  static LocalDate findPrevThurs(LocalDate date) {
    return date.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));
  }

  public static void main(String[] args) {
    date();
    time();
    datetime();
    otherDates();

    instant();
    period();
    duration();
    exercises();
  }
}
