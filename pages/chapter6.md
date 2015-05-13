---
layout: page
title: Chapter 6 Lambda Cookbook
description: 1Z0-810 Java SE8
comments: true
---

### 6.1: Develop the code that use Java SE 8 collection improvements: Collection.removeIf, List.replaceAll, Map.computeIfAbsent/Present, Map.forEach

> Java 8 Changes

##### `java.util.Collection<E>` Interface changes
{% highlight java %}    
default boolean removeIf(Predicate<? super E> filter)
 - Removes all of the elements of this collection that satisfy the given predicate.

default Stream<E> stream()
 - Returns a sequential Stream with this collection as its source. 

default Stream<E> parallelStream()
 - Returns a possibly parallel Stream with this collection as its source.
   It is allowable for this method to return a sequential stream. 
{% endhighlight %}

##### `java.util.List<E>` Interface changes
{% highlight java %}  
default void replaceAll(UnaryOperator<E> operator)
 - Replaces each element of this list with the result of applying the operator to that element. 
   it mutates the elements of the List. 

default void sort(Comparator<? super E> c)
 - Sorts this list according to the order induced by the specified Comparator.
   If the specified comparator is null then all elements in this list will be sorted by
   natural order, elems must implement the Comparable interface
 {% endhighlight %}
 
##### `java.util.Iterator<E>` Interface changes
{% highlight java %}  
default void forEachRemaining(Consumer<? super E> action)
 - Performs the given action for each remaining element until all elements have been processed 
 {% endhighlight %}
 
##### java,util.Comparator<T> Interface changes  
{% highlight java %}     
default Comparator<T> reversed()
 - Returns a comparator that imposes the reverse ordering of this comparator.
{% endhighlight %}
 
##### java.util.Map
{% highlight java %}  

default void forEach(BiConsumer<? super K,? super V> action)
 - Performs the given action for each entry in this map 

default void replaceAll(BiFunction<? super K,? super V,? extends V> function)
 - Replaces each entry's value with the result of invoking the given function on that entry

default V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)    
 - If there is no value for the key, then it computes its value using the given mapping function 
   and inserts it if the value!=null

default V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)   
 - If the value for the specified key is present and non-null, attempts to compute a new mapping 
   given the key and its current mapped value. 
   If the function returns null, the mapping is removed.

default V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)
 - Computes a mapping for the specified key and its current mapped value
{% endhighlight %}
	
:sweat_drops: Section 6.2 is soon after Section 6.3: 
    
### 6.3: Use merge, flatMap methods on a collection

##### `java.util.Map<K, V>`
{% highlight java %}  
default V merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction) 
 - If there is no value for this key, use value, if there is, pass to func and assign that 
   value to the key. If new value is null, removes the key from map
{% endhighlight %}	

e.g. To either create or append a String msg to a value mapping: 
    
     map.merge(key, msg, String::concat)

##### [java.util.stream.Stream](http://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html)
{% highlight java %}	
<R> Stream<R>  flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
 - Returns a stream consisting of the results of replacing each element of this stream with the 
   contents of a mapped stream 
{% endhighlight %}		
Example:

`.flatMap(e -> Stream.of(e.split(" ")))` -> Returns a `stream of R` instead of just R
{% highlight java linenos %}  
    List<String> list = Arrays.asList("Zello Some", "New Some", "Thing New", "zGood");
    List<String> list2 = list.stream()
        .flatMap(e -> Stream.of(e.split(" ")))
        .peek(s -> System.out.println(s + " "))
        .distinct()
        .sorted()
        .collect(Collectors.toList());
    System.out.println("Unique words = " + list2);
{% endhighlight %}
:key: Output

    Zello 
    Some 
    New 
    Some 
    Thing 
    New 
    zGood 
    Unique words = [New, Some, Thing, Zello, zGood]
        
:point_right: Note the difference with map method :fire:

 `<R> Stream<R>   map(Function<? super T, ? extends R> mapper)`
  
 - Both map and flatMap take in a Function <T, R>
 - flatMap R -> Stream<? extends R>  whereas map  R -> R
 - flatMap is useful when you want to split the incoming stream elements into another stream of same type.

### 6.2 Read files using lambda improvements: Files.find, lines(), walk()

Streams have a `BaseStream.close()` method and implement `AutoCloseable`, but nearly all stream instances do not actually need to be closed after use. 

