package ocp.study.part8;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class ZonesDemo {

  static void zonedemo() {
    Set<String> allZones = ZoneId.getAvailableZoneIds();
//    allZones.stream().forEach(System.out::println);

    LocalDateTime ldt = LocalDateTime.now();

    ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("America/Los_Angeles"));
    ZonedDateTime zdt2 = ZonedDateTime.of(ldt, ZoneId.of("Asia/Macau"));
    ZonedDateTime zdt3 = ZonedDateTime.of(ldt, ZoneId.of("Europe/Malta"));

    System.out.println(ldt + " zdt= " + zdt + " zone offset: " + zdt.getOffset());
    System.out.println(ldt + " zdt= " + zdt2 + " zone offset: " + zdt2.getOffset());
    System.out.println(ldt + " zdt= " + zdt3 + " zone offset: " + zdt3.getOffset());

  }

  static void offsetdatetime() {
    // Find the last Thursday in July 2013.
    LocalDateTime localDate = LocalDateTime.of(2015, Month.APRIL, 25, 11, 30);
    ZoneOffset offset = ZoneOffset.of("-08:00");

    OffsetDateTime offsetDate = OffsetDateTime.of(localDate, offset);
    OffsetDateTime lastThursday = offsetDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.THURSDAY));
    System.out.printf(offsetDate + " The last Thursday in April 2015 is the %sth.%n", lastThursday.getDayOfMonth());
  }

  public static void main(String[] args) {
    zonedemo();
    offsetdatetime();
  }
}
