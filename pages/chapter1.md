---
layout: page
title: Chapter 1. Lambda Expressions
description: 1Z0-810 Java SE8
comments: true
---

### 1.1.  Describe Java inner classes and develop the code that uses Java inner classes (such as: nested class, static class, local class and anonymous classes) 

#### Inner Classes

 - nested class 
 - static class
 - local class
 - anonymous class
  

##### 1.1.1 Member Inner Class

 - It has access to all methods, fields, and the Outer's this reference: 
 - Inner class instance must be instantiated with an enclosing instance. 
 - Outer and inner class can directly access each other's fields and methods (even if private.) 
 - `Inner i = new Outer().new Inner();`
 - You cannot declare static initializers or static member interfaces in a inner class unless that are constants.
 
##### 1.1.2 Static Nested Classes

 - No link to an instance of the outer class. 
 - Can only access static fields and static methods of the outer class. 
 - `Outer.Inner n = new Outer.Inner();`

##### 1.1.3 Local classes: 

 - Classes that are defined in a block, which is a group of zero or more statements between balanced curly braces. 
 - You typically find local classes defined in the body of a method: 
 - If method is static, the local inner classes becomes static.
 - A local class can access local variables and parameters of the enclosing block that are final or effectively final.
 - A local class can access its outer classes members just fine (final/not final)
  

###### Static method local inner class

 - Class inside a static method
 - Can only access static members & static methods of outside scope
  
###### Static members
  
 - Local classes are similar to inner classes so they cannot define or declare any static members. :exclamation: See [OuterInnerDemo](../sources/src/ocp/study/part1/OuterInnerDemo.java)
 - You cannot declare an interface inside a block; interfaces are inherently static. 
 
{% highlight java linenos %}
public void saySomething() {
  interface HelloThere {			// Compile failure 
     public void greet();
  }
}
{% endhighlight %}
  
<i class="icon-hand-right"></i> You cannot declare static initializers or member interfaces in a local class. 
    
{% highlight java linenos %}
public void saySomething() {
  class SayHello {
    public static void goodbye() {  // Compile failure
      System.out.println("Bye bye");
    }
  }
  SayHello.goodbye();
}
{% endhighlight %}

{% highlight java linenos %}
public class OuterInnerDemo {
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
{% endhighlight %}

##### 1.1.4 Anonmymous class

 - has access to the members of its enclosing class.
 - cannot access local variables in its enclosing scope that are not declared as final or effectively final.
 - Same rules apply as local class
 
 - Anonymous classes also have the same restrictions as local classes with respect to their members:
   - You cannot declare static initializers or member interfaces in an anonymous class.
   - An anonymous class can have static members provided that they are constant variables.
   
{% highlight java linenos %}
public class InnerClassDemo {

  private int x = 10;
  private static int staticX = 20;
  
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
}
{% endhighlight %}

### 1.2.  Define and write functional interfaces 

##### Functional interfaces

 - provide target types for lambda expressions and method references. 
 - has a single abstract method (SAM), called the functional method for that functional interface, 
   to which the lambda expression's parameter and return types are matched or adapted. 
 - All the existing single method interfaces like `Runnable`, `Callable`, `Comparator`, and `ActionListener` in the JDK 8 are now functional interfaces 
 - `@FunctionalInterface` annotation
   - optional, it is a "hint" for Java compiler similar to `@Override`
   
##### Default methods in interfaces

They are introduced so that you can add new methods in the interface without breaking old code that uses this interface.

 - Methods in interfaces can now have implementation :fire:
 - A default method is an instance method defined in an interface whose method header begins with the default keyword; it also MUST provide a code body. 
 - A static method is a class method  defined in an interface whose method header begins with the static keyword; it also MUST provide a code body.
 - Static methods can be called only with the Interface name i.e cannot refer them by Class name or using an instance
 
{% highlight java linenos %}
public interface SomeInterface {
   default void defdoSome() { /* implementation */ }
   static  void statdoSome() { /* implementation */ }
   void doIt();  // SAM
}

// Calling code:
public static void main(String[] args) {
  SomeInterface myInterface = () -> System.out.println("do something");

  myInterface.defdoSome();
  myInterface.doIt();
  myInterface.statdoSome();
}
{% endhighlight %}


### 1.3.  Describe a Lambda expression; refactor the code that use anonymous inner class to use Lambda expression; including type inference, target typing 

{% highlight java linenos %}
// Pre-Java8 style
button.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        comp.setText("Button has been clicked");
    }
});

// Java8 style
button.addActionListener(e -> comp.setText("Button has been clicked"));
// or
button.addActionListener((ActionEvent e) -> comp.setText("Button has been clicked"));
{% endhighlight %} 

##### Lambda Expressions
  - A comma-separated list of formal parameters enclosed in parentheses. 
  - The arrow token **->**
  - A body, which consists of a single expression or a statement block
  - Remember return statement is NOT an expression so in a lambda expression, you MUST enclose statements in braces ({...}). 
  - You can omit the data type of the parameters in a lambda expression.
  
{% highlight java linenos %}
p -> p.getDept() >= 2
(Person p) -> p.getDept() >= 2
(Person p) -> { return p.getDept() >= 2; }      // Note the {}
	
{% endhighlight %} 

--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part1)

--------------------------------	    
[Chapter2 - Using Built in Lambda Types](chapter2.html)

--------------------------------