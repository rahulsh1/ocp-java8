---
layout: page
title: Chapter 2. Using Built in Lambda Types
description: 1Z0-810 Java SE8
comments: true
---

### 2.1.  Describe the built in interfaces included in Java 8 - java.util.function package 

>New Package: `java.util.function` -  Contains a lot of commonly used functional interfaces :fire:
  
##### java.util.function.Predicate  
Represents a predicate (boolean-valued function) of one argument.
{% highlight java %} 
public interface Predicate<T extends Object> {
  boolean test(T t);
}
{% endhighlight %}

##### java.util.function.Consumer
Represents an operation that accepts a single input argument and returns no result. 

{% highlight java %} 
public interface Consumer<T extends Object> {
  void accept(T t);
}
{% endhighlight %}

##### java.util.function.Function
Represents a function that accepts one argument and produces a result. 

{% highlight java %} 
public interface Function<T extends Object, R extends Object> {
  R apply(T t);
}
{% endhighlight %}

##### java.util.function.Supplier
Represents a supplier of results. There is no requirement that a new or distinct result be returned each time the supplier is invoked. 

{% highlight java %} 
public interface Supplier<T extends Object> {
  T get();
}
{% endhighlight %}
  
##### java.util.function.UnaryOperator 
Represents an operation on a single operand that produces a result of the same type as its operand. This is a specialization of Function for the case where the operand and result are of the same type. 

{% highlight java %} 
public interface UnaryOperator<T extends Object> extends Function<T, T> {
}
{% endhighlight %}
    
:point_right: Know the above interfaces very well, the inputs as well as the output. :fire:


### 2.2.  Develop code that uses Function interface 

{% highlight java %} 
public interface Function<T extends Object, R extends Object> {
  R apply(T t);
}
{% endhighlight %}

Example:
{% highlight java linenos %} 

Function<String, String> f = s -> new Boolean(s);

System.out.println(f.apply("TRUE"));
System.out.println(f.apply("true"));
System.out.println(f.apply("Java8"));
System.out.println(f.apply(null));
{% endhighlight %}

### 2.3.  Develop code that uses Consumer interface 

{% highlight java %} 
public interface Consumer<T extends Object> {
  public void accept(T t);
}
{% endhighlight %}

Example:
{% highlight java linenos %} 	
public class ConsumerDemo {

  static class Name {
    String name;

    Name(String nm) {
      name = nm;
    }

    void setName(String nm) {
      name = nm;
    }
    public String toString() {
      return name;
    }
  }
  public static void processList(List<Name> names, Consumer<Name> consumer) {
    for (Name n : names) {
      consumer.accept(n);
    }
  }

  public static void main(String[] args) {
    List<Name> list = Arrays.asList(new Name("a"), new Name("b"), new Name("c"), new Name("d"));
    System.out.println(list);
    Consumer<Name> capitalize = s -> s.setName(s.name.toUpperCase());

    processList(list, capitalize);
    System.out.println(list);
  }
}
{% endhighlight %}

### 2.4.  Develop code that uses Supplier interface 
{% highlight java %} 
public interface Supplier<T extends Object> {
  T get();
}
{% endhighlight %}
Example: 
Constructor reference (T::new)
  - Much like a method reference, except it is a reference to a constructor instead of a method
  - Constructor is not called just referenced.
  - On get, the constructor will be called.
  
{% highlight java linenos %} 	
Supplier<String> sup = String::new;
System.out.println(sup); // com.jcp8.chapter.two.SupplierDemo$$Lambda$2/990368553@41629346
System.out.println(sup.get()); // ""
{% endhighlight %}


### 2.5.  Develop code that uses UnaryOperator interface 

{% highlight java %} 	
public interface UnaryOperator<T extends Object> extends Function<T, T> {
}
{% endhighlight %}
Example:

{% highlight java linenos %} 
UnaryOperator<String> uo = s -> "Have a " +  s;
System.out.print(uo.apply("good one"));
{% endhighlight %}

### 2.6.  Develop code that uses Predicate interface 
{% highlight java  %} 
public interface Predicate<T extends Object> {
  boolean test(T t);
}
{% endhighlight %}    

Example:
{% highlight java linenos %} 
Predicate<String> boolTest = s -> new Boolean(s).booleanValue();

List<String> strList = Arrays.asList("TRUE", "true", null, "", "false", "TrUe");
processList(strList, boolTest);

private static void processList(List<String> list, Predicate<String> predicate) {
  for (String s : list) {
    System.out.println(s + " Test pass? " + predicate.test(s));
  }
}
{% endhighlight %}    
### 2.7.  Develop the code that use primitive and binary variations of base interfaces of java.util.function package 

A specialized version of the functional interfaces in order to avoid autoboxing operations when the inputs or outputs are primitives.
{% highlight java linenos %} 
@FunctionalInterface
public interface IntPredicate {
  boolean test(int i);
}
{% endhighlight %}

#### Mapping Table
You should know by just the Interface name, what parameter they accept and what they return.

> How to remember :question:

`Intxx` -> Will take int as an argument and return T or nothing depending on type

