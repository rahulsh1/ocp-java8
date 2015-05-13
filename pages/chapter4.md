---
layout: page
title: Chapter 4. Collection Operations with Lambda
description: 1Z0-810 Java SE8
comments: true
---

### 4.1.  Develop the code to extract data from an object using map 

Applying a function to each element of a stream
  
Streams support the method `map()`, which takes a `Function` as argument.

{% highlight java  %} 
Stream<Employee> emps = Stream.of(new Employee("Alice"), new Employee("Bob"),
                                new Employee("Jane"));
List<String> staff = emps.map(Employee::getName);
                         .collect(Collectors.toList());
System.out.println(staff);
{% endhighlight %} 

##### Primitive stream specializations
Java 8 introduces three primitive specialized stream interfaces that support specialized methods (like max(), sum(), average()) 
to work with streams of numbers: `IntStream` (rather than a `Stream<Integer>`), `DoubleStream`, and `LongStream`.
  
{% highlight java linenos %} 
 
IntStream istream = names.mapToInt(s -> s.length());
System.out.println("Max: " + istream.max().getAsInt());

// stream has already been operated upon or closed 
// following if uncommented will throw an Exception
//  System.out.println("Total: " + istream.count());
{% endhighlight %} 

### 4.2.  Search for data using search methods including: findFirst, findAny, anyMatch, allMatch, noneMatch.

##### Methods on Stream

* `findFirst` - Finding the first element
    
      Optional<T> findFirst();


* `findAny`  - Finding an element - findAny
  Gives a more flexible alternative to findFirst(). Can be used in a parallel stream.
  
      Optional<T> findAny();

  
* `anyMatch` - Checking to see if a predicate matches at least one element
  Is there an element in the stream matching the given predicate?
 
      boolean anyMatch(Predicate<? super T> predicate);
  
* `allMatch` - Checking to see if a predicate matches all elements

      boolean allMatch(Predicate<? super T> predicate);

* `noneMatch` - Checking to see if a predicate does not matche any element -   

      boolean noneMatch(Predicate<? super T> predicate);  
  
:fire: Remember the method signatures: 

:point_right: All `findXxx()` methods have no arguments and return `Optional`.

:point_right: All `xxxMatch(...)` methods accept a `Predicate` and return a `boolean` primitive. 
    
All the above are `short circuit` operations. If results match or dont match, they will come out without processing the whole stream.

{% highlight java linenos %} 
private static void findFirst() {
  List<Employee> employees = Arrays.asList(new Employee("Jack"), new Employee("Jill"), new Employee("Jiane"));
  Stream<Employee> emps = employees.stream();

  Optional<Employee> result = emps.filter(s -> s.getName().contains("i")).findFirst();

  System.out.println("findFirst Result = " + result.get());
}

private static void allMatch() {
  List<Employee> employees = Arrays.asList(new Employee("Jack"), new Employee("Jill"), new Employee("Jiane"));
  Stream<Employee> emps = employees.stream();

  boolean result = emps.allMatch(s -> s.getName().startsWith("J"));

  System.out.println("allMatch Result = " + result);
}
private static void noneMatch() {
  List<Employee> employees = Arrays.asList(new Employee("Jack"), new Employee("Jill"), new Employee("Jiane"));
  Stream<Employee> emps = employees.stream();

  boolean result = emps.noneMatch(s -> s.getName().startsWith("K"));

  System.out.println("noneMatch Result = " + result);
}  
{% endhighlight %} 

:key: Output
    
      findFirst Result = Jill
      allMatch Result = true
      noneMatch Result = true
    
    
### 4.3.  Describe the unique characteristics of the Optional classes 

A `java.util.Optional<T>` object is either a wrapper for an Object of type T or a wrapper for no object.

##### Creation
{% highlight java %} 
Optional<String> str = Optional.empty();

Optional<String> optStr = Optional.of("Hello");

Optional<String> optStr = Optional.ofNullable(null);   -> Optional.empty()
{% endhighlight %} 

The Optional class provides several instance methods to read the value contained by an Optional instance.

