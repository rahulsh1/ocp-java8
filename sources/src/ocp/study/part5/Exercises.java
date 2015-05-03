package ocp.study.part5;

import ocp.study.Album;
import ocp.study.Person;

import java.util.*;
import java.util.stream.Collectors;

public class Exercises {

  static void exercise1(List<Person> persons) {

    // Old way
    for (Person p : persons) {
      if (p.getGender() == Person.Sex.MALE) {
        System.out.println(p.getName());
      }
    }

    // Using streams
    persons.stream()
        .filter(p -> p.getGender() == Person.Sex.MALE)
        .map(Person::getName)
        .forEach(System.out::println);
  }

  static void exercise2(List<Album> albums) {
    List<Album> favs = new ArrayList<>();
    for (Album a : albums) {
      boolean hasFavorite = false;
      for (Album.Track t : a.tracks) {
        if (t.rating >= 4) {
          hasFavorite = true;
          break;
        }
      }
      if (hasFavorite) {
        favs.add(a);
      }
    }
    System.out.println("Favs=" + favs);
    Collections.sort(favs, new Comparator<Album>() {
      public int compare(Album a1, Album a2) {
        return a1.name.compareTo(a2.name);
      }
    });
    System.out.println("Sorted Favs=" + favs);
  }

  static void exercise2Streams(List<Album> albums) {
    //  Hint: Make a pipeline that invokes the filter, sorted, and collect operations, in that order.

    // Note: getTracks returns a Stream on which we can apply anyMatch
    List<Album> list = albums.stream()
        .filter(a -> a.getTracks().anyMatch(t -> t.rating > 4))
        .sorted(Comparator.comparing(a -> a.name))
        .collect(Collectors.toList());
    System.out.println("exercise2Streams: " + list);
  }

  public static void main(String[] args) {
    List<Person> persons = Arrays.asList(new Person("Jim", 25, true), new Person("Alice", 28, false),
        new Person("Bob", 42, true), new Person("Tim", 19, true));
    exercise1(persons);

    Album a1 = new Album("Zeetles", "SongA", 5);
    a1.addTrack(new Album.Track("SongB", 10));
    a1.addTrack(new Album.Track("SongC", 4));
    Album a2 = new Album("RockStar", "SongP", 10);
    a2.addTrack(new Album.Track("SongQ", 9));
    a2.addTrack(new Album.Track("SongR", 8));

    List<Album> albums = Arrays.asList(a1, a2);
    exercise2(albums);
    exercise2Streams(albums);
  }

}
