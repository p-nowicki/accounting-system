package pl.coderstrust.accounting.database.impl.multifile;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import pl.coderstrust.accounting.database.Database;
import pl.coderstrust.accounting.database.DatabaseTest;
import pl.coderstrust.accounting.database.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MultiFileDatabaseTest extends DatabaseTest {

  private static final String FILES_PATH = "src/test/resources/MultiFileDatabase/";
  private FileHelper fileHelper;
  private IdGenerator idGenerator;
  private PathHelper pathHelper;

  public MultiFileDatabaseTest() {
  }

  @Override
  public Database getDatabase() throws IOException {
    return new MultiFileDatabase (FILES_PATH, fileHelper, pathHelper, idGenerator);
  }

  @Before
  public void setup() throws IOException {
    File file = new File(FILES_PATH);
    FileUtils.deleteDirectory(file);
    Files.createDirectories(file.toPath());

    pathHelper = new PathHelper(FILES_PATH);
    fileHelper = new FileHelper();
    idGenerator = new IdGenerator(FILES_PATH, fileHelper, pathHelper);
  }
}