`Optional.get()` 
is the simplest but also the least safe of these methods.
It returns the wrapped value if present but throws a `NoSuchElementException` otherwise. :fire:
For this reason, using this method is almost always a bad idea unless you are really sure the optional contains a value.

`Optional.orElse(T other)`
it allows you to provide a default value for when the optional does not contain a value.
NOTE: the other value may be null.

`Optional.orElseGet(Supplier<? extends T> other)`
is the lazy counterpart of the orElse method, 
because the supplier is invoked only if the optional contains no value.
You should use this method either when the default value is time-consuming to create or you want to be sure this is done only
if the optional is empty.

`Optional.orElseThrow(Supplier<? extends X> exceptionSupplier)` 
is similar to the get() method in that it throws an exception 
when the optional is empty, but in this case it allows you to choose the type of exception that you want to throw.

`Optional.ifPresent(Consumer<? super T> consumer)` 
lets you execute the action given as argument if a value is present; otherwise no action is taken.

`Optional.isPresent()` returns true if the Optional contains a value, false otherwise. 

{% highlight java linenos %}
public static void main(String[] args) {
  Optional<String> opt1 = Optional.of("Hello");
  String s = opt1.get();

  System.out.println(opt1.isPresent() + " " + s);

  Optional<String> opt2 = Optional.empty();
  System.out.println("Value or default: " + opt2.orElse("Default"));

  // With Supplier
  System.out.println("Value or default: " + opt2.orElseGet(() -> "Some default"));

  // Print only if opt1 has a value, Takes in a Consumer
  opt1.ifPresent(System.out::println);

  // Throws NPE
  Optional<String> optNull = Optional.of(null);
}
{% endhighlight %} 
  
:key: Output
    
      true Hello
      Value or default: Default
      Value or default: Some default
      Hello
      Exception in thread "main" java.lang.NullPointerException  
      
### 4.4.  Perform calculations using methods: count, max, min, average, sum

##### Stream Data Methods
  `Stream.min(...) and Stream.max(...) `
  
  `Optional<T> min(Comparator<? super T> comp);`
  
  `Optional<T> max(Comparator<? super T> comp);`

  `Stream.count()`
  
  `long count()`
 
:point_right: Note that `min` and `max` return an `Optional`. Also `average` and `sum` apply to primitive streams only :fire:
 
##### Primitive streams
  `IntStream`    - also used for  short, char, byte, and boolean
  
  `LongStream`,  and 
  
  `DoubleStream`  also for float
  
:point_right: Stream of objects can be mapped using `mapToInt`, `mapToLong`, or `mapToDouble` methods. :fire:

{% highlight java linenos %}  
private static void primitiveStreams() {
  List<Integer> list = Arrays.asList(20, 2, 72, 991, 100, -11);

  IntStream is = list.stream().mapToInt(t -> t);
  System.out.println("Max = " + is.max());

  IntStream is2 = list.stream().mapToInt(t -> t);
  System.out.println("Min = " + is2.min());

  IntStream is3 = list.stream().mapToInt(t -> t);
  System.out.println("Sum = " + is3.sum());

  IntStream is4 = list.stream().mapToInt(t -> t);
  System.out.println("Avg = " + is4.average().getAsDouble());
}
{% endhighlight %} 
    
:key: Output
        
    Max = OptionalInt[991]
    Min = OptionalInt[-11]
    Sum = 1174
    Avg = 195.66666666666666
    
### 4.5.  Sort a collection using lambda expressions 

`sorted()`  - This is a stateful intermediate operation.

Sorts the input stream that has elements of type Comparable(if not `ClassCastException` is thrown :fire: ) in natural order.
{% highlight java %}       
Stream<T> sorted()
Stream<T> sorted(Comparator<? super T> comparator)
{% endhighlight %} 
Comparators can also be chained using 
 `.sorted(c1.thenComparing(c2))`
 
 where
  
   `Comparator<Employee> c1 = Comparator.comparing(e -> e.name.length());`
   
   `Comparator<Employee> c2 = (e1, e2) -> e1.name.compareTo(e2.name);`
     
### 4.6.  Save results to a collection by using the collect method and Collector class; including methods such as averagingDouble, groupingBy, joining, partitioningBy
 
