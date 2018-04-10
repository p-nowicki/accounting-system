package pl.coderstrust.newinjava;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class IntroducedInJava8Test {

  IntroducedInJava8 newJava8 = new IntroducedInJava8();

  @Test
  public void generateIntegerListShouldReturnListOfIntegers() {
    List<Integer> expectedResult = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
    List<Integer> result = newJava8.generateIntegerList(7);
    Assert.assertThat(result, is(expectedResult));
  }

  @Test
  public void sumOfListElementsShouldReturnCorrectValue() {
    int expectedResult = 76;
    int result = newJava8.sumOfProcessedListElements(newJava8.generateIntegerList(7));
    Assert.assertThat(result, is(expectedResult));
  }

  @Test
  public void sumOfListElementsShouldReturnNegativeNumberWhenEmptyList() {
    int expectedResult = -1;
    int result = newJava8.sumOfProcessedListElements(newJava8.generateIntegerList(0));
    Assert.assertThat(result, is(expectedResult));
  }
}