package ocp.study.part8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class FormatParse {

  private static void format() {
    LocalDateTime now = LocalDateTime.now();
    System.out.println("Now=" + now + " formatted: " + now.format(DateTimeFormatter.ISO_ORDINAL_DATE));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
    System.out.println("Now=" + now + " formatted: " + now.format(formatter));
  }

  private static void parse() {
    String inStr = "May 1 2015";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
    // Throw RuntimeException - DateTimeParseException
    LocalDate d = LocalDate.parse(inStr, formatter);
    System.out.println(inStr + " = " + d);
  }

  private static void adjusters() {
    LocalDateTime now = LocalDateTime.now();

    System.out.printf("first day of Month: %s%n", now.with(TemporalAdjusters.firstDayOfMonth()));
    System.out.printf("first Monday of Month: %s%n", now.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));
    System.out.printf("first day of next Year: %s%n", now.with(TemporalAdjusters.firstDayOfNextYear()));
  }

  public static void main(String[] args) {
    parse();
    format();
    adjusters();
  }
}