* `joining` - Returns a Collector that concatenates the input elements into a String
{% highlight java %} 
static Collector<CharSequence,?,String> joining()
 - Returns a Collector that concatenates the input elements into a String, in encounter order.
 
static Collector<CharSequence,?,String> joining(CharSequence delimiter)
 - Returns a Collector that concatenates the input elements, separated by the specified delimiter, in encounter order.
 
static Collector<CharSequence,?,String> joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
 - Returns a Collector that concatenates the input elements, separated by the specified delimiter,
  with the specified prefix and suffix, in encounter order.
 {% endhighlight %}  
 

* `groupingBy` - Create a group of values
{% highlight java %} 
static <T,K> Collector<T,?,Map<K,List<T>>> groupingBy(Function<? super T,? extends K> classifier)
 - Returns a Collector implementing a "group by" operation on input elements of type T,
   grouping elements according to a classification function, and returning the results in a Map.
   
static <T,K,A,D> Collector<T,?,Map<K,D>> groupingBy(Function<? super T,? extends K> classifier,
                                                       Collector<? super T,A,D> downstream)
 - Returns a Collector implementing a cascaded "group by" operation on input elements of type T, 
   grouping elements according to a classification function, and then performing a reduction operation
   on the values associated with a given key using the specified downstream Collector.
   
static <T,K,D,A,M extends Map<K,D>>
Collector<T,?,M> 	groupingBy(Function<? super T,? extends K> classifier,
                               Supplier<M> mapFactory, Collector<? super T,A,D> downstream)
 - Returns a Collector implementing a cascaded "group by" operation on input elements of type T,
   grouping elements according to a classification function, and then performing a reduction operation 
   on the values associated with a given key using the specified downstream Collector.
{% endhighlight %}  
 
Example:
{% highlight java %} 
Map<K, List<T> groupingBy(Function<? super T,? extends K> classifier)`
// Group employees by department
Map<Department, List<Employee>> byDept = emps.collect(Collectors.groupingBy(Employee::getDepartment));
{% endhighlight %} 
  
* `partitioningBy`:  Returns a Collector which partitions the input elements according to a Predicate, and organizes them into a Map<Boolean, List<T>>.

{% highlight java %} 
static <T> Collector<T,?,Map<Boolean,List<T>>> 	partitioningBy(Predicate<? super T> predicate)
 - Returns a Collector which partitions the input elements according to a Predicate, 
   and organizes them into a Map<Boolean, List<T>>.
   
static <T,D,A> Collector<T,?,Map<Boolean,D>> 	partitioningBy(Predicate<? super T> predicate, 
                                                         Collector<? super T,A,D> downstream)
 - Returns a Collector which partitions the input elements according to a Predicate, 
   reduces the values in each partition according to another Collector, and organizes them 
   into a Map<Boolean, D> whose values are the result of the downstream reduction.
{% endhighlight %} 
  
Example:
{% highlight java %} 
Map<Boolean,List<T>>>   partitioningBy(Predicate<? super T> predicate)
{% endhighlight %}   
 
* `averagingDouble`:  
{% highlight java %}  
    Double averagingDouble(ToDoubleFunction<? super T> mapper)
{% endhighlight %} 

{% highlight java %} 
static <T> Collector<T,?,Double> 	averagingDouble(ToDoubleFunction<? super T> mapper)
 - Returns a Collector that produces the arithmetic mean of a double-valued function applied to the input elements.
 
static <T> Collector<T,?,Double> 	averagingInt(ToIntFunction<? super T> mapper)
 - Returns a Collector that produces the arithmetic mean of an integer-valued function applied to the input elements.
 
static <T> Collector<T,?,Double> 	averagingLong(ToLongFunction<? super T> mapper)
 - Returns a Collector that produces the arithmetic mean of a long-valued function applied to the input elements.
{% endhighlight %} 

:fire: You should know all the overloaded methods given here.

[See Also](http://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html)

--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part4)

--------------------------------	    
[Next Chapter -  Parallel Streams](chapter5.html)

--------------------------------   
