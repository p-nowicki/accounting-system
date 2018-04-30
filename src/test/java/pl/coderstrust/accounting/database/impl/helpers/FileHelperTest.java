package pl.coderstrust.accounting.database.impl.helpers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileHelperTest {

  private FileHelper fileHelper = new FileHelper();
  private File file = new File(
      "src/test/resources/newFile.txt"); // TODO should use temporaryFile / folder Junit rule

  @Test
  public void shouldReadExistingFile() throws IOException {
    // given
    List<String> expected = Arrays.asList("this is a test 1", "this is a test 2");
    Files.write(Paths.get(file.getAbsolutePath()), expected, StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING);

    //when
    List<String> result = fileHelper.readLinesFromFile(file);

    //then
    assertThat(result, is(expected));
  }

  @Test
  public void shouldWriteListToFile() throws IOException {
    //given
    file.delete();
    String expected = "some text to check";

    //when
    fileHelper.writeFile(file, expected);
    List<String> result = fileHelper.readLinesFromFile(file);

    //then
    assertThat(result.size(), is(1));
    assertThat(result.get(0), is(expected));
  }
}