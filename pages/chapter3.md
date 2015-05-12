---
layout: page
title: Chapter 3. Filtering Collections with Lambdas
description: 1Z0-810 Java SE8
comments: true
---

### 3.1.  Develop the code that iterates a collection by using forEach; including method chaining 
	
{% highlight java  %}  
public interface Iterable<T extends Object> {

  public Iterator<T> iterator();
  public default void forEach(Consumer<? super T> cnsmr) {
     ...
  }
}
{% endhighlight %} 

`java.util.Collection` interface extends `java.lang.Iterable`, 

Collection Example:
{% highlight java  %}  
List<String> names = new ArrayList<>();
names.add("Alice");
names.add("Bob");
names.add("Smith");

names.forEach(name -> System.out.println(name));
{% endhighlight %} 
`java.util.Map`  also has a *foreach*
{% highlight java  %}  
public interface Map<K extends Object, V extends Object> {

    /**
     * Performs the given action for each entry in this map until all entries have
     * been processed or the action throws an exception.
     */
    public default void forEach(BiConsumer<? super K, ? super V> bc) {
      ...
    }
    ...
}
{% endhighlight %} 

### 3.2.  Describe the Stream interface and pipelines 

Stream is a sequence of elements from a source supporting sequential and parallel aggregate operations.

#### Stream Pipelines

To perform a computation, stream operations are composed into a stream pipeline. :fire: A **stream pipeline** consists of:

 -  **a source** (which might be an array, a Collection, a generator function, an I/O channel, etc.)

 -  **zero or more intermediate operations** (which transform a Stream into another Stream, such as filter(Predicate p))

 -  **a terminal operation** (which produces a result or side-effect, such as count() or forEach(Consumer c)) 

  Streams are *lazy*; computation on the source data is only performed when the terminal operation is initiated, and source elements are consumed only as needed. 


Unlike a collection, a **stream is not a data structure**. It instead carries values from a source through a pipeline. 
 
- `Intermediate operations`: 
  They are always lazy; executing an intermediate operation such as filter() does not actually perform any filtering,
  but instead creates a new stream that, when traversed, contains the elements of the initial stream that match the given predicate. 
  **Traversal of the pipeline source does not begin until the terminal operation of the pipeline is executed. **

- `Terminal operations`: such as Stream.forEach or Stream.count may traverse the stream to produce a result


 
  
##### Intermediate operations: 
All intermediate operations are lazy - no results until terminal operation

:point_right: Intermediate operations are further divided into `stateless` and `stateful` operations. 
Stateless operations, such as filter and map, retain no state from previously seen element when processing a 
 new element -- each element can be processed independently of operations on other elements. 
 
Stateful operations, such as distinct and sorted, may incorporate state from previously seen elements when processing new elements.

:point_right: Stateful operations may need to process the entire input before producing a result. 
For example, one cannot produce any results from sorting a stream until one has seen all elements of the stream. 
As a result, under parallel computation, some pipelines containing stateful intermediate operations may require multiple passes
on the data or may need to buffer significant data. 

Pipelines containing exclusively stateless intermediate operations can be processed in a single pass, 
whether sequential or parallel, with minimal data buffering. 

 - `Stream.filter()`
 
   	Stream<T> filter(Predicate<? super T> predicate);

 - `Stream.map()`
 
   	<R> Stream<R> map(Function<? super T, ? extends R> mapper);
   
 - `Stream.distinct()`

	    Stream<T> distinct();
   
 - `Stream.peek()`
{% highlight java  %}
 Stream<T> peek(Consumer<? super T> action)
{% endhighlight %} 

Since peek returns the stream itself, nothing will happen if you do this: i.e no output
`list.stream().peek(s -> System.out.println(s.toUpperCase()));`

You can add terminal operations like foreach and then you see both peek and foreach output.

##### Terminal Operations
  Stream API operations that returns a result or produce a side effect. 
  Commonly used terminal methods are forEach, toArray, min, max, findFirst, anyMatch, allMatch, etc. 
  You can identify terminal methods from the return type, **they will never return a Stream.** 
 
:point_right: You can operate on a stream only once. :fire: Doing another terminal operation will result in :bulb:

`Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed`


 - `Stream.collect()`
   Stream.collect(...) is a terminal operation to transform the elements of the stream into a different kind of result, e.g. a java.util.List, java.util.Set or java.util.Map:

{% highlight java  %}   
  <R, A> R collect(Collector<? super T, A, R> collector);

  public interface Collector<T, A, R> {
    Supplier<A> supplier();
    BiConsumer<A, T> accumulator();
    BinaryOperator<A> combiner();
    Function<A, R> finisher();
  }
{% endhighlight %}         

Java 8 supports various built-in collectors via the Collectors final class. 
    
 - `Stream.min()` / `Stream.max()`
	   
	   Optional<T> min(Comparator<? super T> comparator);`
	   
	   Optional<T> max(Comparator<? super T> comparator);`
   
 - `Stream.findAny()`
   finds any element in the stream, This is a short circuit operation which stops processing of stream early.
    
        Optional<T> findAny(); 
   
   
 - `Stream.findFirst()`
   
        Optional<T> findFirst();
   
 - `Stream.count()`
             
        long count();

### 3.3.  Filter a collection using lambda expressions 

The `Stream.filter(...)` methods is an intermediate operation.
It filters a stream on the basis of a given predicate and returns a stream object.
And again you can apply other stream methods to this instance. The method syntax is as follows

`Stream<T> filter(Predicate<? super T> predicate)`

### 3.4.  Identify lambda operations that are lazy 

Streams have two types of methods: intermediate and terminal, which work together. 
The secret behind their laziness is that we chain multiple intermediate operations followed by a terminal operation.

Methods like `map()` and `filter()` are intermediate; calls to them return immediately and the lambda expressions provided to them are not evaluated right away

The `filter()` method does not look through all the elements in the collection in one shot. 
Instead, it runs until it finds the first element that satisfies the condition given.
As soon as it finds an element, it passes that to the next method in the chain.
If the terminal operation got what it needed, the computation of the chain terminates. 

:pushpin: What is the output of code below :question:
{% highlight java linenos %}  
Stream<String> words = Stream.of("lower", "case", "text");
List<String> list2 = words
    .peek(s -> System.out.println(s))
    .map(s -> s.toUpperCase());
System.out.println(list2);
{% endhighlight %} 
       
:boom:

> If you guessed, just all those words would be upper case :-1:

> If you guessed, both lower (due to peek) and upper case words :-1:

> If you guessed, no output :clap:

Since there is no terminal operation, there would be no output.

If you add a `foreach(System.out::println)` to above stream at the end, you will see all lower case words first and then all upper case words.

--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part3)

--------------------------------	    
[Next Chapter - Collection Operations with Lambda](chapter4.html)

--------------------------------  