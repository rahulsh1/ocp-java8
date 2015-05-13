package ocp.study.part2;

import java.util.function.UnaryOperator;

public class UnaryOperatorDemo {

  public static void main(String[] args) {
    UnaryOperator<String> uo = s -> s + " is great !";
    System.out.print(uo.apply("Java 8"));
  }
}