Generally, only streams whose source is an IO channel (such as those returned by `Files.lines(Path, Charset))` will require closing
Most streams are backed by collections, arrays, or generating functions, require no special closing.

The below methods opens up some of other file resource and should be used with the try-with-resources to ensure that the stream's close method is invoked. :fire:
{% highlight java %} 
public static Stream<Path> list(Path dir) throws IOException
 - Return a lazily populated Stream, the elements of which are the entries in the directory. 
   The listing is not recursive.
{% endhighlight %}
  
#### walk:
{% highlight java %} 	
public static Stream<Path> walk(Path start,
                              int maxDepth,
                              FileVisitOption... options) throws IOException
public static Stream<Path> walk(Path start,
                              FileVisitOption... options) throws IOException 
 - Return a Stream that is lazily populated with Path by walking the file tree rooted at a given 
   starting file. The file tree is traversed depth-first, the elements in the stream are Path 
   objects that are obtained as if by resolving the relative path against start. 
{% endhighlight %}
   
#### find:
{% highlight java %} 
public static Stream<Path> find(Path start,
                              int maxDepth,
                              BiPredicate<Path,BasicFileAttributes> matcher,
                              FileVisitOption... options) throws IOException
 - Return a Stream that is lazily populated with Path by searching for files in a file tree 
   rooted at a given starting file. 
{% endhighlight %}
 	
#### lines
{% highlight java %} 
public static Stream<String> lines(Path path, Charset cs) throws IOException  
public static Stream<String> lines(Path path) throws IOException    -> Uses UTF-8 charset                               
 - Read all lines from a file as a Stream. Unlike readAllLines, this method does not read
   all lines into a List, but instead populates lazily as the stream is consumed.
{% endhighlight %}
 	
The returned stream encapsulates a Reader so this should be called with try-with-resources.

[See Also java.nio.file.Files](http://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html)

### 6.4: Describe other stream sources: Arrays.stream(), IntStream.range()

##### Arrays:
{% highlight java %}   
public static <T> Stream<T> stream(T[] array)
public static <T> Stream<T> stream(T[] array, int startInclusive, int endExclusive)
 - Returns a sequential Stream with the specified array as its source.

public static IntStream stream(int[] array)
 - Returns a sequential IntStream with the specified array as its source.

public static LongStream stream(long[] array)
 - Returns a sequential LongStream with the specified array as its source.

public static DoubleStream stream(double[] array)
 - Returns a sequential DoubleStream with the specified array as its source
{% endhighlight %}
    
Example:
{% highlight java linenos %}          
String[] strArray = {"A", "B", "PC", "D", "PM"};
Arrays.stream(strArray)
    .filter(s -> s.startsWith("P"))
    .forEach(System.out::println);

long[] longArr = {1L, 2L, 3L, 5L, 10L};
LongStream longStream = Arrays.stream(longArr);
System.out.println("Sum = " + longStream.sum());

Output: 
PC
PM
Sum = 21
{% endhighlight %}
        
##### IntStream:
{% highlight java %} 	
static IntStream 	range(int startInclusive, int endExclusive)
 - Returns range from start to end-1 

static IntStream 	rangeClosed(int startInclusive, int endInclusive)
 - Returns range from start to end - Both inclusive

Stream<Integer> 	boxed()
 - Returns a Stream consisting of the elements of this stream, each boxed to an Integer.
 {% endhighlight %}  
 
Example: 
{% highlight java linenos %}        
IntStream intStream = IntStream.range(1, 20);
intStream.forEach(i -> System.out.print(i + " "));
System.out.println("\nAverage: " + IntStream.range(1, 20).average());

// Note it includes numbers upto 20
IntStream intStream2 = IntStream.rangeClosed(1, 20);
intStream2.forEach(i -> System.out.print(i + " "));

Output:
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 
Average: OptionalDouble[10.0]
1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 

{% endhighlight %}    
          
##### IntSummaryStatistics (not mentioned for exam)
A state object for collecting statistics such as count, min, max, sum, and average. 
{% highlight java %} 
IntSummaryStatistics stats = people.stream()
                                .collect(Collectors.summarizingInt(Person::getDependents));
{% endhighlight %}                                    

--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part6)

--------------------------------	    
[Next Chapter 7. Method Enhancements](chapter7.html)

--------------------------------