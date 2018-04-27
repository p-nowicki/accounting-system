package pl.coderstrust.accounting.logic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


import org.junit.Test;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileHelperTest {

  private FileHelper fileHelper = new FileHelper();
  private String fileName = "src/test/resources/files/newFile";

  @Test
  public void shouldReadExistingFile() throws FileNotFoundException {

    List<String> expected = Arrays.asList("this is a test", "this is a test");
    //when
    List<String> result = fileHelper.readLinesFromFile("src/test/resources/files/testText2");
    //then
    assertThat(result, is(expected));
  }

  @Test
  public void shouldWriteListToFile() throws IOException {
    //given
    new File(fileName).delete();
    String matcher = "[some text to check ]";
    //when
    fileHelper.writeFile(fileName, "some text to check ");
    String result = String.valueOf(fileHelper.readLinesFromFile(fileName));
    //then
    assertThat(result, is(matcher));
  }
}