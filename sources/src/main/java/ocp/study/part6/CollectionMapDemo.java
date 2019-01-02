package ocp.study.part6;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionMapDemo {

  static void collection() {
    // NOTE : We use LinkedList
    // For ArrayList: Exception in thread "main" java.lang.UnsupportedOperationException as list cannot be modified.
//    List<String> list = new ArrayList<>(Arrays.asList("A", "B", "AB", "AC", "Z"));
    List<String> list = new LinkedList<>(Arrays.asList("A", "B", "AB", "AC", "Z"));
    System.out.println("B4:" + list);
    list.removeIf(e -> !e.startsWith("A"));
    System.out.println("After:" + list);
  }

  static void list() {
    List<String> list = new ArrayList<>(Arrays.asList("A", "B", "AB", "AC", "Z"));
    list.replaceAll(e -> e + "Q");
    System.out.println("After Q Append:" + list);
  }

  static void iterator() {
    System.out.println("\nIterator forEachRemaining");
    List<String> list = new ArrayList<>(Arrays.asList("A", "B", "AB", "AC", "Z"));
    list.iterator().forEachRemaining(System.out::println);
  }

  static void map() {
    System.out.println("\nMap operations");
    Map<Integer, String> map = new HashMap<>();
    String val = map.getOrDefault(1, "Default");
    System.out.println("getOrDefault Value = " + val);

    map.put(1, "A");
    map.put(2, "B");
    map.put(3, "C");
    map.put(4, "D");

    map.forEach((k, v) -> System.out.println(k + " -> " + v));

    System.out.println("putIfAbsent = " + map.putIfAbsent(2, "Z"));
    System.out.println("putIfAbsent = " + map.putIfAbsent(8, "Z") + " now map.get(8)= " + map.get(8));

    System.out.println("computeIfPresent = " + map.computeIfPresent(2, (k, v) -> k + "--" + v));

    System.out.println("map merge: " + map.merge(4, "P", String::concat));
    System.out.println("map merge: " + map.merge(6, "P", String::concat));

    System.out.println("map merge: " + map.merge(3, "-Updated", (v1, v2) -> {
      return v1 + v2;
    }));
    System.out.println("map: " + map);
  }

  static void stream() {
    List<String> list = Arrays.asList("Zello Some", "New Some", "Thing New", "zGood");
    List<String> list2 = list.stream()
        .flatMap(e -> Stream.of(e.split(" ")))
        .peek(s -> System.out.println(s + " "))
        .distinct()
        .sorted()
        .collect(Collectors.toList());
    System.out.println("Unique words = " + list2);

    List<String> list3 = list.stream()
        .peek(s -> System.out.println(s))
        .map(s -> s.toUpperCase())
        .collect(Collectors.toList());
    System.out.println(list3);
  }

  public static void main(String[] args) {
    collection();
    list();
    iterator();
    map();
    stream();
  }
}
