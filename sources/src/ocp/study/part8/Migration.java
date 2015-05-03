package ocp.study.part8;

import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Conversion to/from  Old Date/Time (Pre Java 8) to Java 8.
 * Not mentioned in the Exam topics though
 */
public class Migration {

  private static void demo() {
    Calendar cal = Calendar.getInstance();
    // Calendar.toInstant()
    Instant calInstant = cal.toInstant();
    ZonedDateTime zdt = ZonedDateTime.ofInstant(calInstant, ZoneId.systemDefault());
    System.out.println("Calendar =" + cal + "\nZDT=" + zdt);

    Date dt = cal.getTime();
    // Date.toInstant()
    LocalDateTime ldt = LocalDateTime.ofInstant(dt.toInstant(), ZoneId.systemDefault());
    // Date.from(Instant)
    Date newDate = Date.from(ldt.toInstant(ZoneOffset.of("-08:00")));
    System.out.println("Old Date = " + dt + "\nldt=" + ldt + "\nUTC newDate=" + newDate);

    TimeZone tz = cal.getTimeZone();
    // TimeZone.toZoneId()
    ZoneId zid = tz.toZoneId();
    System.out.println("Tz=" + tz.getDisplayName() + "\nZoneId=" + zid);
  }

  public static void main(String[] args) {
    demo();
  }

}
