package ocp.study.part6;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class OtherStreamSources {

  static void arrays() {
    String[] strArray = {"A", "B", "PC", "D", "PM"};
    Arrays.stream(strArray)
        .filter(s -> s.startsWith("P"))
        .forEach(System.out::println);

    long[] longArr = {1L, 2L, 3L, 5L, 10L};
    LongStream longStream = Arrays.stream(longArr);
    System.out.println("Sum = " + longStream.sum());
  }

  static void intstream() {
    IntStream intStream = IntStream.range(1, 20);
    intStream.forEach(i -> System.out.print(i + " "));
    System.out.println("\nAverage: " + IntStream.range(1, 20).average());

    // Note it includes numbers upto 20
    IntStream intStream2 = IntStream.rangeClosed(1, 20);
    intStream2.forEach(i -> System.out.print(i + " "));
    System.out.println();

    IntStream intStream1 = IntStream.of(1, 10, 9, 3, 8);
    intStream1.sorted().forEach(e -> System.out.print(e + " "));
  }

  public static void main(String[] args) {
    arrays();
    intstream();
  }
}
