---
layout: page
title: Chapter 7. Method Enhancements 
description: 1Z0-810 Java SE8
comments: true
---

### 7.1.  Adding static methods to interfaces 

A static method is a method that is associated with the class in which it is defined rather than with any object.
Every instance of the class shares its static methods. :fire:

- Helper methods can now be part of the Interface, rather than part of classes.
- You can invoke static methods from within default methods
- You cannot invoke default methods from static methods.

- Static methods can only be referenced by the Interface in which they are defined.
  - CANNOT use ClassName or instance of the class to refer to it

- Static methods are not inherited...they belong to only that Interface where they are defined.
  - An Interface inheriting from one with static method wont have that static method.
  - You still have to use the original Interface name to invoke it.
  - You may define the static method again, in another sub-interface. Use the appropriate interface name to invoke the static method.
    
Example: 

Comparator interface has been enhanced with static method 

 * comparing      -> specify custom comparator using lambda expresssion
 * comparingInt
 * naturalOrder
 
e.g.  `myDeck.sort(Comparator.comparing(Card::getRank));`

Example:
{% highlight java linenos %}       
interface One {
  void doIt();

  default void doSomething() {
    System.out.println("I - One: Do something");
    // Default can invoke static methods.
    sayHello();
  }

  static void sayHello() {
    System.out.println("I - One: SayHello");
    // Cant call default methods from static context
    // doSomething();
  }
}
static class OneTest implements One {

  @Override
  public void doIt() {
    System.out.println("C - OneTest: Do it");
  }

}
public static void main(String[] args) {
  One one = new OneTest();
  one.doIt();
  one.doSomething();

  // Can use only Interface One to call sayHello()
  One.sayHello();
}
{% endhighlight %}  

:key: Output

    C - OneTest: Do it
    C - OneTest: Do something
    I - One: SayHello
    I - One: SayHello
    
### 7.2. Define and use a default method of a interface; Describe the inheritance rules for a default method 

Default methods enable you to add new functionality to the interfaces of your libraries and 
ensure binary compatibility with code written for older versions of those interfaces.

- Lets you add new methods without breaking classes that already implement this interface.
- You add a default implementation for this method though.
- You still need a Class instance to call the default methods.
- You can call Interface static method from it
- You can also call other instance methods (abstract ones) from it. (Since you need an instance to call the default method, the abstract methods will be implemented)
  
When you extend an interface that contains a default method, you can do the following:

- Not mention the default method at all, which lets your extended interface inherit the default method.
- Redeclare the default method, which makes it abstract. i.e. redeclare it but without the implementation. :fire:
- Redefine the default method, which overrides it. i.e Specify some other implementation for the default method.
  
:boom: You can define an Interface with all default methods? Yes, :fire: One or more or all the methods can be default
 
e.g: Comparator interface has been enhanced with default method 

 * thenComparing
 * thenComparingDouble  to chain Comparators.
 
Example:
{% highlight java linenos %}   
public class DefaultDemo {

  @FunctionalInterface
  interface One {
    void doIt();

    public default void doSomething() {
      System.out.println("I - One: Do something");
    }

    default void doSomething2() {
      System.out.println("I - One: Do something2");
    }
  }

  // Two is NOT a @FunctionalInterface -> Two SAMs
  interface Two extends One {
    // Redefined it now
    default void doSomething() {
      System.out.println("I - Two: Do something");
    }

    // doSomething2 is now abstract..you cant get the default impl now
    void doSomething2();
  }

  static class OneTest implements One {

    @Override
    public void doIt() {
      System.out.println("C - OneTest: Do it");
    }

    @Override
    public void doSomething() {
      System.out.println("C - OneTest: Do something");
    }

  }

  static class TwoTest implements Two {

    @Override
    public void doIt() {
      System.out.println("C - TwoTest: Do it");
    }

    @Override
    public void doSomething2() {
      System.out.println("C - TwoTest: Do something");
    }
  }

  public static void main(String[] args) {
    One one = new OneTest();
    one.doIt();
    one.doSomething();
    one.doSomething2();

    Two two = new TwoTest();
    two.doIt();
    two.doSomething();
    two.doSomething2();
  }
}
{% endhighlight %}  

:key: Output

    C - OneTest: Do it
    C - OneTest: Do something
    I - One: Do something2
    C - TwoTest: Do it
    I - Two: Do something
    C - TwoTest: Do something

[See Also ](https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html)

--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part7)

--------------------------------	    
[Next Chapter 8. Use Java SE 8 Date/Time API](chapter8.html)

--------------------------------
