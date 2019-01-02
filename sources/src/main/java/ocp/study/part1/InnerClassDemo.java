package ocp.study.part1;

public class InnerClassDemo {

  private int x = 10;
  private static int staticX = 20;

  interface SomeInterface {
    void doSomething();
  }

  public static void testLocalClassStatic() {
    int z = 0;
    class MyInner {
      public void seeOuter() {
        System.out.println("staticX is " + staticX);
        // Cannot change z here;
//        z++;
      }
    }

    MyInner i = new MyInner();
    i.seeOuter();
  }

  public void testLocalClass() {

    class MyInner {
      public void seeOuter() {
        System.out.println("x is " + x);
      }
    }

    MyInner i = new MyInner();
    i.seeOuter();
  }

  public void testAnonymousClass() {
    int y = 30;
    System.out.println("Anonymous Class");
    SomeInterface someInterface = new SomeInterface() {
      int z = 40;
      // static int z1 = 42; // <-- Inner class cannot have static declarations
      static final int z2 = 42; // <-- OK

      @Override
      public void doSomething() {
//        y++;  // y needs to be final
        x++;
        z++;
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("z = " + z);

        System.out.println("staticX is " + staticX);
      }
    };
    someInterface.doSomething();
  }

  public static void main(String[] args) {
    InnerClassDemo innerClassDemo = new InnerClassDemo();
    innerClassDemo.testLocalClass();
    InnerClassDemo.testLocalClassStatic();
    innerClassDemo.testAnonymousClass();
  }
}
