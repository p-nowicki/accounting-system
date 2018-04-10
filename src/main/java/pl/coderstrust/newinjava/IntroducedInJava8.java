package pl.coderstrust.newinjava;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntroducedInJava8 {

  // Streams
  public List<Integer> generateIntegerList(int number) {
    return Stream.iterate(0, i -> i + 1).limit(number).collect(Collectors.toList());
  }

  // Predicate + method references + Optional + Lambda + Supplier
  public int sumOfProcessedListElements(List<Integer> list) {
    Calculable calculable = ii -> ii + 5;
    Supplier supplier = () -> Integer.valueOf(-1);
    return list.stream()
        .filter(ii -> ii % 2 == 0)
        .map(this::multiply)
        .map(calculable::calculate)
        .reduce((ii, jj) -> ii + jj)
        .orElseGet(supplier);
  }

  private Integer multiply(Integer number) {
    return number * number;
  }
}
