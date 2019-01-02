package ocp.study.part1;

public class OuterInnerDemo {

  private String out;

  public class Inner {
    private String in;

    // Compile error: Inner class cannot have static declarations
//    static int x = 10;

    // This is ok
    static final int y = 20;

    //  Compile error: No static declarations
//    public static void doIt() {
//    }
  }

  public static class StaticInner {
    private String in;

    // OK
    static int x = 10;
    static final int y = 20;

    public static void doIt() {

      class StaticInnerInner {
        // Compile error: Inner class cannot have static declarations
//    static int x = 10;
      }
    }
  }

  public static void main(String[] args) {
    OuterInnerDemo.Inner inner = new OuterInnerDemo().new Inner();
    inner.in = "Hello";
  }
}
