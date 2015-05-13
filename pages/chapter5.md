---
layout: page
title: Chapter 5. Parallel Streams
description: 1Z0-810 Java SE8
comments: true
---

### Chp 5.1: Develop the code that use parallel streams

Aggregate operations and parallel streams enable you to implement parallelism with non-thread-safe collections provided 
that you do not modify the collection while you are operating on it.

Use `.parallelStream()` instead of `.stream()` or use `.stream().parallel()`

use `.sequential()` method on a stream to convert a parallel stream into a sequential stream

Parallel streams uses the `fork/join` framework that was added in Java 7.
When a stream executes in parallel, the Java runtime partitions the stream into multiple substreams. 
Aggregate operations iterate over and process these substreams in parallel and then combine the results.
{% highlight java %}  
ConcurrentMap<Person.Sex, List<Person>> byGender =
    roster
        .parallelStream()
        .collect(
          Collectors.groupingByConcurrent(Person::getGender));
{% endhighlight %} 

> Differences:

  - Uses ConcurrentMap instead of Map
  - Uses groupingByConcurrent instead of groupingBy
  - the operation Collectors.toConcurrentMap performs better with parallel streams than the operation Collectors.toMap.

[See Also](http://docs.oracle.com/javase/tutorial/collections/streams/parallelism.html)

###### Ordering: Pipeline methods
The ordering will be totally random here incase of parallel streams.

{% highlight java %} 
 listOfIntegers
  .parallelStream()
  .forEach(e -> System.out.print(e + " "));
{% endhighlight %} 

- `forEachOrdered`-> processes the elements of the stream in the order specified by its source. You will loose the benefits of parallelism.

{% highlight java %} 
 listOfIntegers
  .parallelStream()
  .forEachOrdered(e -> System.out.print(e + " "));
{% endhighlight %} 

:point_right: Interference
  - Lambda expressions in stream operations should not interfere. 
    Interference occurs when the source of a stream is modified while a pipeline processes the stream.

:point_right: Stateful Lambda Expressions
  - Avoid using stateful lambda expressions as parameters in stream operations. 
  - A stateful lambda expression is one whose result depends on any state that might change during the execution of a pipeline
  
{% highlight java %}  
  List<Integer> serialStorage = new ArrayList<>();
  serialStorage
    .stream()
    // Don't do this! It uses a stateful lambda expression.
    .map(e -> { serialStorage.add(e); return e; }) // Throws ConcurrentModificationException
    .forEachOrdered(e -> System.out.print(e + " "));
{% endhighlight %} 

:point_right: Make sure you use a concurrent version or synchronized version of the Map/List for parallel stream when you add to it.
otherwise, you'll get incorrect results since multiple threads access and modify the collection.

### Chap 5.2    Implement decomposition, reduction, in streams

#### Reduction Operations:
- Reduce to one value or groups of values
- Many terminal operations (such as average, sum, min, max, and count) return one value by combining the contents of a stream. 
- Some terminal operations (such as reduce and collect) return a collection by finding the average of values or grouping elements into categories.

##### Stream.reduce

- `identity`:  initial value of the reduction or the default result if no elements
-  `accumulator`:  a partial result of the reduction and the next element of the stream 
  
:arrow_right: `reduce(BinaryOperator<T> accumulator)`

Performs a reduction on the elements of this stream, using an associative accumulation function, and returns an Optional describing the reduced value, if any.

:arrow_right: `T 	reduce(T identity, BinaryOperator<T> accumulator)`

Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function, and returns the reduced value.

:arrow_right: `U> U 	reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)`

Performs a reduction on the elements of this stream, using the provided identity, accumulation and combining functions.
 
:fire: 
:point_right:  The reduce operation always returns a **new** value. The accumulator function also returns a new value every time it processes an element of a stream

Example1:
{% highlight java linenos %}    
static void reduce(List<Person> roster) {
  Integer totalAge = roster
      .stream()
      .mapToInt(Person::getAge)
      .sum();
  System.out.println("TotalAge: " + totalAge);

  Integer totalAgeReduce = roster
      .stream()
      .map(Person::getAge)
      .reduce(
          0,
          (a, b) -> a + b);
  System.out.println("TotalAge: " + totalAgeReduce);
}
{% endhighlight %}

Example2:  
{% highlight java linenos %}   
static class Averager implements IntConsumer {
  private int total = 0;
  private int count = 0;

  public double average() {
    return count > 0 ? ((double) total) / count : 0;
  }

  public void accept(int i) {
    total += i;
    count++;
  }

  public void combine(Averager other) {
    total += other.total;
    count += other.count;
  }
}

static void collect(List<Person> roster) {
  Averager averageCollect = roster.stream()
      .map(Person::getAge)
      .collect(Averager::new, Averager::accept, Averager::combine);
  System.out.println("Average age of all members: " + averageCollect.average());
}

public static void main(String[] args) {  
 List<Person> persons = Arrays.asList(new Person("Jane", 25, false), new Person("Alice", 28, false),
    new Person("Bob", 42, true), new Person("Tina", 19, false));
 collect(persons);
}
{% endhighlight %}
:key: Output
     
     Average age of all members: 28.5
            
##### Stream.collect
Unlike the reduce method, which always creates a new value when it processes an element, the collect method modifies, or mutates, an existing value.
  
:arrow_right:  `<R,A> R 	collect(Collector<? super T,A,R> collector)`
 
Performs a mutable reduction operation on the elements of this stream using a Collector.

:arrow_right: `<R> R 	collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)`

Performs a mutable reduction operation on the elements of this stream.
  
The collect operation is best suited for collections. 
{% highlight java %} 
.collect(Collectors.groupingBy(Person::getGender)); -> returns Map<Gender, List<Person>>
.collect(Collectors.groupingBy(
                               Person::getGender,
                              Collectors.averagingInt(Person::getAge)));
{% endhighlight %} 

   
Example:  
{% highlight java linenos %}  
Map<Person.Sex, Integer> totalAgeByGender =
        persons
            .stream()
            .collect(
                Collectors.groupingBy(
                    Person::getGender,
                    Collectors.reducing(
                        0,
                        Person::getAge,
                        Integer::sum)));
System.out.println("TotalAgeByGender: " + totalAgeByGender);
    
>> TotalAgeByGender: {FEMALE=72, MALE=42} // for input above.
{% endhighlight %}
    
:fire: Know all the above methods very well - the input arguments and the return types.

[See Also](http://docs.oracle.com/javase/tutorial/collections/streams/reduction.html)   

--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part5)

--------------------------------	    
[Next Chapter - Lambda Cookbook](chapter6.html)

--------------------------------  