`ToIntxx` -> Will return int, and take in T as arg if it applies

 
| `Predicate<T>`  | `Consumer<T>`      |  `Function<T, R>` | `Supplier<T>`     | `UnaryOperator<T>` |
| ------------- | -------------    |-----------------|-----------------| -----------------|
| IntPredicate  | IntConsumer      | IntFunction<R>  | IntSupplier    |  IntUnaryOperator |    
| LongPredicate  | LongConsumer    | LongFunction<R>  | LongSupplier   | LongUnaryOperator|
| DoublePredicate | DoubleConsumer | DoubleFunction<R>| DoubleSupplier | DoubleUnaryOperator|
| -               | -              | -                | BooleanSupplier | -    |

##### Additional Function<T, R>

`IntToDoubleFunction` - Function that accepts an int-valued argument and produces a double-valued result.

`IntToLongFunction` - Function that accepts an int-valued argument and produces a long-valued result.

`LongToDoubleFunction` - Function that accepts a long-valued argument and produces a double-valued result.

`LongToIntFunction` - Function that accepts a long-valued argument and produces an int-valued result.

`ToIntFunction<T>` - Function that produces an int-valued result.

`ToDoubleFunction<T>` - Function that produces a double-valued result.

`ToLongFunction<T>` - Function that produces a long-valued result.

#### Binary Interfaces - Takes two inputs instead of one

|     |  `Predicate<T>`  | `Consumer<T>`      |  `Function<T, R>` | `Supplier<T>`     | `UnaryOperator<T>` |
|-----| ------------- | -------------    |-----------------|-----------------|-------------------|
| Type| BiPredicate<L, R>  | BiConsumer<T, U>  |BiFunction<T, U, R>  | -      | BinaryOperator<T> |
|Params| (L, R) -> boolean  | (T, U) -> void    | (T, U) -> R       | -      | (T, T) -> T       |

##### Additional Ones 
> Binary Operators

`IntBinaryOperator` - Function operation upon two int-valued operands and producing an int-valued result.

`LongBinaryOperator` - Function operation upon two long-valued operands and producing a long-valued result.

`DoubleBinaryOperator` - Function operation upon two double-valued operands and producing a double-valued result.

> Object Consumers

`ObjIntConsumer<T>` - Function operation that accepts an Object-valued and an int-valued argument and returns no result.

`ObjLongConsumer<T>` - Function operation that accepts an Object-valued and a long-valued argument and returns no result.

`ObjDoubleConsumer<T>` - Function operation that accepts an Object-valued and a double-valued argument and returns no result.
  
> To Primitive Bi-Functions

`ToIntBiFunction<T, U>` - Function that accepts two arguments and produces an int-valued result.

`ToLongBiFunction<T, U>` - Function that accepts two arguments and produces a long-valued result.

`ToDoubleBiFunction<T, U>` - Function that accepts two arguments and produces a double-valued result. 

:point_right: You have to know each of the above types for the exam, with their arguments and return types. :fire:

### 2.8.  Develop the code that use method reference; including refactor the code that use Lambda expression to use method references 

##### Method References

 - shorthand for lambdas calling only a specific method
 - the target reference is placed before the delimiter :: and the name of the method is provided after it.
 - String::toUpperCase is a method reference to the method toUpperCase defined in the String class, shorthand for the lambda expression (String s) -> s.toUpperCase(); 
 
Four Types:

**ContainingClass::staticMethodName**     - Reference to a static method                            
**ContainingObject::instanceMethodName**  - Reference to an instance method of a particular object  
**ContainingType::methodName**            - Reference to an instance method of an arbitrary object of a particular type   
**ClassName::new**                        - Reference to a constructor 

 
###### Reference to a static method - `Class::staticMethodName`
{% highlight java  %}   
IntFunction<String> f1 = (i) -> String.valueOf(i);
System.out.println(f1.apply(100));

IntFunction<String> f1 = String::valueOf;
System.out.println(f1.apply(100));
{% endhighlight %} 
  
###### Reference to a constructor - `ClassName::new`
{% highlight java  %}   
Function<char[], String> f1 = String::new;
System.out.println(f1.apply(new char[] {'H', 'i'}));
{% endhighlight %} 
   
###### Reference to an instance method of an arbitrary object of a particular type - `Class::instanceMethodName`
   
Reference to an instance method of an arbitrary object of a particular type refers to a non-static method that is not bound to a receiver.
{% highlight java  %}   
String::trim // (String str) -> { return str.trim(); }. 
BiFunction<String, String, Boolean> f1 = String::equalsIgnoreCase;
System.out.println(f1.apply("Hello", "HELLO"))
{% endhighlight %} 
   
###### Reference to an instance method of a particular object - `Object::instanceMethodName`
   
Reference to an instance method of a particular object refers to a non-static method that is bound to a receiver. 

This kind of method references refers to a situation when you are calling a method in a lambda to an external object that already exists
{% highlight java  %}   
Integer i = new Integer(1);   
Supplier<String> f1 = i::toString;
System.out.println(f1.get());
{% endhighlight %}

:bulb: Know when to use which type of method reference.
 
--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part2)
 
--------------------------------	    
[Next Chapter - Filtering Collections with Lambdas](chapter3.html)

--------------------------------  